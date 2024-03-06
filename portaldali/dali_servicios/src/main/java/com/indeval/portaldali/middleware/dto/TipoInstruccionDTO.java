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
 * Data Transfer Object que representa un tipo de instrucción de liquidación.
 * 
 * @author Emigdio Hernández
 * @version 1.0
 *
 */
public class TipoInstruccionDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Identificador del tipo de instrucción.
	 */
	private Long idTipoInstruccion;
	
	/**
	 * Nombre corto del tipo de instrucción.
	 */
	private String nombreCorto;
	
	/**
	 * Descripción del tipo de instrucción.
	 */
	private String descripcion;
	
	/**
	 * Descripcion corta del tipo de instruccion.
	 */
	private String instruccion;

	public TipoInstruccionDTO() {
		super();
	}

	
	public TipoInstruccionDTO(long idTipoInstruccion) {
		super();
		this.idTipoInstruccion = idTipoInstruccion;
	}


	/**
	 * Obtiene el valor del atributo idTipoInstruccion
	 *
	 * @return el valor del atributo idTipoInstruccion
	 */
	public Long getIdTipoInstruccion() {
		return idTipoInstruccion;
	}

	/**
	 * Establece el valor del atributo idTipoInstruccion
	 *
	 * @param idTipoInstruccion el valor del atributo idTipoInstruccion a establecer.
	 */
	public void setIdTipoInstruccion(Long idTipoInstruccion) {
		this.idTipoInstruccion = idTipoInstruccion;
	}

	/**
	 * Obtiene el valor del atributo nombreCorto
	 *
	 * @return el valor del atributo nombreCorto
	 */
	public String getNombreCorto() {
		return nombreCorto;
	}

	/**
	 * Establece el valor del atributo nombreCorto
	 *
	 * @param nombreCorto el valor del atributo nombreCorto a establecer.
	 */
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
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
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o){
		boolean result = false;
		if(o != null && o instanceof TipoInstruccionDTO){
			if(((TipoInstruccionDTO)o).getIdTipoInstruccion() == null &&
					this.getIdTipoInstruccion() == null){
				return true;
				
			}
			if(this.getIdTipoInstruccion() == null || ((TipoInstruccionDTO)o).getIdTipoInstruccion() == null){
				return false;
			}
			result = ((TipoInstruccionDTO)o).getIdTipoInstruccion().longValue() == 
				this.getIdTipoInstruccion().longValue();
		}else{
			result = super.equals(o);
		}
		return result;
	}

	/**
	 * @return the instruccion
	 */
	public String getInstruccion() {
	    return instruccion;
	}

	/**
	 * @param instruccion the instruccion to set
	 */
	public void setInstruccion(String instruccion) {
	    this.instruccion = instruccion;
	}	
}
