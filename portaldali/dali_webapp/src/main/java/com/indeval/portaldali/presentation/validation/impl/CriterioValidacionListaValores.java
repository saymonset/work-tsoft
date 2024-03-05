/**
 * 2H Software SA de CV
 * 
 * Proyecto: Sistema de Integración de Datos y Documentos Digitalizados para la BDNRC
 * 
 * Archivo: CriterioValidacionListaValores.java
 *
 * Creado el Jul 13, 2007
 */
package com.indeval.portaldali.presentation.validation.impl;

import java.util.List;
import java.util.Map;

import com.indeval.portaldali.presentation.validation.CriterioDeValidacionAbstract;

/**
 * Implementación de un criterio de validación para validar que un valor se
 * encuentra dentro de una lista de valores válidos.
 *
 * @author José Antonio Huizar Moreno
 * @version 1.0
 *
 */
public class CriterioValidacionListaValores extends CriterioDeValidacionAbstract {
	
	/** La lista que contiene los valores válidos */
	private List<Object> valoresValidos = null;
	
	/* (non-Javadoc)
	 * @see com.hh.renapo.framework.validacion.CriterioDeValidacion#validar(java.util.Map)
	 */
	public boolean validar(Map<String, Object> parametros) {
		
		boolean resultado = false;
		
		Object o = parametros.get("campo");
		
		if(o != null) {
			resultado = valoresValidos.contains(o.toString());
		} else {
			resultado = true;
		}
		
		return resultado;
	}

	/**
	 * método para obtener el atributo valoresValidos
	 *
	 * @return Obtiene el atributo valoresValidos.
	 */
	public List<Object> getValoresValidos() {
		return valoresValidos;
	}

	/**
	 * método para establecer el atributo valoresValidos
	 *
	 * @param valoresValidos El valor del atributo valoresValidos a establecer.
	 */
	public void setValoresValidos(List<Object> valoresValidos) {
		this.valoresValidos = valoresValidos;
	}

}
