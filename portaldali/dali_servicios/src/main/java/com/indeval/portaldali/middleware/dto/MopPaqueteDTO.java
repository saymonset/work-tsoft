/**
 * 
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class MopPaqueteDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String folioPaquete;
	private Long folioControl;
	private String numTraspasos;
//	private String remitente;
	private String referenciaPaquete;
	private Integer totalOperacionesPaquete;
	private Integer numeroOperacionPaquete;
	private Long totalTitulosPaquete;
	private BigDecimal totalImportePaquete;
	private String mensaje;
	private List<MopInstruccionLiquidacionDTO> instruccionLiquidaciones;
	
	/**
	 * @return the instruccionLiquidacionDTO
	 */
	public List<MopInstruccionLiquidacionDTO> getInstruccionLiquidaciones() {
		return this.instruccionLiquidaciones;
	}

	/**
	 * @return the numeroOperacion
	 */
	public Integer getNumeroOperacionPaquete() {
		return this.numeroOperacionPaquete;
	}

	/**
	 * @return the referenciaPaquete
	 */
	public String getReferenciaPaquete() {
		return this.referenciaPaquete;
	}

	/**
	 * @return the totalImportePaquete
	 */
	public BigDecimal getTotalImportePaquete() {
		return this.totalImportePaquete;
	}

	/**
	 * @return the totalOperacionesPaquete
	 */
	public Integer getTotalOperacionesPaquete() {
		return this.totalOperacionesPaquete;
	}

	/**
	 * @return the totalTitulosPaquete
	 */
	public Long getTotalTitulosPaquete() {
		return this.totalTitulosPaquete;
	}

	/**
	 * @param instruccionLiquidacionDTO the instruccionLiquidacionDTO to set
	 */
	public void setInstruccionLiquidaciones(
			List<MopInstruccionLiquidacionDTO> instruccionLiquidaciones) {
		this.instruccionLiquidaciones = instruccionLiquidaciones;
	}

	/**
	 * @param numeroOperacion the numeroOperacion to set
	 */
	public void setNumeroOperacionPaquete(Integer numeroOperacionPaquete) {
		this.numeroOperacionPaquete = numeroOperacionPaquete;
	}

	/**
	 * @param referenciaPaquete
	 *            the referenciaPaquete to set
	 */
	public void setReferenciaPaquete(String referenciaPaquete) {
		this.referenciaPaquete = referenciaPaquete;
	}

	/**
	 * @param totalImportePaquete
	 *            the totalImportePaquete to set
	 */
	public void setTotalImportePaquete(BigDecimal totalImportePaquete) {
		this.totalImportePaquete = totalImportePaquete;
	}

	/**
	 * @param totalOperacionesPaquete
	 *            the totalOperacionesPaquete to set
	 */
	public void setTotalOperacionesPaquete(Integer totalOperacionesPaquete) {
		this.totalOperacionesPaquete = totalOperacionesPaquete;
	}

	/**
	 * @param totalTitulosPaquete
	 *            the totalTitulosPaquete to set
	 */
	public void setTotalTitulosPaquete(Long totalTitulosPaquete) {
		this.totalTitulosPaquete = totalTitulosPaquete;
	}

	public String getFolioPaquete() {
		return folioPaquete;
	}

	public void setFolioPaquete(String folioPaquete) {
		this.folioPaquete = folioPaquete;
	}

	public String getNumTraspasos() {
		return numTraspasos;
	}

	public void setNumTraspasos(String numTraspasos) {
		this.numTraspasos = numTraspasos;
	}

//	public String getRemitente() {
//		return remitente;
//	}
//
//	public void setRemitente(String remitente) {
//		this.remitente = remitente;
//	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Long getFolioControl() {
		return folioControl;
	}

	public void setFolioControl(Long folioControl) {
		this.folioControl = folioControl;
	}

}
