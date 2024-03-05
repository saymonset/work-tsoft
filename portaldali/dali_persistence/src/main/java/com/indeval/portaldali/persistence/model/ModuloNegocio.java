/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Este cata&acute;logo contiene los Módulos de Negocio del Nuevo Sistema INDEVAL que
 * pueden emitir instrucciones de liquidación a travs del bus.
 * 
 * C_MODULO_NEGOCIO
 * 
 * @author rchavez
 * @version 1.0
 */
@Entity
@Table(name = "C_MODULO_NEGOCIO")
public class ModuloNegocio implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador del modulo de negocio.
	 */
	@Id
	@Column(name = "ID_MODULO_NEGOCIO")
	private BigInteger idModuloNegocio;
	/**
	 * Clave del modulo de negocio.
	 */
	@Column(name = "CLAVE_MODULO_NEGOCIO")
	private String claveModuloNegocio;
	/**
	 * Descripci&oacute;n del modulo de negocio.
	 */
	@Column(name = "DESCRIPCION")
	private String descripcion;
	/**
	 * Cola de entrada para los mensajes del modulo de negocio.
	 */
	@Column(name = "COLA_ENTRADA")
	private String colaEntrada;

	/**
	 * Identificador secuencial del Módulo de Negocio.
	 * 
	 * @return BigInteger
	 */
	public BigInteger getIdModuloNegocio() {
		return idModuloNegocio;
	}

	/**
	 * Identificador secuencial del Módulo de Negocio.
	 * 
	 * @param idModuloNegocio
	 */
	public void setIdModuloNegocio(BigInteger idModuloNegocio) {
		this.idModuloNegocio = idModuloNegocio;
	}

	/**
	 * Clave del Módulo de Negocio.
	 * 
	 * @return String
	 */
	public String getClaveModuloNegocio() {
		return claveModuloNegocio;
	}

	/**
	 * Clave del Módulo de Negocio.
	 * 
	 * @param claveModuloNegocio
	 */
	public void setClaveModuloNegocio(String claveModuloNegocio) {
		this.claveModuloNegocio = claveModuloNegocio;
	}

	/**
	 * Descripción del Módulo de Negocio.
	 * 
	 * @return String
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Descripción del Módulo de Negocio.
	 * 
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the colaEntrada
	 */
	public String getColaEntrada() {
		return colaEntrada;
	}

	/**
	 * @param colaEntrada
	 *            the colaEntrada to set
	 */
	public void setColaEntrada(String colaEntrada) {
		this.colaEntrada = colaEntrada;
	}
}