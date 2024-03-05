/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class MdIntranDTO extends Mdintran implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	
	private String indicador;
	
	private ValorPK valorPK;
	
	private BigDecimal saldoDisponible;
	
	private BigDecimal saldoInicialDia;
	
	public String getIndicador() {
		return indicador;
	}

	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}
	public ValorPK getValorPK() {
		return valorPK;
	}

	public void setValorPK(ValorPK valorPK) {
		this.valorPK = valorPK;
	}

	public BigDecimal getSaldoDisponible() {
		return saldoDisponible;
	}

	public void setSaldoDisponible(BigDecimal saldoDisponible) {
		this.saldoDisponible = saldoDisponible;
	}

	public BigDecimal getSaldoInicialDia() {
		return saldoInicialDia;
	}

	public void setSaldoInicialDia(BigDecimal saldoInicialDia) {
		this.saldoInicialDia = saldoInicialDia;
	}

	
}
