package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "T_VENCIMIENTO_ANTICIPADO")
@SequenceGenerator(name = "secuencia", sequenceName = "SEQ_VENCIMIENTO_ANTICIPADO", allocationSize = 1, initialValue = 1)
@NamedQueries({
	@NamedQuery(name = "VencimientoAnticipado.findByInstruccionAndStatus",
			query = " FROM VencimientoAnticipado vto" +
					" WHERE vto.idInstruccionOperacion = :idInstruccionOperacion" +
					"   AND vto.estatusSolicitud = :estatusSolicitud")
})
public class VencimientoAnticipado implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final String ESTATUS_PENDIENTE  = "PE";
	public static final String ESTATUS_CANCELADA  = "CA";
	public static final String ESTATUS_AUTORIZADA = "AU";
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secuencia")
    @Column(name = "ID_VENCIMIENTO_ANTICIPADO")
	private Long id;
	
	@Column(name="ID_INSTRUCCION_OPERACION")
	private Long idInstruccionOperacion;
	
	@Column(name = "PLAZO_VIGENTE")
	private Integer plazoVigente;
	@Column(name = "FECHA_VENCIMIENTO_VIGENTE")
	private Date fechaVencimientoVigente;
	@Column(name = "PLAZO_SOLICITUD")
	private Integer plazoSolicitud;
	@Column(name = "FECHA_VENCIMIENTO_SOLICITUD")
	private Date fechaVencimientoSolicitud;
	@Column(name = "FECHA_VIGENCIA_SOLICITUD")
	private Date fechaVigenciaSolicitud;
	
	@Column(name="ESTATUS_SOLICITUD")
	private String estatusSolicitud;
	@Column(name = "ID_INSTITUCION_SOLICITUD")
	private Long  idInstitucionSolicitud;
	@Column(name = "FECHA_HORA_SOLICITUD")
	private Date fechaSolicitud;
	@Column(name = "USUARIO_SOLICITUD")
	private String usuarioSolicitud;
	@Column(name = "IP_SOLICITUD")
	private String ipSolicitud;
	@Column(name = "FIRMA_SOLICITUD")
	private String firmaSolicitud;
	
	@Column(name = "ID_INSTITUCION_RESPUESTA", updatable=true)
	private Long idInstitucionRespuesta;
	@Column(name = "FECHA_HORA_RESPUESTA", updatable=true)
	private Date fechaRespuesta;
	@Column(name = "USUARIO_RESPUESTA", updatable=true)
	private String usuarioRespuesta;
	@Column(name = "IP_RESPUESTA", updatable=true)
	private String ipRespuesta;
	@Column(name = "FIRMA_RESPUESTA", updatable=true)
	private String firmaRespuesta;
	
	public VencimientoAnticipado() {
	}
	
	public VencimientoAnticipado(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	
	public Long getIdInstruccionOperacion() {
		return idInstruccionOperacion;
	}

	public void setIdInstruccionOperacion(Long idInstruccionOperacion) {
		this.idInstruccionOperacion = idInstruccionOperacion;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPlazoVigente() {
		return plazoVigente;
	}

	public void setPlazoVigente(Integer plazoVigente) {
		this.plazoVigente = plazoVigente;
	}

	public Integer getPlazoSolicitud() {
		return plazoSolicitud;
	}

	public void setPlazoSolicitud(Integer plazoSolicitud) {
		this.plazoSolicitud = plazoSolicitud;
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

	public String getEstatusSolicitud() {
		return estatusSolicitud;
	}

	public void setEstatusSolicitud(String estatusSolicitud) {
		this.estatusSolicitud = estatusSolicitud;
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

	public Date getFechaVencimientoVigente() {
		return fechaVencimientoVigente;
	}

	public void setFechaVencimientoVigente(Date fechaVencimientoVigente) {
		this.fechaVencimientoVigente = fechaVencimientoVigente;
	}

	public Date getFechaVencimientoSolicitud() {
		return fechaVencimientoSolicitud;
	}

	public void setFechaVencimientoSolicitud(Date fechaVencimientoSolicitud) {
		this.fechaVencimientoSolicitud = fechaVencimientoSolicitud;
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

	public Long getIdInstitucionSolicitud() {
		return idInstitucionSolicitud;
	}

	public void setIdInstitucionSolicitud(Long idInstitucionSolicitud) {
		this.idInstitucionSolicitud = idInstitucionSolicitud;
	}

	public Long getIdInstitucionRespuesta() {
		return idInstitucionRespuesta;
	}

	public void setIdInstitucionRespuesta(Long idInstitucionRespuesta) {
		this.idInstitucionRespuesta = idInstitucionRespuesta;
	}

	public Date getFechaVigenciaSolicitud() {
		return fechaVigenciaSolicitud;
	}

	public void setFechaVigenciaSolicitud(Date fechaVigenciaSolicitud) {
		this.fechaVigenciaSolicitud = fechaVigenciaSolicitud;
	}

	public String getFirmaSolicitud() {
		return firmaSolicitud;
	}

	public void setFirmaSolicitud(String firmaSolicitud) {
		this.firmaSolicitud = firmaSolicitud;
	}

	public String getFirmaRespuesta() {
		return firmaRespuesta;
	}

	public void setFirmaRespuesta(String firmaRespuesta) {
		this.firmaRespuesta = firmaRespuesta;
	}
	
}
