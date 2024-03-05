/**
 * 2H Software - Bursatec
 * 
 * Sistema de Consulta de Estados de Cuenta
 *
 * Jan 14, 2008
 *
 */
package com.indeval.portaldali.middleware.services.estadocuenta.test;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseTestCase;
import com.indeval.portaldali.middleware.services.conciliacion.ConciliacionService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;


/**
 * Prueba unitaria del servicio de negocio ConsultaSaldosEfectivoService
 * @author Pablo Julián Balderas Méndez
 * 
 */
public class ConciliacionServiceImplSaldoInicialTest extends BaseTestCase {
	
	private Logger logger = LoggerFactory.getLogger(ConciliacionServiceImplSaldoInicialTest.class);
	
	/**
	 * Servicio de negocio sobre el cual se efectuaran las pruebas unitarias
	 */
	private ConciliacionService conciliacionService;
	
	public void onSetUp() {
		super.onSetUp();
		conciliacionService = (ConciliacionService)getBean("conciliacionService");
	}
	
	/**
	 * Prueba unitaria para el método consultarSaldosEfectivo
	 */
	public void testConsultarSaldoInicial() {
		EmisionVO emision = new EmisionVO();
		AgenteVO agenteFirmado = new AgenteVO();
		BigInteger bovedaId = BigInteger.valueOf(1);
		
		agenteFirmado.setId("01");
		agenteFirmado.setFolio("003");
		agenteFirmado.setCuenta("0315");
		
		emision.setIdTipoValor("1");
		emision.setEmisora("AMX");
		emision.setSerie("L");
		
		
		
		BigInteger saldoInicial = conciliacionService.getPosicionInicial(agenteFirmado, emision, bovedaId);
		
		logger.info("Saldo inicial: [" + saldoInicial + "]");
	}
	
}
