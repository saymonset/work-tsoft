/**
 * Bursatec - 2H Software SA de CV
 *
 * Sistema DALI
 *
 * TipoInstruccionBovedaPK.java - 21/08/2012
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PK para la clase TipoInstruccionBoveda
 *
 * @author Marcos Rivas
 * @version 1.0
 */
@Embeddable
public class TipoInstruccionBovedaPK implements Serializable {

	private static final long serialVersionUID = 1L;

	/** La ID del Tipo de Instruccion Relacionado */
	@Column(name = "ID_TIPO_INSTRUCCION")
	private BigInteger idTipoInstruccion;

	/** La ID de la Boveda relacionada */
	@Column(name = "ID_BOVEDA")
	private BigInteger idBoveda;

	/**
	 * Obtiene el valor del atributo idTipoInstruccion.
	 *
	 * @return el valor del atributo idTipoInstruccion.
	 */
	public BigInteger getIdTipoInstruccion() {
		return idTipoInstruccion;
	}

	/**
	 * Establece el valor del atributo idTipoInstruccion.
	 *
	 * @param idTipoInstruccion el valor del atributo idTipoInstruccion a establecer.
	 */
	public void setIdTipoInstruccion(BigInteger idTipoInstruccion) {
		this.idTipoInstruccion = idTipoInstruccion;
	}

	/**
	 * Obtiene el valor del atributo idBoveda.
	 *
	 * @return el valor del atributo idBoveda.
	 */
	public BigInteger getIdBoveda() {
		return idBoveda;
	}

	/**
	 * Establece el valor del atributo idBoveda.
	 *
	 * @param idBoveda el valor del atributo idBoveda a establecer.
	 */
	public void setIdBoveda(BigInteger idBoveda) {
		this.idBoveda = idBoveda;
	}

}
