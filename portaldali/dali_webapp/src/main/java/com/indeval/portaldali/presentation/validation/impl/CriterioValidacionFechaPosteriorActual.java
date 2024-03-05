/**
 * 2H Software SA de CV
 * 
 * Proyecto: Sistema de Integración de Datos y Documentos Digitalizados para la BDNRC
 * 
 * Archivo: CriterioValidacionFechaPosteriorActual.java
 *
 * Creado el Jul 12, 2007
 */
package com.indeval.portaldali.presentation.validation.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

import com.indeval.portaldali.presentation.validation.CriterioDeValidacionAbstract;

/**
 * Implementación de un criterio de validación para validar que una fecha no sea
 * posterior a la fecha actual
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public class CriterioValidacionFechaPosteriorActual extends CriterioDeValidacionAbstract {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hh.renapo.framework.validacion.CriterioDeValidacion#validar(java.util.Map)
	 */
	public boolean validar(Map<String, Object> parametros) {

		boolean resultado = true;

		Date fecha = (Date) parametros.get("fecha");
		Date fechaActual = new Date();

		if(fecha != null) {
			if (DateUtils.truncate(fecha, Calendar.DATE).before(DateUtils.truncate(fechaActual, Calendar.DATE))) {
				resultado = false;
			}
		}

		return resultado;
	}

}
