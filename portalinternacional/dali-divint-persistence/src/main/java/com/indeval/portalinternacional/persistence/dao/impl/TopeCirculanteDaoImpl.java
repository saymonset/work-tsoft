/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao.impl;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.middleware.servicios.modelo.TopeCirculante;
import com.indeval.portalinternacional.persistence.dao.TopeCirculanteDao;

/**
 * @author <a href="mailto:ivan.kazakov@2hsoftware.com.mx">Ivan Kazakov</a>
 *
 */
public class TopeCirculanteDaoImpl extends BaseDaoHibernateImpl implements TopeCirculanteDao {

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.TopeCirculanteDao#getTopeCirculante(java.lang.Long)
	 */
	public TopeCirculante getTopeCirculante(Long idEmision) {
		return ((TopeCirculante)this.getByPk(TopeCirculante.class, idEmision));
	}
}
