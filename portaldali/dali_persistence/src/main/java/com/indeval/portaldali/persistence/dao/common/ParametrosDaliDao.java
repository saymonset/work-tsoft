/*
 * Copyrigth (c) 2010 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.common;

import com.indeval.portaldali.middleware.dto.ParametrosDaliDTO;

/**
 * Interface que expone los m&eacute;todos para las acciones sobre los parametros del Dali.
 * 
 * @author Maria C. Buendia
 * @version 1.0
 */
public interface ParametrosDaliDao {

	/** 
	 * Obtiene el valor de los parametros de Dali.
	 * @param objeto ParametrosDali
	 */
	ParametrosDaliDTO getAll();	
	
}