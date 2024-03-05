/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.tesoreria;

import java.math.BigDecimal;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class EstadoCuentaSNEVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private PaginaVO pagina;

	private BigDecimal totCobros;

	private BigDecimal totPagos;

	private BigDecimal totSaldos;

	/**
	 * @return PaginaVO
	 */
	public PaginaVO getPagina() {
		return pagina;
	}

	/**
	 * @param pagina
	 */
	public void setPagina(PaginaVO pagina) {
		this.pagina = pagina;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getTotCobros() {
		return totCobros;
	}

	/**
	 * @param totCobros
	 */
	public void setTotCobros(BigDecimal totCobros) {
		this.totCobros = totCobros;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getTotPagos() {
		return totPagos;
	}

	/**
	 * @param totPagos
	 */
	public void setTotPagos(BigDecimal totPagos) {
		this.totPagos = totPagos;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getTotSaldos() {
		return totSaldos;
	}

	/**
	 * @param totSaldos
	 */
	public void setTotSaldos(BigDecimal totSaldos) {
		this.totSaldos = totSaldos;
	}

	/**
	 * @param inObject
	 * @return boolean
	 */
	public static boolean isAn(Object inObject) {

		if ((inObject == null) || (!(inObject instanceof EstadoCuentaSNEVO)))
			return false;

		return true;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

}
