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
 * @author Emigdio Hernández
 * @version 1.0
 * 
 */
public class TipoCuentaDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * Clave utilizada para designar una cuenta controlada
	 */
	public static String CUENTA_CONTROLADA = "C";
	/**
	 * Clave utilizada para designar una cuenta nombrada
	 */
	public static String CUENTA_NOMBRADA = "N";
	
	private String id = null;
	private String descripcion = null;
	/**
	 * Constructor predeterminado
	 */
	public TipoCuentaDTO(){
	/**
	 * Constructor para inicializar los valores de id y descripcion
	 */
	}
	public TipoCuentaDTO(String id, String descripcion){
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
		if(o != null && o instanceof TipoCuentaDTO){
			result = ((TipoCuentaDTO)o).getId().equals( this.getId());
		}else{
			if(o != null && o instanceof String){
				result =  ((String)o).equals( this.getId());
			}
		}
		return result;
	}

}