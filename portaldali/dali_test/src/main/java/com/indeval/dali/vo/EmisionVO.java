package com.indeval.dali.vo;

import java.math.BigDecimal;

public class EmisionVO {
	private Integer idEmision;
	private String tv;
	private String emisora;
	private String serie;
	private String cupon;
	private String cuenta;
	private BigDecimal posicionDisponible;
	private String boveda;
	
	public String getTv() {
		return tv;
	}
	public void setTv(String tv) {
		this.tv = tv;
	}
	public String getEmisora() {
		return emisora;
	}
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public String getCupon() {
		return cupon;
	}
	public void setCupon(String cupon) {
		this.cupon = cupon;
	}
	public Integer getIdEmision() {
		return idEmision;
	}
	public void setIdEmision(Integer idEmision) {
		this.idEmision = idEmision;
	}
	
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	public String getCuenta() {
		return cuenta;
	}
	public void setPosicionDisponible(BigDecimal posicionDisponible) {
		this.posicionDisponible = posicionDisponible;
	}
	public BigDecimal getPosicionDisponible() {
		return posicionDisponible;
	}
	
	public void setBoveda(String boveda) {
		this.boveda = boveda;
	}
	
	public String getBoveda() {
		return boveda;
	}
	
}
