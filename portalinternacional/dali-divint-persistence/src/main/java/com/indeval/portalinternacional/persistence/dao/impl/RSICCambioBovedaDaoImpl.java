/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.persistence.dao.impl;

import org.hibernate.FlushMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.middleware.servicios.modelo.RSICCambioBoveda;
import com.indeval.portalinternacional.persistence.dao.RSICCambioBovedaDao;

/**
 * DAO de implementaci&oacute;n de EmisionesConsultasDao
 */

public class RSICCambioBovedaDaoImpl extends BaseDaoHibernateImpl implements RSICCambioBovedaDao {

    /** Logger **/
	private static final Logger LOG = LoggerFactory.getLogger(RSICCambioBovedaDaoImpl.class);

    /**
     * @see com.indeval.portalinternacional.persistence.dao.RSICCambioBovedaDao#save(RSICCambioBoveda)
     */
    public void save(RSICCambioBoveda obj) {
        getHibernateTemplate().getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
        Integer idFileTransfer = (Integer) getHibernateTemplate().save(obj);
        getHibernateTemplate().flush();
    }

}
