/**
 * 
 */
package com.indeval.portaldali.middleware.services.tesoreria.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.indeval.portaldali.middleware.services.BaseTestCase;
import com.indeval.sidv.decretos.common.exception.BusinessException;
import com.indeval.sidv.decretos.persistence.model.vo.AgenteVO;
import com.indeval.sidv.decretos.persistence.model.vo.EmisionVO;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosParams;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosService;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosVO;

/**
 * @author javiles
 * 
 */
public class LiquidacionDecretosTest extends BaseTestCase {

	public void testGetLiquidacionDecretos() {
		/** Representa el detalle del usuario logueado */

		LiquidacionDecretosService servicio = (LiquidacionDecretosService) getBean("decretosEjercicioDerechos");

		try {
			LiquidacionDecretosParams params = new LiquidacionDecretosParams();
			AgenteVO agenteVO = new AgenteVO();
			agenteVO.setId("01");
			agenteVO.setFolio("003");
			params.setAgente(agenteVO);
			Calendar cal = Calendar.getInstance();
			cal.set(2008, Calendar.SEPTEMBER, 9, 0, 0, 0);
			params.setFechaIni(cal.getTime());
			cal.set(2008, Calendar.OCTOBER, 14, 23, 59, 59);
			params.setFechaFin(cal.getTime());
			params.setTipoConsulta("COBROS");
			List lista = servicio.getLiquidacionDecretos(params);

			for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
				LiquidacionDecretosVO liqDecVO = (LiquidacionDecretosVO) iterator
						.next();
				System.out.println("REGISTRO ["
						+ ReflectionToStringBuilder.toString(liqDecVO) + "]");
			}
		} catch (com.indeval.sidv.decretos.common.exception.BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void testConsulta() throws BusinessException {

		LiquidacionDecretosParams filtro = new LiquidacionDecretosParams();

		Calendar cal = Calendar.getInstance();
		cal.set(2008, Calendar.OCTOBER, 6, 0, 0, 0);
		
		filtro.setFechaIni(cal.getTime());
		
		cal.set(2008, Calendar.OCTOBER, 14, 23, 59, 59);

		filtro.setFechaFin(cal.getTime());

		EmisionVO emision = new EmisionVO();

		emision.setEmisora("BMONEX");

		emision.setIdTipoValor("I");

		emision.setSerie(null);

		filtro.setEmision(emision);

		filtro.setTipoConsulta("COBROS");

		AgenteVO agente = new AgenteVO();

		agente.setId("01");

		agente.setFolio("010");

		agente.setCuenta(null);

		filtro.setAgente(agente);

		filtro.setTipoMoneda("MXN");
		//filtro.setTipoEjercicio("AMP");

		List x = new ArrayList();

		LiquidacionDecretosService servicio = (LiquidacionDecretosService) getBean("decretosEjercicioDerechos");
		
		
		List<String> tiposDerecho = servicio.getTiposDerecho(filtro);		
		System.out.println(" tiposDerecho ["+tiposDerecho+"]");		
		
		
		System.out.println(" filtro "+ToStringBuilder.reflectionToString( filtro));

		x = servicio.getLiquidacionDecretos(filtro);

		System.out.println(" size [" + x.size() + "]");

		for (Iterator iter = x.iterator(); iter.hasNext();) {

			LiquidacionDecretosVO element = (LiquidacionDecretosVO) iter.next();
			System.out.println(" element "+ToStringBuilder.reflectionToString( element));

		}

	}

}
