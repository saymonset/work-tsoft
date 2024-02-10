/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import javax.jms.TextMessage;


/**
 * @author javiles
 *
 */
public interface ReceiverOperacionesDivIntService {

    /**
     * Recibe las operaciones y las procesa de acuerdo al tipo de objeto recibido
     * 
     * @param textMessage
     */
    public void receiverMessages(TextMessage textMessage);
    
}
