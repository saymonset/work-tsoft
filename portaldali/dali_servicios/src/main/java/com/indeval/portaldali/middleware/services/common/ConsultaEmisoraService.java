/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * 03/03/2008
 */
package com.indeval.portaldali.middleware.services.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.EmisoraDTO;

/**
 * Interface de servicio de negocio que expone los métodos para las operaciones
 * realizadas sobre el catálogo de emisoras de la base de datos.
 * 
 * @author Emigdio Hernández
 * @version 1.0
 */
public interface ConsultaEmisoraService {
	/**
	 * Obtiene una lista de objetos de tipo EmisoraDTO que contiene los datos de
	 * las emisoras del catálogo de emisoras de la base de datos.
	 * 
	 * @return Lista de emisoras del catálogo de emisoras de la base de datos
	 */
	List<EmisoraDTO> buscarEmisoras();

	/**
	 * Busca una emisora en la base de datos por medio de su identificador.
	 * 
	 * @param idEmisora
	 *            el identificador de la emisora a consultar.
	 * @return un objeto {@link EmisoraDTO} con los datos de la emisora a
	 *         consultar.
	 */
	EmisoraDTO buscarEmisoraPorId(long idEmisora);

	/**
	 * Busca emisoras en el catálogo cuya descripción comiencen con el prefijo
	 * proporcionado.
	 * 
	 * @param prefijo
	 *            el prefijo a consultar en la BD.
	 * @return una lista con objetos de tipo {@link EmisoraDTO} con todas las
	 *         coincidencias encontradas.
	 */
	List<EmisoraDTO> buscarEmisorasPorPrefijo(String prefijo);

	/**
	 * Busca una emisora por medio de su descripción.
	 * 
	 * @param descripcion
	 *            la descripción a consultar.
	 * @return un objeto {@link EmisoraDTO} con los datos de la emisora
	 *         encontrada. <code>null</code> en caso de no encontrar
	 *         correspondencia.
	 */
	EmisoraDTO buscarEmisoraPorDescripcion(String descripcion);
}
