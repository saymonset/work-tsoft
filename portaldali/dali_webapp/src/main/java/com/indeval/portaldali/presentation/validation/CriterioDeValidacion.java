/**
 * 2H Software SA de CV
 * 
 * Proyecto: Framework RENAPO
 * 
 * Archivo: CriterioDeValidacion.java
 *
 * Creado el Jul 12, 2007
 */
package com.indeval.portaldali.presentation.validation;

import java.util.Map;

/**
 * Representa un criterio de validación el cual ser aplicado
 * a un objeto.
 *
 * @author José Antonio Huizar Moreno
 * @version 1.0
 *
 */
public interface CriterioDeValidacion {
	
	/**
	 * Obtiene el mapa con los parámetros requeridos por este criterio de validación. 
	 * 
	 * @return el mapa con los parámetros requeridos por el criterio de validación.
	 */
	Map<String, Object> getParametros();
	
	/**
	 * Obtiene la llave del resource bundle del mensaje que ser desplegado al usuario
	 * en caso de que la validación no tenga xito. 
	 * 
	 * @return
	 */
	String getLlaveMensaje();
	
	/**
	 * 
	 * Valida que los datos recibidos como parámetros cumplan con el criterio de validación que
	 * representa este objeto.
	 * 
	 * @param parametros los parámetros sujetos a validación.
	 * @return <code>true</code> si tiene xito la validación, <code>false</code> en cualquier otro caso.
	 */
	boolean validar(Map<String, Object> parametros);
	
}
