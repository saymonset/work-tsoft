/**
 * Portal DALI
 * 
 * 2H Software SA
 *
 * EstadoEmision.java 
 *
 * Creado el: Sep 22, 2008
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mapeo de la tabla "C_ESTADO_EMISION"
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
@Entity
@Table(name="C_ESTADO_EMISION")
public class EstadoEmision implements Serializable {

	private static final long serialVersionUID = 1L;

	/** El identificadr del estado de la emisión */
	@Id
	@Column (name = "ID_ESTADO_EMISION")
	private BigInteger idEstadoEmision;

	/** La clave del estado de la emisión */
	@Column (name = "CLAVE_ESTADO_EMISION")
	private String clave;

	/** La descripción del estado de la emisión */
	@Column (name = "DESCRIPCION")
	private String descripcion;

	/**
	 * Obtiene el valor del atributo idEstadoEmision
	 * 
	 * @return el valor del atributo idEstadoEmision
	 */
	public BigInteger getIdEstadoEmision() {
		return idEstadoEmision;
	}

	/**
	 * Establece el valor del atributo idEstadoEmision
	 * 
	 * @param idEstadoEmision
	 *            el valor del atributo idEstadoEmision a establecer.
	 */
	public void setIdEstadoEmision(BigInteger idEstadoEmision) {
		this.idEstadoEmision = idEstadoEmision;
	}

	/**
	 * Obtiene el valor del atributo clave
	 * 
	 * @return el valor del atributo clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * Establece el valor del atributo clave
	 * 
	 * @param clave
	 *            el valor del atributo clave a establecer.
	 */
	public void setClave(String clave) {
		this.clave = clave;
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
