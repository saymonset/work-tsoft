/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class LiquidacionDecretosDetalleVO extends AbstractBaseDTO {

	/**
	 * Constante de serializacion
	 */
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

	private Integer plazo;

	private String idInstitucion;

	private String folioInstitucion;

	private String cuenta;

	private Integer idDerecho;

	private String descTipoEjercicio;

	private BigDecimal tasaDescuento;

	private BigDecimal tasaRendimiento;

	/**
	 * @return String
	 */
	public String getCuenta() {
		return cuenta;
	}

	/**
	 * @param cuenta
	 */
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	/**
	 * @return String
	 */
	public String getDescTipoEjercicio() {
		return descTipoEjercicio;
	}

	/**
	 * @param descTipoEjercicio
	 */
	public void setDescTipoEjercicio(String descTipoEjercicio) {
		this.descTipoEjercicio = descTipoEjercicio;
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
	 * @return BigDecimal
	 */
	public BigDecimal getTasaDescuento() {
		return tasaDescuento;
	}

	/**
	 * @param tasaDescuento
	 */
	public void setTasaDescuento(BigDecimal tasaDescuento) {
		this.tasaDescuento = tasaDescuento;
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
		this.fechaLiquidacion = fechaLiquidacion;
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
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {

	}

}
