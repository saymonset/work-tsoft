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
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8BEN;
import com.indeval.portalinternacional.persistence.dao.FormatoW8BENDao;

public class FormatoW8BENDaoImpl extends BaseDaoHibernateImpl implements FormatoW8BENDao{

	 /** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(FormatoW8BENDaoImpl.class);
	
	/**
	 * @see com.indeval.portalinternacional.persistence.dao.FormatoW8BENDao#findFormatoW8BEN(com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario)
	 */
	public FormatoW8BEN findFormatoW8BEN(Beneficiario beneficiario) {
		log.info("Entrando a findFormatoW8BEN()");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(FormatoW8BEN.class);
		detachedCriteria.add(Restrictions.eq("idCamposFormatoW8ben", beneficiario.getFormatoW8BEN().getIdCamposFormatoW8ben()));
		return (FormatoW8BEN) getHibernateTemplate().findByCriteria(detachedCriteria).get(0);
	}

}
