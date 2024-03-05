/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exception.mercadodinero.mercadodinero;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadodinero.CapturaConcertacionPrestamosParams;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;
import com.indeval.portaldali.persistence.util.DateUtil;

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
public class ITestCapturaConcertacionPrestamos_e1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final LLogger logger = LoggerFactory.getLogger(TestCapturaConcertacionPrestamos_e1.class);

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
     * TestCase para el servicio de capturaConcertacionPrestamos()
     * TODO - Esta prueba es dependiente de la fecha
     *
     * @throws BusinessException
     */
    public void testCapturaConcertacionPrestamos() throws BusinessException {
        
        log.info("Entrando a ITestCapturaConcertacionPrestamos_1.testCapturaConcertacionPrestamos()");

        assertNotNull(mercadoDineroService);

        Integer resultado = null;
        Integer folioPrestamo = null;
        List list = null;

        log.debug("Probando con el params NULL");
        CapturaConcertacionPrestamosParams param = null;

        long startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.capturaConcertacionPrestamos(param);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        double milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio CapturaConcertacionPrestamos():"
                        + milisegundos);

        log.debug("Probando con el params VACIO");
        param = new CapturaConcertacionPrestamosParams();
        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.capturaConcertacionPrestamos(param);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio CapturaConcertacionPrestamos():"
                        + milisegundos);

        log.debug("Probando con un prestamista que no existe");
        param.setIdPrestamista("033");
        param.setFolioPrestamista("7779");
        param.setId("060");
        param.setFolio("7777");
        param.setCantidadSolicitada(new BigInteger("10000"));

        EmisionVO emisionVOPrestamistaInexistente = new EmisionVO();
        emisionVOPrestamistaInexistente.setIdTipoValor("BI");
        emisionVOPrestamistaInexistente.setEmisora("GOBFED");
        emisionVOPrestamistaInexistente.setSerie("070927");
        emisionVOPrestamistaInexistente.setCupon("0000");
        param.setEmision(emisionVOPrestamistaInexistente);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2006, Calendar.DECEMBER, 01);
        param.setFecha(calendar.getTime());

        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.capturaConcertacionPrestamos(param);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio CapturaConcertacionPrestamos():"
                        + milisegundos);

        log
                .debug("Probando cuando no existe la emision. La emision ya vencio o no existe el papel.");
        param.setIdPrestamista("02");
        param.setFolioPrestamista("033");
        param.setId("02");
        param.setFolio("013");
        EmisionVO emisionVOInvalida = new EmisionVO();
        emisionVOInvalida.setIdTipoValor("BI");
        emisionVOInvalida.setEmisora("GOBFED");
        emisionVOInvalida.setSerie("061101");
        emisionVOInvalida.setCupon("0000");
        param.setEmision(emisionVOInvalida);
        calendar.set(2007, Calendar.DECEMBER, 01);
        param.setFecha(calendar.getTime());

        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.capturaConcertacionPrestamos(param);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio CapturaConcertacionPrestamos():"
                        + milisegundos);

        log
                .debug("Probando con un prestamo mayor al plazo maximo de prestamo en dias naturales");
        EmisionVO emisionVOPrestamoMayor = new EmisionVO();
        emisionVOPrestamoMayor.setIdTipoValor("LD");
        emisionVOPrestamoMayor.setEmisora("GOBFED");
        emisionVOPrestamoMayor.setSerie("080807");
        emisionVOPrestamoMayor.setCupon("0000");
        param.setEmision(emisionVOPrestamoMayor);
        calendar.set(2007, Calendar.DECEMBER, 11);
        param.setFecha(calendar.getTime());

        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.capturaConcertacionPrestamos(param);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio CapturaConcertacionPrestamos():"
                        + milisegundos);

        log.debug("Probando con un prestamo vencido");
        EmisionVO emisionVOPrestamoVencido = new EmisionVO();
        emisionVOPrestamoVencido.setIdTipoValor("BI");
        emisionVOPrestamoVencido.setEmisora("GOBFED");
        emisionVOPrestamoVencido.setSerie("070927");
        emisionVOPrestamoVencido.setCupon("0000");
        param.setEmision(emisionVOPrestamoVencido);
        calendar.set(2006, Calendar.NOVEMBER, 15);
        param.setFecha(calendar.getTime());

        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.capturaConcertacionPrestamos(param);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio CapturaConcertacionPrestamos():"
                        + milisegundos);

        log.debug("Probando con una emision valida");

        EmisionVO emisionVOValida = new EmisionVO();
        emisionVOValida.setIdTipoValor("BI");
        emisionVOValida.setEmisora("GOBFED");
        emisionVOValida.setSerie("070524");
        emisionVOValida.setCupon("0000");
        param.setEmision(emisionVOValida);

        log.debug("la fecha del dia es... " + dateUtilsDao.getDateFechaDB());
        param.setFecha(DateUtil.addDays(dateUtilsDao.getDateFechaDB(), 1));
        log.debug("la fecha setteada es... " + param.getFecha().toString());

        param.setPlazo(new Integer("1"));

        startTime = System.currentTimeMillis();
        Integer folio = mercadoDineroService
                .capturaConcertacionPrestamos(param);
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio CapturaConcertacionPrestamos():"
                        + milisegundos);
        assertNotNull(folio);
        log.debug("Folio Operacion: [" + folio + "]");

        log
                .debug("Ejecucion exitosa del testEJBCapturaConcertacionPrestamos()");

    }

}