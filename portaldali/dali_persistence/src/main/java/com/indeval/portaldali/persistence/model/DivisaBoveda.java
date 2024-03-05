/**
 * Bursatec - 2H Software SA de CV
 *
 * Sistema DALI
 *
 * DivisaBoveda.java - 14/08/2012
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Objeto que representa la relación entre Divisa y Boveda R_DIVISA_BOVEDA
 *
 * @author Marcos Rivas
 * @version 1.0
 */

@Entity
@Table(name = "R_DIVISA_BOVEDA")
public class DivisaBoveda implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DivisaBovedaPK id;

	/**
	 * Obtiene el valor del atributo id.
	 *
	 * @return el valor del atributo id.
	 */
	public DivisaBovedaPK getId() {
		return id;
	}

	/**
	 * Establece el valor del atributo id.
	 *
	 * @param id el valor del atributo id a establecer.
	 */
	public void setId(DivisaBovedaPK id) {
		this.id = id;
	}


}
