/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.persistence.dao.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.TipoOperacionDTO;

/**
 * Interfaz que define el contrato para las operaciones de consulta del catálogo de tipo
 * de operaciones.
 * @author Emigdio Hernández Rodríguez
 * @version 1.0
 */
public interface TipoOperacionDAO {

	/**
	 * Consulta todos los tipos de operación que existen en el catálogo de tipos de operación.
	 * @param tiposCustodia Lista de valores separados por coma que indican los tipos de custodia que aplican
	 * para la consulta de operaciones.
	 * @return Lista de {@link TipoOperacionDTO} que representan los datos de los registros encontrados.
	 */
	List<TipoOperacionDTO> buscatTiposOperacion(String tiposCustodia);
	/**
	 * Consulta un registro del catálogo de tipos de operación basado en su ID
	 * @param id Identificador único del tipo de operación.
	 * @return DTO con los datos obtenidos del tipo de operación.
	 */
	TipoOperacionDTO buscarTipoOperaciconPorId(long id);
	
	List<TipoOperacionDTO> buscarTiposDeOperacion();
}
