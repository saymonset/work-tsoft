/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.mercadodinero.mercadodinero;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
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
public class ITestConfirmaOperacion_1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final Logger logger = LoggerFactory.getLogger(ITestConfirmaOperacion_1.class);

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
     * TestCase para testEJBConfirmaOperacion() Prueba para confirmar
     * operaciones
     *
     * @throws BusinessException
     */
    public void testConfirmaOperacion() throws BusinessException {
        
        log.info("Entrando a ITestConfirmaOperacion_1.testConfirmaOperacion()");

        assertNotNull(mercadoDineroService);

        AgenteVO agente = new AgenteVO();
        agente.setId("02");
        agente.setFolio("013");
        agente.setCuenta("6502");
        String folio = "070216135930";
        Boolean esAgenteFirmado = Boolean.TRUE;

        BigInteger[] resultado = mercadoDineroService.confirmaOperacion(agente,
                folio, esAgenteFirmado);

        assertNotNull(resultado);

        for (int i = 0; i < resultado.length; i++) {
            log.debug("Folio[" + i + "]: " + resultado[i]);
        }

        log.debug("Ejecucion exitosa del testEJBConfirmaOperacion()");

    }

}