/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exception.tesoreria.tesoreria;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.tesoreria.GetEdoCtaLiqParams;
import com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService;
import com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosService;
import com.indeval.portaldali.persistence.util.UtilsLog;


/**
 * Clase de Prueba para los metodos de TesoreriaService
 *
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ITestGetLiquidacionDecretos_e1 extends BaseITestService {

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
     * Prueba getLiquidacionDecretos(GetEdoCtaLiqParams):List
     *
     * @throws BusinessException
     */
    public void testEJBGetLiquidacionDecretos() throws BusinessException {

        log.debug("Entrando exitosa de testEJBGetLiquidacionDecretos()");

        GetEdoCtaLiqParams params = new GetEdoCtaLiqParams();
        params.setAgente(new com.indeval.sidv.decretos.persistence.model.vo.AgenteVO());
        params.getAgente().setId("01");
        params.getAgente().setFolio("003");
        params.setEmision(new com.indeval.sidv.decretos.persistence.model.vo.EmisionVO());
        Calendar fechaIni = Calendar.getInstance();
        fechaIni.set(2007, 6, 12, 0, 0, 0);
        params.setFechaIni(fechaIni.getTime());
        Calendar fechaFin = Calendar.getInstance();
        fechaIni.set(2007, 6, 12, 23, 59, 59);
        params.setFechaFin(fechaFin.getTime());
        params.setTipoConsulta("COBROS");
        params.setTipoEjercicio("TODOS");
        params.setTipoMoneda("PE");

        //TODO
//        List resultado = tesoreriaService.getLiquidacionDecretos(params);
//        assertNotNull(resultado);
//
//        log.debug("RESULTADO CONSULTA " + params.getTipoConsulta());
//        UtilsLog.logElementosLista(resultado);
        //TODO

        log.debug("Saliendo de testEJBGetLiquidacionDecretos()");

    }

}
