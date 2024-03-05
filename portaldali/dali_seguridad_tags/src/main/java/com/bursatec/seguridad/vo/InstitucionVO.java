/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.bursatec.seguridad.vo;

import java.io.Serializable;

/**
 * DTO que representa los datos de una institución
 * @author Emigdio
 *
 */
public class InstitucionVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String clave;
    
    private String folio;
    
    private Long id;
    
    private String nombre;
    
    private String origen;
    
    private boolean primaria;

	/**
	 * Obtiene el campo clave
	 * @return  clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * Asigna el valor del campo clave
	 * @param clave el valor de clave a asignar
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}

	/**
	 * Obtiene el campo folio
	 * @return  folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * Asigna el valor del campo folio
	 * @param folio el valor de folio a asignar
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * Obtiene el campo id
	 * @return  id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Asigna el valor del campo id
	 * @param id el valor de id a asignar
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el campo nombre
	 * @return  nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Asigna el valor del campo nombre
	 * @param nombre el valor de nombre a asignar
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene el campo origen
	 * @return  origen
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * Asigna el valor del campo origen
	 * @param origen el valor de origen a asignar
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * Obtiene el campo primaria
	 * @return  primaria
	 */
	public boolean isPrimaria() {
		return primaria;
	}

	/**
	 * Asigna el valor del campo primaria
	 * @param primaria el valor de primaria a asignar
	 */
	public void setPrimaria(boolean primaria) {
		this.primaria = primaria;
	}

	
}
