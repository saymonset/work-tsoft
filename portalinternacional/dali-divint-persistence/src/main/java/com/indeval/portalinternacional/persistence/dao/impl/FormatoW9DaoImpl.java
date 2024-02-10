/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW9;
import com.indeval.portalinternacional.persistence.dao.FormatoW9Dao;

public class FormatoW9DaoImpl extends BaseDaoHibernateImpl implements FormatoW9Dao{

	 /** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(FormatoW9DaoImpl.class);
	
	/**
	 * @see com.indeval.portalinternacional.persistence.dao.FormatoW9Dao#findFormatoW9(java.lang.Long)
	 */
	public FormatoW9 findFormatoW9(Beneficiario beneficiario) {
		log.info("Entrando a findFormatoW9()");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(FormatoW9.class);
		detachedCriteria.add(Restrictions.eq("idCamposFormatoW9", beneficiario.getFormatoW9().getIdCamposFormatoW9()));
		return (FormatoW9) getHibernateTemplate().findByCriteria(detachedCriteria).get(0);
	}

}
