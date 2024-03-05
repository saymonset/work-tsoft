/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.modelo;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;

/**
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class EstatusVO extends AbstractBaseDTO {

	/** Constante de Serializacion */
	private static final long serialVersionUID = 1L;
	private Integer idEstatus;
	private String descEstatus;

	/**
	 * 
	 * @return String
	 */
	public String getDescEstatus() {

		return descEstatus;

	}

	/**
	 * 
	 * @param descEstatus
	 */
	public void setDescEstatus(String descEstatus) {

		this.descEstatus = descEstatus;

	}

	/**
	 * 
	 * @return Integer
	 */
	public Integer getIdEstatus() {

		return idEstatus;

	}

	/**
	 * 
	 * @param idEstatus
	 */
	public void setIdEstatus(Integer idEstatus) {

		this.idEstatus = idEstatus;

	}

	/**
	 * 
	 * @param inObject
	 * 
	 * @return boolean
	 */
	public static boolean isAn(Object inObject) {

		if ((inObject == null) || (!(inObject instanceof EstatusVO))) {

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
