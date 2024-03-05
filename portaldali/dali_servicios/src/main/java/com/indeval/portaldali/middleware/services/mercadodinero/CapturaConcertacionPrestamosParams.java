/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class CapturaConcertacionPrestamosParams extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private String id;

	private String folio;

	private String cuenta;

	private String idPrestamista;

	private String folioPrestamista;

	private EmisionVO emision;

	private BigInteger saldoBanxico;

	private BigInteger totalDisponible;

	private BigInteger saldoEnPrestamo;

	private BigInteger cantidadSolicitada;

	private Integer plazo;

	private Date fecha;

	private BigDecimal Vector;

	private BigDecimal tasaPremio;

	private BigDecimal tasaT;

	private BigDecimal sobreTasa;

	private BigDecimal valuacionPrestamo;

	private BigDecimal colateral;

	private BigDecimal aforo;

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getAforo() {
		return aforo;
	}

	/**
	 * @param aforo
	 */
	public void setAforo(BigDecimal aforo) {
		this.aforo = aforo;
	}

	/**
	 * @return BigInteger
	 */
	public BigInteger getCantidadSolicitada() {
		return cantidadSolicitada;
	}

	/**
	 * @param cantidadSolicitada
	 */
	public void setCantidadSolicitada(BigInteger cantidadSolicitada) {
		this.cantidadSolicitada = cantidadSolicitada;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getColateral() {
		return colateral;
	}

	/**
	 * @param colateral
	 */
	public void setColateral(BigDecimal colateral) {
		this.colateral = colateral;
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
	 * @return String
	 */
	public String getFolioPrestamista() {
		return folioPrestamista;
	}

	/**
	 * @param folioPrestamista
	 */
	public void setFolioPrestamista(String folioPrestamista) {
		this.folioPrestamista = folioPrestamista;
	}

	/**
	 * @return String
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return String
	 */
	public String getIdPrestamista() {
		return idPrestamista;
	}

	/**
	 * @param idPrestamista
	 */
	public void setIdPrestamista(String idPrestamista) {
		this.idPrestamista = idPrestamista;
	}

	/**
	 * @return Integer
	 */
	public Integer getPlazo() {
		return plazo;
	}

	/**
	 * @param plazo
	 */
	public void setPlazo(Integer plazo) {
		this.plazo = plazo;
	}

	/**
	 * @return BigInteger
	 */
	public BigInteger getSaldoBanxico() {
		return saldoBanxico;
	}

	/**
	 * @param saldoBanxico
	 */
	public void setSaldoBanxico(BigInteger saldoBanxico) {
		this.saldoBanxico = saldoBanxico;
	}

	/**
	 * @return BigInteger
	 */
	public BigInteger getSaldoEnPrestamo() {
		return saldoEnPrestamo;
	}

	/**
	 * @param saldoEnPrestamo
	 */
	public void setSaldoEnPrestamo(BigInteger saldoEnPrestamo) {
		this.saldoEnPrestamo = saldoEnPrestamo;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getSobreTasa() {
		return sobreTasa;
	}

	/**
	 * @param sobreTasa
	 */
	public void setSobreTasa(BigDecimal sobreTasa) {
		this.sobreTasa = sobreTasa;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getTasaPremio() {
		return tasaPremio;
	}

	/**
	 * @param tasaPremio
	 */
	public void setTasaPremio(BigDecimal tasaPremio) {
		this.tasaPremio = tasaPremio;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getTasaT() {
		return tasaT;
	}

	/**
	 * @param tasaT
	 */
	public void setTasaT(BigDecimal tasaT) {
		this.tasaT = tasaT;
	}

	/**
	 * @return BigInteger
	 */
	public BigInteger getTotalDisponible() {
		return totalDisponible;
	}

	/**
	 * @param totalDisponible
	 */
	public void setTotalDisponible(BigInteger totalDisponible) {
		this.totalDisponible = totalDisponible;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getValuacionPrestamo() {
		return valuacionPrestamo;
	}

	/**
	 * @param valuacionPrestamo
	 */
	public void setValuacionPrestamo(BigDecimal valuacionPrestamo) {
		this.valuacionPrestamo = valuacionPrestamo;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getVector() {
		return Vector;
	}

	/**
	 * @param vector
	 */
	public void setVector(BigDecimal vector) {
		Vector = vector;
	}

	/**
	 * Valida que el objeto tenga los atributos requeridos
	 * 
	 * @throws BusinessException
	 */
	public void esValido() throws BusinessException {
		if (this.getEmision() == null) {
			throw new BusinessException("Error: El parametro de entrada no trae " + "el objeto Emision requerido");
		}
		if (StringUtils.isBlank(this.getEmision().getIdTipoValor())) {
			throw new BusinessException("Error: La emision recibida tiene el atributo tipoValor NULL");
		}
		if (StringUtils.isBlank(this.getId())) {
			throw new BusinessException("ERROR: El parametro de entrada tiene NULL o VACIO " + "el atributo ID requerido");
		}
		if (StringUtils.isBlank(this.getFolio())) {
			throw new BusinessException("ERROR: El parametro de entrada tiene NULL o VACIO " + "el atributo FOLIO requerido");
		}
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

}
