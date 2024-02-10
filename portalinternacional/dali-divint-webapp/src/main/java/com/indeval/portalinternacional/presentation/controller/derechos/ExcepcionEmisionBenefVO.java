package com.indeval.portalinternacional.presentation.controller.derechos;

import com.indeval.portalinternacional.middleware.servicios.modelo.ExcepcionEmisionBenef;

public class ExcepcionEmisionBenefVO{

	private Long idExcepcionEmision;
	private Long idCuentaNombrada;
	private String tv;
	private String emisora;
	private String serie;
	private String isin;
	private Boolean eliminado;
	
	public Long getIdExcepcionEmision() {
		return idExcepcionEmision;
	}
	public Long getIdCuentaNombrada() {
		return idCuentaNombrada;
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
	public String getIsin() {
		return isin;
	}
	public void setIdExcepcionEmision(Long idExcepcionEmision) {
		this.idExcepcionEmision = idExcepcionEmision;
	}
	public void setIdCuentaNombrada(Long idCuentaNombrada) {
		this.idCuentaNombrada = idCuentaNombrada;
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
	public void setIsin(String isin) {
		this.isin = isin;
	}
	public Boolean getEliminado() {
		return eliminado;
	}
	public void setEliminado(Boolean eliminado) {
		this.eliminado = eliminado;
	}
	
	public void transform(ExcepcionEmisionBenef emisionBenef){
		setEmisora(emisionBenef.getEmisora());
		setIdCuentaNombrada(emisionBenef.getIdCuentaNombrada());
		setIdExcepcionEmision(emisionBenef.getIdExcepcionEmision());
		setIsin(emisionBenef.getIsin());
		setSerie(emisionBenef.getSerie());
		setTv(emisionBenef.getTv());
		setEliminado(Boolean.FALSE);
	}
}
