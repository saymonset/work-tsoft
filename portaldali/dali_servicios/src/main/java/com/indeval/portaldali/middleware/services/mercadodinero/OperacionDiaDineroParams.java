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
 * Clase de transporte.
 * 
 * @author cerjio
 */
public class OperacionDiaDineroParams extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private AgenteVO agenteVO;

	private EmisionVO emisionVO;

	private String origen;

	private String aplicacion;

	private Date fechaInicial;

	private Date fechaFinal;

	private String estatusOperacion;

	private String[] tipoOperacion;

	private String maneraExtraccion;

	private String funcionSQL;

	private PaginaVO paginaVO;

	private boolean isExport;

	/**
	 * @return the isExport
	 */
	public boolean isExport() {
		return isExport;
	}

	/**
	 * @param isExport
	 *            the isExport to set
	 */
	public void setExport(boolean isExport) {
		this.isExport = isExport;
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
	 * @return the agenteVO
	 */
	public AgenteVO getAgenteVO() {
		return agenteVO;
	}

	/**
	 * @param agenteVO
	 *            the agenteVO to set
	 */
	public void setAgenteVO(AgenteVO agenteVO) {
		this.agenteVO = agenteVO;
	}

	/**
	 * @return the aplicacion
	 */
	public String getAplicacion() {
		return aplicacion;
	}

	/**
	 * @param aplicacion
	 *            the aplicacion to set
	 */
	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	/**
	 * @return the emisionVO
	 */
	public EmisionVO getEmisionVO() {
		return emisionVO;
	}

	/**
	 * @param emisionVO
	 *            the emisionVO to set
	 */
	public void setEmisionVO(EmisionVO emisionVO) {
		this.emisionVO = emisionVO;
	}

	/**
	 * @return the estatusOperacion
	 */
	public String getEstatusOperacion() {
		return estatusOperacion;
	}

	/**
	 * @param estatusOperacion
	 *            the estatusOperacion to set
	 */
	public void setEstatusOperacion(String estatusOperacion) {
		this.estatusOperacion = estatusOperacion;
	}

	/**
	 * @return the fechaFinal
	 */
	public Date getFechaFinal() {
		return fechaFinal;
	}

	/**
	 * @param fechaFinal
	 *            the fechaFinal to set
	 */
	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	/**
	 * @return the fechaInicial
	 */
	public Date getFechaInicial() {
		return fechaInicial;
	}

	/**
	 * @param fechaInicial
	 *            the fechaInicial to set
	 */
	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	/**
	 * @return the maneraExtraccion
	 */
	public String getManeraExtraccion() {
		return maneraExtraccion;
	}

	/**
	 * @param maneraExtraccion
	 *            the maneraExtraccion to set
	 */
	public void setManeraExtraccion(String maneraExtraccion) {
		this.maneraExtraccion = maneraExtraccion;
	}

	/**
	 * @return the origen
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * @param origen
	 *            the origen to set
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * @return the tipoOperacion
	 */
	public String[] getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * @param tipoOperacion
	 *            the tipoOperacion to set
	 */
	public void setTipoOperacion(String[] tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	/**
	 * @return the funcionSQL
	 */
	public String getFuncionSQL() {
		return funcionSQL;
	}

	/**
	 * @param funcionSQL
	 *            the funcionSQL to set
	 */
	public void setFuncionSQL(String funcionSQL) {
		this.funcionSQL = funcionSQL;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
	}

}
