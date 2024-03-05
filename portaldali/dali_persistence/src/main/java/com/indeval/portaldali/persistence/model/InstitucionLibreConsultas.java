/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Catalogo que contiene las instituciones a las que no
 * se les debe de cobrar
 * 
 * C_INST_LIBRE_CONSULTAS
 * 
 * @author Rafael Ibarra
 * @version 1.0
 */
@Entity
@Table(name = "C_INST_LIBRE_CONSULTAS")
public class InstitucionLibreConsultas implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador del registro de la institucion libre de consulta
	 */
	@Id
	@Column(name = "ID_COL")
	private Long idCol;
	/**
	 * Institucion libre de consulta.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_INSTITUCION")
	private Institucion institucion;
	/**
	 * @return the idCol
	 */
	public Long getIdCol() {
		return idCol;
	}
	/**
	 * @param idCol the idCol to set
	 */
	public void setIdCol(Long idCol) {
		this.idCol = idCol;
	}
	/**
	 * @return the institucion
	 */
	public Institucion getInstitucion() {
		return institucion;
	}
	/**
	 * @param institucion the institucion to set
	 */
	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}
		
}
