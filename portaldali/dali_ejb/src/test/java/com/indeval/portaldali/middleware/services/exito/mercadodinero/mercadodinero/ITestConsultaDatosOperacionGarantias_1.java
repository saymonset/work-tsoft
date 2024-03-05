/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.mercadodinero.mercadodinero;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
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
public class ITestConsultaDatosOperacionGarantias_1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final LLogger logger = LoggerFactory.getLogger(TestConsultaDatosOperacionGarantias_1.class);

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
     * TestCase para MercadoDineroDao.consultaDatosOperacionGarantias()
     *
     * @throws BusinessException
     */
    public void testConsultaDatosOperacionGarantias()
            throws BusinessException {

        log.info("Entrando a ITestConsultaDatosOperacionGarantias_1.testConsultaDatosOperacionGarantias()");
        
        assertNotNull(mercadoDineroService);

        log.debug("Probando con los parametros NULL");
        long startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.consultaDatosOperacionGarantias(null, null);
            log.debug("Checar validacion");
            assertFalse(true);
        } catch (IllegalArgumentException e) {
            log.debug(e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        double milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio ConsultaDatosOperacionGarantias():"
                        + milisegundos);

        log.debug("Probando con la emision VACIA y el excedente NULL");
        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.consultaDatosOperacionGarantias(
                    new EmisionVO(), null);
            log.debug("Checar validacion");
            assertFalse(true);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio ConsultaDatosOperacionGarantias():"
                        + milisegundos);

        log.debug("Probando con la emision NULL y el excedente");
        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.consultaDatosOperacionGarantias(null,
                    new BigDecimal(1000));
            log.debug("Checar validacion");
            assertFalse(true);
        } catch (IllegalArgumentException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio ConsultaDatosOperacionGarantias():"
                        + milisegundos);

        log.debug("Probando con la emision incompleta y el excedente");
        EmisionVO emisionVO = new EmisionVO();
        emisionVO.setIdTipoValor("XA");
        startTime = System.currentTimeMillis();
        try {
            log.debug("La emision solo lleva el tv");
            mercadoDineroService.consultaDatosOperacionGarantias(null,
                    new BigDecimal(1000));
            log.debug("Checar validacion");
            assertFalse(true);
        } catch (IllegalArgumentException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio ConsultaDatosOperacionGarantias():"
                        + milisegundos);

        emisionVO.setEmisora("BREMS");
        startTime = System.currentTimeMillis();
        try {
            log.debug("La emision lleva el tv y la emisora");
            mercadoDineroService.consultaDatosOperacionGarantias(null,
                    new BigDecimal(1000));
            log.debug("Checar validacion");
            assertFalse(true);
        } catch (IllegalArgumentException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio ConsultaDatosOperacionGarantias():"
                        + milisegundos);

        emisionVO.setSerie("080807");
        startTime = System.currentTimeMillis();
        try {
            log.debug("La emision lleva el tv, la emisora y la serie");
            mercadoDineroService.consultaDatosOperacionGarantias(null,
                    new BigDecimal(1000));
            log.debug("Checar validacion");
            assertFalse(true);
        } catch (IllegalArgumentException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio ConsultaDatosOperacionGarantias():"
                        + milisegundos);

        log.debug("Probando con la emision completa y el excedente NULL");
        emisionVO.setCupon("0000");
        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.consultaDatosOperacionGarantias(emisionVO,
                    null);
            log.debug("Checar validacion");
            assertFalse(true);
        } catch (IllegalArgumentException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio ConsultaDatosOperacionGarantias():"
                        + milisegundos);

        log.debug("Probando con la emision completa y el excedente");
        startTime = System.currentTimeMillis();
        BigInteger operacionGarantiaVOValida = mercadoDineroService
                .consultaDatosOperacionGarantias(emisionVO,
                        new BigDecimal(1000));
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio ConsultaDatosOperacionGarantias():"
                        + milisegundos);
        assertNotNull(operacionGarantiaVOValida);
        log.debug("["
                + ToStringBuilder.reflectionToString(operacionGarantiaVOValida)
                + "]");

        log
                .debug("Ejecucion exitosa del testEJBConsultaDatosOperacionGarantias()");

    }

}