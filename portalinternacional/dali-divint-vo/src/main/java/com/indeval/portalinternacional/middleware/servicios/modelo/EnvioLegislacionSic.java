package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.indeval.portalinternacional.middleware.servicios.vo.EstadoEnvioMoi;


/**
 * Entidad para el control interno de autorizacion de mensajes Legislacion SIC 
 * @author javier.perez
 *
 */

@Entity
@Table(name="T_ENVIO_LEG_SIC")
@SequenceGenerator(name = "foliador", sequenceName = "T_ENVIO_LEG_SIC_SEQ", allocationSize = 1, initialValue = 1)
public class EnvioLegislacionSic implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
    @Column(name ="ID_ENVIO_LEG_SIC", unique = true, nullable = false)
	private Long id;
	
	@Column(name ="USUARIO")
	private String usuario;
	
	@Column(name ="USUARIO_ACTUALIZA")
	private String usuarioAutoriza;
	
	@Column(name ="ESTADO_ENVIO")
	private Integer estadoEnvioMoi;
	
	@Column(name ="FECHA_CREACION")
	private Date fechaCreacion;
	
	@Column(name ="FECHA_ACTUALIZACION")
	private Date fechaActualizacion;
	
	@Column(name ="ID_CALENDARIO_INT")
	private Long idCalendario;
	
	@Column(name ="ID_HIST_DERECHO_INT")
	private Long idHistoricoSic;
	
	@Column(name ="DESTINATARIO")
	private String destinatario;
	
	@Transient
	private String descEstado;

	public EnvioLegislacionSic(Long id, String usuario, String usuarioAutoriza, Integer estadoEnvioMoi,
			Date fechaCreacion, Date fechaActualizacion, Long idCalendario, Long idHistoricoSic, String destinatario) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.usuarioAutoriza = usuarioAutoriza;
		this.estadoEnvioMoi = estadoEnvioMoi;
		this.fechaCreacion = fechaCreacion;
		this.fechaActualizacion = fechaActualizacion;
		this.idCalendario = idCalendario;
		this.idHistoricoSic = idHistoricoSic;
		this.destinatario = destinatario;
	}

	public EnvioLegislacionSic() {
		super();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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

	/**
	 * @return the usuarioAutoriza
	 */
	public String getUsuarioAutoriza() {
		return usuarioAutoriza;
	}

	/**
	 * @param usuarioAutoriza the usuarioAutoriza to set
	 */
	public void setUsuarioAutoriza(String usuarioAutoriza) {
		this.usuarioAutoriza = usuarioAutoriza;
	}

	/**
	 * @return the estadoEnvioMoi
	 */
	public Integer getEstadoEnvioMoi() {
		return estadoEnvioMoi;
	}

	/**
	 * @param estadoEnvioMoi the estadoEnvioMoi to set
	 */
	public void setEstadoEnvioMoi(Integer estadoEnvioMoi) {
		this.estadoEnvioMoi = estadoEnvioMoi;
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
	 * @return the fechaActualizacion
	 */
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	/**
	 * @param fechaActualizacion the fechaActualizacion to set
	 */
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	/**
	 * @return the idCalendario
	 */
	public Long getIdCalendario() {
		return idCalendario;
	}

	/**
	 * @param idCalendario the idCalendario to set
	 */
	public void setIdCalendario(Long idCalendario) {
		this.idCalendario = idCalendario;
	}

	/**
	 * @return the idHistoricoSic
	 */
	public Long getIdHistoricoSic() {
		return idHistoricoSic;
	}

	/**
	 * @param idHistoricoSic the idHistoricoSic to set
	 */
	public void setIdHistoricoSic(Long idHistoricoSic) {
		this.idHistoricoSic = idHistoricoSic;
	}

	/**
	 * @return the destinatario
	 */
	public String getDestinatario() {
		return destinatario;
	}

	/**
	 * @param destinatario the destinatario to set
	 */
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	/**
	 * @return the descEstado
	 */
	public String getDescEstado() {
		return this.estadoEnvioMoi != null ? EstadoEnvioMoi.values()[this.estadoEnvioMoi].getValor() : ""; 		
	}

	/**
	 * @param descEstado the descEstado to set
	 */
	public void setDescEstado(String descEstado) {
		this.descEstado = descEstado;
	}
}
