package com.indeval.portalinternacional.middleware.servicios.vo;

import java.math.BigDecimal;
import java.util.Date;

public class HistorialBitacoraOperacionesSICDTO {
	
	private BigDecimal folioControl;	
	private Date fechaInAut;
	private Date fechafinAut;
	private Date fechaInOp;
	private Date fechaFinOp;
	private String detalleCustodio;
	private String operacion;
	private String tipoOperacion;
	private String estatusAnterior;
	private String estatusNuevo;
	
	/**
	 * @return the folioControl
	 */
	public BigDecimal getFolioControl() {
		return folioControl;
	}
	/**
	 * @param folioControl the folioControl to set
	 */
	public void setFolioControl(BigDecimal folioControl) {
		this.folioControl = folioControl;
	}
	/**
	 * @return the fechaInAut
	 */
	public Date getFechaInAut() {
		return fechaInAut;
	}
	/**
	 * @param fechaInAut the fechaInAut to set
	 */
	public void setFechaInAut(Date fechaInAut) {
		this.fechaInAut = fechaInAut;
	}
	/**
	 * @return the fechafinAut
	 */
	public Date getFechafinAut() {
		return fechafinAut;
	}
	/**
	 * @param fechafinAut the fechafinAut to set
	 */
	public void setFechafinAut(Date fechafinAut) {
		this.fechafinAut = fechafinAut;
	}
	/**
	 * @return the fechaInOp
	 */
	public Date getFechaInOp() {
		return fechaInOp;
	}
	/**
	 * @param fechaInOp the fechaInOp to set
	 */
	public void setFechaInOp(Date fechaInOp) {
		this.fechaInOp = fechaInOp;
	}
	/**
	 * @return the fechaFinOp
	 */
	public Date getFechaFinOp() {
		return fechaFinOp;
	}
	/**
	 * @param fechaFinOp the fechaFinOp to set
	 */
	public void setFechaFinOp(Date fechaFinOp) {
		this.fechaFinOp = fechaFinOp;
	}
	/**
	 * @return the detalleCustodio
	 */
	public String getDetalleCustodio() {
		return detalleCustodio;
	}
	/**
	 * @param detalleCustodio the detalleCustodio to set
	 */
	public void setDetalleCustodio(String detalleCustodio) {
		this.detalleCustodio = detalleCustodio;
	}
	/**
	 * @return the operacion
	 */
	public String getOperacion() {
		return operacion;
	}
	/**
	 * @param operacion the operacion to set
	 */
	public void setOperacion(String operacion) {
		this.operacion = operacion;
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
	/**
	 * @return the estatusAnterior
	 */
	public String getEstatusAnterior() {
		return estatusAnterior;
	}
	/**
	 * @param estatusAnterior the estatusAnterior to set
	 */
	public void setEstatusAnterior(String estatusAnterior) {
		this.estatusAnterior = estatusAnterior;
	}
	/**
	 * @return the estatusNuevo
	 */
	public String getEstatusNuevo() {
		return estatusNuevo;
	}
	/**
	 * @param estatusNuevo the estatusNuevo to set
	 */
	public void setEstatusNuevo(String estatusNuevo) {
		this.estatusNuevo = estatusNuevo;
	}

}
