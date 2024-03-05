/**
 * CMMV - Portal Dali
 * Copyrigth (c) 2017 CMMV. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.cfi;

import com.indeval.portaldali.modelo.to.cfi.DetalleCfiTO;

/**
 * Interfaz de negocio que define los metodos de consulta de CFI
 * 
 * @author Pablo Balderas
 */
public interface CfiService {

	/**
	 * Consulta el detalle del CFI
	 * @param cfi CFI para su detalle
	 */
	DetalleCfiTO findDetalleCfi(String cfi);
	
}
