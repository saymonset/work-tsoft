/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.util.util.Constantes;
import com.indeval.portaldali.middleware.services.util.vo.BitacoraMatchParams;
import com.indeval.portaldali.middleware.services.util.vo.BitacoraMatchVO;
import com.indeval.portaldali.middleware.services.util.vo.BitacoraVO;
import com.indeval.portaldali.middleware.services.util.vo.BitacoraVOParams;
import com.indeval.portaldali.middleware.services.util.vo.BitacoraVOTotales;
import com.indeval.portaldali.middleware.services.util.vo.TradingInVO;
import com.indeval.portaldali.middleware.services.util.vo.TradingOutVO;
import com.indeval.portaldali.persistence.dao.common.BitacoraOperacionesDaliDao;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;
import com.indeval.portaldali.persistence.dao.protocolofinanciero.ProtocoloFinancieroDao;
import com.indeval.portaldali.persistence.util.DateUtil;
import com.indeval.portaldali.persistence.vo.BitacoraMatchParamsPersistence;
import com.indeval.portaldali.persistence.vo.BitacoraMatchVOPersistence;
import com.indeval.portaldali.persistence.vo.BitacoraOperacionDaoParams;
import com.indeval.portaldali.persistence.vo.PageVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Implementacion de los servicios de BitacoraOperacionesSybase
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 * @author salvador
 */
public class BitacoraOperacionesServiceImpl implements BitacoraOperacionesService {

	/** Log de clase. */
	private Logger log = LoggerFactory.getLogger(BitacoraOperacionesServiceImpl.class);

	/** interfaz para utilService */
	private UtilServices utilService;

	// /** interfaz para bitacoraOperacionesSybaseDao */
	// private BitacoraOperacionesSybaseDao bitacoraOperacionesSybaseDao;

	/** Acceso a protocoloFinancieroDao */
	private ProtocoloFinancieroDao protocoloFinancieroDao;

	/** Bean de acceso a DateUtilsDao */
	private DateUtilsDao dateUtilsDao;

	/** Resolvedor de Mensajes */
	private MessageResolver errorResolver;

	/** bean para bitacoraOperacionesDaliDao */
	private BitacoraOperacionesDaliDao bitacoraOperacionesDaliDao;

	/** Lista para los tipos de operaci&oacute;n */
	private List<String> listaTiposOperacion;

	/**
	 * @see com.indeval.portaldali.middleware.services.util.BitacoraOperacionesService#getMensajeBitacora(com.indeval.portaldali.middleware.services.util.vo.BitacoraVOParams)
	 */
	@SuppressWarnings("unchecked")
	public BitacoraVOTotales getMensajeBitacora(BitacoraVOParams bitacoraVO) throws BusinessException {

		log.info("Entrando a BitacoraOperacionesServiceImpl.getMensajeBitacora()");

		/* Se efectuan las validaciones de los parametros recibidos */
		utilService.validaDTONoNulo(bitacoraVO, "");

		bitacoraVO.validateAttributes();

		/* Se construye el params a enviar a persistence */
		BitacoraOperacionDaoParams params = new BitacoraOperacionDaoParams();

		/* Se settea el agentePK */
		params.setAgenteFirmado(UtilsDaliVO.getAgentePK(new AgenteVO(bitacoraVO.getIdTrasp(), bitacoraVO.getFolioTrasp())));

		/* Se settea la emisionPK */
		if (bitacoraVO.getEmisionVO() != null) {

			params.setEmisionPK(UtilsDaliVO.parseEmisionVO2EmisionPK(bitacoraVO.getEmisionVO()));

		}

		/* Se settea el resto de los filtros recibidos */
		if (StringUtils.isNotBlank(bitacoraVO.getReferenciaMensaje())) {

			params.setReferenciaMensaje(bitacoraVO.getReferenciaMensaje().trim());

		}

		if (bitacoraVO.getFechaLiquidacion() != null) {

			params.setFechaLiquidacion(DateUtil.preparaIntervaloFechas(bitacoraVO.getFechaLiquidacion(), bitacoraVO.getFechaLiquidacion()));

		}

		if (bitacoraVO.getFechaConcertacion() != null) {

			params.setFechaConcertacion(DateUtil.preparaIntervaloFechas(bitacoraVO.getFechaConcertacion(), bitacoraVO.getFechaConcertacion()));

		}

		if (StringUtils.isNotBlank(bitacoraVO.getEstatusRegistro())) {

			params.setEstatusRegistro(bitacoraVO.getEstatusRegistro().trim());

		}

		/*
		 * Se el construye el PageVO a partir del PaginaVO recibido y se efectua
		 * la consulta
		 */
		PageVO pageVO = UtilsDaliVO.getPageVO(bitacoraVO.getPaginaVO());

		if (bitacoraVO.getBanderaOracle()) {

			// pageVO = bitacoraOperacionesDaliDao.getBitacoras(params, pageVO);

		} else {

			// pageVO = bitacoraOperacionesSybaseDao.getBitacoras(params,
			// pageVO);
		}

		/* Se verifica la obtencion de los recursos */
		if (pageVO.getRegistros() == null || pageVO.getRegistros().isEmpty()) {
			throw new BusinessException(errorResolver.getMessage("J9999"));
		}

		Iterator it = pageVO.getRegistros().iterator();
		List<BitacoraVO> listaBitacoraVO = new ArrayList<BitacoraVO>();
		int countEnviados = 0;
		int countACK = 0;
		int countNAK = 0;
		int countNE = 0;

		while (it.hasNext()) {

			Object[] bo = (Object[]) it.next();

			if (StringUtils.isNotBlank((String) bo[26])) {

				if (Constantes.ESTATUS_ENVIADO.equalsIgnoreCase(((String) bo[26]).trim())) {

					countEnviados++;

				}

				if (Constantes.ESTATUS_ACK.equalsIgnoreCase(((String) bo[26]).trim())) {

					countACK++;

				}

				if (Constantes.ESTATUS_NAK.equalsIgnoreCase(((String) bo[26]).trim())) {

					countNAK++;

				}

				if (Constantes.ESTATUS_NE.equalsIgnoreCase(((String) bo[26]).trim())) {

					countNE++;

				}

			}

			listaBitacoraVO.add(bitacoraVO.getBanderaOracle() ? this.objectToBitacoravoOracle(bo) : this.objectToBitacoraVO(bo));

		}

		BitacoraVOTotales bitacoraVOTotales = new BitacoraVOTotales();

		// si se invoca para retornar una paginaVO esta bandera va en true
		if (bitacoraVO.getBanderaPaginaVO()) {

			pageVO.setTotalRegistros(new Integer(pageVO.getTotalRegistros().intValue()));

		}

		pageVO.setRegistros(listaBitacoraVO);
		bitacoraVOTotales.setPaginaVO(UtilsDaliVO.getPaginaVO(pageVO));
		bitacoraVOTotales.setTotalesACK(new BigInteger(String.valueOf(countACK)));
		bitacoraVOTotales.setTotalesEnviados(new BigInteger(String.valueOf(countEnviados)));
		bitacoraVOTotales.setTotalesNAK(new BigInteger(String.valueOf(countNAK)));
		bitacoraVOTotales.setTotalesNE(new BigInteger(String.valueOf(countNE)));

		return bitacoraVOTotales;

	}

	/**
	 * Metodo utilizado para convertir de un arreglo de objeto a un BitacoraVO
	 * 
	 * @param item
	 *            arreglo de objeto.
	 * 
	 * @return BitacoraVO
	 */
	private BitacoraVO objectToBitacoraVO(Object[] item) {

		log.info("Entrando al metodo BitacoraOperacionesServiceImpl.objectToBitacoraVO(item)");

		BitacoraVO bitacoraVO = null;

		if (item != null) {

			bitacoraVO = new BitacoraVO();
			bitacoraVO.setReferenciaMensaje((String) item[0]);
			bitacoraVO.setFechaConcertacion((Date) item[1]);
			bitacoraVO.setIdTrasp((String) item[2]);
			bitacoraVO.setFolioTrasp((String) item[3]);
			bitacoraVO.setCuentaTrasp((String) item[4]);
			bitacoraVO.setIdRecep((String) item[5]);
			bitacoraVO.setFolioRecep((String) item[6]);
			bitacoraVO.setCuentaRecep((String) item[7]);
			bitacoraVO.setTv((String) item[8]);
			bitacoraVO.setEmisora((String) item[9]);
			bitacoraVO.setSerie((String) item[10]);
			bitacoraVO.setCupon((String) item[11]);
			bitacoraVO.setCantidadTitulos((BigInteger) item[12]);
			bitacoraVO.setTipoInstruccion((String) item[13]);
			bitacoraVO.setFolioControl((Integer) item[14]);
			bitacoraVO.setMonto((BigDecimal) item[15]);
			bitacoraVO.setPrecio((BigDecimal) item[16]);
			bitacoraVO.setDivisa((String) item[17]);
			bitacoraVO.setFechaLiquidacion((Date) item[18]);
			bitacoraVO.setFechaVencimiento((Date) item[19]);
			bitacoraVO.setTasaNegociada((BigDecimal) item[20]);
			bitacoraVO.setTasaFija((((Integer) item[21] == null) || (((Integer) item[21]).intValue() == 0)) ? new Boolean(false) : new Boolean(true));
			bitacoraVO.setTasaReferencia((BigDecimal) item[22]);
			bitacoraVO.setIdFolioCtaPuente((String) item[23]);
			bitacoraVO.setFechaRegistro((Date) item[24]);
			bitacoraVO.setUsuario((String) item[25]);
			bitacoraVO.setEstatusRegistro((String) item[26]);
			bitacoraVO.setOrigenRegistro((String) item[27]);
			bitacoraVO.setCodigoError((String) item[28]);
			bitacoraVO.setReferenciaOperacion((String) item[31]);
			bitacoraVO.setMercado((String) item[34]);

			bitacoraVO.setMarcaCompra((Integer) item[35] != null && Constantes.UNO_INT == ((Integer) item[35]).intValue() ? Constantes.DESC_MENSAJEC
					: Constantes.DESC_MENSAJEV);

		}

		return bitacoraVO;

	}

	/**
	 * 
	 * @see com.indeval.portaldali.middleware.services.util.BitacoraOperacionesService#getMensajeBitacoraMatch(com.indeval.portaldali.middleware.services.util.vo.BitacoraMatchParams)
	 */
	public BitacoraMatchVO[] getMensajeBitacoraMatch(BitacoraMatchParams bitacoraVO) throws BusinessException {

		log.info("Entrando a BitacoraOperacionesServiceImpl.getMensajeBitacoraMatch()");

		/* Se valida el objeto de parametro recibido */
		this.validaBitacoraMatchParams(bitacoraVO);

		/* Se valida combinaciones de busqueda invalidas */
		if (combinacionesBusquedaInvalidas(bitacoraVO)) {
			throw new BusinessException(errorResolver.getMessage("J9999"));
		}

		/* Se parsea el objeto params en un paramsPersistence */
		BitacoraMatchParamsPersistence bitacoraMatchParamsPersistence = parseBitacoraMatchParams2BitacoraMatchParamsPersistence(bitacoraVO);

		/* Se recuperan los registros de la bitacora */
		List listaRegistrosBitacoraMatchPersistence = null;
		try {
			listaRegistrosBitacoraMatchPersistence = protocoloFinancieroDao.getBitacoraMatch(bitacoraMatchParamsPersistence);
		} catch (CannotGetJdbcConnectionException cannotGetJdbcConnectionException) {
			throw new BusinessException(errorResolver.getMessage("A0001"));
		}

		/* Se verifica la lista de registros */
		if ((listaRegistrosBitacoraMatchPersistence == null) || listaRegistrosBitacoraMatchPersistence.isEmpty()) {

			throw new BusinessException(errorResolver.getMessage("J9999"));

		}

		log.debug("El Dao retorno [" + listaRegistrosBitacoraMatchPersistence.size() + "] registros de la Bitacora del Match");

		/* Se convierte la lista de VOsPersistence a una lista de VOs */
		List<BitacoraMatchVO> listaRegistrosBitacoraMatch = parseBitacoraMatchVOPersistence2BitacoraMatchVO(listaRegistrosBitacoraMatchPersistence);

		/*
		 * Se construye el arreglo de BitacoraMatchVO y se llena con los objetos
		 * de la lista
		 */
		List<BitacoraMatchVO> listaRegistrosBitacoraMatchPermisos = this.determinaPermisos(listaRegistrosBitacoraMatch, bitacoraVO.getAgenteFirmado());

		BitacoraMatchVO[] bitacoraMatchVOs = (BitacoraMatchVO[]) listaRegistrosBitacoraMatchPermisos
				.toArray(new BitacoraMatchVO[listaRegistrosBitacoraMatchPermisos.size()]);
		log.debug("El servicio envia [" + bitacoraMatchVOs.length + "] registros de la Bitacora del Match");

		return bitacoraMatchVOs;

	}

	/**
	 * Valida las combinaciones de busqueda invalidas
	 * 
	 * @param bitacoraVO
	 * @return boolean
	 */
	private boolean combinacionesBusquedaInvalidas(BitacoraMatchParams bitacoraVO) {

		log.info("Entrando a BitacoraOperacionesServiceImpl.combinacionesBusquedaInvalidas()");

		if (bitacoraVO.getRemitidos() != null && bitacoraVO.getRemitidos().booleanValue() && StringUtils.isNotBlank(bitacoraVO.getTipoMensaje())
				&& bitacoraVO.getTipoMensaje().equalsIgnoreCase(Constantes.TMSJ_541) && StringUtils.isNotBlank(bitacoraVO.getRol())
				&& bitacoraVO.getRol().equalsIgnoreCase(Constantes.TRASPASANTE)) {

			return true;
		}
		if (bitacoraVO.getRemitidos() != null && bitacoraVO.getRemitidos().booleanValue() && StringUtils.isNotBlank(bitacoraVO.getTipoMensaje())
				&& bitacoraVO.getTipoMensaje().equalsIgnoreCase(Constantes.TMSJ_543) && StringUtils.isNotBlank(bitacoraVO.getRol())
				&& bitacoraVO.getRol().equalsIgnoreCase(Constantes.RECEPTOR)) {

			return true;
		}
		return false;
	}

	/**
	 * Encapsula la logica de validacion del params utilizado en la consulta
	 * Bitacora Match
	 * 
	 * @param bitacoraVO
	 * 
	 * @throws BusinessException
	 */
	private void validaBitacoraMatchParams(BitacoraMatchParams bitacoraVO) throws BusinessException {

		log.info("Entrando a BitacoraOperacionesServiceImpl.validaBitacoraMatchParams()");

		utilService.validaDTONoNulo(bitacoraVO, "");
		utilService.validaAgente(bitacoraVO.getAgenteFirmado(), "firmado", false);
		log.debug("AgentePersistence Firmado = [" + bitacoraVO.getAgenteFirmado().toResumeString() + "]");
		bitacoraVO.validaRol();
		log.debug("Rol = [" + bitacoraVO.getRol() + "]");

	}

	/**
	 * Convierte un BitacoraMatchParams en un BitacoraMatchParamsPersistence
	 * 
	 * @param params
	 * 
	 * @return BitacoraMatchParamsPersistence
	 */
	private BitacoraMatchParamsPersistence parseBitacoraMatchParams2BitacoraMatchParamsPersistence(BitacoraMatchParams params) {

		log.info("Entrando a BitacoraOperacionesServiceImpl." + "parseBitacoraMatchParams2BitacoraMatchParamsPersistence()");

		BitacoraMatchParamsPersistence paramsPersistence = new BitacoraMatchParamsPersistence();

		paramsPersistence.setIdTraspasante(params.getAgenteFirmado().getId());
		paramsPersistence.setFolioTraspasante(params.getAgenteFirmado().getFolio());
		paramsPersistence.setCuentaTraspasante(params.getAgenteFirmado().getCuenta());

		if (params.getContraparte() != null) {
			paramsPersistence.setIdReceptor(params.getContraparte().getId());
			paramsPersistence.setFolioReceptor(params.getContraparte().getFolio());
			paramsPersistence.setCuentaReceptor(params.getContraparte().getCuenta());
		}

		// paramsPersistence.setEmision(UtilsDaliVO.parseEmisionVO2Emision(params
		// .getEmisionVO()));
		paramsPersistence.setMercado(params.getMercado());
		paramsPersistence.setCantidadTitulos(params.getCantidadTitulos());
		paramsPersistence.setFolioUsuario(params.getFolioUsuario());

		paramsPersistence.setCodigoOperacion(params.getCodigoOperacion());
		paramsPersistence.setExpira(params.getExpira());
		paramsPersistence.setFecha(params.getFecha());
		paramsPersistence.setFechaHoraRecepcion(params.getFechaHoraRecepcion());
		paramsPersistence.setFechaLiquidacion(params.getFechaLiquidacion());
		paramsPersistence.setFolioInstruccionReceptor(params.getFolioInstruccionReceptor());
		paramsPersistence.setFolioInstruccionTraspasante(params.getFolioInstruccionTraspasante());
		paramsPersistence.setFolioMatch(params.getFolioMatch());
		paramsPersistence.setIdBitacora(params.getIdBitacora());
		paramsPersistence.setIdBitacoraMatch(params.getIdBitacoraMatch());
		paramsPersistence.setInstancia(params.getInstancia());
		paramsPersistence.setMatchKey(params.getMatchKey());
		paramsPersistence.setTipoMensaje(params.getTipoMensaje());
		paramsPersistence.setTipoOperacion(params.getTipoOperacion());
		paramsPersistence.setRemitidos(params.getRemitidos());
		paramsPersistence.setRol(params.getRol());

		return paramsPersistence;

	}

	/**
	 * Convierte una lista de BitacoraMatchVOsPersistence en una lista de
	 * BitacoraMatchVOs
	 * 
	 * @param listaRegistrosBitacoraMatchPersistence
	 * 
	 * @return List La lista de BitacoraMatchVO
	 */
	@SuppressWarnings("unchecked")
	private List<BitacoraMatchVO> parseBitacoraMatchVOPersistence2BitacoraMatchVO(List listaRegistrosBitacoraMatchPersistence) {

		log.info("Entrando a BitacoraOperacionesServiceImpl.parseBitacoraMatchVOPersistence2BitacoraMatchVO()");

		List<BitacoraMatchVO> listaRegistrosBitacoraMatch = new ArrayList<BitacoraMatchVO>();

		for (Iterator iter = listaRegistrosBitacoraMatchPersistence.iterator(); iter.hasNext();) {

			BitacoraMatchVOPersistence bitacoraMatchVOPersistence = (BitacoraMatchVOPersistence) iter.next();
			BitacoraMatchVO bitacoraMatchVO = parseBitacoraMatchVOPersistence2BitacoraMatchVO(bitacoraMatchVOPersistence);

			if (bitacoraMatchVO != null) {

				listaRegistrosBitacoraMatch.add(bitacoraMatchVO);

			}

		}

		return listaRegistrosBitacoraMatch;

	}

	/**
	 * Convierte un objeto de BitacoraMatchVOPersistence en un objeto de
	 * BitacoraMatchVO
	 * 
	 * @param bitacoraMatchVOPersistence
	 *            El objeto BitacoraMatchVOPersistence a parsear
	 * 
	 * @return BitacoraMatchVO El obejto BitacoraMatchVO construido a partir del
	 *         BitacoraMatchVOPersistence recibido
	 */
	private BitacoraMatchVO parseBitacoraMatchVOPersistence2BitacoraMatchVO(BitacoraMatchVOPersistence bitacoraMatchVOPersistence) {

		log.info("Entrando a BitacoraOperacionesServiceImpl." + "parseBitacoraMatchVOPersistence2BitacoraMatchVO()");

		BitacoraMatchVO bitacoraMatchVO = null;
		Integer enviada = UtilsDaliVO.INTEGER_ZERO;

		if (bitacoraMatchVOPersistence != null) {

			bitacoraMatchVO = new BitacoraMatchVO();
			bitacoraMatchVO.setIdBitacoraMatch(bitacoraMatchVOPersistence.getIdBitacoraMatch());

			TradingInVO vo = (TradingInVO) this.getDetalleOperacion(bitacoraMatchVOPersistence.getMensaje());

			try {
				enviada = new Integer(bitacoraMatchVOPersistence.getEnviada());
			} catch (NumberFormatException e) {
				log.debug("El valor [" + bitacoraMatchVOPersistence.getEnviada() + "] no es numerico");
				e.printStackTrace();
			}

			bitacoraMatchVO.setEnviada(enviada);
			bitacoraMatchVO.setMensaje(parseTradingInVO2TradingOutVO(vo));
			bitacoraMatchVO.setEstaConfirmada(bitacoraMatchVOPersistence.getConfirmacion());
			bitacoraMatchVO.setEstaCancelada(bitacoraMatchVOPersistence.getCancelacion());

		}

		return bitacoraMatchVO;

	}

	/**
	 * Convierte un objeto de TradingInVO en un objeto de TradingOutVO
	 * 
	 * @param vo
	 * 
	 * @return TradingOutVO
	 */
	private TradingOutVO parseTradingInVO2TradingOutVO(TradingInVO vo) {

		log.info("Entrando a BitacoraOperacionesServiceImpl.parseTradingInVO2TradingOutVO()");

		TradingOutVO tradingOutVO = new TradingOutVO();

		if (vo != null) {

			tradingOutVO.setCantidadTitulosOp((vo.getCantidadTitulosOp() != null) ? new BigInteger(vo.getCantidadTitulosOp()) : UtilsDaliVO.BIG_INTEGER_ZERO);
			tradingOutVO.setCuentaComprador(vo.getCuentaComprador());
			tradingOutVO.setCuentaVendedora(vo.getCuentaVendedora());
			tradingOutVO.setCupon(vo.getCupon());
			tradingOutVO.setDiasPlazo(vo.getDiasPlazo());
			tradingOutVO.setDivisa(vo.getDivisa());
			tradingOutVO.setEmisora(vo.getEmisora());
			tradingOutVO.setEstado(vo.getEstado());
			tradingOutVO.setFechaConcertacion(vo.getFechaConcertacion());
			tradingOutVO.setFechaHoraRegistro(vo.getFechaHoraRegistro());
			tradingOutVO.setFechaLiquidacion(vo.getFechaLiquidacion());
			tradingOutVO.setFechaReporto(vo.getFechaReporto());
			tradingOutVO.setFolioControl(vo.getFolioControl());
			tradingOutVO.setFolioInstitucionCompradora(vo.getFolioInstitucionCompradora());
			tradingOutVO.setFolioInstitucionVendedora(vo.getFolioInstitucionVendedora());
			tradingOutVO.setFolioUsuario(vo.getFolioUsuario());
			tradingOutVO.setFolioUsuarioReceptor(vo.getFolioUsuarioReceptor());
			tradingOutVO.setFolioUsuarioTraspasante(vo.getFolioUsuarioTraspasante());
			tradingOutVO.setIdFolioCargo(vo.getIdFolioCargo());
			tradingOutVO.setIdInstitucionCompradora(vo.getIdInstitucionCompradora());
			tradingOutVO.setIdInstitucionVendedora(vo.getIdInstitucionVendedora());
			tradingOutVO.setImporte((vo.getImporte() != null) ? new BigDecimal(vo.getImporte()) : UtilsDaliVO.BIG_DECIMAL_ZERO);
			tradingOutVO.setMatchRequeridoId(vo.getMatchRequeridoId());
			tradingOutVO.setOrigenTransac(vo.getOrigenTransac());

			if (vo.getParticipanteVO() != null) {

				tradingOutVO.setIdInstitucionRemitente(vo.getParticipanteVO().getIdInstitucion());
				tradingOutVO.setFolioInstitucionRemitente(vo.getParticipanteVO().getFolioInstitucion());

			}

			tradingOutVO.setPrecioTitulo((vo.getPrecioTitulo() != null) ? new BigDecimal(vo.getPrecioTitulo()) : UtilsDaliVO.BIG_DECIMAL_ZERO);
			tradingOutVO.setReferenciaParticipante(vo.getReferenciaParticipante());
			tradingOutVO.setSerie(vo.getSerie());
			tradingOutVO.setStatus(vo.getStatus());
			tradingOutVO.setTasaNegociada((vo.getTasaNegociada() != null) ? new BigDecimal(vo.getTasaNegociada()) : UtilsDaliVO.BIG_DECIMAL_ZERO);
			tradingOutVO.setTasaReferencia((vo.getTasaReferencia() != null) ? new BigDecimal(vo.getTasaReferencia()) : UtilsDaliVO.BIG_DECIMAL_ZERO);
			tradingOutVO.setTipoMensaje(vo.getTipoMensaje());
			tradingOutVO.setTipoOperacion(vo.getTipoOperacion());
			tradingOutVO.setTipoValor(vo.getTipoValor());

		}

		return tradingOutVO;

	}

	/**
	 * 
	 * @see com.indeval.portaldali.middleware.services.util.BitacoraOperacionesService#getDetalleOperacion(java.lang.String)
	 */
	public Object getDetalleOperacion(String inMessage) {

		log.info("Entrando a BitacoraOperacionesServiceImpl.getDetalleOperacion()");

		log.debug(inMessage);

		XStream xstream = new XStream(new DomDriver());
		xstream.autodetectAnnotations(true);		
		xstream.processAnnotations(TradingInVO.class);

		TradingInVO vo = null;

		if (StringUtils.isNotBlank(inMessage)) {

			try {

				vo = (TradingInVO) xstream.fromXML(inMessage);

			} catch (Exception e) {

				e.printStackTrace();

			}

		}

		return vo;

	}

	/**
	 * Metodo que sirve para realizar la iteracion por cada objeto
	 * BitacoraMatchVO.
	 * 
	 * @param listaBitacoraMatchVO
	 *            lista de los objetos.
	 * @param agenteFirmado
	 *            parametros de consulta.
	 * 
	 * @return List<BitacoraMatchVO>
	 */
	private List<BitacoraMatchVO> determinaPermisos(List<BitacoraMatchVO> listaBitacoraMatchVO, AgenteVO agenteFirmado) {

		log.info("Entrando a BitacoraOperacionesServiceImpl.determinaPermisos()");

		Iterator it = listaBitacoraMatchVO.iterator();
		List<BitacoraMatchVO> listaBitacoraMatch = new ArrayList<BitacoraMatchVO>();

		while (it.hasNext()) {

			BitacoraMatchVO bitacoraMatchVO = (BitacoraMatchVO) it.next();
			TradingOutVO tradingOutVO = (TradingOutVO) bitacoraMatchVO.getMensaje();
			listaBitacoraMatch.add(this.determinarPermisos(tradingOutVO, agenteFirmado, bitacoraMatchVO));

		}

		return listaBitacoraMatch;

	}

	/**
	 * @see com.indeval.portaldali.middleware.services.util.BitacoraOperacionesService#verificaPermisosConfirmacionCancelacion(com.indeval.portaldali.middleware.services.util.vo.TradingInVO,
	 *      com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      com.indeval.portaldali.middleware.services.util.vo.BitacoraMatchVO)
	 */
	public BitacoraMatchVO verificaPermisosConfirmacionCancelacion(TradingInVO tradingInVO, AgenteVO agenteFirmado, BitacoraMatchVO bitacoraMatchVO) {

		log.info("Entrando a BitacoraOperacionesServiceImpl.verificaPermisosConfirmacion()");

		TradingOutVO tradingOutVO = this.parseTradingInVO2TradingOutVO(tradingInVO);
		log.debug("AgentePersistence Firmado: " + agenteFirmado);
		bitacoraMatchVO = determinarPermisos(tradingOutVO, agenteFirmado, bitacoraMatchVO);

		return bitacoraMatchVO;

	}

	/**
	 * Metodo que sirve para determinar los permisos por cada BitacoraMatchVO.
	 * 
	 * @param bitacoraMatchVO
	 *            parametros de entrada para realizar validaciones.
	 * @param agenteFirmado
	 * @param bitacoraMatchVO
	 *            el objeto al cual se le asignan los permisos
	 * 
	 * @return BitacoraMatchVO
	 */
	private BitacoraMatchVO determinarPermisos(TradingOutVO tradingOutVO, AgenteVO agenteFirmado, BitacoraMatchVO bitacoraMatchVO) {

		log.info("Entrando a BitacoraOperacionesServiceImpl.determinarPermisos()");

		AgenteVO agenteVendedor = new AgenteVO(tradingOutVO.getIdInstitucionVendedora(), tradingOutVO.getFolioInstitucionVendedora());
		AgenteVO agenteComprador = new AgenteVO(tradingOutVO.getIdInstitucionCompradora(), tradingOutVO.getFolioInstitucionCompradora());

		// validando que no este cancelada ni confirmada

		if (bitacoraMatchVO.getEstaConfirmada() != null && !bitacoraMatchVO.getEstaConfirmada() && bitacoraMatchVO.getEstaCancelada() != null
				&& !bitacoraMatchVO.getEstaCancelada()) {

			if (tradingOutVO.getFechaLiquidacion() != null && !tradingOutVO.getFechaLiquidacion().before(dateUtilsDao.getFechaHoraCero(dateUtilsDao.getDateFechaDB()))) {

				if (tradingOutVO != null) {

					if (Constantes.TMSJ_541.equals(tradingOutVO.getTipoMensaje())) {

						if ( agenteFirmado.equalsSinCuenta(agenteComprador) &&
								agenteFirmado.equalsSinCuenta(agenteVendedor) ) {
							
							bitacoraMatchVO.setPuedeConfirmar(Boolean.TRUE);
							bitacoraMatchVO.setPuedeCancelar(Boolean.TRUE);
						} else if (agenteFirmado.equalsSinCuenta(agenteVendedor)) {

							bitacoraMatchVO.setPuedeConfirmar(Boolean.TRUE);
						} else {

							bitacoraMatchVO.setPuedeCancelar(Boolean.TRUE);
						}

					} else if (Constantes.TMSJ_543.equals(tradingOutVO.getTipoMensaje())) {

						if ( agenteFirmado.equalsSinCuenta(agenteComprador) &&
								agenteFirmado.equalsSinCuenta(agenteVendedor) ) {
							
							bitacoraMatchVO.setPuedeConfirmar(Boolean.TRUE);
							bitacoraMatchVO.setPuedeCancelar(Boolean.TRUE);
							bitacoraMatchVO.setPuedeExpirar(Boolean.TRUE);
						} else if (agenteFirmado.equalsSinCuenta(agenteComprador)) {

							bitacoraMatchVO.setPuedeConfirmar(Boolean.TRUE);
							bitacoraMatchVO.setPuedeExpirar(Boolean.TRUE);
						} else {

							bitacoraMatchVO.setPuedeCancelar(Boolean.TRUE);
							bitacoraMatchVO.setPuedeExpirar(Boolean.TRUE);
						}
					} else if (Constantes.TMSJ_540.equals(tradingOutVO.getTipoMensaje())) {
						if (listaTiposOperacion.contains(tradingOutVO.getTipoOperacion())) {
							if ( agenteFirmado.equalsSinCuenta(agenteComprador) &&
									agenteFirmado.equalsSinCuenta(agenteVendedor) ) {
								
								bitacoraMatchVO.setPuedeConfirmar(Boolean.TRUE);
								bitacoraMatchVO.setPuedeCancelar(Boolean.TRUE);
							} else if (agenteFirmado.equalsSinCuenta(agenteVendedor)) {

								bitacoraMatchVO.setPuedeConfirmar(Boolean.TRUE);
							} else {

								bitacoraMatchVO.setPuedeCancelar(Boolean.TRUE);
							}
						}
					} else if (Constantes.TMSJ_542.equals(tradingOutVO.getTipoMensaje())) {
						if (listaTiposOperacion.contains(tradingOutVO.getTipoOperacion())) {
							if ( agenteFirmado.equalsSinCuenta(agenteComprador) &&
									agenteFirmado.equalsSinCuenta(agenteVendedor) ) {
								
								bitacoraMatchVO.setPuedeConfirmar(Boolean.TRUE);
								bitacoraMatchVO.setPuedeCancelar(Boolean.TRUE);
								bitacoraMatchVO.setPuedeExpirar(Boolean.TRUE);
							} else if (agenteFirmado.equalsSinCuenta(agenteComprador)) {

								bitacoraMatchVO.setPuedeConfirmar(Boolean.TRUE);
								bitacoraMatchVO.setPuedeExpirar(Boolean.TRUE);
							} else {

								bitacoraMatchVO.setPuedeCancelar(Boolean.TRUE);
								bitacoraMatchVO.setPuedeExpirar(Boolean.TRUE);
							}
						}
					}
					log.debug("El mensaje viene nulo o vacio");

				}

			}

		}

		return bitacoraMatchVO;

	}

	/**
	 * 
	 * @see com.indeval.portaldali.middleware.services.util.BitacoraOperacionesService#tradingInVO2TraspasoContraPago(com.indeval.portaldali.middleware.services.util.vo.TradingInVO)
	 */
	public TraspasoContraPagoVO tradingInVO2TraspasoContraPago(TradingInVO tradingInVO, Boolean esConfirmacion) {

		log.info("Entrando al método tradingInVO2TraspasoContraPago");

		TraspasoContraPagoVO traspasoContraPagoVO = new TraspasoContraPagoVO();

		if (tradingInVO != null) {

			traspasoContraPagoVO.setBoveda(tradingInVO.getBoveda());
			traspasoContraPagoVO.setBovedaEfectivo(tradingInVO.getBovedaEfectivo());
			traspasoContraPagoVO.setCantidadTitulos((tradingInVO.getCantidadTitulosOp() != null) ? new Long(tradingInVO.getCantidadTitulosOp()) : null);
			traspasoContraPagoVO.setCupon(tradingInVO.getCupon());
			traspasoContraPagoVO.setDivisa(tradingInVO.getDivisa());
			traspasoContraPagoVO.setEmisora(tradingInVO.getEmisora());

			/*
			 * String cadenaFecha_yyyyMMdd = tradingInVO.getFechaConcertacion();
			 * SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			 * Date fechaConcertacion = null;
			 * 
			 * try {
			 * 
			 * fechaConcertacion = dateFormat.parse(cadenaFecha_yyyyMMdd); }
			 * catch (ParseException e) {
			 * 
			 * log .debug("Excepcion de formato de fecha: " +
			 * e.getStackTrace()); }
			 */

			traspasoContraPagoVO.setFechaConcertacion(dateUtilsDao.getFechaHoraCero(dateUtilsDao.getDateFechaDB()));
			traspasoContraPagoVO.setFechaLiquidacion(tradingInVO.getFechaLiquidacion());

			Date fechaHoraRegistro = null;

			SimpleDateFormat dateFormatHoraRegistro = new SimpleDateFormat("yyyyMMdd hhmmss");

			String cadenaFecha_yyyyMMddhhmmss = tradingInVO.getFechaHoraRegistro();

			try {

				fechaHoraRegistro = dateFormatHoraRegistro.parse(cadenaFecha_yyyyMMddhhmmss);

			} catch (ParseException e) {

				log.debug("Excepcion de formato de fecha: ", e);

			}

			traspasoContraPagoVO.setFechaRegistro(fechaHoraRegistro);
			//traspasoContraPagoVO.setFuncionMensaje(tradingInVO.getFuncionDelMensaje());
			traspasoContraPagoVO.setISIN(tradingInVO.getIsin());
			if( StringUtils.isNotBlank(tradingInVO.getTipoOperacion()) ) {
				if( tradingInVO.getTipoOperacion().trim().equalsIgnoreCase("T")) {
					traspasoContraPagoVO.setPrecio(BigDecimal.valueOf(0));
					traspasoContraPagoVO.setMonto(BigDecimal.valueOf(0));
				} else {
			traspasoContraPagoVO.setPrecio(new BigDecimal(tradingInVO.getPrecioTitulo()));
					traspasoContraPagoVO.setMonto(new BigDecimal(tradingInVO.getImporte()));
				}
			}
			traspasoContraPagoVO.setSerie(tradingInVO.getSerie());
			traspasoContraPagoVO.setTasaNegociada((tradingInVO.getTasaNegociada() != null) ? new Double(tradingInVO.getTasaNegociada()) : new Double(0));
			traspasoContraPagoVO.setTasaReferencia((tradingInVO.getTasaReferencia() != null) ? new Double(tradingInVO.getTasaReferencia()) : new Double(0));
			traspasoContraPagoVO.setTipoMensaje(tradingInVO.getTipoMensaje());
			traspasoContraPagoVO.setTipoValor(tradingInVO.getTipoValor());
			

			traspasoContraPagoVO.setIdFolioCtaPuente(null);
			traspasoContraPagoVO.setMatch(null);
			traspasoContraPagoVO.setNuevo(null);

			traspasoContraPagoVO.setIdFolioCtaReceptora(tradingInVO.getIdInstitucionCompradora() + tradingInVO.getFolioInstitucionCompradora()
					+ tradingInVO.getCuentaComprador());

			traspasoContraPagoVO.setIdFolioCtaTraspasante(tradingInVO.getIdInstitucionVendedora() + tradingInVO.getFolioInstitucionVendedora()
					+ tradingInVO.getCuentaVendedora());

			if (esConfirmacion) {
				traspasoContraPagoVO.setReferenciaRelacionada(null);
				traspasoContraPagoVO.setReferenciaOperacion(null);
				traspasoContraPagoVO.setTipoInstruccion(tradingInVO.getTipoOperacion());
			} else {
				// Referencia Relacionada
				traspasoContraPagoVO.setReferenciaRelacionada(StringUtils.isNotBlank(tradingInVO.getReferenciaParticipante()) ? tradingInVO
						.getReferenciaParticipante() : null);
				// Referencia Operacion
				traspasoContraPagoVO.setReferenciaOperacion(StringUtils.isNotBlank(tradingInVO.getFolioUsuario()) ? tradingInVO.getFolioUsuario() : null);
				// Se valida que la referencia operacion y la referencia
				// relacionada no sean nulas
				if (StringUtils.isBlank(traspasoContraPagoVO.getReferenciaOperacion())) {
					throw new BusinessException(errorResolver.getMessage("J0110"));
				}
				// Tipo Instruccion
				traspasoContraPagoVO.setTipoInstruccion(tradingInVO.getTipoOperacion().concat(Constantes.OPERACION_CANCELADA));
			}

			traspasoContraPagoVO.setReferenciaMensaje(null);

			if (StringUtils.isNotBlank(tradingInVO.getTipoOperacion())) {

				if (tradingInVO.getTipoOperacion().equalsIgnoreCase("R")) {

					traspasoContraPagoVO.setTasaFija(Boolean.TRUE);

				} else {

					traspasoContraPagoVO.setTasaFija(null);

				}

				// si se trata de una venta, no hay fecha de vencimiento
				if (tradingInVO.getTipoOperacion().equalsIgnoreCase("V")) {

					traspasoContraPagoVO.setFechaVencimiento(null);

				} else {

					traspasoContraPagoVO.setFechaVencimiento(tradingInVO.getFechaReporto());

				}

			}

		}

		return traspasoContraPagoVO;

	}

	/**
	 * @see com.indeval.portaldali.middleware.services.util.BitacoraOperacionesService#actualizaEdoInsExpira(java.math.BigInteger,
	 *      com.indeval.portaldali.middleware.services.modelo.AgenteVO, boolean)
	 * @deprecated
	 */
	public int actualizaEdoInsExpira(BigInteger idBitacoraMatch, AgenteVO agenteFirmado, boolean operacion) throws BusinessException {

		log.info("Entrando a BitacoraOperacionesServiceImpl.actualizaEdoInsExpira()");

		int actualiza = protocoloFinancieroDao.actualizaEdoInsExpira(idBitacoraMatch, operacion, UtilsDaliVO.getAgentePK(agenteFirmado));

		if (actualiza < 1) {

			throw new BusinessException(errorResolver.getMessage("J0043"));

		}

		return actualiza;

	}

	/**
	 * Metodo utilizado para convertir de un arreglo de objeto a un BitacoraVO
	 * 
	 * @param item
	 *            arreglo de objeto.
	 * 
	 * @return BitacoraVO
	 */
	private BitacoraVO objectToBitacoravoOracle(Object[] item) {

		log.info("Entrando a BitacoraOperacionesServiceImpl.objectToBitacoravoOracle()");

		BitacoraVO bitacoraVO = null;

		if (item != null) {

			bitacoraVO = new BitacoraVO();
			bitacoraVO.setReferenciaMensaje((String) item[0]);
			bitacoraVO.setFechaConcertacion((Date) item[1]);
			bitacoraVO.setIdTrasp((String) item[2]);
			bitacoraVO.setFolioTrasp((String) item[3]);
			bitacoraVO.setCuentaTrasp((String) item[4]);
			bitacoraVO.setIdRecep((String) item[5]);
			bitacoraVO.setFolioRecep((String) item[6]);
			bitacoraVO.setCuentaRecep((String) item[7]);
			bitacoraVO.setTv((String) item[8]);
			bitacoraVO.setEmisora((String) item[9]);
			bitacoraVO.setSerie((String) item[10]);
			bitacoraVO.setCupon((String) item[11]);
			bitacoraVO.setCantidadTitulos((BigInteger) item[12]);
			bitacoraVO.setTipoInstruccion((String) item[13]);
			bitacoraVO.setFolioControl((Integer) item[14]);
			bitacoraVO.setMonto((BigDecimal) item[15]);
			bitacoraVO.setPrecio((BigDecimal) item[16]);
			bitacoraVO.setDivisa((String) item[17]);
			bitacoraVO.setFechaLiquidacion((Date) item[18]);
			bitacoraVO.setFechaVencimiento((Date) item[19]);
			bitacoraVO.setTasaNegociada((BigDecimal) item[20]);
			bitacoraVO.setTasaFija((((Integer) item[21] == null) || (((Integer) item[21]).intValue() == 0)) ? new Boolean(false) : new Boolean(true));
			bitacoraVO.setTasaReferencia((BigDecimal) item[22]);
			bitacoraVO.setIdFolioCtaPuente((String) item[23]);
			bitacoraVO.setFechaRegistro((Date) item[24]);
			bitacoraVO.setUsuario((String) item[25]);
			bitacoraVO.setEstatusRegistro((String) item[26]);
			bitacoraVO.setOrigenRegistro((String) item[27]);
			bitacoraVO.setCodigoError((String) item[28]);
			bitacoraVO.setReferenciaOperacion((String) item[31]);
			bitacoraVO.setMercado((String) item[34]);

			bitacoraVO.setMarcaCompra((Integer) item[35] != null && Constantes.UNO_INT == ((Integer) item[35]).intValue() ? Constantes.DESC_MENSAJEC
					: Constantes.DESC_MENSAJEV);

			bitacoraVO.setIdBitacoraOperaciones((BigDecimal) item[36]);

		}

		return bitacoraVO;

	}

	/**
	 * @see com.indeval.portaldali.middleware.services.util.BitacoraOperacionesService#tradingInVO2TraspasoLibrePago(com.indeval.portaldali.middleware.services.util.vo.TradingInVO)
	 */
	public TraspasoLibrePagoVO tradingInVO2TraspasoLibrePago(TradingInVO tradingInVO, Boolean esConfirmacion) throws BusinessException {
		log.info("Entrando al m\u00e9todo tradingInVO2TraspasoLibrePago");

		TraspasoLibrePagoVO traspasoLibrePagoVO = new TraspasoLibrePagoVO();

		if (tradingInVO != null) {
			traspasoLibrePagoVO.setBoveda(tradingInVO.getBoveda()); 
			traspasoLibrePagoVO.setCantidadTitulos(tradingInVO.getCantidadTitulosOp() != null ? new Long(tradingInVO.getCantidadTitulosOp()) : new Long("0"));
			traspasoLibrePagoVO.setCargoParticipante(null);
			traspasoLibrePagoVO.setCupon(tradingInVO.getCupon());
			traspasoLibrePagoVO.setEmisora(tradingInVO.getEmisora());
			traspasoLibrePagoVO.setFechaLiquidacion(tradingInVO.getFechaLiquidacion());
			traspasoLibrePagoVO.setFechaRegistro(dateUtilsDao.getDateFechaDB());
			traspasoLibrePagoVO.setIdFolioCtaPuente(null);
			traspasoLibrePagoVO.setIdFolioCtaReceptora(tradingInVO.getIdInstitucionCompradora() + tradingInVO.getFolioInstitucionCompradora()
					+ tradingInVO.getCuentaComprador());
			traspasoLibrePagoVO.setIdFolioCtaTraspasante(tradingInVO.getIdInstitucionVendedora() + tradingInVO.getFolioInstitucionVendedora()
					+ tradingInVO.getCuentaVendedora());
			traspasoLibrePagoVO.setISIN(tradingInVO.getIsin());
			traspasoLibrePagoVO.setMatch(null);
			traspasoLibrePagoVO.setNuevo(null);
			traspasoLibrePagoVO.setReferenciaMensaje(null);
			traspasoLibrePagoVO.setReferenciaOperacion(tradingInVO.getFolioUsuario());
			traspasoLibrePagoVO.setSerie(tradingInVO.getSerie());
			if (esConfirmacion) {
				traspasoLibrePagoVO.setReferenciaRelacionada(null);
				traspasoLibrePagoVO.setTipoInstruccion(tradingInVO.getTipoOperacion());
			} else {
				// Referencia Relacionada
				traspasoLibrePagoVO.setReferenciaRelacionada(StringUtils.isNotBlank(tradingInVO.getReferenciaParticipante()) ? tradingInVO
						.getReferenciaParticipante() : null);
				// Referencia Operacion
				traspasoLibrePagoVO.setReferenciaOperacion(StringUtils.isNotBlank(tradingInVO.getFolioUsuario()) ? tradingInVO.getFolioUsuario() : null);
				// Se valida que la referencia relacionada ni la referencia
				// operacion sean nulas
				if (StringUtils.isBlank(traspasoLibrePagoVO.getReferenciaRelacionada()) || StringUtils.isBlank(traspasoLibrePagoVO.getReferenciaOperacion())) {
					throw new BusinessException(errorResolver.getMessage("J0110"));
				}
				// Tipo Instruccion
				traspasoLibrePagoVO.setTipoInstruccion(tradingInVO.getTipoOperacion().concat(Constantes.OPERACION_CANCELADA)); 
			}

			traspasoLibrePagoVO.setTipoMensaje(tradingInVO.getTipoMensaje());
			traspasoLibrePagoVO.setTipoValor(tradingInVO.getTipoValor());
			if (Constantes.TIPO_OPERACION_M.equalsIgnoreCase(tradingInVO.getTipoOperacion())) {
				traspasoLibrePagoVO.setPrecioAdquisicion(new BigDecimal(tradingInVO.getPrecioAdquisicion()));
				traspasoLibrePagoVO.setRfcCurp(tradingInVO.getRfcCurp());
				traspasoLibrePagoVO.setCliente(tradingInVO.getCliente());
				traspasoLibrePagoVO.setFechaAdquisicion(tradingInVO.getFechaAdquisicion());
				traspasoLibrePagoVO.setCostoFiscalActualizado(tradingInVO.getCostoPromedio() != null ? new BigDecimal(tradingInVO.getCostoPromedio()) : null);
			}
		}
		return traspasoLibrePagoVO;
	}

	/**
	 * @param utilService
	 *            the utilService to set
	 */
	public void setUtilService(UtilServices utilService) {

		this.utilService = utilService;

	}

	/**
	 * @param errorResolver
	 *            the errorResolver to set
	 */
	public void setErrorResolver(MessageResolver errorResolver) {

		this.errorResolver = errorResolver;

	}

	/**
	 * @param protocoloFinancieroDao
	 *            the protocoloFinancieroDao to set
	 */
	public void setProtocoloFinancieroDao(ProtocoloFinancieroDao protocoloFinancieroDao) {

		this.protocoloFinancieroDao = protocoloFinancieroDao;

	}

	// /**
	// * @param bitacoraOperacionesSybaseDao the bitacoraOperacionesSybaseDao to
	// set
	// */
	// public void setBitacoraOperacionesSybaseDao(
	// BitacoraOperacionesSybaseDao bitacoraOperacionesSybaseDao) {
	// this.bitacoraOperacionesSybaseDao = bitacoraOperacionesSybaseDao;
	// }

	/**
	 * @param bitacoraOperacionesDaliDao
	 *            the bitacoraOperacionesDaliDao to set
	 */
	public void setBitacoraOperacionesDao(BitacoraOperacionesDaliDao bitacoraOperacionesDaliDao) {
		this.bitacoraOperacionesDaliDao = bitacoraOperacionesDaliDao;
	}

	/**
	 * @param dateUtilsDao
	 *            the dateUtilsDao to set
	 */
	public void setDateUtilsDao(DateUtilsDao dateUtilsDao) {
		this.dateUtilsDao = dateUtilsDao;
	}

	public List<String> getListaTiposOperacion() {
		return listaTiposOperacion;
	}

	public void setListaTiposOperacion(List<String> listaTiposOperacion) {
		this.listaTiposOperacion = listaTiposOperacion;
	}

}
