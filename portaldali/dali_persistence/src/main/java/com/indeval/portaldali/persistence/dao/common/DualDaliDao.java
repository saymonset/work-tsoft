/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.common;

import java.util.Date;

import com.bursatec.persistence.dao.BaseDao;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface DualDaliDao extends BaseDao {

	/**
	 * Recupera la fecha actual del sistema
	 * @return Date
	 */
    Date getFechaActual();

}
