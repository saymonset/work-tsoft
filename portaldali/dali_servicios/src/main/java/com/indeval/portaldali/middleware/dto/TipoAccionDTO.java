/**
 * 2H Software SA de CV
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Fecha de creación: Dec 17, 2007
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;

/**
 * Objeto de transferencia de datos para encapsular la información referente al 
 * catálogo de tipos de acciones para las operaciones.
 * 
 * @author Sandra Flores Arrieta
 * @version 1.0
 *
 */
public class TipoAccionDTO implements Serializable {

	/** Default serial version id  */
	private static final long serialVersionUID = 1L;

	/** Identificador del tipo de acción */
	private long id;
	
	/** Descripción del tipo de acción */
	private String descripcion;

	/**
	 * Constructor de la clase.
	 */
	public TipoAccionDTO() {
		super();
	}

	/**
	 * Constructor de la clase
	 * @param id Id del tipo de accion
	 */
	public TipoAccionDTO(long id) {
		super();
		this.id = id;
	}
	
	/**
	 * Constructor de la clase
	 * @param id Id del tipo de accion
	 * @param descripcion Descripcion del tipo de accion.
	 */
	public TipoAccionDTO(long id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}

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
}
