/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * FechasUtilService.java
 * Apr 23, 2008
 */
package com.indeval.portaldali.middleware.services.util;

import java.util.Date;

/**
 * Interfaz utilizada para el acceso a las consultas de fechas de la BD
 * 
 * @author Emigdio Hernández
 * 
 */
public interface FechasUtilService {
	/**
	 * Obtiene la fecha actual de la BD con hora, minutos y segundos en cero
	 * 
	 * @return
	 */
	public Date getCurrentDate();
	/**
	 * Obtiene la fecha actual de la BD con hora, minutos y segundos
	 * 
	 * @return
	 */
	public Date getFullCurrentDate();
}
