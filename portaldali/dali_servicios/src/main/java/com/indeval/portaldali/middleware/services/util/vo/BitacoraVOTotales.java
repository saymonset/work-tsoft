/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.util.vo;

import java.math.BigInteger;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import org.springframework.validation.Errors;

/**
 * 
 * @author Sergio Mena
 * 
 */
public class BitacoraVOTotales extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private PaginaVO paginaVO;
	private BigInteger totalesEnviados;
	private BigInteger totalesACK;
	private BigInteger totalesNAK;
	private BigInteger totalesNE;

	/**
	 * Constructor por defecto
	 */
	public BitacoraVOTotales() {
		this.totalesEnviados = BIG_INTEGER_ZERO;
		this.totalesNAK = BIG_INTEGER_ZERO;
		this.totalesACK = BIG_INTEGER_ZERO;
		this.totalesNE = BIG_INTEGER_ZERO;
	}

	/**
	 * @return the totalesNE
	 */
	public BigInteger getTotalesNE() {
		return totalesNE;
	}

	/**
	 * @param totalesNE
	 *            the totalesNE to set
	 */
	public void setTotalesNE(BigInteger totalesNE) {
		this.totalesNE = totalesNE;
	}

	/**
	 * @see org.springframework.validation.Validator #validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {

	}

	/**
	 * @return the paginaVO
	 */
	public PaginaVO getPaginaVO() {
		return paginaVO;
	}

	/**
	 * @param paginaVO
	 *            the paginaVO to set
	 */
	public void setPaginaVO(PaginaVO paginaVO) {
		this.paginaVO = paginaVO;
	}

	/**
	 * @return the totalesACK
	 */
	public BigInteger getTotalesACK() {
		return totalesACK;
	}

	/**
	 * @param totalesACK
	 *            the totalesACK to set
	 */
	public void setTotalesACK(BigInteger totalesACK) {
		this.totalesACK = totalesACK;
	}

	/**
	 * @return the totalesEnviados
	 */
	public BigInteger getTotalesEnviados() {
		return totalesEnviados;
	}

	/**
	 * @param totalesEnviados
	 *            the totalesEnviados to set
	 */
	public void setTotalesEnviados(BigInteger totalesEnviados) {
		this.totalesEnviados = totalesEnviados;
	}

	/**
	 * @return the totalesNAK
	 */
	public BigInteger getTotalesNAK() {
		return totalesNAK;
	}

	/**
	 * @param totalesNAK
	 *            the totalesNAK to set
	 */
	public void setTotalesNAK(BigInteger totalesNAK) {
		this.totalesNAK = totalesNAK;
	}

}
