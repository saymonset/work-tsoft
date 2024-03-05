/**
 * Bursatec - 2H Software SA de CV
 *
 * Sistema DALI
 *
 * TipoInstruccionDivisa.java - 13/08/2012
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
 * Objeto que representa la relación entre TipoInstruccion y Divisa R_TIPO_INSTRUCCION_DIVISA
 *
 * @author Marcos Rivas
 * @version 1.0
 */

@Entity
@Table(name = "R_TIPO_INSTRUCCION_DIVISA")
public class TipoInstruccionDivisa implements Serializable {

	private static final long serialVersionUID = 1L;

	/** El ID de la tabla */
	@Id
	@Column(name = "ID_TIPO_INSTRUCCION")
	private TipoInstruccion idTipoInstruccion;

	/** La Divisa relacionada */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DIVISA", updatable = false, insertable = false)
	private Divisa divisa;

	/** El Tipo de Instruccion Relacionado */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_INSTRUCCION", updatable = false, insertable = false)
	private TipoInstruccion tipoInstruccion;

	/**
	 * Obtiene el valor del atributo idTipoInstruccion.
	 *
	 * @return el valor del atributo idTipoInstruccion.
	 */
	public TipoInstruccion getIdTipoInstruccion() {
		return idTipoInstruccion;
	}

	/**
	 * Establece el valor del atributo idTipoInstruccion.
	 *
	 * @param idTipoInstruccion el valor del atributo idTipoInstruccion a establecer.
	 */
	public void setIdTipoInstruccion(TipoInstruccion idTipoInstruccion) {
		this.idTipoInstruccion = idTipoInstruccion;
	}

	/**
	 * Obtiene el valor del atributo divisa.
	 *
	 * @return el valor del atributo divisa.
	 */
	public Divisa getDivisa() {
		return divisa;
	}

	/**
	 * Establece el valor del atributo divisa.
	 *
	 * @param divisa el valor del atributo divisa a establecer.
	 */
	public void setDivisa(Divisa divisa) {
		this.divisa = divisa;
	}

	/**
	 * Obtiene el valor del atributo tipoInstruccion.
	 *
	 * @return el valor del atributo tipoInstruccion.
	 */
	public TipoInstruccion getTipoInstruccion() {
		return tipoInstruccion;
	}

	/**
	 * Establece el valor del atributo tipoInstruccion.
	 *
	 * @param tipoInstruccion el valor del atributo tipoInstruccion a establecer.
	 */
	public void setTipoInstruccion(TipoInstruccion tipoInstruccion) {
		this.tipoInstruccion = tipoInstruccion;
	}

}
