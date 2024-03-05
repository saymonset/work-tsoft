/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * EmisionDaliDAO.java
 * 04/03/2008
 */
package com.indeval.portaldali.persistence.dao.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.InstitucionDTO;

/**
 * Interface que expone los métodos para las operaciones realizadas sobre el catálogo
 * de Instituciones
 * 
 * @author Emigdio Hernández
 */
public interface InstitucionDaliDAO {

	/**
	 * Obtiene una lista de objetos de tipo {@link InstitucionDTO} que contiene los datos de las instituciones
	 * del catálogo de instituciones.
	 * @return  Lista de objetos de tipo {@link InstitucionDTO} que contiene los datos de las instituciones
	 * obtenidas.
	 */
	List<InstitucionDTO> buscarInstituciones();
	/**
	 * Obtiene un objeto del tipo {@link InstitucionDTO} que representa los datos de una institución en particular
	 * cuyo ID corresponda al ID enviado como parámetro.
	 * @param id Identificador de la Institucion a recuperar
	 * @return DTO que representa los datos de la instutución
	 */
	InstitucionDTO buscarInstitucionPorId(long id);
	
	/**
	 * Obtiene una lista de objetos de tipo {@link InstitucionDTO} que contiene los datos de las instituciones
	 * del catálogo de instituciones cuyo nombre comience con la cadena enviada como parámetro.
	 * @return  Lista de objetos de tipo {@link InstitucionDTO} que contiene los datos de las instituciones
	 * obtenidas.
	 */
	List<InstitucionDTO> buscarInstitucionesPorPrefijo(String prefijo);
	
	/**
	 * Obtiene una lista de objetos de tipo {@link String} que contiene los datos del origen de las instituciones
	 * cuyo nombre comience con la cadena enviada como parámetro.
	 * @return  Lista de objetos de tipo {@link String} que contiene los datos de los origenes de las instituciones
	 * registradas.
	 */
	List<String> buscarOrigenPorPrefijo(String prefijo);
	
	
	/**
	 * Obtiene un objeto del tipo {@link InstitucionDTO} que representa los datos de una institución en particular
	 * cuyo nombre corto corresponda al nombre enviado como parámetro.
	 * @param nombre Nombre de la Institucion a recuperar
	 * @return DTO que representa los datos de la instutución, null en caso de no encontrar una institución
	 */
	InstitucionDTO buscarInstitucionPorNombreCorto(String nombre);
	/**
	 * Obtiene un objeto del tipo  {@link InstitucionDTO} que representa los datos
	 * de una institución en particular cuya concatenación de la clave del tipo y del folio sea
	 * igual al parámetro recibido
	 * @param claveFolio Clave de tipo institución concatenado con el folio de tipo institución
	 * @return DTO que representa los datos de la instutución, null en caso de no encontrar una institución
	 */
	InstitucionDTO buscarInstitucionPorClaveYFolio(String claveFolio);
	
	
	/**
	 * Obtiene un Objeto de tipo @link InstitucionDTO} a partir 
	 * del casfim  o null en caso de que no exista
	 * 
	 * @param casfim
	 * @return
	 */
	InstitucionDTO buscarInstitucionPorCasfim(String casfim);
	
	/**
	 * Obtiene un Objeto de tipo @link InstitucionDTO} a partir de la clave spei
	 * 
	 * @param casfim
	 * @return
	 */
	InstitucionDTO buscarInstitucionPorClaveSpei(String claveSpei);
	
	
	
}
