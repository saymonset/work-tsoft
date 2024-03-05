/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exception.mercadodinero.mercadodinero;

import java.util.Date;

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
public class ITestAgregarDiasHabiles_e1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final LLogger logger = LoggerFactory.getLogger(TestAgregarDiasHabiles_e1.class);

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
     * TestCase para MercadoDineroDao.agregarDiasHabiles()
     *
     * @throws BusinessException
     */
    public void testAgregarDiasHabiles() throws BusinessException {

        log.info("Entrando a ITestAgregarDiasHabiles_1.testAgregarDiasHabiles()");

        assertNotNull(mercadoDineroService);

        long startTime = System.currentTimeMillis();
        Date fecha = mercadoDineroService.agregarDiasHabiles(new Date(), 5);
        long endTime = System.currentTimeMillis();
        double milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio AgregarDiasHabiles():"
                + milisegundos);
        assertNotNull(fecha);
        log.debug("Fecha: [" + fecha + "]");

        log.debug("Ejecucion exitosa del testEJBAgregarDiasHabiles()");

    }

}