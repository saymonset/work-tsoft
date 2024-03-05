/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.mercadodinero.mercadodinero;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.mercadodinero.ParcialidadVO;
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
public class ITestDetalleParcialidades_1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final LLogger logger = LoggerFactory.getLogger(TestDetalleParcialidades_1.class);

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
     * TestCase para el servicio de detalleParcialidades()
     * TODO - Esta prueba es dependiente de que existan registros de la fecha del dia
     *
     * @throws BusinessException
     */
    public void testDetalleParcialidades() throws BusinessException {
        
        log.info("Entrando a ITestDetalleParcialidades_1.testDetalleParcialidades()");

        assertNotNull(mercadoDineroService);

        log.debug("Probando con el folio NULL");
        long startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.detalleParcialidades(null);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        double milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio DetalleParcialidades():"
                        + milisegundos);

        log.debug("Probando con el folio VACIO");
        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.detalleParcialidades("");
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio DetalleParcialidades():"
                        + milisegundos);

        log.debug("Probando con el folio incorrecto");
        startTime = System.currentTimeMillis();
        try {
            ParcialidadVO parcialidadVO = mercadoDineroService
                    .detalleParcialidades("xxxxx");
            log.debug("[" + ToStringBuilder.reflectionToString(parcialidadVO)
                    + "]");
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio DetalleParcialidades():"
                        + milisegundos);

        log.debug("Probando con el folio correcto");
        startTime = System.currentTimeMillis();
        ParcialidadVO parcialidadVO = mercadoDineroService
                .detalleParcialidades("15025");
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio DetalleParcialidades():"
                        + milisegundos);
        assertNotNull(parcialidadVO);
        log
                .debug("[" + ToStringBuilder.reflectionToString(parcialidadVO)
                        + "]");

        log.debug("Ejecucion exitosa del testEJBDetalleParcialidades()");

    }

}