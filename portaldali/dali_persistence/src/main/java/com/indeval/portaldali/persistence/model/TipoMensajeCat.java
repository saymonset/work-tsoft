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
 * Este cata&acute;logo contiene los tipos de mensaje.
 * Este objeto no es utilizado por el resto del modelo de objetos de hibernate, sirve
 * para tener acceso a la tabla:
 * C_TIPO_MENSAJE
 *
 * @author Emigdio Herna&acute;ndez
 * @version 1.0
 */
@Entity
@Table(name = "C_TIPO_MENSAJE")
public class TipoMensajeCat implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_TIPO_MENSAJE")
	private BigInteger idTipoMensaje;
	
	@Column(name = "CLAVE_MENSAJE")
	private String claveTipoMensaje;
	
	@Column(name = "DESCRIPCION")
	private String descripcion;
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
	   if (!(obj instanceof TipoMensajeCat)) {
		     return false;
	   }
	   if (this == obj) {
		     return true;
	   }
	   TipoMensajeCat rhs = (TipoMensajeCat) obj;
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