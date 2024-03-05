/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import java.util.Date;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class PosicionValoresParams extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private AgenteVO consultante;

	private EmisionVO claveValor;

	private String idTipoPapel;

	private Date fecha;

	private PaginaVO paginaVO;

	/**
	 * @return
	 */
	public PaginaVO getPaginaVO() {
		return paginaVO;
	}

	/**
	 * @return
	 */
	public EmisionVO getClaveValor() {
		return claveValor;
	}

	/**
	 * @return
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @return
	 */
	public String getIdTipoPapel() {
		return idTipoPapel;
	}

	/**
	 * @return
	 */
	public AgenteVO getConsultante() {
		return consultante;
	}

	/**
	 * @param paginaVO
	 */
	public void setPaginaVO(PaginaVO paginaVO) {
		this.paginaVO = paginaVO;
	}

	/**
	 * @param claveValor
	 */
	public void setClaveValor(EmisionVO claveValor) {
		this.claveValor = claveValor;
	}

	/**
	 * @param fecha
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @param idTipoPapel
	 */
	public void setIdTipoPapel(String idTipoPapel) {
		this.idTipoPapel = idTipoPapel;
	}

	/**
	 * @param consultante
	 */
	public void setConsultante(AgenteVO consultante) {
		this.consultante = consultante;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

}
