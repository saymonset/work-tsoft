/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigInteger;


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


public class EstadoInstruccionCatDTO implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	private BigInteger idEstadoInstruccion;
	
	
	private String claveEstadoInstruccion;
	
	
	private String descripcion;
	
	
	public EstadoInstruccionCatDTO() {
		super();
	}

	
	public EstadoInstruccionCatDTO(long idEstadoInstruccion) {
		super();
		this.idEstadoInstruccion = new BigInteger(Long.valueOf(idEstadoInstruccion).toString());
	}


	/**
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
	   if (!(obj instanceof EstadoInstruccionCatDTO)) {
		     return false;
	   }
	   if (this == obj) {
		     return true;
	   }
	   EstadoInstruccionCatDTO rhs = (EstadoInstruccionCatDTO) obj;
	   return new EqualsBuilder()
	   		.append(idEstadoInstruccion, rhs.getIdEstadoInstruccion())
			.isEquals();
	}

	/**
	 * Obtiene el campo idEstadoInstruccion
	 * 
	 */
	public BigInteger getIdEstadoInstruccion() {
		return idEstadoInstruccion;
	}

	/**
	 * Asigna el campo idEstadoInstruccion
	 * 
	 */
	public void setIdEstadoInstruccion(BigInteger idEstadoInstruccion) {
		this.idEstadoInstruccion = idEstadoInstruccion;
	}

	/**
	 * Obtiene el campo claveEstadoInstruccion
	 * 
	 */
	public String getClaveEstadoInstruccion() {
		return claveEstadoInstruccion;
	}

	/**
	 * Asigna el campo claveEstadoInstruccion
	 * 
	 */
	public void setClaveEstadoInstruccion(String claveEstadoInstruccion) {
		this.claveEstadoInstruccion = claveEstadoInstruccion;
	}

	/**
	 * Obtiene el campo descripcion
	 * 
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Asigna el campo descripcion
	 * 
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}