package com.indeval.portalinternacional.presentation.controller.derechos;

import java.io.Serializable;
import java.util.Date;

public class AsignacionBeneficiarioVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** ID FOLIO **/
	private String folioID;
	
	/** Cuenta **/
	private String cuenta;
	
	/** Tipo de Valor **/
	private String tipoValor;
	
	/** Emisora **/
	private String emisora;
	
	/** Serie **/
	private String serie;
	
	/** Fecha de Corte **/
	private Date fechaCorte;
	
	/** Fecha de Corte **/
	private String fechaCorteString;
	
	/** UOI **/
    private String uoi;
    
    /** Cantidad titulos **/
    private Long asignacion;
    
    /** Porcentaje de Retencion **/
    private Double porcentajeRetencion;
    
    /** Error **/
    private String error;

	public AsignacionBeneficiarioVO() {
		super();
	}

	/**
	 * @return the uoi
	 */
	public String getUoi() {
		return uoi;
	}

	/**
	 * @param uoi the uoi to set
	 */
	public void setUoi(String uoi) {
		this.uoi = uoi;
	}

	/**
	 * @return the asignacion
	 */
	public Long getAsignacion() {
		return asignacion;
	}

	/**
	 * @param asignacion the asignacion to set
	 */
	public void setAsignacion(Long asignacion) {
		this.asignacion = asignacion;
	}

	/**
	 * @return the porcentajeRetencion
	 */
	public Double getPorcentajeRetencion() {
		return porcentajeRetencion;
	}

	/**
	 * @param porcentajeRetencion the porcentajeRetencion to set
	 */
	public void setPorcentajeRetencion(Double porcentajeRetencion) {
		this.porcentajeRetencion = porcentajeRetencion;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @return the folioID
	 */
	public String getFolioID() {
		return folioID;
	}

	/**
	 * @param folioID the folioID to set
	 */
	public void setFolioID(String folioID) {
		this.folioID = folioID;
	}

	/**
	 * @return the cuenta
	 */
	public String getCuenta() {
		return cuenta;
	}

	/**
	 * @param cuenta the cuenta to set
	 */
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	/**
	 * @return the tipoValor
	 */
	public String getTipoValor() {
		return tipoValor;
	}

	/**
	 * @param tipoValor the tipoValor to set
	 */
	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
	}

	/**
	 * @return the emisora
	 */
	public String getEmisora() {
		return emisora;
	}

	/**
	 * @param emisora the emisora to set
	 */
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}

	/**
	 * @return the serie
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * @param serie the serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	/**
	 * @return the fechaCorte
	 */
	public Date getFechaCorte() {
		return fechaCorte;
	}

	/**
	 * @param fechaCorte the fechaCorte to set
	 */
	public void setFechaCorte(Date fechaCorte) {
		this.fechaCorte = fechaCorte;
	}

	public String getFechaCorteString() {
		return fechaCorteString;
	}

	public void setFechaCorteString(String fechaCorteString) {
		this.fechaCorteString = fechaCorteString;
	}

}
