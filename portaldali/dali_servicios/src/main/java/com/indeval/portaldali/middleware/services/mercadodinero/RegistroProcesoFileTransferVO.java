/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class RegistroProcesoFileTransferVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private Date fechaRegistro;

	private String cadena;

	private String estado;

	private String error;

	private String nombreUsuario;

	private BigDecimal consec;

	private List datos;

	/**
	 * @return Returns the cadena.
	 */
	public String getCadena() {
		return cadena;
	}

	/**
	 * @param cadena
	 *            The cadena to set.
	 */
	public void setCadena(String cadena) {
		this.cadena = cadena;
	}

	/**
	 * @return Returns the consec.
	 */
	public BigDecimal getConsec() {
		return consec;
	}

	/**
	 * @param consec
	 *            The consec to set.
	 */
	public void setConsec(BigDecimal consec) {
		this.consec = clonaBigDecimal(consec);
	}

	/**
	 * @return Returns the error.
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error
	 *            The error to set.
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @return Returns the estado.
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            The estado to set.
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return Returns the fechaRegistro.
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro
	 *            The fechaRegistro to set.
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = clona(fechaRegistro);
	}

	/**
	 * @return Returns the nombreUsuario.
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * @param nombreUsuario
	 *            The nombreUsuario to set.
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * @return
	 */
	public List getDatos() {
		return datos;
	}

	/**
	 * @param datos
	 */
	public void setDatos(List datos) {
		this.datos = datos;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
	}

}
