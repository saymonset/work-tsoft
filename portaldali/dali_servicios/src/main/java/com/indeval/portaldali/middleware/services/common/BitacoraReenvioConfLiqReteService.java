/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ConsultaCatalogoService.java
 * Apr 24, 2008
 */
package com.indeval.portaldali.middleware.services.common;

import java.util.List;

import com.indeval.portaldali.modelo.to.commons.BitacoraReenvioConfLiqReteDTO;

/**
 * Interfaz para servicios sobre la bitacora de reenvios
 * de confirmacion de liquidacion con operaciones RETE
 */
public interface BitacoraReenvioConfLiqReteService {
	
	public void saveBitacora(List<BitacoraReenvioConfLiqReteDTO> retes);
	
}
