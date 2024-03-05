/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class LiquidacionDecretosVO extends AbstractBaseDTO {

	private static final long serialVersionUID = 1L;

	private String idInst;

	private String folioInst;

	private String tipoDerecho;

	private String cuenta;

	private String tv;

	private String emisora;

	private String serie;

	private String cupon;

	private BigDecimal importeACobrar;

	private BigDecimal importeCobrado;

	private BigDecimal remanente;

	private Date fechaLiq;

	private Integer idDerecho;

	private String isin;

	private String divisaPago;

	private Date fechaVencimientoEmision;

	private Integer idMercado;

	private Integer idTipoDerecho;

	private Integer idTipoEjercicio;

	private Integer folioFija;

	private Integer folioVariable;

	/**
	 * @return Integer
	 */
	public Integer getFolioFija() {
		return folioFija;
	}

	/**
	 * @param folioFija
	 */
	public void setFolioFija(Integer folioFija) {
		this.folioFija = folioFija;
	}

	/**
	 * @return Integer
	 */
	public Integer getFolioVariable() {
		return folioVariable;
	}

	/**
	 * @param folioVariable
	 */
	public void setFolioVariable(Integer folioVariable) {
		this.folioVariable = folioVariable;
	}

	/**
	 * @return Integer
	 */
	public Integer getIdTipoDerecho() {
		return idTipoDerecho;
	}

	/**
	 * @param idTipoDerecho
	 */
	public void setIdTipoDerecho(Integer idTipoDerecho) {
		this.idTipoDerecho = idTipoDerecho;
	}

	/**
	 * @return Integer
	 */
	public Integer getIdTipoEjercicio() {
		return idTipoEjercicio;
	}

	/**
	 * @param idTipoEjercicio
	 */
	public void setIdTipoEjercicio(Integer idTipoEjercicio) {
		this.idTipoEjercicio = idTipoEjercicio;
	}

	/**
	 * 
	 */
	public LiquidacionDecretosVO() {
	}

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
	public String getCupon() {
		return cupon;
	}

	/**
	 * @param cupon
	 */
	public void setCupon(String cupon) {
		this.cupon = cupon;
	}

	/**
	 * @return String
	 */
	public String getEmisora() {
		return emisora;
	}

	/**
	 * @param emisora
	 */
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}

	/**
	 * @return Date
	 */
	public Date getFechaLiq() {
		return fechaLiq;
	}

	/**
	 * @param fechaLiq
	 */
	public void setFechaLiq(Date fechaLiq) {
		this.fechaLiq = clona(fechaLiq);
	}

	/**
	 * @return String
	 */
	public String getFolioInst() {
		return folioInst;
	}

	/**
	 * @param folioInst
	 */
	public void setFolioInst(String folioInst) {
		this.folioInst = folioInst;
	}

	/**
	 * @return String
	 */
	public String getIdInst() {
		return idInst;
	}

	/**
	 * @param idInst
	 */
	public void setIdInst(String idInst) {
		this.idInst = idInst;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getImporteACobrar() {
		return importeACobrar;
	}

	/**
	 * @param importeACobrar
	 */
	public void setImporteACobrar(BigDecimal importeACobrar) {
		this.importeACobrar = clonaBigDecimal(importeACobrar);
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getImporteCobrado() {
		return importeCobrado;
	}

	/**
	 * @param importeCobrado
	 */
	public void setImporteCobrado(BigDecimal importeCobrado) {
		this.importeCobrado = clonaBigDecimal(importeCobrado);
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getRemanente() {
		return remanente;
	}

	/**
	 * @param remanente
	 */
	public void setRemanente(BigDecimal remanente) {
		this.remanente = clonaBigDecimal(remanente);
	}

	/**
	 * @return String
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * @param serie
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	/**
	 * @return String
	 */
	public String getTipoDerecho() {
		return tipoDerecho;
	}

	/**
	 * @param tipoDerecho
	 */
	public void setTipoDerecho(String tipoDerecho) {
		this.tipoDerecho = tipoDerecho;
	}

	/**
	 * @return String
	 */
	public String getTv() {
		return tv;
	}

	/**
	 * @param tv
	 */
	public void setTv(String tv) {
		this.tv = tv;
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
	 * @return Integer
	 */
	public Integer getIdMercado() {
		return idMercado;
	}

	/**
	 * @param idMercado
	 */
	public void setIdMercado(Integer idMercado) {
		this.idMercado = idMercado;
	}

	/**
	 * @return String
	 */
	public String getIsin() {
		return isin;
	}

	/**
	 * @param isin
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}

	/**
	 * @return String
	 */
	public String getDivisaPago() {
		return divisaPago;
	}

	/**
	 * @param divisaPago
	 */
	public void setDivisaPago(String divisaPago) {
		this.divisaPago = divisaPago;
	}

	/**
	 * @return Date
	 */
	public Date getFechaVencimientoEmision() {
		return fechaVencimientoEmision;
	}

	/**
	 * @param fechaVencimientoEmision
	 */
	public void setFechaVencimientoEmision(Date fechaVencimientoEmision) {
		this.fechaVencimientoEmision = fechaVencimientoEmision;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors1) {
	}

}