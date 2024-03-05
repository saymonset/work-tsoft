/**
 * Bursatec - 2H Software SA de CV
 *
 * Sistema DALI
 *
 * TipoInstruccionBoveda.java - 21/08/2012
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Objeto que representa la relación entre TipoInstruccion y Boveda R_TIPO_INSTRUCCION_BOVEDA
 *
 * @author Marcos Rivas
 * @version 1.0
 */
@Entity
@Table(name = "R_TIPO_INSTRUCCION_BOVEDA")
public class TipoInstruccionBoveda implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TipoInstruccionBovedaPK id;

	/**
	 * Obtiene el valor del atributo id.
	 *
	 * @return el valor del atributo id.
	 */
	public TipoInstruccionBovedaPK getId() {
		return id;
	}

	/**
	 * Establece el valor del atributo id.
	 *
	 * @param id el valor del atributo id a establecer.
	 */
	public void setId(TipoInstruccionBovedaPK id) {
		this.id = id;
	}

}
