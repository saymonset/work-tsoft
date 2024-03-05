/**
 * 2H Software
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Creado el Jan 9, 2008
 */
package com.indeval.portaldali.middleware.services.estadocuenta.test;

import com.indeval.portaldali.middleware.dto.DetalleMovimientoValorDTO;
import com.indeval.portaldali.middleware.services.BaseTestCase;
import com.indeval.portaldali.middleware.services.estadocuenta.ConsultaDetalleMovimientoPosicionService;
import com.indeval.portaldali.middleware.services.estadocuenta.ConsultaDetalleMovimientoPosicionServiceImpl;

/**
 * Prueba unitaria de la clase {@link ConsultaDetalleMovimientoPosicionServiceImpl}
 *
 * @author José Antonio Huizar Moreno
 * @version 1.0
 *
 */
public class ConsultaDetalleMovimientoServiceTest extends BaseTestCase {
	
	/**
	 * Prueba unitaria del método consultarDetalleMovimientoValor
	 */
	public void testConsultarDetalleMovimientoValorDVP() {
		ConsultaDetalleMovimientoPosicionService consultaDetalleMovimientoPosicionService = (ConsultaDetalleMovimientoPosicionService) applicationContext.getBean("consultaDetalleMovimientoPosicionService");
		
		DetalleMovimientoValorDTO detalleMovimientoTraspasante = consultaDetalleMovimientoPosicionService.consultarDetalleMovimientoValor(19784, 4, false);
		
		assertNotNull(detalleMovimientoTraspasante);
		assertTrue(detalleMovimientoTraspasante.getDetallesParcialidad() != null && detalleMovimientoTraspasante.getDetallesParcialidad().size() > 0);
		
		DetalleMovimientoValorDTO detalleMovimientoReceptor = consultaDetalleMovimientoPosicionService.consultarDetalleMovimientoValor(19785, 12, false);
		
		assertNotNull(detalleMovimientoReceptor);
		assertTrue(detalleMovimientoReceptor.getDetallesParcialidad() != null && detalleMovimientoReceptor.getDetallesParcialidad().size() > 0);
	}
	
	
}
