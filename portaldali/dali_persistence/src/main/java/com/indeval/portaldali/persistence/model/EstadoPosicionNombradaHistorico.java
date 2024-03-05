/**
 * Sistema Portal DALI
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Nov 18, 2008
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Mapeo de Hibernate para la clase T_ESTADO_POSICIONES_NOMBRADA_H
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
@Entity
@Table(name = "T_ESTADO_POSICIONES_NOMBRADA_H")
public class EstadoPosicionNombradaHistorico implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador del estado de la posici&oacute;n.
	 */
	@Id
	@Column(name = "ID_ESTADO_POSICION_NOMBRADA_H")
	private BigInteger idEstadoPosicionNombradaHistorico;

	/**
	 * Posicion nombrada que le corresponde.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns ({
        @JoinColumn(name="ID_POSICION", referencedColumnName = "ID_POSICION_NOMBRADA",updatable = false, insertable = false,nullable=true),
        @JoinColumn(name="FECHA_CREACION", referencedColumnName = "FECHA_CREACION",updatable = false, insertable = false,nullable=true)
    })
	private PosicionNombradaHistorico posicionNombrada;

	/**
	 * Identificador de la posici&oacute;n afectada.
	 */
	@Column(name = "ID_POSICION")
	private Long idPosicion;

	/**
	 * Ciclo durante el cual se realizo la afectaci&oacute;n.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CICLO", updatable = false, insertable = false)
	private Ciclo ciclo;

	/**
	 * Identificador del ciclo durante el cual se realizo la afectaci&oacute;n.
	 */
	@Column(name = "ID_CICLO")
	private Long idCiclo;
	/**
	 * Posici&oacute;n disponible al inicio del ciclo.
	 */
	@Column(name = "POSICION_INICIAL_DISPONIBLE")
	private BigInteger posicionInicialDisponible;
	/**
	 * Posici&oacute;n no disponible al inicio del ciclo.
	 */
	@Column(name = "POSICION_INICIAL_NO_DISPONIBLE")
	private BigInteger posicionInicialNoDisponible;
	/**
	 * Posici&oacute;n dispobible al final del ciclo.
	 */
	@Column(name = "POSICION_FINAL_DISPONIBLE")
	private BigInteger posicionFinalDisponible;
	/**
	 * Posici&oacute;n no disponible al final del ciclo.
	 */
	@Column(name = "POSICION_FINAL_NO_DISPONIBLE")
	private BigInteger posicionFinalNoDisponible;

	/**
	 * Obtiene el valor del atributo idEstadoPosicionNombradaHistorico
	 * 
	 * @return el valor del atributo idEstadoPosicionNombradaHistorico
	 */
	public BigInteger getIdEstadoPosicionNombradaHistorico() {
		return idEstadoPosicionNombradaHistorico;
	}

	/**
	 * Establece el valor del atributo idEstadoPosicionNombradaHistorico
	 * 
	 * @param idEstadoPosicionNombradaHistorico
	 *            el valor del atributo idEstadoPosicionNombradaHistorico a
	 *            establecer
	 */
	public void setIdEstadoPosicionNombradaHistorico(BigInteger idEstadoPosicionNombradaHistorico) {
		this.idEstadoPosicionNombradaHistorico = idEstadoPosicionNombradaHistorico;
	}

	/**
	 * Obtiene el valor del atributo posicionNombrada
	 * 
	 * @return el valor del atributo posicionNombrada
	 */
	public PosicionNombradaHistorico getPosicionNombrada() {
		return posicionNombrada;
	}

	/**
	 * Establece el valor del atributo posicionNombrada
	 * 
	 * @param posicionNombrada
	 *            el valor del atributo posicionNombrada a establecer
	 */
	public void setPosicionNombrada(PosicionNombradaHistorico posicionNombrada) {
		this.posicionNombrada = posicionNombrada;
	}

	/**
	 * Obtiene el valor del atributo idPosicion
	 * 
	 * @return el valor del atributo idPosicion
	 */
	public Long getIdPosicion() {
		return idPosicion;
	}

	/**
	 * Establece el valor del atributo idPosicion
	 * 
	 * @param idPosicion
	 *            el valor del atributo idPosicion a establecer
	 */
	public void setIdPosicion(Long idPosicion) {
		this.idPosicion = idPosicion;
	}

	/**
	 * Obtiene el valor del atributo ciclo
	 * 
	 * @return el valor del atributo ciclo
	 */
	public Ciclo getCiclo() {
		return ciclo;
	}

	/**
	 * Establece el valor del atributo ciclo
	 * 
	 * @param ciclo
	 *            el valor del atributo ciclo a establecer
	 */
	public void setCiclo(Ciclo ciclo) {
		this.ciclo = ciclo;
	}

	/**
	 * Obtiene el valor del atributo idCiclo
	 * 
	 * @return el valor del atributo idCiclo
	 */
	public Long getIdCiclo() {
		return idCiclo;
	}

	/**
	 * Establece el valor del atributo idCiclo
	 * 
	 * @param idCiclo
	 *            el valor del atributo idCiclo a establecer
	 */
	public void setIdCiclo(Long idCiclo) {
		this.idCiclo = idCiclo;
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
	 * @param posicionInicialDisponible
	 *            el valor del atributo posicionInicialDisponible a establecer
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
	 * @param posicionInicialNoDisponible
	 *            el valor del atributo posicionInicialNoDisponible a establecer
	 */
	public void setPosicionInicialNoDisponible(BigInteger posicionInicialNoDisponible) {
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
	 * @param posicionFinalDisponible
	 *            el valor del atributo posicionFinalDisponible a establecer
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
	 * @param posicionFinalNoDisponible
	 *            el valor del atributo posicionFinalNoDisponible a establecer
	 */
	public void setPosicionFinalNoDisponible(BigInteger posicionFinalNoDisponible) {
		this.posicionFinalNoDisponible = posicionFinalNoDisponible;
	}

}
