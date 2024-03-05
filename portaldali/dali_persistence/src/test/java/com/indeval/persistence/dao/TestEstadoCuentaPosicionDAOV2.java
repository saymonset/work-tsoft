/**
 * Sistema Portal DALI
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Nov 20, 2008
 */
package com.indeval.persistence.dao;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionControladaDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionNombradaDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.middleware.services.common.constants.TipoCuentaConstants;
import com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaPosicionDAOV2;

/**
 * Prueba unitaria para el DAO {@link TestEstadoCuentaPosicionDAOV2}
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public class TestEstadoCuentaPosicionDAOV2 extends BaseDaoTestCase {

	/**
	 * Prueba unitaria para el método buscarCuentasDePosicionesControladas
	 */
	public void testBuscarCuentasDePosicionesControladas() {
		PosicionDTO criterios = new PosicionDTO();

		criterios.setCuenta(new CuentaDTO());
		criterios.getCuenta().setInstitucion(new InstitucionDTO());
		criterios.getCuenta().getInstitucion().setClaveTipoInstitucion("01");
		criterios.getCuenta().getInstitucion().setId(3);
		criterios.getCuenta().getInstitucion().setFolioInstitucion("003");
		//criterios.getCuenta().setNumeroCuenta("0307");
		criterios.getCuenta().setNumeroCuenta("-1");
		criterios.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
		criterios.getCuenta().getTipoTenencia().setTipoCustodia("V");
		criterios.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_CONTROLADA, ""));
		criterios.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
		criterios.setEmision(new EmisionDTO());
		criterios.getEmision().setTipoValor(new TipoValorDTO());
		criterios.getEmision().getTipoValor().setId(-1L);
		// criterios.getEmision().getTipoValor().setId(22L);

		EstadoCuentaPosicionDAOV2 estadoCuentaPosicionDAOV2 = (EstadoCuentaPosicionDAOV2) applicationContext.getBean("estadoCuentaPosicionDAOV2");

		List<Long> idsCuentas = estadoCuentaPosicionDAOV2.buscarCuentasDePosicionesControladas(criterios);

		assertTrue(idsCuentas != null && !idsCuentas.isEmpty());
	}
	
	/**
	 * Prueba unitaria para el método buscarCuentasDePosicionesControladasHistoricas
	 */
	public void testBuscarCuentasDePosicionesControladasHistoricas() {
		PosicionDTO criterios = new PosicionDTO();

		criterios.setCuenta(new CuentaDTO());
		criterios.getCuenta().setInstitucion(new InstitucionDTO());
		criterios.getCuenta().getInstitucion().setClaveTipoInstitucion("01");
		criterios.getCuenta().getInstitucion().setId(3);
		criterios.getCuenta().getInstitucion().setFolioInstitucion("003");
		//criterios.getCuenta().setNumeroCuenta("0307");
		criterios.getCuenta().setNumeroCuenta("-1");
		criterios.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
		criterios.getCuenta().getTipoTenencia().setTipoCustodia("V");
		criterios.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_CONTROLADA, ""));
		criterios.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
		criterios.setEmision(new EmisionDTO());
		criterios.getEmision().setTipoValor(new TipoValorDTO());
		criterios.getEmision().getTipoValor().setId(-1L);
		// criterios.getEmision().getTipoValor().setId(22L);

		EstadoCuentaPosicionDAOV2 estadoCuentaPosicionDAOV2 = (EstadoCuentaPosicionDAOV2) applicationContext.getBean("estadoCuentaPosicionDAOV2");

		Calendar c = GregorianCalendar.getInstance();
		
		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);
		
		List<Long> idsCuentas = estadoCuentaPosicionDAOV2.buscarCuentasDePosicionesControladasHistoricas(criterios, c.getTime());

		assertTrue(idsCuentas != null && !idsCuentas.isEmpty());
	}
	
	
	/**
	 * Prueba unitaria para el método buscarCuentasDePosicionesNombradas
	 */
	public void testBuscarCuentasDePosicionesNombradas() {
		PosicionDTO criterios = new PosicionDTO();

		criterios.setCuenta(new CuentaDTO());
		criterios.getCuenta().setInstitucion(new InstitucionDTO());
		criterios.getCuenta().getInstitucion().setClaveTipoInstitucion("01");
		criterios.getCuenta().getInstitucion().setId(3);
		criterios.getCuenta().getInstitucion().setFolioInstitucion("003");
		//criterios.getCuenta().setNumeroCuenta("0307");
		criterios.getCuenta().setNumeroCuenta("-1");
		criterios.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
		criterios.getCuenta().getTipoTenencia().setTipoCustodia("V");
		criterios.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA, ""));
		criterios.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
		criterios.setEmision(new EmisionDTO());
		criterios.getEmision().setTipoValor(new TipoValorDTO());
		criterios.getEmision().getTipoValor().setId(-1L);
		// criterios.getEmision().getTipoValor().setId(22L);

		EstadoCuentaPosicionDAOV2 estadoCuentaPosicionDAOV2 = (EstadoCuentaPosicionDAOV2) applicationContext.getBean("estadoCuentaPosicionDAOV2");
		
		List<Long> idsCuentas = estadoCuentaPosicionDAOV2.buscarCuentasDePosicionesNombradas(criterios);

		assertTrue(idsCuentas != null && !idsCuentas.isEmpty());
	}
	
	/**
	 * Prueba unitaria para el método buscarCuentasDePosicionesNombradasHistoricas
	 */
	public void testBuscarCuentasDePosicionesNombradasHistoricas() {
		PosicionDTO criterios = new PosicionDTO();

		criterios.setCuenta(new CuentaDTO());
		criterios.getCuenta().setInstitucion(new InstitucionDTO());
		criterios.getCuenta().getInstitucion().setClaveTipoInstitucion("01");
		criterios.getCuenta().getInstitucion().setId(3);
		criterios.getCuenta().getInstitucion().setFolioInstitucion("003");
		//criterios.getCuenta().setNumeroCuenta("0307");
		criterios.getCuenta().setNumeroCuenta("-1");
		criterios.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
		criterios.getCuenta().getTipoTenencia().setTipoCustodia("V");
		criterios.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA, ""));
		criterios.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
		criterios.setEmision(new EmisionDTO());
		criterios.getEmision().setTipoValor(new TipoValorDTO());
		criterios.getEmision().getTipoValor().setId(-1L);
		// criterios.getEmision().getTipoValor().setId(22L);

		EstadoCuentaPosicionDAOV2 estadoCuentaPosicionDAOV2 = (EstadoCuentaPosicionDAOV2) applicationContext.getBean("estadoCuentaPosicionDAOV2");

		Calendar c = GregorianCalendar.getInstance();
		
		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);
		
		List<Long> idsCuentas = estadoCuentaPosicionDAOV2.buscarCuentasDePosicionesNombradasHistoricas(criterios, c.getTime());

		assertTrue(idsCuentas != null && !idsCuentas.isEmpty());
	}
	
	/**
	 * Prueba unitaria para el método buscarEmisionesDePosicionesControladas
	 */
	public void testBuscarEmisionesDePosicionesControladas() {
		PosicionDTO criterios = new PosicionDTO();

		criterios.setCuenta(new CuentaDTO());
		criterios.getCuenta().setInstitucion(new InstitucionDTO());
		criterios.getCuenta().getInstitucion().setClaveTipoInstitucion("01");
		criterios.getCuenta().getInstitucion().setId(3);
		criterios.getCuenta().getInstitucion().setFolioInstitucion("003");
		//criterios.getCuenta().setNumeroCuenta("0307");
		criterios.getCuenta().setNumeroCuenta("-1");
		criterios.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
		criterios.getCuenta().getTipoTenencia().setTipoCustodia("V");
		criterios.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_CONTROLADA, ""));
		criterios.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
		criterios.setEmision(new EmisionDTO());
		criterios.getEmision().setTipoValor(new TipoValorDTO());
		criterios.getEmision().getTipoValor().setId(-1L);
		// criterios.getEmision().getTipoValor().setId(22L);

		EstadoCuentaPosicionDAOV2 estadoCuentaPosicionDAOV2 = (EstadoCuentaPosicionDAOV2) applicationContext.getBean("estadoCuentaPosicionDAOV2");

		List<Long> idsEmisiones = estadoCuentaPosicionDAOV2.buscarEmisionesDePosicionesControladas(criterios);

		assertTrue(idsEmisiones != null && !idsEmisiones.isEmpty());
	}
	
	/**
	 * Prueba unitaria para el método buscarEmisionesDePosicionesControladasHistoricas
	 */
	public void testBuscarEmisionesDePosicionesControladasHistoricas() {
		PosicionDTO criterios = new PosicionDTO();

		criterios.setCuenta(new CuentaDTO());
		criterios.getCuenta().setInstitucion(new InstitucionDTO());
		criterios.getCuenta().getInstitucion().setClaveTipoInstitucion("01");
		criterios.getCuenta().getInstitucion().setId(3);
		criterios.getCuenta().getInstitucion().setFolioInstitucion("003");
		//criterios.getCuenta().setNumeroCuenta("0307");
		criterios.getCuenta().setNumeroCuenta("-1");
		criterios.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
		criterios.getCuenta().getTipoTenencia().setTipoCustodia("V");
		criterios.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_CONTROLADA, ""));
		criterios.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
		criterios.setEmision(new EmisionDTO());
		criterios.getEmision().setTipoValor(new TipoValorDTO());
		criterios.getEmision().getTipoValor().setId(-1L);
		// criterios.getEmision().getTipoValor().setId(22L);

		EstadoCuentaPosicionDAOV2 estadoCuentaPosicionDAOV2 = (EstadoCuentaPosicionDAOV2) applicationContext.getBean("estadoCuentaPosicionDAOV2");

		Calendar c = GregorianCalendar.getInstance();
		
		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);
		
		List<Long> idsEmisiones = estadoCuentaPosicionDAOV2.buscarEmisionesDePosicionesControladasHistoricas(criterios, c.getTime());

		assertTrue(idsEmisiones != null && !idsEmisiones.isEmpty());
	}
	
	/**
	 * Prueba unitaria para el método buscarEmisionesDePosicionesNombradas
	 */
	public void testBuscarEmisionesDePosicionesNombradas() {
		PosicionDTO criterios = new PosicionDTO();

		criterios.setCuenta(new CuentaDTO());
		criterios.getCuenta().setInstitucion(new InstitucionDTO());
		criterios.getCuenta().getInstitucion().setClaveTipoInstitucion("01");
		criterios.getCuenta().getInstitucion().setId(3);
		criterios.getCuenta().getInstitucion().setFolioInstitucion("003");
		//criterios.getCuenta().setNumeroCuenta("0307");
		criterios.getCuenta().setNumeroCuenta("-1");
		criterios.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
		criterios.getCuenta().getTipoTenencia().setTipoCustodia("V");
		criterios.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA, ""));
		criterios.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
		criterios.setEmision(new EmisionDTO());
		criterios.getEmision().setTipoValor(new TipoValorDTO());
		criterios.getEmision().getTipoValor().setId(-1L);
		// criterios.getEmision().getTipoValor().setId(22L);

		EstadoCuentaPosicionDAOV2 estadoCuentaPosicionDAOV2 = (EstadoCuentaPosicionDAOV2) applicationContext.getBean("estadoCuentaPosicionDAOV2");
		
		List<Long> idsEmisiones = estadoCuentaPosicionDAOV2.buscarEmisionesDePosicionesNombradas(criterios);

		assertTrue(idsEmisiones != null && !idsEmisiones.isEmpty());
	}
	
	/**
	 * Prueba unitaria para el método buscarEmisionesDePosicionesNombradasHistoricas
	 */
	public void testBuscarEmisionesDePosicionesNombradasHistoricas() {
		PosicionDTO criterios = new PosicionDTO();

		criterios.setCuenta(new CuentaDTO());
		criterios.getCuenta().setInstitucion(new InstitucionDTO());
		criterios.getCuenta().getInstitucion().setClaveTipoInstitucion("01");
		criterios.getCuenta().getInstitucion().setId(3);
		criterios.getCuenta().getInstitucion().setFolioInstitucion("003");
		//criterios.getCuenta().setNumeroCuenta("0307");
		criterios.getCuenta().setNumeroCuenta("-1");
		criterios.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
		criterios.getCuenta().getTipoTenencia().setTipoCustodia("V");
		criterios.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA, ""));
		criterios.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
		criterios.setEmision(new EmisionDTO());
		criterios.getEmision().setTipoValor(new TipoValorDTO());
		criterios.getEmision().getTipoValor().setId(-1L);
		// criterios.getEmision().getTipoValor().setId(22L);

		EstadoCuentaPosicionDAOV2 estadoCuentaPosicionDAOV2 = (EstadoCuentaPosicionDAOV2) applicationContext.getBean("estadoCuentaPosicionDAOV2");

		Calendar c = GregorianCalendar.getInstance();
		
		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);
		
		List<Long> idsEmisiones = estadoCuentaPosicionDAOV2.buscarEmisionesDePosicionesNombradasHistoricas(criterios, c.getTime());

		assertTrue(idsEmisiones != null && !idsEmisiones.isEmpty());
	}
	
	/**
	 * Prueba unitaria para el método buscarPosicionesControladas
	 */
	public void testBuscarPosicionesControladas() {
		PosicionDTO criterios = new PosicionDTO();

		criterios.setCuenta(new CuentaDTO());
		criterios.getCuenta().setInstitucion(new InstitucionDTO());
		criterios.getCuenta().getInstitucion().setClaveTipoInstitucion("01");
		criterios.getCuenta().getInstitucion().setId(3);
		criterios.getCuenta().getInstitucion().setFolioInstitucion("003");
		//criterios.getCuenta().setNumeroCuenta("0307");
		criterios.getCuenta().setNumeroCuenta("-1");
		criterios.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
		criterios.getCuenta().getTipoTenencia().setTipoCustodia("V");
		criterios.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_CONTROLADA, ""));
		criterios.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
		criterios.setEmision(new EmisionDTO());
		criterios.getEmision().setTipoValor(new TipoValorDTO());
		criterios.getEmision().getTipoValor().setId(-1L);
		// criterios.getEmision().getTipoValor().setId(22L);

		EstadoCuentaPosicionDAOV2 estadoCuentaPosicionDAOV2 = (EstadoCuentaPosicionDAOV2) applicationContext.getBean("estadoCuentaPosicionDAOV2");

		List<PosicionDTO> posiciones = estadoCuentaPosicionDAOV2.buscarPosicionesControladas(criterios, Arrays.asList(new Long[]{1144L,1162L,1165L,1181L}));

		assertTrue(posiciones != null && !posiciones.isEmpty());
	}
	
	/**
	 * Prueba unitaria para el método buscarPosicionesControladasHistoricas
	 */
	public void testBuscarPosicionesControladasHistoricas() {
		PosicionDTO criterios = new PosicionDTO();

		criterios.setCuenta(new CuentaDTO());
		criterios.getCuenta().setInstitucion(new InstitucionDTO());
		criterios.getCuenta().getInstitucion().setClaveTipoInstitucion("01");
		criterios.getCuenta().getInstitucion().setId(3);
		criterios.getCuenta().getInstitucion().setFolioInstitucion("003");
		//criterios.getCuenta().setNumeroCuenta("0307");
		criterios.getCuenta().setNumeroCuenta("-1");
		criterios.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
		criterios.getCuenta().getTipoTenencia().setTipoCustodia("V");
		criterios.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_CONTROLADA, ""));
		criterios.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
		criterios.setEmision(new EmisionDTO());
		criterios.getEmision().setTipoValor(new TipoValorDTO());
		criterios.getEmision().getTipoValor().setId(-1L);
		// criterios.getEmision().getTipoValor().setId(22L);

		EstadoCuentaPosicionDAOV2 estadoCuentaPosicionDAOV2 = (EstadoCuentaPosicionDAOV2) applicationContext.getBean("estadoCuentaPosicionDAOV2");

		Calendar c = GregorianCalendar.getInstance();
		
		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);
		
		List<PosicionDTO> posiciones = estadoCuentaPosicionDAOV2.buscarPosicionesControladasHistoricas(criterios, c.getTime(), Arrays.asList(new Long[]{1144L,1162L,1165L,1181L}));

		assertTrue(posiciones != null && !posiciones.isEmpty());
	}
	
	/**
	 * Prueba unitaria para el método buscarPosicionesNombradas
	 */
	public void testBuscarPosicionesNombradas() {
		PosicionDTO criterios = new PosicionDTO();

		criterios.setCuenta(new CuentaDTO());
		criterios.getCuenta().setInstitucion(new InstitucionDTO());
		criterios.getCuenta().getInstitucion().setClaveTipoInstitucion("01");
		criterios.getCuenta().getInstitucion().setId(3);
		criterios.getCuenta().getInstitucion().setFolioInstitucion("003");
		//criterios.getCuenta().setNumeroCuenta("0307");
		criterios.getCuenta().setNumeroCuenta("-1");
		criterios.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
		criterios.getCuenta().getTipoTenencia().setTipoCustodia("V");
		criterios.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA, ""));
		criterios.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
		criterios.setEmision(new EmisionDTO());
		criterios.getEmision().setTipoValor(new TipoValorDTO());
		criterios.getEmision().getTipoValor().setId(-1L);
		// criterios.getEmision().getTipoValor().setId(22L);

		EstadoCuentaPosicionDAOV2 estadoCuentaPosicionDAOV2 = (EstadoCuentaPosicionDAOV2) applicationContext.getBean("estadoCuentaPosicionDAOV2");

		List<PosicionDTO> posiciones = estadoCuentaPosicionDAOV2.buscarPosicionesNombradas(criterios, Arrays.asList(new Long[]{1144L,1162L,1165L,1181L}));

		assertTrue(posiciones != null && !posiciones.isEmpty());
	}
	
	/**
	 * Prueba unitaria para el método buscarPosicionesControladasHistoricas
	 */
	public void testBuscarPosicionesNombradasHistoricas() {
		PosicionDTO criterios = new PosicionDTO();

		criterios.setCuenta(new CuentaDTO());
		criterios.getCuenta().setInstitucion(new InstitucionDTO());
		criterios.getCuenta().getInstitucion().setClaveTipoInstitucion("01");
		criterios.getCuenta().getInstitucion().setId(3);
		criterios.getCuenta().getInstitucion().setFolioInstitucion("003");
		//criterios.getCuenta().setNumeroCuenta("0307");
		criterios.getCuenta().setNumeroCuenta("-1");
		criterios.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
		criterios.getCuenta().getTipoTenencia().setTipoCustodia("V");
		criterios.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA, ""));
		criterios.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
		criterios.setEmision(new EmisionDTO());
		criterios.getEmision().setTipoValor(new TipoValorDTO());
		criterios.getEmision().getTipoValor().setId(-1L);
		// criterios.getEmision().getTipoValor().setId(22L);

		EstadoCuentaPosicionDAOV2 estadoCuentaPosicionDAOV2 = (EstadoCuentaPosicionDAOV2) applicationContext.getBean("estadoCuentaPosicionDAOV2");

		Calendar c = GregorianCalendar.getInstance();
		
		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);
		
		List<PosicionDTO> posiciones = estadoCuentaPosicionDAOV2.buscarPosicionesNombradasHistoricas(criterios, c.getTime(), Arrays.asList(new Long[]{1144L,1162L,1165L,1181L}));

		assertTrue(posiciones != null && !posiciones.isEmpty());
	}
	
	/**
	 * Prueba unitaria para el método buscarRegistrosContablesControladas
	 */
	public void testBuscarRegistrosContablesControladas() {
		PosicionDTO criterios = new PosicionDTO();

		criterios.setCuenta(new CuentaDTO());
		criterios.getCuenta().setInstitucion(new InstitucionDTO());
		criterios.getCuenta().getInstitucion().setClaveTipoInstitucion("01");
		criterios.getCuenta().getInstitucion().setId(3);
		criterios.getCuenta().getInstitucion().setFolioInstitucion("003");
		//criterios.getCuenta().setNumeroCuenta("0307");
		criterios.getCuenta().setNumeroCuenta("-1");
		criterios.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
		criterios.getCuenta().getTipoTenencia().setTipoCustodia("V");
		criterios.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_CONTROLADA, ""));
		criterios.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
		criterios.setEmision(new EmisionDTO());
		criterios.getEmision().setTipoValor(new TipoValorDTO());
		criterios.getEmision().getTipoValor().setId(-1L);
		// criterios.getEmision().getTipoValor().setId(22L);

		EstadoCuentaPosicionDAOV2 estadoCuentaPosicionDAOV2 = (EstadoCuentaPosicionDAOV2) applicationContext.getBean("estadoCuentaPosicionDAOV2");
		
		Calendar c = GregorianCalendar.getInstance();
		
		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);
		
		criterios.setFechaInicio(c.getTime());
		criterios.setFechaFinal(new Date());
		
		List<RegistroContablePosicionControladaDTO> registros = estadoCuentaPosicionDAOV2.buscarRegistrosContablesControladas(criterios, Arrays.asList(new Long[]{1144L,1162L,1165L,1181L}));

		assertTrue(registros != null && !registros.isEmpty());
	}
	
	/**
	 * Prueba unitaria para el método buscarRegistrosContablesControladasHistoricos
	 */
	public void testBuscarRegistrosContablesControladasHistoricos() {
		PosicionDTO criterios = new PosicionDTO();

		criterios.setCuenta(new CuentaDTO());
		criterios.getCuenta().setInstitucion(new InstitucionDTO());
		criterios.getCuenta().getInstitucion().setClaveTipoInstitucion("01");
		criterios.getCuenta().getInstitucion().setId(3);
		criterios.getCuenta().getInstitucion().setFolioInstitucion("003");
		//criterios.getCuenta().setNumeroCuenta("0307");
		criterios.getCuenta().setNumeroCuenta("-1");
		criterios.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
		criterios.getCuenta().getTipoTenencia().setTipoCustodia("V");
		criterios.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_CONTROLADA, ""));
		criterios.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
		criterios.setEmision(new EmisionDTO());
		criterios.getEmision().setTipoValor(new TipoValorDTO());
		criterios.getEmision().getTipoValor().setId(-1L);
		// criterios.getEmision().getTipoValor().setId(22L);

		EstadoCuentaPosicionDAOV2 estadoCuentaPosicionDAOV2 = (EstadoCuentaPosicionDAOV2) applicationContext.getBean("estadoCuentaPosicionDAOV2");

		Calendar c = GregorianCalendar.getInstance();
		
		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);
		
		criterios.setFechaInicio(c.getTime());
		criterios.setFechaFinal(new Date());
		
		List<RegistroContablePosicionControladaDTO> registros = estadoCuentaPosicionDAOV2.buscarRegistrosContablesControladasHistoricos(criterios, Arrays.asList(new Long[]{1144L,1162L,1165L,1181L}));

		assertTrue(registros != null && !registros.isEmpty());
	}
	
	/**
	 * Prueba unitaria para el método buscarRegistrosContablesNombradas
	 */
	public void testBuscarRegistrosContablesNombradas() {
		PosicionDTO criterios = new PosicionDTO();

		criterios.setCuenta(new CuentaDTO());
		criterios.getCuenta().setInstitucion(new InstitucionDTO());
		criterios.getCuenta().getInstitucion().setClaveTipoInstitucion("01");
		criterios.getCuenta().getInstitucion().setId(3);
		criterios.getCuenta().getInstitucion().setFolioInstitucion("003");
		//criterios.getCuenta().setNumeroCuenta("0307");
		criterios.getCuenta().setNumeroCuenta("-1");
		criterios.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
		criterios.getCuenta().getTipoTenencia().setTipoCustodia("V");
		criterios.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA, ""));
		criterios.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
		criterios.setEmision(new EmisionDTO());
		criterios.getEmision().setTipoValor(new TipoValorDTO());
		criterios.getEmision().getTipoValor().setId(-1L);
		// criterios.getEmision().getTipoValor().setId(22L);

		EstadoCuentaPosicionDAOV2 estadoCuentaPosicionDAOV2 = (EstadoCuentaPosicionDAOV2) applicationContext.getBean("estadoCuentaPosicionDAOV2");
		
		Calendar c = GregorianCalendar.getInstance();
		
		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);
		
		criterios.setFechaInicio(c.getTime());
		criterios.setFechaFinal(new Date());
		
		List<RegistroContablePosicionNombradaDTO> registros = estadoCuentaPosicionDAOV2.buscarRegistrosContablesNombradas(criterios, Arrays.asList(new Long[]{1144L,1162L,1165L,1181L}));

		assertTrue(registros != null && !registros.isEmpty());
	}
	
	/**
	 * Prueba unitaria para el método buscarRegistrosContablesNombradasHistoricos
	 */
	public void testBuscarRegistrosContablesNombradasHistoricos() {
		PosicionDTO criterios = new PosicionDTO();

		criterios.setCuenta(new CuentaDTO());
		criterios.getCuenta().setInstitucion(new InstitucionDTO());
		criterios.getCuenta().getInstitucion().setClaveTipoInstitucion("01");
		criterios.getCuenta().getInstitucion().setId(3);
		criterios.getCuenta().getInstitucion().setFolioInstitucion("003");
		//criterios.getCuenta().setNumeroCuenta("0307");
		criterios.getCuenta().setNumeroCuenta("-1");
		criterios.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
		criterios.getCuenta().getTipoTenencia().setTipoCustodia("V");
		criterios.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA, ""));
		criterios.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
		criterios.setEmision(new EmisionDTO());
		criterios.getEmision().setTipoValor(new TipoValorDTO());
		criterios.getEmision().getTipoValor().setId(-1L);
		// criterios.getEmision().getTipoValor().setId(22L);

		EstadoCuentaPosicionDAOV2 estadoCuentaPosicionDAOV2 = (EstadoCuentaPosicionDAOV2) applicationContext.getBean("estadoCuentaPosicionDAOV2");

		Calendar c = GregorianCalendar.getInstance();
		
		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);
		
		criterios.setFechaInicio(c.getTime());
		criterios.setFechaFinal(new Date());
		
		List<RegistroContablePosicionNombradaDTO> registros = estadoCuentaPosicionDAOV2.buscarRegistrosContablesNombradasHistoricos(criterios, Arrays.asList(new Long[]{1144L,1162L,1165L,1181L}));

		assertTrue(registros != null && !registros.isEmpty());
	}
	
	/**
	 * Prueba unitaria para el método obtenerProyeccionRegistrosContablesControladas
	 */
	public void testObtenerProyeccionRegistrosContablesControladas() {
		PosicionDTO criterios = new PosicionDTO();

		criterios.setCuenta(new CuentaDTO());
		criterios.getCuenta().setInstitucion(new InstitucionDTO());
		criterios.getCuenta().getInstitucion().setClaveTipoInstitucion("01");
		criterios.getCuenta().getInstitucion().setId(3);
		criterios.getCuenta().getInstitucion().setFolioInstitucion("003");
		//criterios.getCuenta().setNumeroCuenta("0307");
		criterios.getCuenta().setNumeroCuenta("-1");
		criterios.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
		criterios.getCuenta().getTipoTenencia().setTipoCustodia("V");
		criterios.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_CONTROLADA, ""));
		criterios.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
		criterios.setEmision(new EmisionDTO());
		criterios.getEmision().setTipoValor(new TipoValorDTO());
		criterios.getEmision().getTipoValor().setId(-1L);
		// criterios.getEmision().getTipoValor().setId(22L);

		EstadoCuentaPosicionDAOV2 estadoCuentaPosicionDAOV2 = (EstadoCuentaPosicionDAOV2) applicationContext.getBean("estadoCuentaPosicionDAOV2");

		Calendar c = GregorianCalendar.getInstance();
		
		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);
		
		criterios.setFechaInicio(c.getTime());
		criterios.setFechaFinal(new Date());
		
		Long registros = estadoCuentaPosicionDAOV2.obtenerProyeccionRegistrosContablesControladas(criterios);
		
		assertTrue(registros != null && registros > 0);
	}
	
	/**
	 * Prueba unitaria para el método obtenerProyeccionRegistrosContablesControladasHistorico
	 */
	public void testObtenerProyeccionRegistrosContablesControladasHistorico() {
		PosicionDTO criterios = new PosicionDTO();

		criterios.setCuenta(new CuentaDTO());
		criterios.getCuenta().setInstitucion(new InstitucionDTO());
		criterios.getCuenta().getInstitucion().setClaveTipoInstitucion("01");
		criterios.getCuenta().getInstitucion().setId(3);
		criterios.getCuenta().getInstitucion().setFolioInstitucion("003");
		//criterios.getCuenta().setNumeroCuenta("0307");
		criterios.getCuenta().setNumeroCuenta("-1");
		criterios.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
		criterios.getCuenta().getTipoTenencia().setTipoCustodia("V");
		criterios.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_CONTROLADA, ""));
		criterios.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
		criterios.setEmision(new EmisionDTO());
		criterios.getEmision().setTipoValor(new TipoValorDTO());
		criterios.getEmision().getTipoValor().setId(-1L);
		// criterios.getEmision().getTipoValor().setId(22L);

		EstadoCuentaPosicionDAOV2 estadoCuentaPosicionDAOV2 = (EstadoCuentaPosicionDAOV2) applicationContext.getBean("estadoCuentaPosicionDAOV2");

		Calendar c = GregorianCalendar.getInstance();
		
		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);
		
		criterios.setFechaInicio(c.getTime());
		criterios.setFechaFinal(new Date());
		
		Long registros = estadoCuentaPosicionDAOV2.obtenerProyeccionRegistrosContablesControladasHistorico(criterios);
		
		assertTrue(registros != null && registros > 0);
	}
	
	/**
	 * Prueba unitaria para el método obtenerProyeccionRegistrosContablesNombradas
	 */
	public void testObtenerProyeccionRegistrosContablesNombradas() {
		PosicionDTO criterios = new PosicionDTO();

		criterios.setCuenta(new CuentaDTO());
		criterios.getCuenta().setInstitucion(new InstitucionDTO());
		criterios.getCuenta().getInstitucion().setClaveTipoInstitucion("01");
		criterios.getCuenta().getInstitucion().setId(3);
		criterios.getCuenta().getInstitucion().setFolioInstitucion("003");
		//criterios.getCuenta().setNumeroCuenta("0307");
		criterios.getCuenta().setNumeroCuenta("-1");
		criterios.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
		criterios.getCuenta().getTipoTenencia().setTipoCustodia("V");
		criterios.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA, ""));
		criterios.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
		criterios.setEmision(new EmisionDTO());
		criterios.getEmision().setTipoValor(new TipoValorDTO());
		criterios.getEmision().getTipoValor().setId(-1L);
		// criterios.getEmision().getTipoValor().setId(22L);

		EstadoCuentaPosicionDAOV2 estadoCuentaPosicionDAOV2 = (EstadoCuentaPosicionDAOV2) applicationContext.getBean("estadoCuentaPosicionDAOV2");

		Calendar c = GregorianCalendar.getInstance();
		
		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);
		
		criterios.setFechaInicio(c.getTime());
		criterios.setFechaFinal(new Date());
		
		Long registros = estadoCuentaPosicionDAOV2.obtenerProyeccionRegistrosContablesNombradas(criterios);
		
		assertTrue(registros != null && registros > 0);
	}
	
	/**
	 * Prueba unitaria para el método obtenerProyeccionRegistrosContablesNombradasHistorico
	 */
	public void testObtenerProyeccionRegistrosContablesNombradasHistorico() {
		PosicionDTO criterios = new PosicionDTO();

		criterios.setCuenta(new CuentaDTO());
		criterios.getCuenta().setInstitucion(new InstitucionDTO());
		criterios.getCuenta().getInstitucion().setClaveTipoInstitucion("01");
		criterios.getCuenta().getInstitucion().setId(3);
		criterios.getCuenta().getInstitucion().setFolioInstitucion("003");
		//criterios.getCuenta().setNumeroCuenta("0307");
		criterios.getCuenta().setNumeroCuenta("-1");
		criterios.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
		criterios.getCuenta().getTipoTenencia().setTipoCustodia("V");
		criterios.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA, ""));
		criterios.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
		criterios.setEmision(new EmisionDTO());
		criterios.getEmision().setTipoValor(new TipoValorDTO());
		criterios.getEmision().getTipoValor().setId(-1L);
		// criterios.getEmision().getTipoValor().setId(22L);

		EstadoCuentaPosicionDAOV2 estadoCuentaPosicionDAOV2 = (EstadoCuentaPosicionDAOV2) applicationContext.getBean("estadoCuentaPosicionDAOV2");

		Calendar c = GregorianCalendar.getInstance();
		
		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);
		
		criterios.setFechaInicio(c.getTime());
		criterios.setFechaFinal(new Date());
		
		Long registros = estadoCuentaPosicionDAOV2.obtenerProyeccionRegistrosContablesNombradasHistorico(criterios);
		
		assertTrue(registros != null && registros > 0);
	}
	
	

}
