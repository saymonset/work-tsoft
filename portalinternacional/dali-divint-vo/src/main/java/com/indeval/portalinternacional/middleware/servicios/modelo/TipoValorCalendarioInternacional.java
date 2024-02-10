package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "R_TV_CALENDARIO_INT")
public class TipoValorCalendarioInternacional implements Serializable{
	
	/**
	 * Constante de serializacion por default
	 */
	private static final long serialVersionUID = 1L;

	private Long idTipoValorCalendarioInternacional;
	
	private String tipoValor;

	
	/**
	 * @return Long
	 */
	@Id
	@Column(name = "ID_TV_CALENDARIO_INT", unique = true, nullable = false)
	public Long getIdTipoValorCalendarioInternacional() {
		return idTipoValorCalendarioInternacional;
	}

	public void setIdTipoValorCalendarioInternacional(
			Long idTipoValorCalendarioInternacional) {
		this.idTipoValorCalendarioInternacional = idTipoValorCalendarioInternacional;
	}
	
	/**
	 * @return String
	 */
	@Column(name = "TIPO_VALOR", unique = true, nullable = false)
	public String getTipoValor() {
		return tipoValor;
	}

	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
	}
	
	

}
