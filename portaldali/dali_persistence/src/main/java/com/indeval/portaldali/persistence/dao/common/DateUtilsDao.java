/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.common;

import java.util.Date;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface DateUtilsDao {
	
	/**
	 * @return Date
	 */
	Date getDateFechaDB();
	
	/**
	 * @return String
	 */
	String getStringFechaDB();
	
	/**
	 * @return String
	 */
	String getStringFechaDBSinHoras();
	
	/**
	 * @return v
	 */
	String getStringFechaDBInicioDia();
	
	/**
	 * @return String
	 */
	String getStringFechaDBFinDia();
	
	/**
	 * @param date
	 * @return Date
	 */
	Date getFechaDBInicioDia(String date);
	
	/**
	 * @param date
	 * @return Date
	 */
	Date getFechaDBFinDia(String date);
	
	/**
	 * @return String
	 */
	String getStringHoras();
    
    /**
     * Recibe una fecha y la regresa con la hora en ceros. Esta fecha sirve para hacer comparaciones
     * entre fechas, cuya hora de comparaci&oacute;n siempre va a ser 12:00AM.
     * @param fecha - Fecha y hora que se desea cambiar, ejemplo: Mon Sep 18 10:21:51 CDT 2006
     * @return Date - Fecha con hora en ceros 00:00:00, ejemplo: Mon Sep 18 00:00:00 CDT 2006
     */
    Date getFechaHoraCero(Date fecha);
    
    /**
     * @param fecha
     * @param pattern
     * @return String
     */
    String date2String(Date fecha,String pattern);
    
    /**
     * Recibe una fecha y la regresa con la hora en 23:59:59. 
     * @param fecha - Fecha y hora que se desea cambiar, ejemplo: Mon Sep 18 10:21:51 CDT 2006
     * @return Date - Fecha con hora en 23:59:59, ejemplo: Mon Sep 18 23:59:59 CDT 2006
     */
    Date getFechaHoraFinDia(Date fecha); 
    
}
