/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Bitacora de movimientos al fideicomiso.
 * 
 * @author RCHAVEZ
 * @version 1.0
 */
@Entity
@Table(name = "T_MOVIMIENTO_FIDEICOMISO")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_MOVIMIENTO_FIDEICOMISO", allocationSize = 1, initialValue = 1)
public class MovimientoFideicomiso implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador del movimiento en el fideicomiso.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_MOVIMIENTO_FIDEICOMISO")
	private BigInteger idMovimientoFideicomiso;
	/**
	 * Identificador del ciclo.
	 */
	@Column(name = "ID_CICLO")
	private BigInteger idCiclo;
	/**
	 * Identificador de la instituci&oacute;n.
	 */
	@Column(name = "ID_INSTITUCION")
	private BigInteger idInstitucion;
	/**
	 * Identificador de la cuenta controlada.
	 */
	@Column(name = "ID_CUENTA_CONTROLADA")
	private BigInteger idCuentaControlada;
	/**
	 * Importe de la operaci&oacute;n.
	 */
	@Column(name = "IMPORTE")
	private BigDecimal importe;
	/**
	 * Tipo de movimiento: Pago o Credito.
	 */
	@Column(name = "TIPO_MOVIMIENTO")
	private String tipoMovimiento;

	/**
	 * Constructor
	 * 
	 * @param idCiclo
	 * @param idInstitucion
	 * @param idCuentaControlada
	 * @param importe
	 * @param tipoMovimiento
	 */
	public MovimientoFideicomiso(BigInteger idCiclo, BigInteger idInstitucion,
			BigInteger idCuentaControlada, BigDecimal importe,
			String tipoMovimiento) {
		this.idCiclo = idCiclo;
		this.idInstitucion = idInstitucion;
		this.idCuentaControlada = idCuentaControlada;
		this.importe = importe;
		this.tipoMovimiento = tipoMovimiento;
	}

	/**
	 * @return the idCiclo
	 */
	public BigInteger getIdCiclo() {
		return idCiclo;
	}

	/**
	 * @param idCiclo
	 *            the idCiclo to set
	 */
	public void setIdCiclo(BigInteger idCiclo) {
		this.idCiclo = idCiclo;
	}

	/**
	 * @return the idCuentaControlada
	 */
	public BigInteger getIdCuentaControlada() {
		return idCuentaControlada;
	}

	/**
	 * @param idCuentaControlada
	 *            the idCuentaControlada to set
	 */
	public void setIdCuentaControlada(BigInteger idCuentaControlada) {
		this.idCuentaControlada = idCuentaControlada;
	}

	/**
	 * @return the idInstitucion
	 */
	public BigInteger getIdInstitucion() {
		return idInstitucion;
	}

	/**
	 * @param idInstitucion
	 *            the idInstitucion to set
	 */
	public void setIdInstitucion(BigInteger idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	/**
	 * @return the idMovimientoFideicomiso
	 */
	public BigInteger getIdMovimientoFideicomiso() {
		return idMovimientoFideicomiso;
	}

	/**
	 * @param idMovimientoFideicomiso
	 *            the idMovimientoFideicomiso to set
	 */
	public void setIdMovimientoFideicomiso(BigInteger idMovimientoFideicomiso) {
		this.idMovimientoFideicomiso = idMovimientoFideicomiso;
	}

	/**
	 * @return the importe
	 */
	public BigDecimal getImporte() {
		return importe;
	}

	/**
	 * @param importe
	 *            the importe to set
	 */
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	/**
	 * @return the tipoMovimiento
	 */
	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	/**
	 * @param tipoMovimiento
	 *            the tipoMovimiento to set
	 */
	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}
}
