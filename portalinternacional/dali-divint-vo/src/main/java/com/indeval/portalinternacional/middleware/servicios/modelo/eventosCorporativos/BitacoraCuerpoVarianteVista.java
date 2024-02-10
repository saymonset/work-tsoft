/**
 * 
 */
package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;

import java.util.ArrayList;
import java.util.List;


/**
 * @author kode-
 *
 */
public class BitacoraCuerpoVarianteVista {

	private String piePaginaHtml;
	private String piePaginaText;
	private String cuerpoEventoHtml;
	private String cuerpoEventoText;
	private List<String> notasList= new ArrayList<String>();
	private List<OpcionesEventoCorporativo> opcionesList= new ArrayList<OpcionesEventoCorporativo>();
	private List<ArchivosAdjuntosEvcoDTO> archivosAdjuntos= new ArrayList<ArchivosAdjuntosEvcoDTO>();
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BitacoraCuerpoVarianteVista [piePaginaHtml=" + piePaginaHtml
				+ ", piePaginaText=" + piePaginaText + ", cuerpoEventoHtml="
				+ cuerpoEventoHtml + ", cuerpoEventoText=" + cuerpoEventoText
				+ ", notasList=" + notasList + ", opcionesList=" + opcionesList
				+ ", archivosAdjuntos=" + archivosAdjuntos + "]";
	}
	
}
