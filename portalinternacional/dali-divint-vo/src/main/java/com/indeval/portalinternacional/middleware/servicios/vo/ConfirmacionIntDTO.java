/**
 * 
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.util.Date;
import java.util.List;

/**
 * @author César Hernández
 *
 */
public class ConfirmacionIntDTO {
	
	public static final int TIPO_NOTIFICACION_TODAS = -1;
	public static final int TIPO_NOTIFICACION_CREDITO = 1;
	public static final int TIPO_NOTIFICACION_DEBITO = 2;
	
	private List<String> bicCodes;
	private List<String> divisas;
	private List<String> cuentas;
	private List<String> tipoMensajes;
	private Date fechaRecepcionMensajeInicio;
	private Date fechaRecepcionMensajeFin;
	private Date fechaCreditoDebitoInicio;
	private Date fechaCreditoDebitoFin;
	private Date fechaRecepcionDaliInicio;
	private Date fechaRecepcionDaliFin;
	private boolean cuentasCustodia;
	private boolean cuentasComercial;
	private String referenciaMensaje;
	private String mtEspecifico;
	private String relatedReference;
	private String idFolio;
	private String isin;
	private int tipoNotificacion;
	
	/**
	 * @return the bicCodes
	 */
	public List<String> getBicCodes() {
		return bicCodes;
	}
	
	/**
	 * @param bicCodes the bicCodes to set
	 */
	public void setBicCodes(List<String> bicCodes) {
		this.bicCodes = bicCodes;
	}
	
	/**
	 * @return the cuentas
	 */
	public List<String> getCuentas() {
		return cuentas;
	}
	/**
	 * @param cuentas the cuentas to set
	 */
	public void setCuentas(List<String> cuentas) {
		this.cuentas = cuentas;
	}
	
	/**
	 * @return the fechaRecepcionMensajeInicio
	 */
	public Date getFechaRecepcionMensajeInicio() {
		return fechaRecepcionMensajeInicio;
	}
	
	/**
	 * @param fechaRecepcionMensajeInicio the fechaRecepcionMensajeInicio to set
	 */
	public void setFechaRecepcionMensajeInicio(Date fechaRecepcionMensajeInicio) {
		this.fechaRecepcionMensajeInicio = fechaRecepcionMensajeInicio;
	}
	/**
	 * @return the fechaRecepcionMensajeFin
	 */
	public Date getFechaRecepcionMensajeFin() {
		return fechaRecepcionMensajeFin;
	}
	
	/**
	 * @param fechaRecepcionMensajeFin the fechaRecepcionMensajeFin to set
	 */
	public void setFechaRecepcionMensajeFin(Date fechaRecepcionMensajeFin) {
		this.fechaRecepcionMensajeFin = fechaRecepcionMensajeFin;
	}
	
	/**
	 * @return the fechaCreditoDebitoInicio
	 */
	public Date getFechaCreditoDebitoInicio() {
		return fechaCreditoDebitoInicio;
	}
	
	/**
	 * @param fechaCreditoDebitoInicio the fechaCreditoDebitoInicio to set
	 */
	public void setFechaCreditoDebitoInicio(Date fechaCreditoDebitoInicio) {
		this.fechaCreditoDebitoInicio = fechaCreditoDebitoInicio;
	}
	
	/**
	 * @return the fechaCreditoDebitoFin
	 */
	public Date getFechaCreditoDebitoFin() {
		return fechaCreditoDebitoFin;
	}
	
	/**
	 * @param fechaCreditoDebitoFin the fechaCreditoDebitoFin to set
	 */
	public void setFechaCreditoDebitoFin(Date fechaCreditoDebitoFin) {
		this.fechaCreditoDebitoFin = fechaCreditoDebitoFin;
	}
	
	/**
	 * @return the cuentasCustodia
	 */
	public boolean isCuentasCustodia() {
		return cuentasCustodia;
	}
	
	/**
	 * @param cuentasCustodia the cuentasCustodia to set
	 */
	public void setCuentasCustodia(boolean cuentasCustodia) {
		this.cuentasCustodia = cuentasCustodia;
	}
	
	/**
	 * @return the cuentasComercial
	 */
	public boolean isCuentasComercial() {
		return cuentasComercial;
	}
	
	/**
	 * @param cuentasComercial the cuentasComercial to set
	 */
	public void setCuentasComercial(boolean cuentasComercial) {
		this.cuentasComercial = cuentasComercial;
	}
	
	/**
	 * @return the divisas
	 */
	public List<String> getDivisas() {
		return divisas;
	}
	
	/**
	 * @param divisas the divisas to set
	 */
	public void setDivisas(List<String> divisas) {
		this.divisas = divisas;
	}

	/**
	 * @return the tipoMensajes
	 */
	public List<String> getTipoMensajes() {
		return tipoMensajes;
	}

	/**
	 * @param tipoMensajes the tipoMensajes to set
	 */
	public void setTipoMensajes(List<String> tipoMensajes) {
		this.tipoMensajes = tipoMensajes;
	}

	/**
	 * @return the referenciaMensaje
	 */
	public String getReferenciaMensaje() {
		return referenciaMensaje;
	}

	/**
	 * @param referenciaMensaje the referenciaMensaje to set
	 */
	public void setReferenciaMensaje(String referenciaMensaje) {
		this.referenciaMensaje = referenciaMensaje;
	}

	/**
	 * @return the mtEspecifico
	 */
	public String getMtEspecifico() {
		return mtEspecifico;
	}

	/**
	 * @param mtEspecifico the mtEspecifico to set
	 */
	public void setMtEspecifico(String mtEspecifico) {
		this.mtEspecifico = mtEspecifico;
	}

	/**
	 * @return the tipoNotificacion
	 */
	public int getTipoNotificacion() {
		return tipoNotificacion;
	}

	/**
	 * @param tipoNotificacion the tipoNotificacion to set
	 */
	public void setTipoNotificacion(int tipoNotificacion) {
		this.tipoNotificacion = tipoNotificacion;
	}

	/**
	 * @return the fechaRecepcionDaliInicio
	 */
	public Date getFechaRecepcionDaliInicio() {
		return fechaRecepcionDaliInicio;
	}

	/**
	 * @param fechaRecepcionDaliInicio the fechaRecepcionDaliInicio to set
	 */
	public void setFechaRecepcionDaliInicio(Date fechaRecepcionDaliInicio) {
		this.fechaRecepcionDaliInicio = fechaRecepcionDaliInicio;
	}

	/**
	 * @return the fechaRecepcionDaliFin
	 */
	public Date getFechaRecepcionDaliFin() {
		return fechaRecepcionDaliFin;
	}

	/**
	 * @param fechaRecepcionDaliFin the fechaRecepcionDaliFin to set
	 */
	public void setFechaRecepcionDaliFin(Date fechaRecepcionDaliFin) {
		this.fechaRecepcionDaliFin = fechaRecepcionDaliFin;
	}

	/**
	 * @return the relatedReference
	 */
	public String getRelatedReference() {
		return relatedReference;
	}

	/**
	 * @param relatedReference the relatedReference to set
	 */
	public void setRelatedReference(String relatedReference) {
		this.relatedReference = relatedReference;
	}

	/**
	 * @return the idFolio
	 */
	public String getIdFolio() {
		return idFolio;
	}

	/**
	 * @param idFolio the idFolio to set
	 */
	public void setIdFolio(String idFolio) {
		this.idFolio = idFolio;
	}

	/**
	 * @return the isin
	 */
	public String getIsin() {
		return isin;
	}

	/**
	 * @param isin the isin to set
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}
}
