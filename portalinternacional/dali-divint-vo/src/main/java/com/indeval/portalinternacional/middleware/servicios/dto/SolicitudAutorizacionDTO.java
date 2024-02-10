package com.indeval.portalinternacional.middleware.servicios.dto;

import java.io.Serializable;
import java.util.Date;

public class SolicitudAutorizacionDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long idSolicitud;
	private String claveFlujoOperativo;
	private Long idProcesoAutorizacion;
	private String descripcion;
	private String identificadorNegocio;
	private String valorAnterior;
	private String valorNuevo;
	private String estatus;
	private String claveUsuario;
	private Date fechaSolicitud;
	private Date fechaRespuesta;

	public Long getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public String getClaveFlujoOperativo() {
		return claveFlujoOperativo;
	}

	public void setClaveFlujoOperativo(String claveFlujoOperativo) {
		this.claveFlujoOperativo = claveFlujoOperativo;
	}

	public Long getIdProcesoAutorizacion() {
		return idProcesoAutorizacion;
	}

	public void setIdProcesoAutorizacion(Long idProcesoAutorizacion) {
		this.idProcesoAutorizacion = idProcesoAutorizacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIdentificadorNegocio() {
		return identificadorNegocio;
	}

	public void setIdentificadorNegocio(String identificadorNegocio) {
		this.identificadorNegocio = identificadorNegocio;
	}

	public String getValorAnterior() {
		return valorAnterior;
	}

	public void setValorAnterior(String valorAnterior) {
		this.valorAnterior = valorAnterior;
	}

	public String getValorNuevo() {
		return valorNuevo;
	}

	public void setValorNuevo(String valorNuevo) {
		this.valorNuevo = valorNuevo;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getClaveUsuario() {
		return claveUsuario;
	}

	public void setClaveUsuario(String claveUsuario) {
		this.claveUsuario = claveUsuario;
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public Date getFechaRespuesta() {
		return fechaRespuesta;
	}

	public void setFechaRespuesta(Date fechaRespuesta) {
		this.fechaRespuesta = fechaRespuesta;
	}
}
