/**
 * 
 */
package com.indeval.portaldali.presentation.controller.common;

import javax.faces.event.ActionEvent;

import com.indeval.portaldali.middleware.dto.MensajePortalDTO;
import com.indeval.portaldali.middleware.services.common.MensajePortalService;

/**
 * @author Rafael Ibarra
 * 
 */
public class MensajePortalBean extends ControllerBase {
	
	/**
	 * Servicio para obtener y guardar el mensaje del Portal
	 */
	private MensajePortalService mensajePortalService;
	
	/**
	 * Objeto para representar el mensaje y su valor de habilitado
	 */
	private MensajePortalDTO mensajeDTO;
	
	/**
	 * Action Listener para limpiar la forma
	 * 
	 * @param event
	 */
	public void limpiar( ActionEvent event ) {
		logger.info("Entrando a MensajePortalBean.limpiar");
		mensajeDTO.setMensaje(null);
		mensajeDTO.setHabilitado(false);
	}
	
	/**
	 * Action Listener para guardar la forma
	 * 
	 * @param event
	 */
	public void guardar( ActionEvent event ) {
		logger.info("Entrando a MensajePortalBean.guardar");
		mensajePortalService.guardaMensaje(mensajeDTO);
	}
	
	/**
	 * Metodo para inicializar la forma, o sea, ir por el mensaje
	 */
	public String getInit() {
		mensajeDTO = mensajePortalService.getMensaje();
		return null;
	}
	
	/**
	 * @param mensajePortalService the mensajePortalService to set
	 */
	public void setMensajePortalService(MensajePortalService mensajePortalService) {
		this.mensajePortalService = mensajePortalService;
	}

	/**
	 * @param mensajePortal the mensajePortal to set
	 */
	public void setMensaje(MensajePortalDTO mensajePortal) {
		this.mensajeDTO = mensajePortal;
	}

	/**
	 * @return the mensajePortal
	 */
	public MensajePortalDTO getMensaje() {
		return mensajeDTO;
	}

}
