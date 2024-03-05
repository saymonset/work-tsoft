/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.util.vo;

import java.util.Date;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

/**
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class BitacoraVOParams extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	/**  */
	private String idTrasp;

	/**  */
	private String folioTrasp;

	/**  */
	private EmisionVO emisionVO;

	/**  */
	private String referenciaMensaje;

	/**  */
	private Date fechaLiquidacion;

	/**  */
	private PaginaVO paginaVO;

	/**  */
	private String estatusRegistro;

	/**  */
	private Date fechaConcertacion;

	/**  */
	private Boolean banderaPaginaVO;

	/**
	 * Bandera que define la base de datos apuntada true = Oracle , false =
	 * Sybase
	 */
	private Boolean banderaOracle;

	/** Constructor */
	public BitacoraVOParams() {

		this.banderaPaginaVO = Boolean.FALSE;
		this.banderaOracle = Boolean.TRUE;

	}

	/**
	 * Constructor que permite seleccionar la base de datos
	 * 
	 * @param sybase
	 *            true = Oracle , false = Sybase
	 */
	public BitacoraVOParams(boolean sybase) {

		this.banderaPaginaVO = Boolean.FALSE;
		this.banderaOracle = new Boolean(sybase);

	}

	/**
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
	}

	/**
	 * 
	 * @return the folioTrasp
	 */
	public String getFolioTrasp() {

		return folioTrasp;

	}

	/**
	 * 
	 * @param folioTrasp
	 *            the folioTrasp to set
	 */
	public void setFolioTrasp(String folioTrasp) {

		this.folioTrasp = folioTrasp;

	}

	/**
	 * 
	 * @return the idTrasp
	 */
	public String getIdTrasp() {

		return idTrasp;

	}

	/**
	 * 
	 * @param idTrasp
	 *            the idTrasp to set
	 */
	public void setIdTrasp(String idTrasp) {

		this.idTrasp = idTrasp;

	}

	/**
	 * 
	 * @return the referenciaMensaje
	 */
	public String getReferenciaMensaje() {

		return referenciaMensaje;

	}

	/**
	 * 
	 * @param referenciaMensaje
	 *            the referenciaMensaje to set
	 */
	public void setReferenciaMensaje(String referenciaMensaje) {

		this.referenciaMensaje = referenciaMensaje;

	}

	/**
	 * 
	 * @return the fechaLiquidacion
	 */
	public Date getFechaLiquidacion() {

		return fechaLiquidacion;

	}

	/**
	 * 
	 * @param fechaLiquidacion
	 *            the fechaLiquidacion to set
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {

		this.fechaLiquidacion = fechaLiquidacion;

	}

	/**
	 * Valida los atributos requeridos 'idTrasp' y 'folioTrasp'
	 * 
	 * @throws BusinessException
	 *             Se arroja si alguno de los atributos requeridos esta null o
	 *             vacio.
	 */
	public void validateAttributes() throws BusinessException {

		// Este metodo se estaba utilizando para validar estos parametros
		// pero no se invoca ahora posiblemente mas adelante si
		if (StringUtils.isBlank(this.idTrasp)) {

			throw new BusinessException("El objeto de parametros tiene el atributo requerido " + "idTrasp NULL o VACIO");

		}

		if (StringUtils.isBlank(this.folioTrasp)) {

			throw new BusinessException("El objeto de parametros tiene el atributo requerido " + "folioTrasp NULL o VACIO");

		}

	}

	/**
	 * 
	 * @return the emisionVO
	 */
	public EmisionVO getEmisionVO() {

		return emisionVO;

	}

	/**
	 * 
	 * @param emisionVO
	 *            the emisionVO to set
	 */
	public void setEmisionVO(EmisionVO emisionVO) {

		this.emisionVO = emisionVO;

	}

	/**
	 * 
	 * @return the paginaVO
	 */
	public PaginaVO getPaginaVO() {

		return paginaVO;

	}

	/**
	 * 
	 * @param paginaVO
	 *            the paginaVO to set
	 */
	public void setPaginaVO(PaginaVO paginaVO) {

		this.paginaVO = paginaVO;

	}

	/**
	 * 
	 * @return the estatusRegistro
	 */
	public String getEstatusRegistro() {

		return estatusRegistro;

	}

	/**
	 * 
	 * @param estatusRegistro
	 *            the estatusRegistro to set
	 */
	public void setEstatusRegistro(String estatusRegistro) {

		this.estatusRegistro = estatusRegistro;

	}

	/**
	 * 
	 * @return the fechaConcertacion
	 */
	public Date getFechaConcertacion() {

		return fechaConcertacion;

	}

	/**
	 * 
	 * @param fechaConcertacion
	 *            the fechaConcertacion to set
	 */
	public void setFechaConcertacion(Date fechaConcertacion) {

		this.fechaConcertacion = fechaConcertacion;

	}

	/**
	 * 
	 * @return the banderaPaginaVO
	 */
	public Boolean getBanderaPaginaVO() {

		return banderaPaginaVO;

	}

	/**
	 * 
	 * @param banderaPaginaVO
	 *            the banderaPaginaVO to set
	 */
	public void setBanderaPaginaVO(Boolean banderaPaginaVO) {

		this.banderaPaginaVO = banderaPaginaVO;

	}

	/**
	 * 
	 * @return the banderaOracle
	 */
	public Boolean getBanderaOracle() {

		return banderaOracle;

	}

	// /**
	// * @param banderaOracle the banderaOracle to set
	// */
	// public void setBanderaOracle(Boolean banderaOracle) {
	// this.banderaOracle = banderaOracle;
	// }
}
