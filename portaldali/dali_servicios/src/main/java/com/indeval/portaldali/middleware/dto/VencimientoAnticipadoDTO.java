package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.util.Date;

public class VencimientoAnticipadoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long idInstruccionOperacion;
	private Integer plazoVigente;
	private Date fechaVencimientoVigente;
	private Integer plazoSolicitud;
	private Date fechaVencimientoSolicitud;

	private String estatusSolicitud;
	private Long idInstitucionSolicitud;
	private Date fechaSolicitud;
	private String usuarioSolicitud;
	private String ipSolicitud;

	private Long idInstitucionRespuesta;
	private Date fechaRespuesta;
	private String usuarioRespuesta;
	private String ipRespuesta;

	private Date fechaLiquidacion;
	private Long diasTranscurridos;

	public VencimientoAnticipadoDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdInstruccionOperacion() {
		return idInstruccionOperacion;
	}

	public void setIdInstruccionOperacion(Long idInstruccionOperacion) {
		this.idInstruccionOperacion = idInstruccionOperacion;
	}

	public Integer getPlazoVigente() {
		return plazoVigente;
	}

	public void setPlazoVigente(Integer plazoVigente) {
		this.plazoVigente = plazoVigente;
	}

	public Date getFechaVencimientoVigente() {
		return fechaVencimientoVigente;
	}

	public void setFechaVencimientoVigente(Date fechaVencimientoVigente) {
		this.fechaVencimientoVigente = fechaVencimientoVigente;
	}

	public Integer getPlazoSolicitud() {
		return plazoSolicitud;
	}

	public void setPlazoSolicitud(Integer plazoSolicitud) {
		this.plazoSolicitud = plazoSolicitud;
	}

	public Date getFechaVencimientoSolicitud() {
		return fechaVencimientoSolicitud;
	}

	public void setFechaVencimientoSolicitud(Date fechaVencimientoSolicitud) {
		this.fechaVencimientoSolicitud = fechaVencimientoSolicitud;
	}

	public String getEstatusSolicitud() {
		return estatusSolicitud;
	}

	public void setEstatusSolicitud(String estatusSolicitud) {
		this.estatusSolicitud = estatusSolicitud;
	}

	public Long getIdInstitucionSolicitud() {
		return idInstitucionSolicitud;
	}

	public void setIdInstitucionSolicitud(Long idInstitucionSolicitud) {
		this.idInstitucionSolicitud = idInstitucionSolicitud;
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public String getUsuarioSolicitud() {
		return usuarioSolicitud;
	}

	public void setUsuarioSolicitud(String usuarioSolicitud) {
		this.usuarioSolicitud = usuarioSolicitud;
	}

	public String getIpSolicitud() {
		return ipSolicitud;
	}

	public void setIpSolicitud(String ipSolicitud) {
		this.ipSolicitud = ipSolicitud;
	}

	public Long getIdInstitucionRespuesta() {
		return idInstitucionRespuesta;
	}

	public void setIdInstitucionRespuesta(Long idInstitucionRespuesta) {
		this.idInstitucionRespuesta = idInstitucionRespuesta;
	}

	public Date getFechaRespuesta() {
		return fechaRespuesta;
	}

	public void setFechaRespuesta(Date fechaRespuesta) {
		this.fechaRespuesta = fechaRespuesta;
	}

	public String getUsuarioRespuesta() {
		return usuarioRespuesta;
	}

	public void setUsuarioRespuesta(String usuarioRespuesta) {
		this.usuarioRespuesta = usuarioRespuesta;
	}

	public String getIpRespuesta() {
		return ipRespuesta;
	}

	public void setIpRespuesta(String ipRespuesta) {
		this.ipRespuesta = ipRespuesta;
	}

	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	public Long getDiasTranscurridos() {
		return diasTranscurridos;
	}

	public void setDiasTranscurridos(Long diasTranscurridos) {
		this.diasTranscurridos = diasTranscurridos;
	}

	
}
