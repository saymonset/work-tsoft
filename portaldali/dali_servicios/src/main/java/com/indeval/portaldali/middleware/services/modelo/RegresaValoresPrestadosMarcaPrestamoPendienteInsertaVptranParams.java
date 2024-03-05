/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.modelo;

import java.math.BigInteger;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 * 
 */
public class RegresaValoresPrestadosMarcaPrestamoPendienteInsertaVptranParams extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	/** Log de clase. */
	private static final Logger logger = LoggerFactory.getLogger(RegresaValoresPrestadosMarcaPrestamoPendienteInsertaVptranParams.class);

	private BigInteger folioPrestamo;

	private String origen;

	private String mercado;

	/**
	 * @return the folioPrestamo
	 */
	public BigInteger getFolioPrestamo() {
		return folioPrestamo;
	}

	/**
	 * @param folioPrestamo
	 *            the folioPrestamo to set
	 */
	public void setFolioPrestamo(BigInteger folioPrestamo) {
		this.folioPrestamo = folioPrestamo;
	}

	/**
	 * @return the mercado
	 */
	public String getMercado() {
		return mercado;
	}

	/**
	 * @param mercado
	 *            the mercado to set
	 */
	public void setMercado(String mercado) {
		this.mercado = mercado;
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
	 * @throws BusinessException
	 */
	public void validaParams() throws BusinessException {

		logger.info("Entrando a RegresaValoresPrestadosMarcaPrestamoPendienteInsertaVptranParams.validaParams");

		if (StringUtils.isBlank(this.getOrigen()) || StringUtils.isBlank(this.getMercado()) || this.getFolioPrestamo() == null) {
			throw new BusinessException(ERROR_DE_PARAMETROS);
		}

	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
	}

}
