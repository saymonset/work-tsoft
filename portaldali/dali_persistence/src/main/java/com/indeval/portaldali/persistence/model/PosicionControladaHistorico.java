/**
 * Portal DALI
 * 
 * 2H Software SA
 *
 * PosicionControladaHistorico.java 
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

/**
 * Mapeo de la tabla T_POSICION_CONTROLADA_HIST.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
@Entity
@Table(name = "T_POSICION_CONTROLADA_H")
public class PosicionControladaHistorico implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Primary Key
	 */
	@Id
	private PosicionControladaHistoricoPK primaryKey;

	/**
	 * Identificador de la posici&oacute;n.
	 */
	@Column(name = "ID_POSICION_CONTROLADA", updatable = false, insertable = false)
	private BigInteger idPosicion;
	/**
	 * Cuenta controlada.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUENTA_CONTROLADA", updatable = false, insertable = false)
	private CuentaControlada cuentaControlada;
	/**
	 * Identificador de la cuenta controlada.
	 */
	@Column(name = "ID_CUENTA_CONTROLADA")
	private BigInteger idCuenta;
	/**
	 * B&oacute;veda.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_BOVEDA", updatable = false, insertable = false)
	private Boveda boveda;
	/**
	 * Identificador de la b&oacute;veda.
	 */
	@Column(name = "ID_BOVEDA")
	private BigInteger idBoveda;
	/**
	 * Emisi&oacute;n.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EMISION", updatable = false, insertable = false)
	private Emision emision;
	/**
	 * Identificador de la emisi&oacute;n.
	 */
	@Column(name = "ID_EMISION")
	private BigInteger idEmision;

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
	 * La posición no disponible de la posición.
	 */
	@Column(name = "POSICION")
	private BigInteger posicion;

	/**
	 * La fecha en que se registró el movimiento en la posición.
	 */
	@Column(name = "FECHA_CREACION", updatable = false, insertable = false)
	private Date fecha;

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
	 * Obtiene el valor del atributo cuentaControlada
	 * 
	 * @return el valor del atributo cuentaControlada
	 */
	public CuentaControlada getCuentaControlada() {
		return cuentaControlada;
	}

	/**
	 * Establece el valor del atributo cuentaControlada
	 * 
	 * @param cuentaControlada
	 *            el valor del atributo cuentaControlada a establecer.
	 */
	public void setCuentaControlada(CuentaControlada cuentaControlada) {
		this.cuentaControlada = cuentaControlada;
	}

	/**
	 * Obtiene el valor del atributo idCuenta
	 * 
	 * @return el valor del atributo idCuenta
	 */
	public BigInteger getIdCuenta() {
		return idCuenta;
	}

	/**
	 * Establece el valor del atributo idCuenta
	 * 
	 * @param idCuenta
	 *            el valor del atributo idCuenta a establecer
	 */
	public void setIdCuenta(BigInteger idCuenta) {
		this.idCuenta = idCuenta;
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
	 * Obtiene el valor del atributo idBoveda
	 * 
	 * @return el valor del atributo idBoveda
	 */
	public BigInteger getIdBoveda() {
		return idBoveda;
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
	 * Obtiene el valor del atributo posicion
	 * 
	 * @return el valor del atributo posicion
	 */
	public BigInteger getPosicion() {
		return posicion;
	}

	/**
	 * Establece el valor del atributo posicion
	 * 
	 * @param posicion
	 *            el valor del atributo posicion a establecer.
	 */
	public void setPosicion(BigInteger posicion) {
		this.posicion = posicion;
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
	 * @param primaryKey the primaryKey to set
	 */
	public void setPrimaryKey(PosicionControladaHistoricoPK primaryKey) {
		this.primaryKey = primaryKey;
	}

	/**
	 * @return the primaryKey
	 */
	public PosicionControladaHistoricoPK getPrimaryKey() {
		return primaryKey;
	}
}