/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Clase que representa un registro de la tabla 'R_SIC_CAMBIO_BOVEDA'.
 */
@Entity
@Table(name = "R_SIC_CAMBIO_BOVEDA")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_R_SIC_CAMBIO_BOVEDA", allocationSize = 1, initialValue = 1)
public class RSICCambioBoveda implements Serializable {

	/**
	 * Version de la clase para serializar
	 */
	private static final long serialVersionUID = 10L;

	/**
	 * Propiedad que representa el id del Proceso
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_SIC_CAMBIO_BOVEDA", unique = true, nullable = false)
	private Integer idRSicCambioBoveda;

	@Column(name = "REFERENCIA_OPERACION", unique = false, nullable = false)
	private String referenciaOperacion;

	public RSICCambioBoveda(String ro) {
	    this.referenciaOperacion = ro;
	}

    public Integer getIdRSicCambioBoveda() {
        return idRSicCambioBoveda;
    }

    public void setIdRSicCambioBoveda(Integer idRSicCambioBoveda) {
        this.idRSicCambioBoveda = idRSicCambioBoveda;
    }

    public String getReferenciaOperacion() {
        return referenciaOperacion;
    }

    public void setReferenciaOperacion(String referenciaOperacion) {
        this.referenciaOperacion = referenciaOperacion;
    }

}
