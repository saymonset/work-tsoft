package com.indeval.portaldali.presentation.controller.mercadodinero.reportos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;

import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.dto.InstruccionOperacionDTO;
import com.indeval.portaldali.middleware.dto.VencimientoAnticipadoDTO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.InfrastructureException;
import com.indeval.portaldali.middleware.services.reportos.VencimientoReportosService;
import com.indeval.portaldali.persistence.util.DateUtil;
import com.indeval.portaldali.presentation.common.constants.UtilConstantes;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;
import com.indeval.portaldali.presentation.helper.IsoHelper;
import com.indeval.portaldali.presentation.util.CifradorDescifradorBlowfish;

public class NuevoVencimientoController extends ControllerBase {

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
	private Date fecha2doDiaHabil;
	private Date fechaHoy;
	private boolean validationErrors;
	
	private boolean facultadEscritura;
	
	public String getInit() {
		logger.debug("initVencimientoAnticipado");
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		facultadEscritura = tieneFacultad(SeguridadConstants.FACULTAD_VTO_REPORTOS_OPERA);
		
		this.fechaHoy = vencimientoReportosService.getCurrentDate();
		this.fecha2doDiaHabil = vencimientoReportosService.agregaDiasHabiles(this.fechaHoy, 2);

		String idInstruccionStr = params.get("idInstruccion");
		logger.debug("idInstruccion: " + idInstruccionStr);

		if (StringUtils.isNotBlank(idInstruccionStr) && StringUtils.isNumeric(idInstruccionStr)) {

			long idInstruccion = Long.parseLong(idInstruccionStr);

			inicializaSolicitud(idInstruccion);
		}

		return null;
	}

	public void validaNuevoPlazo(ActionEvent event) {
		logger.debug("validaNuevoPlazo");

		if (this.solicitud == null)
			return;
		
		this.validationErrors = false;
				
		Date fechaLiquidacion = this.solicitud.getFechaLiquidacion();
		Date fechaVencimientoVigente = this.solicitud.getFechaVencimientoVigente();
		
		Date fechaVencimientoSolicitud = this.solicitud.getFechaVencimientoSolicitud();
		//Integer nuevoPlazo = this.solicitud.getPlazoSolicitud();

		logger.debug("fechaLiquidacion: " + fechaLiquidacion);
		logger.debug("fechaSolicitud: " + fechaVencimientoSolicitud);
		
		if(fechaVencimientoSolicitud == null ) {
			String message = "la fecha de reporto es un dato requerido.";
			
			logger.warn(message);
			this.validationErrors = true;
			FacesContext.getCurrentInstance().addMessage("mensajes",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
			
			return;
		}
		
		
		//Date fechaVencimiento = DateUtil.addDays(fechaLiquidacion, nuevoPlazo);
		//this.solicitud.setFechaVencimientoSolicitud(fechaVencimiento);
		if(this.fechaHoy.compareTo(fechaVencimientoSolicitud)>=0) {
			String message = "La fecha de reporto debe ser mayor al d\u00eda de hoy.";
			
			logger.warn(message);
			this.validationErrors = true;
			FacesContext.getCurrentInstance().addMessage("mensajes",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
			
			return ;
		}else if(fechaVencimientoVigente.compareTo(fechaVencimientoSolicitud) <= 0) {
			String message = "La nueva fecha de reporto debe ser menor a la fecha de reporto vigente.";
			
			logger.warn(message);
			this.validationErrors = true;
			FacesContext.getCurrentInstance().addMessage("mensajes",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
		}else if(vencimientoReportosService.esInhabil(fechaVencimientoSolicitud)) {
			String message = "La fecha de reporto es inhabil";
			
			logger.warn(message);
			this.validationErrors = true;
			FacesContext.getCurrentInstance().addMessage("mensajes",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
		}
		
		
		int nuevoPlazo = getPlazo(fechaLiquidacion, fechaVencimientoSolicitud); 
		this.solicitud.setPlazoSolicitud(nuevoPlazo);
		
	}

	public String solicitarVencimientoAnticipado() {
		logger.debug("<<<<<<solicitarVencimientoAnticipado>>>>>");
		
		String result = null; 
		
		// SI NO PUEDE CONSULTAR, MUESTRA UN MENSAJE
		if(!facultadEscritura ) {
			String message="Usted no tiene facultades para crear una solicitud de vencimiento. Favor de verificar con su administrador.";
			logger.warn(message);
			FacesContext.getCurrentInstance().addMessage("mensajes",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
			
			return null;
		}
				
		if (this.solicitud == null) {
			return result;
		
		}
		this.validationErrors = false;
		
		long idInstruccion = this.solicitud.getIdInstruccionOperacion();
		Date fechaVencimientoSolicitud = this.solicitud.getFechaVencimientoSolicitud();

		logger.debug("idInstruccion: " + idInstruccion);
		logger.debug("fechaVencimientoSolicitud: " + fechaVencimientoSolicitud);
		
		if(fechaVencimientoSolicitud == null ) {
			String message = "La fecha de reporto es un dato requerido.";
			
			logger.warn(message);
			this.validationErrors = true;
			FacesContext.getCurrentInstance().addMessage("mensajes",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
			
			return result;
		}
		
		
		
		// buscar el detalle de la operacion
		InstruccionOperacionDTO instruccion = vencimientoReportosService.findReporto(idInstruccion); 
		if(instruccion == null) {
			String message = "No se encontr\u00f3 la operaci\u00f3n con id " + idInstruccion;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
			this.validationErrors = true;
			
			return result;
		}
		
		int nuevoPlazo = getPlazo(instruccion.getFechaLiquidacion(), fechaVencimientoSolicitud);
		
		List<String> errors = vencimientoReportosService.validarSolicitudVencimiento(instruccion, nuevoPlazo);
		if(errors!=null && !errors.isEmpty()) {
			for(String error:errors) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
			}
			this.validationErrors = true;
		}else {
			this.solicitud.setPlazoSolicitud(nuevoPlazo);
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
                    
                	guardarVencimiento(instruccion, nuevoPlazo, isoFirmado);
                    
            	}else {
            		isoSinFirmar = crearCadena(this.solicitud);
                    hashIso = cdb.cipherHash(isoSinFirmar);
                    
                    return null;
            	}
                
            } else {
            	guardarVencimiento(instruccion, nuevoPlazo, null);
            }
			
			result = "consulta-solicitud";
		}
		
		logger.debug(result);
		return result;
	}
	
	private int getPlazo(Date fechaLiquidacion, Date fechaVencimiento) {
		Long plazo = DateUtil.getPlazo(fechaLiquidacion, fechaVencimiento);
		
		return plazo.intValue();
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
	
	private void guardarVencimiento(InstruccionOperacionDTO instruccion, int nuevoPlazo, String firma) {
		logger.debug("guardarVencimiento");
		long idInstitucion = getInstitucionActual().getId();
		String usuario = obtenerUsuarioSesion().getClaveUsuario();
		String ipRemota = obtenerIpRemota();
		
		
		vencimientoReportosService.guardarSolicitudVencimiento(instruccion, idInstitucion, nuevoPlazo, firma, usuario, ipRemota);
		
	}
	private String crearCadena(VencimientoAnticipadoDTO dto) {
		logger.debug("crearCadena");
		DateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		
		StringBuilder sb = new StringBuilder();
		sb.append(dto.getIdInstruccionOperacion()).append("|")
		.append(dto.getPlazoVigente()).append("|")
		.append(sf.format(dto.getFechaVencimientoVigente()) ).append("|")
		.append(dto.getPlazoSolicitud() ).append("|")
		.append(sf.format(dto.getFechaVencimientoSolicitud())).append("||");
		//Id_instruccion_operacion|plazo_vigente|fecha_vencimiento|vigente|plazo_solicitado|fecha_vencimiento_solicitado
		
		logger.debug(sb.toString());
		
		return sb.toString();
	}
	private void inicializaSolicitud(long idInstruccion) {
		this.validationErrors = false;
		try {
			InstruccionOperacionDTO dto = vencimientoReportosService.findReporto(idInstruccion);
			
			if(dto==null) {
				throw new BusinessException(
						"No se encontr\u00f3 la operaci\u00f3n con id " + idInstruccion);
			}
			Date fechaLiquidacion = DateUtil.preparaFechaConExtremoEnSegundos(dto.getFechaLiquidacion(), true);
			int plazoVigente = dto.getPlazoReporto();
			Date fechaVencimientoVigente = DateUtil.addDays(fechaLiquidacion, plazoVigente);
			long diasTranscurridos = DateUtil.getPlazo(fechaLiquidacion, this.fechaHoy);

			this.solicitud = new VencimientoAnticipadoDTO();
			this.solicitud.setIdInstruccionOperacion(idInstruccion);
			this.solicitud.setPlazoVigente(plazoVigente);
			this.solicitud.setFechaVencimientoVigente(fechaVencimientoVigente);
			this.solicitud.setDiasTranscurridos(diasTranscurridos);
			this.solicitud.setFechaLiquidacion(fechaLiquidacion);

			if (fecha2doDiaHabil.compareTo(fechaVencimientoVigente) > 0)
				throw new BusinessException(
						"Esta operaci\u00f3n no se puede vencer anticipadamente. La fecha vencimiento vigente debe ser mayor o igual a dos dias h\u00e1biles.");

		} catch (BusinessException e) {
			logger.warn(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
			this.validationErrors = true;
		}
	}

	public String toConsultaSolicitud() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String reload = params.get("reload");
		String modMessage = params.get("modMessage");
		logger.debug("reload: " + reload);
		logger.debug("modMessage: " + modMessage);
		
		return "consulta-solicitud";
	}

	public VencimientoAnticipadoDTO getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(VencimientoAnticipadoDTO solicitud) {
		this.solicitud = solicitud;
	}

	public Date getFecha2doDiaHabil() {
		return fecha2doDiaHabil;
	}

	
	
	
	public void setFecha2doDiaHabil(Date fecha2doDiaHabil) {
		this.fecha2doDiaHabil = fecha2doDiaHabil;
	}

	public Date getFechaHoy() {
		return fechaHoy;
	}

	public void setFechaHoy(Date fechaHoy) {
		this.fechaHoy = fechaHoy;
	}

	public boolean isValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(boolean validationErrors) {
		this.validationErrors = validationErrors;
	}

	public void setVencimientoReportosService(VencimientoReportosService vencimientoReportosService) {
		this.vencimientoReportosService = vencimientoReportosService;
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
	
}
