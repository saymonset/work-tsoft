/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.middleware.services.estadocuenta;

import java.util.List;
import java.util.Set;

import com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO;
import com.indeval.portaldali.middleware.dto.EstadoCuentaPosicionPorEmisionDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionControladaDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionNombradaDTO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;

/**
 * Interface que define las operaciones del servicio de negocio para la consultas de movimientos de valor.
 * @author Emigdio Hernández Rodríguez
 * @version 1.0
 *
 */
public interface ConsultaMovimientosValorService {
	
	
	/**
	 * Busca las distintas cuentas nombradas o controladas asociadas a un conjunto de movimientos
	 * de valor los cuales se consultan
	 * por cuenta, emisión y bóveda.
	 * 
	 * @param criterio el DTO con los criterios para la consulta de movimientos 
	 * @return una lista con los identificadores de las distintas cuentas controladas y nombradas de los valores encontrados.
	 */
	List<Long> buscarCuentasDeMovimientosDeValor(CriterioConsultaMovimientosValorDTO criterio);
	
	/**
	 * Busca las distintas emisiones asociadas a un conjunto de movimientos
	 * de valor los cuales se consultan
	 * por cuenta, emisión y bóveda.
	 * 
	 * @param criterio el DTO con los criterios para la consulta de movimientos 
	 * @return una lista con los identificadores de las distintas emisiones de los valores encontrados.
	 */
	List<Long> buscarEmisionesDeMovimientosDeValor(CriterioConsultaMovimientosValorDTO criterio);
	
	/**
	 * Invoca la búsqueda correspondiente de registros contables que reflejan
	 * las operaciones relacionadas a posiciones nombradas y controladas según
	 * los criterios de búsqueda recibidos como parámetros. Consulta la base de
	 * datos para obtener los valores de las operaciones que se registran en un
	 * lapso de tiempo correspondientes a un conjunto de posiciones nombradas
	 * para las cuentas de una institución.
	 * 
	 * @param  criterio el DTO con los criterios para realizar la consulta
	 * @param  idsEmisiones la lista de identificadores de emisiones a considerar para la consulta del estado de cuenta.
	 * @return Lista de objetos {@link EstadoCuentaPosicionPorEmisionDTO} los cuales contienen el detalle del estado de cuenta por emisión y bóveda.
	 */
	List<EstadoCuentaPosicionPorEmisionDTO> consultarMovimientosDeValor(CriterioConsultaMovimientosValorDTO criterio, Set<Long> idsEmisiones);

	/**
	 * Obtiene el total de registros contables encontrados para un consulta de movimientos de valor
	 * tomando en cuenta el criterio enviado como parámetro.
	 * @param criterio Criterio de consulta
	 * @return número de registros encontrados
	 */
	long obtenerProyeccionDeMovimientosDeValor(CriterioConsultaMovimientosValorDTO criterio, Boolean debeDejarLog);
	
	/**
	 * Invoca la búsqueda correspondiente de registros contables que reflejan
	 * las operaciones relacionadas a posiciones nombradas y controladas según
	 * los criterios de búsqueda recibidos como parámetros. Consulta la base de
	 * datos para obtener los valores de las operaciones que se registran en un
	 * lapso de tiempo correspondientes a un conjunto de posiciones nombradas
	 * para las cuentas de una institución. Los registros no se agruparán por emisión ni por bóveda.
	 * 
	 * @param  criterio el DTO con los criterios para realizar la consulta
	 * @param  idsEmisiones la lista de identificadores de emisiones a considerar para la consulta del estado de cuenta.
	 * @return Lista con los registros contables nombrados o controlados los cuales contienen el detalle del estado de cuenta por emisión y bóveda.
	 */
	List<RegistroContablePosicionNombradaDTO> consultarMovimientosDeValorNombradasSinAgrupar(CriterioConsultaMovimientosValorDTO criterio, Set<Long> idsEmisiones);
	
	/**
	 * Invoca la búsqueda correspondiente de registros contables que reflejan
	 * las operaciones relacionadas a posiciones nombradas y controladas según
	 * los criterios de búsqueda recibidos como parámetros. Consulta la base de
	 * datos para obtener los valores de las operaciones que se registran en un
	 * lapso de tiempo correspondientes a un conjunto de posiciones nombradas
	 * para las cuentas de una institución. Los registros no se agruparán por emisión ni por bóveda.
	 * 
	 * Este metodo hace lo mismo que el anterior solamente que se separo para 
	 * que deje bitacora por aparte de la consulta de movimientos
	 * 
	 * @param  criterio el DTO con los criterios para realizar la consulta
	 * @param  idsEmisiones la lista de identificadores de emisiones a considerar para la consulta del estado de cuenta.
	 * @return PaginaVO con los registros contables nombrados o controlados los cuales contienen el detalle del estado de cuenta por emisión y bóveda.
	 */
	PaginaVO consultarMovimientosArchivoConciliacion(CriterioConsultaMovimientosValorDTO criterio, Set<Long> idsEmisiones, PaginaVO pagina, Boolean debeDejarLog);
	
	/**
	 * Invoca la búsqueda correspondiente de registros contables que reflejan
	 * las operaciones relacionadas a posiciones nombradas y controladas según
	 * los criterios de búsqueda recibidos como parámetros. Consulta la base de
	 * datos para obtener los valores de las operaciones que se registran en un
	 * lapso de tiempo correspondientes a un conjunto de posiciones nombradas
	 * para las cuentas de una institución. Los registros no se agruparán por emisión ni por bóveda.
	 * 
	 * @param  criterio el DTO con los criterios para realizar la consulta
	 * @param  idsEmisiones la lista de identificadores de emisiones a considerar para la consulta del estado de cuenta.
	 * @return Lista con los registros contables nombrados o controlados los cuales contienen el detalle del estado de cuenta por emisión y bóveda.
	 */
	List<RegistroContablePosicionControladaDTO> consultarMovimientosDeValorControladasSinAgrupar(CriterioConsultaMovimientosValorDTO criterio, Set<Long> idsEmisiones);

}
