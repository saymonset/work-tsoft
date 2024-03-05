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
public class ConsultaPrestamosParams extends AbstractBaseDTO {

	/** Constante de Serializacion */
	private static final long serialVersionUID = 1L;

	private String folio;

	private EmisionVO emision;

	private Date fechaInicio;

	private Date fechaFin;

	private Integer idStatusPrestamo;

	private BigInteger cantidad;

	private BigDecimal precio;

	private Integer noProrroga;

	private Date fechaConcertacion;

	private Date fechaVencimiento;

	private String estatusPrima;

	private Boolean faltanteSobrante;

	private String idPrestatario;

	private String folPrestatario;

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
	 * @return String
	 */
	public String getEstatusPrima() {
		return estatusPrima;
	}

	/**
	 * @param estatusPrima
	 */
	public void setEstatusPrima(String estatusPrima) {
		this.estatusPrima = estatusPrima;
	}

	/**
	 * @return Boolean
	 */
	public Boolean getFaltanteSobrante() {
		return faltanteSobrante;
	}

	/**
	 * @param faltanteSobrante
	 */
	public void setFaltanteSobrante(Boolean faltanteSobrante) {
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
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = clona(fechaFin);
	}

	/**
	 * @return Date
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = clona(fechaInicio);
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
	 * @return
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * @return
	 */
	public Integer getIdStatusPrestamo() {
		return idStatusPrestamo;
	}

	/**
	 * @param idStatusPrestamo
	 */
	public void setIdStatusPrestamo(Integer idStatusPrestamo) {
		this.idStatusPrestamo = idStatusPrestamo;
	}

	/**
	 * @return
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
	 * @return
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
	 * @return
	 */
	public String getFolPrestatario() {
		return folPrestatario;
	}

	/**
	 * @param folPrestatario
	 */
	public void setFolPrestatario(String folPrestatario) {
		this.folPrestatario = folPrestatario;
	}

	/**
	 * @return
	 */
	public String getIdPrestatario() {
		return idPrestatario;
	}

	/**
	 * @param idPrestatario
	 */
	public void setIdPrestatario(String idPrestatario) {
		this.idPrestatario = idPrestatario;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

}
