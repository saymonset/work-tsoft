/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class TraspasoMiscFiscalVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private AgenteVO traspasante;

	private AgenteVO receptor;

	private EmisionVO emision;

	private BigDecimal cantidadOperada;

	private Date fechaLiquidacion;

	private Date fechaAquisicion;

	private BigDecimal precioAdquisicion;

	private String cliente;

	private String curpRFC;

	private String folio;

	private String tipoOperacion; // [aperturaSistema|traspasoMiscelaneaFiscal]

	private String mensajeCargo;

	/** Costo promedio actualizado */
    private BigDecimal costoPromedio;
    
	/**
	 * @return
	 */
	public BigDecimal getCantidadOperada() {
		return cantidadOperada;
	}

	/**
	 * @param cantidadOperada
	 */
	public void setCantidadOperada(BigDecimal cantidadOperada) {
		this.cantidadOperada = cantidadOperada;
	}

	/**
	 * @return
	 */
	public String getCliente() {
		return cliente;
	}

	/**
	 * @param cliente
	 */
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return
	 */
	public String getCurpRFC() {
		return curpRFC;
	}

	/**
	 * @param curpRFC
	 */
	public void setCurpRFC(String curpRFC) {
		this.curpRFC = curpRFC;
	}

	/**
	 * @return
	 */
	public EmisionVO getEmision() {
		return emision;
	}

	/**
	 * @param emision
	 */
	public void setEmision(EmisionVO emision) {
		this.emision = emision;
	}

	/**
	 * @return
	 */
	public Date getFechaAquisicion() {
		return fechaAquisicion;
	}

	/**
	 * @param fechaAquisicion
	 */
	public void setFechaAquisicion(Date fechaAquisicion) {
		this.fechaAquisicion = clona(fechaAquisicion);
	}

	/**
	 * @return
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	/**
	 * @param fechaLiquidacion
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = clona(fechaLiquidacion);
	}

	/**
	 * @return
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * @return
	 */
	public BigDecimal getPrecioAdquisicion() {
		return precioAdquisicion;
	}

	/**
	 * @param precioAdquisicion
	 */
	public void setPrecioAdquisicion(BigDecimal precioAdquisicion) {
		this.precioAdquisicion = precioAdquisicion;
	}

	/**
	 * @return
	 */
	public AgenteVO getReceptor() {
		return receptor;
	}

	/**
	 * @param receptor
	 */
	public void setReceptor(AgenteVO receptor) {
		this.receptor = receptor;
	}

	/**
	 * @return
	 */
	public String getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * @param tipoOperacion
	 */
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	/**
	 * @return
	 */
	public AgenteVO getTraspasante() {
		return traspasante;
	}

	/**
	 * @param traspasante
	 */
	public void setTraspasante(AgenteVO traspasante) {
		this.traspasante = traspasante;
	}

	/**
	 * @return
	 */
	public String getMensajeCargo() {
		return mensajeCargo;
	}

	/**
	 * @param mensajeCargo
	 */
	public void setMensajeCargo(String mensajeCargo) {
		this.mensajeCargo = mensajeCargo;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

	/**
	 * @return the costoPromedio
	 */
	public BigDecimal getCostoPromedio() {
		return costoPromedio;
	}

	/**
	 * @param costoPromedio the costoPromedio to set
	 */
	public void setCostoPromedio(BigDecimal costoPromedio) {
		this.costoPromedio = costoPromedio;
	}

}
