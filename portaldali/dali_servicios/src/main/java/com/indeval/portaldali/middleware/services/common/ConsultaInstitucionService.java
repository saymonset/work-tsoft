/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ConsultaInstitucionService.java
 * 06/03/2008
 */
package com.indeval.portaldali.middleware.services.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.InstitucionDTO;

/**
 * Define la funcionalidad necesaria  para realizar consulas al catálogo de instituciones
 * @author Emigdio
 *
 */
public interface ConsultaInstitucionService {
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
	 * Obtiene un objeto del tipo {@link InstitucionDTO} que representa los datos de una institución en particular
	 * cuyo nombre corto corresponda al nombre enviado como parámetro.
	 * @param nombre Nombre de la Institucion a recuperar
	 * @return DTO que representa los datos de la instutución
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
	 * Obtiene una lista de objetos de tipo {@link String} que contiene los datos del origen de las instituciones
	 * cuyo nombre comience con la cadena enviada como parámetro.
	 * @return  Lista de objetos de tipo {@link String} que contiene los datos de los origenes de las instituciones
	 * registradas.
	 */
	List<String> buscarOrigenPorPrefijo(String prefijo);
}
