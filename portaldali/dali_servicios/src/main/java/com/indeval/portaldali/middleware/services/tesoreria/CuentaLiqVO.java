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
public class CuentaLiqVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private RegCuentaLiqVO[] regCuentaLiq;

	private BigDecimal granTotalImportePendiente;

	private BigDecimal granTotalImporteLiquidado;

	private BigDecimal granTotalRemanente;

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getGranTotalImporteLiquidado() {
		return granTotalImporteLiquidado;
	}

	/**
	 * @param granTotalImporteLiquidado
	 */
	public void setGranTotalImporteLiquidado(BigDecimal granTotalImporteLiquidado) {
		this.granTotalImporteLiquidado = granTotalImporteLiquidado;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getGranTotalImportePendiente() {
		return granTotalImportePendiente;
	}

	/**
	 * @param granTotalImportePendiente
	 */
	public void setGranTotalImportePendiente(BigDecimal granTotalImportePendiente) {
		this.granTotalImportePendiente = granTotalImportePendiente;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getGranTotalRemanente() {
		return granTotalRemanente;
	}

	/**
	 * @param granTotalRemanente
	 */
	public void setGranTotalRemanente(BigDecimal granTotalRemanente) {
		this.granTotalRemanente = granTotalRemanente;
	}

	/**
	 * @return RegCuentaLiqVO[]
	 */
	public RegCuentaLiqVO[] getRegCuentaLiq() {
		return regCuentaLiq;
	}

	/**
	 * @param regCuentaLiq
	 */
	public void setRegCuentaLiq(RegCuentaLiqVO[] regCuentaLiq) {
		this.regCuentaLiq = regCuentaLiq;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

}
