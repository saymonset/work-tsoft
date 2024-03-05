/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.services.common.constants.TipoCuentaConstants;
import com.indeval.portaldali.persistence.dao.estadocuenta.impl.PosicionDAOImpl;

/**
 * @author antonio
 * 
 */
public class TestPosicionDAOImpl extends BaseDaoTestCase {

	/** Log de clase */
	private Logger log = LoggerFactory.getLogger(TestPosicionDAOImpl.class);

	/**
	 * Test Dummy
	 */
	public void testDummy() {
		// Manten feliz a JUnit
	}
	
	public void testBuscarPosicionesYEmisionesV2() {
		PosicionDAOImpl posicionDAO = (PosicionDAOImpl) applicationContext.getBean("posicionDAO");
		
		PosicionDTO criterios = new PosicionDTO();
		EstadoPaginacionDTO estadoPaginacion = new EstadoPaginacionDTO();
		
		estadoPaginacion.setNumeroPagina(1);
		estadoPaginacion.setRegistrosPorPagina(50);
		
		criterios.setCuenta(new CuentaDTO());
		criterios.getCuenta().setInstitucion(new InstitucionDTO());
		criterios.getCuenta().getInstitucion().setClaveTipoInstitucion("01");
		criterios.getCuenta().getInstitucion().setId(3);
		criterios.getCuenta().getInstitucion().setFolioInstitucion("003");
		criterios.getCuenta().setNumeroCuenta("0307");
		//criterios.getCuenta().setNumeroCuenta("-1");
		criterios.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
		criterios.getCuenta().getTipoTenencia().setTipoCustodia("V");
		criterios.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA, ""));
		criterios.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
		criterios.setEmision(new EmisionDTO());
		criterios.getEmision().setTipoValor(new TipoValorDTO());
		criterios.getEmision().getTipoValor().setId(-1L);
		//criterios.getEmision().getTipoValor().setId(22L);
		StopWatch stop = new StopWatch();
		stop.start();
		long total = posicionDAO.contarEmisionesYPosiciones(criterios, new Long[]{2L,3L}).longValue();
		List<Object[]> resultados = posicionDAO.buscarEmisionesYPosiciones(criterios, estadoPaginacion, new Long[]{2L,3L});
		stop.stop();
		log.debug("stop: " + stop.prettyPrint());
		assertTrue(total > 0);
		assertTrue(resultados != null && !resultados.isEmpty());
	}

	@Test
	public void testObtenerIdsControladas() {
		log.info("Entrando a TestPosicionDAOImpl.testConsultarPosicionesPorMercado()");

		PosicionDAOImpl posicionDAO = (PosicionDAOImpl) applicationContext.getBean("posicionDAO");

		PosicionDTO criterios = new PosicionDTO();

		EstadoPaginacionDTO paginacion = new EstadoPaginacionDTO();
		paginacion.setNumeroPagina(2);
		paginacion.setRegistrosPorPagina(10);
		paginacion.setTotalPaginas(5);
		paginacion.setTotalResultados(50);

		criterios.setCuenta(new CuentaDTO());
		criterios.getCuenta().setInstitucion(new InstitucionDTO());
		criterios.getCuenta().getInstitucion().setClaveTipoInstitucion("02");
		criterios.getCuenta().getInstitucion().setId(61);
		criterios.getCuenta().getInstitucion().setFolioInstitucion("015");
		criterios.getCuenta().setTipoCustodia("V");
		criterios.getCuenta().setNumeroCuenta("-1");
		criterios.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
		criterios.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_CONTROLADA, ""));
		criterios.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));

		criterios.setEmision(new EmisionDTO());
		criterios.setFechaFinal(DateUtils.add(new Date(), Calendar.DATE, -1));

		StopWatch stop = new StopWatch();
		stop.start();

		posicionDAO.obtenerIdentificadoresValidosParaBoveda(criterios);
		posicionDAO.obtenerIdentificadoresValidosParaBovedaHistoricas(criterios);
		posicionDAO.obtenerIdentificadoresValidosParaCuenta(criterios);
		posicionDAO.obtenerIdentificadoresValidosParaCuentaHistoricas(criterios);
		posicionDAO.obtenerIdentificadoresValidosParaEmision(criterios);
		posicionDAO.obtenerIdentificadoresValidosParaEmisionHistoricas(criterios);

		stop.stop();
		System.out.println(stop.prettyPrint());
		// assertTrue(posiciones != null && posiciones.size() > 0);
	}

	public void testObtenerIdsNombradas() {
		log.info("Entrando a TestPosicionDAOImpl.testConsultarPosicionesPorMercado()");

		PosicionDAOImpl posicionDAO = (PosicionDAOImpl) applicationContext.getBean("posicionDAO");

		PosicionDTO criterios = new PosicionDTO();

		EstadoPaginacionDTO paginacion = new EstadoPaginacionDTO();
		paginacion.setNumeroPagina(2);
		paginacion.setRegistrosPorPagina(10);
		paginacion.setTotalPaginas(5);
		paginacion.setTotalResultados(50);

		criterios.setCuenta(new CuentaDTO());
		criterios.getCuenta().setInstitucion(new InstitucionDTO());
		criterios.getCuenta().getInstitucion().setClaveTipoInstitucion("02");
		criterios.getCuenta().getInstitucion().setId(61);
		criterios.getCuenta().getInstitucion().setFolioInstitucion("015");
		criterios.getCuenta().setTipoCustodia("V");
		criterios.getCuenta().setNumeroCuenta("-1");
		criterios.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
		criterios.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA, ""));
		criterios.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));

		criterios.setEmision(new EmisionDTO());
		criterios.setFechaFinal(DateUtils.add(new Date(), Calendar.DATE, -1));

		StopWatch stop = new StopWatch();
		stop.start();

		posicionDAO.obtenerIdentificadoresValidosParaBoveda(criterios);
		posicionDAO.obtenerIdentificadoresValidosParaBovedaHistoricas(criterios);
		posicionDAO.obtenerIdentificadoresValidosParaCuenta(criterios);
		posicionDAO.obtenerIdentificadoresValidosParaCuentaHistoricas(criterios);
		posicionDAO.obtenerIdentificadoresValidosParaEmision(criterios);
		posicionDAO.obtenerIdentificadoresValidosParaEmisionHistoricas(criterios);

		stop.stop();
		System.out.println(stop.prettyPrint());
		// assertTrue(posiciones != null && posiciones.size() > 0);
	}

	public void testConsultaHistoricaPosicionesControladas() {

		log.info("Entrando a TestPosicionDAOImpl.testConsultarPosicionesPorMercado()");

		PosicionDAOImpl posicionDAO = (PosicionDAOImpl) applicationContext.getBean("posicionDAO");

		PosicionDTO criterios = new PosicionDTO();

		EstadoPaginacionDTO paginacion = new EstadoPaginacionDTO();
		paginacion.setNumeroPagina(2);
		paginacion.setRegistrosPorPagina(10);
		paginacion.setTotalPaginas(5);
		paginacion.setTotalResultados(50);

		criterios.setCuenta(new CuentaDTO());
		criterios.getCuenta().setInstitucion(new InstitucionDTO());
		criterios.getCuenta().getInstitucion().setClaveTipoInstitucion("02");
		criterios.getCuenta().getInstitucion().setId(61);
		criterios.getCuenta().getInstitucion().setFolioInstitucion("015");
		criterios.getCuenta().setTipoCustodia("V");
		criterios.getCuenta().setNumeroCuenta("-1");
		criterios.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
		criterios.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_CONTROLADA, ""));
		criterios.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));

		criterios.setEmision(new EmisionDTO());
		criterios.setFechaFinal(DateUtils.add(new Date(), Calendar.DATE, -1));

		StopWatch stop = new StopWatch();
		stop.start();

//		posicionDAO.obtenerProyeccionDeConsultaDePosicionesControladasHistoricas(criterios);
//		List<PosicionDTO> posiciones = posicionDAO.buscarPosicionesControladasHistoricas(criterios, paginacion, null);
		stop.stop();
		System.out.println(stop.prettyPrint());
		// assertTrue(posiciones != null && posiciones.size() > 0);
	}

	public void testConsultaPosicionesControladas() {

		log.info("Entrando a TestPosicionDAOImpl.testConsultarPosicionesPorMercado()");

		PosicionDAOImpl posicionDAO = (PosicionDAOImpl) applicationContext.getBean("posicionDAO");

		PosicionDTO criterios = new PosicionDTO();

		EstadoPaginacionDTO paginacion = new EstadoPaginacionDTO();
		paginacion.setNumeroPagina(2);
		paginacion.setRegistrosPorPagina(10);
		paginacion.setTotalPaginas(5);
		paginacion.setTotalResultados(50);

		criterios.setCuenta(new CuentaDTO());
		criterios.getCuenta().setInstitucion(new InstitucionDTO());
		criterios.getCuenta().getInstitucion().setClaveTipoInstitucion("02");
		criterios.getCuenta().getInstitucion().setId(61);
		criterios.getCuenta().getInstitucion().setFolioInstitucion("015");
		criterios.getCuenta().setTipoCustodia("V");
		criterios.getCuenta().setNumeroCuenta("-1");
		criterios.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
		criterios.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_CONTROLADA, ""));
		criterios.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));

		criterios.setFechaFinal(new Date());

		StopWatch stop = new StopWatch();
		stop.start();
//		posicionDAO.obtenerProyeccionDeConsultaDePosicionesNombradas(criterios);
//		List<PosicionDTO> posiciones = posicionDAO.buscarPosicionesControladas(criterios, paginacion, null);
		stop.stop();
		System.out.println(stop.prettyPrint());
//		assertTrue(posiciones != null && posiciones.size() > 0);
	}


	public void testConsultaHistoricaPosiciones() {

		log.info("Entrando a TestPosicionDAOImpl.testConsultarPosicionesPorMercado()");

		PosicionDAOImpl posicionDAO = (PosicionDAOImpl) applicationContext.getBean("posicionDAO");

		PosicionDTO criterios = new PosicionDTO();

		EstadoPaginacionDTO paginacion = new EstadoPaginacionDTO();
		paginacion.setNumeroPagina(2);
		paginacion.setRegistrosPorPagina(10);
		paginacion.setTotalPaginas(5);
		paginacion.setTotalResultados(50);

		criterios.setCuenta(new CuentaDTO());
		criterios.getCuenta().setInstitucion(new InstitucionDTO());
		criterios.getCuenta().getInstitucion().setClaveTipoInstitucion("02");
		criterios.getCuenta().getInstitucion().setId(61);
		criterios.getCuenta().getInstitucion().setFolioInstitucion("015");
		criterios.getCuenta().setTipoCustodia("V");
		criterios.getCuenta().setNumeroCuenta("-1");
		criterios.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
		criterios.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA, ""));
		criterios.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));

		criterios.setFechaFinal(DateUtils.add(new Date(), Calendar.DATE, -1));

		StopWatch stop = new StopWatch();
		stop.start();
//		posicionDAO.obtenerProyeccionDeConsultaDePosicionesNombradasHistoricas(criterios);
//		List<PosicionDTO> posiciones = posicionDAO.buscarPosicionesNombradasHistoricas(criterios, paginacion, null);
		stop.stop();
		System.out.println(stop.prettyPrint());
		// assertTrue(posiciones != null && posiciones.size() > 0);

	}

//	public void testConsultaPosiciones() {
//
//		log.info("Entrando a TestPosicionDAOImpl.testConsultarPosicionesPorMercado()");
//
//		PosicionDAOImpl posicionDAO = (PosicionDAOImpl) applicationContext.getBean("posicionDAO");
//
//		PosicionDTO criterios = new PosicionDTO();
//
//		EstadoPaginacionDTO paginacion = new EstadoPaginacionDTO();
//		paginacion.setNumeroPagina(2);
//		paginacion.setRegistrosPorPagina(10);
//		paginacion.setTotalPaginas(5);
//		paginacion.setTotalResultados(50);
//
//		criterios.setCuenta(new CuentaDTO());
//		criterios.getCuenta().setInstitucion(new InstitucionDTO());
//		criterios.getCuenta().getInstitucion().setClaveTipoInstitucion("02");
//		criterios.getCuenta().getInstitucion().setId(61);
//		criterios.getCuenta().getInstitucion().setFolioInstitucion("015");
//		criterios.getCuenta().setTipoCustodia("V");
//		criterios.getCuenta().setNumeroCuenta("-1");
//		criterios.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
//		criterios.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA, ""));
//		criterios.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
//
//		criterios.setFechaFinal(new Date());
//
//		StopWatch stop = new StopWatch();
//		stop.start();
//		posicionDAO.obtenerProyeccionDeConsultaDePosicionesNombradas(criterios);
//		List<PosicionDTO> posiciones = posicionDAO.buscarPosicionesNombradas(criterios, paginacion, null);
//		stop.stop();
//		System.out.println(stop.prettyPrint());
//		assertTrue(posiciones != null && posiciones.size() > 0);
//
//	}



}
