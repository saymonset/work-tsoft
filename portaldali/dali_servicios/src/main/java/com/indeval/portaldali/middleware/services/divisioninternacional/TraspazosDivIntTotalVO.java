/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.divisioninternacional;

import java.math.BigInteger;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class TraspazosDivIntTotalVO extends AbstractBaseDTO {

	
	/** Constante de Serializacion	 */
	private static final long serialVersionUID = 1L;
	
	private TraspazosDivIntVO traspazos;
	
	private BigInteger totalTV;
	
	private BigInteger tituloOperado;
	
	
	/**
	 * @return BigInteger
	 */
	public BigInteger getTituloOperado() {
		return tituloOperado;
	}

	/**
	 * @param tituloOperado
	 */
	public void setTituloOperado(BigInteger tituloOperado) {
		this.tituloOperado = tituloOperado;
	}

	/**
	 * @return BigInteger
	 */
	public BigInteger getTotalTV() {
		return totalTV;
	}

	/**
	 * @param totalTV
	 */
	public void setTotalTV(BigInteger totalTV) {
		this.totalTV = totalTV;
	}

	/**
	 * @return TraspazosDivIntVO
	 */
	public TraspazosDivIntVO getTraspazos() {
		return traspazos;
	}

	/**
	 * @param traspazos
	 */
	public void setTraspazos(TraspazosDivIntVO traspazos) {
		this.traspazos = traspazos;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	public void validate(Object arg0, Errors arg1) {
	}
    
}