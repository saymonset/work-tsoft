/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ConsultaCatalogosFacade.java
 * 29/02/2008
 */
package com.indeval.portaldali.persistence.dao.common;

import com.indeval.portaldali.persistence.model.MensajePortal;

/**
 * Interface que expone los métodos para los mensajes para el portal.
 * Existe un unico registro con id de 1.
 * 
 * @author Rafael Ibarra Zendejas
 */
public interface MensajePortalDaliDAO {

	/**
	 * Metodo para obtener el mensaje del portal
	 * 
	 * @return MensajePortal
	 */
	public MensajePortal getMensajePortal();
	
	/**
	 * Metodo para acutlizar el mensaje del portal
	 * 
	 * @param MensajePortal
	 */
	public void actualizaMensaje(MensajePortal mensajePortal);

}
