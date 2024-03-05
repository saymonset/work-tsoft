/*
 * Copyright (c) 2019 CMMV Tecnologia. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.common;

import com.indeval.portaldali.middleware.dto.PropiedadesDaliDTO;

/**
 * Interface que expone los metodos para las operaciones sobre la tabla C_PROPIEDADES_DALI
 * 
 * @author Amm
 */
public interface PropiedadesDaliDAO {

	/**
	 * Recupera un parametro de c_propiedades_dali
	 * 
	 * @param criterios
	 * @return
	 */
	PropiedadesDaliDTO obtenerParametroPorAmbienteYNombre(PropiedadesDaliDTO criterios);
	
}
