/**
 * 2H Software
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Creado el Jan 15, 2008
 */
package com.indeval.portaldali.middleware.dto.util;

import java.util.HashMap;

import com.indeval.portaldali.middleware.dto.DefinicionDetalleMovimientoDTO;

/**
 * Helper encargado de administrar las definiciones de los diferentes detalles
 * de movimientos disponibles en el sistema de consulta de estado de cuenta.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public class DefinicionDetallesMovimientosHelper {
	
	/**
	 * Mapa el cual contiene las definiciones de los detalles de movimiento por
	 * id de tipo de instrucción
	 */
	private HashMap<String, DefinicionDetalleMovimientoDTO> definiciones;

	/**
	 * Obtiene el valor del atributo definiciones
	 * 
	 * @return el valor del atributo definiciones
	 */
	public HashMap<String, DefinicionDetalleMovimientoDTO> getDefiniciones() {
		return definiciones;
	}

	/**
	 * Establece el valor del atributo definiciones
	 * 
	 * @param definiciones
	 *            el valor del atributo definiciones a establecer
	 */
	public void setDefiniciones(HashMap<String, DefinicionDetalleMovimientoDTO> definiciones) {
		this.definiciones = definiciones;
	}

	/**
	 * Obtiene la definición del detalle de movimiento que corresponde a un tipo
	 * de instrucción dado por medio de su identificador.
	 * 
	 * @param idTipoInstruccion
	 *            el identificador del tipo de instrucción a consultar.
	 * @return un DTO con los datos de la definición del detalle de movimiento.
	 *         <code>null</code> en caso de que no exista una definición para
	 *         el id proporcionado.
	 */
	public DefinicionDetalleMovimientoDTO buscarDefinicionDetalleMovimiento(Long idTipoInstruccion) {
		return definiciones.get(idTipoInstruccion.toString());
	}
}
