/**
 * 2H Software - Bursatec
 * 
 * Sistema de Consulta de Estados de Cuenta
 *
 * Dec 20, 2007
 *
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Data Transfer Object que representa el saldo efectivo conformado por una bóveda, divisa y cuenta.
 * 
 * @author José Antonio Huizar Moreno 
 * @version 1.0
 *
 */
public class SaldoEfectivoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** Divisa en que esta dado el saldo */
	private DivisaDTO divisa;
	
	/** Boveda en donde se resguarda el saldo */
	private BovedaDTO boveda;
	
	/** Cuenta del saldo */
	private CuentaEfectivoDTO cuenta = new CuentaEfectivoDTO();

	/** Id del estado del saldo */
	private long idEstadoSaldo;
	
	/** El identificador del saldo */
	private Long idSaldo;
	
	/** Saldo de la cuenta */
	private double saldo;
	
	/** Saldo disponible. Aplica para cuentas nombradas */
	private Double saldoDisponible;
	
	/** Saldo no disponible. Aplica para cuentas nombradas */
	private double saldoNoDisponible;

	/** Criterio de fecha de inicio */
	private Date fecha;
	
	/** Criterio de la fecha inicial para un rango de fechas */
	private Date fechaInicial;
	
	/** Criterio de la fecha final para un rango de fechas */
	private Date fechaFinal;
	
	/**
	 * Descripción adicional del saldo
	 */
	private String descripcion = null;

	/**
	 * Obtiene el valor del atributo divisa
	 * 
	 * @return el valor del atributo divisa
	 */
	public DivisaDTO getDivisa() {
		return divisa;
	}

	/**
	 * Establece el valor del atributo divisa
	 *
	 * @param divisa el valor del atributo divisa a establecer
	 */
	public void setDivisa(DivisaDTO divisa) {
		this.divisa = divisa;
	}

	/**
	 * Obtiene el valor del atributo boveda
	 * 
	 * @return el valor del atributo boveda
	 */
	public BovedaDTO getBoveda() {
		return boveda;
	}

	/**
	 * Establece el valor del atributo boveda
	 *
	 * @param boveda el valor del atributo boveda a establecer
	 */
	public void setBoveda(BovedaDTO boveda) {
		this.boveda = boveda;
	}

	/**
	 * Obtiene el valor del atributo cuenta
	 * 
	 * @return el valor del atributo cuenta
	 */
	public CuentaEfectivoDTO getCuenta() {
		return cuenta;
	}

	/**
	 * Establece el valor del atributo cuenta
	 *
	 * @param cuenta el valor del atributo cuenta a establecer
	 */
	public void setCuenta(CuentaEfectivoDTO cuenta) {
		this.cuenta = cuenta;
	}

	/**
	 * Obtiene el valor del atributo idEstadoSaldo
	 * 
	 * @return el valor del atributo idEstadoSaldo
	 */
	public long getIdEstadoSaldo() {
		return idEstadoSaldo;
	}

	/**
	 * Establece el valor del atributo idEstadoSaldo
	 *
	 * @param idEstadoSaldo el valor del atributo idEstadoSaldo a establecer
	 */
	public void setIdEstadoSaldo(long idEstadoSaldo) {
		this.idEstadoSaldo = idEstadoSaldo;
	}

	/**
	 * Obtiene el valor del atributo saldo
	 * 
	 * @return el valor del atributo saldo
	 */
	public double getSaldo() {
		return saldo;
	}

	/**
	 * Establece el valor del atributo saldo
	 *
	 * @param saldo el valor del atributo saldo a establecer
	 */
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	/**
	 * Obtiene el valor del atributo saldoDisponible
	 * 
	 * @return el valor del atributo saldoDisponible
	 */
	public Double getSaldoDisponible() {
		return saldoDisponible;
	}

	/**
	 * Establece el valor del atributo saldoDisponible
	 *
	 * @param saldoDisponible el valor del atributo saldoDisponible a establecer
	 */
	public void setSaldoDisponible(Double saldoDisponible) {
		this.saldoDisponible = saldoDisponible;
	}

	/**
	 * Obtiene el valor del atributo saldoNoDisponible
	 * 
	 * @return el valor del atributo saldoNoDisponible
	 */
	public double getSaldoNoDisponible() {
		return saldoNoDisponible;
	}

	/**
	 * Establece el valor del atributo saldoNoDisponible
	 *
	 * @param saldoNoDisponible el valor del atributo saldoNoDisponible a establecer
	 */
	public void setSaldoNoDisponible(double saldoNoDisponible) {
		this.saldoNoDisponible = saldoNoDisponible;
	}

	/**
	 * Obtiene el valor del atributo fecha
	 * 
	 * @return el valor del atributo fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Establece el valor del atributo fecha
	 *
	 * @param fecha el valor del atributo fecha a establecer
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Obtiene el valor del atributo fechaInicial
	 * 
	 * @return el valor del atributo fechaInicial
	 */
	public Date getFechaInicial() {
		return fechaInicial;
	}

	/**
	 * Establece el valor del atributo fechaInicial
	 *
	 * @param fechaInicial el valor del atributo fechaInicial a establecer
	 */
	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	/**
	 * Obtiene el valor del atributo fechaFinal
	 * 
	 * @return el valor del atributo fechaFinal
	 */
	public Date getFechaFinal() {
		return fechaFinal;
	}

	/**
	 * Establece el valor del atributo fechaFinal
	 *
	 * @param fechaFinal el valor del atributo fechaFinal a establecer
	 */
	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	/**
	 * Obtiene el valor del atributo descripcion
	 * 
	 * @return el valor del atributo descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece el valor del atributo descripcion
	 *
	 * @param descripcion el valor del atributo descripcion a establecer
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el valor del atributo idSaldo
	 * 
	 * @return el valor del atributo idSaldo
	 */
	public Long getIdSaldo() {
		return idSaldo;
	}

	/**
	 * Establece el valor del atributo idSaldo
	 *
	 * @param idSaldo el valor del atributo idSaldo a establecer
	 */
	public void setIdSaldo(Long idSaldo) {
		this.idSaldo = idSaldo;
	}
	
}
