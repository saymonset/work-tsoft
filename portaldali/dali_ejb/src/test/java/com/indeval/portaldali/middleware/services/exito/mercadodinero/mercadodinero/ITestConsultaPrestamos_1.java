/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.mercadodinero.mercadodinero;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadodinero.ConsultaPrestamosParams;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.mercadodinero.PrestamoVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;
import com.indeval.portaldali.persistence.util.UtilsLog;

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
public class ITestConsultaPrestamos_1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final LLogger logger = LoggerFactory.getLogger(TestConsultaPrestamos_1.class);

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
     * TestCase para el servicio de mercado de dinero consultaPrestamos()
     *
     * @throws BusinessException
     */
    public void testConsultaPrestamos() throws BusinessException {
        
        log.info("Entrando a ITestConsultaPrestamos_1.testConsultaPrestamos()");

        assertNotNull(mercadoDineroService);

//        log.debug("Probando con el objeto params nulo");
        ConsultaPrestamosParams params = null;
        long startTime = System.currentTimeMillis();
//        try {
//            mercadoDineroService.consultaPrestamos(params);
//            log.debug("Checar validacion");
//            assertTrue(false);
//        } catch (IllegalArgumentException e) {
//            log.debug(e.getMessage());
//        }
        long endTime = System.currentTimeMillis();
        double milisegundos = (endTime - startTime);
//        log.debug("Milisegundos en responder el servicio ConsultaPrestamos():"
//                + milisegundos);
//
//        log.debug("Probando con el objeto params vacio");
//        startTime = System.currentTimeMillis();
//        try {
            params = new ConsultaPrestamosParams();
//            mercadoDineroService.consultaPrestamos(params);
//            log.debug("Checar validacion");
//            assertTrue(false);
//        } catch (IllegalArgumentException e) {
//            log.debug(e.getMessage());
//        }
//        endTime = System.currentTimeMillis();
//        milisegundos = (endTime - startTime);
//        log.debug("Milisegundos en responder el servicio ConsultaPrestamos():"
//                + milisegundos);
//
//        log.debug("Probando con el IdPrestatario NULL en el objeto params");
//        startTime = System.currentTimeMillis();
//        try {
//            params = new ConsultaPrestamosParams();
//            params.setFolPrestatario("061");
//            mercadoDineroService.consultaPrestamos(params);
//            log.debug("Checar validacion");
//            assertTrue(false);
//        } catch (IllegalArgumentException e) {
//            log.debug(e.getMessage());
//        }
//        endTime = System.currentTimeMillis();
//        milisegundos = (endTime - startTime);
//        log.debug("Milisegundos en responder el servicio ConsultaPrestamos():"
//                + milisegundos);
//
//        log.debug("Probando con el IdPrestatario VACIO en el objeto params");
//        startTime = System.currentTimeMillis();
//        try {
//            params = new ConsultaPrestamosParams();
//            params.setIdPrestatario("");
//            params.setFolPrestatario("061");
//            mercadoDineroService.consultaPrestamos(params);
//            log.debug("Checar validacion");
//            assertTrue(false);
//        } catch (BusinessException e) {
//            log.debug(e.getMessage());
//        }
//        endTime = System.currentTimeMillis();
//        milisegundos = (endTime - startTime);
//        log.debug("Milisegundos en responder el servicio ConsultaPrestamos():"
//                + milisegundos);
//
//        log.debug("Probando con el FolPrestatario NULL en el objeto params");
//        startTime = System.currentTimeMillis();
//        try {
//            params = new ConsultaPrestamosParams();
//            params.setFolPrestatario(null);
//            params.setIdPrestatario("02");
//            mercadoDineroService.consultaPrestamos(params);
//            log.debug("Checar validacion");
//            assertTrue(false);
//        } catch (IllegalArgumentException e) {
//            log.debug(e.getMessage());
//        }
//        endTime = System.currentTimeMillis();
//        milisegundos = (endTime - startTime);
//        log.debug("Milisegundos en responder el servicio ConsultaPrestamos():"
//                + milisegundos);
//
//        log.debug("Probando con el FolPrestatario VACIO en el objeto params");
//        startTime = System.currentTimeMillis();
//        try {
//            params = new ConsultaPrestamosParams();
//            params.setFolPrestatario("");
//            params.setIdPrestatario("02");
//            mercadoDineroService.consultaPrestamos(params);
//            log.debug("Checar validacion");
//            assertTrue(false);
//        } catch (BusinessException e) {
//            log.debug(e.getMessage());
//        }
//        endTime = System.currentTimeMillis();
//        milisegundos = (endTime - startTime);
//        log.debug("Milisegundos en responder el servicio ConsultaPrestamos():"
//                + milisegundos);
//
//        log.debug("Probando solo con el agente firmado (prestatario)");

        params.setIdPrestatario("02");
        params.setFolPrestatario("061");
//        startTime = System.currentTimeMillis();
//        PrestamoVO[] prestamosSoloPrestatario = mercadoDineroService
//                .consultaPrestamos(params);
//        endTime = System.currentTimeMillis();
//        milisegundos = (endTime - startTime);
//        log.debug("Milisegundos en responder el servicio ConsultaPrestamos():"
//                + milisegundos);
//        assertNotNull(prestamosSoloPrestatario);
//        log.debug("El arreglo de prestamos tiene ["
//                + prestamosSoloPrestatario.length + "] elementos");
//        assertTrue(prestamosSoloPrestatario.length > 0);
//        UtilsLog.logElementosArreglo(prestamosSoloPrestatario, true);
//        UtilsLog.logElementosArreglo(prestamosSoloPrestatario, false);
//
//        log.debug("Probando con el agente firmado (prestatario) y la emision indicando "
//                        + "como sobrante");
//
//        EmisionVO emisionVO = new EmisionVO(); // parametro opcional
//        emisionVO.setIdTipoValor("BI"); // parametro opcional
//        emisionVO.setEmisora("GOBFED"); // parametro opcional
//        emisionVO.setSerie("070111"); // parametro opcional
//        emisionVO.setCupon("0000"); // parametro opcional
//        params.setEmision(emisionVO); // parametro opcional
//        params.setFaltanteSobrante(Boolean.TRUE);
//        startTime = System.currentTimeMillis();
//        PrestamoVO[] prestamos = mercadoDineroService.consultaPrestamos(params);
//        endTime = System.currentTimeMillis();
//        milisegundos = (endTime - startTime);
//        log.debug("Milisegundos en responder el servicio ConsultaPrestamos():"
//                + milisegundos);
//        assertNotNull(prestamos);
//        log.debug("El arreglo de prestamos tiene [" + prestamos.length
//                + "] elementos");
//        assertTrue(prestamos.length > 0);
//        UtilsLog.logElementosArreglo(prestamos, true);
//        UtilsLog.logElementosArreglo(prestamos, false);

        // log.debug("Probando con el agente firmado (prestatario) y la emision
        // indicando " +
        // "como faltante");
        // params.setFaltanteSobrante(Boolean.FALSE);
        //
        // prestamos = mercadoDineroService.consultaPrestamos(params);
        // assertNotNull(prestamos);
        // log.debug("El arreglo de prestamos tiene [" + prestamos.length + "]
        // elementos");
        // assertTrue(prestamos.length>0);
        // UtilsLog.logElementosArreglo(prestamos, true);
        // UtilsLog.logElementosArreglo(prestamos, false);
        //
        log.debug("Probando con el agente firmado (prestatario), la emision indicando "
                        + "como sobrante y las fechas de vencimiento e inicio");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2006, Calendar.SEPTEMBER, 24);
        //params.setFechaVencimiento(calendar.getTime());
        calendar.set(2006, Calendar.SEPTEMBER, 21);
        //params.setFechaInicio(calendar.getTime()); // fechaInicio prela sobre
                                                    // fechaVencimiento
        startTime = System.currentTimeMillis();
        //prestamos = mercadoDineroService.consultaPrestamos(params);
        PrestamoVO[] prestamos = mercadoDineroService.consultaPrestamos(params);
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio ConsultaPrestamos():"
                + milisegundos);
        assertNotNull(prestamos);
        log.debug("El arreglo de prestamos tiene [" + prestamos.length
                + "] elementos");
        assertTrue(prestamos.length > 0);
        UtilsLog.logElementosArreglo(prestamos, true);
        UtilsLog.logElementosArreglo(prestamos, false);

        for (int i = 0; i < prestamos.length; i++) {
            if (prestamos[i].getGarantias() != null) {
                for (int j = 0; j < prestamos[i].getGarantias().length; j++) {
                    log.debug("Folio Original : " + prestamos[i].getFolioOriginal());
                    log.debug("Emision de la garantia : " + prestamos[i].getGarantias()[j].getEmision());
                }
            }
        }


//        log.debug("Probando con el agente firmado (prestatario), la emision y el folio "
//                        + "indicando como faltante, y las fechas de vencimiento e inicio");
//
//        params.setFolio("709171106");
//        startTime = System.currentTimeMillis();
//        prestamos = mercadoDineroService.consultaPrestamos(params);
//        endTime = System.currentTimeMillis();
//        milisegundos = (endTime - startTime);
//        log.debug("Milisegundos en responder el servicio:" + milisegundos);
//        assertNotNull(prestamos);
//        log.debug("El arreglo de prestamos tiene [" + prestamos.length
//                + "] elementos");
//        assertTrue(prestamos.length > 0);
//        UtilsLog.logElementosArreglo(prestamos, true);
//        UtilsLog.logElementosArreglo(prestamos, false);
        //
        // log.debug("Probando con el agente firmado (prestatario), la emision y
        // el folio" +
        // " indicando como sobrante, y las fechas de vencimiento e inicio");
        //
        // params.setFaltanteSobrante(Boolean.FALSE);
        // prestamos = mercadoDineroService.consultaPrestamos(params);
        // assertNotNull(prestamos);
        // log.debug("El arreglo de prestamos tiene [" + prestamos.length + "]
        // elementos");
        // assertTrue(prestamos.length>0);
        // UtilsLog.logElementosArreglo(prestamos, true);
        // UtilsLog.logElementosArreglo(prestamos, false);

        log.debug("Ejecucion exitosa del testEJBConsultaPrestamos()");

    }

}