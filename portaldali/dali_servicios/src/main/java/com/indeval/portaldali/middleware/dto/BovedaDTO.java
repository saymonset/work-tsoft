/**
 * Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * 03/03/2008
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Data Transfer Object que representa un elemento del catalogo de bovedas.
 * 
 * @author
 * 
 */
public class BovedaDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** El identificador de la boveda */
	private long id=0;
	private String descripcion = null;
	private String claveTipoBoveda = null;
	private String nombreCorto = null;
	public static long BOVEDA_BANXICO=11;	
	
	public BovedaDTO( ) { 
	}
	
	public BovedaDTO(long idBoveda) {
		id = idBoveda; 
	}
	public String getNombreCorto() {
		return nombreCorto;
	}
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}
	/**
	 * Obtiene el campo id
	 * @return  id
	 */
	public long getId() {
		return id;
	}
	/**
	 * Asigna el valor del campo id
	 * @param id el valor de id a asignar
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * Obtiene el campo descripcion
	 * @return  descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * Asigna el valor del campo descripcion
	 * @param descripcion el valor de descripcion a asignar
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * Obtiene el campo claveTipoBoveda
	 * @return  claveTipoBoveda
	 */
	public String getClaveTipoBoveda() {
		return claveTipoBoveda;
	}
	/**
	 * Asigna el valor del campo claveTipoBoveda
	 * @param claveTipoBoveda el valor de claveTipoBoveda a asignar
	 */
	public void setClaveTipoBoveda(String claveTipoBoveda) {
		this.claveTipoBoveda = claveTipoBoveda;
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o){
		boolean result = false;
		if(o != null && o instanceof BovedaDTO){
			result = ((BovedaDTO)o).getId() == this.getId();
		}else{
			result = super.equals(o);
		}
		return result;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).
				append("id", id).
				append("nombreCorto", nombreCorto).
				append("claveTipoBoveda", claveTipoBoveda).
				append("descripcion", descripcion).
				toString();
	}

}
