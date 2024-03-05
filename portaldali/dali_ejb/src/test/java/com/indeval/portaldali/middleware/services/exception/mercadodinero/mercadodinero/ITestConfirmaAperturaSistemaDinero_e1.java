/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exception.mercadodinero.mercadodinero;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
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
public class ITestConfirmaAperturaSistemaDinero_e1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final Logger logger = LoggerFactory.getLogger(ITestConfirmaAperturaSistemaDinero_e1.class);

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
     * TestCase para el servicio de confirmaAperturaSistemaDinero()
     *
     * @throws Exception
     */
    public void testConfirmaAperturaSistemaDinero() throws Exception {
        
        log.info("Entrando a ITestConfirmaAperturaSistemaDinero_1.testConfirmaAperturaSistemaDinero()");

        assertNotNull(mercadoDineroService);

        log
                .debug("Prueba sin folios control recibidos y con agente inexistente");
        AgenteVO agente = new AgenteVO();
        agente.setId("01");
        agente.setFolio("000");
        BigInteger[] folioControlVacio = {};
        long startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.confirmaAperturaSistemaDinero(agente,
                    folioControlVacio, true);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        double milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio ConfirmaAperturaSistemaDinero():"
                        + milisegundos);

        log.debug("Prueba con folios control recibidos y agente existente");
        agente.setId("01");
        agente.setFolio("001");
        BigInteger[] folioControl = { new BigInteger("4006"),
                new BigInteger("4007"), new BigInteger("4008") }; // Para MD
        // BigInteger[] folioControl = {new BigInteger("112"), new
        // BigInteger("113"), new BigInteger("114")}; //Para MC - MISCFISC
        startTime = System.currentTimeMillis();
        boolean actualizados = mercadoDineroService
                .confirmaAperturaSistemaDinero(agente, folioControl, true);
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio ConfirmaAperturaSistemaDinero():"
                        + milisegundos);
        log.debug("Num. Folios Recibidos==> " + folioControl.length
                + "; se actualizaron corectamente: " + actualizados);

        log
                .debug("Ejecucion exitosa del testEJBConfirmaAperturaSistemaDinero()");

    }

    /**
     * Prueba del servicio confirmaAperturaSistemaDinero2
     *
     * @throws Exception
     */
    public void testConfirmaAperturaSistemaDinero2() throws Exception {
        
        log.info("Entrando a ITestConfirmaAperturaSistemaDinero_1.testConfirmaAperturaSistemaDinero2()");
        
        assertNotNull(mercadoDineroService);

        AgenteVO agenteVO = new AgenteVO();
        agenteVO.setId("01");
        agenteVO.setFolio("001");
        BigInteger[] folios = { new BigInteger("3368"), new BigInteger("3370") };

        boolean resultado = mercadoDineroService.confirmaAperturaSistemaDinero(
                agenteVO, folios, true);

        log.debug("Resultado: " + resultado);
    }

}