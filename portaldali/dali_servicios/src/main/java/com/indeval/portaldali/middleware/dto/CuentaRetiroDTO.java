/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.math.BigDecimal;

/**
 * Data Transfer Object que representa una cuenta de retiro
 *
 * @author  FERNANDO VAZQUEZ ULLOA
 * @version 1.0
 */
public class CuentaRetiroDTO implements Serializable {
		
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** identificador de cuenta */
	private BigInteger idCuentaRetiro;
	
	/** institucion que crea la cuenta */
	private InstitucionDTO institucion;
		
	/** estado de la cuenta */
	private EstadosCuentaDTO estado;
	
	/** divisa de la cuenta */
	private DivisaDTO divisa;
	
	/** monto maximo mensual */
	private BigDecimal montoMaxMensual;
	
	/** monto maximo diario */
	private BigDecimal montoMaxDiario;
	
	/** monto maximo por transaccion */
	private BigDecimal montoMaxPorTran;
	
	/** maximo de movimientos por mes */
	private Long maxMovsMensual;
	
	/** identificador de cuenta por institucion*/
	private Long idCuentaRetiroPorInstitucion;

	public BigInteger getIdCuentaRetiro() {
		return idCuentaRetiro;
	}

	public void setIdCuentaRetiro(BigInteger idCuentaRetiro) {
		this.idCuentaRetiro = idCuentaRetiro;
	}

	public InstitucionDTO getInstitucion() {
		return institucion;
	}

	public void setInstitucion(InstitucionDTO institucion) {
		this.institucion = institucion;
	}

	public EstadosCuentaDTO getEstado() {
		return estado;
	}

	public void setEstado(EstadosCuentaDTO estado) {
		this.estado = estado;
	}

	public DivisaDTO getDivisa() {
		return divisa;
	}

	public void setDivisa(DivisaDTO divisa) {
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

	public Long getIdCuentaRetiroPorInstitucion() {
		return idCuentaRetiroPorInstitucion;
	}

	public void setIdCuentaRetiroPorInstitucion(Long idCuentaRetiroPorInstitucion) {
		this.idCuentaRetiroPorInstitucion = idCuentaRetiroPorInstitucion;
	}
	
}

