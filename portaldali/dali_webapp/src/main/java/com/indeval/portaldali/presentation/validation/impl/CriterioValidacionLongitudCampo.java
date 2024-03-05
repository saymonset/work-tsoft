/**
 * 2H Software SA de CV
 * 
 * Proyecto: Sistema de Integración de Datos y Documentos Digitalizados para la BDNRC
 * 
 * Archivo: CriterioValidacionLongitudCampo.java
 *
 * Creado el Jul 13, 2007
 */
package com.indeval.portaldali.presentation.validation.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.presentation.validation.CriterioDeValidacionAbstract;

/**
 * Implementación de un criterio de validación para validar que un campo
 * no exceda la longitud mxima permitida.
 *
 * @author Sandra Flores
 * @version 1.0
 *
 */
public class CriterioValidacionLongitudCampo extends CriterioDeValidacionAbstract {

	/** Longitud mxima del campo que se est validando, se establece como una propiedad en la configuración 
	 * de los criterios de validación en el applicationContext correspondiente al acto. 
	 * Ejemplo: <property name="longitudMaxima" value="50" />
	 */
	private Integer longitudMaxima = null; 
		
	/* (non-Javadoc)
	 * @see com.hh.renapo.framework.validacion.CriterioDeValidacion#validar(java.util.Map)
	 */
	public boolean validar(Map<String, Object> parametros) {
		boolean resultado = true;
		if (parametros.get("campo") != null)
		{
			String campo = parametros.get("campo").toString();
			if(StringUtils.isNotEmpty(campo) && campo.length() > longitudMaxima.intValue()){
    			resultado = false;
    		}			
		}
				                	        				
		return resultado;
	}

	/**
	 * método para obtener el atributo longitudMaxima
	 *
	 * @return Obtiene el atributo longitudMaxima.
	 */
	public Integer getLongitudMaxima() {
		return longitudMaxima;
	}

	/**
	 * método para establecer el atributo longitudMaxima
	 *
	 * @param longitudMaxima El valor del atributo longitudMaxima a establecer.
	 */
	public void setLongitudMaxima(Integer longitudMaxima) {
		this.longitudMaxima = longitudMaxima;
	}

}
