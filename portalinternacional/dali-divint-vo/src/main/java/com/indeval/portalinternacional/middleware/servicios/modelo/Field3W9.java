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
@Table(name = "C_W9_FIELD3")
public class Field3W9 implements Serializable {
    
	private static final long serialVersionUID = -4221058734991247458L;

	private Long idCampo;

	private String descripcion;

	@Id
	@Column(name = "ID_W9_FIELD3", unique = true, nullable = false)
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
