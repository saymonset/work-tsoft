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
 * Objeto de transferencia de datos para encapsular la información referente al 
 * catálogo de tipos de operaciones.
 * 
 * @author Emigdio Hernández
 * @version 1.0
 *
 */
public class TipoOperacionDTO implements Serializable{

	/** Default serial version id */
	private static final long serialVersionUID = 1L;
	
	/** Identificador del tipo de operación */
	private long id;
	
	/** Clave de la operación */
	private String claveTipoOperacion;
	
	/** Descripción del tipo de operación */
	private String descripcion;

	/**
	 * Obtiene el valor del atributo id
	 *
	 * @return el valor del atributo id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Establece el valor del atributo id
	 *
	 * @param id el valor del atributo id a establecer.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Obtiene el valor del atributo descripcion
	 *
	 * @return el valor del atributo descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece el valor del atributo descripcion
	 *
	 * @param descripcion el valor del atributo descripcion a establecer.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el valor del atributo claveTipoOperacion
	 * 
	 * @return el valor del atributo claveTipoOperacion
	 */
	public String getClaveTipoOperacion() {
		return claveTipoOperacion;
	}

	/**
	 * Establece el valor del atributo claveTipoOperacion
	 *
	 * @param claveTipoOperacion el valor del atributo claveTipoOperacion a establecer
	 */
	public void setClaveTipoOperacion(String claveTipoOperacion) {
		this.claveTipoOperacion = claveTipoOperacion;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o){
		boolean result = false;
		if(o != null && o instanceof TipoOperacionDTO){
			result = ((TipoOperacionDTO)o).getId() == 
				this.getId();
		}else{
			result = super.equals(o);
		}
		return result;
	}	
	
}
