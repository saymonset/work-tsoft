package com.indeval.portalinternacional.middleware.servicios.multicarga.excell.vo;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("registroErrorAutorizacionMultiempresa")
public class DetalleRegistroErrorMultiempresaVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@XStreamAlias("uoi") 
	@XStreamAsAttribute
	private String uoi;
	
	@XStreamAlias("institucionOrigen") 
	@XStreamAsAttribute
	private String institucionOrigen;
	
	@XStreamAlias("institucionDestino") 
	@XStreamAsAttribute
	private String institucionDestino;
	
	@XStreamAlias("estadoBeneficiario") 
	@XStreamAsAttribute
	private String estadoBeneficiario;
	
	@XStreamAlias("descripcionError") 
	@XStreamAsAttribute
	private String descripcionError;

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
	 * @return the institucionOrigen
	 */
	public String getInstitucionOrigen() {
		return institucionOrigen;
	}

	/**
	 * @param institucionOrigen the institucionOrigen to set
	 */
	public void setInstitucionOrigen(String institucionOrigen) {
		this.institucionOrigen = institucionOrigen;
	}

	/**
	 * @return the institucionDestino
	 */
	public String getInstitucionDestino() {
		return institucionDestino;
	}

	/**
	 * @param institucionDestino the institucionDestino to set
	 */
	public void setInstitucionDestino(String institucionDestino) {
		this.institucionDestino = institucionDestino;
	}
	
	/**
	 * @return the estadoBeneficiario
	 */
	public String getEstadoBeneficiario() {
		return estadoBeneficiario;
	}

	/**
	 * @param estadoBeneficiario the estadoBeneficiario to set
	 */
	public void setEstadoBeneficiario(String estadoBeneficiario) {
		this.estadoBeneficiario = estadoBeneficiario;
	}
	
	/**
	 * @return the descripcionError
	 */
	public String getDescripcionError() {
		return descripcionError;
	}

	/**
	 * @param descripcionError the descripcionError to set
	 */
	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}

}
