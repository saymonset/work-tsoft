/**
 * 2H Software
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Creado el Jan 7, 2008
 */
package com.indeval.portaldali.middleware.services.common.constants;

/**
 * Constantes para el catálogo de estados de una instrucción.
 *
 * @author José Antonio Huizar Moreno
 * @version 1.0
 *
 */
public interface EstadoInstruccionConstants {
	/**
	 * Intrucción con match
	 */
	int CON_MATCH = 0;
	/**
	 * Instrucción sin match
	 */
	int SIN_MATCH = 1;
	
	/** Instrucción cancelada */
	int CANCELADA = 2;
	
	/** Se refiere a que una instrucción ya ha sido liquidada */
	int INSTRUCCION_LIQUIDADA = 6;
	
	/** La clave del estado de instrucción LIQUIDADA */
	String CLAVE_EDO_INSTRUCCION_LIQUIDADA = "LI";
	
	/** Instrucción liquidada parcialmente */
	int LIQUIDADA_PARCIALMENTE = 15;
}
