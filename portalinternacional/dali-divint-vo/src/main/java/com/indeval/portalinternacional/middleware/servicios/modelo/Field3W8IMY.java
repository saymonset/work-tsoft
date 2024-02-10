/*
 * Copyrigth (c) 2008 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
@Entity
@Table(name = "C_W8IMY_FIELD3")
public class Field3W8IMY implements Serializable {
    
	private static final long serialVersionUID = 7813860899268604741L;

	private Long idCampo;

	private String descripcion;

	@Id
	@Column(name = "ID_W8IMY_FIELD3", unique = true, nullable = false)
	public Long getIdCampo() {
		return idCampo;
	}

	@Column(name = "DESCRIPCION", unique = false, nullable = false)
	public String getDescripcion() {
		return descripcion;
	}

	public void setIdCampo(Long idCampo) {
		this.idCampo = idCampo;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
