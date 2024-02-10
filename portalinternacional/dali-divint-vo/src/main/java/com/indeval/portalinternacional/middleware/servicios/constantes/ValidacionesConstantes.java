/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2016 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.constantes;

/**
 * Interfaz que expone las constantes utilizadas en las validaciones.
 * 
 * @author Pablo Balderas
 */
public interface ValidacionesConstantes {

	/** Expresion regular que valida una fecha en formato dd/MM/yyyy */
	String ER_FORMATO_FECHA_DD_MM_YYYY = 
		"^(((0[1-9]|[12]\\d|3[01])\\/(0[13578]|1[02])\\/((19|[2-9]\\d)\\d{2}))|"
		+ "((0[1-9]|[12]\\d|30)\\/(0[13456789]|1[012])\\/((19|[2-9]\\d)\\d{2}))|"
		+ "((0[1-9]|1\\d|2[0-8])\\/02\\/((19|[2-9]\\d)\\d{2}))|(29\\/02\\/((1[6-9]|"
		+ "[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$";
	
	/** Expresión regular que valida alfanumericos */
	String ER_ALFANUMERICOS = "^[a-zA-Z0-9]+$";
	
	/** Expresión regular que valida las series de las emisiones */
	String ER_EMISORA = "^[a-zA-Z0-9*+\\-&.,!#]+$";
	
	/** Expresión regular que valida las series de las emisiones */
	String ER_SERIE = "^[a-zA-Z0-9*+-.|/_]+$";

	/** Expresión regular que valida las bovedas */
	String ER_BOVEDA = "^[a-zA-Z0-9-]+$";
	
	/** Expresión regular que valida alfanumericos, espacio, guiones, diagonal y punto */
	String ER_ALFANUMERICO_ESPACIO_GUIONES_PUNTO_DIAGONAL = "^[a-zA-Z0-9\\s-_./]+$";

	/** Expresión regular que valida alfanumericos con espacio */
	String ER_ALFANUMERICO_ESPACIO = "^[a-zA-Z0-9\\s]+$";
	
	/** Expresión regular que valida numericos. */
	String ER_NUMERICOS = "^[0-9]+$";

    /** Expresión regular que valida alfabeticos */
    String ER_ALFABETICOS = "^[a-zA-Z]+$";

    /** Expresión regular que valida decimales */
    String ER_DECIMALES = "^[0-9]*(\\.)?[0-9]+$";
    
    /** Expresión regular que valida alfabeticos */
    String ER_NUMERICOS_DOS_DECIMAL = "^[0-9]+(\\.[0-9]{1,2})?$";
    
    /** Expresión regular que valida enteros y seis decimales */
    String ER_ENTEROS_SEIS_DECIMALES = "^[0-9]+(\\.[0-9]{1,6})?$";
	
	/** Longitud 4 */
	int LONGITUD_4 = 4;
	
	/** Longitud 5 */
	int LONGITUD_5 = 5;
	
	/** Longitud 6 */
	int LONGITUD_6 = 6;
	
	/** Longitud 7 */
	int LONGITUD_7 = 7;

	/** Longitud 10 */
	int LONGITUD_10 = 10;
	
	/** Longitud 12 */
	int LONGITUD_12 = 12;
	
	/** Longitud 15 */
	int LONGITUD_15 = 15;
	
	/** Longitud 18 */
	int LONGITUD_18 = 18;
	
	/** Longitud 40 */
	int LONGITUD_40 = 40;
}
