/**
 * 2H Software - Bursatec
 * Sistema de Consulta de Estados de Cuenta
 *
 */
package com.indeval.portaldali.persistence.dao.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;

/**
 * Define la funcionalidad que debe de cumplir los servicios que implementen
 * para las operaciones relacionadas con el 
 * catálogo de tipos de tenencia.
 * 
 * @author Emigdio Hernández
 * @version 1.0
 */
public interface TipoTenenciaDAO {

	/**
	 * Obtiene una lista de objetos <code>TipoTenenciaDTO</code> que representan cada uno 
	 * de los resultados obtenidos en la consulta de tipos de tenencia.
	 * Se utilizan los criterios de:
	 * TipoTenenciaDTO donde se indica:
	 * Tipo de cuenta (Nombrada o controlada)
	 * Tipo de naturaleza (activo o pasivo)
	 * Tipo de custodia (valor o efectivo)
	 * @param tipoTenencia  DTO con los criterios de búsqueda.
	 * @return  Lista de objetos de tipo TipoTenenciaDTO que contiene los datos de los tipos 
	 * de tenencia obtenidos como resultado de la consulta.
	 */
	public List<TipoTenenciaDTO> buscarTipoTenenciaPorNaturalezaTipoCuentaYTipoCustodia(TipoTenenciaDTO tipoTenencia);
	
	/**
	 * Busca un elemento en el catálogo de tipos de tenencia por medio de su
	 * identificador.
	 * 
	 * @param idTipoTenencia
	 *            el identificador a consultar.
	 * @return un elemento de tipo {@link TipoTenenciaDTO} el cual corresponde
	 *         al identificador proporcionado. <code>null</code> en caso de no
	 *         existir el elemento.
	 */
	public TipoTenenciaDTO buscarTipoTenenciaPorId(long idTipoTenencia);
}
