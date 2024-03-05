/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.presentation.dto.tesoreria;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 
 * 
 */ 
public class DepositoDivisaVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/** Tipo de Mensaje */
	private Integer tipoMensaje;
	
	/** Fecha de Operaci&oacute; */
	private String fechaOperacion;

	/** Clave del Ordenante */
	private String claveOrdenante;

	/** Nombre del Ordenante */
	private String nombreOrdenante;

	/** Clave del Beneficiario */
	private String claveBeneficiario;

	/** Concepto */
	private String concepto;

	/** Referencia del mensaje */
	private BigInteger referenciaNumerica;

	/** Clave de rastreo */
	private BigInteger claveRastreo;

	/** Tipo de pago */
	private Integer tipoPago;

	/** Monto Abonado */
	private BigDecimal monto;
	
	private String boveda;
	
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
	

	public Integer getTipoMensaje() {
		return tipoMensaje;
	}

	public void setTipoMensaje(Integer tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	/**
	 * @return the fechaOperacion
	 */
	public String getFechaOperacion() {
		return fechaOperacion;
	}

	/**
	 * @param fechaOperacion
	 *            the fechaOperacion to set
	 */
	public void setFechaOperacion(String fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	/**
	 * @return the claveOrdenante
	 */
	public String getClaveOrdenante() {
		return claveOrdenante;
	}

	/**
	 * @param claveOrdenante
	 *            the claveOrdenante to set
	 */
	public void setClaveOrdenante(String claveOrdenante) {
		this.claveOrdenante = claveOrdenante;
	}

	/**
	 * @return the nombreOrdenante
	 */
	public String getNombreOrdenante() {
		return nombreOrdenante;
	}

	/**
	 * @param nombreOrdenante
	 *            the nombreOrdenante to set
	 */
	public void setNombreOrdenante(String nombreOrdenante) {
		this.nombreOrdenante = nombreOrdenante;
	}

	/**
	 * @return the claveBeneficiario
	 */
	public String getClaveBeneficiario() {
		return claveBeneficiario;
	}

	/**
	 * @param claveBeneficiario
	 *            the claveBeneficiario to set
	 */
	public void setClaveBeneficiario(String claveBeneficiario) {
		this.claveBeneficiario = claveBeneficiario;
	}

	/**
	 * @return the concepto
	 */
	public String getConcepto() {
		return concepto;
	}

	/**
	 * @param concepto
	 *            the concepto to set
	 */
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}


	public BigInteger getReferenciaNumerica() {
		return referenciaNumerica;
	}

	public void setReferenciaNumerica(BigInteger referenciaNumerica) {
		this.referenciaNumerica = referenciaNumerica;
	}

	/**
	 * @return the claveRastreo
	 */
	public BigInteger getClaveRastreo() {
		return claveRastreo;
	}

	/**
	 * @param claveRastreo
	 *            the claveRastreo to set
	 */
	public void setClaveRastreo(BigInteger claveRastreo) {
		this.claveRastreo = claveRastreo;
	}

	/**
	 * @return the tipoPago
	 */
	public Integer getTipoPago() {
		return tipoPago;
	}

	/**
	 * @param tipoPago
	 *            the tipoPago to set
	 */
	public void setTipoPago(Integer tipoPago) {
		this.tipoPago = tipoPago;
	}


	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

}
