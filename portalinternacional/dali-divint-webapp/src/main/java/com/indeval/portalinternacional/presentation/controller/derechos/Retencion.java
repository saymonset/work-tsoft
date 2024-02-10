package com.indeval.portalinternacional.presentation.controller.derechos;

import java.math.BigDecimal;


public class Retencion {
	
	private Long idCuentaNombrada;
	private String cuenta;
	private String nombreInstitucion;
	private Float porcentajeRetencion;
	private Long asignacion;
	private String claveInstitucion;
	private BigDecimal monto;
	private BigDecimal montoRetenido;
	private BigDecimal montoPagado;
	private BigDecimal montoFee;
	
	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	public Float getPorcentajeRetencion() {
		return porcentajeRetencion;
	}
	public void setPorcentajeRetencion(Float porcentajeRetencion) {
		this.porcentajeRetencion = porcentajeRetencion;
	}
	public Long getAsignacion() {
		return asignacion;
	}
	public void setAsignacion(Long asignacion) {
		this.asignacion = asignacion;
	}
	public Long getIdCuentaNombrada() {
		return idCuentaNombrada;
	}
	public void setIdCuentaNombrada(Long idCuentaNombrada) {
		this.idCuentaNombrada = idCuentaNombrada;
	}
	public String getNombreInstitucion() {
		return nombreInstitucion;
	}
	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}
	public String getClaveInstitucion() {
		return claveInstitucion;
	}
	public void setClaveInstitucion(String claveInstitucion) {
		this.claveInstitucion = claveInstitucion;
	}
	public BigDecimal getMonto() {
		return monto;
	}
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
	public BigDecimal getMontoRetenido() {
		return montoRetenido;
	}
	public void setMontoRetenido(BigDecimal montoRetenido) {
		this.montoRetenido = montoRetenido;
	}
	public BigDecimal getMontoPagado() {
		return montoPagado;
	}
	public void setMontoPagado(BigDecimal montoPagado) {
		this.montoPagado = montoPagado;
	}
	public BigDecimal getMontoFee() {
		return montoFee;
	}
	public void setMontoFee(BigDecimal montoFee) {
		this.montoFee = montoFee;
	}
	@Override
	public String toString() {
		return "Retencion [idCuentaNombrada=" + idCuentaNombrada + ", cuenta=" + cuenta + ", nombreInstitucion="
				+ nombreInstitucion + ", porcentajeRetencion=" + porcentajeRetencion + ", asignacion=" + asignacion
				+ ", claveInstitucion=" + claveInstitucion + ", monto=" + monto + ", montoRetenido=" + montoRetenido
				+ ", montoPagado=" + montoPagado + ", montoFee=" + montoFee + "]";
	}
}
