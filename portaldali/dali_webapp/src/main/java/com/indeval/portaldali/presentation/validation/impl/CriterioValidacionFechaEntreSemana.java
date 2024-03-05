/**
 * 2H Software SA de CV
 * 
 * Proyecto: Dali BMV
 * 
 * Archivo: CriterioValidacionFechaPosteriorActual.java
 *
 * Creado el Abril 23, 2008
 */
package com.indeval.portaldali.presentation.validation.impl;

import java.util.Map;

import com.indeval.portaldali.presentation.validation.CriterioDeValidacionAbstract;

/**
 * Implementación de un criterio de validación para validar que una fecha no sea
 * posterior a la fecha actual
 * 
 * @author Marcos Rivas Bermudez
 * @version 1.0
 * 
 */
public class CriterioValidacionFechaEntreSemana extends CriterioDeValidacionAbstract {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hh.renapo.framework.validacion.CriterioDeValidacion#validar(java.util.Map)
	 */
	public boolean validar(Map<String, Object> parametros) {

		boolean resultado = true;

//		Date fecha = (Date) parametros.get("fecha");
//
//		if(fecha != null) {
//			if ((DateUtils.isSameDay(fecha, Calendar.SUNDAY))||(DateUtils.isSameDay(fecha, Calendar.SATURDAY))) {
//				resultado = false;
//			}
//		}

		return resultado;
	}

}
