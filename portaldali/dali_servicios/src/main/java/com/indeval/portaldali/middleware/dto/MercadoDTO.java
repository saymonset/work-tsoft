/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * 27/02/2008
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;

/**
 * Data Transfer Object que representa un elemento del catálogo de mercados.
 * 
 * @author Emigdio Hernández
 * @version 1.0
 * 
 */
public class MercadoDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** El identificador del mercado */
	private long id = 0;
	
	/** La descripción del mercado */
	private String descripcion = null;
	
	/** Clave del mercado */
	private String claveMercado = null;

	/**
	 * 
	 */
	public MercadoDTO(){}
	
	/**
	 * 
	 * @param id
	 */
	public MercadoDTO(long id) {
		this.id = id;
	}

	/**
	 * 
	 * @param id
	 * @param claveMercado
	 */
	public MercadoDTO(long id, String claveMercado) {
		this.id = id;
		this.claveMercado = claveMercado;
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
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o){
		boolean result = false;
		if(o != null && o instanceof MercadoDTO){
			result = ((MercadoDTO)o).getId() == this.getId();
		}else{
			result = super.equals(o);
		}
		return result;
	}

	

	/**
	 * Obtiene el campo claveMercado
	 * @return  claveMercado
	 */
	public String getClaveMercado() {
		return claveMercado;
	}

	/**
	 * Asigna el campo claveMercado
	 * @param claveMercado el valor de claveMercado a asignar
	 */
	public void setClaveMercado(String claveMercado) {
		this.claveMercado = claveMercado;
	}

	@Override
	public String toString() {
		return "MercadoDTO{" +
				"id=" + id +
				", descripcion='" + descripcion + '\'' +
				", claveMercado='" + claveMercado + '\'' +
				'}';
	}
}