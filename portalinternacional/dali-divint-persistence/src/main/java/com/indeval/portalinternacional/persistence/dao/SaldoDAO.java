// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao;

import com.indeval.portalinternacional.middleware.servicios.dto.CriterioOrdenamientoDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.EstadoPaginacionDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.SaldoEfectivoDTO;

import java.util.List;
import java.util.Map;

public interface SaldoDAO {
	/**
	 * Obtiene una lista de objetos de tipo SaldoEfectivoDTO con los datos de los 
	 * saldos de efectivo nombradas.
	 * 
	 * @param saldo   DTO con los parámetros de búsqueda de saldos (fecha, cuenta, divisa y bóveda).
	 * @param paginacion Estado de la paginación que se debe considerar para realizar la consulta
	 * @param orden Criterios de ordenamiento que se deben considerar para realizar la consulta
	 * @return    Lista de objetos de tipo SaldoEfectivoDTO con los datos de los 
	 * saldos de efectivo nombrados.
	 */
	List<SaldoEfectivoDTO> consultarSaldosNombradas(SaldoEfectivoDTO saldo, EstadoPaginacionDTO paginacion, CriterioOrdenamientoDTO orden);
	
	/**
	 * Obtiene una lista de objetos de tipo SaldoEfectivoDTO con los datos de los 
	 * saldos de efectivo controladas.
	 * 
	 * @param saldo   DTO con los parámetros de búsqueda de saldos (fecha, cuenta, divisa y bóveda).
	 * @param paginacion Estado de la paginación que se debe considerar para realizar la consulta
	 * @param orden Criterios de ordenamiento que se deben considerar para realizar la consulta
	 * @return    Lista de objetos de tipo SaldoEfectivoDTO con los datos de los 
	 * saldos de efectivo nombrados.
	 */
	List<SaldoEfectivoDTO> consultarSaldosControladas(SaldoEfectivoDTO saldo, EstadoPaginacionDTO paginacion, CriterioOrdenamientoDTO orden);
	/**
	 * Obtiene el número de registros obtenidos en una consulta de saldos de cuentas nombradas basado en el criterio
	 * recibido como parámetro
	 * @param criterio DTO con el criterio de búsqueda requerido
	 * @return número de registros obtenidos como resultados de la consulta
	 */
	int obtenerProyeccionConsultaSaldosNombradas(SaldoEfectivoDTO criterio);
	/**
	 * Obtiene el número de registros obtenidos en una consulta de saldos de cuentas controladas basado en el criterio
	 * recibido como parámetro
	 * @param criterio DTO con el criterio de búsqueda requerido
	 * @return número de registros obtenidos como resultados de la consulta
	 */
	int obtenerProyeccionConsultaSaldosControladas(SaldoEfectivoDTO criterio);
	/**
	 * Obtiene un conjunto de resultados con los distintos ids de divisa  que produce la consulta de saldos
	 * de efectivo, ya sea nombrados o controlados.
	 * @param criterio Criterio para emitir la consulta de saldos de efectivo
	 * @return Lista con valores de los ids de las divisas 
	 */
	List<Long> obtenerIdentificadoresValidosParaDivisa(SaldoEfectivoDTO criterio);
	
	/**
	 * Obtiene un conjunto de resultados con los distintos ids de boveda  que produce la consulta de saldos
	 * de efectivo, ya sea nombrados o controlados.
	 * @param criterio Criterio para emitir la consulta de saldos de efectivo
	 * @return Lista con valores de los ids de las  bovedas
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
	 * Consulta un saldo asociado a una cuenta controlada basado en su Identificador único
	 * @param idSaldo Identificador del saldo
	 * @return DTO con los datos del saldo de efectivo consultado, null en caso de no localizar
	 * un saldo con el id indicado
	 */
	SaldoEfectivoDTO obtenerSaldoEfectivoControladaPorId(long idSaldo);
	/**
	 * Consulta un saldo asociado a una cuenta nombrada basado en su Identificador único
	 * @param idSaldo Identificador del saldo
	 * @return DTO con los datos del saldo de efectivo consultado, null en caso de no localizar
	 * un saldo con el id indicado
	 */
	SaldoEfectivoDTO obtenerSaldoEfectivoNombradaPorId(long idSaldo);
	
	/**
	 * Consulta las cifras totales de una consulta de saldos controlada sin considerar paginación.
	 * Los valores retornados en el mapa son:
	 * Llave          				Valor
	 * 
	 * saldoTotal   			sumatoria de saldos (Double)
	 * 
	 * @param criterio Criterio de consulta
	 * @return Mapa con los resultados de los totales de la consulta.
	 */
	Map<String,Number> obtenerTotalesConsultaSaldoControlada(SaldoEfectivoDTO criterio);
	/**
	 * Consulta las cifras totales de una consulta de saldos sin considerar paginación.
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
	Map<String,Number> obtenerTotalesConsultaSaldoNombrada(SaldoEfectivoDTO criterio);
	
	
	// Para datos historicos
	

	/**
	 * Obtiene una lista de objetos de tipo SaldoEfectivoDTO con los datos de los 
	 * saldos de efectivo nombradas del historico.
	 * 
	 * @param saldo   DTO con los parámetros de búsqueda de saldos (fecha, cuenta, divisa y bóveda).
	 * @param paginacion Estado de la paginación que se debe considerar para realizar la consulta
	 * @param orden Criterios de ordenamiento que se deben considerar para realizar la consulta
	 * @return    Lista de objetos de tipo SaldoEfectivoDTO con los datos de los 
	 * saldos de efectivo nombrados.
	 */
	List<SaldoEfectivoDTO> consultarSaldosNombradasHistorico(SaldoEfectivoDTO saldo, EstadoPaginacionDTO paginacion, CriterioOrdenamientoDTO orden);
	
	/**
	 * Obtiene una lista de objetos de tipo SaldoEfectivoDTO con los datos de los 
	 * saldos de efectivo controladas del historico.
	 * 
	 * @param saldo   DTO con los parámetros de búsqueda de saldos (fecha, cuenta, divisa y bóveda).
	 * @param paginacion Estado de la paginación que se debe considerar para realizar la consulta
	 * @param orden Criterios de ordenamiento que se deben considerar para realizar la consulta
	 * @return    Lista de objetos de tipo SaldoEfectivoDTO con los datos de los 
	 * saldos de efectivo nombrados.
	 */
	List<SaldoEfectivoDTO> consultarSaldosControladasHistorico(SaldoEfectivoDTO saldo, EstadoPaginacionDTO paginacion, CriterioOrdenamientoDTO orden);
	/**
	 * Obtiene el número de registros obtenidos en una consulta de saldos historicos de cuentas nombradas basado en el criterio
	 * recibido como parámetro
	 * @param criterio DTO con el criterio de búsqueda requerido
	 * @return número de registros obtenidos como resultados de la consulta
	 */
	int obtenerProyeccionConsultaSaldosNombradasHistorico(SaldoEfectivoDTO criterio);
	/**
	 * Obtiene el número de registros obtenidos en una consulta de saldos historicos de cuentas controladas basado en el criterio
	 * recibido como parámetro
	 * @param criterio DTO con el criterio de búsqueda requerido
	 * @return número de registros obtenidos como resultados de la consulta
	 */
	int obtenerProyeccionConsultaSaldosControladasHistorico(SaldoEfectivoDTO criterio);
	/**
	 * Obtiene un conjunto de resultados con los distintos ids de divisa  que produce la consulta de saldos historicos
	 * de efectivo, ya sea nombrados o controlados.
	 * @param criterio Criterio para emitir la consulta de saldos de efectivo
	 * @return Lista con valores de los ids de las divisas 
	 */
	List<Long> obtenerIdentificadoresValidosParaDivisaHistorico(SaldoEfectivoDTO criterio);
	
	/**
	 * Obtiene un conjunto de resultados con los distintos ids de boveda  que produce la consulta de saldos historicos
	 * de efectivo, ya sea nombrados o controlados.
	 * @param criterio Criterio para emitir la consulta de saldos de efectivo
	 * @return Lista con valores de los ids de las  bovedas
	 */
	List<Long> obtenerIdentificadoresValidosParaBovedaHistorico(SaldoEfectivoDTO criterio);
	
	/**
	 * Obtiene un conjunto de resultados con los distintos ids de cuenta  que produce la consulta de saldos historicos
	 * de efectivo, ya sea nombrados o controlados.
	 * @param criterio Criterio para emitir la consulta de saldos de efectivo
	 * @return Lista con valores de los ids de las cuentas
	 */
	List<Long> obtenerIdentificadoresValidosParaCuentaHistorico(SaldoEfectivoDTO criterio);
	
	/**
	 * Consulta las cifras totales de una consulta de saldos historicos controlada sin considerar paginación.
	 * Los valores retornados en el mapa son:
	 * Llave          				Valor
	 * 
	 * saldoTotal   			sumatoria de saldos (Double)
	 * 
	 * @param criterio Criterio de consulta
	 * @return Mapa con los resultados de los totales de la consulta.
	 */
	Map<String,Number> obtenerTotalesConsultaSaldoControladaHistorico(SaldoEfectivoDTO criterio);
	/**
	 * Consulta las cifras totales de una consulta de saldos historicos sin considerar paginación.
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
	Map<String,Number> obtenerTotalesConsultaSaldoNombradaHistorico(SaldoEfectivoDTO criterio);
	
	/**
	 * Obtiene un saldo de cuenta nombrada del histórico por medio de su ID
	 * 
	 * @param idSaldo el identificador del saldo en el histórico a consultar.
	 * @return un DTO que representa el saldo del histórico.
	 */
	SaldoEfectivoDTO obtenerSaldoEfectivoNombradaHistoricoPorId(long idSaldo);
	
	/**
	 * Obtiene un saldo de cuenta controlada del histórico por medio de su ID
	 * 
	 * @param idSaldo el identificador del saldo en el histórico a consultar.
	 * @return un DTO que representa el saldo del histórico.
	 */
	SaldoEfectivoDTO obtenerSaldoEfectivoControladaHistoricoPorId(long idSaldo);
	
}
