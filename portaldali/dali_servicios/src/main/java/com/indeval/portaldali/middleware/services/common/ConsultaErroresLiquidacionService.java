/**
 * Portal DALI
 * 
 * 2H Software SA
 *
 * ConsultaErroresLiquidacionService.java 
 *
 * Creado el: Sep 13, 2008
 */
package com.indeval.portaldali.middleware.services.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.ErrorDaliDTO;

/**
 * Servicio de negocio para la consulta del catálogo de errores de liquidación del DALI.
 *
 * @author José Antonio Huizar Moreno
 * @version 1.0
 *
 */
public interface ConsultaErroresLiquidacionService {
	
	/**
	 * Obtiene todos los elementos del catálogo de errores de liquidación del DALI.
	 * 
	 * @return una lista con los elementos del catálogo de errores de liquidación del DALI.
	 */
	List<ErrorDaliDTO> buscarErroresLiquidacion();
	
	/**
	 * Busca un error de liquidación en el catálogo de errores de liquidación.
	 * 
	 * @param idError el identificador del error a consultar.
	 * @return el objeto {@link ErrorDaliDTO} que corresponde al identificador proporcionado.
	 */
	ErrorDaliDTO buscarErrorLiquidacionPorClaveError(String claveError);
}
