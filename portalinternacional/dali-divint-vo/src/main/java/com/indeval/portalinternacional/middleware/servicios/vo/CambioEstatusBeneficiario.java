package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;

public class CambioEstatusBeneficiario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Uoi del beneficiario **/
	private String uoi;

	/** Estado a actualizar **/
	private String estadoActualBeneficiario;
	
	/** Estado a actualizar **/
	private String estadoActualizadoBeneficiario;
	
	/** Indica si se actualizo el registro o no **/
	private String registroActualizado;
	
	/** Indica si hubo errores **/
	private String error;

	/**
	 * 
	 */
	public CambioEstatusBeneficiario() {
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
	 * @return the estadoActualBeneficiario
	 */
	public String getEstadoActualBeneficiario() {
		return estadoActualBeneficiario;
	}

	/**
	 * @param estadoActualBeneficiario the estadoActualBeneficiario to set
	 */
	public void setEstadoActualBeneficiario(String estadoActualBeneficiario) {
		this.estadoActualBeneficiario = estadoActualBeneficiario;
	}

	/**
	 * @return the estadoBeneficiario
	 */
	public String getEstadoActualizadoBeneficiario() {
		return estadoActualizadoBeneficiario;
	}

	/**
	 * @param estadoBeneficiario the estadoBeneficiario to set
	 */
	public void setEstadoActualizadoBeneficiario(String estadoActualizadoBeneficiario) {
		this.estadoActualizadoBeneficiario = estadoActualizadoBeneficiario;
	}

	/**
	 * @return the registroActualizado
	 */
	public String getRegistroActualizado() {
		return registroActualizado;
	}

	/**
	 * @param registroActualizado the registroActualizado to set
	 */
	public void setRegistroActualizado(String registroActualizado) {
		this.registroActualizado = registroActualizado;
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

}
