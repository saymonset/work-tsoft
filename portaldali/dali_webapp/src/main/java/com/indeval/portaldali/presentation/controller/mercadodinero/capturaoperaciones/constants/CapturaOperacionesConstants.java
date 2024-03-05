/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * CapturaOperacionesConstants.java
 * Apr 23, 2008
 */
package com.indeval.portaldali.presentation.controller.mercadodinero.capturaoperaciones.constants;

/**
 * Constantes comunes para los controladores de las pantallas de captura de operaciones.
 *
 * @author José Antonio Huizar Moreno
 * @version 1.0
 *
 */
public interface CapturaOperacionesConstants {
	
	/** Indica que la operación de compra se realiza el mismo día */
	Integer COMPRA_MISMO_DIA = 0;
	Integer COMPRA_FECHA_VALOR = 1;
	
	/** Indica el tipo de trasferencia. */
	Integer MISMA_INSTITUCION = 0;
	Integer TERCEROS = 1;
	
	/** Indica el tipo de cuenta origen  cuenta destino. */
	Integer CC = 0;
	Integer MC = 1;
	Integer MD = 2;
	
	/** Indica el tipo retiro de fondos. */
	Integer SPEI = 0;
	Integer SIAC = 1;
	
	/** Indica a quien se hace el cargo de la operación de apertura. */
	Integer APERTURA_CARGO_TRASPASANTE = 0;
	Integer APERTURA_CARGO_RECEPTOR = 1;
	
}
