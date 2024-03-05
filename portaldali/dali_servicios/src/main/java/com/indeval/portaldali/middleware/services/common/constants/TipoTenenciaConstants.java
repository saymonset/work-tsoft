/**
 * 2H Software
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Creado el Jan 15, 2008
 */
package com.indeval.portaldali.middleware.services.common.constants;

/**
 * Constantes para referirse al tipo de tenencia de una cuenta.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public interface TipoTenenciaConstants {

	/** El identificador del tipo de tenencia Garantías VALPRE */
	Long TIPO_GARANTIAS_VALPRE = 5L;
	
	/** La clave del subgrupo del tipo de tenencia GARANTIAS */
	String CLAVE_SUBGRUPO_GARANTIAS = "GARANTIAS";
	
	String CIRCULANTE = "C";
	
	String EMISION = "E";
}
