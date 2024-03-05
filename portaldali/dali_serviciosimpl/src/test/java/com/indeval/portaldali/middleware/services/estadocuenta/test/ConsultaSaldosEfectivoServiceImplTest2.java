/**
 * 2H Software - Bursatec
 * 
 * Sistema de Consulta de Estados de Cuenta
 *
 * Jan 14, 2008
 *
 */
package com.indeval.portaldali.middleware.services.estadocuenta.test;

import java.util.Map;

import com.indeval.portaldali.middleware.dto.SaldoEfectivoDTO;
import com.indeval.portaldali.middleware.services.BaseTestCase;
import com.indeval.portaldali.middleware.services.common.util.FormatUtil;
import com.indeval.portaldali.middleware.services.estadocuenta.ConsultaSaldosEfectivoService;
import com.indeval.portaldali.middleware.services.util.test.DTOUtil;


/**
 * Prueba unitaria para el servicio ConsultaSaldoEfectivoService
 * @author Emigdio Hernández
 * 
 */
public class ConsultaSaldosEfectivoServiceImplTest2 extends BaseTestCase {

	
	/**
	 * Servicio de negocio sobre el cual se efectuaran las pruebas unitarias
	 */
	private ConsultaSaldosEfectivoService consultaSaldosEfectivoService = null;
	
	
	public void testConsultaTotalesSaldo(){
		
		consultaSaldosEfectivoService = (ConsultaSaldosEfectivoService)getApplicationContext().getBean("consultaSaldosEfectivoService");
		
		SaldoEfectivoDTO criterio = crearSaldoEfectivoDTOControlada();
		
		criterio.getCuenta().getInstitucion().setId(-1);
		
		
		Map<String,Number> res = consultaSaldosEfectivoService.obtenerTotalesConsultaSaldo(criterio);
		
		criterio = crearSaldoEfectivoDTONombrada();
		
		res = consultaSaldosEfectivoService.obtenerTotalesConsultaSaldo(criterio);
		
		
	}
	
	/**
	 * Crea un DTO de {@link SaldoEfectivoDTO} para ejecutar las pruebas sobre saldos nombradas
	 * @return DTO con los datos necesarios para ejecutar las pruebas 
	 */
	private SaldoEfectivoDTO crearSaldoEfectivoDTONombrada() {
		SaldoEfectivoDTO criterio = DTOUtil.crearSaldoEfectivoDTO("N", "P");		
		//Colocamos la fecha
		criterio.setFecha(FormatUtil.stringToDate("01/02/2008"));
		//Colocamos la institucion
		criterio.getCuenta().getInstitucion().setId(1);		
		//Colocamos el número de cuenta
		criterio.getCuenta().setNumeroCuenta("010012001");
		//Colocamos la divisa
		criterio.getDivisa().setId(1);
		//Colocamos la boveda
		criterio.getBoveda().setId(18);
		return criterio;
	}
	
	/**
	 * Crea un DTO de {@link SaldoEfectivoDTO} para ejecutar las pruebas sobre saldos controladas
	 * @return DTO con los datos necesarios para ejecutar las pruebas 
	 */	
	private SaldoEfectivoDTO crearSaldoEfectivoDTOControlada() {
		SaldoEfectivoDTO criterio = DTOUtil.crearSaldoEfectivoDTO("C", "P");
		//Colocamos la fecha
		criterio.setFecha(FormatUtil.stringToDate("01/02/2008"));
		//Colocamos la institucion
		criterio.getCuenta().getInstitucion().setId(1);		
		//Colocamos el número de cuenta
		criterio.getCuenta().setNumeroCuenta("010019005");
		//Colocamos la divisa
		criterio.getDivisa().setId(2);
		//Colocamos la boveda
		criterio.getBoveda().setId(17);
		return criterio;
	}	
	
	
}
