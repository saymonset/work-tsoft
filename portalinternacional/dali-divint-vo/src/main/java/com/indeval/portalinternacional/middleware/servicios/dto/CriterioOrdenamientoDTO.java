// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.servicios.dto;

import java.io.Serializable;

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