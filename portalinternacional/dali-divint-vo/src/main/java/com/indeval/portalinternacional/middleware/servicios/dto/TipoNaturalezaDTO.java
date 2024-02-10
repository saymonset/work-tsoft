// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.servicios.dto;

import java.io.Serializable;

public class TipoNaturalezaDTO implements Serializable{
	
public static String PASIVO = "P";
	
	public static String ACTIVO = "A";
	
	private static final long serialVersionUID = 1L;
	private String id = null;
	private String descripcion = null;
	/**
	 * Constructor predeterminado
	 */
	public TipoNaturalezaDTO(){
		
	}
	/**
	 * Constructor para inicializar los valores del objeto
	 * @param id Identificador a asignar
	 * @param descripcion Descripción asignar
	 */
	public TipoNaturalezaDTO(String id, String descripcion){
		this.id = id;
		this.descripcion = descripcion;
	}
	/**
	 * Obtiene el campo id
	 * @return  id
	 */
	public String getId() {
		return id;
	}
	/**
	 * Asigna el valor del campo id
	 * @param id el valor de id a asignar
	 */
	public void setId(String id) {
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
		if(o != null && o instanceof TipoNaturalezaDTO){
			result = ((TipoNaturalezaDTO)o).getId().equals( this.getId());
		}else{
			if(o != null && o instanceof String){
				result =  ((String)o).equals( this.getId());
			}
		}
		return result;
	}


}
