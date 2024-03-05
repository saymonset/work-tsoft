/**
 * 2H Sotware SA de CV
 * Sistema de Estado de Cuenta
 */
package com.indeval.portaldali.middleware.services.estadocuenta.test;

import java.util.List;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.EstadoCuentaEfectivoPorDivisaDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.SaldoEfectivoDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.services.BaseTestCase;
import com.indeval.portaldali.middleware.services.common.util.FormatUtil;
import com.indeval.portaldali.middleware.services.estadocuenta.ConsultaEstadoCuentaSaldoEfectivoService;

public class ConsultaEstadoCuentaSaldosEfectivoServiceImplTest extends BaseTestCase {
	/**
	 * Prueba la funcionalidad de la consulta de estado de cuenta de cuentas
	 * nombradas y controladas utilizando citerios predeterminados
	 */
	public void testConsultaEstadoCuentaEfectivoNombradoYControlado() {
		
		ConsultaEstadoCuentaSaldoEfectivoService servicio = 
			(ConsultaEstadoCuentaSaldoEfectivoService)
			applicationContext.getBean("consultaEstadoCuentaEfectivoService");
		
		SaldoEfectivoDTO saldo = new SaldoEfectivoDTO();
		
		saldo.setFechaFinal(FormatUtil.stringToDate("25/01/2008"));
		saldo.setFechaInicial(FormatUtil.stringToDate("01/01/2008"));
		InstitucionDTO institucion = new InstitucionDTO();
		institucion.setId(4);
		TipoCuentaDTO tipoCuenta = new TipoCuentaDTO("C","");
		CuentaEfectivoDTO cuenta = new CuentaEfectivoDTO();
		cuenta.setInstitucion(institucion);
		cuenta.setTipoCuenta(tipoCuenta);
		cuenta.setTipoCustodia("E");
		cuenta.setNumeroCuenta("-1");
		BovedaDTO boveda = new BovedaDTO();
		DivisaDTO divisa = new DivisaDTO();
		boveda.setId(-1);
		divisa.setId(-1);
		
		saldo.setCuenta(cuenta);
		saldo.setBoveda(boveda);
		saldo.setDivisa(divisa);
		
		List<Long> cuentas = servicio.buscarCuentasDeEfectivo(saldo);
		List<Long> divisas = servicio.buscarDivisasDeSaldosControlados(saldo,false);
		List<EstadoCuentaEfectivoPorDivisaDTO> reg =  servicio.consultarEstadoDeCuentaSaldosControlados(saldo, divisas);
		assertTrue(reg.size() > 0);
		cuenta.getTipoCuenta().setId("N");
		
		cuentas = servicio.buscarCuentasDeEfectivo(saldo);
		divisas = servicio.buscarDivisasDeSaldosNombrados(saldo,false);
		reg =  servicio.consultarEstadoDeCuentaSaldosNombrada(saldo, divisas);
		
		assertTrue(reg.size() > 0);
	}
	
	
}
