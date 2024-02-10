package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
@XStreamAlias("notificaciones")
public class NotificacionesXml {
	@XStreamAsAttribute
	private Long idEventoCorporativo;
	@XStreamImplicit(itemFieldName="notificacion")
	private List<NotificacionEvCoMensaje> notificaciones;

	/**
	 * @return the notificaciones
	 */
	public List<NotificacionEvCoMensaje> getNotificaciones() {
		return notificaciones;
	}

	/**
	 * @param notificaciones the notificaciones to set
	 */
	public void setNotificaciones(List<NotificacionEvCoMensaje> notificaciones) {
		this.notificaciones = notificaciones;
	}

	/**
	 * @return the idEventoCorporativo
	 */
	public Long getIdEventoCorporativo() {
		return idEventoCorporativo;
	}

	/**
	 * @param idEventoCorporativo the idEventoCorporativo to set
	 */
	public void setIdEventoCorporativo(Long idEventoCorporativo) {
		this.idEventoCorporativo = idEventoCorporativo;
	}
}
