package com.indeval.portalinternacional.middleware.servicios.multicarga.excell.vo;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("registroErrorAutorizacionAdp")
public class DetalleRegistroErrorAdpVO implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@XStreamAlias("uoi") 
	@XStreamAsAttribute
	private String uoi;
	
	@XStreamAlias("adp") 
	@XStreamAsAttribute
	private String adp;
	
	@XStreamAlias("nombreCustodio") 
	@XStreamAsAttribute
	private String nombreCustodio;
	
	@XStreamAlias("estadoBeneficiario") 
	@XStreamAsAttribute
	private String estadoBeneficiario;
	
	@XStreamAlias("descripcionError") 
	@XStreamAsAttribute
	private String descripcionError;
	
	

	public String getUoi() {
		return uoi;
	}

	public void setUoi(String uoi) {
		this.uoi = uoi;
	}

	public String getAdp() {
		return adp;
	}

	public void setAdp(String adp) {
		this.adp = adp;
	}

	public String getNombreCustodio() {
		return nombreCustodio;
	}

	public void setNombreCustodio(String nombreCustodio) {
		this.nombreCustodio = nombreCustodio;
	}

	public String getEstadoBeneficiario() {
		return estadoBeneficiario;
	}

	public void setEstadoBeneficiario(String estadoBeneficiario) {
		this.estadoBeneficiario = estadoBeneficiario;
	}

	public String getDescripcionError() {
		return descripcionError;
	}

	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}

	
}
