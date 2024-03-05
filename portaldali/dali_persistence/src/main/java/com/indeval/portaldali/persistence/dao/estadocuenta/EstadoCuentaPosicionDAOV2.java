/**
 * Sistema Portal DALI
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Nov 18, 2008
 */
package com.indeval.portaldali.persistence.dao.estadocuenta;

import java.util.Date;
import java.util.List;

import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionControladaDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionNombradaDTO;

/**
 * DAO para la conuslta del estado de cuenta de posiciones en el DALI.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public interface EstadoCuentaPosicionDAOV2 {

	/**
	 * Busca las distintas emisiones de un conjunto de posiciones controladas
	 * las cuales se consultan por cuenta, emisión y bóveda.
	 * 
	 * @param criterio
	 *            el DTO con los criterios para la consulta de posiciones
	 *            controladas.
	 * @return una lista con los identificadores de las distintas emisiones de
	 *         las posiciones controladas encontradas.
	 */
	List<Long> buscarEmisionesDePosicionesControladas(final PosicionDTO criterio);

	/**
	 * Busca las distintas emisiones de un conjunto de posiciones nombradas las
	 * cuales se consultan por cuenta, emisión y bóveda.
	 * 
	 * @param criterio
	 *            el DTO con los criterios para la consulta de posiciones
	 *            nombradas.
	 * @return una lista con los identificadores de las distintas emisiones de
	 *         las posiciones nombradas encontradas.
	 */
	List<Long> buscarEmisionesDePosicionesNombradas(final PosicionDTO criterio);

	/**
	 * Busca las distintas emisiones de un conjunto de posiciones controladas
	 * las cuales se consultan por cuenta, emisión y bóveda.
	 * 
	 * @param criterio
	 *            el DTO con los criterios para la consulta de posiciones
	 *            controladas.
	 * @param fecha
	 *            La fecha para consultar el histórico
	 * @return una lista con los identificadores de las distintas emisiones de
	 *         las posiciones controladas encontradas.
	 */
	List<Long> buscarEmisionesDePosicionesControladasHistoricas(final PosicionDTO criterio, final Date fecha);

	/**
	 * Busca las distintas emisiones de un conjunto de posiciones nombradas las
	 * cuales se consultan por cuenta, emisión y bóveda.
	 * 
	 * @param criterio
	 *            el DTO con los criterios para la consulta de posiciones
	 *            nombradas.
	 * @param fecha
	 *            La fecha para consultar el histórico
	 * @return una lista con los identificadores de las distintas emisiones de
	 *         las posiciones nombradas encontradas.
	 */
	List<Long> buscarEmisionesDePosicionesNombradasHistoricas(final PosicionDTO criterio, final Date fecha);

	/**
	 * Busca las distintas cuentas nombradas asociadas a un conjunto de valores
	 * los cuales se consultan por cuenta, emisión y bóveda.
	 * 
	 * @param criterio
	 *            el DTO con los criterios para la consulta de posiciones
	 * @return una lista con los identificadores de las distintas cuentas
	 *         nombradas de los valores encontrados.
	 */
	List<Long> buscarCuentasDePosicionesNombradas(final PosicionDTO criterio);

	/**
	 * Busca las distintas cuentas controladas asociadas a un conjunto de
	 * valores los cuales se consultan por cuenta, emisión y bóveda.
	 * 
	 * @param criterio
	 *            el DTO con los criterios para la consulta de posiciones
	 * @return una lista con los identificadores de las distintas cuentas
	 *         controladas de los valores encontrados.
	 */
	List<Long> buscarCuentasDePosicionesControladas(final PosicionDTO criterio);

	/**
	 * Busca las distintas cuentas nombradas asociadas a un conjunto de valores
	 * los cuales se consultan por cuenta, emisión y bóveda.
	 * 
	 * @param criterio
	 *            el DTO con los criterios para la consulta de posiciones
	 * @param fecha
	 *            La fecha para consultar el histórico
	 * @return una lista con los identificadores de las distintas cuentas
	 *         nombradas de los valores encontrados.
	 */
	List<Long> buscarCuentasDePosicionesNombradasHistoricas(final PosicionDTO criterio, final Date fecha);

	/**
	 * Busca las distintas cuentas controladas asociadas a un conjunto de
	 * valores los cuales se consultan por cuenta, emisión y bóveda.
	 * 
	 * @param criterio
	 *            el DTO con los criterios para la consulta de posiciones
	 * @param fecha
	 *            La fecha para consultar el histórico
	 * @return una lista con los identificadores de las distintas cuentas
	 *         controladas de los valores encontrados.
	 */
	List<Long> buscarCuentasDePosicionesControladasHistoricas(final PosicionDTO criterio, final Date fecha);

	/**
	 * Busca las posiciones nombradas al día actual de algunas emisiones.
	 * 
	 * @param criterio
	 *            el DTO con los criterios para la consulta de posiciones.
	 * @param idsEmisiones
	 *            una lista con los ids de las emisiones a consultar. Si es
	 *            nula, se ignora y se retornan posiciones de todas las
	 *            emisiones que cumplan con el criterio de consulta.
	 * @return una lista con las posiciones que cumplen con el criterio de
	 *         consulta.
	 */
	List<PosicionDTO> buscarPosicionesNombradas(final PosicionDTO criterio, final List<Long> idsEmisiones);

	/**
	 * Busca las posiciones nombradas históricas de algunas emisiones.
	 * 
	 * @param criterio
	 *            el DTO con los criterios para la consulta de posiciones.
	 * @param fecha
	 *            la fecha para consultar las posiciones.
	 * @param idsEmisiones
	 *            una lista con los ids de las emisiones a consultar. Si es
	 *            nula, se ignora y se retornan posiciones de todas las
	 *            emisiones que cumplan con el criterio de consulta.
	 * @return una lista con las posiciones que cumplen con el criterio de
	 *         consulta.
	 */
	List<PosicionDTO> buscarPosicionesNombradasHistoricas(final PosicionDTO criterio, final Date fecha, final List<Long> idsEmisiones);

	/**
	 * Busca las posiciones controladas al día actual de algunas emisiones.
	 * 
	 * @param criterio
	 *            el DTO con los criterios para la consulta de posiciones.
	 * @param idsEmisiones
	 *            una lista con los ids de las emisiones a consultar. Si es
	 *            nula, se ignora y se retornan posiciones de todas las
	 *            emisiones que cumplan con el criterio de consulta.
	 * @return una lista con las posiciones que cumplen con el criterio de
	 *         consulta.
	 */
	List<PosicionDTO> buscarPosicionesControladas(final PosicionDTO criterio, final List<Long> idsEmisiones);

	/**
	 * Busca las posiciones controladas históricaszaz de algunas emisiones.
	 * 
	 * @param criterio
	 *            el DTO con los criterios para la consulta de posiciones.
	 * @param fecha
	 *            la fecha para consultar las posiciones.
	 * @param idsEmisiones
	 *            una lista con los ids de las emisiones a consultar. Si es
	 *            nula, se ignora y se retornan posiciones de todas las
	 *            emisiones que cumplan con el criterio de consulta.
	 * @return una lista con las posiciones que cumplen con el criterio de
	 *         consulta.
	 */
	List<PosicionDTO> buscarPosicionesControladasHistoricas(final PosicionDTO criterio, final Date fecha, final List<Long> idsEmisiones);

	/**
	 * Busca los registros contables de algunas posiciones nombradas al día
	 * actual.
	 * 
	 * @param criterio
	 *            el DTO con los criterios para realizar la consulta
	 * @param idsEmisiones
	 *            los identificadores de las emisiones a consutar. Si es nula,
	 *            se ignora y se retornan posiciones de todas las emisiones que
	 *            cumplan con el criterio de consulta.
	 * @return una lista con los registros contables encontrados que coiciden
	 *         con la consulta.
	 */
	List<RegistroContablePosicionNombradaDTO> buscarRegistrosContablesNombradas(final PosicionDTO criterio, final List<Long> idsEmisiones);

	/**
	 * Busca los registros contables de algunas posiciones nombradas en el
	 * histórico.
	 * 
	 * @param criterio
	 *            el DTO con los criterios para realizar la consulta
	 * @param idsEmisiones
	 *            los identificadores de las emisiones a consutar. Si es nula,
	 *            se ignora y se retornan posiciones de todas las emisiones que
	 *            cumplan con el criterio de consulta.
	 * @return una lista con los registros contables encontrados que coiciden
	 *         con la consulta.
	 */
	List<RegistroContablePosicionNombradaDTO> buscarRegistrosContablesNombradasHistoricos(final PosicionDTO criterio, final List<Long> idsEmisiones);

	/**
	 * Busca los registros contables de algunas posiciones controladas al día
	 * actual.
	 * 
	 * @param criterio
	 *            el DTO con los criterios para realizar la consulta
	 * @param idsEmisiones
	 *            los identificadores de las emisiones a consutar. Si es nula,
	 *            se ignora y se retornan posiciones de todas las emisiones que
	 *            cumplan con el criterio de consulta.
	 * @return una lista con los registros contables encontrados que coiciden
	 *         con la consulta.
	 */
	List<RegistroContablePosicionControladaDTO> buscarRegistrosContablesControladas(final PosicionDTO criterio, final List<Long> idsEmisiones);

	/**
	 * Busca los registros contables de algunas posiciones controladas en el
	 * histórico.
	 * 
	 * @param criterio
	 *            el DTO con los criterios para realizar la consulta
	 * @param idsEmisiones
	 *            los identificadores de las emisiones a consutar. Si es nula,
	 *            se ignora y se retornan posiciones de todas las emisiones que
	 *            cumplan con el criterio de consulta.
	 * @return una lista con los registros contables encontrados que coiciden
	 *         con la consulta.
	 */
	List<RegistroContablePosicionControladaDTO> buscarRegistrosContablesControladasHistoricos(final PosicionDTO criterio, final List<Long> idsEmisiones);
	
	/**
	 * Obtiene la proyección de cuántos registros contables se hayarán al realizar la consulta de registros contables
	 * de cuentas nombradas.
	 * 
	 * @param criterio el DTO con los criterios para realizar la consulta.
	 * @return el número de registros que resultarán de ejecutar la consulta.
	 */
	Long obtenerProyeccionRegistrosContablesNombradas(final PosicionDTO criterio);
	
	/**
	 * Obtiene la proyección de cuántos registros contables se hayarán al realizar la consulta de registros contables
	 * de cuentas nombradas en el histórico.
	 * 
	 * @param criterio el DTO con los criterios para realizar la consulta.
	 * @return el número de registros que resultarán de ejecutar la consulta.
	 */
	Long obtenerProyeccionRegistrosContablesNombradasHistorico(final PosicionDTO criterio);
	
	/**
	 * Obtiene la proyección de cuántos registros contables se hayarán al realizar la consulta de registros contables
	 * de cuentas nombradas.
	 * 
	 * @param criterio el DTO con los criterios para realizar la consulta.
	 * @return el número de registros que resultarán de ejecutar la consulta.
	 */
	Long obtenerProyeccionRegistrosContablesControladas(final PosicionDTO criterio);
	
	/**
	 * Obtiene la proyección de cuántos registros contables se hayarán al realizar la consulta de registros contables
	 * de cuentas nombradas en el histórico.
	 * 
	 * @param criterio el DTO con los criterios para realizar la consulta.
	 * @return el número de registros que resultarán de ejecutar la consulta.
	 */
	Long obtenerProyeccionRegistrosContablesControladasHistorico(final PosicionDTO criterio);
}
