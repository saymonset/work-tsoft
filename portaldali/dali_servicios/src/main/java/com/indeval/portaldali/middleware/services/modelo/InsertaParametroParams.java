/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.modelo;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 * 
 */
public class InsertaParametroParams extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	/** Log de clase. */
	private static final Logger logger = LoggerFactory.getLogger(InsertaParametroParams.class);

	private String parametro;

	private String valor;

	/**
	 * @return the parametro
	 */
	public String getParametro() {
		return parametro;
	}

	/**
	 * @param parametro
	 *            the parametro to set
	 */
	public void setParametro(String parametro) {
		this.parametro = parametro;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * Valida que el objeto de parametro tengo los valores necesarios
	 * 
	 * @throws BusinessException
	 */
	public void validaParams() throws BusinessException {

		logger.info("Entrando a InsertaParametroParams.validaParams");

		if (StringUtils.isBlank(this.getParametro()) || StringUtils.isBlank(this.getValor())) {
			throw new BusinessException("Error: Parametros incorrectos");
		}
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
	}

}
