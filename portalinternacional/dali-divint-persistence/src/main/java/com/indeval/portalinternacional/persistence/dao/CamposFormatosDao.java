/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portalinternacional.middleware.servicios.modelo.Field3W8BEN;
import com.indeval.portalinternacional.middleware.servicios.modelo.Field3W8IMY;
import com.indeval.portalinternacional.middleware.servicios.modelo.Field3W9;
/**
 * 
 * @author Oscar Garcia
 *
 */
public interface CamposFormatosDao extends BaseDao{
	
	/**
	 * Regresa los campos para la forma W8BEN
	 * @return List<Field3W8BEN>
	 */
	public List<Field3W8BEN> getField3W8BEN();
	
	/**
	 * Regresa los campos para la forma W8BEN
	 * @return List<Field3W8BEN>
	 */
	public List<Field3W8IMY> getField3W8IMY();
	
	/**
	 * Regresa los campos para la forma W8BEN
	 * @return List<Field3W8BEN>
	 */
	public List<Field3W9> getField3W9();
	
}
