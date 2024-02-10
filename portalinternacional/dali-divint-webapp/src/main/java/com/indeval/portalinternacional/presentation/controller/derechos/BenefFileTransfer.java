package com.indeval.portalinternacional.presentation.controller.derechos;

import java.util.Date;

public class BenefFileTransfer {

	private String tv;
	private String emisora;
	private String serie;
	private String cupon;
	private Integer subTipo;
	private Integer tipo;
	private Long cantidadTitulos;
	private String uoi;
	private Date fechaCorte;
	private String cuenta;
	private Long idCuenta;
	private Long idDerecho;
	private Long idBeneficiario;
	private String error;
	
	public String getTv() {
		return tv;
	}
	public String getEmisora() {
		return emisora;
	}
	public String getSerie() {
		return serie;
	}
	public String getCupon() {
		return cupon;
	}
	public Integer getSubTipo() {
		return subTipo;
	}
	public Long getCantidadTitulos() {
		return cantidadTitulos;
	}
	public String getUoi() {
		return uoi;
	}
	public Date getFechaCorte() {
		return fechaCorte;
	}
	public String getCuenta() {
		return cuenta;
	}
	public String getError() {
		return error;
	}
	public void setTv(String tv) {
		this.tv = tv;
	}
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public void setCupon(String cupon) {
		this.cupon = cupon;
	}
	public void setSubTipo(Integer subTipo) {
		this.subTipo = subTipo;
	}
	public void setCantidadTitulos(Long cantidadTitulos) {
		this.cantidadTitulos = cantidadTitulos;
	}
	public void setUoi(String uoi) {
		this.uoi = uoi;
	}
	public void setFechaCorte(Date fechaCorte) {
		this.fechaCorte = fechaCorte;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	public void setError(String error) {
		this.error = error;
	}
	public Long getIdCuenta() {
		return idCuenta;
	}
	public void setIdCuenta(Long idCuenta) {
		this.idCuenta = idCuenta;
	}
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	public Long getIdDerecho() {
		return idDerecho;
	}
	public Long getIdBeneficiario() {
		return idBeneficiario;
	}
	public void setIdDerecho(Long idDerecho) {
		this.idDerecho = idDerecho;
	}
	public void setIdBeneficiario(Long idBeneficiario) {
		this.idBeneficiario = idBeneficiario;
	}
}
