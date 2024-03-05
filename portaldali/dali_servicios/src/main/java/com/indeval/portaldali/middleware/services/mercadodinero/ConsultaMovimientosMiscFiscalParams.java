/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ConsultaMovimientosMiscFiscalParams extends AbstractBaseDTO {

	/** Constante de Serializacion */
	private static final long serialVersionUID = 1L;

	private String idAgenteFirmado;

	private String folioAgenteFirmado;

	private String idTipoAgente;

	private String idEstatusOperacion;

	private Date fechaConsulta;

	private Boolean mercado;

	PaginaVO paginaVO;

	/**
	 * @return Date
	 */
	public Date getFechaConsulta() {
		return fechaConsulta;
	}

	/**
	 * @param fechaConsulta
	 */
	public void setFechaConsulta(Date fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}

	/**
	 * @return String
	 */
	public String getFolioAgenteFirmado() {
		return folioAgenteFirmado;
	}

	/**
	 * @param folioAgenteFirmado
	 */
	public void setFolioAgenteFirmado(String folioAgenteFirmado) {
		this.folioAgenteFirmado = folioAgenteFirmado;
	}

	/**
	 * @return String
	 */
	public String getIdAgenteFirmado() {
		return idAgenteFirmado;
	}

	/**
	 * @param idAgenteFirmado
	 */
	public void setIdAgenteFirmado(String idAgenteFirmado) {
		this.idAgenteFirmado = idAgenteFirmado;
	}

	/**
	 * @return String
	 */
	public String getIdEstatusOperacion() {
		return idEstatusOperacion;
	}

	/**
	 * @param idEstatusOperacion
	 */
	public void setIdEstatusOperacion(String idEstatusOperacion) {
		this.idEstatusOperacion = idEstatusOperacion;
	}

	/**
	 * @return String
	 */
	public String getIdTipoAgente() {
		return idTipoAgente;
	}

	/**
	 * @param idTipoAgente
	 */
	public void setIdTipoAgente(String idTipoAgente) {
		this.idTipoAgente = idTipoAgente;
	}

	/**
	 * @return Boolean
	 */
	public Boolean getMercado() {
		return mercado;
	}

	/**
	 * @param mercado
	 */
	public void setMercado(Boolean mercado) {
		this.mercado = mercado;
	}

	/**
	 * @return PaginaVO
	 */
	public PaginaVO getPaginaVO() {
		return paginaVO;
	}

	/**
	 * @param paginaVO
	 */
	public void setPaginaVO(PaginaVO paginaVO) {
		this.paginaVO = paginaVO;
	}

	/**
	 * Valida que el objeto tenga todos los parametros requeridos
	 * 
	 * @throws BusinessException
	 */
	public void esValido() throws BusinessException {
		if (this.getMercado() == null) {
			throw new BusinessException("No se recibio el mercado");
		}
		if (StringUtils.isBlank(this.getIdAgenteFirmado())) {
			throw new BusinessException("El parametro agenteVO, tiene NULL o VACIO " + "el atributo requerido ID");
		}
		if (StringUtils.isBlank(this.getFolioAgenteFirmado())) {
			throw new BusinessException("El parametro agenteVO, tiene NULL o VACIO " + "el atributo requerido FOLIO");
		}
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object arg0, Errors arg1) {

	}

}
