package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="C_ESTATUS_DERECHOS_DALI")
public class EstatusDerecho implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8254306886890791836L;
	
	private Integer idEstatusDerecho;
	private String descEstatusDerecho;
	
    @Id
    @Column(name ="id_estatus_derecho", unique = true, nullable = false)
	public Integer getIdEstatusDerecho() {
		return idEstatusDerecho;
	}	
    @Column(name ="desc_estatus_derecho", unique = false, nullable = true)
	public String getDescEstatusDerecho() {
		return descEstatusDerecho;
	}
	public void setIdEstatusDerecho(Integer idEstatusDerecho) {
		this.idEstatusDerecho = idEstatusDerecho;
	}
	public void setDescEstatusDerecho(String descEstatusDerecho) {
		this.descEstatusDerecho = descEstatusDerecho;
	}
	
	

}
