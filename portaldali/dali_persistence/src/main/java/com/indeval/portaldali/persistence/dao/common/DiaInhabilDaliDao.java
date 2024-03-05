/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.common;

import java.util.Date;
import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.persistence.model.DiaInhabil;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface DiaInhabilDaliDao extends BaseDao {

	/**
	 * Valida si la fecha recibida es inhabil
	 * @param fecha
	 * @return int 1 = es inhabil, 0 = es habil
	 */
	int fechaInhabil(Date fecha);
	
	/**
	 * Valida si la fecha recibida es inhabil
	 * @param fecha
	 * @return boolean
	 */
	boolean esInhabil(Date fecha);
    
    /**
     * @param fecha
     * @param offset
     * @return Date
     */
    Date agregaDiasHabiles(final Date fecha, int offset);
    
    /**
     * @param month
     * @param year
     * @return List
     */
    List<DiaInhabil> findDiasInhabilesByMonthYear(int month, int year);

}
