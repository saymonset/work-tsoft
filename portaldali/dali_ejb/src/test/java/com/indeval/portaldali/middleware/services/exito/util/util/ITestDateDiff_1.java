/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.util.util;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.util.UtilServices;

/**
 * Clase de prueba para el servicio dateDiff() de UtilService
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ITestDateDiff_1 extends BaseITestService {

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
     * Prueba para el m&eacute;todo dateDiff
     * 
     * @throws Exception
     */
    public void testDateDiff() throws Exception {

        log.info("Entrando al metodo testDateDiff");
        
        assertNotNull(utilService);

        Calendar c = Calendar.getInstance();
        c.set(2007, Calendar.AUGUST, 20);

        Calendar cc = Calendar.getInstance();
        cc.set(2007, Calendar.AUGUST, 21);

        long diferencia = utilService.dateDiff(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                .get(Calendar.DATE), cc.get(Calendar.YEAR), cc.get(Calendar.MONTH), cc
                .get(Calendar.DATE));
        log.debug("La fecha es: " + diferencia);

        log.debug("" + c.get(Calendar.YEAR));
        log.debug("" + c.get(Calendar.MONTH));
        log.debug("" + c.get(Calendar.DATE));
        log.debug("" + cc.get(Calendar.YEAR));
        log.debug("" + cc.get(Calendar.MONTH));
        log.debug("" + cc.get(Calendar.DATE));

    }

}
