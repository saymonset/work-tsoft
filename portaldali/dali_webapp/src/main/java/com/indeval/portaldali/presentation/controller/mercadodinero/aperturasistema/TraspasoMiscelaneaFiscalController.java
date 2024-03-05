/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * TraspasoMiscelaneaFiscalController.java
 * Apr 24, 2008
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
import com.indeval.portaldali.presentation.common.constants.CamposPantallaConstantes;
import com.indeval.portaldali.presentation.common.constants.UtilConstantes;
import com.indeval.portaldali.presentation.controller.mercadodinero.capturaoperaciones.CapturaOperacionesController;
import com.indeval.portaldali.presentation.dto.mercadodinero.TraspasoMiscelaneaFiscalDTO;
import com.indeval.portaldali.presentation.helper.ServicesCapturaOperacionViewHelper;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;
import com.indeval.portaldali.presentation.validation.DTOValidator;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

/**
 * Implementación de un Controller para la pantalla de captura de Traspasos de
 * Miscelanea Fiscal.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public class TraspasoMiscelaneaFiscalController extends CapturaOperacionesController {

	/** DTO para almacenar los datos capturados por el usuario en la pantalla */
	private TraspasoMiscelaneaFiscalDTO traspasoMiscelaneaFiscal = new TraspasoMiscelaneaFiscalDTO();

	/** La clase que permite el acceso a la consulta de los catálogos utilizados */
	private ConsultaCatalogosFacade consultaCatalogosFacade = null;

	/** Validador de Miscelania Fiscal */
	private DTOValidator validadorMF;

	/** Validador de Miscelania Fiscal Recepción */
	private DTOValidator validadorMFR;

	/** fecha utilitaria servidor */
	private FechasUtilService fechaservice = null;

	/** Servicio de mercado de dinero */
	private MercadoDineroService mercadoDineroService;

	/** Servicio para sacar folios de secuencia */
	private UtilServices utilService = null;

	/**
	 * Servicio de negocio relacionado con el envo de operaciones
	 */
	private EnvioOperacionesService envioOperacionesService = null;

	/** Serivicio de captura de operaciones */
	private ServicesCapturaOperacionViewHelper servicesCapturaOperacionViewHelper;

	/** Valid Object de Traspaso Libre de Pago */
	private TraspasoLibrePagoVO tlpvo = null;

	private boolean editarTraspasante = Boolean.FALSE;

	private boolean editarReceptor = Boolean.TRUE;

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
		traspasoMiscelaneaFiscal.setRecepcion(Boolean.FALSE);
		traspasoMiscelaneaFiscal.setIdFolioTraspasante(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion());
		traspasoMiscelaneaFiscal.getPosicionTraspasante().getCuenta().setInstitucion(getInstitucionActual());
		traspasoMiscelaneaFiscal.setValorEn(MXP);
		return null;
	}

	/**
	 * Ejecuta las actividades necesarias de inicialización de la pantalla.
	 * 
	 * @return <code>null</code>. No es necesario un valor de retorno.
	 */
	public String getInit() {

		if (isUsuarioIndeval()) {
			editarReceptor = Boolean.TRUE;
			editarTraspasante = Boolean.TRUE;
		}

		InstitucionDTO inst = new InstitucionDTO();
		if (traspasoMiscelaneaFiscal.getIdFolioTraspasante() != null) {
			inst = consultaCatalogosFacade.buscarInstitucionPorIdFolio(traspasoMiscelaneaFiscal.getIdFolioTraspasante());
			if (inst != null) {
				traspasoMiscelaneaFiscal.getPosicionTraspasante().getCuenta().setInstitucion(inst);
			} else {
				traspasoMiscelaneaFiscal.setIdFolioTraspasante(StringUtils.EMPTY);
			}
		}
		if (traspasoMiscelaneaFiscal.getPosicionTraspasante().getCuenta().getCuenta() != null) {
			CuentaDTO cnt = consultaCatalogosFacade.buscarCuentaPorNumeroCuentaNullSiNoExiste(traspasoMiscelaneaFiscal.getIdFolioTraspasante()
					+ traspasoMiscelaneaFiscal.getPosicionTraspasante().getCuenta().getCuenta());
			if (cnt != null) {
				traspasoMiscelaneaFiscal.getPosicionTraspasante().setCuenta(cnt);
			} else {
				
				traspasoMiscelaneaFiscal.getPosicionTraspasante().getCuenta().setTipoTenencia(new TipoTenenciaDTO());
				traspasoMiscelaneaFiscal.getPosicionTraspasante().getCuenta().setNumeroCuenta(StringUtils.EMPTY);
				traspasoMiscelaneaFiscal.getPosicionTraspasante().getCuenta().setCuenta(StringUtils.EMPTY);
				traspasoMiscelaneaFiscal.getPosicionTraspasante().getCuenta().setNombreCuenta(StringUtils.EMPTY);
			}
		}
		if (traspasoMiscelaneaFiscal.getIdFolioReceptor() != null) {
			inst = consultaCatalogosFacade.buscarInstitucionPorIdFolio(traspasoMiscelaneaFiscal.getIdFolioReceptor());
			if (inst != null) {
				traspasoMiscelaneaFiscal.getCuentaReceptor().setInstitucion(inst);
			} else {
				traspasoMiscelaneaFiscal.setIdFolioReceptor(StringUtils.EMPTY);
				traspasoMiscelaneaFiscal.setCuentaReceptor(new CuentaDTO());
			}
		}
		if (traspasoMiscelaneaFiscal.getCuentaReceptor().getCuenta() != null) {
			CuentaDTO cnt = consultaCatalogosFacade.buscarCuentaPorNumeroCuentaNullSiNoExiste(traspasoMiscelaneaFiscal.getIdFolioReceptor()
					+ traspasoMiscelaneaFiscal.getCuentaReceptor().getCuenta());
			if (cnt != null) {
				traspasoMiscelaneaFiscal.setCuentaReceptor(cnt);
			} else {
				traspasoMiscelaneaFiscal.getCuentaReceptor().setTipoTenencia(new TipoTenenciaDTO());
				traspasoMiscelaneaFiscal.getCuentaReceptor().setNumeroCuenta(StringUtils.EMPTY);
				traspasoMiscelaneaFiscal.getCuentaReceptor().setCuenta(StringUtils.EMPTY);
				traspasoMiscelaneaFiscal.getCuentaReceptor().setNombreCuenta(StringUtils.EMPTY);
			}
		}
		//Inicializamo la fecha de adquisición con el día actual
		traspasoMiscelaneaFiscal.setFechaAdquisicion(getFechaActual());
		// obtener los datos de la posicion
		obtenerDatosPosicion();
		return null;
	}

	/**
	 * Indica si se trata de una recepción o no.
	 * 
	 * @return <code>true</code> si se trata de una recepción o no.
	 *         <code>false</code> en cualquier otro caso.
	 */
	public boolean isRecepcion() {
		return traspasoMiscelaneaFiscal.getRecepcion() != null && traspasoMiscelaneaFiscal.getRecepcion().booleanValue();
	}

	/**
	 * ActionListener para el botn que permite limpiar los campos de la forma
	 * de captura.
	 * 
	 * @param event
	 *            el evento que dispar este ActionListener
	 */
	public void limpiar(ActionEvent event) {
		traspasoMiscelaneaFiscal = new TraspasoMiscelaneaFiscalDTO();

		traspasoMiscelaneaFiscal.setRecepcion(Boolean.FALSE);
		traspasoMiscelaneaFiscal.setIdFolioTraspasante(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion());
		traspasoMiscelaneaFiscal.getPosicionTraspasante().getCuenta().setInstitucion(getInstitucionActual());
		traspasoMiscelaneaFiscal.setValorEn(MXP);
		traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().getTipoValor().setClaveTipoValor(StringUtils.EMPTY);
		traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().getEmisora().setDescripcion(StringUtils.EMPTY);
		traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().getSerie().setSerie(StringUtils.EMPTY);
		traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().setCupon(StringUtils.EMPTY);
		
		super.limpiarCampos();
		cambioRecepcion(event);
	}

	/**
	 * ActionListener para tratar el evento en el cual se marca o se desmarca la
	 * casilla de verificación Recepción.
	 * 
	 * @param e
	 *            el evento que dispar este ActionListener
	 */
	public void cambioRecepcion(ActionEvent e) {

		traspasoMiscelaneaFiscal.setPosicionTraspasante(new PosicionDTO());
		traspasoMiscelaneaFiscal.setCuentaReceptor(new CuentaDTO());
		traspasoMiscelaneaFiscal.setCantidadOperada(null);

		traspasoMiscelaneaFiscal.setFechaAdquisicion(null);
		traspasoMiscelaneaFiscal.setPrecioAdquisicion(null);
		traspasoMiscelaneaFiscal.setCliente(null);
		traspasoMiscelaneaFiscal.setRfcCurp(null);

		if (traspasoMiscelaneaFiscal.getRecepcion() != null && traspasoMiscelaneaFiscal.getRecepcion().booleanValue()) {

			editarReceptor = Boolean.FALSE;
			editarTraspasante = Boolean.TRUE;
			traspasoMiscelaneaFiscal.getPosicionTraspasante().setCuenta(new CuentaDTO());
			traspasoMiscelaneaFiscal.setIdFolioTraspasante(StringUtils.EMPTY);
			traspasoMiscelaneaFiscal.setCuentaReceptor(new CuentaDTO());
			traspasoMiscelaneaFiscal.setIdFolioReceptor(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion());
			getInit();
		} else {

			editarReceptor = Boolean.TRUE;
			editarTraspasante = Boolean.FALSE;
			traspasoMiscelaneaFiscal.getPosicionTraspasante().setCuenta(new CuentaDTO());
			traspasoMiscelaneaFiscal.setIdFolioTraspasante(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion());
			traspasoMiscelaneaFiscal.setCuentaReceptor(new CuentaDTO());
			traspasoMiscelaneaFiscal.setIdFolioReceptor(StringUtils.EMPTY);
			getInit();
		}
	}

	/**
	 * Valida que la cuenta del Transpasante no puede ser igual a la del
	 * Receptor
	 * 
	 * @param e
	 */
	public String validarCuenta(ActionEvent e) {

		mensajeUsuario = "";
		if (traspasoMiscelaneaFiscal.getPosicionTraspasante().getCuenta().getCuenta() != null
				&& !traspasoMiscelaneaFiscal.getPosicionTraspasante().getCuenta().getCuenta().equals("")) {
			if (traspasoMiscelaneaFiscal.getPosicionTraspasante().getCuenta().getCuenta().equals(traspasoMiscelaneaFiscal.getCuentaReceptor().getCuenta())
					&& traspasoMiscelaneaFiscal.getIdFolioReceptor().equals(traspasoMiscelaneaFiscal.getIdFolioTraspasante())) {
				mensajeUsuario = "La cuenta traspasante no puede ser la misma que la del receptor";
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensajeUsuario, mensajeUsuario));
				traspasoMiscelaneaFiscal.getCuentaReceptor().setCuenta("");
				if (isRecepcion() == Boolean.TRUE) {
					traspasoMiscelaneaFiscal.getPosicionTraspasante().getCuenta().setCuenta("");
				} else {
					traspasoMiscelaneaFiscal.getCuentaReceptor().setCuenta("");
				}
			}
		}
		return mensajeUsuario;

	}

	/**
	 * ActionListener para el botn que permite persistir la operación capturada
	 * por el usuario.
	 * 
	 * @param event
	 *            el evento que dispar este ActionListener
	 */
	public void enviarOperacion(ActionEvent e) {
		super.limpiarCampos();
		
		try {
			if(validarFechaObligatoria(traspasoMiscelaneaFiscal.getFechaAdquisicion(), true, CamposPantallaConstantes.campoFechaAdquisicion)) {
				if (StringUtils.isEmpty(validarCuenta(e))) {
					traspasoMiscelaneaFiscal.getPosicionTraspasante().getCuenta().setInstitucion(
							consultaCatalogosFacade.buscarInstitucionPorIdNoNulaFolio(traspasoMiscelaneaFiscal.getIdFolioTraspasante()));
					traspasoMiscelaneaFiscal.getCuentaReceptor().setInstitucion(
							consultaCatalogosFacade.buscarInstitucionPorIdNoNulaFolio(traspasoMiscelaneaFiscal.getIdFolioReceptor()));
					// Si el usuario debe firmar la operacion, se recuperar la firma.
					// Si no se ha firmado, se procesan los datos y regresa el control a la pantalla para firmar
					if (isUsuarioConFacultadFirmar()) {
						//Valida la vigencia del certificado - 18-Agosto-2014 Pablo Balderas
						validarVigenciaCertificado();
					}
					
					procesarDatos();
				}
			}
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
				}catch (Exception e) {
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
			calcularYRegistrarOperacicon();
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
		logger.debug("isoFirmado: '" + isoFirmado + "'");
		enviarOperacionABitacora();
	}

	/**
	 * Envia la operacion a bitacora
	 */
	private void enviarOperacionABitacora() {
		boolean isCompra = isRecepcion();
		String folioControl = tlpvo.getReferenciaOperacion();
		
		HashMap<String, String> datosAdicionales = new HashMap<String, String>();     
		datosAdicionales.put(SeguridadConstants.USR_CREDENCIAL, 
				(String)FacesContext.getCurrentInstance().getExternalContext().
				getSessionMap().get(SeguridadConstants.TICKET_SESION));

		envioOperacionesService.grabaOperacion(tlpvo, folioControl, isCompra, datosAdicionales, null, isoFirmado);

		if (!existeErrorEnInvocacion()) {
			String mensaje = "La informacion se ha guardado y se ha generado el Folio: " + folioAsignado;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje));
			traspasoMiscelaneaFiscal = new TraspasoMiscelaneaFiscalDTO();
			limpiar(null);
		}
	}

	private boolean validarDTO() {
		ResultadoValidacionDTO resultado = null;
		
		if(!traspasoMiscelaneaFiscal.getRfcCurp().equals("") && (traspasoMiscelaneaFiscal.getRfcCurp().length() < 9 || traspasoMiscelaneaFiscal.
						getRfcCurp().length() > 18)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"El campo RFC/CURP es inv\u00E1lido", "El campo RFC/CURP es inv\u00E1lido"));
			resultado = new ResultadoValidacionDTO();
			resultado.setValido(false);
			
			return resultado.isValido();
		}
		
		if (traspasoMiscelaneaFiscal.getRecepcion().booleanValue()) {
			resultado = validadorMFR.validarDTO(traspasoMiscelaneaFiscal);
		} else {
			resultado = validadorMF.validarDTO(traspasoMiscelaneaFiscal);
		}

		if (!resultado.isValido()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resultado.getMensaje(), resultado.getMensaje()));
		}

		return resultado.isValido();
	}

	/** métodos getters & setters */

	private void calcularYRegistrarOperacicon() {
		traspasoMiscelaneaFiscal.setValorEn(consultaCatalogosFacade.buscarDivisaPorId(traspasoMiscelaneaFiscal.getValorEn().getId()));
		TraspasoMercadoDineroParams params = new TraspasoMercadoDineroParams();

		params.setTipoMovimiento(TraspasoMercadoDineroParams.TRASPASO);

		// SE TRATA DE MERCADO DE DINERO
		params.setMercadoDinero(true);

		// --- AGENTE TRASPASANTE
		params.setTraspasante(DTOAssembler.crearAgenteVO(traspasoMiscelaneaFiscal.getPosicionTraspasante().getCuenta()));

		// --- AGENTE DEL RECEPTOR
		params.setReceptor(DTOAssembler.crearAgenteVO(traspasoMiscelaneaFiscal.getCuentaReceptor()));
		// params.setCURP(traspasoMiscelaneaFiscal.getRfcCurp());
		params.setEmision(new EmisionVO());

		// -- TIPO VALOR, --EMISORA, --SERIE, --CUPON, ---ISIN
		params.getEmision().setIdTipoValor(traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor());
		params.getEmision().setEmisora(traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().getEmisora().getDescripcion());
		params.getEmision().setSerie(traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().getSerie().getSerie());
		params.getEmision().setCupon(traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().getCupon());
		params.getEmision().setIsin(traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().getIsin());
		params.getEmision().setMercado(traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().getTipoValor().getMercado().getClaveMercado());

		// --- SALDO DISPONIBLE
		if (traspasoMiscelaneaFiscal.getSaldoDisponible() != null) {
			// params.getEmision().setSaldoDisponible(traspasoMiscelaneaFiscal.getSaldoDisponible().doubleValue());
		}

		// --- CANTIDAD
		if (traspasoMiscelaneaFiscal.getCantidadOperada() != null) {
			params.setCantidad(new BigDecimal(traspasoMiscelaneaFiscal.getCantidadOperada()));
		}

		// -- CLIENTE, --FECHA ADQUISICION, --TIPO OPERACION, PRECIO ADQUISICION
		params.setCliente(traspasoMiscelaneaFiscal.getCliente().toUpperCase());
		params.setRfcCURP(traspasoMiscelaneaFiscal.getRfcCurp().toUpperCase());
		params.setFechaAdquisicion(traspasoMiscelaneaFiscal.getFechaAdquisicion());
		params.setPrecioAdquisicion(new BigDecimal(traspasoMiscelaneaFiscal.getPrecioAdquisicion().toString()));
		params.setAceptaCargo(traspasoMiscelaneaFiscal.getAceptaCargo().booleanValue());
		params.setTipoMovimiento("TRASPASO");
		params.setFechaHoraCierreOper(traspasoMiscelaneaFiscal.getFechaHoraCierreOper());
		
		BovedaDTO bovedaSeleccionada =  null;
		
		if(traspasoMiscelaneaFiscal.getPosicionTraspasante().getBoveda() != null &&  traspasoMiscelaneaFiscal.getPosicionTraspasante().getBoveda().getId() >0 ) {
			bovedaSeleccionada =  consultaCatalogosFacade.buscarBovedaPorId(traspasoMiscelaneaFiscal.getPosicionTraspasante().getBoveda().getId());
		}
		
		if (mercadoDineroService != null) {
			BigInteger folioAsignadoBI = mercadoDineroService.traspasoMercadoDineroBusinessRules(params);

			if (folioAsignadoBI != null) {
				folioAsignado = folioAsignadoBI.toString();
			}
			if (!existeErrorEnInvocacion()) {
				String tipoOperacionLocal = "M"; // Misc Fisc Mercado Dinero

				tlpvo = llenaVO(params);

				tlpvo.setReferenciaOperacion(folioAsignado);
				tlpvo.setReferenciaMensaje(utilService.getFolio(Constantes.SEQ_REFERENCIA_MENSAJE).toString());
				tlpvo.setFechaAdquisicion(params.getFechaAdquisicion());
				tlpvo.setPrecioAdquisicion(params.getPrecioAdquisicion());
				tlpvo.setCliente(params.getCliente());
				tlpvo.setRfcCurp(params.getRfcCURP());
				tlpvo.setTipoInstruccion(tipoOperacionLocal);
				tlpvo.setBoveda( null != bovedaSeleccionada ?  bovedaSeleccionada.getNombreCorto() : null);

				// Si el usuario debe firmar la operacion, se crea el iso
				if (isUsuarioConFacultadFirmar()) {
					isoSinFirmar = isoHelper.creaISO(tlpvo, traspasoMiscelaneaFiscal.getRecepcion());
					hashIso = cdb.cipherHash(isoSinFirmar);
				} else {
					enviarOperacionABitacora();
				}
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "No se puede asignar Operacion, por favor intente mas tarde",
							"No se puede asignar Operacion, por favor intente mas tarde"));
		}
	}

	/**
	 * Metodo auxiliar para llenar el VO de Traspaso de Miscelanea Fiscal de
	 * Mercado de Dinero
	 * 
	 * @param params
	 * @return
	 */
	private TraspasoLibrePagoVO llenaVO(TraspasoMercadoDineroParams params) {
		TraspasoLibrePagoVO vo = new TraspasoLibrePagoVO();

		vo.setTipoValor(params.getEmision().getIdTipoValor().trim());
		vo.setEmisora(params.getEmision().getEmisora().trim());
		vo.setSerie(params.getEmision().getSerie().trim());
		vo.setCupon(params.getEmision().getCupon().trim());
		vo.setCantidadTitulos(new Long(params.getCantidad().longValue()));
		vo.setFechaRegistro(servicesCapturaOperacionViewHelper.getFechasUtilService().getCurrentDate());
		vo.setFechaLiquidacion(servicesCapturaOperacionViewHelper.getFechasUtilService().getCurrentDate());
		vo.setFechaHoraCierreOper(params.getFechaHoraCierreOper());

		vo.setIdFolioCtaReceptora(params.getReceptor().getId() + params.getReceptor().getFolio() + params.getReceptor().getCuenta());

		vo.setIdFolioCtaTraspasante(params.getTraspasante().getId() + params.getTraspasante().getFolio() + params.getTraspasante().getCuenta());

		return vo;
	}

	/**
	 * Metodo para calcular el saldo actual
	 * 
	 * @param event
	 */
	public void realizaCalculoSaldoActual(ActionEvent event) {
		if (traspasoMiscelaneaFiscal.getCantidadOperada() != null && traspasoMiscelaneaFiscal.getCantidadOperada() > 0) {
			if (traspasoMiscelaneaFiscal.getSaldoDisponible() != null) {
				traspasoMiscelaneaFiscal.setSaldoActual(traspasoMiscelaneaFiscal.getSaldoDisponible() - traspasoMiscelaneaFiscal.getCantidadOperada());
			}
		} else {
			traspasoMiscelaneaFiscal.setSaldoActual(new Double(0));
		}

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
		if (StringUtils.isNotEmpty(traspasoMiscelaneaFiscal.getPosicionTraspasante().getCuenta().getCuenta())
				&& StringUtils.isNotEmpty(traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor())
				&& StringUtils.isNotEmpty(traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().getEmisora().getDescripcion())
				&& StringUtils.isNotEmpty(traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().getSerie().getSerie())) {
			PosicionDTO criterio = new PosicionDTO();

			criterio.setCuenta(new CuentaDTO());
			criterio.getCuenta().setTipoCustodia(TipoCustodiaConstants.VALORES);
			criterio.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
			criterio.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA, ""));
			criterio.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
			criterio.getCuenta().setNumeroCuenta(
					getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion()
							+ traspasoMiscelaneaFiscal.getPosicionTraspasante().getCuenta().getCuenta());
			criterio.setEmision(new EmisionDTO());
			InstitucionDTO institucion = null;
			institucion = consultaCatalogosFacade.buscarInstitucionPorIdFolio(traspasoMiscelaneaFiscal.getIdFolioTraspasante());

			if (institucion != null) {
				// cuenta
				if (StringUtils.isNotEmpty(traspasoMiscelaneaFiscal.getPosicionTraspasante().getCuenta().getCuenta())) {
					criterio.getCuenta().setNumeroCuenta(
							institucion.getClaveTipoInstitucion() + institucion.getFolioInstitucion()
									+ traspasoMiscelaneaFiscal.getPosicionTraspasante().getCuenta().getCuenta());
				} else {
					criterio.getCuenta().setCuenta("-1");
				}

				// TV
				criterio.getEmision().setTipoValor(
						consultaCatalogosFacade.buscarTipoValorPorClave(traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().getTipoValor()
								.getClaveTipoValor()));
				// Emisora
				criterio.getEmision().setEmisora(
						consultaCatalogosFacade.buscarEmisoraPorNombreCorto(traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().getEmisora()
								.getDescripcion()));
				criterio.getEmision().setSerie(new SerieDTO());
				// Serie
				criterio.getEmision().getSerie().setSerie(traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().getSerie().getSerie().trim());

				if (traspasoMiscelaneaFiscal.getPosicionTraspasante().getBoveda() != null && traspasoMiscelaneaFiscal.getPosicionTraspasante().getBoveda().getId() > 0) {
					criterio.setBoveda(new BovedaDTO());
					criterio.getBoveda().setId(traspasoMiscelaneaFiscal.getPosicionTraspasante().getBoveda().getId());
				}else{
					criterio.getBoveda().setId(NumberUtils.toLong("-1", DaliConstants.VALOR_COMBO_TODOS));
				}
				criterio.getCuenta().setInstitucion(institucion);
				criterio.setFechaFinal(new Date());

				CriterioOrdenamientoDTO orden = new CriterioOrdenamientoDTO();
				orden.setColumna("sortCuenta");

				List<PosicionDTO> listaPosiciones = consultaPosicionService.consultarPosicionesPorMercado(criterio, null, orden, idMercadoDinero
						.toArray(new Long[] {}));
				
				traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().setCupon(null);
				traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().setIsin(null);
				traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().getTipoValor().getMercado().setClaveMercado(null);
				traspasoMiscelaneaFiscal.getPosicionTraspasante().setBoveda(null);
				traspasoMiscelaneaFiscal.setSaldoDisponible(null);

				if (listaPosiciones != null && !listaPosiciones.isEmpty()) {
					posicion = listaPosiciones.get(0);
					// obtencion de los datos para ser desplegado en pantalla
					traspasoMiscelaneaFiscal.getPosicionTraspasante().getCuenta().setCuenta(posicion.getCuenta().getCuenta());
					traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().getEmisora().setDescripcion(
							posicion.getEmision().getEmisora().getDescripcion());
					traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().setCupon(posicion.getEmision().getCupon());
					traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().setIsin(posicion.getEmision().getIsin());
					traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().getTipoValor().getMercado().setClaveMercado(
							posicion.getEmision().getTipoValor().getMercado().getClaveMercado());
					traspasoMiscelaneaFiscal.getPosicionTraspasante().setBoveda(new BovedaDTO());
					traspasoMiscelaneaFiscal.getPosicionTraspasante().getBoveda().setNombreCorto(posicion.getBoveda().getNombreCorto());
					traspasoMiscelaneaFiscal.getPosicionTraspasante().getBoveda().setId(posicion.getBoveda().getId());
					traspasoMiscelaneaFiscal.setSaldoDisponible(new BigDecimal(posicion.getPosicionDisponible()).doubleValue());

				}
			}  

		} 
		
		if (posicion == null && StringUtils.isNotEmpty(traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor())
				&& StringUtils.isNotEmpty(traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().getEmisora().getDescripcion())
				&& StringUtils.isNotEmpty(traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().getSerie().getSerie())) {

			EmisionDTO criterio = new EmisionDTO();
			criterio.setTipoValor(new TipoValorDTO());
			criterio.getTipoValor().setClaveTipoValor(traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor());
			criterio.setEmisora(new EmisoraDTO());
			criterio.getEmisora().setDescripcion(traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().getEmisora().getDescripcion());
			criterio.setSerie(new SerieDTO());
			criterio.getSerie().setSerie(traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().getSerie().getSerie());
			List<EmisionDTO> listaEmisiones = emisionDaliDAO.consultarEmisionesPorDescripciones(criterio, null);

			if (listaEmisiones != null && !listaEmisiones.isEmpty() && listaEmisiones.size() == 1) {
				// pasar los datos al objeto de la pantalla
				traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().getEmisora().setDescripcion(listaEmisiones.get(0).getEmisora().getDescripcion());
				traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().getTipoValor().setClaveTipoValor(
						listaEmisiones.get(0).getTipoValor().getClaveTipoValor());
				traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().getSerie().setSerie(listaEmisiones.get(0).getSerie().getSerie());
				traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().getTipoValor().getMercado().setClaveMercado(
						listaEmisiones.get(0).getTipoValor().getMercado().getClaveMercado());
				traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().setIsin(listaEmisiones.get(0).getIsin());
				traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().setCupon(listaEmisiones.get(0).getCupon());

				traspasoMiscelaneaFiscal.getPosicionTraspasante().setBoveda(new BovedaDTO());
				traspasoMiscelaneaFiscal.getPosicionTraspasante().getBoveda().setNombreCorto(listaEmisiones.get(0).getBoveda().getNombreCorto());
				traspasoMiscelaneaFiscal.getPosicionTraspasante().getBoveda().setId(listaEmisiones.get(0).getBoveda().getId());
				
			} else {
				traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().setCupon(null);
				traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().setIsin(null);
				traspasoMiscelaneaFiscal.getPosicionTraspasante().getEmision().getTipoValor().getMercado().setClaveMercado(null);
				traspasoMiscelaneaFiscal.getPosicionTraspasante().setBoveda(null);
				traspasoMiscelaneaFiscal.setSaldoDisponible(null);
			}

		}
		return null;
	}

	/**
	 * @return the traspasoMiscelaneaFiscal
	 */
	public TraspasoMiscelaneaFiscalDTO getTraspasoMiscelaneaFiscal() {
		return traspasoMiscelaneaFiscal;
	}

	/**
	 * @param traspasoMiscelaneaFiscal
	 *            the traspasoMiscelaneaFiscal to set
	 */
	public void setTraspasoMiscelaneaFiscal(TraspasoMiscelaneaFiscalDTO traspasoMiscelaneaFiscal) {
		this.traspasoMiscelaneaFiscal = traspasoMiscelaneaFiscal;
	}

	/**
	 * @return the consultaCatalogosFacade
	 */
	public ConsultaCatalogosFacade getConsultaCatalogosFacade() {
		return consultaCatalogosFacade;
	}

	/**
	 * @param consultaCatalogosFacade
	 *            the consultaCatalogosFacade to set
	 */
	public void setConsultaCatalogosFacade(ConsultaCatalogosFacade consultaCatalogosFacade) {
		this.consultaCatalogosFacade = consultaCatalogosFacade;
	}

	/**
	 * Obtiene el valor del campo validadorMF
	 * 
	 * @return el valor de validadorMF
	 */
	public DTOValidator getValidadorMF() {
		return validadorMF;
	}

	/**
	 * Asigna el campo validadorMF
	 * 
	 * @param validadorMF
	 *            el valor de validadorMF a asignar
	 */
	public void setValidadorMF(DTOValidator validadorMF) {
		this.validadorMF = validadorMF;
	}

	/**
	 * Obtiene el valor del campo validatorMFR
	 * 
	 * @return el valor de validatorMFR
	 */
	public DTOValidator getValidadorMFR() {
		return validadorMFR;
	}

	/**
	 * Asigna el campo validatorMFR
	 * 
	 * @param validatorMFR
	 *            el valor de validatorMFR a asignar
	 */
	public void setValidadorMFR(DTOValidator validadorMFR) {
		this.validadorMFR = validadorMFR;
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
	 * Obtiene el valor del campo fechaservice
	 * 
	 * @return el valor de fechaservice
	 */
	public FechasUtilService getFechaservice() {
		return fechaservice;
	}

	/**
	 * Asigna el campo fechaservice
	 * 
	 * @param fechaservice
	 *            el valor de fechaservice a asignar
	 */
	public void setFechaservice(FechasUtilService fechaservice) {
		this.fechaservice = fechaservice;
	}

	/**
	 * Obtiene el valor del campo serviceCapturaViewHelper
	 * 
	 * @return el valor de serviceCapturaViewHelper
	 */
	public ServicesCapturaOperacionViewHelper getServicesCapturaOperacionViewHelper() {
		return servicesCapturaOperacionViewHelper;
	}

	/**
	 * Asigna el campo serviceCapturaViewHelper
	 * 
	 * @param serviceCapturaViewHelper
	 *            el valor de serviceCapturaViewHelper a asignar
	 */
	public void setServicesCapturaOperacionViewHelper(ServicesCapturaOperacionViewHelper serviceCapturaViewHelper) {
		this.servicesCapturaOperacionViewHelper = serviceCapturaViewHelper;
	}

	/**
	 * Obtiene el valor del campo mercadoDineroService
	 * 
	 * @return el valor de mercadoDineroService
	 */
	public MercadoDineroService getMercadoDineroService() {
		return mercadoDineroService;
	}

	/**
	 * Asigna el campo mercadoDineroService
	 * 
	 * @param mercadoDineroService
	 *            el valor de mercadoDineroService a asignar
	 */
	public void setMercadoDineroService(MercadoDineroService mercadoDineroService) {
		this.mercadoDineroService = mercadoDineroService;
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

	public boolean isEditarTraspasante() {
		return editarTraspasante;
	}

	public void setEditarTraspasante(boolean editarTraspasante) {
		this.editarTraspasante = editarTraspasante;
	}

	public boolean isEditarReceptor() {
		return editarReceptor;
	}

	public void setEditarReceptor(boolean editarReceptor) {
		this.editarReceptor = editarReceptor;
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
