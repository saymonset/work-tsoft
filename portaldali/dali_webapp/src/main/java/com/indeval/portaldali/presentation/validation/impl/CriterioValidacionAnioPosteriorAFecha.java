/**
 * 2H Software SA de CV
 * 
 * Proyecto: Sistema de Integración de Datos y Documentos Digitalizados para la BDNRC
 * 
 * Archivo: CriterioValidacionAnioPosteriorAFecha.java
 *
 * Creado el Jul 17, 2007
 */
package com.indeval.portaldali.presentation.validation.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import com.indeval.portaldali.presentation.validation.CriterioDeValidacionAbstract;


/**
 * Implementación de un criterio de validación para validar que un ao sea
 * posterior al ao de una fecha y mayor al ao lmite inferior establecido.
 *
 * @author Sandra Flores
 * @version 1.0
 *
 */
public class CriterioValidacionAnioPosteriorAFecha extends CriterioDeValidacionAbstract {
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hh.renapo.framework.validacion.CriterioDeValidacion#validar(java.util.Map)
	 */
	public boolean validar(Map<String, Object> parametros) {

		boolean resultado = true;

		Integer anio = (Integer) parametros.get("anio");
		Date fechaReferencia = (Date) parametros.get("fechaReferencia");
		Calendar cal = new GregorianCalendar();

		if(anio != null) {
			if(fechaReferencia != null){
				cal.setTime(fechaReferencia);			
				if (anio.intValue() < cal.get(Calendar.YEAR)){ //|| anio.intValue() <  calLimite.get(Calendar.YEAR)) {
					resultado = false;
				}
			}
		}

		return resultado;
	}
}
