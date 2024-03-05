/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class EstatusOperacionesVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private RegistroEstatusOperacionesVO[] registros;

	private BigInteger titulosTraspasante;

	private BigInteger titulosReceptor;

	private BigInteger netoTitulos;

	private BigDecimal montoTraspasante;

	private BigDecimal montoReceptor;

	private BigDecimal netoMonto;

	/**
	 * @return
	 */
	public BigDecimal getMontoReceptor() {
		return montoReceptor;
	}

	/**
	 * @return
	 */
	public BigDecimal getMontoTraspasante() {
		return montoTraspasante;
	}

	/**
	 * @return
	 */
	public BigDecimal getNetoMonto() {
		return netoMonto;
	}

	/**
	 * @return
	 */
	public BigInteger getNetoTitulos() {
		return netoTitulos;
	}

	/**
	 * @return
	 */
	public RegistroEstatusOperacionesVO[] getRegistros() {
		return registros;
	}

	/**
	 * @return
	 */
	public BigInteger getTitulosReceptor() {
		return titulosReceptor;
	}

	/**
	 * @return
	 */
	public BigInteger getTitulosTraspasante() {
		return titulosTraspasante;
	}

	/**
	 * @param montoReceptor
	 */
	public void setMontoReceptor(BigDecimal montoReceptor) {
		this.montoReceptor = montoReceptor;
	}

	/**
	 * @param montoTraspasante
	 */
	public void setMontoTraspasante(BigDecimal montoTraspasante) {
		this.montoTraspasante = montoTraspasante;
	}

	/**
	 * @param netoMonto
	 */
	public void setNetoMonto(BigDecimal netoMonto) {
		this.netoMonto = netoMonto;
	}

	/**
	 * @param netoTitulos
	 */
	public void setNetoTitulos(BigInteger netoTitulos) {
		this.netoTitulos = netoTitulos;
	}

	/**
	 * @param registros
	 */
	public void setRegistros(RegistroEstatusOperacionesVO[] registros) {
		this.registros = registros;
	}

	/**
	 * @param titulosReceptor
	 */
	public void setTitulosReceptor(BigInteger titulosReceptor) {
		this.titulosReceptor = titulosReceptor;
	}

	/**
	 * @param titulosTraspasante
	 */
	public void setTitulosTraspasante(BigInteger titulosTraspasante) {
		this.titulosTraspasante = titulosTraspasante;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

}
