/**
 * Bursatec - Portal DALI
 * Copyrigth (c) 2014 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.common;

/**
 * Interface de negocio para realizar el procesamiento de mensajes que cumplen con un formato.
 * 
 * @author Pablo Balderas
 */
public interface ProcesadorMensajesFormato {

	/**
	 * 
	 * @param mensaje
	 */
	void procesarMensaje(String mensaje);
	
}
