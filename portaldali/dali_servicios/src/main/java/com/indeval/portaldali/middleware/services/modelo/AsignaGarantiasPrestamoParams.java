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
 * 
 */
public class AsignaGarantiasPrestamoParams extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	/** Define DEPOSITO (D) */
	public static final String DEPOSITO = "D";

	/** Define RETIRO (D) */
	public static final String RETIRO = "R";

	/** Log de clase. */
	private static final Logger logger = LoggerFactory.getLogger(AsignaGarantiasPrestamoParams.class);

	private String cuenta;

	private String tv;

	private String emisora;

	private String serie;

	private String cupon;

	private BigDecimal cantidad;

	private BigDecimal folio_prestamo;

	private String origen;

	private String operacion;

	/**
	 * @return the cantidad
	 */
	public BigDecimal getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad
	 *            the cantidad to set
	 */
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

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
	 * @return the cupon
	 */
	public String getCupon() {
		return cupon;
	}

	/**
	 * @param cupon
	 *            the cupon to set
	 */
	public void setCupon(String cupon) {
		this.cupon = cupon;
	}

	/**
	 * @return the emisora
	 */
	public String getEmisora() {
		return emisora;
	}

	/**
	 * @param emisora
	 *            the emisora to set
	 */
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}

	/**
	 * @return the folio_prestamo
	 */
	public BigDecimal getFolio_prestamo() {
		return folio_prestamo;
	}

	/**
	 * @param folio_prestamo
	 *            the folio_prestamo to set
	 */
	public void setFolio_prestamo(BigDecimal folio_prestamo) {
		this.folio_prestamo = folio_prestamo;
	}

	/**
	 * @return the operacion
	 */
	public String getOperacion() {
		return operacion;
	}

	/**
	 * @param operacion
	 *            the operacion to set
	 */
	public void setOperacion(String operacion) {
		this.operacion = operacion;
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
	 * @return the serie
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * @param serie
	 *            the serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	/**
	 * @return the tv
	 */
	public String getTv() {
		return tv;
	}

	/**
	 * @param tv
	 *            the tv to set
	 */
	public void setTv(String tv) {
		this.tv = tv;
	}

	/**
	 * @throws BusinessException
	 */
	public void validaParams() throws BusinessException {

		logger.info("Entrando a ConcertacionPrestamosValpreEParams.validaParams");

		if (StringUtils.isBlank(this.getCuenta()) || StringUtils.isBlank(this.getTv()) || StringUtils.isBlank(this.getEmisora())
				|| StringUtils.isBlank(this.getSerie()) || StringUtils.isBlank(this.getCupon()) || StringUtils.isBlank(this.getOrigen())
				|| StringUtils.isBlank(this.getOperacion()) || this.getFolio_prestamo() == null
				|| (this.getCantidad() == null || BIG_DECIMAL_ZERO.compareTo(this.getCantidad()) >= 0)) {
			throw new BusinessException(ERROR_DE_PARAMETROS);
		}

		if (!this.isDeposito() && !this.isRetiro()) {
			throw new BusinessException("Error: La operacion debe ser 'D' o 'R'");
		}

	}

	/**
	 * Determina si la operacion es de deposito
	 * 
	 * @return boolean
	 */
	public boolean isDeposito() {
		boolean isDeposito = true;
		if (!DEPOSITO.equalsIgnoreCase(this.getOperacion())) {
			isDeposito = false;
		}
		return isDeposito;
	}

	/**
	 * Determina si la operacion es de retiro
	 * 
	 * @return boolean
	 */
	public boolean isRetiro() {
		boolean isRetiro = true;
		if (!RETIRO.equalsIgnoreCase(this.getOperacion())) {
			isRetiro = false;
		}
		return isRetiro;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
	}

}
