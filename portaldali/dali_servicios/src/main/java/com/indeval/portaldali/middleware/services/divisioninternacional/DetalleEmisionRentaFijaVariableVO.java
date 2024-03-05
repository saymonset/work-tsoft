/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.divisioninternacional;

import java.math.BigDecimal;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class DetalleEmisionRentaFijaVariableVO extends AbstractBaseDTO {

	
	/** Constante de Serialziacion */
	private static final long serialVersionUID = 1L;

	private AgenteVO agente; 
	
	private  BigDecimal posicionActual;
	
	private  BigDecimal importe;

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
	 * @return BigDecimal
	 */
	public BigDecimal getImporte() {
		return importe;
	}

	/**
	 * @param importe
	 */
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getPosicionActual() {
		return posicionActual;
	}

	/**
	 * @param posicionActual
	 */
	public void setPosicionActual(BigDecimal posicionActual) {
		this.posicionActual = posicionActual;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	public void validate(Object arg0, Errors arg1) {
	}
    
}