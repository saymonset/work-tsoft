/**
 * Archivo: InfrastructureException.java
 *
 * Fecha de creación: Oct 9, 2006
 *
 * Derechos Reservados 2H Software 2006
 */
package com.indeval.portaldali.middleware.services.modelo;

/**
 * Excepción de infraestructura para el sistema Framework RENAPO J2EE. Una
 * excepción de infraestructura para el sistema Framework RENAPO J2EE se refiere
 * a toda situación de error ajena a la lógica del negocio o de la presentación.
 * Un ejemplo de estas situaciones puede ser un error de conectividad con la
 * base de datos.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0.
 */
public class InfrastructureException extends RuntimeException {

	/**
	 * Serial Version UID calculado por Eclipse.
	 */
	private static final long serialVersionUID = 1720791472034561151L;

	/**
	 * Crea una excepción de infraestructura.
	 * 
	 * @param messageKey
	 *            La llave del mensaje a utilizar.
	 */
	public InfrastructureException(String messageKey) {

		super(messageKey);
	}

	/**
	 * Crea una excepción de infraestructura.
	 * 
	 * @param messageKey
	 *            La llave del mensaje a utilizar.
	 * @param cause
	 *            La causa de esta excepción.
	 */
	public InfrastructureException(String messageKey, Throwable cause) {

		super(messageKey, cause);
	}

	/**
	 * Crea una excepción de negocios.
	 * 
	 * @param cause
	 *            La causa de esta excepción
	 */
	public InfrastructureException(Throwable cause) {
		super(cause.getMessage(), cause);
	}
}
