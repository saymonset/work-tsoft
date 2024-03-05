/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.modelo;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ValorVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private String idInst;

	private String folioInst;

	private String cuenta;

	private String tv;

	private String emisora;

	private String serie;

	private String cupon;

	private String uVersion;

	private String aplicacion;

	private BigDecimal cantidadAcciones;

	private String emisionExtr;

	private BigDecimal ejerDia;

	private Date fechaHora;

	private BigDecimal saldoDisponible;

	private BigDecimal saldoIncluyeOp;

	private BigDecimal saldoInicialDia;

	private BigDecimal saldoReporto;

	private String usuario;

	private BigDecimal valoresPrestados;

	private BigDecimal valEnPrestamo;

	private BigDecimal saldoTesoreria;

	private String areaTrabajo;

	private String nombreUsuario;

	private String ipAddress;

	private EmisionVO emision;

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
	 * @return the areaTrabajo
	 */
	public String getAreaTrabajo() {
		return areaTrabajo;
	}

	/**
	 * @param areaTrabajo
	 *            the areaTrabajo to set
	 */
	public void setAreaTrabajo(String areaTrabajo) {
		this.areaTrabajo = areaTrabajo;
	}

	/**
	 * @return the cantidadAcciones
	 */
	public BigDecimal getCantidadAcciones() {
		return cantidadAcciones;
	}

	/**
	 * @param cantidadAcciones
	 *            the cantidadAcciones to set
	 */
	public void setCantidadAcciones(BigDecimal cantidadAcciones) {
		this.cantidadAcciones = cantidadAcciones;
	}

	/**
	 * @return the cuenta
	 */
	public String getCuenta() {
		return cuenta;
	}

	/**
	 * @param cuenta
	 *            the cuenta to set
	 */
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	/**
	 * @return the cupon
	 */
	public String getCupon() {
		return cupon;
	}

	/**
	 * @param cupon
	 *            the cupon to set
	 */
	public void setCupon(String cupon) {
		this.cupon = cupon;
	}

	/**
	 * @return the ejerDia
	 */
	public BigDecimal getEjerDia() {
		return ejerDia;
	}

	/**
	 * @param ejerDia
	 *            the ejerDia to set
	 */
	public void setEjerDia(BigDecimal ejerDia) {
		this.ejerDia = ejerDia;
	}

	/**
	 * @return the emision
	 */
	public EmisionVO getEmision() {
		return emision;
	}

	/**
	 * @param emision
	 *            the emision to set
	 */
	public void setEmision(EmisionVO emision) {
		this.emision = emision;
	}

	/**
	 * @return the emisionExtr
	 */
	public String getEmisionExtr() {
		return emisionExtr;
	}

	/**
	 * @param emisionExtr
	 *            the emisionExtr to set
	 */
	public void setEmisionExtr(String emisionExtr) {
		this.emisionExtr = emisionExtr;
	}

	/**
	 * @return the emisora
	 */
	public String getEmisora() {
		return emisora;
	}

	/**
	 * @param emisora
	 *            the emisora to set
	 */
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}

	/**
	 * @return the fechaHora
	 */
	public Date getFechaHora() {
		return fechaHora;
	}

	/**
	 * @param fechaHora
	 *            the fechaHora to set
	 */
	public void setFechaHora(Date fechaHora) {
		this.fechaHora = clona(fechaHora);
	}

	/**
	 * @return the folioInst
	 */
	public String getFolioInst() {
		return folioInst;
	}

	/**
	 * @param folioInst
	 *            the folioInst to set
	 */
	public void setFolioInst(String folioInst) {
		this.folioInst = folioInst;
	}

	/**
	 * @return the idInst
	 */
	public String getIdInst() {
		return idInst;
	}

	/**
	 * @param idInst
	 *            the idInst to set
	 */
	public void setIdInst(String idInst) {
		this.idInst = idInst;
	}

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress
	 *            the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @return the nombreUsuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * @param nombreUsuario
	 *            the nombreUsuario to set
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * @return the saldoDisponible
	 */
	public BigDecimal getSaldoDisponible() {
		return saldoDisponible;
	}

	/**
	 * @param saldoDisponible
	 *            the saldoDisponible to set
	 */
	public void setSaldoDisponible(BigDecimal saldoDisponible) {
		this.saldoDisponible = saldoDisponible;
	}

	/**
	 * @return the saldoIncluyeOp
	 */
	public BigDecimal getSaldoIncluyeOp() {
		return saldoIncluyeOp;
	}

	/**
	 * @param saldoIncluyeOp
	 *            the saldoIncluyeOp to set
	 */
	public void setSaldoIncluyeOp(BigDecimal saldoIncluyeOp) {
		this.saldoIncluyeOp = saldoIncluyeOp;
	}

	/**
	 * @return the saldoInicialDia
	 */
	public BigDecimal getSaldoInicialDia() {
		return saldoInicialDia;
	}

	/**
	 * @param saldoInicialDia
	 *            the saldoInicialDia to set
	 */
	public void setSaldoInicialDia(BigDecimal saldoInicialDia) {
		this.saldoInicialDia = saldoInicialDia;
	}

	/**
	 * @return the saldoReporto
	 */
	public BigDecimal getSaldoReporto() {
		return saldoReporto;
	}

	/**
	 * @param saldoReporto
	 *            the saldoReporto to set
	 */
	public void setSaldoReporto(BigDecimal saldoReporto) {
		this.saldoReporto = saldoReporto;
	}

	/**
	 * @return the saldoTesoreria
	 */
	public BigDecimal getSaldoTesoreria() {
		return saldoTesoreria;
	}

	/**
	 * @param saldoTesoreria
	 *            the saldoTesoreria to set
	 */
	public void setSaldoTesoreria(BigDecimal saldoTesoreria) {
		this.saldoTesoreria = saldoTesoreria;
	}

	/**
	 * @return the serie
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * @param serie
	 *            the serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	/**
	 * @return the tv
	 */
	public String getTv() {
		return tv;
	}

	/**
	 * @param tv
	 *            the tv to set
	 */
	public void setTv(String tv) {
		this.tv = tv;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the uVersion
	 */
	public String getUVersion() {
		return uVersion;
	}

	/**
	 * @param version
	 *            the uVersion to set
	 */
	public void setUVersion(String version) {
		uVersion = version;
	}

	/**
	 * @return the valEnPrestamo
	 */
	public BigDecimal getValEnPrestamo() {
		return valEnPrestamo;
	}

	/**
	 * @param valEnPrestamo
	 *            the valEnPrestamo to set
	 */
	public void setValEnPrestamo(BigDecimal valEnPrestamo) {
		this.valEnPrestamo = valEnPrestamo;
	}

	/**
	 * @return the valoresPrestados
	 */
	public BigDecimal getValoresPrestados() {
		return valoresPrestados;
	}

	/**
	 * @param valoresPrestados
	 *            the valoresPrestados to set
	 */
	public void setValoresPrestados(BigDecimal valoresPrestados) {
		this.valoresPrestados = valoresPrestados;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object arg0, Errors arg1) {
	}

}
