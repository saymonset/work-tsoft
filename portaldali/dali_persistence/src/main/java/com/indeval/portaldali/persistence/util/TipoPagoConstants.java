/**
 * Sistema Portal DALI
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Nov 3, 2008
 */
package com.indeval.portaldali.persistence.util;

/**
 * Constantes para el catálogo de tipos de pago.
 *
 * @author José Antonio Huizar Moreno
 * @version 1.0
 *
 */
public interface TipoPagoConstants {
	
	int TERCERO_A_TERCERO_SPEI = 0;
	
	int PARTICIPANTE_A_TERCERO_SPEI = 1;
	
	int PARTICIPANTE_A_PARTICIPANTE_SPEI = 2;
	
	int TRASPASO_FONDOS_SIAC = 3;
	
	int DEPOSITO_DEVOLUCION = 4;
	
	int DEPOSITO_OTRAS_DIVISAS = 5;
	
	String DESCRIPCION_SPEI = "SPEI";
	
	String DESCRIPCION_SIAC = "SIAC";
	
	String DESCRIPCION_DIVI = "DIVI";
	
	String DESCRIPCION_DEVO = "DEVO";
}
