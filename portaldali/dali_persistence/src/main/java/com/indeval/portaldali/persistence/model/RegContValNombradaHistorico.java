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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Mapeo de Hibernate de la tabla T_REG_CONTABLE_VAL_NOMBRADA_H
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
@Entity
@Table(name = "T_REG_CONTABLE_VAL_NOMBRADA_H")
public class RegContValNombradaHistorico implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador de la acci&oacute;n.
	 */
	@Id
	@Column(name = "ID_REGISTRO_CONTABLE_H")
	private BigInteger idRegistroContableHistorico;

	/**
	 * Posici&oacute;n
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns ({
        @JoinColumn(name="ID_POSICION", referencedColumnName = "ID_POSICION_NOMBRADA",updatable = false, insertable = false),
        @JoinColumn(name="FECHA_CREACION", referencedColumnName = "FECHA_CREACION",updatable = false, insertable = false)
    })
	private PosicionNombradaHistorico posicion;

	/**
	 * Identificador de la posici&oacute;n afectada.
	 */
	@Column(name = "ID_POSICION", updatable = false)
	private BigInteger idPosicion;

	/**
	 * Tipo de acci&oacutel;n aplicada a la posici&oacute;n.
	 */
	@Column(name = "ID_TIPO_ACCION", updatable = false)
	private TipoAccion tipoAccion;

	/**
	 * Cantidad aplicada a la posici&oacute;n.
	 */
	@Column(name = "CANTIDAD", updatable = false)
	private BigInteger cantidad;

	/**
	 * Fecha en que se afecto la posici&oacute;n.
	 */
	@Column(name = "FECHA", updatable = false)
	private Date fecha;

	/**
	 * Fecha en que se afecto la posici&oacute;n.
	 */
	@Column(name = "FECHA_CREACION", updatable = false)
	private Date fechaCreacion;

	/**
	 * Ciclo Operaci&oacute;n
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CICLO", updatable = false, insertable = false)
	private Ciclo ciclo;

	/**
	 * Identificador del ciclo durante el cual se realizo la afectaci&oacute;n.
	 */
	@Column(name = "ID_CICLO", updatable = false)
	private BigInteger idCiclo;

	/**
	 * Operaci&oacute;n
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_OPERACION", updatable = false, insertable = false)
	private OperacionNombradaHistorico operacion;

	/**
	 * Identificador de la operaci&oacute;n que origino la afectaci&oacute;n.
	 */
	@Column(name = "ID_OPERACION", updatable = false)
	private BigInteger idOperacion;

	/**
	 * Identificador de la cuenta de la posici&oacute;n afectada.
	 */
	@Column(name = "ID_CUENTA", updatable = false)
	private BigInteger idCuenta;

	/**
	 * Identifica si se afecto el disponible o el no disponible de la
	 * posici&oacute;n.
	 */
	@Column(name = "CARGO_A", updatable = false)
	private BigInteger cargoA;

	/**
	 * Identificador del registro contable de controladas donde participo este
	 * registro.
	 */
	@Column(name = "ID_REG_CONTABLE_CONTROLADA", updatable = false)
	private BigInteger idRegContableControlada;

	/**
	 * Obtiene el valor del atributo idRegistroContableHistorico
	 * 
	 * @return el valor del atributo idRegistroContableHistorico
	 */
	public BigInteger getIdRegistroContableHistorico() {
		return idRegistroContableHistorico;
	}

	/**
	 * Establece el valor del atributo idRegistroContableHistorico
	 * 
	 * @param idRegistroContableHistorico
	 *            el valor del atributo idRegistroContableHistorico a establecer
	 */
	public void setIdRegistroContableHistorico(BigInteger idRegistroContableHistorico) {
		this.idRegistroContableHistorico = idRegistroContableHistorico;
	}

	/**
	 * Obtiene el valor del atributo posicion
	 * 
	 * @return el valor del atributo posicion
	 */
	public PosicionNombradaHistorico getPosicion() {
		return posicion;
	}

	/**
	 * Establece el valor del atributo posicion
	 * 
	 * @param posicion
	 *            el valor del atributo posicion a establecer
	 */
	public void setPosicion(PosicionNombradaHistorico posicion) {
		this.posicion = posicion;
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
	 *            el valor del atributo idPosicion a establecer
	 */
	public void setIdPosicion(BigInteger idPosicion) {
		this.idPosicion = idPosicion;
	}

	/**
	 * Obtiene el valor del atributo tipoAccion
	 * 
	 * @return el valor del atributo tipoAccion
	 */
	public TipoAccion getTipoAccion() {
		return tipoAccion;
	}

	/**
	 * Establece el valor del atributo tipoAccion
	 * 
	 * @param tipoAccion
	 *            el valor del atributo tipoAccion a establecer
	 */
	public void setTipoAccion(TipoAccion tipoAccion) {
		this.tipoAccion = tipoAccion;
	}

	/**
	 * Obtiene el valor del atributo cantidad
	 * 
	 * @return el valor del atributo cantidad
	 */
	public BigInteger getCantidad() {
		return cantidad;
	}

	/**
	 * Establece el valor del atributo cantidad
	 * 
	 * @param cantidad
	 *            el valor del atributo cantidad a establecer
	 */
	public void setCantidad(BigInteger cantidad) {
		this.cantidad = cantidad;
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
	 *            el valor del atributo fecha a establecer
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Obtiene el valor del atributo fechaCreacion
	 * 
	 * @return el valor del atributo fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * Establece el valor del atributo fechaCreacion
	 * 
	 * @param fechaCreacion
	 *            el valor del atributo fechaCreacion a establecer
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
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
	public BigInteger getIdCiclo() {
		return idCiclo;
	}

	/**
	 * Establece el valor del atributo idCiclo
	 * 
	 * @param idCiclo
	 *            el valor del atributo idCiclo a establecer
	 */
	public void setIdCiclo(BigInteger idCiclo) {
		this.idCiclo = idCiclo;
	}

	/**
	 * Obtiene el valor del atributo operacion
	 * 
	 * @return el valor del atributo operacion
	 */
	public OperacionNombradaHistorico getOperacion() {
		return operacion;
	}

	/**
	 * Establece el valor del atributo operacion
	 * 
	 * @param operacion
	 *            el valor del atributo operacion a establecer
	 */
	public void setOperacion(OperacionNombradaHistorico operacion) {
		this.operacion = operacion;
	}

	/**
	 * Obtiene el valor del atributo idOperacion
	 * 
	 * @return el valor del atributo idOperacion
	 */
	public BigInteger getIdOperacion() {
		return idOperacion;
	}

	/**
	 * Establece el valor del atributo idOperacion
	 * 
	 * @param idOperacion
	 *            el valor del atributo idOperacion a establecer
	 */
	public void setIdOperacion(BigInteger idOperacion) {
		this.idOperacion = idOperacion;
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
	 * Obtiene el valor del atributo cargoA
	 * 
	 * @return el valor del atributo cargoA
	 */
	public BigInteger getCargoA() {
		return cargoA;
	}

	/**
	 * Establece el valor del atributo cargoA
	 * 
	 * @param cargoA
	 *            el valor del atributo cargoA a establecer
	 */
	public void setCargoA(BigInteger cargoA) {
		this.cargoA = cargoA;
	}

	/**
	 * Obtiene el valor del atributo idRegContableControlada
	 * 
	 * @return el valor del atributo idRegContableControlada
	 */
	public BigInteger getIdRegContableControlada() {
		return idRegContableControlada;
	}

	/**
	 * Establece el valor del atributo idRegContableControlada
	 * 
	 * @param idRegContableControlada
	 *            el valor del atributo idRegContableControlada a establecer
	 */
	public void setIdRegContableControlada(BigInteger idRegContableControlada) {
		this.idRegContableControlada = idRegContableControlada;
	}

}
