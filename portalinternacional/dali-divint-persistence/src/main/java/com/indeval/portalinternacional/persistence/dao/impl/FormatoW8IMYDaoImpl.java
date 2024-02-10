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
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8IMY;
import com.indeval.portalinternacional.persistence.dao.FormatoW8IMYDao;

public class FormatoW8IMYDaoImpl extends BaseDaoHibernateImpl implements FormatoW8IMYDao{

	 /** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(FormatoW8IMYDaoImpl.class);
	
	/**
	 * @see com.indeval.portalinternacional.persistence.dao.FormatoW8IMYDao#findFormatoW8IMY(com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario)
	 */
	public FormatoW8IMY findFormatoW8IMY(Beneficiario beneficiario) {
		log.info("Entrando a findFormatoW8IMY()");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(FormatoW8IMY.class);
		detachedCriteria.add(Restrictions.eq("idCamposFormatoW8imy", beneficiario.getFormatoW8IMY().getIdCamposFormatoW8imy()));
		return (FormatoW8IMY) getHibernateTemplate().findByCriteria(detachedCriteria).get(0);
	}

}
