/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.tesoreria.tesoreria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.tesoreria.DetalleAmortizacionesVO;
import com.indeval.portaldali.middleware.services.tesoreria.GetDetalleAmortizacionesParams;
import com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService;
import com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosService;
import com.indeval.portaldali.persistence.util.UtilsLog;


/**
 * Clase de Prueba para los metodos de TesoreriaService
 *
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ITestGetDetalleAmortizaciones_1 extends BaseITestService {

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

    /*
     * where id_inst= '01' and folio_inst= '037'and cuenta = '0604' and
     * folio_fija = 0 and folio_variable=17110
     */
    /**
     * TestCase para el servicio de testEJBGetDetalleAmortizaciones()
     *
     * @throws BusinessException
     */
    public void testEJBGetDetalleAmortizaciones() throws BusinessException {

        log.debug("Entrando al metodo testEJBGetDetalleAmortizaciones()");
        assertNotNull(tesoreriaService);

        log.debug("Probando con el Params NULL");
        GetDetalleAmortizacionesParams params1 = null;
        long startTime = System.currentTimeMillis();
        try {
            tesoreriaService.getDetalleAmortizaciones(params1);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            log.debug(e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        double milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio GetDetalleAmortizaciones():"
                        + milisegundos);

        log.debug("Probando con el Params VACIO");
        params1 = new GetDetalleAmortizacionesParams();
        startTime = System.currentTimeMillis();
        try {
            tesoreriaService.getDetalleAmortizaciones(params1);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio GetDetalleAmortizaciones():"
                        + milisegundos);

        log.debug("Probando solo con el Agente VACIO");
        //TODO
//        params1.setAgente(new com.indeval.sidv.decretos.persistence.model.vo.AgenteVO());
        startTime = System.currentTimeMillis();
        try {
            tesoreriaService.getDetalleAmortizaciones(params1);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio GetDetalleAmortizaciones():"
                        + milisegundos);

        log.debug("Probando solo con el Agente sin cuenta");
        //TODO
//        params1.setAgente(new com.indeval.sidv.decretos.persistence.model.vo.AgenteVO("02", "014"));
        startTime = System.currentTimeMillis();
        try {
            tesoreriaService.getDetalleAmortizaciones(params1);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio GetDetalleAmortizaciones():"
                        + milisegundos);

        log.debug("Probando solo con el Agente");
        //TODO
//        params1.setAgente(new com.indeval.sidv.decretos.persistence.model.vo.AgenteVO("02", "014", "6620"));
        startTime = System.currentTimeMillis();
        try {
            tesoreriaService.getDetalleAmortizaciones(params1);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio GetDetalleAmortizaciones():"
                        + milisegundos);

        log.debug("Probando con el Agente y el folio fija");
        params1.setFolioFija("139131");
        startTime = System.currentTimeMillis();
        try {
            tesoreriaService.getDetalleAmortizaciones(params1);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio GetDetalleAmortizaciones():"
                        + milisegundos);

        log.debug("Probando con todos los parametros");
        params1.setFolioVariable("0");

        startTime = System.currentTimeMillis();
        DetalleAmortizacionesVO[] detalles = tesoreriaService
                .getDetalleAmortizaciones(params1);
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio GetDetalleAmortizaciones():"
                        + milisegundos);
        assertNotNull(detalles);
        assertTrue(detalles.length > 0);
        log.debug("El arreglo de detalles tiene [" + detalles.length
                + "] elementos");
        UtilsLog.logElementosArreglo(detalles, true);
        UtilsLog.logElementosArreglo(detalles, false);

        log.debug("Ejecucion exitosa de testEJBGetDetalleAmortizaciones()");

    }

}
