/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.protocolofinancieroapi;

import javax.jms.TextMessage;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface TransformaMensaje {

	/**
	 * Transforma los mensajes del protocolo a VOs
	 * 
	 * @param tm
	 * @return VO especifico
	 */
	public Object generaVO(TextMessage tm);

}
