/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.mercadodinero.mercadodinero;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadodinero.AdministracionGarantiaVO;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
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
public class ITestAdministracionGarantias_1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final LLogger logger = LoggerFactory.getLogger(TestAdministracionGarantias_1.class);

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
     * TestCase para el servicio de administracionGarantias()
     *
     * @throws Exception
     */
    public void testAdministracionGarantias() throws Exception {

        log.info("Entrando a ITestAdministracionGarantias_1.testAdministracionGarantias()");
        
        assertNotNull(mercadoDineroService);

        // Parametros obligatorios
        String idInstitucion = "02";
        String folioInstitucion = "013";

        log.debug("Prueba 1 - Sin el folio prestamo (folioPrestamo=null)");
        BigInteger folioPrestamo = null;
        AdministracionGarantiaVO[] vo1 = mercadoDineroService
                .administracionGarantias(folioPrestamo, idInstitucion,
                        folioInstitucion);
        assertNotNull(vo1);
        log.debug("El arreglo de AdministracionGarantiaVO tiene [" + vo1.length
                + "] elementos");
        assertTrue(vo1.length > 0);
        UtilsLog.logElementosArreglo(vo1, true);
        UtilsLog.logElementosArreglo(vo1, false);

        log.debug("Prueba 2 - Con valor invalido del folio prestamo");
        folioPrestamo = new BigInteger("4171000");
        long startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.administracionGarantias(folioPrestamo,
                    idInstitucion, folioInstitucion);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        double milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio AdministracionGarantias():"
                        + milisegundos);

        log.debug("Prueba 3 - Con valor valido del folio prestamo");
        folioPrestamo = new BigInteger("410111206");
        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.administracionGarantias(folioPrestamo,
                    idInstitucion, folioInstitucion);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio AdministracionGarantias():"
                        + milisegundos);

        log
                .debug("Prueba 4 - Con valor valido del folio en prestamo y en garantia");
        folioPrestamo = new BigInteger("410111206");
        startTime = System.currentTimeMillis();
        AdministracionGarantiaVO[] vo4 = mercadoDineroService
                .administracionGarantias(folioPrestamo, idInstitucion,
                        folioInstitucion);
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio AdministracionGarantias():"
                        + milisegundos);
        assertNotNull(vo4);
        log.debug("El arreglo de AdministracionGarantiaVO tiene [" + vo4.length
                + "] elementos");
        assertTrue(vo4.length > 0);
        for (int i = 0; i < vo4.length; i++) {
            assertTrue((vo4[i].getFolioPrestamo().compareTo(folioPrestamo) == 0));
        }
        UtilsLog.logElementosArreglo(vo4, true);
        UtilsLog.logElementosArreglo(vo4, false);

        log.debug("Ejecucion exitosa del testEJBAdministracionGarantias()");

    }

}