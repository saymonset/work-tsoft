/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portalinternacional.middleware.servicios.modelo.TopeCirculante;

/**
 * @author <a href="mailto:ivan.kazakov@2hsoftware.com.mx">Ivan Kazakov</a>
 *
 */
public interface TopeCirculanteDao extends BaseDao {

	/**
	 * @param idEmision
	 * @return TopeCirculante
	 */
	public TopeCirculante getTopeCirculante(Long idEmision);
	
}
