/**
 * 2H Software
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Creado el Dec 21, 2007
 */
package com.indeval.portaldali.middleware.services.estadocuenta.test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.DetalleEstadoCuentaPosicionPorBovedaDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.EmisoraDTO;
import com.indeval.portaldali.middleware.dto.EstadoCuentaPosicionPorEmisionDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.MercadoDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionNombradaDTO;
import com.indeval.portaldali.middleware.dto.SerieDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.middleware.services.BaseTestCase;
import com.indeval.portaldali.middleware.services.estadocuenta.ConsultaEstadoCuentaPosicionService;

/**
 * Prueba unitaria para la clase {@link ConsultaEstadoCuentaPosicionServiceImpl}
 *
 * @author José Antonio Huizar Moreno
 * @version 1.0
 *
 */
public class ConsultaEstadoCuentaPosicionServiceImplTest extends
BaseTestCase {
	
	/**
	 * Prueba unitaria del método consultarEstadoDeCuentaPosicionesNombrada y consultarEstadoDeCuentaPosicionesControlada
	 */
	public void testConsultarEstadoDeCuentaPosicionesControladaNombrada() {
		
		ConsultaEstadoCuentaPosicionService consultaEstadoCuentaPosicionService = (ConsultaEstadoCuentaPosicionService)applicationContext.getBean("consultaEstadoCuentaService");
		
		
		
		PosicionDTO criterio = new PosicionDTO();
		Calendar calInicio = new GregorianCalendar();
		Calendar calFin = new GregorianCalendar();
		CuentaDTO cuenta = new CuentaDTO();
		InstitucionDTO institucion = new InstitucionDTO();
		BovedaDTO boveda =  new BovedaDTO();
		EmisionDTO emision = new EmisionDTO();
		TipoTenenciaDTO tipoTenencia = new TipoTenenciaDTO();
		TipoNaturalezaDTO tipoNaturaleza = new TipoNaturalezaDTO();
		
		calInicio.set(Calendar.YEAR, 2008);
		calInicio.set(Calendar.MONTH, 0);
		calInicio.set(Calendar.DATE, 1);
		
		calFin.set(Calendar.YEAR, 2008);
		calFin.set(Calendar.MONTH, 0);
		calFin.set(Calendar.DATE, 25);
		
		institucion.setId(4);
		cuenta.setInstitucion(institucion);
		cuenta.setNumeroCuenta("-1");
		
		tipoTenencia.setIdTipoCuenta(11);
		cuenta.setTipoTenencia(tipoTenencia);
		
		TipoCuentaDTO tipoCuenta = new TipoCuentaDTO();
		tipoCuenta.setId("C");
		tipoTenencia.setTipoCuenta(tipoCuenta);
		tipoTenencia.setTipoCustodia(null);
		tipoNaturaleza.setId("P");
		tipoTenencia.setTipoCustodia("V");
		tipoTenencia.setTipoNaturaleza(tipoNaturaleza);
		boveda.setId(-1);

		criterio.setCuenta(cuenta);
		criterio.setBoveda(boveda);
		criterio.setFechaInicio(calInicio.getTime());
		criterio.setFechaFinal(calFin.getTime());
		
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
		
		List<Long> idsEmisiones = consultaEstadoCuentaPosicionService.buscarEmisionesDePosicionesControladas(criterio, false);
		List<Long> idsCuentas = consultaEstadoCuentaPosicionService.buscarCuentasDePosiciones(criterio);
		
	
	
		List<EstadoCuentaPosicionPorEmisionDTO> detalleEstadoCuenta = consultaEstadoCuentaPosicionService.consultarEstadoDeCuentaPosicionesControladas(criterio, idsEmisiones);
		
		assertTrue(idsEmisiones != null && idsEmisiones.size() > 0);
		assertTrue(detalleEstadoCuenta != null && detalleEstadoCuenta.size() > 0);
		
		criterio.getCuenta().getTipoTenencia().getTipoCuenta().setId("N");
		criterio.getCuenta().getTipoTenencia().setIdTipoCuenta(-1);
		idsEmisiones = consultaEstadoCuentaPosicionService.buscarEmisionesDePosicionesNombradas(criterio,false);
		idsCuentas = consultaEstadoCuentaPosicionService.buscarCuentasDePosiciones(criterio);
		
		detalleEstadoCuenta = consultaEstadoCuentaPosicionService.consultarEstadoDeCuentaPosicionesNombrada(criterio, idsEmisiones);
		
		
		assertTrue(idsEmisiones != null && idsEmisiones.size() > 0);
		assertTrue(detalleEstadoCuenta != null && detalleEstadoCuenta.size() > 0);
		
		
	}
	
	/**
	 * Prueba unitaria del método consultarEstadoDeCuentaPosicionesNombrada y consultarEstadoDeCuentaPosicionesControlada
	 * con criterios específicos
	 */
	public void testConsultarEstadoDeCuentaPosicionesControladaNombradaConCriterios() {
		
		ConsultaEstadoCuentaPosicionService consultaEstadoCuentaPosicionService = (ConsultaEstadoCuentaPosicionService)applicationContext.getBean("consultaEstadoCuentaService");
		
		
		
		PosicionDTO criterio = new PosicionDTO();
		Calendar calInicio = new GregorianCalendar();
		Calendar calFin = new GregorianCalendar();
		CuentaDTO cuenta = new CuentaDTO();
		InstitucionDTO institucion = new InstitucionDTO();
		BovedaDTO boveda =  new BovedaDTO();
		EmisionDTO emision = new EmisionDTO();
		TipoTenenciaDTO tipoTenencia = new TipoTenenciaDTO();
		TipoNaturalezaDTO tipoNaturaleza = new TipoNaturalezaDTO();
		
		calInicio.set(Calendar.YEAR, 2008);
		calInicio.set(Calendar.MONTH, 0);
		calInicio.set(Calendar.DATE, 1);
		
		calFin.set(Calendar.YEAR, 2008);
		calFin.set(Calendar.MONTH, 0);
		calFin.set(Calendar.DATE, 25);
		
		institucion.setId(1);
		cuenta.setInstitucion(institucion);
		cuenta.setNumeroCuenta("010019002");
		
		tipoTenencia.setIdTipoCuenta(10);
		cuenta.setTipoTenencia(tipoTenencia);
		
		TipoCuentaDTO tipoCuenta = new TipoCuentaDTO();
		tipoCuenta.setId("C");
		tipoTenencia.setTipoCuenta(tipoCuenta);
		tipoTenencia.setTipoCustodia(null);
		tipoNaturaleza.setId("P");
		tipoTenencia.setTipoCustodia("V");
		tipoTenencia.setTipoNaturaleza(tipoNaturaleza);
		boveda.setId(1);

		criterio.setCuenta(cuenta);
		criterio.setBoveda(boveda);
		criterio.setFechaInicio(calInicio.getTime());
		criterio.setFechaFinal(calFin.getTime());
		
		EmisoraDTO emisora = new EmisoraDTO();
		SerieDTO serie = new SerieDTO();
		TipoValorDTO tipoValor = new TipoValorDTO();
		MercadoDTO mercado = new MercadoDTO();
		
		mercado.setId(1);
		tipoValor.setId(130);
		tipoValor.setMercado(mercado);
		emisora.setId(592);
		serie.setEmisora(emisora);
		
		serie.setSerie("B2");
		serie.setTipoValor(tipoValor);
		emision.setId(-1);
		emision.setSerie(serie);
		emision.setEmisora(emisora);
		emision.setTipoValor(tipoValor);
		emision.setIsin("MX54DL000026");
		criterio.setEmision(emision);
		
		List<Long> idsEmisiones = consultaEstadoCuentaPosicionService.buscarEmisionesDePosicionesControladas(criterio,false);
		List<Long> idsCuentas = consultaEstadoCuentaPosicionService.buscarCuentasDePosiciones(criterio);
		
	
		List<EstadoCuentaPosicionPorEmisionDTO> detalleEstadoCuenta = consultaEstadoCuentaPosicionService.consultarEstadoDeCuentaPosicionesControladas(criterio, idsEmisiones);
		
		assertTrue(idsEmisiones != null && idsEmisiones.size() > 0);
		assertTrue(detalleEstadoCuenta != null && detalleEstadoCuenta.size() > 0);
		
		criterio.getCuenta().getTipoTenencia().getTipoCuenta().setId("N");
		criterio.getCuenta().getTipoTenencia().setIdTipoCuenta(1);
		criterio.getCuenta().setNumeroCuenta("010010117");
		idsEmisiones = consultaEstadoCuentaPosicionService.buscarEmisionesDePosicionesNombradas(criterio,false);
		idsCuentas = consultaEstadoCuentaPosicionService.buscarCuentasDePosiciones(criterio);
		detalleEstadoCuenta = consultaEstadoCuentaPosicionService.consultarEstadoDeCuentaPosicionesNombrada(criterio, idsEmisiones);
		
		for (EstadoCuentaPosicionPorEmisionDTO estadoCuentaPosicionPorEmisionDTO : detalleEstadoCuenta) {
			List<DetalleEstadoCuentaPosicionPorBovedaDTO> d = estadoCuentaPosicionPorEmisionDTO.getDetallesBoveda();
			for (DetalleEstadoCuentaPosicionPorBovedaDTO detalleEstadoCuentaPosicionPorBovedaDTO : d) {
				List<RegistroContablePosicionNombradaDTO> j =  detalleEstadoCuentaPosicionPorBovedaDTO.getRegistrosContablesNombradas();
				for (RegistroContablePosicionNombradaDTO registroContablePosicionNombradaDTO : j) {
					consultaEstadoCuentaPosicionService.buscarPosicionPorId(new Date(), registroContablePosicionNombradaDTO.getPosicion().getIdPosicion(),"N");
				}
			}
		}
		
		assertTrue(idsEmisiones != null && idsEmisiones.size() > 0);
		assertTrue(detalleEstadoCuenta != null && detalleEstadoCuenta.size() > 0);
		
		
	}
	
	
}
