/**
 * 2H Software SA de CV
 * 
 * Proyecto: Bursatec BMV
 * 
 * Archivo: CriterioValidacionRangoNumerico.java
 *
 * Creado el Abr 14, 2008
 */
package com.indeval.portaldali.presentation.validation.impl;

import java.math.BigDecimal;
import java.util.Map;

import com.indeval.portaldali.presentation.validation.CriterioDeValidacionAbstract;

/**
 * Implementación de un criterio de validación para validar que un campo
 * solo contenga numeros naturales.
 * 
 * @author Marcos Rivas
 * @version 1.0
 * 
 */
public class CriterioValidacionRangoNumerico extends CriterioDeValidacionAbstract {
	
	/** El limite superior del Rango a considerar */
	private BigDecimal limSuperior;
	
	/** El Limite inferior del Rango a considerar */
	private BigDecimal limInferior;
	
	/*
	 * Verifica que un valor BigDecimal este en un rango dado entre 2 limites
	 */
	public boolean validar(Map<String, Object> parametros) {

		boolean resultado = true;
        if (parametros.get("campo") != null)
        {
    		BigDecimal valor = (BigDecimal) parametros.get("campo");

    		if ((valor.compareTo(limInferior)<0)||(valor.compareTo(limSuperior)>0)) {
    			resultado = false;
    		}        	
        }

		return resultado;
	}

	/**
	 * @return the limSuperior
	 */
	public BigDecimal getLimSuperior() {
		return limSuperior;
	}

	/**
	 * @param limSuperior the limSuperior to set
	 */
	public void setLimSuperior(BigDecimal limSuperior) {
		this.limSuperior = limSuperior;
	}

	/**
	 * @return the limInferior
	 */
	public BigDecimal getLimInferior() {
		return limInferior;
	}

	/**
	 * @param limInferior the limInferior to set
	 */
	public void setLimInferior(BigDecimal limInferior) {
		this.limInferior = limInferior;
	}

	
	
}

