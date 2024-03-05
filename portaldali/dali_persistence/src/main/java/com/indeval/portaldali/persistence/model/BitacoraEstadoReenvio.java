package com.indeval.portaldali.persistence.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_BITACORA_ESTADO_REENVIO")
public class BitacoraEstadoReenvio {

	@Id
	@Column(name ="FOLIO_OPERACION", unique = true, nullable = false)
	private String folioOperacion;

	@Column(name ="FECHA_REENVIO", unique = false, nullable = false)
	private Date fechaReenvio;

	@Column(name ="PROCESADO", unique = false, nullable = false)
	private Boolean procesado;

	public String getFolioOperacion() {
		return folioOperacion;
	}

	public void setFolioOperacion(String folioOperacion) {
		this.folioOperacion = folioOperacion;
	}

	public Date getFechaReenvio() {
		return fechaReenvio;
	}

	public void setFechaReenvio(Date fechaReenvio) {
		this.fechaReenvio = fechaReenvio;
	}

	public Boolean getProcesado() {
		return procesado;
	}

	public void setProcesado(Boolean procesado) {
		this.procesado = procesado;
	}
	
}
