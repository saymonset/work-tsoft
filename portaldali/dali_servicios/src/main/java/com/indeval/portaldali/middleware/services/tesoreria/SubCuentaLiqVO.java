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
public class SubCuentaLiqVO extends AbstractBaseDTO {

	private static final long serialVersionUID = 1L;

	private RegCuentaLiqVO[] regCuentaLiq;

	private BigDecimal subTotalImportePendiente;

	private BigDecimal subTotalImporteLiquidado;

	private BigDecimal subTotalRemanente;

	/** Constructor por defecto */
	public SubCuentaLiqVO() {
		this.subTotalImporteLiquidado = new BigDecimal(0);
		this.subTotalImportePendiente = new BigDecimal(0);
		this.subTotalRemanente = new BigDecimal(0);
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getTotalImporteLiquidado() {
		return subTotalImporteLiquidado;
	}

	/**
	 * @param totalImporteLiquidado
	 */
	public void setTotalImporteLiquidado(BigDecimal totalImporteLiquidado) {
		this.subTotalImporteLiquidado = totalImporteLiquidado;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getTotalImportePendiente() {
		return subTotalImportePendiente;
	}

	/**
	 * @param totalImportePendiente
	 */
	public void setTotalImportePendiente(BigDecimal totalImportePendiente) {
		this.subTotalImportePendiente = totalImportePendiente;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getTotalRemanente() {
		return subTotalRemanente;
	}

	/**
	 * @param totalRemanente
	 */
	public void setTotalRemanente(BigDecimal totalRemanente) {
		this.subTotalRemanente = totalRemanente;
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

	/**
	 * @return Integer
	 */
	public Integer getNumRegistros() {
		if (regCuentaLiq != null) {
			return new Integer(regCuentaLiq.length);
		}
		return new Integer(0);
	}
}
