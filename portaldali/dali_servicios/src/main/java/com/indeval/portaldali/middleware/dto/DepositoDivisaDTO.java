/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * 27/02/2008
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * DTO que representa un deposito de divisa
 * 
 * @author Rafael Ibarra
 * @version 1.0
 * 
 */
public class DepositoDivisaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String claveInstitucion;
	
	private String nombreCorto;
	
	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	private String casfim;

	private BigInteger idBoveda;
	
	private String boveda;

	private BigInteger idDivisa;
	
	private String divisa;

	public String getBoveda() {
		return boveda;
	}

	public void setBoveda(String boveda) {
		this.boveda = boveda;
	}

	public String getDivisa() {
		return divisa;
	}

	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	private BigDecimal saldo;

	private BigDecimal importe;

	/**
	 * @return the claveInstitucion
	 */
	public String getClaveInstitucion() {
		return claveInstitucion;
	}

	/**
	 * @param claveInstitucion
	 *            the claveInstitucion to set
	 */
	public void setClaveInstitucion(String claveInstitucion) {
		this.claveInstitucion = claveInstitucion;
	}

	/**
	 * @return the idBoveda
	 */
	public BigInteger getIdBoveda() {
		return idBoveda;
	}

	/**
	 * @param idBoveda
	 *            the idBoveda to set
	 */
	public void setIdBoveda(BigInteger idBoveda) {
		this.idBoveda = idBoveda;
	}

	/**
	 * @return the idDivisa
	 */
	public BigInteger getIdDivisa() {
		return idDivisa;
	}

	/**
	 * @param idDivisa
	 *            the idDivisa to set
	 */
	public void setIdDivisa(BigInteger idDivisa) {
		this.idDivisa = idDivisa;
	}

	/**
	 * @return the saldo
	 */
	public BigDecimal getSaldo() {
		return saldo;
	}

	/**
	 * @param saldo
	 *            the saldo to set
	 */
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	/**
	 * @return the importe
	 */
	public BigDecimal getImporte() {
		return importe;
	}

	/**
	 * @param importe
	 *            the importe to set
	 */
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public String getCasfim() {
		return casfim;
	}

	public void setCasfim(String casfim) {
		this.casfim = casfim;
	}

}
