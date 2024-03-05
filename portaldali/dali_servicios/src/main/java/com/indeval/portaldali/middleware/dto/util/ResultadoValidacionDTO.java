/**
 * 2H Software SA de CV
 * 
 * Proyecto: Framework RENAPO
 * 
 * Archivo: ResultadoValidacionDTO.java
 *
 * Creado el Jul 12, 2007
 */
package com.indeval.portaldali.middleware.dto.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Data Transfer Object que representa el resultado de la aplicacion de un criterio de validacion.
 *
 * @author Jose Antonio Huizar Moreno
 * @version 1.0
 *
 */
public class ResultadoValidacionDTO implements Serializable {

	/** Id para la serializacion */
	private static final long serialVersionUID = -805982972247178929L;

	/** Indica si el resultado de la validacion tuvo exito o no */
	private boolean valido = false;
	
	/** La llave del resource bundle que contiene el mensaje para el usuario en caso de que la validacion no haya tenido exito */
	private String mensaje = null;
	
	/** Mapa de mensajes de error que acumula los mensajes de error resultantes de todas las validaciones para cada campo */
	private Map<String,List<String>> mensajesError = null;
	
	/**
	 * Metodo para obtener el atributo mensaje
	 *
	 * @return Obtiene el atributo mensaje.
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * Metodo para establecer el atributo mensaje
	 *
	 * @param mensaje El valor del atributo mensaje a establecer.
	 */
	public void setMensaje(String llaveMensaje) {
		this.mensaje = llaveMensaje;
	}

	/**
	 * Metodo para obtener el atributo valido
	 *
	 * @return Obtiene el atributo valido.
	 */
	public boolean isValido() {
		return valido;
	}

	/**
	 * Metodo para establecer el atributo valido
	 *
	 * @param valido El valor del atributo valido a establecer.
	 */
	public void setValido(boolean valido){
		this.valido = valido;
	}

	public Map<String, List<String>> getMensajesError() {
		return mensajesError;
	}

	public void setMensajesError(Map<String, List<String>> mensajesError){
		this.mensajesError = mensajesError;
	}
	
}
