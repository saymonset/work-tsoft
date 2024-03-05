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


	public SeguridadException(Throwable cause) {
		super(cause);
	}

	public SeguridadException(String message, Throwable cause) {
		super(message, cause);
	}

	public SeguridadException(String message) {
		super(message);
	}

	public SeguridadException() {
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
