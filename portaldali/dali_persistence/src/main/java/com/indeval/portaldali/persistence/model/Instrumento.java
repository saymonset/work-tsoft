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
 * Cata&acute;logo de los Tipos de Valor que se pueden operar, asociados a las
 * emisiones.
 * 
 * C_INSTRUMENTO
 * 
 * @author rchavez
 * @version 1.0
 */
@Entity
@Table(name = "C_INSTRUMENTO")
public class Instrumento implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador del instrumento.
	 */
	@Id
	@Column(name = "ID_INSTRUMENTO")
	private BigInteger idInstrumento;
	/**
	 * Clave del tipo de instrumento.
	 */
	@Column(name = "CLAVE_TIPO_VALOR")
	private String claveTipoValor;
	/**
	 * Clave CFI del instrumento.
	 */
	@Column(name = "CLAVE_CFI")
	private String claveCfi;
	/**
	 * Descripci&oacute;n del instrumento.
	 */
	@Column(name = "DESCRIPCION")
	private String descripcion;
	/**
	 * Descripci&oacute;n en ingles del instrumento.
	 */
	@Column(name = "DESCRIPCION_INGLES")
	private String descripcionIngles;

	/**
	 * Mercado en que opera el instrumento.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_MERCADO", updatable = false, insertable = false)
	private Mercado mercado;

	/**
	 * Identificador del mercado en que opera el instrumento.
	 */
	@Column(name = "ID_MERCADO")
	private BigInteger idMercado;

	/**
	 * Identificador secuencial para el Instrumento.
	 * 
	 * @return BigInteger
	 */
	public BigInteger getIdInstrumento() {
		return idInstrumento;
	}

	/**
	 * Identificador secuencial para el Instrumento.
	 * 
	 * @param idInstrumento
	 */
	public void setIdInstrumento(BigInteger idInstrumento) {
		this.idInstrumento = idInstrumento;
	}

	/**
	 * Representa la clave del tipo de valor
	 * 
	 * @return String
	 */
	public String getClaveTipoValor() {
		return claveTipoValor;
	}

	/**
	 * Representa la clave del tipo de valor
	 * 
	 * @param claveTipoValor
	 */
	public void setClaveTipoValor(String claveTipoValor) {
		this.claveTipoValor = claveTipoValor;
	}

	/**
	 * Clasificación del instrumento de acuerdo al cata&acute;logo de tipos de
	 * instrumentos ISO-10962/CFI
	 * 
	 * @return String
	 */
	public String getClaveCfi() {
		return claveCfi;
	}

	/**
	 * Clasificación del instrumento de acuerdo al cata&acute;logo de tipos de
	 * instrumentos ISO-10962/CFI
	 * 
	 * @param claveCfi
	 */
	public void setClaveCfi(String claveCfi) {
		this.claveCfi = claveCfi;
	}

	/**
	 * Descripción del Tipo de Valor o Instrumento
	 * 
	 * @return String
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Descripción del Tipo de Valor o Instrumento
	 * 
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Descripción del Tipo de Valor o Instrumento en ingls.
	 * 
	 * @return String
	 */
	public String getDescripcionIngles() {
		return descripcionIngles;
	}

	/**
	 * Descripción del Tipo de Valor o Instrumento en ingls.
	 * 
	 * @param descripcionIngles
	 */
	public void setDescripcionIngles(String descripcionIngles) {
		this.descripcionIngles = descripcionIngles;
	}

	/**
	 * Referencia al Mercado en que se encuentra el Instrumento.
	 * 
	 * @return BigInteger
	 */
	public BigInteger getIdMercado() {
		return idMercado;
	}

	/**
	 * Referencia al Mercado en que se encuentra el Instrumento
	 * 
	 * @param idMercado
	 */
	public void setIdMercado(BigInteger idMercado) {
		this.idMercado = idMercado;
	}

	/**
	 * @return the mercado
	 */
	public Mercado getMercado() {
		return mercado;
	}

	/**
	 * @param mercado
	 *            the mercado to set
	 */
	public void setMercado(Mercado mercado) {
		this.mercado = mercado;
	}
}
