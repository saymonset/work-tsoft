package com.indeval.portaldali.middleware.services.advice;

import javax.jms.TextMessage;

/**
 * Interface del servicio que consume los mensajes de 
 * la bitacora de consultas
 * 
 * @author Rafael Ibarra
 *
 */
public interface ReceiverBitacoraConsultaService {

	/**
	 * Metodo para recibir mensajes
	 * 
	 * @param TextMessage mensaje
	 */
	public void recibeMensajes(TextMessage textMessage);
	
}
