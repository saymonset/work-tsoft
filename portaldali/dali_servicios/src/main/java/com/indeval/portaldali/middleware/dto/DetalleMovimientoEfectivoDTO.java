/**
 * 2H Software - Bursatec
 * 
 * Sistema de Consulta de Estados de Cuenta
 *
 * Dec 28, 2007
 *
 */
package com.indeval.portaldali.middleware.dto;

import java.util.Date;

/**
 * Objeto de transferencia de datos para encapsular la información referente al 
 * detalle de un registro contable de efectivo.
 * 
 * @author Pablo Julián Balderas Méndez
 * 
 */
public class DetalleMovimientoEfectivoDTO {
	
	/** Fecha del movimiento */
	private Date fechaMovimiento = null;
	
	/** Participante del movimiento */
	private String participante = null;
	
	/** Contraparte del movimiento */
	private String contraparte = null;
	
	/** Descripción del movimiento */
	private String descripcionMovimiento = null;
	
	/** Folio de la operación */
	private Long folioOperacion = null;
	
	/** Cargo del movimiento (es el mismo que monto operado)*/
	private Double cargoMovimiento = null;
	
	/** Traspasante */
	private String traspasante = null;
	
	/** Receptor */
	private String receptor = null;
	
	/** Saldo efectivo, contiene la información de la boveda, la cuenta y la divisa */
	private SaldoEfectivoDTO saldoEfectivoDTO = null;
	
	/** Instrucción de liquidación */
	private InstruccionLiquidacionDTO instruccion = null;
	
	/** Operación realizada */
	private OperacionSaldoDTO operacion = null;
	
	/** Tipo de movimiento, cargo o abono */
	private String movimiento = null;

	/** Concepto en la instruccion de efectivo */
	private String concepto = null;
	/**
	 * Obtiene el atributo fechaMovimiento
	 *
	 * @return El atrubuto fechaMovimiento
	 */
	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}

	/**
	 * Establece la propiedad fechaMovimiento
	 *
	 * @param fechaMovimiento el campo fechaMovimiento a establecer
	 */
	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}

	/**
	 * Obtiene el atributo participante
	 *
	 * @return El atrubuto participante
	 */
	public String getParticipante() {
		return participante;
	}

	/**
	 * Establece la propiedad participante
	 *
	 * @param participante el campo participante a establecer
	 */
	public void setParticipante(String participante) {
		this.participante = participante;
	}

	/**
	 * Obtiene el atributo contraparte
	 *
	 * @return El atrubuto contraparte
	 */
	public String getContraparte() {
		return contraparte;
	}

	/**
	 * Establece la propiedad contraparte
	 *
	 * @param contraparte el campo contraparte a establecer
	 */
	public void setContraparte(String contraparte) {
		this.contraparte = contraparte;
	}

	/**
	 * Obtiene el atributo descripcionMovimiento
	 *
	 * @return El atrubuto descripcionMovimiento
	 */
	public String getDescripcionMovimiento() {
		return descripcionMovimiento;
	}

	/**
	 * Establece la propiedad descripcionMovimiento
	 *
	 * @param descripcionMovimiento el campo descripcionMovimiento a establecer
	 */
	public void setDescripcionMovimiento(String descripcionMovimiento) {
		this.descripcionMovimiento = descripcionMovimiento;
	}

	/**
	 * Obtiene el atributo folioOperacion
	 *
	 * @return El atrubuto folioOperacion
	 */
	public Long getFolioOperacion() {
		return folioOperacion;
	}

	/**
	 * Establece la propiedad folioOperacion
	 *
	 * @param folioOperacion el campo folioOperacion a establecer
	 */
	public void setFolioOperacion(Long folioOperacion) {
		this.folioOperacion = folioOperacion;
	}

	/**
	 * Obtiene el atributo cargoMovimiento
	 *
	 * @return El atrubuto cargoMovimiento
	 */
	public Double getCargoMovimiento() {
		return cargoMovimiento;
	}

	/**
	 * Establece la propiedad cargoMovimiento
	 *
	 * @param cargoMovimiento el campo cargoMovimiento a establecer
	 */
	public void setCargoMovimiento(Double cargoMovimiento) {
		this.cargoMovimiento = cargoMovimiento;
	}

	/**
	 * Obtiene el atributo traspasante
	 *
	 * @return El atrubuto traspasante
	 */
	public String getTraspasante() {
		return traspasante;
	}

	/**
	 * Establece la propiedad traspasante
	 *
	 * @param traspasante el campo traspasante a establecer
	 */
	public void setTraspasante(String transpasante) {
		this.traspasante = transpasante;
	}

	/**
	 * Obtiene el atributo receptor
	 *
	 * @return El atrubuto receptor
	 */
	public String getReceptor() {
		return receptor;
	}

	/**
	 * Establece la propiedad receptor
	 *
	 * @param receptor el campo receptor a establecer
	 */
	public void setReceptor(String receptor) {
		this.receptor = receptor;
	}

	/**
	 * Obtiene el atributo instruccion
	 *
	 * @return El atrubuto instruccion
	 */
	public InstruccionLiquidacionDTO getInstruccion() {
		return instruccion;
	}

	/**
	 * Establece la propiedad instruccion
	 *
	 * @param instruccion el campo instruccion a establecer
	 */
	public void setInstruccion(InstruccionLiquidacionDTO instruccion) {
		this.instruccion = instruccion;
	}

	/**
	 * Obtiene el atributo operacion
	 *
	 * @return El atrubuto operacion
	 */
	public OperacionSaldoDTO getOperacion() {
		return operacion;
	}

	/**
	 * Establece la propiedad operacion
	 *
	 * @param operacion el campo operacion a establecer
	 */
	public void setOperacion(OperacionSaldoDTO operacion) {
		this.operacion = operacion;
	}

	/**
	 * Obtiene el atributo movimiento
	 *
	 * @return El atrubuto movimiento
	 */
	public String getMovimiento() {
		return movimiento;
	}

	/**
	 * Establece la propiedad movimiento
	 *
	 * @param movimiento el campo movimiento a establecer
	 */
	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}

	/**
	 * Obtiene el atributo saldoEfectivoDTO
	 *
	 * @return El atrubuto saldoEfectivoDTO
	 */
	public SaldoEfectivoDTO getSaldoEfectivoDTO() {
		return saldoEfectivoDTO;
	}

	/**
	 * Establece la propiedad saldoEfectivoDTO
	 *
	 * @param saldoEfectivoDTO el campo saldoEfectivoDTO a establecer
	 */
	public void setSaldoEfectivoDTO(SaldoEfectivoDTO saldoEfectivoDTO) {
		this.saldoEfectivoDTO = saldoEfectivoDTO;
	}

	/**
	 * Concepto en la instruccion de efectivo
	 *
	 */
	public String getConcepto() {
		return concepto;
	}

	/**
	 * Concepto en la instruccion de efectivo
	 *
	 * @param concepto el campo concepto en la instruccion de efectivo
	 */
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
}
