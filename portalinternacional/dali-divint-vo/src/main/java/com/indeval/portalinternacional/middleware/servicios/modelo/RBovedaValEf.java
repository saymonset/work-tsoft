package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="R_BOVEDA_V_E")
public class RBovedaValEf implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ID_VALORES")
	private Long idValores;
	
	@Column(name="ID_EFECTIVO")
	private Long idEfectivo;

	public Long getIdValores() {
		return idValores;
	}

	public void setIdValores(Long idValores) {
		this.idValores = idValores;
	}

	public Long getIdEfectivo() {
		return idEfectivo;
	}

	public void setIdEfectivo(Long idEfectivo) {
		this.idEfectivo = idEfectivo;
	}

	@Override
	public String toString() {
		return "RBovedaValEf [idValores=" + idValores + ", idEfectivo=" + idEfectivo + "]";
	}

}
