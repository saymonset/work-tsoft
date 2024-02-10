/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.persistence.dao;

import java.util.List;

import com.indeval.portalinternacional.middleware.servicios.dto.InstitucionWebDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.Instituciones;

/**
 * Interfaz de DAO para las operaciones relacionadas con instituciones
 * 
 * @author IDS Comercial S.A de C.V.
 * @version $Revision$
 */

public interface InstitucionesDao {

	/**
	 * Se obtiene una instituci&oacute;n en base a su tipo y su folio
	 * 
	 * @param tipoInstitucion
	 * @param folio
	 * 
	 * @return java.util.List
	 */
	Instituciones getInstitucionByTipoYFolio(String tipoInstitucion, String folio);

	
	List<InstitucionWebDTO> buscarInstitucionesPorPrefijo(String prefijo);
	
	InstitucionWebDTO buscarInstitucionPorClaveYFolio(String idFolio);
}
