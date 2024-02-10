/*
 * Copyright (c) 2008 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:ivan.kazakov@2hsoftware.com.mx">Ivan Kazakov</a>
 */
@Entity
@Table(name = "C_TOPE_CIRCULANTE")
public class TopeCirculante implements Serializable {
    
	/**
	 * Constante de versi&oacute;n de serializaci&oacute;n 
	 */
	private static final long serialVersionUID = (long)1;
	
	/**
	 * Long 
	 */
	private Long idEmision;
	
	/**
	 * BigDecimal
	 */
	private BigDecimal cantidadTitulos;

	
	/* Accessors */
	
	/**
	 * @return Emision
	 */
	@Id
	@Column(name = "ID_EMISION", nullable = false)
	public Long getIdEmision() {
		return idEmision;
	}

	/**
	 * @return BigDecimal
	 */
	@Column(name ="CANTIDAD_TITULOS", nullable = false)
	public BigDecimal getCantidadTitulos() {
		return cantidadTitulos;
	}

	/* Mutators */
	
	/**
	 * @param emision
	 */
	public void setIdEmision(Long idEmision) {
		this.idEmision = idEmision;
	}

	/**
	 * @param topeCirculante
	 */
	public void setCantidadTitulos(BigDecimal cantidadTitulos) {
		this.cantidadTitulos = cantidadTitulos;
	}
}
