/**
 * 2H Software - Bursatec
 * 
 * Sistema de Consulta de Estados de Cuenta
 *
 * Jan 14, 2008
 *
 */
package com.indeval.portaldali.middleware.services.ingresos;

import com.indeval.portaldali.middleware.services.BaseTestCase;
import com.indeval.portaldali.middleware.services.advice.ReceiverBitacoraConsultaService;


/**
 * Prueba unitaria del servicio de negocio ConsultaSaldosEfectivoService
 * @author Pablo Julián Balderas Méndez
 * 
 */
public class IngresosRecibeMensajelTest extends BaseTestCase {
	
	/**
	 * Servicio de negocio sobre el cual se efectuaran las pruebas unitarias
	 */
	private ReceiverBitacoraConsultaService receiverBitacoraConsultaService;
	
	public void onSetUp() {
		super.onSetUp();
		receiverBitacoraConsultaService = (ReceiverBitacoraConsultaService)getBean("receiverBitacoraConsultaService");
	}
	
	/**
	 * Prueba unitaria para el método consultarSaldosEfectivo
	 */
	public void testRecibeMesnaje() {
		
	}
	
}
