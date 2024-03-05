/**
 * 2H Software
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Creado el Jan 15, 2008
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;

/**
 * Data Transfer Object que representa la definición de un detalle de movimiento
 * en el sistema para la consulta de estado de cuenta.
 *
 * @author José Antonio Huizar Moreno
 * @version 1.0
 *
 */
public class DefinicionDetalleMovimientoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** El identificador del detalle de movimiento que describe esta definición */
	private String tipoOperacionPrincipal = null;
	
	/** La ruta de la pantalla para presentar el detalle del movimiento */
	private String rutaPantallaDetalleMovimiento = null;
	/**
	 * ancho de la pantalla a desplegar
	 */
	private String anchoPantalla = null;
	/**
	 * alto de la pantalla a desplegar
	 */
	private String altoPantalla = null;

	/**
	 * Obtiene el valor del atributo tipoOperacionPrincipal
	 * 
	 * @return el valor del atributo tipoOperacionPrincipal
	 */
	public String getTipoOperacionPrincipal() {
		return tipoOperacionPrincipal;
	}

	/**
	 * Establece el valor del atributo tipoOperacionPrincipal
	 *
	 * @param tipoOperacionPrincipal el valor del atributo tipoOperacionPrincipal a establecer
	 */
	public void setTipoOperacionPrincipal(String tipoOperacionPrincipal) {
		this.tipoOperacionPrincipal = tipoOperacionPrincipal;
	}

	/**
	 * Obtiene el valor del atributo rutaPantallaDetalleMovimiento
	 * 
	 * @return el valor del atributo rutaPantallaDetalleMovimiento
	 */
	public String getRutaPantallaDetalleMovimiento() {
		return rutaPantallaDetalleMovimiento;
	}

	/**
	 * Establece el valor del atributo rutaPantallaDetalleMovimiento
	 *
	 * @param rutaPantallaDetalleMovimiento el valor del atributo rutaPantallaDetalleMovimiento a establecer
	 */
	public void setRutaPantallaDetalleMovimiento(String rutaPantallaDetalleMovimiento) {
		this.rutaPantallaDetalleMovimiento = rutaPantallaDetalleMovimiento;
	}

	/**
	 * Obtiene el campo anchoPantalla
	 * @return  anchoPantalla
	 */
	public String getAnchoPantalla() {
		return anchoPantalla;
	}

	/**
	 * Asigna el valor del campo anchoPantalla
	 * @param anchoPantalla el valor de anchoPantalla a asignar
	 */
	public void setAnchoPantalla(String anchoPantalla) {
		this.anchoPantalla = anchoPantalla;
	}

	/**
	 * Obtiene el campo altoPantalla
	 * @return  altoPantalla
	 */
	public String getAltoPantalla() {
		return altoPantalla;
	}

	/**
	 * Asigna el valor del campo altoPantalla
	 * @param altoPantalla el valor de altoPantalla a asignar
	 */
	public void setAltoPantalla(String altoPantalla) {
		this.altoPantalla = altoPantalla;
	}
	
}
