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
 * Tabla que contiene las posiciones de las cuentas controladas con que inicia y finaliza cada ciclo. 
 * Solo muestra las posiciones que se hayan visto afectadas durante el ciclo.
 * @author RCHAVEZ
 * @version 1.0
 */
@Entity
@Table(name = "T_ESTADO_POSICIONES_CONTROLADA")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_ESTADO_POS_CONTROLADA", allocationSize = 1, initialValue = 1)
public class EstadoPosicionControlada implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador del estado de la posici&oacute;n.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_ESTADO_POSICION_CONTROLADA")
	private BigInteger idEstadoPosicionControlada;
	/**
	 * Identificador de la posici&oacute;n afectada.
	 */
	@Column(name = "ID_POSICION")
	private Long idPosicion;
	
	/**
	 * Posicion controlada que le corresponde.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_POSICION", updatable = false, insertable = false)
	private PosicionControlada posicionControlada;
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
	 * Posici&oacute;n al inicio del ciclo.
	 */
	@Column(name = "POSICION_INICIAL")
	private BigInteger posicionInicial;
	/**
	 * Posici&oacute;n al final del ciclo.
	 */
	@Column(name = "POSICION_FINAL")
	private BigInteger posicionFinal;
	/**
	 * Default constructor.
	 */
	public EstadoPosicionControlada() {
	}
	/**
	 * Constructor
	 * @param idPosicion
	 * @param posicionInicial
	 * @param posicionFinal
	 * @param idCiclo
	 */
	public EstadoPosicionControlada(Long idPosicion, BigInteger posicionInicial,
			BigInteger posicionFinal, Long idCiclo) {
		this.idPosicion = idPosicion;
		this.posicionInicial = posicionInicial;
		this.posicionFinal = posicionFinal;
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
	 * @return the idEstadoPosicionControlada
	 */
	public BigInteger getIdEstadoPosicionControlada() {
		return idEstadoPosicionControlada;
	}
	/**
	 * @param idEstadoPosicionControlada the idEstadoPosicionControlada to set
	 */
	public void setIdEstadoPosicionControlada(BigInteger idEstadoPosicionControlada) {
		this.idEstadoPosicionControlada = idEstadoPosicionControlada;
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
	 * @return the posicionFinal
	 */
	public BigInteger getPosicionFinal() {
		return posicionFinal;
	}
	/**
	 * @param posicionFinal the posicionFinal to set
	 */
	public void setPosicionFinal(BigInteger posicionFinal) {
		this.posicionFinal = posicionFinal;
	}
	/**
	 * @return the posicionInicial
	 */
	public BigInteger getPosicionInicial() {
		return posicionInicial;
	}
	/**
	 * @param posicionInicial the posicionInicial to set
	 */
	public void setPosicionInicial(BigInteger posicionInicial) {
		this.posicionInicial = posicionInicial;
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(13, 23).append(idEstadoPosicionControlada).toHashCode();
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
	   if (obj instanceof EstadoPosicionControlada == false) {
		     return false;
	   }
	   if (this == obj) {
		     return true;
	   }
	   EstadoPosicionControlada rhs = (EstadoPosicionControlada) obj;
	   return new EqualsBuilder()
	   		.append(idEstadoPosicionControlada, rhs.getIdEstadoPosicionControlada())
			.isEquals();
	}
	/**
	 * Obtiene el campo posicionControlada
	 * @return  posicionControlada
	 */
	public PosicionControlada getPosicionControlada() {
		return posicionControlada;
	}
	/**
	 * Asigna el valor del campo posicionControlada
	 * @param posicionControlada el valor de posicionControlada a asignar
	 */
	public void setPosicionControlada(PosicionControlada posicionControlada) {
		this.posicionControlada = posicionControlada;
	}
	/**
	 * Obtiene el atributo ciclo
	 *
	 * @return El atrubuto ciclo
	 */
	public Ciclo getCiclo() {
		return ciclo;
	}
	/**
	 * Establece la propiedad ciclo
	 *
	 * @param ciclo el campo ciclo a establecer
	 */
	public void setCiclo(Ciclo ciclo) {
		this.ciclo = ciclo;
	}
}