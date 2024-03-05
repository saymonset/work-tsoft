/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.util.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.util.UtilServices;

/**
 * Clase de prueba para el servicio leeArchivo() de UtilService
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ITestLeeArchivo_1 extends BaseITestService {

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
    public void testLeeArchivo() throws BusinessException {

        log.debug("Entrando a testLeeArchivo");

        assertNotNull(utilService);

        String lectura = utilService.leeArchivo(new File("c:\\indeval\\dinero.dat"));

        log.debug("Lectura: " + lectura);

        log.debug("Saliendo de testLeeArchivo");

    }

}
