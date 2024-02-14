package com.indeval.portalinternacional.middleware.servicios.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "C_ESTATUS_FT_DIVISAS_INT")
public class EstatusDivisas {

	public Long idEstatus;
	public String descripcion;
	@Id
	@Column(name = "ID_ESTATUS", unique = true, nullable = false)
	public Long getIdEstatus() {
		return idEstatus;
	}
	public void setIdEstatus(Long idEstatus) {
		this.idEstatus = idEstatus;
	}

	@Column(name = "DESCRIPCION", unique = false, nullable = false)
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
