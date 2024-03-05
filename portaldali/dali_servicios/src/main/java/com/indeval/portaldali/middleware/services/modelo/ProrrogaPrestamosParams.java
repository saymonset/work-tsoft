/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.modelo;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ProrrogaPrestamosParams extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	/** Log de clase. */
	private static final Logger logger = LoggerFactory.getLogger(ProrrogaPrestamosParams.class);

	private BigDecimal folioAnterior;

	private String origen;

	/**
	 * @return the folioAnterior
	 */
	public BigDecimal getFolioAnterior() {
		return folioAnterior;
	}

	/**
	 * @param folioAnterior
	 *            the folioAnterior to set
	 */
	public void setFolioAnterior(BigDecimal folioAnterior) {
		this.folioAnterior = folioAnterior;
	}

	/**
	 * @return the origen
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * @param origen
	 *            the origen to set
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * 
	 * @return
	 */
	public boolean validaParams() {

		logger.info("Entrando a ProrrogaPrestamosParams.validaParams");

		if (StringUtils.isBlank(this.getOrigen()) || folioAnterior == null) {
			return false;
		}
		return true;

	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
	}

}
