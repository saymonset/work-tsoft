/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import java.math.BigInteger;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class RetiroVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private BigInteger saldoDisponible;

	private BigInteger cantidadSugerida;

	/**
	 * @return
	 */
	public BigInteger getCantidadSugerida() {
		return cantidadSugerida;
	}

	/**
	 * @param cantidadSugerida
	 */
	public void setCantidadSugerida(BigInteger cantidadSugerida) {
		this.cantidadSugerida = cantidadSugerida;
	}

	/**
	 * @return
	 */
	public BigInteger getSaldoDisponible() {
		return saldoDisponible;
	}

	/**
	 * @param saldoDisponible
	 */
	public void setSaldoDisponible(BigInteger saldoDisponible) {
		this.saldoDisponible = saldoDisponible;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

}
