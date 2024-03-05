/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * TipoMensajeDTO.java
 * 28/02/2008
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;

/**
 * DTO que representa un tipo de mensaje
 * @author Emigdio Hernández
 *
 */
public class TipoMensajeDTO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 468780766706684948L;
	/**
	 * Identificador
	 */
	private int idTipoMensaje;
	/**
	 * Clave
	 */
	private String claveTipoMensaje;
	/**
	 * Descripción
	 */
	private String descripcion;

	/**
	 * Obtiene el campo idTipoMensaje
	 * @return  idTipoMensaje
	 */
	public int getIdTipoMensaje() {
		return idTipoMensaje;
	}

	/**
	 * Asigna el campo idTipoMensaje
	 * @param idTipoMensaje el valor de idTipoMensaje a asignar
	 */
	public void setIdTipoMensaje(int idTipoMensaje) {
		this.idTipoMensaje = idTipoMensaje;
	}

	/**
	 * Obtiene el campo claveTipoMensaje
	 * @return  claveTipoMensaje
	 */
	public String getClaveTipoMensaje() {
		return claveTipoMensaje;
	}

	/**
	 * Asigna el campo claveTipoMensaje
	 * @param claveTipoMensaje el valor de claveTipoMensaje a asignar
	 */
	public void setClaveTipoMensaje(String claveTipoMensaje) {
		this.claveTipoMensaje = claveTipoMensaje;
	}

	/**
	 * Obtiene el campo descripcion
	 * @return  descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Asigna el campo descripcion
	 * @param descripcion el valor de descripcion a asignar
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		boolean result = false;
		if (o != null && o instanceof TipoMensajeDTO) {
			result = ((TipoMensajeDTO) o).getIdTipoMensaje() ==
					this.getIdTipoMensaje();
		} else {
			result = super.equals(o);
		}
		return result;
	}
}
