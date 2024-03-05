/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class RegistroEstatusOperacionesVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private BigInteger folioControl;

	private BigInteger folioOrigen;

	private String folioDescripcion;

	private String status;

	private AgenteVO contraparte;

	private AgenteVO agenteFirmado;

	private EmisionVO claveDeValor;

	private String tipoOperacion;

	private BigDecimal cantidadOperada;

	private BigDecimal montoOperado;

	private BigDecimal precio;

	private BigDecimal tasa;

	private String tasaVariable;

	private Date fechaRegistro;

	private Date fechaLiquidacion;

	private String origen;

	private String rol; // [T|R]

	private String plazo;

	private String error;

	private BigDecimal premio;

	private Integer plazoFechaValor;

	private String divisa;

	private String llaveFolio;

	private String descError;

	/**
	 * @return
	 */
	public String getLlaveFolio() {
		return llaveFolio;
	}

	/**
	 * @param llaveFolio
	 */
	public void setLlaveFolio(String llaveFolio) {
		this.llaveFolio = llaveFolio;
	}

	/**
	 * @return
	 */
	public BigDecimal getCantidadOperada() {
		return cantidadOperada;
	}

	/**
	 * @param cantidadOperada
	 */
	public void setCantidadOperada(BigDecimal cantidadOperada) {
		this.cantidadOperada = cantidadOperada;
	}

	/**
	 * @return
	 */
	public EmisionVO getClaveDeValor() {
		return claveDeValor;
	}

	/**
	 * @param claveDeValor
	 */
	public void setClaveDeValor(EmisionVO claveDeValor) {
		this.claveDeValor = claveDeValor;
	}

	/**
	 * @return
	 */
	public AgenteVO getContraparte() {
		return contraparte;
	}

	/**
	 * @param contraparte
	 */
	public void setContraparte(AgenteVO contraparte) {
		this.contraparte = contraparte;
	}

	/**
	 * @return
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	/**
	 * @param fechaLiquidacion
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = clona(fechaLiquidacion);
	}

	/**
	 * @return
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = clona(fechaRegistro);
	}

	/**
	 * @return
	 */
	public BigInteger getFolioControl() {
		return folioControl;
	}

	/**
	 * @param folioControl
	 */
	public void setFolioControl(BigInteger folioControl) {
		this.folioControl = folioControl;
	}

	/**
	 * @return
	 */
	public String getFolioDescripcion() {
		return folioDescripcion;
	}

	/**
	 * @param folioDescripcion
	 */
	public void setFolioDescripcion(String folioDescripcion) {
		this.folioDescripcion = folioDescripcion;
	}

	/**
	 * @return
	 */
	public BigInteger getFolioOrigen() {
		return folioOrigen;
	}

	/**
	 * @param folioOrigen
	 */
	public void setFolioOrigen(BigInteger folioOrigen) {
		this.folioOrigen = folioOrigen;
	}

	/**
	 * @return
	 */
	public BigDecimal getMontoOperado() {
		return montoOperado;
	}

	/**
	 * @param montoOperado
	 */
	public void setMontoOperado(BigDecimal montoOperado) {
		this.montoOperado = montoOperado;
	}

	/**
	 * @return
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * @param origen
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * @return
	 */
	public String getPlazo() {
		return plazo;
	}

	/**
	 * @param plazo
	 */
	public void setPlazo(String plazo) {
		this.plazo = plazo;
	}

	/**
	 * @return
	 */
	public Integer getPlazoFechaValor() {
		return plazoFechaValor;
	}

	/**
	 * @param plazoFechaValor
	 */
	public void setPlazoFechaValor(Integer plazoFechaValor) {
		this.plazoFechaValor = plazoFechaValor;
	}

	/**
	 * @return
	 */
	public BigDecimal getPrecio() {
		return precio;
	}

	/**
	 * @param precio
	 */
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	/**
	 * @return
	 */
	public BigDecimal getPremio() {
		return premio;
	}

	/**
	 * @param premio
	 */
	public void setPremio(BigDecimal premio) {
		this.premio = premio;
	}

	/**
	 * @return
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * @param rol
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}

	/**
	 * @return
	 */
	public String getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * @param sipoOperacion
	 */
	public void setTipoOperacion(String sipoOperacion) {
		this.tipoOperacion = sipoOperacion;
	}

	/**
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return
	 */
	public BigDecimal getTasa() {
		return tasa;
	}

	/**
	 * @param tasa
	 */
	public void setTasa(BigDecimal tasa) {
		this.tasa = tasa;
	}

	/**
	 * @return
	 */
	public String getTasaVariable() {
		return tasaVariable;
	}

	/**
	 * @param tasaVariable
	 */
	public void setTasaVariable(String tasaVariable) {
		this.tasaVariable = tasaVariable;
	}

	/**
	 * @return
	 */
	public String getDivisa() {
		return divisa;
	}

	/**
	 * @param divisa
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	/**
	 * @return
	 */
	public AgenteVO getAgenteFirmado() {
		return agenteFirmado;
	}

	/**
	 * @param agenteFirmado
	 */
	public void setAgenteFirmado(AgenteVO agenteFirmado) {
		this.agenteFirmado = agenteFirmado;
	}

	/**
	 * @return
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

	/**
	 * @return the descError
	 */
	public String getDescError() {
		return descError;
	}

	/**
	 * @param descError
	 *            the descError to set
	 */
	public void setDescError(String descError) {
		this.descError = descError;
	}

}
