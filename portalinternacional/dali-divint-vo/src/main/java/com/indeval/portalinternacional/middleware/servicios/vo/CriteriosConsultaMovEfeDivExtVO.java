// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.util.Date;

public class CriteriosConsultaMovEfeDivExtVO {

	private String idFolioParticipante;
	private String tipoMovimiento;
	private String folioControl;
	private Long idBoveda;
	private Long idDivisa;
	private Long estatusMovimiento;
	private Date fechaInicio;
	private Date fechaFin;
	private Date fechaLiqInicio;
	private Date fechaLiqFin;

	/**
	 * @return the idFolioParticipante
	 */
	public String getIdFolioParticipante() {
		return idFolioParticipante;
	}

	/**
	 * @param idFolioParticipante the idFolioParticipante to set
	 */
	public void setIdFolioParticipante(String idFolioParticipante) {
		this.idFolioParticipante = idFolioParticipante;
	}

	/**
	 * @return the tipoMovimiento
	 */
	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	/**
	 * @param tipoMovimiento the tipoMovimiento to set
	 */
	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	/**
	 * @return the folioControl
	 */
	public String getFolioControl() {
		return folioControl;
	}

	/**
	 * @param folioControl the folioControl to set
	 */
	public void setFolioControl(String folioControl) {
		this.folioControl = folioControl;
	}

	/**
	 * @return the idBoveda
	 */
	public Long getIdBoveda() {
		return idBoveda;
	}

	/**
	 * @param idBoveda the idBoveda to set
	 */
	public void setIdBoveda(Long idBoveda) {
		this.idBoveda = idBoveda;
	}

	/**
	 * @return the idDivisa
	 */
	public Long getIdDivisa() {
		return idDivisa;
	}

	/**
	 * @param idDivisa the idDivisa to set
	 */
	public void setIdDivisa(Long idDivisa) {
		this.idDivisa = idDivisa;
	}

	/**
	 * @return the estatusMovimiento
	 */
	public Long getEstatusMovimiento() {
		return estatusMovimiento;
	}

	/**
	 * @param estatusMovimiento the estatusMovimiento to set
	 */
	public void setEstatusMovimiento(Long estatusMovimiento) {
		this.estatusMovimiento = estatusMovimiento;
	}

	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @return the fechaLiqInicio
	 */
	public Date getFechaLiqInicio() {
		return fechaLiqInicio;
	}

	/**
	 * @param fechaLiqInicio the fechaLiqInicio to set
	 */
	public void setFechaLiqInicio(Date fechaLiqInicio) {
		this.fechaLiqInicio = fechaLiqInicio;
	}

	/**
	 * @return the fechaLiqFin
	 */
	public Date getFechaLiqFin() {
		return fechaLiqFin;
	}

	/**
	 * @param fechaLiqFin the fechaLiqFin to set
	 */
	public void setFechaLiqFin(Date fechaLiqFin) {
		this.fechaLiqFin = fechaLiqFin;
	}

}
