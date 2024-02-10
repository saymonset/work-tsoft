package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "C_SUBTIPOS_DERECHO")
public class SubTipoDerecho  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3370903946248118718L;
	
	private Integer idSubtipoDerecho;
	private String descSubtipoDerecho;
	
	@Id
	@Column(name = "ID_SUBTIPO_DERECHO", unique = true, nullable = false)
	public Integer getIdSubtipoDerecho() {
		return idSubtipoDerecho;
	}
	@Column(name = "DESC_SUBTIPO_DERECHO", unique = false, nullable = false)
	public String getDescSubtipoDerecho() {
		return descSubtipoDerecho;
	}
	public void setIdSubtipoDerecho(Integer idSubtipoDerecho) {
		this.idSubtipoDerecho = idSubtipoDerecho;
	}
	public void setDescSubtipoDerecho(String descSubtipoDerecho) {
		this.descSubtipoDerecho = descSubtipoDerecho;
	}
	
	
}
