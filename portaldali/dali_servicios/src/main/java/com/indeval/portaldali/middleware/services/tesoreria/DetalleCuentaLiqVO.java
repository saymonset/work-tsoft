/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.tesoreria;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class DetalleCuentaLiqVO extends AbstractBaseDTO {

	/** Constante de Serializacion */
	private static final long serialVersionUID = 1L;

	private EmisionVO emision;

	private Date fechaLiquidacion;

	private BigDecimal valorNominal;

	private BigDecimal saldoTitulos;

	private BigDecimal importeDecreto;

	private BigDecimal intereses;

	private BigDecimal importeCobrar;

	private String divisa;

	private BigDecimal tasaInteres;

	private BigDecimal tasaRendimientoDescto;

	private BigDecimal tasaRendimiento;

	private BigDecimal tasaDescto;

	private Integer plazo;

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
	 * @return Date
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	/**
	 * @param fechaLiquidacion
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = clona(fechaLiquidacion);
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getImporteDecreto() {
		return importeDecreto;
	}

	/**
	 * @param importeDecreto
	 */
	public void setImporteDecreto(BigDecimal importeDecreto) {
		this.importeDecreto = importeDecreto;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getIntereses() {
		return intereses;
	}

	/**
	 * @param intereses
	 */
	public void setIntereses(BigDecimal intereses) {
		this.intereses = intereses;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getSaldoTitulos() {
		return saldoTitulos;
	}

	/**
	 * @param saldoTitulos
	 */
	public void setSaldoTitulos(BigDecimal saldoTitulos) {
		this.saldoTitulos = saldoTitulos;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getValorNominal() {
		return valorNominal;
	}

	/**
	 * @param valorNominal
	 */
	public void setValorNominal(BigDecimal valorNominal) {
		this.valorNominal = valorNominal;
	}

	/**
	 * @param inObject
	 * @return boolean
	 */
	public static boolean isAn(Object inObject) {

		if ((inObject == null) || (!(inObject instanceof DetalleCuentaLiqVO)))
			return false;

		return true;
	}

	/**
	 * @return String
	 */
	public String getDivisa() {
		return divisa;
	}

	/**
	 * @param divisa
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	/**
	 * @return Integer
	 */
	public Integer getPlazo() {
		return plazo;
	}

	/**
	 * @param plazo
	 */
	public void setPlazo(Integer plazo) {
		this.plazo = plazo;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getTasaInteres() {
		return tasaInteres;
	}

	/**
	 * @param tasaInteres
	 */
	public void setTasaInteres(BigDecimal tasaInteres) {
		this.tasaInteres = tasaInteres;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getTasaRendimientoDescto() {
		return tasaRendimientoDescto;
	}

	/**
	 * @param tasaRendimientoDescto
	 */
	public void setTasaRendimientoDescto(BigDecimal tasaRendimientoDescto) {
		this.tasaRendimientoDescto = tasaRendimientoDescto;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getImporteCobrar() {
		return importeCobrar;
	}

	/**
	 * @param importeCobrar
	 */
	public void setImporteCobrar(BigDecimal importeCobrar) {
		this.importeCobrar = importeCobrar;
	}

	/**
	 * 
	 * @see org.springframework.validation.Validator #validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {

	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getTasaDescto() {
		return tasaDescto;
	}

	/**
	 * @param tasaDescto
	 */
	public void setTasaDescto(BigDecimal tasaDescto) {
		this.tasaDescto = tasaDescto;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getTasaRendimiento() {
		return tasaRendimiento;
	}

	/**
	 * @param tasaRendimiento
	 */
	public void setTasaRendimiento(BigDecimal tasaRendimiento) {
		this.tasaRendimiento = tasaRendimiento;
	}

}
