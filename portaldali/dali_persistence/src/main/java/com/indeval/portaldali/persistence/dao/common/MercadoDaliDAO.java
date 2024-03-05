/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * 27/02/2008
 */
package com.indeval.portaldali.persistence.dao.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.MercadoDTO;

/**
 * Interface que expone los métodos para las operaciones realizadas sobre el catálogo
 * de mercados de la base de datos.
 * 
 * @author Emigdio Hernández
 */
public interface MercadoDaliDAO {

	/**
	 * Obtiene una lista de objetos de tipo MercadoDTO que contiene los datos de 
	 * los mercados del catálogo de mercados de la base de datos.
	 * 
	 * @return  Lista de objetos de tipo MercadoDTO que contiene los datos de 
	 * los mercados del catálogo de mercados de la base de datos.
	 */
	List<MercadoDTO> buscarMercados();
	/**
	 * Obtiene un mercado de la base de datos utilizando el id único del mercado
	 * como criterio de bsqeda
	 * @param idMercado Identificador del mercado
	 * @return DTO con los datos obtenidos del mercado
	 */
	MercadoDTO buscarMercadoPorId(long idMercado);
}
