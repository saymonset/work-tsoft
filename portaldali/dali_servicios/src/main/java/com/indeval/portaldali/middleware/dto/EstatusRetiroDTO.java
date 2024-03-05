/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigInteger;



/**
 * DTO del Estatus Retiro C_ESTATUS_RETIRO
 * @author fernando vazquez ulloa 2009-1109
 * 
 * 
 */



public class EstatusRetiroDTO implements Serializable {
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Id asignado para cada estado */
	
	
	
	private BigInteger idEstatusRetiro;
	
	/** Descripcion del estado que corresponde al id */
	
	private String descripcion;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigInteger getIdEstatusRetiro() {
		return idEstatusRetiro;
	}

	public void setIdEstatusRetiro(BigInteger idEstatusRetiro) {
		this.idEstatusRetiro = idEstatusRetiro;
	}

}
