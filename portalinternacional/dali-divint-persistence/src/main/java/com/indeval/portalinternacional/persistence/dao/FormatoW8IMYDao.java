/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8IMY;
/**
 * 
 * @author Oscar Garcia Granados
 *
 */
public interface FormatoW8IMYDao extends BaseDao{
	
	/**
	 * 
	 * @param beneficiario
	 * @return FormatoW8IMY
	 */
	FormatoW8IMY findFormatoW8IMY(Beneficiario beneficiario);

}
