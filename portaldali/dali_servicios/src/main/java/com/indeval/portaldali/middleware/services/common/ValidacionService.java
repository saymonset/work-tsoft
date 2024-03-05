/**
 * Bursatec - Portal DALI
 * Copyrigth (c) 2014 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.common;

import java.util.Date;

/**
 * Interface de negocio que expone servicios para validaciones comunes en el sistema.
 * 
 * @author Pablo Balderas
 *
 */
public interface ValidacionService {
	
	boolean validarFechaValida(String strFecha, String formatoFecha);
	
	/**
	 * Valida si una cadena representa una fecha en el formato indicado y si dicha fecha
	 * está dentro del rango del último año calendario.
	 * @param strFecha Cadena con la fecha a validar.
	 * @param formatoFecha Formato a cumplir
	 * @return true si la validación es correcta; false en caso contrario.
	 */
	boolean validarFechaRangoUnAnio(String strFecha, String formatoFecha);
	
	/**
	 * Valida si una cadena representa una fecha en el formato indicado y, opcionalmente, si
	 * es un día hábil.
	 * @param strFecha Cadena con la fecha a validar.
	 * @param formatoFecha Formato a cumplir
	 * @param actual true si valida que sea la fecha actual.
	 * @return true si la validación es correcta; false en caso contrario.
	 */
	boolean validarFechaDiaHabil(String strFecha, String formatoFecha, boolean actual);
	
	/**
	 * Valida si una fecha es un día hábil.
	 * @param fecha Fecha a validar.
	 * @return true si es día hábil; false en caso contrario.
	 */
	boolean validarFechaDiaHabil(Date fecha);
	
	/**
	 * Valida que la cadena cumpla con el formato de RFC o CURP.
	 * @param rfcCurp Cadena a validar.
	 * @return true si la cadena cumple con el formato; false en caso contrario.
	 */
	boolean validarRfcCurp(String rfcCurp);
	
	/**
	 * Valida si la cadena cumple con la expresion regular.
	 * @param cadena Cadena a validar.
	 * @param expresionRegular Expresion regular para validar la cadena.
	 * @return true si se cumple la expresion; false en caso contrario.
	 */
	boolean validarExpresionRegular(String cadena, String expresionRegular);
	
	/**
	 * Valida si la institucion y cuenta existen en el sistema.
	 * @param idInstitucion Id de la institución.
	 * @param folioInstitucion Folio de la isntitución.
	 * @param cuenta Cuenta a validar
	 * @param tipoCustodia Tipo de custodia de la cuenta.
	 * @param naturaleza Naturalez contable de la cuenta.
	 * @return
	 */
	boolean validarInstitucionCuenta(String idInstitucion, String folioInstitucion, String cuenta, String tipoCustodia, String naturaleza);

	/**
	 * Valida una divisa por su clave alfabetica
	 * @param divisa Divisa a validar
	 * @return true si la divisa es correcta; false en caso contrario.
	 */
	boolean validarDivisa(String divisa);
}
