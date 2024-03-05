/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * 27/02/2008
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;

/**
 * Data Transfer Object que representa un cupón de una operación.
 * 
 * @author Emigdio Hernández
 * @version 1.0
 * 
 */
public class CuponDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Identificador del cupón.
	 */
	private Long idCupon;

	/**
	 * Clave del cupón.
	 */
	private String claveCupon;

	/** La emisión relacionada con este cupón */
	private EmisionDTO emision;

	/**
	 * 
	 */
	public CuponDTO() {}
	
	
	
	
	/**
	 * @param idCupon
	 * @param claveCupon
	 */
	public CuponDTO(Long idCupon, String claveCupon) {
		super();
		this.idCupon = idCupon;
		this.claveCupon = claveCupon;
	}




	/**
	 * Obtiene el valor del atributo emision
	 * 
	 * @return el valor del atributo emision
	 */
	public EmisionDTO getEmision() {
		return emision;
	}

	/**
	 * Establece el valor del atributo emision
	 * 
	 * @param emision
	 *            el valor del atributo emision a establecer.
	 */
	public void setEmision(EmisionDTO emision) {
		this.emision = emision;
	}

	/**
	 * Obtiene el valor del atributo idCupon
	 * 
	 * @return el valor del atributo idCupon
	 */
	public Long getIdCupon() {
		return idCupon;
	}

	/**
	 * Establece el valor del atributo idCupon
	 * 
	 * @param idCupon
	 *            el valor del atributo idCupon a establecer.
	 */
	public void setIdCupon(Long idCupon) {
		this.idCupon = idCupon;
	}

	/**
	 * Obtiene el valor del atributo claveCupon
	 * 
	 * @return el valor del atributo claveCupon
	 */
	public String getClaveCupon() {
		return claveCupon;
	}

	/**
	 * Establece el valor del atributo claveCupon
	 * 
	 * @param claveCupon
	 *            el valor del atributo claveCupon a establecer.
	 */
	public void setClaveCupon(String claveCupon) {
		this.claveCupon = claveCupon;
	}

}
