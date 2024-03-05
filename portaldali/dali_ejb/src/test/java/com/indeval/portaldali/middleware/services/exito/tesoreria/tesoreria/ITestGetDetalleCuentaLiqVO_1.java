/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.tesoreria.tesoreria;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.tesoreria.DetalleCuentaLiqVO;
import com.indeval.portaldali.middleware.services.tesoreria.GetDetalleCuentaLiqParams;
import com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService;
import com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosService;


/**
 * Clase de Prueba para los metodos de TesoreriaService
 *
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ITestGetDetalleCuentaLiqVO_1 extends BaseITestService {

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

    /**
     * TestCase para el servicio de testEJBGetDetalleCuentaLiqVO()
     *
     * @throws BusinessException
     */
    public void testEJBGetDetalleCuentaLiqVO() throws BusinessException {

        log.debug("Entrando al metodo testEJBGetDetalleCuentaLiqVO()");
        assertNotNull(tesoreriaService);

        log.debug("Probando con el params NULL");
        GetDetalleCuentaLiqParams params = new GetDetalleCuentaLiqParams();
        long startTime = System.currentTimeMillis();
        try {
            tesoreriaService.getDetalleCuentaLiqVO(params);
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

        log.debug("Probando con el params VACIO");
        params = new GetDetalleCuentaLiqParams();
        startTime = System.currentTimeMillis();
        try {
            tesoreriaService.getDetalleCuentaLiqVO(params);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio GetDetalleAmortizaciones():"
                        + milisegundos);

        log.debug("Probando con el Agente VACIO");
        //TODO
//        params.setAgente(new com.indeval.sidv.decretos.persistence.model.vo.AgenteVO());
        startTime = System.currentTimeMillis();
        try {
            tesoreriaService.getDetalleCuentaLiqVO(params);
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

        // log.debug("Probando solo con el Agente (sin cuenta)");
        // params.setAgente(new AgenteVO("01","001"));
        // DetalleCuentaLiqVO detalleCuentaLiqVO =
        // tesoreriaService.getDetalleCuentaLiqVO(params);
        // assertNotNull(detalleCuentaLiqVO);
        // log.debug("[" +
        // ToStringBuilder.reflectionToString(detalleCuentaLiqVO)+ "]");

        // log.debug("Probando solo con el Agente");
        // params.setAgente(new AgenteVO("01","022","0081"));
        // DetalleCuentaLiqVO detalleCuentaLiqVO =
        // tesoreriaService.getDetalleCuentaLiqVO(params);
        // assertNotNull(detalleCuentaLiqVO);
        // log.debug("[" +
        // ToStringBuilder.reflectionToString(detalleCuentaLiqVO)+ "]");

        log.debug("Probando con el params correcto");
        //TODO
//        params.setAgente(new com.indeval.sidv.decretos.persistence.model.vo.AgenteVO("02", "013", "6528"));
        params.setFolioFija("164855");
        params.setFolioVariable("732996");
        startTime = System.currentTimeMillis();
        DetalleCuentaLiqVO detalleCuentaLiqVO = tesoreriaService
                .getDetalleCuentaLiqVO(params);
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio GetDetalleAmortizaciones():"
                        + milisegundos);
        assertNotNull(detalleCuentaLiqVO);
        log.debug("[" + ToStringBuilder.reflectionToString(detalleCuentaLiqVO)
                + "]");

        log.debug("Ejecucion exitosa de testEJBGetDetalleCuentaLiqVO()");

    }

}
