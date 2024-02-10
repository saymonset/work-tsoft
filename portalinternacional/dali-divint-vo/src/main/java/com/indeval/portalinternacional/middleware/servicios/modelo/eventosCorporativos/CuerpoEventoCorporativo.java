package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "T_Cuerpo_EVCO")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_Cuerpo_EVCO", allocationSize = 1, initialValue = 1)

public class CuerpoEventoCorporativo implements Serializable{
    private static final long serialVersionUID = 1L;
	private Long idCuerpo;
	private Long idEventoCorporativo;
	private String cuerpo;
	private String piePagina;
	/**
	 * @return the idCuerpo
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_cuerpo", unique = true, nullable = false)
	public Long getIdCuerpo() {
		return idCuerpo;
	}
	/**
	 * @param idCuerpo the idCuerpo to set
	 */
	public void setIdCuerpo(Long idCuerpo) {
		this.idCuerpo = idCuerpo;
	}
	/**
	 * @return the idEventoCorporativo
	 */
	@Column(name = "ID_EVENTO_CORPORATIVO", unique = true, nullable = false)
	public Long getIdEventoCorporativo() {
		return idEventoCorporativo;
	}
	/**
	 * @param idEventoCorporativo the idEventoCorporativo to set
	 */
	public void setIdEventoCorporativo(Long idEventoCorporativo) {
		this.idEventoCorporativo = idEventoCorporativo;
	}
	/**
	 * @return the cuerpo
	 */
	@Lob
	@Column(name = "cuerpo", unique = true, nullable = false)
	public String getCuerpo() {
		return cuerpo;
	}
	/**
	 * @param cuerpo the cuerpo to set
	 */
	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}
	/**
	 * @return the piePagina
	 */
	@Lob
	@Column(name = "pie_pagina", unique = true, nullable = false)
	public String getPiePagina() {
		return piePagina;
	}
	/**
	 * @param piePagina the piePagina to set
	 */
	public void setPiePagina(String piePagina) {
		this.piePagina = piePagina;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CuerpoEventoCorporativo [idCuerpo=" + idCuerpo
				+ ", idEventoCorporativo=" + idEventoCorporativo + ", cuerpo="
				+ cuerpo + ", piePagina=" + piePagina + "]";
	}
}
