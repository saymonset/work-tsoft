/**
 * Sistema Portal DALI
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Nov 3, 2008
 */
package com.indeval.persistence.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.dto.DetalleMovimientoEfectivoDTO;
import com.indeval.portaldali.persistence.dao.estadocuenta.DetalleMovimientoEfectivoDAO;

/**
 * Prueba unitaria para el DAO de la consulta de movimientos de Efectivo.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public class TestConsultaDetalleMovimientoEfectivoDAO extends BaseDaoTestCase{
	
	private Logger logger = LoggerFactory.getLogger(TestConsultaDetalleMovimientoEfectivoDAO.class);

	public void testConsultaMovimientosEfectivo() {

		DetalleMovimientoEfectivoDAO detalleMovimientoEfectivoDAO = 
			(DetalleMovimientoEfectivoDAO) applicationContext.getBean("detalleMovimientoEfectivoDAO");

		Long idRegistroContable = 2129724l;

		DetalleMovimientoEfectivoDTO detalle = 
			detalleMovimientoEfectivoDAO.consultarDetalleMovimientoEfectivo(idRegistroContable, false);
		assertNotNull(detalle);

		logger.info("Folio Instruccion: [" + detalle.getInstruccion().getFolioInstruccion() + "]");
		
		logger.info("Tipo D/R: [" + detalle.getOperacion().getInstruccionLiquidacion().getInstruccionEfectivo().getTipoRetiro() + "]");
	}

	

}
