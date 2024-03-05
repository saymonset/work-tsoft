/**
 * 2H Software SA de CV
 * 
 * Proyecto: Bursatec BMV
 * 
 * Archivo: CriterioValidacionCampoRequerido.java
 *
 * Creado el Abr 14, 2008
 */
package com.indeval.portaldali.presentation.validation.impl;

import java.util.Map;

import com.indeval.portaldali.presentation.validation.CriterioDeValidacionAbstract;

/**
 * Implementación de un criterio de validación para validar que un campo
 * no contenga solo cero.
 * 
 * @author Marcos Rivas
 * @version 1.0
 * 
 */
public class CriterioValidacionEsCero extends CriterioDeValidacionAbstract {
	
	/*
	 * 
	 */
	public boolean validar(Map<String, Object> parametros) {

		boolean resultado = true;
        if (parametros.get("campo") != null)
        {
    		Integer valor = (Integer) parametros.get("campo");

    		if (valor != 0) {
    			resultado = false;
    		}        	
        }

		return resultado;
	}

}
