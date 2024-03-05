/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class EstatusGarantiasVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private EmisionVO claveValor;

	private BigDecimal cantidadTitulos;

	private Date fecha;

	private String descEstatus;

	private String tipoPapel;

	/**
	 * @return
	 */
	public BigDecimal getCantidadTitulos() {
		return cantidadTitulos;
	}

	/**
	 * @param cantidadTitulos
	 */
	public void setCantidadTitulos(BigDecimal cantidadTitulos) {
		this.cantidadTitulos = cantidadTitulos;
	}

	/**
	 * @return
	 */
	public EmisionVO getClaveValor() {
		return claveValor;
	}

	/**
	 * @param claveValor
	 */
	public void setClaveValor(EmisionVO claveValor) {
		this.claveValor = claveValor;
	}

	/**
	 * @return
	 */
	public String getDescEstatus() {
		return descEstatus;
	}

	/**
	 * @param descEstatus
	 */
	public void setDescEstatus(String descEstatus) {
		this.descEstatus = descEstatus;
	}

	/**
	 * @return
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha
	 */
	public void setFecha(Date fecha) {
		this.fecha = clona(fecha);
	}

	/**
	 * @return
	 */
	public String getTipoPapel() {
		return tipoPapel;
	}

	/**
	 * @param tipoPapel
	 */
	public void setTipoPapel(String tipoPapel) {
		this.tipoPapel = tipoPapel;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

}
