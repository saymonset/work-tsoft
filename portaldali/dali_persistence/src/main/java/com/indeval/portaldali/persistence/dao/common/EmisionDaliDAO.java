/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * EmisionDaliDAO.java
 * 04/03/2008
 */
package com.indeval.portaldali.persistence.dao.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.SerieDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionExtended;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;

/**
 * Interface que expone los métodos para las operaciones realizadas sobre la
 * tabla de emisiones de la base de datos.
 * 
 * @author Emigdio Hernández
 */
public interface EmisionDaliDAO {

	/**
	 * Obtiene una lista de objetos de tipo SerieDTO con las series
	 * pertenecientes a emisiones filtradas por el tipo de valor y la emisora.
	 * 
	 * 
	 * @param criterio
	 *            DTO con los criterios de búsqueda
	 * @return Lista de objetos de tipo SerieDTO con las series pertenecientes a
	 *         emisiones
	 */
	List<SerieDTO> buscarSeries(SerieDTO criterio);

	/**
	 * Consulta la entidad de emisiones basndose en los parámetros de consulta
	 * pasados al servicio. Crea una lista de {@link EmisionDTO} con los
	 * resultados obtenidos
	 * 
	 * @param criterio
	 *            DTO con los criterios necesarios para buscar las emisiones
	 * @param estadoPaginacion
	 *            Estado actual de la paginación de los resultados
	 * @return Lista de emisiones encontradas
	 */
	List<EmisionDTO> consultarEmisiones(EmisionDTO criterio, EstadoPaginacionDTO estadoPaginacion);

	/**
	 * Busca en la tabla de cupones, aquellos cuyas emisiones cumplan con los
	 * criterios de consulta.
	 * 
	 * @param criterio
	 *            el DTO con los criterios necesario para realizar la consulta.
	 * @param estadoPaginacion
	 *            el DTO con el estado actual de la paginación de los
	 *            resultados.
	 * @param identificadoresMercado
	 *            los identificadores de los mercados de las emisiones buscadas.
	 * @return una lista con objetos {@link EmisionDTO} los cuales representan
	 *         los registros encontrados.
	 */
	List<EmisionDTO> buscarEmisionesPorCupon(EmisionDTO criterio, EstadoPaginacionExtended estadoPaginacion, Long[] identificadoresMercado);

	/**
	 * Obtiene la proyección de resultados de la consulta de cupones cuyas
	 * emisiones cumplan con los criterios de consulta.
	 * 
	 * @param criterio
	 *            el DTO con los criterios necesario para realizar la consulta.
	 * @param identificadoresMercado
	 *            los identificadores de los mercados de las emisiones buscadas.
	 * @return el número de registros que arrojará la consulta.
	 */
	Long obtenerProyeccionBuscarEmisionesPorCupon(EmisionDTO criterio, Long[] identificadoresMercado);

	/**
	 * Obtiene el total de registros que la consulta de emisiones retorna con
	 * los criterios proporcionados
	 * 
	 * @param criterio
	 *            DTO con los criterios proporcionados
	 * @return número de registros obtenidos en la consulta
	 */
	int obtenerProyeccionDeEmisiones(EmisionDTO criterio);

	/**
	 * Obtiene una emision de la base de datos basado en su ID
	 * 
	 * @param idEmision
	 *            Identificador único de la emisión
	 * @return DTO conlos datos de la emisión
	 */
	EmisionDTO consultarEmisionPorId(long idEmision);
	
	/**
	 * Busca una emision liberada por su tipo de valor, emisora y serie.
	 * @param tv Tipo de valor de la emision
	 * @param emisora Emisora de la emision
	 * @param serie Serie de la emision
	 * @return Dto con la emision, o nulo si no existe
	 */
	EmisionDTO consultarEmisionLiberadaPorTVEmisoraSerie(String tv, String emisora, String serie);

	/**
	 * Obtiene una emision de la base de datos basado en su cupon, tv, serie,
	 * emisora
	 * 
	 * @param criterioDTO
	 *            con los criterios proporcionados
	 * @return Objeto con los datos de la emision
	 */
	EmisionDTO consultarEmisionPorCupon(EmisionVO criterio);

	/**
	 * Consulta la entidad de emisiones basndose en los parámetros de consulta
	 * pasados al servicio y en un listado de ids de emisiones válida para la
	 * consulta en curso. Crea una lista de {@link EmisionDTO} con los
	 * resultados obtenidos.
	 * 
	 * @param criterio
	 *            DTO con los criterios necesarios para buscar las emisiones
	 * @param estadoPaginacion
	 *            Estado actual de la paginación de los resultados
	 * @param Conjunto
	 *            de identificadores permitidos para la consulta de emisiones en
	 *            curso
	 * @return Lista de emisiones encontradas
	 */
	List<EmisionDTO> consultarEmisiones(EmisionDTO criterio, EstadoPaginacionDTO estadoPaginacion, List<Long> emisionesValidas);

	/**
	 * Obtiene el total de registros que la consulta de emisiones retorna con
	 * los criterios proporcionados y el conjunto de ids válidos para la
	 * consulta en curso.
	 * 
	 * @param criterio
	 *            DTO con los criterios proporcionados
	 * @param Conjunto
	 *            de identificadores permitidos para la consulta de emisiones en
	 *            curso
	 * @return número de registros obtenidos en la consulta
	 */
	int obtenerProyeccionDeEmisiones(EmisionDTO criterio, List<Long> emisionesValidas);

	/**
	 * Consulta la entidad de emisiones basndose en los parámetros de consulta
	 * pasados (descripciones) al servicio. Crea una lista de {@link EmisionDTO}
	 * con los resultados obtenidos
	 * 
	 * @param criterio
	 *            DTO con los criterios necesarios para buscar las emisiones
	 * @param estadoPaginacion
	 *            Estado actual de la paginación de los resultados
	 * @return Lista de emisiones encontradas
	 */
	List<EmisionDTO> consultarEmisionesPorDescripciones(EmisionDTO criterio, EstadoPaginacionDTO estadoPaginacion);
}
