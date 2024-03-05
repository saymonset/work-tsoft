/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.tesoreria.tesoreria;

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
public class ITestGetTiposDerecho_1 extends BaseITestService {

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
     * Prueba getTiposDerecho(GetEdoCtaLiqParams):List
     *
     * @throws BusinessException
     */
    public void testEJBGetTiposDerecho() throws BusinessException {

        log.debug("Entrando exitosa de testEJBGetTiposDerecho()");

        GetEdoCtaLiqParams params = new GetEdoCtaLiqParams();
        params.setAgente(new com.indeval.sidv.decretos.persistence.model.vo.AgenteVO());
        params.getAgente().setId("02");
        params.getAgente().setFolio("022");

        Calendar fechaIni = Calendar.getInstance();
        fechaIni.set(2007, Calendar.NOVEMBER, 29, 0, 0, 0);
        params.setFechaIni(fechaIni.getTime());
        Calendar fechaFin = Calendar.getInstance();
        fechaIni.set(2007, Calendar.NOVEMBER, 29, 23, 59, 59);
        params.setFechaFin(fechaFin.getTime());
        params.setTipoConsulta("PAGOS");
        // params.setTipoEjercicio("TODOS");
        params.setTipoMoneda("PE");

        com.indeval.sidv.decretos.persistence.model.vo.EmisionVO emisionVO = new com.indeval.sidv.decretos.persistence.model.vo.EmisionVO();
        emisionVO.setIdTipoValor("1B");
        emisionVO.setEmisora("NAFTRAC");
        emisionVO.setSerie("*02");
        emisionVO.setCupon("0000");

        params.setEmision(emisionVO);

        List resultado = tesoreriaService.getTiposDerecho(params);
        assertNotNull(resultado);

        log.debug("RESULTADO " + params.getTipoConsulta());
        UtilsLog.logElementosLista(resultado, true);

        log.debug("Saliendo de testEJBGetTiposDerecho()");

    }

}
