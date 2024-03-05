/**
 * 2H Software SA de CV
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Fecha de creación: Jan 3, 2008
 */
package com.indeval.portaldali.middleware.services.estadocuenta;

import java.util.List;

import com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosEfectivoDTO;
import com.indeval.portaldali.middleware.dto.EstadoCuentaEfectivoPorDivisaDTO;
import com.indeval.portaldali.middleware.dto.RegistroContableSaldoControladaDTO;
import com.indeval.portaldali.middleware.dto.RegistroContableSaldoNombradaDTO;

/**
 * Interface que define el contrato del servicio de negocio para las operaciones
 * relacionadas con la consulta de movimientos que refleja las operaciones
 * realizadas en cuentas de efectivo.
 * 
 * @author Sandra Flores Arrieta
 * @version 1.0
 * 
 */
public interface ConsultaMovimientosEfectivoService {

	/**
	 * Obtiene los diferentes identificadores de las cuentas obtenidas en una
	 * consulta de movimientos de efectivo.
	 * 
	 * @param criterio
	 *            Criterio para realizar la consulta.
	 * @return Listado de identificadores de cuentas obtenidas de la consulta.
	 */
	List<Long> buscarCuentasDeRegistrosContablesDeEfectivo(CriterioConsultaMovimientosEfectivoDTO criterio);

	/**
	 * Obtiene los diferentes identificadores de las divisas obtenidas en una
	 * consulta de movimientos de efectivo.
	 * 
	 * @param criterio
	 *            Criterio para realizar la consulta.
	 * @return Listado de identificadores de divisas obtenidas de la consulta.
	 */
	List<Long> buscarDivisasDeRegistrosContablesDeEfectivo(CriterioConsultaMovimientosEfectivoDTO criterio);

	/**
	 * Invoca la búsqueda correspondiente de registros contables que reflejan
	 * las operaciones relacionadas a efectivo según los criterios de búsqueda
	 * recibidos como parámetros. Consulta la base de datos para obtener los
	 * valores de las operaciones que se registran en un lapso de tiempo
	 * correspondientes a un conjunto de salos de efectivo.
	 * 
	 * @param criterio
	 *            el DTO con los criterios para realizar la consulta
	 * @param idsDivisas
	 *            la lista de identificadores de divisas a considerar para la
	 *            consulta del estado de cuenta.
	 * @return Lista de objetos {@link EstadoCuentaEfectivoPorDivisaDTO} los
	 *         cuales contienen el detalle del estado de cuenta por divisa y
	 *         bóveda.
	 */
	List<EstadoCuentaEfectivoPorDivisaDTO> consultarMovimientosDeEfectivo(CriterioConsultaMovimientosEfectivoDTO criterio, List<Long> idsDivisas, Boolean debeDejarLog);

	/**
	 * Invoca la búsqueda correspondiente de registros contables que reflejan
	 * las operaciones relacionadas a efectivo según los criterios de búsqueda
	 * recibidos como parámetros. Consulta la base de datos para obtener los
	 * valores de las operaciones que se registran en un lapso de tiempo
	 * correspondientes a un conjunto de salos de efectivo. Los resultados no se
	 * agruparán por emisión ni por bóveda.
	 * 
	 * @param criterio
	 *            el DTO con los criterios para realizar la consulta
	 * @param idsDivisas
	 *            la lista de identificadores de divisas a considerar para la
	 *            consulta del estado de cuenta.
	 * @return Lista de con los registros contrables los cuales contienen el
	 *         detalle del estado de cuenta por divisa y bóveda.
	 */
	List<RegistroContableSaldoNombradaDTO> consultarMovimientosDeEfectivoNombradasSinAgrupar(CriterioConsultaMovimientosEfectivoDTO criterio,
			List<Long> idsDivisas);

	/**
	 * Invoca la búsqueda correspondiente de registros contables que reflejan
	 * las operaciones relacionadas a efectivo según los criterios de búsqueda
	 * recibidos como parámetros. Consulta la base de datos para obtener los
	 * valores de las operaciones que se registran en un lapso de tiempo
	 * correspondientes a un conjunto de salos de efectivo. Los resultados no se
	 * agruparán por emisión ni por bóveda.
	 * 
	 * @param criterio
	 *            el DTO con los criterios para realizar la consulta
	 * @param idsDivisas
	 *            la lista de identificadores de divisas a considerar para la
	 *            consulta del estado de cuenta.
	 * @return Lista de con los registros contrables los cuales contienen el
	 *         detalle del estado de cuenta por divisa y bóveda.
	 */
	List<RegistroContableSaldoControladaDTO> consultarMovimientosDeEfectivoControladasSinAgrupar(CriterioConsultaMovimientosEfectivoDTO criterio,
			List<Long> idsDivisas);

	/**
	 * Obtiene el total de registro resultado de una consulta de movimientos de
	 * efectivo dados ciertos criterios de búsqueda.
	 * 
	 * @param criterio
	 *            Criterios de búsqueda para realizar la consulta
	 * @return número de registros encontrados
	 */
	long obtenerProyeccionDeRegistrosContablesDeMovimientosDeEfectivo(CriterioConsultaMovimientosEfectivoDTO criterio);
}
