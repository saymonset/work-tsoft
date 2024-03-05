package com.indeval.portaldali.persistence.dao.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.TipoInstitucionDTO;

public interface TipoInstitucionDaliDAO {
	
	/**
	 * Obtiene una lista de objetos de tipo {@link TipoInstitucionDTO} que contiene los datos de los tipos de instituciones
	 * del catálogo de tipos de instituciones cuyo nombre comience con la cadena enviada como parámetro.
	 * @return  Lista de objetos de tipo {@link TipoInstitucionDTO} que contiene los datos de los tipos de instituciones
	 * obtenidas.
	 */
	public List<TipoInstitucionDTO> buscarTipoInstitucionPorPrefijo(String prefijo);

}
