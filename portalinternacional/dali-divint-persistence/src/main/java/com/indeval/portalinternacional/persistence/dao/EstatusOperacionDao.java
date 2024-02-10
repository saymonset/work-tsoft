/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstatusOperacion;

/**
 * @author javiles
 *
 */
public interface EstatusOperacionDao extends BaseDao {
    
    public List<EstatusOperacion> findEstatusOperaciones();

}
