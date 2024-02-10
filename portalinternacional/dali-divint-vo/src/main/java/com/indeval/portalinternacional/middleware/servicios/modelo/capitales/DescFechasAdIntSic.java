package com.indeval.portalinternacional.middleware.servicios.modelo.capitales;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the C_DESC_FECHAS_AD_INT_SIC database table.
 * 
 */
@Entity
@Table(name="C_DESC_FECHAS_AD_INT_SIC")
public class DescFechasAdIntSic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private Long id;
	
	@Column(name = "QUALIFIER")
	private String qualifier;
	
	@Column(name = "DESCRIPCION")
	private String descripcion;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the qualifier
	 */
	public String getQualifier() {
		return qualifier;
	}

	/**
	 * @param qualifier the qualifier to set
	 */
	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
