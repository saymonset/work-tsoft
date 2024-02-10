package com.indeval.portaldali.middleware.dto;

public class MensajePortalDTO {
	/**
	 * Mensaje del portal 
	 */
	private String mensaje;
	
	/**
	 * Indica si el mensaje esta habilitado o no
	 */
	private boolean habilitado;

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @return the habilitado
	 */
	public boolean isHabilitado() {
		return habilitado;
	}

	/**
	 * @param habilitado the habilitado to set
	 */
	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}
}
