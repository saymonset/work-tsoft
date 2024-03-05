/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Tabla que tiene las posiciones actualizadas de las cuentas nombradas.
 * 
 * T_POSICION_CONTROLADA
 * 
 * @author rchavez
 * @version 1.0
 */
@Entity
@Table(name = "T_POSICION_CONTROLADA")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_POSICION_CONTROLADA", allocationSize = 1, initialValue = 1)
public class PosicionControlada implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador de la posici&oacute;n.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_POSICION")
	private BigInteger idPosicion;
	/**
	 * Cuenta controlada.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUENTA", updatable = false, insertable = false)
	private CuentaControlada cuentaControlada;
	/**
	 * Identificador de la cuenta controlada.
	 */
	@Column(name = "ID_CUENTA")
	private BigInteger idCuenta;
	/**
	 * B&oacute;veda.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_BOVEDA", updatable = false, insertable = false)
	private Boveda boveda;
	/**
	 * Identificador de la b&oacute;veda.
	 */
	@Column(name = "ID_BOVEDA")
	private BigInteger idBoveda;
	/**
	 * Emisi&oacute;n.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EMISION", updatable = false, insertable = false)
	private Emision emision;
	/**
	 * Identificador de la emisi&oacute;n.
	 */
	@Column(name = "ID_EMISION")
	private BigInteger idEmision;
	/**
	 * Posici&oacute;n.
	 */
	@Column(name = "POSICION")
	private BigInteger posicion;

	/**
	 * El identificador del cupón asociado a la posición.
	 */
	@Column(name = "ID_CUPON")
	private BigInteger idCupon;

	/**
	 * El cupón asociado a la posición.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUPON", updatable = false, insertable = false)
	private Cupon cupon;

	/**
	 * Identificador de la posici&oacute;n.
	 * 
	 * @return BigInteger
	 */
	public BigInteger getIdPosicion() {
		return idPosicion;
	}

	/**
	 * Identificador de la posici&oacute;n.
	 * 
	 * @param idPosicion
	 */
	public void setIdPosicion(BigInteger idPosicion) {
		this.idPosicion = idPosicion;
	}

	/**
	 * Obtiene el valor del atributo idCupon
	 * 
	 * @return el valor del atributo idCupon
	 */
	public BigInteger getIdCupon() {
		return idCupon;
	}

	/**
	 * Establece el valor del atributo idCupon
	 * 
	 * @param idCupon
	 *            el valor del atributo idCupon a establecer.
	 */
	public void setIdCupon(BigInteger idCupon) {
		this.idCupon = idCupon;
	}

	/**
	 * Obtiene el valor del atributo cupon
	 * 
	 * @return el valor del atributo cupon
	 */
	public Cupon getCupon() {
		return cupon;
	}

	/**
	 * Establece el valor del atributo cupon
	 * 
	 * @param cupon
	 *            el valor del atributo cupon a establecer.
	 */
	public void setCupon(Cupon cupon) {
		this.cupon = cupon;
	}

	/**
	 * Identificador de la cuenta.
	 * 
	 * @return CuentaControlada
	 */
	public CuentaControlada getCuentaControlada() {
		return cuentaControlada;
	}

	/**
	 * Identificador de la cuenta.
	 * 
	 * @param cuentaControlada
	 */
	public void setCuentaControlada(CuentaControlada cuentaControlada) {
		this.cuentaControlada = cuentaControlada;
	}

	/**
	 * Identificador de la b&oacute;veda.
	 * 
	 * @return Boveda
	 */
	public Boveda getBoveda() {
		return boveda;
	}

	/**
	 * Identificador de la b&oacute;veda.
	 * 
	 * @param boveda
	 */
	public void setBoveda(Boveda boveda) {
		this.boveda = boveda;
	}

	/**
	 * Identificador de la emisi&oacute;n.
	 * 
	 * @return EmisionPersistence
	 */
	public Emision getEmision() {
		return emision;
	}

	/**
	 * Identificador de la emisi&oacute;n.
	 * 
	 * @param emision
	 */
	public void setEmision(Emision emision) {
		this.emision = emision;
	}

	/**
	 * Posici&oacute;n de valores.
	 * 
	 * @return BigInteger
	 */
	public BigInteger getPosicion() {
		return posicion;
	}

	/**
	 * Posici&oacute;n de valores.
	 * 
	 * @param posicion
	 */
	public void setPosicion(BigInteger posicion) {
		this.posicion = posicion;
	}

	/**
	 * @return the idBoveda
	 */
	public BigInteger getIdBoveda() {
		return idBoveda;
	}

	/**
	 * @param idBoveda
	 *            the idBoveda to set
	 */
	public void setIdBoveda(BigInteger idBoveda) {
		this.idBoveda = idBoveda;
	}

	/**
	 * @return the idCuenta
	 */
	public BigInteger getIdCuenta() {
		return idCuenta;
	}

	/**
	 * @param idCuenta
	 *            the idCuenta to set
	 */
	public void setIdCuenta(BigInteger idCuenta) {
		this.idCuenta = idCuenta;
	}

	/**
	 * @return the idEmision
	 */
	public BigInteger getIdEmision() {
		return idEmision;
	}

	/**
	 * @param idEmision
	 *            the idEmision to set
	 */
	public void setIdEmision(BigInteger idEmision) {
		this.idEmision = idEmision;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(53, 63).append(idPosicion).toHashCode();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PosicionControlada == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		PosicionControlada rhs = (PosicionControlada) obj;
		return new EqualsBuilder().append(idPosicion, rhs.getIdPosicion()).isEquals();
	}

}
