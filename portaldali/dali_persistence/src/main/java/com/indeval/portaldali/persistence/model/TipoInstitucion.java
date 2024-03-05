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

/**
 * Contiene el cata&acute;logo con los tipos de instituciones.
 *
 * C_TIPO_INSTITUCION
 *
 * @author rchavez
 * @version 1.0
 */
@Entity
@Table(name = "C_TIPO_INSTITUCION")
public class TipoInstitucion implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador del tipo de instituci&oacute;n.
	 */
	@Id
	@Column(name = "ID_TIPO_INSTITUCION")
	private BigInteger idTipoInstitucion;
	/**
	 * Clave del tipo de instituci&oacute;n.
	 */
	@Column(name = "CLAVE_TIPO_INSTITUCION")
	private String claveTipoInstitucion;
	/**
	 * Descripci&oacute;n del tipo de instituci&oacute;n.
	 */
	@Column(name = "DESCRIPCION")
	private String descripcion;
	
	/**
	 * Identificador secuencial tipo instituci&oacute;n.
	 * @return long
	 */
	public BigInteger getIdTipoInstitucion() {
		return idTipoInstitucion;
	}

	/**
	 * Identificador secuencial tipo instituci&oacute;n.
	 * @param idTipoInstitucion
	 */
	public void setIdTipoInstitucion(BigInteger idTipoInstitucion) {
		this.idTipoInstitucion = idTipoInstitucion;
	}

	/**
	 * Clave del tipo de instituci&oacute;n.
	 * @return String
	 */
	public String getClaveTipoInstitucion() {
		return claveTipoInstitucion;
	}

	/**
	 * Clave del tipo de instituci&oacute;n.
	 * @param claveTipoInstitucion
	 */
	public void setClaveTipoInstitucion(String claveTipoInstitucion) {
		this.claveTipoInstitucion = claveTipoInstitucion;
	}

	/**
	 * Descripci&oacute;n del tipo de instituci&oacute;n.
	 * @return String
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Descripci&oacute;n del tipo de instituci&oacute;n.
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
