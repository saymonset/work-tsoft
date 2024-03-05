/**
 * 2H Software
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Creado el Dec 21, 2007
 */
package com.indeval.portaldali.middleware.services.common.constants;

/**
 * Constantes para el tipo de acción de un registro contable
 *
 * @author José Antonio Huizar Moreno
 * @version 1.0
 *
 */
public interface TipoAccionConstants {
	
	/** La descripción del tipo de acción recepción */
	public String TIPO_ACCION_RECEPCION_POSICION = "RECEPCI\u00D3N";
	
	/** La descripción del tipo de ación entrega */
	public String TIPO_ACCION_ENTREGA_POSICION = "ENTREGA";
	
	/** La descripción del tipo de acción recepción */
	public String TIPO_ACCION_RECEPCION_SALDO = "ABONO";
	
	/** La descripción del tipo de ación entrega */
	public String TIPO_ACCION_ENTREGA_SALDO = "CARGO";
}
