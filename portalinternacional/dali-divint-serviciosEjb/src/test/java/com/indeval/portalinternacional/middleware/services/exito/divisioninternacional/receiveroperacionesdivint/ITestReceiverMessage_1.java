/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.receiveroperacionesdivint;

import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ReceiverOperacionesDivIntService;
import com.mockrunner.mock.jms.MockTextMessage;


/**
 * @author javiles
 *
 */
public class ITestReceiverMessage_1 extends BaseITestService {

    /** Servicio de log */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

    /** Servicio que sera probado en esta prueba */
    private ReceiverOperacionesDivIntService receiverOperacionesDivIntService;

    /**
     * @see com.indeval.portalinternacional.portalinternacional.services.BaseITestService#onSetUp()
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (receiverOperacionesDivIntService == null) {
            receiverOperacionesDivIntService = (ReceiverOperacionesDivIntService) applicationContext
                    .getBean("receiverOperacionesDivIntService");
        }
    }
    
    /**
     * TestCase para probar receiverOperacionesDivIntService.receiverMessages();
     */
    public void testReceiverMessage() {
    	
        log.info("Entrando a ITestReceiverMessage_1.testReceiverMessage()");
        
        assertNotNull(receiverOperacionesDivIntService);
        
        StringBuffer sb = new StringBuffer();
        sb.append("375434\n");
        sb.append("544\n");
        sb.append(":16R:GENL\n");
        sb.append(":20C::SEME//466067\n");
        sb.append(":23G:NEWM\n");
        sb.append(":98C::PREP//20080303121115\n");
        sb.append(":16R:LINK\n");
        sb.append(":20C::RELA//73706\n"); // ESTE ES EL FOLIO_INDEVAL_OPERACION_PADRE
        sb.append(":16S:LINK\n");
        sb.append(":16S:GENL\n");
        sb.append(":16R:TRADDET\n");
        sb.append(":98A::ESET//20080303\n");
        sb.append(":98C::TRAD//20080303000002\n");
        sb.append(":90B::DEAL//ACTU/MXN0,\n");
        sb.append(":35B:1/ALFA/A/0022\n");
        sb.append(":16S:TRADDET\n");
        sb.append(":16R:FIAC\n");
        sb.append(":36B::ESTT//UNIT/1,\n");
        sb.append(":97A::SAFE//0050\n");
        sb.append(":16S:FIAC\n");
        sb.append(":16R:SETDET\n");
        sb.append(":22F::SETR//TRAD\n");
        sb.append(":22F::STCO//DLWM\n");
        sb.append(":16R:SETPRTY\n");
        sb.append(":95Q::BUYR//01015\n");
        sb.append(":97A::SAFE//0052\n");
        sb.append(":16S:SETPRTY\n");
        sb.append(":16R:SETPRTY\n");
        sb.append(":95Q::DEAG//01003\n");
        sb.append(":97A::SAFE//0050\n");
        sb.append(":16S:SETPRTY\n");
        sb.append(":16R:SETPRTY\n");
        sb.append(":95P::PSET//INDEMXMM\n");
        sb.append(":16S:SETPRTY\n");
        sb.append(":16S:SETDET\n");
        sb.append("00000100000100009261\n");
        sb.append("R3XwZjrav7xTsH80LSsZRPa9Al1BRw7puT8gCB0HVJ+G27N6/ZBnwUwd7aqAFdFWSVjh5LHJHkrXBg/Jw4IsWuVpuFbakzaWyohraR/OmOiYW/U0MTQv9vmFE22oK1YwA2nwW59b5mOAVQK0U2lFiS4knbvkxGzQUgMGp45IKyM=");
        TextMessage textMessage = new MockTextMessage(sb.toString());
        receiverOperacionesDivIntService.receiverMessages(textMessage);
    }

}
