/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Objeto que representa la llave primaria para la tabla T_LISTA_Persona_EVCO
 * 
 * @author Pablo Balderas
 */
@Embeddable
public class ListaPersonaPK implements Serializable {

	/** Id para la serialización */
	private static final long serialVersionUID = 8678060091582596045L;

	/** Id del grupo */
	@Column(name = "ID_Persona", unique = false, nullable = false)
	private Long idPersona;
	
	/** Id de la lista */
	@Column(name = "ID_LISTA", unique = false, nullable = false)
	private Long idLista;

	/**
	 * Constructor de la clase
	 */
	public ListaPersonaPK() {
		super();
	}

	/**
	 * Constructor de la clase
	 * @param idGrupo Id del grupo
	 * @param idLista Id de la lista
	 */
	public ListaPersonaPK(Long idPersona, Long idLista) {
		super();
		this.idPersona = idPersona;
		this.idLista = idLista;
	}

	/**
	 * Método para obtener el atributo idLista
	 * @return El atributo idLista
	 */
	public Long getIdLista() {
		return idLista;
	}

	/**
	 * Método para establecer el atributo idLista
	 * @param idLista El valor del atributo idLista a establecer.
	 */
	public void setIdLista(Long idLista) {
		this.idLista = idLista;
	}

	/**
	 * @return the idPersona
	 */
	public Long getIdPersona() {
		return idPersona;
	}

	/**
	 * @param idPersona the idPersona to set
	 */
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
}
