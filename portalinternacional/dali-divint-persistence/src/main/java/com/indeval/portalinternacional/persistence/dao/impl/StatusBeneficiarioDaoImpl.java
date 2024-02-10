/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.middleware.servicios.modelo.StatusBeneficiario;
import com.indeval.portalinternacional.persistence.dao.OperacionSicDao;
import com.indeval.portalinternacional.persistence.dao.StatusBeneficiarioDao;

public class StatusBeneficiarioDaoImpl extends BaseDaoHibernateImpl implements StatusBeneficiarioDao{
	
	 /** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(OperacionSicDao.class);

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.StatusBeneficiarioDao#findStatusBeneficiario()
	 */
	@SuppressWarnings("unchecked")
	public List<StatusBeneficiario> findStatusBeneficiario() {
		
		log.info("Entrando a StatusBeneficiarioDaoImpl.findStatusBeneficiario())");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StatusBeneficiario.class);
	    detachedCriteria.addOrder(Order.asc("idStatusBenef"));
	    return getHibernateTemplate().findByCriteria(detachedCriteria);
	}
	
	

}
