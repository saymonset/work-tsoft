/**
 * 2H Software SA de CV
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 */
package com.indeval.portaldali.middleware.services.estadocuenta;

import java.util.Date;
import java.util.List;

import com.indeval.portaldali.middleware.dto.EstadoCuentaEfectivoPorDivisaDTO;
import com.indeval.portaldali.middleware.dto.SaldoEfectivoDTO;

/**
 * Interface que define el contrato del servicio de negocio para las operaciones 
 * relacionadas con la consulta del estado de cuenta de saldo de efectivo.
 * 
 * @author Emigdio Hernández
 * @version 1.0
 *
 */
public interface ConsultaEstadoCuentaSaldoEfectivoService {
	
	/**
	 * Invoca la búsqueda correspondiente de registros contables que reflejan
	 * las operaciones relacionadas a saldos nombrados según
	 * los criterios de búsqueda recibidos como parámetros. Consulta la base de
	 * datos para obtener los valores de las operaciones que se registran en un
	 * lapso de tiempo correspondientes a un conjunto de saldos nombrados
	 * para las cuentas de una institución.
	 * 
	 * @param  criterio el DTO con los criterios para realizar la consulta
	 * @param  idsDivisas la lista de identificadores de divisas a considerar para la consulta del estado de cuenta.
	 * @return Lista de objetos {@link EstadoCuentaEfectivoPorDivisaDTO} los cuales contienen el detalle del estado de cuenta por divisa y bóveda.
	 */
	List<EstadoCuentaEfectivoPorDivisaDTO> consultarEstadoDeCuentaSaldosNombrada(SaldoEfectivoDTO saldoEfectivo, List<Long> idsDivisas);
	
	/**
	 * Busca las distintas divisas de un conjunto de saldos nombrados las cuales se consultan
	 * por cuenta, divisa y bóveda.
	 * 
	 * @param saldoEfectivo el DTO con los criterios para la consulta de saldos nombrados.
	 * @return una lista con los identificadores de las distintas emisiones de los saldos nombrados encontrados.
	 */
	List<Long> buscarDivisasDeSaldosNombrados(SaldoEfectivoDTO saldoEfectivo, Boolean debeDejarLog);
	
	/**
	 * Invoca la búsqueda correspondiente de registros contables que reflejan
	 * las operaciones relacionadas a saldos controlados según
	 * los criterios de búsqueda recibidos como parámetros. Consulta la base de
	 * datos para obtener los valores de las operaciones que se registran en un
	 * lapso de tiempo correspondientes a un conjunto de saldos controlados
	 * para las cuentas de una institución.
	 * 
	 * @param  saldoEfectivo el DTO con los criterios para realizar la consulta
	 * @param  idsDivisas la lista de identificadores de divisas a considerar para la consulta del estado de cuenta.
	 * @return Lista de objetos {@link EstadoCuentaEfectivoPorDivisaDTO} los cuales contienen el detalle del estado de cuenta por divisa y bóveda.
	 */
	List<EstadoCuentaEfectivoPorDivisaDTO> consultarEstadoDeCuentaSaldosControlados(SaldoEfectivoDTO saldoEfectivo, List<Long> idsDivisas);
	
	/**
	 * Busca las distintas divisas de un conjunto de saldos controlados las cuales se consultan
	 * por cuenta, divisa y bóveda.
	 * 
	 * @param saldoEfectivo el DTO con los criterios para la consulta de saldos controlados.
	 * @return una lista con los identificadores de las distintas emisiones de los saldos controlados encontrados.
	 */
	List<Long> buscarDivisasDeSaldosControlados(SaldoEfectivoDTO saldoEfectivo, Boolean debeDejarLog);
	
	
	/**
	 * Busca los distintos id de la cuentas resultantes de un conjunto de saldos de efectivo,
	 *  las cuales se consultan
	 * por cuenta, divisa y bóveda.
	 * 
	 * @param saldoEfectivo el DTO con los criterios para la consulta de saldos.
	 * @return una lista con los identificadores de las distintas cuentas de los saldos encontrados.
	 */
	List<Long> buscarCuentasDeEfectivo(SaldoEfectivoDTO saldoEfectivo);
	/**
	 * Consulta un saldo de efectivo asociado a una cuenta contorlada o nombrada
	 * basado en su identificador único.
	 * @param fecha la fecha para realizar la consulta
	 * @param idSaldo Identificador del saldo
	 * @param tipoCuenta Tipo de cuenta asociada al saldo, N para nombrada, C para controlada
	 * @return DTO con los datos del saldo localizado, null en caso de no localizar el saldo.
	 */
	SaldoEfectivoDTO buscarSaldoEfectivoPorId(Date fecha, long idSaldo,String tipoCuenta);
	
	
	/**
	 * Obtiene el total de registros contables encontrados para una consulta de estado de cuenta de saldos de efectivo
	 * @param criterio Criterio para la consulta
	 * @return Total de registros encontrados
	 */
	long obtenerProyeccionDeRegistrosContablesDeSaldos(SaldoEfectivoDTO criterio);
	
}
