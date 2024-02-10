package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;

import java.util.Date;
import java.util.Map;


public class EventoCorporativoAltaDTO {
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
	private String piePaginaHtml;
	private String piePaginaText;
	private String cuerpoEventoHtml;
	private String cuerpoEventoText;
	private boolean isEdicion;
	private Map<String, OpcionesDTO> mapOpciones;
	private Map<String, NotasDTO> mapNotas;
	private Map<String, ArchivosAdjuntosEvcoDTO> mapAdjuntos;
	private Map<String, NotificacionesEvcoDTO> mapNotificaciones;
	private Map<String, ValidacionesEvcoDTO> mapValidaciones;
	private String usuarioAlta;
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
	 * @return the piePaginaHtml
	 */
	public String getPiePaginaHtml() {
		return piePaginaHtml;
	}
	/**
	 * @param piePaginaHtml the piePaginaHtml to set
	 */
	public void setPiePaginaHtml(String piePaginaHtml) {
		this.piePaginaHtml = piePaginaHtml;
	}
	/**
	 * @return the piePaginaText
	 */
	public String getPiePaginaText() {
		return piePaginaText;
	}
	/**
	 * @param piePaginaText the piePaginaText to set
	 */
	public void setPiePaginaText(String piePaginaText) {
		this.piePaginaText = piePaginaText;
	}
	/**
	 * @return the cuerpoEventoHtml
	 */
	public String getCuerpoEventoHtml() {
		return cuerpoEventoHtml;
	}
	/**
	 * @param cuerpoEventoHtml the cuerpoEventoHtml to set
	 */
	public void setCuerpoEventoHtml(String cuerpoEventoHtml) {
		this.cuerpoEventoHtml = cuerpoEventoHtml;
	}
	/**
	 * @return the cuerpoEventoText
	 */
	public String getCuerpoEventoText() {
		return cuerpoEventoText;
	}
	/**
	 * @param cuerpoEventoText the cuerpoEventoText to set
	 */
	public void setCuerpoEventoText(String cuerpoEventoText) {
		this.cuerpoEventoText = cuerpoEventoText;
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
	
	public Map<String, OpcionesDTO> getMapOpciones() {
		return mapOpciones;
	}
	
	public void setMapOpciones(Map<String, OpcionesDTO> mapOpciones) {
		this.mapOpciones = mapOpciones;
	}
	
	public Map<String, NotasDTO> getMapNotas() {
		return mapNotas;
	}
	
	public void setMapNotas(Map<String, NotasDTO> mapNotas) {
		this.mapNotas = mapNotas;
	}
	public Map<String, ArchivosAdjuntosEvcoDTO> getMapAdjuntos() {
		return mapAdjuntos;
	}
	public void setMapAdjuntos(Map<String, ArchivosAdjuntosEvcoDTO> mapAdjuntos) {
		this.mapAdjuntos = mapAdjuntos;
	}
	public Map<String, NotificacionesEvcoDTO> getMapNotificaciones() {
		return mapNotificaciones;
	}
	public void setMapNotificaciones(
			Map<String, NotificacionesEvcoDTO> mapNotificaciones) {
		this.mapNotificaciones = mapNotificaciones;
	}
	/**
	 * @return the usuarioAlta
	 */
	public String getUsuarioAlta() {
		return usuarioAlta;
	}
	/**
	 * @param usuarioAlta the usuarioAlta to set
	 */
	public void setUsuarioAlta(String usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}
	public Map<String, ValidacionesEvcoDTO> getMapValidaciones() {
		return mapValidaciones;
	}
	public void setMapValidaciones(
			Map<String, ValidacionesEvcoDTO> mapValidaciones) {
		this.mapValidaciones = mapValidaciones;
	}
}
