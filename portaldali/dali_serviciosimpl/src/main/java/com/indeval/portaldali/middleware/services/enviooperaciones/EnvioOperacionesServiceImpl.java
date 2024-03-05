/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.enviooperaciones;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.lob.ClobImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaRetiroInternacionalDTO;
import com.indeval.portaldali.middleware.dto.DepositoDivisaDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoInternacionalDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.services.enviooperaciones.util.Constantes;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessDataException;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.middleware.services.modelo.util.UtilsVOCatalogo;
import com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService;
import com.indeval.portaldali.middleware.services.tesoreria.cuentas.AdministracionCuentasRetiroService;
import com.indeval.portaldali.middleware.services.util.BitacoraOperacionesService;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.middleware.services.util.UtilsDaliVO;
import com.indeval.portaldali.middleware.services.util.vo.BitacoraMatchParams;
import com.indeval.portaldali.middleware.services.util.vo.BitacoraMatchVO;
import com.indeval.portaldali.middleware.services.util.vo.BitacoraVOParams;
import com.indeval.portaldali.middleware.services.util.vo.BitacoraVOTotales;
import com.indeval.portaldali.middleware.services.util.vo.TradingInVO;
import com.indeval.portaldali.persistence.dao.common.BitacoraOperacionesDaliDao;
import com.indeval.portaldali.persistence.dao.common.BovedaDaliDAO;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;
import com.indeval.portaldali.persistence.dao.common.InstitucionDaliDAO;
import com.indeval.portaldali.persistence.dao.common.InstrumentoDaliDao;
import com.indeval.portaldali.persistence.dao.protocolofinanciero.RegistroInstruccionesMatch2Dao;
import com.indeval.portaldali.persistence.model.BitacoraOperaciones;
import com.indeval.portaldali.persistence.model.Boveda;
import com.indeval.portaldali.persistence.model.Divisa;
import com.indeval.portaldali.persistence.model.Institucion;
import com.indeval.portaldali.persistence.model.protocolofinanciero.RegistroInstruccionesMatch2;
import com.indeval.portaldali.persistence.vo.InstrumentoVO;
import com.indeval.protocolofinanciero.api.JmsClient;
import com.indeval.protocolofinanciero.api.ProtocoloFinancieroException;
import com.indeval.protocolofinanciero.api.service.Efectivo;
import com.indeval.protocolofinanciero.api.service.TraspasoContraPago;
import com.indeval.protocolofinanciero.api.service.TraspasoLibrePago;
import com.indeval.protocolofinanciero.api.vo.BaseTlpDvpVO;
import com.indeval.protocolofinanciero.api.vo.RetiroEfectivoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoEfectivoFvVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoEfectivoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

/**
 * Implementacion de los servicios de Envio Operaciones
 *
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class EnvioOperacionesServiceImpl implements EnvioOperacionesService, Constantes {

	private AdministracionCuentasRetiroService admonCuentasRetiroEfectivoService;


	/** Define CANCELADO */
	private static final String CANCELADO = "CANCELADO";

	/** Define CANCPORTAL */
	private static final String CANCPORTAL = "CANCPORTAL";

	private TesoreriaService tesoreriaService = null;

	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(EnvioOperacionesServiceImpl.class);

	/** Define COLA_PORTAL */
	private static int COLA_PORTAL = 1;

	/** Bean para acceso a las utilerias de los servicios */
	private UtilServices utilService;

	/** Bean para acceso al properties de mensaje de errores */
	private MessageResolver errorResolver;

	/** Bean de acceso a DateUtilsDao */
	private DateUtilsDao dateUtilsDao;

	/** Bean para acceso al cliente de jms */
	private JmsClient jmsClient;

	/** Bean para acceso al template de jms */
	private JmsTemplate jmsTemplate;

	/** Bean para acceso al servicio de traspasos contra pago */
	private TraspasoContraPago dvpService;

	/** Bean para acceso al servicio de traspasos libres de pago */
	private TraspasoLibrePago tlpService;

	// /**
	// * Bean para acceso a la bitacora de las operaciones
	// * bddinero..bitacora_operaciones
	// */
	// private BitacoraOperacionesSybaseDao bitacoraOperacionesSybaseDao;
	//
	// /**
	// * Bean para acceso a la bitacora de las operaciones
	// * bddinero..bitacora_operaciones con driver XA
	// */
	// private BitacoraOperacionesSybaseDao bitacoraOperacionesXADao;
	//
	// /** Bean para acceso a la consulta de Oracle para validar participantes
	// */
	// private ValidadorPFIDao validadorPFIDao;

	/** Define la cola para el env&iacute;o del mensaje */
	public String cola;

	/** Define la cola para el env&iacute;o del mensaje */
	public String cola2;

	/** Acceso a bitacoraOperacionesService */
	private BitacoraOperacionesService bitacoraOperacionesService;

	/** Acceso a instrumentoDaliDao */
	private InstrumentoDaliDao instrumentoDaliDao;

	/**
	 * Mapa para validar las fases en las que se permite la confirmaci&oacute;n
	 * de operaciones match
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private Map fasesValidasConfirmacion;

	/**
	 * Bean para acceso a la bitacora de las operaciones
	 */
	private BitacoraOperacionesDaliDao bitacoraOperacionesDaliDao;

	/** Bean para el acceso a registroInstruccionesMatch2Dao */
	private RegistroInstruccionesMatch2Dao registroInstruccionesMatch2Dao;

	/** Lista para los tipos de operaci&oacute;n */
	private List<String> listaTiposOperacion;

	/** Bean para acceso al servicio de operaciones de efectivo */
	private Efectivo efectivo;

	private InstitucionDaliDAO institucionDAO;

	private BovedaDaliDAO bovedaDao;

	/**
	 *
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#enviaOperacion(com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO,
	 *      String, boolean)
	 */
	public void enviaOperacion(TraspasoContraPagoVO vo, String folioControl, boolean isCompra) throws ProtocoloFinancieroException, BusinessException {

		log.info("Entrando a EnvioOperacionesServiceImpl.enviaOperacion()");

		vo.setReferenciaMensaje(getReferenciaMensaje());

		if (TIPO_INSTRUCCION_V.equalsIgnoreCase(vo.getTipoInstruccion().trim()) || TIPO_INSTRUCCION_J.equalsIgnoreCase(vo.getTipoInstruccion().trim())) {

			vo.setFechaVencimiento(null);
			vo.setTasaNegociada(null);
			vo.setTasaFija(null);

		}

		enviaOperacionComun(vo, folioControl, isCompra);

	}

	/**
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#enviaOperacion(com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO,
	 *      java.lang.String, boolean)
	 */
	public void enviaOperacion(TraspasoLibrePagoVO vo, String folioControl, boolean isCompra) throws ProtocoloFinancieroException, BusinessException {

		log.info("Entrando a EnvioOperacionesServiceImpl.enviaOperacion()");

		vo.setReferenciaMensaje(getReferenciaMensaje());
		enviaOperacionComun(vo, folioControl, isCompra);

	}

	/**
	 *
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#grabaOperacion(com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO,
	 *      String, boolean)
	 */
	public void grabaOperacion(RetiroEfectivoDTO vo, HashMap datosAdicionales, String origenRegistro,
			String isoFirmado) throws BusinessException {
        boolean isCompra = false;
		setBitacora(vo, vo.getReferenciaOperacion(), ESTATUS_NO_ENVIADO, true, isCompra, datosAdicionales, origenRegistro, isoFirmado);
	}

	/**
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#grabaOperacion(com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO, java.lang.String, boolean, java.util.HashMap, java.lang.String, java.lang.String)
	 */
	public void grabaOperacion(TraspasoContraPagoVO vo, String folioControl, boolean isCompra, HashMap datosAdicionales, String origenRegistro,
			String isoFirmado) throws BusinessException {

		log.info("Entrando a EnvioOperacionesServiceImpl.grabaOperacion()");

		vo.setReferenciaMensaje(StringUtils.isBlank(vo.getReferenciaMensaje()) ? getReferenciaMensaje() : vo.getReferenciaMensaje());
		vo.setReferenciaOperacion(StringUtils.isBlank(vo.getReferenciaOperacion()) ? folioControl : vo.getReferenciaOperacion());
		setBitacora(vo, folioControl, ESTATUS_NO_ENVIADO, true, isCompra, datosAdicionales, origenRegistro, isoFirmado);
	}

	/**
	 *
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#grabaOperacion(com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO,
	 *      String, boolean, HashMap, String)
	 */
	public void grabaOperacion(TraspasoLibrePagoVO vo, String folioControl, boolean isCompra, HashMap datosAdicionales, String origenRegistro, String isoFirmado)
			throws BusinessException {

		log.info("Entrando a EnvioOperacionesServiceImpl.grabaOperacion()");

		vo.setReferenciaMensaje(StringUtils.isBlank(vo.getReferenciaMensaje()) ? getReferenciaMensaje() : vo.getReferenciaMensaje());
		vo.setReferenciaOperacion(StringUtils.isBlank(vo.getReferenciaOperacion()) ? folioControl : vo.getReferenciaOperacion());
		setBitacora(vo, folioControl, ESTATUS_NO_ENVIADO, true, isCompra, datosAdicionales, origenRegistro, isoFirmado);
	}

	/**
	 *
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#grabaOperacion(com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO,
	 *      boolean)
	 */
	public BigInteger grabaOperacion(TraspasoContraPagoVO vo, boolean isCompra, HashMap datosAdicionales, String origenRegistro, String isoFirmado)
			throws BusinessException {

		log.info("Entrando a EnvioOperacionesServiceImpl.grabaOperacion()");

		String folioControl = "";

		if (MERCADO_DINERO.equalsIgnoreCase(getMercado(vo.getTipoValor()))) {

			folioControl = utilService.getFolio(SECUENCIA_FOLIO_CONTROL).toString();

		} else if (MERCADO_CAPITALES.equalsIgnoreCase(getMercado(vo.getTipoValor()))) {

			folioControl = utilService.getFolio(SECUENCIA_FOLIO_CONTROL).toString();

		} else {
			throw new BusinessException(errorResolver.getMessage("J0109"));
		}
		grabaOperacion(vo, folioControl, isCompra, datosAdicionales, origenRegistro, isoFirmado);

		return new BigInteger(folioControl);
	}

	/**
	 *
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#grabaOperacion(com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO,
	 *      boolean)
	 */
	public BigInteger grabaOperacion(TraspasoLibrePagoVO vo, boolean isCompra, HashMap datosAdicionales, String origenRegistro, String isoFirmado)
			throws BusinessException {

		log.info("Entrando a EnvioOperacionesServiceImpl.grabaOperacion()");

		BigInteger folioControl = utilService.getFolio(SECUENCIA_FOLIO_CONTROL);


//		if (MERCADO_DINERO.equalsIgnoreCase(getMercado(vo.getTipoValor()))) {
//			folioControl = utilService.getFolio(SECUENCIA_FOLIO_CONTROL).toString();
//		} else if (MERCADO_CAPITALES.equalsIgnoreCase(getMercado(vo.getTipoValor()))) {
//			folioControl = utilService.getFolio(SECUENCIA_FOLIO_CONTROL).toString();
//		} else {
//			throw new BusinessException(errorResolver.getMessage("J0109"));
//		}

		grabaOperacion(vo, folioControl !=null?folioControl.toString():null, isCompra, datosAdicionales, origenRegistro, isoFirmado);

		return folioControl;

	}

	/**
	 *
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#recibeOperacion()
	 */
	public void recibeOperacion() throws ProtocoloFinancieroException, BusinessException {

		/*
		 * Solo se utliza para realizar la prueba de recepcion de mensajes a
		 * traves de iniciaListener()
		 */
		log.info("Entrando a EnvioOperacionesServiceImpl.recibeOperacion()");

	}

	/**
	 *
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#enviaOperacionSimple(com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO,
	 *      java.lang.String, boolean)
	 */
	public void enviaOperacionSimple(TraspasoContraPagoVO vo, String folioControl, boolean isCompra) throws ProtocoloFinancieroException, BusinessException {

		log.info("Entrando a EnvioOperacionesServiceImpl.enviaOperacionSimple()");

		if (vo.getTipoInstruccion().equalsIgnoreCase(TIPO_INSTRUCCION_V)) {

			vo.setFechaVencimiento(null);
			vo.setTasaNegociada(null);
			vo.setTasaFija(null);

		}

		enviaOperacionProtocolo(vo, folioControl, COLA_PORTAL, isCompra);

	}

	/**
	 *
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#enviaOperacionSimple(com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO,
	 *      java.lang.String, boolean)
	 */
	public void enviaOperacionSimple(TraspasoLibrePagoVO vo, String folioControl, boolean isCompra) throws ProtocoloFinancieroException, BusinessException {

		log.info("Entrando a EnvioOperacionesServiceImpl.enviaOperacionSimple()");

		enviaOperacionProtocolo(vo, folioControl, COLA_PORTAL, isCompra);

	}

	// /**
	// *
	// * @see
	// com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#reenviaOperacion(com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO,
	// * java.lang.String)
	// */
	// public void reenviaOperacion(TraspasoContraPagoVO vo, String
	// folioControl, int cola,
	// boolean isCompra) throws ProtocoloFinancieroException, BusinessException
	// {
	//
	// log.info("Entrando a EnvioOperacionesServiceImpl.reenviaOperacion()");
	//
	// String referenciaMensaje = getReferenciaMensaje();
	// vo.setReferenciaMensaje(referenciaMensaje);
	// vo.setReferenciaOperacion(vo.getReferenciaOperacion() != null ?
	// vo.getReferenciaOperacion()
	// .trim() : null);
	// folioControl = folioControl != null ? folioControl.trim() : null;
	//
	// if (vo.getTipoInstruccion().equalsIgnoreCase(TIPO_INSTRUCCION_V)) {
	//
	// vo.setFechaVencimiento(null);
	// vo.setTasaNegociada(null);
	// vo.setTasaFija(null);
	//
	// }
	//
	// reenviaOperacionComun(vo, folioControl, referenciaMensaje,
	// vo.getFechaLiquidacion(),
	// getMercado(vo.getTipoValor()), cola, isCompra);
	//
	// }

	// /**
	// *
	// * @see
	// com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#reenviaOperacion(com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO,
	// * java.lang.String)
	// */
	// public void reenviaOperacion(TraspasoLibrePagoVO vo, String folioControl,
	// int cola,
	// boolean isCompra) throws ProtocoloFinancieroException, BusinessException
	// {
	//
	// log.info("Entrando a EnvioOperacionesServiceImpl.reenviaOperacion()");
	//
	// String referenciaMensaje = getReferenciaMensaje();
	// vo.setReferenciaMensaje(referenciaMensaje);
	// vo.setReferenciaOperacion(vo.getReferenciaOperacion() != null ?
	// vo.getReferenciaOperacion()
	// .trim() : null);
	// folioControl = folioControl != null ? folioControl.trim() : null;
	//
	// reenviaOperacionComun(vo, folioControl, referenciaMensaje,
	// vo.getFechaLiquidacion(),
	// getMercado(vo.getTipoValor()), cola, isCompra);
	//
	// }

	/**
	 *
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService
	 *      #getMensajeBitacora(com.indeval.portaldali.middleware.services.util.vo.BitacoraVOParams)
	 */
	public PaginaVO getMensajeBitacora(BitacoraVOParams bitacoraVO) throws BusinessException {

		log.info("Entrando a EnvioOperacionesServiceImpl.getMensajeBitacora()");

		bitacoraVO.setBanderaPaginaVO(Boolean.TRUE);

		return bitacoraOperacionesService.getMensajeBitacora(bitacoraVO).getPaginaVO();

	}

	// /**
	// *
	// * @see
	// com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService
	// *
	// #getMensajeBitacoraXEstatusRegistro(com.indeval.portaldali.middleware.services.util.vo.BitacoraVOParams)
	// */
	// public List getMensajeBitacoraXEstatusRegistro(String estatusRegistro,
	// String origen)
	// throws BusinessException {
	//
	// log.info("Entrando a
	// EnvioOperacionesServiceImpl.getMensajeBitacoraXEstatusRegistro()");
	//
	// return
	// bitacoraOperacionesSybaseDao.getMensajeBitacoraXEstatusRegistro(estatusRegistro,
	// dateUtilsDao.getFechaHoraCero(dateUtilsDao.getDateFechaDB()), origen);
	//
	// }

	/**
	 *
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService
	 *      #getTotalesBitacora(com.indeval.portaldali.middleware.services.util.vo.BitacoraVOParams)
	 */
	public BitacoraVOTotales getTotalesBitacora(BitacoraVOParams bitacoraVO) throws BusinessException {

		log.info("Entrando a EnvioOperacionesServiceImpl.getTotalesBitacora()");

		return bitacoraOperacionesService.getMensajeBitacora(bitacoraVO);

	}

	// /**
	// *
	// * @see
	// com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#validaInstitucionPFI(com.indeval.portaldali.middleware.services.modelo.AgenteVO)
	// */
	// public boolean validaInstitucionPFI(AgenteVO agenteFirmado) throws
	// BusinessException {
	//
	// log.info("Entrando a
	// EnvioOperacionesServiceImpl.validaInstitucionPFI()");
	//
	// utilService.validaDTONoNulo(agenteFirmado, " agente firmado");
	// agenteFirmado.tieneClaveValida();
	//
	// return validadorPFIDao.validarParticipantePFI(agenteFirmado.getId(),
	// agenteFirmado
	// .getFolio());
	//
	// }

	/**
	 * Envia al protocolo la operacion correspondiente al tipo de vo
	 * proporcionado
	 *
	 * @param vo
	 * @param folioControl
	 * @param cola
	 * @param isCompra
	 *            true - Indica que la operacion es una compra (para hacer
	 *            match)
	 *
	 * @throws ProtocoloFinancieroException
	 * @throws BusinessException
	 */
	private void enviaOperacionProtocolo(Object vo, String folioControl, int cola, boolean isCompra) throws ProtocoloFinancieroException, BusinessException {

		log.info("Entrando a EnvioOperacionesServiceImpl.enviaOperacionProtocolo()");

		// long init = System.currentTimeMillis();
		String isoSigned = getIsoSigned(vo, isCompra);
		String ticket = getTicket();
		sendCola(ticket, folioControl, isoSigned, cola);

		// long fin = System.currentTimeMillis();

	}

	/**
	 * Contiene el codigo comun a todos los envios del servicio sobrecargado
	 * enviaOperacion() para generar ref del mensaje y grabar en la bitacora de
	 * operaciones
	 *
	 * @param vo
	 * @param folioControl
	 * @param isCompra
	 *            true - Inidca que la operaci&oacute;n es una compra (para
	 *            hacer match)
	 *
	 * @throws ProtocoloFinancieroException
	 * @throws BusinessException
	 */
	private void enviaOperacionComun(Object vo, String folioControl, boolean isCompra) throws ProtocoloFinancieroException, BusinessException {

		log.info("Entrando a EnvioOperacionesServiceImpl.enviaOperacionComun()");

		try {

			setBitacora(vo, folioControl, ESTATUS_NO_ENVIADO, true, false, null, null, null);

			if (vo instanceof TraspasoContraPagoVO) {

				TraspasoContraPagoVO tcp = (TraspasoContraPagoVO) vo;
				System.out.println("antes envio --> " + tcp.getPrecio());

			}

			enviaOperacionProtocolo(vo, folioControl, COLA_PORTAL, isCompra);

			if (vo instanceof TraspasoContraPagoVO) {

				TraspasoContraPagoVO tcp = (TraspasoContraPagoVO) vo;
				System.out.println("depues envio --> " + tcp.getPrecio());

			}

			setBitacora(vo, folioControl, ESTATUS_ENVIADO, false, false, null, null, null);

		} catch (Exception e) {

			log.debug(e.getMessage());
			e.printStackTrace();
		}

	}

	// /**
	// * Contiene el codigo comun a todos los reenvios del servicio sobrecargado
	// * reenviaOperacion() solo actualiza la referencia del mensaje y al
	// estatus
	// * lo settea con EN
	// *
	// * @param vo
	// * @param folioControl
	// * @param referenciaMensaje
	// * @param fechaLiquidacion
	// * @param mercado
	// * @param cola
	// * @param isCompra
	// * true - Indica que la operacion es una compra (para hacer
	// * match)
	// *
	// * @throws ProtocoloFinancieroException
	// * @throws BusinessException
	// */
	// private void reenviaOperacionComun(Object vo, String folioControl, String
	// referenciaMensaje,
	// Date fechaLiquidacion, String mercado, int cola, boolean isCompra)
	// throws ProtocoloFinancieroException, BusinessException {
	//
	// log.info("Entrando a
	// EnvioOperacionesServiceImpl.reenviaOperacionComun()");
	//
	// try {
	//
	// enviaOperacionProtocolo(vo, folioControl, cola, isCompra);
	//
	// // SI NO SE ACTUALIZA REGISTROS SIMPLEMENTE NO SE ENVIAN
	// int numReg =
	// bitacoraOperacionesXADao.updateRefMensajeEstatus(referenciaMensaje,
	// fechaLiquidacion, new Integer(folioControl), mercado);
	//
	// // error no se actualizo el registro esto no dbe suceder el
	// // funcioamiento de esto esta basado en que el registro es unico
	// // por mercado , fecha de liquidacion y folio control
	// if (numReg != 1) {
	//
	// throw new BusinessException("ERROR NO SE ACTUALIZO REGISTRO "
	// + "POR LO TANTO NO SE ENVIA AL PFI LA OPERACION " + "[" +
	// fechaLiquidacion
	// + "] [" + folioControl + "] [" + mercado + "] numReg [" + numReg + "]");
	//
	// }
	// }
	// catch (Exception e) {
	//
	// log.debug(e.getMessage());
	// e.printStackTrace();
	// throw new BusinessException(e.getMessage(), e);
	//
	// }
	//
	// }

	/**
	 * Obtiene el consecutivo de la referencia del mensaje
	 *
	 * @return String El consecutivo de la referencia
	 * @throws BusinessException
	 */
	private String getReferenciaMensaje() throws BusinessException {

		log.info("Entrando a EnvioOperacionesServiceImpl.getReferenciaMensaje()");

		return utilService.getFolio(SEQ_REFERENCIA_MENSAJE).toString();

	}





	/**
	 * Obtiene el iso firmado
	 *
	 * @param vo
	 * @param isCompra
	 *            true - Inidica que la operacion es una compra (para hacer
	 *            match)
	 *
	 * @return String
	 *
	 * @throws ProtocoloFinancieroException
	 * @throws BusinessException
	 */
	private String getIsoSigned(Object vo, boolean isCompra) throws ProtocoloFinancieroException, BusinessException {

		log.info("Entrando a EnvioOperacionesServiceImpl.getIsoSigned()");

		String iso = null;

		if (vo instanceof TraspasoContraPagoVO) {

			if (!isCompra) {
				System.out.println("Enviando una venta... (TraspasoContraPagoVO)");
				iso = dvpService.entrega((TraspasoContraPagoVO) vo);
			} else {
				System.out.println("Enviando una compra... (TraspasoContraPagoVO)");
				iso = dvpService.recepcion((TraspasoContraPagoVO) vo);
			}

		} else if (vo instanceof TraspasoLibrePago) {

			if (!isCompra) {
				System.out.println("Enviando una venta... (TraspasoLibrePago)");
				iso = tlpService.entrega((TraspasoLibrePagoVO) vo);
			} else {
				System.out.println("Enviando una compra... (TraspasoLibrePago)");
				iso = tlpService.recepcion((TraspasoLibrePagoVO) vo);
			}

		}

		String certificateSerialNumber = jmsClient.getPrivateKeyProvider().getCertificateSerialNumber();
		PrivateKey privateKey = jmsClient.getPrivateKeyProvider().getPrivateKey();

		return jmsClient.signMessage(iso, privateKey, certificateSerialNumber);

	}

	/**
	 * Env&iacute;a el mensaje a la cola
	 *
	 * @param ticket
	 * @param folioControl
	 * @param isoSigned
	 * @param numCola
	 */
	private void sendCola(final String ticket, final String folioControl, final String isoSigned, final int numCola) {

		log.info("Entrando a EnvioOperacionesServiceImpl.sendCola()");

		StringBuffer nameCola = new StringBuffer(UtilsDaliVO.BLANK);

		switch (numCola) {

		case 1:
			nameCola.append(cola);
			break;

		default:
			nameCola.append(cola2);
			break;

		}

		jmsTemplate.send(nameCola.toString().trim(), new MessageCreator() {

			public Message createMessage(Session session) throws JMSException {

				TextMessage textMessage = session.createTextMessage();
				textMessage.setStringProperty("credencial", ticket);

				if (StringUtils.isNotBlank(folioControl)) {

					textMessage.setStringProperty("folioControl", folioControl);

				}

				textMessage.setText(isoSigned);

				return textMessage;

			}

		});

	}

	/**
	 * Obtiene el ticket del mensaje
	 *
	 * @return String
	 */
	private String getTicket() {

		log.info("Entrando a EnvioOperacionesServiceImpl.getTicket()");

		return jmsClient.getSecurityDelegate().getTicket();

	}

	/**
	 * Graba la bitacora de la operacion
	 *
	 * @param vo
	 * @param folioControl
	 * @param estatus
	 * @param isAlta
	 * @param isCompra
	 *            true - Inidica que la operacion es una compra (para match)
	 * @param datosAdicionales
	 * @param origenRegistro
	 * @param isoFirmado
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	private void setBitacora(Object vo, String folioControl, String estatus, Boolean isAlta, boolean isCompra, HashMap datosAdicionales, String origenRegistro,
			String isoFirmado) throws BusinessException {

		log.info("Entrando a EnvioOperacionesServiceImpl.setBitacora()");
		utilService.validaDTONoNulo(vo, " El vo ");
		utilService.validarClaveTipo(estatus, " estatus ");

		BitacoraOperaciones bitacoraOperaciones = new BitacoraOperaciones();
		//------
		BigInteger idFolioBitacoraOperaciones = utilService.getFolio(SEQ_BITACORA_OPERACIONES);
		bitacoraOperaciones.setIdBitacoraOperaciones(idFolioBitacoraOperaciones);
		//---------
		Date fechaActual = dateUtilsDao.getDateFechaDB();
		//---------

		bitacoraOperaciones.setFolioControl(new BigInteger(folioControl != null ? folioControl : CERO_STRING));
		bitacoraOperaciones.setOrigenRegistro(ORIGEN_OPERACION_PORTAL);
		bitacoraOperaciones.setFechaHoraAlta(fechaActual);
		bitacoraOperaciones.setEstatusRegistro(estatus);
		bitacoraOperaciones.setMarcaCompra(isCompra ? UtilsDaliVO.UNO_INT : UtilsDaliVO.CERO_INT);
		bitacoraOperaciones.setCargoParticipante(UtilsDaliVO.CERO_INT);
		bitacoraOperaciones.setTasaFija(UtilsDaliVO.CERO_INT);
		bitacoraOperaciones.setFechaConcertacion(dateUtilsDao.getFechaHoraCero(fechaActual));
		bitacoraOperaciones.setOperacionFirmada(isoFirmado);

		if (vo instanceof TraspasoContraPagoVO) {
			vo2BitacoraOperaciones((TraspasoContraPagoVO) vo, bitacoraOperaciones);
		} else if (vo instanceof TraspasoLibrePagoVO) {
			vo2BitacoraOperaciones((TraspasoLibrePagoVO) vo, bitacoraOperaciones, datosAdicionales);
		} else if (vo instanceof RetiroEfectivoVO) {
			this.vo2BitacoraOperaciones((RetiroEfectivoVO) vo, bitacoraOperaciones);
		} else if (vo instanceof TraspasoEfectivoVO) {
			this.vo2BitacoraOperaciones((TraspasoEfectivoVO) vo, bitacoraOperaciones);
		} else if (vo instanceof RetiroEfectivoDTO) {
			this.vo2BitacoraOperaciones((RetiroEfectivoDTO) vo, bitacoraOperaciones);
		}

		if (datosAdicionales != null && !datosAdicionales.isEmpty() && bitacoraOperaciones.getDatosAdicionales() == null) {
			String xml = utilService.mapaToXml(datosAdicionales);
			Clob clob = new ClobImpl(xml);
			bitacoraOperaciones.setDatosAdicionales(clob);
		}

		if( bitacoraOperaciones.getFechaRegistro() == null ) {
			bitacoraOperaciones.setFechaRegistro(dateUtilsDao.getFechaHoraCero(fechaActual));
		}

		log.info("Bitacora Operaciones: [" + ToStringBuilder.reflectionToString(bitacoraOperaciones, ToStringStyle.MULTI_LINE_STYLE) + "]");

		if ((isAlta == null) || isAlta.booleanValue()) {

			if (bitacoraOperacionesDaliDao.save(bitacoraOperaciones) == null) {
				throw new BusinessDataException(errorResolver.getMessage("J0048", new Object[] { "bitacoraOperaciones" }), "J0048");
			}

		} else {

			if (bitacoraOperacionesDaliDao.merge(bitacoraOperaciones) == null) {
				throw new BusinessDataException(errorResolver.getMessage("J0048", new Object[] { "bitacoraOperaciones" }), "J0048");

			}

		}
	}

	/**
	 * Vacia la informacion del vo de traspasos contra pago a la bitacora de
	 * operaciones
	 *
	 * @param traspasoContraPagoVO
	 * @param bitacoraOperaciones
	 * @throws BusinessException
	 */
	private void vo2BitacoraOperaciones(TraspasoContraPagoVO traspasoContraPagoVO, BitacoraOperaciones bitacoraOperaciones) throws BusinessException {

		log.info("Entrando a EnvioOperacionesServiceImpl.vo2BitacoraOperaciones(TraspasoContraPagoVO, BitacoraOperaciones)");

		bitacoraOperaciones.setReferenciaMensaje(traspasoContraPagoVO.getReferenciaMensaje());

		if (StringUtils.isNotBlank(traspasoContraPagoVO.getIdFolioCtaTraspasante()) && (traspasoContraPagoVO.getIdFolioCtaTraspasante().length() >= 5)) {

			bitacoraOperaciones.setIdTrasp(traspasoContraPagoVO.getIdFolioCtaTraspasante().substring(0, 2));
			bitacoraOperaciones.setFolioTrasp(traspasoContraPagoVO.getIdFolioCtaTraspasante().substring(2, 5));
			bitacoraOperaciones.setCuentaTrasp(traspasoContraPagoVO.getIdFolioCtaTraspasante().substring(5));

		}

		if (StringUtils.isNotBlank(traspasoContraPagoVO.getIdFolioCtaReceptora()) && (traspasoContraPagoVO.getIdFolioCtaReceptora().length() >= 5)) {

			bitacoraOperaciones.setIdRecep(traspasoContraPagoVO.getIdFolioCtaReceptora().substring(0, 2));
			bitacoraOperaciones.setFolioRecep(traspasoContraPagoVO.getIdFolioCtaReceptora().substring(2, 5));
			bitacoraOperaciones.setCuentaRecep(traspasoContraPagoVO.getIdFolioCtaReceptora().substring(5));

		}

		
		bitacoraOperaciones.setTv(traspasoContraPagoVO.getTipoValor());
		bitacoraOperaciones.setEmisora(traspasoContraPagoVO.getEmisora());
		bitacoraOperaciones.setSerie(traspasoContraPagoVO.getSerie());
		bitacoraOperaciones.setCupon(traspasoContraPagoVO.getCupon());
		bitacoraOperaciones.setCantidadTitulos((traspasoContraPagoVO.getCantidadTitulos() != null) ? new BigInteger(traspasoContraPagoVO.getCantidadTitulos()
				.toString()) : null);
		bitacoraOperaciones.setDivisa(traspasoContraPagoVO.getDivisa());
		bitacoraOperaciones.setBoveda(traspasoContraPagoVO.getBoveda());
		bitacoraOperaciones.setBovedaEfectivo(traspasoContraPagoVO.getBovedaEfectivo());
		bitacoraOperaciones.setFechaLiquidacion(traspasoContraPagoVO.getFechaLiquidacion());
		bitacoraOperaciones.setFechaRegistro(traspasoContraPagoVO.getFechaRegistro());
		bitacoraOperaciones.setFechaHoraCierreOper(traspasoContraPagoVO.getFechaHoraCierreOper());
		bitacoraOperaciones.setIdFolioCtaPuente(traspasoContraPagoVO.getIdFolioCtaPuente());
		bitacoraOperaciones.setTipoInstruccion(traspasoContraPagoVO.getTipoInstruccion());
		bitacoraOperaciones.setReferenciaOperacion(traspasoContraPagoVO.getReferenciaOperacion() != null ? traspasoContraPagoVO.getReferenciaOperacion().trim()
				: CERO_STRING);
		bitacoraOperaciones.setReferenciaRelacionada(traspasoContraPagoVO.getReferenciaRelacionada());

		String mercado = getMercado(traspasoContraPagoVO.getTipoValor());
		if (StringUtils.isBlank(mercado)) {
			throw new BusinessException(errorResolver.getMessage("J0032"));
		}
		bitacoraOperaciones.setMercado(mercado);

		log.debug("MONTO= [" + traspasoContraPagoVO.getMonto().setScale(10, BigDecimal.ROUND_HALF_UP) + "]");
		bitacoraOperaciones.setMonto((traspasoContraPagoVO.getMonto() != null) ? traspasoContraPagoVO.getMonto().setScale(10, BigDecimal.ROUND_HALF_UP)
				: UtilsDaliVO.BIG_DECIMAL_ZERO);

		log.debug("PRECIO= [" + traspasoContraPagoVO.getPrecio() + "]");
		bitacoraOperaciones.setPrecio((traspasoContraPagoVO.getPrecio() != null) ? traspasoContraPagoVO.getPrecio().setScale(10, BigDecimal.ROUND_HALF_UP)
				: UtilsDaliVO.BIG_DECIMAL_ZERO);
		bitacoraOperaciones.setTasaNegociada((traspasoContraPagoVO.getTasaNegociada() != null) ? new BigDecimal(traspasoContraPagoVO.getTasaNegociada()
				.doubleValue()) : null);
		bitacoraOperaciones.setTasaReferencia((traspasoContraPagoVO.getTasaReferencia() != null) ? new BigDecimal(traspasoContraPagoVO.getTasaReferencia()
				.doubleValue()) : null);

		/* Datos que le indican al PFI que la operacion es un reporto */
		bitacoraOperaciones.setTasaNegociada((traspasoContraPagoVO.getTasaNegociada() != null) ? new BigDecimal(traspasoContraPagoVO.getTasaNegociada()
				.doubleValue()) : null);
		bitacoraOperaciones.setTasaFija((traspasoContraPagoVO.getTasaFija() != null && traspasoContraPagoVO.getTasaFija().booleanValue() ? UtilsDaliVO.UNO_INT
				: UtilsDaliVO.CERO_INT));
		bitacoraOperaciones.setFechaVencimiento(traspasoContraPagoVO.getFechaVencimiento());

	}

	/**
	 * Vacia la informacion del vo de traspasos libres de pago a la bitacora de
	 * operaciones
	 *
	 * @param traspasoLibrePagoVO
	 * @param bitacoraOperaciones
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	private void vo2BitacoraOperaciones(TraspasoLibrePagoVO traspasoLibrePagoVO, BitacoraOperaciones bitacoraOperaciones, HashMap datosAdicionales) throws BusinessException {

		log.info("Entrando a EnvioOperacionesServiceImpl.vo2BitacoraOperaciones(TraspasoLibrePagoVO, BitacoraOperaciones)");

		bitacoraOperaciones.setReferenciaMensaje(traspasoLibrePagoVO.getReferenciaMensaje());

		if (StringUtils.isNotBlank(traspasoLibrePagoVO.getIdFolioCtaTraspasante()) && (traspasoLibrePagoVO.getIdFolioCtaTraspasante().length() >= 5)) {

			bitacoraOperaciones.setIdTrasp(traspasoLibrePagoVO.getIdFolioCtaTraspasante().substring(0, 2));
			bitacoraOperaciones.setFolioTrasp(traspasoLibrePagoVO.getIdFolioCtaTraspasante().substring(2, 5));
			bitacoraOperaciones.setCuentaTrasp(traspasoLibrePagoVO.getIdFolioCtaTraspasante().substring(5));

		}

		if (StringUtils.isNotBlank(traspasoLibrePagoVO.getIdFolioCtaReceptora()) && (traspasoLibrePagoVO.getIdFolioCtaReceptora().length() >= 5)) {

			bitacoraOperaciones.setIdRecep(traspasoLibrePagoVO.getIdFolioCtaReceptora().substring(0, 2));
			bitacoraOperaciones.setFolioRecep(traspasoLibrePagoVO.getIdFolioCtaReceptora().substring(2, 5));
			bitacoraOperaciones.setCuentaRecep(traspasoLibrePagoVO.getIdFolioCtaReceptora().substring(5));

		}

		bitacoraOperaciones.setTv(traspasoLibrePagoVO.getTipoValor());
		bitacoraOperaciones.setEmisora(traspasoLibrePagoVO.getEmisora());
		bitacoraOperaciones.setSerie(traspasoLibrePagoVO.getSerie());
		bitacoraOperaciones.setCupon(traspasoLibrePagoVO.getCupon());
		bitacoraOperaciones.setCantidadTitulos((traspasoLibrePagoVO.getCantidadTitulos() != null) ? new BigInteger(traspasoLibrePagoVO.getCantidadTitulos()
				.toString()) : null);
		bitacoraOperaciones.setFechaLiquidacion(traspasoLibrePagoVO.getFechaLiquidacion());
		bitacoraOperaciones.setFechaRegistro(traspasoLibrePagoVO.getFechaRegistro());
		bitacoraOperaciones.setFechaHoraCierreOper(traspasoLibrePagoVO.getFechaHoraCierreOper());
		bitacoraOperaciones.setIdFolioCtaPuente(traspasoLibrePagoVO.getIdFolioCtaPuente());
		bitacoraOperaciones.setTipoInstruccion(traspasoLibrePagoVO.getTipoInstruccion());
		bitacoraOperaciones.setReferenciaOperacion(traspasoLibrePagoVO.getReferenciaOperacion() != null ? traspasoLibrePagoVO.getReferenciaOperacion().trim()
				: CERO_STRING);
		bitacoraOperaciones.setReferenciaRelacionada(traspasoLibrePagoVO.getReferenciaRelacionada());
		bitacoraOperaciones.setCargoParticipante(((traspasoLibrePagoVO.getCargoParticipante() != null) && traspasoLibrePagoVO.getCargoParticipante()
				.booleanValue()) ? UtilsDaliVO.UNO_INT : UtilsDaliVO.CERO_INT);
		String mercado = getMercado(traspasoLibrePagoVO.getTipoValor());
		if (StringUtils.isBlank(mercado)) {
			throw new BusinessException(errorResolver.getMessage("J0032"));
		}

		bitacoraOperaciones.setMonto(UtilsDaliVO.BIG_DECIMAL_ZERO);
		bitacoraOperaciones.setPrecio(UtilsDaliVO.BIG_DECIMAL_ZERO);
		bitacoraOperaciones.setBoveda(traspasoLibrePagoVO.getBoveda());

		bitacoraOperaciones.setMercado(mercado);
		if (Constantes.MISCELANEA_FISCAL.equalsIgnoreCase(traspasoLibrePagoVO.getTipoInstruccion().trim())
				|| MISCELANEA_FISCAL_CANCELACION.equalsIgnoreCase(traspasoLibrePagoVO.getTipoInstruccion().trim()) ) {

			datosAdicionales.put("bovedaTraspasante", traspasoLibrePagoVO.getBoveda());
			datosAdicionales.put("fechaAdquisicion", traspasoLibrePagoVO.getFechaAdquisicion());
			datosAdicionales.put("precioAdquisicion", traspasoLibrePagoVO.getPrecioAdquisicion());
			datosAdicionales.put("costoPromActualizado", traspasoLibrePagoVO.getCostoFiscalActualizado());
			datosAdicionales.put("cliente", traspasoLibrePagoVO.getCliente());
			datosAdicionales.put("rfccurp", traspasoLibrePagoVO.getRfcCurp());

			String xml = utilService.mapaToXml(datosAdicionales);
			Clob clob = new ClobImpl(xml);
			bitacoraOperaciones.setDatosAdicionales(clob);
		}

	}

	/**
	 * Vacia la informacion del vo de retiro de efectivo a la bitacora de
	 * operaciones
	 *
	 * @param retiroEfectivoVO
	 * @param bitacoraOperaciones
	 * @throws BusinessException
	 */
	private void vo2BitacoraOperaciones(RetiroEfectivoVO retiroEfectivoVO, BitacoraOperaciones bitacoraOperaciones) throws BusinessException {
		log.info("Entrando a EnvioOperacionesServiceImpl.vo2BitacoraOperaciones(RetiroEfectivoVO, BitacoraOperaciones)");

		if (retiroEfectivoVO != null && bitacoraOperaciones != null) {
			bitacoraOperaciones.setDivisa(retiroEfectivoVO.getDivisa());
			bitacoraOperaciones.setBoveda(retiroEfectivoVO.getBovedaTraspasante());
			bitacoraOperaciones.setBovedaEfectivo(retiroEfectivoVO.getBoveda());
			bitacoraOperaciones.setFechaLiquidacion(retiroEfectivoVO.getFechaLiquidacion());

			if (StringUtils.isNotBlank(retiroEfectivoVO.getCuentaBeneficiaria()) && retiroEfectivoVO.getCuentaBeneficiaria().length() >= 18) {
				bitacoraOperaciones.setIdTrasp(retiroEfectivoVO.getCuentaBeneficiaria().substring(8, 10));
				bitacoraOperaciones.setFolioTrasp(retiroEfectivoVO.getCuentaBeneficiaria().substring(10, 13));
				bitacoraOperaciones.setCuentaTrasp(retiroEfectivoVO.getCuentaBeneficiaria());
			}

			if (StringUtils.isNotBlank(retiroEfectivoVO.getCuentaOrdenante()) && retiroEfectivoVO.getCuentaOrdenante().length() >= 18) {
				bitacoraOperaciones.setIdRecep(retiroEfectivoVO.getCuentaOrdenante().substring(8, 10));
				bitacoraOperaciones.setFolioRecep(retiroEfectivoVO.getCuentaOrdenante().substring(10, 13));
				bitacoraOperaciones.setCuentaRecep(retiroEfectivoVO.getCuentaOrdenante());
			}

			bitacoraOperaciones.setCantidadTitulos(Constantes.CERO_BIG_INTEGER);

			bitacoraOperaciones.setFechaRegistro(retiroEfectivoVO.getFechaRegistro());
			bitacoraOperaciones.setMonto(retiroEfectivoVO.getMonto() == null ? new BigDecimal(Constantes.CERO_STR) : new BigDecimal(retiroEfectivoVO.getMonto()
					.doubleValue()));
			bitacoraOperaciones.setReferenciaMensaje(retiroEfectivoVO.getReferenciaMensaje());
			bitacoraOperaciones.setReferenciaOperacion(retiroEfectivoVO.getReferenciaOperacion());
			bitacoraOperaciones.setTipoInstruccion(retiroEfectivoVO.getTipoInstruccion());
		}
	}

	private void vo2BitacoraOperaciones(RetiroEfectivoDTO retiroEfectivoDTO, BitacoraOperaciones bitacoraOperaciones) throws BusinessException {
		if (retiroEfectivoDTO != null && bitacoraOperaciones != null) {
			String folioControl="";
			if (retiroEfectivoDTO.getReferenciaOperacion()==null){
				folioControl = utilService.getFolio(SECUENCIA_FOLIO_CONTROL).toString();
			}else{
				folioControl = retiroEfectivoDTO.getReferenciaOperacion();
			}

			bitacoraOperaciones.setFolioControl(new BigInteger(folioControl));
			bitacoraOperaciones.setReferenciaOperacion(folioControl);

			bitacoraOperaciones.setDivisa(String.valueOf(retiroEfectivoDTO.getDivisa().getClaveAlfabetica()));
			bitacoraOperaciones.setBoveda(String.valueOf(retiroEfectivoDTO.getBoveda().getNombreCorto()));
			bitacoraOperaciones.setBovedaEfectivo(String.valueOf(retiroEfectivoDTO.getBoveda().getNombreCorto()));
			bitacoraOperaciones.setFechaLiquidacion(new Date());
			bitacoraOperaciones.setFechaRegistro(retiroEfectivoDTO.getFechaCreacion());
			bitacoraOperaciones.setReferenciaRelacionada(null);
			bitacoraOperaciones.setCantidadTitulos(Constantes.CERO_BIG_INTEGER);

			bitacoraOperaciones.setIdRecep(retiroEfectivoDTO.getIdInstReceptor().getClaveTipoInstitucion());
			bitacoraOperaciones.setFolioRecep(retiroEfectivoDTO.getIdInstReceptor().getFolioInstitucion());
			bitacoraOperaciones.setCuentaRecep(retiroEfectivoDTO.getCuentaBeneficiario());
			
			bitacoraOperaciones.setIdTrasp(retiroEfectivoDTO.getInstitucion().getClaveTipoInstitucion());
			bitacoraOperaciones.setFolioTrasp(retiroEfectivoDTO.getInstitucion().getFolioInstitucion());
			bitacoraOperaciones.setCuentaTrasp(retiroEfectivoDTO.getCuentaEmisor());
			//---

			bitacoraOperaciones.setMonto(retiroEfectivoDTO.getImporteTraspaso() == null ? new BigDecimal(Constantes.CERO_STRING) : new BigDecimal(retiroEfectivoDTO.getImporteTraspaso().doubleValue()));
			bitacoraOperaciones.setReferenciaMensaje(retiroEfectivoDTO.getReferenciaMensaje());
			
			if(com.indeval.portaldali.middleware.services.tesoreria.util.Constantes.TIPO_OPERACION__RETIRO_CCS.equals( retiroEfectivoDTO.getTipoOperacion())) {
				bitacoraOperaciones.setTipoInstruccion("RETI");
			}else { // RETE o TREF
				bitacoraOperaciones.setTipoInstruccion(retiroEfectivoDTO.getTipoInstruccion().getNombreCorto());
			}
			
		}
	}

	/**
	 * Vacia la informacion del vo de traspaso de efectivo a la bitacora de
	 * operaciones
	 *
	 * @param traspasoEfectivoVO
	 * @param bitacoraOperaciones
	 * @throws BusinessException
	 */
	private void vo2BitacoraOperaciones(TraspasoEfectivoVO traspasoEfectivoVO, BitacoraOperaciones bitacoraOperaciones) throws BusinessException {

		log.info("Entrando a EnvioOperacionesServiceImpl.vo2BitacoraOperaciones(TraspasoEfectivoVO, BitacoraOperaciones)");

		if (traspasoEfectivoVO != null && bitacoraOperaciones != null) {
			bitacoraOperaciones.setDivisa(traspasoEfectivoVO.getDivisa());
			bitacoraOperaciones.setBoveda(traspasoEfectivoVO.getBovedaTraspasante());
 			bitacoraOperaciones.setBovedaEfectivo(traspasoEfectivoVO.getBoveda());
			bitacoraOperaciones.setFechaLiquidacion(traspasoEfectivoVO.getFechaLiquidacion());
			bitacoraOperaciones.setFechaRegistro(traspasoEfectivoVO.getFechaRegistro());
			bitacoraOperaciones.setReferenciaOperacion(traspasoEfectivoVO.getReferenciaOperacion());
			bitacoraOperaciones.setReferenciaRelacionada(traspasoEfectivoVO.getReferenciaRelacionada());
			bitacoraOperaciones.setCantidadTitulos(Constantes.CERO_BIG_INTEGER);

			if (StringUtils.isNotBlank(traspasoEfectivoVO.getCuentaBeneficiaria())) {
				if (traspasoEfectivoVO.getCuentaBeneficiaria().length() == 7) {
					bitacoraOperaciones.setIdRecep(traspasoEfectivoVO.getCuentaBeneficiaria().substring(0, 2));
					bitacoraOperaciones.setFolioRecep(traspasoEfectivoVO.getCuentaBeneficiaria().substring(2, 5));
					bitacoraOperaciones.setCuentaRecep(traspasoEfectivoVO.getCuentaBeneficiaria().substring(5));
				} else if (traspasoEfectivoVO.getCuentaBeneficiaria().length() == 18) {
					bitacoraOperaciones.setIdRecep(traspasoEfectivoVO.getCuentaBeneficiaria().substring(8, 10));
					bitacoraOperaciones.setFolioRecep(traspasoEfectivoVO.getCuentaBeneficiaria().substring(10, 13));
					bitacoraOperaciones.setCuentaRecep(traspasoEfectivoVO.getCuentaBeneficiaria());
				}
			}

			if (StringUtils.isNotBlank(traspasoEfectivoVO.getCuentaOrdenante())) {
				if (traspasoEfectivoVO.getCuentaOrdenante().length() == 7) {
					bitacoraOperaciones.setIdTrasp(traspasoEfectivoVO.getCuentaOrdenante().substring(0, 2));
					bitacoraOperaciones.setFolioTrasp(traspasoEfectivoVO.getCuentaOrdenante().substring(2, 5));
					bitacoraOperaciones.setCuentaTrasp(traspasoEfectivoVO.getCuentaOrdenante().substring(5));
				} else if (traspasoEfectivoVO.getCuentaOrdenante().length() == 18) {
					bitacoraOperaciones.setIdTrasp(traspasoEfectivoVO.getCuentaOrdenante().substring(8, 10));
					bitacoraOperaciones.setFolioTrasp(traspasoEfectivoVO.getCuentaOrdenante().substring(10, 13));
					bitacoraOperaciones.setCuentaTrasp(traspasoEfectivoVO.getCuentaOrdenante());
				}
			}

			bitacoraOperaciones.setMonto(traspasoEfectivoVO.getMonto() == null ? new BigDecimal(Constantes.CERO_STRING) : new BigDecimal(traspasoEfectivoVO
					.getMonto().doubleValue()));
			bitacoraOperaciones.setReferenciaMensaje(traspasoEfectivoVO.getReferenciaMensaje());
			bitacoraOperaciones.setReferenciaOperacion(traspasoEfectivoVO.getReferenciaOperacion());
			bitacoraOperaciones.setTipoInstruccion(traspasoEfectivoVO.getTipoInstruccion());
		}
	}

	/**
	 * Obtiene el mercado a partir del TV proporcionado
	 *
	 * @param tv
	 *
	 * @return String
	 */
	private String getMercado(String tv) {

		log.info("Entrando a EnvioOperacionesServiceImpl.getMercado()");

		InstrumentoVO instrumento = UtilsVOCatalogo.getInstanceInstrumento(instrumentoDaliDao.getInstrumento(tv));

		if (instrumento == null) {

			return null;

		}
		if (StringUtils.isNotBlank(instrumento.getMercado())) {

			if (instrumento.getMercado().equalsIgnoreCase("PB") || instrumento.getMercado().equalsIgnoreCase("PG")) {

				return "MD";

			}

		}

		return instrumento.getMercado();

	}

	/**
	 *
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#getMensajeBitacoraMatch(com.indeval.portaldali.middleware.services.util.vo.BitacoraMatchParams)
	 */
	public BitacoraMatchVO[] getMensajeBitacoraMatch(BitacoraMatchParams bitacoraVO) throws BusinessException {

		log.info("Entrando a EnvioOperacionesServiceImpl.getMensajeBitacoraMatch()");

		return bitacoraOperacionesService.getMensajeBitacoraMatch(bitacoraVO);

	}

	/**
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#actualizaEstadoInstruccionExpira(java.math.BigInteger,
	 *      AgenteVO, boolean)
	 */
	public BigInteger actualizaEstadoInstruccionExpira(BigInteger idBitacoraMatch, AgenteVO agenteFirmado, HashMap datosAdicionales, String isoFirmado) throws BusinessException {

		return creaOperacionMatch(idBitacoraMatch, agenteFirmado, Boolean.FALSE, null, datosAdicionales, isoFirmado, null);

	}

	/**
	 *
	 * @param idBitacoraMatch
	 * @param agenteFirmado
	 * @param esConfirmacion
	 * @return
	 */
	private BigInteger creaOperacionMatch(BigInteger idBitacoraMatch, AgenteVO agenteFirmado, Boolean esConfirmacion, BovedaDTO bovedaDTO, HashMap datosAdicionales, String isoFirmado, Date fechaHoraCierreOper) {

		GrabaOperacionVO grabaOperacionVO=generaTLpVoDvpVO(idBitacoraMatch,agenteFirmado, bovedaDTO,esConfirmacion);

		if(StringUtils.isNotEmpty(isoFirmado)){
			String refMensaje=StringUtils.split(isoFirmado,'\n')[0];
			grabaOperacionVO.baseTlpDvpVO.setReferenciaMensaje(refMensaje);
		}
		grabaOperacionVO.baseTlpDvpVO.setFechaHoraCierreOper(fechaHoraCierreOper);
		
		BigInteger folio = null;

		/* Se graba la operacion */
		if (listaTiposOperacion.contains(grabaOperacionVO.tipoOperacion)) {
			folio = this.grabaOperacion((TraspasoLibrePagoVO)grabaOperacionVO.baseTlpDvpVO , grabaOperacionVO.isCompra, datosAdicionales, null, isoFirmado);
		} else {
			folio = this.grabaOperacion((TraspasoContraPagoVO)grabaOperacionVO.baseTlpDvpVO , grabaOperacionVO.isCompra, datosAdicionales, null, isoFirmado);
		}

		if (folio == null) {
			throw new BusinessException(errorResolver.getMessage("J0058"), "J0058");
		}

		/* Se marca el registro como confirmado */
		int resultadoActualizacion = registroInstruccionesMatch2Dao.updateStatusConfirmacion(grabaOperacionVO.match);

		if (resultadoActualizacion < 0) {
			throw new BusinessException(errorResolver.getMessage("J0043"), "J0043");
		}
		return folio;
	}


	private class GrabaOperacionVO{
		public BaseTlpDvpVO baseTlpDvpVO;
		public Boolean 	isCompra;
		public String tipoOperacion;
		public RegistroInstruccionesMatch2 match;
	}


	private GrabaOperacionVO generaTLpVoDvpVO(BigInteger idBitacoraMatch,AgenteVO agenteFirmado, BovedaDTO bovedaDTO, Boolean esConfirmacion){

		log.info("Entrando a EnvioOperacionesServiceImpl.confirmaOperacionMatch()");

		/* Se validan los parametros de entrada */
		utilService.validaDTONoNulo(idBitacoraMatch, " identificador de la operacion ");
		utilService.validaAgente(agenteFirmado, "firmado", false);


		/* Se recupera el registro */
		RegistroInstruccionesMatch2 match = registroInstruccionesMatch2Dao.getRegistroInstruccionMatch2(idBitacoraMatch.longValue());

		/* Se verifica el registro de la operacion de match */
		if (match == null) {
			throw new BusinessException("No se encontr\u00f3 registro de operaci\u00f3n match");
		}

		String mensaje = match.getMensaje();

		/* Se verifica el mensaje del registro */
		if (StringUtils.isBlank(mensaje)) {
			throw new BusinessException("No se encontr\u00f3 mensaje en el registro de la operaci\u00f3n");
		}

		/* Se construye el TradingInVO a partir del mensaje */
		TradingInVO tradingInVO = (TradingInVO) bitacoraOperacionesService.getDetalleOperacion(mensaje);
		tradingInVO.setReferenciaParticipante(match.getReferenciaParticipante()==null?"":match.getReferenciaParticipante().toString());

		String tipoOperacion = tradingInVO.getTipoOperacion();

		/* Se verifica si la operacion esta confirmada o cancelada */
		BitacoraMatchVO bitacoraMatchVO = verificaMatch(match);

		/* Se valida los permisos de confirmacion */
		bitacoraMatchVO = bitacoraOperacionesService.verificaPermisosConfirmacionCancelacion(tradingInVO, agenteFirmado, bitacoraMatchVO);

		if (esConfirmacion) {
			if (bitacoraMatchVO != null && bitacoraMatchVO.getPuedeConfirmar() != null) {
				if (!bitacoraMatchVO.getPuedeConfirmar().booleanValue()) {
					throw new BusinessException("La operaci\u00f3n [" + idBitacoraMatch + "] no puede ser confirmada");
				}
			} else {
				throw new BusinessException("No han podido ser verificados los permisos" + "para confirmar esta operacion");
			}
		}

		/* Se construye el objeto TraspasoContraPagoVO */
		BaseTlpDvpVO baseTlpDvpVO=null;

		if(bovedaDTO != null){
			tradingInVO.setBovedaEfectivo(bovedaDTO.getNombreCorto());
		}
		if (listaTiposOperacion.contains(tipoOperacion)) {
			baseTlpDvpVO= bitacoraOperacionesService.tradingInVO2TraspasoLibrePago(tradingInVO, esConfirmacion);
		} else {
			baseTlpDvpVO = bitacoraOperacionesService.tradingInVO2TraspasoContraPago(tradingInVO, esConfirmacion);
		}

		String tipoMensaje = null;

		if (tradingInVO != null) {
			tipoMensaje = tradingInVO.getTipoMensaje(); // 543 o 541
		}

		/* Se verifica si la operacion es una venta */
		boolean isCompra = false;

		if (esConfirmacion) {
			if (StringUtils.isNotBlank(tipoMensaje)
					&& (com.indeval.portaldali.middleware.services.util.util.Constantes.TMSJ_543.equals(tipoMensaje.trim())
							|| com.indeval.portaldali.middleware.services.util.util.Constantes.TMSJ_542
							.equals(tipoMensaje.trim()))) {
				isCompra = true;
			}
			// Si es una cancelacion
		} else {
			if (StringUtils.isNotBlank(tipoMensaje)
					&& (com.indeval.portaldali.middleware.services.util.util.Constantes.TMSJ_543.equals(tipoMensaje.trim())
							|| com.indeval.portaldali.middleware.services.util.util.Constantes.TMSJ_542
							.equals(tipoMensaje.trim()))) {
				isCompra = false;
			} else if (StringUtils.isNotBlank(tipoMensaje)
					&& (com.indeval.portaldali.middleware.services.util.util.Constantes.TMSJ_541.equals(tipoMensaje.trim())
							|| com.indeval.portaldali.middleware.services.util.util.Constantes.TMSJ_540
							.equals(tipoMensaje.trim()))) {
				isCompra = true;
			}
		}

		GrabaOperacionVO grabaOperacionVO = new GrabaOperacionVO();
		grabaOperacionVO.baseTlpDvpVO=baseTlpDvpVO;
		grabaOperacionVO.isCompra=isCompra;
		grabaOperacionVO.tipoOperacion=tipoOperacion;
		grabaOperacionVO.match=match;

		String referencia=getReferenciaMensaje();

		grabaOperacionVO.baseTlpDvpVO.setReferenciaMensaje(referencia);
		if(esConfirmacion){
			grabaOperacionVO.baseTlpDvpVO.setReferenciaOperacion(referencia);
		}
		return grabaOperacionVO;

	}


	public String generaIso(BigInteger idBitacoraMatch, AgenteVO agenteFirmado, BovedaDTO bovedaDTO, Date fechaHoraCierreOper, Boolean esConfirmacion) {
		GrabaOperacionVO grabaOperacionVO=generaTLpVoDvpVO(idBitacoraMatch, agenteFirmado, bovedaDTO, esConfirmacion);
		grabaOperacionVO.baseTlpDvpVO.setTipoInstruccion(grabaOperacionVO.baseTlpDvpVO.getTipoInstruccion().replace(Constantes.OPERACION_CANCELADA, StringUtils.EMPTY));

		if(fechaHoraCierreOper != null) {
			grabaOperacionVO.baseTlpDvpVO.setFechaHoraCierreOper(fechaHoraCierreOper);
		}
		
		return creaISO(grabaOperacionVO.baseTlpDvpVO, grabaOperacionVO.isCompra, esConfirmacion);
	}


	public String generaIso(RetiroEfectivoInternacionalDTO retiroEfectivoInternacionalVO)	{
		try {
			return creaIsoRetiroEfectivo(retiroEfectivoInternacionalVO);
		} catch (BusinessException e) {
		    log.error("Error al creaIsoRetiroEfectivo", e);
		} catch (ProtocoloFinancieroException e) {
            log.error("Error al creaIsoRetiroEfectivo", e);
		}
		return null;
	}

	public String generaIso(RetiroEfectivoDTO Rete)	{
		try {
			return creaIsoRetiroEfectivo(Rete);
		} catch (BusinessException e) {
            log.error("Error al creaIsoRetiroEfectivo", e);
		} catch (ProtocoloFinancieroException e) {
		    log.error("Error al creaIsoRetiroEfectivo", e);
		}
		return null;
	}



	/**
	 * @param idBitacoraMatch
	 * @param agenteFirmado
	 *
	 * @throws BusinessException
	 *
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#confirmaOperacionMatch(java.math.BigInteger,
	 *      com.indeval.portaldali.middleware.services.modelo.AgenteVO)
	 */
	public BigInteger confirmaOperacionMatch(BigInteger idBitacoraMatch, AgenteVO agenteFirmado, BovedaDTO bovedaDTO, HashMap datosAdicionales, String isoFirmado, Date fechaHoraCierreOper) throws BusinessException {

		return creaOperacionMatch(idBitacoraMatch, agenteFirmado, Boolean.TRUE, bovedaDTO, datosAdicionales, isoFirmado, fechaHoraCierreOper);
	}

	/**
	 * Metodo que convierte el Clob recibido en una cadena String
	 *
	 * @param clobMensaje
	 * @return String
	 * @throws BusinessException
	 * @deprecated use UtilService.clobToString
	 */
	@SuppressWarnings("unused")
	private String converterClob2String(Clob clobMensaje) throws BusinessException {

		log.info("Entrando a EnvioOperacionesServiceImpl.converterClob2String()");

		StringBuffer buffer = new StringBuffer("");

		if (clobMensaje != null) {

			try {
				char[] charMensaje = new char[(int) clobMensaje.length()];
				clobMensaje.getCharacterStream().read(charMensaje);
				buffer.append(charMensaje);

			} catch (SQLException e) {
				e.printStackTrace();
				throw new BusinessException("No se encontr\u00f3 el mensaje " + "en el registro de la operaci\u00f3n");
			} catch (IOException e) {
				e.printStackTrace();
				throw new BusinessException("Ha ocurrido un error inesperado");
			}

		}

		return buffer.toString();

	}

	/**
	 * Metodo que sirve para verificar si la operacion de match esta confirmada
	 * o cancelada.
	 *
	 * @param match
	 * @return BitacoraMatchVO
	 */
	private BitacoraMatchVO verificaMatch(RegistroInstruccionesMatch2 match) {

		log.info("Entrando a EnvioOperacionesServiceImpl.verificaMatch()");

		/* Se verifica si la operacion esta confirmada */
		BitacoraMatchVO bitacoraMatchVO = new BitacoraMatchVO();
		bitacoraMatchVO.setEstaConfirmada(Boolean.FALSE);

		/* Se verifica si la operacion esta cancelada */
		if (StringUtils.isNotBlank(match.getEstadoInstruccion())
				&& (CANCPORTAL.equalsIgnoreCase(match.getEstadoInstruccion()) || CANCELADO.equalsIgnoreCase(match.getEstadoInstruccion()))) {
			bitacoraMatchVO.setEstaCancelada(Boolean.TRUE);

		} else {
			bitacoraMatchVO.setEstaCancelada(Boolean.FALSE);
		}

		return bitacoraMatchVO;
	}

	// /**
	// *
	// * @see com.indeval.portaldali.middleware.services.enviooperaciones
	// *
	// .EnvioOperacionesService#grabaOperacionSybase(com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO,
	// * java.lang.String, boolean)
	// */
	// public void grabaOperacionSybase(TraspasoContraPagoVO vo, String
	// folioControl, boolean isCompra)
	// throws BusinessException {
	//
	// log.info("Ejecutando grabaOperacionSybase(TraspasoContraPagoVO,
	// folioControl, isCompra)");
	// System.out
	// .println("Ejecutando grabaOperacionSybase(TraspasoContraPagoVO,
	// folioControl, isCompra)");
	//
	// vo.setReferenciaMensaje(getReferenciaMensaje());
	// vo.setReferenciaOperacion(folioControl);
	//
	// setBitacoraSybase(vo, folioControl, ESTATUS_NO_ENVIADO, true, isCompra);
	//
	// }

	// /**
	// *
	// * @see com.indeval.portaldali.middleware.services.enviooperaciones
	// *
	// .EnvioOperacionesService#grabaOperacionSybase(com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO,
	// * java.lang.String, boolean)
	// */
	// public void grabaOperacionSybase(TraspasoLibrePagoVO vo, String
	// folioControl, boolean isCompra)
	// throws BusinessException {
	//
	// log.info("Ejecutando grabaOperacionSybase(TraspasoLibrePagoVO,
	// folioControl, isCompra)");
	// System.out
	// .println("Ejecutando grabaOperacionSybase(TraspasoLibrePagoVO,
	// folioControl, isCompra)");
	//
	// vo.setReferenciaMensaje(getReferenciaMensaje());
	// vo.setReferenciaOperacion(folioControl);
	// setBitacoraSybase(vo, folioControl, ESTATUS_NO_ENVIADO, true, isCompra);
	//
	// }

	// /**
	// *
	// * @see com.indeval.portaldali.middleware.services.enviooperaciones
	// *
	// .EnvioOperacionesService#grabaOperacionSybase(com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO,
	// * boolean)
	// */
	// public BigInteger grabaOperacionSybase(TraspasoContraPagoVO vo,
	// boolean isCompra) throws BusinessException {
	//
	// log
	// .info("Ejecutando grabaOperacionSybase(TraspasoContraPagoVO, boolean)");
	// System.out
	// .println("Ejecutando grabaOperacionSybase(TraspasoContraPagoVO,
	// boolean)");
	//
	// String folioControl = UtilsDaliVO.BLANK;
	//
	// if ("MD".equalsIgnoreCase(getMercado(vo.getTipoValor()))) {
	//
	// folioControl = utilService.getFolio(SECUENCIA_FOLIO_CONTROL).toString();
	//
	// } else if ("MC".equalsIgnoreCase(getMercado(vo.getTipoValor()))) {
	//
	// folioControl = utilService.getFolio(SECUENCIA_FOLIO_CONTROL).toString();
	//
	// } else {
	// throw new BusinessException(
	// "El mercado es incorrecto y no se permite grabar el registro");
	// }
	//
	// grabaOperacion(vo, folioControl, isCompra);
	//
	// return new BigInteger(folioControl);
	// }

	// /**
	// *
	// * @see com.indeval.portaldali.middleware.services.enviooperaciones
	// *
	// .EnvioOperacionesService#grabaOperacionSybase(com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO,
	// * boolean)
	// */
	// public BigInteger grabaOperacionSybase(TraspasoLibrePagoVO vo,
	// boolean isCompra) throws BusinessException {
	//
	// log
	// .info("Ejecutando grabaOperacionSybase(TraspasoLibrePagoVO, boolean)");
	// System.out
	// .println("Ejecutando grabaOperacionSybase(TraspasoLibrePagoVO,
	// boolean)");
	//
	// String folioControl = UtilsDaliVO.BLANK;
	//
	// if ("MD".equalsIgnoreCase(getMercado(vo.getTipoValor()))) {
	//
	// folioControl = utilService.getFolio(SECUENCIA_FOLIO_CONTROL).toString();
	//
	// } else if ("MC".equalsIgnoreCase(getMercado(vo.getTipoValor()))) {
	//
	// folioControl = utilService.getFolio(SECUENCIA_FOLIO_CONTROL).toString();
	//
	// } else {
	// throw new BusinessException(
	// "El mercado es incorrecto y no se permite grabar el registro");
	// }
	//
	// grabaOperacion(vo, folioControl, isCompra, null, null);
	//
	// return new BigInteger(folioControl);
	// }

	// /**
	// * Graba la bitacora de la operacion en sybase
	// *
	// * @param vo
	// * @param folioControl
	// * @param estatus
	// * @param isAlta
	// * @param isCompra
	// * true - Inidica que la operacion es una compra (para match)
	// *
	// * @throws BusinessException
	// */
	// private void setBitacoraSybase(Object vo, String folioControl, String
	// estatus, Boolean isAlta,
	// boolean isCompra) throws BusinessException {
	//
	// log.info("Entrando a EnvioOperacionesServiceImpl.setBitacora()");
	//
	// utilService.validaDTONoNulo(vo, " vo ");
	// utilService.validarClaveTipo(estatus, " estatus ");
	//
	// BitacoraOperacionesSybase bitacoraOperacionesSybase = new
	// BitacoraOperacionesSybase();
	// bitacoraOperacionesSybase.setBitacoraOperacionesPK(new
	// BitacoraOperacionesSybasePK());
	//
	// bitacoraOperacionesSybase.setFolioControl(Integer
	// .valueOf(folioControl != null ? folioControl : CERO_STRING));
	// bitacoraOperacionesSybase.setOrigenRegistro(ORIGEN_OPERACION_PORTAL);
	// bitacoraOperacionesSybase.setFechaHoraAlta(dateUtilsDao.getDateFechaDB());
	// bitacoraOperacionesSybase.setEstatusRegistro(estatus);
	// bitacoraOperacionesSybase.setMarcaCompra(isCompra ?
	// UtilsDaliVO.INTEGER_UNO
	// : UtilsDaliVO.INTEGER_ZERO);
	//
	// if (vo instanceof TraspasoContraPagoVO) {
	//
	// vo2BitacoraOperacionesSybase((TraspasoContraPagoVO) vo,
	// bitacoraOperacionesSybase);
	//
	// }
	// else if (vo instanceof TraspasoLibrePagoVO) {
	//
	// vo2BitacoraOperacionesSybase((TraspasoLibrePagoVO) vo,
	// bitacoraOperacionesSybase);
	//
	// }
	// //  else ???
	//
	// // bitacoraOperacionesPK == null ???
	// if ((bitacoraOperacionesSybase.getBitacoraOperacionesPK() == null)
	// ||
	// StringUtils.isBlank(bitacoraOperacionesSybase.getBitacoraOperacionesPK()
	// .getReferenciaMensaje())
	// ||
	// (bitacoraOperacionesSybase.getBitacoraOperacionesPK().getFechaConcertacion()
	// == null)
	// || (bitacoraOperacionesSybase.getFolioControl() == null)
	// || (bitacoraOperacionesSybase.getFechaLiquidacion() == null)
	// || StringUtils.isBlank(bitacoraOperacionesSybase.getMercado())) {
	//
	// throw new BusinessDataException(errorResolver.getMessage("J0019", new
	// Object[] {
	// ReflectionToStringBuilder.reflectionToString(bitacoraOperacionesSybase
	// .getBitacoraOperacionesPK()) }), "J0019");
	//
	// }
	//
	// if ((isAlta == null) || isAlta.booleanValue()) {
	//
	// if (bitacoraOperacionesXADao.save(bitacoraOperacionesSybase) == null) {
	//
	// throw new BusinessDataException(errorResolver.getMessage("J0048", new
	// Object[] {
	// "bitacoraOperaciones" }), "J0048");
	//
	// }
	//
	// }
	// else {
	//
	// if (bitacoraOperacionesXADao.merge(bitacoraOperacionesSybase) == null) {
	//
	// throw new BusinessDataException(errorResolver.getMessage("J0048", new
	// Object[] {
	// "bitacoraOperaciones" }), "J0048");
	//
	// }
	//
	// }
	//
	// }

	// /**
	// * Vacia la informacion del vo de traspasos contra pago a la bitacora de
	// * operaciones
	// *
	// * @param traspasoContraPagoVO
	// * @param bitacoraOperacionesSybase
	// * @throws BusinessException
	// */
	// private void vo2BitacoraOperacionesSybase(TraspasoContraPagoVO
	// traspasoContraPagoVO,
	// BitacoraOperacionesSybase bitacoraOperacionesSybase) throws
	// BusinessException {
	//
	// log.info("Entrando a
	// EnvioOperacionesServiceImpl.vo2BitacoraOperaciones()");
	//
	// bitacoraOperacionesSybase.getBitacoraOperacionesPK().setReferenciaMensaje(
	// traspasoContraPagoVO.getReferenciaMensaje());
	// bitacoraOperacionesSybase.getBitacoraOperacionesPK().setFechaConcertacion(
	// dateUtilsDao.getFechaHoraCero(traspasoContraPagoVO.getFechaConcertacion()));
	//
	// if
	// (StringUtils.isNotBlank(traspasoContraPagoVO.getIdFolioCtaTraspasante())
	// && (traspasoContraPagoVO.getIdFolioCtaTraspasante().length() >= 5)) {
	//
	// bitacoraOperacionesSybase.setIdTrasp(traspasoContraPagoVO.getIdFolioCtaTraspasante()
	// .substring(0, 2));
	// bitacoraOperacionesSybase.setFolioTrasp(traspasoContraPagoVO.getIdFolioCtaTraspasante()
	// .substring(2, 5));
	// bitacoraOperacionesSybase.setCuentaTrasp(traspasoContraPagoVO
	// .getIdFolioCtaTraspasante().substring(5));
	//
	// }
	//
	// if (StringUtils.isNotBlank(traspasoContraPagoVO.getIdFolioCtaReceptora())
	// && (traspasoContraPagoVO.getIdFolioCtaReceptora().length() >= 5)) {
	//
	// bitacoraOperacionesSybase.setIdRecep(traspasoContraPagoVO.getIdFolioCtaReceptora()
	// .substring(0, 2));
	// bitacoraOperacionesSybase.setFolioRecep(traspasoContraPagoVO.getIdFolioCtaReceptora()
	// .substring(2, 5));
	// bitacoraOperacionesSybase.setCuentaRecep(traspasoContraPagoVO.getIdFolioCtaReceptora()
	// .substring(5));
	//
	// }
	//
	// bitacoraOperacionesSybase.setTv(traspasoContraPagoVO.getTipoValor());
	// bitacoraOperacionesSybase.setEmisora(traspasoContraPagoVO.getEmisora());
	// bitacoraOperacionesSybase.setSerie(traspasoContraPagoVO.getSerie());
	// bitacoraOperacionesSybase.setCupon(traspasoContraPagoVO.getCupon());
	// bitacoraOperacionesSybase
	// .setCantidadTitulos((traspasoContraPagoVO.getCantidadTitulos() != null) ?
	// new BigInteger(
	// traspasoContraPagoVO.getCantidadTitulos().toString())
	// : null);
	// bitacoraOperacionesSybase.setDivisa(traspasoContraPagoVO.getDivisa());
	// bitacoraOperacionesSybase.setFechaLiquidacion(traspasoContraPagoVO.getFechaLiquidacion());
	// bitacoraOperacionesSybase.setFechaRegistro(traspasoContraPagoVO.getFechaRegistro());
	// bitacoraOperacionesSybase.setIdFolioCtaPuente(traspasoContraPagoVO.getIdFolioCtaPuente());
	// bitacoraOperacionesSybase.setTipoInstruccion(traspasoContraPagoVO.getTipoInstruccion());
	// bitacoraOperacionesSybase.setReferenciaOperacion(traspasoContraPagoVO
	// .getReferenciaOperacion() != null ?
	// traspasoContraPagoVO.getReferenciaOperacion()
	// .trim() : CERO_STRING);
	// bitacoraOperacionesSybase.setReferenciaRelacionada(traspasoContraPagoVO
	// .getReferenciaRelacionada());
	//
	// String mercado = getMercado(traspasoContraPagoVO.getTipoValor());
	// if (StringUtils.isBlank(mercado)) {
	// throw new BusinessException(errorResolver.getMessage("J0032"));
	// }
	// bitacoraOperacionesSybase.setMercado(mercado);
	//
	// log.debug("MONTO= ["
	// + traspasoContraPagoVO.getMonto().setScale(2, BigDecimal.ROUND_HALF_UP) +
	// "]");
	// bitacoraOperacionesSybase
	// .setMonto((traspasoContraPagoVO.getMonto() != null) ?
	// traspasoContraPagoVO
	// .getMonto().setScale(2, BigDecimal.ROUND_HALF_UP)
	// : UtilsDaliVO.BIG_DECIMAL_ZERO);
	//
	// log.debug("PRECIO= [" + traspasoContraPagoVO.getPrecio() + "]");
	// bitacoraOperacionesSybase
	// .setPrecio((traspasoContraPagoVO.getPrecio() != null) ?
	// traspasoContraPagoVO
	// .getPrecio().setScale(8, BigDecimal.ROUND_HALF_UP)
	// : UtilsDaliVO.BIG_DECIMAL_ZERO);
	// bitacoraOperacionesSybase
	// .setTasaNegociada((traspasoContraPagoVO.getTasaNegociada() != null) ? new
	// BigDecimal(
	// traspasoContraPagoVO.getTasaNegociada().doubleValue())
	// : null);
	// bitacoraOperacionesSybase
	// .setTasaReferencia((traspasoContraPagoVO.getTasaReferencia() != null) ?
	// new BigDecimal(
	// traspasoContraPagoVO.getTasaReferencia().doubleValue())
	// : null);
	//
	// /* Datos que le indican al PFI que la operacion es un reporto */
	// bitacoraOperacionesSybase
	// .setTasaNegociada((traspasoContraPagoVO.getTasaNegociada() != null) ? new
	// BigDecimal(
	// traspasoContraPagoVO.getTasaNegociada().doubleValue())
	// : null);
	// bitacoraOperacionesSybase
	// .setTasaFija((traspasoContraPagoVO.getTasaFija() != null) ?
	// (traspasoContraPagoVO
	// .getTasaFija().booleanValue() ? Integer.valueOf(UNO_INT) : Integer
	// .valueOf(CERO_INT)) : null);
	// bitacoraOperacionesSybase.setFechaVencimiento(traspasoContraPagoVO.getFechaVencimiento());
	//
	// }
	// /**
	// * Vacia la informacion del vo de traspasos libres de pago a la bitacora
	// de
	// * operaciones
	// *
	// * @param traspasoLibrePagoVO
	// * @param bitacoraOperacionesSybase
	// * @throws BusinessException
	// */
	// private void vo2BitacoraOperacionesSybase(TraspasoLibrePagoVO
	// traspasoLibrePagoVO,
	// BitacoraOperacionesSybase bitacoraOperacionesSybase) throws
	// BusinessException {
	//
	// log.info("Entrando a
	// EnvioOperacionesServiceImpl.vo2BitacoraOperaciones()");
	//
	// bitacoraOperacionesSybase.getBitacoraOperacionesPK().setReferenciaMensaje(
	// traspasoLibrePagoVO.getReferenciaMensaje());
	// bitacoraOperacionesSybase.getBitacoraOperacionesPK().setFechaConcertacion(
	// dateUtilsDao.getFechaHoraCero(traspasoLibrePagoVO.getFechaRegistro()));
	//
	// if
	// (StringUtils.isNotBlank(traspasoLibrePagoVO.getIdFolioCtaTraspasante())
	// && (traspasoLibrePagoVO.getIdFolioCtaTraspasante().length() >= 5)) {
	//
	// bitacoraOperacionesSybase.setIdTrasp(traspasoLibrePagoVO.getIdFolioCtaTraspasante()
	// .substring(0, 2));
	// bitacoraOperacionesSybase.setFolioTrasp(traspasoLibrePagoVO.getIdFolioCtaTraspasante()
	// .substring(2));
	// bitacoraOperacionesSybase.setCuentaTrasp(traspasoLibrePagoVO.getIdFolioCtaTraspasante()
	// .substring(5));
	//
	// }
	//
	// if (StringUtils.isNotBlank(traspasoLibrePagoVO.getIdFolioCtaReceptora())
	// && (traspasoLibrePagoVO.getIdFolioCtaReceptora().length() >= 5)) {
	//
	// bitacoraOperacionesSybase.setIdRecep(traspasoLibrePagoVO.getIdFolioCtaReceptora()
	// .substring(0, 2));
	// bitacoraOperacionesSybase.setFolioRecep(traspasoLibrePagoVO.getIdFolioCtaReceptora()
	// .substring(2, 5));
	// bitacoraOperacionesSybase.setCuentaRecep(traspasoLibrePagoVO.getIdFolioCtaReceptora()
	// .substring(5));
	//
	// }
	//
	// bitacoraOperacionesSybase.setTv(traspasoLibrePagoVO.getTipoValor());
	// bitacoraOperacionesSybase.setEmisora(traspasoLibrePagoVO.getEmisora());
	// bitacoraOperacionesSybase.setSerie(traspasoLibrePagoVO.getSerie());
	// bitacoraOperacionesSybase.setCupon(traspasoLibrePagoVO.getCupon());
	// bitacoraOperacionesSybase
	// .setCantidadTitulos((traspasoLibrePagoVO.getCantidadTitulos() != null) ?
	// new BigInteger(
	// traspasoLibrePagoVO.getCantidadTitulos().toString())
	// : null);
	// bitacoraOperacionesSybase.setFechaLiquidacion(traspasoLibrePagoVO.getFechaLiquidacion());
	// bitacoraOperacionesSybase.setFechaRegistro(traspasoLibrePagoVO.getFechaRegistro());
	// bitacoraOperacionesSybase.setIdFolioCtaPuente(traspasoLibrePagoVO.getIdFolioCtaPuente());
	// bitacoraOperacionesSybase.setTipoInstruccion(traspasoLibrePagoVO.getTipoInstruccion());
	// bitacoraOperacionesSybase.setReferenciaOperacion(traspasoLibrePagoVO
	// .getReferenciaOperacion() != null ?
	// traspasoLibrePagoVO.getReferenciaOperacion()
	// .trim() : CERO_STRING);
	// bitacoraOperacionesSybase.setReferenciaRelacionada(traspasoLibrePagoVO
	// .getReferenciaRelacionada());
	// bitacoraOperacionesSybase
	// .setCargoParticipante(((traspasoLibrePagoVO.getCargoParticipante() !=
	// null) && traspasoLibrePagoVO
	// .getCargoParticipante()) ? UtilsDaliVO.INTEGER_UNO :
	// UtilsDaliVO.INTEGER_ZERO);
	// String mercado = getMercado(traspasoLibrePagoVO.getTipoValor());
	// if (StringUtils.isBlank(mercado)) {
	// throw new BusinessException(errorResolver.getMessage("J0032"));
	// }
	// bitacoraOperacionesSybase.setMercado(mercado);
	//
	// }

	/**
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#grabaOperacion(com.indeval.protocolofinanciero.api.vo.RetiroEfectivoVO,
	 *      java.lang.String, boolean)
	 */
	@SuppressWarnings("unchecked")
	public void grabaOperacion(RetiroEfectivoVO vo, String folioControl, boolean isCompra, HashMap datosAdicionales, String origenRegistro, String isoFirmado)
			throws BusinessException {

		log.info("Entrando a EnvioOperacionesServiceImpl.grabaOperacion()");

		vo.setReferenciaMensaje(StringUtils.isBlank(vo.getReferenciaMensaje()) ? getReferenciaMensaje() : vo.getReferenciaMensaje());
		vo.setReferenciaOperacion(StringUtils.isBlank(vo.getReferenciaOperacion()) ? folioControl : vo.getReferenciaOperacion());
		setBitacora(vo, folioControl, ESTATUS_NO_ENVIADO, true, isCompra, datosAdicionales, origenRegistro, isoFirmado);
	}

	@SuppressWarnings("unchecked")
	public void grabaOperacion(TraspasoEfectivoVO vo, String folioControl, boolean isCompra, HashMap datosAdicionales, String origenRegistro, String isoFirmado)
			throws BusinessException {

		log.info("Entrando a EnvioOperacionesServiceImpl.grabaOperacion()");

		vo.setReferenciaMensaje(StringUtils.isBlank(vo.getReferenciaMensaje()) ? getReferenciaMensaje() : vo.getReferenciaMensaje());
		vo.setReferenciaOperacion(StringUtils.isBlank(vo.getReferenciaOperacion()) ? folioControl : vo.getReferenciaOperacion());
		setBitacora(vo, folioControl, ESTATUS_NO_ENVIADO, true, isCompra, datosAdicionales, origenRegistro, isoFirmado);
	}

	/**
	 * @see com.indeval.portallegado.middleware.services.enviooperaciones.EnvioOperacionesService#creaISO(java.lang.Object,
	 *      boolean)
	 */
	public String creaISO(Object vo, boolean isCompra, boolean esConfirmacion){

		log.info("Entrando a EnvioOperacionesServiceImpl.creaISO()");

		String iso = null;

		try {
			if (vo instanceof TraspasoContraPagoVO) {
				iso = creaIsoDvp(vo, isCompra, esConfirmacion);
			} else if (vo instanceof TraspasoLibrePagoVO) {
				iso = creaIsoTlp(vo, isCompra, esConfirmacion);
			} else if (vo instanceof RetiroEfectivoVO) {
				iso = creaIsoRetiroEfectivo(vo);
			} else if (vo instanceof TraspasoEfectivoVO) {
				iso = creaIsoTraspasoEfectivo(vo);
			}

			if(iso != null ){
				iso= iso.replace("\r\n", "\n");
			}
		} catch (ProtocoloFinancieroException e) {
			throw new BusinessException(e.getMessage());
		}

		return iso;

	}

	/**
	 * Crea el iso correspondiente a traspasos contrapago
	 *
	 * @param vo
	 * @param isCompra
	 * @return String
	 * @throws ProtocoloFinancieroException
	 * @throws BusinessException
	 */
	private String creaIsoDvp(Object vo, boolean isCompra, boolean esConfirmacion) throws ProtocoloFinancieroException, BusinessException {

		log.info("Entrando a EnvioOperacionesServiceImpl.creaIsoDvp()");

		String iso = null;
		TraspasoContraPagoVO dvpVo = (TraspasoContraPagoVO) vo;
		dvpVo.setCupon(dvpVo.getCupon().trim());
		dvpVo.setEmisora(dvpVo.getEmisora().trim());
		dvpVo.setIdFolioCtaReceptora(dvpVo.getIdFolioCtaReceptora().trim());
		dvpVo.setIdFolioCtaTraspasante(dvpVo.getIdFolioCtaTraspasante().trim());
		dvpVo.setReferenciaMensaje(dvpVo.getReferenciaMensaje().trim());
		dvpVo.setSerie(dvpVo.getSerie().trim());
		dvpVo.setTipoInstruccion(dvpVo.getTipoInstruccion().trim());
		dvpVo.setTipoValor(dvpVo.getTipoValor().trim());
		dvpVo.setReferenciaOperacion(dvpVo.getReferenciaOperacion().trim());
		dvpVo.setISIN(dvpVo.getISIN() != null ? dvpVo.getISIN().trim() : null);
		if (dvpVo.getTipoInstruccion().equalsIgnoreCase(TIPO_INSTRUCCION_V)) {
			dvpVo.setFechaVencimiento(null);
			dvpVo.setTasaNegociada(null);
			dvpVo.setTasaFija(null);
		}
		if (!isCompra) {
			log.debug("Enviando una venta... (TraspasoContraPagoVO)");
			if(esConfirmacion){
				iso = dvpService.entrega(dvpVo);
			}else{
				iso = dvpService.cancelaEntrega(dvpVo);
			}
		} else {
			log.debug("Enviando una compra... (TraspasoContraPagoVO)");
			if(esConfirmacion){
				iso = dvpService.recepcion(dvpVo);
			}else {
				iso = dvpService.cancelaRecepcion(dvpVo);
			}
		}

		if(iso!=null){
			iso=iso+"\n";
		}

		log.debug("Valor de iso..DVP.. " + iso);

		return iso;

	}

	/**
	 * Crea el iso correspondiente a traspasos libres de pago
	 *
	 * @param vo
	 * @param isCompra
	 * @return String
	 * @throws ProtocoloFinancieroException
	 * @throws BusinessException
	 */
	private String creaIsoTlp(Object vo, boolean isCompra, boolean esConfirmacion) throws ProtocoloFinancieroException, BusinessException {

		log.info("Entrando a EnvioOperacionesServiceImpl.creaIsoTlp()");

		String iso = null;
		TraspasoLibrePagoVO tlpVO = (TraspasoLibrePagoVO) vo;
		tlpVO.setCliente(tlpVO.getCliente() != null ? tlpVO.getCliente().trim() : null);
		tlpVO.setCupon(tlpVO.getCupon().trim());
		tlpVO.setEmisora(tlpVO.getEmisora().trim());
		tlpVO.setIdFolioCtaReceptora(tlpVO.getIdFolioCtaReceptora().trim());
		tlpVO.setIdFolioCtaTraspasante(tlpVO.getIdFolioCtaTraspasante().trim());
		tlpVO.setReferenciaMensaje(tlpVO.getReferenciaMensaje().trim());
		tlpVO.setRfcCurp(tlpVO.getRfcCurp() != null ? tlpVO.getRfcCurp().trim() : null);
		tlpVO.setSerie(tlpVO.getSerie().trim());
		tlpVO.setTipoInstruccion(tlpVO.getTipoInstruccion().trim());
		tlpVO.setTipoValor(tlpVO.getTipoValor().trim());
		tlpVO.setReferenciaOperacion(tlpVO.getReferenciaOperacion().trim());
		tlpVO.setISIN(tlpVO.getISIN() != null ? tlpVO.getISIN().trim() : null);
		if (!isCompra) {
			log.debug("Enviando una venta... (TraspasoLibrePagoVO)  | Objeto [" + ReflectionToStringBuilder.toString(tlpVO) + "]");
			if(esConfirmacion){
				iso = tlpService.entrega(tlpVO);
			}else{
				iso = tlpService.cancelaEntrega(tlpVO);
			}
		} else {
			log.debug("Enviando una compra... (TraspasoLibrePagoVO)");
			if(esConfirmacion){
				iso = tlpService.recepcion(tlpVO);
			}else {
				iso = tlpService.cancelaRecepcion(tlpVO);
			}
		}

		if(iso!=null){
			iso=iso+"\n";
		}

		log.debug("Valor de iso..TLP.. " + iso);

		return iso;

	}

	/**
	 * Crea el iso correspondiente a retiros de efectivo
	 *
	 * @param vo
	 * @return String
	 * @throws ProtocoloFinancieroException
	 * @throws BusinessException
	 */

	private String creaIsoRetiroEfectivo(Object vo) throws ProtocoloFinancieroException, BusinessException {

		log.info("Entrando a EnvioOperacionesServiceImpl.creaIsoRetiroEfectivo()");

		String iso = null;
		RetiroEfectivoVO reteVO = new RetiroEfectivoVO();

		if (vo instanceof RetiroEfectivoInternacionalDTO ){
			RetiroEfectivoInternacionalDTO reteIntlDTO = (RetiroEfectivoInternacionalDTO) vo;
			reteVO = DTOAssembler.creaRetiroEfectivoVOByRetiroEfectivoInternacionalDTO(reteIntlDTO);
			iso = efectivo.retiro103(reteVO);
		}else if (vo instanceof RetiroEfectivoDTO ){
			RetiroEfectivoDTO reteDTO = (RetiroEfectivoDTO) vo;
			
			
			if(reteDTO.getTipoInstruccion().getIdTipoInstruccion() == com.indeval.portaldali.middleware.services.tesoreria.util.Constantes.ID_TIPO_INSTRUCCION__TREF){
				// TREF
				TraspasoEfectivoVO traspasoDto = DTOAssembler.crearTraspasoEfectivoVOByRetiroEfectivoDTO(reteDTO);
				iso = efectivo.traspaso(traspasoDto);
				
			}else if(reteDTO.getTipoInstruccion().getIdTipoInstruccion() == com.indeval.portaldali.middleware.services.tesoreria.util.Constantes.ID_TIPO_INSTRUCCION__RETE){
				reteVO = DTOAssembler.creaRetiroEfectivoVOByRetiroEfectivoDTO(reteDTO);
				if(com.indeval.portaldali.middleware.services.tesoreria.util.Constantes.TIPO_OPERACION__RETIRO_CCS.equals(reteDTO.getTipoOperacion())) {
					iso = efectivo.retiro103(reteVO);
				}else { // SPEI, SIAC
					
					reteVO.setTipoRetiro(reteDTO.getTipoOperacion());
					iso = efectivo.retiro(reteVO);
				}
			}
		}
		else if (vo instanceof RetiroEfectivoVO ){
			reteVO = (RetiroEfectivoVO) vo;
			reteVO.setBeneficiario(reteVO.getBeneficiario().trim());
			reteVO.setCuentaBeneficiaria(reteVO.getCuentaBeneficiaria().trim());
			reteVO.setReferenciaMensaje(reteVO.getReferenciaMensaje().trim());
			reteVO.setTipoInstruccion(reteVO.getTipoInstruccion().trim());
			reteVO.setReferenciaOperacion(reteVO.getReferenciaOperacion().trim());
			iso = efectivo.retiro(reteVO);
		}


		if(iso!=null){
			iso=iso+"\n";
		}

		return iso;
	}



	/**
	 * Crea el iso correspondiente a traspaos de efectivo
	 *
	 * @param vo
	 * @return String
	 * @throws ProtocoloFinancieroException
	 * @throws BusinessException
	 */
	private String creaIsoTraspasoEfectivo(Object vo) throws ProtocoloFinancieroException, BusinessException {

		log.info("Entrando a EnvioOperacionesServiceImpl.creaIsoTraspasoEfectivo()");

		String iso = null;
		TraspasoEfectivoVO trefVO = (TraspasoEfectivoVO) vo;
		trefVO.setBeneficiario(trefVO.getBeneficiario().trim());
		trefVO.setCuentaBeneficiaria(trefVO.getCuentaBeneficiaria().trim());
		trefVO.setOrdenante(trefVO.getOrdenante().trim());
		trefVO.setCuentaOrdenante(trefVO.getCuentaOrdenante().trim());
		trefVO.setReferenciaMensaje(trefVO.getReferenciaMensaje().trim());
		trefVO.setTipoInstruccion(trefVO.getTipoInstruccion().trim());
		trefVO.setReferenciaOperacion(trefVO.getReferenciaOperacion().trim());
		log.debug("Enviando un traspaso de efectivo");
		iso = efectivo.traspaso(trefVO);
		log.debug("Valor de iso..TREF.. " + iso);

		return iso;

	}

	/**
	 * @param instrumentoDaliDao
	 *            the instrumentoDaliDao to set
	 */
	public void setInstrumentoDao(InstrumentoDaliDao instrumentoDaliDao) {
		this.instrumentoDaliDao = instrumentoDaliDao;
	}

	/**
	 * @param cola2
	 */
	public void setCola2(String cola2) {
		this.cola2 = cola2;
	}

	/**
	 * @param errorResolver
	 */
	public void setErrorResolver(MessageResolver errorResolver) {
		this.errorResolver = errorResolver;
	}

	/**
	 * @param utilService
	 */
	public void setUtilService(UtilServices utilService) {
		this.utilService = utilService;
	}

	/**
	 * @param dvpService
	 */
	public void setDvpService(TraspasoContraPago dvpService) {
		this.dvpService = dvpService;
	}

	/**
	 * @param jmsClient
	 */
	public void setJmsClient(JmsClient jmsClient) {
		this.jmsClient = jmsClient;
	}

	/**
	 * @param jmsTemplate
	 */
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	// /**
	// * @param bitacoraOperacionesSybaseDao
	// */
	// public void setBitacoraOperacionesSybaseDao(
	// BitacoraOperacionesSybaseDao bitacoraOperacionesSybaseDao) {
	// this.bitacoraOperacionesSybaseDao = bitacoraOperacionesSybaseDao;
	// }
	/**
	 * @param cola
	 */
	public void setCola(String cola) {
		this.cola = cola;
	}

	/**
	 * @param tlpService
	 */
	public void setTlpService(TraspasoLibrePago tlpService) {
		this.tlpService = tlpService;
	}

	// /**
	// *
	// * @param validadorPFIDao
	// */
	// public void setValidadorPFIDao(ValidadorPFIDao validadorPFIDao) {
	// this.validadorPFIDao = validadorPFIDao;
	// }
	//
	// /**
	// * @return BitacoraOperacionesSybaseDao
	// */
	// public BitacoraOperacionesSybaseDao getBitacoraOperacionesXADao() {
	// return bitacoraOperacionesXADao;
	// }
	//
	// /**
	// * @param bitacoraOperacionesXADao
	// */
	// public void setBitacoraOperacionesXADao(BitacoraOperacionesSybaseDao
	// bitacoraOperacionesXADao) {
	// this.bitacoraOperacionesXADao = bitacoraOperacionesXADao;
	// }

	/**
	 * @param fasesValidasConfirmacion
	 *            the fasesValidasConfirmacion to set
	 */
	@SuppressWarnings("unchecked")
	public void setFasesValidasConfirmacion(Map fasesValidasConfirmacion) {
		this.fasesValidasConfirmacion = fasesValidasConfirmacion;
	}

	/**
	 * @param bitacoraOperacionesService
	 *            the bitacoraOperacionesService to set
	 */
	public void setBitacoraOperacionesService(BitacoraOperacionesService bitacoraOperacionesService) {
		this.bitacoraOperacionesService = bitacoraOperacionesService;
	}

	/**
	 * @param bitacoraOperacionesDaliDao
	 *            the bitacoraOperacionesDaliDao to set
	 */
	public void setBitacoraOperacionesDao(BitacoraOperacionesDaliDao bitacoraOperacionesDaliDao) {
		this.bitacoraOperacionesDaliDao = bitacoraOperacionesDaliDao;
	}

	/**
	 * @param registroInstruccionesMatch2Dao
	 *            the registroInstruccionesMatch2Dao to set
	 */
	public void setRegistroInstruccionesMatch2Dao(RegistroInstruccionesMatch2Dao registroInstruccionesMatch2Dao) {
		this.registroInstruccionesMatch2Dao = registroInstruccionesMatch2Dao;
	}

	/**
	 * @param dateUtilsDao
	 *            the dateUtilsDao to set
	 */
	public void setDateUtilsDao(DateUtilsDao dateUtilsDao) {
		this.dateUtilsDao = dateUtilsDao;
	}

	/**
	 * @param listaTiposOperacion
	 *            the listaTiposOperacion to set
	 */
	public void setListaTiposOperacion(List<String> listaTiposOperacion) {
		this.listaTiposOperacion = listaTiposOperacion;
	}

	public PaginaVO getBitacoras(Object bitacoraOperaciones, PaginaVO paginaVO) {
		BitacoraOperaciones bitaOperArg = (BitacoraOperaciones) bitacoraOperaciones;
		// Retrieves missing fields for bovedas, and changes from id to
		// nombreCorto: originally the fields in BitacoraOperaciones carries id,
		// but here we change from ids to nombreCorto.
		try {
			if(!"TODAS".equalsIgnoreCase(bitaOperArg.getBoveda())) {
				final String bovedaNombreCorto = bovedaDao.consultarBovedaPorId(
						Long.valueOf(bitaOperArg.getBoveda())).getNombreCorto();
				bitaOperArg.setBoveda(bovedaNombreCorto);
			}
			if(!"TODAS".equalsIgnoreCase(bitaOperArg.getBovedaEfectivo())) {
				final String bovedaEffNombreCorto = bovedaDao.consultarBovedaPorId(
						Long.valueOf(bitaOperArg.getBovedaEfectivo())).getNombreCorto();
				bitaOperArg.setBovedaEfectivo(bovedaEffNombreCorto);
			}
		} catch (NumberFormatException e) {
			log.debug("Realizando generacion de reporte. Ya se ha realizado el cambio de id a nombre de boveda.");
			bitaOperArg = (BitacoraOperaciones) bitacoraOperaciones;
		}
		return bitacoraOperacionesDaliDao.getBitacoras(bitaOperArg, paginaVO);
	}

	public void grabaOperacion(final DepositoDivisaDTO depositoDivisa, final Map<String, Object> datosAdicionales, final String isoFirmado)
            throws BusinessException {
		log.info("Entrando a EnvioOperacionesServiceImpl.grabaOperacion");
		setBitacora(depositoDivisa, datosAdicionales, isoFirmado);
		log.info("Saliendo de EnvioOperacionesServiceImpl.grabaOperacion");
	}

	private void setBitacora(DepositoDivisaDTO depositoDivisa, Map<String, Object> datosAdicionales, String isoFirmado) throws BusinessException {
		log.info("Entrando a EnvioOperacionesServiceImpl.setBitacora(DepositoDivisa)");
		Institucion institucion = null;
		InstitucionDTO institucionDTO =null;

		if( StringUtils.isBlank(depositoDivisa.getClaveInstitucion())
				&&  StringUtils.isNotBlank(depositoDivisa.getCasfim())){
			institucionDTO = institucionDAO.buscarInstitucionPorCasfim(depositoDivisa.getCasfim().trim());
		}else{
			institucionDTO = institucionDAO.buscarInstitucionPorClaveYFolio(depositoDivisa.getClaveInstitucion());
		}

		if (institucionDTO == null) {
			throw new BusinessDataException(errorResolver.getMessage("J0001", new Object[] { depositoDivisa.getClaveInstitucion() }), "J0001");
		} else {
			BigInteger idInstitucion = new BigInteger(String.valueOf(institucionDTO.getId()));
			institucion = (Institucion) bitacoraOperacionesDaliDao.getByPk(Institucion.class, idInstitucion);
			if (institucion == null) {
				throw new BusinessDataException(errorResolver.getMessage("J0001", new Object[] { depositoDivisa.getClaveInstitucion() }), "J0001");
			}
		}

		BitacoraOperaciones bitacoraOperaciones = new BitacoraOperaciones();

		BigInteger idFolioBitacoraOperaciones = utilService.getFolio(SEQ_BITACORA_OPERACIONES);

		log.info("ID de bitacora: [" + idFolioBitacoraOperaciones + "]");

		StringBuffer bufferIdInst= new StringBuffer();
		bufferIdInst.append(institucion.getTipoInstitucion().getIdTipoInstitucion());

		bitacoraOperaciones.setIdTrasp(StringUtils.leftPad(bufferIdInst.toString(),2,'0'));
		bitacoraOperaciones.setFolioTrasp(institucion.getFolioInstitucion());

		bitacoraOperaciones.setIdRecep(bitacoraOperaciones.getIdTrasp());
		bitacoraOperaciones.setFolioRecep(institucion.getFolioInstitucion());

		bitacoraOperaciones.setIdBitacoraOperaciones(idFolioBitacoraOperaciones);
		bitacoraOperaciones.setFolioControl(null);
		bitacoraOperaciones.setReferenciaMensaje(getReferenciaMensaje());
		bitacoraOperaciones.setReferenciaOperacion(idFolioBitacoraOperaciones.toString());
		bitacoraOperaciones.setMarcaCompra(UtilsDaliVO.CERO_INT);
		bitacoraOperaciones.setTipoInstruccion("DPDV");
		bitacoraOperaciones.setOrigenRegistro(ORIGEN_OPERACION_PORTAL);
		bitacoraOperaciones.setTasaFija(UtilsDaliVO.CERO_INT);
		bitacoraOperaciones.setCargoParticipante(UtilsDaliVO.CERO_INT);

		Date fechaActual = dateUtilsDao.getDateFechaDB();

		if (fechaActual == null) {
			throw new BusinessDataException(errorResolver.getMessage("J0001", new Object[] { "fecha actual" }), "J0001");
		}
		bitacoraOperaciones.setFechaHoraAlta(fechaActual);
		bitacoraOperaciones.setFechaRegistro(dateUtilsDao.getFechaHoraCero(fechaActual));
		bitacoraOperaciones.setEstatusRegistro(ESTATUS_NO_ENVIADO);

		bitacoraOperaciones.setCuentaTrasp(institucion.getClaveCasfim()); // se pone el casfim
		bitacoraOperaciones.setCuentaRecep(institucion.getNombreCorto()); // aqui se pone el nombre corto


		Boveda boveda = (Boveda) bitacoraOperacionesDaliDao.getByPk(Boveda.class, depositoDivisa.getIdBoveda());
		if (boveda == null) {
			throw new BusinessDataException(errorResolver.getMessage("J0001", new Object[] { "boveda" }), "J0001");
		}
		//CAMBIO SETEO
		if(depositoDivisa.getNombreCorto()!= null){
		    bitacoraOperaciones.setBovedaEfectivo(boveda.getNombreCorto());//original
		}else{
			bitacoraOperaciones.setBovedaEfectivo(depositoDivisa.getNombreCorto());		
		}
		//bitacoraOperaciones.setBovedaEfectivo(boveda.getNombreCorto());
		Divisa divisa = (Divisa) bitacoraOperacionesDaliDao.getByPk(Divisa.class, depositoDivisa.getIdDivisa());
		if (divisa == null) {
			throw new BusinessDataException(errorResolver.getMessage("J0001", new Object[] { "divisa" }), "J0001");
		}
		//CAMBIO DE SETEO
		if(divisa.getClaveAlfabetica()!= null){
			bitacoraOperaciones.setDivisa(divisa.getClaveAlfabetica()); //cambio vato de divisa en depositoDivisa -original
		}else{
			bitacoraOperaciones.setDivisa(depositoDivisa.getDivisa()); //agregado 03092012
		}
		
		bitacoraOperaciones.setMonto(depositoDivisa.getImporte());
		bitacoraOperaciones.setFechaConcertacion(dateUtilsDao.getFechaHoraCero(fechaActual));


		if (datosAdicionales != null && !datosAdicionales.isEmpty() && bitacoraOperaciones.getDatosAdicionales() == null) {
			String xml = utilService.mapaToXml(datosAdicionales);
			Clob clob = new ClobImpl(xml);
			bitacoraOperaciones.setDatosAdicionales(clob);
		}
		bitacoraOperaciones.setOperacionFirmada(isoFirmado);
		if (bitacoraOperacionesDaliDao.save(bitacoraOperaciones) == null) {
			throw new BusinessDataException(errorResolver.getMessage("J0048", new Object[] { "bitacoraOperaciones" }), "J0048");

		}
		log.info("Saliendo de EnvioOperacionesServiceImpl.setBitacora(DepositoDivisa)");
	}

	public void grabaOperacion(RetiroEfectivoInternacionalDTO vo, String folioControl,boolean isCompra, HashMap datosAdicionales, String origenRegistro,
			String isoFirmado) throws BusinessException {
		BitacoraOperaciones bitacoraOperaciones = new BitacoraOperaciones();

		BigInteger idFolioBitacoraOperaciones = utilService.getFolio(SEQ_BITACORA_OPERACIONES);

		log.info("ID de bitacora: [" + idFolioBitacoraOperaciones + "]");
		bitacoraOperaciones.setIdBitacoraOperaciones(idFolioBitacoraOperaciones);
		bitacoraOperaciones.setFolioControl(null);

		bitacoraOperaciones.setReferenciaMensaje(vo.getReferenciaMensaje());

		if (vo.getReferenciaOperacion()==null){
			folioControl = utilService.getFolio(SECUENCIA_FOLIO_CONTROL).toString();
		}else{
			folioControl = vo.getReferenciaOperacion();
		}


		bitacoraOperaciones.setFolioControl(new BigInteger(folioControl));
		bitacoraOperaciones.setReferenciaOperacion(folioControl);
		bitacoraOperaciones.setTipoInstruccion("RETI");
		bitacoraOperaciones.setTasaNegociada(null);
		bitacoraOperaciones.setCuentaTrasp(vo.getInstitucion().getCuentaClabe());
		//------
		bitacoraOperaciones.setIdTrasp(String.valueOf(vo.getInstitucion().getClaveTipoInstitucion()));
		bitacoraOperaciones.setFolioTrasp(vo.getInstitucion().getFolioInstitucion());
		bitacoraOperaciones.setIdRecep(null);
		bitacoraOperaciones.setFolioRecep(null);

		String strId =  String.valueOf( vo.getIdCuentaBeneficiario());
		CuentaRetiroInternacionalDTO cuentaRetiroInternacionalDTO = (CuentaRetiroInternacionalDTO) admonCuentasRetiroEfectivoService.buscarCuentaRetiroInternacional(new BigInteger( strId ));
		if (cuentaRetiroInternacionalDTO!=null){
			vo.setCuentaBeneficiarioFinal(cuentaRetiroInternacionalDTO.getCuentaBeneficiarioFinal());
		}else{
			vo.setCuentaBeneficiarioFinal(null);
		}


		bitacoraOperaciones.setCuentaRecep(vo.getCuentaBeneficiarioFinal());

		datosAdicionales.put("ordenante", vo.getInstitucion().getNombreCorto());
		datosAdicionales.put("bovedaTraspasante", vo.getBoveda().getNombreCorto());
		datosAdicionales.put("beneficiario",   vo.getCuentaBeneficiarioFinal());
		datosAdicionales.put("conceptoPago", vo.getConceptoPago());
		datosAdicionales.put("referenciaNumerica",vo.getReferenciaOperacion());


		bitacoraOperaciones.setMonto(vo.getImporteTraspaso());
		bitacoraOperaciones.setFechaConcertacion(dateUtilsDao.getFechaHoraCero(dateUtilsDao.getDateFechaDB()) );//no puede ser nula
		bitacoraOperaciones.setFechaLiquidacion(dateUtilsDao.getFechaHoraCero(dateUtilsDao.getDateFechaDB()));
		bitacoraOperaciones.setFechaVencimiento(null);

		bitacoraOperaciones.setMarcaCompra(UtilsDaliVO.CERO_INT);
		bitacoraOperaciones.setDivisa(vo.getDivisa().getClaveAlfabetica());
		bitacoraOperaciones.setOrigenRegistro(ORIGEN_OPERACION_PORTAL);
		bitacoraOperaciones.setCargoParticipante(UtilsDaliVO.CERO_INT);


		bitacoraOperaciones.setCantidadTitulos(BigInteger.ZERO);
		bitacoraOperaciones.setTasaFija(0);
		bitacoraOperaciones.setOperacionFirmada(isoFirmado);

		Date fechaActual = dateUtilsDao.getDateFechaDB();

		if (fechaActual == null) {
			throw new BusinessDataException(errorResolver.getMessage("J0001", new Object[] { "fecha actual" }), "J0001");
		}
		bitacoraOperaciones.setFechaHoraAlta(fechaActual);
		bitacoraOperaciones.setFechaRegistro(dateUtilsDao.getFechaHoraCero(fechaActual));
		bitacoraOperaciones.setEstatusRegistro(ESTATUS_NO_ENVIADO);


		if (datosAdicionales != null && !datosAdicionales.isEmpty() && bitacoraOperaciones.getDatosAdicionales() == null) {
			String xml = utilService.mapaToXml(datosAdicionales);
			Clob clob = new ClobImpl(xml);
			bitacoraOperaciones.setDatosAdicionales(clob);
		}

		if (bitacoraOperacionesDaliDao.save(bitacoraOperaciones) == null) {
			throw new BusinessDataException(errorResolver.getMessage("J0048", new Object[] { "bitacoraOperaciones" }), "J0048");

		}
		log.info("Saliendo de EnvioOperacionesServiceImpl.setBitacora(RetiroInternacionalEfectivo)");

	}



	public void grabaOperacion(TraspasoEfectivoFvVO vo, String folioControl,
			boolean isCompra, HashMap datosAdicionales, String origenRegistro,
			String isoFirmado, boolean isCancelacion) throws BusinessException {

		log.info("Entrando a EnvioOperacionesServiceImpl.setBitacora(CallMoneyVO)");
		Institucion institucion = null;


		BitacoraOperaciones bitacoraOperaciones = new BitacoraOperaciones();

		BigInteger idFolioBitacoraOperaciones = utilService.getFolio(SEQ_BITACORA_OPERACIONES);

		log.info("ID de bitacora: [" + idFolioBitacoraOperaciones + "]");
		bitacoraOperaciones.setIdBitacoraOperaciones(idFolioBitacoraOperaciones);
		bitacoraOperaciones.setFolioControl(BigInteger.valueOf(Integer.valueOf(folioControl)));
		bitacoraOperaciones.setReferenciaMensaje(vo.getReferenciaMensaje());
		bitacoraOperaciones.setReferenciaOperacion(vo.getReferenciaOperacion());
		bitacoraOperaciones.setTasaNegociada( new BigDecimal(vo.getTasaInteres()));

		bitacoraOperaciones.setIdTrasp((String)datosAdicionales.get("idTrasp"));
		bitacoraOperaciones.setFolioTrasp((String)datosAdicionales.get("folioTrasp"));
		bitacoraOperaciones.setIdRecep((String)datosAdicionales.get("idRecep"));
		bitacoraOperaciones.setFolioRecep((String)datosAdicionales.get("folioRecep"));


		bitacoraOperaciones.setCuentaTrasp(vo.getCuentaIdPrestamista());
		bitacoraOperaciones.setCuentaRecep(vo.getCuentaIdPrestatario());

		bitacoraOperaciones.setMonto(new BigDecimal( vo.getMontoPrestamo()));


		bitacoraOperaciones.setFechaConcertacion(dateUtilsDao.getFechaHoraCero(vo.getFechaConcertacion()));

		bitacoraOperaciones.setFechaLiquidacion(dateUtilsDao.getFechaHoraCero(vo.getFechaValor()));

		bitacoraOperaciones.setFechaVencimiento(vo.getFechaVencimiento());

		bitacoraOperaciones.setReferenciaRelacionada(vo.getReferenciaRelacionada());

		bitacoraOperaciones.setMarcaCompra(isCompra?Integer.valueOf(1):Integer.valueOf(0));

		bitacoraOperaciones.setTipoInstruccion(isCancelacion?"CCA":"TEFV");
		bitacoraOperaciones.setDivisa("MXN");
		bitacoraOperaciones.setOrigenRegistro(ORIGEN_OPERACION_PORTAL);
		bitacoraOperaciones.setCargoParticipante(UtilsDaliVO.CERO_INT);

		Date fechaActual = dateUtilsDao.getDateFechaDB();

		if (fechaActual == null) {
			throw new BusinessDataException(errorResolver.getMessage("J0001", new Object[] { "fecha actual" }), "J0001");
		}
		bitacoraOperaciones.setFechaHoraAlta(fechaActual);
		bitacoraOperaciones.setFechaRegistro(dateUtilsDao.getFechaHoraCero(fechaActual));
		bitacoraOperaciones.setEstatusRegistro(ESTATUS_NO_ENVIADO);



		if (datosAdicionales != null && !datosAdicionales.isEmpty() && bitacoraOperaciones.getDatosAdicionales() == null) {
			String xml = utilService.mapaToXml(datosAdicionales);
			Clob clob = new ClobImpl(xml);
			bitacoraOperaciones.setDatosAdicionales(clob);
		}

		if (bitacoraOperacionesDaliDao.save(bitacoraOperaciones) == null) {
			throw new BusinessDataException(errorResolver.getMessage("J0048", new Object[] { "bitacoraOperaciones" }), "J0048");

		}
		log.info("Saliendo de EnvioOperacionesServiceImpl.setBitacora(CallMoneyVO)");

	}

	/**
	 * @param institucionDAOgetFechaHoraCero
	 *            the institucionDAO to set
	 */
	public void setInstitucionDAO(InstitucionDaliDAO institucionDAO) {
		this.institucionDAO = institucionDAO;
	}

	public TraspasoContraPago getDvpService() {
		return dvpService;
	}

	public TraspasoLibrePago getTlpService() {
		return tlpService;
	}

	public Efectivo getEfectivo() {
		return efectivo;
	}

	public void setEfectivo(Efectivo efectivo) {
		this.efectivo = efectivo;
	}

	public AdministracionCuentasRetiroService getAdmonCuentasRetiroEfectivoService() {
		return admonCuentasRetiroEfectivoService;
	}

	public void setAdmonCuentasRetiroEfectivoService(
			AdministracionCuentasRetiroService admonCuentasRetiroEfectivoService) {
		this.admonCuentasRetiroEfectivoService = admonCuentasRetiroEfectivoService;
	}

	public TesoreriaService getTesoreriaService() {
		return tesoreriaService;
	}

	public void setTesoreriaService(TesoreriaService tesoreriaService) {
		this.tesoreriaService = tesoreriaService;
	}

	public void setBovedaDao(BovedaDaliDAO bovedaDao) {
		this.bovedaDao = bovedaDao;
	}


}
