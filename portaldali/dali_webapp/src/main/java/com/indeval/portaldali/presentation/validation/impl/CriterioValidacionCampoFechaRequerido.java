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
 * Implementación de un criterio de validación para validar que un campo no venga vacio.
 * 
 * @author Marcos Rivas
 * @version 1.0
 * 
 */
public class CriterioValidacionCampoFechaRequerido extends CriterioDeValidacionAbstract {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hh.renapo.framework.validacion.CriterioDeValidacion#validar(java.util.Map)
	 */
	public boolean validar(Map<String, Object> parametros) {

		boolean resultado = true;

		Object campo = parametros.get("campo");

		if (campo == null) {
			resultado = false;
		}

		return resultado;
	}

}
