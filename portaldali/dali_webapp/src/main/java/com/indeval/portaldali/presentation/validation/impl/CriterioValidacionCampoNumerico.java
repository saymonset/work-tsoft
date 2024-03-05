/**
 * 2H Software SA de CV
 * 
 * Proyecto: Sistema de Integración de Datos y Documentos Digitalizados para la BDNRC
 * 
 * Archivo: CriterioValidacionCampoRequerido.java
 *
 * Creado el Jul 12, 2007
 */
package com.indeval.portaldali.presentation.validation.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.presentation.validation.CriterioDeValidacionAbstract;

/**
 * Implementación de un criterio de validación para validar que un campo
 * solo contenga numeros.
 * 
 * @author 2H
 * @version 1.0
 * 
 */
public class CriterioValidacionCampoNumerico extends CriterioDeValidacionAbstract {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hh.renapo.framework.validacion.CriterioDeValidacion#validar(java.util.Map)
	 */
	public boolean validar(Map<String, Object> parametros) {

		boolean resultado = false;
        if (parametros.get("campo") != null)
        {
    		String nombre = parametros.get("campo").toString();

    		if (!StringUtils.isEmpty(nombre)) {
    			try{
    				Double.parseDouble(nombre);
    				resultado = true;
    			}catch(Exception e){
    				resultado = false;
    			}
    			
    		}        	
        }


		return resultado;
	}

}
