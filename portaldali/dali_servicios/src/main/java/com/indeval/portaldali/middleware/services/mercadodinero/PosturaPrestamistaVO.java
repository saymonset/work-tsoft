/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class PosturaPrestamistaVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private BigDecimal saldoBanxico;

	private BigInteger totalDisponible;

	private BigInteger saldoPrestamo;

	private BigInteger saldoDisponible;

	/**
	 * @return Returns the saldoBanxico.
	 */
	public BigDecimal getSaldoBanxico() {
		return saldoBanxico;
	}

	/**
	 * @param saldoBanxico
	 *            The saldoBanxico to set.
	 */
	public void setSaldoBanxico(BigDecimal saldoBanxico) {
		this.saldoBanxico = saldoBanxico;
	}

	/**
	 * @return Returns the saldoDisponible.
	 */
	public BigInteger getSaldoDisponible() {
		return saldoDisponible;
	}

	/**
	 * @param saldoDisponible
	 *            The saldoDisponible to set.
	 */
	public void setSaldoDisponible(BigInteger saldoDisponible) {
		this.saldoDisponible = saldoDisponible;
	}

	/**
	 * @return Returns the saldoPrestamo.
	 */
	public BigInteger getSaldoPrestamo() {
		return saldoPrestamo;
	}

	/**
	 * @param saldoPrestamo
	 *            The saldoPrestamo to set.
	 */
	public void setSaldoPrestamo(BigInteger saldoPrestamo) {
		this.saldoPrestamo = saldoPrestamo;
	}

	/**
	 * @return Returns the totalDisponible.
	 */
	public BigInteger getTotalDisponible() {
		return totalDisponible;
	}

	/**
	 * @param totalDisponible
	 *            The totalDisponible to set.
	 */
	public void setTotalDisponible(BigInteger totalDisponible) {
		this.totalDisponible = totalDisponible;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
	}

}
