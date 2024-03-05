/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * RolConstants.java
 * 04/03/2008
 */
package com.indeval.portaldali.middleware.services.common.constants;

/**
 * Constante para los valores de cadena de los roles
 * @author Emigdio Hernández
 *
 */
public interface RolConstants {
	/**
	 * Valor para el rol : Ambos
	 */
	int ROL_AMBOS = 0;
	/**
	 * Valor para el rol: Traspasante
	 */
	int ROL_TRASPASANTE = 1;
	/**
	 * Valor para el rol: Receptor
	 */
	int ROL_RECEPTOR = 2;
	
	String []DESCRIPCION_ROLES = new String[]{"AMBOS","TRASPASANTE","RECEPTOR"};
}
