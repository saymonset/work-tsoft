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
public class ITestGetEstadoCuentaLiq_1 extends BaseITestService {

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
//     * TestCase para el servicio de testEJBGetEstadoCuentaLiq()
//     *
//     * @throws BusinessException
//     */
//    public void testEJBGetEstadoCuentaLiq() throws BusinessException {
//
//        log.debug("Entrando al metodo testEJBGetEstadoCuentaLiq()");
//        assertNotNull(tesoreriaService);
//
//        log.debug("Probando con el params NULL");
//        GetEdoCtaLiqParams params = null;
//
//        long startTime = System.currentTimeMillis();
//        try {
//            tesoreriaService.getEstadoCuentaLiq(params);
//            log.debug("Checar validacion");
//            assertTrue(false);
//        } catch (IllegalArgumentException e) {
//            log.debug(e.getMessage());
//        }
//        long endTime = System.currentTimeMillis();
//        double milisegundos = (endTime - startTime);
//        log.debug("Milisegundos en responder el servicio GetEstadoCuentaLiq():"
//                + milisegundos);
//
//        log.debug("Probando con el params VACIO");
//        params = new GetEdoCtaLiqParams();
//        startTime = System.currentTimeMillis();
//        try {
//            tesoreriaService.getEstadoCuentaLiq(params);
//            log.debug("Checar validacion");
//            assertTrue(false);
//        } catch (IllegalArgumentException e) {
//            log.debug(e.getMessage());
//        }
//        endTime = System.currentTimeMillis();
//        milisegundos = (endTime - startTime);
//        log.debug("Milisegundos en responder el servicio GetEstadoCuentaLiq():"
//                + milisegundos);
//
//        log.debug("Probando solo con el Agente VACIO");
//        com.indeval.sidv.decretos.persistence.model.vo.AgenteVO agenteVO = new com.indeval.sidv.decretos.persistence.model.vo.AgenteVO();
//        params.setAgente(agenteVO);
//
//        startTime = System.currentTimeMillis();
//        try {
//            tesoreriaService.getEstadoCuentaLiq(params);
//            log.debug("Checar validacion");
//            assertTrue(false);
//        } catch (BusinessException e) {
//            log.debug(e.getMessage());
//        }
//        endTime = System.currentTimeMillis();
//        milisegundos = (endTime - startTime);
//        log.debug("Milisegundos en responder el servicio GetEstadoCuentaLiq():"
//                + milisegundos);
//
//        log.debug("Probando solo con el Agente");
//        agenteVO = new com.indeval.sidv.decretos.persistence.model.vo.AgenteVO("01", "001");
//        params.setAgente(agenteVO);
//
//        startTime = System.currentTimeMillis();
//        try {
//            tesoreriaService.getEstadoCuentaLiq(params);
//            log.debug("Checar validacion");
//            assertTrue(false);
//        } catch (BusinessException e) {
//            log.debug(e.getMessage());
//        }
//        endTime = System.currentTimeMillis();
//        milisegundos = (endTime - startTime);
//        log.debug("Milisegundos en responder el servicio GetEstadoCuentaLiq():"
//                + milisegundos);
//        log
//                .debug("Probando con el Agente y las fechas inicio y fin, sin el tipo de moneda");
//
//        Calendar calendarDesde = Calendar.getInstance();
//        calendarDesde.set(2006, Calendar.DECEMBER, 12);
//        params.setFechaIni(calendarDesde.getTime());
//
//        Calendar calendarHasta = Calendar.getInstance();
//        calendarHasta.set(2007, Calendar.JANUARY, 2);
//        params.setFechaFin(calendarHasta.getTime());
//
//        startTime = System.currentTimeMillis();
//        try {
//            tesoreriaService.getEstadoCuentaLiq(params);
//            log.debug("Checar validacion");
//            assertTrue(false);
//        } catch (BusinessException e) {
//            log.debug(e.getMessage());
//        }
//        endTime = System.currentTimeMillis();
//        milisegundos = (endTime - startTime);
//        log.debug("Milisegundos en responder el servicio GetEstadoCuentaLiq():"
//                + milisegundos);
//
//        log
//                .debug("Probando con el Agente y las fechas inicio y fin, y el tipo de moneda");
//        params.setTipoMoneda(TesoreriaService.PESOS);
//
//        startTime = System.currentTimeMillis();
//        try {
//            tesoreriaService.getEstadoCuentaLiq(params);
//            log.debug("Checar validacion");
//            assertTrue(false);
//        } catch (BusinessException e) {
//            log.debug(e.getMessage());
//        }
//        endTime = System.currentTimeMillis();
//        milisegundos = (endTime - startTime);
//        log.debug("Milisegundos en responder el servicio GetEstadoCuentaLiq():"
//                + milisegundos);
//
//        log
//                .debug("Probando con el Agente, las fechas, el tipo de moneda y el tipo de consulta=COBROS");
//        params.setTipoConsulta(TesoreriaService.COBROS);
//
//        startTime = System.currentTimeMillis();
//        CuentaLiquidacionVO cuentaLiq = tesoreriaService
//                .getEstadoCuentaLiq(params);
//        endTime = System.currentTimeMillis();
//        milisegundos = (endTime - startTime);
//        log.debug("Milisegundos en responder el servicio GetEstadoCuentaLiq():"
//                + milisegundos);
//
//        assertNotNull(cuentaLiq);
//        // log.debug("[" + ToStringBuilder.reflectionToString(cuentaLiq)+ "]");
//        assertNotNull(cuentaLiq.getSubCuentaLiq());
//        log.debug("Hay  [" + cuentaLiq.getSubCuentaLiq().length
//                + "] registros de COBROS");
//        assertTrue(cuentaLiq.getSubCuentaLiq().length > 0);
//        for (int i = 0; i < cuentaLiq.getSubCuentaLiq().length; i++) {
//            if (i < 5) {
//                RegCuentaLiqVO[] regCuentaLiqVO = cuentaLiq.getSubCuentaLiq()[i]
//                        .getRegCuentaLiq();
//                UtilsLog.logElementosArreglo(regCuentaLiqVO, true);
//                UtilsLog.logElementosArreglo(regCuentaLiqVO, false);
//            }
//        }
//
//        log
//                .debug("Probando con el Agente, las fechas, el tipo de moneda y el tipo de consulta=PAGOS");
//        params.setTipoConsulta(TesoreriaService.PAGOS);
//
//        startTime = System.currentTimeMillis();
//        cuentaLiq = tesoreriaService.getEstadoCuentaLiq(params);
//        endTime = System.currentTimeMillis();
//        milisegundos = (endTime - startTime);
//        log.debug("Milisegundos en responder el servicio GetEstadoCuentaLiq():"
//                + milisegundos);
//        assertNotNull(cuentaLiq);
//        // log.debug("[" + ToStringBuilder.reflectionToString(cuentaLiq)+ "]");
//        assertNotNull(cuentaLiq.getSubCuentaLiq());
//        log.debug("Hay  [" + cuentaLiq.getSubCuentaLiq().length
//                + "] registros de PAGOS");
//        assertTrue(cuentaLiq.getSubCuentaLiq().length > 0);
//        for (int i = 0; i < cuentaLiq.getSubCuentaLiq().length; i++) {
//            if (i < 5) {
//                RegCuentaLiqVO[] regCuentaLiqVO = cuentaLiq.getSubCuentaLiq()[i]
//                        .getRegCuentaLiq();
//                UtilsLog.logElementosArreglo(regCuentaLiqVO, true);
//                UtilsLog.logElementosArreglo(regCuentaLiqVO, false);
//            }
//        }
//
//        // log.debug("Probando con todos los parametros");
//        // EmisionVO emisionVO = new EmisionVO();
//        // emisionVO.setCupon("0020");
//        // emisionVO.setEmisora("METROCB");
//        // emisionVO.setIdTipoValor("91");
//        // emisionVO.setSerie("04U");
//        // params.setEmision(emisionVO);
//
//        log.debug("Ejecucion exitosa de testEJBGetEstadoCuentaLiq()");
//    }

}
