/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;

/**
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class MovimientosMiscFiscalVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;
	private Date fechaTraspaso;
	private BigInteger folio;
	private AgenteVO receptorTraspasante;
	private AgenteVO receptor;
	private Date fechaAdquisicion;
	private EmisionVO emision;
	private Date fechaVencimiento;
	private String estatus;
	private BigInteger cantidadOperada;
	private BigDecimal precioAdquisicion;
	private String cliente;
	private String curpRFC;
	/** Costo promedio actualizado */
    private BigDecimal costoPromedio;
    
	/**
	 * 
	 * @return BigInteger
	 */
	public BigInteger getCantidadOperada() {

		return cantidadOperada;

	}

	/**
	 * 
	 * @param cantidadOperada
	 */
	public void setCantidadOperada(BigInteger cantidadOperada) {

		this.cantidadOperada = cantidadOperada;

	}

	/**
	 * 
	 * @return String
	 */
	public String getCliente() {

		return cliente;

	}

	/**
	 * 
	 * @param cliente
	 */
	public void setCliente(String cliente) {

		this.cliente = cliente;

	}

	/**
	 * 
	 * @return String
	 */
	public String getCurpRFC() {

		return curpRFC;

	}

	/**
	 * 
	 * @param curpRFC
	 */
	public void setCurpRFC(String curpRFC) {

		this.curpRFC = curpRFC;

	}

	/**
	 * 
	 * @return EmisionVO
	 */
	public EmisionVO getEmision() {

		return emision;

	}

	/**
	 * 
	 * @param emision
	 */
	public void setEmision(EmisionVO emision) {

		this.emision = emision;

	}

	/**
	 * 
	 * @return String
	 */
	public String getEstatus() {

		return estatus;

	}

	/**
	 * 
	 * @param estatus
	 */
	public void setEstatus(String estatus) {

		this.estatus = estatus;

	}

	/**
	 * 
	 * @return Date
	 */
	public Date getFechaAdquisicion() {

		return fechaAdquisicion;

	}

	/**
	 * 
	 * @param fechaAdquisicion
	 */
	public void setFechaAdquisicion(Date fechaAdquisicion) {

		this.fechaAdquisicion = clona(fechaAdquisicion);

	}

	/**
	 * 
	 * @return Date
	 */
	public Date getFechaTraspaso() {

		return fechaTraspaso;

	}

	/**
	 * 
	 * @param fechaTraspaso
	 */
	public void setFechaTraspaso(Date fechaTraspaso) {

		this.fechaTraspaso = clona(fechaTraspaso);

	}

	/**
	 * 
	 * @return Date
	 */
	public Date getFechaVencimiento() {

		return fechaVencimiento;

	}

	/**
	 * 
	 * @param fechaVencimiento
	 */
	public void setFechaVencimiento(Date fechaVencimiento) {

		this.fechaVencimiento = clona(fechaVencimiento);

	}

	/**
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getPrecioAdquisicion() {

		return precioAdquisicion;

	}

	/**
	 * 
	 * @param precioAdquisicion
	 */
	public void setPrecioAdquisicion(BigDecimal precioAdquisicion) {

		this.precioAdquisicion = precioAdquisicion;

	}

	/**
	 * 
	 * @return AgenteVO
	 */
	public AgenteVO getReceptorTraspasante() {

		return receptorTraspasante;

	}

	/**
	 * 
	 * @param receptorTraspasante
	 */
	public void setReceptorTraspasante(AgenteVO receptorTraspasante) {

		this.receptorTraspasante = receptorTraspasante;

	}

	/**
	 * 
	 * @return Returns the receptor.
	 */
	public AgenteVO getReceptor() {

		return receptor;

	}

	/**
	 * 
	 * @param receptor
	 *            The receptor to set.
	 */
	public void setReceptor(AgenteVO receptor) {

		this.receptor = receptor;

	}

	/**
	 * 
	 * @return BigInteger
	 */
	public BigInteger getFolio() {

		return folio;

	}

	/**
	 * 
	 * @param folio
	 */
	public void setFolio(BigInteger folio) {

		this.folio = folio;

	}

	/**
	 * 
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
