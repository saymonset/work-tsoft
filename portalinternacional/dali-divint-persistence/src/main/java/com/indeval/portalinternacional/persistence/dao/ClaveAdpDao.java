package com.indeval.portalinternacional.persistence.dao;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portalinternacional.middleware.servicios.modelo.adp.ClaveAdp;

public interface ClaveAdpDao extends BaseDao {

	/**
	 * Regresa un Clave ADP por su valor de clave
	 * 
	 * @param claveAdp
	 * @return ClaveAdp
	 */
	public ClaveAdp findClaveAdpByClaveAdp(final String claveAdp);
}