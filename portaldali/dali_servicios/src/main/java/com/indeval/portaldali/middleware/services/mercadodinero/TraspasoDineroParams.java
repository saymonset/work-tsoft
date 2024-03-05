/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;

/**
 * Clase de traporte para el detalle de mercado de dinero
 * 
 * @author cerjio
 */
public class TraspasoDineroParams extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private String idInst;

	private String folioInst;

	private String cuenta;

	private String llaveFolio;

	private Date fechaLiquidacion;

	/**
	 * @return the cuenta
	 */
	public String getCuenta() {
		return cuenta;
	}

	/**
	 * @param cuenta
	 *            the cuenta to set
	 */
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	/**
	 * @return the fechaLiquidacion
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	/**
	 * @param fechaLiquidacion
	 *            the fechaLiquidacion to set
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	/**
	 * @return the folioInst
	 */
	public String getFolioInst() {
		return folioInst;
	}

	/**
	 * @param folioInst
	 *            the folioInst to set
	 */
	public void setFolioInst(String folioInst) {
		this.folioInst = folioInst;
	}

	/**
	 * @return the idInst
	 */
	public String getIdInst() {
		return idInst;
	}

	/**
	 * @param idInst
	 *            the idInst to set
	 */
	public void setIdInst(String idInst) {
		this.idInst = idInst;
	}

	/**
	 * @return the llaveFolio
	 */
	public String getLlaveFolio() {
		return llaveFolio;
	}

	/**
	 * @param llaveFolio
	 *            the llaveFolio to set
	 */
	public void setLlaveFolio(String llaveFolio) {
		this.llaveFolio = llaveFolio;
	}

	/**
	 * Valida que el objeto Params tengo todos sus atributos llenos
	 * 
	 * @return boolean
	 */
	public boolean validaParams() {

		boolean paramsValido = true;
		if (StringUtils.isBlank(this.idInst)) {
			paramsValido = false;
		}
		if (StringUtils.isBlank(this.folioInst)) {
			paramsValido = false;
		}
		if (StringUtils.isBlank(this.cuenta)) {
			paramsValido = false;
		}
		if (StringUtils.isBlank(this.llaveFolio)) {
			paramsValido = false;
		}
		return paramsValido;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {

	}

}
