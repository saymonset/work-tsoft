/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW9;
/**
 * 
 * @author Oscar Garcia Granados
 *
 */
public interface FormatoW9Dao extends BaseDao{
	
	/**
	 * 
	 * @param beneficiario
	 * @return FormatoW9
	 */
	FormatoW9 findFormatoW9(Beneficiario beneficiario);

}
