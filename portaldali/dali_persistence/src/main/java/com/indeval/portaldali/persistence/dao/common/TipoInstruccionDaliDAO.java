/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * TipoInstruccionDaliDAO.java
 * 29/02/2008
 */
package com.indeval.portaldali.persistence.dao.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;

/**
 * Interface que expone los métodos para las operaciones realizadas sobre el catálogo
 * de tipos instrucción de la base de datos.
 * 
 * @author Emigdio Hernández Rodríguez
 * @version 1.0
 */
public interface TipoInstruccionDaliDAO {

	/**
	 * Obtiene una lista con objetos de tipo {@link TipoInstruccionDTO} con los datos
	 * de los tipos de instrucción obtenidos de la base de datos..
	 *
	 * 
	 * @return  Lista con objetos de tipo {@link TipoInstruccionDTO} con los datos de los tipos de 
	 * instrucciones.
	 */
	List<TipoInstruccionDTO> buscarTiposDeInstruccion();
	/**
	 * Busca un Tipo de Instrucción en la base de datos basado en el ID.
	 * @param idTipoInstruccion Id del tipo de instrucción.
	 * @return DTO que representa los datos del tipos de instrucción.
	 */
	TipoInstruccionDTO buscarTipoDeInstruccionPorId(long idTipoInstruccion);
	
	/**
	 * Obtiene una lista con objetos de tipo {@link TipoInstruccionDTO} con los datos
	 * de los tipos de instrucción obtenidos de la base de datos.
	 * Se filtran los tipos de instrucción por el prefijo del nombre corto del tipo de instrucción
	 * @param prefijo La cadena con el prefijo a filtrar
	 * @return  Lista con objetos de tipo {@link TipoInstruccionDTO} con los datos de los tipos de 
	 * instrucciones.
	 */
	List<TipoInstruccionDTO> buscarTiposDeInstruccionPorPrefijo(String prefijo);
	/**
	 * Busca un Tipo de Instrucción en la base de datos basado en su clave
	 * @param claveTipoInstruccion Clave  del tipo de instrucción.
	 * @return DTO que representa los datos del tipos de instrucción.
	 */
	TipoInstruccionDTO buscarTipoDeInstruccionPorClave(String claveTipoInstruccion);
	/**
	 * Obtiene una lista con objetos de tipo {@link TipoInstruccionDTO} con los datos
	 * de los tipos de instrucción obtenidos de la base de datos..
	 * Se filtran los tipos de instrucción por un indicador de efectivo o de valor.
	 * @param tiposCustodia Lista de valores separados por coma que indican los tipos de custoria que aplican
	 * para la consulta de instrucciones.
	 * @return  Lista con objetos de tipo {@link TipoInstruccionDTO} con los datos de los tipos de 
	 * instrucciones.
	 */
	List<TipoInstruccionDTO> buscarTiposDeInstruccion(String tiposCustodia);
	
	/**
	 * Busca en el catálogo de tipos de instrucción aquellos tipos de instrucción de efectivo que comiencen con un prefijo dado.
	 * 
	 * @param prefijo el prefijo a consultar en el catálogo
	 * @return una lista con los tipos de instrucción que cumplen con el criterio de consulta.
	 */
	List<TipoInstruccionDTO> buscarTiposDeInstruccionPorIdsEfectivo(String prefijo);
	
	/**
	 * Busca en el catálogo de tipos de instrucción aquellos tipos de instrucción de valores que comiencen con un prefijo dado.
	 * 
	 * @param prefijo el prefijo a consultar en el catálogo
	 * @return una lista con los tipos de instrucción que cumplen con el criterio de consulta.
	 */
	List<TipoInstruccionDTO> buscarTiposDeInstruccionPorIdsValores(String prefijo);
}
