/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class DateUtil {

    /** Define el patron de fecha */
    protected static String PATTERN = "MM/dd/yyyy";

    /**
     * Metodo que formatea una fecha a un patron dado
     * 
     * @param date Fecha a formatear
     * @param pattern Patron a aplicar
     *
     * @return String con la fecha formateada
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    /**
     * Metodo que formatea una fecha con el patron MM/dd/yyyy
     * 
     * @param date Fecha a formatear
     *
     * @return String con la fecha formateada
     */
    public static String date2SybaseString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(PATTERN);
        return formatter.format(date);
    }

    /**
     * Prepara una fecha con la hora en algun extremo en segundos, ya sea al primer 
     * o al &uacute;ltimo segundo del d&iacute;a segun el par&aacute;metro aPrimerSegundo.
     * 
     * @param fecha
     *            La fecha a preparar.
     * @param aPrimerSegundo
     *            Boolean que indica si preparar la hora al primer o ultimo
     *            segundo del dia.
     * @return La fecha modificada.
     */
    public static Date preparaFechaConExtremoEnSegundos(Date fecha,
            boolean aPrimerSegundo) {
        
        if(fecha != null) {
            Calendar cal = Calendar.getInstance();
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
            return cal.getTime();
        }
        return fecha;
    }
    
	public static Calendar preparaFechaConExtremoEnSegundos(Calendar fecha, boolean aPrimerSegundo) {
        if(fecha != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(fecha.getTime());
            int anio = cal.get(Calendar.YEAR);
            int mes = cal.get(Calendar.MONTH);
            int dia = cal.get(Calendar.DATE);
            if (aPrimerSegundo) {
                cal.set(anio, mes, dia, 0, 0, 0);
                cal.set(Calendar.MILLISECOND, 0);
            } else {
                cal.set(anio, mes, dia, 23, 59, 59);
				cal.set(Calendar.MILLISECOND, 999);
            }
            return cal;
        }
        return fecha;
    }
    
    /**
     * Construye un objeto Date a partir de parametros enteros
     *
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @return Date
     */
    public static final Date getDate(int year, int month, int day, int hour,
            int minute) {
        Calendar cal = new GregorianCalendar(year, month - 1, day, hour, minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * Prepara el intervalo de fechas. A la fecha inicial la prepara en el segundo 0 
     * del d&iacute;a y la fecha final la prepara en el ultimo segundo del d&iacute;a.
     * 
     * @param fechaInicial
     *            La fecha inicial a preparar.
     * @param fechaFinal
     *            La fecha final a preparar.
     * @return Las fechas modificadas en un arreglo.
     */
    public static Date[] preparaIntervaloFechas(Date fechaInicial,
            Date fechaFinal) {
        Date[] dates = new Date[2];
        dates[0] = DateUtil
                .preparaFechaConExtremoEnSegundos(fechaInicial, true);
        dates[1] = DateUtil.preparaFechaConExtremoEnSegundos(fechaFinal, false);
        return dates;
    }

    /**
     * Metodo que incrementa una fecha en el numero de dias indicados
     * 
     * @param fechaInicial
     * @param offset
     * @return Date
     */
    public static Date addDays(Date fechaInicial, int offset) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaInicial);
        calendar.add(Calendar.DATE, offset);
        return calendar.getTime();
    }
    
    public static Long getPlazo(Date fechaInicial, Date fechaFinal){
    	Calendar inicio = Calendar.getInstance();
    	Calendar fin = Calendar.getInstance();
    	inicio.setTime(fechaInicial);
    	fin.setTime(fechaFinal);
    	long diff = fin.getTimeInMillis() - inicio.getTimeInMillis();
    	diff = diff / (24*60*60*1000);
    	
    	return new Long(diff);
    }

	public static long getPlazo(Calendar fechaInicial, Calendar fechaFinal){
		fechaInicial = preparaFechaConExtremoEnSegundos(fechaInicial, true);
		fechaFinal = preparaFechaConExtremoEnSegundos(fechaFinal, true);
    	long diff = fechaFinal.getTimeInMillis() - fechaInicial.getTimeInMillis();
    	diff = diff / (24*60*60*1000);

    	return diff;
    }

    /**
     * Metodo que parsea un String en un tipo Date
     * 
     * @param fecha
     * @throws ParseException
     * @return La fecha en formato "dd/MM/yyyy" y de tipo Date
     */
    public static Date convierteStringToDate(String fecha)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = sdf.parse(fecha);
        return date;
    }

    /**
     * @param fecha
     * @return Date
     * @throws ParseException
     */
    public static Date convierteStringToDate2(String fecha)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date date = sdf.parse(fecha);
        return date;
    }

    /**
     * Metodo que parsea un String en un tipo Date
     * 
     * @param fecha
     * @throws ParseException
     * @return La fecha en formato "aa/MM/dd" y de tipo Date
     */
    public static Date convierteStringToDate3(String fecha)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
        Date date = sdf.parse(fecha);
        return date;
    }
    
    /**
     * Método que parsea una cadena a fecha según el formato dado.
     * @param fecha Cadena con la fecha a parsear.
     * @param formato Formato de la fecha.
     * @return Fecha parseada.
     * @throws ParseException En caso de ocurrir un error.
     */
    public static Date stringToDate(String fecha, String formato) {
    	Date date = null;
    	if(StringUtils.isNotBlank(fecha) && StringUtils.isNotBlank(formato)) {    		
    		try {
    			SimpleDateFormat sdf = new SimpleDateFormat(formato);
    			sdf.setLenient(false);
				date = sdf.parse(fecha);
			} 
    		catch (ParseException e) {}
    	}
    	return date;
    }
    
    
	/**
	 * Obtiene la hora de una fecha.
	 * @param fechaCarga Fecha de la cual se obtendra la hora
	 * @return hora en formato HH:mm:ss
	 */
	public static String obetnerHoraDeFecha(Date fechaCarga) {
		String hora = "";
		if (fechaCarga != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				hora = sdf.format(fechaCarga);
			} 
			catch (Exception e) {
				e.printStackTrace();
				hora = "";
			}
		}
		return hora;
	}

    /**
     * Compara las fechas expuestas, solo cotejando el d&iacute;a, mes y ano.
     * 
     * @param fecha1
     *            Fecha Base
     * @param fecha2
     *            Fecha que se comparara
     * @return 0 en caso de ser iguales, -1 en caso de que la fecha base sea
     *         menor que la fecha a comparar, 1 en caso de que la fecha base sea
     *         mayor a la fecha a comparar.
     * @throws BusinessException
     *             En caso de que alguna de las fechas sea nula.
     */
    public static int comparaFechasDias(Date fecha1, Date fecha2) {

        if (fecha1 == null || fecha2 == null) {
            throw new IllegalArgumentException();
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(fecha1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(fecha2);

        if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR)) {
            return -1;
        }
        if (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR)) {
            return 1;
        }
        if (cal1.get(Calendar.DAY_OF_YEAR) < cal2.get(Calendar.DAY_OF_YEAR)) {
            return -1;
        }
        if (cal1.get(Calendar.DAY_OF_YEAR) > cal2.get(Calendar.DAY_OF_YEAR)) {
            return 1;
        }
        return 0;
    }

}
