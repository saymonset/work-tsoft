/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.util.util;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.util.UtilServices;

/**
 * Clase de prueba para el servicio validaNumber() de UtilService
 * para el patron limite "importe"
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ITestValidaNumber_4 extends BaseITestService {
    
    /** Patron para el limite del valor numerico */
    private static final String PATTER_LIMIT = "9999999999999999999999999.99";
    private static final String PATTER_LIMIT_INTEGER = "99999999";
    
    /** Inyeccion del servicio */
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
     * Prueba para el m&eacute;todo validaNumber
     * 
     * @throws Exception
     */
    public void testValidaNumberImporteInteger() throws Exception {

        log.info("Entrando a ITestValidaNumber_1.testValidaNumber()");
        
        assertNotNull(utilService);
        
        Integer numero = new Integer("99999998");
        utilService.validaNumber(numero, PATTER_LIMIT_INTEGER);

    }
    
    /**
     * Prueba para el m&eacute;todo validaNumber
     * 
     * @throws Exception
     */
    public void testValidaNumberImporteBigInteger() throws Exception {

        log.info("Entrando a ITestValidaNumber_1.testValidaNumber()");
        
        assertNotNull(utilService);
        
        BigInteger numero = new BigInteger("9999999999999999999999999");
        utilService.validaNumber(numero, PATTER_LIMIT);

    }
    
    /**
     * Prueba para el m&eacute;todo validaNumber
     * 
     * @throws Exception
     */
    public void testValidaNumberImporteDouble() throws Exception {

        log.info("Entrando a ITestValidaNumber_1.testValidaNumber()");
        
        assertNotNull(utilService);
        
        Double numero = new Double("9999999999999999999999999.98");
        utilService.validaNumber(numero, PATTER_LIMIT);

    }
    
    /**
     * Prueba para el m&eacute;todo validaNumber
     * 
     * @throws Exception
     */
    public void testValidaNumberImporteBigDecimal() throws Exception {

        log.info("Entrando a ITestValidaNumber_1.testValidaNumber()");
        
        assertNotNull(utilService);
        
        BigDecimal numero = new BigDecimal("9999999999999999999999999.98");
        utilService.validaNumber(numero, PATTER_LIMIT);

    }

}
