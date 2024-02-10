package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="C_ESTATUS_DERECHOS")
public class ElementEstatusDerecho implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 8044166472114049810L;
	
	private Long idStatusDerecho;
    private String descEstatusDerecho;
    
    @Id
    @Column(name ="ID_ESTATUS_DERECHO", unique = true, nullable = false)
	public Long getIdStatusDerecho() {
		return idStatusDerecho;
	}
    
    @Column(name ="DESC_ESTATUS_DERECHO", unique = false, nullable = true)
	public String getDescEstatusDerecho() {
		return descEstatusDerecho;
	}
	public void setIdStatusDerecho(Long idStatusDerecho) {
		this.idStatusDerecho = idStatusDerecho;
	}
	public void setDescEstatusDerecho(String descEstatusDerecho) {
		this.descEstatusDerecho = descEstatusDerecho;
	}

}