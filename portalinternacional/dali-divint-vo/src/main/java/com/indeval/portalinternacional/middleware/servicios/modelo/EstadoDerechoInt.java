package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name = "C_ESTADO_DERECHO_INT")
public class EstadoDerechoInt implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;//ID_ESTADO_DERECHO_INT
	private String claveEstado;//CLAVE_ESTADO
	private String descripcion;//DESCRIPCION
	private Integer participante;//PARTICIPANTE
	
	public EstadoDerechoInt(){
		
	}
	/**
	 * @param id
	 * @param claveEstado
	 * @param descripcion
	 */
	public EstadoDerechoInt(Integer id, String claveEstado, String descripcion) {
		this.id = id;
		this.claveEstado = claveEstado;
		this.descripcion = descripcion;
	}
	/**
	 * @return the id
	 */
	@Id		
	@Column(name = "ID_ESTADO_DERECHO_INT")
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the claveEstado
	 */
	@Column(name = "CLAVE_ESTADO")
	public String getClaveEstado() {
		return claveEstado;
	}
	/**
	 * @param claveEstado the claveEstado to set
	 */
	public void setClaveEstado(String claveEstado) {
		this.claveEstado = claveEstado;
	}
	/**
	 * @return the descripcion
	 */
	@Column(name = "DESCRIPCION")
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * @return the participante
	 */
	@Column(name = "PARTICIPANTE")
	public Integer getParticipante() {
		return participante;
	}
	/**
	 * @param participante the participante to set
	 */
	public void setParticipante(Integer participante) {
		this.participante = participante;
	}

}
