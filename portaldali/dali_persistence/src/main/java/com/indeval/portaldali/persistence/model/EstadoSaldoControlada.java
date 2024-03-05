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
 * Tabla que contiene los saldos de las cuentas controladas con que inicia y finaliza cada ciclo. 
 * Solo muestra los saldos que se hayan visto afectados durante el ciclo.
 * @author RCHAVEZ
 * @version 1.0
 */
@Entity
@Table(name = "T_ESTADO_SALDOS_CONTROLADA")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_ESTADO_SALDOS_CONTROLADA", allocationSize = 1, initialValue = 1)
public class EstadoSaldoControlada implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador del estado del saldo.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_ESTADO_SALDO_CONTROLADA")
	private Long idEstadoSaldoControlada;
	
	/**
	 * Saldo
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SALDO", updatable = false, insertable = false)	
	private SaldoControlada saldoControlada;	
	
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
	 * Saldo al inicio del ciclo.
	 */
	@Column(name = "SALDO_INICIAL")
	private BigDecimal saldoInicial;
	/**
	 * Saldo al final del ciclo.
	 */
	@Column(name = "SALDO_FINAL")
	private BigDecimal saldoFinal;
	/**
	 * Default constructor.
	 */
	public EstadoSaldoControlada() {
		
	}
	/**
	 * Constructor
	 * @param idSaldo
	 * @param saldoInicial
	 * @param saldoFinal
	 * @param idCiclo
	 */
	public EstadoSaldoControlada(Long idSaldo, BigDecimal saldoInicial,
			BigDecimal saldoFinal, Long idCiclo) {
		this.idSaldo = idSaldo;
		this.saldoInicial = saldoInicial;
		this.saldoFinal = saldoFinal;
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
	 * @return the idEstadoSaldoControlada
	 */
	public Long getIdEstadoSaldoControlada() {
		return idEstadoSaldoControlada;
	}
	/**
	 * @param idEstadoSaldoControlada the idEstadoSaldoControlada to set
	 */
	public void setIdEstadoSaldoControlada(Long idEstadoSaldoControlada) {
		this.idEstadoSaldoControlada = idEstadoSaldoControlada;
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
	 * @return the saldoFinal
	 */
	public BigDecimal getSaldoFinal() {
		return saldoFinal;
	}
	/**
	 * @param saldoFinal the saldoFinal to set
	 */
	public void setSaldoFinal(BigDecimal saldoFinal) {
		this.saldoFinal = saldoFinal;
	}
	/**
	 * @return the saldoInicial
	 */
	public BigDecimal getSaldoInicial() {
		return saldoInicial;
	}
	/**
	 * @param saldoInicial the saldoInicial to set
	 */
	public void setSaldoInicial(BigDecimal saldoInicial) {
		this.saldoInicial = saldoInicial;
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(13, 23).append(idEstadoSaldoControlada).toHashCode();
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
	   if (obj instanceof EstadoSaldoControlada == false) {
		     return false;
	   }
	   if (this == obj) {
		     return true;
	   }
	   EstadoSaldoControlada rhs = (EstadoSaldoControlada) obj;
	   return new EqualsBuilder()
	   		.append(idEstadoSaldoControlada, rhs.getIdEstadoSaldoControlada())
			.isEquals();
	}
	/**
	 * @return the ciclo
	 */
	public Ciclo getCiclo() {
		return ciclo;
	}
	/**
	 * @param ciclo the ciclo to set
	 */
	public void setCiclo(Ciclo ciclo) {
		this.ciclo = ciclo;
	}
	/**
	 * @return the saldoControlada
	 */
	public SaldoControlada getSaldoControlada() {
		return saldoControlada;
	}
	/**
	 * @param saldoControlada the saldoControlada to set
	 */
	public void setSaldoControlada(SaldoControlada saldoControlada) {
		this.saldoControlada = saldoControlada;
	}
}