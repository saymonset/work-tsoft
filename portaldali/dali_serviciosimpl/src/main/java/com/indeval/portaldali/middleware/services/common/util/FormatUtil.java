/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.middleware.services.common.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase de utilera para realizar la conversión de formatos de fechas o moneda.
 * @author Emigdio Hernández
 *
 */
public class FormatUtil {
	/**
	 * Formato predeterminado dd/MM/yyyy
	 */
	public static DateFormat FORMATO_DMA = new SimpleDateFormat("dd/MM/yyyy");
	/**
	 * Formato fecha largo dd/MM/yyyy HH:mm:ss
	 */
	public static DateFormat FORMATO_DMAHMS = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	/**
	 * Formato de moneda para las cantidades de efectivo
	 */
	public static DecimalFormat FORMATO_MONEDA = new DecimalFormat("$ ###,##0.00");
	/**
	 * Formato de numrico para las cantidades enteras de posición
	 */
	public static DecimalFormat FORMATO_ENTERO = new DecimalFormat("###,##0");
	/**
	 * Transforma una fecha representada por una cadena en un objeto del tipo {@link Date} basado en el formato
	 * dd/MM/yyyy
	 * @param fechaString
	 * @return Objeto que representa la fecha recibida como parámetro, null en caso de que no pueda ser parseada correctamente
	 */
	public static Date stringToDate(String fechaString){
		Date fecha = null;
		try{
			fecha = FORMATO_DMA.parse(fechaString);
		}catch (Exception e) {
			//no se pudo dar formato a la fecha
		}
		return fecha;
	}
	/**
	 * Formatea un tipo de dato double al formato estndar utilizado en el sistema.
	 * @param numero número a formatear
	 * @return Cadena que representa al número recibido como parámetro en el formato estndar
	 */
	public static String numberToString(double numero){	
		
		return FORMATO_MONEDA.format(numero);
	}
	
	/**
	 * Formatea un tipo de dato double al formato estndar utilizado en el sistema para
	 * la posición.
	 * @param numero número a formatear
	 * @return Cadena que representa al número recibido como parámetro en el formato estndar
	 */
	public static String numberIntToString(double numero){
		try{
			return FORMATO_ENTERO.format(numero);
		}catch (Exception e) {
			//no se pudo dar formato a la fecha
		
		}
		return null;
	}
	
	
	/**
	 * Transforma una fecha representada por un objeto del tipo {@link Date} basado en el formato
	 * dd/MM/yyyy HH:mm:ss
	 * @param fechaString
	 * @return Objeto que representa la fecha recibida como parámetro, null en caso de que no pueda ser parseada correctamente
	 */
	public static String DateToLongString(Date fecha){
		String  fechaCadena = null;
		try{
			fechaCadena = FORMATO_DMAHMS.format(fecha);
		}catch (Exception e) {
			//no se pudo dar formato a la fecha
		}
		return fechaCadena;
	}
	
	/**
	 * Transforma una fecha representada por un objeto del tipo {@link Date} basado en el formato
	 * dd/MM/yyyy HH:mm
	 * @param fechaString
	 * @return Objeto que representa la fecha recibida como parámetro, null en caso de que no pueda ser parseada correctamente
	 */
	public static String DateToShortString(Date fecha){
		String  fechaCadena = null;
		try{
			fechaCadena = FORMATO_DMA.format(fecha);
		}catch (Exception e) {
			//no se pudo dar formato a la fecha
		}
		return fechaCadena;
	}
	/**
	 * Formatea un tipo de dato long al formato estndar utilizado en el sistema para
	 * la posición.
	 * @param numero número a formatear
	 * @return Cadena que representa al número recibido como parámetro en el formato estndar
	 */
	public static String longToString(long numero){
		return FORMATO_ENTERO.format(numero);
	}
}
