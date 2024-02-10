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
 * 
 * @author Arzate-Jacinto A.
 *
 */
@Entity
@Table(name = "C_ESTATUS_CONCILIACION_INT")
@SequenceGenerator(name = "foliador", sequenceName = "C_ESTATUS_CONC_INT_ID_SEQ", allocationSize = 1, initialValue = 1)
public class EstatusConciliacionInt implements Serializable{

	private static final long serialVersionUID = -2198929005144500134L;

	private Long id;
	private String descripcion;
	
	
	public EstatusConciliacionInt() {
		super();
	}
	
	public EstatusConciliacionInt(Long id, String descripcion) {
		super();
		this.id = id;
		this.setDescripcion(descripcion);
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
    @Column(name = "ID_ESTATUS_CONCILIACION_INT")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="DESCRIPCION")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}