/**
 * 2H Software
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Creado el Dec 28, 2007
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Data Transfer Object que representa el estado de un saldo de efectivo en un
 * ciclo
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public class EstadoSaldoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Identificador del estado del saldo.
	 */
	private Long idEstadoSaldo;

	/**
	 * Saldo disponible al inicio del ciclo.
	 */
	private BigDecimal saldoInicialDisponible;

	/**
	 * Saldo no disponible al inicio del ciclo.
	 */
	private BigDecimal saldoInicialNoDisponible;

	/**
	 * Saldo dispobible al final del ciclo.
	 */
	private BigDecimal saldoFinalDisponible;

	/**
	 * Saldo no disponible al final del ciclo.
	 */
	private BigDecimal saldoFinalNoDisponible;

	/** Saldo al final del ciclo */
	private BigDecimal saldoFinal;

	/** Saldo al inicio del ciclo */
	private BigDecimal saldoInicial;

	/**
	 * Obtiene el valor del atributo idEstadoSaldo
	 * 
	 * @return el valor del atributo idEstadoSaldo
	 */
	public Long getIdEstadoSaldo() {
		return idEstadoSaldo;
	}

	/**
	 * Establece el valor del atributo idEstadoSaldo
	 *
	 * @param idEstadoSaldo el valor del atributo idEstadoSaldo a establecer
	 */
	public void setIdEstadoSaldo(Long idEstadoSaldo) {
		this.idEstadoSaldo = idEstadoSaldo;
	}

	/**
	 * Obtiene el valor del atributo saldoInicialDisponible
	 * 
	 * @return el valor del atributo saldoInicialDisponible
	 */
	public BigDecimal getSaldoInicialDisponible() {
		return saldoInicialDisponible;
	}

	/**
	 * Establece el valor del atributo saldoInicialDisponible
	 *
	 * @param saldoInicialDisponible el valor del atributo saldoInicialDisponible a establecer
	 */
	public void setSaldoInicialDisponible(BigDecimal saldoInicialDisponible) {
		this.saldoInicialDisponible = saldoInicialDisponible;
	}

	/**
	 * Obtiene el valor del atributo saldoInicialNoDisponible
	 * 
	 * @return el valor del atributo saldoInicialNoDisponible
	 */
	public BigDecimal getSaldoInicialNoDisponible() {
		return saldoInicialNoDisponible;
	}

	/**
	 * Establece el valor del atributo saldoInicialNoDisponible
	 *
	 * @param saldoInicialNoDisponible el valor del atributo saldoInicialNoDisponible a establecer
	 */
	public void setSaldoInicialNoDisponible(BigDecimal saldoInicialNoDisponible) {
		this.saldoInicialNoDisponible = saldoInicialNoDisponible;
	}

	/**
	 * Obtiene el valor del atributo saldoFinalDisponible
	 * 
	 * @return el valor del atributo saldoFinalDisponible
	 */
	public BigDecimal getSaldoFinalDisponible() {
		return saldoFinalDisponible;
	}

	/**
	 * Establece el valor del atributo saldoFinalDisponible
	 *
	 * @param saldoFinalDisponible el valor del atributo saldoFinalDisponible a establecer
	 */
	public void setSaldoFinalDisponible(BigDecimal saldoFinalDisponible) {
		this.saldoFinalDisponible = saldoFinalDisponible;
	}

	/**
	 * Obtiene el valor del atributo saldoFinalNoDisponible
	 * 
	 * @return el valor del atributo saldoFinalNoDisponible
	 */
	public BigDecimal getSaldoFinalNoDisponible() {
		return saldoFinalNoDisponible;
	}

	/**
	 * Establece el valor del atributo saldoFinalNoDisponible
	 *
	 * @param saldoFinalNoDisponible el valor del atributo saldoFinalNoDisponible a establecer
	 */
	public void setSaldoFinalNoDisponible(BigDecimal saldoFinalNoDisponible) {
		this.saldoFinalNoDisponible = saldoFinalNoDisponible;
	}

	/**
	 * Obtiene el valor del atributo saldoFinal
	 * 
	 * @return el valor del atributo saldoFinal
	 */
	public BigDecimal getSaldoFinal() {
		return saldoFinal;
	}

	/**
	 * Establece el valor del atributo saldoFinal
	 *
	 * @param saldoFinal el valor del atributo saldoFinal a establecer
	 */
	public void setSaldoFinal(BigDecimal saldoFinal) {
		this.saldoFinal = saldoFinal;
	}

	/**
	 * Obtiene el valor del atributo saldoInicial
	 * 
	 * @return el valor del atributo saldoInicial
	 */
	public BigDecimal getSaldoInicial() {
		return saldoInicial;
	}

	/**
	 * Establece el valor del atributo saldoInicial
	 *
	 * @param saldoInicial el valor del atributo saldoInicial a establecer
	 */
	public void setSaldoInicial(BigDecimal saldoInicial) {
		this.saldoInicial = saldoInicial;
	}
	
}
