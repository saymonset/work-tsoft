package com.indeval.portalinternacional.middleware.servicios.vo;

/**
 * Enum para los estados de mensajes enviados de legislaci&oacute;n sic enviados al sistema MOI
 * @author Externo
 *
 */
public enum EstadoEnvioMoi {
	POR_AUTORIZAR("Por Autorizar"),
	AUTORIZADO("Autorizado"),
	CANCELADO("Cancelado");
	
	private String valor;
	
	EstadoEnvioMoi(String valor){
		this.valor = valor;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

}
