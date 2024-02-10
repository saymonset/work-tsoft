/**
 * 
 */
package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author kode-
 *
 */
public class AtributosEmbebidosEvcoDto {

	private String piePaginaHtml;
	private String piePaginaText;
	private String cuerpoEventoHtml;
	private String cuerpoEventoText;
	private Date fechaActualizacion;
	private List<String> notasList= new ArrayList<String>();
	private List<OpcionesEventoCorporativo> opcionesList= new ArrayList<OpcionesEventoCorporativo>();
	private List<ArchivosAdjuntosEvcoDTO> archivosAdjuntos= new ArrayList<ArchivosAdjuntosEvcoDTO>();
	private List<NotificacionesEvcoDTO> notificacionesList= new ArrayList<NotificacionesEvcoDTO>();
	private List<ValidacionesEvcoDTO> validacionesList= new ArrayList<ValidacionesEvcoDTO>();
	
	/**
	 * @return the piePaginaHtml
	 */
	public String getPiePaginaHtml() {
		return piePaginaHtml;
	}
	/**
	 * @param piePaginaHtml the piePaginaHtml to set
	 */
	public void setPiePaginaHtml(String piePaginaHtml) {
		this.piePaginaHtml = piePaginaHtml;
	}
	/**
	 * @return the piePaginaText
	 */
	public String getPiePaginaText() {
		return piePaginaText;
	}
	/**
	 * @param piePaginaText the piePaginaText to set
	 */
	public void setPiePaginaText(String piePaginaText) {
		this.piePaginaText = piePaginaText;
	}
	/**
	 * @return the cuerpoEventoHtml
	 */
	public String getCuerpoEventoHtml() {
		return cuerpoEventoHtml;
	}
	/**
	 * @param cuerpoEventoHtml the cuerpoEventoHtml to set
	 */
	public void setCuerpoEventoHtml(String cuerpoEventoHtml) {
		this.cuerpoEventoHtml = cuerpoEventoHtml;
	}
	/**
	 * @return the cuerpoEventoText
	 */
	public String getCuerpoEventoText() {
		return cuerpoEventoText;
	}
	/**
	 * @param cuerpoEventoText the cuerpoEventoText to set
	 */
	public void setCuerpoEventoText(String cuerpoEventoText) {
		this.cuerpoEventoText = cuerpoEventoText;
	}

	/**
	 * @return the notasList
	 */
	public List<String> getNotasList() {
		return notasList;
	}
	/**
	 * @param notasList the notasList to set
	 */
	public void setNotasList(List<String> notasList) {
		this.notasList = notasList;
	}
	/**
	 * @return the opcionesList
	 */
	public List<OpcionesEventoCorporativo> getOpcionesList() {
		return opcionesList;
	}
	/**
	 * @param opcionesList the opcionesList to set
	 */
	public void setOpcionesList(List<OpcionesEventoCorporativo> opcionesList) {
		this.opcionesList = opcionesList;
	}
	/**
	 * @return the archivosAdjuntos
	 */
	public List<ArchivosAdjuntosEvcoDTO> getArchivosAdjuntos() {
		return archivosAdjuntos;
	}
	/**
	 * @param archivosAdjuntos the archivosAdjuntos to set
	 */
	public void setArchivosAdjuntos(List<ArchivosAdjuntosEvcoDTO> archivosAdjuntos) {
		this.archivosAdjuntos = archivosAdjuntos;
	}
	/**
	 * @return the notificacionesList
	 */
	public List<NotificacionesEvcoDTO> getNotificacionesList() {
		return notificacionesList;
	}
	/**
	 * @param notificacionesList the notificacionesList to set
	 */
	public void setNotificacionesList(List<NotificacionesEvcoDTO> notificacionesList) {
		this.notificacionesList = notificacionesList;
	}	

	/**
	 * @return the validacionesList
	 */
	public List<ValidacionesEvcoDTO> getValidacionesList() {
		return validacionesList;
	}
	/**
	 * @param validacionesList the validacionesList to set
	 */
	public void setValidacionesList(List<ValidacionesEvcoDTO> validacionesList) {
		this.validacionesList = validacionesList;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AtributosEmbebidosEvcoDto [piePaginaHtml=" + piePaginaHtml
				+ ", piePaginaText=" + piePaginaText + ", cuerpoEventoHtml="
				+ cuerpoEventoHtml + ", cuerpoEventoText=" + cuerpoEventoText
				+ ", notasList=" + notasList + ", opcionesList=" + opcionesList
				+ ", archivosAdjuntos=" + archivosAdjuntos
				+ ", notificacionesList=" + notificacionesList
				+ ", validacionesList=" + validacionesList + "]";
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

	
}
