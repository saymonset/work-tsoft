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
public class ITestTraspasoMercadoCapitales_1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final LLogger logger = LoggerFactory.getLogger(TestTraspasoMercadoCapitales_1.class);

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
     * TestCase para el servicio de traspasoMercadoDinero() atendiendo a Mercado
     * de Capitales
     *
     * @throws BusinessException
     */
    public void testTraspasoMercadoCapitales() throws BusinessException {

        log.info("Entrando a ITestTraspasoMercadoCapitales_1.testTraspasoMercadoCapitales()");

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
        AgenteVO traspasante = new AgenteVO("01", "003", "0307");
        traspasante.setNombreCuenta("TERC"); // solo traslp
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
        AgenteVO receptor = new AgenteVO("02", "014", "6620");
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
        emision.setIdTipoValor("00");
        emision.setEmisora("BANCEN");
        emision.setSerie("BCPC");
        emision.setCupon("0000");
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
        param.setCantidad(new BigDecimal(1000));
        param.setFechaAdquisicion(new Date()); // solo traslp
        param.setPrecioAdquisicion(new BigDecimal(150)); // solo traslp
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

        log.debug("Ejecucion exitosa del testEJBTraspasoMercadoCapitales()");

    }

}