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

import com.indeval.portaldali.middleware.services.BaseTestCase;
import com.indeval.portaldali.middleware.services.conciliacion.ConciliacionService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.persistence.dao.conciliacion.OperacionNombradaDao;


/**
 * Prueba unitaria del servicio de negocio ConsultaSaldosEfectivoService
 * @author Pablo Julián Balderas Méndez
 * 
 */
public class ConciliacionServiceImplTest extends BaseTestCase {
	
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
	public void testConsultarSaldosEfectivo() {
		assertNotNull(conciliacionService);
		PaginaVO pagina = new PaginaVO();
		pagina.setRegistrosXPag(PaginaVO.TODOS);
		
		AgenteVO agenteFirmado = new AgenteVO();
		agenteFirmado.setId("01");
		agenteFirmado.setFolio("003");
		agenteFirmado.setCuenta("0315");
		
		EmisionVO emision = new EmisionVO();
		
		pagina.getValores().put(OperacionNombradaDao.BOVEDA, BigInteger.ONE);
		
		pagina = conciliacionService.archivoConciliacion(agenteFirmado, emision, pagina, false);
	}
	
}
