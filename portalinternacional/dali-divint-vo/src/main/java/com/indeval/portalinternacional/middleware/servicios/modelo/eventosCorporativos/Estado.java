package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "C_ESTADO_EVCO")
public class Estado {
	private Long idEstado;
	private String estado;
	private String descripcion;
	private Integer ordenamiento;
	/**
	 * @return the idEstado
	 */
	@Id
	@Column(name = "ID_ESTADO", unique = true, nullable = false)
	public Long getIdEstado() {
		return idEstado;
	}
	/**
	 * @param idEstado the idEstado to set
	 */
	public void setIdEstado(Long idEstado) {
		this.idEstado = idEstado;
	}
	/**
	 * @return the estado
	 */
	@Column(name = "ESTADO", unique = true, nullable = false)
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * @return the descripcion
	 */
	@Column(name = "DESCRIPCION", unique = true, nullable = false)
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
	 * @return the ordenamiento
	 */
	@Column(name = "ORDENAMIENTO", unique = false, nullable = true)
	public Integer getOrdenamiento() {
		return ordenamiento;
	}
	/**
	 * @param ordenamiento the ordenamiento to set
	 */
	public void setOrdenamiento(Integer ordenamiento) {
		this.ordenamiento = ordenamiento;
	}
}
