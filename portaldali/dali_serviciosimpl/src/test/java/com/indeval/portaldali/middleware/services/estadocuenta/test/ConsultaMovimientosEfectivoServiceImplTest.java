/**
 * 2H Software
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval

 */
package com.indeval.portaldali.middleware.services.estadocuenta.test;

import java.util.ArrayList;
import java.util.List;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosEfectivoDTO;
import com.indeval.portaldali.middleware.dto.CuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.EstadoCuentaEfectivoPorDivisaDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.TipoOperacionDTO;
import com.indeval.portaldali.middleware.services.BaseTestCase;
import com.indeval.portaldali.middleware.services.common.util.FormatUtil;
import com.indeval.portaldali.middleware.services.estadocuenta.ConsultaMovimientosEfectivoService;

/**
 * Prueba unitaria para la clase {@link ConsultaMovimientosEfectivoServiceImpl}
 *
 * @author Emigdio Hernández Rodríguez
 * @version 1.0
 *
 */
public class ConsultaMovimientosEfectivoServiceImplTest extends 
BaseTestCase {
	
	/**
	 * Prueba unitaria para la consulta de movimientos de registros nombrados y controlados
	 */
	public void testConsultarMovimientoEfectivo() {
		
		ConsultaMovimientosEfectivoService servicio = (ConsultaMovimientosEfectivoService)applicationContext.getBean("consultaMovimientosEfectivoService");
		
		
		CriterioConsultaMovimientosEfectivoDTO criterio = new CriterioConsultaMovimientosEfectivoDTO();
		
		
	
		CuentaEfectivoDTO cuenta = new CuentaEfectivoDTO();
		CuentaEfectivoDTO cuentaContraparte = new CuentaEfectivoDTO();
		InstitucionDTO institucion = new InstitucionDTO();
		BovedaDTO boveda =  new BovedaDTO();
		EmisionDTO emision = new EmisionDTO();
		
		TipoNaturalezaDTO tipoNaturaleza = new TipoNaturalezaDTO();
		criterio.setFechaInicial(FormatUtil.stringToDate("01/01/2008"));
		criterio.setFechaFinal(FormatUtil.stringToDate("25/01/2008"));
		
		institucion.setId(4);
		cuenta.setInstitucion(institucion);
		cuenta.setNumeroCuenta("-1");
		
		
		
		
		TipoCuentaDTO tipoCuenta = new TipoCuentaDTO();
		tipoCuenta.setId("C");
		cuenta.setTipoCuenta(tipoCuenta);
		
		tipoNaturaleza.setId("P");
		cuenta.setTipoCustodia("E");
		cuenta.setTipoNaturaleza(tipoNaturaleza);
		boveda.setId(-1);
		cuentaContraparte.setTipoCustodia("E");
		cuentaContraparte.setTipoNaturaleza(tipoNaturaleza);
		cuentaContraparte.setTipoCuenta(tipoCuenta);
		
		cuentaContraparte.setNumeroCuenta("-1");
		InstitucionDTO institucionContraparte = new InstitucionDTO();
		institucionContraparte.setId(-1);
		cuentaContraparte.setInstitucion(institucionContraparte);
		criterio.setCuenta(cuenta);
		criterio.setBoveda(boveda);
		
		
		DivisaDTO divisa = new DivisaDTO();
		divisa.setId(-1);
		
		criterio.setDivisa(divisa);
		 
		TipoInstruccionDTO tipoInstruccion = new TipoInstruccionDTO();
		tipoInstruccion.setIdTipoInstruccion(-1L);
		
		TipoOperacionDTO tipoOperacion = new TipoOperacionDTO();
		tipoOperacion.setId(-1);
		
		criterio.setFolioInstruccion(null);
		criterio.setTipoInstruccion(tipoInstruccion);
		criterio.setTipoOperacion(tipoOperacion);
		
		List<Long> idsCuentas = servicio.buscarCuentasDeRegistrosContablesDeEfectivo(criterio);
		List<Long> idsDivisas = servicio.buscarDivisasDeRegistrosContablesDeEfectivo(criterio);
		
		List<Long> setDivisas = new ArrayList<Long>();
		if(idsDivisas.size()>100){
			idsDivisas = idsDivisas.subList(0, 100);
		}
		setDivisas.addAll(idsDivisas); 
		
		List<EstadoCuentaEfectivoPorDivisaDTO> detalleEstadoCuenta = servicio.consultarMovimientosDeEfectivo(criterio, setDivisas,false);
		
		criterio.setCuentaContraparte(cuentaContraparte);
		
		criterio.getCuenta().getTipoCuenta().setId("N");
		cuentaContraparte.getTipoCuenta().setId("N");
		
		idsCuentas = servicio.buscarCuentasDeRegistrosContablesDeEfectivo(criterio);
		idsDivisas = servicio.buscarDivisasDeRegistrosContablesDeEfectivo(criterio);
		setDivisas = new ArrayList<Long>();
		if(idsDivisas.size()>100){
			idsDivisas = idsDivisas.subList(0, 100);
		}
		setDivisas.addAll(idsDivisas);
		detalleEstadoCuenta = servicio.consultarMovimientosDeEfectivo(criterio, setDivisas,false);
		
		assertTrue(idsDivisas != null && idsDivisas.size() > 0);
		assertTrue(detalleEstadoCuenta != null && detalleEstadoCuenta.size() > 0);
		
	}
	
	
}
