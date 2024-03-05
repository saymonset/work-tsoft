/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class CapturaGarantiasParams extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private AgenteVO traspasante;

	private AgenteVO receptor;

	private EmisionVO emision;

	private BigDecimal precioVector;

	private BigDecimal cantidadOperada;

	private BigDecimal saldoDisponible;

	private BigDecimal posicionActual;

	private String descripcion;

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getCantidadOperada() {
		return cantidadOperada;
	}

	/**
	 * @param cantidadOperada
	 */
	public void setCantidadOperada(BigDecimal cantidadOperada) {
		this.cantidadOperada = cantidadOperada;
	}

	/**
	 * @return String
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
	 * @return BigDecimal
	 */
	public BigDecimal getPrecioVector() {
		return precioVector;
	}

	/**
	 * @param precioVector
	 */
	public void setPrecioVector(BigDecimal precioVector) {
		this.precioVector = precioVector;
	}

	/**
	 * @return AgenteVO
	 */
	public AgenteVO getReceptor() {
		return receptor;
	}

	/**
	 * @param receptor
	 */
	public void setReceptor(AgenteVO receptor) {
		this.receptor = receptor;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getSaldoDisponible() {
		return saldoDisponible;
	}

	/**
	 * @param saldoDisponible
	 */
	public void setSaldoDisponible(BigDecimal saldoDisponible) {
		this.saldoDisponible = saldoDisponible;
	}

	/**
	 * @return AgenteVO
	 */
	public AgenteVO getTraspasante() {
		return traspasante;
	}

	/**
	 * @param traspasante
	 */
	public void setTraspasante(AgenteVO traspasante) {
		this.traspasante = traspasante;
	}

	/**
	 * Valida que el objeto tenga todos los atributos requeridos
	 * 
	 * @throws BusinessException
	 */
	public void esValido() throws BusinessException {
		try {
			Assert.notNull(this.getTraspasante());
			this.getTraspasante().tieneClaveValida();
			StringUtils.isNotBlank(this.getTraspasante().getCuenta());
			Assert.notNull(this.getReceptor());
			this.getReceptor().tieneClaveValida();
			StringUtils.isNotBlank(this.getReceptor().getCuenta());
			Assert.notNull(this.getEmision());
			this.getEmision().tienePKValida();
			Assert.notNull(this.getPrecioVector());
			Assert.notNull(this.getCantidadOperada());
			Assert.notNull(this.getSaldoDisponible());
			Assert.notNull(this.getPosicionActual());
		} catch (IllegalArgumentException e) {
			new BusinessException(e.getMessage());
		}
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "traspasante", "El traspasante esta NULL");
		ValidationUtils.rejectIfEmpty(errors, "receptor", "El receptor esta NULL");
		ValidationUtils.rejectIfEmpty(errors, "emision", "La emision esta NULL");
		ValidationUtils.rejectIfEmpty(errors, "precioVector", "El precioVector esta NULL");
		ValidationUtils.rejectIfEmpty(errors, "cantidadOperada", "La cantidadOperada esta NULL");
		ValidationUtils.rejectIfEmpty(errors, "saldoDisponible", "El saldoDisponible esta NULL");
		ValidationUtils.rejectIfEmpty(errors, "posicionActual", "La posicionActual esta NULL");
	}

}
