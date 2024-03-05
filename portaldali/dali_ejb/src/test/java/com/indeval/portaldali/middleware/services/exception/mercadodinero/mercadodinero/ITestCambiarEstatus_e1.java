/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exception.mercadodinero.mercadodinero;

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
public class ITestCambiarEstatus_e1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final LLogger logger = LoggerFactory.getLogger(TestCambiarEstatus_e1.class);

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
     * TestCase para mercadoDineroService.cambiarEstatus()
     * TODO - Dependiente de condiciones, dependiente de la fecha_hora, fecha_liquidacion y status TestCase para el servicio de cambiarEstatus()
     *
     * @throws BusinessException
     */
    public void testCambiarEstatus() throws BusinessException {

        log.info("Entrando a ITestCambiarEstatus_1.testCambiarEstatus()");
        
        assertNotNull(mercadoDineroService);

        // log.debug("Probando con todos los parametros NULL");
        // try {
        // mercadoDineroService.cambiarEstatus(null, null, null);
        // }
        // catch (IllegalArgumentException e) {
        // log.debug(e.getMessage());
        // }
        //
        // log.debug("Probando solo con el Agente VACIO");
        // try {
        // mercadoDineroService.cambiarEstatus(new AgenteVO(), null, null);
        // }
        // catch (BusinessException e) {
        // log.debug(e.getMessage());
        // }
        //
        // log.debug("Probando solo con el Agente (sin cuenta)");
        // try {
        // mercadoDineroService.cambiarEstatus(new AgenteVO("01","003"), null,
        // null);
        // }
        // catch (BusinessException e) {
        // log.debug(e.getMessage());
        // }
        //
        // BigInteger[] folioControl = {new BigInteger("35"), new
        // BigInteger("39")};
        //
        // log.debug("Probando con la llaveFolio NULL");
        // BigInteger[] resultado = mercadoDineroService.cambiarEstatus(
        // new AgenteVO("01", "001", "0109"), null, folioControl);
        //
        // assertNotNull(resultado);
        // log.debug("El arreglo de folios de operacion tiene [" +
        // resultado.length + "] elementos");
        // assertTrue(resultado.length>0);
        // UtilsLog.logElementosArreglo(resultado, true);
        // UtilsLog.logElementosArreglo(resultado, false);

        // log.debug("Probando con la llaveFolio VACIA");
        // resultado = mercadoDineroService.cambiarEstatus(
        // new AgenteVO("01", "003", "0307"), "", folioControl);
        //
        // assertNotNull(resultado);
        // log.debug("El arreglo de folios de operacion tiene [" +
        // resultado.length + "] elementos");
        // assertTrue(resultado.length>0);
        // UtilsLog.logElementosArreglo(resultado, true);
        // UtilsLog.logElementosArreglo(resultado, false);

        // log.debug("Probando con la llaveFolio");
        // resultado = mercadoDineroService.cambiarEstatus(
        // new AgenteVO("02", "014", "6622"), "", null);
        //
        // assertNotNull(resultado);
        // log.debug("El arreglo de folios de operacion tiene [" +
        // resultado.length + "] elementos");
        // assertTrue(resultado.length>0);
        // UtilsLog.logElementosArreglo(resultado, true);
        // UtilsLog.logElementosArreglo(resultado, false);

        log.debug("Ejecucion exitosa del testEJBcambiarEstatus()");

    }

}