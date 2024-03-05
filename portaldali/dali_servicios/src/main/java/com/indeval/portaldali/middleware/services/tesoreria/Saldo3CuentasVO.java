/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.tesoreria;

import java.math.BigDecimal;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;

/**
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class Saldo3CuentasVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;
	private BigDecimal saldoCtaConcentradora;
	private BigDecimal saldoLiqDinero;
	private BigDecimal saldoLiqCapitales;
	private BigDecimal saldoTot;
	private BigDecimal valorColateral;
	private BigDecimal salSaldoIniEfecLiqDinero;
	private BigDecimal salSaldoIniEfecLiqCapitales;
	private BigDecimal salSaldoIniEfecConcentradora;

	/**
	 * Constructor por defecto
	 */
	public Saldo3CuentasVO() {

		this.setSaldoCtaConcentradora(BIG_DECIMAL_ZERO);
		this.setSaldoLiqCapitales(BIG_DECIMAL_ZERO);
		this.setSaldoLiqDinero(BIG_DECIMAL_ZERO);
		this.setSaldoTot(BIG_DECIMAL_ZERO);
		this.setValorColateral(BIG_DECIMAL_ZERO);
		this.setSalSaldoIniEfecLiqDinero(BIG_DECIMAL_ZERO);
		this.setSalSaldoIniEfecLiqCapitales(BIG_DECIMAL_ZERO);
		this.setSalSaldoIniEfecConcentradora(BIG_DECIMAL_ZERO);

	}

	/**
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getSaldoCtaConcentradora() {

		return saldoCtaConcentradora;

	}

	/**
	 * 
	 * @param saldoCtaConcentradora
	 */
	public void setSaldoCtaConcentradora(BigDecimal saldoCtaConcentradora) {

		this.saldoCtaConcentradora = saldoCtaConcentradora;

	}

	/**
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getSaldoLiqCapitales() {

		return saldoLiqCapitales;

	}

	/**
	 * 
	 * @param saldoLiqCapitales
	 */
	public void setSaldoLiqCapitales(BigDecimal saldoLiqCapitales) {

		this.saldoLiqCapitales = saldoLiqCapitales;

	}

	/**
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getSaldoLiqDinero() {

		return saldoLiqDinero;

	}

	/**
	 * 
	 * @param saldoLiqDinero
	 */
	public void setSaldoLiqDinero(BigDecimal saldoLiqDinero) {

		this.saldoLiqDinero = saldoLiqDinero;

	}

	/**
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getSaldoTot() {

		return saldoTot;

	}

	/**
	 * 
	 * @param saldoTot
	 */
	public void setSaldoTot(BigDecimal saldoTot) {

		this.saldoTot = saldoTot;

	}

	/**
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getValorColateral() {

		return valorColateral;

	}

	/**
	 * 
	 * @param valorColateral
	 */
	public void setValorColateral(BigDecimal valorColateral) {

		this.valorColateral = valorColateral;

	}

	/**
	 * 
	 * @return the salSaldoIniEfecConcentradora
	 */
	public BigDecimal getSalSaldoIniEfecConcentradora() {

		return salSaldoIniEfecConcentradora;

	}

	/**
	 * 
	 * @param salSaldoIniEfecConcentradora
	 *            the salSaldoIniEfecConcentradora to set
	 */
	public void setSalSaldoIniEfecConcentradora(BigDecimal salSaldoIniEfecConcentradora) {

		this.salSaldoIniEfecConcentradora = salSaldoIniEfecConcentradora;

	}

	/**
	 * 
	 * @return the salSaldoIniEfecLiqCapitales
	 */
	public BigDecimal getSalSaldoIniEfecLiqCapitales() {

		return salSaldoIniEfecLiqCapitales;

	}

	/**
	 * 
	 * @param salSaldoIniEfecLiqCapitales
	 *            the salSaldoIniEfecLiqCapitales to set
	 */
	public void setSalSaldoIniEfecLiqCapitales(BigDecimal salSaldoIniEfecLiqCapitales) {

		this.salSaldoIniEfecLiqCapitales = salSaldoIniEfecLiqCapitales;

	}

	/**
	 * 
	 * @return the salSaldoIniEfecLiqDinero
	 */
	public BigDecimal getSalSaldoIniEfecLiqDinero() {

		return salSaldoIniEfecLiqDinero;

	}

	/**
	 * 
	 * @param salSaldoIniEfecLiqDinero
	 *            the salSaldoIniEfecLiqDinero to set
	 */
	public void setSalSaldoIniEfecLiqDinero(BigDecimal salSaldoIniEfecLiqDinero) {

		this.salSaldoIniEfecLiqDinero = salSaldoIniEfecLiqDinero;

	}

	/**
	 * 
	 * @param inObject
	 * 
	 * @return boolean
	 */
	public static boolean isAn(Object inObject) {

		if ((inObject == null) || (!(inObject instanceof Saldo3CuentasVO))) {

			return false;
		}

		return true;

	}

	/**
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

}
