/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.mercadodinero.mercadodinero;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
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
public class ITestVencimientoAnticipado_1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final LLogger logger = LoggerFactory.getLogger(TestVencimientoAnticipado_1.class);

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
     * TestCase para el servicio de encimientoAnticipado vencimientoAnticipado()
     *
     * @throws Exception
     */
    public void testVencimientoAnticipado() throws Exception {
        
        log.info("Entrando a ITestVencimientoAnticipado_1.testVencimientoAnticipado()");

        assertNotNull(mercadoDineroService);

        log.debug("Prueba con parametro nulo...");
        long startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.vencimientoAnticipado(null);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        double milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio VencimientoAnticipado():"
                        + milisegundos);

        log.debug("Prueba con parametro invalido...");
        startTime = System.currentTimeMillis();
        boolean retorno = mercadoDineroService
                .vencimientoAnticipado(new BigDecimal(105290506));
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio VencimientoAnticipado():"
                        + milisegundos);
        assertFalse(retorno);

        log.debug("Prueba con parametro valido...");
        startTime = System.currentTimeMillis();
        retorno = mercadoDineroService.vencimientoAnticipado(new BigDecimal(
                348231106));
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio VencimientoAnticipado():"
                        + milisegundos);
        assertTrue(retorno);

        log.debug("Ejecucion exitosa del testEJBVencimientoAnticipado()");

    }

}