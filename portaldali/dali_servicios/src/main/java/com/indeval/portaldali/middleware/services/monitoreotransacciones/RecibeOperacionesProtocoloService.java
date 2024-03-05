/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.monitoreotransacciones;

import javax.jms.TextMessage;

/**
 * Clase que monitorea los mensajes de protocolo
 * 
 * @author csanchez
 * 
 */
public interface RecibeOperacionesProtocoloService {

	/**
	 * Recibe los mensajes del protocolo
	 * 
	 * @param textMessage
	 */
	void recibeMensajes(TextMessage textMessage);

}
