/**
 * Portal DALI
 * 
 * 2H Software SA
 *
 * PosicionNombradaHistorico.java 
 *
 * Creado el: Sep 14, 2008
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Mapeo de la tabla T_POSICION_NOMBRADA_H
 * 
 * @author Jose Antonio Huizar Moreno
 * @version 1.0
 * 
 */
@Entity
@Table(name = "T_POSICION_NOMBRADA_H")
public class PosicionNombradaHistorico implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Llave primaria compuesta
	 */
	@Id
	private PosicionNombradaHistoricoPK primaryKey;
	
	/**
	 * El identificador de la posición en el histórico.
	 */
	@Column(name = "ID_POSICION_NOMBRADA_H")
	private BigInteger idPosicionH;
	
	/**
	 * El identificador de la posición asociada al histórico.
	 */
	@Column(name = "ID_POSICION_NOMBRADA",updatable=false,insertable=false)
	private BigInteger idPosicion;
	
	/**
	 * El objeto de la posición asociada al histórico.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_POSICION_NOMBRADA", updatable = false, insertable = false)
	private PosicionNombrada posicion;

	/**
	 * El identificador de la cuenta asociada a la posición.
	 */
	@Column(name = "ID_CUENTA_NOMBRADA")
	private BigInteger idCuentaNombrada;

	/**
	 * La cuenta nombrada con la posición.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUENTA_NOMBRADA", updatable = false, insertable = false)
	private CuentaNombrada cuentaNombrada;

	/**
	 * El identificador de la bóveda asociada a la posición.
	 */
	@Column(name = "ID_BOVEDA")
	private BigInteger idBoveda;

	/**
	 * La bóveda asociada con la posición.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_BOVEDA", updatable = false, insertable = false)
	private Boveda boveda;

	/**
	 * La posición disponible de la posición.
	 */
	@Column(name = "POSICION_DISPONIBLE")
	private BigInteger posicionDisponible;

	/**
	 * La posición no disponible de la posición.
	 */
	@Column(name = "POSICION_NO_DISPONIBLE")
	private BigInteger posicionNoDisponible;

	/**
	 * La fecha en que se registró el movimiento en la posición.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_CREACION",updatable=false,insertable=false)
	private Date fecha;

	/**
	 * El identificador del cupón asociado a la posición.
	 */
	@Column(name = "ID_CUPON")
	private BigInteger idCupon;

	/**
	 * El cupón ligado a la posición.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUPON", updatable = false, insertable = false)
	private Cupon cupon;

	/**
	 * El identificador de la emisión ligada a la posición.
	 */
	@Column(name = "ID_EMISION")
	private BigInteger idEmision;

	/**
	 * La emisión ligada a la posición.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EMISION", updatable = false, insertable = false)
	private Emision emision;

	/**
	 * @param idPosicionH the idPosicionH to set
	 */
	public void setIdPosicionH(BigInteger idPosicionH) {
		this.idPosicionH = idPosicionH;
	}

	/**
	 * @return the idPosicionH
	 */
	public BigInteger getIdPosicionH() {
		return idPosicionH;
	}

	/**
	 * Obtiene el valor del atributo idPosicion
	 * 
	 * @return el valor del atributo idPosicion
	 */
	public BigInteger getIdPosicion() {
		return idPosicion;
	}

	/**
	 * Establece el valor del atributo idPosicion
	 * 
	 * @param idPosicion
	 *            el valor del atributo idPosicion a establecer.
	 */
	public void setIdPosicion(BigInteger idPosicion) {
		this.idPosicion = idPosicion;
	}

	/**
	 * @param posicion the posicion to set
	 */
	public void setPosicion(PosicionNombrada posicion) {
		this.posicion = posicion;
	}

	/**
	 * @return the posicion
	 */
	public PosicionNombrada getPosicion() {
		return posicion;
	}

	/**
	 * Obtiene el valor del atributo idCuentaNombrada
	 * 
	 * @return el valor del atributo idCuentaNombrada
	 */
	public BigInteger getIdCuentaNombrada() {
		return idCuentaNombrada;
	}

	/**
	 * Establece el valor del atributo idCuentaNombrada
	 * 
	 * @param idCuentaNombrada
	 *            el valor del atributo idCuentaNombrada a establecer.
	 */
	public void setIdCuentaNombrada(BigInteger idCuentaNombrada) {
		this.idCuentaNombrada = idCuentaNombrada;
	}

	/**
	 * Obtiene el valor del atributo cuentaNombrada
	 * 
	 * @return el valor del atributo cuentaNombrada
	 */
	public CuentaNombrada getCuentaNombrada() {
		return cuentaNombrada;
	}

	/**
	 * Establece el valor del atributo cuentaNombrada
	 * 
	 * @param cuentaNombrada
	 *            el valor del atributo cuentaNombrada a establecer.
	 */
	public void setCuentaNombrada(CuentaNombrada cuentaNombrada) {
		this.cuentaNombrada = cuentaNombrada;
	}

	/**
	 * Obtiene el valor del atributo idBoveda
	 * 
	 * @return el valor del atributo idBoveda
	 */
	public BigInteger getIdBoveda() {
		return idBoveda;
	}

	/**
	 * Obtiene el valor del atributo idEmision
	 * 
	 * @return el valor del atributo idEmision
	 */
	public BigInteger getIdEmision() {
		return idEmision;
	}

	/**
	 * Establece el valor del atributo idEmision
	 * 
	 * @param idEmision
	 *            el valor del atributo idEmision a establecer.
	 */
	public void setIdEmision(BigInteger idEmision) {
		this.idEmision = idEmision;
	}

	/**
	 * Obtiene el valor del atributo emision
	 * 
	 * @return el valor del atributo emision
	 */
	public Emision getEmision() {
		return emision;
	}

	/**
	 * Establece el valor del atributo emision
	 * 
	 * @param emision
	 *            el valor del atributo emision a establecer.
	 */
	public void setEmision(Emision emision) {
		this.emision = emision;
	}

	/**
	 * Establece el valor del atributo idBoveda
	 * 
	 * @param idBoveda
	 *            el valor del atributo idBoveda a establecer.
	 */
	public void setIdBoveda(BigInteger idBoveda) {
		this.idBoveda = idBoveda;
	}

	/**
	 * Obtiene el valor del atributo boveda
	 * 
	 * @return el valor del atributo boveda
	 */
	public Boveda getBoveda() {
		return boveda;
	}

	/**
	 * Establece el valor del atributo boveda
	 * 
	 * @param boveda
	 *            el valor del atributo boveda a establecer.
	 */
	public void setBoveda(Boveda boveda) {
		this.boveda = boveda;
	}

	/**
	 * Obtiene el valor del atributo posicionDisponible
	 * 
	 * @return el valor del atributo posicionDisponible
	 */
	public BigInteger getPosicionDisponible() {
		return posicionDisponible;
	}

	/**
	 * Establece el valor del atributo posicionDisponible
	 * 
	 * @param posicionDisponible
	 *            el valor del atributo posicionDisponible a establecer.
	 */
	public void setPosicionDisponible(BigInteger posicionDisponible) {
		this.posicionDisponible = posicionDisponible;
	}

	/**
	 * Obtiene el valor del atributo posicionNoDisponible
	 * 
	 * @return el valor del atributo posicionNoDisponible
	 */
	public BigInteger getPosicionNoDisponible() {
		return posicionNoDisponible;
	}

	/**
	 * Establece el valor del atributo posicionNoDisponible
	 * 
	 * @param posicionNoDisponible
	 *            el valor del atributo posicionNoDisponible a establecer.
	 */
	public void setPosicionNoDisponible(BigInteger posicionNoDisponible) {
		this.posicionNoDisponible = posicionNoDisponible;
	}

	/**
	 * Obtiene el valor del atributo fecha
	 * 
	 * @return el valor del atributo fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Establece el valor del atributo fecha
	 * 
	 * @param fecha
	 *            el valor del atributo fecha a establecer.
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Obtiene el valor del atributo idCupon
	 * 
	 * @return el valor del atributo idCupon
	 */
	public BigInteger getIdCupon() {
		return idCupon;
	}

	/**
	 * Establece el valor del atributo idCupon
	 * 
	 * @param idCupon
	 *            el valor del atributo idCupon a establecer.
	 */
	public void setIdCupon(BigInteger idCupon) {
		this.idCupon = idCupon;
	}

	/**
	 * Obtiene el valor del atributo cupon
	 * 
	 * @return el valor del atributo cupon
	 */
	public Cupon getCupon() {
		return cupon;
	}

	/**
	 * Establece el valor del atributo cupon
	 * 
	 * @param cupon
	 *            el valor del atributo cupon a establecer.
	 */
	public void setCupon(Cupon cupon) {
		this.cupon = cupon;
	}
	
	/**
	 * @return the primaryKey
	 */
	public PosicionNombradaHistoricoPK getPrimaryKey() {
		return primaryKey;
	}

	/**
	 * @param primaryKey the primaryKey to set
	 */
	public void setPrimaryKey(PosicionNombradaHistoricoPK primaryKey) {
		this.primaryKey = primaryKey;
	}
}
