/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao;

import java.util.Date;
import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.persistence.modelo.Retiros;

/**
 * @author <a href="mailto:ivan.kazakov@2hsoftware.com.mx">Ivan Kazakov</a>
 *
 */
public interface RetirosDivIntDao extends BaseDao {

	/**
	 * @param idCuentaNombrada
	 * @param fechaConsulta
	 * @return List<Depositos>
	 */
	public List<Retiros> getRetirosDivInt(final Long idCuentaNombrada, final Date fechaConsulta);
	
}
