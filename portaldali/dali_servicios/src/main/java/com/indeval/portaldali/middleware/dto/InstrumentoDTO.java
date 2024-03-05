/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigInteger;



/**
 * Cata&acute;logo de los Tipos de Valor que se pueden operar, asociados a las
 * emisiones.
 * 
 * C_INSTRUMENTO
 * 
 * @author Fernando Vazquez ulloa
 * @version 1.0
 */

public class InstrumentoDTO implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador del instrumento.
	 */
	
	private BigInteger idInstrumento;
	/**
	 * Clave del tipo de instrumento.
	 */
	
	private String claveTipoValor;
	/**
	 * Clave CFI del instrumento.
	 */
	
	private String claveCfi;
	/**
	 * Descripci&oacute;n del instrumento.
	 */

	private String descripcion;
	/**
	 * Descripci&oacute;n en ingles del instrumento.
	 */

	private String descripcionIngles;

	/**
	 * Mercado en que opera el instrumento.
	 */
	
	private MercadoDTO mercado;

	/**
	 * Identificador del mercado en que opera el instrumento.
	 */

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
	public MercadoDTO getMercado() {
		return mercado;
	}

	/**
	 * @param mercado
	 *            the mercado to set
	 */
	public void setMercado(MercadoDTO mercado) {
		this.mercado = mercado;
	}
}
