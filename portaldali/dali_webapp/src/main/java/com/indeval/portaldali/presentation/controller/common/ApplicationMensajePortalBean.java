/**
 * 
 */
package com.indeval.portaldali.presentation.controller.common;

import com.indeval.portaldali.middleware.services.common.MensajePortalService;

/**
 * @author Rafael Ibarra
 * 
 */
public class ApplicationMensajePortalBean extends ControllerBase {
	
	/**
	 * Servicio para obtener y guardar el mensaje del Portal
	 */
	private MensajePortalService mensajePortalService;
	
	/**
	 * Metodo que devuelve el mensaje desde Base de Datos, regresa null
	 * si no existe el registro, si viene vacio o si esta deshabilitado
	 * @return
	 */
	public String getMensajePortal() {
		logger.trace("Entrando a ApplicationMensajePortalBean.getMensajePortal");
		return mensajePortalService.getMensajeWeb();
	}

	/**
	 * @param mensajePortalService the mensajePortalService to set
	 */
	public void setMensajePortalService(MensajePortalService mensajePortalService) {
		this.mensajePortalService = mensajePortalService;
	}

}
