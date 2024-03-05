/**
 * 
 */
package com.indeval.portaldali.presentation.helper;

import java.math.BigDecimal;
import java.util.Date;

import com.indeval.portaldali.middleware.dto.EmisionDTO;



/**
 * Contiene los cálculos de Simulado, Fecha Regreso, Importe y Premio necesarios
 * para las pantallas de Captura de Operaciones
 * 
 * @author Juan Carlos Huizar Moreno
 * 
 */
public class CalculoCapturaOperacionViewHelper {

	private static final long serialVersionUID = 1L;

	
	//Campos de resultados 
	
	/** El saldo de Efectivo que resultara al restar el importe a traspasar */
	private BigDecimal simulado;

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
	private BigDecimal cantidadOperada;

	/** El precio del título */
	private BigDecimal precioTitulo;

	/** El identificador del tipo de Operacion */
	private long idTipoOperacion = 0;

	/** La selección del combo Plazo Liquidación (horas) */
	private int plazoLiquidacionHoras;

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

	
	
	//Campos de entrada
	private String tipoOperacion = null;
	private EmisionDTO emision = null;
	private String tipoOperacionLeida = null;
	private String cantidadOperadaLeida = null;
	private String saldoDisponibleLeido = null;
	private String plazoRepDiasLeido = null;
	private String plazoLiquidacionHorasLeido = null;
	private String plazoRepDiasInhabilitadoLeido = null;
	private String plazoLiquidacionHorasInhabilitadoLeido = null;
	private String precioTituloLeido = null;
	private String precioTituloInhabilitadoLeido = null;
	private String importeInhabilitadoLeido = null;
	private String tasaPremioInhabilitadoLeido = null;
	private String tasaPremioLeido = null;
	private Boolean plazoLiquidacionHorasInhabilitado = null;
	
	
	/**
	 * @return the simulado
	 */
	public BigDecimal getSimulado() {
		return simulado;
	}

	/**
	 * @param simulado
	 *            the simulado to set
	 */
	public void setSimulado(BigDecimal simulado) {
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
	public BigDecimal getCantidadOperada() {
		return cantidadOperada;
	}

	/**
	 * @param cantidadOperada
	 *            the cantidadOperada to set
	 */
	public void setCantidadOperada(BigDecimal cantidadOperada) {
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
	 * Obtiene el campo tipoOperacionLeida
	 * @return  tipoOperacionLeida
	 */
	public String getTipoOperacionLeida() {
		return tipoOperacionLeida;
	}

	/**
	 * Asigna el campo tipoOperacionLeida
	 * @param tipoOperacionLeida el valor de tipoOperacionLeida a asignar
	 */
	public void setTipoOperacionLeida(String tipoOperacionLeida) {
		this.tipoOperacionLeida = tipoOperacionLeida;
	}

	/**
	 * Obtiene el campo cantidadOperadaLeida
	 * @return  cantidadOperadaLeida
	 */
	public String getCantidadOperadaLeida() {
		return cantidadOperadaLeida;
	}

	/**
	 * Asigna el campo cantidadOperadaLeida
	 * @param cantidadOperadaLeida el valor de cantidadOperadaLeida a asignar
	 */
	public void setCantidadOperadaLeida(String cantidadOperadaLeida) {
		this.cantidadOperadaLeida = cantidadOperadaLeida;
	}

	/**
	 * Obtiene el campo saldoDisponibleLeido
	 * @return  saldoDisponibleLeido
	 */
	public String getSaldoDisponibleLeido() {
		return saldoDisponibleLeido;
	}

	/**
	 * Asigna el campo saldoDisponibleLeido
	 * @param saldoDisponibleLeido el valor de saldoDisponibleLeido a asignar
	 */
	public void setSaldoDisponibleLeido(String saldoDisponibleLeido) {
		this.saldoDisponibleLeido = saldoDisponibleLeido;
	}

	/**
	 * Obtiene el campo plazoRepDiasLeido
	 * @return  plazoRepDiasLeido
	 */
	public String getPlazoRepDiasLeido() {
		return plazoRepDiasLeido;
	}

	/**
	 * Asigna el campo plazoRepDiasLeido
	 * @param plazoRepDiasLeido el valor de plazoRepDiasLeido a asignar
	 */
	public void setPlazoRepDiasLeido(String plazoRepDiasLeido) {
		this.plazoRepDiasLeido = plazoRepDiasLeido;
	}

	/**
	 * Obtiene el campo plazoLiquidacionHorasLeido
	 * @return  plazoLiquidacionHorasLeido
	 */
	public String getPlazoLiquidacionHorasLeido() {
		return plazoLiquidacionHorasLeido;
	}

	/**
	 * Asigna el campo plazoLiquidacionHorasLeido
	 * @param plazoLiquidacionHorasLeido el valor de plazoLiquidacionHorasLeido a asignar
	 */
	public void setPlazoLiquidacionHorasLeido(String plazoLiquidacionHorasLeido) {
		this.plazoLiquidacionHorasLeido = plazoLiquidacionHorasLeido;
	}

	/**
	 * Obtiene el campo plazoRepDiasInhabilitadoLeido
	 * @return  plazoRepDiasInhabilitadoLeido
	 */
	public String getPlazoRepDiasInhabilitadoLeido() {
		return plazoRepDiasInhabilitadoLeido;
	}

	/**
	 * Asigna el campo plazoRepDiasInhabilitadoLeido
	 * @param plazoRepDiasInhabilitadoLeido el valor de plazoRepDiasInhabilitadoLeido a asignar
	 */
	public void setPlazoRepDiasInhabilitadoLeido(
			String plazoRepDiasInhabilitadoLeido) {
		this.plazoRepDiasInhabilitadoLeido = plazoRepDiasInhabilitadoLeido;
	}

	/**
	 * Obtiene el campo plazoLiquidacionHorasInhabilitadoLeido
	 * @return  plazoLiquidacionHorasInhabilitadoLeido
	 */
	public String getPlazoLiquidacionHorasInhabilitadoLeido() {
		return plazoLiquidacionHorasInhabilitadoLeido;
	}

	/**
	 * Asigna el campo plazoLiquidacionHorasInhabilitadoLeido
	 * @param plazoLiquidacionHorasInhabilitadoLeido el valor de plazoLiquidacionHorasInhabilitadoLeido a asignar
	 */
	public void setPlazoLiquidacionHorasInhabilitadoLeido(
			String plazoLiquidacionHorasInhabilitadoLeido) {
		this.plazoLiquidacionHorasInhabilitadoLeido = plazoLiquidacionHorasInhabilitadoLeido;
	}

	/**
	 * Obtiene el campo precioTituloLeido
	 * @return  precioTituloLeido
	 */
	public String getPrecioTituloLeido() {
		return precioTituloLeido;
	}

	/**
	 * Asigna el campo precioTituloLeido
	 * @param precioTituloLeido el valor de precioTituloLeido a asignar
	 */
	public void setPrecioTituloLeido(String precioTituloLeido) {
		this.precioTituloLeido = precioTituloLeido;
	}

	/**
	 * Obtiene el campo precioTituloInhabilitadoLeido
	 * @return  precioTituloInhabilitadoLeido
	 */
	public String getPrecioTituloInhabilitadoLeido() {
		return precioTituloInhabilitadoLeido;
	}

	/**
	 * Asigna el campo precioTituloInhabilitadoLeido
	 * @param precioTituloInhabilitadoLeido el valor de precioTituloInhabilitadoLeido a asignar
	 */
	public void setPrecioTituloInhabilitadoLeido(
			String precioTituloInhabilitadoLeido) {
		this.precioTituloInhabilitadoLeido = precioTituloInhabilitadoLeido;
	}

	/**
	 * Obtiene el campo importeInhabilitadoLeido
	 * @return  importeInhabilitadoLeido
	 */
	public String getImporteInhabilitadoLeido() {
		return importeInhabilitadoLeido;
	}

	/**
	 * Asigna el campo importeInhabilitadoLeido
	 * @param importeInhabilitadoLeido el valor de importeInhabilitadoLeido a asignar
	 */
	public void setImporteInhabilitadoLeido(String importeInhabilitadoLeido) {
		this.importeInhabilitadoLeido = importeInhabilitadoLeido;
	}

	/**
	 * Obtiene el campo tasaPremioInhabilitadoLeido
	 * @return  tasaPremioInhabilitadoLeido
	 */
	public String getTasaPremioInhabilitadoLeido() {
		return tasaPremioInhabilitadoLeido;
	}

	/**
	 * Asigna el campo tasaPremioInhabilitadoLeido
	 * @param tasaPremioInhabilitadoLeido el valor de tasaPremioInhabilitadoLeido a asignar
	 */
	public void setTasaPremioInhabilitadoLeido(String tasaPremioInhabilitadoLeido) {
		this.tasaPremioInhabilitadoLeido = tasaPremioInhabilitadoLeido;
	}

	/**
	 * Obtiene el campo tasaPremioLeido
	 * @return  tasaPremioLeido
	 */
	public String getTasaPremioLeido() {
		return tasaPremioLeido;
	}

	/**
	 * Asigna el campo tasaPremioLeido
	 * @param tasaPremioLeido el valor de tasaPremioLeido a asignar
	 */
	public void setTasaPremioLeido(String tasaPremioLeido) {
		this.tasaPremioLeido = tasaPremioLeido;
	}

	/**
	 * Obtiene el campo plazoLiquidacionHorasInhabilitado
	 * @return  plazoLiquidacionHorasInhabilitado
	 */
	public Boolean getPlazoLiquidacionHorasInhabilitado() {
		return plazoLiquidacionHorasInhabilitado;
	}

	/**
	 * Asigna el campo plazoLiquidacionHorasInhabilitado
	 * @param plazoLiquidacionHorasInhabilitado el valor de plazoLiquidacionHorasInhabilitado a asignar
	 */
	public void setPlazoLiquidacionHorasInhabilitado(
			Boolean plazoLiquidacionHorasInhabilitado) {
		this.plazoLiquidacionHorasInhabilitado = plazoLiquidacionHorasInhabilitado;
	}

	/**
	 * Obtiene el campo tipoOperacion
	 * @return  tipoOperacion
	 */
	public String getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * Asigna el campo tipoOperacion
	 * @param tipoOperacion el valor de tipoOperacion a asignar
	 */
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	/**
	 * Obtiene el campo emision
	 * @return  emision
	 */
	public EmisionDTO getEmision() {
		return emision;
	}

	/**
	 * Asigna el campo emision
	 * @param emision el valor de emision a asignar
	 */
	public void setEmision(EmisionDTO emision) {
		this.emision = emision;
	}

	

}
