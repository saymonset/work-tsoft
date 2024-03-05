/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.util.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.util.UtilServices;

/**
 * Clase de prueba para el servicio isCplexActive() de UtilService
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ITestIsCplexActive_1 extends BaseITestService {

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
     * 
     * @throws BusinessException
     */
    public void testIsCplexActive() throws BusinessException {

        log.debug("Entrando al metodo testIsCplexActive()");
        
        assertNotNull(utilService);

        boolean activo = utilService.isCplexActive();
        String buffer = activo ? "ACTIVO" : "INACTIVO";
        log.debug("El CPLEX esta " + buffer);

    }

}
