/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * 03/03/2008
 */
package com.indeval.portaldali.persistence.dao.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.EmisoraDTO;

/**
 * Interface que expone los métodos para las operaciones realizadas sobre el
 * catálogo de emisoras de la base de datos.
 * 
 * @author Emigdio Hernández
 */
public interface EmisoraDaliDAO {

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
	 * @return un objeto {@link EmisoraDTO} con los datos de la emisora.
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
	 * Busca un elemento en el catálogo de emisoras por medio de su descripción.
	 * 
	 * @param descripcion
	 *            la descripción del elemento a consultar.
	 * @return un objeto {@link EmisoraDTO} con los datos de la emisora
	 *         encontrada. <code>null</code> si no se encuentra
	 *         correspondencia.
	 */
	EmisoraDTO buscarEmisoraPorDescripcion(String descripcion);
}
