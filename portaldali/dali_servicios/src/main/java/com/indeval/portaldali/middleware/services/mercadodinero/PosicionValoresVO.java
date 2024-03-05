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
public class PosicionValoresVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private EmisionVO emision;

	private String cuentaPropia;

	private BigDecimal alCierre;

	private BigDecimal actual;

	private BigDecimal enTransito;

	private BigDecimal en24;

	private BigDecimal en48;

	private BigDecimal en72;

	private BigDecimal en96;

	private BigDecimal neto;

	/**
	 * @return
	 */
	public BigDecimal getActual() {
		return actual;
	}

	/**
	 * @param actual
	 */
	public void setActual(BigDecimal actual) {
		this.actual = actual;
	}

	/**
	 * @return
	 */
	public BigDecimal getAlCierre() {
		return alCierre;
	}

	/**
	 * @param alCierre
	 */
	public void setAlCierre(BigDecimal alCierre) {
		this.alCierre = alCierre;
	}

	/**
	 * @return
	 */
	public String getCuentaPropia() {
		return cuentaPropia;
	}

	/**
	 * @param cuentaPropia
	 */
	public void setCuentaPropia(String cuentaPropia) {
		this.cuentaPropia = cuentaPropia;
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
	public BigDecimal getEn24() {
		return en24;
	}

	/**
	 * @param en24
	 */
	public void setEn24(BigDecimal en24) {
		this.en24 = en24;
	}

	/**
	 * @return
	 */
	public BigDecimal getEn48() {
		return en48;
	}

	/**
	 * @param en48
	 */
	public void setEn48(BigDecimal en48) {
		this.en48 = en48;
	}

	/**
	 * @return
	 */
	public BigDecimal getEn72() {
		return en72;
	}

	/**
	 * @param en72
	 */
	public void setEn72(BigDecimal en72) {
		this.en72 = en72;
	}

	/**
	 * @return
	 */
	public BigDecimal getEn96() {
		return en96;
	}

	/**
	 * @param en96
	 */
	public void setEn96(BigDecimal en96) {
		this.en96 = en96;
	}

	/**
	 * @return
	 */
	public BigDecimal getEnTransito() {
		return enTransito;
	}

	/**
	 * @param enTransito
	 */
	public void setEnTransito(BigDecimal enTransito) {
		this.enTransito = enTransito;
	}

	/**
	 * @return
	 */
	public BigDecimal getNeto() {
		return neto;
	}

	/**
	 * @param neto
	 */
	public void setNeto(BigDecimal neto) {
		this.neto = neto;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

}
