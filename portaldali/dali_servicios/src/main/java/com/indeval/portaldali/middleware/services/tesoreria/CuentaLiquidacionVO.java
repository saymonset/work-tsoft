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
public class CuentaLiquidacionVO extends AbstractBaseDTO {

	private static final long serialVersionUID = 1L;

	private SubCuentaLiqVO[] subCuentaLiq;

	private BigDecimal granTotalImportePendiente;

	private BigDecimal granTotalImporteLiquidado;

	private BigDecimal granTotalRemanente;

	/**
	 * Constructor
	 */
	public CuentaLiquidacionVO() {
		this.granTotalImporteLiquidado = new BigDecimal(0);
		this.granTotalImportePendiente = new BigDecimal(0);
		this.granTotalRemanente = new BigDecimal(0);
	}

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
	 * @return SubCuentaLiqVO[]
	 */
	public SubCuentaLiqVO[] getSubCuentaLiq() {
		return subCuentaLiq;
	}

	/**
	 * @param subCuentaLiq
	 */
	public void setSubCuentaLiq(SubCuentaLiqVO[] subCuentaLiq) {
		this.subCuentaLiq = subCuentaLiq;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

}
