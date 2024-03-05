/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class LiquidacionDecretosDetalleAmortizacionesVO extends AbstractBaseDTO {

	/**
	 * Constante de serializacion
	 */
	private static final long serialVersionUID = 1L;

	private String idContraparte;

	private String folioContraparte;

	private BigDecimal importe;

	private BigInteger titulos;

	private BigInteger operacion;

	private String folioLiquidacion;

	private Integer idDerecho;

	private String descEjercicio;

	private String codigoDivisa;

	private String idInstitucion;

	private String folioInstitucion;

	/**
	 * @return String
	 */
	public String getCodigoDivisa() {
		return codigoDivisa;
	}

	/**
	 * @param codigoDivisa
	 */
	public void setCodigoDivisa(String codigoDivisa) {
		this.codigoDivisa = codigoDivisa;
	}

	/**
	 * @return String
	 */
	public String getDescEjercicio() {
		return descEjercicio;
	}

	/**
	 * @param descEjercicio
	 */
	public void setDescEjercicio(String descEjercicio) {
		this.descEjercicio = descEjercicio;
	}

	/**
	 * @return String
	 */
	public String getFolioInstitucion() {
		return folioInstitucion;
	}

	/**
	 * @param folioInstitucion
	 */
	public void setFolioInstitucion(String folioInstitucion) {
		this.folioInstitucion = folioInstitucion;
	}

	/**
	 * @return Integer
	 */
	public Integer getIdDerecho() {
		return idDerecho;
	}

	/**
	 * @param idDerecho
	 */
	public void setIdDerecho(Integer idDerecho) {
		this.idDerecho = idDerecho;
	}

	/**
	 * @return String
	 */
	public String getIdInstitucion() {
		return idInstitucion;
	}

	/**
	 * @param idInstitucion
	 */
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	/**
	 * @return String
	 */
	public String getFolioContraparte() {
		return folioContraparte;
	}

	/**
	 * @param folioContraparte
	 */
	public void setFolioContraparte(String folioContraparte) {
		this.folioContraparte = folioContraparte;
	}

	/**
	 * @return BigInteger
	 */
	public String getFolioLiquidacion() {
		return folioLiquidacion;
	}

	/**
	 * @param folioLiquidacion
	 */
	public void setFolioLiquidacion(String folioLiquidacion) {
		this.folioLiquidacion = folioLiquidacion;
	}

	/**
	 * @return String
	 */
	public String getIdContraparte() {
		return idContraparte;
	}

	/**
	 * @param idContraparte
	 */
	public void setIdContraparte(String idContraparte) {
		this.idContraparte = idContraparte;
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
	 * @return BigInteger
	 */
	public BigInteger getOperacion() {
		return operacion;
	}

	/**
	 * @param operacion
	 */
	public void setOperacion(BigInteger operacion) {
		this.operacion = operacion;
	}

	/**
	 * @return BigInteger
	 */
	public BigInteger getTitulos() {
		return titulos;
	}

	/**
	 * @param titulos
	 */
	public void setTitulos(BigInteger titulos) {
		this.titulos = titulos;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {

	}

}
