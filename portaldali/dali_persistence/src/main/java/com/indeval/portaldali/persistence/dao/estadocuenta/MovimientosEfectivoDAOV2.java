/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.persistence.dao.estadocuenta;

import java.math.BigInteger;
import java.util.List;

import com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosEfectivoDTO;
import com.indeval.portaldali.middleware.dto.RegistroContableSaldoControladaDTO;
import com.indeval.portaldali.middleware.dto.RegistroContableSaldoNombradaDTO;

/**
 * Interfaz que define las operaciones necesarias para la consulta de movimientos de efectivo.
 * 
 * @author Emigdio Hernández Rodríguez
 * @version 1.0
 */
public interface MovimientosEfectivoDAOV2 {
	/**
	 * Obtiene una lista de registros contables de efectivo  controladas, para formar la consulta de movimientos
	 * de efectivo. Los registros contables son los que se refieren a los saldos de efectivo indicados
	 * como parámetro.
	 * @param criterio Criterio para la consulta de registros contables
	 * @param idsDivisas Lista de divisas válidas para las que se debe de filtrar la consulta.
	 * @return Listado de registros contables que representan los movimientos de efectivo.
	 */
	List<RegistroContableSaldoControladaDTO> buscarRegistrosContablesDeEfectivoControlada(final CriterioConsultaMovimientosEfectivoDTO criterio, final List<Long> idsDivisas);
	
	/**
	 * Obtiene una lista de registros contables de efectivo nombrada, para formar la consulta de movimientos
	 * de efectivo. Los registros contables son los que se refieren a los saldos de efectivo indicados
	 * como parámetro.
	 * @param criterio Criterio para la consulta de registros contables
	 * @param idsDivisas Lista de divisas válidas para las que se debe de filtrar la consulta.
	 * @return Listado de registros contables que representan los movimientos de efectivo.
	 */
	List<RegistroContableSaldoNombradaDTO> buscarRegistrosContablesDeEfectivoNombrada(final CriterioConsultaMovimientosEfectivoDTO criterio, final List<Long> idsDivisas);
	
	/**
	 * Obtiene los diferentes identificadores de las cuentas obtenidas en una consulta de movimientos de 
	 * efectivo de cuentas controladas
	 * 
	 * @param criterio Criterio para realizar la consulta.
	 * @return Listado de identificadores de cuentas obtenidas de la consulta.
	 */	
	List<Long> buscarCuentasDeRegistrosContablesDeEfectivoControlada(final CriterioConsultaMovimientosEfectivoDTO criterio);
	
	/**
	 * Obtiene los diferentes identificadores de las cuentas obtenidas en una consulta de movimientos de 
	 * efectivo de cuentas nombradas
	 * 
	 * @param criterio Criterio para realizar la consulta.
	 * @return Listado de identificadores de cuentas obtenidas de la consulta.
	 */	
	List<Long> buscarCuentasDeRegistrosContablesDeEfectivoNombrada(final CriterioConsultaMovimientosEfectivoDTO criterio);
	
	/**
	 * Obtiene los diferentes identificadores de las divisas obtenidas en una consulta de movimientos de efectivo controlados
	 * 
	 * @param criterio Criterio para realizar la consulta.
	 * @return Listado de identificadores de divisas obtenidas de la consulta.
	 */	
	List<Long> buscarDivisasDeRegistrosContablesDeEfectivoControlada(final CriterioConsultaMovimientosEfectivoDTO criterio);
	
	/**
	 * Obtiene los diferentes identificadores de las divisas obtenidas en una consulta de movimientos de efectivo nombradas
	 * 
	 * @param criterio Criterio para realizar la consulta.
	 * @return Listado de identificadores de divisas obtenidas de la consulta.
	 */	
	List<Long> buscarDivisasDeRegistrosContablesDeEfectivoNombrada(final CriterioConsultaMovimientosEfectivoDTO criterio);
	
	/**
	 * Obtiene el total de registro resultado de una consulta de movimientos de efectivo de cuentas
	 * controladas dados ciertos
	 * criterios de búsqueda.
	 * @param criterio Criterios de búsqueda para realizar la consulta
	 * @return número de registros encontrados
	 */
	long obtenerProyeccionDeRegistrosContablesDeSaldosControlada(final CriterioConsultaMovimientosEfectivoDTO criterio);
	
	/**
	 * Obtiene el total de registro resultado de una consulta de movimientos de efectivo de cuentas
	 * nombradas dados ciertos
	 * criterios de búsqueda.
	 * @param criterio Criterios de búsqueda para realizar la consulta
	 * @return número de registros encontrados
	 */
	long obtenerProyeccionDeRegistrosContablesDeSaldosNombrada(final CriterioConsultaMovimientosEfectivoDTO criterio);	
	
	
	
	
	// Para datos historicos
	
	

	/**
	 * Obtiene una lista de registros contables historicos de efectivo controladas, para formar la consulta de movimientos
	 * de efectivo. Los registros contables son los que se refieren a los saldos de efectivo indicados
	 * como parámetro.
	 * @param criterio Criterio para la consulta de registros contables
	 * @param idsDivisas Lista de divisas válidas para las que se debe de filtrar la consulta.
	 * @return Listado de registros contables que representan los movimientos de efectivo.
	 */
	List<RegistroContableSaldoControladaDTO> buscarRegistrosContablesDeEfectivoControladaHistorico(final CriterioConsultaMovimientosEfectivoDTO criterio, final List<Long> idsDivisas);
	
	/**
	 * Obtiene una lista de registros contables historicos de efectivo nombrada, para formar la consulta de movimientos
	 * de efectivo. Los registros contables son los que se refieren a los saldos de efectivo indicados
	 * como parámetro.
	 * @param criterio Criterio para la consulta de registros contables
	 * @param idsDivisas Lista de divisas válidas para las que se debe de filtrar la consulta.
	 * @return Listado de registros contables que representan los movimientos de efectivo.
	 */
	List<RegistroContableSaldoNombradaDTO> buscarRegistrosContablesDeEfectivoNombradaHistorico(final CriterioConsultaMovimientosEfectivoDTO criterio, final List<Long> idsDivisas);
	
	/**
	 * Obtiene los diferentes identificadores de las cuentas obtenidas en una consulta historica de movimientos de 
	 * efectivo de cuentas controladas
	 * 
	 * @param criterio Criterio para realizar la consulta.
	 * @return Listado de identificadores de cuentas obtenidas de la consulta.
	 */	
	List<Long> buscarCuentasDeRegistrosContablesDeEfectivoControladaHistorico(final CriterioConsultaMovimientosEfectivoDTO criterio);
	
	/**
	 * Obtiene los diferentes identificadores de las cuentas obtenidas en una consulta historica de movimientos de 
	 * efectivo de cuentas nombradas
	 * 
	 * @param criterio Criterio para realizar la consulta.
	 * @return Listado de identificadores de cuentas obtenidas de la consulta.
	 */	
	List<Long> buscarCuentasDeRegistrosContablesDeEfectivoNombradaHistorico(final CriterioConsultaMovimientosEfectivoDTO criterio);
	
	/**
	 * Obtiene los diferentes identificadores de las divisas obtenidas en una consulta historica de movimientos de efectivo controlados
	 * 
	 * @param criterio Criterio para realizar la consulta.
	 * @return Listado de identificadores de divisas obtenidas de la consulta.
	 */	
	List<Long> buscarDivisasDeRegistrosContablesDeEfectivoControladaHistorico(final CriterioConsultaMovimientosEfectivoDTO criterio);
	
	/**
	 * Obtiene los diferentes identificadores de las divisas obtenidas en una consulta historica de movimientos de efectivo nombradas
	 * 
	 * @param criterio Criterio para realizar la consulta.
	 * @return Listado de identificadores de divisas obtenidas de la consulta.
	 */	
	List<Long> buscarDivisasDeRegistrosContablesDeEfectivoNombradaHistorico(final CriterioConsultaMovimientosEfectivoDTO criterio);
	
	/**
	 * Obtiene el total de registro resultado de una consulta de movimientos historicos de efectivo de cuentas
	 * controladas dados ciertos
	 * criterios de búsqueda.
	 * @param criterio Criterios de búsqueda para realizar la consulta
	 * @return número de registros encontrados
	 */
	long obtenerProyeccionDeRegistrosContablesDeSaldosControladaHistorico(final CriterioConsultaMovimientosEfectivoDTO criterio);
	
	/**
	 * Obtiene el total de registro resultado de una consulta de movimientos historicos de efectivo de cuentas
	 * nombradas dados ciertos
	 * criterios de búsqueda.
	 * @param criterio Criterios de búsqueda para realizar la consulta
	 * @return número de registros encontrados
	 */
	long obtenerProyeccionDeRegistrosContablesDeSaldosNombradaHistorico(final CriterioConsultaMovimientosEfectivoDTO criterio);	
	
	/**
	 * Obtiene el listado total de las divisas
	 * 
	 * @return Lista de divisas
	 */
	List<BigInteger> buscarDivisas();
	
}
