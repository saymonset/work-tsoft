/**
 * 
 */
package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;


import java.io.File;
import java.util.Arrays;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * @author kode-
 *
 */
@XStreamAlias("AdjuntosDTO")
public class ArchivosAdjuntosEvcoDTO {

	private Long idAdjuntos;
	private Long idEventoCorporativo;
	private File archivo;
	private String descripcion;
	private String borrado;
	private String numAdjunto;
	private boolean borradoLogico;
	private byte[] archivoAdjunto;

	public File getArchivo() {
		return archivo;
	}
	public void setArchivo(File archivo) {
		this.archivo = archivo;
	}
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * @return the borrado
	 */
	public String getBorrado() {
		return borrado;
	}
	/**
	 * @param borrado the borrado to set
	 */
	public void setBorrado(String borrado) {
		this.borrado = borrado;
	}	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ArchivosAdjuntosEvcoDTO [idAdjuntos=" + idAdjuntos
				+ ", idEventoCorporativo=" + idEventoCorporativo
				+ ", descripcion=" + descripcion + ", borrado=" + borrado
				+ ", archivoAdjunto=" + Arrays.toString(archivoAdjunto) + "]";
	}
	public String getNumAdjunto() {
		return numAdjunto;
	}
	public void setNumAdjunto(String numAdjunto) {
		this.numAdjunto = numAdjunto;
	}
	public boolean isBorradoLogico() {
		return borradoLogico;
	}
	public void setBorradoLogico(boolean borradoLogico) {
		this.borradoLogico = borradoLogico;
	}

	public Long getIdAdjuntos() {
		return idAdjuntos;
	}
	
	public void setIdAdjuntos(Long idAdjuntos) {
		this.idAdjuntos = idAdjuntos;
	}

	public Long getIdEventoCorporativo() {
		return idEventoCorporativo;
	}

	public void setIdEventoCorporativo(Long idEventoCorporativo) {
		this.idEventoCorporativo = idEventoCorporativo;
	}

	public byte[] getArchivoAdjunto() {
		return archivoAdjunto;
	}

	public void setArchivoAdjunto(byte[] archivoAdjunto) {
		this.archivoAdjunto = archivoAdjunto;
	}
}
