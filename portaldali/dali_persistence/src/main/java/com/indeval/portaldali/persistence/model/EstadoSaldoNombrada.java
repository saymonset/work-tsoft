/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;

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
 * Tabla que contiene los saldos de las cuentas nombradas con que inicia y finaliza cada ciclo.
 * Solo muestra los saldos que se hayan visto afectados durante el ciclo.
 * @author RCHAVEZ
 * @version 1.0
 */
@Entity
@Table(name = "T_ESTADO_SALDOS_NOMBRADA")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_ESTADO_SALDOS_NOMBRADA", allocationSize = 1, initialValue = 1)
public class EstadoSaldoNombrada implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador del estado del saldo.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_ESTADO_SALDO_NOMBRADA")
	private Long idEstadoSaldoNombrada;
	
	
	/**
	 * Saldo
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SALDO", updatable = false, insertable = false)	
	private SaldoNombrada saldoNombrada;
	/**
	 * Identificador del saldo afectado.
	 */
	@Column(name = "ID_SALDO")
	private Long idSaldo;
	
	/**
	 * Ciclo
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CICLO", updatable = false, insertable = false)
	private Ciclo ciclo;
	
	/**
	 * Identificador del ciclo durante el cual se realizo la afectaci&oacute;n.
	 */
	@Column(name = "ID_CICLO")
	private Long idCiclo;

	/**
	 * Saldo disponible al inicio del ciclo.
	 */
	@Column(name = "SALDO_INICIAL_DISPONIBLE")
	private BigDecimal saldoInicialDisponible;
	/**
	 * Saldo no disponible al inicio del ciclo.
	 */
	@Column(name = "SALDO_INICIAL_NO_DISPONIBLE")
	private BigDecimal saldoInicialNoDisponible;
	/**
	 * Saldo disponible al final del ciclo.
	 */
	@Column(name = "SALDO_FINAL_DISPONIBLE")
	private BigDecimal saldoFinalDisponible;
	/**
	 * Saldo no disponble al final del ciclo.
	 */
	@Column(name = "SALDO_FINAL_NO_DISPONIBLE")
	private BigDecimal saldoFinalNoDisponible;
	/**
	 * Default constructor.
	 */
	public EstadoSaldoNombrada() {

	}
	/**
	 * Constructor.
	 * @param idSaldo
	 * @param saldoInicialDisponible
	 * @param saldoInicialNoDisponible
	 * @param saldoFinalDisponible
	 * @param saldoFinalNoDisponible
	 * @param idCiclo
	 */
	public EstadoSaldoNombrada(Long idSaldo, BigDecimal saldoInicialDisponible,
			BigDecimal saldoInicialNoDisponible, BigDecimal saldoFinalDisponible,
			BigDecimal saldoFinalNoDisponible, Long idCiclo) {
		this.idSaldo = idSaldo;
		this.saldoInicialDisponible = saldoInicialDisponible;
		this.saldoInicialNoDisponible = saldoInicialNoDisponible;
		this.saldoFinalDisponible = saldoFinalDisponible;
		this.saldoFinalNoDisponible = saldoFinalNoDisponible;
		this.idCiclo = idCiclo;
	}
	/**
	 * @return the idCiclo
	 */
	public Long getIdCiclo() {
		return idCiclo;
	}
	/**
	 * @param idCiclo the idCiclo to set
	 */
	public void setIdCiclo(Long idCiclo) {
		this.idCiclo = idCiclo;
	}
	/**
	 * @return the idEstadoSaldoNombrada
	 */
	public Long getIdEstadoSaldoNombrada() {
		return idEstadoSaldoNombrada;
	}
	/**
	 * @param idEstadoSaldoNombrada the idEstadoSaldoNombrada to set
	 */
	public void setIdEstadoSaldoNombrada(Long idEstadoSaldoNombrada) {
		this.idEstadoSaldoNombrada = idEstadoSaldoNombrada;
	}
	/**
	 * @return the idSaldo
	 */
	public Long getIdSaldo() {
		return idSaldo;
	}
	/**
	 * @param idSaldo the idSaldo to set
	 */
	public void setIdSaldo(Long idSaldo) {
		this.idSaldo = idSaldo;
	}
	/**
	 * @return the saldoFinalDisponible
	 */
	public BigDecimal getSaldoFinalDisponible() {
		return saldoFinalDisponible;
	}
	/**
	 * @param saldoFinalDisponible the saldoFinalDisponible to set
	 */
	public void setSaldoFinalDisponible(BigDecimal saldoFinalDisponible) {
		this.saldoFinalDisponible = saldoFinalDisponible;
	}
	/**
	 * @return the saldoFinalNoDisponible
	 */
	public BigDecimal getSaldoFinalNoDisponible() {
		return saldoFinalNoDisponible;
	}
	/**
	 * @param saldoFinalNoDisponible the saldoFinalNoDisponible to set
	 */
	public void setSaldoFinalNoDisponible(BigDecimal saldoFinalNoDisponible) {
		this.saldoFinalNoDisponible = saldoFinalNoDisponible;
	}
	/**
	 * @return the saldoInicialDisponible
	 */
	public BigDecimal getSaldoInicialDisponible() {
		return saldoInicialDisponible;
	}
	/**
	 * @param saldoInicialDisponible the saldoInicialDisponible to set
	 */
	public void setSaldoInicialDisponible(BigDecimal saldoInicialDisponible) {
		this.saldoInicialDisponible = saldoInicialDisponible;
	}
	/**
	 * @return the saldoInicialNoDisponible
	 */
	public BigDecimal getSaldoInicialNoDisponible() {
		return saldoInicialNoDisponible;
	}
	/**
	 * @param saldoInicialNoDisponible the saldoInicialNoDisponible to set
	 */
	public void setSaldoInicialNoDisponible(BigDecimal saldoInicialNoDisponible) {
		this.saldoInicialNoDisponible = saldoInicialNoDisponible;
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(13, 23).append(idEstadoSaldoNombrada).toHashCode();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
	   if (!(obj instanceof EstadoSaldoNombrada)) {
		     return false;
	   }
	   if (this == obj) {
		     return true;
	   }
	   EstadoSaldoNombrada rhs = (EstadoSaldoNombrada) obj;
	   return new EqualsBuilder()
	   		.append(idEstadoSaldoNombrada, rhs.getIdEstadoSaldoNombrada())
			.isEquals();
	}
	/**
	 * Obtiene el atributo ciclo
	 *
	 * @return El atrubuto ciclo
	 */
	public Ciclo getCiclo() {
		return ciclo;
	}
	/**
	 * Establece la propiedad ciclo
	 *
	 * @param ciclo el campo ciclo a establecer
	 */
	public void setCiclo(Ciclo ciclo) {
		this.ciclo = ciclo;
	}
	/**
	 * Obtiene el atributo saldoNombrada
	 *
	 * @return El atrubuto saldoNombrada
	 */
	public SaldoNombrada getSaldoNombrada() {
		return saldoNombrada;
	}
	/**
	 * Establece la propiedad saldoNombrada
	 *
	 * @param saldoNombrada el campo saldoNombrada a establecer
	 */
	public void setSaldoNombrada(SaldoNombrada saldoNombrada) {
		this.saldoNombrada = saldoNombrada;
	}
}
