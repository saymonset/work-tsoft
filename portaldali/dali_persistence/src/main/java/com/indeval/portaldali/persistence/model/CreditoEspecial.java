/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Tabla que indica los Créditos al fideicomiso (T_CREDITO_ESPECIAL).
 * @author RCHAVEZ
 * @version 1.0
 */
@Entity
@Table(name = "T_CREDITO_ESPECIAL")
public class CreditoEspecial implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador de la instituci&oacute;n.
	 */
	@Id
	@Column(name = "ID_INSTITUCION")
	private BigInteger idInstitucion;
	/**
	 * Monto del cr&eacute;dito especial.
	 */
	@Column(name = "CREDITO_ESPECIAL")
	private BigDecimal creditoEspecial;

	/**
	 * Constructor default.
	 */
	public CreditoEspecial() {

	}

	/**
	 * Constructor.
	 * @param idInstitucion
	 * @param creditoEspecial
	 */
	public CreditoEspecial(BigInteger idInstitucion, BigDecimal creditoEspecial) {
		this.idInstitucion = idInstitucion;
		this.creditoEspecial = creditoEspecial;
	}

	/**
	 * Obtiene el identificador de la instituci&oacute;n a la que se le otorga
	 * el cr&eacute;dito especial.
	 * @return the idInstitucion
	 */
	public BigInteger getIdInstitucion() {
		return idInstitucion;
	}
	/**
	 * Asigna el identificador de la instituci&oacute;n a la que se le otorga
	 * el cr&eacute;dito especial.
	 * @param idInstitucion the idInstitucion to set
	 */
	public void setIdInstitucion(BigInteger idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	/**
	 * Obtiene el cr&eacute;dito especial que tiene asignada la
	 * instituci&oacute;n
	 * @return the creditoEspecial
	 */
	public BigDecimal getCreditoEspecial() {
		return creditoEspecial;
	}
	/**
	 * Asigna el monto del cr&eacute;dito especial que tiene asignada la
	 * instituci&oacute;n
	 * @param creditoEspecial the creditoEspecial to set
	 */
	public void setCreditoEspecial(BigDecimal creditoEspecial) {
		this.creditoEspecial = creditoEspecial;
	}
}