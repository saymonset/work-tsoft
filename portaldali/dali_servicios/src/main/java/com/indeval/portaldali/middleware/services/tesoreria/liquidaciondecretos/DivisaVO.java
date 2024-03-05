/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;

/**
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class DivisaVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;
	private String cveDivisa; // Codigo alfabetico de la divisa
	private String descDivisa;

	/**
	 * Constructor por defecto
	 */
	public DivisaVO() {
	}

	/**
	 * Constructor sobrecargado
	 * 
	 * @param cveDivisa
	 * @param descDivisa
	 */
	public DivisaVO(String cveDivisa, String descDivisa) {

		setCveDivisa(cveDivisa);
		setDescDivisa(descDivisa);

	}

	/**
	 * 
	 * @return String
	 */
	public String getCveDivisa() {

		return cveDivisa;

	}

	/**
	 * 
	 * @param cveDivisa
	 */
	public void setCveDivisa(String cveDivisa) {

		this.cveDivisa = cveDivisa;

	}

	/**
	 * 
	 * @return String
	 */
	public String getDescDivisa() {

		return descDivisa;

	}

	/**
	 * 
	 * @param descDivisa
	 */
	public void setDescDivisa(String descDivisa) {

		this.descDivisa = descDivisa;

	}

	/**
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
	}

}
