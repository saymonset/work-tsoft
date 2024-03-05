/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.tesoreria.tesoreria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService;
import com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosService;


/**
 * Clase de Prueba para los metodos de TesoreriaService
 *
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ITestIsCplexActive_1 extends BaseITestService {

    private LiquidacionDecretosService liquidacionDecretosService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
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
        if(liquidacionDecretosService==null){
        	liquidacionDecretosService= (LiquidacionDecretosService) applicationContext
            .getBean("decretosEjercicioDerechos");

        }

    }

//    /**
//     * 
//     * @throws BusinessException
//     */
//    public void testIsCplexActive() throws BusinessException {
//
//        log.debug("Entrando al metodo testIsCplexActive()");
//
//        boolean activo = tesoreriaService.isCplexActive();
//        String buffer = activo ? "ACTIVO" : "INACTIVO";
//        log.debug("El CPLEX esta " + buffer);
//
//    }

}
