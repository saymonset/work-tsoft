package com.indeval.portalinternacional.middleware.servicios.modelo.makercheker;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "T_SOLICITUD_AUTORIZACION")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_SOLICITUD_AUTORIZACION", allocationSize = 1, initialValue = 1)
@NamedQuery(name = "SolicitudAutorizacion.actualizaEstatus", query = "UPDATE SolicitudAutorizacion SET estatus = :estatus, fechaRespuesta = :fechaRespuesta WHERE idSolicitud = :idSolicitud")
public class SolicitudAutorizacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
    @Column(name = "id_solicitud", unique = true, nullable = false)
	private Long idSolicitud;
	
	@Column(name = "clave_flujo_operativo", unique = false, nullable = true)
	private String claveFlujoOperativo;
	
	@Column(name = "id_proceso_autorizacion", unique = false, nullable = true)
	private Long idProcesoAutorizacion;
	
	@Column(name = "descripcion", unique = false, nullable = true)
	private String descripcion;
	
	@Column(name = "identificador_negocio", unique = false, nullable = true)
	private String identificadorNegocio;
	
	@Column(name = "valor_anterior", unique = false, nullable = true)
	private String valorAnterior;
	
	@Column(name = "valor_nuevo", unique = false, nullable = true)
	private String valorNuevo;
	
	@Column(name = "estatus", unique = false, nullable = false)
	private String estatus;
	
	@Column(name = "clave_usuario", unique = false, nullable = false)
	private String claveUsuario;
	
	@Column(name = "fecha_solicitud", unique = false, nullable = false)
	private Date fechaSolicitud;
	
	@Column(name = "fecha_respuesta", unique = false, nullable = true)
	private Date fechaRespuesta;

	public Long getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public String getClaveUsuario() {
		return claveUsuario;
	}

	public void setClaveUsuario(String claveUsuario) {
		this.claveUsuario = claveUsuario;
	}

	public Date getFechaRespuesta() {
		return fechaRespuesta;
	}

	public void setFechaRespuesta(Date fechaRespuesta) {
		this.fechaRespuesta = fechaRespuesta;
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
	
	@Override
	public String toString() {
		return "SolicitudAutorizacion [idSolicitud=" + idSolicitud + ", claveFlujoOperativo=" + claveFlujoOperativo
				+ ", idProcesoAutorizacion=" + idProcesoAutorizacion + ", descripcion=" + descripcion
				+ ", identificadorNegocio=" + identificadorNegocio + ", valorAnterior=" + valorAnterior
				+ ", valorNuevo=" + valorNuevo + ", estatus=" + estatus + ", claveUsuario=" + claveUsuario
				+ ", fechaSolicitud=" + fechaSolicitud + ", fechaRespuesta=" + fechaRespuesta + "]";
	}
}