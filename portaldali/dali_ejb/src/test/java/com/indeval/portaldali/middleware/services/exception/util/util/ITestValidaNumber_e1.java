/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exception.util.util;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.util.UtilServices;

/**
 * Clase de prueba para el servicio validaNumber() de UtilService
 * para el patron limite "cantidad" 
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ITestValidaNumber_e1 extends BaseITestService {

    /** Patron para el limite del valor numerico */
    private static final String PATTER_LIMIT = "99999999999999999999.99999999";
    
    /** Inyeccion del servicio */
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
     * Prueba para el m&eacute;todo validaNumber
     * 
     * @throws Exception
     */
    public void testValidaNumberCantidadInteger() throws Exception {

        log.info("Entrando a ITestValidaNumber_1.testValidaNumber()");
        
        assertNotNull(utilService);
        
        Integer numero = new Integer("123456789012345678901");
        try {
            utilService.validaNumber(numero, PATTER_LIMIT);
            log.debug("Verificar validacion");
            assertTrue(false);
        }
        catch (BusinessException e) {
            e.printStackTrace();
        }

    }
    
    /**
     * Prueba para el m&eacute;todo validaNumber
     * 
     * @throws Exception
     */
    public void testValidaNumberCantidadBigInteger() throws Exception {

        log.info("Entrando a ITestValidaNumber_1.testValidaNumber()");
        
        assertNotNull(utilService);
        
        BigInteger numero = new BigInteger("123456789012345678901");
        try {
            utilService.validaNumber(numero, PATTER_LIMIT);
            log.debug("Verificar validacion");
            assertTrue(false);
        }
        catch (BusinessException e) {
            e.printStackTrace();
        }

    }
    
    /**
     * Prueba para el m&eacute;todo validaNumber
     * 
     * @throws Exception
     */
    public void testValidaNumberCantidadDouble() throws Exception {

        log.info("Entrando a ITestValidaNumber_1.testValidaNumber()");
        
        assertNotNull(utilService);
        
        Double numero = new Double("123456789012345678901.234567890");
        try {
            utilService.validaNumber(numero, PATTER_LIMIT);
            log.debug("Verificar validacion");
            assertTrue(false);
        }
        catch (BusinessException e) {
            e.printStackTrace();
        }

    }
    
    /**
     * Prueba para el m&eacute;todo validaNumber
     * 
     * @throws Exception
     */
    public void testValidaNumberCantidadBigDecimal() throws Exception {

        log.info("Entrando a ITestValidaNumber_1.testValidaNumber()");
        
        assertNotNull(utilService);
        
        BigDecimal numero = new BigDecimal("123456789012345678901.234567890");
        try {
            utilService.validaNumber(numero, PATTER_LIMIT);
            log.debug("Verificar validacion");
            assertTrue(false);
        }
        catch (BusinessException e) {
            e.printStackTrace();
        }

    }

}
