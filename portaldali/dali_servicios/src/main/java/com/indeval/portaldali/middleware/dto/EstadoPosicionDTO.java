/**
 * 2H Software SA de CV
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Fecha de creación: Dec 20, 2007
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Data Transfer Object que representa el estado de una posición en un ciclo.
 * 
 * @author Sandra Flores Arrieta
 * @version 1.0
 * 
 */
public class EstadoPosicionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Identificador del estado de la posici&oacute;n.
	 */
	private Long idEstadoPosicion;

	/**
	 * Posici&oacute;n disponible al inicio del ciclo.
	 */
	private BigInteger posicionInicialDisponible;
	
	/**
	 * Posici&oacute;n no disponible al inicio del ciclo.
	 */
	private BigInteger posicionInicialNoDisponible;
	
	/**
	 * Posici&oacute;n dispobible al final del ciclo.
	 */
	private BigInteger posicionFinalDisponible;
	
	/**
	 * Posici&oacute;n no disponible al final del ciclo.
	 */
	private BigInteger posicionFinalNoDisponible;
	
	/** Posición al final del ciclo */
	private BigInteger posicionFinal;
	
	/** Posición al inicio del ciclo */
	private BigInteger posicionInicial;
	
	/**
	 * Obtiene el valor del atributo posicionFinal
	 * 
	 * @return el valor del atributo posicionFinal
	 */
	public BigInteger getPosicionFinal() {
		return posicionFinal;
	}

	/**
	 * Establece el valor del atributo posicionFinal
	 *
	 * @param posicionFinal el valor del atributo posicionFinal a establecer
	 */
	public void setPosicionFinal(BigInteger posicionFinal) {
		this.posicionFinal = posicionFinal;
	}

	/**
	 * Obtiene el valor del atributo posicionInicial
	 * 
	 * @return el valor del atributo posicionInicial
	 */
	public BigInteger getPosicionInicial() {
		return posicionInicial;
	}

	/**
	 * Establece el valor del atributo posicionInicial
	 *
	 * @param posicionInicial el valor del atributo posicionInicial a establecer
	 */
	public void setPosicionInicial(BigInteger posicionInicial) {
		this.posicionInicial = posicionInicial;
	}

	/**
	 * Obtiene el valor del atributo idEstadoPosicionNombrada
	 *
	 * @return el valor del atributo idEstadoPosicionNombrada
	 */
	public Long getIdEstadoPosicion() {
		return idEstadoPosicion;
	}

	/**
	 * Establece el valor del atributo idEstadoPosicionNombrada
	 *
	 * @param idEstadoPosicionNombrada el valor del atributo idEstadoPosicionNombrada a establecer.
	 */
	public void setIdEstadoPosicion(Long idEstadoPosicion) {
		this.idEstadoPosicion = idEstadoPosicion;
	}

	/**
	 * Obtiene el valor del atributo posicionInicialDisponible
	 *
	 * @return el valor del atributo posicionInicialDisponible
	 */
	public BigInteger getPosicionInicialDisponible() {
		return posicionInicialDisponible;
	}

	/**
	 * Establece el valor del atributo posicionInicialDisponible
	 *
	 * @param posicionInicialDisponible el valor del atributo posicionInicialDisponible a establecer.
	 */
	public void setPosicionInicialDisponible(BigInteger posicionInicialDisponible) {
		this.posicionInicialDisponible = posicionInicialDisponible;
	}

	/**
	 * Obtiene el valor del atributo posicionInicialNoDisponible
	 *
	 * @return el valor del atributo posicionInicialNoDisponible
	 */
	public BigInteger getPosicionInicialNoDisponible() {
		return posicionInicialNoDisponible;
	}

	/**
	 * Establece el valor del atributo posicionInicialNoDisponible
	 *
	 * @param posicionInicialNoDisponible el valor del atributo posicionInicialNoDisponible a establecer.
	 */
	public void setPosicionInicialNoDisponible(
			BigInteger posicionInicialNoDisponible) {
		this.posicionInicialNoDisponible = posicionInicialNoDisponible;
	}

	/**
	 * Obtiene el valor del atributo posicionFinalDisponible
	 *
	 * @return el valor del atributo posicionFinalDisponible
	 */
	public BigInteger getPosicionFinalDisponible() {
		return posicionFinalDisponible;
	}

	/**
	 * Establece el valor del atributo posicionFinalDisponible
	 *
	 * @param posicionFinalDisponible el valor del atributo posicionFinalDisponible a establecer.
	 */
	public void setPosicionFinalDisponible(BigInteger posicionFinalDisponible) {
		this.posicionFinalDisponible = posicionFinalDisponible;
	}

	/**
	 * Obtiene el valor del atributo posicionFinalNoDisponible
	 *
	 * @return el valor del atributo posicionFinalNoDisponible
	 */
	public BigInteger getPosicionFinalNoDisponible() {
		return posicionFinalNoDisponible;
	}

	/**
	 * Establece el valor del atributo posicionFinalNoDisponible
	 *
	 * @param posicionFinalNoDisponible el valor del atributo posicionFinalNoDisponible a establecer.
	 */
	public void setPosicionFinalNoDisponible(BigInteger posicionFinalNoDisponible) {
		this.posicionFinalNoDisponible = posicionFinalNoDisponible;
	}

}
