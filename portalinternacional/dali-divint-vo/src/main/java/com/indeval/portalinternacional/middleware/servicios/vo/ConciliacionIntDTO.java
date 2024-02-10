package com.indeval.portalinternacional.middleware.servicios.vo;

import java.util.Date;

public class ConciliacionIntDTO {
	private String folio;
	private Date fechaMensajeInicio;
	private Date fechaMensajeFin;
	private Integer custodio;
	private Integer bovedaDali;
	private Date fechaConciliacionInicio;
	private Date fechaConciliacionFin;
	private boolean isDifDali;
	private boolean porConc;
	private boolean isDifPos;
	private boolean isDifCust;
	private Boolean conciliacionNacional;
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
	/**
	 * @return the fechaMensajeInicio
	 */
	public Date getFechaMensajeInicio() {
		return fechaMensajeInicio;
	}
	/**
	 * @param fechaMensajeInicio the fechaMensajeInicio to set
	 */
	public void setFechaMensajeInicio(Date fechaMensajeInicio) {
		this.fechaMensajeInicio = fechaMensajeInicio;
	}
	/**
	 * @return the fechaMensajeFin
	 */
	public Date getFechaMensajeFin() {
		return fechaMensajeFin;
	}
	/**
	 * @param fechaMensajeFin the fechaMensajeFin to set
	 */
	public void setFechaMensajeFin(Date fechaMensajeFin) {
		this.fechaMensajeFin = fechaMensajeFin;
	}
	/**
	 * @return the custodio
	 */
	public Integer getCustodio() {
		return custodio;
	}
	/**
	 * @param custodio the custodio to set
	 */
	public void setCustodio(Integer custodio) {
		this.custodio = custodio;
	}
	/**
	 * @return the bovedaDali
	 */
	public Integer getBovedaDali() {
		return bovedaDali;
	}
	/**
	 * @param bovedaDali the bovedaDali to set
	 */
	public void setBovedaDali(Integer bovedaDali) {
		this.bovedaDali = bovedaDali;
	}
	/**
	 * @return the fechaConciliacionInicio
	 */
	public Date getFechaConciliacionInicio() {
		return fechaConciliacionInicio;
	}
	/**
	 * @param fechaConciliacionInicio the fechaConciliacionInicio to set
	 */
	public void setFechaConciliacionInicio(Date fechaConciliacionInicio) {
		this.fechaConciliacionInicio = fechaConciliacionInicio;
	}
	/**
	 * @return the fechaConciliacionFin
	 */
	public Date getFechaConciliacionFin() {
		return fechaConciliacionFin;
	}
	/**
	 * @param fechaConciliacionFin the fechaConciliacionFin to set
	 */
	public void setFechaConciliacionFin(Date fechaConciliacionFin) {
		this.fechaConciliacionFin = fechaConciliacionFin;
	}
	/**
	 * @return the isDifDali
	 */
	public boolean isDifDali() {
		return isDifDali;
	}
	/**
	 * @param isDifDali the isDifDali to set
	 */
	public void setDifDali(boolean isDifDali) {
		this.isDifDali = isDifDali;
	}
	/**
	 * @return the porConc
	 */
	public boolean isPorConc() {
		return porConc;
	}
	/**
	 * @param porConc the porConc to set
	 */
	public void setPorConc(boolean porConc) {
		this.porConc = porConc;
	}
	/**
	 * @return the isDifPos
	 */
	public boolean isDifPos() {
		return isDifPos;
	}
	/**
	 * @param isDifPos the isDifPos to set
	 */
	public void setDifPos(boolean isDifPos) {
		this.isDifPos = isDifPos;
	}
	/**
	 * @return the isDifCust
	 */
	public boolean isDifCust() {
		return isDifCust;
	}
	/**
	 * @param isDifCust the isDifCust to set
	 */
	public void setDifCust(boolean isDifCust) {
		this.isDifCust = isDifCust;
	}
	/**
	 * @return the conciliacionNacional
	 */
	public Boolean getConciliacionNacional() {
		return conciliacionNacional;
	}
	/**
	 * @param conciliacionNacional the conciliacionNacional to set
	 */
	public void setConciliacionNacional(Boolean conciliacionNacional) {
		this.conciliacionNacional = conciliacionNacional;
	}

}
