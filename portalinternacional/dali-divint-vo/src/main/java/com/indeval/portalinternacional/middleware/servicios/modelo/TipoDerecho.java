package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "C_TIPOS_DERECHO")
public class TipoDerecho  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3676773886840248522L;
	private Integer idTipoDerecho;
	private String descTipoDerecho;
	
	@Id
	@Column(name = "ID_TIPO_DERECHO", unique = true, nullable = false)
	public Integer getIdTipoDerecho() {
		return idTipoDerecho;
	}
	
	@Column(name = "DESC_TIPO_DERECHO", unique = false, nullable = false)
	public String getDescTipoDerecho() {
		return descTipoDerecho;
	}
	
	public void setIdTipoDerecho(Integer idTipoDerecho) {
		this.idTipoDerecho = idTipoDerecho;
	}
		
	public void setDescTipoDerecho(String descTipoDerecho) {
		this.descTipoDerecho = descTipoDerecho;
	}

}
