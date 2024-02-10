package com.indeval.portalinternacional.middleware.servicios.modelo;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "V_SALDO_POR_RAZON_SOCIAL")
@Immutable
@SequenceGenerator(name = "foliador", sequenceName = "INDICE", allocationSize = 1, initialValue = 1)
public class SaldoPorCuenta implements Serializable {

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
	@Column(name = "CUENTA")
	private double cuenta;
	@Column(name = "DIVISA")
	private String divisa;
	@Column(name = "RAZON_SOCIAL")
	private String boveda;
	@Column(name = "SALDO_CALCULADO")
	private double saldo;
	@Column(name = "SALDO_DISPONIBLE")
	private double saldoDisponible;
	@Column(name = "SALDO_NO_DISPONIBLE")
	private double saldoNoDisponible;



	public SaldoPorCuenta() {
	}

	/**
	 * @param divisa
	 * @param boveda
	 * @param saldo
	 * @param saldoDisponible
	 * @param saldoNoDisponible
	 */
	public SaldoPorCuenta(Long id,String divisa, String boveda, double saldo, double saldoDisponible, double saldoNoDisponible) {
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
	@Column(name = "DIV_DESCRIPCION")
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
	@Column(name = "BOV_DESCRIPCION")
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
	@Column(name = "SALDO_CALCULADO")
	public double getSaldo() {
		return saldo;
	}

	/**
	 * @param saldo the saldo
	 */
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}


	/**
	 * @return the saldo
	 */
	@Column(name = "SALDO_DISPONIBLE")
	public double getSaldoDisponible() {
		return saldoDisponible;
	}

	/**
	 * @param saldoDisponible the saldoDisponible to set
	 */
	public void setSaldoDisponible(double saldoDisponible) {
		this.saldoDisponible = saldoDisponible;
	}


	/**
	 * @return the saldoNoDisponible
	 */
	@Column(name = "SALDO_NO_DISPONIBLE")
	public double getSaldoNoDisponible() {
		return saldoNoDisponible;
	}

	/**
	 * @param saldoNoDisponible the saldoNoDisponible to set
	 */
	@Column(name = "SALDO_NO_DISPONIBLE")
	public void setSaldoNoDisponible(double saldoNoDisponible) {
		this.saldoNoDisponible = saldoNoDisponible;
	}

	@Column(name = "CUENTA")
	public double getCuenta() {
		return cuenta;
	}

	@Column(name = "CUENTA")
	public void setCuenta(double cuenta) {
		this.cuenta = cuenta;
	}

	public Long getId() {
		return id;
	}
}
