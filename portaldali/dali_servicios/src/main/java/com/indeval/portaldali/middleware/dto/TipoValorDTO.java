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
 * @author Emigdio Hernández
 * @version 1.0
 *
 */
public class TipoValorDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private MercadoDTO mercado = new MercadoDTO();

	private long id = 0;
	private String descripcion = null;
	private String claveTipoValor = null;
	
	
	
	

	public TipoValorDTO() {
		super();
	}

	public TipoValorDTO(long id, String claveTipoValor) {
		super();
		this.id = id;
		this.claveTipoValor = claveTipoValor;
	}

	/**
	 * Obtiene el campo id
	 * @return  id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Asigna el valor del campo id
	 * @param id el valor de id a asignar
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * Obtiene el campo descripcion
	 * @return  descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	
	/**
	 * Asigna el valor del campo descripcion
	 * @param descripcion el valor de descripcion a asignar
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el campo mercado
	 * @return  mercado
	 */
	public MercadoDTO getMercado() {
		return mercado;
	}

	/**
	 * Asigna el valor del campo mercado
	 * @param mercado el valor de mercado a asignar
	 */
	public void setMercado(MercadoDTO mercado) {
		this.mercado = mercado;
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o){
		boolean result = false;
		if(o != null && o instanceof TipoValorDTO){
			result = ((TipoValorDTO)o).getId() == this.getId();
		}else{
			result = super.equals(o);
		}
		return result;
	}

	/**
	 * Obtiene el campo claveTipoValor
	 * @return  claveTipoValor
	 */
	public String getClaveTipoValor() {
		return claveTipoValor;
	}

	/**
	 * Asigna el valor del campo claveTipoValor
	 * @param claveTipoValor el valor de claveTipoValor a asignar
	 */
	public void setClaveTipoValor(String claveTipoValor) {
		this.claveTipoValor = claveTipoValor;
	}

	@Override
	public String toString() {
		return "TipoValorDTO{" +
				"mercado=" + mercado +
				", id=" + id +
				", descripcion='" + descripcion + '\'' +
				", claveTipoValor='" + claveTipoValor + '\'' +
				'}';
	}
}