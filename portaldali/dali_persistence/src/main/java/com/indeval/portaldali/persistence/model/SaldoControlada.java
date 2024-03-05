/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * Contiene el detalle de las cuentas controladas de efectivo.
 *
 * T_SALDO_CONTROLADA
 *
 * @author rchavez
 * @version 1.0
 */
@Entity
@Table(name = "T_SALDO_CONTROLADA")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_SALDO_CONTROLADA", allocationSize = 1, initialValue = 1)
public class SaldoControlada implements Serializable {
    
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador del saldo.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_SALDO")
	private BigInteger idSaldo;
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
	 * Divisa.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DIVISA", updatable = false, insertable = false)
	private Divisa divisa;
	/**
	 * Identificador de la divisa.
	 */
	@Column(name = "ID_DIVISA")
	private BigInteger idDivisa;
	/**
	 * Saldo.
	 */
	@Column(name = "SALDO")
	private BigDecimal saldo;

	/**
	 * Identificador del saldo.
	 * @return BigInteger
	 */
	public BigInteger getIdSaldo() {
		return idSaldo;
	}

	/**
	 * Identificador del saldo.
	 * @param idSaldo
	 */
	public void setIdSaldo(BigInteger idSaldo) {
		this.idSaldo = idSaldo;
	}

	/**
	 * Identificador de la cuenta.
	 * @return CuentaControlada
	 */
	public CuentaControlada getCuentaControlada() {
		return cuentaControlada;
	}

	/**
	 * Identificador de la cuenta.
	 * @param cuentaControlada
	 */
	public void setCuentaControlada(CuentaControlada cuentaControlada) {
		this.cuentaControlada = cuentaControlada;
	}

	/**
	 * Identificador de la b&oacute;veda.
	 * @return Boveda
	 */
	public Boveda getBoveda() {
		return boveda;
	}

	/**
	 * Identificador de la b&oacute;veda.
	 * @param boveda
	 */
	public void setBoveda(Boveda boveda) {
		this.boveda = boveda;
	}

	/**
	 * Identificador de la divisa.
	 * @return Divisa
	 */
	public Divisa getDivisa() {
		return divisa;
	}

	/**
	 * Identificador de la divisa.
	 * @param divisa
	 */
	public void setDivisa(Divisa divisa) {
		this.divisa = divisa;
	}

	/**
	 * Saldo de la cuenta.
	 * @return BigDecimal
	 */
	public BigDecimal getSaldo() {
		return saldo;
	}

	/**
	 * Saldo de la cuenta.
	 * @param saldo
	 */
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	/**
	 * @return the idBoveda
	 */
	public BigInteger getIdBoveda() {
		return idBoveda;
	}

	/**
	 * @param idBoveda the idBoveda to set
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
	 * @param idCuenta the idCuenta to set
	 */
	public void setIdCuenta(BigInteger idCuenta) {
		this.idCuenta = idCuenta;
	}

	/**
	 * @return the idDivisa
	 */
	public BigInteger getIdDivisa() {
		return idDivisa;
	}

	/**
	 * @param idDivisa the idDivisa to set
	 */
	public void setIdDivisa(BigInteger idDivisa) {
		this.idDivisa = idDivisa;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(31, 51).append(idSaldo).toHashCode();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
	   if (obj instanceof SaldoControlada == false) {
		     return false;
	   }
	   if (this == obj) {
		     return true;
	   }
	   SaldoControlada rhs = (SaldoControlada) obj;
	   return new EqualsBuilder()
	   		.append(idSaldo, rhs.getIdSaldo())
			.isEquals();
	}

}
