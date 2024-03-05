/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.mercadodinero.mercadodinero;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
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
public class ITestConsultarDiasInhabilesByMonth_1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final LLogger logger = LoggerFactory.getLogger(TestConsultarDiasInhabilesByMonth_1.class);

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
     * TestCase para MercadoDineroDao.consultarDiasInhabilesByMonth()()
     *
     * @throws BusinessException
     */
    public void testConsultarDiasInhabilesByMonth() throws BusinessException {
        
        log.info("Entrando a ITestConsultarDiasInhabilesByMonth_1.testConsultarDiasInhabilesByMonth()");

        assertNotNull(mercadoDineroService);
        
        long startTime = System.currentTimeMillis();
        List listaDiasInhabilesByMonth = mercadoDineroService
                .consultarDiasInhabilesByMonth(11, 2006);
        long endTime = System.currentTimeMillis();
        double milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio ConsultarDiasInhabilesByMonth():"
                        + milisegundos);
        assertNotNull(listaDiasInhabilesByMonth);
        log.debug("Hay [" + listaDiasInhabilesByMonth.size()
                + "] dias inhabiles en el mes");
        UtilsLog.logElementosLista(listaDiasInhabilesByMonth, true);

        log
                .debug("Ejecucion exitosa del testEJBConsultarDiasInhabilesByMonth()");

    }

}