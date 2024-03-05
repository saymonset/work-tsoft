package com.indeval.portaldali.presentation.controller.mercadodinero.reportos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;

import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.dto.VencimientoAnticipadoDTO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.InfrastructureException;
import com.indeval.portaldali.middleware.services.reportos.VencimientoReportosService;

import com.indeval.portaldali.presentation.common.constants.UtilConstantes;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;
import com.indeval.portaldali.presentation.helper.IsoHelper;
import com.indeval.portaldali.presentation.util.CifradorDescifradorBlowfish;

public class DetalleVencimientoController extends ControllerBase {
	
	private VencimientoReportosService vencimientoReportosService;
	
	/** Ayudante para la generación de las cadenas ISO que deberán ser firmadas */
    protected IsoHelper isoHelper = null;

    /** La cadena que contiene el iso sin firmar */
    protected String isoSinFirmar = "";

    /** La cadena que contiene el iso firmado */
    protected String isoFirmado = "";

    /** EL número de serie asociado al iso firmado */
    protected String numeroSerie = "";
    /**
     * Folio de la operación
     */
    protected String folioAsignado = null;
    /**
     * Cadena Hash del ISO a firmar
     */
    protected String hashIso = null;
    
    protected CifradorDescifradorBlowfish cdb = new CifradorDescifradorBlowfish();
    
	private VencimientoAnticipadoDTO solicitud;
	private boolean validationErrors;
	
	private boolean consulta;
	
	private boolean facultadLectura;
	private boolean facultadEscritura;
	
	private String userAction;
	
	public String getInit() {
		logger.debug("initDetalleVencimientoAnticipado");
		Map<String , String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		String idVencimientoStr = params.get("idVencimiento");
		String consultaStr = params.get("consulta");
		
		logger.debug("idVencimiento: " + idVencimientoStr);
		logger.debug("consulta: " + consultaStr);
		
		facultadEscritura = tieneFacultad(SeguridadConstants.FACULTAD_VTO_REPORTOS_OPERA);
		facultadLectura   = tieneFacultad(SeguridadConstants.FACULTAD_VTO_REPORTOS_CONSULTA);
		
		logger.info("facultadEscritura: " + facultadEscritura);
		logger.info("facultadLectura: " + facultadLectura);
		
		// SI NO PUEDE CONSULTAR, MUESTRA UN MENSAJE
		if(!(facultadEscritura || facultadLectura) ) {
			String message="Usted no tiene facultades de consulta. Favor de verificar con su administrador.";
			logger.warn(message);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
			
			return null;
		}
		
		this.consulta = Boolean.valueOf(consultaStr);
		
		if (StringUtils.isNotBlank(idVencimientoStr) && StringUtils.isNumeric(idVencimientoStr)) {

			long idVencimiento = Long.parseLong(idVencimientoStr);
			
			buscarSolicitudPorId(idVencimiento);
		}
		
		// this.userAction = null;
		
		return null;
	}
	
	public String autorizarVencimientoAnticipado() {
		logger.debug("autorizarVencimientoAnticipado");
		
		this.userAction = "AUTORIZA";
		
		// SI NO PUEDE CONSULTAR, MUESTRA UN MENSAJE
		if(!facultadEscritura ) {
			String message="Usted no tiene facultades para autorizar una solicitud de vencimiento. Favor de verificar con su administrador.";
			logger.warn(message);
			FacesContext.getCurrentInstance().addMessage("mensajes",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
			
			return null;
		}
		
		String result = null;
		if(this.solicitud == null) return result;
		
		this.validationErrors = false;
		
		long idInstruccion = this.solicitud.getIdInstruccionOperacion();
		logger.debug("idInstruccion: " + idInstruccion);
		
		// buscar la solicitud de vencimiento
		long idVencimiento = vencimientoReportosService.findVencimientoPendiente(idInstruccion);
		if(idVencimiento <= 0) {
			String message = "No se encontro la solicitud de vencimiento de la instruccion seleccionada.";
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
			this.validationErrors = true;
			
			return result;
		}
		
		VencimientoAnticipadoDTO vto = vencimientoReportosService.findSolicitud(idVencimiento);
		
		List<String> errors = vencimientoReportosService.validarAutorizacionVencimiento(vto);
		
		if(errors!=null && !errors.isEmpty()) {
			for(String error:errors) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
			}
			this.validationErrors = true;
		}else {
			
			// Si el usuario debe firmar la operacion, se crea el iso
            if(isUsuarioConFacultadFirmar()) {
            	
            	validarVigenciaCertificado();
				recuperarCamposFirma();
				
            	if (!StringUtils.isEmpty(isoFirmado)) {
            		if(!cdb.validation(hashIso, isoSinFirmar)){
                		throw new InfrastructureException(UtilConstantes.ERROR_ISO_DIFERENTE);
                	}
                	isoFirmado = (new StringBuilder()).append(isoSinFirmar).append(numeroSerie).append("\n").append("{SHA1withRSA}").append(isoFirmado).toString();
                	logger.debug("isoFirmado: '" + isoFirmado + "'");

                	autorizarVencimiento(idVencimiento, isoFirmado);
                    
            	}else {
            		isoSinFirmar = crearCadena(vto, "AU");
                    hashIso = cdb.cipherHash(isoSinFirmar);
                    
                    return null;
            	}
                
            } else {
            	autorizarVencimiento(idVencimiento, null);
            }
			
			result = "consulta-solicitud";
		}
		
		
		return result;
	}
	
	public String rechazarVencimientoAnticipado() {
		logger.debug("rechazarVencimientoAnticipado");
		
		String result = null;
		
		this.userAction = "RECHAZA";
		// SI NO PUEDE CONSULTAR, MUESTRA UN MENSAJE
		if(!facultadEscritura ) {
			String message="Usted no tiene facultades para rechazar una solicitud de vencimiento. Favor de verificar con su administrador.";
			logger.warn(message);
			FacesContext.getCurrentInstance().addMessage("mensajes",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
			
			return null;
		}
				
		if(this.solicitud == null) return null;
		
		this.validationErrors = false;
		
		long idInstruccion = this.solicitud.getIdInstruccionOperacion();
		logger.debug("idInstruccion: " + idInstruccion);
		
		long idVencimiento = vencimientoReportosService.findVencimientoPendiente(idInstruccion);
		if(idVencimiento <= 0) {
			String message = "No se encontro la solicitud de vencimiento de la instruccion seleccionada.";
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
			this.validationErrors = true;
			
			return null;
		}
		
		VencimientoAnticipadoDTO vto = vencimientoReportosService.findSolicitud(idVencimiento);
		// Si el usuario debe firmar la operacion, se crea el iso
        if(isUsuarioConFacultadFirmar()) {
        	
        	validarVigenciaCertificado();
			recuperarCamposFirma();
			
        	if (!StringUtils.isEmpty(isoFirmado)) {
        		if(!cdb.validation(hashIso, isoSinFirmar)){
            		throw new InfrastructureException(UtilConstantes.ERROR_ISO_DIFERENTE);
            	}
            	isoFirmado = (new StringBuilder()).append(isoSinFirmar).append(numeroSerie).append("\n").append("{SHA1withRSA}").append(isoFirmado).toString();
            	logger.debug("isoFirmado: '" + isoFirmado + "'");

            	rechazarVencimiento(idVencimiento, isoFirmado);
            	
            	result = "consulta-solicitud";
        	}else {
        		isoSinFirmar = crearCadena(vto, "CA");
                hashIso = cdb.cipherHash(isoSinFirmar);
                
                return null;
        	}
            
        } else {
        	rechazarVencimiento(idVencimiento, null);
        	
        	 result = "consulta-solicitud";
        }
		
		
		return result;
	}
	
	private String crearCadena(VencimientoAnticipadoDTO dto, String estatus) {
		logger.debug("crearCadena");
		DateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat sfull = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
		StringBuilder sb = new StringBuilder();
		sb.append(dto.getIdInstruccionOperacion()).append("|")
		.append(dto.getPlazoVigente()).append("|")
		.append(sf.format(dto.getFechaVencimientoVigente()) ).append("|")
		.append(dto.getPlazoSolicitud() ).append("|")
		.append(sf.format(dto.getFechaVencimientoSolicitud())).append("|")
		.append(estatus).append("|")
		.append(sfull.format(new Date())).append("||");
		//Id_instruccion_operacion|plazo_vigente|fecha_vencimiento_vigente|plazo_solicitado|fecha_vencimiento_solicitado|estatus
		
		logger.debug(sb.toString());
		
		return sb.toString();
	}
	
	private void rechazarVencimiento(long idVencimiento, String firmaRespuesta) {
		logger.debug("rechazarVencimiento");
		long idInstitucion = getInstitucionActual().getId();
		String usuario = obtenerUsuarioSesion().getClaveUsuario();
		String ipRemota = obtenerIpRemota();
		
		vencimientoReportosService.rechazarVencimientoAnticipado(idVencimiento, idInstitucion, firmaRespuesta, usuario, ipRemota);
	}
	
	private void autorizarVencimiento(long idVencimiento, String firmaRespuesta) {
		logger.debug("autorizarVencimiento");
		long idInstitucion = getInstitucionActual().getId();
		String usuario = obtenerUsuarioSesion().getClaveUsuario();
		String ipRemota = obtenerIpRemota();
		vencimientoReportosService.autorizarVencimientoAnticipado(idVencimiento, idInstitucion, firmaRespuesta, usuario, ipRemota);
	}
	
	protected void recuperarCamposFirma(){
		logger.debug("recuperarCamposFirma");
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            isoFirmado = params.get("isoFirmado").replace("\r\n","\n");
            hashIso = params.get("hashIso").replace("\r\n","\n");
            isoSinFirmar = params.get("isoSinFirmar").replace("\r\n","\n");
            numeroSerie = params.get("numeroSerie").replace("\r\n","\n");
        logger.info(String.valueOf(params));
        
	}
	
	public String buscarSolicitudPorId(long idVencimiento) {
		logger.debug("buscarSolicitudPorId");
		this.validationErrors = false;
		
		
		solicitud = vencimientoReportosService.findSolicitud(idVencimiento);
		
		
		if(solicitud == null) {
			String message = "No se encontr\u00f3 la solicitud de vencimiento con id " + idVencimiento;
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
			this.validationErrors = true;
		}
        
		return null;
	}
	
	public String toConsultaSolicitud() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String reload = params.get("reload");
		String modMessage = params.get("modMessage");
		logger.debug("reload: " + reload);
		logger.debug("modMessage: " + modMessage);
		
		return "consulta-solicitud";
	}
	
	public void setVencimientoReportosService(VencimientoReportosService vencimientoReportosService) {
		this.vencimientoReportosService = vencimientoReportosService;
	}

	public VencimientoAnticipadoDTO getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(VencimientoAnticipadoDTO solicitud) {
		this.solicitud = solicitud;
	}

	public boolean isValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(boolean validationErrors) {
		this.validationErrors = validationErrors;
	}

	public boolean isConsulta() {
		return consulta;
	}

	public void setConsulta(boolean consulta) {
		this.consulta = consulta;
	}

	public boolean isFacultadLectura() {
		return facultadLectura;
	}

	public void setFacultadLectura(boolean facultadLectura) {
		this.facultadLectura = facultadLectura;
	}

	public boolean isFacultadEscritura() {
		return facultadEscritura;
	}

	public void setFacultadEscritura(boolean facultadEscritura) {
		this.facultadEscritura = facultadEscritura;
	}

	public IsoHelper getIsoHelper() {
		return isoHelper;
	}

	public void setIsoHelper(IsoHelper isoHelper) {
		this.isoHelper = isoHelper;
	}

	public String getIsoSinFirmar() {
		return isoSinFirmar;
	}

	public void setIsoSinFirmar(String isoSinFirmar) {
		this.isoSinFirmar = isoSinFirmar;
	}

	public String getIsoFirmado() {
		return isoFirmado;
	}

	public void setIsoFirmado(String isoFirmado) {
		this.isoFirmado = isoFirmado;
	}

	public String getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public String getFolioAsignado() {
		return folioAsignado;
	}

	public void setFolioAsignado(String folioAsignado) {
		this.folioAsignado = folioAsignado;
	}

	public String getHashIso() {
		return hashIso;
	}

	public void setHashIso(String hashIso) {
		this.hashIso = hashIso;
	}

	public String getUserAction() {
		return userAction;
	}

	public void setUserAction(String userAction) {
		this.userAction = userAction;
	}
	
}
