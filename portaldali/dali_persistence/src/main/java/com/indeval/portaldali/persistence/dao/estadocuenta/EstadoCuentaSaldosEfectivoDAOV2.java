/**
 * 2H Software - Bursatec
 * 
 * Sistema de Consulta de Estados de Cuenta
 *
 * Dec 20, 2007
 *
 */
package com.indeval.portaldali.persistence.dao.estadocuenta;

import java.util.Date;
import java.util.List;

import com.indeval.portaldali.middleware.dto.RegistroContableSaldoControladaDTO;
import com.indeval.portaldali.middleware.dto.RegistroContableSaldoNombradaDTO;
import com.indeval.portaldali.middleware.dto.SaldoEfectivoDTO;

/**
 * Interface que define el contrato para el DAO de consulta de registros contables
 * de saldos controlados o nombrados.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 */
public interface EstadoCuentaSaldosEfectivoDAOV2 {
	
	/** 
	 * Busca las diferentes divisas de un conjunto de posiciones controladas las cuales
	 * se consultan por cuenta, divisa y bóveda.
	 * 
	 * @param saldoEfectivo el DTO con los criterios para realizar la consulta.
	 * @return una lista con los identificadores de las distintas divisas en los saldos controlados encontrados.
	 */
	List<Long> buscarDivisasDeSaldosControlados(final SaldoEfectivoDTO saldoEfectivo);
	
	/** 
	 * Busca las diferentes divisas de un conjunto de saldos de efectivo nombrados las cuales
	 * se consultan por cuenta, divisa y bóveda.
	 * 
	 * @param saldoEfectivo el DTO con los criterios para realizar la consulta.
	 * @return una lista con los identificadores de las distintas divisas en los saldos nombrados encontrados.
	 */
	List<Long> buscarDivisasDeSaldosNombrados(final SaldoEfectivoDTO saldoEfectivo);
	
	/**
	 * Busca los saldos nombrados para
	 * formar el estado de cuenta de saldos de efectivo.
	 * 
	 * @param saldoEfectivo DTO con los criterios de consulta de los saldos.
	 * @param idsDivisas los identificadores de las divisas a consultar.
	 * @return DTO con los datos del saldo.
	 */
	List<SaldoEfectivoDTO>buscarSaldoNombrado(final SaldoEfectivoDTO saldoEfectivo, final List<Long> idsDivisas);
	
	/**
	 * Busca los saldos controlados para
	 * formar el estado de cuenta de saldos de efectivo.
	 * 
	 * @param saldoEfectivo DTO con los criterios de consulta de los saldos.
	 * @param idsDivisas los identificadores de las divisas a consultar.
	 * @return DTO con los datos del saldo.
	 */
	List<SaldoEfectivoDTO>buscarSaldoControlado(final SaldoEfectivoDTO saldoEfectivo, final List<Long> idsDivisas);
	
	/**
	 * Obtiene una lista de registros contables de saldos de efectivo nombrados, para formar el estado 
	 * de cuenta de saldos de efectivo. Los registros contables son los que se refieren a los saldos indicados
	 * como parámetro.
	 * 
	 * @param saldoEfectivo el DTO con los criterios para la consulta de saldos.
	 * @return Lista de operaciones de saldos de efectivo de tipo {@link RegistroContableSaldoNombradaDTO} para 
	 * 			formar el estado de cuenta de posiciones.
	 */
	List<RegistroContableSaldoNombradaDTO> buscarRegistrosContablesDeSaldosNombradas(final SaldoEfectivoDTO saldoEfectivo);
	
	/**
	 * Obtiene una lista de registros contables de saldos de efectivo controlados, para formar el estado 
	 * de cuenta de saldos de efectivo. Los registros contables son los que se refieren a los saldos indicados
	 * como parámetro.
	 * 
	 * @param saldoEfectivo el DTO con los criterios para la consulta de saldos.
	 * @return Lista de operaciones de saldos de efectivo de tipo {@link RegistroContableSaldoControladaDTO} para 
	 * 			formar el estado de cuenta de posiciones.
	 */
	List<RegistroContableSaldoControladaDTO> buscarRegistrosContablesDeSaldosControladas(final SaldoEfectivoDTO saldoEfectivo);
	
	/**
	 * Busca los distintos id de la cuentas resultantes de un conjunto de saldos de efectivo nombradas ,
	 *  las cuales se consultan
	 * por cuenta, divisa y bóveda.
	 * 
	 * @param saldoEfectivo el DTO con los criterios para la consulta de saldos.
	 * @return una lista con los identificadores de las distintas cuentas de los saldos encontrados.
	 */
	List<Long> buscarCuentasDeEfectivoNombradas(final SaldoEfectivoDTO saldoEfectivo);
	
	/**
	 * Busca los distintos id de la cuentas resultantes de un conjunto de saldos de efectivo controladas ,
	 * las cuales se consultan
	 * por cuenta, divisa y bóveda.
	 * 
	 * @param saldoEfectivo el DTO con los criterios para la consulta de saldos.
	 * @return una lista con los identificadores de las distintas cuentas de los saldos encontrados.
	 */
	List<Long> buscarCuentasDeEfectivoControladas(final SaldoEfectivoDTO saldoEfectivo);
	
	/**
	 * Obtiene el total de registros contables encontrados para una consulta de estado de cuenta de saldos nombrados de efectivo
	 * @param criterio Criterio para la consulta
	 * @return Total de registros encontrados
	 */
	long obtenerProyeccionDeRegistrosContablesDeSaldosNombrada(final SaldoEfectivoDTO criterio);
	
	/**
	 * Obtiene el total de registros contables encontrados para una consulta de estado de cuenta de saldos controlados de efectivo
	 * @param criterio Criterio para la consulta
	 * @return Total de registros encontrados
	 */
	long obtenerProyeccionDeRegistrosContablesDeSaldosControlada(final SaldoEfectivoDTO criterio);
	
	
	
	// Para datos historicos
		

	
	/** 
	 * Busca las diferentes divisas de un conjunto de posiciones controladas las cuales
	 * se consultan por cuenta, divisa y bóveda.
	 * 
	 * @param saldoEfectivo el DTO con los criterios para realizar la consulta.
	 * @param fecha La fecha para consultar el histórico
	 * @return una lista con los identificadores de las distintas divisas en los saldos controlados encontrados.
	 */
	List<Long> buscarDivisasDeSaldosControladosHistorico(final SaldoEfectivoDTO saldoEfectivo, final Date fecha);
	
	/** 
	 * Busca las diferentes divisas de un conjunto de saldos de efectivo nombrados las cuales
	 * se consultan por cuenta, divisa y bóveda.
	 * 
	 * @param saldoEfectivo el DTO con los criterios para realizar la consulta.
	 * @param fecha La fecha para consultar el histórico
	 * @return una lista con los identificadores de las distintas divisas en los saldos nombrados encontrados.
	 */
	List<Long> buscarDivisasDeSaldosNombradosHistorico(final SaldoEfectivoDTO saldoEfectivo, final Date fecha);
	
	/**
	 * Busca los distintos id de la cuentas resultantes de un conjunto de saldos historicos de efectivo nombradas ,
	 *  las cuales se consultan
	 * por cuenta, divisa y bóveda.
	 * 
	 * @param saldoEfectivo el DTO con los criterios para la consulta de saldos.
	 * @param fecha La fecha para consultar el histórico
	 * @return una lista con los identificadores de las distintas cuentas de los saldos encontrados.
	 */
	List<Long> buscarCuentasDeEfectivoNombradasHistorico(final SaldoEfectivoDTO saldoEfectivo, final Date fecha);
	
	/**
	 * Busca los distintos id de la cuentas resultantes de un conjunto de saldos historicos de efectivo controladas ,
	 * las cuales se consultan
	 * por cuenta, divisa y bóveda.
	 * 
	 * @param saldoEfectivo el DTO con los criterios para la consulta de saldos.
	 * @param fecha La fecha para consultar el histórico
	 * @return una lista con los identificadores de las distintas cuentas de los saldos encontrados.
	 */
	List<Long> buscarCuentasDeEfectivoControladasHistorico(final SaldoEfectivoDTO saldoEfectivo, final Date fecha);
	
	/**
	 * Busca los saldos nombrados historicos para
	 * formar el estado de cuenta de saldos de efectivo.
	 * 
	 * @param saldoEfectivo DTO con los criterios de consulta de los saldos.
	 * @param idsDivisas los identificadores de las divisas a consultar.
	 * @param fecha La fecha para consultar el histórico
	 * @return DTO con los datos del saldo
	 */
	List<SaldoEfectivoDTO>buscarSaldoNombradoHistorico(final SaldoEfectivoDTO saldoEfectivo, final List<Long> idsDivisas, final Date fecha);
	
	/**
	 * Busca los saldos controlados historico para
	 * formar el estado de cuenta de saldos de efectivo.
	 * 
	 * @param saldoEfectivo DTO con los criterios de consulta de los saldos.
	 * @param idsDivisas los identificadores de las divisas a consultar.
	 * @param fecha La fecha para consultar el histórico
	 * @return DTO con los datos del saldo
	 */
	List<SaldoEfectivoDTO>buscarSaldoControladoHistorico(final SaldoEfectivoDTO saldoEfectivo, final List<Long> idsDivisas, final Date fecha);
	
	/**
	 * Obtiene una lista de registros contables de saldos historicos de efectivo nombrados, para formar el estado 
	 * de cuenta de saldos de efectivo. Los registros contables son los que se refieren a los saldos indicados
	 * como parámetro.
	 * 
	 * @param saldoEfectivo DTO con los criterios de consulta de los saldos.
	 * @return Lista de operaciones de saldos de efectivo de tipo {@link RegistroContableSaldoNombradaDTO} para 
	 * 			formar el estado de cuenta de posiciones.
	 */
	List<RegistroContableSaldoNombradaDTO> buscarRegistrosContablesDeSaldosNombradasHistorico(final SaldoEfectivoDTO saldoEfectivo);
	
	/**
	 * Obtiene una lista de registros contables de saldos historicos de efectivo controlados, para formar el estado 
	 * de cuenta de saldos de efectivo. Los registros contables son los que se refieren a los saldos indicados
	 * como parámetro.
	 * 
	 * @param saldoEfectivo DTO con los criterios de consulta de los saldos.
	 * @return Lista de operaciones de saldos de efectivo de tipo {@link RegistroContableSaldoControladaDTO} para 
	 * 			formar el estado de cuenta de posiciones.
	 */
	List<RegistroContableSaldoControladaDTO> buscarRegistrosContablesDeSaldosControladasHistorico(final SaldoEfectivoDTO saldoEfectivo);
	
	/**
	 * Obtiene el total de registros contables encontrados para una consulta de estado de cuenta de saldos nombrados de efectivo
	 * @param criterio Criterio para la consulta
	 * @return Total de registros encontrados
	 */
	long obtenerProyeccionDeRegistrosContablesDeSaldosNombradaHistorico(final SaldoEfectivoDTO criterio);
	
	/**
	 * Obtiene el total de registros contables encontrados para una consulta de estado de cuenta de saldos controlados de efectivo
	 * @param criterio Criterio para la consulta
	 * @return Total de registros encontrados
	 */
	long obtenerProyeccionDeRegistrosContablesDeSaldosControladaHistorico(final SaldoEfectivoDTO criterio);
	
}
