/**
 * 
 */
package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 * @author kode-
 *
 */
public class BitacoraVistaDTO {

	private String idEventoCorporativo;
	private Date fechaCreacion;	
	private Date fechaRegistro;	
	private String tipoValor;
	private String emisora;
	private String serie;
	private String isin;
	private String tipoDerechoMO;
	private String tipoDerechoML;
	private String tipoMercado;
	private String estado;
	private Date fechaIndeval;	
	private Date fechaCliente;	
	private Date fechaPago;	
	private String custodio;	
	private String tipoEvento;	

	private List<BitacoraCuerpoVarianteVista> cuerpoEnVistaLista= new ArrayList<BitacoraCuerpoVarianteVista>();

	private boolean isEdicion;
	/**
	 * @return the idEventoCorporativo
	 */
	public String getIdEventoCorporativo() {
		return idEventoCorporativo;
	}
	/**
	 * @param idEventoCorporativo the idEventoCorporativo to set
	 */
	public void setIdEventoCorporativo(String idEventoCorporativo) {
		this.idEventoCorporativo = idEventoCorporativo;
	}
	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
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
	 * @return the tipoDerechoMO
	 */
	public String getTipoDerechoMO() {
		return tipoDerechoMO;
	}
	/**
	 * @param tipoDerechoMO the tipoDerechoMO to set
	 */
	public void setTipoDerechoMO(String tipoDerechoMO) {
		this.tipoDerechoMO = tipoDerechoMO;
	}
	/**
	 * @return the tipoDerechoML
	 */
	public String getTipoDerechoML() {
		return tipoDerechoML;
	}
	/**
	 * @param tipoDerechoML the tipoDerechoML to set
	 */
	public void setTipoDerechoML(String tipoDerechoML) {
		this.tipoDerechoML = tipoDerechoML;
	}
	/**
	 * @return the tipoMercado
	 */
	public String getTipoMercado() {
		return tipoMercado;
	}
	/**
	 * @param tipoMercado the tipoMercado to set
	 */
	public void setTipoMercado(String tipoMercado) {
		this.tipoMercado = tipoMercado;
	}
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * @return the fechaIndeval
	 */
	public Date getFechaIndeval() {
		return fechaIndeval;
	}
	/**
	 * @param fechaIndeval the fechaIndeval to set
	 */
	public void setFechaIndeval(Date fechaIndeval) {
		this.fechaIndeval = fechaIndeval;
	}
	/**
	 * @return the fechaCliente
	 */
	public Date getFechaCliente() {
		return fechaCliente;
	}
	/**
	 * @param fechaCliente the fechaCliente to set
	 */
	public void setFechaCliente(Date fechaCliente) {
		this.fechaCliente = fechaCliente;
	}
	/**
	 * @return the fechaPago
	 */
	public Date getFechaPago() {
		return fechaPago;
	}
	/**
	 * @param fechaPago the fechaPago to set
	 */
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
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
	 * @return the tipoEvento
	 */
	public String getTipoEvento() {
		return tipoEvento;
	}
	/**
	 * @param tipoEvento the tipoEvento to set
	 */
	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	/**
	 * @return the isEdicion
	 */
	public boolean isEdicion() {
		return isEdicion;
	}
	/**
	 * @param isEdicion the isEdicion to set
	 */
	public void setEdicion(boolean isEdicion) {
		this.isEdicion = isEdicion;
	}
	/**
	 * @return the cuerpoEnVistaLista
	 */
	public List<BitacoraCuerpoVarianteVista> getCuerpoEnVistaLista() {
		return cuerpoEnVistaLista;
	}
	/**
	 * @param cuerpoEnVistaLista the cuerpoEnVistaLista to set
	 */
	public void setCuerpoEnVistaLista(
			List<BitacoraCuerpoVarianteVista> cuerpoEnVistaLista) {
		this.cuerpoEnVistaLista = cuerpoEnVistaLista;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BitacoraVistaDTO [idEventoCorporativo=" + idEventoCorporativo
				+ ", fechaCreacion="
				+ fechaCreacion + ", fechaRegistro=" + fechaRegistro
				+ ", tipoValor=" + tipoValor + ", emisora=" + emisora
				+ ", serie=" + serie + ", isin=" + isin + ", tipoDerechoMO="
				+ tipoDerechoMO + ", tipoDerechoML=" + tipoDerechoML
				+ ", tipoMercado=" + tipoMercado + ", estado=" + estado
				+ ", fechaIndeval=" + fechaIndeval + ", fechaCliente="
				+ fechaCliente + ", fechaPago=" + fechaPago + ", custodio="
				+ custodio + ", tipoEvento=" + tipoEvento
				+ ", cuerpoEnVistaLista=" + cuerpoEnVistaLista + ", isEdicion="
				+ isEdicion + "]";
	}

	
	
}
