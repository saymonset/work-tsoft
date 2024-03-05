/**
 * Bursatec - Portal DALI
 * Copyrigth (c) 2014 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.constantes;

/**
 * Interface que expone las constantes utilizadas en las validaciones del sistema.
 * 
 * @author Pablo Balderas
 */
public interface ValidacionConstantes {

	/** Expresión regular para validar el CURRP */
	String ER_CURP = "[A-Z][A,E,I,O,U,X][A-Z]{2}[0-9]{2}[0-1][0-9][0-3][0-9][M,H][A-Z]{2}[B,C,D,F,G,H,J,K,L,M,N,Ñ,P,Q,R,S,T,V,W,X,Y,Z]{3}[0-9,A-Z][0-9]";
	
	/** Expresión regular para validar el RFC */
	String ER_RFC = "[A-Z]{3,4}[0-9]{6}[A-Z0-9]{0,3}";
	
	/** Expresión regular para validar números con 13 enteros y 8 decimales */
	String ER_NUMERO_13ENTEROS_8DECIMALES = "^\\d{0,13}(\\.\\d{1,8})?$";
	
	/** Expresión regular para validar números con 14 enteros */
	String ER_NUMERO_14ENTEROS = "^[0-9]{1,14}";
	
	/** Expresión regular que valida alfanuméricos y permite solo el caracter punto y espacio en blanco */
	String ER_ALFANUMERICOS_ESPACIO_PUNTO = "^[a-zA-Z0-9\\s.]+$";
	
	/** Expresion regular que valida numeros enteros o decimales */
	String ER_NUMERO_ENTERO_DECIMAL = "^\\d*(\\.\\d*)?$";
}
