/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;

/**
 * Clase para transportar los valores de ordenamiento
 * Transporta la columna a ordenar y la dirección
 * @author Emigdio
 * @version 1.0
 */
public class CriterioOrdenamientoDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Nombre de la columna de ordenamiento
	 */
	private String columna = null;
	/**
	 * Indica si se va a ordenar de forma ascendente
	 */
	private boolean ascendente = true;

	/**
	 * Obtiene el campo columna
	 * @return  columna
	 */
	public String getColumna() {
		return columna;
	}

	/**
	 * Asigna el valor del campo columna
	 * @param columna el valor de columna a asignar
	 */
	public void setColumna(String columna) {
		this.columna = columna;
	}

	/**
	 * Obtiene el campo ascendente
	 * @return  ascendente
	 */
	public boolean isAscendente() {
		return ascendente;
	}

	/**
	 * Asigna el valor del campo ascendente
	 * @param ascendente el valor de ascendente a asignar
	 */
	public void setAscendente(boolean ascendente) {
		this.ascendente = ascendente;
	}
}
