/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exception.mercadodinero.mercadodinero;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;

/**
 * La clase ITestMercadoDineroService tiene como objetivo probar los
 * m&eacute;todos creados en la clase MercadoDineroService
 *
 * @author Agustin Calderon Orduña.
 * @author Claudia Becerril Rejon.
 * @author Erick Navarrete Sevilla.
 * @author Domingo Suarez Torres.
 * @author Jose Guadalupe Aviles.
 * @author Ricardo Caballero.
 * @author Sergio Mena Zamora.
 * @author Salvador Valeriano Lucas.
 */
public class ITestEjecutaOperacionGarantias_e1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final LLogger logger = LoggerFactory.getLogger(TestEjecutaOperacionGarantias_e1.class);

    /** Inyecci&oacute;n del bean mercadoDineroService */
    private MercadoDineroService mercadoDineroService;

    /** Bean de acceso a DateUtilsDao */
    private DateUtilsDao dateUtilsDao;

    /**
     * En el m&eacute;todo onSetUp inicializamos los beans que utilizaremos en
     * la clase de prueba.
     *
     * @throws Exception
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (mercadoDineroService == null) {
            mercadoDineroService = (MercadoDineroService) applicationContext
                    .getBean("mercadoDineroService");
        }
        if (dateUtilsDao == null) {
            dateUtilsDao = (DateUtilsDao) applicationContext.getBean("dateUtilsDao");
        }
    }

    /**
     * TestCase para MercadoDineroDao.ejecutaOperacionGarantias()()
     *
     * @throws BusinessException
     */
    public void testEjecutaOperacionGarantias() throws BusinessException {
        
        log.info("Entrando a ITestEjecutaOperacionGarantias_1.testEjecutaOperacionGarantias()");

        assertNotNull(mercadoDineroService);

        log.debug("Probando con el agente, la emision y el tipoOperacion NULL");
        long startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.ejecutaOperacionGarantias(null, null,
                    new BigDecimal(1), new BigInteger("0"), null);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            log.debug(e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        double milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio EjecutaOperacionGarantias():"
                        + milisegundos);

        log
                .debug("Probando con el agente VACIO, y la emision y el tipoOperacion NULL");
        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.ejecutaOperacionGarantias(new AgenteVO(),
                    null, new BigDecimal(1), new BigInteger("0"), null);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio EjecutaOperacionGarantias():"
                        + milisegundos);

        log
                .debug("Probando con el agente, la emision y el tipoOperacion VACIO");
        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService
                    .ejecutaOperacionGarantias(new AgenteVO(), new EmisionVO(),
                            new BigDecimal(1), new BigInteger("0"), "");
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio EjecutaOperacionGarantias():"
                        + milisegundos);

        log
                .debug("Probando con el agente, y la emision y el tipoOperacion VACIO");
        AgenteVO agenteVO = new AgenteVO("02", "012", "6320");
        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService
                    .ejecutaOperacionGarantias(agenteVO, new EmisionVO(),
                            new BigDecimal(1), new BigInteger("0"), "");
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio EjecutaOperacionGarantias():"
                        + milisegundos);

        log
                .debug("Probando con el agente y la emision, y el tipoOperacion VACIO");
        EmisionVO emision = new EmisionVO();
        emision.setCupon("0000");
        emision.setEmisora("BREMS");
        emision.setIdTipoValor("XA");
        emision.setSerie("080807");
        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.ejecutaOperacionGarantias(agenteVO, emision,
                    new BigDecimal(1), new BigInteger("0"), "");
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio EjecutaOperacionGarantias():"
                        + milisegundos);

        log.debug("Probando con todos los parametros");
        startTime = System.currentTimeMillis();
        Integer folio = mercadoDineroService.ejecutaOperacionGarantias(
                agenteVO, emision, new BigDecimal(1), new BigInteger("0"), "R");
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio EjecutaOperacionGarantias():"
                        + milisegundos);
        assertNotNull(folio);
        log.debug("Folio Operacion: [" + folio + "]");

        log.debug("Ejecucion exitosa del testEJBEjecutaOperacionGarantias()");

    }

}