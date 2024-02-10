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
import com.indeval.portalinternacional.middleware.servicios.modelo.EstatusOperacion;
import com.indeval.portalinternacional.persistence.dao.EstatusOperacionDao;

/**
 * @author javiles
 *
 */
public class EstatusOperacionDaoImpl extends BaseDaoHibernateImpl implements
        EstatusOperacionDao {
    
    /** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(EstatusOperacionDaoImpl.class);

    /**
     * @see com.indeval.portalinternacional.persistence.dao.EstatusOperacionDao#findEstatusOperaciones()
     */
    @SuppressWarnings("unchecked")
    public List<EstatusOperacion> findEstatusOperaciones() {

        log.info("Entrando a findEstatusOperaciones()");
        
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(EstatusOperacion.class);
        detachedCriteria.addOrder(Order.asc("idEstatusOperacion"));
        
        return getHibernateTemplate().findByCriteria(detachedCriteria);
        
    }

}
