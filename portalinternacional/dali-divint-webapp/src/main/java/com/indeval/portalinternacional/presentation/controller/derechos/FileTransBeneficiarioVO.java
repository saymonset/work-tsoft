package com.indeval.portalinternacional.presentation.controller.derechos;

import java.util.Date;

import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransBeneficiario;

public class FileTransBeneficiarioVO {

	private String folio;
	private String tv;
	private String emisora;
	private String serie;
	private String cupon;
	private Integer tipoDerecho;
	private Integer subtipoDerecho;
	private Long cantidadTitulos;
	private String uoi;
	private Date fechaCorte;
    private String fechaCorteStr;
	private String cuenta;
	private Date fechaRegistro;
	private String error;
	private String linea;
	private Integer consecCampoError;
	private Long idCuenta;
	private Long idDerecho;
	private Long idBeneficiario;
	
	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}
	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}
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
	public Integer getTipoDerecho() {
		return tipoDerecho;
	}
	public Integer getSubtipoDerecho() {
		return subtipoDerecho;
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
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public String getError() {
		return error;
	}
	public String getLinea() {
		return linea;
	}
	public Integer getConsecCampoError() {
		return consecCampoError;
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
	public void setTipoDerecho(Integer tipoDerecho) {
		this.tipoDerecho = tipoDerecho;
	}
	public void setSubtipoDerecho(Integer subtipoDerecho) {
		this.subtipoDerecho = subtipoDerecho;
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
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public void setError(String error) {
		this.error = error;
	}
	public void setLinea(String linea) {
		this.linea = linea;
	}
	public void setConsecCampoError(Integer consecCampoError) {
		this.consecCampoError = consecCampoError;
	}
	
	public void transform(FileTransBeneficiario fileTransBeneficiario){
		this.tv = fileTransBeneficiario.getTv();
		this.emisora = fileTransBeneficiario.getEmisora();
		this.serie = fileTransBeneficiario.getSerie();
		this.cupon = fileTransBeneficiario.getCupon();
		this.tipoDerecho = fileTransBeneficiario.getTipoDerecho();
		this.subtipoDerecho = fileTransBeneficiario.getSubtipoDerecho();
		this.cantidadTitulos = fileTransBeneficiario.getCantidadTitulos();
		this.uoi = fileTransBeneficiario.getUoi();
		this.fechaCorte = fileTransBeneficiario.getFechaCorte();
		this.cuenta = fileTransBeneficiario.getCuenta();
		this.fechaRegistro = fileTransBeneficiario.getFechaRegistro();
		this.error = fileTransBeneficiario.getError();
		this.linea = fileTransBeneficiario.getLinea();
		this.consecCampoError = fileTransBeneficiario.getConsecCampoError();
	}
	public Long getIdCuenta() {
		return idCuenta;
	}
	public Long getIdDerecho() {
		return idDerecho;
	}
	public Long getIdBeneficiario() {
		return idBeneficiario;
	}
	public void setIdCuenta(Long idCuenta) {
		this.idCuenta = idCuenta;
	}
	public void setIdDerecho(Long idDerecho) {
		this.idDerecho = idDerecho;
	}
	public void setIdBeneficiario(Long idBeneficiario) {
		this.idBeneficiario = idBeneficiario;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FileTransBeneficiarioVO [folio=" + folio + ", tv=" + tv + ", emisora=" + emisora + ", serie=" + serie
				+ ", cupon=" + cupon + ", tipoDerecho=" + tipoDerecho + ", subtipoDerecho=" + subtipoDerecho
				+ ", cantidadTitulos=" + cantidadTitulos + ", uoi=" + uoi + ", fechaCorte=" + fechaCorte + ", cuenta="
				+ cuenta + ", fechaRegistro=" + fechaRegistro + ", error=" + error + ", linea=" + linea
				+ ", consecCampoError=" + consecCampoError + ", idCuenta=" + idCuenta + ", idDerecho=" + idDerecho
				+ ", idBeneficiario=" + idBeneficiario + "]";
	}

    public String getFechaCorteStr() {
        return fechaCorteStr;
    }

    public void setFechaCorteStr(String fechaCorteStr) {
        this.fechaCorteStr = fechaCorteStr;
    }

}
