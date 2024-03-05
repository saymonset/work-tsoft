/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import org.springframework.validation.Errors;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class GetCapturaTraspasoParams extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private AgenteVO agenteFirmado;

	private AgenteVO traspasante;

	private EmisionVO emision;

	private BigDecimal precioVector;

	private BigInteger saldoDisponible;

	private BigInteger saldoActual;

	private AgenteVO receptor;

	private BigDecimal cantidad;

	private Date fechaAdquisicion;

	private BigDecimal precioAdquisicion;

	private String cliente;

	private String rfcCurp;

	private Date fecha;

	private String cveReporto;

	private String tv;

	private String divisa; 

	private Date fechaLiquidacion;
	
	private Date fechaHoraCierreOper;

	private Integer folioControl;

	private String llaveFolioMd;

	private String folioDescripcion;

	private String serie;

	private String tipoOperacion;

	/** Costo promedio actualizado */
    private BigDecimal costoPromedio;
    
	/**
	 * @return String
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * @param serie
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	/**
	 * @return String
	 */
	public String getFolioDescripcion() {
		return folioDescripcion;
	}

	/**
	 * @param folioDescripcion
	 */
	public void setFolioDescripcion(String folioDescripcion) {
		this.folioDescripcion = folioDescripcion;
	}

	/**
	 * @return Date
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

	public Date getFechaHoraCierreOper() {
		return fechaHoraCierreOper;
	}

	public void setFechaHoraCierreOper(Date fechaHoraCierreOper) {
		this.fechaHoraCierreOper = fechaHoraCierreOper;
	}

	/**
	 * @return String
	 */
	public String getDivisa() {
		return divisa;
	}

	/**
	 * @param divisa
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	/**
	 * @return String
	 */
	public String getTv() {
		return tv;
	}

	/**
	 * @param tv
	 */
	public void setTv(String tv) {
		this.tv = tv;
	}

	/**
	 * @return String
	 */
	public String getCveReporto() {
		return cveReporto;
	}

	/**
	 * @param cveReporto
	 */
	public void setCveReporto(String cveReporto) {
		this.cveReporto = cveReporto;
	}

	/**
	 * @return AgenteVO
	 */
	public AgenteVO getAgenteFirmado() {
		return agenteFirmado;
	}

	/**
	 * @param agenteFirmado
	 */
	public void setAgenteFirmado(AgenteVO agenteFirmado) {
		this.agenteFirmado = agenteFirmado;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad
	 */
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return String
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
	 * @return EmisionVO
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
	 * @return Date
	 */
	public Date getFechaAdquisicion() {
		return fechaAdquisicion;
	}

	/**
	 * @param fechaAdquisicion
	 */
	public void setFechaAdquisicion(Date fechaAdquisicion) {
		this.fechaAdquisicion = clona(fechaAdquisicion);
	}

	/**
	 * @return BigDecimal
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
	 * @return BigDecimal
	 */
	public BigDecimal getPrecioVector() {
		return precioVector;
	}

	/**
	 * @param precioVector
	 */
	public void setPrecioVector(BigDecimal precioVector) {
		this.precioVector = precioVector;
	}

	/**
	 * @return AgenteVO
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
	 * @return String
	 */
	public String getRfcCurp() {
		return rfcCurp;
	}

	/**
	 * @param rfcCurp
	 */
	public void setRfcCurp(String rfcCurp) {
		this.rfcCurp = rfcCurp;
	}

	/**
	 * @return BigInteger
	 */
	public BigInteger getSaldoActual() {
		return saldoActual;
	}

	/**
	 * @param saldoActual
	 */
	public void setSaldoActual(BigInteger saldoActual) {
		this.saldoActual = saldoActual;
	}

	/**
	 * @return BigInteger
	 */
	public BigInteger getSaldoDisponible() {
		return saldoDisponible;
	}

	/**
	 * @param saldoDisponible
	 */
	public void setSaldoDisponible(BigInteger saldoDisponible) {
		this.saldoDisponible = saldoDisponible;
	}

	/**
	 * @return AgenteVO
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
	 * @return Integer
	 */
	public Integer getFolioControl() {
		return folioControl;
	}

	/**
	 * @param folioControl
	 */
	public void setFolioControl(Integer folioControl) {
		this.folioControl = folioControl;
	}

	/**
	 * @return String
	 */
	public String getLlaveFolioMd() {
		return llaveFolioMd;
	}

	/**
	 * @param llaveFolioMd
	 */
	public void setLlaveFolioMd(String llaveFolioMd) {
		this.llaveFolioMd = llaveFolioMd;
	}

	/**
	 * @return Date
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha
	 */
	public void setFecha(Date fecha) {
		this.fecha = clona(fecha);
	}

	/**
	 * @return String
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
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object arg0, Errors arg1) {
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
