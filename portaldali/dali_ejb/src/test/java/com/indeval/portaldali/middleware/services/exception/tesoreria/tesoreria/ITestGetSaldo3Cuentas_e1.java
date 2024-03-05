/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exception.tesoreria.tesoreria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService;

/**
 * Clase de prueba para los casos de excepcion del metodo 
 * getSaldo3Cuentas() de TesoreriaService
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ITestGetSaldo3Cuentas_e1 extends BaseITestService {
    
    /** Log */
    private Logger logger = LoggerFactory.getLogger(ITestGetSaldo3Cuentas_e1.class);
    
    /** Bean a probar */
    private TesoreriaService tesoreriaService;

    /**
     * @see com.indeval.portaldali.middleware.services.BaseITestService#onSetUp()
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (tesoreriaService == null) {
            tesoreriaService = (TesoreriaService) applicationContext
                    .getBean("tesoreriaService");
        }
    }

    /**
     * TestCase para el servicio de testEJBGetSaldo3Cuentas()
     * Caso: Probando con el parametro NULL.
     * @throws BusinessException
     */
    public void testEJBGetSaldo3Cuentas_e1() throws BusinessException {

        log.info("Entrando a ITestGetSaldo3Cuentas_e1.testEJBGetSaldo3Cuentas_e1()");
        assertNotNull(tesoreriaService);

        log.debug("Probando con el parametro NULL");
        AgenteVO agenteVO = null;
        long startTime = System.currentTimeMillis();
        try {
            tesoreriaService.getSaldo3Cuentas(agenteVO);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            log.debug(e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        double milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio GetSaldo3Cuentas():"
                + milisegundos);

        log.debug("Ejecucion exitosa de testEJBGetSaldo3Cuentas()");

    }
    
    /**
     * TestCase para el servicio de testEJBGetSaldo3Cuentas()
     * Caso: Probando con el parametro VACIO.
     * 
     * @throws BusinessException
     */
    public void testEJBGetSaldo3Cuentas_e2() throws BusinessException {

        log.info("Entrando a ITestGetSaldo3Cuentas_e1.testEJBGetSaldo3Cuentas_e2()");
        assertNotNull(tesoreriaService);

        log.debug("Probando con el parametro VACIO");
        AgenteVO agenteVO = new AgenteVO();
        long startTime = System.currentTimeMillis();
        try {
            tesoreriaService.getSaldo3Cuentas(agenteVO);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        long milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio GetSaldo3Cuentas():"
                + milisegundos);

        log.debug("Ejecucion exitosa de testEJBGetSaldo3Cuentas()");

    }
    
    /**
     * TestCase para el servicio de testEJBGetSaldo3Cuentas()
     * Caso: Probando con un agente que no existe.
     * 
     * @throws BusinessException
     */
    public void testEJBGetSaldo3Cuentas_e3() throws BusinessException {

        log.info("Entrando a ITestGetSaldo3Cuentas_e1.testEJBGetSaldo3Cuentas_e3()");
        assertNotNull(tesoreriaService);

        log.debug("Probando con el parametro con un agente que no existe");
        AgenteVO agenteVO = new AgenteVO("99","999");
        long startTime = System.currentTimeMillis();
        try {
            tesoreriaService.getSaldo3Cuentas(agenteVO);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        long milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio GetSaldo3Cuentas():"
                + milisegundos);

        log.debug("Ejecucion exitosa de testEJBGetSaldo3Cuentas()");

    }

}