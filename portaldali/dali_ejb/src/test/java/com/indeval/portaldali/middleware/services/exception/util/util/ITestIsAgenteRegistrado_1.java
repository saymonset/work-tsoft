/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exception.util.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.util.UtilServices;

/**
 * Clase de prueba para el servicio dateDiff() de UtilService
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ITestIsAgenteRegistrado_1 extends BaseITestService {

    private UtilServices utilService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @see com.indeval.portaldali.middleware.services.BaseITestService#onSetUp()
     */
    protected void onSetUp() throws Exception {

        super.onSetUp();

        if (utilService == null) {

            utilService = (UtilServices) applicationContext.getBean("utilService");

        }

    }

    /**
     * Prueba para el m&eacute;todo isAgenteRegistrado()
     * 
     * @throws Exception
     */
    public void testIsAgenteRegistrado() throws Exception {
        
        log.info("Entrando a ITestIsAgenteRegistrado_1.testIsAgenteRegistrado()");
        
        assertNotNull(utilService);
        
        utilService.isAgenteRegistrado(new AgenteVO("44", "013"));
                
    }

}
