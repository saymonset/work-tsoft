/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.mercadodinero.mercadodinero;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.mercadodinero.TraspasoMercadoDineroParams;
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
public class ITestTraspasoMercadoDinero_1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final LLogger logger = LoggerFactory.getLogger(TestTraspasoMercadoDinero_1.class);

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
     * TestCase para el servicio de traspasoMercadoDinero()
     *
     * @throws BusinessException
     */
    public void testTraspasoMercadoDinero() throws BusinessException {

        log.info("Entrando a ITestTraspasoMercadoDinero_1.testTraspasoMercadoDinero()");

        assertNotNull(mercadoDineroService);

        log.debug("Probando con el params NULL");
        TraspasoMercadoDineroParams param = null;
        long startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.traspasoMercadoDinero(param);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        double milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio TraspasoMercadoDinero():"
                        + milisegundos);

        log.debug("Probando con el params VACIO");
        param = new TraspasoMercadoDineroParams();
        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.traspasoMercadoDinero(param);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio TraspasoMercadoDinero():"
                        + milisegundos);

        log.debug("Probando con el params solo con el Traspasante");
        param = new TraspasoMercadoDineroParams();
        AgenteVO traspasante = new AgenteVO("01", "007", "0802");
        traspasante.setNombreCuenta("PROP"); // solo traslp
        startTime = System.currentTimeMillis();
        try {
            param.setTraspasante(traspasante);
            mercadoDineroService.traspasoMercadoDinero(param);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio TraspasoMercadoDinero():"
                        + milisegundos);

        log.debug("Probando con el params solo con el Receptor");
        param = new TraspasoMercadoDineroParams();
        AgenteVO receptor = new AgenteVO("01", "029", "3410");
        receptor.setNombreCuenta("PROP"); // solo traslp
        startTime = System.currentTimeMillis();
        try {
            param.setReceptor(receptor);
            mercadoDineroService.traspasoMercadoDinero(param);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio TraspasoMercadoDinero():"
                        + milisegundos);

        log.debug("Probando con el params solo con el Receptor");
        param = new TraspasoMercadoDineroParams();
        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.traspasoMercadoDinero(param);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio TraspasoMercadoDinero():"
                        + milisegundos);

        log.debug("Probando con el params solo con la Emision");
        EmisionVO emision = new EmisionVO();
        emision.setIdTipoValor("93");
        emision.setEmisora("CREYCA");
        emision.setSerie("01906");
        emision.setCupon("0002");
        param.setEmision(emision);
        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.traspasoMercadoDinero(param);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio TraspasoMercadoDinero():"
                        + milisegundos);

        log.debug("Probando con el params sin el tipo de movimiento");
        param.setTraspasante(traspasante);
        param.setReceptor(receptor);
        param.setCantidad(new BigDecimal(10));
        param.setFechaAdquisicion(new Date()); // solo traslp
        param.setPrecioAdquisicion(new BigDecimal(5)); // solo traslp
        param.setCliente("AAAA"); // solo traslp
        param.setRfcCURP("AAAAAA"); // solo traslp
        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.traspasoMercadoDinero(param);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio TraspasoMercadoDinero():"
                        + milisegundos);

        log
                .debug("Probando con el params lleno y el tipo de movimiento TRASPASO");
        param.setTipoMovimiento("TRASPASO"); // TRASPASO/APERTURA

        startTime = System.currentTimeMillis();
        BigInteger folio = mercadoDineroService.traspasoMercadoDinero(param);
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio TraspasoMercadoDinero():"
                        + milisegundos);
        assertNotNull(folio);
        log.debug("Folio Operacion: [" + folio + "]");

        // log.debug("Probando con el params lleno y el tipo de movimiento
        // APERTURA");
        // param.setTipoMovimiento("APERTURA"); //TRASPASO/APERTURA
        //
        // folio = mercadoDineroService.traspasoMercadoDinero(param);
        // assertNotNull(folio);
        // log.debug("Folio Operacion: [" + folio + "]");

        log.debug("Ejecucion exitosa del testEJBTraspasoMercadoDinero()");

    }

    /**
     * Prueba del servicio traspasoMercadoDinero
     *
     * @throws BusinessException
     */
    public void testTraspasoMercadoDinero2() throws BusinessException {
        assertNotNull(mercadoDineroService);

        TraspasoMercadoDineroParams params = new TraspasoMercadoDineroParams();
        AgenteVO traspasante = new AgenteVO();
        traspasante.setId("01");
        traspasante.setFolio("003");
        traspasante.setCuenta("0307");
        params.setTraspasante(traspasante);
        AgenteVO receptor = new AgenteVO();
        receptor.setId("01");
        receptor.setFolio("001");
        receptor.setCuenta("0109");
        params.setReceptor(receptor);
        EmisionVO emision = new EmisionVO();
        emision.setIdTipoValor("2U");
        emision.setEmisora("CBIC006");
        emision.setSerie("321125");
        emision.setCupon("0009");
        emision.setIsin("MX2UCB1D0004");
        params.setEmision(emision);
        params.setCantidad(new BigDecimal("12456"));
        params.setTipoMovimiento("APERTURA");

        BigInteger folio = mercadoDineroService.traspasoMercadoDinero(params);
        assertNotNull(folio);

        log.debug("FOLIO APERTURA: " + folio);
    }

}