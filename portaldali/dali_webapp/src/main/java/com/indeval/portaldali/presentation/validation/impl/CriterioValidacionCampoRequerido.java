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
 * Implementación de un criterio de validación para validar que un campo no venga vacio.
 * 
 * @author 2H
 * @version 1.0
 * 
 */
public class CriterioValidacionCampoRequerido extends CriterioDeValidacionAbstract {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hh.renapo.framework.validacion.CriterioDeValidacion#validar(java.util.Map)
	 */
	public boolean validar(Map<String, Object> parametros) {

		boolean resultado = true;

		Object campo = parametros.get("campo");

		if (campo instanceof String && StringUtils.isEmpty((String)campo)) {
			resultado = false;
		} else if(campo == null) {
			resultado = false;
		}

		return resultado;
	}

}
