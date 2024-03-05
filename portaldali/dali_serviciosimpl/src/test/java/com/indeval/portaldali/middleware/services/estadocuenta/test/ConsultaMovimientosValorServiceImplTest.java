/**
 * 2H Software
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval

 */
package com.indeval.portaldali.middleware.services.estadocuenta.test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.EmisoraDTO;
import com.indeval.portaldali.middleware.dto.EstadoCuentaPosicionPorEmisionDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.MercadoDTO;
import com.indeval.portaldali.middleware.dto.SerieDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.TipoOperacionDTO;
import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.middleware.services.BaseTestCase;
import com.indeval.portaldali.middleware.services.common.util.FormatUtil;
import com.indeval.portaldali.middleware.services.estadocuenta.ConsultaMovimientosValorService;

/**
 * Prueba unitaria para la clase {@link ConsultaMovimientosValorServiceImpl}
 *
 * @author Emigdio Hernández Rodríguez
 * @version 1.0
 *
 */
public class ConsultaMovimientosValorServiceImplTest extends
BaseTestCase {
	
	/**
	 * Prueba unitaria de la consulta de movimientos de cuentas de pasivo
	 */
	public void testConsultarMovimientosePosicionesControladas() {
		
		ConsultaMovimientosValorService servicio = (ConsultaMovimientosValorService)getBean("consultaMovimientosValorService");
		
		
		CriterioConsultaMovimientosValorDTO criterio = new CriterioConsultaMovimientosValorDTO();
		
		
	
		CuentaDTO cuenta = new CuentaDTO();
		CuentaDTO cuentaContraparte = new CuentaDTO();
		InstitucionDTO institucion = new InstitucionDTO();
		BovedaDTO boveda =  new BovedaDTO();
		EmisionDTO emision = new EmisionDTO();
		TipoTenenciaDTO tipoTenencia = new TipoTenenciaDTO();
		TipoNaturalezaDTO tipoNaturaleza = new TipoNaturalezaDTO();
		criterio.setFechaInicial(FormatUtil.stringToDate("01/01/2008"));
		criterio.setFechaFinal(FormatUtil.stringToDate("25/01/2008"));
		
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
		
		List<Long> idsCuentas = servicio.buscarCuentasDeMovimientosDeValor(criterio);
		List<Long> idsEmisiones = servicio.buscarEmisionesDeMovimientosDeValor(criterio);
		if(idsEmisiones.size()>100){
			idsEmisiones = idsEmisiones.subList(0,100);
		}
		Set<Long> setEmisiones = new HashSet();
		
		setEmisiones.addAll(idsEmisiones);
		
		
		List<EstadoCuentaPosicionPorEmisionDTO> detalleEstadoCuenta = servicio.consultarMovimientosDeValor(criterio, setEmisiones);
		
		criterio.setCuentaContraparte(cuentaContraparte);
		
		criterio.getCuenta().getTipoTenencia().getTipoCuenta().setId("N");
		criterio.getCuenta().getTipoTenencia().setIdTipoCuenta(-1);
		idsCuentas = servicio.buscarCuentasDeMovimientosDeValor(criterio);
		idsEmisiones = servicio.buscarEmisionesDeMovimientosDeValor(criterio);
		 
		setEmisiones = new HashSet();
		if(idsEmisiones.size()>100){
			idsEmisiones = idsEmisiones.subList(0,100);
		}
		setEmisiones.addAll(idsEmisiones);
		
		detalleEstadoCuenta = servicio.consultarMovimientosDeValor(criterio, setEmisiones);
		
		assertTrue(idsEmisiones != null && idsEmisiones.size() > 0);
		assertTrue(detalleEstadoCuenta != null && detalleEstadoCuenta.size() > 0);
		
	}
	
	/**
	 * Prueba unitaria del método de consulta de movimientos para cuentas de activo
	 */
	public void testConsultarMovimientosePosicionesNombradasYControladasActivo() {
		
		ConsultaMovimientosValorService servicio = (ConsultaMovimientosValorService)getBean("consultaMovimientosValorService");
		
		
		CriterioConsultaMovimientosValorDTO criterio = new CriterioConsultaMovimientosValorDTO();
		
		
	
		CuentaDTO cuenta = new CuentaDTO();
		CuentaDTO cuentaContraparte = new CuentaDTO();
		InstitucionDTO institucion = new InstitucionDTO();
		BovedaDTO boveda =  new BovedaDTO();
		EmisionDTO emision = new EmisionDTO();
		TipoTenenciaDTO tipoTenencia = new TipoTenenciaDTO();
		TipoNaturalezaDTO tipoNaturaleza = new TipoNaturalezaDTO();
		criterio.setFechaInicial(FormatUtil.stringToDate("01/01/2008"));
		criterio.setFechaFinal(FormatUtil.stringToDate("25/01/2008"));
		
		institucion.setId(-1);
		cuenta.setInstitucion(institucion);
		cuenta.setNumeroCuenta("-1");
		
		tipoTenencia.setIdTipoCuenta(-1);
		cuenta.setTipoTenencia(tipoTenencia);
		
		TipoCuentaDTO tipoCuenta = new TipoCuentaDTO();
		tipoCuenta.setId("C");
		tipoTenencia.setTipoCuenta(tipoCuenta);
		
		tipoNaturaleza.setId("A");
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
		
		List<Long> idsCuentas = servicio.buscarCuentasDeMovimientosDeValor(criterio);
		List<Long> idsEmisiones = servicio.buscarEmisionesDeMovimientosDeValor(criterio);
		
		Set<Long> setEmisiones = new HashSet();
		if(idsEmisiones.size()>100){
			idsEmisiones = idsEmisiones.subList(0,100);
		}
		setEmisiones.addAll(idsEmisiones);
		
		List<EstadoCuentaPosicionPorEmisionDTO> detalleEstadoCuenta = servicio.consultarMovimientosDeValor(criterio, setEmisiones);
		
		criterio.setCuentaContraparte(cuentaContraparte);
		
		criterio.getCuenta().getTipoTenencia().getTipoCuenta().setId("N");
		criterio.getCuenta().getTipoTenencia().setIdTipoCuenta(-1);
		idsCuentas = servicio.buscarCuentasDeMovimientosDeValor(criterio);
		idsEmisiones = servicio.buscarEmisionesDeMovimientosDeValor(criterio);
		 
		setEmisiones = new HashSet();
		if(idsEmisiones.size()>100){
			idsEmisiones = idsEmisiones.subList(0,100);
		}
		setEmisiones.addAll(idsEmisiones);
		
		detalleEstadoCuenta = servicio.consultarMovimientosDeValor(criterio, setEmisiones);
		
		assertTrue(idsEmisiones != null && idsEmisiones.size() > 0);
		assertTrue(detalleEstadoCuenta != null && detalleEstadoCuenta.size() > 0);
		
	}
	
	
}
