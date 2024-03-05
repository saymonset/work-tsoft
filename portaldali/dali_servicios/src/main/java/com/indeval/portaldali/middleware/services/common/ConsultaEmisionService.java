/**
 * 2H Software - Bursatec
 * Sistema de Consulta de Estados de Cuenta
 *
 * ConsultaEmisionService.java
 * Dec 6, 2007
 */
package com.indeval.portaldali.middleware.services.common;


import java.util.List;

import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.SerieDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;

/**
 * Interface de servicio de negocio que define los métodos para la consulta de emisiones
 * 
 * @author Sandra Flores Arrieta
 * @version 1.0
 */
public interface ConsultaEmisionService {

	/**
	 * Obtiene una lista de objetos de tipo SerieDTO con las series pertenecientes
	 * a emisiones filtradas por el tipo de valor y la emisora.
	 * 
	 *
	 * @param criterio  DTO con los criterios de búsqueda.
	 * @return  Lista de objetos de tipo SerieDTO con las series pertenecientes
	 * a emisiones
	 */
	List<SerieDTO> buscarSeries(SerieDTO criterio);
	
	
	/**
	 * Consulta la entidad de emisiones basndose en los parámetros de consulta pasados
	 * al servicio.
	 * Crea una lista de {@link EmisionDTO} con los resultados obtenidos
	 * @param criterio DTO con los criterios necesarios para buscar las emisiones
	 * @param paginacion Estado de la paginación a considerar para la ejecución de la consulta
	 * @return Lista de emisiones encontradas
	 */
	List<EmisionDTO> consultarEmisiones(EmisionDTO criterio,EstadoPaginacionDTO paginacion);
	

	/**
	 * Obtiene el total de registros que la consulta de emisiones retorna con los criterios 
	 * proporcionados
	 * @param criterio DTO con los criterios proporcionados
	 * @return número de registros obtenidos en la consulta
	 */
	int obtenerProyeccionDeEmisiones(EmisionDTO criterio);
	
	
	/**
	 * Obtiene una emision de la base de datos basado en su ID
	 * @param idEmision Identificador único de la emisión
	 * @return DTO conlos datos de la emisión
	 */
	EmisionDTO consultarEmisionPorId(long idEmision);
	
	
	/**
	 * Consulta la entidad de emisiones basndose en los parámetros de consulta pasados
	 * al servicio y en un listado de ids de emisiones válida para la consulta en curso.
	 * Crea una lista de {@link EmisionDTO} con los resultados obtenidos.
	 * @param criterio DTO con los criterios necesarios para buscar las emisiones
	 * @param estadoPaginacion Estado actual de la paginación de los resultados
	 * @param Conjunto de identificadores permitidos para la consulta de emisiones en curso
	 * @return Lista de emisiones encontradas
	 */
	List<EmisionDTO> consultarEmisiones(EmisionDTO criterio,EstadoPaginacionDTO estadoPaginacion,List<Long> emisionesValidas);
	/**
	 * Obtiene el total de registros que la consulta de emisiones retorna con los criterios 
	 * proporcionados y el conjunto de ids válidos para la consulta en curso.
	 * @param criterio DTO con los criterios proporcionados
	 * @param Conjunto de identificadores permitidos para la consulta de emisiones en curso
	 * @return número de registros obtenidos en la consulta
	 */
	int obtenerProyeccionDeEmisiones(EmisionDTO criterio,List<Long> emisionesValidas);
	
	public List<EmisionDTO> consultarEmisionesPorDescripciones(EmisionDTO criterio, final EstadoPaginacionDTO estadoPaginacion);
	
}
