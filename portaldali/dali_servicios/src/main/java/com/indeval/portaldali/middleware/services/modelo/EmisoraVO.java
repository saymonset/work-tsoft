package com.indeval.portaldali.middleware.services.modelo;

import java.math.BigInteger;

public class EmisoraVO {

	private BigInteger idEmisora;
	
	private String clavePizarra;
	
	private String razonSocial;

	/**
	 * @return the idEmisora
	 */
	public BigInteger getIdEmisora() {
		return idEmisora;
	}

	/**
	 * @param idEmisora the idEmisora to set
	 */
	public void setIdEmisora(BigInteger idEmisora) {
		this.idEmisora = idEmisora;
	}

	/**
	 * @return the clavePizarra
	 */
	public String getClavePizarra() {
		return clavePizarra;
	}

	/**
	 * @param clavePizarra the clavePizarra to set
	 */
	public void setClavePizarra(String clavePizarra) {
		this.clavePizarra = clavePizarra;
	}

	/**
	 * @return the razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * @param razonSocial the razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
}
