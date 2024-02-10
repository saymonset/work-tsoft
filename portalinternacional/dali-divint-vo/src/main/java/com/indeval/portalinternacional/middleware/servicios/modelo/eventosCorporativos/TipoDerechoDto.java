/**
 * 
 */
package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;


/**
 * @author eperez
 *
 */

public class TipoDerechoDto {

	private String tipoDerecho;
	private String activo;
	private String tipo;
	private String idTipoDerecho;
	private String nombre;

	
	/**
	 * @return the activo
	 */
	public String getActivo() {
		return activo;
	}
	/**
	 * @param activo the activo to set
	 */
	public void setActivo(String activo) {
		this.activo = activo;
	}
	/**
	 * @return the tipoDerecho
	 */
	public String getTipoDerecho() {
		return tipoDerecho;
	}
	/**
	 * @param tipoDerecho the tipoDerecho to set
	 */
	public void setTipoDerecho(String tipoDerecho) {
		this.tipoDerecho = tipoDerecho;
	}
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return the idTipoDerecho
	 */
	public String getIdTipoDerecho() {
		return idTipoDerecho;
	}
	/**
	 * @param idTipoDerecho the idTipoDerecho to set
	 */
	public void setIdTipoDerecho(String idTipoDerecho) {
		this.idTipoDerecho = idTipoDerecho;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TipoDerechoDto [tipoDerecho=" + tipoDerecho + ", activo="
				+ activo + ", tipo=" + tipo + ", idTipoDerecho="
				+ idTipoDerecho + "]";
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



}
