/**
 * 
 */
package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author kode-
 *
 */
@XStreamAlias("NotasDTO")
public class NotasDTO {

	private String cuerpoNota;
	private String numNota;
	private boolean borradoLogico;
	private String idNota;
	private long id;
	private String nombre;
	private String descripcion;
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NotasDTO [id=" + id + ", nombre=" + nombre + ", descripcion="
				+ descripcion + "]";
	}
	
	public String getCuerpoNota() {
		return cuerpoNota;
	}
	
	public void setCuerpoNota(String cuerpoNota) {
		this.cuerpoNota = cuerpoNota;
	}
	
	public String getNumNota() {
		return numNota;
	}
	
	public void setNumNota(String numNota) {
		this.numNota = numNota;
	}
	
	public boolean isBorradoLogico() {
		return borradoLogico;
	}
	
	public void setBorradoLogico(boolean borradoLogico) {
		this.borradoLogico = borradoLogico;
	}
	
	public String getIdNota() {
		return idNota;
	}
	
	public void setIdNota(String idNota) {
		this.idNota = idNota;
	}
}
