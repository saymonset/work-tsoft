/**
 * 2H Software - Bursatec
 * 
 * Sistema de Consulta de Estados de Cuenta
 *
 * Jan 14, 2008
 *
 */
package com.indeval.portaldali.middleware.services.match;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseTestCase;
import com.indeval.portaldali.middleware.services.estatus.ConsultaEstatusOperacionesMatchService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;


/**
 * Prueba unitaria del servicio de negocio ConsultaSaldosEfectivoService
 * @author Pablo Julián Balderas Méndez
 * 
 */
public class MatchSaldoActuallTest extends BaseTestCase {
	
	private Logger log = LoggerFactory.getLogger(MatchSaldoActuallTest.class);
	
	/**
	 * Servicio de negocio sobre el cual se efectuaran las pruebas unitarias
	 */
	private ConsultaEstatusOperacionesMatchService servicio;
	
	public void onSetUp() {
		super.onSetUp();
		servicio = (ConsultaEstatusOperacionesMatchService)getBean("consultaEstatusOperacionesMatchService");
	}
	
	/**
	 * Prueba unitaria para el método consultarSaldosEfectivo
	 */
	public void testConsultarSaldoActual() {
		EmisionVO emision = new EmisionVO();
		AgenteVO agenteFirmado = new AgenteVO();
		BigInteger bovedaId = BigInteger.valueOf(1);
		
		agenteFirmado.setId("01");
		agenteFirmado.setFolio("003");
		agenteFirmado.setCuenta("0315");
		
		emision.setIdTipoValor("1");
		emision.setEmisora("TELMEX");
		emision.setSerie("L");
		emision.setCupon("0049");
		emision.setIsin("ENC000000014");
		
		BigInteger saldoActual = servicio.getSaldoActual(agenteFirmado, emision, bovedaId);
		
		log.info("Saldo inicial: [" + saldoActual + "]");
	}
	
}
