// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portalinternacional.middleware.servicios.dto.CriterioOrdenamientoDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.EstadoPaginacionDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.SaldoEfectivoDTO;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface ConsultaSaldosEfectivoService {
	/**
	 * Invoca la búsqueda correspondiente de saldos de efectivo de cuentas nombradas o controladas
	 * dependiendo de los criterios de búsqueda recibidos como parámetros.
	 * Consulta la base de datos para obtener los valores de los saldos de cuentas nombradas 
	 * dependiendo de los criterios enviados a la consulta.
	 * Obtiene el valor y los datos de los saldos de cuentas nombradas requeridas por los criterios 
	 * al corte de cierto día, es decir, se toma el valor del último estado de la posición al día 
	 * consultado.
	 * 
	 * @param criterios Criterios de búsqueda
	 * @param paginacion Estado de la paginación a tomarse en cuenta durante la consulta de resultados
	 * @param orden Criterio de ordenamiento a tomarse en cuenta durante la ejecución de la consulta
	 * @return Lista de DTO con los valores obtenidos en la consulta.
	 */
	List<SaldoEfectivoDTO> consultarSaldosEfectivo(SaldoEfectivoDTO criterios, EstadoPaginacionDTO paginacion, CriterioOrdenamientoDTO orden);
	
	/**
	 * Invoca la búsqueda correspondiente de saldos de efectivo de cuentas nombradas o controladas
	 * dependiendo de los criterios de búsqueda recibidos como parámetros.
	 * Consulta la base de datos para obtener los valores de los saldos de cuentas nombradas 
	 * dependiendo de los criterios enviados a la consulta.
	 * Obtiene el valor y los datos de los saldos de cuentas nombradas requeridas por los criterios 
	 * al corte de cierto día, es decir, se toma el valor del último estado de la posición al día 
	 * consultado.
	 * 
	 * El mismo metodo que el anterior solo que este si pasa por el filtro del Advice para que pueda
	 * dejar bitacora por que se utiliz en varios lugares
	 * 
	 * @param criterios Criterios de búsqueda
	 * @param paginacion Estado de la paginación a tomarse en cuenta durante la consulta de resultados
	 * @param orden Criterio de ordenamiento a tomarse en cuenta durante la ejecución de la consulta
	 * @return Lista de DTO con los valores obtenidos en la consulta.
	 */
	List<SaldoEfectivoDTO> consultarSaldosEfectivoBitacora(SaldoEfectivoDTO criterios, EstadoPaginacionDTO paginacion, CriterioOrdenamientoDTO orden);
	
	/**
	 * Obtiene el número de registros arrojados como resultado de la consulta de saldos de efectivo
	 * de cuentas nombradas o controladas, basado en los criterios de consulta enviados como parámetros.
	 * @param criterios  Criterios necesarios para la ejecución de la consulta
	 * @return número de registros obtenidos como resultado
	 */
	int obtenerProyeccionConsultaSaldosEfectivo(SaldoEfectivoDTO criterios, Boolean debeDejarLog);
	
	/**
	 * Obtiene un conjunto de resultados con los distintos ids de divisa  que produce la consulta de saldos
	 * de efectivo, ya sea nombrados o controlados.
	 * @param criterio Criterio para emitir la consulta de saldos de efectivo
	 * @return Lista con valores de los ids de las divisas y las bovedas
	 */
	List<Long> obtenerIdentificadoresValidosParaDivisa(SaldoEfectivoDTO criterio);
	
	/**
	 * Obtiene un conjunto de resultados con los distintos ids de boveda  que produce la consulta de saldos
	 * de efectivo, ya sea nombrados o controlados.
	 * @param criterio Criterio para emitir la consulta de saldos de efectivo
	 * @return Lista con valores de los ids de las divisas y las bovedas
	 */
	List<Long> obtenerIdentificadoresValidosParaBoveda(SaldoEfectivoDTO criterio);
	
	/**
	 * Obtiene un conjunto de resultados con los distintos ids de cuenta  que produce la consulta de saldos
	 * de efectivo, ya sea nombrados o controlados.
	 * @param criterio Criterio para emitir la consulta de saldos de efectivo
	 * @return Lista con valores de los ids de las cuentas
	 */
	List<Long> obtenerIdentificadoresValidosParaCuenta(SaldoEfectivoDTO criterio);
	
	/**
	 * Consulta las cifras totales de una consulta de saldos nombradas sin considerar paginación.
	 * Los valores retornados en el mapa son:
	 * Llave          				Valor
	 * 
	 * saldoTotal   			sumatoria de saldos (Double)
	 * saldoTotalDisponible		sumatoria de saldos  disponibles (Double)
	 * saldoTotalNoDisponible	sumatoria de saldos  no disponibles (Double)
	 * 
	 * @param criterio Criterio de consulta
	 * @return Mapa con los resultados de los totales de la consulta.
	 */
	Map<String,Number> obtenerTotalesConsultaSaldo(SaldoEfectivoDTO criterio);
	
	
	/**
	  * Obtiene el saldo de efectivo para una boveda y divisa especifica
	  * 
	 * @param id
	 * @param folio
	 * @param idBoveda
	 * @param idDivisa
	 * @return BigDecimal
	 */
	public BigDecimal getSaldoEfectivo(String id, String folio, BigInteger idBoveda, BigInteger idDivisa);

	

}
