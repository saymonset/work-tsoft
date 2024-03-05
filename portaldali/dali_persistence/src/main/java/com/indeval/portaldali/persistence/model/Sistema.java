/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Catalogo de sistemas de la seguridad
 * 
 * SEGU_SISTEMA
 * 
 * @author Rafael Ibarra
 * @version 1.0
 */
@Entity
@Table(name = "SEGU_SISTEMA")
public class Sistema implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador del sistema
	 */
	@Id
	@Column(name = "ID_SISTEMA")
	private Long idSistema;
	/**
	 * Nombre de la consulta
	 */
	@Column(name = "DESCRIPCION")
	private String descripcion;
	/**
	 * Nombre de la consulta
	 */
	@Column(name = "NOMBRE")
	private String nombre;
	/**
	 * @return the idSistema
	 */
	public Long getIdSistema() {
		return idSistema;
	}
	/**
	 * @param idSistema the idSistema to set
	 */
	public void setIdSistema(Long idSistema) {
		this.idSistema = idSistema;
	}
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
