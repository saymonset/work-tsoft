/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exception.mercadodinero.mercadodinero;

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
public class ITestProrroga_e1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final LLogger logger = LoggerFactory.getLogger(TestProrroga_e1.class);

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

    /*
     * Para obtener valores de prueba de exito...
     *
     * select folio_prestamo from bdvalpre..prestamo where (id_prestatario =
     * '02' and fol_prestatario = '013' and status not in (1,3,6,7,8,9)) order
     * by folio_prestamo
     */
    /**
     * TestCase para el servicio de prorroga()
     *
     * @throws Exception
     */
    public void testProrroga() throws Exception {

        log.info("Entrando a ITestProrroga_1.testProrroga()");
        
        assertNotNull(mercadoDineroService);

        log.debug("Prueba con parametro nulo...");

        log.debug("Construyendo condiciones de prueba...");
        getHibernateTemplate()
                .bulkUpdate(
                        "update Prestamo set status=2, aplicacion='VALUAC_N' "
                                + "where idInstitucion = '02' and folioInstitucion = '013' and folioPrestamo in "
                                + "(321231106, 322231106, 323231106, 324231106,325231106, 326231106, 327231106, "
                                + "328231106, 329231106, 345231106, 346231106, 347231106, 409111206, 325231106)");
        // getHibernateTemplate().bulkUpdate("delete from vptran where
        // folio_prestamo in " +
        // "(321231106, 322231106, 323231106, 324231106,325231106, 326231106,
        // 327231106, " +
        // "328231106, 329231106, 345231106, 346231106, 347231106, 409111206,
        // 325231106)");

        long startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.prorroga(null);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        double milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio Prorroga():"
                + milisegundos);

        log.debug("Prueba con parametro valido...");
        String[] folio_prestamo = { "321231106", "322231106", "323231106",
                "324231106", "325231106", "326231106", "327231106",
                "328231106", "329231106", "345231106", "346231106",
                "347231106", "409111206" };

        int exitos = 0;
        for (int i = 0; i < folio_prestamo.length; i++) {
            startTime = System.currentTimeMillis();
            try {

                BigDecimal folPrestamoProrrogado = mercadoDineroService
                        .prorroga(new BigDecimal(folio_prestamo[i]));
                log.debug("El folio prestamo [" + folio_prestamo[i]
                        + "] es un caso de exito");
                log.debug("El folio prestamo prorrogado es : ["
                        + folPrestamoProrrogado + "]");
                exitos++;
            } catch (BusinessException e) {
                log.debug("El folio [" + folio_prestamo[i]
                        + "] arrojo el error...");
                log.debug(e.getMessage());
            }
            endTime = System.currentTimeMillis();
            milisegundos = (endTime - startTime);
            log.debug("Milisegundos en responder el servicio Prorroga():"
                    + milisegundos);
        }
        assertTrue(exitos > 0);

        log.debug("Ejecucion exitosa del testEJBProrroga()");

    }

}