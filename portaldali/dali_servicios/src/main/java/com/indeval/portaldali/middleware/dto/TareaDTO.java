package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.util.Date;

public class TareaDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String idTarea;
	private Date fechaTarea;
	
	private String nombreTarea;
	private String descripcionTarea;
	
	private String aplicacion;
	private String flujoOperativo;
	private String solicitante;
	private String detalle;
	
	private String observaciones;
	public TareaDTO() {
		super();
	}

	public String getIdTarea() {
		return idTarea;
	}

	public void setIdTarea(String idTarea) {
		this.idTarea = idTarea;
	}

	public Date getFechaTarea() {
		return fechaTarea;
	}

	public void setFechaTarea(Date fechaTarea) {
		this.fechaTarea = fechaTarea;
	}

	public String getNombreTarea() {
		return nombreTarea;
	}

	public void setNombreTarea(String nombreTarea) {
		this.nombreTarea = nombreTarea;
	}

	public String getDescripcionTarea() {
		return descripcionTarea;
	}

	public void setDescripcionTarea(String descripcionTarea) {
		this.descripcionTarea = descripcionTarea;
	}

	public String getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	public String getFlujoOperativo() {
		return flujoOperativo;
	}

	public void setFlujoOperativo(String flujoOperativo) {
		this.flujoOperativo = flujoOperativo;
	}

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
}
