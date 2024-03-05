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
 * Cat&aacute;logo de mercados a los que puede pertenecer una
 * instituci&oacute;n.
 * 
 * @author RCHAVEZ
 */
@Entity
@Table(name = "C_MERCADO")
public class Mercado implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador del mercado.
	 */
	@Id
	@Column(name = "ID_MERCADO")
	private BigInteger idMercado;
	/**
	 * Clave del mercado.
	 */
	@Column(name = "CLAVE_MERCADO")
	private String clave;
	/**
	 * Descripci&oacute;n del mercado.
	 */
	@Column(name = "DESC_MERCADO")
	private String descripcion;

	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @param clave
	 *            the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the idMercado
	 */
	public BigInteger getIdMercado() {
		return idMercado;
	}

	/**
	 * @param idMercado
	 *            the idMercado to set
	 */
	public void setIdMercado(BigInteger idMercado) {
		this.idMercado = idMercado;
	}

}
