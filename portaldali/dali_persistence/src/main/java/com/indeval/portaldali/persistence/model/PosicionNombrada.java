/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Tabla que contiene las posiciones actualizadas de las cuentas nombradas.
 * 
 * T_POSICION_NOMBRADA
 * 
 * @author rchavez
 * @version 1.0
 */
@Entity
@Table(name = "T_POSICION_NOMBRADA")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_POSICION_NOMBRADA", allocationSize = 1, initialValue = 1)
public class PosicionNombrada implements Serializable {
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
	 * Cuenta nombrada de la posici&oacute;n.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUENTA", updatable = false, insertable = false)
	private CuentaNombrada cuentaNombrada;

	/**
	 * Identificador de la cuenta nombrada de la posici&oacute;n.
	 */
	@Column(name = "ID_CUENTA")
	private BigInteger idCuentaNombrada;

	/**
	 * B&oacute;veda de la posici&oacute;n.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_BOVEDA", updatable = false, insertable = false)
	private Boveda boveda;

	/**
	 * Identificador de la b&oacute;veda de la posici&oacute;n.
	 */
	@Column(name = "ID_BOVEDA")
	private BigInteger idBoveda;

	/**
	 * El id del cupón ligado a la posición nombrada.
	 */
	@Column(name = "ID_CUPON")
	private BigInteger idCupon;

	/**
	 * El cupón ligado a la posición nombrada.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUPON", updatable = false, insertable = false)
	private Cupon cupon;

	/**
	 * Emisi&oacute;n de la posici&oacute;n.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EMISION", updatable = false, insertable = false)
	private Emision emision;

	/**
	 * Identificador de la emisi&oacuten;n de la posici&oacute;n.
	 */
	@Column(name = "ID_EMISION")
	private BigInteger idEmision;

	/**
	 * Posici&oacute;n disponible.
	 */
	@Column(name = "POSICION_DISPONIBLE")
	private BigInteger posicionDisponible;

	/**
	 * Posici&oacute;n no disponible.
	 */
	@Column(name = "POSICION_NO_DISPONIBLE")
	private BigInteger posicionNoDisponible;
	
	@OneToMany(fetch = FetchType.LAZY, targetEntity=PosicionNombradaHistorico.class)
	@JoinColumn(name = "ID_POSICION_NOMBRADA")
	private Set<PosicionNombradaHistorico> posicionNombradaHistorico; 

	/**
	 * Identificador de la posici&oacute;n.
	 * 
	 * @return long
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
	 * Identificador de la cuenta.
	 * 
	 * @return long
	 */
	public CuentaNombrada getCuentaNombrada() {
		return cuentaNombrada;
	}

	/**
	 * Identificador de la cuenta.
	 * 
	 * @param cuentaNombrada
	 */
	public void setCuentaNombrada(CuentaNombrada cuentaNombrada) {
		this.cuentaNombrada = cuentaNombrada;
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
	 * Posici&oacute;n disponible.
	 * 
	 * @return BigInteger
	 */
	public BigInteger getPosicionDisponible() {
		return posicionDisponible;
	}

	/**
	 * Posici&oacute;n disponible.
	 * 
	 * @param posicionDisponible
	 */
	public void setPosicionDisponible(BigInteger posicionDisponible) {
		this.posicionDisponible = posicionDisponible;
	}

	/**
	 * Posici&oacute;n no disponible.
	 * 
	 * @return BigInteger
	 */
	public BigInteger getPosicionNoDisponible() {
		return posicionNoDisponible;
	}

	/**
	 * Posici&oacute;n no disponible.
	 * 
	 * @param posicionNoDisponible
	 */
	public void setPosicionNoDisponible(BigInteger posicionNoDisponible) {
		this.posicionNoDisponible = posicionNoDisponible;
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
	 * @return the idCuentaNombrada
	 */
	public BigInteger getIdCuentaNombrada() {
		return idCuentaNombrada;
	}

	/**
	 * @param idCuentaNombrada
	 *            the idCuentaNombrada to set
	 */
	public void setIdCuentaNombrada(BigInteger idCuentaNombrada) {
		this.idCuentaNombrada = idCuentaNombrada;
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
	 * @param posicionNombradaHistorico the posicionNombradaHistorico to set
	 */
	public void setPosicionNombradaHistorico(
			Set<PosicionNombradaHistorico> posicionNombradaHistorico) {
		this.posicionNombradaHistorico = posicionNombradaHistorico;
	}

	/**
	 * @return the posicionNombradaHistorico
	 */
	public Set<PosicionNombradaHistorico> getPosicionNombradaHistorico() {
		return posicionNombradaHistorico;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(63, 73).append(idPosicion).toHashCode();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
	
		if (!(obj instanceof PosicionNombrada)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		PosicionNombrada rhs = (PosicionNombrada) obj;
		return new EqualsBuilder().append(idPosicion, rhs.getIdPosicion()).isEquals();
	}

}
