/**
 * 2H Software SA de CV
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Fecha de creación: Dec 19, 2007
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Data Tranfer Object que representa una instrucción de liquidación
 * 
 * @author Sandra Flores Arrieta
 * @version 1.0
 * 
 */
public class InstruccionLiquidacionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** El identificador de la instrucción de liquidación */
	private Long idInstruccionLiquidacion;

	/** El identificador de la instrucción que di origen a esta instrucción */
	private Long idInstruccionLiquidacionOrigen;

	/** El tipo de instrucción de esta instrucción de liquidación */
	private TipoInstruccionDTO tipoInstruccion;

	/** La descripción del estado de la instrucción */
	private String descripcionEstadoInstruccion;

	/** El identificador del estado de la instrucción */
	private int idEstadoInstruccion;

	/** La instrucción efectivo ligada a esta instrucción liquidación */
	private InstruccionEfectivoDTO instruccionEfectivo;

	/**
	 * Folio de la instrucci&oacute;n.
	 */
	private Long folioInstruccion;

	/** Fecha de liquidación de la instrucción */
	private Date fechaLiquidacion;

	/** Indica si es banco de trabajo */
	private boolean esBancoTrabajo;

	/** la tasa negociada para operaciones de reporto */
	private double tasaNegociada;

	/** El plazo para operaciones de reporto */
	private Long plazoReporto;

	/**
	 * nuevos campos para manejo por paquete
	 **/
	private boolean porPaquete;
	
	private String referenciaPaquete;
	
	private String totalOperacionesPaquete;
	
	private String numeroOperacionPaquete;
	
	private String totalTitulosPaquete;
	
	private String totalImportePaquete;
	
	
	/**
	 * Obtiene el valor del atributo descripcionEstadoInstruccion
	 * 
	 * @return el valor del atributo descripcionEstadoInstruccion
	 */
	public String getDescripcionEstadoInstruccion() {
		return descripcionEstadoInstruccion;
	}

	/**
	 * Obtiene el valor del atributo tasaNegociada
	 * 
	 * @return el valor del atributo tasaNegociada
	 */
	public double getTasaNegociada() {
		return tasaNegociada;
	}

	/**
	 * Establece el valor del atributo tasaNegociada
	 * 
	 * @param tasaNegociada
	 *            el valor del atributo tasaNegociada a establecer
	 */
	public void setTasaNegociada(double tasaNegociada) {
		this.tasaNegociada = tasaNegociada;
	}

	/**
	 * Obtiene el valor del atributo plazoReporto
	 * 
	 * @return el valor del atributo plazoReporto
	 */
	public Long getPlazoReporto() {
		return plazoReporto;
	}

	/**
	 * Establece el valor del atributo plazoReporto
	 * 
	 * @param plazoReporto
	 *            el valor del atributo plazoReporto a establecer
	 */
	public void setPlazoReporto(Long plazoReporto) {
		this.plazoReporto = plazoReporto;
	}

	/**
	 * Establece el valor del atributo descripcionEstadoInstruccion
	 * 
	 * @param descripcionEstadoInstruccion
	 *            el valor del atributo descripcionEstadoInstruccion a
	 *            establecer
	 */
	public void setDescripcionEstadoInstruccion(String descripcionEstadoInstruccion) {
		this.descripcionEstadoInstruccion = descripcionEstadoInstruccion;
	}

	/**
	 * Obtiene el valor del atributo instruccionEfectivo
	 * 
	 * @return el valor del atributo instruccionEfectivo
	 */
	public InstruccionEfectivoDTO getInstruccionEfectivo() {
		return instruccionEfectivo;
	}

	/**
	 * Establece el valor del atributo instruccionEfectivo
	 * 
	 * @param instruccionEfectivo
	 *            el valor del atributo instruccionEfectivo a establecer
	 */
	public void setInstruccionEfectivo(InstruccionEfectivoDTO instruccionEfectivo) {
		this.instruccionEfectivo = instruccionEfectivo;
	}

	/**
	 * Obtiene el valor del atributo idEstadoInstruccion
	 * 
	 * @return el valor del atributo idEstadoInstruccion
	 */
	public int getIdEstadoInstruccion() {
		return idEstadoInstruccion;
	}

	/**
	 * Establece el valor del atributo idEstadoInstruccion
	 * 
	 * @param idEstadoInstruccion
	 *            el valor del atributo idEstadoInstruccion a establecer
	 */
	public void setIdEstadoInstruccion(int idEstadoInstruccion) {
		this.idEstadoInstruccion = idEstadoInstruccion;
	}

	/**
	 * Obtiene el valor del atributo idInstruccionLiquidacion
	 * 
	 * @return el valor del atributo idInstruccionLiquidacion
	 */
	public Long getIdInstruccionLiquidacion() {
		return idInstruccionLiquidacion;
	}

	/**
	 * Obtiene el valor del atributo idInstruccionLiquidacionOrigen
	 * 
	 * @return el valor del atributo idInstruccionLiquidacionOrigen
	 */
	public Long getIdInstruccionLiquidacionOrigen() {
		return idInstruccionLiquidacionOrigen;
	}

	/**
	 * Establece el valor del atributo idInstruccionLiquidacionOrigen
	 * 
	 * @param idInstruccionLiquidacionOrigen
	 *            el valor del atributo idInstruccionLiquidacionOrigen a
	 *            establecer
	 */
	public void setIdInstruccionLiquidacionOrigen(Long idInstruccionLiquidacionOrigen) {
		this.idInstruccionLiquidacionOrigen = idInstruccionLiquidacionOrigen;
	}

	/**
	 * Establece el valor del atributo idInstruccionLiquidacion
	 * 
	 * @param idInstruccionLiquidacion
	 *            el valor del atributo idInstruccionLiquidacion a establecer.
	 */
	public void setIdInstruccionLiquidacion(Long idInstruccionLiquidacion) {
		this.idInstruccionLiquidacion = idInstruccionLiquidacion;
	}

	/**
	 * Obtiene el valor del atributo tipoInstruccion
	 * 
	 * @return el valor del atributo tipoInstruccion
	 */
	public TipoInstruccionDTO getTipoInstruccion() {
		return tipoInstruccion;
	}

	/**
	 * Establece el valor del atributo tipoInstruccion
	 * 
	 * @param tipoInstruccion
	 *            el valor del atributo tipoInstruccion a establecer.
	 */
	public void setTipoInstruccion(TipoInstruccionDTO tipoInstruccion) {
		this.tipoInstruccion = tipoInstruccion;
	}

	/**
	 * Obtiene el valor del atributo folioInstruccion
	 * 
	 * @return el valor del atributo folioInstruccion
	 */
	public Long getFolioInstruccion() {
		return folioInstruccion;
	}

	/**
	 * Establece el valor del atributo folioInstruccion
	 * 
	 * @param folioInstruccion
	 *            el valor del atributo folioInstruccion a establecer.
	 */
	public void setFolioInstruccion(Long folioInstruccion) {
		this.folioInstruccion = folioInstruccion;
	}

	/**
	 * Obtiene el atributo fechaLiquidacion
	 * 
	 * @return El atrubuto fechaLiquidacion
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	/**
	 * Establece la propiedad fechaLiquidacion
	 * 
	 * @param fechaLiquidacion
	 *            el campo fechaLiquidacion a establecer
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	/**
	 * Obtiene el atributo esBancoTrabajo
	 * 
	 * @return El atrubuto esBancoTrabajo
	 */
	public boolean isEsBancoTrabajo() {
		return esBancoTrabajo;
	}

	/**
	 * Establece la propiedad esBancoTrabajo
	 * 
	 * @param esBancoTrabajo
	 *            el campo esBancoTrabajo a establecer
	 */
	public void setEsBancoTrabajo(boolean esBancoTrabajo) {
		this.esBancoTrabajo = esBancoTrabajo;
	}

	public boolean isPorPaquete() {
		if(referenciaPaquete != null && referenciaPaquete.length() > 0){
			porPaquete = true;
		}else{
			porPaquete = false;
		}
		return porPaquete;
	}
	
	public void setPorPaquete(boolean porPaquete) {
		this.porPaquete = porPaquete;
	}
	
	public String getReferenciaPaquete() {
		return referenciaPaquete;
	}

	public void setReferenciaPaquete(String referenciaPaquete) {
		this.referenciaPaquete = referenciaPaquete;
	}
	
	public String getTotalOperacionesPaquete() {
		return totalOperacionesPaquete;
	}

	public void setTotalOperacionesPaquete(String totalOperacionesPaquete) {
		this.totalOperacionesPaquete = totalOperacionesPaquete;
	}

	public String getNumeroOperacionPaquete() {
		return numeroOperacionPaquete;
	}

	public void setNumeroOperacionPaquete(String numeroOperacionPaquete) {
		this.numeroOperacionPaquete = numeroOperacionPaquete;
	}

	public String getTotalTitulosPaquete() {
		return totalTitulosPaquete;
	}

	public void setTotalTitulosPaquete(String totalTitulosPaquete) {
		this.totalTitulosPaquete = totalTitulosPaquete;
	}

	public String getTotalImportePaquete() {
		return totalImportePaquete;
	}
	
	public void setTotalImportePaquete(String totalImportePaquete) {
		this.totalImportePaquete = totalImportePaquete;
	}
	
}
