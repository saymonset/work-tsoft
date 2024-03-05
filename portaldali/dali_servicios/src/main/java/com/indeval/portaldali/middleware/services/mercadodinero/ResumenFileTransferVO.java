/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import java.util.Date;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ResumenFileTransferVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private Date fechaCarga;

	private Integer totalNuevos;

	private Integer totalCargados;

	private Integer totalError;

	private String descripcionError;

	private Integer totalRegistros;

	private String nombreUsuario;

	private AgenteVO agenteFirmado;

	private String tipoPapel;

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
	 * @return Returns the descripcionError.
	 */
	public String getDescripcionError() {
		return descripcionError;
	}

	/**
	 * @param descripcionError
	 *            The descripcionError to set.
	 */
	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}

	/**
	 * @return Returns the fechaCarga.
	 */
	public Date getFechaCarga() {
		return fechaCarga;
	}

	/**
	 * @param fechaCarga
	 *            The fechaCarga to set.
	 */
	public void setFechaCarga(Date fechaCarga) {
		this.fechaCarga = clona(fechaCarga);
	}

	/**
	 * @return Returns the totalCargados.
	 */
	public Integer getTotalCargados() {
		return totalCargados;
	}

	/**
	 * @param totalCargados
	 *            The totalCargados to set.
	 */
	public void setTotalCargados(Integer totalCargados) {
		this.totalCargados = totalCargados;
	}

	/**
	 * @return Returns the totalError.
	 */
	public Integer getTotalError() {
		return totalError;
	}

	/**
	 * @param totalError
	 *            The totalError to set.
	 */
	public void setTotalError(Integer totalError) {
		this.totalError = totalError;
	}

	/**
	 * @return Returns the totalNuevos.
	 */
	public Integer getTotalNuevos() {
		return totalNuevos;
	}

	/**
	 * @param totalNuevos
	 *            The totalNuevos to set.
	 */
	public void setTotalNuevos(Integer totalNuevos) {
		this.totalNuevos = totalNuevos;
	}

	/**
	 * @return Returns the totalRegistros.
	 */
	public Integer getTotalRegistros() {
		return totalRegistros;
	}

	/**
	 * @param totalRegistros
	 *            The totalRegistros to set.
	 */
	public void setTotalRegistros(Integer totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	/**
	 * @return Returns the agenteFirmado.
	 */
	public AgenteVO getAgenteFirmado() {
		return agenteFirmado;
	}

	/**
	 * @param agenteFirmado
	 *            The agenteFirmado to set.
	 */
	public void setAgenteFirmado(AgenteVO agenteFirmado) {
		this.agenteFirmado = agenteFirmado;
	}

	/**
	 * @return Returns the tipoPapel.
	 */
	public String getTipoPapel() {
		return tipoPapel;
	}

	/**
	 * @param tipoPapel
	 *            The tipoPapel to set.
	 */
	public void setTipoPapel(String tipoPapel) {
		this.tipoPapel = tipoPapel;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
	}

}
