/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.modelo;

import java.math.BigInteger;

/**
 * @author Rafael Ibarra Zendejas
 */
public class EmisionSIVO {

	/** Constante de Serializacion */
	private static final long serialVersionUID = 1L;

	/** */
	private String idTipoValor;

	/** */
	private String emisora;

	/** */
	private String serie;

	/** */
	private String cupon;

	private BigInteger posicionDisponible;
	
	private BigInteger posicionTesoreria;
	
	private BigInteger posicionTotal;

	/**
	 * @return the idTipoValor
	 */
	public String getIdTipoValor() {
		return idTipoValor;
	}

	/**
	 * @param idTipoValor the idTipoValor to set
	 */
	public void setIdTipoValor(String idTipoValor) {
		this.idTipoValor = idTipoValor;
	}

	/**
	 * @return the emisora
	 */
	public String getEmisora() {
		return emisora;
	}

	/**
	 * @param emisora the emisora to set
	 */
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}

	/**
	 * @return the serie
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * @param serie the serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	/**
	 * @return the cupon
	 */
	public String getCupon() {
		return cupon;
	}

	/**
	 * @param cupon the cupon to set
	 */
	public void setCupon(String cupon) {
		this.cupon = cupon;
	}

	/**
	 * @return the posicionDisponible
	 */
	public BigInteger getPosicionDisponible() {
		return posicionDisponible;
	}

	/**
	 * @param posicionDisponible the posicionDisponible to set
	 */
	public void setPosicionDisponible(BigInteger posicionDisponible) {
		this.posicionDisponible = posicionDisponible;
	}

	/**
	 * @return the posicionTesoreria
	 */
	public BigInteger getPosicionTesoreria() {
		return posicionTesoreria;
	}

	/**
	 * @param posicionTesoreria the posicionTesoreria to set
	 */
	public void setPosicionTesoreria(BigInteger posicionTesoreria) {
		this.posicionTesoreria = posicionTesoreria;
	}

	/**
	 * @return the posicionTotal
	 */
	public BigInteger getPosicionTotal() {
		return posicionTotal;
	}

	/**
	 * @param posicionTotal the posicionTotal to set
	 */
	public void setPosicionTotal(BigInteger posicionTotal) {
		this.posicionTotal = posicionTotal;
	}


	
}
