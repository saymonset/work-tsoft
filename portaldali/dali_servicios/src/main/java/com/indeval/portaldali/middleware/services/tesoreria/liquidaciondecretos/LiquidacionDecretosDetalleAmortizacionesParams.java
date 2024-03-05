/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class LiquidacionDecretosDetalleAmortizacionesParams extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private AgenteVO agente;

	private String folioVariable;

	private String folioFija;

	private Integer idDerecho;

	private Integer idTipoDerecho;

	private Integer idTipoEjercicio;

	/**
	 * @return Integer
	 */
	public Integer getIdDerecho() {
		return idDerecho;
	}

	/**
	 * @param idDerecho
	 */
	public void setIdDerecho(Integer idDerecho) {
		this.idDerecho = idDerecho;
	}

	/**
	 * @return Integer
	 */
	public Integer getIdTipoDerecho() {
		return idTipoDerecho;
	}

	/**
	 * @param idTipoDerecho
	 */
	public void setIdTipoDerecho(Integer idTipoDerecho) {
		this.idTipoDerecho = idTipoDerecho;
	}

	/**
	 * @return Integer
	 */
	public Integer getIdTipoEjercicio() {
		return idTipoEjercicio;
	}

	/**
	 * @param idTipoEjercicio
	 */
	public void setIdTipoEjercicio(Integer idTipoEjercicio) {
		this.idTipoEjercicio = idTipoEjercicio;
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
	 * @return String
	 */
	public String getFolioFija() {
		return folioFija;
	}

	/**
	 * @param folioFija
	 */
	public void setFolioFija(String folioFija) {
		this.folioFija = folioFija;
	}

	/**
	 * @return String
	 */
	public String getFolioVariable() {
		return folioVariable;
	}

	/**
	 * @param folioVariable
	 */
	public void setFolioVariable(String folioVariable) {
		this.folioVariable = folioVariable;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {

	}

}
