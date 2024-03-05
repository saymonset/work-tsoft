/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.enviooperaciones;

import com.indeval.protocolofinanciero.api.ProtocoloFinancieroException;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface ReceiverOperacionesService {

	/**
	 * Recibe los mensajes enviados por el PFI
	 * 
	 * @throws ProtocoloFinancieroException
	 */
	public void iniciaListener() throws ProtocoloFinancieroException;

}
