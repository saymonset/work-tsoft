/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.mercadodinero.mercadodinero;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
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
public class ITestSetActualizaParametrosValpreE_1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final Log log = LogFactory
            .getLog(ITestSetActualizaParametrosValpreE_1.class);

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
     * Prueba del servicio setActualizaParametrosValpreE()
     *
     * @throws Exception
     */
    public void testSetActualizaParametrosValpreE_1() throws Exception {

        log.info("Entrando a ITestSetActualizaParametrosValpreE_1.testSetActualizaParametrosValpreE_1()");

        assertNotNull(mercadoDineroService);

        HashMap parametros = new HashMap();
        parametros.put("PRUEBA 1", "UNOO");
        parametros.put("PRUEBA 2", "DOSS");
        mercadoDineroService.setActualizaParametrosValpreE(parametros);
    }


    /**
     * Prueba del servicio setActualizaParametrosValpreE()
     *
     * @throws Exception
     */
    public void testSetActualizaParametrosValpreE_2() throws Exception {

        log.info("Entrando a ITestSetActualizaParametrosValpreE_1.testSetActualizaParametrosValpreE_2()");

        assertNotNull(mercadoDineroService);

        HashMap parametros = new HashMap();
        parametros.put("UNO", "1");
        parametros.put("DOS", "2");
        mercadoDineroService.setActualizaParametrosValpreE(parametros);
    }


}