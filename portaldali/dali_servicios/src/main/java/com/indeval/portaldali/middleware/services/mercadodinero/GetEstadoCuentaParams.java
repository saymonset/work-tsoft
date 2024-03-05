/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import java.util.Date;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class GetEstadoCuentaParams extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private AgenteVO agente;

	private AgenteVO contraparte;

	private EmisionVO claveValor;

	private String[] tipoOperacion;

	private Date fechaConcertacion;

	private Date fechaLiquidacion;

	private String rol;

	private String mercado;

	/**
	 * @return the mercado
	 */
	public String getMercado() {
		return mercado;
	}

	/**
	 * @param mercado
	 *            the mercado to set
	 */
	public void setMercado(String mercado) {
		this.mercado = mercado;
	}

	/**
	 * @return AgenteVO
	 */
	public AgenteVO getAgente() {
		return agente;
	}

	/**
	 * @param agente
	 */
	public void setAgente(AgenteVO agente) {
		this.agente = agente;
	}

	/**
	 * @return EmisionVO
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
	 * @return AgenteVO
	 */
	public AgenteVO getContraparte() {
		return contraparte;
	}

	/**
	 * @param contraparte
	 */
	public void setContraparte(AgenteVO contraparte) {
		this.contraparte = contraparte;
	}

	/**
	 * @return Date
	 */
	public Date getFechaConcertacion() {
		return fechaConcertacion;
	}

	/**
	 * @param fechaConcertacion
	 */
	public void setFechaConcertacion(Date fechaConcertacion) {
		this.fechaConcertacion = clona(fechaConcertacion);
	}

	/**
	 * @return Date
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	/**
	 * @param fechaLiquidacion
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = clona(fechaLiquidacion);
	}

	/**
	 * @return String
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * @param rol
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}

	/**
	 * @return String[]
	 */
	public String[] getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * @param tipoOperacion
	 */
	public void setTipoOperacion(String[] tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

}
