/**
 * 2H Software - Bursatec
 * Sistema de Consulta de Estados de Cuenta
 *
 */
package com.indeval.portaldali.middleware.services.common;

import com.indeval.portaldali.middleware.dto.MensajePortalDTO;

/**
 * Servicio para manejo del Mensaje del Portal
 * 
 * @author Rafael Ibarra
 */
public interface MensajePortalService {

	/**
	 * Regresa el objeto MensajePortal; o nulo en caso de que no exista
	 * 
	 * @return String
	 */
	public MensajePortalDTO getMensaje();

	/**
	 * Regresa el mensaje; o nulo en caso de que no exista o de que este
	 * deshabilitado
	 * 
	 * @return String
	 */
	public String getMensajeWeb();

	/**
	 * Guarda los cambios en el mensaje
	 * 
	 * @param MensajePortalDTO
	 */
	public void guardaMensaje(MensajePortalDTO mensaje);

}