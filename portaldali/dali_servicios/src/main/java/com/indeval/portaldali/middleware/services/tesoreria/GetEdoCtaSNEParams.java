/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.tesoreria;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class GetEdoCtaSNEParams extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private Date fechaOperacion;

	private String agrupacion;

	private AgenteVO agente;

	private String idOrigen;

	private String idAplicacion;

	private String idMercado;

	private String idMovimiento;

	private String idCuentasEfectivo;

	private AgenteVO contraparte;

	private PaginaVO pagina;

	private EmisionVO emision;

	/* Getters and Setters */

	/**
	 * @return PaginaVO
	 */
	public PaginaVO getPagina() {
		return pagina;
	}

	/**
	 * @param pagina
	 */
	public void setPagina(PaginaVO pagina) {
		this.pagina = pagina;
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
	public String getAgrupacion() {
		return agrupacion;
	}

	/**
	 * @param agrupacion
	 */
	public void setAgrupacion(String agrupacion) {
		this.agrupacion = agrupacion;
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
	public Date getFechaOperacion() {
		return fechaOperacion;
	}

	/**
	 * @param fechaOperacion
	 */
	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = clona(fechaOperacion);
	}

	/**
	 * @return String
	 */
	public String getIdAplicacion() {
		return idAplicacion;
	}

	/**
	 * @param idAplicacion
	 */
	public void setIdAplicacion(String idAplicacion) {
		this.idAplicacion = idAplicacion;
	}

	/**
	 * @return String
	 */
	public String getIdCuentasEfectivo() {
		return idCuentasEfectivo;
	}

	/**
	 * @param idCuentasEfectivo
	 */
	public void setIdCuentasEfectivo(String idCuentasEfectivo) {
		this.idCuentasEfectivo = idCuentasEfectivo;
	}

	/**
	 * @return String
	 */
	public String getIdMercado() {
		return idMercado;
	}

	/**
	 * @param idMercado
	 */
	public void setIdMercado(String idMercado) {
		this.idMercado = idMercado;
	}

	/**
	 * @return String
	 */
	public String getIdMovimiento() {
		return idMovimiento;
	}

	/**
	 * @param idMovimiento
	 */
	public void setIdMovimiento(String idMovimiento) {
		this.idMovimiento = idMovimiento;
	}

	/**
	 * @return String
	 */
	public String getIdOrigen() {
		return idOrigen;
	}

	/**
	 * @param idOrigen
	 */
	public void setIdOrigen(String idOrigen) {
		this.idOrigen = idOrigen;
	}

	/**
	 * @return EmisionVO
	 */
	public EmisionVO getEmision() {
		return emision;
	}

	/**
	 * @param emision
	 */
	public void setEmision(EmisionVO emision) {
		this.emision = emision;
	}

	/**
	 * @throws BusinessException
	 */
	public void agrupacionValida() throws BusinessException {
		if (!this.agrupacion.equalsIgnoreCase(TesoreriaService.AGRUPADA) && !this.agrupacion.equalsIgnoreCase(TesoreriaService.CRONOLOGICA)) {
			throw new BusinessException("El atributo agrupacion, tiene un valor no valido");
		}
	}

	/**
	 * Valida que el objeto tenga todos los atributos requeridos
	 * 
	 * @throws BusinessException
	 */
	public void esValido() throws BusinessException {
		Assert.notNull(this.getAgente(), "El objeto params tiene el agente NULL");

		if (StringUtils.isBlank(this.getAgente().getId())) {
			throw new BusinessException("El id del agente no puede ser nulo");
		}
		if (StringUtils.isBlank(this.getAgente().getFolio())) {
			throw new BusinessException("El folio del agente no puede ser nulo");
		}
		// this.getAgente().tieneClaveValida();
		this.agrupacionValida();
	}

	/**
	 * @param inObject
	 * @return boolean
	 */
	public static boolean isAn(Object inObject) {

		if ((inObject == null) || (!(inObject instanceof GetEdoCtaSNEParams)))
			return false;

		return true;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

}
