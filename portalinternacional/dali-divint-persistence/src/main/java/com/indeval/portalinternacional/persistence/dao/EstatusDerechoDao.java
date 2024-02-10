package com.indeval.portalinternacional.persistence.dao;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstatusDerecho;

public interface EstatusDerechoDao  extends BaseDao{

	List<EstatusDerecho> findEstatusDerecho();
}
