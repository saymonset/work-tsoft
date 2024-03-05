/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class EstadoCuentaTotalMDVO extends AbstractBaseDTO implements Serializable {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private BigDecimal totalSaldoInicial;

	private BigDecimal totalSalida;

	private BigDecimal totalEntrada;

	private BigDecimal totalSaldoFinal;

	private ElementoEstadoCuentaVO[] elementoEstadoCuentaVO;

	/**
	 * @return
	 */
	public ElementoEstadoCuentaVO[] getElementoEstadoCuentaVO() {
		return elementoEstadoCuentaVO;
	}

	/**
	 * @param elementoEstadoCuentaVO
	 */
	public void setElementoEstadoCuentaVO(ElementoEstadoCuentaVO[] elementoEstadoCuentaVO) {
		this.elementoEstadoCuentaVO = elementoEstadoCuentaVO;
	}

	/**
	 * @return
	 */
	public BigDecimal getTotalEntrada() {
		return totalEntrada;
	}

	/**
	 * @param totalEntrada
	 */
	public void setTotalEntrada(BigDecimal totalEntrada) {
		this.totalEntrada = totalEntrada;
	}

	/**
	 * @return
	 */
	public BigDecimal getTotalSalida() {
		return totalSalida;
	}

	/**
	 * @param totalSalida
	 */
	public void setTotalSalida(BigDecimal totalSalida) {
		this.totalSalida = totalSalida;
	}

	/**
	 * @return
	 */
	public BigDecimal getTotalSaldoFinal() {
		return totalSaldoFinal;
	}

	/**
	 * @param totalSaldoFinal
	 */
	public void setTotalSaldoFinal(BigDecimal totalSaldoFinal) {
		this.totalSaldoFinal = totalSaldoFinal;
	}

	/**
	 * @return
	 */
	public BigDecimal getTotalSaldoInicial() {
		return totalSaldoInicial;
	}

	/**
	 * @param totalSaldoInicial
	 */
	public void setTotalSaldoInicial(BigDecimal totalSaldoInicial) {
		this.totalSaldoInicial = totalSaldoInicial;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object arg0, Errors arg1) {
	}

}