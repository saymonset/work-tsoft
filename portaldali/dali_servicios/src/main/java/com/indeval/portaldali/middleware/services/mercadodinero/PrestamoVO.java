/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class PrestamoVO extends AbstractBaseDTO {

	/**
	 * Constante de Serializacion
	 */
	private static final long serialVersionUID = 1L;

	private Integer idInstitucion;

	private Integer folioInstitucion;

	private BigInteger folio;

	private EmisionVO emision;

	private BigInteger cantidad;

	private BigDecimal precio;

	private BigDecimal valuacion;

	private BigDecimal aforo;

	private BigDecimal aforado;

	private Integer noProrroga;

	private BigDecimal garantiasNecesarias;

	private BigDecimal tasa;

	private BigDecimal sobreTasa;

	private BigDecimal tasaP;

	private BigDecimal premio;

	private Date fechaConcertacion;

	private Date fechaVencimiento;

	private Integer folioOriginal;

	private String status;

	private String statusDescripcion;

	private BigDecimal garantiasCubiertas;

	private String statusPrima;

	private BigDecimal faltanteSobrante;

	/** Garantias del prestamo */
	private GarantiaVigenteVO[] garantias;

	/** Constructor por defecto */
	public PrestamoVO() {
		this.setPrecio(BIG_DECIMAL_ZERO);
		this.setAforo(BIG_DECIMAL_ZERO);
		this.setAforado(BIG_DECIMAL_ZERO);
		this.setGarantiasCubiertas(BIG_DECIMAL_ZERO);
		this.setGarantiasNecesarias(BIG_DECIMAL_ZERO);
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getAforo() {
		return aforo;
	}

	/**
	 * @param aforo
	 */
	public void setAforo(BigDecimal aforo) {
		this.aforo = aforo;
	}

	/**
	 * @return BigInteger
	 */
	public BigInteger getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad
	 */
	public void setCantidad(BigInteger cantidad) {
		this.cantidad = cantidad;
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
	public BigDecimal getFaltanteSobrante() {
		return faltanteSobrante;
	}

	/**
	 * @param faltanteSobrante
	 */
	public void setFaltanteSobrante(BigDecimal faltanteSobrante) {
		this.faltanteSobrante = faltanteSobrante;
	}

	/**
	 * @return Date
	 */
	public Date getFechaConcertacion() {
		return fechaConcertacion;
	}

	/**
	 * @param fechaConcertacion
	 */
	public void setFechaConcertacion(Date fechaConcertacion) {
		this.fechaConcertacion = clona(fechaConcertacion);
	}

	/**
	 * @return Date
	 */
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	/**
	 * @param fechaVencimiento
	 */
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = clona(fechaVencimiento);
	}

	/**
	 * @return BigInteger
	 */
	public BigInteger getFolio() {
		return folio;
	}

	/**
	 * @param folio
	 */
	public void setFolio(BigInteger folio) {
		this.folio = folio;
	}

	/**
	 * @return Integer
	 */
	public Integer getFolioOriginal() {
		return folioOriginal;
	}

	/**
	 * @param folioOriginal
	 */
	public void setFolioOriginal(Integer folioOriginal) {
		this.folioOriginal = folioOriginal;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getGarantiasCubiertas() {
		return garantiasCubiertas;
	}

	/**
	 * @param garantiasCubiertas
	 */
	public void setGarantiasCubiertas(BigDecimal garantiasCubiertas) {
		this.garantiasCubiertas = garantiasCubiertas;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getGarantiasNecesarias() {
		return garantiasNecesarias;
	}

	/**
	 * @param garantiasNecesarias
	 */
	public void setGarantiasNecesarias(BigDecimal garantiasNecesarias) {
		this.garantiasNecesarias = garantiasNecesarias;
	}

	/**
	 * @return Integer
	 */
	public Integer getNoProrroga() {
		return noProrroga;
	}

	/**
	 * @param noProrroga
	 */
	public void setNoProrroga(Integer noProrroga) {
		this.noProrroga = noProrroga;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getPrecio() {
		return precio;
	}

	/**
	 * @param precio
	 */
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getPremio() {
		return premio;
	}

	/**
	 * @param premio
	 */
	public void setPremio(BigDecimal premio) {
		this.premio = premio;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getSobreTasa() {
		return sobreTasa;
	}

	/**
	 * @param sobreTasa
	 */
	public void setSobreTasa(BigDecimal sobreTasa) {
		this.sobreTasa = sobreTasa;
	}

	/**
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return String
	 */
	public String getStatusPrima() {
		return statusPrima;
	}

	/**
	 * @param statusPrima
	 */
	public void setStatusPrima(String statusPrima) {
		this.statusPrima = statusPrima;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getTasa() {
		return tasa;
	}

	/**
	 * @param tasa
	 */
	public void setTasa(BigDecimal tasa) {
		this.tasa = tasa;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getTasaP() {
		return tasaP;
	}

	/**
	 * @param tasaP
	 */
	public void setTasaP(BigDecimal tasaP) {
		this.tasaP = tasaP;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getValuacion() {
		return valuacion;
	}

	/**
	 * @param valuacion
	 */
	public void setValuacion(BigDecimal valuacion) {
		this.valuacion = valuacion;
	}

	/**
	 * @return Integer
	 */
	public Integer getIdInstitucion() {
		return idInstitucion;
	}

	/**
	 * @param idInstitucion
	 */
	public void setIdInstitucion(Integer idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	/**
	 * @return Integer
	 */
	public Integer getFolioInstitucion() {
		return folioInstitucion;
	}

	/**
	 * @param folioInstitucion
	 */
	public void setFolioInstitucion(Integer folioInstitucion) {
		this.folioInstitucion = folioInstitucion;
	}

	/**
	 * @return the statusDescripcion
	 */
	public String getStatusDescripcion() {
		return statusDescripcion;
	}

	/**
	 * @param statusDescripcion
	 *            the statusDescripcion to set
	 */
	public void setStatusDescripcion(String statusDescripcion) {
		this.statusDescripcion = statusDescripcion;
	}

	/**
	 * @return the aforado
	 */
	public BigDecimal getAforado() {
		return aforado;
	}

	/**
	 * @param aforado
	 *            the aforado to set
	 */
	public void setAforado(BigDecimal aforado) {
		this.aforado = aforado;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {

	}

	/**
	 * @return the garantias
	 */
	public GarantiaVigenteVO[] getGarantias() {
		return garantias;
	}

	/**
	 * @param garantias
	 *            the garantias to set
	 */
	public void setGarantias(GarantiaVigenteVO[] garantias) {
		this.garantias = garantias;
	}

}
