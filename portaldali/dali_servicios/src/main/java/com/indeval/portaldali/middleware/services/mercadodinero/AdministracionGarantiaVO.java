/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class AdministracionGarantiaVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private BigInteger folioPrestamo;

	private EmisionVO emision;

	private String idPrestario;

	private String folioPrestario;

	private String idPrestamista;

	private String folioPrestamista;

	private BigInteger cantidadTitulos;

	private Date concertacion;

	private Date vencimiento;

	private Integer plazo;

	private BigDecimal premio;

	private BigDecimal tasaP;

	private BigDecimal tasaT;

	private BigDecimal sobreTasa;

	private GarantiaVigenteVO[] garantiaVigentes;

	private BigDecimal aforo;

	private BigDecimal aforado;

	private BigDecimal excedenteFaltante;

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getAforado() {
		return aforado;
	}

	/**
	 * @param aforado
	 */
	public void setAforado(BigDecimal aforado) {
		this.aforado = aforado;
	}

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
	public BigInteger getCantidadTitulos() {
		return cantidadTitulos;
	}

	/**
	 * @param cantidadTitulos
	 */
	public void setCantidadTitulos(BigInteger cantidadTitulos) {
		this.cantidadTitulos = cantidadTitulos;
	}

	/**
	 * @return Date
	 */
	public Date getConcertacion() {
		return concertacion;
	}

	/**
	 * @param concertacion
	 */
	public void setConcertacion(Date concertacion) {
		this.concertacion = clona(concertacion);
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
	 * @return BigDecimal
	 */
	public BigDecimal getExcedenteFaltante() {
		return excedenteFaltante;
	}

	/**
	 * @param excedenteFaltante
	 */
	public void setExcedenteFaltante(BigDecimal excedenteFaltante) {
		this.excedenteFaltante = excedenteFaltante;
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
	public String getFolioPrestario() {
		return folioPrestario;
	}

	/**
	 * @param folioPrestario
	 */
	public void setFolioPrestario(String folioPrestario) {
		this.folioPrestario = folioPrestario;
	}

	/**
	 * @return GarantiaVigenteVO[]
	 */
	public GarantiaVigenteVO[] getGarantiaVigentes() {
		return garantiaVigentes;
	}

	/**
	 * @param garantiaVigentes
	 */
	public void setGarantiaVigentes(GarantiaVigenteVO[] garantiaVigentes) {
		this.garantiaVigentes = garantiaVigentes;
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
	 * @return String
	 */
	public String getIdPrestario() {
		return idPrestario;
	}

	/**
	 * @param idPrestario
	 */
	public void setIdPrestario(String idPrestario) {
		this.idPrestario = idPrestario;
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
	 * @return BigDecimal
	 */
	public BigDecimal getPremio() {
		return premio;
	}

	/**
	 * @param premio
	 */
	public void setPremio(BigDecimal premio) {
		this.premio = premio;
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
	public BigDecimal getTasaP() {
		return tasaP;
	}

	/**
	 * @param tasaP
	 */
	public void setTasaP(BigDecimal tasaP) {
		this.tasaP = tasaP;
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
	 * @return Date
	 */
	public Date getVencimiento() {
		return vencimiento;
	}

	/**
	 * @param vencimiento
	 */
	public void setVencimiento(Date vencimiento) {
		this.vencimiento = clona(vencimiento);
	}

	/**
	 * @return Returns the folioPrestamo.
	 */
	public BigInteger getFolioPrestamo() {
		return folioPrestamo;
	}

	/**
	 * @param folioPrestamo
	 *            The folioPrestamo to set.
	 */
	public void setFolioPrestamo(BigInteger folioPrestamo) {
		this.folioPrestamo = folioPrestamo;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

}
