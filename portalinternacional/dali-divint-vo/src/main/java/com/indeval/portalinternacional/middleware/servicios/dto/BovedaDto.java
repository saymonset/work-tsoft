/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.middleware.servicios.dto;

import java.io.Serializable;

/**
 * Dto que representa una boveda
 */
public class BovedaDto implements Serializable {

	/** Id para la serializacion */
	private static final long serialVersionUID = -8168058611529545458L;
	public static Integer BOVEDA_BANXICO = 11;

	/** Id de la boveda */
    private Integer idBoveda;
	// Cambio Multidivisas
	private String idBovedaStr;
	// Fin Cambio Multidivisas
    
    /** Tipo de boveda */
    private TipoBovedaDto tipoBoveda;
    
    /** Nombre corto de la boveda */
    private String nombreCorto;

    /** Descripcion de la boveda */
    private String descripcion;
    
    /** Id de la cuenta de boveda */
    private Integer idCuentaBoveda;

    /**
     * Constructor de la clase BovedaDto
     */
    public BovedaDto() {}

    /**
     * Constructor de la clase BovedaDto
     * @param idBoveda Id de la boveda
     * @param nombreCorto Nombre corto de la boveda
     * @param descripcion Descripcion de la boveda
     * @param idCuentaBoveda Id de la cuenta de boveda
     */
    public BovedaDto(Integer idBoveda, String nombreCorto, String descripcion, Integer idCuentaBoveda) {
        this.idBoveda = idBoveda;
        this.nombreCorto = nombreCorto;
        this.descripcion = descripcion;
        this.idCuentaBoveda = idCuentaBoveda;
    }

    /**
     * Constructor de la clase BovedaDto
     * @param idBoveda Id de la boveda
     * @param tipoBoveda Tipo de boveda
     * @param nombreCorto Nombre corto de la boveda
     * @param descripcion Descripcion de la boveda
     * @param idCuentaBoveda Id de la cuenta de boveda
     */
    public BovedaDto(Integer idBoveda, TipoBovedaDto tipoBoveda, String nombreCorto, String descripcion, Integer idCuentaBoveda) {
		this.idBoveda = idBoveda;
		this.tipoBoveda = tipoBoveda;
		this.nombreCorto = nombreCorto;
		this.descripcion = descripcion;
		this.idCuentaBoveda = idCuentaBoveda;
	}        
    
	/**
	 * Obtiene el valor del atributo idBoveda.
	 *
	 * @return el valor del atributo idBoveda.
	 */
	public Integer getIdBoveda() {
		// Cambio Multidivisas
		if(idBoveda == null && idBovedaStr != null) {
			idBoveda = Integer.getInteger(idBovedaStr);
		}
		// Fin Cambio Multidivisas
		return idBoveda;
	}

	/**
	 * Establece el valor del atributo idBoveda. 
	 *
	 * @param idBoveda el valor del atributo idBoveda a establecer.
	 */
	public void setIdBoveda(Integer idBoveda) {
		this.idBoveda = idBoveda;
	}

	/**
	 * Obtiene el valor del atributo tipoBoveda.
	 *
	 * @return el valor del atributo tipoBoveda.
	 */
	public TipoBovedaDto getTipoBoveda() {
		return tipoBoveda;
	}

	/**
	 * Establece el valor del atributo tipoBoveda. 
	 *
	 * @param tipoBoveda el valor del atributo tipoBoveda a establecer.
	 */
	public void setTipoBoveda(TipoBovedaDto tipoBoveda) {
		this.tipoBoveda = tipoBoveda;
	}

	/**
	 * Obtiene el valor del atributo nombreCorto.
	 *
	 * @return el valor del atributo nombreCorto.
	 */
	public String getNombreCorto() {
		return nombreCorto;
	}

	/**
	 * Establece el valor del atributo nombreCorto. 
	 *
	 * @param nombreCorto el valor del atributo nombreCorto a establecer.
	 */
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
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

	/**
	 * Obtiene el valor del atributo idCuentaBoveda.
	 *
	 * @return el valor del atributo idCuentaBoveda.
	 */
	public Integer getIdCuentaBoveda() {
		return idCuentaBoveda;
	}

	/**
	 * Establece el valor del atributo idCuentaBoveda. 
	 *
	 * @param idCuentaBoveda el valor del atributo idCuentaBoveda a establecer.
	 */
	public void setIdCuentaBoveda(Integer idCuentaBoveda) {
		this.idCuentaBoveda = idCuentaBoveda;
	}

	// Cambio Multidivisas
	public void setIdBovedaStr(String idBovedaStr) {
		this.idBovedaStr = idBovedaStr;
		if(this.idBovedaStr != null) {
			idBoveda = Integer.parseInt(this.idBovedaStr);
		}
	}

	public String getIdBovedaStr() {
		return idBovedaStr;
	}
	// Fin Cambio Multidivisas
	
}
