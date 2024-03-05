/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;

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
 * @author rchavez
 * @version 1.0
 */
@Entity
@Table(name = "C_INSTITUCION_PERFILES")
public class InstitucionPerfil implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_INSTITUCION_PERFIL")
	private BigInteger idInstitucionPerfil;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_INSTITUCION")
	private Institucion institucion;

	@Column(name = "ID_PERFIL")
	private BigInteger perfil;

	/**
	 * @return the idInstitucionPerfil
	 */
	public BigInteger getIdInstitucionPerfil() {
		return idInstitucionPerfil;
	}

	/**
	 * @param idInstitucionPerfil
	 *            the idInstitucionPerfil to set
	 */
	public void setIdInstitucionPerfil(BigInteger idInstitucionPerfil) {
		this.idInstitucionPerfil = idInstitucionPerfil;
	}

	/**
	 * @return the institucion
	 */
	public Institucion getInstitucion() {
		return institucion;
	}

	/**
	 * @param institucion
	 *            the institucion to set
	 */
	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	/**
	 * @return the perfil
	 */
	public BigInteger getPerfil() {
		return perfil;
	}

	/**
	 * @param perfil
	 *            the perfil to set
	 */
	public void setPerfil(BigInteger perfil) {
		this.perfil = perfil;
	}

}
