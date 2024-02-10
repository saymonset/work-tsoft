package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;

public class NotificacionesVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String minutosProgramados;
	private String numeroNotificacion;
	private String destinatario;
	private String textoNotificacion;
	private String fechaInicio;
	private String fechaFin;
	private String hora;
	
	public String getNumeroNotificacion() {
		return numeroNotificacion;
	}

	public void setNumeroNotificacion(String numeroNotificacion) {
		this.numeroNotificacion = numeroNotificacion;
	}

	public String getMinutosProgramados() {
		return minutosProgramados;
	}
	
	public void setMinutosProgramados(String minutosProgramados) {
		this.minutosProgramados = minutosProgramados;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getTextoNotificacion() {
		return textoNotificacion;
	}

	public void setTextoNotificacion(String textoNotificacion) {
		this.textoNotificacion = textoNotificacion;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}
}
