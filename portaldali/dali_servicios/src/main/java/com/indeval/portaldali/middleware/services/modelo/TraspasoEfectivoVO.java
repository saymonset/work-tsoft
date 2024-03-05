/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.modelo;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class TraspasoEfectivoVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private AgenteVO parte;

	private AgenteVO contraparte;

	private String aplicacion;

	private String cuenta;

	private BigDecimal folioOperacion;

	private String folioOriginal;

	private Date fechaHora;

	private BigDecimal importe;

	private String mercado;

	private String movimiento;

	private String origen;

	/**
	 * @return String
	 */
	public String getAplicacion() {
		return aplicacion;
	}

	/**
	 * @param aplicacion
	 */
	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	/**
	 * @return AgenteVO
	 */
	public AgenteVO getContraparte() {
		return contraparte;
	}

	/**
	 * @param contraparte
	 */
	public void setContraparte(AgenteVO contraparte) {
		this.contraparte = contraparte;
	}

	/**
	 * @return String
	 */
	public String getCuenta() {
		return cuenta;
	}

	/**
	 * @param cuenta
	 */
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	/**
	 * @return Date
	 */
	public Date getFechaHora() {
		return fechaHora;
	}

	/**
	 * @param fechaHora
	 */
	public void setFechaHora(Date fechaHora) {
		this.fechaHora = clona(fechaHora);
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getFolioOperacion() {
		return folioOperacion;
	}

	/**
	 * @param folioOperacion
	 */
	public void setFolioOperacion(BigDecimal folioOperacion) {
		this.folioOperacion = folioOperacion;
	}

	/**
	 * @return String
	 */
	public String getFolioOriginal() {
		return folioOriginal;
	}

	/**
	 * @param folioOriginal
	 */
	public void setFolioOriginal(String folioOriginal) {
		this.folioOriginal = folioOriginal;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getImporte() {
		return importe;
	}

	/**
	 * @param importe
	 */
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	/**
	 * @return String
	 */
	public String getMercado() {
		return mercado;
	}

	/**
	 * @param mercado
	 */
	public void setMercado(String mercado) {
		this.mercado = mercado;
	}

	/**
	 * @return String
	 */
	public String getMovimiento() {
		return movimiento;
	}

	/**
	 * @param movimiento
	 */
	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}

	/**
	 * @return String
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * @param origen
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * @return AgenteVO
	 */
	public AgenteVO getParte() {
		return parte;
	}

	/**
	 * @param parte
	 */
	public void setParte(AgenteVO parte) {
		this.parte = parte;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

}
