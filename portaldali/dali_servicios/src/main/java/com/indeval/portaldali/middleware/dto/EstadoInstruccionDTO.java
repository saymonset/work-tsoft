/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * EstadoInstruccionDTO.java
 * 28/02/2008
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;

/**
 * DTO que represena los estados de instrucción
 * @author Emigdio Hernández
 *
 */
public class EstadoInstruccionDTO implements Serializable {

	/**
	 * SV
	 */
	private static final long serialVersionUID = -5605087459250733309L;
	/**
	 * Identificador
	 */
	private int idEstadoInstruccion;
	/**
	 * Clave
	 */
	private String claveEstadoInstruccion;
	/**
	 * Descripción
	 */
	private String descripcion;
	/**
	 * Obtiene el campo idEstadoInstruccion
	 * @return  idEstadoInstruccion
	 */
	public int getIdEstadoInstruccion() {
		return idEstadoInstruccion;
	}
	/**
	 * Asigna el campo idEstadoInstruccion
	 * @param idEstadoInstruccion el valor de idEstadoInstruccion a asignar
	 */
	public void setIdEstadoInstruccion(int idEstadoInstruccion) {
		this.idEstadoInstruccion = idEstadoInstruccion;
	}
	/**
	 * Obtiene el campo claveEstadoInstruccion
	 * @return  claveEstadoInstruccion
	 */
	public String getClaveEstadoInstruccion() {
		return claveEstadoInstruccion;
	}
	/**
	 * Asigna el campo claveEstadoInstruccion
	 * @param claveEstadoInstruccion el valor de claveEstadoInstruccion a asignar
	 */
	public void setClaveEstadoInstruccion(String claveEstadoInstruccion) {
		this.claveEstadoInstruccion = claveEstadoInstruccion;
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
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		boolean result = false;
		if (o != null && o instanceof EstadoInstruccionDTO) {
			result = ((EstadoInstruccionDTO) o).getIdEstadoInstruccion() ==
					this.getIdEstadoInstruccion();
		} else {
			result = super.equals(o);
		}
		return result;
	}
	
	
}
