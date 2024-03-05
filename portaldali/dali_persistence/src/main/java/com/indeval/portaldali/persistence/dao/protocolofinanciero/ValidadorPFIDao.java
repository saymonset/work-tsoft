/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.protocolofinanciero;

import com.indeval.portaldali.persistence.dao.protocolofinanciero.impl.support.configuration.CacheStoreMatch;


/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface ValidadorPFIDao extends CacheStoreMatch{

	/**
	 * @param id
	 * @param folio
	 * @return boolean
	 */
	boolean validarParticipantePFI(String id, String folio);
	
    /**
     * @return String
     */
    String getOrigenBanxico();
    
}
