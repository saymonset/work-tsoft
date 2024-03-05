/**
 * 2H Software SA de CV
 * 
 * Proyecto: Sistema de Integración de Datos y Documentos Digitalizados para la BDNRC
 * 
 * Archivo: CriterioValidacionExpresionRegular.java
 *
 * Creado el Jul 13, 2007
 */
package com.indeval.portaldali.presentation.validation.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.presentation.validation.CriterioDeValidacionAbstract;

/**
 * Implementación de un criterio de validación para validar que una cadena
 * cumpla con lo establecido por una expresin regular.
 *
 * @author José Antonio Huizar Moreno
 * @version 1.0
 *
 */
public class CriterioValidacionExpresionRegular extends CriterioDeValidacionAbstract {
	
	/** La expresin regular contra la que se validar el campo */
	private String expresionRegular = null;
	
	/* (non-Javadoc)
	 * @see com.hh.renapo.framework.validacion.CriterioDeValidacion#validar(java.util.Map)
	 */
	public boolean validar(Map<String, Object> parametros) {
		
		boolean resultado = true;
		
		String valor = (String)parametros.get("campo");
		
		if(StringUtils.isNotEmpty(StringUtils.trimToEmpty(valor))) {
			
			resultado = valor.matches(expresionRegular);
		}
		
		return resultado;
	}

	/**
	 * método para obtener el atributo expresionRegular
	 *
	 * @return Obtiene el atributo expresionRegular.
	 */
	public String getExpresionRegular() {
		return expresionRegular;
	}

	/**
	 * método para establecer el atributo expresionRegular
	 *
	 * @param expresionRegular El valor del atributo expresionRegular a establecer.
	 */
	public void setExpresionRegular(String expresionRegular) {
		this.expresionRegular = expresionRegular;
	}

}
