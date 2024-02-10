package com.indeval.portalinternacional.middleware.servicios.modelo;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "V_SALDO_CUSTODIOS")
@Immutable
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_SALDO_CUSTODIOS", allocationSize = 1, initialValue = 1)
public class SaldoCustodio implements Serializable {
	/**
	 *Clase creada para multidivisas
	 *
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "INDICE")
	private Long id;//INDICE

	@Column(name = "DIV_DESCRIPCION")
	private String divisa;

	@Column(name = "BOV_DESCRIPCION")
	private String boveda;
	@Column(name = "SALDO_CALCULADO")
	private BigDecimal saldo;

	@Column(name = "SALDO_DISPONIBLE")
	private BigDecimal saldoDisponible;

	@Column(name = "SALDO_NO_DISPONIBLE")
	private BigDecimal saldoNoDisponible;



	public SaldoCustodio() {
	}

	/**
	 * @param divisa
	 * @param boveda
	 * @param saldo
	 * @param saldoDisponible
	 * @param saldoNoDisponible
	 */
	public SaldoCustodio(Long id,String divisa, String boveda, BigDecimal saldo, BigDecimal saldoDisponible, BigDecimal saldoNoDisponible) {
		this.id = id;
		this.divisa = divisa;
		this.boveda = boveda;
		this.saldo = saldo;
		this.saldoDisponible = saldoDisponible;
		this.saldoNoDisponible = saldoNoDisponible;
	}


	/**
	 * @return the divisa
	 */

	public String getDivisa() {
		return divisa;
	}

	/**
	 * @param divisa the divisa to set
	 */

	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	/**
	 * @return the boveda
	 */

	public String getBoveda() {
		return boveda;
	}

	/**
	 * @param boveda the boveda to set
	 */

	public void setBoveda(String boveda) {
		this.boveda = boveda;
	}

	/**
	 * @return the saldo
	 */

	public BigDecimal getSaldo() {
		return saldo;
	}

	/**
	 * @param saldo the saldo
	 */

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}


	/**
	 * @return the saldo
	 */

	public BigDecimal getSaldoDisponible() {
		return saldoDisponible;
	}

	/**
	 * @param saldoDisponible the saldoDisponible to set
	 */

	public void setSaldoDisponible(BigDecimal saldoDisponible) {
		this.saldoDisponible = saldoDisponible;
	}


	/**
	 * @return the saldoNoDisponible
	 */

	public BigDecimal getSaldoNoDisponible() {
		return saldoNoDisponible;
	}

	/**
	 * @param saldoNoDisponible the saldoNoDisponible to set
	 */

	public void setSaldoNoDisponible(BigDecimal saldoNoDisponible) {
		this.saldoNoDisponible = saldoNoDisponible;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

}
