/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.bursatec.seguridad.vo;

import java.io.Serializable;

/**
 * @author Emigdio
 *
 */
public class RespuestaVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String clave;
    
    private String explicacion;
    
    private String tipoRespuesta;

	/**
	 * Obtiene el campo clave
	 * @return  clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * Asigna el valor del campo clave
	 * @param clave el valor de clave a asignar
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}

	/**
	 * Obtiene el campo explicacion
	 * @return  explicacion
	 */
	public String getExplicacion() {
		return explicacion;
	}

	/**
	 * Asigna el valor del campo explicacion
	 * @param explicacion el valor de explicacion a asignar
	 */
	public void setExplicacion(String explicacion) {
		this.explicacion = explicacion;
	}

	/**
	 * Obtiene el campo tipoRespuesta
	 * @return  tipoRespuesta
	 */
	public String getTipoRespuesta() {
		return tipoRespuesta;
	}

	/**
	 * Asigna el valor del campo tipoRespuesta
	 * @param tipoRespuesta el valor de tipoRespuesta a asignar
	 */
	public void setTipoRespuesta(String tipoRespuesta) {
		this.tipoRespuesta = tipoRespuesta;
	}

	
}
