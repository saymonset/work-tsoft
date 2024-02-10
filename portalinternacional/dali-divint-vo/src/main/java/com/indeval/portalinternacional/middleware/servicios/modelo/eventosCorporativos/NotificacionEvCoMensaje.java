package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;

import java.util.Date;



import org.apache.commons.lang.StringUtils;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
@XStreamAlias("Notificacion")

public class NotificacionEvCoMensaje {
	
	public enum AccionNotificacion {AGREGAR("AG"), MODIFICAR("MO"), BORRAR("BO");
	private String tipoAccion;
	/**
	 * constructor
	 * @param tipoAccion
	 */
	private AccionNotificacion(String tipoAccion) {
		this.tipoAccion = tipoAccion;
	}  
	/**
	 * tipo de accion
	 * @return tipo accion
	 */
	public String getAccionNotificacion() {
		return tipoAccion;
	}	
	
}
	
	
	private String cronexpression;
	@XStreamAsAttribute
	private String accion;
	private Date fechaInicio;
	private Date fechaFin;
	
	@XStreamAsAttribute
	private Long idNotificacionEvCo;
	@XStreamAsAttribute
	private Long numNotificacion;
	private String lista;
	private String mensaje;
	
	/**
	 * @return the cronexpression
	 */
	public String getCronexpression() {
		return cronexpression;
	}
	/**
	 * @param cronexpression the cronexpression to set
	 */
	public void setCronexpression(String cronexpression) {
		this.cronexpression = cronexpression;
	}
	/**
	 * @return the accion
	 */
	public String getAccion() {
		return accion;
	}
	/**
	 * @param accion the accion to set
	 */
	public void setAccion(String accion) {
		this.accion = accion;
	}
	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}
	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}
	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	/**
	 * @return the idNotificacionEvCo
	 */
	public Long getIdNotificacionEvCo() {
		return idNotificacionEvCo;
	}
	/**
	 * @param idNotificacionEvCo the idNotificacionEvCo to set
	 */
	public void setIdNotificacionEvCo(Long idNotificacionEvCo) {
		this.idNotificacionEvCo = idNotificacionEvCo;
	}
	/**
	 * @return the numNotificacion
	 */
	public Long getNumNotificacion() {
		return numNotificacion;
	}
	/**
	 * @param numNotificacion the numNotificacion to set
	 */
	public void setNumNotificacion(Long numNotificacion) {
		this.numNotificacion = numNotificacion;
	}
	/**
	 * @return the lista
	 */
	public String getLista() {
		return lista;
	}
	/**
	 * @param lista the lista to set
	 */
	public void setLista(String lista) {
		this.lista = lista;
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
	
	public AccionNotificacion getAccionNotificacion() {
		if(StringUtils.isEmpty(this.accion)) {
			return null;
		}
		if(this.accion.equals(AccionNotificacion.AGREGAR.getAccionNotificacion())) {
			return AccionNotificacion.AGREGAR;
		}
		else if (this.accion.equals(AccionNotificacion.MODIFICAR.getAccionNotificacion())) {
			return AccionNotificacion.MODIFICAR;
		}
		else if (this.accion.equals(AccionNotificacion.BORRAR.getAccionNotificacion())) {
			return AccionNotificacion.BORRAR;
		}else {
			return null;
		}
	}
}
