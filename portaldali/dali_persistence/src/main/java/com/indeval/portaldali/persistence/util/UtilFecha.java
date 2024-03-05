/*
 * Copyright (c) 2006 Bursatec. All Rights Reserved
 */
package com.indeval.portaldali.persistence.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Clase que contiene métodos para la manipulacion de fechas tales como la suma
 * y resta de d&iacute;as a una fecha, el calculo de d&iacute;as h&aacute;biles entre otras
 * funciones.
 * 
 * @author Placido Escalera
 */

public class UtilFecha {

	/**
	 * Suma una cantidad de dias a la fecha proporcionada. Notese que la
	 * cantidad de dias puede ser negativa o positiva.
	 */
	public static Date addDays(Date fecha, int dias) {
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(fecha);
		cal.add(Calendar.DAY_OF_MONTH, dias);
		
		return cal.getTime();
	}

	/**
	 * Determina si la fecha proporcionada es un d&iacute;a h&aacute;bil
	 */
	public static boolean isDiaHabil(Date fecha){
		// Primero determinar si la fecha es sabado o domingo
		if (fecha.getDay() == 0 || fecha.getDay() == 6) {
			return false;
		}
		return true;
	}// fin metodo mstValidaHabil( )

	/**
	 * Suma una cantidad de dias habiles a una fecha. El parametro d&iacute;as puede
	 * ser negativo.
	 */
	public static Date addDiasHabiles(Date fecha, int dias) {
		Date fechaRet = new Date();

		fechaRet.setDate(fecha.getDate());
		fechaRet.setMonth(fecha.getMonth());
		fechaRet.setYear(fecha.getYear());
		
		fechaRet.setSeconds(fecha.getSeconds());
		fechaRet.setMinutes(fecha.getMinutes());
		fechaRet.setHours(fecha.getHours());

		if (dias > 0) {
			for (int i = 0; i < dias; i++) {
				do {
					fechaRet = UtilFecha.addDays(fechaRet, 1);
				}
				while (!UtilFecha.isDiaHabil(fechaRet));
			}// END for(int i=0.....
		}// END if(dias > 0)
		else {
			for (int i = 0; i > dias; i--) {
				do {
					fechaRet = UtilFecha.addDays(fechaRet, -1);
				}
				while (!UtilFecha.isDiaHabil(fechaRet));
			}// END for(int i=0.....
		}
		return fechaRet;
	}// END public Date addDiasHabiles(Date fecha, int dias)
	
	public static Date preparaFechaConExtremoEnSegundos(Date fecha, boolean aPrimerSegundo) {
    	Calendar cal = Calendar.getInstance();
    	
    	if (fecha != null) {
    		cal.setTime(fecha);
            int anio = cal.get(Calendar.YEAR);
            int mes = cal.get(Calendar.MONTH);
            int dia = cal.get(Calendar.DATE);
            if (aPrimerSegundo) {
                cal.set(anio, mes, dia, 0, 0, 0);
                cal.set(Calendar.MILLISECOND, 0);
            } else {
                cal.set(anio, mes, dia, 23, 59, 59);
            }	
    	}
        
        return fecha != null ? cal.getTime() : null;
    }
}// END public class UtilFecha

