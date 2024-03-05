/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Este cata&acute;logo contiene los estados de una instrucción.
 * Este objeto no es utilizado por el resto del modelo de objetos de hibernate, sirve
 * para tener acceso a la tabla:
 * C_ESTADO_INSTRUCCION
 *
 * @author Emigdio Herna&acute;ndez
 * @version 1.0
 */
@Entity
@Table(name = "C_ESTADO_INSTRUCCION")
public class EstadoInstruccionCat implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_ESTADO_INSTRUCCION")
	private BigInteger idEstadoInstruccion;
	
	@Column(name = "CLAVE_ESTADO_INSTRUCCION")
	private String claveEstadoInstruccion;
	
	@Column(name = "DESCRIPCION")
	private String descripcion;
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
	   if (!(obj instanceof EstadoInstruccionCat)) {
		     return false;
	   }
	   if (this == obj) {
		     return true;
	   }
	   EstadoInstruccionCat rhs = (EstadoInstruccionCat) obj;
	   return new EqualsBuilder()
	   		.append(idEstadoInstruccion, rhs.getIdEstadoInstruccion())
			.isEquals();
	}

	/**
	 * Obtiene el campo idEstadoInstruccion
	 * @return  idEstadoInstruccion
	 */
	public BigInteger getIdEstadoInstruccion() {
		return idEstadoInstruccion;
	}

	/**
	 * Asigna el campo idEstadoInstruccion
	 * @param idEstadoInstruccion el valor de idEstadoInstruccion a asignar
	 */
	public void setIdEstadoInstruccion(BigInteger idEstadoInstruccion) {
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
}