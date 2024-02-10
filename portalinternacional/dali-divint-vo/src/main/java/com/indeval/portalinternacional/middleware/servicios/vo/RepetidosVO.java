package com.indeval.portalinternacional.middleware.servicios.vo;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.servicios.modelo.AbstractBaseDTO;
/**
 * 
 * @author Oscar Garcia Granados
 *
 */
public class RepetidosVO extends AbstractBaseDTO {

	/**
     * Constante de serializacion 
     */
    private static final long serialVersionUID = 1L;
    
    private String nombre;
    private boolean esModificable;
 
   	/**
	 * 
	 * @return boolean
	 */
	public boolean isEsModificable() {
		return esModificable;
	}

	/**
	 * 
	 * @return String
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * 
	 * @param esModificable
	 */
	public void setEsModificable(boolean esModificable) {
		this.esModificable = esModificable;
	}

	/**
	 * 
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
     * 
     */
	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub

	}

}
