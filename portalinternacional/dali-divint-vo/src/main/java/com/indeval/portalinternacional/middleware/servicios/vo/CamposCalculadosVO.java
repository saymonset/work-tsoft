/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.servicios.modelo.AbstractBaseDTO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class CamposCalculadosVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private Date fechaLiquidacion;

	private Date fechaReporto;

	private Date fechaConcertacion;

	private BigDecimal precioTitulo;

	private BigDecimal tasaPremio;

	private BigDecimal importe;

	private String mercado;

	private String origenAplicacion;

	private String origen;

	private String divisa;

	private String sociedadSerie;

	/**
	 * @return the fechaLiquidacion
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	/**
	 * @param fechaLiquidacion the fechaLiquidacion to set
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	/**
	 * @return the fechaReporto
	 */
	public Date getFechaReporto() {
		return fechaReporto;
	}

	/**
	 * @param fechaReporto the fechaReporto to set
	 */
	public void setFechaReporto(Date fechaReporto) {
		this.fechaReporto = fechaReporto;
	}

	/**
	 * @return the fechaConcertacion
	 */
	public Date getFechaConcertacion() {
		return fechaConcertacion;
	}

	/**
	 * @param fechaConcertacion the fechaConcertacion to set
	 */
	public void setFechaConcertacion(Date fechaConcertacion) {
		this.fechaConcertacion = fechaConcertacion;
	}

	/**
	 * @return the precioTitulo
	 */
	public BigDecimal getPrecioTitulo() {
		return precioTitulo;
	}

	/**
	 * @param precioTitulo the precioTitulo to set
	 */
	public void setPrecioTitulo(BigDecimal precioTitulo) {
		this.precioTitulo = precioTitulo;
	}

	/**
	 * @return the tasaPremio
	 */
	public BigDecimal getTasaPremio() {
		return tasaPremio;
	}

	/**
	 * @param tasaPremio the tasaPremio to set
	 */
	public void setTasaPremio(BigDecimal tasaPremio) {
		this.tasaPremio = tasaPremio;
	}

	/**
	 * @return the importe
	 */
	public BigDecimal getImporte() {
		return importe;
	}

	/**
	 * @param importe the importe to set
	 */
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	/**
	 * @return the mercado
	 */
	public String getMercado() {
		return mercado;
	}

	/**
	 * @param mercado the mercado to set
	 */
	public void setMercado(String mercado) {
		this.mercado = mercado;
	}

	/**
	 * @return the origenAplicacion
	 */
	public String getOrigenAplicacion() {
		return origenAplicacion;
	}

	/**
	 * @param origenAplicacion the origenAplicacion to set
	 */
	public void setOrigenAplicacion(String origenAplicacion) {
		this.origenAplicacion = origenAplicacion;
	}

	/**
	 * @return the origen
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * @param origen the origen to set
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * @return the divisa
	 */
	public String getDivisa() {
		return divisa;
	}

	/**
	 * @param divisa the divisa to set
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	/**
	 * @return the sociedadSerie
	 */
	public String getSociedadSerie() {
		return sociedadSerie;
	}

	/**
	 * @param sociedadSerie the sociedadSerie to set
	 */
	public void setSociedadSerie(String sociedadSerie) {
		this.sociedadSerie = sociedadSerie;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {

	}

}
