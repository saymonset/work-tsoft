/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import java.math.BigInteger;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;

/**
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ResultadoCambiosVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;
	private BigInteger folio;
	private BigInteger ok; // OK = 0 , Fallo menor que 0

	/**
	 * 
	 * @return
	 */
	public BigInteger getFolio() {

		return folio;

	}

	/**
	 * 
	 * @return
	 */
	public BigInteger getOk() {

		return ok;

	}

	/**
	 * 
	 * @param folio
	 */
	public void setFolio(BigInteger folio) {

		this.folio = folio;

	}

	/**
	 * 
	 * @param ok
	 */
	public void setOk(BigInteger ok) {

		this.ok = ok;

	}

	/**
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

}
