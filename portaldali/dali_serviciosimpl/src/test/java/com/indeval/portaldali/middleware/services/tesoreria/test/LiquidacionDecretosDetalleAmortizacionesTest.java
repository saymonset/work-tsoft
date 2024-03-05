/**
 * 
 */
package com.indeval.portaldali.middleware.services.tesoreria.test;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.indeval.portaldali.middleware.services.BaseTestCase;
import com.indeval.sidv.decretos.persistence.model.vo.AgenteVO;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosDetalleAmortizacionesParams;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosParams;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosService;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosVO;

/**
 * @author javiles
 *
 */
public class LiquidacionDecretosDetalleAmortizacionesTest extends BaseTestCase {

    public void testLiquidacionDecretosDetalleAmortizaciones() {
	/** Representa el detalle del usuario logueado */
	
	LiquidacionDecretosService servicio = (LiquidacionDecretosService)getBean("decretosEjercicioDerechos");
	
	try {
	    LiquidacionDecretosDetalleAmortizacionesParams params = new LiquidacionDecretosDetalleAmortizacionesParams();
	    AgenteVO agenteVO = new AgenteVO();
	    agenteVO.setId("01");
	    agenteVO.setFolio("001");
	    agenteVO.setCuenta("0109");
	    params.setAgente(agenteVO);
	    params.setIdDerecho(Integer.valueOf(1));
	    params.setIdTipoDerecho(Integer.valueOf(1));
	    params.setIdTipoEjercicio(Integer.valueOf(1));
	    List lista = servicio.getLiquidacionDecretosDetalleAmortizaciones(params);
	    assertNotNull(lista);
	    assertTrue(!lista.isEmpty());
	    for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
		System.out.println("LISTA ["+iterator.next().getClass()+"]");
//		LiquidacionDecretosVO liqDecVO = (LiquidacionDecretosVO) iterator.next();
//		System.out.println("REGISTRO ["+ReflectionToStringBuilder.toString(liqDecVO)+"]");
	    }
	} catch (com.indeval.sidv.decretos.common.exception.BusinessException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
    }
    
}
