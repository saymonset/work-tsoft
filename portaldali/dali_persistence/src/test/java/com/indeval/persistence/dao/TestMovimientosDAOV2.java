/**
 * Sistema Portal DALI
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Nov 25, 2008
 */
package com.indeval.persistence.dao;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TreeSet;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.EmisoraDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.MercadoDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionControladaDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionNombradaDTO;
import com.indeval.portaldali.middleware.dto.SerieDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.TipoOperacionDTO;
import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.persistence.dao.estadocuenta.MovimientosValorDAOV2;

/**
 * Prueba unitaria para la clase {@link MovimientosValorDAOV2}
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public class TestMovimientosDAOV2 extends BaseDaoTestCase {

	/**
	 * Prueba unitaria para el método buscarCuentasDePosicionesControladas
	 */
	public void testBuscarCuentasDePosicionesControladas() {

		MovimientosValorDAOV2 movimientosValorDAOV2 = (MovimientosValorDAOV2) applicationContext.getBean("movimientosValorDAOV2");

		CriterioConsultaMovimientosValorDTO criterio = new CriterioConsultaMovimientosValorDTO();

		CuentaDTO cuenta = new CuentaDTO();
		CuentaDTO cuentaContraparte = new CuentaDTO();
		InstitucionDTO institucion = new InstitucionDTO();
		BovedaDTO boveda = new BovedaDTO();
		EmisionDTO emision = new EmisionDTO();
		TipoTenenciaDTO tipoTenencia = new TipoTenenciaDTO();
		TipoNaturalezaDTO tipoNaturaleza = new TipoNaturalezaDTO();
		Calendar c = GregorianCalendar.getInstance();

		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);

		criterio.setFechaInicial(c.getTime());
		criterio.setFechaFinal(new Date());

		institucion.setId(4);
		cuenta.setInstitucion(institucion);
		cuenta.setNumeroCuenta("-1");

		tipoTenencia.setIdTipoCuenta(-1);
		cuenta.setTipoTenencia(tipoTenencia);

		TipoCuentaDTO tipoCuenta = new TipoCuentaDTO();
		tipoCuenta.setId("C");
		tipoTenencia.setTipoCuenta(tipoCuenta);

		tipoNaturaleza.setId("P");
		tipoTenencia.setTipoCustodia("V");
		tipoTenencia.setTipoNaturaleza(tipoNaturaleza);
		boveda.setId(-1);
		cuentaContraparte.setTipoTenencia(tipoTenencia);
		cuentaContraparte.setNumeroCuenta("-1");
		InstitucionDTO institucionContraparte = new InstitucionDTO();
		institucionContraparte.setId(-1);
		cuentaContraparte.setInstitucion(institucionContraparte);
		criterio.setCuenta(cuenta);
		criterio.setBoveda(boveda);

		EmisoraDTO emisora = new EmisoraDTO();
		SerieDTO serie = new SerieDTO();
		TipoValorDTO tipoValor = new TipoValorDTO();
		MercadoDTO mercado = new MercadoDTO();

		mercado.setId(-1);
		tipoValor.setId(-1);
		tipoValor.setMercado(mercado);
		emisora.setId(-1);
		serie.setEmisora(emisora);

		serie.setTipoValor(tipoValor);
		emision.setId(-1);
		emision.setSerie(serie);
		emision.setEmisora(emisora);
		emision.setTipoValor(tipoValor);

		criterio.setEmision(emision);

		TipoInstruccionDTO tipoInstruccion = new TipoInstruccionDTO();
		tipoInstruccion.setIdTipoInstruccion(-1L);

		TipoOperacionDTO tipoOperacion = new TipoOperacionDTO();
		tipoOperacion.setId(-1);

		criterio.setFolioInstruccion(null);
		criterio.setTipoInstruccion(tipoInstruccion);
		criterio.setTipoOperacion(tipoOperacion);
		
		List<Long> idsCuentas = movimientosValorDAOV2.buscarCuentasDePosicionesControladas(criterio);

		assertTrue(idsCuentas != null && idsCuentas.size() > 0);
	}

	/**
	 * Prueba unitaria para el método buscarCuentasDePosicionesNombradas
	 */
	public void testBuscarCuentasDePosicionesNombradas() {

		MovimientosValorDAOV2 movimientosValorDAOV2 = (MovimientosValorDAOV2) applicationContext.getBean("movimientosValorDAOV2");

		CriterioConsultaMovimientosValorDTO criterio = new CriterioConsultaMovimientosValorDTO();

		CuentaDTO cuenta = new CuentaDTO();
		CuentaDTO cuentaContraparte = new CuentaDTO();
		InstitucionDTO institucion = new InstitucionDTO();
		BovedaDTO boveda = new BovedaDTO();
		EmisionDTO emision = new EmisionDTO();
		TipoTenenciaDTO tipoTenencia = new TipoTenenciaDTO();
		TipoNaturalezaDTO tipoNaturaleza = new TipoNaturalezaDTO();
		Calendar c = GregorianCalendar.getInstance();

		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);

		criterio.setFechaInicial(c.getTime());
		criterio.setFechaFinal(new Date());

		institucion.setId(4);
		cuenta.setInstitucion(institucion);
		cuenta.setNumeroCuenta("-1");

		tipoTenencia.setIdTipoCuenta(-1);
		cuenta.setTipoTenencia(tipoTenencia);

		TipoCuentaDTO tipoCuenta = new TipoCuentaDTO();
		tipoCuenta.setId("N");
		tipoTenencia.setTipoCuenta(tipoCuenta);

		tipoNaturaleza.setId("P");
		tipoTenencia.setTipoCustodia("V");
		tipoTenencia.setTipoNaturaleza(tipoNaturaleza);
		boveda.setId(-1);
		cuentaContraparte.setTipoTenencia(tipoTenencia);
		cuentaContraparte.setNumeroCuenta("-1");
		InstitucionDTO institucionContraparte = new InstitucionDTO();
		institucionContraparte.setId(-1);
		cuentaContraparte.setInstitucion(institucionContraparte);
		criterio.setCuenta(cuenta);
		criterio.setBoveda(boveda);

		EmisoraDTO emisora = new EmisoraDTO();
		SerieDTO serie = new SerieDTO();
		TipoValorDTO tipoValor = new TipoValorDTO();
		MercadoDTO mercado = new MercadoDTO();

		mercado.setId(-1);
		tipoValor.setId(-1);
		tipoValor.setMercado(mercado);
		emisora.setId(-1);
		serie.setEmisora(emisora);

		serie.setTipoValor(tipoValor);
		emision.setId(-1);
		emision.setSerie(serie);
		emision.setEmisora(emisora);
		emision.setTipoValor(tipoValor);

		criterio.setEmision(emision);

		TipoInstruccionDTO tipoInstruccion = new TipoInstruccionDTO();
		tipoInstruccion.setIdTipoInstruccion(-1L);

		TipoOperacionDTO tipoOperacion = new TipoOperacionDTO();
		tipoOperacion.setId(-1);

		criterio.setFolioInstruccion(null);
		criterio.setTipoInstruccion(tipoInstruccion);
		criterio.setTipoOperacion(tipoOperacion);

		criterio.setCuentaContraparte(cuentaContraparte);

		criterio.getCuenta().getTipoTenencia().getTipoCuenta().setId("N");
		criterio.getCuenta().getTipoTenencia().setIdTipoCuenta(-1);

		List<Long> idsCuentas = movimientosValorDAOV2.buscarCuentasDePosicionesNombradas(criterio);

		assertTrue(idsCuentas != null && idsCuentas.size() > 0);
	}
	
	/**
	 * Prueba unitaria para el método buscarCuentasDePosicionesControladasHistoricas
	 */
	public void testBuscarCuentasDePosicionesControladasHistoricas() {

		MovimientosValorDAOV2 movimientosValorDAOV2 = (MovimientosValorDAOV2) applicationContext.getBean("movimientosValorDAOV2");

		CriterioConsultaMovimientosValorDTO criterio = new CriterioConsultaMovimientosValorDTO();

		CuentaDTO cuenta = new CuentaDTO();
		CuentaDTO cuentaContraparte = new CuentaDTO();
		InstitucionDTO institucion = new InstitucionDTO();
		BovedaDTO boveda = new BovedaDTO();
		EmisionDTO emision = new EmisionDTO();
		TipoTenenciaDTO tipoTenencia = new TipoTenenciaDTO();
		TipoNaturalezaDTO tipoNaturaleza = new TipoNaturalezaDTO();
		Calendar c = GregorianCalendar.getInstance();

		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);

		criterio.setFechaInicial(c.getTime());
		criterio.setFechaFinal(new Date());

		institucion.setId(4);
		cuenta.setInstitucion(institucion);
		cuenta.setNumeroCuenta("-1");

		tipoTenencia.setIdTipoCuenta(-1);
		cuenta.setTipoTenencia(tipoTenencia);

		TipoCuentaDTO tipoCuenta = new TipoCuentaDTO();
		tipoCuenta.setId("C");
		tipoTenencia.setTipoCuenta(tipoCuenta);

		tipoNaturaleza.setId("P");
		tipoTenencia.setTipoCustodia("V");
		tipoTenencia.setTipoNaturaleza(tipoNaturaleza);
		boveda.setId(-1);
		cuentaContraparte.setTipoTenencia(tipoTenencia);
		cuentaContraparte.setNumeroCuenta("-1");
		InstitucionDTO institucionContraparte = new InstitucionDTO();
		institucionContraparte.setId(-1);
		cuentaContraparte.setInstitucion(institucionContraparte);
		criterio.setCuenta(cuenta);
		criterio.setBoveda(boveda);

		EmisoraDTO emisora = new EmisoraDTO();
		SerieDTO serie = new SerieDTO();
		TipoValorDTO tipoValor = new TipoValorDTO();
		MercadoDTO mercado = new MercadoDTO();

		mercado.setId(-1);
		tipoValor.setId(-1);
		tipoValor.setMercado(mercado);
		emisora.setId(-1);
		serie.setEmisora(emisora);

		serie.setTipoValor(tipoValor);
		emision.setId(-1);
		emision.setSerie(serie);
		emision.setEmisora(emisora);
		emision.setTipoValor(tipoValor);

		criterio.setEmision(emision);

		TipoInstruccionDTO tipoInstruccion = new TipoInstruccionDTO();
		tipoInstruccion.setIdTipoInstruccion(-1L);

		TipoOperacionDTO tipoOperacion = new TipoOperacionDTO();
		tipoOperacion.setId(-1);

		criterio.setFolioInstruccion(null);
		criterio.setTipoInstruccion(tipoInstruccion);
		criterio.setTipoOperacion(tipoOperacion);
		
		List<Long> idsCuentas = movimientosValorDAOV2.buscarCuentasDePosicionesControladasHistoricas(criterio, c.getTime());

		assertTrue(idsCuentas != null && idsCuentas.size() > 0);
	}

	/**
	 * Prueba unitaria para el método buscarCuentasDePosicionesNombradasHistoricas
	 */
	public void testBuscarCuentasDePosicionesNombradasHistoricas() {

		MovimientosValorDAOV2 movimientosValorDAOV2 = (MovimientosValorDAOV2) applicationContext.getBean("movimientosValorDAOV2");

		CriterioConsultaMovimientosValorDTO criterio = new CriterioConsultaMovimientosValorDTO();

		CuentaDTO cuenta = new CuentaDTO();
		CuentaDTO cuentaContraparte = new CuentaDTO();
		InstitucionDTO institucion = new InstitucionDTO();
		BovedaDTO boveda = new BovedaDTO();
		EmisionDTO emision = new EmisionDTO();
		TipoTenenciaDTO tipoTenencia = new TipoTenenciaDTO();
		TipoNaturalezaDTO tipoNaturaleza = new TipoNaturalezaDTO();
		Calendar c = GregorianCalendar.getInstance();

		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);

		criterio.setFechaInicial(c.getTime());
		criterio.setFechaFinal(new Date());

		institucion.setId(4);
		cuenta.setInstitucion(institucion);
		cuenta.setNumeroCuenta("-1");

		tipoTenencia.setIdTipoCuenta(-1);
		cuenta.setTipoTenencia(tipoTenencia);

		TipoCuentaDTO tipoCuenta = new TipoCuentaDTO();
		tipoCuenta.setId("N");
		tipoTenencia.setTipoCuenta(tipoCuenta);

		tipoNaturaleza.setId("P");
		tipoTenencia.setTipoCustodia("V");
		tipoTenencia.setTipoNaturaleza(tipoNaturaleza);
		boveda.setId(-1);
		cuentaContraparte.setTipoTenencia(tipoTenencia);
		cuentaContraparte.setNumeroCuenta("-1");
		InstitucionDTO institucionContraparte = new InstitucionDTO();
		institucionContraparte.setId(-1);
		cuentaContraparte.setInstitucion(institucionContraparte);
		criterio.setCuenta(cuenta);
		criterio.setBoveda(boveda);

		EmisoraDTO emisora = new EmisoraDTO();
		SerieDTO serie = new SerieDTO();
		TipoValorDTO tipoValor = new TipoValorDTO();
		MercadoDTO mercado = new MercadoDTO();

		mercado.setId(-1);
		tipoValor.setId(-1);
		tipoValor.setMercado(mercado);
		emisora.setId(-1);
		serie.setEmisora(emisora);

		serie.setTipoValor(tipoValor);
		emision.setId(-1);
		emision.setSerie(serie);
		emision.setEmisora(emisora);
		emision.setTipoValor(tipoValor);

		criterio.setEmision(emision);

		TipoInstruccionDTO tipoInstruccion = new TipoInstruccionDTO();
		tipoInstruccion.setIdTipoInstruccion(-1L);

		TipoOperacionDTO tipoOperacion = new TipoOperacionDTO();
		tipoOperacion.setId(-1);

		criterio.setFolioInstruccion(null);
		criterio.setTipoInstruccion(tipoInstruccion);
		criterio.setTipoOperacion(tipoOperacion);

		criterio.setCuentaContraparte(cuentaContraparte);

		criterio.getCuenta().getTipoTenencia().getTipoCuenta().setId("N");
		criterio.getCuenta().getTipoTenencia().setIdTipoCuenta(-1);

		List<Long> idsCuentas = movimientosValorDAOV2.buscarCuentasDePosicionesNombradasHistoricas(criterio, c.getTime());

		assertTrue(idsCuentas != null && idsCuentas.size() > 0);
	}
	
	/**
	 * Prueba unitaria para el método buscarEmisionesDePosicionesControladas
	 */
	public void testBuscarEmisionesDePosicionesControladas() {

		MovimientosValorDAOV2 movimientosValorDAOV2 = (MovimientosValorDAOV2) applicationContext.getBean("movimientosValorDAOV2");

		CriterioConsultaMovimientosValorDTO criterio = new CriterioConsultaMovimientosValorDTO();

		CuentaDTO cuenta = new CuentaDTO();
		CuentaDTO cuentaContraparte = new CuentaDTO();
		InstitucionDTO institucion = new InstitucionDTO();
		BovedaDTO boveda = new BovedaDTO();
		EmisionDTO emision = new EmisionDTO();
		TipoTenenciaDTO tipoTenencia = new TipoTenenciaDTO();
		TipoNaturalezaDTO tipoNaturaleza = new TipoNaturalezaDTO();
		Calendar c = GregorianCalendar.getInstance();

		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);

		criterio.setFechaInicial(c.getTime());
		criterio.setFechaFinal(new Date());

		institucion.setId(4);
		cuenta.setInstitucion(institucion);
		cuenta.setNumeroCuenta("-1");

		tipoTenencia.setIdTipoCuenta(-1);
		cuenta.setTipoTenencia(tipoTenencia);

		TipoCuentaDTO tipoCuenta = new TipoCuentaDTO();
		tipoCuenta.setId("C");
		tipoTenencia.setTipoCuenta(tipoCuenta);

		tipoNaturaleza.setId("P");
		tipoTenencia.setTipoCustodia("V");
		tipoTenencia.setTipoNaturaleza(tipoNaturaleza);
		boveda.setId(-1);
		cuentaContraparte.setTipoTenencia(tipoTenencia);
		cuentaContraparte.setNumeroCuenta("-1");
		InstitucionDTO institucionContraparte = new InstitucionDTO();
		institucionContraparte.setId(-1);
		cuentaContraparte.setInstitucion(institucionContraparte);
		criterio.setCuenta(cuenta);
		criterio.setBoveda(boveda);

		EmisoraDTO emisora = new EmisoraDTO();
		SerieDTO serie = new SerieDTO();
		TipoValorDTO tipoValor = new TipoValorDTO();
		MercadoDTO mercado = new MercadoDTO();

		mercado.setId(-1);
		tipoValor.setId(-1);
		tipoValor.setMercado(mercado);
		emisora.setId(-1);
		serie.setEmisora(emisora);

		serie.setTipoValor(tipoValor);
		emision.setId(-1);
		emision.setSerie(serie);
		emision.setEmisora(emisora);
		emision.setTipoValor(tipoValor);

		criterio.setEmision(emision);

		TipoInstruccionDTO tipoInstruccion = new TipoInstruccionDTO();
		tipoInstruccion.setIdTipoInstruccion(-1L);

		TipoOperacionDTO tipoOperacion = new TipoOperacionDTO();
		tipoOperacion.setId(-1);

		criterio.setFolioInstruccion(null);
		criterio.setTipoInstruccion(tipoInstruccion);
		criterio.setTipoOperacion(tipoOperacion);
		
		List<Long> idsEmisiones = movimientosValorDAOV2.buscarEmisionesDePosicionesControladas(criterio);

		assertTrue(idsEmisiones != null && idsEmisiones.size() > 0);
	}

	/**
	 * Prueba unitaria para el método buscarEmisionesDePosicionesNombradas
	 */
	public void testBuscarEmisionesDePosicionesNombradas() {

		MovimientosValorDAOV2 movimientosValorDAOV2 = (MovimientosValorDAOV2) applicationContext.getBean("movimientosValorDAOV2");

		CriterioConsultaMovimientosValorDTO criterio = new CriterioConsultaMovimientosValorDTO();

		CuentaDTO cuenta = new CuentaDTO();
		CuentaDTO cuentaContraparte = new CuentaDTO();
		InstitucionDTO institucion = new InstitucionDTO();
		BovedaDTO boveda = new BovedaDTO();
		EmisionDTO emision = new EmisionDTO();
		TipoTenenciaDTO tipoTenencia = new TipoTenenciaDTO();
		TipoNaturalezaDTO tipoNaturaleza = new TipoNaturalezaDTO();
		Calendar c = GregorianCalendar.getInstance();

		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);

		criterio.setFechaInicial(c.getTime());
		criterio.setFechaFinal(new Date());

		institucion.setId(4);
		cuenta.setInstitucion(institucion);
		cuenta.setNumeroCuenta("-1");

		tipoTenencia.setIdTipoCuenta(-1);
		cuenta.setTipoTenencia(tipoTenencia);

		TipoCuentaDTO tipoCuenta = new TipoCuentaDTO();
		tipoCuenta.setId("N");
		tipoTenencia.setTipoCuenta(tipoCuenta);

		tipoNaturaleza.setId("P");
		tipoTenencia.setTipoCustodia("V");
		tipoTenencia.setTipoNaturaleza(tipoNaturaleza);
		boveda.setId(-1);
		cuentaContraparte.setTipoTenencia(tipoTenencia);
		cuentaContraparte.setNumeroCuenta("-1");
		InstitucionDTO institucionContraparte = new InstitucionDTO();
		institucionContraparte.setId(-1);
		cuentaContraparte.setInstitucion(institucionContraparte);
		criterio.setCuenta(cuenta);
		criterio.setBoveda(boveda);

		EmisoraDTO emisora = new EmisoraDTO();
		SerieDTO serie = new SerieDTO();
		TipoValorDTO tipoValor = new TipoValorDTO();
		MercadoDTO mercado = new MercadoDTO();

		mercado.setId(-1);
		tipoValor.setId(-1);
		tipoValor.setMercado(mercado);
		emisora.setId(-1);
		serie.setEmisora(emisora);

		serie.setTipoValor(tipoValor);
		emision.setId(-1);
		emision.setSerie(serie);
		emision.setEmisora(emisora);
		emision.setTipoValor(tipoValor);

		criterio.setEmision(emision);

		TipoInstruccionDTO tipoInstruccion = new TipoInstruccionDTO();
		tipoInstruccion.setIdTipoInstruccion(-1L);

		TipoOperacionDTO tipoOperacion = new TipoOperacionDTO();
		tipoOperacion.setId(-1);

		criterio.setFolioInstruccion(null);
		criterio.setTipoInstruccion(tipoInstruccion);
		criterio.setTipoOperacion(tipoOperacion);

		criterio.setCuentaContraparte(cuentaContraparte);

		criterio.getCuenta().getTipoTenencia().getTipoCuenta().setId("N");
		criterio.getCuenta().getTipoTenencia().setIdTipoCuenta(-1);

		List<Long> idsEmisiones = movimientosValorDAOV2.buscarEmisionesDePosicionesNombradas(criterio);

		assertTrue(idsEmisiones != null && idsEmisiones.size() > 0);
	}
	
	/**
	 * Prueba unitaria para el método buscarEmisionesDePosicionesControladasHistoricas
	 */
	public void testBuscarEmisionesDePosicionesControladasHistoricas() {

		MovimientosValorDAOV2 movimientosValorDAOV2 = (MovimientosValorDAOV2) applicationContext.getBean("movimientosValorDAOV2");

		CriterioConsultaMovimientosValorDTO criterio = new CriterioConsultaMovimientosValorDTO();

		CuentaDTO cuenta = new CuentaDTO();
		CuentaDTO cuentaContraparte = new CuentaDTO();
		InstitucionDTO institucion = new InstitucionDTO();
		BovedaDTO boveda = new BovedaDTO();
		EmisionDTO emision = new EmisionDTO();
		TipoTenenciaDTO tipoTenencia = new TipoTenenciaDTO();
		TipoNaturalezaDTO tipoNaturaleza = new TipoNaturalezaDTO();
		Calendar c = GregorianCalendar.getInstance();

		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);

		criterio.setFechaInicial(c.getTime());
		criterio.setFechaFinal(new Date());

		institucion.setId(4);
		cuenta.setInstitucion(institucion);
		cuenta.setNumeroCuenta("-1");

		tipoTenencia.setIdTipoCuenta(-1);
		cuenta.setTipoTenencia(tipoTenencia);

		TipoCuentaDTO tipoCuenta = new TipoCuentaDTO();
		tipoCuenta.setId("C");
		tipoTenencia.setTipoCuenta(tipoCuenta);

		tipoNaturaleza.setId("P");
		tipoTenencia.setTipoCustodia("V");
		tipoTenencia.setTipoNaturaleza(tipoNaturaleza);
		boveda.setId(-1);
		cuentaContraparte.setTipoTenencia(tipoTenencia);
		cuentaContraparte.setNumeroCuenta("-1");
		InstitucionDTO institucionContraparte = new InstitucionDTO();
		institucionContraparte.setId(-1);
		cuentaContraparte.setInstitucion(institucionContraparte);
		criterio.setCuenta(cuenta);
		criterio.setBoveda(boveda);

		EmisoraDTO emisora = new EmisoraDTO();
		SerieDTO serie = new SerieDTO();
		TipoValorDTO tipoValor = new TipoValorDTO();
		MercadoDTO mercado = new MercadoDTO();

		mercado.setId(-1);
		tipoValor.setId(-1);
		tipoValor.setMercado(mercado);
		emisora.setId(-1);
		serie.setEmisora(emisora);

		serie.setTipoValor(tipoValor);
		emision.setId(-1);
		emision.setSerie(serie);
		emision.setEmisora(emisora);
		emision.setTipoValor(tipoValor);

		criterio.setEmision(emision);

		TipoInstruccionDTO tipoInstruccion = new TipoInstruccionDTO();
		tipoInstruccion.setIdTipoInstruccion(-1L);

		TipoOperacionDTO tipoOperacion = new TipoOperacionDTO();
		tipoOperacion.setId(-1);

		criterio.setFolioInstruccion(null);
		criterio.setTipoInstruccion(tipoInstruccion);
		criterio.setTipoOperacion(tipoOperacion);
		
		List<Long> idsEmisiones = movimientosValorDAOV2.buscarEmisionesDePosicionesControladasHistoricas(criterio, c.getTime());

		assertTrue(idsEmisiones != null && idsEmisiones.size() > 0);
	}

	/**
	 * Prueba unitaria para el método buscarEmisionesDePosicionesNombradasHistoricas
	 */
	public void testBuscarEmisionesDePosicionesNombradasHistoricas() {

		MovimientosValorDAOV2 movimientosValorDAOV2 = (MovimientosValorDAOV2) applicationContext.getBean("movimientosValorDAOV2");

		CriterioConsultaMovimientosValorDTO criterio = new CriterioConsultaMovimientosValorDTO();

		CuentaDTO cuenta = new CuentaDTO();
		CuentaDTO cuentaContraparte = new CuentaDTO();
		InstitucionDTO institucion = new InstitucionDTO();
		BovedaDTO boveda = new BovedaDTO();
		EmisionDTO emision = new EmisionDTO();
		TipoTenenciaDTO tipoTenencia = new TipoTenenciaDTO();
		TipoNaturalezaDTO tipoNaturaleza = new TipoNaturalezaDTO();
		Calendar c = GregorianCalendar.getInstance();

		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);

		criterio.setFechaInicial(c.getTime());
		criterio.setFechaFinal(new Date());

		institucion.setId(4);
		cuenta.setInstitucion(institucion);
		cuenta.setNumeroCuenta("-1");

		tipoTenencia.setIdTipoCuenta(-1);
		cuenta.setTipoTenencia(tipoTenencia);

		TipoCuentaDTO tipoCuenta = new TipoCuentaDTO();
		tipoCuenta.setId("N");
		tipoTenencia.setTipoCuenta(tipoCuenta);

		tipoNaturaleza.setId("P");
		tipoTenencia.setTipoCustodia("V");
		tipoTenencia.setTipoNaturaleza(tipoNaturaleza);
		boveda.setId(-1);
		cuentaContraparte.setTipoTenencia(tipoTenencia);
		cuentaContraparte.setNumeroCuenta("-1");
		InstitucionDTO institucionContraparte = new InstitucionDTO();
		institucionContraparte.setId(-1);
		cuentaContraparte.setInstitucion(institucionContraparte);
		criterio.setCuenta(cuenta);
		criterio.setBoveda(boveda);

		EmisoraDTO emisora = new EmisoraDTO();
		SerieDTO serie = new SerieDTO();
		TipoValorDTO tipoValor = new TipoValorDTO();
		MercadoDTO mercado = new MercadoDTO();

		mercado.setId(-1);
		tipoValor.setId(-1);
		tipoValor.setMercado(mercado);
		emisora.setId(-1);
		serie.setEmisora(emisora);

		serie.setTipoValor(tipoValor);
		emision.setId(-1);
		emision.setSerie(serie);
		emision.setEmisora(emisora);
		emision.setTipoValor(tipoValor);

		criterio.setEmision(emision);

		TipoInstruccionDTO tipoInstruccion = new TipoInstruccionDTO();
		tipoInstruccion.setIdTipoInstruccion(-1L);

		TipoOperacionDTO tipoOperacion = new TipoOperacionDTO();
		tipoOperacion.setId(-1);

		criterio.setFolioInstruccion(null);
		criterio.setTipoInstruccion(tipoInstruccion);
		criterio.setTipoOperacion(tipoOperacion);

		criterio.setCuentaContraparte(cuentaContraparte);

		criterio.getCuenta().getTipoTenencia().getTipoCuenta().setId("N");
		criterio.getCuenta().getTipoTenencia().setIdTipoCuenta(-1);

		List<Long> idsEmisiones = movimientosValorDAOV2.buscarEmisionesDePosicionesNombradasHistoricas(criterio, c.getTime());

		assertTrue(idsEmisiones != null && idsEmisiones.size() > 0);
	}
	
	/**
	 * Prueba unitaria para el método buscarRegistrosContablesControladas
	 */
	public void testBuscarRegistrosContablesControladas() {

		MovimientosValorDAOV2 movimientosValorDAOV2 = (MovimientosValorDAOV2) applicationContext.getBean("movimientosValorDAOV2");

		CriterioConsultaMovimientosValorDTO criterio = new CriterioConsultaMovimientosValorDTO();

		CuentaDTO cuenta = new CuentaDTO();
		CuentaDTO cuentaContraparte = new CuentaDTO();
		InstitucionDTO institucion = new InstitucionDTO();
		BovedaDTO boveda = new BovedaDTO();
		EmisionDTO emision = new EmisionDTO();
		TipoTenenciaDTO tipoTenencia = new TipoTenenciaDTO();
		TipoNaturalezaDTO tipoNaturaleza = new TipoNaturalezaDTO();
		Calendar c = GregorianCalendar.getInstance();

		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);

		criterio.setFechaInicial(c.getTime());
		criterio.setFechaFinal(new Date());

		institucion.setId(4);
		cuenta.setInstitucion(institucion);
		cuenta.setNumeroCuenta("-1");

		tipoTenencia.setIdTipoCuenta(-1);
		cuenta.setTipoTenencia(tipoTenencia);

		TipoCuentaDTO tipoCuenta = new TipoCuentaDTO();
		tipoCuenta.setId("C");
		tipoTenencia.setTipoCuenta(tipoCuenta);

		tipoNaturaleza.setId("P");
		tipoTenencia.setTipoCustodia("V");
		tipoTenencia.setTipoNaturaleza(tipoNaturaleza);
		boveda.setId(-1);
		cuentaContraparte.setTipoTenencia(tipoTenencia);
		cuentaContraparte.setNumeroCuenta("-1");
		InstitucionDTO institucionContraparte = new InstitucionDTO();
		institucionContraparte.setId(-1);
		cuentaContraparte.setInstitucion(institucionContraparte);
		criterio.setCuenta(cuenta);
		criterio.setBoveda(boveda);

		EmisoraDTO emisora = new EmisoraDTO();
		SerieDTO serie = new SerieDTO();
		TipoValorDTO tipoValor = new TipoValorDTO();
		MercadoDTO mercado = new MercadoDTO();

		mercado.setId(-1);
		tipoValor.setId(-1);
		tipoValor.setMercado(mercado);
		emisora.setId(-1);
		serie.setEmisora(emisora);

		serie.setTipoValor(tipoValor);
		emision.setId(-1);
		emision.setSerie(serie);
		emision.setEmisora(emisora);
		emision.setTipoValor(tipoValor);

		criterio.setEmision(emision);

		TipoInstruccionDTO tipoInstruccion = new TipoInstruccionDTO();
		tipoInstruccion.setIdTipoInstruccion(-1L);

		TipoOperacionDTO tipoOperacion = new TipoOperacionDTO();
		tipoOperacion.setId(-1);

		criterio.setFolioInstruccion(null);
		criterio.setTipoInstruccion(tipoInstruccion);
		criterio.setTipoOperacion(tipoOperacion);
		
		List<RegistroContablePosicionControladaDTO> registros = movimientosValorDAOV2.buscarRegistrosContablesControladas(criterio, new TreeSet<Long>(Arrays.asList(new Long[]{1130L, 1136L, 1137L, 1162L, 1188L, 1191L, 1200L})));

		assertTrue(registros != null && registros.size() > 0);
	}

	/**
	 * Prueba unitaria para el método buscarRegistrosContablesNombradas
	 */
	public void testBuscarRegistrosContablesNombradas() {

		MovimientosValorDAOV2 movimientosValorDAOV2 = (MovimientosValorDAOV2) applicationContext.getBean("movimientosValorDAOV2");

		CriterioConsultaMovimientosValorDTO criterio = new CriterioConsultaMovimientosValorDTO();

		CuentaDTO cuenta = new CuentaDTO();
		CuentaDTO cuentaContraparte = new CuentaDTO();
		InstitucionDTO institucion = new InstitucionDTO();
		BovedaDTO boveda = new BovedaDTO();
		EmisionDTO emision = new EmisionDTO();
		TipoTenenciaDTO tipoTenencia = new TipoTenenciaDTO();
		TipoNaturalezaDTO tipoNaturaleza = new TipoNaturalezaDTO();
		Calendar c = GregorianCalendar.getInstance();

		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);

		criterio.setFechaInicial(c.getTime());
		criterio.setFechaFinal(new Date());

		institucion.setId(4);
		cuenta.setInstitucion(institucion);
		cuenta.setNumeroCuenta("-1");

		tipoTenencia.setIdTipoCuenta(-1);
		cuenta.setTipoTenencia(tipoTenencia);

		TipoCuentaDTO tipoCuenta = new TipoCuentaDTO();
		tipoCuenta.setId("N");
		tipoTenencia.setTipoCuenta(tipoCuenta);

		tipoNaturaleza.setId("P");
		tipoTenencia.setTipoCustodia("V");
		tipoTenencia.setTipoNaturaleza(tipoNaturaleza);
		boveda.setId(-1);
		cuentaContraparte.setTipoTenencia(tipoTenencia);
		cuentaContraparte.setNumeroCuenta("-1");
		InstitucionDTO institucionContraparte = new InstitucionDTO();
		institucionContraparte.setId(-1);
		cuentaContraparte.setInstitucion(institucionContraparte);
		criterio.setCuenta(cuenta);
		criterio.setBoveda(boveda);

		EmisoraDTO emisora = new EmisoraDTO();
		SerieDTO serie = new SerieDTO();
		TipoValorDTO tipoValor = new TipoValorDTO();
		MercadoDTO mercado = new MercadoDTO();

		mercado.setId(-1);
		tipoValor.setId(-1);
		tipoValor.setMercado(mercado);
		emisora.setId(-1);
		serie.setEmisora(emisora);

		serie.setTipoValor(tipoValor);
		emision.setId(-1);
		emision.setSerie(serie);
		emision.setEmisora(emisora);
		emision.setTipoValor(tipoValor);

		criterio.setEmision(emision);

		TipoInstruccionDTO tipoInstruccion = new TipoInstruccionDTO();
		tipoInstruccion.setIdTipoInstruccion(-1L);

		TipoOperacionDTO tipoOperacion = new TipoOperacionDTO();
		tipoOperacion.setId(-1);

		criterio.setFolioInstruccion(null);
		criterio.setTipoInstruccion(tipoInstruccion);
		criterio.setTipoOperacion(tipoOperacion);

		criterio.setCuentaContraparte(cuentaContraparte);

		criterio.getCuenta().getTipoTenencia().getTipoCuenta().setId("N");
		criterio.getCuenta().getTipoTenencia().setIdTipoCuenta(-1);

		List<RegistroContablePosicionNombradaDTO> registros = movimientosValorDAOV2.buscarRegistrosContablesNombradas(criterio, new TreeSet<Long>(Arrays.asList(new Long[]{1130L, 1136L, 1137L, 1162L, 1188L, 1191L, 1200L})));

		assertTrue(registros != null && registros.size() > 0);
	}
	
	/**
	 * Prueba unitaria para el método buscarRegistrosContablesControladasHistoricos
	 */
	public void testBuscarRegistrosContablesControladasHistoricos() {

		MovimientosValorDAOV2 movimientosValorDAOV2 = (MovimientosValorDAOV2) applicationContext.getBean("movimientosValorDAOV2");

		CriterioConsultaMovimientosValorDTO criterio = new CriterioConsultaMovimientosValorDTO();

		CuentaDTO cuenta = new CuentaDTO();
		CuentaDTO cuentaContraparte = new CuentaDTO();
		InstitucionDTO institucion = new InstitucionDTO();
		BovedaDTO boveda = new BovedaDTO();
		EmisionDTO emision = new EmisionDTO();
		TipoTenenciaDTO tipoTenencia = new TipoTenenciaDTO();
		TipoNaturalezaDTO tipoNaturaleza = new TipoNaturalezaDTO();
		Calendar c = GregorianCalendar.getInstance();

		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);

		criterio.setFechaInicial(c.getTime());
		criterio.setFechaFinal(new Date());

		institucion.setId(4);
		cuenta.setInstitucion(institucion);
		cuenta.setNumeroCuenta("-1");

		tipoTenencia.setIdTipoCuenta(-1);
		cuenta.setTipoTenencia(tipoTenencia);

		TipoCuentaDTO tipoCuenta = new TipoCuentaDTO();
		tipoCuenta.setId("C");
		tipoTenencia.setTipoCuenta(tipoCuenta);

		tipoNaturaleza.setId("P");
		tipoTenencia.setTipoCustodia("V");
		tipoTenencia.setTipoNaturaleza(tipoNaturaleza);
		boveda.setId(-1);
		cuentaContraparte.setTipoTenencia(tipoTenencia);
		cuentaContraparte.setNumeroCuenta("-1");
		InstitucionDTO institucionContraparte = new InstitucionDTO();
		institucionContraparte.setId(-1);
		cuentaContraparte.setInstitucion(institucionContraparte);
		criterio.setCuenta(cuenta);
		criterio.setBoveda(boveda);

		EmisoraDTO emisora = new EmisoraDTO();
		SerieDTO serie = new SerieDTO();
		TipoValorDTO tipoValor = new TipoValorDTO();
		MercadoDTO mercado = new MercadoDTO();

		mercado.setId(-1);
		tipoValor.setId(-1);
		tipoValor.setMercado(mercado);
		emisora.setId(-1);
		serie.setEmisora(emisora);

		serie.setTipoValor(tipoValor);
		emision.setId(-1);
		emision.setSerie(serie);
		emision.setEmisora(emisora);
		emision.setTipoValor(tipoValor);

		criterio.setEmision(emision);

		TipoInstruccionDTO tipoInstruccion = new TipoInstruccionDTO();
		tipoInstruccion.setIdTipoInstruccion(-1L);

		TipoOperacionDTO tipoOperacion = new TipoOperacionDTO();
		tipoOperacion.setId(-1);

		criterio.setFolioInstruccion(null);
		criterio.setTipoInstruccion(tipoInstruccion);
		criterio.setTipoOperacion(tipoOperacion);
		
		List<RegistroContablePosicionControladaDTO> registros = movimientosValorDAOV2.buscarRegistrosContablesControladasHistoricos(criterio, new TreeSet<Long>(Arrays.asList(new Long[]{1130L, 1136L, 1137L, 1162L, 1188L, 1191L, 1200L})));

		assertTrue(registros != null && registros.size() > 0);
	}

	/**
	 * Prueba unitaria para el método buscarRegistrosContablesNombradasHistoricos
	 */
	public void testBuscarRegistrosContablesNombradasHistoricos() {

		MovimientosValorDAOV2 movimientosValorDAOV2 = (MovimientosValorDAOV2) applicationContext.getBean("movimientosValorDAOV2");

		CriterioConsultaMovimientosValorDTO criterio = new CriterioConsultaMovimientosValorDTO();

		CuentaDTO cuenta = new CuentaDTO();
		CuentaDTO cuentaContraparte = new CuentaDTO();
		InstitucionDTO institucion = new InstitucionDTO();
		BovedaDTO boveda = new BovedaDTO();
		EmisionDTO emision = new EmisionDTO();
		TipoTenenciaDTO tipoTenencia = new TipoTenenciaDTO();
		TipoNaturalezaDTO tipoNaturaleza = new TipoNaturalezaDTO();
		Calendar c = GregorianCalendar.getInstance();

		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);

		criterio.setFechaInicial(c.getTime());
		criterio.setFechaFinal(new Date());

		institucion.setId(4);
		cuenta.setInstitucion(institucion);
		cuenta.setNumeroCuenta("-1");

		tipoTenencia.setIdTipoCuenta(-1);
		cuenta.setTipoTenencia(tipoTenencia);

		TipoCuentaDTO tipoCuenta = new TipoCuentaDTO();
		tipoCuenta.setId("N");
		tipoTenencia.setTipoCuenta(tipoCuenta);

		tipoNaturaleza.setId("P");
		tipoTenencia.setTipoCustodia("V");
		tipoTenencia.setTipoNaturaleza(tipoNaturaleza);
		boveda.setId(-1);
		cuentaContraparte.setTipoTenencia(tipoTenencia);
		cuentaContraparte.setNumeroCuenta("-1");
		InstitucionDTO institucionContraparte = new InstitucionDTO();
		institucionContraparte.setId(-1);
		cuentaContraparte.setInstitucion(institucionContraparte);
		criterio.setCuenta(cuenta);
		criterio.setBoveda(boveda);

		EmisoraDTO emisora = new EmisoraDTO();
		SerieDTO serie = new SerieDTO();
		TipoValorDTO tipoValor = new TipoValorDTO();
		MercadoDTO mercado = new MercadoDTO();

		mercado.setId(-1);
		tipoValor.setId(-1);
		tipoValor.setMercado(mercado);
		emisora.setId(-1);
		serie.setEmisora(emisora);

		serie.setTipoValor(tipoValor);
		emision.setId(-1);
		emision.setSerie(serie);
		emision.setEmisora(emisora);
		emision.setTipoValor(tipoValor);

		criterio.setEmision(emision);

		TipoInstruccionDTO tipoInstruccion = new TipoInstruccionDTO();
		tipoInstruccion.setIdTipoInstruccion(-1L);

		TipoOperacionDTO tipoOperacion = new TipoOperacionDTO();
		tipoOperacion.setId(-1);

		criterio.setFolioInstruccion(null);
		criterio.setTipoInstruccion(tipoInstruccion);
		criterio.setTipoOperacion(tipoOperacion);

		criterio.setCuentaContraparte(cuentaContraparte);

		criterio.getCuenta().getTipoTenencia().getTipoCuenta().setId("N");
		criterio.getCuenta().getTipoTenencia().setIdTipoCuenta(-1);

		List<RegistroContablePosicionNombradaDTO> registros = movimientosValorDAOV2.buscarRegistrosContablesNombradasHistoricos(criterio, new TreeSet<Long>(Arrays.asList(new Long[]{1130L, 1136L, 1137L, 1162L, 1188L, 1191L, 1200L})));

		assertTrue(registros != null && registros.size() > 0);
	}
	
	/**
	 * Prueba unitaria para el método obtenerProyeccionRegistrosContablesControladas
	 */
	public void testObtenerProyeccionRegistrosContablesControladas() {

		MovimientosValorDAOV2 movimientosValorDAOV2 = (MovimientosValorDAOV2) applicationContext.getBean("movimientosValorDAOV2");

		CriterioConsultaMovimientosValorDTO criterio = new CriterioConsultaMovimientosValorDTO();

		CuentaDTO cuenta = new CuentaDTO();
		CuentaDTO cuentaContraparte = new CuentaDTO();
		InstitucionDTO institucion = new InstitucionDTO();
		BovedaDTO boveda = new BovedaDTO();
		EmisionDTO emision = new EmisionDTO();
		TipoTenenciaDTO tipoTenencia = new TipoTenenciaDTO();
		TipoNaturalezaDTO tipoNaturaleza = new TipoNaturalezaDTO();
		Calendar c = GregorianCalendar.getInstance();

		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);

		criterio.setFechaInicial(c.getTime());
		criterio.setFechaFinal(new Date());

		institucion.setId(4);
		cuenta.setInstitucion(institucion);
		cuenta.setNumeroCuenta("-1");

		tipoTenencia.setIdTipoCuenta(-1);
		cuenta.setTipoTenencia(tipoTenencia);

		TipoCuentaDTO tipoCuenta = new TipoCuentaDTO();
		tipoCuenta.setId("C");
		tipoTenencia.setTipoCuenta(tipoCuenta);

		tipoNaturaleza.setId("P");
		tipoTenencia.setTipoCustodia("V");
		tipoTenencia.setTipoNaturaleza(tipoNaturaleza);
		boveda.setId(-1);
		cuentaContraparte.setTipoTenencia(tipoTenencia);
		cuentaContraparte.setNumeroCuenta("-1");
		InstitucionDTO institucionContraparte = new InstitucionDTO();
		institucionContraparte.setId(-1);
		cuentaContraparte.setInstitucion(institucionContraparte);
		criterio.setCuenta(cuenta);
		criterio.setBoveda(boveda);

		EmisoraDTO emisora = new EmisoraDTO();
		SerieDTO serie = new SerieDTO();
		TipoValorDTO tipoValor = new TipoValorDTO();
		MercadoDTO mercado = new MercadoDTO();

		mercado.setId(-1);
		tipoValor.setId(-1);
		tipoValor.setMercado(mercado);
		emisora.setId(-1);
		serie.setEmisora(emisora);

		serie.setTipoValor(tipoValor);
		emision.setId(-1);
		emision.setSerie(serie);
		emision.setEmisora(emisora);
		emision.setTipoValor(tipoValor);

		criterio.setEmision(emision);

		TipoInstruccionDTO tipoInstruccion = new TipoInstruccionDTO();
		tipoInstruccion.setIdTipoInstruccion(-1L);

		TipoOperacionDTO tipoOperacion = new TipoOperacionDTO();
		tipoOperacion.setId(-1);

		criterio.setFolioInstruccion(null);
		criterio.setTipoInstruccion(tipoInstruccion);
		criterio.setTipoOperacion(tipoOperacion);
		
		Long registros = movimientosValorDAOV2.obtenerProyeccionRegistrosContablesControladas(criterio);

		assertTrue(registros != null && registros > 0);
	}

	/**
	 * Prueba unitaria para el método obtenerProyeccionRegistrosContablesControladasHistorico
	 */
	public void testObtenerProyeccionRegistrosContablesControladasHistorico() {

		MovimientosValorDAOV2 movimientosValorDAOV2 = (MovimientosValorDAOV2) applicationContext.getBean("movimientosValorDAOV2");

		CriterioConsultaMovimientosValorDTO criterio = new CriterioConsultaMovimientosValorDTO();

		CuentaDTO cuenta = new CuentaDTO();
		CuentaDTO cuentaContraparte = new CuentaDTO();
		InstitucionDTO institucion = new InstitucionDTO();
		BovedaDTO boveda = new BovedaDTO();
		EmisionDTO emision = new EmisionDTO();
		TipoTenenciaDTO tipoTenencia = new TipoTenenciaDTO();
		TipoNaturalezaDTO tipoNaturaleza = new TipoNaturalezaDTO();
		Calendar c = GregorianCalendar.getInstance();

		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);

		criterio.setFechaInicial(c.getTime());
		criterio.setFechaFinal(new Date());

		institucion.setId(4);
		cuenta.setInstitucion(institucion);
		cuenta.setNumeroCuenta("-1");

		tipoTenencia.setIdTipoCuenta(-1);
		cuenta.setTipoTenencia(tipoTenencia);

		TipoCuentaDTO tipoCuenta = new TipoCuentaDTO();
		tipoCuenta.setId("C");
		tipoTenencia.setTipoCuenta(tipoCuenta);

		tipoNaturaleza.setId("P");
		tipoTenencia.setTipoCustodia("V");
		tipoTenencia.setTipoNaturaleza(tipoNaturaleza);
		boveda.setId(-1);
		cuentaContraparte.setTipoTenencia(tipoTenencia);
		cuentaContraparte.setNumeroCuenta("-1");
		InstitucionDTO institucionContraparte = new InstitucionDTO();
		institucionContraparte.setId(-1);
		cuentaContraparte.setInstitucion(institucionContraparte);
		criterio.setCuenta(cuenta);
		criterio.setBoveda(boveda);

		EmisoraDTO emisora = new EmisoraDTO();
		SerieDTO serie = new SerieDTO();
		TipoValorDTO tipoValor = new TipoValorDTO();
		MercadoDTO mercado = new MercadoDTO();

		mercado.setId(-1);
		tipoValor.setId(-1);
		tipoValor.setMercado(mercado);
		emisora.setId(-1);
		serie.setEmisora(emisora);

		serie.setTipoValor(tipoValor);
		emision.setId(-1);
		emision.setSerie(serie);
		emision.setEmisora(emisora);
		emision.setTipoValor(tipoValor);

		criterio.setEmision(emision);

		TipoInstruccionDTO tipoInstruccion = new TipoInstruccionDTO();
		tipoInstruccion.setIdTipoInstruccion(-1L);

		TipoOperacionDTO tipoOperacion = new TipoOperacionDTO();
		tipoOperacion.setId(-1);

		criterio.setFolioInstruccion(null);
		criterio.setTipoInstruccion(tipoInstruccion);
		criterio.setTipoOperacion(tipoOperacion);

		Long registros = movimientosValorDAOV2.obtenerProyeccionRegistrosContablesControladasHistorico(criterio);

		assertTrue(registros != null && registros > 0);
	}
	
	/**
	 * Prueba unitaria para el método obtenerProyeccionRegistrosContablesNombradas
	 */
	public void testObtenerProyeccionRegistrosContablesNombradas() {

		MovimientosValorDAOV2 movimientosValorDAOV2 = (MovimientosValorDAOV2) applicationContext.getBean("movimientosValorDAOV2");

		CriterioConsultaMovimientosValorDTO criterio = new CriterioConsultaMovimientosValorDTO();

		CuentaDTO cuenta = new CuentaDTO();
		CuentaDTO cuentaContraparte = new CuentaDTO();
		InstitucionDTO institucion = new InstitucionDTO();
		BovedaDTO boveda = new BovedaDTO();
		EmisionDTO emision = new EmisionDTO();
		TipoTenenciaDTO tipoTenencia = new TipoTenenciaDTO();
		TipoNaturalezaDTO tipoNaturaleza = new TipoNaturalezaDTO();
		Calendar c = GregorianCalendar.getInstance();

		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);

		criterio.setFechaInicial(c.getTime());
		criterio.setFechaFinal(new Date());

		institucion.setId(4);
		cuenta.setInstitucion(institucion);
		cuenta.setNumeroCuenta("-1");

		tipoTenencia.setIdTipoCuenta(-1);
		cuenta.setTipoTenencia(tipoTenencia);

		TipoCuentaDTO tipoCuenta = new TipoCuentaDTO();
		tipoCuenta.setId("N");
		tipoTenencia.setTipoCuenta(tipoCuenta);

		tipoNaturaleza.setId("P");
		tipoTenencia.setTipoCustodia("V");
		tipoTenencia.setTipoNaturaleza(tipoNaturaleza);
		boveda.setId(-1);
		cuentaContraparte.setTipoTenencia(tipoTenencia);
		cuentaContraparte.setNumeroCuenta("-1");
		InstitucionDTO institucionContraparte = new InstitucionDTO();
		institucionContraparte.setId(-1);
		cuentaContraparte.setInstitucion(institucionContraparte);
		criterio.setCuenta(cuenta);
		criterio.setBoveda(boveda);

		EmisoraDTO emisora = new EmisoraDTO();
		SerieDTO serie = new SerieDTO();
		TipoValorDTO tipoValor = new TipoValorDTO();
		MercadoDTO mercado = new MercadoDTO();

		mercado.setId(-1);
		tipoValor.setId(-1);
		tipoValor.setMercado(mercado);
		emisora.setId(-1);
		serie.setEmisora(emisora);

		serie.setTipoValor(tipoValor);
		emision.setId(-1);
		emision.setSerie(serie);
		emision.setEmisora(emisora);
		emision.setTipoValor(tipoValor);

		criterio.setEmision(emision);

		TipoInstruccionDTO tipoInstruccion = new TipoInstruccionDTO();
		tipoInstruccion.setIdTipoInstruccion(-1L);

		TipoOperacionDTO tipoOperacion = new TipoOperacionDTO();
		tipoOperacion.setId(-1);

		criterio.setFolioInstruccion(null);
		criterio.setTipoInstruccion(tipoInstruccion);
		criterio.setTipoOperacion(tipoOperacion);
		
		criterio.setCuentaContraparte(cuentaContraparte);

		criterio.getCuenta().getTipoTenencia().getTipoCuenta().setId("N");
		criterio.getCuenta().getTipoTenencia().setIdTipoCuenta(-1);
		
		Long registros = movimientosValorDAOV2.obtenerProyeccionRegistrosContablesNombradas(criterio);

		assertTrue(registros != null && registros > 0);
	}

	/**
	 * Prueba unitaria para el método obtenerProyeccionRegistrosContablesNombradasHistorico
	 */
	public void testObtenerProyeccionRegistrosContablesNombradasHistorico() {

		MovimientosValorDAOV2 movimientosValorDAOV2 = (MovimientosValorDAOV2) applicationContext.getBean("movimientosValorDAOV2");

		CriterioConsultaMovimientosValorDTO criterio = new CriterioConsultaMovimientosValorDTO();

		CuentaDTO cuenta = new CuentaDTO();
		CuentaDTO cuentaContraparte = new CuentaDTO();
		InstitucionDTO institucion = new InstitucionDTO();
		BovedaDTO boveda = new BovedaDTO();
		EmisionDTO emision = new EmisionDTO();
		TipoTenenciaDTO tipoTenencia = new TipoTenenciaDTO();
		TipoNaturalezaDTO tipoNaturaleza = new TipoNaturalezaDTO();
		Calendar c = GregorianCalendar.getInstance();

		c.set(Calendar.DATE, 14);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.YEAR, 2008);

		criterio.setFechaInicial(c.getTime());
		criterio.setFechaFinal(new Date());

		institucion.setId(4);
		cuenta.setInstitucion(institucion);
		cuenta.setNumeroCuenta("-1");

		tipoTenencia.setIdTipoCuenta(-1);
		cuenta.setTipoTenencia(tipoTenencia);

		TipoCuentaDTO tipoCuenta = new TipoCuentaDTO();
		tipoCuenta.setId("N");
		tipoTenencia.setTipoCuenta(tipoCuenta);

		tipoNaturaleza.setId("P");
		tipoTenencia.setTipoCustodia("V");
		tipoTenencia.setTipoNaturaleza(tipoNaturaleza);
		boveda.setId(-1);
		cuentaContraparte.setTipoTenencia(tipoTenencia);
		cuentaContraparte.setNumeroCuenta("-1");
		InstitucionDTO institucionContraparte = new InstitucionDTO();
		institucionContraparte.setId(-1);
		cuentaContraparte.setInstitucion(institucionContraparte);
		criterio.setCuenta(cuenta);
		criterio.setBoveda(boveda);

		EmisoraDTO emisora = new EmisoraDTO();
		SerieDTO serie = new SerieDTO();
		TipoValorDTO tipoValor = new TipoValorDTO();
		MercadoDTO mercado = new MercadoDTO();

		mercado.setId(-1);
		tipoValor.setId(-1);
		tipoValor.setMercado(mercado);
		emisora.setId(-1);
		serie.setEmisora(emisora);

		serie.setTipoValor(tipoValor);
		emision.setId(-1);
		emision.setSerie(serie);
		emision.setEmisora(emisora);
		emision.setTipoValor(tipoValor);

		criterio.setEmision(emision);

		TipoInstruccionDTO tipoInstruccion = new TipoInstruccionDTO();
		tipoInstruccion.setIdTipoInstruccion(-1L);

		TipoOperacionDTO tipoOperacion = new TipoOperacionDTO();
		tipoOperacion.setId(-1);

		criterio.setFolioInstruccion(null);
		criterio.setTipoInstruccion(tipoInstruccion);
		criterio.setTipoOperacion(tipoOperacion);

		criterio.setCuentaContraparte(cuentaContraparte);

		criterio.getCuenta().getTipoTenencia().getTipoCuenta().setId("N");
		criterio.getCuenta().getTipoTenencia().setIdTipoCuenta(-1);

		Long registros = movimientosValorDAOV2.obtenerProyeccionRegistrosContablesNombradasHistorico(criterio);

		assertTrue(registros != null && registros > 0);
	}
	

}
