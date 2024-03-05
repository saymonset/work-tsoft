package com.indeval.portaldali.middleware.services.mercadodinero.test;


import java.math.BigDecimal;

import com.indeval.portaldali.middleware.services.BaseTestCase;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService;

/**
 * Clase de prueba unitaria para verificar la integracicn del servicio de envo
 * de operaciones
 * 
 * @author Juan Carlos Huizar Moreno.
 * 
 */
public class TesoreriaServiceImplTest extends
BaseTestCase {

	

	public void testObtenerSaldo() {
		/** Representa el detalle del usuario logueado */
		
		TesoreriaService service = (TesoreriaService) applicationContext.getBean("tesoreriaService");
		AgenteVO agente = new AgenteVO();
		agente.setId("01");
		agente.setFolio("003");
		try {
			
			Integer folio = service.retirarFondos(agente, "SPEI",new BigDecimal( 1500));
		} catch (BusinessException e) {

			e.printStackTrace();

		}

	}




}
