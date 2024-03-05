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
 * Clase de transporte
 * 
 * @author cerjio
 */
public class OperacionDiaDetalleDineroVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private AgenteVO comprador;

	private AgenteVO vendedor;

	private String folio;

	private String folioDescripcion;

	private EmisionVO emisionVO;

	private Date fechaCaptura;

	private Date fechaTerm;

	private BigDecimal cantidad;

	private BigDecimal precio;

	private Integer plazo;

	private Integer liquidacion;

	private BigDecimal tasa;

	private BigDecimal importe;

	private BigDecimal tasaReferencial;

	private BigDecimal premio;

	private BigInteger folioControl;

	private String claveReporto;

	private String bajaLogica;

	private String origen;

	private String origenAplicacion;

	private String divisa;

	private Date fechaHora;

	private Date fechaReporto;

	private Integer tasaRef;

	private BigInteger folioTransmision;

	private String llaveFolio;

	private AgenteVO emisor;

	private AgenteVO receptor;

	/**
	 * @return the emisor
	 */
	public AgenteVO getEmisor() {
		return emisor;
	}

	/**
	 * @param emisor
	 *            the emisor to set
	 */
	public void setEmisor(AgenteVO emisor) {
		this.emisor = emisor;
	}

	/**
	 * @return the receptor
	 */
	public AgenteVO getReceptor() {
		return receptor;
	}

	/**
	 * @param receptor
	 *            the receptor to set
	 */
	public void setReceptor(AgenteVO receptor) {
		this.receptor = receptor;
	}

	/**
	 * @return the llaveFolio
	 */
	public String getLlaveFolio() {
		return llaveFolio;
	}

	/**
	 * @param llaveFolio
	 *            the llaveFolio to set
	 */
	public void setLlaveFolio(String llaveFolio) {
		this.llaveFolio = llaveFolio;
	}

	/**
	 * @return the tasaRef
	 */
	public Integer getTasaRef() {
		return tasaRef;
	}

	/**
	 * @param tasaRef
	 *            the tasaRef to set
	 */
	public void setTasaRef(Integer tasaRef) {
		this.tasaRef = tasaRef;
	}

	/**
	 * @return the comprador
	 */
	public AgenteVO getComprador() {
		return comprador;
	}

	/**
	 * @param comprador
	 *            the comprador to set
	 */
	public void setComprador(AgenteVO comprador) {
		this.comprador = comprador;
	}

	/**
	 * @return the vendedor
	 */
	public AgenteVO getVendedor() {
		return vendedor;
	}

	/**
	 * @param vendedor
	 *            the vendedor to set
	 */
	public void setVendedor(AgenteVO vendedor) {
		this.vendedor = vendedor;
	}

	/**
	 * @return the bajaLogica
	 */
	public String getBajaLogica() {
		return bajaLogica;
	}

	/**
	 * @param bajaLogica
	 *            the bajaLogica to set
	 */
	public void setBajaLogica(String bajaLogica) {
		this.bajaLogica = bajaLogica;
	}

	/**
	 * @return the cantidad
	 */
	public BigDecimal getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad
	 *            the cantidad to set
	 */
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the claveReporto
	 */
	public String getClaveReporto() {
		return claveReporto;
	}

	/**
	 * @param claveReporto
	 *            the claveReporto to set
	 */
	public void setClaveReporto(String claveReporto) {
		this.claveReporto = claveReporto;
	}

	/**
	 * @return the divisa
	 */
	public String getDivisa() {
		return divisa;
	}

	/**
	 * @param divisa
	 *            the divisa to set
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	/**
	 * @return the emisionVO
	 */
	public EmisionVO getEmisionVO() {
		return emisionVO;
	}

	/**
	 * @param emisionVO
	 *            the emisionVO to set
	 */
	public void setEmisionVO(EmisionVO emisionVO) {
		this.emisionVO = emisionVO;
	}

	/**
	 * @return the fechaCaptura
	 */
	public Date getFechaCaptura() {
		return fechaCaptura;
	}

	/**
	 * @param fechaCaptura
	 *            the fechaCaptura to set
	 */
	public void setFechaCaptura(Date fechaCaptura) {
		this.fechaCaptura = fechaCaptura;
	}

	/**
	 * @return the fechaTerm
	 */
	public Date getFechaTerm() {
		return fechaTerm;
	}

	/**
	 * @param fechaTerm
	 *            the fechaTerm to set
	 */
	public void setFechaTerm(Date fechaTerm) {
		this.fechaTerm = fechaTerm;
	}

	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio
	 *            the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * @return the folioControl
	 */
	public BigInteger getFolioControl() {
		return folioControl;
	}

	/**
	 * @param folioControl
	 *            the folioControl to set
	 */
	public void setFolioControl(BigInteger folioControl) {
		this.folioControl = folioControl;
	}

	/**
	 * @return the folioDescripcion
	 */
	public String getFolioDescripcion() {
		return folioDescripcion;
	}

	/**
	 * @param folioDescripcion
	 *            the folioDescripcion to set
	 */
	public void setFolioDescripcion(String folioDescripcion) {
		this.folioDescripcion = folioDescripcion;
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

	/**
	 * @return the liquidacion
	 */
	public Integer getLiquidacion() {
		return liquidacion;
	}

	/**
	 * @param liquidacion
	 *            the liquidacion to set
	 */
	public void setLiquidacion(Integer liquidacion) {
		this.liquidacion = liquidacion;
	}

	/**
	 * @return the origen
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * @param origen
	 *            the origen to set
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * @return the origenAplicacion
	 */
	public String getOrigenAplicacion() {
		return origenAplicacion;
	}

	/**
	 * @param origenAplicacion
	 *            the origenAplicacion to set
	 */
	public void setOrigenAplicacion(String origenAplicacion) {
		this.origenAplicacion = origenAplicacion;
	}

	/**
	 * @return the plazo
	 */
	public Integer getPlazo() {
		return plazo;
	}

	/**
	 * @param plazo
	 *            the plazo to set
	 */
	public void setPlazo(Integer plazo) {
		this.plazo = plazo;
	}

	/**
	 * @return the precio
	 */
	public BigDecimal getPrecio() {
		return precio;
	}

	/**
	 * @param precio
	 *            the precio to set
	 */
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	/**
	 * @return the premio
	 */
	public BigDecimal getPremio() {
		return premio;
	}

	/**
	 * @param premio
	 *            the premio to set
	 */
	public void setPremio(BigDecimal premio) {
		this.premio = premio;
	}

	/**
	 * @return the tasa
	 */
	public BigDecimal getTasa() {
		return tasa;
	}

	/**
	 * @param tasa
	 *            the tasa to set
	 */
	public void setTasa(BigDecimal tasa) {
		this.tasa = tasa;
	}

	/**
	 * @return the tasaReferencial
	 */
	public BigDecimal getTasaReferencial() {
		return tasaReferencial;
	}

	/**
	 * @param tasaReferencial
	 *            the tasaReferencial to set
	 */
	public void setTasaReferencial(BigDecimal tasaReferencial) {
		this.tasaReferencial = tasaReferencial;
	}

	/**
	 * @return the fechaHora
	 */
	public Date getFechaHora() {
		return fechaHora;
	}

	/**
	 * @param fechaHora
	 *            the fechaHora to set
	 */
	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	/**
	 * @return the fechaReporto
	 */
	public Date getFechaReporto() {
		return fechaReporto;
	}

	/**
	 * @param fechaReporto
	 *            the fechaReporto to set
	 */
	public void setFechaReporto(Date fechaReporto) {
		this.fechaReporto = fechaReporto;
	}

	/**
	 * @return the folioTransmision
	 */
	public BigInteger getFolioTransmision() {
		return folioTransmision;
	}

	/**
	 * @param folioTransmision
	 *            the folioTransmision to set
	 */
	public void setFolioTransmision(BigInteger folioTransmision) {
		this.folioTransmision = folioTransmision;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
	}

}
