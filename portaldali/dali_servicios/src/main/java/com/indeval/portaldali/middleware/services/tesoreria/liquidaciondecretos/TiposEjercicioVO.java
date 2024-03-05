/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class TiposEjercicioVO extends AbstractBaseDTO {

	/**
	 * Constante de serializacion
	 */
	private static final long serialVersionUID = 1L;

	private String descTipoEjercicio;

	private String cveTipoEjercicio;

	/**
	 * Constructor por defecto
	 */
	public TiposEjercicioVO() {

	}

	/**
	 * Constructor sobrecargado
	 * 
	 * @param descTipoEjercicio
	 * @param cveTipoEjercicio
	 */
	public TiposEjercicioVO(String descTipoEjercicio, String cveTipoEjercicio) {
		setDescTipoEjercicio(descTipoEjercicio);
		setCveTipoEjercicio(cveTipoEjercicio);
	}

	/**
	 * @return String
	 */
	public String getCveTipoEjercicio() {
		return cveTipoEjercicio;
	}

	/**
	 * @param cveTipoEjercicio
	 */
	public void setCveTipoEjercicio(String cveTipoEjercicio) {
		this.cveTipoEjercicio = cveTipoEjercicio;
	}

	/**
	 * @return String
	 */
	public String getDescTipoEjercicio() {
		return descTipoEjercicio;
	}

	/**
	 * @param descTipoEjercicio
	 */
	public void setDescTipoEjercicio(String descTipoEjercicio) {
		this.descTipoEjercicio = descTipoEjercicio;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {

	}

}
