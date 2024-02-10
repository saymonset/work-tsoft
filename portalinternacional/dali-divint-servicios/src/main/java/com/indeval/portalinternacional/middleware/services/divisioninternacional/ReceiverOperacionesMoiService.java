/**
 * 
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import javax.jms.TextMessage;

/**
 * @author javiles
 *
 */
public interface ReceiverOperacionesMoiService {
    
    /**
     * Recibe las operaciones y las procesa de acuerdo al tipo de objeto recibido
     * 
     * @param textMessage
     */
    public void receiverMessages(TextMessage textMessage);

}
