// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.servicios.modelo;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Objeto que representa la relacion entre Divisa y Boveda R_DIVISA_BOVEDA
 *
 * @author Enrique Guzman
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
