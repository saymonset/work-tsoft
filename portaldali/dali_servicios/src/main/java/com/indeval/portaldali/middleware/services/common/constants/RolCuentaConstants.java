/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.middleware.services.common.constants;

/**
 * Contiene los identificadores utilizados para designar 
 * el rol que jugar una cuenta en una operación
 * @author Emigdio Hernández Rodríguez
 * @version 1.0
 */
public interface RolCuentaConstants {
	/**
	 * Indica que se deben considerar ambos roles
	 */
	int AMBOS = -1;
	/**
	 * Indica que es un receptor
	 */
	int RECEPTOR = 1;
	/**
	 * Indica que el rol es de traspasante
	 */
	int TRASPASANTE = 2;
	
	String []DESCRIPCION_ROL = new String[]{"AMBOS","RECEPTOR","TRASPASANTE"};
}
