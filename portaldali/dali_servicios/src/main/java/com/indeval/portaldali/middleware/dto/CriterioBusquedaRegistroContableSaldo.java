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
import java.util.List;

/**
 * Objeto de transferencia de datos con los criterios necesarios para
 * la consulta de registros contables de saldos
 * @author Pablo Julián Balderas Méndez
 * 
 */
public class CriterioBusquedaRegistroContableSaldo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Saldo de efectivo con los criterios de cuenta, divisa y bóveda */
	private SaldoEfectivoDTO saldoEfectivo;
	
	/** Fecha inicial de búsqueda */
	private Date fechaInicial;
	
	/** Fecha final de búsqueda */
	private Date fechaFinal;
	
	/** Lista de divisas para realizar la búsqueda */
	private List<DivisaDTO> divisas;

	/**
	 * Obtiene el atributo saldoEfectivo
	 *
	 * @return El atrubuto saldoEfectivo
	 */
	public SaldoEfectivoDTO getSaldoEfectivo() {
		return saldoEfectivo;
	}

	/**
	 * Establece la propiedad saldoEfectivo
	 *
	 * @param saldoEfectivo el campo saldoEfectivo a establecer
	 */
	public void setSaldoEfectivo(SaldoEfectivoDTO saldoEfectivo) {
		this.saldoEfectivo = saldoEfectivo;
	}

	/**
	 * Obtiene el atributo fechaInicial
	 *
	 * @return El atrubuto fechaInicial
	 */
	public Date getFechaInicial() {
		return fechaInicial;
	}

	/**
	 * Establece la propiedad fechaInicial
	 *
	 * @param fechaInicial el campo fechaInicial a establecer
	 */
	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	/**
	 * Obtiene el atributo fechaFinal
	 *
	 * @return El atrubuto fechaFinal
	 */
	public Date getFechaFinal() {
		return fechaFinal;
	}

	/**
	 * Establece la propiedad fechaFinal
	 *
	 * @param fechaFinal el campo fechaFinal a establecer
	 */
	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	/**
	 * Obtiene el atributo divisas
	 *
	 * @return El atrubuto divisas
	 */
	public List<DivisaDTO> getDivisas() {
		return divisas;
	}

	/**
	 * Establece la propiedad divisas
	 *
	 * @param divisas el campo divisas a establecer
	 */
	public void setDivisas(List<DivisaDTO> divisas) {
		this.divisas = divisas;
	}
}
