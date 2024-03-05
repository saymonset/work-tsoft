/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * OrigenDTO.java
 * 28/02/2008
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;

/**
 * DTO que representa el criterio de origen de instrucción
 * @author Emigdio Hernández
 *
 */
public class OrigenDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4056942819574756213L;
	
	private int idOrigen ;
	
	private String descripcion;

	/**
	 * Obtiene el campo idOrigen
	 * @return  idOrigen
	 */
	public int getIdOrigen() {
		return idOrigen;
	}

	/**
	 * Asigna el campo idOrigen
	 * @param idOrigen el valor de idOrigen a asignar
	 */
	public void setIdOrigen(int idOrigen) {
		this.idOrigen = idOrigen;
	}

	/**
	 * Obtiene el campo descripcion
	 * @return  descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Asigna el campo descripcion
	 * @param descripcion el valor de descripcion a asignar
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
