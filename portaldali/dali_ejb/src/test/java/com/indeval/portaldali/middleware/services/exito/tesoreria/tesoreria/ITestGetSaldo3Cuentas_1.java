/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.tesoreria.tesoreria;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.tesoreria.Saldo3CuentasVO;
import com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService;

/**
 * Clase de prueba para los casos de exito del metodo 
 * getSaldo3Cuentas() de TesoreriaService
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ITestGetSaldo3Cuentas_1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private Logger logger = LoggerFactory.getLogger(ITestGetSaldo3Cuentas_1.class);

    /** Inyecci&oacute;n del bean tesoreriaService */
    private TesoreriaService tesoreriaService;

    /**
     * 
     * @see com.indeval.portaldali.middleware.services.BaseITestService#onSetUp()
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (tesoreriaService == null) {
            tesoreriaService = (TesoreriaService) applicationContext.getBean("tesoreriaService");
        }
    }

    /**
     * TestCase para el servicio de testEJBGetSaldo3Cuentas()
     * Caso: Probando con una institucion que si esta en catalogo..empresascat
     * 
     * @throws BusinessException
     */
    public void testEJBGetSaldo3Cuentas_1() throws BusinessException {

        log.info("Entrando a ITestGetSaldo3Cuentas.testEJBGetSaldo3Cuentas_1()");
        assertNotNull(tesoreriaService);

        log.debug("Probando con una institucion que si esta en catalogo..empresascat");
        AgenteVO agenteVO = new AgenteVO("01","003");

        long startTime = System.currentTimeMillis();
        Saldo3CuentasVO saldo3CuentasVO = tesoreriaService.getSaldo3Cuentas(agenteVO);
        long endTime = System.currentTimeMillis();
        long milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio GetSaldo3Cuentas():"
                + milisegundos);
        log.debug("[" + ToStringBuilder.reflectionToString(saldo3CuentasVO)
                + "]");

        log.debug("Ejecucion exitosa de testEJBGetSaldo3Cuentas()");

    }
    
    /**
     * TestCase para el servicio de testEJBGetSaldo3Cuentas()
     * Caso: Probando con una institucion que no esta en catalogo..empresascat
     * 
     * @throws BusinessException
     */
    public void testEJBGetSaldo3Cuentas_2() throws BusinessException {

        log.info("Entrando a ITestGetSaldo3Cuentas.testEJBGetSaldo3Cuentas_2()");
        assertNotNull(tesoreriaService);

        log.debug("Probando con una institucion que no esta en catalogo..empresascat");
        AgenteVO agenteVO = new AgenteVO("02","017");
        long startTime = System.currentTimeMillis();
            tesoreriaService.getSaldo3Cuentas(agenteVO);
        long endTime = System.currentTimeMillis();
        long milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio GetSaldo3Cuentas():"
                + milisegundos);

        log.debug("Ejecucion exitosa de testEJBGetSaldo3Cuentas()");

    }

}