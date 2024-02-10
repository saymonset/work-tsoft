/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author <a href="mailto:ivan.kazakov@2hsoftware.com.mx">Ivan Kazakov</a>
 *
 */
public class ConsultaCierreFideicomisoParams implements Serializable {
	
	/**
	 * Constante de versio&oacute;n de serializaci&oacute;n 
	 */
	public static final long serialVersionUID = (long)1;
	
	/**
	 * BigDecimal 
	 */
	private BigDecimal saldoInicialBanamex;
	
	/**
	 * BigDecimal
	 */
	private BigDecimal saldoInicialInbur;
	
	/**
	 * BigDecimal
	 */
	private BigDecimal saldoInicialNafin;
	
	/**
	 * BigDecimal
	 */
	private BigDecimal saldoInicialVitro;
	
	/**
	 * Date 
	 */
	private Date fechaConsulta;

	/* Accessors and mutators */

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getSaldoInicialBanamex() {
		return saldoInicialBanamex;
	}

	/**
	 * @param saldoInicialBanamex
	 */
	public void setSaldoInicialBanamex(BigDecimal saldoInicialBanamex) {
		this.saldoInicialBanamex = saldoInicialBanamex;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getSaldoInicialInbur() {
		return saldoInicialInbur;
	}

	/**
	 * @param saldoInicialInbur
	 */
	public void setSaldoInicialInbur(BigDecimal saldoInicialInbur) {
		this.saldoInicialInbur = saldoInicialInbur;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getSaldoInicialNafin() {
		return saldoInicialNafin;
	}

	/**
	 * @param saldoInicialNafin
	 */
	public void setSaldoInicialNafin(BigDecimal saldoInicialNafin) {
		this.saldoInicialNafin = saldoInicialNafin;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getSaldoInicialVitro() {
		return saldoInicialVitro;
	}

	/**
	 * @param saldoInicialVitro
	 */
	public void setSaldoInicialVitro(BigDecimal saldoInicialVitro) {
		this.saldoInicialVitro = saldoInicialVitro;
	}

	/**
	 * @return Date
	 */
	public Date getFechaConsulta() {
		return fechaConsulta;
	}

	/**
	 * @param fechaConsulta
	 */
	public void setFechaConsulta(Date fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}
	
	/**
	 * Realiza la validaci&oacute;n del objeto
	 * @return String
	 */
	public String validate() {
		StringBuilder stringBuilder = new StringBuilder();
		if (saldoInicialBanamex == null) {
			stringBuilder.append("saldoInicialBanamex, ");
		}
		if (saldoInicialInbur == null) {
			stringBuilder.append("saldoInicialInbur, ");
		}
		if (saldoInicialNafin == null) {
			stringBuilder.append("saldoInicialNafin, ");
		}
		if (saldoInicialVitro == null) {
			stringBuilder.append("saldoInicialVitro, ");
		}
		if (fechaConsulta == null) {
			stringBuilder.append("fechaConsulta, ");
		}
		if (stringBuilder.length() > 0) {
			return (stringBuilder.substring(0, stringBuilder.length() - 2));
		}
		else {
			return (null);
		}
	}
}
