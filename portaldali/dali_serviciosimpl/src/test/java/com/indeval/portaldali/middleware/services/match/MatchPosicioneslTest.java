/**
 * 2H Software - Bursatec
 * 
 * Sistema de Consulta de Estados de Cuenta
 *
 * Jan 14, 2008
 *
 */
package com.indeval.portaldali.middleware.services.match;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.services.BaseTestCase;
import com.indeval.portaldali.middleware.services.estatus.ConsultaEstatusOperacionesMatchService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;


/**
 * Prueba unitaria del servicio de negocio ConsultaSaldosEfectivoService
 * @author Pablo Julián Balderas Méndez
 * 
 */
public class MatchPosicioneslTest extends BaseTestCase {
	
	private Logger logger = LoggerFactory.getLogger(MatchPosicioneslTest.class);
	
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
	public void testConsultarPosiciones() {
		EmisionVO emision = new EmisionVO();
		AgenteVO agenteFirmado = new AgenteVO();
		
		agenteFirmado.setId("01");
		agenteFirmado.setFolio("001");
		agenteFirmado.setCuenta("0109");
		
		emision.setIdTipoValor("1");
		emision.setEmisora("AMX");
		emision.setSerie("L");
		
		List<PosicionDTO> lista = servicio.getListaPosiciones(agenteFirmado, emision);
		
		assertNotNull(lista);
		assertFalse(lista.size() == 0);
		
		logger.info("Tamaño de la lista: [" + lista.size() + "]");
		
		for( PosicionDTO posicion : lista ) {
			logger.info("Posicion: [" + posicion.getCuenta().getInstitucion().getClaveTipoInstitucion() + "" +
					"-" + posicion.getCuenta().getInstitucion().getFolioInstitucion() + "" +
					"-" + posicion.getCuenta().getCuenta() + "" +
					"-" + posicion.getEmision().getTipoValor().getClaveTipoValor() + "" +
					"-" + posicion.getEmision().getEmisora().getDescripcion() + "" +
					"-" + posicion.getEmision().getSerie().getSerie() + "" +
					"-" + posicion.getEmision().getCupon() + "" +
					"-" + posicion.getPosicionDisponible() + "" +
							"]");
		}
	}
	
}
