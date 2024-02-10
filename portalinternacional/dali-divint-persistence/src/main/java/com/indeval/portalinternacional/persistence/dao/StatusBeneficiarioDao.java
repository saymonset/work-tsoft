/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portalinternacional.middleware.servicios.modelo.StatusBeneficiario;
/**
 * 
 * @author Oscar Garcia
 *
 */

public interface StatusBeneficiarioDao extends BaseDao{

	/**
	 * Devuelve una lista de todos los StatusBeneficiario
	 * @return List<StatusBeneficiario>
	 */
	List<StatusBeneficiario> findStatusBeneficiario();
	
}
