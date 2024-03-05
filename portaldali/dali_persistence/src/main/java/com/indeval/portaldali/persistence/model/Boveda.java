/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Este cata&acute;logo contiene todas las bóvedas manejadas en INDEVAL.
 * 
 * C_BOVEDA
 * 
 * @author rchavez
 * @version 1.0
 */
@Entity
@Table(name = "C_BOVEDA")
public class Boveda implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador de la b&oacute;veda.
	 */
	@Id
	@Column(name = "ID_BOVEDA")
	private BigInteger idBoveda;
	/**
	 * Identificador del tipo de b&oacute;veda.
	 */
    @Column(name = "ID_TIPO_BOVEDA")
	private TipoBoveda tipoBoveda;
	/**
	 * Nombre corto de la b&oacute;veda.
	 */
	@Column(name = "NOMBRE_CORTO")
	private String nombreCorto;
	/**
	 * Descripci&oacute;n de la b&oacute;veda.
	 */
	@Column(name = "DESCRIPCION")
	private String descripcion;
	/**
	 * Cuenta nombrada ligada a la b&oacute;veda.
	 */
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_CUENTA_BOVEDA")
	private CuentaNombrada cuentaNombrada;

	/**
	 * Identificador de bóveda.
	 * 
	 * @return BigInteger
	 */
	public BigInteger getIdBoveda() {
		return idBoveda;
	}

	/**
	 * @param idBoveda
	 */
	public void setIdBoveda(BigInteger idBoveda) {
		this.idBoveda = idBoveda;
	}

	/**
	 * Identificador del tipo de bóveda.
	 * 
	 * @return BigInteger
	 */
	public TipoBoveda getTipoBoveda() {
		return tipoBoveda;
	}

	/**
	 * @param tipoBoveda
	 */
	public void setTipoBoveda(TipoBoveda tipoBoveda) {
		this.tipoBoveda = tipoBoveda;
	}

	/**
	 * Nombre corto de la bóveda.
	 * 
	 * @return String
	 */
	public String getNombreCorto() {
		return nombreCorto;
	}

	/**
	 * @param nombreCorto
	 */
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	/**
	 * Descripción de la bóveda.
	 * 
	 * @return String
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Identificador del tipo de bóveda.
	 * 
	 * @return long
	 */
	public CuentaNombrada getCuentaNombrada() {
		return cuentaNombrada;
	}

	/**
	 * @param cuentaNombrada
	 */
	public void setCuentaNombrada(CuentaNombrada cuentaNombrada) {
		this.cuentaNombrada = cuentaNombrada;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(27, 47).append(idBoveda).toHashCode();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Boveda)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Boveda rhs = (Boveda) obj;
		return new EqualsBuilder().append(idBoveda, rhs.getIdBoveda())
				.isEquals();
	}
	
	@Override
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public String toString() {
		return new ToStringBuilder(this).
				append("idBoveda", idBoveda).
				append("cuentaNombrada", cuentaNombrada).
				append("nombreCorto", nombreCorto).
				append("descripcion", descripcion).
				toString();
	}
}