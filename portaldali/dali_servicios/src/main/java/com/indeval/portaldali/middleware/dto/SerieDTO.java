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
public class SerieDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private EmisoraDTO emisora = null;
	
	private TipoValorDTO tipoValor = null;
	
	private long id = 0;
	private String serie;
	private String descripcion = null;

	/**
	 * 
	 */
	public SerieDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param serie
	 */
	public SerieDTO(long id, String serie) {
		super();
		this.id = id;
		this.serie = serie;
	}

	
	
	
	
	/**
	 * @param serie
	 * @param descripcion
	 */
	public SerieDTO(String serie, String descripcion) {
		super();
		this.serie = serie;
		this.descripcion = descripcion;
	}

	/**
	 * método para obtener el atributo id
	 * 
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Método para establecer el atributo id
	 * 
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Método para obtener el atributo serie
	 * 
	 * @return the serie
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * Método para establecer el atributo serie
	 * 
	 * @param serie the serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	/**
	 * Obtiene el campo emisora
	 * @return  emisora
	 */
	public EmisoraDTO getEmisora() {
		return emisora;
	}

	/**
	 * Asigna el valor del campo emisora
	 * @param emisora el valor de emisora a asignar
	 */
	public void setEmisora(EmisoraDTO emisora) {
		this.emisora = emisora;
	}

	/**
	 * Obtiene el campo tipoValor
	 * @return  tipoValor
	 */
	public TipoValorDTO getTipoValor() {
		return tipoValor;
	}

	/**
	 * Asigna el valor del campo tipoValor
	 * @param tipoValor el valor de tipoValor a asignar
	 */
	public void setTipoValor(TipoValorDTO tipoValor) {
		this.tipoValor = tipoValor;
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o){
		boolean result = false;
		if(o != null && o instanceof SerieDTO){
			result = ((SerieDTO)o).getSerie().equals(this.getSerie());
		}else{
			result = super.equals(o);
		}
		return result;
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

	@Override
	public String toString() {
		return "SerieDTO{" +
				"emisora=" + emisora +
				", tipoValor=" + tipoValor +
				", id=" + id +
				", serie='" + serie + '\'' +
				", descripcion='" + descripcion + '\'' +
				'}';
	}
}