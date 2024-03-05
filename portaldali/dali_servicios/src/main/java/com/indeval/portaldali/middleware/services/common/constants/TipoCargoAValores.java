/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.middleware.services.common.constants;

/**
 * Constantes para identificar el tipo de cargo a la cantidad disponible o no
 * disponible de una posición.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 *
 */
public interface TipoCargoAValores {
	
	/** Indica que el cargo afecta a la cantidad disponible de una posición */
	public Long CARGO_AFECTA_DISPONIBLE = new Long(1);
	
	/** Indica que el cargo afecta a la cantidad no disponible de una posición */
	public Long CARGO_AFECTA_NO_DISPONIBLE = new Long(2);
	
	/** La descripción de la cantidad disponible */
	public String DESCRIPCION_DISPONIBLE = "DISPONIBLE";
	
	/** La descripción de la cantidad disponible */
	public String DESCRIPCION_NO_DISPONIBLE = "NO DISPONIBLE";
}
