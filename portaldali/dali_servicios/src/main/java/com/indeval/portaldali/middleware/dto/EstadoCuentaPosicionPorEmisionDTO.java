/**
 * 2H Software SA de CV
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Fecha de creación: Dec 20, 2007
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object que representa el detalle del estado de cuenta de posiciones
 * organizado por emisiones.
 * 
 * @author Sandra Flores Arrieta
 * @version 1.0
 *
 */
public class EstadoCuentaPosicionPorEmisionDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** La emisión de este detalle de estado de cuenta */
	private EmisionDTO emision = null;
	
	/** Los registros con el detalle por bóveda del estado de cuenta para la emisión en cuestin */
	private List<DetalleEstadoCuentaPosicionPorBovedaDTO> detallesBoveda = new ArrayList<DetalleEstadoCuentaPosicionPorBovedaDTO>();

	/**
	 * Obtiene el valor del atributo emision
	 *
	 * @return el valor del atributo emision
	 */
	public EmisionDTO getEmision() {
		return emision;
	}

	/**
	 * Establece el valor del atributo emision
	 *
	 * @param emision el valor del atributo emision a establecer.
	 */
	public void setEmision(EmisionDTO emision) {
		this.emision = emision;
	}

	/**
	 * Obtiene el valor del atributo detallesBoveda
	 *
	 * @return el valor del atributo detallesBoveda
	 */
	public List<DetalleEstadoCuentaPosicionPorBovedaDTO> getDetallesBoveda() {
		return detallesBoveda;
	}

	/**
	 * Establece el valor del atributo detallesBoveda
	 *
	 * @param detallesBoveda el valor del atributo detallesBoveda a establecer.
	 */
	public void setDetallesBoveda(
			List<DetalleEstadoCuentaPosicionPorBovedaDTO> detallesBoveda) {
		this.detallesBoveda = detallesBoveda;
	}
	
}
