/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.middleware.servicios.dto;

import java.io.Serializable;

/**
 * Dto que representa el tipo de boveda
 */
public class TipoBovedaDto implements Serializable {

	/** Id para la serializacion */
	private static final long serialVersionUID = -4775147606468288896L;

	/** Id del tipo de boveda */
    private Integer idTipoBoveda;

    /** Clave del tipo de boveda */
    private String clave;
    
    /** Ubicacion del tipo de boveda */
    private String ubicacion;
    
    /** Descripcion del tipo de boveda */
    private String descripcion;

    /**
     * Constructor de la clase TipoBovedaDto
     */
    public TipoBovedaDto() {}
    
    /**
     * Constructor de la clase TipoBovedaDto
     * @param idTipoBoveda Id de tipo de boveda
     * @param clave Clave del tipo de boveda
     * @param ubicacion Ubicacion del tipo de boveda
     * @param descripcion Descripcion de tipo de boveda
     */
    public TipoBovedaDto(Integer idTipoBoveda, String clave, String ubicacion, String descripcion) {
    	this.idTipoBoveda = idTipoBoveda;
    	this.clave = clave;
    	this.ubicacion = ubicacion;
    	this.descripcion = descripcion;
    }
    
	/**
	 * Obtiene el valor del atributo idTipoBoveda.
	 *
	 * @return el valor del atributo idTipoBoveda.
	 */
	public Integer getIdTipoBoveda() {
		return idTipoBoveda;
	}

	/**
	 * Establece el valor del atributo idTipoBoveda. 
	 *
	 * @param idTipoBoveda el valor del atributo idTipoBoveda a establecer.
	 */
	public void setIdTipoBoveda(Integer idTipoBoveda) {
		this.idTipoBoveda = idTipoBoveda;
	}

	/**
	 * Obtiene el valor del atributo clave.
	 *
	 * @return el valor del atributo clave.
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * Establece el valor del atributo clave. 
	 *
	 * @param clave el valor del atributo clave a establecer.
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}

	/**
	 * Obtiene el valor del atributo ubicacion.
	 *
	 * @return el valor del atributo ubicacion.
	 */
	public String getUbicacion() {
		return ubicacion;
	}

	/**
	 * Establece el valor del atributo ubicacion. 
	 *
	 * @param ubicacion el valor del atributo ubicacion a establecer.
	 */
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	/**
	 * Obtiene el valor del atributo descripcion.
	 *
	 * @return el valor del atributo descripcion.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece el valor del atributo descripcion. 
	 *
	 * @param descripcion el valor del atributo descripcion a establecer.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}	
	
}
