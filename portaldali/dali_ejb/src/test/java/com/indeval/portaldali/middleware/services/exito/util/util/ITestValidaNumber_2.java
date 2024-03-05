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
 * para el patron limite "precioxtitulo"
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ITestValidaNumber_2 extends BaseITestService {

    /** Patron para el limite del valor numerico */
    private static final String PATTER_LIMIT = "9999.99999999";
    
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
    public void testValidaNumberPrecioXTituloInteger() throws Exception {

        log.info("Entrando a ITestValidaNumber_1.testValidaNumber()");
        
        assertNotNull(utilService);
        
        Integer numero = new Integer("9998");
        utilService.validaNumber(numero, PATTER_LIMIT);

    }
    
    /**
     * Prueba para el m&eacute;todo validaNumber
     * 
     * @throws Exception
     */
    public void testValidaNumberPrecioXTituloBigInteger() throws Exception {

        log.info("Entrando a ITestValidaNumber_1.testValidaNumber()");
        
        assertNotNull(utilService);
        
        BigInteger numero = new BigInteger("9998");
        utilService.validaNumber(numero, PATTER_LIMIT);

    }
    
    /**
     * Prueba para el m&eacute;todo validaNumber
     * 
     * @throws Exception
     */
    public void testValidaNumberPrecioXTituloDouble() throws Exception {

        log.info("Entrando a ITestValidaNumber_1.testValidaNumber()");
        
        assertNotNull(utilService);
        
        Double numero = new Double("9999.999999989");
        utilService.validaNumber(numero, PATTER_LIMIT);

    }
    
    /**
     * Prueba para el m&eacute;todo validaNumber
     * 
     * @throws Exception
     */
    public void testValidaNumberPrecioXTituloBigDecimal() throws Exception {

        log.info("Entrando a ITestValidaNumber_1.testValidaNumber()");
        
        assertNotNull(utilService);
        
        BigDecimal numero = new BigDecimal("9999.999999989");
        utilService.validaNumber(numero, PATTER_LIMIT);

    }

}
