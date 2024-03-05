/*
 *Copyright (c) 2009 Bursatec. All Rights Reserved 
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;

/**
 * Data Transfer Object que representa un estado de cuenta
 *
 * @author Maria C. Buendia
 * @version 1.0
 */
public class EstadosCuentaDTO implements Serializable{
	
	/** Default serial version */
	private static final long serialVersionUID = 1L;
	
	/** Id asignado para cada estado */
	private long id;
	
	/** Descripcion del estado que corresponde al id */
	private String descripcion;

	public EstadosCuentaDTO(){}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
