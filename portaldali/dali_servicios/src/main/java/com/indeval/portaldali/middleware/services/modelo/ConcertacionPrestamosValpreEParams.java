/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.modelo;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 * 
 */
public class ConcertacionPrestamosValpreEParams extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	/** Log de clase. */
	private static final Logger logger = LoggerFactory.getLogger(ConcertacionPrestamosValpreEParams.class);

	private String idPrestamista;

	private String folioPrestami;

	private String idPrestatario;

	private String folioPrestata;

	private String tv;

	private String emisora;

	private String serie;

	private String cupon;

	private String cantidadChar;

	private Date fechaVencimie;

	private String prorroga;

	/**
	 * @return the cantidadChar
	 */
	public String getCantidadChar() {
		return cantidadChar;
	}

	/**
	 * @param cantidadChar
	 *            the cantidadChar to set
	 */
	public void setCantidadChar(String cantidadChar) {
		this.cantidadChar = cantidadChar;
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
	 * @return the fechaVencimie
	 */
	public Date getFechaVencimie() {
		return fechaVencimie;
	}

	/**
	 * @param fechaVencimie
	 *            the fechaVencimie to set
	 */
	public void setFechaVencimie(Date fechaVencimie) {
		this.fechaVencimie = fechaVencimie;
	}

	/**
	 * @return the folioPrestami
	 */
	public String getFolioPrestami() {
		return folioPrestami;
	}

	/**
	 * @param folioPrestami
	 *            the folioPrestami to set
	 */
	public void setFolioPrestami(String folioPrestami) {
		this.folioPrestami = folioPrestami;
	}

	/**
	 * @return the folioPrestata
	 */
	public String getFolioPrestata() {
		return folioPrestata;
	}

	/**
	 * @param folioPrestata
	 *            the folioPrestata to set
	 */
	public void setFolioPrestata(String folioPrestata) {
		this.folioPrestata = folioPrestata;
	}

	/**
	 * @return the idPrestamista
	 */
	public String getIdPrestamista() {
		return idPrestamista;
	}

	/**
	 * @param idPrestamista
	 *            the idPrestamista to set
	 */
	public void setIdPrestamista(String idPrestamista) {
		this.idPrestamista = idPrestamista;
	}

	/**
	 * @return the idPrestatario
	 */
	public String getIdPrestatario() {
		return idPrestatario;
	}

	/**
	 * @param idPrestatario
	 *            the idPrestatario to set
	 */
	public void setIdPrestatario(String idPrestatario) {
		this.idPrestatario = idPrestatario;
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

		if (StringUtils.isBlank(this.getIdPrestamista()) || StringUtils.isBlank(this.getFolioPrestami()) || StringUtils.isBlank(this.getIdPrestatario())
				|| StringUtils.isBlank(this.getFolioPrestata()) || StringUtils.isBlank(this.getTv()) || StringUtils.isBlank(this.getEmisora())
				|| StringUtils.isBlank(this.getSerie()) || StringUtils.isBlank(this.getCupon()) || StringUtils.isBlank(this.getCantidadChar())
				|| this.getFechaVencimie() == null) {
			throw new BusinessException(ERROR_DE_PARAMETROS);
		}

	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
	}

	/**
	 * @return String
	 */
	public String getProrroga() {
		return prorroga;
	}

	/**
	 * @param prorroga
	 */
	public void setProrroga(String prorroga) {
		this.prorroga = prorroga;
	}

}
