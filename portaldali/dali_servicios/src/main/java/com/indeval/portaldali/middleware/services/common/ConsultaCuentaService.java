/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ConsultaCuentaService.java
 * 29/02/2008
 */
package com.indeval.portaldali.middleware.services.common;


import java.util.List;

import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.CuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;

/**
 * Define la funcionalidad del servicio de negocio encargado de realizar las
 * operaciones de consulta a las tablas de cuentas controladas y cuentas
 * nombradas de valores y efectivo.
 * 
 * @author Emigdio Hernández
 * @version 1.0
 */
public interface ConsultaCuentaService {

	/**
	 * Obtiene una proyección del total de resultados obtenidos de la consulta
	 * de búsqueda de cuentas controladas buscando por cliente, tipo de tenencia
	 * y tipo de custodia. De estos, sólo el valor de tipo de tenencia es
	 * opcional.
	 * 
	 * @param criterio
	 *            Criterios de consulta de la cuenta controlada
	 * @return el número de resultados que se obtendrán de la consulta.
	 */
	 int obtenerProyeccionDeBusquedaDeCuentasControladas(
			CuentaDTO criterio);
	/**
	 * Obtiene una proyección del total de resultados obtenidos de la consulta
	 * de búsqueda de cuentas controladas buscando por cliente, tipo de tenencia
	 * y tipo de custodia. De estos, sólo el valor de tipo de tenencia es
	 * opcional.
	 * Adicionalment este método considera un conjunto de ID's de cuentas que 
	 * requieren ser filtrados
	 * @param criterio
	 *            Criterios de consulta de la cuenta controlada.
	 * @param idsCuentaValidos conjunto de cuentas válidas para la consulta
	 * @return el número de resultados que se obtendrán de la consulta.
	 */
	int obtenerProyeccionDeBusquedaDeCuentasControladas(
			CuentaDTO criterio,List<Long> idsCuentaValidos);
	/**
	 * Obtiene una proyección del total de resultados obtenidos de la consulta
	 * de búsqueda de cuentas nombradas buscando por cliente, tipo de tenencia y
	 * tipo de custodia. De estos, sólo el valor de tipo de tenencia es
	 * opcional.
	 * 
	 * @param criterio
	 *            Criterios de consulta de la cuenta controlada
	 * @return el número de resultados que se obtendrán de la consulta.
	 */
	int obtenerProyeccionDeBusquedaDeCuentasNombradas(CuentaDTO criterio);

	/**
	 * Obtiene una proyección del total de resultados obtenidos de la consulta
	 * de búsqueda de cuentas nombradas buscando por cliente, tipo de tenencia y
	 * tipo de custodia. De estos, sólo el valor de tipo de tenencia es
	 * opcional.
	 * Adicionalmente este método filtra las cuentas obtenidas utilizando
	 * el criterio de idsCuentasValidos
	 * @param criterio Criterios de consulta de la cuenta controlada.
	 * @param idsCuentaValidos conjunto de cuentas válidas para la consulta.
	 * @return el número de resultados que se obtendrán de la consulta.
	 */
	int obtenerProyeccionDeBusquedaDeCuentasNombradas(CuentaDTO criterio,
			List<Long> idsCuentaValidos);

	/**
	 * Obtiene una lista de objetos de tipo CuentaDTO que contiene las cuentas
	 * controladas buscando por cliente, tipo de tenencia y tipo de custodia. De
	 * estos, sólo el valor de tipo de tenencia es opcional.
	 * 
	 * @param cuenta
	 *            DTO con los criterios de búsqueda
	 * @param estadoPaginacion
	 *            DTO con los datos de la paginación actual. <code>null</code>
	 *            si no se requiere de la paginación.
	 * @return Lista de objetos de tipo CuentaDTO que contiene las cuentas
	 *         controladas buscando por cliente, tipo de tenencia y tipo de
	 *         custodia.
	 */
	public List<CuentaDTO> buscarCuentasControladas(CuentaDTO criterios,
			EstadoPaginacionDTO estadoPaginacion);

	/**
	 * Obtiene una lista de objetos de tipo CuentaDTO que contiene las cuentas
	 * nombradas buscando por cliente, tipo de tenencia y tipo de custodia. De
	 * estos, sólo el valor de tipo de tenencia es opcional.
	 * 
	 * @param cuenta
	 *            DTO con los criterios de búsqueda
	 * @param estadoPaginacion
	 *            DTO con los datos de la paginación actual. <code>null</code>
	 *            si no se requiere de la paginación.
	 * @return Lista de cuentas nombradas buscando por cliente, tipo de tenencia
	 *         y tipo de custodia.
	 */
	List<CuentaDTO> buscarCuentasNombradas(CuentaDTO criterios,
			EstadoPaginacionDTO estadoPaginacion);
	/**
	 * Obtiene una lista de cuentas nombradas buscando por cliente, tipo de
	 * tenencia y tipo de custodia. De estos, sólo el valor de tipo de tenencia
	 * es opcional.
	 * Adicionalmente este método filtra las cuentas obtenidas utilizando
	 * el criterio de idsCuentasValidos
	 * @param criterio
	 *            Criterios de consulta de la cuenta nombrada
	 * @param estadoPaginacion
	 *            DTO con los datos de la paginación actual. <code>null</code>
	 *            si no se requiere de la paginación.
	 * @param idsCuentaValidos conjunto de cuentas válidas para la consulta.
	 * @return Lista de cuentas nombradas buscando por cliente, tipo de tenencia
	 *         y tipo de custodia.
	 */
	List<CuentaDTO> buscarCuentasNombradas(CuentaDTO criterio,
			EstadoPaginacionDTO estadoPaginacion,List<Long>idsValidos);
	
	/**
	 * Obtiene una lista de cuentas controladas buscando por cliente, tipo de
	 * tenencia y tipo de custodia. De estos, sólo el valor de tipo de tenencia
	 * es opcional.
	 * Adicionalmente ste método toma en cuanta un conjunto de ids a filtrar
	 * que son enviados como parámetro
	 * @param criterio
	 *            Criterios de consulta de la cuenta controlada
	 * @param estadoPaginacion
	 *            DTO con los datos de la paginación actual. <code>null</code>
	 *            si no se requiere de la paginación.
	 * @param idsValidos Conjunto de identificadores a filtrar
	 * @return Lista de cuentas controladas buscando por numero de cuenta, tipo
	 *         de tenencia o naturaleza y tipo de cuenta
	 */
	List<CuentaDTO> buscarCuentasControladas(CuentaDTO criterio,
			EstadoPaginacionDTO estadoPaginacion,List<Long>idsValidos);
	
	
	/**
	 * Busca un conjunto de cuentas tomando como criterio un fragmento del número de cuenta y la naturaleza contable
	 * de la misma.
	 * 
	 * @param criterio el DTO con los criterios para realizar la consulta.
	 * @return una lista con objetos de tipo {@link CuentaDTO} que representan las cuentas que conciden con los criterios proporcionados.
	 */
	List<CuentaDTO> buscarCuentasPorFragmentoNumeroCuenta(CuentaDTO criterio);
	
	/**
	 * Obtiene una cuenta basado en su numero de cuenta y en el tipo de cuenta
	 * (controlada o nombrada) Recibe como parámetro un objeto del tipo
	 * {@link CuentaEfectivoDTO} con los criterios de consulta
	 * 
	 * @param criterio
	 *            DTO con los criterios de consulta
	 * @return DTO que representa los datos de la cuenta encontrada, null si no
	 *         fue localizada
	 */
	CuentaEfectivoDTO buscarCuentaEfectivoPorNumeroCuenta(
			CuentaEfectivoDTO criterio);

	/**
	 * Obtiene una lista de cuentas nombradas de tipo efectivo buscando por
	 * cliente, tipo de cuenta y tipo de custodia. Estos criterios son
	 * obligatorios.
	 * 
	 * @param criterio
	 *            DTO con los criterios de consulta
	 * @param estadoPaginacion
	 *            DTO con los datos de la paginación actual. <code>null</code>
	 *            si no se requiere de la paginación.
	 * @return DTO que representa los datos de la cuenta encontrada, null si no
	 *         fue localizada
	 */
	List<CuentaEfectivoDTO> buscarCuentasNombradasEfectivo(
			CuentaEfectivoDTO criterio,
			EstadoPaginacionDTO estadoPaginacion);
	
	/**
	 * Obtiene una proyección del total de resultados obtenidos de la consulta
	 * de búsqueda de cuentas nombradas de efectivo buscando por
	 * cliente, tipo de cuenta y tipo de custodia. Estos criterios son
	 * obligatorios.
	 * 
	 * @param criterio
	 *            Criterios de consulta de la cuenta controlada
	 * @return el número de resultados que se obtendrán de la consulta.
	 */
	int obtenerProyeccionDeBusquedaDeCuentasNombradasEfectivo(CuentaEfectivoDTO criterio);
	/**
	 * Obtiene una proyección del total de resultados obtenidos de la consulta
	 * de búsqueda de cuentas nombradas de efectivo buscando por
	 * cliente, tipo de cuenta y tipo de custodia. Estos criterios son
	 * obligatorios.
	 * Adicionalmente te método limita los resultados a las cuentas que tengan el id del conjunto
	 * de cuentas válidas enviadas como parámetro.
	 * @param criterio
	 *            Criterios de consulta de la cuenta controlada
	 * @param idsValidos Conjunto de ids para realizar filtrado de las cuentas
	 * @return el número de resultados que se obtendrán de la consulta.
	 */
	int obtenerProyeccionDeBusquedaDeCuentasNombradasEfectivo(CuentaEfectivoDTO criterio,List<Long>idsValidos);
	/**
	 * Obtiene una lista de cuentas controladas de tipo efectivo buscando por
	 * cliente, tipo de cuenta y tipo de custodia. Estos criterios son
	 * obligatorios.
	 * 
	 * @param criterio
	 *            DTO con los criterios de consulta
	 * @param estadoPaginacion
	 *            DTO con los datos de la paginación actual. <code>null</code>
	 *            si no se requiere de la paginación.
	 * @return DTO que representa los datos de la cuenta encontrada, null si no
	 *         fue localizada
	 */
	List<CuentaEfectivoDTO> buscarCuentasControladasEfectivo(
			CuentaEfectivoDTO criterio,
			EstadoPaginacionDTO estadoPaginacion);
	
	/**
	 * Obtiene una proyección del total de resultados obtenidos de la consulta
	 * de búsqueda de cuentas controladas de efectivo buscando por
	 * cliente, tipo de cuenta y tipo de custodia. Estos criterios son
	 * obligatorios.
	 * 
	 * @param criterio
	 *            Criterios de consulta de la cuenta controlada
	 * @return el número de resultados que se obtendrán de la consulta.
	 */
	int obtenerProyeccionDeBusquedaDeCuentasControladasEfectivo(CuentaEfectivoDTO criterio);
	
	/**
	 * Obtiene una proyección del total de resultados obtenidos de la consulta
	 * de búsqueda de cuentas controladas de efectivo buscando por
	 * cliente, tipo de cuenta y tipo de custodia. Estos criterios son
	 * obligatorios.
	 * Adicionalmente te método limita los resultados a las cuentas que tengan el id del conjunto
	 * de cuentas válidas enviadas como parámetro.
	 * @param criterio
	 *            Criterios de consulta de la cuenta controlada
	 * @param idsValidos Conjunto de ids para realizar filtrado de las cuentas
	 * @return el número de resultados que se obtendrán de la consulta.
	 */
	int obtenerProyeccionDeBusquedaDeCuentasControladasEfectivo(CuentaEfectivoDTO criterio,List<Long>idsValidos);
	
	/**
	 * Obtiene una lista de cuentas controladas de tipo efectivo buscando por
	 * cliente, tipo de cuenta y tipo de custodia. Estos criterios son
	 * obligatorios.
	 * Adicionalmente te método limita los resultados a las cuentas que tengan el id del conjunto
	 * de cuentas válidas enviadas como parámetro.
	 * @param criterio
	 *            DTO con los criterios de consulta
	 * @param estadoPaginacion
	 *            DTO con los datos de la paginación actual. <code>null</code>
	 *            si no se requiere de la paginación.
	 * @param  idsValidos Conjunto de ids para realizar filtrado de las cuentas
	 * @return DTO que representa los datos de la cuenta encontrada, null si no
	 *         fue localizada
	 */
	List<CuentaEfectivoDTO> buscarCuentasControladasEfectivo(
			CuentaEfectivoDTO criterio,
			EstadoPaginacionDTO estadoPaginacion,List<Long>idsValidos);
	
	/**
	 * Obtiene una lista de cuentas nombradas de tipo efectivo buscando por
	 * cliente, tipo de cuenta y tipo de custodia. Estos criterios son
	 * obligatorios.
	 * Adicionalmente este método limita los resultados según los ids
	 * enviamos como parámetro
	 * @param criterio
	 *            DTO con los criterios de consulta
	 * @param estadoPaginacion
	 *            DTO con los datos de la paginación actual. <code>null</code>
	 *            si no se requiere de la paginación.
	 * @param idsValidos Conjunto de identificadores de las cuentas que se pueden
	 * 			  obtener de la consulta.
	 * @return DTO que representa los datos de la cuenta encontrada, null si no
	 *         fue localizada
	 */
	List<CuentaEfectivoDTO> buscarCuentasNombradasEfectivo(
			CuentaEfectivoDTO criterio,
			EstadoPaginacionDTO estadoPaginacion,List<Long>idsValidos);
	
	/**
	 * Busca un conjunto de cuentas tomando como criterio un fragmento del número de cuenta y la naturaleza contable
	 * de la misma.
	 * 
	 * @param criterio el DTO con los criterios para realizar la consulta.
	 * @return una lista con objetos de tipo {@link CuentaEfectivoDTO} que representan las cuentas que conciden con los criterios proporcionados.
	 */
	List<CuentaEfectivoDTO> buscarCuentasPorFragmentoNumeroCuenta(CuentaEfectivoDTO criterio);

	
	/**
	 * Obtiene una cuenta basado en su numero de cuenta y en el tipo de cuenta
	 * (controlada o nombrada) Recibe como parámetro un objeto del tipo
	 * {@link CuentaDTO} con los criterios de consulta
	 * 
	 * @param criterio
	 *            DTO con los criterios de consulta
	 * @return DTO que representa los datos de la cuenta encontrada, null si no
	 *         fue localizada
	 */
	CuentaDTO buscarCuentaPorNumeroCuenta(CuentaDTO criterio);
	
	
	
	
	
}
