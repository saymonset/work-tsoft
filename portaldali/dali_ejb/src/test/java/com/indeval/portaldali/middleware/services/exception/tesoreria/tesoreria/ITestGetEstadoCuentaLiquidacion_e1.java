/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exception.tesoreria.tesoreria;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.tesoreria.CuentaLiquidacionVO;
import com.indeval.portaldali.middleware.services.tesoreria.GetEdoCtaLiqParams;
import com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService;
import com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosService;
import com.indeval.portaldali.persistence.util.UtilsLog;


/**
 * Clase de Prueba para los metodos de TesoreriaService
 *
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ITestGetEstadoCuentaLiquidacion_e1 extends BaseITestService {

    /** Bean para LiquidacionDecretosService */
	private LiquidacionDecretosService liquidacionDecretosService;
	/** Log de bitacora */
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /** Bean para TesoreriaService*/
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
     * TestCase para el servicio de testEJBGetEstadoCuentaLiq()
     *
     * @throws BusinessException
     */
    public void testEJBGetEstadoCuentaLiquidacion() throws BusinessException {

        log.debug("Entrando exitosa de testEJBGetEstadoCuentaLiq()");

        GetEdoCtaLiqParams params = new GetEdoCtaLiqParams();
        com.indeval.sidv.decretos.persistence.model.vo.AgenteVO agente = new com.indeval.sidv.decretos.persistence.model.vo.AgenteVO();
        agente.setId("01");
        agente.setFolio("003");
        params.setAgente(agente);

        //params.getAgente().setId("02");
        //params.getAgente().setFolio("033");
        com.indeval.sidv.decretos.persistence.model.vo.AgenteVO agente2 = new com.indeval.sidv.decretos.persistence.model.vo.AgenteVO();
        agente2.setId("02");
        agente2.setFolio("033");

        params.setInstitucionAutorizada(agente2);
        params.setEmision(new com.indeval.sidv.decretos.persistence.model.vo.EmisionVO());
        Calendar fechaIni = Calendar.getInstance();
        fechaIni.set(2007, 6, 9, 0, 0, 0);
        params.setFechaIni(fechaIni.getTime());
        Calendar fechaFin = Calendar.getInstance();
        fechaIni.set(2007, 6, 10, 23, 59, 59);
        params.setFechaFin(fechaFin.getTime());
        params.setTipoConsulta("PAGOS");
        params.setTipoEjercicio("TODOS");
        params.setTipoMoneda("PE");
        params.getEmision().setIdTipoValor("1");

        CuentaLiquidacionVO[] cuentaLiquidacionVO = tesoreriaService
                .getEstadoCuentaLiquidacion(params);
        assertNotNull(cuentaLiquidacionVO);

        log.debug("RESULTADO");
        UtilsLog.logElementosArreglo(cuentaLiquidacionVO, true);

        for (int i = 0; i < cuentaLiquidacionVO.length; i++) {
            UtilsLog.logElementosArreglo(cuentaLiquidacionVO[i]
                    .getSubCuentaLiq(), true);

            for (int j = 0; j < cuentaLiquidacionVO[i].getSubCuentaLiq().length; j++) {
                UtilsLog.logElementosArreglo(cuentaLiquidacionVO[i]
                        .getSubCuentaLiq()[j].getRegCuentaLiq(), true);

            }

        }

        log.debug("Saliendo de testEJBGetEstadoCuentaLiq()");

    }

}
