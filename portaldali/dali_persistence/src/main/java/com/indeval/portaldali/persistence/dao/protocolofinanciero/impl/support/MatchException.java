/*
 * Copyright (c) 2007-2007 S.D. Indeval. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.protocolofinanciero.impl.support;

/**
 * Esta clase de excepci&oacute;n se utiliza como base a todas las
 * excepciones del m&oacute;dulo de match. Este tipo de excepci&oacute;n
 * no debe ser atrapada para permitir el rollback de las operaciones

 * @author Damian Garcia
 */
public class MatchException extends Exception {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
    
	/**
	 * variable que contiene el c&oacute;digo de error de la excepcion 
	 */
	private String codigoError;


	/**
	 * @param codigoError
	 * @param mensaje
	 */
	public MatchException(String codigoError, String mensaje) {
		super(mensaje);
		this.codigoError = codigoError;
	}

	/**
	 * @param codigoError
	 * @param mensaje
	 * @param exception
	 */
	public MatchException(String codigoError, String mensaje, Throwable exception) {
		super(mensaje, exception);
		this.codigoError = codigoError;
	}	
	
	/**
	 * Regresa el c&oacute;digo de error de la excepci&oacute;n
	 * @return String
	 */
	public String getCodigoError() {
		return codigoError;
	}
	
}
