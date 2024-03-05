/**
 * Bursatec - 2H Software SA de CV
 *
 * Sistema DALI
 *
 * DivisaBovedaPK.java - 16/08/2012
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PK para la clase DivisaBoveda
 *
 * @author Marcos Rivas
 * @version 1.0
 */
@Embeddable
public class DivisaBovedaPK implements Serializable {

	private static final long serialVersionUID = 1L;

	/** La ID de la Divisa relacionada */
	@Column(name = "ID_DIVISA")
	private BigInteger idDivisa;

	/** La ID de la Boveda relacionada */
	@Column(name = "ID_BOVEDA")
	private BigInteger idBoveda;

	/**
	 * Obtiene el valor del atributo idDivisa.
	 *
	 * @return el valor del atributo idDivisa.
	 */
	public BigInteger getIdDivisa() {
		return idDivisa;
	}

	/**
	 * Establece el valor del atributo idDivisa.
	 *
	 * @param idDivisa el valor del atributo idDivisa a establecer.
	 */
	public void setIdDivisa(BigInteger idDivisa) {
		this.idDivisa = idDivisa;
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
