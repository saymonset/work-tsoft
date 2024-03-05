/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Cata&acute;logo con los campos comunes para cuentas de retiro de efectivo tanto nacionales 
 * como internacionales
 * 
 * C_CUENTA_RETIRO
 *
 * @author  Maria C. Buendia
 * @version 1.0
 */
@Entity
@Table(name="C_CUENTA_RETIRO")
@SequenceGenerator(name = "SEQ_CuentaRetiro", sequenceName = "C_CUENTA_RETIRO_SEQ",allocationSize = 1,initialValue = 1)
@Inheritance(strategy=InheritanceType.JOINED)
public class CuentaRetiro implements Serializable {
		
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** identificador de cuenta */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CuentaRetiro")
	@Column(name = "ID_CUENTA_RETIRO", nullable = false)
	private Long idCuentaRetiro;
	
	/** institucion que crea la cuenta */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_INSTITUCION", nullable = false)	
	private Institucion institucion;
	
	/** identificador de cuenta por institucion */
	@Column(name = "ID_CUENTA_POR_INST", nullable = false)
	private Long idCuentaPorInstitucion;
		
	/** estado de la cuenta */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ESTADO", nullable = false)	
	private EstadoInstruccionCat estado;
	
	/** divisa de la cuenta */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DIVISA", nullable = false)
	private Divisa divisa;
	
	/** monto maximo mensual */
	@Column(name = "MONTO_MAX_MENSUAL")
	private BigDecimal montoMaxMensual;
	
	/** monto maximo diario */
	@Column(name = "MONTO_MAX_DIARIO")
	private BigDecimal montoMaxDiario;
	
	/** monto maximo por transaccion */
	@Column(name = "MONTO_MAX_TRANSAC")
	private BigDecimal montoMaxPorTran;
	
	/** maximo de movimientos por mes */
	@Column(name = "MAX_MOVS_MENSUAL")
	private Long maxMovsMensual;

	public Long getIdCuentaRetiro() {
		return idCuentaRetiro;
	}

	public void setIdCuentaRetiro(Long idCuentaRetiro) {
		this.idCuentaRetiro = idCuentaRetiro;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public EstadoInstruccionCat getEstado() {
		return estado;
	}

	public void setEstado(EstadoInstruccionCat estado) {
		this.estado = estado;
	}

	public Divisa getDivisa() {
		return divisa;
	}

	public void setDivisa(Divisa divisa) {
		this.divisa = divisa;
	}

	public BigDecimal getMontoMaxMensual() {
		return montoMaxMensual;
	}

	public void setMontoMaxMensual(BigDecimal montoMaxMensual) {
		this.montoMaxMensual = montoMaxMensual;
	}

	public BigDecimal getMontoMaxDiario() {
		return montoMaxDiario;
	}

	public void setMontoMaxDiario(BigDecimal montoMaxDiario) {
		this.montoMaxDiario = montoMaxDiario;
	}

	public BigDecimal getMontoMaxPorTran() {
		return montoMaxPorTran;
	}

	public void setMontoMaxPorTran(BigDecimal montoMaxPorTran) {
		this.montoMaxPorTran = montoMaxPorTran;
	}

	public Long getMaxMovsMensual() {
		return maxMovsMensual;
	}

	public void setMaxMovsMensual(Long maxMovsMensual) {
		this.maxMovsMensual = maxMovsMensual;
	}

	public Long getIdCuentaPorInstitucion() {
		return idCuentaPorInstitucion;
	}

	public void setIdCuentaPorInstitucion(Long idCuentaPorInstitucion) {
		this.idCuentaPorInstitucion = idCuentaPorInstitucion;
	}
	
}

