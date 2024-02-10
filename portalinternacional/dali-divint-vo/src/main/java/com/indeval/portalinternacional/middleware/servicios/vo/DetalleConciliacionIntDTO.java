package com.indeval.portalinternacional.middleware.servicios.vo;

import java.util.Date;

public class DetalleConciliacionIntDTO {
	private Long folio;
	private String tipoValor;
	private String emisora;
	private String serie;
	private String isin;
	private Date fechaMensajeInicio;
	private Date fechaMensajeFin;
	private Date fechaConciliacionInicio;
	private Date fechaConciliacionFin;
	private Long custodio;
	private Long boveda;
	private boolean conDiferencia;
	/**
	 * @return the folio
	 */
	public Long getFolio() {
		return folio;
	}
	/**
	 * @param folio the folio to set
	 */
	public void setFolio(Long folio) {
		this.folio = folio;
	}
	/**
	 * @return the tipoValor
	 */
	public String getTipoValor() {
		return tipoValor;
	}
	/**
	 * @param tipoValor the tipoValor to set
	 */
	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
	}
	/**
	 * @return the emisora
	 */
	public String getEmisora() {
		return emisora;
	}
	/**
	 * @param emisora the emisora to set
	 */
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}
	/**
	 * @return the serie
	 */
	public String getSerie() {
		return serie;
	}
	/**
	 * @param serie the serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}
	/**
	 * @return the isin
	 */
	public String getIsin() {
		return isin;
	}
	/**
	 * @param isin the isin to set
	 */
	public void setIsin(String isin) {
		this.isin = isin;
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
	 * @return the custodio
	 */
	public Long getCustodio() {
		return custodio;
	}
	/**
	 * @param custodio the custodio to set
	 */
	public void setCustodio(Long custodio) {
		this.custodio = custodio;
	}
	/**
	 * @return the boveda
	 */
	public Long getBoveda() {
		return boveda;
	}
	/**
	 * @param boveda the boveda to set
	 */
	public void setBoveda(Long boveda) {
		this.boveda = boveda;
	}
	/**
	 * @return the conDiferencia
	 */
	public boolean getConDiferencia() {
		return conDiferencia;
	}
	/**
	 * @param conDiferencia the conDiferencia to set
	 */
	public void setConDiferencia(boolean conDiferencia) {
		this.conDiferencia = conDiferencia;
	}
}
