/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class PrecioVectorValpreEVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private List registros;

	private BigDecimal totalPrecioVector;

	/**
	 * @return List
	 */
	public List getRegistros() {
		return registros;
	}

	/**
	 * @param registros
	 */
	public void setRegistros(List registros) {
		this.registros = registros;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getTotalPrecioVector() {
		return totalPrecioVector;
	}

	/**
	 * @param totalPrecioVector
	 */
	public void setTotalPrecioVector(BigDecimal totalPrecioVector) {
		this.totalPrecioVector = clonaBigDecimal(totalPrecioVector);
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
	}

}
