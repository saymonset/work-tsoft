/**
 * CMMV - Portal Dali
 * Copyrigth (c) 2016 CMMV. All Rights Reserved.
 */
package com.indeval.portaldali.modelo.to.estadocuenta;

import java.io.Serializable;

/**
 * Transfer object que representa los totales de la consulta de posicion.
 * 
 * @author Pablo Balderas
 */
public class TotalesPosicionTO implements Serializable {

	/** Id para la serializacion */
	private static final long serialVersionUID = -3780287354556421966L;
	private Number posicionTotal;
	private Number posicionTotalDisponible;
	private Number posicionTotalNoDisponible;
	private Number valuacionTotal;
	
	/**
	 * Constructor de la clase.
	 */
	public TotalesPosicionTO() {
		this.posicionTotal = 0;
		this.posicionTotalDisponible = 0;
		this.posicionTotalNoDisponible = 0;
		this.valuacionTotal = 0;
	}
	
	
	/**
	 * Método para obtener el atributo posicionTotal
	 * @return El atributo posicionTotal
	 */
	public Number getPosicionTotal() {
		return posicionTotal;
	}
	/**
	 * Método para establecer el atributo posicionTotal
	 * @param posicionTotal El valor del atributo posicionTotal a establecer.
	 */
	public void setPosicionTotal(Number posicionTotal) {
		this.posicionTotal = posicionTotal;
	}
	/**
	 * Método para obtener el atributo posicionTotalDisponible
	 * @return El atributo posicionTotalDisponible
	 */
	public Number getPosicionTotalDisponible() {
		return posicionTotalDisponible;
	}
	/**
	 * Método para establecer el atributo posicionTotalDisponible
	 * @param posicionTotalDisponible El valor del atributo posicionTotalDisponible a establecer.
	 */
	public void setPosicionTotalDisponible(Number posicionTotalDisponible) {
		this.posicionTotalDisponible = posicionTotalDisponible;
	}
	/**
	 * Método para obtener el atributo posicionTotalNoDisponible
	 * @return El atributo posicionTotalNoDisponible
	 */
	public Number getPosicionTotalNoDisponible() {
		return posicionTotalNoDisponible;
	}
	/**
	 * Método para establecer el atributo posicionTotalNoDisponible
	 * @param posicionTotalNoDisponible El valor del atributo posicionTotalNoDisponible a establecer.
	 */
	public void setPosicionTotalNoDisponible(Number posicionTotalNoDisponible) {
		this.posicionTotalNoDisponible = posicionTotalNoDisponible;
	}
	/**
	 * Método para obtener el atributo valuacionTotal
	 * @return El atributo valuacionTotal
	 */
	public Number getValuacionTotal() {
		return valuacionTotal;
	}
	/**
	 * Método para establecer el atributo valuacionTotal
	 * @param valuacionTotal El valor del atributo valuacionTotal a establecer.
	 */
	public void setValuacionTotal(Number valuacionTotal) {
		this.valuacionTotal = valuacionTotal;
	}
	
	
}
