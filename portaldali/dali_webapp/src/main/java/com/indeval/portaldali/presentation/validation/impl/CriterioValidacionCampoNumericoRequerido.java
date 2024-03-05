/**
 * 2H Software SA de CV
 * 
 * Proyecto: Sistema de Integración de Datos y Documentos Digitalizados para la BDNRC
 * 
 * Archivo: CriterioValidacionCampoRequerido.java
 *
 * Creado el Abr 15, 2007
 */
package com.indeval.portaldali.presentation.validation.impl;

import java.util.Map;

import com.indeval.portaldali.presentation.validation.CriterioDeValidacionAbstract;

/**
 * Implementación de un criterio de validación para validar que un campo no
 * venga vacio.
 * 
 * @author Marcos Rivas
 * @version 1.0
 * 
 */
public class CriterioValidacionCampoNumericoRequerido extends
		CriterioDeValidacionAbstract {
	
	Integer valua;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hh.renapo.framework.validacion.CriterioDeValidacion#validar(java.util.Map)
	 */
	public boolean validar(Map<String, Object> parametros) {

		boolean resultado = true;

		if(parametros.get("campo") == null){
			resultado = false;
		}else{
			if(parametros.get("campo") instanceof Number){
				Number campo = (Number)parametros.get("campo");
				if (campo.equals(0)) {
					resultado = false;
				}
			}else{
				resultado = false;
			}
		}
		
		
		return resultado;
	}

}
