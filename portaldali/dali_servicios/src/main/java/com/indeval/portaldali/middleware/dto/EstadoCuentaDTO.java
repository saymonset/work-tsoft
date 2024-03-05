/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;


/**
 * Cata&acute;logo con los estados que puede adquirir una cuenta durante su ciclo de 
 * vida. Se decidi no llamar a esta tabla ESTADO_CUENTA, pues podra ocasionar
 * confusiones y llevar a creer que contiene datos relativos a una entidad Estado 
 * de Cuenta.
 * 
 * C_CUENTA_ESTADO
 *
 * @author FERNANDO VAZQUEZ ULLOA
 * @version 1.0
 */
 
 
public class EstadoCuentaDTO implements Serializable {
    
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador del estado de la cuenta.
	 */
	
	
	private Long idCuentaEstado;
	/**
	 * Clave para el estado de la cuenta.
	 */
	
    private String claveCuentaEstado;
	/**
	 * Descripci&oacute;n del estado de la cuenta.
	 */
	
    private String descripcion;

	/**
	 * Identificador secuencial del Estado de la Cuenta.
	 * 
	 */
	public Long getIdCuentaEstado() {
        return idCuentaEstado;
    }

    /**
     * 
     */
	public void setIdCuentaEstado(Long idCuentaEstado) {
        this.idCuentaEstado = idCuentaEstado;
    }

    /**
	 * Clave de identificación para el Estado de la Cuenta.
	 * 
	 */
	public String getClaveCuentaEstado() {
		return claveCuentaEstado;
	}

	/**
     * 
     */
    public void setClaveCuentaEstado(String claveCuentaEstado) {
        this.claveCuentaEstado = claveCuentaEstado;
    }

    /**
	 * Descripción del Estado de la Cuenta.
	 * 
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Descripción del Estado de la Cuenta
     * 
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
