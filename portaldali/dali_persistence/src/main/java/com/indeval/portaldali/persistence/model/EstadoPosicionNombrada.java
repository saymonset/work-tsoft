/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Tabla que contiene las posiciones de las cuentas nombradas con que inicia y finaliza cada ciclo. 
 * Solo muestra las posiciones que se hayan visto afectadas durante el ciclo.
 * @author RCHAVEZ
 * @version 1.0
 */
@Entity
@Table(name = "T_ESTADO_POSICIONES_NOMBRADA")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_ESTADO_POS_NOMBRADA", allocationSize = 1, initialValue = 1)
public class EstadoPosicionNombrada implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador del estado de la posici&oacute;n.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_ESTADO_POSICION_NOMBRADA")
	private BigInteger idEstadoPosicionNombrada;
	
	/**
	 * Posicion nombrada que le corresponde.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_POSICION", updatable = false, insertable = false)
	private PosicionNombrada posicionNombrada;
	
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
	 * Default constructor.
	 */
	public EstadoPosicionNombrada() {
	}
	/**
	 * Constructor
	 * @param idPosicion
	 * @param posicionInicialDisponible
	 * @param posicionInicialNoDisponible
	 * @param posicionFinalDisponible
	 * @param posicionFinalNoDisponible
	 * @param idCiclo
	 */
	public EstadoPosicionNombrada(Long idPosicion, BigInteger posicionInicialDisponible,
			BigInteger posicionInicialNoDisponible, BigInteger posicionFinalDisponible,
			BigInteger posicionFinalNoDisponible, Long idCiclo) {
		this.idPosicion = idPosicion;
		this.posicionInicialDisponible = posicionInicialDisponible;
		this.posicionInicialNoDisponible = posicionInicialNoDisponible;
		this.posicionFinalDisponible = posicionFinalDisponible;
		this.posicionFinalNoDisponible = posicionFinalNoDisponible;
		this.idCiclo = idCiclo;
	}
	/**
	 * @return the idCiclo
	 */
	public Long getIdCiclo() {
		return idCiclo;
	}
	/**
	 * @param idCiclo the idCiclo to set
	 */
	public void setIdCiclo(Long idCiclo) {
		this.idCiclo = idCiclo;
	}
	/**
	 * @return the idEstadoPosicionNombrada
	 */
	public BigInteger getIdEstadoPosicionNombrada() {
		return idEstadoPosicionNombrada;
	}
	/**
	 * @param idEstadoPosicionNombrada the idEstadoPosicionNombrada to set
	 */
	public void setIdEstadoPosicionNombrada(BigInteger idEstadoPosicionNombrada) {
		this.idEstadoPosicionNombrada = idEstadoPosicionNombrada;
	}
	/**
	 * @return the idPosicion
	 */
	public Long getIdPosicion() {
		return idPosicion;
	}
	/**
	 * @param idPosicion the idPosicion to set
	 */
	public void setIdPosicion(Long idPosicion) {
		this.idPosicion = idPosicion;
	}
	/**
	 * @return the posicionFinalDisponible
	 */
	public BigInteger getPosicionFinalDisponible() {
		return posicionFinalDisponible;
	}
	/**
	 * @param posicionFinalDisponible the posicionFinalDisponible to set
	 */
	public void setPosicionFinalDisponible(BigInteger posicionFinalDisponible) {
		this.posicionFinalDisponible = posicionFinalDisponible;
	}
	/**
	 * @return the posicionFinalNoDisponible
	 */
	public BigInteger getPosicionFinalNoDisponible() {
		return posicionFinalNoDisponible;
	}
	/**
	 * @param posicionFinalNoDisponible the posicionFinalNoDisponible to set
	 */
	public void setPosicionFinalNoDisponible(BigInteger posicionFinalNoDisponible) {
		this.posicionFinalNoDisponible = posicionFinalNoDisponible;
	}
	/**
	 * @return the posicionInicialDisponible
	 */
	public BigInteger getPosicionInicialDisponible() {
		return posicionInicialDisponible;
	}
	/**
	 * @param posicionInicialDisponible the posicionInicialDisponible to set
	 */
	public void setPosicionInicialDisponible(BigInteger posicionInicialDisponible) {
		this.posicionInicialDisponible = posicionInicialDisponible;
	}
	/**
	 * @return the posicionInicialNoDisponible
	 */
	public BigInteger getPosicionInicialNoDisponible() {
		return posicionInicialNoDisponible;
	}
	/**
	 * @param posicionInicialNoDisponible the posicionInicialNoDisponible to set
	 */
	public void setPosicionInicialNoDisponible(
			BigInteger posicionInicialNoDisponible) {
		this.posicionInicialNoDisponible = posicionInicialNoDisponible;
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(13, 23).append(idEstadoPosicionNombrada).toHashCode();
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
	   if (!(obj instanceof EstadoPosicionNombrada)) {
		     return false;
	   }
	   if (this == obj) {
		     return true;
	   }
	   EstadoPosicionNombrada rhs = (EstadoPosicionNombrada) obj;
	   return new EqualsBuilder()
	   		.append(idEstadoPosicionNombrada, rhs.getIdEstadoPosicionNombrada())
			.isEquals();
	}
	/**
	 * método para obtener el atributo posicionNombrada
	 * 
	 * @return the posicionNombrada
	 */
	public PosicionNombrada getPosicionNombrada() {
		return posicionNombrada;
	}
	/**
	 * método para establecer el atributo posicionNombrada
	 * 
	 * @param posicionNombrada the posicionNombrada to set
	 */
	public void setPosicionNombrada(PosicionNombrada posicionNombrada) {
		this.posicionNombrada = posicionNombrada;
	}
	/**
	 * método para obtener el atributo ciclo
	 * 
	 * @return the ciclo
	 */
	public Ciclo getCiclo() {
		return ciclo;
	}
	/**
	 * método para establecer el atributo ciclo
	 * 
	 * @param ciclo the ciclo to set
	 */
	public void setCiclo(Ciclo ciclo) {
		this.ciclo = ciclo;
	}
}