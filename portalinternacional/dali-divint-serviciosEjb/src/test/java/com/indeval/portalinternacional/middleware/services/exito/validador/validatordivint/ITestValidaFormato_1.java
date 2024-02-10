/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.exito.validador.validatordivint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.validador.ValidatorDivIntService;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ITestValidaFormato_1 extends BaseITestService {

    /** Servicio de log */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

    /** Servicio que sera probado en esta prueba */
    private ValidatorDivIntService validatorDivIntService;

    /**
     * @see com.indeval.portalinternacional.portalinternacional.services.BaseITestService#onSetUp()
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (validatorDivIntService == null) {
        	validatorDivIntService = (ValidatorDivIntService) applicationContext.getBean(
        			"validatorDivIntService");
        }
    }
    
    /**
     * testCase para probar el metodo fileTransferService.muestraInformacion()
     */
    public void testValidaFormato_1() {
    	
    	log.info("Entrando a ITestValidaFormato_1.testValidaFormato_1()");
    	
    	assertNotNull(validatorDivIntService);
    	
    }
    
}
