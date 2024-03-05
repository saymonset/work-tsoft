/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.modelo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 * 
 */
public class AltaMdintranParams extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	/** Log de clase. */
	private static final Logger logger = LoggerFactory.getLogger(AltaMdintranParams.class);

	private String id1;

	private String fol1;

	private String cta1;

	private String llave;

	private String id2;

	private String fol2;

	private String cta2;

	private String tv;

	private String emis;

	private String error;

	private String serie;

	private String cupon;

	private BigDecimal cantOp;

	private String claveReporto;

	private BigInteger diasPlazo;

	private Date fechaReporto;

	private BigDecimal precioTitulo;

	private String sociedad;

	private BigDecimal tasaPremio;

	private String bajaLogica;

	private Date fechaLiq;

	private BigInteger liq;

	private String mercado;

	private String origenAplicac;

	private String origen;

	private String usuario;

	private String areaTrabajo;

	private String folioDescripc;

	private String divisa;

	private BigInteger folioc;

	private Date fechaConcer;

	private BigInteger idTasaRef;

	private String aplicacion;

	/**
	 * @return the aplicacion
	 */
	public String getAplicacion() {
		return aplicacion;
	}

	/**
	 * @param aplicacion
	 *            the aplicacion to set
	 */
	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	/**
	 * @return the areaTrabajo
	 */
	public String getAreaTrabajo() {
		return areaTrabajo;
	}

	/**
	 * @param areaTrabajo
	 *            the areaTrabajo to set
	 */
	public void setAreaTrabajo(String areaTrabajo) {
		this.areaTrabajo = areaTrabajo;
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
	 * @return the cantOp
	 */
	public BigDecimal getCantOp() {
		return cantOp;
	}

	/**
	 * @param cantOp
	 *            the cantOp to set
	 */
	public void setCantOp(BigDecimal cantOp) {
		this.cantOp = cantOp;
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
	 * @return the cta1
	 */
	public String getCta1() {
		return cta1;
	}

	/**
	 * @param cta1
	 *            the cta1 to set
	 */
	public void setCta1(String cta1) {
		this.cta1 = cta1;
	}

	/**
	 * @return the cta2
	 */
	public String getCta2() {
		return cta2;
	}

	/**
	 * @param cta2
	 *            the cta2 to set
	 */
	public void setCta2(String cta2) {
		this.cta2 = cta2;
	}

	/**
	 * @return the cupon
	 */
	public String getCupon() {
		return cupon;
	}

	/**
	 * @param cupon
	 *            the cupon to set
	 */
	public void setCupon(String cupon) {
		this.cupon = cupon;
	}

	/**
	 * @return the diasPlazo
	 */
	public BigInteger getDiasPlazo() {
		return diasPlazo;
	}

	/**
	 * @param diasPlazo
	 *            the diasPlazo to set
	 */
	public void setDiasPlazo(BigInteger diasPlazo) {
		this.diasPlazo = diasPlazo;
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
	 * @return the emis
	 */
	public String getEmis() {
		return emis;
	}

	/**
	 * @param emis
	 *            the emis to set
	 */
	public void setEmis(String emis) {
		this.emis = emis;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error
	 *            the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @return the fechaConcer
	 */
	public Date getFechaConcer() {
		return fechaConcer;
	}

	/**
	 * @param fechaConcer
	 *            the fechaConcer to set
	 */
	public void setFechaConcer(Date fechaConcer) {
		this.fechaConcer = fechaConcer;
	}

	/**
	 * @return the fechaLiq
	 */
	public Date getFechaLiq() {
		return fechaLiq;
	}

	/**
	 * @param fechaLiq
	 *            the fechaLiq to set
	 */
	public void setFechaLiq(Date fechaLiq) {
		this.fechaLiq = fechaLiq;
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
	 * @return the fol1
	 */
	public String getFol1() {
		return fol1;
	}

	/**
	 * @param fol1
	 *            the fol1 to set
	 */
	public void setFol1(String fol1) {
		this.fol1 = fol1;
	}

	/**
	 * @return the fol2
	 */
	public String getFol2() {
		return fol2;
	}

	/**
	 * @param fol2
	 *            the fol2 to set
	 */
	public void setFol2(String fol2) {
		this.fol2 = fol2;
	}

	/**
	 * @return the folioc
	 */
	public BigInteger getFolioc() {
		return folioc;
	}

	/**
	 * @param folioc
	 *            the folioc to set
	 */
	public void setFolioc(BigInteger folioc) {
		this.folioc = folioc;
	}

	/**
	 * @return the folioDescripc
	 */
	public String getFolioDescripc() {
		return folioDescripc;
	}

	/**
	 * @param folioDescripc
	 *            the folioDescripc to set
	 */
	public void setFolioDescripc(String folioDescripc) {
		this.folioDescripc = folioDescripc;
	}

	/**
	 * @return the id1
	 */
	public String getId1() {
		return id1;
	}

	/**
	 * @param id1
	 *            the id1 to set
	 */
	public void setId1(String id1) {
		this.id1 = id1;
	}

	/**
	 * @return the id2
	 */
	public String getId2() {
		return id2;
	}

	/**
	 * @param id2
	 *            the id2 to set
	 */
	public void setId2(String id2) {
		this.id2 = id2;
	}

	/**
	 * @return the idTasaRef
	 */
	public BigInteger getIdTasaRef() {
		return idTasaRef;
	}

	/**
	 * @param idTasaRef
	 *            the idTasaRef to set
	 */
	public void setIdTasaRef(BigInteger idTasaRef) {
		this.idTasaRef = idTasaRef;
	}

	/**
	 * @return the liq
	 */
	public BigInteger getLiq() {
		return liq;
	}

	/**
	 * @param liq
	 *            the liq to set
	 */
	public void setLiq(BigInteger liq) {
		this.liq = liq;
	}

	/**
	 * @return the llave
	 */
	public String getLlave() {
		return llave;
	}

	/**
	 * @param llave
	 *            the llave to set
	 */
	public void setLlave(String llave) {
		this.llave = llave;
	}

	/**
	 * @return the mercado
	 */
	public String getMercado() {
		return mercado;
	}

	/**
	 * @param mercado
	 *            the mercado to set
	 */
	public void setMercado(String mercado) {
		this.mercado = mercado;
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
	 * @return the origenAplicac
	 */
	public String getOrigenAplicac() {
		return origenAplicac;
	}

	/**
	 * @param origenAplicac
	 *            the origenAplicac to set
	 */
	public void setOrigenAplicac(String origenAplicac) {
		this.origenAplicac = origenAplicac;
	}

	/**
	 * @return the precioTitulo
	 */
	public BigDecimal getPrecioTitulo() {
		return precioTitulo;
	}

	/**
	 * @param precioTitulo
	 *            the precioTitulo to set
	 */
	public void setPrecioTitulo(BigDecimal precioTitulo) {
		this.precioTitulo = precioTitulo;
	}

	/**
	 * @return the serie
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * @param serie
	 *            the serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	/**
	 * @return the sociedad
	 */
	public String getSociedad() {
		return sociedad;
	}

	/**
	 * @param sociedad
	 *            the sociedad to set
	 */
	public void setSociedad(String sociedad) {
		this.sociedad = sociedad;
	}

	/**
	 * @return the tasaPremio
	 */
	public BigDecimal getTasaPremio() {
		return tasaPremio;
	}

	/**
	 * @param tasaPremio
	 *            the tasaPremio to set
	 */
	public void setTasaPremio(BigDecimal tasaPremio) {
		this.tasaPremio = tasaPremio;
	}

	/**
	 * @return the tv
	 */
	public String getTv() {
		return tv;
	}

	/**
	 * @param tv
	 *            the tv to set
	 */
	public void setTv(String tv) {
		this.tv = tv;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @throws BusinessException
	 */
	public void validaParams() throws BusinessException {

		logger.info("Entrando a AltaMdintranParams.validaParams");

		if (StringUtils.isBlank(this.getId1()) || StringUtils.isBlank(this.getFol1()) || StringUtils.isBlank(this.getCta1())
				|| StringUtils.isBlank(this.getLlave())) {
			throw new BusinessException(ERROR_DE_PARAMETROS);
		}
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
	}

}
