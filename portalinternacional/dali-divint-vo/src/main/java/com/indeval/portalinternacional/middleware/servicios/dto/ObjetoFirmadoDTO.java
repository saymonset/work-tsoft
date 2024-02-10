// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.servicios.dto;

import java.io.Serializable;

public class ObjetoFirmadoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String numeroSerieCertificado;
	private String cadenaFirmada;
	private Object objetoFirmado;
	
	public ObjetoFirmadoDTO() {
		super();
	}

	public ObjetoFirmadoDTO(String numeroSerieCertificado, String cadenaFirmada, Object objetoFirmado) {
		super();
		this.numeroSerieCertificado = numeroSerieCertificado;
		this.cadenaFirmada = cadenaFirmada;
		this.objetoFirmado = objetoFirmado;
	}

	public String getNumeroSerieCertificado() {
		return numeroSerieCertificado;
	}

	public void setNumeroSerieCertificado(String numeroSerieCertificado) {
		this.numeroSerieCertificado = numeroSerieCertificado;
	}

	public String getCadenaFirmada() {
		return cadenaFirmada;
	}

	public void setCadenaFirmada(String cadenaFirmada) {
		this.cadenaFirmada = cadenaFirmada;
	}

	public Object getObjetoFirmado() {
		return objetoFirmado;
	}

	public void setObjetoFirmado(Object objetoFirmado) {
		this.objetoFirmado = objetoFirmado;
	}

}
