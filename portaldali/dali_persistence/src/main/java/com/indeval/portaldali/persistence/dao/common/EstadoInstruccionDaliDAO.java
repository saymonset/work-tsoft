/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * EstadoInstruccionDaliDAO.java
 * 29/02/2008
 */
package com.indeval.portaldali.persistence.dao.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.EstadoInstruccionDTO;

/**
 * Interfaz que define el contrato que debe de cumplir el objeto de acceso a datos
 * para el catálogo de estado de instrucciones.
 * @author Emigdio Hernández
 *
 */
public interface EstadoInstruccionDaliDAO {
	/**
	 * Obtiene una lista con los diferentes estados de instrucción del catálogo de 
	 * estados de instrucciones de la base de datos
	 * @return Lista don los DTO que representan los resultados
	 */
	List<EstadoInstruccionDTO> consultarTodosLosEstadosInstruccion();
	
	/**
	 * Consulta un estado de instrucción en específico basado en su ID y un prefijo dado
	 * @param prefijo a buscar
	 * @return Lista don los DTO que representan los resultados
	 */
	public List<EstadoInstruccionDTO> buscarEstadosInstruccionPorIds(String prefijo);

	/**
	 * Consulta un estado de instrucción en específico basado en su ID
	 * @param idEstadoInstruccion Id del estado
	 * @return DTO con los datos resultantes, null en caso de no localizar el estado
	 */
	EstadoInstruccionDTO consultarEstadoInstruccionPorId(long idEstadoInstruccion);

	/**
	 * Consulta un estado de instrucción en específico basado en su claveEstadoInstruccion
	 * @param claveEstadoInstruccion  del estado
	 * @return DTO con los datos resultantes, null en caso de no localizar el estado
	 */
	public EstadoInstruccionDTO consultarEstadoInstruccionPorClave(String claveEstadoInstruccion);

	List<EstadoInstruccionDTO> consultarEstadosInstruccionPorIds();
	
}
