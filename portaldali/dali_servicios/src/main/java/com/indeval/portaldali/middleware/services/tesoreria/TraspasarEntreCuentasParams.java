/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.tesoreria;

import java.math.BigDecimal;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class TraspasarEntreCuentasParams extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private String idTraspasante;

	private String folioTraspasante;

	private String cuentaTraspasante;

	private String idReceptor;

	private String folioReceptor;

	private String cuentaReceptor;

	private BigDecimal importe;

	/**
	 * @return String
	 */
	public String getCuentaReceptor() {
		return cuentaReceptor;
	}

	/**
	 * @param cuentaReceptor
	 */
	public void setCuentaReceptor(String cuentaReceptor) {
		this.cuentaReceptor = cuentaReceptor;
	}

	/**
	 * @return String
	 */
	public String getCuentaTraspasante() {
		return cuentaTraspasante;
	}

	/**
	 * @param cuentaTraspasante
	 */
	public void setCuentaTraspasante(String cuentaTraspasante) {
		this.cuentaTraspasante = cuentaTraspasante;
	}

	/**
	 * @return String
	 */
	public String getFolioReceptor() {
		return folioReceptor;
	}

	/**
	 * @param folioReceptor
	 */
	public void setFolioReceptor(String folioReceptor) {
		this.folioReceptor = folioReceptor;
	}

	/**
	 * @return String
	 */
	public String getFolioTraspasante() {
		return folioTraspasante;
	}

	/**
	 * @param folioTraspasante
	 */
	public void setFolioTraspasante(String folioTraspasante) {
		this.folioTraspasante = folioTraspasante;
	}

	/**
	 * @return String
	 */
	public String getIdReceptor() {
		return idReceptor;
	}

	/**
	 * @param idReceptor
	 */
	public void setIdReceptor(String idReceptor) {
		this.idReceptor = idReceptor;
	}

	/**
	 * @return String
	 */
	public String getIdTraspasante() {
		return idTraspasante;
	}

	/**
	 * @param idTraspasante
	 */
	public void setIdTraspasante(String idTraspasante) {
		this.idTraspasante = idTraspasante;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getImporte() {
		return importe;
	}

	/**
	 * @param importe
	 */
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

}
