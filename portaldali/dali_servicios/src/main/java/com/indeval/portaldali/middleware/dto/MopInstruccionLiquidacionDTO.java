package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.util.Date;

public class MopInstruccionLiquidacionDTO  implements Serializable, Comparable<MopInstruccionLiquidacionDTO>{
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 8266458959158019457L;
	private Long idInstruccion;
	private String folio;
	private String folioUsuario;
	private String traspasante;
	private String receptor;
	private String tv;
	private String emisora;
	private String serie;
	private String cupon;
	private String cantidad;
	private String importe;
	private String estatus;
	private Boolean cancelada;
	private Date fechaLiquidacion;
	private String emisorTraspasante;
	private String emisorReceptor;
	private Integer numeroOperacion;
	
	/**
	 * Comparador
	 */
	public int compareTo(MopInstruccionLiquidacionDTO mopObj) {
		int resultado = -1;
		if(numeroOperacion != null && mopObj != null && mopObj.getNumeroOperacion() != null)
		{
			resultado = numeroOperacion.compareTo(mopObj.getNumeroOperacion());
		}
		return resultado;
	}
	
	/**
	 * @return the idInstruccion
	 */
	public Long getIdInstruccion() {
		return idInstruccion;
	}
	/**
	 * @param idInstruccion the idInstruccion to set
	 */
	public void setIdInstruccion(Long idInstruccion) {
		this.idInstruccion = idInstruccion;
	}
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
	 * @return the folioUsuario
	 */
	public String getFolioUsuario() {
		return folioUsuario;
	}
	/**
	 * @param folioUsuario the folioUsuario to set
	 */
	public void setFolioUsuario(String folioUsuario) {
		this.folioUsuario = folioUsuario;
	}
	/**
	 * @return the traspasante
	 */
	public String getTraspasante() {
		return traspasante;
	}
	/**
	 * @param traspasante the traspasante to set
	 */
	public void setTraspasante(String traspasante) {
		this.traspasante = traspasante;
	}
	/**
	 * @return the receptor
	 */
	public String getReceptor() {
		return receptor;
	}
	/**
	 * @param receptor the receptor to set
	 */
	public void setReceptor(String receptor) {
		this.receptor = receptor;
	}
	/**
	 * @return the tv
	 */
	public String getTv() {
		return tv;
	}
	/**
	 * @param tv the tv to set
	 */
	public void setTv(String tv) {
		this.tv = tv;
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
	 * @return the cupon
	 */
	public String getCupon() {
		return cupon;
	}
	/**
	 * @param cupon the cupon to set
	 */
	public void setCupon(String cupon) {
		this.cupon = cupon;
	}
	/**
	 * @return the cantidad
	 */
	public String getCantidad() {
		return cantidad;
	}
	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	/**
	 * @return the importe
	 */
	public String getImporte() {
		return importe;
	}
	/**
	 * @param importe the importe to set
	 */
	public void setImporte(String importe) {
		this.importe = importe;
	}
	/**
	 * @return the estatus
	 */
	public String getEstatus() {
		return estatus;
	}
	/**
	 * @param estatus the estatus to set
	 */
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	/**
	 * @return the cancelada
	 */
	public Boolean getCancelada() {
		return cancelada;
	}
	/**
	 * @param cancelada the cancelada to set
	 */
	public void setCancelada(Boolean cancelada) {
		this.cancelada = cancelada;
	}
	/**
	 * @return the fechaLiquidacion
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}
	/**
	 * @param fechaLiquidacion the fechaLiquidacion to set
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}
	/**
	 * @return the emisorTraspasante
	 */
	public String getEmisorTraspasante() {
		return emisorTraspasante;
	}
	/**
	 * @param emisorTraspasante the emisorTraspasante to set
	 */
	public void setEmisorTraspasante(String emisorTraspasante) {
		this.emisorTraspasante = emisorTraspasante;
	}
	/**
	 * @return the emisorReceptor
	 */
	public String getEmisorReceptor() {
		return emisorReceptor;
	}
	/**
	 * @param emisorReceptor the emisorReceptor to set
	 */
	public void setEmisorReceptor(String emisorReceptor) {
		this.emisorReceptor = emisorReceptor;
	}
	/**
	 * @return the numeroOperacion
	 */
	public Integer getNumeroOperacion() {
		return numeroOperacion;
	}
	/**
	 * @param numeroOperacion the numeroOperacion to set
	 */
	public void setNumeroOperacion(Integer numeroOperacion) {
		this.numeroOperacion = numeroOperacion;
	}
	
	
}
