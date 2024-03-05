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
 * C_INSTITUCION_PERFIL
 * 
 * @author Rafael Ibarra
 * @version 1.0
 */
@Entity
@Table(name = "R_INSTITUCION_PERFIL_EMISIONES")
public class InstitucionPerfilEmision implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name ="ID_EMISION")
	private Emision idEmision;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_INSTITUCION_PERFIL", updatable = false, insertable = false)
	private InstitucionPerfil institucionPerfil;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EMISION", updatable = false, insertable = false)
	private Emision emision;

	/**
	 * @param idEmision the idEmision to set
	 */
	public void setIdEmision(Emision idEmision) {
		this.idEmision = idEmision;
	}

	/**
	 * @return the idEmision
	 */
	public Emision getIdEmision() {
		return idEmision;
	}

	/**
	 * @return the institucionPerfil
	 */
	public InstitucionPerfil getInstitucionPerfil() {
		return institucionPerfil;
	}

	/**
	 * @param institucionPerfil
	 *            the institucionPerfil to set
	 */
	public void setInstitucionPerfil(InstitucionPerfil institucionPerfil) {
		this.institucionPerfil = institucionPerfil;
	}

	/**
	 * @return the emision
	 */
	public Emision getEmision() {
		return emision;
	}

	/**
	 * @param emision
	 *            the emision to set
	 */
	public void setEmision(Emision emision) {
		this.emision = emision;
	}

}
