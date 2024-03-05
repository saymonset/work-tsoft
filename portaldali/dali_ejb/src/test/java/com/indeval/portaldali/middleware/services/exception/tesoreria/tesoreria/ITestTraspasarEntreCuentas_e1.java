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
public class ITestTraspasarEntreCuentas_e1 extends BaseITestService {

    /** Bean para LiquidacionDecretosService */
	private LiquidacionDecretosService liquidacionDecretosService;
	/** Log de bitacora */
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /** Bean para TesoreriaService */
    private TesoreriaService tesoreriaService;
    
    
    /**
     * @throws Exception
     */
    protected void onSetUp() throws Exception {

        super.onSetUp();

        if (tesoreriaService == null) {
            tesoreriaService = (TesoreriaService) applicationContext
                    .getBean("tesoreriaService");
        }
        if(liquidacionDecretosService == null){
        	liquidacionDecretosService = (LiquidacionDecretosService) applicationContext.getBean(
                    "decretosEjercicioDerechos");

        }

    }

    /**
     * TestCase para tesoreriaService.traspasarEntreCuentas() TODO - Depende de
     * precondiciones TestCase para el servicio de testEJBTraspasarEntreCuentas()
     *
     * @throws BusinessException
     */
    public void testEJBTraspasarEntreCuentas() throws BusinessException {

        log.debug("Entrando al metodo testEJBTraspasarEntreCuentas()");
        assertNotNull(tesoreriaService);

        Integer folioOperacion = null;

        log.debug("Probando con todos los parametros NULL");

        long startTime = System.currentTimeMillis();

        try {

            tesoreriaService.traspasarEntreCuentas(null, null, null);
            log.debug("Checar validacion");
            assertTrue(false);

        } catch (BusinessException e) {

            log.debug(e.getMessage());

        }

        long endTime = System.currentTimeMillis();
        double milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio TraspasarEntreCuentas():" + milisegundos);

        log.debug("Probando solo con el traspasante VACIO");
        startTime = System.currentTimeMillis();

        try {

            tesoreriaService.traspasarEntreCuentas(new AgenteVO(), null, null);
            log.debug("Checar validacion");
            assertTrue(false);

        } catch (BusinessException e) {

            log.debug(e.getMessage());

        }

        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio TraspasarEntreCuentas():" + milisegundos);

        log.debug("Probando solo con el traspasante");
        startTime = System.currentTimeMillis();

        try {

            tesoreriaService.traspasarEntreCuentas(new AgenteVO("01", "001", "0109"), null, null);
            log.debug("Checar validacion");
            assertTrue(false);

        } catch (BusinessException e) {

            log.debug(e.getMessage());

        }

        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio TraspasarEntreCuentas():" + milisegundos);

        log.debug("Probando con el traspasante NULL y el receptor NULL");
        startTime = System.currentTimeMillis();

        try {

            tesoreriaService.traspasarEntreCuentas(null, null, new BigDecimal(1000.0));
            log.debug("Checar validacion");
            assertTrue(false);

        } catch (BusinessException e) {

            log.debug(e.getMessage());

        }

        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio TraspasarEntreCuentas():" + milisegundos);

        log.debug("Probando con el traspasante NULL");
        startTime = System.currentTimeMillis();

        try {

            tesoreriaService.traspasarEntreCuentas(null, new AgenteVO("01", "001", "4500"),
                                                   new BigDecimal(1000.0));
            log.debug("Checar validacion");
            assertTrue(false);

        } catch (BusinessException e) {

            log.debug(e.getMessage());

        }

        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio TraspasarEntreCuentas():" + milisegundos);

        log.debug("Probando con el receptor NULL");
        startTime = System.currentTimeMillis();

        try {

            tesoreriaService.traspasarEntreCuentas(new AgenteVO("01", "001", "0109"), null,
                                                   new BigDecimal(1000.0));
            log.debug("Checar validacion");
            assertTrue(false);

        } catch (BusinessException e) {

            log.debug(e.getMessage());

        }

        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio TraspasarEntreCuentas():" + milisegundos);

        log.debug("Probando con el receptor VACIO");
        startTime = System.currentTimeMillis();

        try {

            tesoreriaService.traspasarEntreCuentas(new AgenteVO("01", "001", "0109"),
                                                   new AgenteVO(), new BigDecimal(1000.0));
            log.debug("Checar validacion");
            assertTrue(false);

        } catch (BusinessException e) {

            log.debug(e.getMessage());

        }

        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio TraspasarEntreCuentas():" + milisegundos);

        log.debug("Probando con el importe NULL");
        startTime = System.currentTimeMillis();

        try {

            tesoreriaService.traspasarEntreCuentas(new AgenteVO("01", "001", "0109"),
                                                   new AgenteVO("01", "001", "4500"), null);
            log.debug("Checar validacion");
            assertTrue(false);

        } catch (BusinessException e) {

            log.debug(e.getMessage());

        }

        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio TraspasarEntreCuentas():" + milisegundos);

        log.debug("Probando con todos los parametros");
        folioOperacion = tesoreriaService.traspasarEntreCuentas(new AgenteVO("01", "001", "0109"),
                                                                new AgenteVO("01", "001", "4500"),
                                                                new BigDecimal(1000.0));
        assertNotNull(folioOperacion);
        log.debug("El folio de la operacion es : [" + folioOperacion + "]");

        log.debug("Ejecucion exitosa de testEJBTraspasarEntreCuentas()");

    }

}
