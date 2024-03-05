/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ConsultaTipoMensajeService.java
 * 03/03/2008
 */
package com.indeval.portaldali.middleware.services.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.TipoMensajeDTO;

/**
 * Interfaz que define el contrato que debe de cumplir el servicio
 * de acceso al catálogo de tipos de mensaje
 * @author Emigdio Hernández
 *
 */
public interface ConsultaTipoMensajeService {
	/**
	 * Consulta los tipos de mensaje disponibles en el catálogo de tipos de mensaje.
	 * @return Lista con los tipos de mensaje encontrados.
	 */
	List<TipoMensajeDTO> consultarTodosLosTiposMensaje();
	/**
	 * Consulta un tipo de mensaje en específico basado en su ID único.
	 * @param idTipoMensaje Identificador del tipo de mensaje
	 * @return DTO con los datos del tipo encontrado
	 */
	TipoMensajeDTO consultaTipoMensajePorId(int idTipoMensaje);
	
	/**
	 * Consulta un tipo de mensaje en específico basado en su clave de tipo de mensaje.
	 * @param claveTipoMensaje Clave del tipo de mensaje
	 * @return DTO con los datos del tipo encontrado
	 */
	TipoMensajeDTO consultaTipoMensajePorClave(String claveTipoMensaje);
}
