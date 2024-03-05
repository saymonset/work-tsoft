/*
 *Copyright (c) 2005-2007 Bursatec. All Rights Reserved 
 */
package com.indeval.portaldali.presentation.controller.mercadodinero.aperturasistema;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CriterioOrdenamientoDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.EmisoraDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.dto.SerieDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.dto.util.ResultadoValidacionDTO;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCuentaConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCustodiaConstants;
import com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService;
import com.indeval.portaldali.middleware.services.estadocuenta.ConsultaPosicionService;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.mercadodinero.TraspasoMercadoDineroParams;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.InfrastructureException;
import com.indeval.portaldali.middleware.services.util.FechasUtilService;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.middleware.services.util.util.Constantes;
import com.indeval.portaldali.persistence.dao.common.EmisionDaliDAO;
import com.indeval.portaldali.presentation.common.constants.UtilConstantes;
import com.indeval.portaldali.presentation.controller.mercadodinero.capturaoperaciones.CapturaOperacionesController;
import com.indeval.portaldali.presentation.controller.mercadodinero.capturaoperaciones.constants.CapturaOperacionesConstants;
import com.indeval.portaldali.presentation.dto.mercadodinero.AperturaDeSistemaDTO;
import com.indeval.portaldali.presentation.helper.CalculoCapturaOperacionViewHelper;
import com.indeval.portaldali.presentation.helper.ServicesCapturaOperacionViewHelper;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;
import com.indeval.portaldali.presentation.validation.DTOValidator;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

/**
 * Controller para la pantalla de captura de Apertura de Sistemas
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 */
public class AperturaDeSistemaController extends CapturaOperacionesController {

	/** El DTO que representara */
	private AperturaDeSistemaDTO aperturaDeSistema = new AperturaDeSistemaDTO();
	/** La clase que permite el acceso a la consulta de los catálogos utilizados */
	private ConsultaCatalogosFacade consultaCatalogosFacade = null;
	/** Servicio de mercado de dinero */
	private MercadoDineroService mercadoDineroService;
	/** validador Apertuta Sistema */
	private DTOValidator validadorAS;
	/** Servicio helper para la captura de operaciones */
	private ServicesCapturaOperacionViewHelper servicesCapturaOperacionViewHelper = null;
	/** Helper de calculos */
	private CalculoCapturaOperacionViewHelper calculos = new CalculoCapturaOperacionViewHelper();
	/** campo para saber si hubo compra */
	private boolean isCompra;
	/** Servicio de envio de operaciones */
	private EnvioOperacionesService envioOperacionesService;
	/** Serivicio de captura de operaciones */
	private ServicesCapturaOperacionViewHelper serviceCapturaViewHelper;
	/** Valid Object de Traspaso Libre de Pago */
	private TraspasoLibrePagoVO tlpvo = null;
	/** Servicio para asignar folio de una secuencia */
	private UtilServices utilService;

	/** Identificadores del mercado a filtrar */
	private List<Long> idMercadoDinero = null;
	/** Dao para la obtención de la emisión */
	private EmisionDaliDAO emisionDaliDAO = null;
	/** Servicio para la obtencion de la posicion */
	private ConsultaPosicionService consultaPosicionService;
	/** Servicio para el acceso al cálculo de fechas */
	private FechasUtilService fechasUtilService = null;

	/**
	 * método Constructor.
	 */
	public String getInitForm() {
		aperturaDeSistema.setRecepcion(Boolean.FALSE);
		aperturaDeSistema.setTipoApertura(CapturaOperacionesConstants.APERTURA_CARGO_TRASPASANTE);
		aperturaDeSistema.setIdFolioTraspasante(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion());
		aperturaDeSistema.getPosicionTraspasante().getCuenta().setInstitucion(getInstitucionActual());
		return null;
	}

	/**
	 * Ejecuta las actividades necesarias de inicialización de la pantalla.
	 * 
	 * @return <code>null</code>. No es necesario un valor de retorno.
	 */
	public String getInit() {
		aperturaDeSistema.setTipoApertura(CapturaOperacionesConstants.APERTURA_CARGO_TRASPASANTE);
		
		if (aperturaDeSistema.getIdFolioTraspasante() != null) {
			InstitucionDTO inst = consultaCatalogosFacade.buscarInstitucionPorIdFolio(aperturaDeSistema.getIdFolioTraspasante());
			if (inst != null) {
				aperturaDeSistema.getPosicionTraspasante().getCuenta().setInstitucion(inst);
			} else {
				aperturaDeSistema.setIdFolioTraspasante(StringUtils.EMPTY);
			}
		}
		if (aperturaDeSistema.getPosicionTraspasante().getCuenta().getCuenta() != null) {
			CuentaDTO cnt = consultaCatalogosFacade.buscarCuentaPorNumeroCuentaNullSiNoExiste(aperturaDeSistema.getIdFolioTraspasante()
					+ aperturaDeSistema.getPosicionTraspasante().getCuenta().getCuenta());
			if (cnt != null) {
				aperturaDeSistema.getPosicionTraspasante().setCuenta(cnt);
			} else {

				aperturaDeSistema.getPosicionTraspasante().getCuenta().setTipoTenencia(new TipoTenenciaDTO());
				aperturaDeSistema.getPosicionTraspasante().getCuenta().setNumeroCuenta(StringUtils.EMPTY);
				aperturaDeSistema.getPosicionTraspasante().getCuenta().setCuenta(StringUtils.EMPTY);
				aperturaDeSistema.getPosicionTraspasante().getCuenta().setNombreCuenta(StringUtils.EMPTY);
			}
		}
		if (aperturaDeSistema.getIdFolioReceptor() != null) {
			InstitucionDTO inst = consultaCatalogosFacade.buscarInstitucionPorIdFolio(aperturaDeSistema.getIdFolioReceptor());
			if (inst != null) {
				aperturaDeSistema.getCuentaReceptor().setInstitucion(inst);
			} else {
				aperturaDeSistema.setIdFolioReceptor(StringUtils.EMPTY);
				aperturaDeSistema.setCuentaReceptor(new CuentaDTO());
			}
		}
		if (aperturaDeSistema.getCuentaReceptor().getCuenta() != null) {
			CuentaDTO cnt = consultaCatalogosFacade.buscarCuentaPorNumeroCuentaNullSiNoExiste(aperturaDeSistema.getIdFolioReceptor()
					+ aperturaDeSistema.getCuentaReceptor().getCuenta());
			if (cnt != null) {
				aperturaDeSistema.setCuentaReceptor(cnt);
			} else {
				aperturaDeSistema.getCuentaReceptor().setTipoTenencia(new TipoTenenciaDTO());
				aperturaDeSistema.getCuentaReceptor().setNumeroCuenta(StringUtils.EMPTY);
				aperturaDeSistema.getCuentaReceptor().setCuenta(StringUtils.EMPTY);
				aperturaDeSistema.getCuentaReceptor().setNombreCuenta(StringUtils.EMPTY);
			}
		}
		// obtener los datos de la posicion
		obtenerDatosPosicion();
		return null;
	}

	/**
	 * ActionListener para la casilla de recepción que permite seleccionar si la
	 * apertura se realiza con recepcion o no.
	 * 
	 * @param event
	 *            el evento que dispar este ActionListener
	 */
	public void cambioRecepcion(ActionEvent event) {
		aperturaDeSistema.setPosicionTraspasante(new PosicionDTO());
		aperturaDeSistema.setCuentaReceptor(new CuentaDTO());
		aperturaDeSistema.setCantidadOperada(null);
		aperturaDeSistema.setFechaAdquisicion(null);
		aperturaDeSistema.setPrecioAdquisicion(null);
		aperturaDeSistema.setCliente(null);

		if (aperturaDeSistema.getRecepcion() != null && aperturaDeSistema.getRecepcion().booleanValue()) {
			aperturaDeSistema.getPosicionTraspasante().setCuenta(new CuentaDTO());
			aperturaDeSistema.setIdFolioTraspasante(StringUtils.EMPTY);
			aperturaDeSistema.setCuentaReceptor(new CuentaDTO());
			aperturaDeSistema.setIdFolioReceptor(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion());
			getInit();
		} else {
			aperturaDeSistema.getPosicionTraspasante().setCuenta(new CuentaDTO());
			aperturaDeSistema.setIdFolioTraspasante(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion());
			aperturaDeSistema.setCuentaReceptor(new CuentaDTO());
			aperturaDeSistema.setIdFolioReceptor(StringUtils.EMPTY);
			getInit();
		}
	}

	/**
	 * Indica si la apertura es con cargo al participante traspasante  es con
	 * cargo al receptor.
	 * 
	 * @return <code>true</code> si se trata de una apertura con cargo al
	 *         traspasante. <code>false</code> si se trata de una apertura con
	 *         cargo al receptor.
	 */
	public boolean isCargoTraspasante() {
		return aperturaDeSistema.getTipoApertura() != null
				&& aperturaDeSistema.getTipoApertura().equals(CapturaOperacionesConstants.APERTURA_CARGO_TRASPASANTE);
	}

	/**
	 * Indica si se trata de una recepción o no.
	 * 
	 * @return <code>true</code> si se trata de una recepción o no.
	 *         <code>false</code> en cualquier otro caso.
	 */
	public boolean isRecepcion() {
		return aperturaDeSistema.getRecepcion() != null && aperturaDeSistema.getRecepcion().booleanValue();
	}

	/**
	 * ActionListener para el boton que permite persistir la operacion capturada
	 * por el usuario.
	 * 
	 * @param event el evento que dispara este ActionListener
	 */
	public void enviarOperacion(ActionEvent event) {
		super.limpiarCampos();
		
		try {				
			aperturaDeSistema.getPosicionTraspasante().getCuenta().setInstitucion(
					consultaCatalogosFacade.buscarInstitucionPorIdNoNulaFolio(aperturaDeSistema.getIdFolioTraspasante()));
			aperturaDeSistema.getCuentaReceptor().setInstitucion(
					consultaCatalogosFacade.buscarInstitucionPorIdNoNulaFolio(aperturaDeSistema.getIdFolioReceptor()));
			// Si el usuario debe firmar la operacion, se recuperar la firma.
			// Si no se ha firmado, se procesan los datos y regresa el control a la pantalla para firmar
			if (isUsuarioConFacultadFirmar()) {
				//Valida la vigencia del certificado - 18-Agosto-2014 Pablo Balderas
				validarVigenciaCertificado();
			}
			
			procesarDatos();
		}
		catch(BusinessException businessException) {
			addMessage(businessException.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}

	public void enviarOperacion2(ActionEvent event) {
		if (isUsuarioConFacultadFirmar()) {
			validarVigenciaCertificado();
			recuperarCamposFirma();
			if (!StringUtils.isEmpty(isoFirmado)) {
				try {
					grabarOperacion(event);
				}catch(BusinessException e) {
					addMessage(e.getMessage(), FacesMessage.SEVERITY_ERROR);
				}
			}
		}
		
		super.limpiarCampos();
	}
	/**
	 * Valida, Calcula y registra la operacion antes de firmar
	 */
	private void procesarDatos() {
		boolean valido = validarDTO();
		if (valido) {
			registrarOperacion();
		}
	}

	/**
	 * Guarda la operacion firmada
	 * 
	 * @param e
	 */
	public void grabarOperacion(ActionEvent e) {
		if (!cdb.validation(hashIso, isoSinFirmar)) {
			throw new InfrastructureException(UtilConstantes.ERROR_ISO_DIFERENTE);
		}
		isoFirmado = (new StringBuilder()).append(isoSinFirmar).append(numeroSerie).append("\n").append("{SHA1withRSA}").append(isoFirmado)
				.toString();

		enviarOperacionABitacora();
	}

	/**
	 * Envia la operacion a bitacora
	 */
	private void enviarOperacionABitacora() {
		isCompra = aperturaDeSistema.getRecepcion() != null ? aperturaDeSistema.getRecepcion() : false;
		
		String folioControl = tlpvo.getReferenciaOperacion();

		HashMap<String, String> datosAdicionales = new HashMap<String, String>();     
		datosAdicionales.put(SeguridadConstants.USR_CREDENCIAL, 
				(String)FacesContext.getCurrentInstance().getExternalContext().
				getSessionMap().get(SeguridadConstants.TICKET_SESION));	
		 
		envioOperacionesService.grabaOperacion(tlpvo, folioControl, isCompra, datosAdicionales, null, isoFirmado);

		if (!existeErrorEnInvocacion()) {
			mensajeUsuario = "La informacion se ha guardado y se ha generado el Folio: " + folioAsignado;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensajeUsuario, mensajeUsuario));
			aperturaDeSistema = new AperturaDeSistemaDTO();
			limpiar(null);
		}
	}

	/**
	 * Metodo para validar la existencia de los campos requeridos y su formato.
	 */
	private boolean validarDTO() {
		ResultadoValidacionDTO resultadao = null;
		resultadao = validadorAS.validarDTO(aperturaDeSistema);

		if (!resultadao.isValido()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resultadao.getMensaje(), resultadao.getMensaje()));
		}
		return resultadao.isValido();
	}

	/**
	 * Realiza los cálculos simulados de la operacicn y construye el objeto de
	 * parámetros que el servicio de negocio espera
	 */
	private void registrarOperacion() {
		TraspasoMercadoDineroParams params = new TraspasoMercadoDineroParams();

		// Tipo de Movimiento
		params.setTipoMovimiento(TraspasoMercadoDineroParams.APERTURA);

		// SE TRATA DE MERCADO DE DINERO
		params.setMercadoDinero(true);

		// --- AGENTE TRASPASANTE
		params.setTraspasante(DTOAssembler.crearAgenteVO(aperturaDeSistema.getPosicionTraspasante().getCuenta()));

		// --- AGENTE DEL RECEPTOR
		params.setReceptor(DTOAssembler.crearAgenteVO(aperturaDeSistema.getCuentaReceptor()));
		params.setRfcCURP(aperturaDeSistema.getRfcCurp());
		params.setEmision(new EmisionVO());

		// -- TIPO VALOR, --EMISORA, --SERIE, --CUPON, ---ISIN
		params.getEmision().setIdTipoValor(aperturaDeSistema.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor());
		params.getEmision().setEmisora(aperturaDeSistema.getPosicionTraspasante().getEmision().getEmisora().getDescripcion());
		params.getEmision().setSerie(aperturaDeSistema.getPosicionTraspasante().getEmision().getSerie().getSerie());
		params.getEmision().setCupon(aperturaDeSistema.getPosicionTraspasante().getEmision().getCupon());
		params.getEmision().setIsin(aperturaDeSistema.getPosicionTraspasante().getEmision().getIsin());

		// --- SALDO DISPONIBLE
		if (aperturaDeSistema.getSaldoDisponible() != null) {
			params.getEmision().setSaldoDisponible(new BigDecimal(aperturaDeSistema.getSaldoDisponible().doubleValue()));
		}

		// --- CANTIDAD
		if (aperturaDeSistema.getCantidadOperada() != null) {
			params.setCantidad(new BigDecimal(aperturaDeSistema.getCantidadOperada()));
		}
		
		params.setFechaHoraCierreOper(aperturaDeSistema.getFechaHoraCierreOper());

		// -- CLIENTE, --FECHA ADQUISICION, --TIPO OPERACION, PRECIO ADQUISICION
		// params.setCliente(aperturaDeSistema.getCliente());
		// params.setFechaAdquisicion(aperturaDeSistema.getFechaAdquisicion());
		// params.setIdTipoOperacion(aperturaDeSistema.getTipoApertura().toString());
		// params.setPrecioAdquisicion(new
		// BigDecimal(aperturaDeSistema.getPrecioAdquisicion()));
		//      
		BigInteger folioAsignadoBI;
		folioAsignadoBI = mercadoDineroService.traspasoMercadoDineroBusinessRules(params);
		if (folioAsignadoBI != null) {
			folioAsignado = folioAsignadoBI.toString();
		}
		if (!existeErrorEnInvocacion()) {

			String tipoOperacionLocal = "";
			if (aperturaDeSistema.getTipoApertura().equals(0)) {
				tipoOperacionLocal = "H";
			} else {
				tipoOperacionLocal = "Z";
			}

			tlpvo = llenaVO(params, tipoOperacionLocal);
			tlpvo.setReferenciaMensaje(utilService.getFolio(Constantes.SEQ_REFERENCIA_MENSAJE).toString());	
			 
			// Si el usuario debe firmar la operacion, se crea el iso
			if (isUsuarioConFacultadFirmar()) {
				isoSinFirmar = isoHelper.creaISO(tlpvo, aperturaDeSistema.getRecepcion());
				hashIso = cdb.cipherHash(isoSinFirmar);
			} else {
				enviarOperacionABitacora();
			}
		}

	}

	/**
	 * Metodo auxiliar para llenar el VO de Traspaso de Miscelnea Fiscal de
	 * Mercado de Dinero
	 * 
	 * @param params
	 * @return
	 */
	private TraspasoLibrePagoVO llenaVO(TraspasoMercadoDineroParams params, String tipoOperacion) {
		TraspasoLibrePagoVO vo = new TraspasoLibrePagoVO();

		vo.setTipoValor(params.getEmision().getIdTipoValor().trim());
		vo.setEmisora(params.getEmision().getEmisora().trim());
		vo.setSerie(params.getEmision().getSerie().trim());
		vo.setCupon(params.getEmision().getCupon().trim());
		vo.setCantidadTitulos(new Long(params.getCantidad().longValue()));
		vo.setFechaRegistro(serviceCapturaViewHelper.getFechasUtilService().getCurrentDate());
		vo.setFechaLiquidacion(serviceCapturaViewHelper.getFechasUtilService().getCurrentDate());
		vo.setFechaHoraCierreOper(params.getFechaHoraCierreOper());

		vo.setCargoParticipante(params.isAceptaCargo());
		vo.setIdFolioCtaReceptora(params.getReceptor().getId() + params.getReceptor().getFolio() + params.getReceptor().getCuenta());

		vo.setIdFolioCtaTraspasante(params.getTraspasante().getId() + params.getTraspasante().getFolio() + params.getTraspasante().getCuenta());
		vo.setTipoInstruccion(tipoOperacion);
		vo.setReferenciaOperacion(folioAsignado);
		if( null != aperturaDeSistema.getPosicionTraspasante().getBoveda()  && aperturaDeSistema.getPosicionTraspasante().getBoveda().getId() >0 ){
			BovedaDTO boveda =  consultaCatalogosFacade.buscarBovedaPorId(aperturaDeSistema.getPosicionTraspasante().getBoveda().getId());
			vo.setBoveda( null != boveda ? boveda.getNombreCorto() : null);
		}	

		return vo;
	}

	/**
	 * Metodo para calcular el saldo actual
	 * 
	 * @param event
	 */
	public void realizaCalculoSaldoActual(ActionEvent event) {
		if (aperturaDeSistema.getCantidadOperada() != null && aperturaDeSistema.getCantidadOperada() > 0) {
			if (aperturaDeSistema.getSaldoDisponible() != null) {
				aperturaDeSistema.setSaldoActual(aperturaDeSistema.getSaldoDisponible() - aperturaDeSistema.getCantidadOperada());
			}
		} else {
			aperturaDeSistema.setSaldoActual(new Double(0.0));
		}

	}

	/**
	 * Valida que la cuenta del Trasnpasante no sea igual a la cuenta del
	 * Receptor
	 * 
	 * @param e
	 */
	public String validarCuenta(ActionEvent e) {

		if (aperturaDeSistema.getPosicionTraspasante().getCuenta().getCuenta() != null
				&& !aperturaDeSistema.getPosicionTraspasante().getCuenta().getCuenta().equals("")) {
			if (aperturaDeSistema.getPosicionTraspasante().getCuenta().getCuenta().equals(aperturaDeSistema.getCuentaReceptor().getCuenta())
					&& aperturaDeSistema.getIdFolioReceptor().equals(aperturaDeSistema.getIdFolioTraspasante())) {
				mensajeUsuario = "La cuenta traspasante no puede ser la misma que la del receptor";
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensajeUsuario, mensajeUsuario));
				if (isCompra == Boolean.TRUE) {
					aperturaDeSistema.getPosicionTraspasante().getCuenta().setCuenta("");
				} else {
					aperturaDeSistema.getCuentaReceptor().setCuenta("");
				}
			}
		}
		return mensajeUsuario;

	}

	/** Realizar el calculo para obtener el simulado del saldo actual. */
	public void realizarCalculo(ActionEvent e) {

		if (aperturaDeSistema.getTipoApertura().equals(0)) {
			calculos.setTipoOperacion("H");
		} else {
			calculos.setTipoOperacion("Z");
		}

		calculos.setCantidadOperadaLeida(aperturaDeSistema.getCantidadOperada() != null ? aperturaDeSistema.getCantidadOperada().toString() : "");
		calculos.setCantidadOperada(new BigDecimal(aperturaDeSistema.getCantidadOperada() != null ? aperturaDeSistema.getCantidadOperada().doubleValue() : 0));
		calculos.setSaldoDisponibleLeido(aperturaDeSistema.getSaldoDisponible() != null ? aperturaDeSistema.getSaldoDisponible().toString() : "");

		servicesCapturaOperacionViewHelper.realizarCalculos(calculos);

		/*
		 * Realiza el cálculo del saldo Simulado se necesitan:
		 * cantidadOperadaLeida, saldoDisponibleLeido
		 */
		aperturaDeSistema.setSaldoActual((calculos.getSimulado() != null ? calculos.getSimulado().doubleValue() : null));

	}

	/**
	 * ActionListener para el botn que permite limpiar los campos de la forma
	 * de captura.
	 * 
	 * @param event
	 *            el evento que dispar este ActionListener
	 */
	public void limpiar(ActionEvent event) {
		aperturaDeSistema = new AperturaDeSistemaDTO();

		aperturaDeSistema.setRecepcion(Boolean.FALSE);
		aperturaDeSistema.setIdFolioTraspasante(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion());
		aperturaDeSistema.getPosicionTraspasante().getCuenta().setInstitucion(getInstitucionActual());
		super.limpiarCampos();
		cambioRecepcion(event);
	}

	/**
	 * Metodo para la obtencion de la Posicion a partir de los campos
	 * idFolioParticiante, TV, Emisora, Serie.
	 * 
	 * return <code>null</code>. No es necesario un valor de retorno.
	 */
	public String obtenerDatosPosicion() {
		// primero se obtiene la Posicion
		PosicionDTO posicion = null;
		if (StringUtils.isNotEmpty(aperturaDeSistema.getPosicionTraspasante().getCuenta().getCuenta())
				&& StringUtils.isNotEmpty(aperturaDeSistema.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor())
				&& StringUtils.isNotEmpty(aperturaDeSistema.getPosicionTraspasante().getEmision().getEmisora().getDescripcion())
				&& StringUtils.isNotEmpty(aperturaDeSistema.getPosicionTraspasante().getEmision().getSerie().getSerie())) {
			PosicionDTO criterio = new PosicionDTO();

			criterio.setCuenta(new CuentaDTO());
			criterio.getCuenta().setTipoCustodia(TipoCustodiaConstants.VALORES);
			criterio.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
			criterio.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA, ""));
			criterio.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
			criterio.getCuenta().setNumeroCuenta(
					getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion()
							+ aperturaDeSistema.getPosicionTraspasante().getCuenta().getCuenta());
			criterio.setEmision(new EmisionDTO());
			InstitucionDTO institucion = null;
			institucion = consultaCatalogosFacade.buscarInstitucionPorIdFolio(aperturaDeSistema.getIdFolioTraspasante());

			if (institucion != null) {
				// cuenta
				if (StringUtils.isNotEmpty(aperturaDeSistema.getPosicionTraspasante().getCuenta().getCuenta())) {
					criterio.getCuenta().setNumeroCuenta(
							institucion.getClaveTipoInstitucion() + institucion.getFolioInstitucion()
									+ aperturaDeSistema.getPosicionTraspasante().getCuenta().getCuenta());
				} else {
					criterio.getCuenta().setCuenta("-1");
				}

				// TV
				criterio.getEmision().setTipoValor(
						consultaCatalogosFacade.buscarTipoValorPorClave(aperturaDeSistema.getPosicionTraspasante().getEmision().getTipoValor()
								.getClaveTipoValor()));
				// Emisora
				criterio.getEmision().setEmisora(
						consultaCatalogosFacade.buscarEmisoraPorNombreCorto(aperturaDeSistema.getPosicionTraspasante().getEmision().getEmisora()
								.getDescripcion()));
				criterio.getEmision().setSerie(new SerieDTO());
				// Serie
				criterio.getEmision().getSerie().setSerie(aperturaDeSistema.getPosicionTraspasante().getEmision().getSerie().getSerie().trim());

				if (aperturaDeSistema.getPosicionTraspasante().getBoveda() != null && aperturaDeSistema.getPosicionTraspasante().getBoveda().getId() > 0) {
					criterio.setBoveda(new BovedaDTO());
					criterio.getBoveda().setId(aperturaDeSistema.getPosicionTraspasante().getBoveda().getId());
				}
				criterio.getBoveda().setId(NumberUtils.toLong("-1", DaliConstants.VALOR_COMBO_TODOS));
				criterio.getCuenta().setInstitucion(institucion);
				criterio.setFechaFinal(new Date());

				CriterioOrdenamientoDTO orden = new CriterioOrdenamientoDTO();
				orden.setColumna("sortCuenta");

				List<PosicionDTO> listaPosiciones = consultaPosicionService.consultarPosicionesPorMercado(criterio, null, orden, idMercadoDinero
						.toArray(new Long[] {}));

				if (listaPosiciones != null && !listaPosiciones.isEmpty()) {
					posicion = listaPosiciones.get(0);
					// obtencion de los datos para ser desplegado en pantalla
					aperturaDeSistema.getPosicionTraspasante().getCuenta().setCuenta(posicion.getCuenta().getCuenta());
					aperturaDeSistema.getPosicionTraspasante().getEmision().getEmisora().setDescripcion(posicion.getEmision().getEmisora().getDescripcion());
					aperturaDeSistema.getPosicionTraspasante().getEmision().setCupon(posicion.getEmision().getCupon());
					aperturaDeSistema.getPosicionTraspasante().getEmision().setIsin(posicion.getEmision().getIsin());
					aperturaDeSistema.getPosicionTraspasante().setBoveda(new BovedaDTO());
					aperturaDeSistema.getPosicionTraspasante().getBoveda().setNombreCorto(posicion.getBoveda().getNombreCorto());
					aperturaDeSistema.getPosicionTraspasante().getBoveda().setId(posicion.getBoveda().getId());
					aperturaDeSistema.setSaldoDisponible(new BigDecimal(posicion.getPosicionDisponible()).doubleValue());

				}
			} else {

				aperturaDeSistema.getPosicionTraspasante().getEmision().setCupon(null);
				aperturaDeSistema.getPosicionTraspasante().getEmision().setIsin(null);
				aperturaDeSistema.getPosicionTraspasante().getEmision().getTipoValor().getMercado().setClaveMercado(null);
				aperturaDeSistema.getPosicionTraspasante().setBoveda(null);
				aperturaDeSistema.setSaldoDisponible(null);
			}

		} 
		
		if (posicion== null && StringUtils.isNotEmpty(aperturaDeSistema.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor())
				&& StringUtils.isNotEmpty(aperturaDeSistema.getPosicionTraspasante().getEmision().getEmisora().getDescripcion())
				&& StringUtils.isNotEmpty(aperturaDeSistema.getPosicionTraspasante().getEmision().getSerie().getSerie())) {

			EmisionDTO criterio = new EmisionDTO();
			criterio.setTipoValor(new TipoValorDTO());
			criterio.getTipoValor().setClaveTipoValor(aperturaDeSistema.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor());
			criterio.setEmisora(new EmisoraDTO());
			criterio.getEmisora().setDescripcion(aperturaDeSistema.getPosicionTraspasante().getEmision().getEmisora().getDescripcion());
			criterio.setSerie(new SerieDTO());
			criterio.getSerie().setSerie(aperturaDeSistema.getPosicionTraspasante().getEmision().getSerie().getSerie());
			List<EmisionDTO> listaEmisiones = emisionDaliDAO.consultarEmisionesPorDescripciones(criterio, null);

			if (listaEmisiones != null && !listaEmisiones.isEmpty() && listaEmisiones.size() == 1) {
				// pasar los datos al objeto de la pantalla
				aperturaDeSistema.getPosicionTraspasante().getEmision().getEmisora().setDescripcion(listaEmisiones.get(0).getEmisora().getDescripcion());
				aperturaDeSistema.getPosicionTraspasante().getEmision().getTipoValor().setClaveTipoValor(
						listaEmisiones.get(0).getTipoValor().getClaveTipoValor());
				aperturaDeSistema.getPosicionTraspasante().getEmision().getSerie().setSerie(listaEmisiones.get(0).getSerie().getSerie());
				aperturaDeSistema.getPosicionTraspasante().getEmision().setIsin(listaEmisiones.get(0).getIsin());
				aperturaDeSistema.getPosicionTraspasante().getEmision().setCupon(listaEmisiones.get(0).getCupon());
				
				
				aperturaDeSistema.getPosicionTraspasante().setBoveda(new BovedaDTO());
				aperturaDeSistema.getPosicionTraspasante().getBoveda().setNombreCorto(listaEmisiones.get(0).getBoveda().getNombreCorto());
				aperturaDeSistema.getPosicionTraspasante().getBoveda().setId(listaEmisiones.get(0).getBoveda().getId());
				
			} else {
				aperturaDeSistema.getPosicionTraspasante().getEmision().setCupon(null);
				aperturaDeSistema.getPosicionTraspasante().getEmision().setIsin(null);
				aperturaDeSistema.getPosicionTraspasante().getEmision().getTipoValor().getMercado().setClaveMercado(null);
				aperturaDeSistema.getPosicionTraspasante().setBoveda(null);
				aperturaDeSistema.setSaldoDisponible(null);
			}

		}
		return null;
	}

	/** métodos getters & setters. */

	/**
	 * Obtiene el campo aperturaDeSistema
	 * 
	 * @return aperturaDeSistema
	 */
	public AperturaDeSistemaDTO getAperturaDeSistema() {

		return aperturaDeSistema;
	}

	/**
	 * Asigna el campo aperturaDeSistema
	 * 
	 * @param aperturaDeSistema
	 *            el valor de aperturaDeSistema a asignar
	 */
	public void setAperturaDeSistema(AperturaDeSistemaDTO aperturaDeSistema) {
		this.aperturaDeSistema = aperturaDeSistema;
	}

	/**
	 * Obtiene el campo consultaCatalogosFacade
	 * 
	 * @return consultaCatalogosFacade
	 */
	public ConsultaCatalogosFacade getConsultaCatalogosFacade() {
		return consultaCatalogosFacade;
	}

	/**
	 * Asigna el campo consultaCatalogosFacade
	 * 
	 * @param consultaCatalogosFacade
	 *            el valor de consultaCatalogosFacade a asignar
	 */
	public void setConsultaCatalogosFacade(ConsultaCatalogosFacade consultaCatalogosFacade) {
		this.consultaCatalogosFacade = consultaCatalogosFacade;
	}

	/**
	 * Obtiene el campo validadorAperturaDeSistema
	 * 
	 * @return validadorAperturaDeSistema
	 */
	public DTOValidator getValidadorAS() {
		return validadorAS;
	}

	/**
	 * Asigna el campo validadorAperturaDeSistema
	 * 
	 * @param validadorAperturaDeSistema
	 *            el valor de validadorAperturaDeSistema a asignar
	 */
	public void setValidadorAS(DTOValidator validadorAperturaDeSistema) {
		this.validadorAS = validadorAperturaDeSistema;
	}

	/**
	 * @return the mercadoService
	 */
	public MercadoDineroService getMercadoDineroService() {
		return mercadoDineroService;
	}

	/**
	 * @param mercadoService
	 *            the mercadoService to set
	 */
	public void setMercadoDineroService(MercadoDineroService mercadoService) {
		this.mercadoDineroService = mercadoService;
	}

	/**
	 * @return the servicesCapturaOperacionViewHelper
	 */
	public ServicesCapturaOperacionViewHelper getServicesCapturaOperacionViewHelper() {
		return servicesCapturaOperacionViewHelper;
	}

	/**
	 * @param servicesCapturaOperacionViewHelper
	 *            the servicesCapturaOperacionViewHelper to set
	 */
	public void setServicesCapturaOperacionViewHelper(ServicesCapturaOperacionViewHelper servicesCapturaOperacionViewHelper) {
		this.servicesCapturaOperacionViewHelper = servicesCapturaOperacionViewHelper;
	}

	/**
	 * @return the calculos
	 */
	public CalculoCapturaOperacionViewHelper getCalculos() {
		return calculos;
	}

	/**
	 * @param calculos
	 *            the calculos to set
	 */
	public void setCalculos(CalculoCapturaOperacionViewHelper calculos) {
		this.calculos = calculos;
	}

	/**
	 * Obtiene el valor del campo isCompra
	 * 
	 * @return el valor de isCompra
	 */
	public boolean isCompra() {
		return isCompra;
	}

	/**
	 * Asigna el campo isCompra
	 * 
	 * @param isCompra
	 *            el valor de isCompra a asignar
	 */
	public void setCompra(boolean isCompra) {
		this.isCompra = isCompra;
	}

	/**
	 * Obtiene el valor del campo envioOperacionesService
	 * 
	 * @return el valor de envioOperacionesService
	 */
	public EnvioOperacionesService getEnvioOperacionesService() {
		return envioOperacionesService;
	}

	/**
	 * Asigna el campo envioOperacionesService
	 * 
	 * @param envioOperacionesService
	 *            el valor de envioOperacionesService a asignar
	 */
	public void setEnvioOperacionesService(EnvioOperacionesService envioOperacionesService) {
		this.envioOperacionesService = envioOperacionesService;
	}

	/**
	 * Obtiene el valor del campo serviceCapturaViewHelper
	 * 
	 * @return el valor de serviceCapturaViewHelper
	 */
	public ServicesCapturaOperacionViewHelper getServiceCapturaViewHelper() {
		return serviceCapturaViewHelper;
	}

	/**
	 * Asigna el campo serviceCapturaViewHelper
	 * 
	 * @param serviceCapturaViewHelper
	 *            el valor de serviceCapturaViewHelper a asignar
	 */
	public void setServiceCapturaViewHelper(ServicesCapturaOperacionViewHelper serviceCapturaViewHelper) {
		this.serviceCapturaViewHelper = serviceCapturaViewHelper;
	}

	/**
	 * Obtiene el valor del campo tlpvo
	 * 
	 * @return el valor de tlpvo
	 */
	public TraspasoLibrePagoVO getTlpvo() {
		return tlpvo;
	}

	/**
	 * Asigna el campo tlpvo
	 * 
	 * @param tlpvo
	 *            el valor de tlpvo a asignar
	 */
	public void setTlpvo(TraspasoLibrePagoVO tlpvo) {
		this.tlpvo = tlpvo;
	}

	/**
	 * @return the utilService
	 */
	public UtilServices getUtilService() {
		return utilService;
	}

	/**
	 * @param utilService
	 *            the utilService to set
	 */
	public void setUtilService(UtilServices utilService) {
		this.utilService = utilService;
	}

	/**
	 * Obtiene el valor del atributo idMercadoDinero
	 * 
	 * @return el valor del atributo idMercadoDinero
	 */
	public List<Long> getIdMercadoDinero() {
		return idMercadoDinero;
	}

	/**
	 * Establece el valor del atributo idMercadoDinero
	 * 
	 * @param idMercadoDinero
	 *            el valor del atributo idMercadoDinero a establecer
	 */
	public void setIdMercadoDinero(List<Long> idMercadoDinero) {
		this.idMercadoDinero = idMercadoDinero;
	}

	/**
	 * Obtiene el valor del atributo emisionDaliDAO
	 * 
	 * @return el valor del atributo emisionDaliDAO
	 */
	public EmisionDaliDAO getEmisionDAO() {
		return emisionDaliDAO;
	}

	/**
	 * Establece el valor del atributo emisionDaliDAO
	 * 
	 * @param emisionDaliDAO
	 *            el valor del atributo emisionDaliDAO a establecer
	 */
	public void setEmisionDAO(EmisionDaliDAO emisionDaliDAO) {
		this.emisionDaliDAO = emisionDaliDAO;
	}

	/**
	 * Obtiene el valor del atributo consultaPosicionService
	 * 
	 * @return el valor del atributo consultaPosicionService
	 */
	public ConsultaPosicionService getConsultaPosicionService() {
		return consultaPosicionService;
	}

	/**
	 * Establece el valor del atributo consultaPosicionService
	 * 
	 * @param consultaPosicionService
	 *            el valor del atributo consultaPosicionService a establecer
	 */
	public void setConsultaPosicionService(ConsultaPosicionService consultaPosicionService) {
		this.consultaPosicionService = consultaPosicionService;
	}

	/**
	 * Obtiene el valor del atributo fechasUtilService
	 * 
	 * @return el valor del atributo fechasUtilService
	 */
	public FechasUtilService getFechasUtilService() {
		return fechasUtilService;
	}

	/**
	 * Establece el valor del atributo fechasUtilService
	 * 
	 * @param fechasUtilService
	 *            el valor del atributo fechasUtilService a establecer
	 */
	public void setFechasUtilService(FechasUtilService fechasUtilService) {
		this.fechasUtilService = fechasUtilService;
	}

}