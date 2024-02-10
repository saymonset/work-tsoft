/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8BEN;
/**
 * 
 * @author Oscar Garcia Granados
 *
 */
public interface FormatoW8BENDao extends BaseDao{
	
	/**
	 * 
	 * @param beneficiario
	 * @return FormatoW8BEN
	 */
	FormatoW8BEN findFormatoW8BEN(Beneficiario beneficiario);

}
