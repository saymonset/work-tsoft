package com.indeval.portalinternacional.middleware.servicios.vo;

import java.util.Date;
import java.util.List;

public class ConsultaEmisionesCalendarioParamsTO {
      
	  private Long idEmision;
	  private String tipoValor;
	  private String emisora;
	  private String serie;
	  private String isin;
	  private String razonSocial;
	  private String estatusRegistro;
	  private String detalleCustodio;
	  private String custodio;
	  private Long custodioInt;
	  private Date fechaAltaInicio;
	  private Date fechaAltaFin;
	  private Date fechaBajaInicio;
	  private Date fechaBajaFin;
	  private Date fechaModificacionInicio;
	  private Date fechaModificacionFin;
	  private String paisOrigen;
	  private String estatusEmision;
	  private String listada;
      private String assetManager;
      private String estatusSistema;
      List<String> tipoValorPermitidos;
	 
      
	/**
	 * @return the idEmision
	 */
	public Long getIdEmision() {
		return idEmision;
	}
	/**
	 * @param idEmision the idEmision to set
	 */
	public void setIdEmision(Long idEmision) {
		this.idEmision = idEmision;
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
	 * @return the razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}
	/**
	 * @param nombre the razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	/**
	 * @return the custodio
	 */
	public String getCustodio() {
		return custodio;
	}
	/**
	 * @param custodio the custodio to set
	 */
	public void setCustodio(String custodio) {
		this.custodio = custodio;
	}
	
	/**
	 * @return the custodioInt
	 */
	public Long getCustodioInt() {
		return custodioInt;
	}
	/**
	 * @param custodioInt the custodioInt to set
	 */
	public void setCustodioInt(Long custodioInt) {
		this.custodioInt = custodioInt;
	}
	/**
	 * @return the fechaAltaInicio
	 */
	public Date getFechaAltaInicio() {
		return fechaAltaInicio;
	}
	/**
	 * @param fechaAltaInicio the fechaAltaInicio to set
	 */
	public void setFechaAltaInicio(Date fechaAltaInicio) {
		this.fechaAltaInicio = fechaAltaInicio;
	}
	/**
	 * @return the fechaAltaFin
	 */
	public Date getFechaAltaFin() {
		return fechaAltaFin;
	}
	/**
	 * @param fechaAltaFin the fechaAltaFin to set
	 */
	public void setFechaAltaFin(Date fechaAltaFin) {
		this.fechaAltaFin = fechaAltaFin;
	}
	/**
	 * @return the fechaBajaInicio
	 */
	public Date getFechaBajaInicio() {
		return fechaBajaInicio;
	}
	/**
	 * @param fechaBajaInicio the fechaBajaInicio to set
	 */
	public void setFechaBajaInicio(Date fechaBajaInicio) {
		this.fechaBajaInicio = fechaBajaInicio;
	}
	/**
	 * @return the fechaBajaFin
	 */
	public Date getFechaBajaFin() {
		return fechaBajaFin;
	}
	/**
	 * @param fechaBajaFin the fechaBajaFin to set
	 */
	public void setFechaBajaFin(Date fechaBajaFin) {
		this.fechaBajaFin = fechaBajaFin;
	}
	
	/**
	 * @return the fechaModificacionInicio
	 */
	public Date getFechaModificacionInicio() {
		return fechaModificacionInicio;
	}
	/**
	 * @param fechaModificacionInicio the fechaModificacionInicio to set
	 */
	public void setFechaModificacionInicio(Date fechaModificacionInicio) {
		this.fechaModificacionInicio = fechaModificacionInicio;
	}
	/**
	 * @return the fechaModificacionFin
	 */
	public Date getFechaModificacionFin() {
		return fechaModificacionFin;
	}
	/**
	 * @param fechaModificacionFin the fechaModificacionFin to set
	 */
	public void setFechaModificacionFin(Date fechaModificacionFin) {
		this.fechaModificacionFin = fechaModificacionFin;
	}
	/**
	 * @return the paisOrigen
	 */
	public String getPaisOrigen() {
		return paisOrigen;
	}
	/**
	 * @param paisOrigen the paisOrigen to set
	 */
	public void setPaisOrigen(String paisOrigen) {
		this.paisOrigen = paisOrigen;
	}
	
	/**
	 * @return the estatusEmision
	 */
	public String getEstatusEmision() {
		return estatusEmision;
	}
	/**
	 * @param estatusEmision the estatusEmision to set
	 */
	public void setEstatusEmision(String estatusEmision) {
		this.estatusEmision = estatusEmision;
	}
	/**
	 * @return the listada
	 */
	public String getListada() {
		return listada;
	}
	/**
	 * @param listada the listada to set
	 */
	public void setListada(String listada) {
		this.listada = listada;
	}
	
	/**
	 * @return the estatusSistema
	 */
	public String getEstatusSistema() {
		return estatusSistema;
	}
	/**
	 * @param estatusSistema the estatusSistema to set
	 */
	public void setEstatusSistema(String estatusSistema) {
		this.estatusSistema = estatusSistema;
	}
	/**
	 * @return the estatusRegistro
	 */
	public String getEstatusRegistro() {
		return estatusRegistro;
	}
	/**
	 * @param estatusRegistro the estatusRegistro to set
	 */
	public void setEstatusRegistro(String estatusRegistro) {
		this.estatusRegistro = estatusRegistro;
	}
	/**
	 * @return the detalleCustodio
	 */
	public String getDetalleCustodio() {
		return detalleCustodio;
	}
	/**
	 * @param detalleCustodio the detalleCustodio to set
	 */
	public void setDetalleCustodio(String detalleCustodio) {
		this.detalleCustodio = detalleCustodio;
	}
	/**
	 * @return the assetManager
	 */
	public String getAssetManager() {
		return assetManager;
	}
	/**
	 * @param assetManager the assetManager to set
	 */
	public void setAssetManager(String assetManager) {
		this.assetManager = assetManager;
	}
	/**
	 * @return the tipoValorPermitidos
	 */
	public List<String> getTipoValorPermitidos() {
		return tipoValorPermitidos;
	}
	/**
	 * @param tipoValorPermitidos the tipoValorPermitidos to set
	 */
	public void setTipoValorPermitidos(List<String> tipoValorPermitidos) {
		this.tipoValorPermitidos = tipoValorPermitidos;
	}
	
}
