/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ConsultaEstatusGarantiasParams extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	EmisionVO claveValor;

	String idTipoPapel;

	String idStatusGarantia;

	AgenteVO agenteVO;

	/**
	 * @return AgenteVO
	 */
	public AgenteVO getAgenteVO() {
		return agenteVO;
	}

	/**
	 * @return EmisionVO
	 */
	public EmisionVO getClaveValor() {
		return claveValor;
	}

	/**
	 * @return String
	 */
	public String getIdStatusGarantia() {
		return idStatusGarantia;
	}

	/**
	 * @return String
	 */
	public String getIdTipoPapel() {
		return idTipoPapel;
	}

	/**
	 * @param agenteVO
	 */
	public void setAgenteVO(AgenteVO agenteVO) {
		this.agenteVO = agenteVO;
	}

	/**
	 * @param claveValor
	 */
	public void setClaveValor(EmisionVO claveValor) {
		this.claveValor = claveValor;
	}

	/**
	 * @param idStatusGarantia
	 */
	public void setIdStatusGarantia(String idStatusGarantia) {
		this.idStatusGarantia = idStatusGarantia;
	}

	/**
	 * @param idTipoPapel
	 */
	public void setIdTipoPapel(String idTipoPapel) {
		this.idTipoPapel = idTipoPapel;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

}
