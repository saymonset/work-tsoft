package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "R_Estado_SM_EVCO")
public class ReglaEstado {
	private Long id;
	private Estado edoActual;
	private Estado edoSiguiente;
	/**
	 * @return the id
	 */
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the edoActual
	 */
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "edo_act", referencedColumnName="ID_ESTADO", insertable=false, updatable=false)	
	public Estado getEdoActual() {
		return edoActual;
	}
	/**
	 * @param edoActual the edoActual to set
	 */
	public void setEdoActual(Estado edoActual) {
		this.edoActual = edoActual;
	}
	/**
	 * @return the edoSiguiente
	 */
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "edo_sig", referencedColumnName="ID_ESTADO", insertable=false, updatable=false)
	public Estado getEdoSiguiente() {
		return edoSiguiente;
	}
	/**
	 * @param edoSiguiente the edoSiguiente to set
	 */
	public void setEdoSiguiente(Estado edoSiguiente) {
		this.edoSiguiente = edoSiguiente;
	}
}
