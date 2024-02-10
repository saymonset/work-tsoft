/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.persistence.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.middleware.servicios.modelo.REmisionBoveda;
import com.indeval.portalinternacional.persistence.dao.REmisionBovedaDao;

/**
 * DAO de implementaci&oacute;n de REmisionBovedaDao
 */

public class REmisionBovedaDaoImpl extends BaseDaoHibernateImpl implements REmisionBovedaDao {

    /** Logger **/
	private static final Logger LOG = LoggerFactory.getLogger(REmisionBovedaDaoImpl.class);

    /**
     * @see com.indeval.portalinternacional.persistence.dao.REmisionBovedaDao#getByIdEmision(Long)
     */
    public List<REmisionBoveda> getByIdEmision(Long idEmision) {
        StringBuffer query = new StringBuffer();
        query.append(" from " + REmisionBoveda.class.getName() + " reb ");
        query.append(" where reb.idEmision = :idEmision ");
        Query q = getSession().createQuery(query.toString()).
                setParameter("idEmision", idEmision);
        return (List<REmisionBoveda>) q.list();
    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.REmisionBovedaDao#save(REmisionBoveda)
     */
    public void update(REmisionBoveda obj) {
        getSession().update(obj);
        getSession().flush();
    }

}
