/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.tesoreria;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class SaldosATraspasarVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	/** Presenta los datos de los registros encontrados */
	Saldo3CuentasVO[] saldos;

	/** Muestra los totales generales */
	BigDecimal[] totales;

	/**
	 * Indica el numero de registros encontrados Se inicializa en 0 al
	 * instanciar el objeto
	 */
	BigInteger registros;

	/**
	 * Constructor por defecto
	 */
	public SaldosATraspasarVO() {
		this.setRegistros(BIG_INTEGER_ZERO);
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {

	}

	/**
	 * @return the saldos
	 */
	public Saldo3CuentasVO[] getSaldos() {
		return saldos;
	}

	/**
	 * @param saldos
	 *            the saldos to set
	 */
	public void setSaldos(Saldo3CuentasVO[] saldos) {
		this.saldos = saldos;
	}

	/**
	 * @return the totales
	 */
	public BigDecimal[] getTotales() {
		return totales;
	}

	/**
	 * @param totales
	 *            the totales to set
	 */
	public void setTotales(BigDecimal[] totales) {
		this.totales = totales;
	}

	/**
	 * @return the registros
	 */
	public BigInteger getRegistros() {
		return registros;
	}

	/**
	 * @param registros
	 *            the registros to set
	 */
	public void setRegistros(BigInteger registros) {
		this.registros = registros;
	}

}
