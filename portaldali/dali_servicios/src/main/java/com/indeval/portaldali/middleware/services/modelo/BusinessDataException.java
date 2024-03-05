/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.modelo;

/**
 * Esta excepci&oacute;n es utilizada cuando se encuentra un error de logica de
 * negocio. Puede ser usada en ejecuciones de procedimientos cuando dicho
 * procedimientos regresen un c&oacute;digo de error. Tambien se usa para
 * mantener compatibilidad con los codigos de error antes usados.
 * 
 * @author Fco. Agustin Calderon O.
 * @version 1.0
 */
public class BusinessDataException extends BusinessException {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	/**
	 * C&oacute;digo de error
	 */
	private String legacyErrorCode;

	/**
	 * Crea un objeto EnlacesBusinessExceptioin nuevo.
	 * 
	 * @param msg
	 *            Mensaje de la excepci&oacute;n.
	 * @param legacyErrorCode
	 *            C&oacute;digo de error devuelto por el Stored Procedure.
	 */
	public BusinessDataException(String msg, String legacyErrorCode) {
		super(msg);
		this.setLegacyErrorCode(legacyErrorCode);
	}

	/**
	 * Crea un objeto EnlacesBusinessExceptioin nuevo.
	 * 
	 * @param msg
	 *            Mensaje de la excepci&oacute;n.
	 * @param ex
	 *            La excepci&oacute;n anidada.
	 * @param legacyErrorCode
	 *            C&oacute;digo de error.
	 */
	public BusinessDataException(String msg, Throwable ex, String legacyErrorCode) {
		super(msg, ex);
		this.setLegacyErrorCode(legacyErrorCode);
	}

	/**
	 * @return Obtiene el legacyErrorCode.
	 */
	public String getLegacyErrorCode() {
		return legacyErrorCode;
	}

	/**
	 * @param legacyErrorCode
	 *            Establece el legacyErrorCode.
	 */
	public void setLegacyErrorCode(String legacyErrorCode) {
		this.legacyErrorCode = legacyErrorCode;
	}

}