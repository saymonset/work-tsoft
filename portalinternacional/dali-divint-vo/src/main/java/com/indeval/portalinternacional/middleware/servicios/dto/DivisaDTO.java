/**
 * Bursatec - INDEVAL
 * Portal DALI
 * 29/11/2021
 */
package com.indeval.portalinternacional.middleware.servicios.dto;

import java.io.Serializable;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Data Transfer Object que representa una divisa
 * @author 
 * 
 */
public class DivisaDTO implements Serializable{
	
	/**
	 * Default serial version
	 */
	private static final long serialVersionUID = 1L;
	
	public static long DIVISA_PESO_MX=1;
	
	
	/** Id de la divisa */
    private long id ;
    
    private String idString;
    
    /** Clave alfabetica de la divisa */
    private String claveAlfabetica = null;
    /** Clave numerica de la divisa */
    private String claveNumerica = null;
    /** Descripcion de la divisa */
    private String descripcion = null;
    /**
     * Constructor default
     */    
    public DivisaDTO(){
    	
    }
    /**
     * Constructor para crear una divisa dado su ID
     * @param idDivisa
     */
	public DivisaDTO(long idDivisa) {
		id = idDivisa;
	}
	/**
	 * Obtiene el atributo id
	 *
	 * @return El atrubuto id
	 */
	public long getId() {
		return id;
	}
	/**
	 * Establece la propiedad id
	 *
	 * @param id el campo id a establecer
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * Obtiene el atributo claveAlfabetica
	 *
	 * @return El atrubuto claveAlfabetica
	 */
	public String getClaveAlfabetica() {
		return claveAlfabetica;
	}
	/**
	 * Establece la propiedad claveAlfabetica
	 *
	 * @param claveAlfabetica el campo claveAlfabetica a establecer
	 */
	public void setClaveAlfabetica(String claveAlfabetica) {
		this.claveAlfabetica = claveAlfabetica;
	}
	/**
	 * Obtiene el atributo claveNumerica
	 *
	 * @return El atrubuto claveNumerica
	 */
	public String getClaveNumerica() {
		return claveNumerica;
	}
	/**
	 * Establece la propiedad claveNumerica
	 *
	 * @param claveNumerica el campo claveNumerica a establecer
	 */
	public void setClaveNumerica(String claveNumerica) {
		this.claveNumerica = claveNumerica;
	}
	/**
	 * Obtiene el atributo descripcion
	 *
	 * @return El atrubuto descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * Establece la propiedad descripcion
	 *
	 * @param descripcion el campo descripcion a establecer
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public void setIdString(String idString) {
		this.idString = idString;
		if(!StringUtils.isEmpty(idString)) {
			id = Long.parseLong(idString);
		}
	}
	
	public String getIdString() {
		return idString;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o){
		boolean result = false;
		if(o != null && o instanceof DivisaDTO){
			result = ((DivisaDTO)o).getId() == this.getId();
		}else{
			result = super.equals(o);
		}
		return result;
	}	

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id)
				.append("claveAlfabetica", claveAlfabetica)
				.append("claveNumerica", claveNumerica)
				.append("descripcion", descripcion).toString();
	}

}
