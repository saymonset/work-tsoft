/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class DetalleParcialidadVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private BigInteger folio;

	private BigInteger cantidadOperada;

	private Date fechaRegistro;

	private String error;

	private String status;

	private BigDecimal importeOperado;

	/**
	 * @return
	 */
	public BigInteger getCantidadOperada() {
		return cantidadOperada;
	}

	/**
	 * @param cantidadOperada
	 */
	public void setCantidadOperada(BigInteger cantidadOperada) {
		this.cantidadOperada = cantidadOperada;
	}

	/**
	 * @return
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @return
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		if (fechaRegistro != null) {
			this.fechaRegistro = new Date(fechaRegistro.getTime());
		}
	}

	/**
	 * @return
	 */
	public BigInteger getFolio() {
		return folio;
	}

	/**
	 * @param folio
	 */
	public void setFolio(BigInteger folio) {
		this.folio = folio;
	}

	/**
	 * @return
	 */
	public BigDecimal getImporteOperado() {
		return importeOperado;
	}

	/**
	 * @param importeOperado
	 */
	public void setImporteOperado(BigDecimal importeOperado) {
		this.importeOperado = importeOperado;
	}

	/**
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

}
