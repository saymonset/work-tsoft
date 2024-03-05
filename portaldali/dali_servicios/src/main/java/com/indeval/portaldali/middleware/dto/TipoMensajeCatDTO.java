/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigInteger;



import org.apache.commons.lang.builder.EqualsBuilder;

/**

 *
 * @author FERNANDO VAZQUEZ ULLOA
 * @version 1.0
 */


public class TipoMensajeCatDTO implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	private BigInteger idTipoMensaje;
	
	
	private String claveTipoMensaje;
	
	
	private String descripcion;
	
	/**
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
	   if (!(obj instanceof TipoMensajeCatDTO)) {
		     return false;
	   }
	   if (this == obj) {
		     return true;
	   }
	   TipoMensajeCatDTO rhs = (TipoMensajeCatDTO) obj;
	   return new EqualsBuilder()
	   		.append(idTipoMensaje, rhs.getIdTipoMensaje())
			.isEquals();
	}

	/**
	 * Obtiene el campo idTipoMensaje
	 * @return  idTipoMensaje
	 */
	public BigInteger getIdTipoMensaje() {
		return idTipoMensaje;
	}

	/**
	 * Asigna el campo idTipoMensaje
	 * @param idTipoMensaje el valor de idTipoMensaje a asignar
	 */
	public void setIdTipoMensaje(BigInteger idTipoMensaje) {
		this.idTipoMensaje = idTipoMensaje;
	}

	/**
	 * Obtiene el campo claveTipoMensaje
	 * @return  claveTipoMensaje
	 */
	public String getClaveTipoMensaje() {
		return claveTipoMensaje;
	}

	/**
	 * Asigna el campo claveTipoMensaje
	 * @param claveTipoMensaje el valor de claveTipoMensaje a asignar
	 */
	public void setClaveTipoMensaje(String claveTipoMensaje) {
		this.claveTipoMensaje = claveTipoMensaje;
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