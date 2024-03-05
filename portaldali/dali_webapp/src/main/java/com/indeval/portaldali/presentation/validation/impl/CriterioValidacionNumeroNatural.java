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
 * Implementación de un criterio de validación para validar que un campo solo
 * contenga numeros naturales.
 * 
 * @author Marcos Rivas
 * @version 1.0
 * 
 */
public class CriterioValidacionNumeroNatural extends
		CriterioDeValidacionAbstract {

	/*
	 * Verifica si los valores de PlazoRepDias, Tasa Premio, Cantidad y Precio
	 * por titulo no sean 0; En caso de que sean 0, se coloca el correspondiente
	 * mensaje de error y se regresa a la página de captura
	 */
	public boolean validar(Map<String, Object> parametros) {

		boolean resultado = true;
		if (parametros.get("campo") != null) {
			Number valor = (Number) parametros.get("campo");

			if (valor.doubleValue()<=0) {
				resultado = false;
			}
		}

		return resultado;
	}

}
