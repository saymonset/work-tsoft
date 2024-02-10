package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "T_Lista_Persona_EVCO")
public class ListaPersona implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Objeto que representa la llave primaria
	 */
	@EmbeddedId
	private ListaPersonaPK id;

	/**
	 * @return the id
	 */
	public ListaPersonaPK getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(ListaPersonaPK id) {
		this.id = id;
	}

	

}
