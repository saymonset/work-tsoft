package com.indeval.portaldali.presentation.controller.tarea;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.dto.TareaDTO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.tarea.TareaService;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;

public class DetalleTareaBean extends ControllerBase{
	/*
	 * Servicios para las tareas pendientes
	 */
	private TareaService tareaService;
	
	private TareaDTO tareaForm;
	//private TareaFormDTO tareaForm;
	private boolean validationErrors;
	
	public String getInit() {
		if(logger.isTraceEnabled())logger.trace("getInit");
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		String idTarea = params.get("idTarea");
		logger.debug("idTareaStr: " + idTarea);
		
		validationErrors = false;
		
		inicializaVista(idTarea);
		
		return null;
	}
	
	private void inicializaVista(String idTarea) {
		if(logger.isTraceEnabled())logger.trace("inicializaVista");
		
		this.validationErrors = false;
		this.tareaForm = new TareaDTO();
		
		try {
			String ticket = obtenerTicketSesion();
			TareaDTO dto = tareaService.findById(idTarea, ticket);
			
			if(dto==null) {
				throw new BusinessException(
						"No se encontr\u00f3 la tarea con id " + idTarea);
			}
			
			this.tareaForm = dto;
			
			logger.debug(String.valueOf(this.tareaForm));

		} catch (BusinessException e) {
			logger.warn(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
			
			this.validationErrors = true;
		}
	}
	
	public String autorizarTarea() {
		if(logger.isTraceEnabled())logger.trace("autorizarTarea");
		
		String idTarea = this.tareaForm.getIdTarea();
		
		if(logger.isDebugEnabled())logger.debug("idTarea: " + idTarea);
		
		String claveUsuario = obtenerUsuarioSesion().getClaveUsuario();
		String ticket = obtenerTicketSesion();
		// String ip = obtenerIpRemota();
		
		if (StringUtils.isNotBlank(idTarea)) {
			tareaService.autorizarTarea(idTarea, claveUsuario, ticket);
		}
		
		return null;
	}
	
	public String rechazarTarea() {
		if(logger.isTraceEnabled())logger.trace("rechazarTarea");
		
		String idTarea = this.tareaForm.getIdTarea();
		if(logger.isDebugEnabled())logger.debug("idTarea: " + idTarea);
		
		String claveUsuario = obtenerUsuarioSesion().getClaveUsuario();
		String ticket = obtenerTicketSesion();
		//String ip = obtenerIpRemota();
		
		if (StringUtils.isNotBlank(idTarea) ) {
			tareaService.rechazarTarea(idTarea, claveUsuario, ticket);
		}
		
		return null;
	}

	public TareaDTO getTareaForm() {
		return tareaForm;
	}

	public void setTareaForm(TareaDTO tareaForm) {
		this.tareaForm = tareaForm;
	}

	public boolean isValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(boolean validationErrors) {
		this.validationErrors = validationErrors;
	}

	public void setTareaService(TareaService tareaService) {
		this.tareaService = tareaService;
	}
}
