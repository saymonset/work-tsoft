/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * FechasUtilServiceImpl.java
 * Apr 23, 2008
 */
package com.indeval.portaldali.middleware.services.util;

import java.util.Date;

import com.indeval.portaldali.persistence.dao.util.FechaDao;

/**
 * Implementación del servicio de consulta de fechas.
 * Utiliza {@link FechaDao} para consultar la BD en busca de las fechas solicitadas.
 * @author Emigdio Hernández
 *
 */
public class FechasUtilServiceImpl implements FechasUtilService {
	/**
	 * DAO para el acceso a la fecha de la BD
	 */
	FechaDao fechaDao = null;
	/* (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.util.FechasUtilService#getCurrentDate()
	 */
	public Date getCurrentDate() {
		return fechaDao.getCurrentDate();
	}
	/* (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.util.FechasUtilService#getFullCurrentDate()
	 */
	public Date getFullCurrentDate() {
		return fechaDao.getFullCurrentDate();
	}
	/**
	 * Obtiene el campo fechaDao
	 * @return  fechaDao
	 */
	public FechaDao getFechaDao() {
		return fechaDao;
	}
	/**
	 * Asigna el campo fechaDao
	 * @param fechaDao el valor de fechaDao a asignar
	 */
	public void setFechaDao(FechaDao fechaDao) {
		this.fechaDao = fechaDao;
	}

}
