/**
 * 
 */
package com.indeval.portaldali.presentation.dto.mercadodinero;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * Representa los elementos de cálculo de Simulado, Fecha Regreso, Importe y
 * Premio.
 * 
 * @author Juan Carlos Huizar Moreno
 * 
 */
public class CalculoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** El saldo de Efectivo que resultara al restar el importe a traspasar */
	private BigInteger simulado;

	/** Fecha de regreso */
	private Date fechaRegreso;

	/** El importe a traspasar entre las cuentas */
	private BigDecimal importe;

	/** El premio */
	private BigDecimal premio;

	/** Plazo representado en días */
	private Integer plazoRepDias;

	/** Tasa premio */
	private BigDecimal tasaPremio;

	/** La cantidad operada */
	private Double cantidadOperada;

	/** El precio del título */
	private BigDecimal precioTitulo;

	/** El identificador del tipo de Operacion */
	private long idTipoOperacion = 0;

	/** La selección del combo Plazo Liquidación (horas) */
	private int plazoLiquidacionHoras;

	/** El tipo de operacion seleccionada. */
	private String tipoOperacion;
	
	/** Mensaje de Error para campo Simulado */
	private String mensajeSimulado;

	/** Mensaje de Error para campo Premio */
	private String mensajePremio;

	/** Mensaje de Error para campo Importe */
	private String mensajeImporte;

	/** Mensaje de Error para campo Fecha Regreso */
	private String mensajeFechaRegreso;

	/** Mensaje de Error para campo Precio Título */
	private String mensajePrecioTitulo;

	/**
	 * @return the simulado
	 */
	public BigInteger getSimulado() {
		return simulado;
	}

	/**
	 * @param simulado
	 *            the simulado to set
	 */
	public void setSimulado(BigInteger simulado) {
		this.simulado = simulado;
	}

	/**
	 * @return the fechaRegreso
	 */
	public Date getFechaRegreso() {
		return fechaRegreso;
	}

	/**
	 * @param fechaRegreso
	 *            the fechaRegreso to set
	 */
	public void setFechaRegreso(Date fechaRegreso) {
		this.fechaRegreso = fechaRegreso;
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
	 * @return the premio
	 */
	public BigDecimal getPremio() {
		return premio;
	}

	/**
	 * @param premio
	 *            the premio to set
	 */
	public void setPremio(BigDecimal premio) {
		this.premio = premio;
	}

	/**
	 * @return the plazoRepDias
	 */
	public Integer getPlazoRepDias() {
		return plazoRepDias;
	}

	/**
	 * @param plazoRepDias
	 *            the plazoRepDias to set
	 */
	public void setPlazoRepDias(Integer plazoRepDias) {
		this.plazoRepDias = plazoRepDias;
	}

	/**
	 * @return the tasaPremio
	 */
	public BigDecimal getTasaPremio() {
		return tasaPremio;
	}

	/**
	 * @param tasaPremio
	 *            the tasaPremio to set
	 */
	public void setTasaPremio(BigDecimal tasaPremio) {
		this.tasaPremio = tasaPremio;
	}

	/**
	 * @return the cantidadOperada
	 */
	public Double getCantidadOperada() {
		return cantidadOperada;
	}

	/**
	 * @param cantidadOperada
	 *            the cantidadOperada to set
	 */
	public void setCantidadOperada(Double cantidadOperada) {
		this.cantidadOperada = cantidadOperada;
	}

	/**
	 * @return the precioTitulo
	 */
	public BigDecimal getPrecioTitulo() {
		return precioTitulo;
	}

	/**
	 * @param precioTitulo
	 *            the precioTitulo to set
	 */
	public void setPrecioTitulo(BigDecimal precioTitulo) {
		this.precioTitulo = precioTitulo;
	}

	/**
	 * @return the idTipoOperacion
	 */
	public long getIdTipoOperacion() {
		return idTipoOperacion;
	}

	/**
	 * @param idTipoOperacion
	 *            the idTipoOperacion to set
	 */
	public void setIdTipoOperacion(long idTipoOperacion) {
		this.idTipoOperacion = idTipoOperacion;
	}

	/**
	 * @return the plazoLiquidacionHoras
	 */
	public int getPlazoLiquidacionHoras() {
		return plazoLiquidacionHoras;
	}

	/**
	 * @param plazoLiquidacionHoras
	 *            the plazoLiquidacionHoras to set
	 */
	public void setPlazoLiquidacionHoras(int plazoLiquidacionHoras) {
		this.plazoLiquidacionHoras = plazoLiquidacionHoras;
	}

	/**
	 * @return the mensajeSimulado
	 */
	public String getMensajeSimulado() {
		return mensajeSimulado;
	}

	/**
	 * @param mensajeSimulado
	 *            the mensajeSimulado to set
	 */
	public void setMensajeSimulado(String mensajeSimulado) {
		this.mensajeSimulado = mensajeSimulado;
	}

	/**
	 * @return the mensajePremio
	 */
	public String getMensajePremio() {
		return mensajePremio;
	}

	/**
	 * @param mensajePremio
	 *            the mensajePremio to set
	 */
	public void setMensajePremio(String mensajePremio) {
		this.mensajePremio = mensajePremio;
	}

	/**
	 * @return the mensajeImporte
	 */
	public String getMensajeImporte() {
		return mensajeImporte;
	}

	/**
	 * @param mensajeImporte
	 *            the mensajeImporte to set
	 */
	public void setMensajeImporte(String mensajeImporte) {
		this.mensajeImporte = mensajeImporte;
	}

	/**
	 * @return the mensajeFechaRegreso
	 */
	public String getMensajeFechaRegreso() {
		return mensajeFechaRegreso;
	}

	/**
	 * @param mensajeFechaRegreso
	 *            the mensajeFechaRegreso to set
	 */
	public void setMensajeFechaRegreso(String mensajeFechaRegreso) {
		this.mensajeFechaRegreso = mensajeFechaRegreso;
	}

	/**
	 * @return the mensajePrecioTitulo
	 */
	public String getMensajePrecioTitulo() {
		return mensajePrecioTitulo;
	}

	/**
	 * @param mensajePrecioTitulo
	 *            the mensajePrecioTitulo to set
	 */
	public void setMensajePrecioTitulo(String mensajePrecioTitulo) {
		this.mensajePrecioTitulo = mensajePrecioTitulo;
	}

	/**
	 * @return the tipoOperacion
	 */
	public String getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * @param tipoOperacion the tipoOperacion to set
	 */
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

}
