/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Clase que representa un registro de la tabla 'R_EMISION_BOVEDA'.
 */
@Entity
@Table(name = "R_EMISION_BOVEDA")
public class REmisionBoveda implements Serializable {

	/**
	 * Version de la clase para serializar
	 */
	private static final long serialVersionUID = 13L;

	/**
	 * Propiedad que representa el id del Proceso
	 */
	@Id
	@Column(name = "ID_EMISION", unique = true, nullable = false)
	private Long idEmision;

	@Column(name = "ID_BOVEDA", unique = false, nullable = false)
	private Long idBoveda;

    public Long getIdEmision() {
        return idEmision;
    }

    public void setIdEmision(Long idEmision) {
        this.idEmision = idEmision;
    }

    public Long getIdBoveda() {
        return idBoveda;
    }

    public void setIdBoveda(Long idBoveda) {
        this.idBoveda = idBoveda;
    }

}
