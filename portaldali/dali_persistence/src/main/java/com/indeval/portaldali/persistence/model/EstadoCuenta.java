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
 * Cata&acute;logo con los estados que puede adquirir una cuenta durante su ciclo de 
 * vida. Se decidi no llamar a esta tabla ESTADO_CUENTA, pues podra ocasionar
 * confusiones y llevar a creer que contiene datos relativos a una entidad Estado 
 * de Cuenta.
 * 
 * C_CUENTA_ESTADO
 *
 * @author rchavez
 * @version 1.0
 */
 @Entity
 @Table(name="C_CUENTA_ESTADO")
public class EstadoCuenta implements Serializable {
    
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador del estado de la cuenta.
	 */
	@Id	
	@Column(name = "ID_CUENTA_ESTADO")
	private Long idCuentaEstado;
	/**
	 * Clave para el estado de la cuenta.
	 */
	@Column(name = "CLAVE_CUENTA_ESTADO")
    private String claveCuentaEstado;
	/**
	 * Descripci&oacute;n del estado de la cuenta.
	 */
	@Column(name = "DESCRIPCION")
    private String descripcion;

	/**
	 * Identificador secuencial del Estado de la Cuenta.
	 * @return long
	 */
	public Long getIdCuentaEstado() {
        return idCuentaEstado;
    }

    /**
     * @param idCuentaEstado
     */
	public void setIdCuentaEstado(Long idCuentaEstado) {
        this.idCuentaEstado = idCuentaEstado;
    }

    /**
	 * Clave de identificación para el Estado de la Cuenta.
	 * @return String
	 */
	public String getClaveCuentaEstado() {
		return claveCuentaEstado;
	}

	/**
     * @param claveCuentaEstado
     */
    public void setClaveCuentaEstado(String claveCuentaEstado) {
        this.claveCuentaEstado = claveCuentaEstado;
    }

    /**
	 * Descripción del Estado de la Cuenta.
	 * @return String
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Descripción del Estado de la Cuenta
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
