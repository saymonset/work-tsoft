package com.indeval.portalinternacional.middleware.services.divisioninternacional;


/**
 * Servicio para manejo del Mensaje del Portal
 * 
 * @author Rafael Ibarra
 */
public interface MensajePortalService {

	/**
	 * Regresa el mensaje; o nulo en caso de que no exista o de que este
	 * deshabilitado
	 * 
	 * @return String
	 */
	public String getMensajeWeb();

}