/**
 * Portal DALI
 * 
 * 2H Software SA
 *
 * ErrorDaliDTO.java 
 *
 * Creado el: Sep 12, 2008
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;

/**
 * Data Transfer Object que representa un error de liquidación del portal DALI.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public class ErrorDaliDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * El identificador del error de liquidación *7 private Integer idErrorDali;
	 */
	private Integer idErrorDali;
	/**
	 * La clave del error de liquidación
	 */
	private String claveError;

	/** La descripción del error de liquidación */
	private String descripcion;

	/**
	 * Obtiene el valor del atributo claveError
	 * 
	 * @return el valor del atributo claveError
	 */
	public String getClaveError() {
		return claveError;
	}

	/**
	 * Establece el valor del atributo claveError
	 * 
	 * @param claveError
	 *            el valor del atributo claveError a establecer.
	 */
	public void setClaveError(String claveError) {
		this.claveError = claveError;
	}

	/**
	 * Obtiene el valor del atributo descripcion
	 * 
	 * @return el valor del atributo descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece el valor del atributo descripcion
	 * 
	 * @param descripcion
	 *            el valor del atributo descripcion a establecer.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el valor del atributo idErrorDali
	 * 
	 * @return el valor del atributo idErrorDali
	 */
	public Integer getIdErrorDali() {
		return idErrorDali;
	}

	/**
	 * Establece el valor del atributo idErrorDali
	 * 
	 * @param idErrorDali
	 *            el valor del atributo idErrorDali a establecer.
	 */
	public void setIdErrorDali(Integer idErrorDali) {
		this.idErrorDali = idErrorDali;
	}

}
