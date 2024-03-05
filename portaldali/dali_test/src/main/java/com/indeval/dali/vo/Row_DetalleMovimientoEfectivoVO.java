package com.indeval.dali.vo;

import java.math.BigDecimal;

public class Row_DetalleMovimientoEfectivoVO extends Row_DetalleMovimientoVO{

	
	private String contraparte;
	
	private BigDecimal abono;
	
	private BigDecimal cargo;
	
	private BigDecimal noDisponible;
	
	private BigDecimal saldo;
	
	
		
	private BigDecimal cobro;
	
	private BigDecimal pago;
	
	private String tipoDR;
	
	private String plazo;
	
	private String tasa;
	
	private String emision;
	
	private String mercado;
	
	private Long cantidad;
	
	private BigDecimal precio;
	
	private String cuentaReceptor;
	
	private String cuentaTraspasante;

	public String getContraparte() {
		return contraparte;
	}

	public void setContraparte(String contraparte) {
		this.contraparte = contraparte;
	}

	public void setAbono(BigDecimal abono) {
		this.abono = abono;
	}
	
	public BigDecimal getAbono() {
		return abono;
	}
	
	public BigDecimal getCobro() {
		return cobro;
	}

	public void setCobro(BigDecimal cobro) {
		this.cobro = cobro;
	}

	public BigDecimal getPago() {
		return pago;
	}

	public void setPago(BigDecimal pago) {
		this.pago = pago;
	}

	public String getTipoDR() {
		return tipoDR;
	}

	public void setTipoDR(String tipoDR) {
		this.tipoDR = tipoDR;
	}

	public String getPlazo() {
		return plazo;
	}

	public void setPlazo(String plazo) {
		this.plazo = plazo;
	}

	public String getTasa() {
		return tasa;
	}

	public void setTasa(String tasa) {
		this.tasa = tasa;
	}

	public String getEmision() {
		return emision;
	}

	public void setEmision(String emision) {
		this.emision = emision;
	}

	public String getMercado() {
		return mercado;
	}

	public void setMercado(String mercado) {
		this.mercado = mercado;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public String getCuentaReceptor() {
		return cuentaReceptor;
	}

	public void setCuentaReceptor(String cuentaReceptor) {
		this.cuentaReceptor = cuentaReceptor;
	}

	public String getCuentaTraspasante() {
		return cuentaTraspasante;
	}

	public void setCuentaTraspasante(String cuentaTraspasante) {
		this.cuentaTraspasante = cuentaTraspasante;
	}

	public BigDecimal getCargo() {
		return cargo;
	}

	public void setCargo(BigDecimal cargo) {
		this.cargo = cargo;
	}

	public BigDecimal getNoDisponible() {
		return noDisponible;
	}

	public void setNoDisponible(BigDecimal noDisponible) {
		this.noDisponible = noDisponible;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	
	
	
	
}
