/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.constantes.MercadosConstantes;
import com.indeval.portaldali.middleware.services.common.ValidacionService;
import com.indeval.portaldali.middleware.services.mercadocapitales.util.Constantes;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessDataException;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.BusinessRulesMercadoCapitalesService;
import com.indeval.portaldali.middleware.services.modelo.CatalogoService;
import com.indeval.portaldali.middleware.services.modelo.EmisionSIVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.EmisoraVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.middleware.services.modelo.TraspasosDineroCompradorParams;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.middleware.services.util.UtilsDaliVO;
import com.indeval.portaldali.middleware.services.util.ValidadorCuentasEmision;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;
import com.indeval.portaldali.persistence.dao.common.DiaInhabilDaliDao;
import com.indeval.portaldali.persistence.dao.common.InstrumentoDaliDao;
import com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaSocInvDAO;
import com.indeval.portaldali.persistence.model.Instrumento;
import com.indeval.portaldali.persistence.util.DateUtil;
import com.indeval.portaldali.persistence.util.UtilsLog;
import com.indeval.portaldali.persistence.vo.AgentePK;
import com.indeval.portaldali.persistence.vo.ArchivoConciliacionMovimientosResultVO;
import com.indeval.portaldali.persistence.vo.ArchivoConciliacionResultVO;
import com.indeval.portaldali.persistence.vo.CapturaTraspasoMercadoCapitalesVO;
import com.indeval.portaldali.persistence.vo.EmisionPK;
import com.indeval.portaldali.persistence.vo.GetEstadocuentaMercadoVO;
import com.indeval.portaldali.persistence.vo.Mdintran;
import com.indeval.portaldali.persistence.vo.PageVO;
import com.indeval.portaldali.persistence.vo.ParamsConfirmaTraspasoVsPagoVO;
import com.indeval.portaldali.persistence.vo.SociedadInvVO;
import com.indeval.portaldali.persistence.vo.Valor;


/**
 * Implementacion del servicio de Mercado De Capitales.
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class MercadoCapitalesServiceImpl implements MercadoCapitalesService, Constantes {

	/** Constante de Serializacion */
	private static Logger logger = LoggerFactory.getLogger(MercadoCapitalesServiceImpl.class);

	/** Bean de acceso a datos de DiaInhabil */
	private DiaInhabilDaliDao diaInhabilDaliDao;
	
	/** Bean de acceso al Dao de Sociedades de Inversion */
	private EstadoCuentaSocInvDAO estadoCuentaSocInvDAO;

	/** Servicio de validaciones */
	private ValidacionService validacionService =  null;
	
	/** Bean de acceso a catalogoService */
	private CatalogoService catalogoService;

	/** Bean de acceso a DateUtilsDao */
	private DateUtilsDao dateUtilsDao;

	/** Bean para instrumento */
	private InstrumentoDaliDao instrumentoDaliDao;

	/** Bean para acceso al properties de mensaje de errores */
	private MessageResolver errorResolver;

	/** Bean para acceso al properties de pattern number */
	private MessageResolver patternResolver;

	/** Bean de acceso a UtilService */
	private UtilServices utilService;

	/** Bean de acceso a BusinessRulesMercadoCapitalesService */
	private BusinessRulesMercadoCapitalesService businessRulesMercadoCapitalesService;

	/** Mapa de fases validas para la captura de traspasos de capitales */
	private Map fasesValidasCapturaTraspasos;

	private List<String> cuentasInvalidas;

	/** Validador para el control de cuentas de emision */
	private ValidadorCuentasEmision validadorCuentasEmision;
	
	/**
	 * @see com.indeval.portaldali.middleware.services.mercadocapitales.MercadoCapitalesService#getCapturaTraspaso(com.indeval.portaldali.middleware.services.mercadocapitales.GetCapturaTraspasoParams)
	 */
	public BigInteger getCapturaTraspaso(GetCapturaTraspasoParams params)
			throws BusinessException {

		logger.info("Entrando a MercadoCapitalesServiceImpl.getCapturaTraspaso()");

		validaGetCapturaTraspaso(params);

		CapturaTraspasoMercadoCapitalesVO daoParams = new CapturaTraspasoMercadoCapitalesVO();

		/* Traspasante */
		daoParams.setIdTraspasante(params.getTraspasante().getId());
		daoParams.setFolTraspasante(params.getTraspasante().getFolio());
		daoParams.setCuentaTraspasante(params.getTraspasante().getCuenta());
		// El mapeo del dao usado para el catalogo de presentacion
		// envia el valor del tipo en el atributo tenencia
		// daoParams.setTipoCuentaTraspasante(params.getTraspasante().getTipo());
		daoParams.setTipoCuentaTraspasante(params.getTraspasante()
				.getTenencia());

		/* Receptor */
		daoParams.setIdReceptor(params.getReceptor().getId());
		daoParams.setFolReceptor(params.getReceptor().getFolio());
		daoParams.setCuentaReceptor(params.getReceptor().getCuenta());
		// El mapeo del dao usado para el catalogo de presentacion
		// envia el valor del tipo en el atributo tenencia
		// daoParams.setTipoCuentaReceptor(params.getReceptor().getTipo());
		daoParams.setTipoCuentaReceptor(params.getReceptor().getTenencia());

		/* EmisionPersistence */
		daoParams.setCupon(params.getEmision().getCupon());
		daoParams.setEmisora(params.getEmision().getEmisora());
		daoParams.setSerie(params.getEmision().getSerie());
		daoParams.setTv(params.getEmision().getIdTipoValor());

		daoParams.setCantidad(params.getCantidad());
		daoParams.setCveReporto(Constantes.CLAVE_REPORTO_T);
		daoParams.setDivisa(UtilsDaliVO.BLANK);
		daoParams.setFecha(params.getFecha());
		daoParams.setFechaLiquidacion(dateUtilsDao.getDateFechaDB());
		daoParams.setFechaReporto(dateUtilsDao.getDateFechaDB());
		daoParams.setFolioControl(UtilsDaliVO.INTEGER_ZERO);
		daoParams.setFolioDescripcion(UtilsDaliVO.BLANK);
		daoParams.setLlaveFolioMd(UtilsDaliVO.BLANK);

		/* Se crea la variable de retorno, se invoca el SP y se retorna el folio */
		BigInteger folio = null;
		if (StringUtils.isNotBlank(params.getTipoOperacion())
				&& params.getTipoOperacion().equals(Constantes.ACTIVO)) {
			// Si el traspaso es de Ctas. Activos S. I. el origen de la
			// aplicacion es FONDEO
			folio = null;
			// TODO
			// storeProcedureDao.capturarTraspasoMercadoCapitales(daoParams,
			// Constantes.ORIGEN_APLICACION_ACTIVO_FONDEO);

		} else {
			// Si el traspaso es de tipo TLP's el origen de la aplicacion es
			// MERSECMC
			folio = null;
			// TODO
			// storeProcedureDao.capturarTraspasoMercadoCapitales( daoParams,
			// Constantes.ORIGEN_APLICACION_TLP_MERSECMC);

		}

		if (folio == null) {
			throw new BusinessException("Error: El folio es nulo");
		}
		if (folio.intValue() < 0) {
			throw new BusinessException(errorResolver.getMessage(
					"bdcaptal..UP_tranmcap" + folio.toString(), folio
							.toString()));
		}
		return folio;
	}

	/**
	 * Encapsula las validaciones necesarias para getCapturaTraspaso()
	 * 
	 * @param params
	 * @throws BusinessException
	 */
	private void validaGetCapturaTraspaso(GetCapturaTraspasoParams params)
			throws BusinessException {

		logger.info("Entrando a MercadoCapitalesServiceImpl.validaGetCapturaTraspaso()");

		utilService.validaDTONoNulo(params, "");
		utilService.validaDTONoNulo(params.getAgenteFirmado(),
				" agente firmado ");
		params.getAgenteFirmado().tieneClaveValida();
		utilService.validaDTONoNulo(params.getTraspasante(), " traspasante ");
		params.getTraspasante().tieneClaveValida();
		utilService.validarClaveTipo(params.getTraspasante().getCuenta(),
				" La cuenta del traspasante ");
		// El mapeo del dao usado para el catalogo de presentacion
		// envia el valor del tipo en el atributo tenencia
		utilService.validarClaveTipo(params.getTraspasante().getTenencia(),
				" El tipo de cuenta del traspasante ");
		String cuentaTraspasante = params.getTraspasante().getCuenta().trim();
		utilService.validaDTONoNulo(params.getReceptor(), " receptor ");
		params.getReceptor().tieneClaveValida();
		utilService.validarClaveTipo(params.getReceptor().getCuenta(),
				" La cuenta del del agente receptor ");
		// El mapeo del dao usado para el catalogo de presentacion
		// envia el valor del tipo en el atributo tenencia
		utilService.validarClaveTipo(params.getReceptor().getTenencia(),
				" El tipo de cuenta del traspasante ");
		String cuentaReceptor = params.getReceptor().getCuenta().trim();
		utilService.validaDTONoNulo(params.getEmision(), " emision ");

		params.getEmision().tienePKValida();
		if (params.getCantidad() == null) {
			throw new BusinessException("La cantidad no puede ser nula");
		}
		utilService.validaNumber(params.getCantidad(), patternResolver
				.getMessage("cantidad"));

		// Esta validacion solo es para los traspasos de sociedades de inversion
		if (StringUtils.isNotBlank(params.getTipoOperacion())
				&& params.getTipoOperacion().equals(Constantes.ACTIVO)) {

			int faseActual = utilService
					.validaFase(fasesValidasCapturaTraspasos);

			if (cuentaTraspasante.equalsIgnoreCase(cuentaReceptor)) {
				throw new BusinessException(
						"La cuenta traspasante y receptora deben ser diferentes");
			}
			if (faseActual == 1) {
				if (!cuentaTraspasante.startsWith(Constantes.CUENTA_53)
						&& !cuentaTraspasante.startsWith(Constantes.CUENTA_97)
						&& !cuentaTraspasante.startsWith(Constantes.CUENTA_98)) {
					throw new BusinessException(errorResolver
							.getMessage("J0053"));
				}
			} else if (faseActual == 8) {
				if (!cuentaReceptor.startsWith(Constantes.CUENTA_53)
						&& !cuentaReceptor.startsWith(Constantes.CUENTA_97)
						&& !cuentaReceptor.startsWith(Constantes.CUENTA_98)) {
					throw new BusinessException(errorResolver
							.getMessage("J0054"));
				}
			} else {
				throw new BusinessException(errorResolver.getMessage("J0055"));
			}
		} else {
			/*
			 * Si no es traspaso de sociedades de inversion, se valida que no
			 * sea la misma cuenta
			 */
			if (params.getTraspasante().getClave().equals(
					params.getReceptor().getClave())
					&& params.getTraspasante().getCuenta().equals(
							params.getReceptor().getCuenta())) {
				throw new BusinessException(errorResolver.getMessage("J0056"));
			}

			// Se valida que el traspaso no sea ninguna de las cuentas de la CCV
			// que inician con 25
			if (params.getReceptor().getId().trim()
					.equals(Constantes.CUENTA_25)) {
				throw new BusinessException(errorResolver.getMessage("J0057"));
			}
		}

	}

	/**
	 * @see com.indeval.portaldali.middleware.services.mercadocapitales.MercadoCapitalesService#getPosicionValorPlain(com.indeval.portaldali.middleware.services.mercadocapitales.GetPosicionValorMerCapParams)
	 */
	public PosicionValorTotalVO[] getPosicionValorPlain(
			GetPosicionValorMerCapParams params) throws BusinessException {

		logger.info("Entrando a MercadoCapitalesServiceImpl.getPosicionValorPlain()");

		// Se valida al agente firmado
		if (params.getAgenteFirmado() == null) {
			throw new BusinessException("No se recibio el agente firmado");
		}
		params.getAgenteFirmado().tieneClaveValida();
		AgentePK pkAgente = UtilsDaliVO.getAgentePK(params.getAgenteFirmado());

		EmisionPK pkEmision = UtilsDaliVO.getEmisionPK(params.getClaveValor());

		// Valida que la pagina sea valida o construye una valida
		params.setPaginaVO(UtilsDaliVO.getPaginaNotBlank(params.getPaginaVO()));

		List listPosicionValorTotal = new ArrayList();
		BigInteger granTotalSldoDisp = UtilsDaliVO.BIG_INTEGER_ZERO;
		BigDecimal granTotalValMerc = UtilsDaliVO.BIG_DECIMAL_ZERO;
		BigDecimal granTotalValNom = UtilsDaliVO.BIG_DECIMAL_ZERO;

		/* Obtiene los tipos de valor */
		List listaTV = null;
		// TODO
		// valorDao.getDistinctTv(pkAgente, pkEmision);
		if (listaTV == null || listaTV.isEmpty()) {
			throw new BusinessDataException(errorResolver.getMessage("J9999"),
					"J9999");
		}
		// UtilsLog.logElementosLista(listaTV);

		/* Pasa los elementos de la lista al arreglo de TV's de salida */
		String[] arregloTVs = new String[listaTV.size()];
		arregloTVs = (String[]) listaTV.toArray(arregloTVs);

		/* Se recupera los resultados por tipo de valor */
		for (Iterator iter = listaTV.iterator(); iter.hasNext();) {

			String element = (String) iter.next();
			pkEmision.setTv(element);

			// Construye el objeto pageVO a partir de paginaVO
			PageVO pageVO = UtilsDaliVO.getPageVO(params.getPaginaVO());

			/* Ejecuta el DAO para la consulta */
			if (StringUtils.isBlank(params.getTipoValor())
					|| !element.trim().equalsIgnoreCase(
							params.getTipoValor().trim())) {
				pageVO.setOffset(UtilsDaliVO.INTEGER_ZERO);
			}
			PageVO paginaResultado = null;
			// TODO
			// valorDao.getPosicionValor(pkAgente, pkEmision, pageVO);

			if (paginaResultado == null
					|| paginaResultado.getRegistros() == null
					|| paginaResultado.getRegistros().isEmpty()) {
				throw new BusinessDataException(errorResolver
						.getMessage("J9999"), "J9999");
			}

			/*
			 * Pasa los valores del POJO al VO resultante y los almacena en la
			 * pagina
			 */
			List listaResultado = getListaPosicionValor(paginaResultado
					.getRegistros());
			paginaResultado.setRegistros(listaResultado);
			// params.setPaginaVO(UtilsDaliVO.getPaginaVO(paginaResultado));

			/*
			 * Obtiene los totales (saldo_disponible, valuacion_mercado,
			 * valuacion_nominal) en un map
			 */
			Map totales = paginaResultado.getValores();
			UtilsLog.logClaveValor(totales);

			/* Obtiene total de registros con cupon cortado 'F' - FIRME */
			BigInteger totalCuponCortadoF = new BigInteger("0");
			// TODO
			// new BigInteger(valorDao .getTotalPosicionValorCuponF(pkAgente,
			// pkEmision) .toString());

			/* Settea los valores al VO de salida */
			PosicionValorTotalVO posicionValorTotal = new PosicionValorTotalVO();
			posicionValorTotal.setCuentaActual(pkAgente.getCuenta());
			posicionValorTotal.setPagina(UtilsDaliVO.getPaginaVO(paginaResultado));
			posicionValorTotal.setTotalSaldoDisponible(((BigDecimal) totales
					.get("TOTAL_SALDO_DISPONIBLE")).toBigInteger());
			posicionValorTotal.setTotalValuacionMercado((BigDecimal) totales
					.get("TOTAL_VALUACION_MERCADO"));
			posicionValorTotal.setTotalValuacionNominal((BigDecimal) totales
					.get("TOTAL_VALUACION_NOMINAL"));
			posicionValorTotal.setTotalRegCuponCortadoF(totalCuponCortadoF);

			posicionValorTotal.setTvs(arregloTVs);

			/* Realiza la suma para el gran total */
			if (posicionValorTotal.getTotalSaldoDisponible() != null) {
				granTotalSldoDisp = granTotalSldoDisp.add(posicionValorTotal
						.getTotalSaldoDisponible());
			}
			if (posicionValorTotal.getTotalValuacionMercado() != null) {
				granTotalValMerc = granTotalValMerc.add(posicionValorTotal
						.getTotalValuacionMercado());
			}
			if (posicionValorTotal.getTotalValuacionNominal() != null) {
				granTotalValNom = granTotalValNom.add(posicionValorTotal
						.getTotalValuacionNominal());
			}
			/* Almacena el objeto VO en la lista */
			listPosicionValorTotal.add(posicionValorTotal);
		}

		/* Almacena los totales generales */
		List listaPosicionValorTotal = new ArrayList();
		for (Iterator iter = listPosicionValorTotal.iterator(); iter.hasNext();) {
			PosicionValorTotalVO posicionValorTotal = (PosicionValorTotalVO) iter
					.next();
			posicionValorTotal.setGranTotalSaldoDisponible(granTotalSldoDisp);
			posicionValorTotal.setGranTotalValuacionMercado(granTotalValMerc);
			posicionValorTotal.setGranTotalValuacionNominal(granTotalValNom);
			listaPosicionValorTotal.add(posicionValorTotal);
		}

		/* Pasa los elementos de la lista al arreglo de VO's de salida */
		PosicionValorTotalVO[] posicionValorTotalResult = new PosicionValorTotalVO[listaPosicionValorTotal
				.size()];
		posicionValorTotalResult = (PosicionValorTotalVO[]) listaPosicionValorTotal
				.toArray(posicionValorTotalResult);

		return posicionValorTotalResult;
	}

	/**
	 * Pasa los valores del POJO al VO resultante
	 * 
	 * @param lista
	 * @return List
	 * @throws BusinessException
	 */
	private List getListaPosicionValor(List lista) throws BusinessException {
		logger.info("Entrando a MercadoCapitalesServiceImpl.getListaPosicionValor()");
		List listaReturn = new ArrayList();
		for (int i = 0; i < lista.size(); i++) {
			PosicionValorVO posicionValorVO = new PosicionValorVO();
			EmisionVO emision = new EmisionVO();
			Object[] valores = (Object[]) lista.get(i);
			Valor valor = (Valor) valores[0];
			emision.setIdTipoValor(valor.getEmision().getEmisionPk().getTv());
			emision.setEmisora(valor.getEmision().getEmisionPk().getEmisora());
			emision.setSerie(valor.getEmision().getEmisionPk().getSerie());
			emision.setCupon(valor.getEmision().getEmisionPk().getCupon());
			posicionValorVO.setClaveValor(emision);
			posicionValorVO.setCuponCortado(valor.getEmision().getCuponCortado());
			posicionValorVO.setAgente(UtilsDaliVO.getAgenteVO(valor.getAgente()));
			posicionValorVO.setSaldoDisponible(valor.getSaldoDisponible());
			posicionValorVO.setUltimoHecho(!valor.getAgente().getTenencia().equalsIgnoreCase(
				Constantes.TENENCIA_TESORERIA) ? valor.getEmision().getUltimoHecho() : UtilsDaliVO.BIG_DECIMAL_ZERO);
			posicionValorVO.setValorNominal(valor.getEmision().getValorNominal());
			posicionValorVO.setValuacionMercado(getValuacionMercado(
				posicionValorVO.getSaldoDisponible(), posicionValorVO.getUltimoHecho()));
			posicionValorVO.setValuacionNominal(getValuacionNominal(
				posicionValorVO.getSaldoDisponible(), posicionValorVO.getValorNominal()));
			listaReturn.add(posicionValorVO);
		}
		return listaReturn;
	}

	/**
	 * @see com.indeval.portaldali.middleware.services.mercadocapitales.MercadoCapitalesService#getEstadoCuentaSociedadesInversion(com.indeval.portaldali.middleware.services.mercadocapitales.GetEstadoCuentaSociedadesInvParams)
	 */
	public EstadoCuentaSocInvVO[] getEstadoCuentaSociedadesInversion(
			GetEstadoCuentaSociedadesInvParams getEstadoCuentaSociedadesInvParams)
			throws BusinessException {

		logger.info("Entrando a MercadoCapitalesServiceImpl.getEstadoCuentaSociedadesInversion()");

		/* Se validan los parametros */
		utilService.validaDTONoNulo(getEstadoCuentaSociedadesInvParams, "");
		utilService.validaDTONoNulo(getEstadoCuentaSociedadesInvParams
				.getAgenteFirmado(), " agente firmado ");
		getEstadoCuentaSociedadesInvParams.getAgenteFirmado()
				.tieneClaveValida();

		/* Se construye el agentePK */
		AgentePK agentefirmadoPK = UtilsDaliVO
				.getAgentePK(getEstadoCuentaSociedadesInvParams
						.getAgenteFirmado());

		/*
		 * Se construye la emisionPK y se obtiene la lista de sociedades de
		 * inversion
		 */
		EmisionPK emisionPK = new EmisionPK();
		emisionPK.setTv(getEstadoCuentaSociedadesInvParams.getTv());
		logger.debug("tv [" + getEstadoCuentaSociedadesInvParams.getTv() + "]");
		emisionPK.setEmisora(getEstadoCuentaSociedadesInvParams.getEmisora());
		logger.debug("emisora [" + getEstadoCuentaSociedadesInvParams.getEmisora()
				+ "]");
		SociedadInversionVO[] inversionVOs = obtenerListaSociedadesInversion(
				agentefirmadoPK, emisionPK);

		/* Se crea la referencia a la lista de detalles */
		List listDetalle = null;

		/* Se crea la lista de registros a retornar */
		List listaDeEstadoCuentaSocInvVO = new ArrayList();

		/* Si no es exportacion */
		if (!getEstadoCuentaSociedadesInvParams.isExportacion()) {

			/*
			 * Si viene la emisora se utiliza esta, en caso contrario se toma la
			 * primera
			 */
			if (StringUtils.isBlank(getEstadoCuentaSociedadesInvParams
					.getEmisora())) {
				emisionPK.setEmisora(inversionVOs[0].getEmisora());
			} else {
				emisionPK.setEmisora(getEstadoCuentaSociedadesInvParams
						.getEmisora().trim());
			}
			/*
			 * Se recuperan los Saldos de las Sociedades de Inversion y se
			 * validan
			 */
			listDetalle = null;
			// TODO
			// valorDao.getSaldosSociedadesInversion( agentefirmadoPK,
			// emisionPK);

			if (listDetalle == null || listDetalle.isEmpty()) {
				throw new BusinessDataException(errorResolver
						.getMessage("J9999"), "J9999");
			}

			/* Se construye la lista de detalles a retornar */
			List listRetorno = new ArrayList();
			for (Iterator iter = listDetalle.iterator(); iter.hasNext();) {
				listRetorno.add(sociedadInvVOToDetalleVo((SociedadInvVO) iter
						.next()));
			}

			/* Se crea el objeto a retornar */
			EstadoCuentaSocInvVO estadoCuentaSocInvVO = new EstadoCuentaSocInvVO();

			/* Se pagina */
			estadoCuentaSocInvVO.setPaginaVO(UtilsDaliVO
					.getPaginaNotBlank(getEstadoCuentaSociedadesInvParams
							.getPaginaVO()));
			estadoCuentaSocInvVO.getPaginaVO().extraerSublist(listRetorno);
			estadoCuentaSocInvVO.setSociedades(inversionVOs);
			estadoCuentaSocInvVO.setSociedadActual(inversionVOs[0]);

			listaDeEstadoCuentaSocInvVO.add(estadoCuentaSocInvVO);

		}
		/* Si es exportacion */
		else {

			/*
			 * Se recuperan los Saldos de las Sociedades de Inversion y se
			 * validan
			 */
			listDetalle = null;
			// TODO
			// valorDao.getSaldosSociedadesInversion( agentefirmadoPK,
			// emisionPK);

			if (listDetalle == null || listDetalle.isEmpty()) {
				throw new BusinessDataException(errorResolver
						.getMessage("J9999"), "J9999");
			}

			Map mapaEmisoras = new HashMap(); // Mapa para filtrar las
			// emisoras ???

			/* Se crea la lista de registros a exportar */
			List listaDeExportacion = new ArrayList();

			/* Se itera la lista de detalles */
			for (Iterator iter = listDetalle.iterator(); iter.hasNext();) {

				SociedadInvVO invVO = (SociedadInvVO) iter.next();

				/*
				 * Se construye el EstadoCuentaSocInvDetalleVO a partir del
				 * SociedadInvVO
				 */
				EstadoCuentaSocInvDetalleVO invDetalleVO = sociedadInvVOToDetalleVo(invVO);

				/*
				 * Se valida el SociedadInvVO, se verifica que la emisora aun no
				 * halla sido considerada, se crea el objeto
				 * estadoCuentaSocInvVO y se aade a la lista de exportacion
				 */
				if (invDetalleVO != null) {
					EstadoCuentaSocInvVO estadoCuentaSocInvVO = (EstadoCuentaSocInvVO) mapaEmisoras
							.get(invDetalleVO.getEmisionVO().getEmisora());

					if (estadoCuentaSocInvVO == null) {

						estadoCuentaSocInvVO = new EstadoCuentaSocInvVO();
						mapaEmisoras.put(invDetalleVO.getEmisionVO()
								.getEmisora(), estadoCuentaSocInvVO);

						/*
						 * Se utiliza una paginaVO nueva para enviar todos los
						 * registros, omitiendo el valor del offset y el de
						 * registrosXPag
						 */
						estadoCuentaSocInvVO.setPaginaVO(new PaginaVO());
						estadoCuentaSocInvVO.getPaginaVO().setRegistros(
								new ArrayList());
						estadoCuentaSocInvVO
								.setSociedadActual(new SociedadInversionVO(
										invVO.getEmisora(), invVO
												.getRazonSocial()));

						listaDeExportacion.add(estadoCuentaSocInvVO);
					}
					estadoCuentaSocInvVO.getPaginaVO().getRegistros().add(
							invDetalleVO);
				}

			}

			/*
			 * Se itera la lista de exportacion y se settean los registros
			 * totales a cada pagina
			 */
			for (Iterator iter = listaDeExportacion.iterator(); iter.hasNext();) {
				EstadoCuentaSocInvVO estadoCuentaSocInvVO = (EstadoCuentaSocInvVO) iter
						.next();
				estadoCuentaSocInvVO.getPaginaVO().setTotalRegistros(
						new Integer(estadoCuentaSocInvVO.getPaginaVO()
								.getRegistros().size()));
			}

			listaDeEstadoCuentaSocInvVO.addAll(listaDeExportacion);

		}

		/*
		 * Se valida la lista de EstadoCuentaSocInvVO y se construye el arreglo
		 * a retornar
		 */
		if (listaDeEstadoCuentaSocInvVO == null
				|| listaDeEstadoCuentaSocInvVO.isEmpty()) {
			throw new BusinessDataException(errorResolver.getMessage("J9999"),
					"J9999");
		}
		EstadoCuentaSocInvVO[] datos = new EstadoCuentaSocInvVO[listaDeEstadoCuentaSocInvVO
				.size()];
		listaDeEstadoCuentaSocInvVO.toArray(datos);

		return datos;

	}

	/**
	 * Obtiene la lista de Sociedades de Inversion de una institucion
	 * 
	 * @param agentefirmadoPK
	 * @param emisionPK
	 * @return SociedadInversionVO[]
	 * @throws BusinessDataException
	 */
	private SociedadInversionVO[] obtenerListaSociedadesInversion(
			AgentePK agentefirmadoPK, EmisionPK emisionPK)
			throws BusinessDataException {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl.obtenerListaSociedadesInversion()");

		/* Se recupera y valida la lista de sociedades de inversion */
		List listaSociedadesInversion = null;
		// TODO
		// valorDao.getListaSociedadesInversion( agentefirmadoPK, emisionPK);

		if (listaSociedadesInversion == null
				|| listaSociedadesInversion.isEmpty()) {
			throw new BusinessDataException(errorResolver.getMessage("J9999"),
					"J9999");
		}

		/* Se convierte la lista de sociedades de inversion en un arreglo */
		SociedadInversionVO[] inversionVOs = convertList2ArraySociedadInversionVO(listaSociedadesInversion);

		return inversionVOs;
	}

	/**
	 * Construye un arreglo de SociedadInversionVO a partir de la lista recibida
	 * 
	 * @param listaSociedadInversionVO
	 * @return SociedadInversionVO[]
	 */
	private SociedadInversionVO[] convertList2ArraySociedadInversionVO(
			List listaSociedadInversionVO) {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl.convertList2ArraySociedadInversionVO()");

		if (listaSociedadInversionVO != null) {
			SociedadInversionVO[] inversionVOs = new SociedadInversionVO[listaSociedadInversionVO
					.size()];
			int i = 0;
			for (Iterator iter = listaSociedadInversionVO.iterator(); iter
					.hasNext();) {
				Object[] element = (Object[]) iter.next();
				inversionVOs[i++] = new SociedadInversionVO(
						(String) element[1], (String) element[0]);
			}
			return inversionVOs;
		}

		/* Si la lista es null se retorna null */
		return null;
	}

	/**
	 * Construye un EstadoCuentaSocInvDetalleVO a partir de un SociedadInvVO
	 * 
	 * @param sociedadInvVO
	 * @return EstadoCuentaSocInvDetalleVO
	 */
	private EstadoCuentaSocInvDetalleVO sociedadInvVOToDetalleVo(
			SociedadInvVO sociedadInvVO) {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl.SociedadInvVOToDetalleVo()");

		EstadoCuentaSocInvDetalleVO detalleVO = null;

		if (sociedadInvVO != null) {
			detalleVO = new EstadoCuentaSocInvDetalleVO();
			EmisionVO emisionVO = new EmisionVO();
			emisionVO.setIdTipoValor(sociedadInvVO.getTv());
			emisionVO.setEmisora(sociedadInvVO.getEmisora());
			emisionVO.setSerie(sociedadInvVO.getSerie());
			emisionVO.setCupon(sociedadInvVO.getCupon());
			detalleVO.setEmisionVO(emisionVO);
			detalleVO.setSaldoCirculacion(sociedadInvVO.getSaldoCir());
			detalleVO.setSaldoTesoreria(sociedadInvVO.getSaldoTes());
			detalleVO.setSaldoTotal(sociedadInvVO.getSaldoTot());
		}

		return detalleVO;
	}

	/**
	 * Obtiene la valuacion mercado a partir del saldo disponible y el ultimo
	 * hecho
	 * 
	 * @param saldoDisponible
	 * @param ultimoHecho
	 * @return BigDecimal
	 */
	private BigDecimal getValuacionMercado(BigDecimal saldoDisponible,
			BigDecimal ultimoHecho) {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl.getValuacionMercado()");

		BigDecimal valuacionMercado = null;
		if (saldoDisponible != null && ultimoHecho != null) {
			valuacionMercado = (ultimoHecho.multiply(saldoDisponible));
		}
		return valuacionMercado;

	}

	/**
	 * Obtiene la valuaci&oacute;n nominal a partir del Saldo Disponible y el
	 * Valor Nominal.
	 * 
	 * @param saldoDisponible
	 * @param valorNominal
	 * @return BigDecimal
	 */
	private BigDecimal getValuacionNominal(BigDecimal saldoDisponible,
			BigDecimal valorNominal) {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl.getValuacionNominal()");

		BigDecimal valuacionNominal = ((valorNominal != null && saldoDisponible != null) ? valorNominal
				.multiply(saldoDisponible)
				: null);
		return valuacionNominal;
	}

	/**
	 * @see com.indeval.portaldali.middleware.services.mercadocapitales.MercadoCapitalesService
	 *      #getNuevoEstadoCuentaMercadoCapital(
	 *      com.indeval.portaldali.middleware.services.mercadocapitales.GetEstadocuentaMercadoParams)
	 */
	public EstadoCuentaTotalVO[] getNuevoEstadoCuentaMercadoCapital(
			GetEstadocuentaMercadoParams params) throws BusinessException {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl.getNuevoEstadoCuentaMercadoCapital()");

		// Se validan los parametros de entrada
		Assert.notNull(params, "No se recibieron los parametros necesarios");
		params.validaParams();

		// Se apaga la bandera de obtencion de listas
		params.setObtenerListas(false);

		String[] arregloCuentas = null;

		// Si no se recibio la cuenta, se recuperan todas las cuentas del agente
		// y se settea la primera cuenta del arreglo de cuentas
		if (StringUtils.isBlank(params.getAgenteFirmado().getCuenta())) {

			// Se construye la lista de Cuentas
			List listaCuentas = null;
			// TODO
			// traspasoDepositosRetirosDao
			// .getListaCuentasNuevoEstadoCuentaMercadoCapitales(parseGetEstadocuentaMercadoParams2GetEstadocuentaMercadoVO(params));

			if (listaCuentas == null || listaCuentas.isEmpty()) {
				throw new BusinessException("No hay registros para el agente "
						+ params.getAgenteFirmado().toResumeString());
			}
			logger.debug(" lista de las cuentas [" + listaCuentas + "]");

			// Se convierte la lista en un arreglo de cuentas
			arregloCuentas = new String[listaCuentas.size()];
			arregloCuentas = (String[]) listaCuentas.toArray(arregloCuentas);

			// Se settea la primera cuenta del arreglo
			params.getAgenteFirmado().setCuenta(arregloCuentas[0]);
		} else {
			// Si se recibio la cuenta, se coloca como unico elemento del
			// arreglo de cuentas
			arregloCuentas = new String[1];
			arregloCuentas[0] = params.getAgenteFirmado().getCuenta();
		}

		List listaCuentasARecuperar = new ArrayList();
		if (params.isExport()) {
			listaCuentasARecuperar.addAll(Arrays.asList(arregloCuentas)); // si
			// se
			// va a
			// exportar
			// la lista a recorrer es toda la lista de cuentas
		} else {// si no se va a exportar se toma la cuenta setteada en el
			// params,
			// bien el dato que se recibe como parametro, o el primer elemento
			// de la lista
			listaCuentasARecuperar.add(params.getAgenteFirmado().getCuenta());
		}

		// Se construye el mapa de EmisionesPK a pasar al Dao
		Map mapaCuentaArregloEmisionesPersistence = new HashMap();
		// Se construye el mapa de EmisionesVO a enviar al retorno
		Map mapaCuentaArregloEmisiones = new HashMap();
		// Se construye la lista de AgentesPK a pasar al Dao
		List listaAgentesPK = obtenerAgentesPK(params.getAgenteFirmado(),
				listaCuentasARecuperar);
		logger.debug(" Hay [" + listaAgentesPK.size()
				+ "] cuentas para el agente ["
				+ params.getAgenteFirmado().toString() + "] ");
		logger.debug("Imprimendo las AgentesPK");
		UtilsLog.logElementosLista(listaAgentesPK);

		// Se convierte en un arreglo de agentesPK
		AgentePK[] arregloAgentePKs = new AgentePK[listaAgentesPK.size()];
		arregloAgentePKs = (AgentePK[]) listaAgentesPK
				.toArray(arregloAgentePKs);

		// Si no es export la iteracion se reduce a una sola pasada
		for (Iterator iter = listaAgentesPK.iterator(); iter.hasNext();) {

			AgentePK elementoARecuperar = (AgentePK) iter.next();

			// Se setea la cuenta en el params y se obtienen las emisiones
			// asociadas
			params.getAgenteFirmado().setCuenta(elementoARecuperar.getCuenta());

			// Se recupera el arreglo de emisiones a consultar (se filtra por
			// tv, emision, serie, cupon)
			CuentasYEmisionesEstadoCuentaCapitalesVO cuentasYEmisiones = null;
			// TODO
			// catalogoService
			// .getListaEmisionesByCuentaEstadoCuentaMercadoCapitales(params);
			EmisionVO[] arregloEmisionesVO = cuentasYEmisiones
					.getArregloEmisionesCuenta();
			if (arregloEmisionesVO == null || arregloEmisionesVO.length <= 0) {
				throw new BusinessException(
						"ERROR : Ha ocurrido un error inesperado al recuperar "
								+ "el arreglo de emisiones para ["
								+ elementoARecuperar.toString() + "]");
			}
			logger.debug("Imprimiendo las emisionesVO a colocar en el mapa");
			UtilsLog.logElementosArreglo(arregloEmisionesVO, false);

			// Se colocan en el mapa de emisionesVO todas las emisionesVO
			// asociadas con la cuenta
			mapaCuentaArregloEmisiones.put(elementoARecuperar,
					arregloEmisionesVO);

			// Se coloca en el mapa de persistencia las emisionesPK asociadas
			// con la cuenta
			if (params.isExport()) { // todas las emisiones si es exportacion
				mapaCuentaArregloEmisionesPersistence.put(elementoARecuperar,
						parseObject2EmisionesVO(Arrays
								.asList(arregloEmisionesVO), true));
			} else { // solo la primera emision si no es exportacion
				EmisionVO[] arregloEmisionesVONoExport = { arregloEmisionesVO[0] };
				mapaCuentaArregloEmisionesPersistence.put(elementoARecuperar,
						parseObject2EmisionesVO(Arrays
								.asList(arregloEmisionesVONoExport), true));
			}

			// Se recuperan todas las emisiones de la cuenta (no se filtra por
			// tv, emision, serie, cupon)
			GetEstadocuentaMercadoParams paramsTotal = new GetEstadocuentaMercadoParams();
			BeanUtils.copyProperties(params, paramsTotal);
			paramsTotal.setClaveValor(null);
			CuentasYEmisionesEstadoCuentaCapitalesVO cuentasYEmisionesTotal = null;
			// TODO
			// catalogoService
			// .getListaEmisionesByCuentaEstadoCuentaMercadoCapitales(paramsTotal);
			EmisionVO[] arregloEmisionesVOTotal = cuentasYEmisionesTotal
					.getArregloEmisionesCuenta();
			logger.debug("Imprimiendo las emisionesVO a colocar en el arreglo");
			UtilsLog.logElementosArreglo(arregloEmisionesVOTotal, false);
			mapaCuentaArregloEmisiones.put(Constantes.TODAS,
					arregloEmisionesVOTotal);
		}
		logger.debug("Imprimiendo el mapaCuentaArregloEmisiones");
		UtilsLog.logClaveValor(mapaCuentaArregloEmisiones);

		// Se recuperan los tipos de operacion
		String[] tipoOperacion2 = recuperaTiposOperacion(params
				.getTipoOperacion());
		// log.debug(" invoking traspasoDao.getEstadoCuentaMercadoCapital ["
		// + arregloAgentePKs + "] ["
		// + mapaCuentaArregloEmisionesPersistence + "]");

		// Se buscan los registros
		PageVO[] arregloPageVO = null;
		// TODO
		// traspasoDepositosRetirosDao
		// .getEstadoCuentaMercadoCapital(arregloAgentePKs,
		// mapaCuentaArregloEmisionesPersistence, params .getFechaOperacion(),
		// params.getOrigen(), params.getAplicacion(), tipoOperacion2, UtilsDaliVO
		// .getPageVO(params.getPaginaVO()));

		// Se verifica si existen registros y se arroja la excepcion si no hay
		if (arregloPageVO == null || arregloPageVO.length <= 0) {
			throw new BusinessException(
					"No se encontraron registros con los datos ingresados");
		}
		logger.debug("Cantidad de paginas en el arreglo = ["
				+ arregloPageVO.length + "]");

		// Se obtienen las PaginaVO del arreglo de PageVO obtenido
		PaginaVO[] arregloPaginas = parsePaginas(arregloPageVO);

		// Se construye el objeto de retorno
		List listaRetorno = new ArrayList();

		// Variables para controlar la iteracion en los elementos de cada
		// arreglo de emisiones
		int j = 0;

		StringBuffer sbCuenta = new StringBuffer("");

		// Por cada pagina, se convierten los elementos de registro de Objects a
		// VOs y se preparan los totales
		for (int i = 0; i < arregloPaginas.length; i++) {

			// Se construye el VO
			EstadoCuentaTotalVO estadoCuentaTotalVO = new EstadoCuentaTotalVO();
			boolean registroValido = true;

			// Si es exportacion no se requieren los arreglos de cuenta y
			// emisiones
			if (!params.isExport()) {
				// Se settea el arreglo de cuentas (todas)
				estadoCuentaTotalVO.setCuenta(arregloCuentas);
				// Se settea el arreglo de emisiones (todas)
				estadoCuentaTotalVO
						.setEmisiones((EmisionVO[]) mapaCuentaArregloEmisiones
								.get(Constantes.TODAS));
			}

			// Se construye el objeto de totales
			TotalesEstadoCuentaVO totales = new TotalesEstadoCuentaVO();

			// Se convierten los arreglos de Object en los VOs de registro
			Map mapaParser = parseObject2EstadoCuentaVO(arregloPaginas[i]
					.getRegistros(), totales);
			List registros = (List) mapaParser.get(Constantes.REGISTROS);
			if (registros == null || registros.isEmpty()) {
				throw new BusinessException(
						"ERROR : Ha ocurrido un error inesperado "
								+ "en la construccion de los objetos de registro");
			}
			arregloPaginas[i].setRegistros(registros);
			estadoCuentaTotalVO.setPaginaVO(arregloPaginas[i]);

			// Se construye el objeto de posicion
			String[] posicion = new String[6];
			// Se indica en la posicion, el nombre corto
			posicion[5] = ((EstadoCuentaRegistroVO) registros.get(0))
					.getAgenteFirmado() != null ? ((EstadoCuentaRegistroVO) registros
					.get(0)).getAgenteFirmado().getNombreCorto()
					: UtilsDaliVO.BLANK;

			// Se verifica si la emision es de la misma cuenta del agente
			// firmado y se incrementa
			// el contador de la iteracion del arreglo de emisiones, en caso de
			// que no sea la misma
			// se cambia la cuenta y se reinicia el contador a 0.
			if (StringUtils.isBlank(sbCuenta.toString())) {
				sbCuenta.append((String) mapaParser
						.get(Constantes.CUENTA_AGENTE_FIRMADO));
			} else {
				String cuentaAgenteFirmado = (String) mapaParser
						.get(Constantes.CUENTA_AGENTE_FIRMADO);
				logger.debug("cuentaAgenteFirmado = " + cuentaAgenteFirmado);
				if (StringUtils.isBlank(cuentaAgenteFirmado)
						|| cuentaAgenteFirmado.equalsIgnoreCase(sbCuenta
								.toString())) {
					j++;
				} else {
					if (cuentaAgenteFirmado == null) {
						registroValido = false;
					}
					j = 0;
					sbCuenta.delete(0, sbCuenta.length());
					sbCuenta.append((String) mapaParser
							.get(Constantes.CUENTA_AGENTE_FIRMADO));
				}
			}
			// Se indica en la posicion, la cuenta
			posicion[0] = sbCuenta.toString();

			AgentePK agentePKIndex = UtilsDaliVO.getAgentePK(params
					.getAgenteFirmado());
			agentePKIndex.setCuenta(sbCuenta.toString());

			EmisionVO[] arregloEmisionesCuenta = (EmisionVO[]) mapaCuentaArregloEmisiones
					.get(agentePKIndex);
			if (arregloEmisionesCuenta == null
					|| arregloEmisionesCuenta.length <= 0) {
				logger.debug("arregloEmisionesCuenta = " + arregloEmisionesCuenta
						+ " para el " + agentePKIndex.toString());
				throw new BusinessException(
						"ERROR : Ha ocurrido un error inesperado");
			}

			// Se indica en la posicion, la tv, emisora, serie y cupon
			posicion[1] = arregloEmisionesCuenta[j].getIdTipoValor();
			posicion[2] = arregloEmisionesCuenta[j].getEmisora();
			posicion[3] = arregloEmisionesCuenta[j].getSerie();
			posicion[4] = arregloEmisionesCuenta[j].getCupon();

			// Se calculan y settean los totales
			totales = (TotalesEstadoCuentaVO) mapaParser
					.get(Constantes.TOTALES);

			if (arregloEmisionesCuenta[j].getSaldoDisponible() != null) {
				totales.setSaldoActual(arregloEmisionesCuenta[j]
						.getSaldoDisponible()); // saldodisponible
			}

			if (arregloEmisionesCuenta[j].getSaldoInicialDia() != null) {
				totales.setSaldoInicial(arregloEmisionesCuenta[j]
						.getSaldoInicialDia()); // saldoinicialdia
				/* saldo calculado = saldo actual + entradas - salidas */
				totales.setSaldoCalculado(totales.getSaldoInicial()
						.toBigInteger().add(totales.getTotalEntradas())
						.subtract(totales.getTotalSalidas()));
			} else {
				arregloEmisionesCuenta[j]
						.setSaldoInicialDia(UtilsDaliVO.BIG_DECIMAL_ZERO);
			}

			estadoCuentaTotalVO.setTotales(totales);
			estadoCuentaTotalVO.setPosicion(posicion);

			if (registroValido) {
				// Se agrega el VO a la lista de retorno
				estadoCuentaTotalVO.getPaginaVO().setTotalRegistros(
						new Integer(estadoCuentaTotalVO.getPaginaVO()
								.getRegistros().size()));
				listaRetorno.add(estadoCuentaTotalVO);

			}

		}

		EstadoCuentaTotalVO[] estadosCuentaTotalVO = new EstadoCuentaTotalVO[listaRetorno
				.size()];
		estadosCuentaTotalVO = (EstadoCuentaTotalVO[]) listaRetorno
				.toArray(estadosCuentaTotalVO);

		return estadosCuentaTotalVO;
	}

	/**
	 * Este metodo construye un arreglo de PaginaVO a partir del arreglo de
	 * PageVO recibido aplicando la paginacion con el metodo extraerSublist y
	 * verificando que el total de registros global sea mayor que cero, en caso
	 * contrario arroja una BusinessException indicando que no se encontraron
	 * registros.
	 * 
	 * @param arregloPaginas
	 * @return PaginaVO[]
	 * @throws BusinessException
	 */
	private PaginaVO[] parsePaginas(PageVO[] arregloPageVO)
			throws BusinessException {

		logger.info("Entrando a MercadoCapitalesServiceImpl.parsePaginas()");

		int totalRegistros = 0; // Variable para determinar el total de
		// registros global
		List listaPaginasVO = new ArrayList();

		for (int i = 0; i < arregloPageVO.length; i++) {

			if (arregloPageVO[i] != null
					&& arregloPageVO[i].getTotalRegistros().intValue() > 0) {
				totalRegistros += arregloPageVO[i].getTotalRegistros()
						.intValue();
				PaginaVO pagina = UtilsDaliVO.getPaginaVO(arregloPageVO[i]);
				pagina.extraerSublist(pagina.getRegistros());
				listaPaginasVO.add(pagina);
			}

		}
		if (totalRegistros == 0) {
			throw new BusinessException(
					"No se encontraron registros con los datos ingresados");
		}
		PaginaVO[] arregloPaginas = new PaginaVO[listaPaginasVO.size()];
		arregloPaginas = (PaginaVO[]) listaPaginasVO.toArray(arregloPaginas);

		return arregloPaginas;
	}

	/**
	 * Construye una lista de agentesPK a partir del agente recibido y una lista
	 * de cuentas
	 * 
	 * @param agenteFirmado
	 * @param listaCuentasARecuperar
	 * @return List
	 */
	private List obtenerAgentesPK(AgenteVO agenteFirmado,
			List listaCuentasARecuperar) {

		logger.info("Entrando a MercadoCapitalesServiceImpl.obtenerAgentesPK()");

		List listaAgentesPK = new ArrayList();
		for (Iterator iter = listaCuentasARecuperar.iterator(); iter.hasNext();) {
			String cuenta = (String) iter.next();
			AgentePK agentePK = UtilsDaliVO.parseAgenteVO2AgentePK(agenteFirmado);
			agentePK.setCuenta(cuenta);
			listaAgentesPK.add(agentePK);
		}
		return listaAgentesPK;

	}

	/**
	 * Aplica las reglas de negocio para la seleccion de tipos de operacion
	 * 
	 * @param tipoOperacionSeleccionadas
	 * @return String[]
	 */
	private String[] recuperaTiposOperacion(String[] tipoOperacionSeleccionadas) {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl.recuperaTiposOperacion()");

		String[] tipoOperacion2 = { Constantes.TIPO_OPERACION_S,
				Constantes.TIPO_OPERACION_O, Constantes.TIPO_OPERACION_T,
				Constantes.TIPO_OPERACION_A, Constantes.TIPO_OPERACION_D,
				Constantes.TIPO_OPERACION_R };

		if (tipoOperacionSeleccionadas != null
				&& tipoOperacionSeleccionadas.length > 0) {

			// Se retiran los elementos nulos o vacios del arreglo original
			String[] tipoOperacionParser = parseArray(tipoOperacionSeleccionadas);
			if (tipoOperacionParser != null && tipoOperacionParser.length > 0) {
				List listaTipoOperacion = Arrays.asList(tipoOperacionParser);
				List listaTipo = new ArrayList();

				for (Iterator iter = listaTipoOperacion.iterator(); iter
						.hasNext();) {
					String element = (String) iter.next();
					if (element.equals(Constantes.TIPO_OPERACION_V)) {
						listaTipo.add(Constantes.TIPO_OPERACION_S);
						listaTipo.add(Constantes.TIPO_OPERACION_O);
					} else if (element.equals(Constantes.TIPO_OPERACION_T)) {
						listaTipo.add(element);
						listaTipo.add(Constantes.TIPO_OPERACION_A);
					} else {
						listaTipo.add(element);
					}
				}
				String[] tipoOperacion = new String[listaTipo.size()];
				tipoOperacion = (String[]) listaTipo.toArray(tipoOperacion);
				tipoOperacion2 = tipoOperacion;
			}
		}

		return tipoOperacion2;

	}

	/**
	 * Si boolean es true, convierte una lista de EmisionVO en una lista de
	 * EmisionPK Si boolean es false, convierte una lista de Object[] en una
	 * lista de EmisionVO
	 * 
	 * @param listaEmisiones
	 * @param pk
	 * @return List
	 * @throws BusinessException
	 */
	private List parseObject2EmisionesVO(List listaEmisiones, boolean pk)
			throws BusinessException {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl.parseObject2EmisionesVO()");

		try {
			Assert.notNull(listaEmisiones,
					"La lista recibida por el metodo privado esta NULL");
			logger.debug("tamanio de la lista principal en metodo privado "
					+ listaEmisiones.size());
		} catch (IllegalArgumentException e) {
			throw new BusinessException(e.getMessage());
		}

		List listaReturn = new ArrayList();

		for (Iterator iter = listaEmisiones.iterator(); iter.hasNext();) {

			if (pk) {
				EmisionVO emisionVO = (EmisionVO) iter.next();
				EmisionPK emisionPK = UtilsDaliVO
						.parseEmisionVO2EmisionPK(emisionVO);
				if (emisionPK != null) {
					listaReturn.add(emisionPK);
				}
			} else {
				Object[] elemento = (Object[]) iter.next();
				EmisionVO emisionVO = new EmisionVO();

				emisionVO.setIdTipoValor(elemento[1] == null ? UtilsDaliVO.BLANK
						: elemento[1].toString());
				emisionVO.setEmisora(elemento[2] == null ? UtilsDaliVO.BLANK
						: elemento[2].toString());
				emisionVO.setSerie(elemento[3] == null ? UtilsDaliVO.BLANK
						: elemento[3].toString());
				emisionVO.setCupon(elemento[4] == null ? UtilsDaliVO.BLANK
						: elemento[4].toString());
				emisionVO
						.setSaldoDisponible(elemento[5] == null ? UtilsDaliVO.BIG_DECIMAL_ZERO
								: (BigDecimal) elemento[5]);
				emisionVO
						.setSaldoInicialDia(elemento[6] == null ? UtilsDaliVO.BIG_DECIMAL_ZERO
								: (BigDecimal) elemento[6]);
				listaReturn.add(emisionVO);
			}
		}
		logger.debug("Se devuelve una lista con [" + listaReturn.size() + "]");
		return listaReturn;
	}

	/*
	 * NOTAS DE DESARROLLO
	 * 
	 * Estructura del Object[]
	 * 
	 * 0 t.idInstRecep, 1 t.folioInstRecep, 2 t.cuentaRecep,
	 * 
	 * 3 t.traspasoPK.idInst, 4 t.traspasoPK.folioInst, 5 t.traspasoPK.cuenta,
	 * 
	 * 6 t.cantidadOperada, 7 t.folioControl, 8 t.claveReporto, 9 t.fechaHora,
	 * 10 t.origen, 11 t.aplicacion, 12 t.origenAplicacion,
	 * 
	 * 13 t.emision.emisionPk.tv, 14 t.emision.emisionPk.emisora, 15
	 * t.emision.emisionPk.serie, 16 t.emision.emisionPk.cupon,
	 * 
	 * 17 idInstAgenteFirmado, 18 folioInstAgenteFirmado, 19
	 * cuentaAgenteFirmado,
	 * 
	 * 20 t.institucion.nombreCorto, 21 t.institucionRecep.nombreCorto, 22
	 * t.precioPorTitulo
	 */
	/**
	 * Convierte una lista de Object[] en una lista de Registros
	 * 
	 * @param listaRegistros
	 * @return Map
	 * @throws ParseException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private Map parseObject2EstadoCuentaVO(List listaRegistros,
			TotalesEstadoCuentaVO totales) throws BusinessException {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl.parseObject2EstadoCuentaVO()");

		try {
			Assert.notNull(listaRegistros,
					"La lista recibida por el metodo privado esta NULL");
			logger.debug("tamanio de la lista principal en metodo privado "
					+ listaRegistros.size());
		} catch (IllegalArgumentException e) {
			// Se captura la IllegalArgumentException y se constuye la
			// BusinessException
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}

		Map mapaReturn = new HashMap();
		BigInteger totalEntradas = UtilsDaliVO.BIG_INTEGER_ZERO;
		BigInteger totalSalidas = UtilsDaliVO.BIG_INTEGER_ZERO;
		BigInteger totalOperaciones = UtilsDaliVO.BIG_INTEGER_ZERO;
		List listaReturn = new ArrayList();
		String cuentaAgenteFirmado = null;
		for (Iterator iter = listaRegistros.iterator(); iter.hasNext();) {

			Object[] registro = (Object[]) iter.next();

			logger.debug("El tipo de objeto obtenido es : ["
					+ ReflectionToStringBuilder.toString(registro) + "]");

			// Se construye y llena el objeto EstadoCuenta
			EstadoCuentaRegistroVO estadoCuenta = new EstadoCuentaRegistroVO();
			EstadoCuentaRegistroVO estadoCuentaCruce = new EstadoCuentaRegistroVO();

			// seteo de datos de la contraparte
			AgenteVO contraparte = new AgenteVO(
					registro[0] == null ? UtilsDaliVO.BLANK : registro[0]
							.toString().trim(),
					registro[1] == null ? UtilsDaliVO.BLANK : registro[1]
							.toString().trim(),
					registro[2] == null ? UtilsDaliVO.BLANK : registro[2]
							.toString().trim());
			contraparte.setNombreCorto(registro[21] == null ? UtilsDaliVO.BLANK
					: registro[21].toString());
			estadoCuenta.setContraparte(contraparte);

			// seteo de datos del agente
			AgenteVO agente = new AgenteVO(registro[3] == null ? UtilsDaliVO.BLANK
					: registro[3].toString().trim(),
					registro[4] == null ? UtilsDaliVO.BLANK : registro[4]
							.toString().trim(),
					registro[5] == null ? UtilsDaliVO.BLANK : registro[5]
							.toString().trim());
			agente.setNombreCorto(registro[20] == null ? UtilsDaliVO.BLANK
					: registro[20].toString());
			estadoCuenta.setAgente(agente);

			cuentaAgenteFirmado = registro[19] == null ? UtilsDaliVO.BLANK
					: registro[19].toString().trim();
			// seteo de datos del agente firmado
			AgenteVO agenteFirmado = new AgenteVO(
					registro[17] == null ? UtilsDaliVO.BLANK : registro[17]
							.toString().trim(),
					registro[18] == null ? UtilsDaliVO.BLANK : registro[18]
							.toString().trim(), cuentaAgenteFirmado);
			estadoCuenta.setAgenteFirmado(agenteFirmado);

			// seteo de emision
			EmisionVO emisionVO = new EmisionVO();
			emisionVO.setIdTipoValor(registro[13] == null ? UtilsDaliVO.BLANK
					: registro[13].toString().trim());
			emisionVO.setEmisora(registro[14] == null ? UtilsDaliVO.BLANK
					: registro[14].toString().trim());
			emisionVO.setSerie(registro[15] == null ? UtilsDaliVO.BLANK
					: registro[15].toString().trim());
			emisionVO.setCupon(registro[16] == null ? UtilsDaliVO.BLANK
					: registro[16].toString().trim());
			estadoCuenta.setEmisionVO(emisionVO);

			// Para el caso de los registros de depositos retiros
			Boolean esDepositoRetiro = null;
			if (registro[22] != null
					&& StringUtils.isNotBlank(registro[22].toString())
					&& ((BigDecimal) registro[22])
							.compareTo(UtilsDaliVO.BIG_DECIMAL_ZERO) < 0) {
				esDepositoRetiro = Boolean.FALSE;
				if ((registro[12] != null
						&& registro[12].toString().trim().equalsIgnoreCase(
								Constantes.DEP_RET) && registro[8] != null && registro[8]
						.toString().trim().equalsIgnoreCase(
								Constantes.TIPO_OPERACION_D))) {
					esDepositoRetiro = Boolean.TRUE;
				}
			}

			estadoCuenta
					.setPrecioPorTitulo(registro[22] == null
							|| StringUtils.isBlank(registro[22].toString())
							|| (((BigDecimal) registro[22])
									.compareTo(UtilsDaliVO.BIG_DECIMAL_ZERO) < 0) ? UtilsDaliVO.BIG_DECIMAL_ZERO
							: (BigDecimal) registro[22]);

			if (estadoCuenta.esContraparte() && estadoCuenta.esTraspasante()
					&& esDepositoRetiro == null) {
				estadoCuentaCruce = estadoCuenta.clona(); // Se clona el
				// estado cuenta
				agenteFirmado.setNombreCorto(agente.getNombreCorto()); // agente.getNombreCorto()
				// ==
				// contraparte.getNombreCorto()
				estadoCuenta
						.setEntradas((registro[6] == null || StringUtils
								.isBlank(registro[6].toString())) ? UtilsDaliVO.BIG_INTEGER_ZERO
								: ((BigDecimal) registro[6]).toBigInteger());
				estadoCuenta
						.setFolio((StringUtils.isBlank(registro[7].toString()) || (new BigInteger(
								registro[7].toString().trim())
								.compareTo(UtilsDaliVO.BIG_INTEGER_ZERO)) < 0) ? UtilsDaliVO.BIG_INTEGER_ZERO
								: new BigInteger(registro[7].toString().trim()));
				estadoCuenta.setMovimiento(Constantes.ENTRADA);
				totalEntradas = totalEntradas.add(estadoCuenta.getEntradas());

				estadoCuentaCruce.setAgenteFirmado(agenteFirmado); // agente.getNombreCorto()
				// ==
				// contraparte.getNombreCorto()
				estadoCuentaCruce
						.setSalidas((registro[6] == null || StringUtils
								.isBlank(registro[6].toString())) ? UtilsDaliVO.BIG_INTEGER_ZERO
								: ((BigDecimal) registro[6]).toBigInteger());
				estadoCuentaCruce
						.setFolio((StringUtils.isBlank(registro[7].toString()) || (new BigInteger(
								registro[7].toString().trim())
								.compareTo(UtilsDaliVO.BIG_INTEGER_ZERO)) < 0) ? UtilsDaliVO.BIG_INTEGER_ZERO
								: new BigInteger(registro[7].toString().trim()));
				estadoCuentaCruce.setMovimiento(Constantes.SALIDA);
				totalSalidas = totalSalidas.add(estadoCuentaCruce.getSalidas());
			} else if ((estadoCuenta.esContraparte() && esDepositoRetiro == null)
					|| (esDepositoRetiro != null && esDepositoRetiro
							.booleanValue())) {
				agenteFirmado.setNombreCorto(contraparte.getNombreCorto());
				estadoCuenta
						.setEntradas((registro[6] == null || StringUtils
								.isBlank(registro[6].toString())) ? UtilsDaliVO.BIG_INTEGER_ZERO
								: ((BigDecimal) registro[6]).toBigInteger());
				estadoCuenta
						.setFolio((StringUtils.isBlank(registro[7].toString()) || (new BigInteger(
								registro[7].toString().trim())
								.compareTo(UtilsDaliVO.BIG_INTEGER_ZERO)) < 0) ? UtilsDaliVO.BIG_INTEGER_ZERO
								: new BigInteger(registro[7].toString().trim()));
				estadoCuenta.setMovimiento(Constantes.ENTRADA);
				totalEntradas = totalEntradas.add(estadoCuenta.getEntradas());
				AgenteVO agenteVOTemp = estadoCuenta.getAgente();
				estadoCuenta.setAgente(estadoCuenta.getContraparte());
				estadoCuenta.setContraparte(agenteVOTemp);
			} else {
				agenteFirmado.setNombreCorto(agente.getNombreCorto());
				estadoCuenta
						.setSalidas((registro[6] == null || StringUtils
								.isBlank(registro[6].toString())) ? UtilsDaliVO.BIG_INTEGER_ZERO
								: ((BigDecimal) registro[6]).toBigInteger());
				estadoCuenta
						.setFolio((StringUtils.isBlank(registro[7].toString()) || (new BigInteger(
								registro[7].toString().trim())
								.compareTo(UtilsDaliVO.BIG_INTEGER_ZERO)) < 0) ? UtilsDaliVO.BIG_INTEGER_ZERO
								: new BigInteger(registro[7].toString().trim()));
				estadoCuenta.setMovimiento(Constantes.SALIDA);
				totalSalidas = totalSalidas.add(estadoCuenta.getSalidas());
			}
			totalOperaciones = totalOperaciones.add(UtilsDaliVO.BIG_INTEGER_UNO);
			if (estadoCuenta.esContraparte() && estadoCuenta.esTraspasante()
					&& esDepositoRetiro == null) {
				totalOperaciones = totalOperaciones
						.add(UtilsDaliVO.BIG_INTEGER_UNO);
				estadoCuentaCruce
						.setTipoOperacion(registro[8] == null ? UtilsDaliVO.BLANK
								: registro[8].toString().trim());
				estadoCuentaCruce.setHora(registro[9] == null ? UtilsDaliVO.BLANK
						: registro[9].toString().substring(10, 16)); // Se
				// hace
				// un
				// substring
				// para
				// obtener
				// solo
				// la
				// hora
				estadoCuentaCruce
						.setOrigen(registro[10] == null ? UtilsDaliVO.BLANK
								: registro[10].toString().trim());
				estadoCuentaCruce
						.setAplicacion(registro[11] == null ? UtilsDaliVO.BLANK
								: registro[11].toString().trim());
				estadoCuentaCruce
						.setDescripcion(registro[12] == null ? UtilsDaliVO.BLANK
								: registro[12].toString().trim());
				listaReturn.add(estadoCuentaCruce);
			}

			estadoCuenta.setAgenteFirmado(agenteFirmado);

			estadoCuenta.setTipoOperacion(registro[8] == null ? UtilsDaliVO.BLANK
					: registro[8].toString());
			estadoCuenta.setHora(registro[9] == null ? UtilsDaliVO.BLANK
					: registro[9].toString().substring(10, 16));// Se hace un
			// substring
			// para obtener
			// solo la hora
			estadoCuenta.setOrigen(registro[10] == null ? UtilsDaliVO.BLANK
					: registro[10].toString());
			estadoCuenta.setAplicacion(registro[11] == null ? UtilsDaliVO.BLANK
					: registro[11].toString());
			estadoCuenta.setDescripcion(registro[12] == null ? UtilsDaliVO.BLANK
					: registro[12].toString());
			listaReturn.add(estadoCuenta);
		}

		totales.setTotalEntradas(totalEntradas);
		totales.setTotalSalidas(totalSalidas);
		totales.setTotalOperaciones(totalOperaciones);
		mapaReturn.put(Constantes.TOTALES, totales);
		mapaReturn.put(Constantes.REGISTROS, listaReturn);
		mapaReturn.put(Constantes.CUENTA_AGENTE_FIRMADO, cuentaAgenteFirmado);
		return mapaReturn;
	}

	/**
	 * Construye un objeto GetEstadocuentaMercadoVO a partir del
	 * GetEstadocuentaMercadoParams recibido
	 * 
	 * @param params
	 * @return GetEstadocuentaMercadoVO
	 */
	private GetEstadocuentaMercadoVO parseGetEstadocuentaMercadoParams2GetEstadocuentaMercadoVO(
			GetEstadocuentaMercadoParams params) {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl."
						+ "parseGetEstadocuentaMercadoParams2GetEstadocuentaMercadoVO()");

		GetEstadocuentaMercadoVO paramsPersistence = null;

		if (params != null) {
			paramsPersistence = new GetEstadocuentaMercadoVO();
			paramsPersistence.setAgenteFirmado(UtilsDaliVO
					.parseAgenteVO2AgentePK(params.getAgenteFirmado()));
			if (StringUtils.isNotBlank(params.getAplicacion())) {
				paramsPersistence.setAplicacion(params.getAplicacion().trim());
			}
			paramsPersistence.setClaveValor(UtilsDaliVO
					.parseEmisionVO2EmisionPK(params.getClaveValor()));
			paramsPersistence.setFechaOperacion(params.getFechaOperacion());
			paramsPersistence.setFechaAyer(utilService
					.getLaborableDay(modificaFecha(params.getFechaOperacion(),
							Calendar.DATE, -1), -1));
			Date fechaOperacionInicioDia = params.getFechaOperacion();
			Date fechaOperacionFinDia = params.getFechaOperacion();
			Date[] fechas = DateUtil.preparaIntervaloFechas(
					fechaOperacionInicioDia, fechaOperacionFinDia);
			paramsPersistence.setFechaOperacionInicioDia(fechas[0]);
			paramsPersistence.setFechaOperacionFinDia(fechas[1]);
			if (StringUtils.isNotBlank(params.getOrigen())) {
				paramsPersistence.setOrigen(params.getOrigen().trim());
			}
			paramsPersistence
					.setPagina(UtilsDaliVO.getPageVO(params.getPaginaVO()));
			paramsPersistence.setTipoOperacion(this
					.recuperaTiposOperacion(params.getTipoOperacion()));
		}

		return paramsPersistence;
	}

	/**
	 * Devuelve un string con la fecha recibida en formato dd/MM/yy
	 * 
	 * @param fecha
	 * @return String
	 */
	private String formatoFechaConcertacion(String fecha) {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl.formatoFechaConcertacion()");

		String fechaConc = null;

		fechaConc = fecha.substring(0, 2);
		fechaConc = fechaConc + "/";
		fechaConc = fechaConc + fecha.substring(2, 4);
		fechaConc = fechaConc + "/";
		fechaConc = fechaConc + fecha.substring(4, 6);

		return fechaConc;
	}

	/**
	 * @param agenteFirmado
	 * @return Un arreglo con objetos tipo ConfirmaTraspasoVO
	 */
	public ConfirmaTraspasoVO[] getConfirmacionTraspaso(AgenteVO agenteFirmado) {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl.getConfirmacionTraspaso()");
		/*
		 * La implementacion de este servicio esta en
		 * MercadoDineroServiceImpl.getListaConfirmacionApertura y
		 * MercadoDineroServiceImpl.confirmaAperturaSistemaDinero
		 */
		return null;
	}

	/**
	 * @see com.indeval.portaldali.middleware.services.mercadocapitales.MercadoCapitalesService#capturaTraspasoContraPago(com.indeval.portaldali.middleware.services.mercadocapitales.GetCapturaTraspasoContraPagoParams)
	 */
	public Integer capturaTraspasoContraPago(
			GetCapturaTraspasoContraPagoParams params) throws BusinessException {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl.capturaTraspasoContraPago()");

		return Integer.valueOf(capturaTraspasoVsTlp(params,
				Constantes.AREA_TRABAJO_DVP, "C").toString());
	}

	/**
	 * @see com.indeval.portaldali.middleware.services.mercadocapitales.MercadoCapitalesService#businessRulesCapturaTraspasoContraPago(com.indeval.portaldali.middleware.services.mercadocapitales.GetCapturaTraspasoContraPagoParams)
	 */
	public String businessRulesCapturaTraspasoContraPago(
			GetCapturaTraspasoContraPagoParams params) throws BusinessException {

		logger.info("Entrando al metodo businessRulesCapturaTraspasoContraPago");

		//Realiza la validacion de control de cuentas de emision
		//Mayo/2018 - Pablo Balderas
		validadorCuentasEmision.validarCuentasEmisionMercadoDinero(params.getTraspasante(), params.getReceptor(), params.getEmision());
		
		return businessRulesCapturaTraspasoVsTlp(params,
				Constantes.AREA_TRABAJO_DVP, "C");
	}

	/**
	 * @see com.indeval.portaldali.middleware.services.mercadocapitales.MercadoCapitalesService#capturaTraspasoTLPFechaValor(com.indeval.portaldali.middleware.services.mercadocapitales.GetCapturaTraspasoContraPagoParams)
	 */
	public Integer capturaTraspasoTLPFechaValor(
			GetCapturaTraspasoContraPagoParams params) throws BusinessException {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl.capturaTraspasoTLPFechaValor()");

		return Integer.valueOf(capturaTraspasoVsTlp(params,
				Constantes.AREA_TRABAJO_TLP, "P").toString());
	}

	/**
	 * 
	 * @param params
	 * @param areaTrabajo
	 * @param sociedadSerie
	 * @return Integer
	 * @throws BusinessException
	 */
	private BigInteger capturaTraspasoVsTlp(
			GetCapturaTraspasoContraPagoParams params, String areaTrabajo,
			String sociedadSerie) throws BusinessException {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl.capturaTraspasoVsTlp()");

		/* Se valida el objeto de parametros */
		utilService.validaDTONoNulo(params, "");

		/* Se valida el agente traspasante */
		utilService.validaDTONoNulo(params.getTraspasante(),
				" agente traspasante ");
		params.getTraspasante().tieneClaveValida();
		if (StringUtils.isBlank(params.getTraspasante().getCuenta())) {
			throw new BusinessException(
					" Error: La cuenta del traspasante es nula. ");
		}
		utilService.isAgenteRegistrado(params.getTraspasante());

		/* Se valida el agente receptor */
		utilService.validaDTONoNulo(params.getReceptor(), " agente receptor ");
		params.getReceptor().tieneClaveValida();
		if (StringUtils.isBlank(params.getReceptor().getCuenta())) {
			throw new BusinessException(
					" Error: La cuenta del receptor es nula. ");
		}
		utilService.isAgenteRegistrado(params.getReceptor());

		/* Se valida que no se utilice la misma cuenta para los cruces */
		if (params.getTraspasante().equals(params.getReceptor())) {
			throw new BusinessException(
					"No se pueden realizar traspasos a la misma cuenta");
		}

		// Se valida que el traspaso no sea ninguna de las cuentas de la CCV que
		// inician con 25
		if (params.getReceptor().getId().trim().equals(Constantes.CUENTA_25)) {
			throw new BusinessException(
					"No es posible realizar traspasos a cuentas de la CCV");
		}

		if (params.getEmision() == null) {
			throw new BusinessException(" Error: La emisi\u00f3n es nula. ");
		}
		params.getEmision().tienePKValida();

		if (areaTrabajo.equals(Constantes.AREA_TRABAJO_DVP)) {

			if (params.getCantidad() == null) {
				throw new BusinessException(
						" Error: La cantidad operada es nula. ");
			}
			if (params.getPrecioTitulo() == null) {
				throw new BusinessException(
						" Error: El campo precio por titulo es nulo. ");
			}
		}

		if (StringUtils.isBlank(params.getCveReporto())) {
			throw new BusinessException(
					" Error: El campo clave reporto es nulo. ");
		}

		if (params.getFechaLiquidacion() == null) {
			throw new BusinessException(
					"Error: La fecha de liquidaci\u00f3n es nula o vacia.");
		}

		if (diaInhabilDaliDao.esInhabil(params.getFechaLiquidacion())) {
			throw new BusinessException(
					"Error: La fecha de liquidaci\u00f3n es inhabil.");
		}

		if (params.getLiquidacion() == null) {
			throw new BusinessException(
					" Error: El campo liquidacion es nulo. ");
		}

		if (StringUtils.isBlank(params.getUsuario())) {
			throw new BusinessException(" Error: El campo usuario es nulo. ");
		}
		if (StringUtils.isBlank(params.getNombreUsuario())) {
			throw new BusinessException(
					" Error: El campo nombre usuario es nulo. ");
		}

		return utilService.getFolio(SECUENCIA_FOLIO_CONTROL);
	}

	/**
	 * Recorre el arreglo de String recibido y construye otro que contiene solo
	 * los elementos distintos de NULL y BLANK.
	 * 
	 * @param array
	 * @return String[]
	 */
	private String[] parseArray(String array[]) {

		logger.info("Entrando a MercadoCapitalesServiceImpl.parseArray()");

		List preResult = new ArrayList();
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null && StringUtils.isNotBlank(array[i])) {
				preResult.add(array[i].trim());
			}
		}
		if (preResult.size() == 0) {
			return null;
		}
		String[] result = new String[preResult.size()];
		for (int j = 0; j < preResult.size(); j++) {
			result[j] = preResult.get(j).toString();
		}
		return result;
	}

	/**
	 * @see com.indeval.portaldali.middleware.services.mercadocapitales.MercadoCapitalesService#getCuentasEstadoCuentaUnico(com.indeval.portaldali.middleware.services.mercadocapitales.EstadoCuentaUnicoParams)
	 */
	public String[] getCuentasEstadoCuentaUnico(EstadoCuentaUnicoParams params)
			throws BusinessException {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl.getCuentasEstadoCuentaUnico()");

		/* Se validan los parametros */
		utilService.validaDTONoNulo(params, "");
		params.validaParams();

		// Determinamos la cuenta del agente con la cual se realizara la
		// consulta de emisiones y de detalles, haciendo una consulta sobre la
		// tabla de agentes para recuperar las cuentas asociadas al agente
		// firmado y que existen en la tabla historica..edoctatr
		// Recuperamos todas las cuentas asociadas al agente(id,folio,?)
		String[] cuentasAgente = null;
		// TODO
		// agenteDao.getCuentasAgenteEstadoCuentaUnico(
		// UtilsDaliVO.parseAgenteVO2AgentePK(params.getAgenteFirmado()),
		// params.getFechaMovimiento(), UtilsDaliVO
		// .parseEmisionVO2EmisionPK(params.getEmisionVO()));
		if (cuentasAgente == null || cuentasAgente.length == 0) {
			throw new BusinessDataException(errorResolver.getMessage("J9999"),
					"J9999");
		}

		return cuentasAgente;
	}

	/**
	 * @param emisionesAgente
	 * @return EmisionVO[]
	 */
	private EmisionVO[] arrayObject2ArrayEmisionVO(List emisionesAgente) {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl.arrayObject2ArrayEmisionVO()");

		EmisionVO[] emisionVOs = new EmisionVO[emisionesAgente.size()];
		if (emisionesAgente != null) {
			for (int j = 0; j < emisionesAgente.size(); j++) {
				Object[] emision = (Object[]) emisionesAgente.get(j);
				EmisionVO emisionVO = new EmisionVO();
				emisionVO.setIdTipoValor((String) emision[0]);
				emisionVO.setEmisora((String) emision[1]);
				emisionVO.setSerie((String) emision[2]);
				emisionVO.setCupon((String) emision[3]);
				emisionVOs[j] = emisionVO;
			}
		}
		return emisionVOs;
	}

	/**
	 * @see com.indeval.portaldali.middleware.services.mercadocapitales.MercadoCapitalesService
	 *      #getListaConfirmacionTraspasoContraPago(
	 *      com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      java.lang.String)
	 */
	public TraspasosContraPagosVO[] getListaConfirmacionTraspasoContraPago(
			AgenteVO agenteFirmado, String estado) throws BusinessException {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl.getListaConfirmacionTraspasoContraPago()");

		/* Se valida el agente */
		utilService.validaDTONoNulo(agenteFirmado, " AgentePersistence ");
		agenteFirmado.tieneClaveValida();

		/* Obtiene fecha de liquidacion (actual) */
		Date fechaLiquidacion = dateUtilsDao.getDateFechaDB();

		/* Ejecuta el DAO para obtener los registros a confirmar */
		List resultadoQuery = null;
		// TODO
		// mdintranDao
		// .getListaConfirmacionTraspasoContraPago(agenteFirmado.getId(),
		// agenteFirmado.getFolio(), fechaLiquidacion, estado);

		if (resultadoQuery == null || resultadoQuery.isEmpty()) {
			throw new BusinessException(errorResolver.getMessage("J9998"));
		}

		return obtieneTraspasosContraPagosVO(resultadoQuery, agenteFirmado);
	}

	/**
	 * Obtiene un arreglo de objetos TraspasosContraPagosVO a partir de la lista
	 * de objetos Mdintran
	 * 
	 * @param listaMdintran
	 * @return Arreglo de objetos del tipo TraspasosContraPagosVO
	 * @throws BusinessException
	 */
	private TraspasosContraPagosVO[] obtieneTraspasosContraPagosVO(
			List listaMdintran, AgenteVO agenteFirmado)
			throws BusinessException {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl.obtieneTraspasosContraPagosVO()");

		if (listaMdintran == null || listaMdintran.isEmpty()) {
			throw new BusinessException(errorResolver.getMessage("J9998"));
		}

		TraspasosContraPagosVO[] resultado = new TraspasosContraPagosVO[listaMdintran
				.size()];

		for (int i = 0; i < listaMdintran.size(); i++) {

			// Se construye el objeto TraspasosContraPagosVO
			TraspasosContraPagosVO traspVsPagVO = new TraspasosContraPagosVO();
			// Se recupera el registro de Mdintran
			Mdintran registroMdintran = (Mdintran) listaMdintran.get(i);
			// Se construye la EmisionVO
			EmisionVO emisionVO = new EmisionVO();
			emisionVO.setIdTipoValor(registroMdintran.getTv());
			emisionVO.setEmisora(registroMdintran.getEmisora());
			emisionVO.setSerie(registroMdintran.getSerie());
			emisionVO.setCupon(registroMdintran.getCupon());
			// Se settea el objeto TraspasosContraPagosVO
			traspVsPagVO.setTraspasante(UtilsDaliVO.getAgenteVO(registroMdintran
					.getAgente()));
			traspVsPagVO.setReceptor(UtilsDaliVO.getAgenteVO(registroMdintran
					.getAgenteRecep()));
			traspVsPagVO.setEmision(emisionVO);
			traspVsPagVO.setCantidadOperada(registroMdintran
					.getCantidadOperada());
			traspVsPagVO.setFechaAquisicion(registroMdintran.getFechaHora());
			traspVsPagVO.setFechaLiquidacion(registroMdintran
					.getFechaLiquidacion());
			traspVsPagVO.setPrecioAdquisicion(registroMdintran
					.getPrecioPorTitulo());
			traspVsPagVO.setFolio(registroMdintran.getFolioControl());
			traspVsPagVO.setSociedadSerie(registroMdintran.getSociedadSerie());

			AgenteVO agenteVO = UtilsDaliVO.getAgenteVO(registroMdintran
					.getAgenteRecep());

			if (agenteFirmado.equals(agenteVO)
					&& registroMdintran.getSociedadSerie().trim().equals("C")) {
				traspVsPagVO.setConfirmacion(true);
			}
			resultado[i] = traspVsPagVO;
		}

		return resultado;

	}

	/**
	 * @see com.indeval.portaldali.middleware.services.mercadocapitales
	 *      .MercadoCapitalesService#confirmaTraspasoContraPago(
	 *      com.indeval.portaldali.middleware.services.mercadocapitales
	 *      .ConfirmaTraspasoContraPagoParams[])
	 */
	public boolean confirmaTraspasoContraPago(
			ConfirmaTraspasoContraPagoParams[] params) throws BusinessException {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl.confirmaTraspasoContraPago()");

		/* Valida el objeto de entrada */
		if (params == null) {
			throw new BusinessException("Falta el objeto de entrada - params");
		}
		if (params.length == 0) {
			throw new BusinessException(
					"El objeto de entrada esta vacio - params");
		}
		for (int i = 0; i < params.length; i++) {
			if (params[i] == null) {
				throw new BusinessException(
						"Falta el elemento del objeto de entrada - params[" + i
								+ "]");
			}
			if (params[i].getTraspasante() == null) {
				throw new BusinessException("Falta el traspasante");
			}
			params[i].getTraspasante().tieneClaveValida();
			if (StringUtils.isBlank(params[i].getTraspasante().getCuenta())) {
				throw new BusinessException(
						"Error: El agente tiene NULL o VACIO el atributo CUENTA");
			}
			if (params[i].getFechaLiquidacion() == null) {
				throw new BusinessException("Falta la fecha de liquidacion");
			}
			if (params[i].getFolio() == null) {
				throw new BusinessException("Falta el folio de control");
			}
		}

		int faseActual = 3;
		// TODO
		// obtenerFaseService.obtenerFase();

		if (faseActual != 3 && faseActual != 5 && faseActual != 7) {
			throw new BusinessException(
					"No es posible efectuar la operacion con la fase ["
							+ faseActual + "] activa ");
		}

		/*
		 * Ejecuta el DAO - itera el arreglo para confirmar varios folios a la
		 * vez
		 */
		// TODO
		// ParamsConfirmaTraspasoVsPagoVO[] arregloParamsDao = this
		// .paramsToParamsDao(params);
		int registrosActualizados = 0;
		// TODO
		// mdintranDao
		// .confirmaTraspasoContraPago(arregloParamsDao);

		/*
		 * Obtiene el valor de salida correpondiente a la actualizacion de
		 * registros
		 */
		if (registrosActualizados > 0) {
			return true;
		}

		return false;

	}

	/**
	 * Obtiene el arreglo de objetos necesarios para ejecutar el DAO
	 * 
	 * @param arregloParams
	 * @return Arreglo de objetos del tipo ParamsConfirmaTraspasoVsPagoVO
	 * @throws BusinessException
	 */
	private ParamsConfirmaTraspasoVsPagoVO[] paramsToParamsDao(
			ConfirmaTraspasoContraPagoParams[] arregloParams)
			throws BusinessException {

		logger.info("Entrando a MercadoCapitalesServiceImpl.paramsToParamsDao()");

		if (arregloParams == null || arregloParams.length == 0) {
			throw new BusinessException("No existen elementos que convertir");
		}
		ParamsConfirmaTraspasoVsPagoVO[] resultado = new ParamsConfirmaTraspasoVsPagoVO[arregloParams.length];
		for (int i = 0; i < arregloParams.length; i++) {
			ConfirmaTraspasoContraPagoParams params = (ConfirmaTraspasoContraPagoParams) arregloParams[i];
			ParamsConfirmaTraspasoVsPagoVO paramsDao = new ParamsConfirmaTraspasoVsPagoVO();
			paramsDao.setFechaLiquidacion(params.getFechaLiquidacion());
			paramsDao.setFolio(params.getFolio());
			paramsDao.setTraspasante(UtilsDaliVO.getAgentePK(params
					.getTraspasante()));
			paramsDao.setUsuario(params.getUsuario());
			resultado[i] = paramsDao;
		}
		return resultado;
	}

	/**
	 * Modifica los dias indicados en diasXModificar.
	 * 
	 * @param fecha
	 *            La fecha a modificar
	 * @param campoXModificar
	 *            puede ser dia mes o ao
	 * @param cantidadAModificar
	 *            es la cantidad que se va a agregar o restar al campo por
	 *            modificar
	 * @return Date
	 */
	private static Date modificaFecha(Date fecha, int campoXModificar,
			int cantidadAModificar) {

		logger.info("Entrando a MercadoCapitalesServiceImpl.modificaFecha()");

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(campoXModificar, cantidadAModificar);
		return calendar.getTime();
	}

	/**
	 * @see com.indeval.portaldali.middleware.services.mercadocapitales.MercadoCapitalesService#archivoConciliacion(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      com.indeval.portaldali.middleware.services.modelo.EmisionVO,
	 *      PaginaVO)
	 */
	public PaginaVO archivoConciliacion(AgenteVO agenteFirmado,
			EmisionVO emision, PaginaVO pagina) throws BusinessException {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl.archivoConciliacion()");

		/* Valida el id y folio del agente firmado */
		utilService.validaDTONoNulo(agenteFirmado, " AgentePersistence ");
		agenteFirmado.tieneClaveValida();

		pagina = UtilsDaliVO.getPaginaNotBlank(pagina);

		/* Ejecuta el DAO */
		List resultado = null;
		// TODO
		// valorDao.getArchivoConciliacion(UtilsDaliVO .getAgentePK(agenteFirmado),
		// UtilsDaliVO.getEmisionPK(emision));

		if (resultado == null || resultado.isEmpty()) {
			throw new BusinessException(errorResolver.getMessage("J9999"));
		}

		/*
		 * Se obtiene una PaginaVO con la sublista correspondiente a los valores
		 * de paginacion
		 */
		pagina = pagina.extraerSublist(resultado);

		/* Pasa los registros al VO expuesto en la capa de presentacion */
		pagina
				.setRegistros(obtieneArchivoConciliacionVO(pagina
						.getRegistros()));
		pagina.setTotalRegistros(new Integer(resultado.size()));

		/* Regresa la pagina con la nueva lista de registros */
		return pagina;

	}

	/**
	 * Obtiene la lista de VO para la capa de presentacion
	 * 
	 * @param lista
	 * @return List
	 * @throws BusinessException
	 */
	private List obtieneArchivoConciliacionVO(List lista)
			throws BusinessException {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl.obtieneArchivoConciliacionVO()");

		if (lista == null) {
			throw new BusinessException(
					"No existe la lista a convertir - ArchivoConciliacionResultVO");
		}

		/* Se determina la fecha de fin de dia de ayer */
		Assert.notNull(dateUtilsDao, "La inyecci\u00f3n del DAO fall\u00f3");
		Date fechaActual = dateUtilsDao.getDateFechaDB();
		logger.debug("La fecha actual es : [" + fechaActual + "]");
		utilService.validaDTONoNulo(fechaActual,
				"La fecha actual no puede ser nula");

		Date fechaFinAyer = DateUtil.addDays(dateUtilsDao
				.getFechaHoraFinDia(fechaActual), -1);

		List resultado = new ArrayList();

		for (Iterator iter = lista.iterator(); iter.hasNext();) {
			ArchivoConciliacionResultVO element = (ArchivoConciliacionResultVO) iter
					.next();
			ArchivoConciliacionVO archivoConciliacionVO = new ArchivoConciliacionVO();
			AgenteVO agenteVO = new AgenteVO();
			EmisionVO emisionVO = new EmisionVO();
			agenteVO.setId(element.getIdInst());
			agenteVO.setFolio(element.getFolioInst());
			agenteVO.setCuenta(element.getCuenta());
			agenteVO.setTipo(element.getTipoCta());
			agenteVO.setTenencia(element.getTenencia());
			emisionVO.setIdTipoValor(element.getTv());
			emisionVO.setEmisora(element.getEmisora());
			emisionVO.setSerie(element.getSerie());
			emisionVO.setCupon(element.getCupon());
			archivoConciliacionVO.setAgenteFirmado(agenteVO);
			archivoConciliacionVO.setEmision(emisionVO);
			archivoConciliacionVO
					.setValorNominal(element.getValorNominal() != null ? element
							.getValorNominal()
							: UtilsDaliVO.BIG_DECIMAL_ZERO);
			archivoConciliacionVO
					.setUltimoHecho(element.getUltimoHecho() != null ? element
							.getUltimoHecho() : UtilsDaliVO.BIG_DECIMAL_ZERO);
			archivoConciliacionVO
					.setSaldoInicial(element.getSaldoInicial() != null ? element
							.getSaldoInicial()
							: UtilsDaliVO.BIG_DECIMAL_ZERO);
			archivoConciliacionVO.setSaldoDisponible(element
					.getSaldoDisponible() != null ? element
					.getSaldoDisponible() : UtilsDaliVO.BIG_DECIMAL_ZERO);
			archivoConciliacionVO
					.setSaldoTesoreria(element.getSaldoTesoreria() != null ? element
							.getSaldoTesoreria()
							: UtilsDaliVO.BIG_DECIMAL_ZERO);
			archivoConciliacionVO.setFechaHora(element.getFechaHora());
			archivoConciliacionVO.setTieneDetalle(archivoConciliacionVO
					.getFechaHora() != null ? !fechaFinAyer
					.after(archivoConciliacionVO.getFechaHora()) : true);

			if (archivoConciliacionVO.getAgenteFirmado().getTenencia()
					.equalsIgnoreCase(Constantes.TENENCIA_TESORERIA)) {
				archivoConciliacionVO.setSaldoTesoreria(archivoConciliacionVO
						.getSaldoDisponible());
				archivoConciliacionVO
						.setSaldoDisponible(UtilsDaliVO.BIG_DECIMAL_ZERO);
				archivoConciliacionVO.setUltimoHecho(UtilsDaliVO.BIG_DECIMAL_ZERO);
				archivoConciliacionVO.setValorNominal(UtilsDaliVO.BIG_DECIMAL_ZERO);
			} else {
				archivoConciliacionVO
						.setSaldoTesoreria(UtilsDaliVO.BIG_DECIMAL_ZERO);
			}
			archivoConciliacionVO.setTotal(archivoConciliacionVO
					.getSaldoDisponible().add(
							archivoConciliacionVO.getSaldoTesoreria()));
			if (archivoConciliacionVO.getSaldoInicial().compareTo(
					UtilsDaliVO.BIG_DECIMAL_ZERO) != 0
					|| archivoConciliacionVO.getSaldoTesoreria().compareTo(
							UtilsDaliVO.BIG_DECIMAL_ZERO) != 0
					|| archivoConciliacionVO.getSaldoDisponible().compareTo(
							UtilsDaliVO.BIG_DECIMAL_ZERO) != 0) {
				resultado.add(archivoConciliacionVO);
			}

		}
		return resultado;
	}

	/**
	 * @see com.indeval.portaldali.middleware.services.mercadocapitales.MercadoCapitalesService#archivoConciliacionMovimientos(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      com.indeval.portaldali.middleware.services.modelo.PaginaVO)
	 */
	public PaginaVO archivoConciliacionMovimientos(AgenteVO agente,
			PaginaVO pagina) throws BusinessException {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl.archivoConciliacionMovimientos()");

		/* Valida el id y folio del agente firmado */
		utilService.validaDTONoNulo(agente, " AgentePersistence ");
		agente.tieneClaveValida();
		pagina = UtilsDaliVO.getPaginaNotBlank(pagina);

		/* Obtiene la fecha actual */
		Date fechaConsulta = dateUtilsDao.getDateFechaDB();

		/* Ejecuta el DAO */
		List resultadoTraspaso = null;
		// TODO
		// traspasoDao.getArchivoConciliacionMovimientos(
		// UtilsDaliVO.getAgentePK(agente), fechaConsulta);

		if (resultadoTraspaso == null) {
			resultadoTraspaso = new ArrayList();
		}

		List resultadoDepositoRetiro = null;
		// TODO
		// depositoRetiroDao
		// .getArchivoConciliacionMovimientos(UtilsDaliVO.getAgentePK(agente),
		// fechaConsulta);

		if (resultadoDepositoRetiro != null
				&& !resultadoDepositoRetiro.isEmpty()) {
			resultadoTraspaso.addAll(resultadoDepositoRetiro);
		}

		if (resultadoTraspaso.isEmpty()) {
			throw new BusinessException(
					"No existen movimientos para el criterio de busqueda seleccionado");
		}

		/*
		 * Se obtiene una PaginaVO con la sublista correspondiente a los valores
		 * de paginacion
		 */
		pagina = pagina.extraerSublist(resultadoTraspaso);

		/* Pasa los registros al VO expuesto en la capa de presentacion */
		pagina.setRegistros(obtieneArchivoConciliacionMovimientosVO(pagina
				.getRegistros()));
		pagina.setTotalRegistros(new Integer(Integer.toString(resultadoTraspaso
				.size())));

		/* Regresa la pagina con la nueva lista de registros */
		return pagina;

	}

	/**
	 * @see com.indeval.portaldali.middleware.services.mercadocapitales.MercadoCapitalesService#archivoConciliacionDetalle(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      EmisionVO,
	 *      com.indeval.portaldali.middleware.services.modelo.PaginaVO)
	 */
	public PaginaVO archivoConciliacionDetalle(AgenteVO agente,
			EmisionVO emision, PaginaVO pagina) throws BusinessException {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl.archivoConciliacionDetalle()");

		/* Valida el id y folio del agente firmado */
		/*
		 * if (agente == null) { throw new BusinessException("Falta el agente
		 * firmado"); } else { agente.tieneClaveValida(); if
		 * (StringUtils.isBlank(agente.getCuenta())) { throw new
		 * BusinessException("Falta la cuenta del agente firmado"); } } if
		 * (emision == null) { throw new BusinessException("Falta la emision"); }
		 * else { emision.tienePKValida(); }
		 */

		utilService.validaDTONoNulo(agente, " AgentePersistence ");
		utilService.validaDTONoNulo(agente.getCuenta(), " Cuenta ");
		utilService.validaDTONoNulo(emision, " EmisionPersistence ");
		agente.tieneClaveValida();
		emision.tienePKValida();

		pagina = UtilsDaliVO.getPaginaNotBlank(pagina);

		/* Obtiene la fecha actual */
		Date fechaConsulta = dateUtilsDao.getDateFechaDB();

		/* Ejecuta el DAO */
		List resultadoTraspaso = null;
		// TODO
		// traspasoDao.getArchivoConciliacionDetalle(
		// UtilsDaliVO.getAgentePK(agente), UtilsDaliVO.getEmisionPK(emision),
		// fechaConsulta);

		List resultadoDepositoRetiro = null;
		// TODO
		// depositoRetiroDao
		// .getArchivoConciliacionMovimientos(UtilsDaliVO.getAgentePK(agente),
		// fechaConsulta);

		resultadoTraspaso.addAll(resultadoDepositoRetiro);

		/*
		 * Se obtiene una PaginaVO con la sublista correspondiente a los valores
		 * de paginacion
		 */
		pagina = pagina.extraerSublist(resultadoTraspaso);

		/* Pasa los registros al VO expuesto en la capa de presentacion */
		pagina.setRegistros(obtieneArchivoConciliacionMovimientosVO(pagina
				.getRegistros()));
		pagina.setTotalRegistros(new Integer(Integer.toString(resultadoTraspaso
				.size())));

		/* Regresa la pagina con la nueva lista de registros */
		return pagina;

	}

	/**
	 * Obtiene la lista de VO para la capa de presentacion
	 * 
	 * @param lista
	 * @return List
	 * @throws BusinessException
	 */
	private List obtieneArchivoConciliacionMovimientosVO(List lista)
			throws BusinessException {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl.obtieneArchivoConciliacionMovimientosVO()");

		if (lista == null) {
			throw new BusinessException(
					"No existe la lista a convertir - ArchivoConciliacionResultVO");
		}
		List resultado = new ArrayList();
		for (Iterator iter = lista.iterator(); iter.hasNext();) {
			ArchivoConciliacionMovimientosResultVO element = (ArchivoConciliacionMovimientosResultVO) iter
					.next();
			ArchivoConciliacionMovimientosVO movimientosVO = new ArchivoConciliacionMovimientosVO();
			AgenteVO traspasante = new AgenteVO();
			AgenteVO receptor = new AgenteVO();
			EmisionVO emisionVO = new EmisionVO();
			traspasante.setId(element.getIdInst());
			traspasante.setFolio(element.getFolioInst());
			traspasante.setCuenta(element.getCuenta());
			traspasante.setTipo(element.getTipoCta());
			traspasante.setNombreCorto(element.getNombreCorto());
			receptor.setId(element.getIdInstRecep());
			receptor.setFolio(element.getFolioInstRecep());
			receptor.setCuenta(element.getCuentaRecep());
			receptor.setTipo(element.getTipoCtaRecep());
			receptor.setNombreCorto(element.getNombreCortoRecep());
			emisionVO.setIdTipoValor(element.getTv());
			emisionVO.setEmisora(element.getEmisora());
			emisionVO.setSerie(element.getSerie());
			emisionVO.setCupon(element.getCupon());
			movimientosVO.setTraspasante(traspasante);
			movimientosVO.setReceptor(receptor);
			movimientosVO.setEmision(emisionVO);
			movimientosVO.setCantidadOperada(element.getCantidadOperada());
			movimientosVO.setOrigen(element.getOrigen());
			movimientosVO.setTipoOperacion(element.getTipoOperacion());
			movimientosVO.setLlaveFolio(element.getLlaveFolio());
			movimientosVO.setFolioDescripcion(element.getFolioDescripcion());
			movimientosVO.setFolioControl(element.getFolioControl());
			resultado.add(movimientosVO);
		}
		return resultado;
	}

	/**
	 * Determina el saldo calculado para el servicio de estado de cuenta
	 * &uacute;nico, para esto, se basa en la siguiente f&oacute;rmula:
	 * 
	 * <b>saldo_disponible + entradas - salidas </b>
	 * 
	 * @param historicosEstadoCuentaTraspasoVO
	 * @param saldoDisponible
	 * @return BigDecimal
	 */
	private BigDecimal calculaSaldoEstadoCuentaUnico(
			List historicosEstadoCuentaTraspasoVO, BigDecimal saldoDisponible) {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl.calculaSaldoEstadoCuentaUnico()");

		BigDecimal entradas = UtilsDaliVO.BIG_DECIMAL_ZERO;
		BigDecimal salidas = UtilsDaliVO.BIG_DECIMAL_ZERO;
		if (historicosEstadoCuentaTraspasoVO != null) {
			for (int i = 0; i < historicosEstadoCuentaTraspasoVO.size(); i++) {
				HistoricoEstadoCuentaTraspasoVO cuentaTraspasoVO = (HistoricoEstadoCuentaTraspasoVO) historicosEstadoCuentaTraspasoVO
						.get(i);
				if (cuentaTraspasoVO != null) {
					if (cuentaTraspasoVO.getEntradas() != null
							&& cuentaTraspasoVO.getEntradas().intValue() > 0) {
						entradas = entradas.add(cuentaTraspasoVO.getEntradas());
					}
					if (cuentaTraspasoVO.getSalidas() != null
							&& cuentaTraspasoVO.getSalidas().intValue() > 0) {
						salidas = salidas.add(cuentaTraspasoVO.getSalidas());
					}
				}
			}
		}
		if (saldoDisponible == null) {
			saldoDisponible = UtilsDaliVO.BIG_DECIMAL_ZERO;
		}
		return saldoDisponible.add(entradas.subtract(salidas));
	}

	/**
	 * @see com.indeval.portaldali.middleware.services.mercadocapitales.MercadoCapitalesService#getOperacionDiaCapitalesVO(com.indeval.portaldali.middleware.services.mercadocapitales.OperacionDiaCapitalesParams)
	 */
	public OperacionDiaCapitalesVO getOperacionDiaCapitalesVO(
			OperacionDiaCapitalesParams params) throws BusinessException {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl.getOperacionDiaCapitalesVO()");

		if (params == null) {
			throw new BusinessException(
					"El objeto de par\u00e1metros no puede ser nulo");
		}
		try {
			Assert.notNull(params.getAgenteVO(),
					"El agente firmado no puede ser nulo");
			Assert.isTrue(StringUtils.isNotBlank(params.getAgenteVO().getId()),
					"El id de la instituci\u00f3n no puede ser nulo");
			Assert.isTrue(StringUtils.isNotBlank(params.getAgenteVO()
					.getFolio()),
					"El folio de la instituci\u00f3n no puede ser nulo");
		} catch (IllegalArgumentException iae) {
			throw new BusinessException(
					"Alguno de los par\u00e1metos requeridos es nulo");
		}

		String[] tvs = null;
		PageVO pagina = null;
		OperacionDiaCapitalesVO operacionDiaCapitalesVO = null;

		// recuperamos el total de registros para la consulta, si no hay
		// registros que coincidan con los
		// criterios de busqueda en la bd, lanzamos una BusinessException
		Long totalRegistros = null;
		// TODO
		// (Long) traspasoDao .generaPaginaOperacionesDiaCapitales(UtilsDaliVO
		// .parseAgenteVO2AgentePK(params.getAgenteVO()), UtilsDaliVO
		// .parseEmisionVO2EmisionPK(params.getEmisionVO()),
		// params.getFechaInicial(), params.getFechaFinal(),
		// params.getTipoOperacion(), params.getManeraExtraccion(),
		// params.getOrigen(), params.getAplicacion(), UtilsDaliVO.getPageVO(params
		// .getPaginaVO()), "COUNT");

		if (totalRegistros == null || totalRegistros.intValue() == 0) {
			throw new BusinessException(
					"No se encontraron registros para los criterios de b\u00fasqueda seleccionados");
		}

		// Si el parametro tv no es nulo, no habra paginacion por tv, por lo que
		// asignamos al arreglo de
		// tv's destinamos a la navegacion el tv parametro
		if (params.getEmisionVO() != null
				&& StringUtils.isNotBlank(params.getEmisionVO()
						.getIdTipoValor())) {
			tvs = new String[1];
			tvs[0] = params.getEmisionVO().getIdTipoValor();
		} else {
			tvs = null;
			// TODO
			// (String[]) traspasoDao.generaPaginaOperacionesDiaCapitales(
			// UtilsDaliVO.parseAgenteVO2AgentePK(params.getAgenteVO()),
			// UtilsDaliVO.parseEmisionVO2EmisionPK(params.getEmisionVO()),
			// params.getFechaInicial(), params.getFechaFinal(), params
			// .getTipoOperacion(), params.getManeraExtraccion(),
			// params.getOrigen(), params.getAplicacion(), UtilsDaliVO
			// .getPageVO(params.getPaginaVO()), "DISTINCT");

		}

		operacionDiaCapitalesVO = new OperacionDiaCapitalesVO();

		BigDecimal sumaTitulos = null;
		// TODO
		// (BigDecimal) traspasoDao .generaPaginaOperacionesDiaCapitales(UtilsDaliVO
		// .parseAgenteVO2AgentePK(params.getAgenteVO()), UtilsDaliVO
		// .parseEmisionVO2EmisionPK(params.getEmisionVO()),
		// params.getFechaInicial(), params.getFechaFinal(),
		// params.getTipoOperacion(), params.getManeraExtraccion(),
		// params.getOrigen(), params.getAplicacion(), UtilsDaliVO.getPageVO(params
		// .getPaginaVO()), "SUMA");

		// si estamos en el caso de export, se setea el numero de registros por
		// pagina en cero, para
		// recuperar todos
		if (params.isExport()) {
			params.getPaginaVO().setRegistrosXPag(new Integer(0));
		}
		pagina = null;
		// TODO
		// (PageVO) traspasoDao.getOperacionesDiaCapitales(UtilsDaliVO
		// .parseAgenteVO2AgentePK(params.getAgenteVO()), UtilsDaliVO
		// .parseEmisionVO2EmisionPK(params.getEmisionVO()), params
		// .getFechaInicial(), params.getFechaFinal(), params
		// .getTipoOperacion(), params.getManeraExtraccion(), params
		// .getOrigen(), params.getAplicacion(), UtilsDaliVO.getPageVO(params
		// .getPaginaVO()), "SELECT");

		List listaDetalleServicios = new ArrayList();
		for (int i = 0; i < pagina.getRegistros().size(); i++) {
			OperacionDiaDetalleCapitalesVO item = this
					.objectToOperacionDiaDetalle((Object[]) pagina
							.getRegistros().get(i));
			listaDetalleServicios.add(item);
		}
		pagina.setRegistros(listaDetalleServicios);
		operacionDiaCapitalesVO.setTv(tvs);
		operacionDiaCapitalesVO.setPaginaVO(UtilsDaliVO.getPaginaVO(pagina));
		operacionDiaCapitalesVO.setTotalTitulos(sumaTitulos);
		operacionDiaCapitalesVO.setTotalMovimientos(totalRegistros);
		return operacionDiaCapitalesVO;
	}

	/**
	 * @param item
	 * @return OperacionDiaDetalleCapitalesVO
	 */
	private OperacionDiaDetalleCapitalesVO objectToOperacionDiaDetalle(
			Object[] item) {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl.objectToOperacionDiaDetalle()");

		OperacionDiaDetalleCapitalesVO result = null;
		if (item != null) {
			result = new OperacionDiaDetalleCapitalesVO();
			result.setFolioControl((Integer) item[0]);

			AgenteVO comprador = new AgenteVO();
			comprador.setId((String) item[1]);
			comprador.setFolio((String) item[2]);
			comprador.setCuenta((String) item[3]);

			AgenteVO vendedor = new AgenteVO();
			vendedor.setId((String) item[4]);
			vendedor.setFolio((String) item[5]);
			vendedor.setCuenta((String) item[6]);
			vendedor.setNombreCorto((String) item[18]);

			EmisionVO emisionVO = new EmisionVO();
			emisionVO.setIdTipoValor((String) item[7]);
			emisionVO.setEmisora((String) item[8]);
			emisionVO.setSerie((String) item[9]);
			emisionVO.setCupon((String) item[10]);

			result.setFechaHora((Timestamp) item[11]);
			result.setCantidadOperada((BigDecimal) item[12]);
			result.setBajaLogica((String) item[13]);
			result.setOrigen((String) item[14]);
			result.setClaveReporto((String) item[15]);
			result.setFolioTransmision((Integer) item[16]);
			comprador.setNombreCorto((String) item[17]);

			result.setLlaveFolio((String) item[19]);
			result.setFolioDescripcion((String) item[20]);

			result.setEmisionVO(emisionVO);
			result.setComprador(comprador);
			result.setVendedor(vendedor);
		}
		return result;
	}

	/**
	 * M&eacute;todo encargado de calcular el importe de las operaciones del
	 * d&iacute;a.
	 * 
	 * @param udiAct
	 * @param cantidad
	 * @param precio
	 * @param divisa
	 * @return BigDecimal
	 */
	private BigDecimal calculaImporteOperacionDiaMC(BigDecimal udiAct,
			BigDecimal cantidad, BigDecimal precio, String divisa) {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl.calculaImporteOperacionDiaMC()");

		BigDecimal importe = UtilsDaliVO.BIG_DECIMAL_ZERO;

		if (divisa != null) {
			if (divisa.equalsIgnoreCase(Constantes.UDI)) {
				importe = precio.multiply(cantidad).multiply(udiAct);
			} else if (divisa.equalsIgnoreCase(Constantes.DOLAR)) {
				importe = precio.multiply(cantidad).multiply(udiAct);
			} else {
				importe = precio.multiply(cantidad);
			}
		}
		return importe;
	}

	/**
	 * @see com.indeval.portaldali.middleware.services.mercadocapitales.MercadoCapitalesService#businessRulesCapturaTraspaso(com.indeval.portaldali.middleware.services.mercadocapitales.GetCapturaTraspasoParams)
	 */
	public String businessRulesCapturaTraspaso(GetCapturaTraspasoParams params)throws BusinessException {

		logger.info("Entrando a MercadoCapitalesServiceImpl.businessRulesCapturaTraspaso()");

		/* Se valida el objeto de parametros */
		utilService.validaDTONoNulo(params, "");

		/* Se valida el traspasante */
		utilService.validaDTONoNulo(params.getTraspasante(),
				" agente traspasante ");
		params.getTraspasante().tieneClaveValida();
		if (StringUtils.isBlank(params.getTraspasante().getCuenta())) {
			throw new BusinessException(errorResolver.getMessage("J0008",
					new Object[] { " del traspasante " }));
		}
		utilService.isAgenteRegistrado(params.getTraspasante());

		/* Se valida el receptor */
		utilService.validaDTONoNulo(params.getReceptor(), " agente receptor ");
		params.getReceptor().tieneClaveValida();
		if (StringUtils.isBlank(params.getReceptor().getCuenta())) {
			throw new BusinessException(errorResolver.getMessage("J0008",
					new Object[] { " del receptor " }));
		}
		utilService.isAgenteRegistrado(params.getReceptor());

		/* Se valida la emision */
		utilService.validaDTONoNulo(params.getEmision(), " emision ");
		params.getEmision().tienePKValida();
		
		
		
		if(!utilService.isCuponValidoParaEmision(params.getEmision())) {
			throw new BusinessException("La emisi\u00F3n no es v\u00E1lida para los datos capturados");
		}

		/* Se verifica que la emision sea de capitales */
		validaInstrumento(params.getEmision().getIdTipoValor());

		
		validaCuenta(params.getTraspasante().getId()+params.getTraspasante().getFolio(), params.getTraspasante().getCuenta(), "traspasante", cuentasInvalidas);
		//validaCuenta(params.getReceptor().getCuenta(), "cuenta receptora", cuentasInvalidas);
		
		/* Se valida el costo promedio actualizado */
		if(StringUtils.isNotBlank(params.getTipoOperacion()) && params.getTipoOperacion().equals("M")) {
			if (params.getCostoPromedio() == null) {
				throw new BusinessException(errorResolver.getMessage("J0211"));

			}

			if (params.getPrecioAdquisicion().compareTo(
					UtilsDaliVO.BIG_DECIMAL_ZERO) <= 0) {
				throw new BusinessException(errorResolver.getMessage("J0068"));
				
			}
			
			if (params.getCostoPromedio().compareTo(
					UtilsDaliVO.BIG_DECIMAL_ZERO) <= 0) {
				throw new BusinessException(errorResolver.getMessage("J0212"));
				
			}
			
			utilService.validaNumber(params.getCostoPromedio(),
					patternResolver.getMessage("precioxtitulo"));
			
		}
		
		//Se realiza la validación del RFC/CURP
		//Pablo Balderas 23/06/2014
		if(StringUtils.isNotBlank(params.getRfcCurp())) {
			if(!validacionService.validarRfcCurp(params.getRfcCurp())) {
				throw new BusinessException(errorResolver.getMessage("errorRfcCurp"));
			}
		}
		
		
		// Esta validacion solo es para los traspasos de sociedades de inversion
		if (StringUtils.isNotBlank(params.getTipoOperacion())
				&& params.getTipoOperacion().equals(Constantes.ACTIVO)) {
			int faseActual = 3;
			String cuentaTraspasante = params.getTraspasante().getCuenta().trim();
			String cuentaReceptor = params.getReceptor().getCuenta().trim();
			if(cuentaTraspasante.equalsIgnoreCase(cuentaReceptor)) {
				throw new BusinessException("La cuenta traspasante y receptora deben ser diferentes");
			}
			if(faseActual == 1) {
				if (!cuentaTraspasante.startsWith(Constantes.CUENTA_53)
						&& !cuentaTraspasante.startsWith(Constantes.CUENTA_97)
						&& !cuentaTraspasante.startsWith(Constantes.CUENTA_98)) {
					throw new BusinessException("La cuenta traspasante debe ser de Activos de Sociedades de Inversi\u00f3n");
				}
			}
			else if (faseActual == 8) {
				if (!cuentaReceptor.startsWith(Constantes.CUENTA_53)
						&& !cuentaReceptor.startsWith(Constantes.CUENTA_97)
						&& !cuentaReceptor.startsWith(Constantes.CUENTA_98)) {
					throw new BusinessException("La cuenta receptora debe ser de Activos de Sociedades de Inversi\u00f3n");
				}
			}
			else {
				throw new BusinessException("Los traspasos con Sociedades de Inversi\u00f3n solo pueden realizarse en fase 1 y 8");
			}
		}
		else {
			//Si no es traspaso de sociedades de inversion, se valida que no sea la misma cuenta
			if (params.getTraspasante().getClave().equals(params.getReceptor().getClave()) &&
					params.getTraspasante().getCuenta().equals(params.getReceptor().getCuenta())) {
				throw new BusinessException("No se pueden realizar traspasos a la misma cuenta");
			}
			/*
			 * Se agrega validación para que permita operar a la CCV con la cuenta 0103
			 * como traspasante o receptor.
			 * Pablo Balderas 29/Octubre/2015 
			 */
			validarCCV(params);
		}

		TraspasosDineroCompradorParams paramsBS = this.paramsService2ParamsBusiness(params);

		if (StringUtils.isNotBlank(params.getTipoOperacion()) && params.getTipoOperacion().equals(Constantes.ACTIVO)) {
			// Si el traspaso es de Ctas. Activos S. I. el origen de la
			// aplicacion es FONDEO
			paramsBS.setOrigenAplicac(Constantes.ORIGEN_APLICACION_ACTIVO_FONDEO);
		}
		else {
			// Si el traspaso es de tipo TLP's el origen de la aplicacion es
			// MERSECMC
			paramsBS.setOrigenAplicac(Constantes.ORIGEN_APLICACION_TLP_MERSECMC);
		}
		businessRulesMercadoCapitalesService.traspasosDineroComprador(paramsBS);

		//Realiza la validacion de control de cuentas de emision
		//Mayo/2018 - Pablo Balderas
		validadorCuentasEmision.validarCuentasEmisionMercadoDinero(params.getTraspasante(), params.getReceptor(), params.getEmision());
		
		return utilService.getFolio(SECUENCIA_FOLIO_CONTROL).toString();
	}

	/**
	 * Metodo que valida que si el traspasante o el receptor es la CCV(25001) solo opere con la cuenta de terceros 0103
	 */
	private void validarCCV(GetCapturaTraspasoParams params) throws BusinessException {
		//Obtiene el id - folio del traspasante y del receptor
		String traspasante = params.getTraspasante().getId().trim() + params.getTraspasante().getFolio().trim();
		String receptor = params.getReceptor().getId().trim() + params.getReceptor().getFolio().trim();;
		//Obtiene la cuenta traspasante y receptora
		String cuentaTraspasante = params.getTraspasante().getCuenta().trim();
		String cuentaReceptor = params.getReceptor().getCuenta().trim();
		if(MercadosConstantes.OPERACION_T.equals(params.getCveReporto()) && 
				(params.getTipoOperacion() == null || 
				!MercadosConstantes.TIPO_OPERACION_LOCAL_M.equals(params.getTipoOperacion()))) {
			String mensajeError = 
				"Error: La CCV \u00FAnicamente puede operar con la cuenta " + MercadosConstantes.CUENTA_CCV_TERCEROS_0103;
			//Si el traspasante y el receptor son iguales y es la CCV, marcamos error
			if(traspasante.equals(receptor) && MercadosConstantes.ID_FOLIO_CCV.equals(traspasante)) {
				throw new BusinessException(mensajeError);
			}
			//Si el traspasante es la CCV, valida que sea la cuenta de terceros 0103
			else if(MercadosConstantes.ID_FOLIO_CCV.equals(traspasante)) {
				if(!MercadosConstantes.CUENTA_CCV_TERCEROS_0103.equals(cuentaTraspasante)) {
					throw new BusinessException(mensajeError);
				}
			}
			//Si el receptor es la CCV, valida que sea la cuenta de terceros 0103
			else if(MercadosConstantes.ID_FOLIO_CCV.equals(receptor)) {
				if(!MercadosConstantes.CUENTA_CCV_TERCEROS_0103.equals(cuentaReceptor)) {
					throw new BusinessException(mensajeError);
				}
			}
		}
		else if (Constantes.CUENTA_25.equals(params.getReceptor().getId().trim())) {
			throw new BusinessException("No es posible realizar traspasos a cuentas de la CCV");
		}
	}
	
	
	private void validaCuenta(String idFolio, String cuenta, String tipoCuenta, List<String> listaCuentasInvalidas) throws BusinessException {
		for( String cuentaInvalida : listaCuentasInvalidas ) {
			if(cuenta.matches(cuentaInvalida)) {
				throw new BusinessException("Cuenta " + tipoCuenta + " " + idFolio + cuentaInvalida + " Invalida");
			}
		}
	}

	/**
	 * Valida el instrumento de la emision recibida y arroja una
	 * BusinessException en caso de que no corresponda al mercado de capitales
	 * 
	 * @param tv
	 * @throws BusinessException
	 */
	private void validaInstrumento(String tv) throws BusinessException {

		logger.info("Entrando a MercadoCapitalesServiceImpl.buscaInstrumento()");

		String mercado = new String("");

		if (StringUtils.isNotBlank(tv)) {
			Instrumento instrumento = instrumentoDaliDao.getInstrumento(tv.trim());
			if (instrumento == null) {
				throw new BusinessException(errorResolver.getMessage("J0072"));
			}
			if (instrumento.getMercado() != null
					&& StringUtils.isNotBlank(instrumento.getMercado()
							.getClave())) {
				mercado = instrumento.getMercado().getClave().trim();
			}
		} else {
			throw new BusinessException(errorResolver.getMessage("J0028",
					new Object[] { " para las emisiones" }));
		}

		logger.debug("El mercado del instrumento es = [" + mercado + "]");
		if (!Constantes.MERCADO_MC.equalsIgnoreCase(mercado)) {
			throw new BusinessException(errorResolver.getMessage("J0074"));
		}

		return;

	}

	/**
	 * Mapea los atributos de un objeto GetCapturaTraspasoParams a un objeto
	 * TraspasosDineroCompradorParams
	 * 
	 * @param params
	 * @return TraspasosDineroCompradorParams
	 */
	private TraspasosDineroCompradorParams paramsService2ParamsBusiness(
			GetCapturaTraspasoParams params) {

		logger
				.info("Entrando a MercadoCapitalesServiceImpl.paramsService2ParamsBusiness()");

		TraspasosDineroCompradorParams paramsBS = new TraspasosDineroCompradorParams();

		paramsBS.setBaja(Constantes.BAJA_LOGICA_F);
		paramsBS.setCant(params.getCantidad());

		// seteo de los atributos del agente traspasante
		if (params.getTraspasante() != null) {
			paramsBS.setId1(params.getTraspasante().getId());
			paramsBS.setFol1(params.getTraspasante().getFolio());
			paramsBS.setCta1(params.getTraspasante().getCuenta());
			paramsBS.setTCta1(Constantes.MERCADO_MC);
		}

		if (params.getReceptor() != null) {
			paramsBS.setId2(params.getReceptor().getId());
			paramsBS.setFol2(params.getReceptor().getFolio());
			paramsBS.setCta2(params.getReceptor().getCuenta());
			paramsBS.setTcta2(Constantes.MERCADO_MC);
		}

		if (params.getEmision() != null) {
			paramsBS.setTv(params.getEmision().getIdTipoValor());
			paramsBS.setEmis(params.getEmision().getEmisora());
			paramsBS.setSerie(params.getEmision().getSerie());
			paramsBS.setCupon(params.getEmision().getCupon());
		}

		paramsBS.setCveRep(params.getCveReporto());
		paramsBS.setDiasPlazo(new BigInteger("0"));
		paramsBS.setDivisa(params.getDivisa());
		paramsBS.setFecha(params.getFecha());
		paramsBS.setFechaLiq(params.getFechaLiquidacion());
		// de donde sale la fecha reporto?, al menos en el SP no se utiliza
		// paramsBS.setFechaReporto(params.get)

		if (params.getFolioControl() != null) {
			paramsBS.setFolCont(new BigInteger(String.valueOf(params
					.getFolioControl())));
		}

		paramsBS.setFolDesc(params.getFolioDescripcion());
		paramsBS.setFolTransm(new BigInteger("0"));

		paramsBS.setLiq(new BigInteger("0"));
		paramsBS.setLlaveFolMd(params.getLlaveFolioMd());
		paramsBS.setMDine("C");
		paramsBS.setMercado(Constantes.MERCADO_MC);
		paramsBS.setNomArea(Constantes.AREA_TRABAJO_MERCAP);
		paramsBS.setNomUs("JV_USER");
		paramsBS.setOrigen(Constantes.ORIGEN_APLICACION_01);
		paramsBS.setPrecioTitulo(new BigDecimal("0"));

		paramsBS.setTasaPremio(new BigDecimal(0));
		paramsBS.setTipoLib("F");
		paramsBS.setUsuario(Constantes.USUARIO_JV);
		paramsBS.setUsuarioReal(Constantes.USUARIO_JV);

		return paramsBS;
	}

	/**
	 * Efectua las validaciones necesarias para una captura de traspaso
	 * contrapago
	 * 
	 * @param params
	 * @param areaTrabajo
	 * @param sociedadSerie
	 * @throws BusinessException
	 * @return String
	 */
	private String businessRulesCapturaTraspasoVsTlp(
			GetCapturaTraspasoContraPagoParams params, String areaTrabajo,
			String sociedadSerie) throws BusinessException {

		logger.info("Entrando a MercadoCapitalesServiceImpl.capturaTraspasoVsTlp()");

		/* Se valida el objeto de parametros */
		utilService.validaDTONoNulo(params, "");

		/* Se valida el agente traspasante */
		utilService.validaDTONoNulo(params.getTraspasante(),
				" agente traspasante ");
		params.getTraspasante().tieneClaveValida();
		if (StringUtils.isBlank(params.getTraspasante().getCuenta())) {
			throw new BusinessException(
					" Error: La cuenta del traspasante es nula. ");
		}
		utilService.isAgenteRegistrado(params.getTraspasante());

		/* Se valida el agente receptor */
		utilService.validaDTONoNulo(params.getReceptor(), " agente receptor ");
		params.getReceptor().tieneClaveValida();
		if (StringUtils.isBlank(params.getReceptor().getCuenta())) {
			throw new BusinessException(
					" Error: La cuenta del receptor es nula. ");
		}
		utilService.isAgenteRegistrado(params.getReceptor());
		
		/* Se verifica que la emision sea de capitales */
		validaInstrumento(params.getEmision().getIdTipoValor());

		validaCuenta(params.getTraspasante().getId()+params.getTraspasante().getFolio(), params.getTraspasante().getCuenta(), "cuenta traspasante", cuentasInvalidas);
		validaCuenta(params.getReceptor().getId()+params.getReceptor().getFolio(), params.getReceptor().getCuenta(), "cuenta receptora", cuentasInvalidas);

		// Se valida que el traspaso no sea ninguna de las cuentas de la CCV que
		// inician con 25
		if (params.getReceptor().getId().trim().equals(Constantes.CUENTA_25)) {
			throw new BusinessException(
					"No es posible realizar traspasos a cuentas de la CCV");
		}

		if (params.getEmision() == null) {
			throw new BusinessException(" Error: La emisi\u00f3n es nula. ");
		}
		params.getEmision().tienePKValida();

		if (areaTrabajo.equals(Constantes.AREA_TRABAJO_DVP)) {

			if (params.getCantidad() == null) {
				throw new BusinessException(
						" Error: La cantidad operada es nula. ");
			}

			utilService.validaNumber(params.getCantidad(), patternResolver
					.getMessage("cantidad"));

			if (params.getPrecioTitulo() == null) {
				throw new BusinessException(
						" Error: El campo precio por titulo es nulo. ");
			}
		}

		if (StringUtils.isBlank(params.getCveReporto())) {
			throw new BusinessException(
					" Error: El campo clave reporto es nulo. ");
		}

		if (params.getFechaLiquidacion() == null) {
			throw new BusinessException(
					"Error: La fecha de liquidaci\u00f3n es nula o vacia.");
		}

		if (diaInhabilDaliDao.esInhabil(params.getFechaLiquidacion())) {
			throw new BusinessException(
					"Error: La fecha de liquidaci\u00f3n es inhabil.");
		}

		if (params.getLiquidacion() == null) {
			throw new BusinessException(
					" Error: El campo liquidacion es nulo. ");
		}

		if (StringUtils.isBlank(params.getUsuario())) {
			throw new BusinessException(" Error: El campo usuario es nulo. ");
		}
		if (StringUtils.isBlank(params.getNombreUsuario())) {
			throw new BusinessException(
					" Error: El campo nombre usuario es nulo. ");
		}
		
		if(!utilService.isCuponValidoParaEmision(params.getEmision())) {
			throw new BusinessException("La emisi\u00F3n no es v\u00E1lida para los datos capturados");
		}
		
		return utilService.getFolio(SECUENCIA_FOLIO_CONTROL).toString();
	}

	/**
	 * Inyeccion del bean errorResolver
	 * 
	 * @param errorResolver
	 */
	public void setErrorResolver(MessageResolver errorResolver) {
		this.errorResolver = errorResolver;
	}

	/**
	 * Inyeccion del bean utilService
	 * 
	 * @param utilService
	 */
	public void setUtilService(UtilServices utilService) {
		this.utilService = utilService;
	}

	/**
	 * Obtiene el bean errorResolver
	 * 
	 * @return MessageResolver
	 */
	public MessageResolver getErrorResolver() {
		return errorResolver;
	}

	/**
	 * Inyeccion del bean BusinessRulesMercadoCapitalesService
	 * 
	 * @param businessRulesMercadoCapitalesService
	 */
	public void setBusinessRulesMercadoCapitalesService(
			BusinessRulesMercadoCapitalesService businessRulesMercadoCapitalesService) {
		this.businessRulesMercadoCapitalesService = businessRulesMercadoCapitalesService;
	}

	/**
	 * @param fasesValidasCapturaTraspasos
	 *            the fasesValidasCapturaTraspasos to set
	 */
	public void setFasesValidasCapturaTraspasos(Map fasesValidasCapturaTraspasos) {
		this.fasesValidasCapturaTraspasos = fasesValidasCapturaTraspasos;
	}

	/**
	 * Inyeccion del bean patternResolver
	 * 
	 * @param patternResolver
	 *            the patternResolver to set
	 */
	public void setPatternResolver(MessageResolver patternResolver) {
		this.patternResolver = patternResolver;
	}

	/**
	 * @param instrumentoDaliDao
	 *            the instrumentoDaliDao to set
	 */
	public void setInstrumentoDao(InstrumentoDaliDao instrumentoDaliDao) {
		this.instrumentoDaliDao = instrumentoDaliDao;
	}

	/**
	 * @param diaInhabilDaliDao
	 *            the diaInhabilDaliDao to set
	 */
	public void setDiaInhabilDao(DiaInhabilDaliDao diaInhabilDaliDao) {
		this.diaInhabilDaliDao = diaInhabilDaliDao;
	}

	/**
	 * @param dateUtilsDao
	 *            the dateUtilsDao to set
	 */
	public void setDateUtilsDao(DateUtilsDao dateUtilsDao) {
		this.dateUtilsDao = dateUtilsDao;
	}

	/**
	 * @param catalogoService
	 *            the catalogoService to set
	 */
	public void setCatalogoService(CatalogoService catalogoService) {
		this.catalogoService = catalogoService;
	}

	public OperacionDiaDetalleCapitalesVO getDetalleOperacionDiaCapitales(
			TraspasoCapitalesVO traspasoCapitalesVO) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	public EstadoCuentaUnicoVO[] getEstadoCuentaUnico(
			EstadoCuentaUnicoParams params) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	public OperacionLiqFuturoTotalVO getOperacionLiqFuturo(AgenteVO agente,
			String tipoFecha, Date fecha, PaginaVO paginaVO)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	public PaginaVO getOperacionPendienteIncumplida(
			GetOperacionPendienteIncumplidaParams params)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see com.indeval.portaldali.middleware.services.mercadocapitales.MercadoCapitalesService#getListaEmisionSociedadesInversion(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      com.indeval.portaldali.middleware.services.modelo.EmisionVO)
	 */
	public List getListaEmisionSociedadesInversion(AgenteVO agente,
			EmisionVO emision) throws BusinessException {
		return estadoCuentaSocInvDAO.getListaEmisiones(agente, emision);
	}

	/**
	 * @see com.indeval.portaldali.middleware.services.mercadocapitales.MercadoCapitalesService#getListaEmisionSociedadesInversionRazonSocial(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      com.indeval.portaldali.middleware.services.modelo.EmisionVO)
	 */
	public List getListaEmisionSociedadesInversionRazonSocial(AgenteVO agente,
			EmisionVO emision) throws BusinessException {
		List listaEmisoras =  estadoCuentaSocInvDAO.getListaEmisionesRazonSocial(agente,
				emision);
		List<EmisoraVO> retorno = new ArrayList<EmisoraVO>();
		for( Iterator it = listaEmisoras.iterator(); it.hasNext() ; ) {
			Object[] array =(Object[])it.next();
			EmisoraVO emisora = new EmisoraVO();
			emisora.setIdEmisora((BigInteger)array[0]);
			emisora.setClavePizarra((String)array[1]);
			emisora.setRazonSocial((String)array[2]);
			retorno.add(emisora);
		}
		
		return retorno;
	}
	
	/**
	 * @see com.indeval.portaldali.middleware.services.mercadocapitales.MercadoCapitalesService#getSaldoEmisionSociedadesInversionRazonSocial(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List getSaldoEmisionSociedadesInversionRazonSocial(AgenteVO agente,
			String emisora,Boolean debeDejarLog) throws BusinessException {
		List listaSaldos = estadoCuentaSocInvDAO.getSaldoEstadoCuentasocInv(agente, emisora);
		List<EmisionSIVO> retorno = new ArrayList<EmisionSIVO>();
		for( Iterator it = listaSaldos.iterator(); it.hasNext() ; ) {
			Object[] array =(Object[])it.next();
			EmisionSIVO emisionVO = new EmisionSIVO();
			emisionVO.setIdTipoValor((String)array[0]);
			emisionVO.setEmisora((String)array[1]);
			emisionVO.setSerie((String)array[2]);
			emisionVO.setCupon((String)array[3]);
			emisionVO.setPosicionDisponible((BigInteger)array[4]);
			emisionVO.setPosicionTesoreria((BigInteger)array[5]);
			emisionVO.setPosicionTotal((BigInteger)array[6]);
			retorno.add(emisionVO);
		}
		
		if( retorno != null && retorno.size() > 0 ) {
			Collections.sort( retorno, new Comparator(){

				public int compare(Object o1, Object o2) {
					EmisionSIVO obj1 = (EmisionSIVO)o1;
					EmisionSIVO obj2 = (EmisionSIVO)o2;
					
					String tv1 = obj1.getIdTipoValor();
					String tv2 = obj2.getIdTipoValor();
					int compTv = tv1.compareTo(tv2);
					if( compTv != 0 ) {
						return compTv;
					} else {
						String emi1 = obj1.getEmisora();
						String emi2 = obj2.getEmisora();
						int compEmi = emi1.compareTo(emi2);
						if( compEmi != 0 ) {
							return compEmi;
						} else {
							String ser1 = obj1.getSerie();
							String ser2 = obj2.getSerie();
							int compSerie = ser1.compareTo(ser2);
							if(compSerie != 0) {
								return compSerie;
							}
							else {
								String cupon1 = obj1.getCupon();
								String cupon2 = obj2.getCupon();
								return cupon1.compareTo(cupon2);
							}
						}
					}
				}
				
			});
			
		}
		
		return retorno;
	}

	/**
	 * @param estadoCuentaSocInvDAO the estadoCuentaSocInvDAO to set
	 */
	public void setEstadoCuentaSocInvDAO(EstadoCuentaSocInvDAO estadoCuentaSocInvDAO) {
		this.estadoCuentaSocInvDAO = estadoCuentaSocInvDAO;
	}


	/**
	 * @param cuentasInvalidas the cuentasInvalidas to set
	 */
	public void setCuentasInvalidas(List<String> cuentasInvalidas) {
		this.cuentasInvalidas = cuentasInvalidas;
	}

	/**
	 * @return the validacionService
	 */
	public ValidacionService getValidacionService() {
		return validacionService;
	}

	/**
	 * @param validacionService the validacionService to set
	 */
	public void setValidacionService(ValidacionService validacionService) {
		this.validacionService = validacionService;
	}

	/**
	 * Método para establecer el atributo validadorCuentasEmision
	 * @param validadorCuentasEmision El valor del atributo validadorCuentasEmision a establecer.
	 */
	public void setValidadorCuentasEmision(ValidadorCuentasEmision validadorCuentasEmision) {
		this.validadorCuentasEmision = validadorCuentasEmision;
	}
	
}
