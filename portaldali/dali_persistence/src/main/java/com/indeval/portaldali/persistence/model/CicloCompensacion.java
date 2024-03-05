/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Tabla que guarda los mensajes de entrada que el preliquidador manda al compensador y
 * los mensajes de salida que recibe del compensador.
 * @author RCHAVEZ
 */
@Entity
@Table(name = "T_CICLO_COMPENSACION")
public class CicloCompensacion {

	/**
	 * Identificador del ciclo.
	 */
	@Id
	@Column(name = "ID_CICLO")
	private BigInteger idCiclo;
	/**
	 * Mensaje de entrada del compensador.
	 */
	@Column(name = "MENSAJE_ENTRADA_COMPENSADOR")
	private char[] mensajeEntrada;
	/**
	 * Mensaje de salida del compensador.
	 */
	@Column(name = "MENSAJE_SALIDA_COMPENSADOR")
	private char[] mensajeSalida;
	/**
	 * @return the idCiclo
	 */
	public BigInteger getIdCiclo() {
		return idCiclo;
	}
	/**
	 * @param idCiclo the idCiclo to set
	 */
	public void setIdCiclo(BigInteger idCiclo) {
		this.idCiclo = idCiclo;
	}
	/**
	 * @return the mensajeEntrada
	 */
	public char[] getMensajeEntrada() {
		return mensajeEntrada;
	}
	/**
	 * @param mensajeEntrada the mensajeEntrada to set
	 */
	public void setMensajeEntrada(char[] mensajeEntrada) {
		this.mensajeEntrada = mensajeEntrada;
	}
	/**
	 * @return the mensajeSalida
	 */
	public char[] getMensajeSalida() {
		return mensajeSalida;
	}
	/**
	 * @param mensajeSalida the mensajeSalida to set
	 */
	public void setMensajeSalida(char[] mensajeSalida) {
		this.mensajeSalida = mensajeSalida;
	}
}
