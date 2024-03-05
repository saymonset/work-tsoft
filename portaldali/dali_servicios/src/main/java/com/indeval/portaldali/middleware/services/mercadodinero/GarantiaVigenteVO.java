/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */

package com.indeval.portaldali.middleware.services.mercadodinero;

import java.math.BigDecimal;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class GarantiaVigenteVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private String cuenta;

	private EmisionVO emision;

	private BigDecimal titulos;

	private BigDecimal valuacion;

	/**
	 * @return
	 */
	public String getCuenta() {
		return cuenta;
	}

	/**
	 * @param cuenta
	 */
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	/**
	 * @return
	 */
	public EmisionVO getEmision() {
		return emision;
	}

	/**
	 * @param emision
	 */
	public void setEmision(EmisionVO emision) {
		this.emision = emision;
	}

	/**
	 * @return
	 */
	public BigDecimal getTitulos() {
		return titulos;
	}

	/**
	 * @param titulos
	 */
	public void setTitulos(BigDecimal titulos) {
		this.titulos = titulos;
	}

	/**
	 * @return
	 */
	public BigDecimal getValuacion() {
		return valuacion;
	}

	/**
	 * @param valuacion
	 */
	public void setValuacion(BigDecimal valuacion) {
		this.valuacion = valuacion;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

}
