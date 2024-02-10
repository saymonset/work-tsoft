package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "C_MENSAJE_PORTAL")
public class MensajePortal implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador del mensaje.
	 */
	@Id
	@Column(name = "ID_MENSAJE_PORTAL", updatable = false)
	private Integer idMensajePortal;

	/**
	 * Contenido del Mensaje
	 */
	@Column(name = "MENSAJE")
	private String mensaje;
	
	/**
	 * Indica sie l mensaje esta habilitaod o no
	 */
	@Column(name = "HABILITADO")
	private boolean	habilitado;
	
	/**
	 * Fecha qie indica el dia en el que se creo el mensaje
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA")
	private Date fecha;

	/**
	 * @return the idMensajePortal
	 */
	public Integer getIdMensajePortal() {
		return idMensajePortal;
	}

	/**
	 * @param idMensajePortal the idMensajePortal to set
	 */
	public void setIdMensajePortal(Integer idMensajePortal) {
		this.idMensajePortal = idMensajePortal;
	}

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @return the habilitado
	 */
	public boolean isHabilitado() {
		return habilitado;
	}

	/**
	 * @param habilitado the habilitado to set
	 */
	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	

}