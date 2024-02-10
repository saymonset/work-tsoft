package com.indeval.portalinternacional.presentation.controller.derechos;

import java.io.Serializable;

import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;

public class BeneficiarioDerechoVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8167993635899374572L;
	
	private String cuenta;
	private String nombre;
	private String direccion;
	private String rfc;
	private String retencion;
	private String posicion;
	private String posicionAsignada;
	private String monto;
	private String montoRetenido;
	private String montoPagado;

	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getRetencion() {
		return retencion;
	}
	public void setRetencion(String retencion) {
		this.retencion = retencion;
	}
	public String getPosicion() {
		return posicion;
	}
	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}
	public String getPosicionAsignada() {
		return posicionAsignada;
	}
	public void setPosicionAsignada(String posicionAsignada) {
		this.posicionAsignada = posicionAsignada;
	}
	public String getMonto() {
		return monto;
	}
	public void setMonto(String monto) {
		this.monto = monto;
	}
	public String getMontoRetenido() {
		return montoRetenido;
	}
	public void setMontoRetenido(String montoRetenido) {
		this.montoRetenido = montoRetenido;
	}
	public String getMontoPagado() {
		return montoPagado;
	}
	public void setMontoPagado(String montoPagado) {
		this.montoPagado = montoPagado;
	}
}
