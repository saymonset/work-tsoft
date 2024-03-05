/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.constantes.MercadosConstantes;
import com.indeval.portaldali.middleware.services.common.ValidacionService;
import com.indeval.portaldali.middleware.services.mercadodinero.util.Constantes;
import com.indeval.portaldali.middleware.services.mercadodinero.util.UtilsVOMercadoDinero;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.AltaMdintranParams;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.BusinessRulesCatalogoService;
import com.indeval.portaldali.middleware.services.modelo.BusinessRulesMercadoDineroService;
import com.indeval.portaldali.middleware.services.modelo.CatalogoService;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.middleware.services.modelo.RegistraTraspasosYCompensaValoresParams;
import com.indeval.portaldali.middleware.services.modelo.util.UtilsVOCatalogo;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.middleware.services.util.UtilsDaliVO;
import com.indeval.portaldali.middleware.services.util.ValidadorCuentasEmision;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;
import com.indeval.portaldali.persistence.dao.common.DiaInhabilDaliDao;
import com.indeval.portaldali.persistence.dao.common.InstrumentoDaliDao;
import com.indeval.portaldali.persistence.model.CuentaNombrada;
import com.indeval.portaldali.persistence.model.Instrumento;
import com.indeval.portaldali.persistence.util.DateUtil;
import com.indeval.portaldali.persistence.vo.AgentePersistence;
import com.indeval.portaldali.persistence.vo.EmisionPK;
import com.indeval.portaldali.persistence.vo.EmisionPersistence;
import com.indeval.portaldali.persistence.vo.InstrumentoVO;

/**
 * Implementacion de los servicios de Mercado de Dinero.
 * 
 */
public class MercadoDineroServiceImpl implements MercadoDineroService, Constantes {

	/** Log de clase. */
	private static final Logger logger = LoggerFactory.getLogger(MercadoDineroServiceImpl.class);

	/** Bean de acceso a datos de DiaInhabil */
	private DiaInhabilDaliDao diaInhabilDaliDao;

	/** Bean de acceso a datos de Instrumento */
	private InstrumentoDaliDao instrumentoDaliDao;

	/** Bean para acceso al properties de mensaje de errores */
	private MessageResolver errorResolver;

	/** Bean de acceso a catalogoService */
	private CatalogoService catalogoService;

	/** Bean para acceso al properties de patrones de numero */
	private MessageResolver patternResolver;


	/** Bean de acceso a DateUtilsDao */
	private DateUtilsDao dateUtilsDao;

	/** Bean de acceso a UtilService */
	private com.indeval.portaldali.middleware.services.util.UtilServices utilService;


	/** Bean de acceso al Map de fases abiertas */
	private Map fasesAbiertas;


	/** Bean de acceso a BusinessRulesCatalogoService */
	private BusinessRulesCatalogoService businessRulesCatalogoService;

	/** Bean de acceso a BusinessRulesMercadoDineroService */
	private BusinessRulesMercadoDineroService businessRulesMercadoDineroService;

	/** Servicio de validaciones */
	private ValidacionService validacionService =  null;
	
	/** Servicio de validacion para control de cuentas de emision */
	private ValidadorCuentasEmision validadorCuentasEmision;
	
	/**
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#administracionGarantias(java.math.BigInteger,
	 *      java.lang.String, java.lang.String)
	 */
	public AdministracionGarantiaVO[] administracionGarantias(BigInteger folio,
			String idInstitucion, String folioInstitucion)
			throws BusinessException {

		logger.info("Entrando a MercadoDineroServiceImpl.administracionGarantias()");

		return null;

	}

	/**
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#capturaConcertacionPrestamos(com.indeval.portaldali.middleware.services.mercadodinero.CapturaConcertacionPrestamosParams)
	 */
	public Integer capturaConcertacionPrestamos(
			CapturaConcertacionPrestamosParams params) throws BusinessException {

		logger.info("Entrando a MercadoDineroServiceImpl.capturaConcertacionPrestamos()");


		return null;

	}

	/**
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#capturaGarantias(com.indeval.portaldali.middleware.services.mercadodinero.CapturaGarantiasParams)
	 */
	public String capturaGarantias(CapturaGarantiasParams params)
			throws BusinessException {

		logger.info("Entrando a MercadoDineroServiceImpl.capturaGarantias()");


		return null;

	}

	/**
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService
	 *      #confirmaAperturaSistemaDinero(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      java.math.BigInteger[], boolean) La Apertura Siafore se da de alta y
	 *      confirma en las fases 8, 10 y 11.
	 */
	public boolean confirmaAperturaSistemaDinero(AgenteVO agenteFirmado,
			BigInteger[] folioTraspaso, boolean aperturaSistema)
			throws BusinessException {

		logger
				.info("Entrando a MercadoDineroServiceImpl.confirmaAperturaSistemaDinero()");


		return false;

	}

	/**
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#setConfirmacionMiscFiscal(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      java.lang.Integer[])
	 */
	public Integer[] setConfirmacionMiscFiscal(AgenteVO agenteFirmado,
			Integer[] foliosTraspasos) throws BusinessException {

		logger
				.info("Entrando a MercadoDineroServiceImpl.setConfirmacionMiscFiscal()");


		return null;

	}

	/**
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#consultaEstatusOperaciones(com.indeval.portaldali.middleware.services.mercadodinero.ConsultaEstatusOperacionesParams)
	 */
	public EstatusOperacionesVO consultaEstatusOperaciones(
			ConsultaEstatusOperacionesParams params) throws BusinessException {

		logger
				.info("Entrando a MercadoDineroServiceImpl.consultaEstatusOperaciones()");

		return null;

	}



	/**
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#cancelaOperacion(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      java.lang.String, java.lang.Boolean)
	 */
	public BigInteger[] cancelaOperacion(AgenteVO agente, String llaveFolio,
			Boolean isAgenteFirmado) throws BusinessException {

		logger.info("Entrando a MercadoDineroServiceImpl.cancelaOperacion()");


		return null;

	}

	/**
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#confirmaOperacion(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      java.lang.String, java.lang.Boolean)
	 */
	public BigInteger[] confirmaOperacion(AgenteVO agenteVO, String llaveFolio,
			Boolean isAgenteFirmado) throws BusinessException {

		logger.info("Entrando a MercadoDineroServiceImpl.confirmaOperacion()");


		return null;

	}

	/**
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService
	 *      #consultaMovimientosMiscFiscal(
	 *      com.indeval.portaldali.middleware.services.mercadodinero.ConsultaMovimientosMiscFiscalParams)
	 */
	public PaginaVO consultaMovimientosMiscFiscal(
			ConsultaMovimientosMiscFiscalParams params)
			throws BusinessException {

		logger
				.info("Entrando a MercadoDineroServiceImpl.consultaMovimientosMiscFiscal()");


		return null;

	}

	/**
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#consultaPosicionValoresGarantias(com.indeval.portaldali.middleware.services.modelo.EmisionVO,
	 *      java.lang.String,
	 *      com.indeval.portaldali.middleware.services.modelo.AgenteVO)
	 */
	public PosicionValorGarantiaVO[] consultaPosicionValoresGarantias(
			EmisionVO emision, String idTipoPapel, AgenteVO agenteVO)
			throws BusinessException {

		logger
				.info("Entrando a MercadoDineroServiceImpl.consultaPosicionValoresGarantias()");


		return null;

	}

	/**
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#consultaPosicionValores(com.indeval.portaldali.middleware.services.mercadodinero.PosicionValoresParams)
	 */
	public PaginaVO consultaPosicionValores(PosicionValoresParams params)
			throws BusinessException {

		logger
				.info("Entrando a MercadoDineroServiceImpl.consultaPosicionValores()");


		return null;

	}



	/**
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#consultaPrestamos(com.indeval.portaldali.middleware.services.mercadodinero.ConsultaPrestamosParams)
	 */
	public PrestamoVO[] consultaPrestamos(ConsultaPrestamosParams params)
			throws BusinessException {

		logger.info("Entrando a MercadoDineroServiceImpl.consultaPrestamos()");


		return null;

	}

	/**
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#detalleParcialidades(java.lang.String)
	 */
	public ParcialidadVO detalleParcialidades(String folio)
			throws BusinessException {

		logger.info("Entrando a MercadoDineroServiceImpl.detalleParcialidades()");

	

		return null;

	}

	/**
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#getEstadoCuenta(com.indeval.portaldali.middleware.services.mercadodinero.GetEstadoCuentaParams)
	 */
	public EstadoCuentaTotalMDVO getEstadoCuenta(GetEstadoCuentaParams params)
			throws BusinessException {

		logger.info("Entrando a MercadoDineroServiceImpl.getEstadoCuenta()");

		// Se validan los parametros
		utilService.validaDTONoNulo(params, "");
		utilService.validaAgente(params.getAgente(), false);


		return null;

	}

	/**
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService
	 *      #getListaConfirmacionApertura(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      boolean)
	 */
	public TraspasoMiscFiscalVO[] getListaConfirmacionApertura(
			AgenteVO agenteFirmado, boolean aperturaSistema)
			throws BusinessException {

		logger
				.info("Entrando a MercadoDineroServiceImpl.getListaConfirmacionApertura()");

		TraspasoMiscFiscalVO[] resultado = null;
		List mdIntsrns = null;

		/* Se valida el agente firmado */
		utilService.validaAgente(agenteFirmado, " firmado ", false);

		Date fechaLiquidacion = dateUtilsDao.getDateFechaDB();


		if ((resultado == null) || (resultado.length == 0)) {

			throw new BusinessException(errorResolver.getMessage("J9998"));

		}

		return resultado;

	}

	/**
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#getListaConfirmacionMiscFiscal(com.indeval.portaldali.middleware.services.modelo.AgenteVO)
	 */
	public TraspasoMiscFiscalVO[] getListaConfirmacionMiscFiscal(
			AgenteVO agenteFirmado) throws BusinessException {

		logger.info("Entrando a MercadoDineroServiceImpl.getListaConfirmacionMiscFiscal()");

		TraspasoMiscFiscalVO[] resultado = null;
		List mdIntsrns = null;

		/* Se valida el agente firmado */
		utilService.validaDTONoNulo(agenteFirmado, " agente firmado ");
		agenteFirmado.tieneClaveValida();

		Date fechaLiquidacion = dateUtilsDao.getFechaHoraCero(dateUtilsDao
				.getDateFechaDB());

		return null;

	}

	/**
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#prorroga(java.math.BigDecimal)
	 */
	public BigDecimal prorroga(BigDecimal folioPrestamo)
			throws BusinessException {

		logger.info("Entrando a MercadoDineroServiceImpl.prorroga()");

		if (folioPrestamo == null) {

			throw new BusinessException(errorResolver.getMessage("J0101"));

		}

		Integer status = Constantes.ERROR_NO_IDENTIFICADO;

		List listResultado = null;
		BigDecimal folPrestamoProrrogado = null;
		Map resultado = null;

		return folPrestamoProrrogado;

	}

	/**
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#registraOperacion(com.indeval.portaldali.middleware.services.mercadodinero.RegistraOperacionParams)
	 */
	public String registraOperacion(RegistraOperacionParams params)
			throws BusinessException {

		logger.info("Entrando a MercadoDineroServiceImpl.registraOperacion()");

		/* Se valida el objeto de parametros */
		params = validaRegistraOperacion(params);

		/* Se obtienen los datos del comprador y del vendedor */
		AgentePersistence agenteVendedor = UtilsVOCatalogo
				.getInstanceAgente((CuentaNombrada) utilService.validaAgente(
						params.getTraspasante(), " traspasante ", true)
						.getRegistros().get(0));
		AgentePersistence agenteComprador = UtilsVOCatalogo
				.getInstanceAgente((CuentaNombrada) utilService.validaAgente(
						params.getReceptor(), " receptor ", true)
						.getRegistros().get(0));

		/*
		 * Se obtienen los datos de la emision si esta registrada o se construye
		 * una nueva con la tv, emisora, serie, cupon recibida
		 */
		EmisionPersistence emision = null;
		EmisionPK emisionRecibida = UtilsDaliVO.parseEmisionVO2EmisionPK(params
				.getEmision());
		// EmisionPersistence emisionRegistrada =
		// emisionesDao.getEmision(emisionRecibida);
		//
		// if (emisionRegistrada == null) {
		//
		// emision = new EmisionPersistence();
		// emision.setEmisionPk(emisionRecibida);
		//
		// } else {
		//
		// emision = emisionRegistrada;
		//
		// }

		/* Se valida la emision */
		utilService.validaDTONoNulo(emision, "  emision ");

		/* Se busca el instrumento y se valida el mercado */
		emision = buscaInstrumento(emision);

		String mercado = emision.getInstrumento().getMercado();

		if (!(PAPEL_BANCARIO.equalsIgnoreCase(mercado) || PAPEL_GUBERNAMENTAL
				.equalsIgnoreCase(mercado))) {

			throw new BusinessException(errorResolver.getMessage("J0110"));

		}

		// this.validaTVMD(params.getEmision().getIdTipoValor());

		/* Se valida la tenencia de la cuenta */
		if (emision.getFechaVencimiento() != null) {

			if (!Constantes.VENTA.equalsIgnoreCase(params.getClaveReporto())
					&& !Constantes.CLAVE_REPORTO_T.equalsIgnoreCase(params
							.getClaveReporto())
					&& !Constantes.TRASPASO_DE_FONDEO.equalsIgnoreCase(params
							.getClaveReporto())
					&& !Constantes.CLAVE_REPORTO_OP_PRIMARIA_RECOMPRA
							.equalsIgnoreCase(params.getClaveReporto())) {

				if (Constantes.TENENCIA_TESO.equalsIgnoreCase(agenteComprador
						.getTenencia().trim())
						|| Constantes.TENENCIA_TESO
								.equalsIgnoreCase(agenteVendedor.getTenencia()
										.trim())) {

					throw new BusinessException(errorResolver
							.getMessage("J0027"));

				}

				try {

					if (DateUtil.comparaFechasDias(emision
							.getFechaVencimiento(), params.getFechaRegreso()) <= 0) {

						throw new BusinessException(
								errorResolver
										.getMessage(
												"J0000",
												new String[] { "La fecha de vencimiento de la emisi\u00f3n debe ser mayor "
														+ "a la fecha del Reporto" }));

					}

				} catch (IllegalArgumentException ex) {

					throw new BusinessException(
							errorResolver
									.getMessage(
											"J0019",
											(Object) "La fecha de vencimiento, o la fecha de reporto"));

				}

			}

		}

		if (!params.getClaveReporto().equalsIgnoreCase(
				Constantes.TRASPASO_DE_FONDEO)) {

			/*
			 * Reglas de negocio para clave_reporto diferente de F - Fondeo
			 * cuenta propia
			 */

			/* Solo entran los tv's que esten en Instrumento */
			if (!emision.tieneTvValida()
					|| (instrumentoDaliDao.getInstrumento(emision.getEmisionPk()
							.getTv().trim()) == null)) {

				throw new BusinessException(errorResolver.getMessage("J0032"));

			}

			// Estas son las validaciones de tv que estan en la forma de unifase
			/*
			 * Validacion de la forma de uniface
			 * 
			 * if ($$mercado = 'PG') selectdb (count(tv)) from "instrumentos"
			 * u_where (tv.instrumentos = tv.emisiones & uso.instrumentos =
			 * 'dine' & clase_ins = 'BM' & (excepcion.instrumentos = 0 |
			 * excepcion.instrumentos = 1)) to $90 else selectdb (count(tv))
			 * from "instrumentos" u_where (tv.instrumentos = tv.emisiones &
			 * uso.instrumentos = 'dine' & clase_ins != 'BM') to $90 endif if
			 * ($90 < 1) help "TIPO DE VALOR INVALIDO" clear/e "emisiones"
			 * tv.emisiones = "" return(-1)
			 * 
			 */

			/*
			 * Se valida el uso del instrumento. La emision y el instrumento ya
			 * fueron validados por lo que se asume que no son null
			 */
			if (((emision.getInstrumento().getUso() != null) && !emision
					.getInstrumento().getUso().trim().equalsIgnoreCase(
							Constantes.DINERO))
					|| (((emision.getInstrumento().getClaseIns() != null) && !emision
							.getInstrumento().getClaseIns().trim()
							.equalsIgnoreCase(Constantes.CLAVE_CLASE_INS_BM))
							&& (emision.getInstrumento().getMercado() != null) && !emision
							.getInstrumento().getMercado().trim()
							.equalsIgnoreCase(PAPEL_BANCARIO))) {

				throw new BusinessException(errorResolver.getMessage("J0091"));

			}

			/*
			 * Se valida el mercado del instrumento. La emision y el instrumento
			 * ya fueron validados por lo que se asume que no son null
			 */
			if (((emision.getInstrumento().getMercado() != null) && emision
					.getInstrumento().getMercado().trim().equalsIgnoreCase(
							PAPEL_GUBERNAMENTAL))
					&& ((emision.getInstrumento().getExcepcion() != null)
							&& (emision.getInstrumento().getExcepcion()
									.intValue() != 0) && (emision
							.getInstrumento().getExcepcion().intValue() != 1))) {

				throw new BusinessException(errorResolver.getMessage("J0092"));

			}

			// validacion de tipo de papel
			/*
			 * Validacion de la forma de uniface
			 * 
			 * if (clase_ins.instrumentos != 'BM' & (clave_reporto.traspasos =
			 * 'D' | clave_reporto.traspasos = 'D' | clave_reporto.traspasos =
			 * 'C')) help "ESTE TIPO DE OPERACION SOLO PARA PAPEL GUBERNAMENTAL"
			 * clave_reporto.traspasos = "" return(-1) endif
			 * 
			 */
			if (((emision != null) && (emision.getInstrumento() != null) && (emision
					.getInstrumento().getClaseIns() != null))
					&& !emision.getInstrumento().getClaseIns().trim()
							.equalsIgnoreCase(Constantes.CLAVE_CLASE_INS_BM)
					&& (params.getClaveReporto().equalsIgnoreCase(
							Constantes.CLAVE_REPORTO_D) || params
							.getClaveReporto().equalsIgnoreCase(
									Constantes.CLAVE_REPORTO_C))) {

				throw new BusinessException(errorResolver.getMessage("J0093"));

			}

			/*
			 * Validacion de la forma de uniface
			 * 
			 * if (clave_reporto.traspasos != 'T' & (tipo_cta.agente_comprador =
			 * 'DIRE' | tipo_cta.agentes = 'DIRE' | tipo_cta.agente_comprador =
			 * 'COBE' | tipo_cta.agentes = 'COBE' | tipo_cta.agente_comprador =
			 * 'CBPR' |tipo_cta.agentes = 'CBPR' | tipo_cta.agente_comprador =
			 * 'CBTE' | tipo_cta.agentes = 'CBTE' |tipo_cta.agente_comprador =
			 * 'LIBR' | tipo_cta.agentes = 'LIBR' |tipo_cta.agente_comprador =
			 * 'MDER' | tipo_cta.agentes = 'MDER')) help "LAS CUENTAS ESPECIALES
			 * NO ENTRAN AL ESQUEMA DE LIQUIDACION" clave_reporto.traspasos = ""
			 * $prompt = clave_reporto.traspasos return(-1)
			 * 
			 */
			if (!params.getClaveReporto().equalsIgnoreCase(
					Constantes.CLAVE_REPORTO_T)
					&& !params.getClaveReporto().equalsIgnoreCase(
							Constantes.VENTA)
					&& !params.getClaveReporto().equalsIgnoreCase(
							Constantes.CLAVE_REPORTO_OP_PRIMARIA_RECOMPRA)
					&& (agenteComprador.getTipoCta().equalsIgnoreCase(
							Constantes.CLAVE_TIPO_CUENTA_DIRE)
							|| agenteComprador.getTipoCta().equalsIgnoreCase(
									Constantes.CLAVE_TIPO_CUENTA_LIBR)
							|| agenteVendedor.getTipoCta().equalsIgnoreCase(
									Constantes.CLAVE_TIPO_CUENTA_DIRE)
							|| agenteVendedor.getTipoCta().equalsIgnoreCase(
									Constantes.CLAVE_TIPO_CUENTA_LIBR)
							|| agenteVendedor.getTipoCta().equalsIgnoreCase(
									Constantes.CLAVE_TIPO_CUENTA_COBE)
							|| agenteVendedor.getTipoCta().equalsIgnoreCase(
									Constantes.CLAVE_TIPO_CUENTA_CBPR) || agenteVendedor
							.getTipoCta().equalsIgnoreCase(
									Constantes.CLAVE_TIPO_CUENTA_CBTE))) {

				throw new BusinessException(
						"Las Operaciones  con cuentas DIRECTAS, "
								+ "LIBRE SUSCRIP, COBERTURAS No pueden ser Reportos");

			}

			/*
			 * Validacion de la forma de uniface if ((clave_reporto.traspasos =
			 * 'R' | clave_reporto.traspasos = 'Y') & excepcion.instrumentos !=
			 * 1 & $hits(emisiones) > 0) help "ESTE PAPEL NO ES REPORTABLE"
			 * clave_reporto.traspasos = "" $prompt = clave_reporto.traspasos
			 * return(-1)
			 */
			if (((emision != null) && (emision.getInstrumento() != null) && (emision
					.getInstrumento().getExcepcion() != null))
					&& !(emision.getInstrumento().getExcepcion().compareTo(
							new Integer(1)) == 0)
					&& (params.getClaveReporto().equalsIgnoreCase(
							Constantes.INICIO_DE_REPORTO) || params
							.getClaveReporto().equalsIgnoreCase(
									Constantes.CLAVE_REPORTO_Y))) {

				throw new BusinessException("El Papel no es Reportable");

			}

			if (Constantes.VENTA.equalsIgnoreCase(params.getClaveReporto()
					.trim())) {

				if (UtilsVOMercadoDinero.esCuentaEmision(agenteVendedor
						.getAgentePK().getCuenta())
						|| UtilsVOMercadoDinero.esCuentaEmision(agenteComprador
								.getAgentePK().getCuenta())) {

					throw new BusinessException(errorResolver
							.getMessage("J0033"));

				}

			}

			/*
			 * if (((id_inst.agentes != '01' & id_inst.agentes != '02' &
			 * id_inst.agentes != '13' ) | (id_inst.agente_comprador != '01' &
			 * id_inst.agente_comprador != '02' & id_inst.agente_comprador !=
			 * '13' & id_inst.agente_comprador != '17')) &
			 * clave_reporto.traspasos != 'T') help "SOLO OPERACIONES LIBRES DE
			 * PAGO PARA INSTIT. DIFERENTES A CB. Y BANCOS Y FIDEI.BANXICO"
			 * clave_reporto.traspasos = "" $prompt = id_inst.agente_comprador
			 * return(-1) endif
			 */
			if (!params.getClaveReporto().equalsIgnoreCase(
					Constantes.CLAVE_REPORTO_T)
					&& ((!agenteVendedor.getAgentePK().getIdInst().equalsIgnoreCase(Constantes.CASAS_DE_BOLSA)
							&& !agenteVendedor.getAgentePK().getIdInst().equalsIgnoreCase(Constantes.AFORES)
							&& !agenteVendedor.getAgentePK().getIdInst().equalsIgnoreCase(Constantes.BANCOS) 
							&& !agenteVendedor.getAgentePK().getIdInst().equalsIgnoreCase("19")
							&& !agenteVendedor.getAgentePK().getIdInst().equalsIgnoreCase("03")
							&& !agenteVendedor.getAgentePK().getIdInst().equalsIgnoreCase("07")
							&& !agenteVendedor.getAgentePK().getIdInst().equalsIgnoreCase(Constantes.FIDEICOMISO_BANCO_MEXICO)) 
					|| (!agenteComprador.getAgentePK().getIdInst().equalsIgnoreCase(Constantes.CASAS_DE_BOLSA)
							&& !agenteComprador.getAgentePK().getIdInst().equalsIgnoreCase(Constantes.AFORES)
							&& !agenteComprador.getAgentePK().getIdInst().equalsIgnoreCase(Constantes.BANCOS)
							&& !agenteComprador.getAgentePK().getIdInst().equalsIgnoreCase("19")
							&& !agenteComprador.getAgentePK().getIdInst().equalsIgnoreCase("03")
							&& !agenteComprador.getAgentePK().getIdInst().equalsIgnoreCase("07")
							&& !agenteComprador.getAgentePK().getIdInst().equalsIgnoreCase(Constantes.FIDEICOMISO_BANCO_MEXICO) && !agenteComprador
							.getAgentePK().getIdInst().equalsIgnoreCase(Constantes.IPAB)))) {

				throw new BusinessException(errorResolver.getMessage("J0096"));

			}

			if ((emision != null) && !emision.esReportable()) {

				throw new BusinessException(errorResolver.getMessage("J0097"));

			}

			if (params.getFechaReporto() == null) {

				/* Se asigna a la fecha reporto la fecha de regreso */
				params.setFechaReporto(params.getFechaRegreso());

			}

			/*
			 * Se coloca la C de confirmacion, solo si la clave_reporto es
			 * diferente de T
			 */
			if (!params.getClaveReporto().equalsIgnoreCase(
					Constantes.CLAVE_REPORTO_T)) {

				params.setSociedad(Constantes.SOCIEDAD_CONFIRMADA);

			} else {

				params.setSociedad(Constantes.SOCIEDAD_PENDIENTE_DE_CONFIRMAR);

			}

		} else {

			/*
			 * Reglas de negocio para clave_reporto igual a F - Fondeo cuenta
			 * propia
			 */

			/* Se valida que la tenencia del agente sea PROP o TERC */
			if (!agenteComprador.getTipoCta().trim().startsWith(
					Constantes.CUENTA_PROPIA)
					&& !agenteComprador.getTipoCta().trim().startsWith(
							Constantes.CUENTA_TERCEROS)) {

				throw new BusinessException(errorResolver.getMessage("J0098"));

			}

			/*
			 * Se valida que la cuenta del traspasante y la del receptor no sean
			 * iguales
			 */
			if (agenteVendedor.getAgentePK().getCuenta().equals(
					agenteComprador.getAgentePK().getCuenta())) {

				throw new BusinessException(errorResolver.getMessage("J0099"));

			}

			/* Se coloca la P de pendiente */
			params.setSociedad(Constantes.SOCIEDAD_PENDIENTE_DE_CONFIRMAR);

		}

		/* Se graba la informacion en la BD a traves del SP */
		Integer folioControl = UtilsDaliVO.INTEGER_ZERO;

		
		return folioControl.toString();

	}

	

	/**
	 * Este metodo se encarga de encapsular las validaciones de parametros y
	 * fases para el registro de operaciones
	 * 
	 * @param params
	 * 
	 * @return RegistraOperacionParams
	 * 
	 * @throws BusinessException
	 */
	private RegistraOperacionParams validaRegistraOperacion(
			RegistraOperacionParams params) throws BusinessException {

		logger.info("Entrando a MercadoDineroServiceImpl.validaRegistraOperacion()");

		/* Se validan el objeto de parametros */
		utilService.validaDTONoNulo(params, "");
		params.esValido();

		/* Se validan el agente traspasante y el receptor */
		utilService.validaDTONoNulo(params.getTraspasante(),
				" agente traspasante ");
		params.getTraspasante().tieneClaveValida();
		utilService.validarClaveTipo(params.getTraspasante().getCuenta(),
				" cuenta traspasante ");

		utilService.validaDTONoNulo(params.getReceptor(), " agente receptor ");
		params.getReceptor().tieneClaveValida();
		utilService.validarClaveTipo(params.getReceptor().getCuenta(),
				" cuenta receptor ");

		utilService.isAgenteRegistrado(params.getTraspasante());
		utilService.isAgenteRegistrado(params.getReceptor());

		/* Se valida la cantidad operada */
		utilService.validaNumber(params.getCantidadOperada(), patternResolver
				.getMessage("cantidad"));

		StringBuffer operacion = new StringBuffer("");

		/* Se valida la operacion de acuerdo a la fase */
		boolean noPermitida = false;
		int faseActiva = utilService.validaFase(fasesAbiertas);

		switch (faseActiva) {

		case 1:
		case 8:

			/* En fase 1 y 8 solo se pueden efectuar traspaso de fondeos */
			if (!Constantes.TRASPASO_DE_FONDEO.equalsIgnoreCase(params
					.getClaveReporto())) {

				operacion.append(" distinta a un Traspaso de Fondeo ");
				noPermitida = true;

			}

			break;

		case 3:
		case 5:
		case 7:

			/* En fase 3, 5 y 7 solo no se pueden efectuar traspaso de fondeos */
			if (Constantes.TRASPASO_DE_FONDEO.equalsIgnoreCase(params
					.getClaveReporto())) {

				operacion.append(" de Traspaso de Fondeo ");
				noPermitida = true;

			}

			break;

		case 10:
		case 11:

			/* En fase 10 y 11 se pueden efectuar... */
			/* traspasos libres de pago entre cuentas de la misma institucion */
			if (Constantes.CLAVE_REPORTO_T.equalsIgnoreCase(params
					.getClaveReporto())
					&& !params.getTraspasante().equals(params.getReceptor())) {

				operacion.append(" entre cuentas de diferente institucion ");
				noPermitida = true;

			} /*
				 * traspasos libres de pago entre cuentas de la misma
				 * institucion
				 */
			else if ((!Constantes.VENTA.equalsIgnoreCase(params
					.getClaveReporto())
					&& !Constantes.CLAVE_REPORTO_T.equalsIgnoreCase(params
							.getClaveReporto()) && !Constantes.TRASPASO_DE_FONDEO
					.equalsIgnoreCase(params.getClaveReporto()))
					&& (params.getPlazoLiquidacion().intValue() == 0)) {

				operacion.append(" entre cuentas de diferente institucion ");
				noPermitida = true;

			} /* ventas fecha valor */
			else if (

			Constantes.VENTA.equalsIgnoreCase(params.getClaveReporto())
					&& (params.getPlazoLiquidacion().intValue() == 0)) {
				operacion.append(" de venta de mismo dia ");
				noPermitida = true;
			}

			break;

		default:
			noPermitida = true;

			break;

		}

		return params;

	}

	/**
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#traspasoMercadoDinero(com.indeval.portaldali.middleware.services.mercadodinero.TraspasoMercadoDineroParams)
	 */
	public BigInteger traspasoMercadoDinero(TraspasoMercadoDineroParams params)
			throws BusinessException {

		logger.info("Entrando a MercadoDineroServiceImpl.traspasoMercadoDinero()");

		return null;

	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#traspasoMercadoDineroBusinessRules(com.indeval.portaldali.middleware.services.mercadodinero.TraspasoMercadoDineroParams)
	 */
	public BigInteger traspasoMercadoDineroBusinessRules(TraspasoMercadoDineroParams params) throws BusinessException {
		logger.info("Entrando a MercadoDineroServiceImpl.traspasoMercadoDinero()");
		
		/* Se valida el objeto de parametros */
		utilService.validaDTONoNulo(params, "");
		/* Se valida el agente traspasante */
		utilService.validaDTONoNulo(params.getTraspasante(), " agente traspasante ");
		params.getTraspasante().tieneClaveValida();
		utilService.isAgenteRegistrado(params.getTraspasante());
		/* Se valida el agente receptor */
		utilService.validaDTONoNulo(params.getReceptor(), " agente receptor ");
		params.getReceptor().tieneClaveValida();
		utilService.isAgenteRegistrado(params.getReceptor());
		/* Se valida que no se use la misma cuenta en los cruces */
		if (params.getTraspasante().equals(params.getReceptor())) {
			throw new BusinessException(errorResolver.getMessage("J0066"));
		}
		//Se realiza la validación del RFC/CURP
		//Pablo Balderas 23/06/2014
		if(StringUtils.isNotBlank(params.getRfcCURP())) {
			if(!validacionService.validarRfcCurp(params.getRfcCURP())) {
				throw new BusinessException(errorResolver.getMessage("errorRfcCurp"));
			}
		}

		/* Se valida el tipo de movimiento */
		params.validaTipoMovimiento();

		if (!params.getTipoMovimiento().equalsIgnoreCase(APERTURA)) {
			
			/* Se valida la fecha de adquisicion */
			if (params.getFechaAdquisicion() == null) {
				throw new BusinessException(errorResolver.getMessage("J0213"));
			}
			
			/* Se valida el precio de adquisicion */
			if (params.getPrecioAdquisicion() == null) {
				throw new BusinessException(errorResolver.getMessage("J0067"));
			}

			if (params.getPrecioAdquisicion().compareTo(UtilsDaliVO.BIG_DECIMAL_ZERO) <= 0) {
				throw new BusinessException(errorResolver.getMessage("J0068"));
			}

			utilService.validaNumber(params.getPrecioAdquisicion(), patternResolver.getMessage("precioxtitulo"));
			
			//Campo nuevo 28nov2013
			
			
			/*
			 * Se agrega validacion. 
			 * Valida que el id de institucion del receptor no sea CCV(25)
			 * 16/05/2014 Pablo Balderas
			 */
			if (params.getReceptor().getId().trim()
					.equals(com.indeval.portaldali.middleware.services.mercadocapitales.util.Constantes.CUENTA_25)) {
				throw new BusinessException(
						"No es posible realizar traspasos a cuentas de la CCV");
			}
		}

		/* Se valida la emision */
		utilService.validaDTONoNulo(params.getEmision(), " emision ");
		params.getEmision().tienePKValida();

		/*
		 * Se recupera el valor del mercado tanto para emisiones de dinero como
		 * para las de capitales
		 */
		EmisionPersistence emision = this.buscaInstrumento(UtilsDaliVO.getEmision(params.getEmision()));
		String mercado = emision.getInstrumento().getMercado();
		
		if(params.isMercadoDinero()){
			if (!(PAPEL_BANCARIO.equalsIgnoreCase(mercado) || PAPEL_GUBERNAMENTAL.equalsIgnoreCase(mercado))) {	
				throw new BusinessException(errorResolver.getMessage("J0110"));
			}
		}
		else{
			if (!Constantes.MERCADO_CAPITALES.equalsIgnoreCase(mercado)) {
				throw new BusinessException(errorResolver.getMessage("J0074"));
			}
		}

		/* Se obtiene la fecha de liquidacion */
		Date fechaLiquidacion = dateUtilsDao.getFechaHoraCero(dateUtilsDao.getDateFechaDB());
		/* Se obtiene el FolioLlave */
		String llave = utilService.getLlaveFolio();

		/*
		 * Si es Apertura de Sistema utiliza... bddinero..UP_altamdin - >
		 * BusinessRulesMercadoDineroService.altaMdintran ()
		 * 
		 * Si es Miscelanea Fiscal utiliza... catalogo..UP_traslp - >
		 * BusinessRulesCatalogoService.registraTraspasosYCompensaValores()
		 */
		if (StringUtils.isNotBlank(params.getTipoMovimiento()) && params.getTipoMovimiento().equalsIgnoreCase(APERTURA)) {
			AltaMdintranParams altaMdintranParams = getInstanceAltaMdintranParams(params, llave, mercado, fechaLiquidacion);
			businessRulesMercadoDineroService.altaMdintran(altaMdintranParams);
		}
		else {
			RegistraTraspasosYCompensaValoresParams registraTraspasosYCompensaValoresParams = 
				getInstanceRegistraTraspasosYCompensaValoresParams(params, llave, mercado, fechaLiquidacion);
			businessRulesCatalogoService.registraTraspasosYCompensaValores(registraTraspasosYCompensaValoresParams);
		}

		//Realiza la validacion de control de cuentas de emision
		//Mayo/2018 - Pablo Balderas
		validadorCuentasEmision.validarCuentasEmisionMercadoDinero(params.getTraspasante(), params.getReceptor(), params.getEmision());
		
		return utilService.getFolio(SECUENCIA_FOLIO_CONTROL);
	}

	/**
	 * Construye y llena el objeto AltaMdintranParams a partir de un
	 * TraspasoMercadoDineroParams
	 * 
	 * @param params
	 * @param llave
	 * @param mercado
	 * @param fechaLiquidacion
	 * 
	 * @return AltaMdintranParams
	 */
	private AltaMdintranParams getInstanceAltaMdintranParams(
			TraspasoMercadoDineroParams params, String llave, String mercado,
			Date fechaLiquidacion) {

		logger.info("Entrando a MercadoDineroServiceImpl.getInstanceAltaMdintranParams()");

		AltaMdintranParams altaMdintranParams = new AltaMdintranParams();

		if (Constantes.MERCADO_CAPITALES.equalsIgnoreCase(mercado)) {

			altaMdintranParams.setAplicacion(Constantes.APLICACION_CAPITALES);

		} else {

			altaMdintranParams.setAplicacion(Constantes.APLICACION);

		}

		altaMdintranParams.setAreaTrabajo(Constantes.AREA_TRABAJO);
		altaMdintranParams.setBajaLogica(Constantes.EN_FIRME);
		altaMdintranParams.setCantOp(params.getCantidad());
		altaMdintranParams.setClaveReporto(Constantes.CLAVE_REPORTO_T);
		altaMdintranParams.setCta1(params.getTraspasante().getCuenta());
		altaMdintranParams.setCta2(params.getReceptor().getCuenta());
		altaMdintranParams.setCupon(params.getEmision().getCupon());
		altaMdintranParams.setDiasPlazo(UtilsDaliVO.BIG_INTEGER_ZERO);
		altaMdintranParams.setDivisa(Constantes.PESO);
		altaMdintranParams.setEmis(params.getEmision().getEmisora());
		altaMdintranParams.setError(Constantes.CLAVE_ERROR);
		altaMdintranParams.setFechaConcer(null);
		altaMdintranParams.setFechaLiq(fechaLiquidacion);
		altaMdintranParams.setFechaReporto(dateUtilsDao.getDateFechaDB());
		altaMdintranParams.setFol1(params.getTraspasante().getFolio());
		altaMdintranParams.setFol2(params.getReceptor().getFolio());
		altaMdintranParams.setFolioc(null);
		altaMdintranParams.setFolioDescripc(null);
		altaMdintranParams.setId1(params.getTraspasante().getId());
		altaMdintranParams.setId2(params.getReceptor().getId());
		altaMdintranParams.setIdTasaRef(null);
		altaMdintranParams.setLiq(UtilsDaliVO.BIG_INTEGER_ZERO);
		altaMdintranParams.setLlave(llave);
		altaMdintranParams.setMercado(mercado);
		altaMdintranParams.setOrigen(Constantes.MOVIMIENTO_APERTURA_SISTEMA);
		altaMdintranParams.setOrigenAplicac(Constantes.ORIGEN);
		altaMdintranParams.setPrecioTitulo(UtilsDaliVO.BIG_DECIMAL_ZERO);
		altaMdintranParams.setSerie(params.getEmision().getSerie());
		altaMdintranParams.setSociedad(Constantes.SOCIEDAD_CONFIRMADA);
		altaMdintranParams.setTasaPremio(UtilsDaliVO.BIG_DECIMAL_ZERO);
		altaMdintranParams.setTv(params.getEmision().getIdTipoValor());
		altaMdintranParams.setUsuario(Constantes.USUARIO); // "MERDIN"??

		return altaMdintranParams;

	}

	/**
	 * Construye y llena el objeto RegistraTraspasosYCompensaValoresParams a
	 * partir de un TraspasoMercadoDineroParams
	 * 
	 * @param params
	 * @param llave
	 * @param mercado
	 * @param fechaLiquidacion
	 * 
	 * @return RegistraTraspasosYCompensaValoresParams
	 */
	private RegistraTraspasosYCompensaValoresParams getInstanceRegistraTraspasosYCompensaValoresParams(
			TraspasoMercadoDineroParams params, String llave, String mercado,
			Date fechaLiquidacion) {

		logger.info("Entrando a MercadoDineroServiceImpl."
				+ "getInstanceRegistraTraspasosYCompensaValoresParams()");

		RegistraTraspasosYCompensaValoresParams registraTraspasosYCompensaValoresParams = new RegistraTraspasosYCompensaValoresParams();

		registraTraspasosYCompensaValoresParams.setCantOp(params.getCantidad());
		registraTraspasosYCompensaValoresParams
				.setClaveReporto(Constantes.CLAVE_REPORTO_T);
		registraTraspasosYCompensaValoresParams.setCliente(params.getCliente());
		registraTraspasosYCompensaValoresParams.setCta1(params.getTraspasante()
				.getCuenta());
		registraTraspasosYCompensaValoresParams.setCta2(params.getReceptor()
				.getCuenta());
		registraTraspasosYCompensaValoresParams.setCupon(params.getEmision()
				.getCupon());
		registraTraspasosYCompensaValoresParams.setCurpRfc(params.getRfcCURP());
		registraTraspasosYCompensaValoresParams.setDivisa(Constantes.PESO);
		registraTraspasosYCompensaValoresParams.setEmis(params.getEmision()
				.getEmisora());
		registraTraspasosYCompensaValoresParams.setFechaAdquisic(params
				.getFechaAdquisicion());
		registraTraspasosYCompensaValoresParams.setFechaLiq(fechaLiquidacion);
		registraTraspasosYCompensaValoresParams.setFol1(params.getTraspasante()
				.getFolio());
		registraTraspasosYCompensaValoresParams.setFol2(params.getReceptor()
				.getFolio());
		registraTraspasosYCompensaValoresParams.setFolioDesc(null);
		registraTraspasosYCompensaValoresParams.setId1(params.getTraspasante()
				.getId());
		registraTraspasosYCompensaValoresParams.setId2(params.getReceptor()
				.getId());
		registraTraspasosYCompensaValoresParams
				.setLiq(UtilsDaliVO.BIG_INTEGER_ZERO);
		registraTraspasosYCompensaValoresParams.setLlave(llave);
		registraTraspasosYCompensaValoresParams.setMercado(mercado);
		registraTraspasosYCompensaValoresParams
				.setNomArea(Constantes.AREA_TRABAJO);
		registraTraspasosYCompensaValoresParams
				.setNombreUsuario(Constantes.USUARIO); // "MERDIN"??
		registraTraspasosYCompensaValoresParams.setNomUs(Constantes.USUARIO); // "MERDIN"??
		registraTraspasosYCompensaValoresParams
				.setOrigen(Constantes.MOVIMIENTO_APERTURA_SISTEMA);
		registraTraspasosYCompensaValoresParams.setOrigenApl(Constantes.ORIGEN);
		registraTraspasosYCompensaValoresParams.setPrecioAdquisi(params
				.getPrecioAdquisicion());
		registraTraspasosYCompensaValoresParams
				.setPrecioTitulo(UtilsDaliVO.BIG_INTEGER_ZERO);
		registraTraspasosYCompensaValoresParams.setSerie(params.getEmision()
				.getSerie());
		registraTraspasosYCompensaValoresParams
				.setSociedadSerie(Constantes.SOCIEDAD_CONFIRMADA);
		registraTraspasosYCompensaValoresParams.setTCta1(params
				.getTraspasante().getTipo());
		registraTraspasosYCompensaValoresParams.setTCta2(params.getReceptor()
				.getTipo());
		registraTraspasosYCompensaValoresParams.setTv(params.getEmision()
				.getIdTipoValor());
		registraTraspasosYCompensaValoresParams.setUsuReal(Constantes.USUARIO); // "MERDIN"??

		return registraTraspasosYCompensaValoresParams;

	}

	/**
	 * TODO hacer uso de errores.properties para el manejo de errores
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#vencimientoAnticipado(java.math.BigDecimal)
	 */
	public boolean vencimientoAnticipado(BigDecimal folioPrestamo)
			throws BusinessException {

		logger.info("Entrando a MercadoDineroServiceImpl.vencimientoAnticipado()");
		return false;

	}

	/**
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService
	 *      #consultaDatosOperacionGarantias(com.indeval.portaldali.middleware.services.modelo.EmisionVO,
	 *      java.math.BigDecimal)
	 */
	public BigInteger consultaDatosOperacionGarantias(EmisionVO emisionVO,
			BigDecimal excedente) throws BusinessException {

		logger.info("Entrando a MercadoDineroServiceImpl.consultaDatosOperacionGarantias()");


		return null;

	}

	/**
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService
	 *      #ejecutaOperacionGarantias(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      com.indeval.portaldali.middleware.services.modelo.EmisionVO,
	 *      java.math.BigDecimal, java.math.BigInteger, java.lang.String)
	 */
	public Integer ejecutaOperacionGarantias(AgenteVO agente,
			EmisionVO emision, BigDecimal cantidadLiberada,
			BigInteger folioPrestamo, String tipoOperacion)
			throws BusinessException {

		logger.info("Entrando a MercadoDineroServiceImpl.ejecutaOperacionGarantias()");

		return null;

	}

	/**
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#agregarDiasHabiles(java.util.Date,
	 *      int)
	 */
	public Date agregarDiasHabiles(Date fechaInicial, int offset)
			throws BusinessException {

		logger.info("Entrando a MercadoDineroServiceImpl.agregarDiasHabiles()");

		utilService.validaDTONoNulo(fechaInicial, " Fecha Inicial ");

		return utilService.agregarDiasHabiles(dateUtilsDao.getFechaHoraCero(fechaInicial), offset);

	}

	/**
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#consultarDiasInhabilesByMonth(int,
	 *      int)
	 */
	public List consultarDiasInhabilesByMonth(int month, int year)
			throws BusinessException {

		logger
				.info("Entrando a MercadoDineroServiceImpl.consultarDiasInhabilesByMonth()");

		return diaInhabilDaliDao.findDiasInhabilesByMonthYear(month, year);

	}


	/**
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#getPosturaPrestamista(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      com.indeval.portaldali.middleware.services.modelo.EmisionVO)
	 */
	public PosturaPrestamistaVO getPosturaPrestamista(AgenteVO agenteFirmado,
			EmisionVO emision) throws BusinessException {

		logger.info("Entrando a MercadoDineroServiceImpl.getPosturaPrestamista()");

		return null;

	}

	/**
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#setActualizaParametrosValpreE(java.util.HashMap)
	 */
	public void setActualizaParametrosValpreE(HashMap mapaParametros)
			throws BusinessException {

		logger.info("Entrando a MercadoDineroServiceImpl.setActualizaParametrosValpreE()");

	}

	/**
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#getPosicionValorSimple(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      com.indeval.portaldali.middleware.services.modelo.EmisionVO,
	 *      com.indeval.portaldali.middleware.services.modelo.PaginaVO, boolean)
	 */
	public PosicionValoresSimpleTotalVO[] getPosicionValorSimple(
			AgenteVO agenteVO, EmisionVO emisionVO, PaginaVO paginaVO,
			boolean export) throws BusinessException {

		logger.info("Entrando a MercadoDineroServiceImpl.getPosicionValorSimple()");


		return null;

	}


	/**
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#getOperacionesDiaDinero(com.indeval.portaldali.middleware.services.mercadodinero.OperacionDiaDineroParams)
	 */
	public OperacionDiaDineroVO getOperacionesDiaDinero(
			OperacionDiaDineroParams operacionDiaDineroParams)
			throws BusinessException {

		logger.info("Entrando a MercadoDineroServiceImpl.getOperacionesDiaDinero()");


		return null;

	}


	/**
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#getDetalleOperacionDiaDinero(com.indeval.portaldali.middleware.services.mercadodinero.TraspasoDineroParams)
	 */
	public OperacionDiaDetalleDineroVO getDetalleOperacionDiaDinero(
			TraspasoDineroParams traspasoDineroParams) throws BusinessException {

		logger.info("Entrando a MercadoDineroServiceImpl.getDetalleOperacionDiaDinero()");

		return null;

	}

	/**
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#getPrecioVectorValpreE(java.lang.String)
	 */
	public List getPrecioVectorValpreE(String tv) throws BusinessException {

		logger.info("Entrando a MercadoDineroServiceImpl.getPrecioVectorValpreE()");

		

		return null;

	}

	/**
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#getTvsPrecioVectorValpreE()
	 */
	public List getTvsPrecioVectorValpreE() throws BusinessException {

		logger.info("Entrando a MercadoDineroServiceImpl.getTvsPrecioVectorValpreE()");


		return null;

	}



	/**
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#getColocacionPrimariaRecompra(com.indeval.portaldali.middleware.services.mercadodinero.RegistraOperacionParams)
	 */
	public String getColocacionPrimariaRecompra(RegistraOperacionParams params)
			throws BusinessException {

		logger.info("Entrando a MercadoDineroServiceImpl.getColocacionPrimariaRecompra()");

		// Validamos que al menos una de las cuentas (traspasante,receptor),
		// deben ser de emision (5000,5001)
		if ((!UtilsVOMercadoDinero.esCuentaEmision(params.getTraspasante()) && !UtilsVOMercadoDinero
				.esCuentaEmision(params.getReceptor()))
				|| (UtilsVOMercadoDinero.esCuentaEmision(params
						.getTraspasante()) && UtilsVOMercadoDinero
						.esCuentaEmision(params.getReceptor()))) {

			throw new BusinessException(errorResolver.getMessage("J0030"));

		}

		if (StringUtils.isBlank(params.getClaveReporto())
				|| !params.getClaveReporto().equalsIgnoreCase(
						Constantes.CLAVE_REPORTO_OP_PRIMARIA_RECOMPRA)) {

			throw new BusinessException(errorResolver.getMessage("J0108"));

		}

		return this.registraOperacion(params);

	}

	/**
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#validaTVMD(java.lang.String)
	 */
	public String validaTVMD(String tv) throws BusinessException {

		logger.info("Entrando al metodo MercadoDineroServiceImpl.validaTV(tv)");

		Instrumento cInstrumento = this.instrumentoDaliDao.getInstrumento(tv,
				PAPELES_DINERO);

		// si el resultado de la consulta no arroja resultados, el tv pasado
		// como parametro no es valido para mercado de dinero
		if (cInstrumento == null) {

			throw new BusinessException(errorResolver.getMessage("J0032"));

		}

		return tv;

	}

	/**
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#validaGetColocacionPrimariaRecompraBusinessRules(com.indeval.portaldali.middleware.services.mercadodinero.RegistraOperacionParams)
	 */
	public String validaGetColocacionPrimariaRecompraBusinessRules(
			RegistraOperacionParams params) throws BusinessException {

		logger.info("Entrando a MercadoDineroServiceImpl.validaGetColocacionPrimariaRecompraBussinesRules");

		// Validamos que al menos una de las cuentas (traspasante,receptor),
		// deben ser de emision (5000,5001)
		if ((!UtilsVOMercadoDinero.esCuentaEmision(params.getTraspasante()) && 
		        !UtilsVOMercadoDinero.esCuentaEmision(params.getReceptor()))
				|| (UtilsVOMercadoDinero.esCuentaEmision(params.getTraspasante()) 
				        && UtilsVOMercadoDinero.esCuentaEmision(params.getReceptor()))) {

			throw new BusinessException(errorResolver.getMessage("J0030"));

		}
		
		/*
		if(params.getEmision().getMercado() == null || params.getEmision().getMercado().equals("MC")) {
			throw new BusinessException("Error: Esta operaci\u00f3n s\u00f3lo es posible con emisiones de mercado de capitales.");
		}
		*/
		
		
		if (StringUtils.isBlank(params.getClaveReporto())
				|| !params.getClaveReporto().equalsIgnoreCase(
						Constantes.CLAVE_REPORTO_OP_PRIMARIA_RECOMPRA)) {

			throw new BusinessException(errorResolver.getMessage("J0108"));

		}
		return this.validaRegistraOperacionBusinessRules(params);

	}

	/**
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#validaRegistraOperacionBusinessRules(com.indeval.portaldali.middleware.services.mercadodinero.RegistraOperacionParams)
	 */
	public String validaRegistraOperacionBusinessRules(
			RegistraOperacionParams params) throws BusinessException {

		logger.info("Entrando a MercadoDineroServiceImpl.validaRegistraOperacionBussinesRules");
		boolean emisionRegistrada = false;
		
		/*
		if(params.getEmision().getMercado() == null || params.getEmision().getMercado().equals("MC")) {
			throw new BusinessException("Error: Esta operaci\u00f3n s\u00f3lo es posible con emisiones de mercado de capitales.");
		}
		*/

		/* Se valida el objeto de parametros */
		params = validaRegistraOperacion(params);

		/*
		 * Se obtienen los datos de la emision si esta registrada o se construye
		 * una nueva con la tv, emisora, serie, cupon recibida
		 */
		EmisionPersistence emision = null;

		emision = UtilsDaliVO.parseEmisionVO2Emision(
			        catalogoService.getEmision(params.getEmision(), params.getIdBoveda()));
		
		if(emision == null){
			
			logger.debug(" WARNING : La Emision no esta registrada ");
			emision = new EmisionPersistence();
			emision.setEmisionPk(UtilsDaliVO.parseEmisionVO2EmisionPK(params
					.getEmision()));
		}
		else {
			emisionRegistrada = Boolean.TRUE;
		}

		/* Se valida la emision */
		utilService.validaDTONoNulo(emision, "  emision ");
		
		if(emision != null) {
			/* Se busca el instrumento y se valida el mercado */
			emision = buscaInstrumento(emision);
			
			if(emision.getInstrumento().getMercado() != null) {
				String mercado = emision.getInstrumento().getMercado().trim();
			

				if (!(PAPEL_BANCARIO.equalsIgnoreCase(mercado) || PAPEL_GUBERNAMENTAL
						.equalsIgnoreCase(mercado))) {

					throw new BusinessException(errorResolver.getMessage("J0110"));
				}
			}
		}
		
		/* Para el mercado de dinero no se valida la emisión.
		 * Se valida que el cupón sea valido para la emisión
		if(!utilService.isCuponValidoParaEmision(params.getEmision())) {
			throw new BusinessException("La emisi\u00F3n no es v\u00E1lida para los datos capturados");
		}
		*/

		//String tipoValor = this.validaTVMD(params.getEmision().getIdTipoValor());
		
		String mercado = getMercado((params.getEmision().getIdTipoValor()));
		if (StringUtils.isBlank(mercado)) {
			throw new BusinessException(errorResolver.getMessage("J0032"));
		}

		/* Se obtienen los datos del comprador y del vendedor */
		CuentaNombrada cCuentaNombradaComprador = (CuentaNombrada) utilService
				.isAgenteRegistrado(params.getReceptor()).getRegistros().get(0);
		AgentePersistence agenteComprador = UtilsVOCatalogo
				.getInstanceAgente(cCuentaNombradaComprador);
		CuentaNombrada cCuentaNombradaVendedor = (CuentaNombrada) utilService
				.isAgenteRegistrado(params.getTraspasante()).getRegistros()
				.get(0);
		AgentePersistence agenteVendedor = UtilsVOCatalogo
				.getInstanceAgente(cCuentaNombradaVendedor);

		/* Se valida la tenencia de la cuenta */
		if (emision.getFechaVencimiento() != null) {

			if (!Constantes.VENTA.equalsIgnoreCase(params.getClaveReporto())
					&& !Constantes.CLAVE_REPORTO_T.equalsIgnoreCase(params
							.getClaveReporto())
					&& !Constantes.TRASPASO_DE_FONDEO.equalsIgnoreCase(params
							.getClaveReporto())
					&& !Constantes.CLAVE_REPORTO_OP_PRIMARIA_RECOMPRA
							.equalsIgnoreCase(params.getClaveReporto())) {

				if (Constantes.TENENCIA_TESO.equalsIgnoreCase(agenteComprador.getTenencia().trim())
                        || Constantes.TENENCIA_TESO.equalsIgnoreCase(agenteVendedor.getTenencia()
                                .trim())) {

					throw new BusinessException(errorResolver.getMessage("J0027"));

				}

				if (diaInhabilDaliDao.esInhabil(params.getFechaRegreso())) {

					throw new BusinessException(errorResolver.getMessage("J0089"));

				}

				try {

					if (DateUtil.comparaFechasDias(emision
							.getFechaVencimiento(), params.getFechaRegreso()) <= 0) {

						throw new BusinessException(errorResolver.getMessage("J0000", new String[] {
                            "La fecha de vencimiento de la emisi\u00f3n "
                                    + "debe ser mayor a la fecha del Reporto" }));

					}

				} catch (IllegalArgumentException ex) {

					throw new BusinessException(errorResolver.getMessage("J0019",
                            (Object) "La fecha de vencimiento, o la fecha de reporto"));

				}

			}

		}

		if (!params.getClaveReporto().equalsIgnoreCase(
				Constantes.TRASPASO_DE_FONDEO)) {

			/*
			 * Reglas de negocio para clave_reporto diferente de F - Fondeo
			 * cuenta propia
			 */

			/* Solo entran los tv's que esten en Instrumento */
			if(emisionRegistrada) {
				if (!emision.tieneTvValida()
						|| (instrumentoDaliDao.getInstrumento(emision.getEmisionPk()
								.getTv().trim()) == null)) {

					throw new BusinessException(errorResolver.getMessage("J0090"));

				}
			}

			// Estas son las validaciones de tv que estan en la forma de unifase
			/*
			 * Validacion de la forma de uniface
			 * 
			 * if ($$mercado = 'PG') selectdb (count(tv)) from "instrumentos"
			 * u_where (tv.instrumentos = tv.emisiones & uso.instrumentos =
			 * 'dine' & clase_ins = 'BM' & (excepcion.instrumentos = 0 |
			 * excepcion.instrumentos = 1)) to $90 else selectdb (count(tv))
			 * from "instrumentos" u_where (tv.instrumentos = tv.emisiones &
			 * uso.instrumentos = 'dine' & clase_ins != 'BM') to $90 endif if
			 * ($90 < 1) help "TIPO DE VALOR INVALIDO" clear/e "emisiones"
			 * tv.emisiones = "" return(-1)
			 * 
			 */

			/*
			 * Se valida el uso del instrumento. La emision y el instrumento ya
			 * fueron validados por lo que se asume que no son null
			 */
			if(emisionRegistrada) {
				if (((emision.getInstrumento().getUso() != null) && !emision
						.getInstrumento().getUso().trim().equalsIgnoreCase(
								Constantes.DINERO))
								|| (((emision.getInstrumento().getClaseIns() != null) && !emision
										.getInstrumento().getClaseIns().trim()
										.equalsIgnoreCase(Constantes.CLAVE_CLASE_INS_BM))
										&& (emision.getInstrumento().getMercado() != null) && !emision
										.getInstrumento().getMercado().trim()
										.equalsIgnoreCase(PAPEL_BANCARIO))) {

					throw new BusinessException(errorResolver.getMessage("J0091"));

				}
			}

			/*
			 * Se valida el mercado del instrumento. La emision y el instrumento
			 * ya fueron validados por lo que se asume que no son null
			 */
			if(emisionRegistrada) {
				if (((emision.getInstrumento().getMercado() != null) && emision
						.getInstrumento().getMercado().trim().equalsIgnoreCase(
								PAPEL_GUBERNAMENTAL))
								&& ((emision.getInstrumento().getExcepcion() != null)
										&& (emision.getInstrumento().getExcepcion()
												.intValue() != 0) && (emision
														.getInstrumento().getExcepcion().intValue() != 1))) {

					throw new BusinessException(errorResolver.getMessage("J0092"));

				}
			}

			// validacion de tipo de papel
			/*
			 * Validacion de la forma de uniface
			 * 
			 * if (clase_ins.instrumentos != 'BM' & (clave_reporto.traspasos =
			 * 'D' | clave_reporto.traspasos = 'D' | clave_reporto.traspasos =
			 * 'C')) help "ESTE TIPO DE OPERACION SOLO PARA PAPEL GUBERNAMENTAL"
			 * clave_reporto.traspasos = "" return(-1) endif
			 * 
			 */
			if(emisionRegistrada) {
				if (((emision != null) && (emision.getInstrumento() != null) && (emision
						.getInstrumento().getClaseIns() != null))
						&& !emision.getInstrumento().getClaseIns().trim()
							.equalsIgnoreCase(Constantes.CLAVE_CLASE_INS_BM)
							&& (params.getClaveReporto().equalsIgnoreCase(
									Constantes.CLAVE_REPORTO_D) || params
									.getClaveReporto().equalsIgnoreCase(
											Constantes.CLAVE_REPORTO_C))) {

					throw new BusinessException(errorResolver.getMessage("J0093"));

				}
			}

			/*
			 * Validacion de la forma de uniface
			 * 
			 * if (clave_reporto.traspasos != 'T' & (tipo_cta.agente_comprador =
			 * 'DIRE' | tipo_cta.agentes = 'DIRE' | tipo_cta.agente_comprador =
			 * 'COBE' | tipo_cta.agentes = 'COBE' | tipo_cta.agente_comprador =
			 * 'CBPR' |tipo_cta.agentes = 'CBPR' | tipo_cta.agente_comprador =
			 * 'CBTE' | tipo_cta.agentes = 'CBTE' |tipo_cta.agente_comprador =
			 * 'LIBR' | tipo_cta.agentes = 'LIBR' |tipo_cta.agente_comprador =
			 * 'MDER' | tipo_cta.agentes = 'MDER')) help "LAS CUENTAS ESPECIALES
			 * NO ENTRAN AL ESQUEMA DE LIQUIDACION" clave_reporto.traspasos = ""
			 * $prompt = clave_reporto.traspasos return(-1)
			 * 
			 */
			if (!params.getClaveReporto().equalsIgnoreCase(
					Constantes.CLAVE_REPORTO_T)
					&& !params.getClaveReporto().equalsIgnoreCase(
							Constantes.VENTA)
					&& !params.getClaveReporto().equalsIgnoreCase(
							Constantes.CLAVE_REPORTO_OP_PRIMARIA_RECOMPRA)
					&& (agenteComprador.getTipoCta().equalsIgnoreCase(
							Constantes.CLAVE_TIPO_CUENTA_DIRE)
							|| agenteComprador.getTipoCta().equalsIgnoreCase(
									Constantes.CLAVE_TIPO_CUENTA_LIBR)
							|| agenteVendedor.getTipoCta().equalsIgnoreCase(
									Constantes.CLAVE_TIPO_CUENTA_DIRE)
							|| agenteVendedor.getTipoCta().equalsIgnoreCase(
									Constantes.CLAVE_TIPO_CUENTA_LIBR)
							|| agenteVendedor.getTipoCta().equalsIgnoreCase(
									Constantes.CLAVE_TIPO_CUENTA_COBE)
							|| agenteVendedor.getTipoCta().equalsIgnoreCase(
									Constantes.CLAVE_TIPO_CUENTA_CBPR) || agenteVendedor
							.getTipoCta().equalsIgnoreCase(
									Constantes.CLAVE_TIPO_CUENTA_CBTE))) {

				throw new BusinessException(errorResolver.getMessage("J0094"));

			}

			/*
			 * Validacion de la forma de uniface if ((clave_reporto.traspasos =
			 * 'R' | clave_reporto.traspasos = 'Y') & excepcion.instrumentos !=
			 * 1 & $hits(emisiones) > 0) help "ESTE PAPEL NO ES REPORTABLE"
			 * clave_reporto.traspasos = "" $prompt = clave_reporto.traspasos
			 * return(-1)
			 */
			if(emisionRegistrada) {
				if (((emision != null) && (emision.getInstrumento() != null) && (emision
						.getInstrumento().getExcepcion() != null))
						&& !(emision.getInstrumento().getExcepcion().compareTo(
								new Integer(1)) == 0)
								&& (params.getClaveReporto().equalsIgnoreCase(
										Constantes.INICIO_DE_REPORTO) || params
										.getClaveReporto().equalsIgnoreCase(
												Constantes.CLAVE_REPORTO_Y))) {

					throw new BusinessException(errorResolver.getMessage("J0095"));

				}
			}

			if (Constantes.VENTA.equalsIgnoreCase(params.getClaveReporto()
					.trim())) {

				if (UtilsVOMercadoDinero.esCuentaEmision(agenteVendedor
						.getAgentePK().getCuenta())
						|| UtilsVOMercadoDinero.esCuentaEmision(agenteComprador
								.getAgentePK().getCuenta())) {

					throw new BusinessException(errorResolver
							.getMessage("J0033"));

				}

			}

			/*
			 * if (((id_inst.agentes != '01' & id_inst.agentes != '02' &
			 * id_inst.agentes != '13' ) | (id_inst.agente_comprador != '01' &
			 * id_inst.agente_comprador != '02' & id_inst.agente_comprador !=
			 * '13' & id_inst.agente_comprador != '17')) &
			 * clave_reporto.traspasos != 'T') help "SOLO OPERACIONES LIBRES DE
			 * PAGO PARA INSTIT. DIFERENTES A CB. Y BANCOS Y FIDEI.BANXICO"
			 * clave_reporto.traspasos = "" $prompt = id_inst.agente_comprador
			 * return(-1) endif
			 */			
			if (!params.getClaveReporto().equalsIgnoreCase(
					Constantes.CLAVE_REPORTO_T)
					&& ((!agenteVendedor.getAgentePK().getIdInst().equalsIgnoreCase(Constantes.CASAS_DE_BOLSA)
							&& !agenteVendedor.getAgentePK().getIdInst().equalsIgnoreCase(Constantes.BANCOS)
							&& !agenteVendedor.getAgentePK().getIdInst().equalsIgnoreCase(Constantes.AFORES)
							&& !agenteVendedor.getAgentePK().getIdInst().equalsIgnoreCase("19") 
							&& !agenteVendedor.getAgentePK().getIdInst().equalsIgnoreCase("03") 
							&& !agenteVendedor.getAgentePK().getIdInst().equalsIgnoreCase("07")
							&& !agenteVendedor.getAgentePK().getIdInst().equalsIgnoreCase(Constantes.FIDEICOMISO_BANCO_MEXICO) 
							&& !agenteVendedor.getAgentePK().getIdInst().equalsIgnoreCase(Constantes.INDEVAL)
							&& !agenteVendedor.getAgentePK().getIdInst().equalsIgnoreCase(Constantes.OPERADORAS_SOCIEDADES_INVERSION))
					|| (!agenteComprador.getAgentePK().getIdInst().equalsIgnoreCase(Constantes.CASAS_DE_BOLSA)
							&& !agenteComprador.getAgentePK().getIdInst().equalsIgnoreCase(Constantes.AFORES)
							&& !agenteComprador.getAgentePK().getIdInst().equalsIgnoreCase("19")
							&& !agenteComprador.getAgentePK().getIdInst().equalsIgnoreCase("03")
							&& !agenteComprador.getAgentePK().getIdInst().equalsIgnoreCase("07")
							&& !agenteComprador.getAgentePK().getIdInst().equalsIgnoreCase(Constantes.BANCOS)
							&& !agenteComprador.getAgentePK().getIdInst().equalsIgnoreCase(Constantes.FIDEICOMISO_BANCO_MEXICO)
							&& !agenteComprador.getAgentePK().getIdInst().equalsIgnoreCase(Constantes.IPAB) 
							&& !agenteComprador.getAgentePK().getIdInst().equalsIgnoreCase(Constantes.INDEVAL)
							&& !agenteComprador.getAgentePK().getIdInst().equalsIgnoreCase(Constantes.OPERADORAS_SOCIEDADES_INVERSION)))) {

				throw new BusinessException(errorResolver.getMessage("J0096"));

			}

			if ((emision != null) && !emision.esReportable()) {

				throw new BusinessException(errorResolver.getMessage("J0097"));

			}

			if (params.getFechaReporto() == null) {

				/* Se asigna a la fecha reporto la fecha de regreso */
				params.setFechaReporto(params.getFechaRegreso());

			}

			/*
			 * Se coloca la C de confirmacion, solo si la clave_reporto es
			 * diferente de T
			 */
			if (!params.getClaveReporto().equalsIgnoreCase(
					Constantes.CLAVE_REPORTO_T)) {

				params.setSociedad(Constantes.SOCIEDAD_CONFIRMADA);

			} else {

				params.setSociedad(Constantes.SOCIEDAD_PENDIENTE_DE_CONFIRMAR);

			}

		} else {

			/*
			 * Reglas de negocio para clave_reporto igual a F - Fondeo cuenta
			 * propia
			 */

			/* Se valida que la tenencia del agente sea PROP o TERC */
			if (!agenteComprador.getTipoCta().trim().startsWith(
					Constantes.CUENTA_PROPIA)
					&& !agenteComprador.getTipoCta().trim().startsWith(
							Constantes.CUENTA_TERCEROS)) {

				throw new BusinessException(errorResolver.getMessage("J0098"));

			}

			/*
			 * Se valida que la cuenta del traspasante y la del receptor no sean
			 * iguales
			 */
			if (agenteVendedor.getAgentePK().getCuenta().equals(
					agenteComprador.getAgentePK().getCuenta())) {

				throw new BusinessException(errorResolver.getMessage("J0099"));

			}

			/* Se coloca la P de pendiente */
			params.setSociedad(Constantes.SOCIEDAD_PENDIENTE_DE_CONFIRMAR);

		}
		//Valida las cuentas de CCV
		validarCCV(params);
		//Realiza la validacion de control de cuentas de emision
		//Mayo/2018 - Pablo Balderas
		validadorCuentasEmision.validarCuentasEmisionMercadoDinero(params.getTraspasante(), params.getReceptor(), params.getEmision());
		//Obtiene el folio de la operacion.
		return utilService.getFolio(SECUENCIA_FOLIO_CONTROL).toString();
	}
	
	/**
	 * Metodo que valida que si el traspasante o el receptor es la CCV(25001) solo opere con la cuenta de terceros 0103
	 */
	private void validarCCV(RegistraOperacionParams params) throws BusinessException {
		//Obtiene el id - folio del traspasante y del receptor
		String traspasante = params.getTraspasante().getId().trim() + params.getTraspasante().getFolio().trim();
		String receptor = params.getReceptor().getId().trim() + params.getReceptor().getFolio().trim();;
		//Obtiene la cuenta traspasante y receptora
		String cuentaTraspasante = params.getTraspasante().getCuenta().trim();
		String cuentaReceptor = params.getReceptor().getCuenta().trim();
		if(MercadosConstantes.OPERACION_T.equals(params.getClaveReporto())) {
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

	/**
	 * Valida (y recupera de ser necesario) el instrumento para la emision
	 * recibida
	 * 
	 * @param emision
	 * @return EmisionPersistence recibida con el instrumento recuperado
	 * @throws BusinessException
	 */
	private EmisionPersistence buscaInstrumento(EmisionPersistence emision)
			throws BusinessException {

		logger.info("Entrando a MercadoDineroServiceImpl.buscaInstrumento()");

		return UtilsDaliVO.parseEmisionVO2Emision(catalogoService
				.buscaInstrumento(UtilsDaliVO.getEmisionVO(emision)));

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
	 * Inyeccion del bean fasesAbiertas
	 * 
	 * @param fasesAbiertas
	 */
	public void setFasesAbiertas(Map fasesAbiertas) {

		this.fasesAbiertas = fasesAbiertas;

	}

	

	/**
	 * Inyeccion del bean BusinessRulesCatalogoService
	 * 
	 * @param businessRulesCatalogoService
	 */
	public void setBusinessRulesCatalogoService(
			BusinessRulesCatalogoService businessRulesCatalogoService) {

		this.businessRulesCatalogoService = businessRulesCatalogoService;

	}

	/**
	 * Inyeccion del bean BusinessRulesMercadoDineroService
	 * 
	 * @param businessRulesMercadoDineroService
	 */
	public void setBusinessRulesMercadoDineroService(
			BusinessRulesMercadoDineroService businessRulesMercadoDineroService) {

		this.businessRulesMercadoDineroService = businessRulesMercadoDineroService;

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
	 * Inyeccion del bean utilService
	 * 
	 * @param utilService
	 */
	public void setUtilService(UtilServices utilService) {

		this.utilService = utilService;

	}

	/**
	 * Inyeccion del bean catalogoService
	 * 
	 * @param catalogoService
	 *            the catalogoService to set
	 */
	public void setCatalogoService(CatalogoService catalogoService) {

		this.catalogoService = catalogoService;

	}

	/**
	 * Inyeccion del bean diaInhabilDaliDao
	 * 
	 * @param diaInhabilDaliDao
	 *            the diaInhabilDaliDao to set
	 */
	public void setDiaInhabilDao(DiaInhabilDaliDao diaInhabilDaliDao) {

		this.diaInhabilDaliDao = diaInhabilDaliDao;

	}

	/**
	 * Inyeccion del bean dateUtilsDao
	 * 
	 * @param dateUtilsDao
	 *            the dateUtilsDao to set
	 */
	public void setDateUtilsDao(DateUtilsDao dateUtilsDao) {

		this.dateUtilsDao = dateUtilsDao;

	}

	/**
	 * @param instrumentoDaliDao
	 *            the instrumentoDaliDao to set
	 */
	public void setInstrumentoDao(InstrumentoDaliDao instrumentoDaliDao) {
		this.instrumentoDaliDao = instrumentoDaliDao;
	}
	
	/**
	 * Obtiene el mercado a partir del TV proporcionado
	 * 
	 * @param tv
	 * 
	 * @return String
	 */
	private String getMercado(String tv) {

		logger.info("Entrando a EnvioOperacionesServiceImpl.getMercado()");

		InstrumentoVO instrumento = UtilsVOCatalogo
				.getInstanceInstrumento(instrumentoDaliDao.getInstrumento(tv));

		if (instrumento == null) {

			return null;

		}
		if (StringUtils.isNotBlank(instrumento.getMercado())) {

			if (instrumento.getMercado().equalsIgnoreCase("PB")
					|| instrumento.getMercado().equalsIgnoreCase("PG")) {

				return "MD";

			}

		}

		return instrumento.getMercado();

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
	 * Metodo para establecer el atributo validadorCuentasEmision
	 * @param validadorCuentasEmision El valor del atributo validadorCuentasEmision a establecer.
	 */
	public void setValidadorCuentasEmision(ValidadorCuentasEmision validadorCuentasEmision) {
		this.validadorCuentasEmision = validadorCuentasEmision;
	}

}
