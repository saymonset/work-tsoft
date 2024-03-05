/**
 * 2H Software SA de CV
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Fecha de creación: Dec 17, 2007
 */
package com.indeval.portaldali.middleware.services.estadocuenta;


import java.util.Date;
import java.util.List;

import com.indeval.portaldali.middleware.dto.EstadoCuentaPosicionPorEmisionDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;

/**
 * Interface que define el contrato del servicio de negocio para las operaciones
 * relacionadas con la consulta del estado de cuenta de posiciones de valores.
 * 
 * @author Sandra Flores Arrieta
 * @version 1.0
 * 
 */
public interface ConsultaEstadoCuentaPosicionService {


	/**
	 * Invoca la búsqueda correspondiente de registros contables que reflejan
	 * las operaciones relacionadas a posiciones nombradas según
	 * los criterios de búsqueda recibidos como parámetros. Consulta la base de
	 * datos para obtener los valores de las operaciones que se registran en un
	 * lapso de tiempo correspondientes a un conjunto de posiciones nombradas
	 * para las cuentas de una institución.
	 * 
	 * @param  criterio el DTO con los criterios para realizar la consulta
	 * @param  idsEmisiones la lista de identificadores de emisiones a considerar para la consulta del estado de cuenta.
	 * @return Lista de objetos {@link EstadoCuentaPosicionPorEmisionDTO} los cuales contienen el detalle del estado de cuenta por emisión y bóveda.
	 */
	List<EstadoCuentaPosicionPorEmisionDTO> consultarEstadoDeCuentaPosicionesNombrada(PosicionDTO criterio, List<Long> idsEmisiones);
	
	/**
	 * Busca las distintas emisiones de un conjunto de posiciones nombradas las cuales se consultan
	 * por cuenta, emisión y bóveda.
	 * 
	 * @param criterio el DTO con los criterios para la consulta de posiciones nombradas.
	 * @return una lista con los identificadores de las distintas emisiones de las posiciones nombradas encontradas.
	 */
	List<Long> buscarEmisionesDePosicionesNombradas(PosicionDTO criterio, Boolean debeDejarLog);
	
	/**
	 * Invoca la búsqueda correspondiente de registros contables que reflejan
	 * las operaciones relacionadas a posiciones  controladas según
	 * los criterios de búsqueda recibidos como parámetros. Consulta la base de
	 * datos para obtener los valores de las operaciones que se registran en un
	 * lapso de tiempo correspondientes a un conjunto de posiciones
	 * controladas para las cuentas de una institución.
	 * 
	 * @param  criterio el DTO con los criterios para realizar la consulta
	 * @param  idsEmisiones la lista de identificadores de emisiones a considerar para la consulta del estado de cuenta.
	 * @return Lista de objetos {@link EstadoCuentaPosicionPorEmisionDTO} los cuales contienen el detalle del estado de cuenta por emisión y bóveda.
	 */
	List<EstadoCuentaPosicionPorEmisionDTO> consultarEstadoDeCuentaPosicionesControladas(PosicionDTO criterio, List<Long> idsEmisiones);
	
	/**
	 * Busca las distintas emisiones de un conjunto de posiciones controladas las cuales se consultan
	 * por cuenta, emisión y bóveda.
	 * 
	 * @param criterio el DTO con los criterios para la consulta de posiciones controladas.
	 * @return una lista con los identificadores de las distintas emisiones de las posiciones controladas encontradas.
	 */
	List<Long> buscarEmisionesDePosicionesControladas(PosicionDTO criterio, Boolean debeDejarLog);
	
	
	/**
	 * Busca las distintas cuentas asociadas a un conjunto de valores los cuales se consultan
	 * por cuenta, emisión y bóveda.
	 * 
	 * @param criterio el DTO con los criterios para la consulta de posiciones 
	 * @return una lista con los identificadores de las distintas cuentas de los valores encontrados.
	 */
	List<Long> buscarCuentasDePosiciones(PosicionDTO criterio);
	
	/**
	 * Consulta los datos de una posición nombrada o contorlada basado en su identificador único. 
	 * @param fecha La fecha en que se realiza la consulta
	 * @param idPosicion Identificador de la posicion buscara
	 * @param tipoCuenta Tipo de cuenta asociado a la posición
	 * @return DTO con los datos de la posición
	 */
	PosicionDTO buscarPosicionPorId(Date fecha, long idPosicion,String tipoCuenta);
	
	
	/**
	 * Obtiene el número de movimientos encontrados para un conjunto de posiciones en un rango de fechas
	 * @param criterio Criterio de consulta
	 * @return número de movimientos encontrados
	 */
	long obtenerProyeccionDeRegistrosContablesDePosiciones(PosicionDTO criterio);
}
