package com.indeval.portalinternacional.persistence.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstatusDerecho;
import com.indeval.portalinternacional.persistence.dao.EstatusDerechoDao;

public class EstatusDerechoDaoImpl  extends BaseDaoHibernateImpl implements EstatusDerechoDao{
	
	private static final Logger LOG = LoggerFactory.getLogger(EstatusDerechoDaoImpl.class);

	public List<EstatusDerecho> findEstatusDerecho() {
		LOG.debug("EstatusDerechoDaoImpl.findEstatusDerecho....");
		final DetachedCriteria detachedCriteria = DetachedCriteria.forClass(EstatusDerecho.class);
		detachedCriteria.addOrder(Order.asc("idEstatusDerecho"));		
		return getHibernateTemplate().findByCriteria(detachedCriteria);
	}
}
