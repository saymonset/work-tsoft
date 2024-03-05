/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exception.util.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.util.UtilServices;

/**
 * Clase de prueba para el servicio validaDTONoNulo() de UtilService
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ITestValidaDTONoNulo_e1 extends BaseITestService {

	/** Bean para UtilService */
    private UtilServices utilService;

    /** Log de bitacora */
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
     * 
     * @throws Exception
     */
    public void testValidaDTONoNulo() throws Exception {

        log.debug("Entrando al metodo testValidaDTONoNulo");
        
        assertNotNull(utilService);
        
        utilService.validaDTONoNulo(null, "parameter");

    }

}
