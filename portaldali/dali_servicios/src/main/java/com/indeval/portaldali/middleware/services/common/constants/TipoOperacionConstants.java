/**
 * 2H Software - Bursatec
 * 
 * Sistema de Consulta de Estados de Cuenta
 *
 * Dec 26, 2007
 *
 */
package com.indeval.portaldali.middleware.services.common.constants;

/**
 * Constantas que indican los diferentes tipos de operaciones
 * @author Pablo Julián Balderas Méndez
 * 
 */
public interface TipoOperacionConstants {

	/** Tipo de operación TLP */
	final String TIPO_OPERACION_TLP = "TLP";
	
	/** Tipo de operación DVD */
	final String TIPO_OPERACION_DVD = "E/E";
	
	/** Tipo de operación DVP */
	final String TIPO_OPERACION_DVP = "DVP";
	
	/** Tipo de operacion depsito de efectivo */
	final String TIPO_OPERACION_DEP_EFE = "DEP $";
	
	/** Tipo de operación retiro de efectivo */
	final String TIPO_OPERACION_RET_EFE = "RET $";
	
	/** Tipo de operación traspaso de efectivo */
	final String TIPO_OPERACION_TRASP_EFE = "TEFEC";
	
	/** Tipo de operación bloqueo de efectivo */
	final String TIPO_OPERACION_BLOQ_EFE = "BLOQ $";	
	
	/** Tipo de operación desbloqueo de efectivo */
	final String TIPO_OPERACION_DESBLOQ_EFE = "DESB $";	
	
}
