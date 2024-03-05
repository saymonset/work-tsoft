/**
 * Sistema Portal DALI
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Nov 3, 2008
 */
package com.indeval.persistence.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosEfectivoDTO;
import com.indeval.portaldali.middleware.dto.CuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.RegistroContableSaldoNombradaDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.TipoOperacionDTO;
import com.indeval.portaldali.persistence.dao.estadocuenta.MovimientosEfectivoDAOV2;

/**
 * Prueba unitaria para el DAO de la consulta de movimientos de Efectivo.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public class TestConsultaMovimientosEfectivoHistoricoDAO extends BaseDaoTestCase {
	
	private Logger logger = LoggerFactory.getLogger(TestConsultaMovimientosEfectivoHistoricoDAO.class);

	public void testConsultaMovimientosEfectivo() {
		
		logger.info("Entrando al metodo");

		MovimientosEfectivoDAOV2 movimientosEfectivoDAO = (MovimientosEfectivoDAOV2) applicationContext.getBean("movimientosEfectivoDAOV2");

		CriterioConsultaMovimientosEfectivoDTO criterio = new CriterioConsultaMovimientosEfectivoDTO();

		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
		Date fechaInicial = null, fechaFinal = null;
		
		try {
			fechaInicial = sdf.parse("17-03-2009 00:00:00");
			fechaFinal = sdf.parse("17-03-2009 23:59:59");
		} catch (Exception e) {
			logger.error("Error al convertir las fechas");
		}
		
		criterio.setFechaInicial(fechaInicial);
		criterio.setFechaFinal(fechaFinal);
		criterio.setBusquedaFechaAplicacion(false);
		criterio.setBusquedaFechaConcertacion(false);
		
		criterio.setCuenta(new CuentaEfectivoDTO());
		criterio.getCuenta().setInstitucion(new InstitucionDTO());
		criterio.getCuenta().setCuenta("2000");
		criterio.getCuenta().setNumeroCuenta("020612000");
		criterio.getCuenta().getInstitucion().setId(104);
		criterio.getCuenta().getInstitucion().setClaveTipoInstitucion("02");
		criterio.getCuenta().getInstitucion().setFolioInstitucion("061");
		criterio.getCuenta().setTipoNaturaleza(new TipoNaturalezaDTO());
		criterio.getCuenta().getTipoNaturaleza().setId("P");
		criterio.getCuenta().setTipoCuenta(new TipoCuentaDTO());
		criterio.getCuenta().getTipoCuenta().setId("N");

		criterio.setCuentaContraparte(new CuentaEfectivoDTO());
		criterio.getCuentaContraparte().setTipoNaturaleza(new TipoNaturalezaDTO());
		criterio.getCuentaContraparte().getTipoNaturaleza().setId("P");
		criterio.getCuentaContraparte().setTipoCuenta(new TipoCuentaDTO());
		criterio.getCuentaContraparte().getTipoCuenta().setId("N");
		criterio.getCuentaContraparte().setNumeroCuenta("-1");

		criterio.setTipoInstruccion(new TipoInstruccionDTO());
		criterio.setTipoOperacion(new TipoOperacionDTO());
		criterio.getTipoInstruccion().setIdTipoInstruccion(-1L);
		criterio.getTipoOperacion().setId(-1);

		List<RegistroContableSaldoNombradaDTO> registros = movimientosEfectivoDAO.buscarRegistrosContablesDeEfectivoNombradaHistorico(
				criterio, null);
		assertTrue(registros != null && registros.size() > 0);
		
		logger.info("Numero de registros encontrados: [" + registros.size() + "]");

	}

}
