package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ArchivosAdjuntosEvcoDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.NotasDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.NotificacionesEvcoDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.OpcionesDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ValidacionesEvcoDTO;

public class EventoCorporativoEdicionDTO implements Serializable {

	private static final long serialVersionUID = 1L;
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
	private String cuerpoEventoHtml;
	private List<OpcionesDTO> listOpciones;
	private List<NotasDTO> listNotas;
	private List<ArchivosAdjuntosEvcoDTO> listAdjuntos;
	private List<NotificacionesEvcoDTO> listNotificaciones;
	private List<NotificacionesEvcoDTO> listaNotificaciones;
	private List<ValidacionesEvcoDTO> listValidaciones;
	private List<ArchivosAdjuntosEvcoDTO> listaAdjuntos;
	private String strFechaRegistro;
	private String opcAccordion;
	private String usuario;
	
	public String getIdEventoCorporativo() {
		return idEventoCorporativo;
	}
	
	public void setIdEventoCorporativo(String idEventoCorporativo) {
		this.idEventoCorporativo = idEventoCorporativo;
	}
	
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	public String getTipoValor() {
		return tipoValor;
	}
	
	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
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
	
	public String getIsin() {
		return isin;
	}
	
	public void setIsin(String isin) {
		this.isin = isin;
	}
	
	public String getTipoDerechoMO() {
		return tipoDerechoMO;
	}
	
	public void setTipoDerechoMO(String tipoDerechoMO) {
		this.tipoDerechoMO = tipoDerechoMO;
	}
	
	public String getTipoDerechoML() {
		return tipoDerechoML;
	}
	
	public void setTipoDerechoML(String tipoDerechoML) {
		this.tipoDerechoML = tipoDerechoML;
	}
	
	public String getTipoMercado() {
		return tipoMercado;
	}
	
	public void setTipoMercado(String tipoMercado) {
		this.tipoMercado = tipoMercado;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public Date getFechaIndeval() {
		return fechaIndeval;
	}
	
	public void setFechaIndeval(Date fechaIndeval) {
		this.fechaIndeval = fechaIndeval;
	}
	
	public Date getFechaCliente() {
		return fechaCliente;
	}
	
	public void setFechaCliente(Date fechaCliente) {
		this.fechaCliente = fechaCliente;
	}
	
	public Date getFechaPago() {
		return fechaPago;
	}
	
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
	
	public String getCustodio() {
		return custodio;
	}
	
	public void setCustodio(String custodio) {
		this.custodio = custodio;
	}
	
	public String getTipoEvento() {
		return tipoEvento;
	}
	
	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	
	public String getPiePaginaHtml() {
		return piePaginaHtml;
	}
	
	public void setPiePaginaHtml(String piePaginaHtml) {
		this.piePaginaHtml = piePaginaHtml;
	}
	
	public String getCuerpoEventoHtml() {
		return cuerpoEventoHtml;
	}
	
	public void setCuerpoEventoHtml(String cuerpoEventoHtml) {
		this.cuerpoEventoHtml = cuerpoEventoHtml;
	}
	
	public List<OpcionesDTO> getListOpciones() {
		return listOpciones;
	}
	
	public void setListOpciones(List<OpcionesDTO> listOpciones) {
		this.listOpciones = listOpciones;
	}
	
	public List<NotasDTO> getListNotas() {
		return listNotas;
	}
	
	public void setListNotas(List<NotasDTO> listNotas) {
		this.listNotas = listNotas;
	}
	
	public List<ArchivosAdjuntosEvcoDTO> getListAdjuntos() {
		return listAdjuntos;
	}
	
	public void setListAdjuntos(List<ArchivosAdjuntosEvcoDTO> listAdjuntos) {
		this.listAdjuntos = listAdjuntos;
	}
	
	public String getStrFechaRegistro() {
		return strFechaRegistro;
	}

	public void setStrFechaRegistro(String strFechaRegistro) {
		this.strFechaRegistro = strFechaRegistro;
	}

	public List<NotificacionesEvcoDTO> getListNotificaciones() {
		return listNotificaciones;
	}

	public void setListNotificaciones(List<NotificacionesEvcoDTO> listNotificaciones) {
		this.listNotificaciones = listNotificaciones;
	}

	public List<ValidacionesEvcoDTO> getListValidaciones() {
		return listValidaciones;
	}

	public void setListValidaciones(List<ValidacionesEvcoDTO> listValidaciones) {
		this.listValidaciones = listValidaciones;
	}

	public String getOpcAccordion() {
		return opcAccordion;
	}

	public void setOpcAccordion(String opcAccordion) {
		this.opcAccordion = opcAccordion;
	}

	public List<NotificacionesEvcoDTO> getListaNotificaciones() {
		return listaNotificaciones;
	}

	public void setListaNotificaciones(
			List<NotificacionesEvcoDTO> listaNotificaciones) {
		this.listaNotificaciones = listaNotificaciones;
	}

	public List<ArchivosAdjuntosEvcoDTO> getListaAdjuntos() {
		return listaAdjuntos;
	}

	public void setListaAdjuntos(List<ArchivosAdjuntosEvcoDTO> listaAdjuntos) {
		this.listaAdjuntos = listaAdjuntos;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
}
