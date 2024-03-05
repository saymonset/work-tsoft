/**
 * Portal DALI
 * 
 * 2H Software SA
 *
 * ErrorDali.java 
 *
 * Creado el: Sep 12, 2008
 */
package com.indeval.portaldali.persistence.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mapeo para la tabla C_ERROR_DALI. Tabla que contiene el catálogo de errores
 * de liquidación del DALI.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
@Entity
@Table(name = "C_ERROR_DALI")
public class ErrorDali {
	/**
	 * El identificador de la tabla C_ERROR_DALI
	 */
	@Id
	@Column(name = "ID_ERROR_DALI")
	private BigInteger idErrorDali;

	/** La clave del error de liquidación */
	@Column(name = "CLAVE_ERROR")
	private String claveError;

	/** La descripción del error de liquidación */
	@Column(name = "DESCRIPCION")
	private String descripcion;

	/**
	 * Obtiene el valor del atributo idErrorDali
	 * 
	 * @return el valor del atributo idErrorDali
	 */
	public BigInteger getIdErrorDali() {
		return idErrorDali;
	}

	/**
	 * Establece el valor del atributo idErrorDali
	 * 
	 * @param idErrorDali
	 *            el valor del atributo idErrorDali a establecer.
	 */
	public void setIdErrorDali(BigInteger idErrorDali) {
		this.idErrorDali = idErrorDali;
	}

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
}
