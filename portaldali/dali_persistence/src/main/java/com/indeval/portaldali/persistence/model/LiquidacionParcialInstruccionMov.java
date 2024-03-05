/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.math.BigDecimal;
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
 * Mapeo de la tabla T_LIQUIDACION_PARCIAL_MOV. Contiene las parcialidades que existen
 * para una instrucción.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 */
@Entity
@Table(name="T_LIQUIDACION_PARCIAL_MOV")
public class LiquidacionParcialInstruccionMov {
	
	/**
	 * El identificador de la tabla T_LIQUIDACION_PARCIAL_MOV
	 */
	@Id
	@Column (name = "ID_LIQUIDACION_PARCIAL_MOV")
	private BigInteger idLiquidacionParcialInstruccionMov;
	
	/** El identificador de la instrucción operación que generó esta parcialidad */
	@Column (name = "ID_INSTRUCCION_OPERACION_VAL")
	private BigInteger idInstruccionOperacionVal;
	
	/** La instrucción operación que generó esta parcialidad */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_INSTRUCCION_OPERACION_VAL", updatable = false, insertable = false)
	private InstruccionOperacionVal instruccionOperacionVal;
	
	/** El identificador de la instrucción liquidación que generó esta parcialidad */
	@Column(name = "ID_INSTRUCCION_LIQUIDACION")
	private BigInteger idInstruccionLiquidacion;
	
	/** La instrucción liquidación que generó esta parcialidad */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_INSTRUCCION_LIQUIDACION", updatable = false, insertable = false)
	private InstruccionLiquidacion instruccionLiquidacion;
	
	/** El número de títulos entregados en esta parcialidad */
	@Column(name = "NUMERO_TITULOS")
    private BigInteger numeroTitulos;
	
	/** El importe entregado en esta parcialidad */
	@Column(name = "IMPORTE")
    private BigDecimal importe;
	
	/** La fecha y hora de registro de esta parcialidad */
	@Column(name = "FECHA_HORA")
	private Date fechaHora;

	/**
	 * Obtiene el valor del atributo idLiquidacionParcialInstruccionMov
	 *
	 * @return el valor del atributo idLiquidacionParcialInstruccionMov
	 */
	public BigInteger getIdLiquidacionParcialInstruccionMov() {
		return idLiquidacionParcialInstruccionMov;
	}

	/**
	 * Establece el valor del atributo idLiquidacionParcialInstruccionMov
	 *
	 * @param idLiquidacionParcialInstruccionMov el valor del atributo idLiquidacionParcialInstruccionMov a establecer.
	 */
	public void setIdLiquidacionParcialInstruccionMov(
			BigInteger idLiquidacionParcialInstruccionMov) {
		this.idLiquidacionParcialInstruccionMov = idLiquidacionParcialInstruccionMov;
	}

	/**
	 * Obtiene el valor del atributo idInstruccionOperacionVal
	 *
	 * @return el valor del atributo idInstruccionOperacionVal
	 */
	public BigInteger getIdInstruccionOperacionVal() {
		return idInstruccionOperacionVal;
	}

	/**
	 * Establece el valor del atributo idInstruccionOperacionVal
	 *
	 * @param idInstruccionOperacionVal el valor del atributo idInstruccionOperacionVal a establecer.
	 */
	public void setIdInstruccionOperacionVal(BigInteger idInstruccionOperacionVal) {
		this.idInstruccionOperacionVal = idInstruccionOperacionVal;
	}

	/**
	 * Obtiene el valor del atributo instruccionOperacionVal
	 *
	 * @return el valor del atributo instruccionOperacionVal
	 */
	public InstruccionOperacionVal getInstruccionOperacionVal() {
		return instruccionOperacionVal;
	}

	/**
	 * Establece el valor del atributo instruccionOperacionVal
	 *
	 * @param instruccionOperacionVal el valor del atributo instruccionOperacionVal a establecer.
	 */
	public void setInstruccionOperacionVal(
			InstruccionOperacionVal instruccionOperacionVal) {
		this.instruccionOperacionVal = instruccionOperacionVal;
	}

	/**
	 * Obtiene el valor del atributo idInstruccionLiquidacion
	 *
	 * @return el valor del atributo idInstruccionLiquidacion
	 */
	public BigInteger getIdInstruccionLiquidacion() {
		return idInstruccionLiquidacion;
	}

	/**
	 * Establece el valor del atributo idInstruccionLiquidacion
	 *
	 * @param idInstruccionLiquidacion el valor del atributo idInstruccionLiquidacion a establecer.
	 */
	public void setIdInstruccionLiquidacion(BigInteger idInstruccionLiquidacion) {
		this.idInstruccionLiquidacion = idInstruccionLiquidacion;
	}

	/**
	 * Obtiene el valor del atributo instruccionLiquidacion
	 *
	 * @return el valor del atributo instruccionLiquidacion
	 */
	public InstruccionLiquidacion getInstruccionLiquidacion() {
		return instruccionLiquidacion;
	}

	/**
	 * Establece el valor del atributo instruccionLiquidacion
	 *
	 * @param instruccionLiquidacion el valor del atributo instruccionLiquidacion a establecer.
	 */
	public void setInstruccionLiquidacion(
			InstruccionLiquidacion instruccionLiquidacion) {
		this.instruccionLiquidacion = instruccionLiquidacion;
	}

	/**
	 * Obtiene el valor del atributo numeroTitulos
	 *
	 * @return el valor del atributo numeroTitulos
	 */
	public BigInteger getNumeroTitulos() {
		return numeroTitulos;
	}

	/**
	 * Establece el valor del atributo numeroTitulos
	 *
	 * @param numeroTitulos el valor del atributo numeroTitulos a establecer.
	 */
	public void setNumeroTitulos(BigInteger numeroTitulos) {
		this.numeroTitulos = numeroTitulos;
	}

	/**
	 * Obtiene el valor del atributo importe
	 *
	 * @return el valor del atributo importe
	 */
	public BigDecimal getImporte() {
		return importe;
	}

	/**
	 * Establece el valor del atributo importe
	 *
	 * @param importe el valor del atributo importe a establecer.
	 */
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	/**
	 * Obtiene el valor del atributo fechaHora
	 *
	 * @return el valor del atributo fechaHora
	 */
	public Date getFechaHora() {
		return fechaHora;
	}

	/**
	 * Establece el valor del atributo fechaHora
	 *
	 * @param fechaHora el valor del atributo fechaHora a establecer.
	 */
	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

}
