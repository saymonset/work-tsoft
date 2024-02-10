/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.bursatec.seguridad.middleware.service;

import java.io.Serializable;

/**
 * @author Emigdio
 *
 */
public class SeguridadException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SeguridadException(String message) {
		super(message);
	}

}
