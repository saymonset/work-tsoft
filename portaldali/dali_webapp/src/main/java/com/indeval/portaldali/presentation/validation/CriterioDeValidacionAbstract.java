/**
 * 2H Software SA de CV
 * 
 * Proyecto: Sistema de Integración de Datos y Documentos Digitalizados para la BDNRC
 * 
 * Archivo: CriterioDeValidacionAbstract.java
 *
 * Creado el Jul 12, 2007
 */
package com.indeval.portaldali.presentation.validation;

import java.util.Map;

/**
 * Contiene la funcionalidad por defecto de un criterio de validación.
 *
 * @author José Antonio Huizar Moreno
 * @version 1.0
 *
 */
public abstract class CriterioDeValidacionAbstract implements CriterioDeValidacion {
	
	/** Mapa que contiene como llave el nombre del parámetro y como valor el nombre de la propiedad del DTO a validar */
	protected Map<String, Object> parametros = null;
	
	/** La llave del resource bundle del mensaje que ser presentado al usuario en caso de que falle la validación */
	protected String llaveMensaje = null;
	
	/**
	 * método para obtener el atributo llaveMensaje
	 *
	 * @return Obtiene el atributo llaveMensaje.
	 */
	public String getLlaveMensaje() {
		return llaveMensaje;
	}

	/**
	 * método para establecer el atributo llaveMensaje
	 *
	 * @param llaveMensaje El valor del atributo llaveMensaje a establecer.
	 */
	public void setLlaveMensaje(String llaveMensaje) {
		this.llaveMensaje = llaveMensaje;
	}

	/**
	 * método para obtener el atributo parametros
	 *
	 * @return Obtiene el atributo parametros.
	 */
	public Map<String, Object> getParametros() {
		return parametros;
	}

	/**
	 * método para establecer el atributo parametros
	 *
	 * @param parametros El valor del atributo parametros a establecer.
	 */
	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}

}
