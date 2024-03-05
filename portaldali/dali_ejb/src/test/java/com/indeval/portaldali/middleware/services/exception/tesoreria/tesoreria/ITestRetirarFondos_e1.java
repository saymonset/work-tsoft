/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exception.tesoreria.tesoreria;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService;
import com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosService;


/**
 * Clase de Prueba para los metodos de TesoreriaService
 *
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ITestRetirarFondos_e1 extends BaseITestService {

    /** Bean para LiquidacionDecretosService */
	private LiquidacionDecretosService liquidacionDecretosService;
	/** Log de bitacora */
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /** Bean para TesoreriaService */
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

    /**
     * TestCase para tesoreriaService.retirarFondos() TODO - Depende de precondiciones,
     * depende de la fase TestCase para el servicio de testEJBRetirarFondos()
     *
     * @throws BusinessException
     */
    public void testEJBRetirarFondos() throws BusinessException {

        log.debug("Entrando al metodo testEJBRetirarFondos()");
        assertNotNull(tesoreriaService);

        log.debug("Probando con todos los parametros NULL");

        AgenteVO agenteVO = null;
        long startTime = System.currentTimeMillis();

        try {

            tesoreriaService.retirarFondos(null, null, null);
            log.debug("Checar validacion");
            assertTrue(false);

        } catch (BusinessException e) {

            log.debug(e.getMessage());

        }

        long endTime = System.currentTimeMillis();
        double milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio RetirarFondos():" + milisegundos);

        log.debug("Probando solo con el Agente VACIO");
        startTime = System.currentTimeMillis();

        try {

            tesoreriaService.retirarFondos(new AgenteVO(), null, null);
            log.debug("Checar validacion");
            assertTrue(false);

        } catch (BusinessException e) {

            log.debug(e.getMessage());

        }

        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio RetirarFondos():" + milisegundos);

        log.debug("Probando solo con el Agente con ID y FOLIO");
        startTime = System.currentTimeMillis();

        try {

            tesoreriaService.retirarFondos(new AgenteVO("01", "003"), null, null);
            log.debug("Checar validacion");
            assertTrue(false);

        } catch (BusinessException e) {

            log.debug(e.getMessage());

        }

        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio RetirarFondos():" + milisegundos);

        log.debug("Probando con todos los datos");

        String tipoRetiro = "21"; // Retiros SIAC
        BigDecimal importe = new BigDecimal("10000");
        Integer folioRetiro;
        agenteVO = new AgenteVO("12", "001");
        agenteVO.setNombreCorto("TESOR");

        startTime = System.currentTimeMillis();
        folioRetiro = tesoreriaService.retirarFondos(agenteVO, tipoRetiro, importe);
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio RetirarFondos():" + milisegundos);
        assertNotNull(folioRetiro);
        log.debug("folioRetiro = [" + folioRetiro + "]");

        log.debug("Ejecucion exitosa de testEJBRetirarFondos()");

    }

}
