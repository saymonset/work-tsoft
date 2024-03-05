/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import java.math.BigDecimal;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;

/**
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class PosicionValoresSimpleVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;
	private String cuenta;
	private EmisionVO emisionVO;
	private BigDecimal saldoDisponible;
	private BigDecimal sumaSaldoDisponible;

	/**
	 * 
	 * @return
	 */
	public String getCuenta() {

		return cuenta;

	}

	/**
	 * 
	 * @param cuenta
	 */
	public void setCuenta(String cuenta) {

		this.cuenta = cuenta;

	}

	/**
	 * 
	 * @return
	 */
	public EmisionVO getEmisionVO() {

		return emisionVO;

	}

	/**
	 * 
	 * @param emisionVO
	 */
	public void setEmisionVO(EmisionVO emisionVO) {

		this.emisionVO = emisionVO;

	}

	/**
	 * 
	 * @return
	 */
	public BigDecimal getSaldoDisponible() {

		return saldoDisponible;

	}

	/**
	 * 
	 * @param saldoDisponible
	 */
	public void setSaldoDisponible(BigDecimal saldoDisponible) {

		this.saldoDisponible = saldoDisponible;

	}

	/**
	 * 
	 * @return
	 */
	public BigDecimal getSumaSaldoDisponible() {

		return sumaSaldoDisponible;

	}

	/**
	 * 
	 * @param sumaSaldoDisponible
	 */
	public void setSumaSaldoDisponible(BigDecimal sumaSaldoDisponible) {

		this.sumaSaldoDisponible = sumaSaldoDisponible;

	}

	/**
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
	}

}
