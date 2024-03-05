/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ConsultaEstadoInstruccionService.java
 * 29/02/2008
 */
package com.indeval.portaldali.middleware.services.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.EstadoInstruccionDTO;

/**
 * Interfaz que define el contrato del servicio de consulta al catálogo
 * de Estados de Intrucción.
 * @author Emigdio Hernández
 *
 */
public interface ConsultaEstadoInstruccionService {
	/**
	 * Obtiene una lista con los diferentes estados de instrucción del catálogo de 
	 * estados de instrucciones 
	 * @return Lista don los DTO que representan los resultados
	 */
	List<EstadoInstruccionDTO> consultarTodosLosEstadosInstruccion();
	
	/**
	 * Consulta un estado de instrucción en específico basado en su ID
	 * @param idEstadoInstruccion Id del estado
	 * @return DTO con los datos resultantes, null en caso de no localizar el estado
	 */
	EstadoInstruccionDTO consultarEstadoInstruccionPorId(long idEstadoInstruccion);
	
	/**
	 * Consulta un estado de instrucción en específico basado en su claveEstadoInstruccion
	 * @param claveEstadoInstruccion del estado
	 * @return DTO con los datos resultantes, null en caso de no localizar el estado
	 */
	EstadoInstruccionDTO consultarEstadoInstruccionPorClave(String claveEstadoInstruccion);
	
	/**
	 * Consulta un estado de instrucción en específico basado en su ID y un prefijo dado
	 * @param prefijo a buscar
	 * @return Lista don los DTO que representan los resultados
	 */
	public List<EstadoInstruccionDTO> buscarEstadosInstruccionPorIds(String prefijo);
}
