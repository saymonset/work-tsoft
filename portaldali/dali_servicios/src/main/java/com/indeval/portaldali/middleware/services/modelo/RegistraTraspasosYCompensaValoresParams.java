/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.modelo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 * 
 */
public class RegistraTraspasosYCompensaValoresParams extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private String id1;

	private String fol1;

	private String cta1;

	private String tCta1;

	private String llave;

	private String id2;

	private String fol2;

	private String cta2;

	private String tCta2;

	private String tv;

	private String emis;

	private String serie;

	private String cupon;

	private BigDecimal cantOp;

	private String claveReporto;

	private BigInteger precioTitulo;

	private Date fechaLiq;

	private BigInteger liq;

	private String mercado;

	private String origen;

	private String origenApl;

	private String folioDesc;

	private String nomArea;

	private String nomUs;

	private String divisa;

	private String usuReal;

	private Date fechaAdquisic;

	private BigDecimal precioAdquisi;

	private String cliente;

	private String curpRfc;

	private String sociedadSerie;

	private String nombreUsuario;

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
	 * @return the cliente
	 */
	public String getCliente() {
		return cliente;
	}

	/**
	 * @param cliente
	 *            the cliente to set
	 */
	public void setCliente(String cliente) {
		this.cliente = cliente;
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
	 * @return the curpRfc
	 */
	public String getCurpRfc() {
		return curpRfc;
	}

	/**
	 * @param curpRfc
	 *            the curpRfc to set
	 */
	public void setCurpRfc(String curpRfc) {
		this.curpRfc = curpRfc;
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
	 * @return the fechaAdquisic
	 */
	public Date getFechaAdquisic() {
		return fechaAdquisic;
	}

	/**
	 * @param fechaAdquisic
	 *            the fechaAdquisic to set
	 */
	public void setFechaAdquisic(Date fechaAdquisic) {
		this.fechaAdquisic = fechaAdquisic;
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
	 * @return the folioDesc
	 */
	public String getFolioDesc() {
		return folioDesc;
	}

	/**
	 * @param folioDesc
	 *            the folioDesc to set
	 */
	public void setFolioDesc(String folioDesc) {
		this.folioDesc = folioDesc;
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
	 * @return the nomArea
	 */
	public String getNomArea() {
		return nomArea;
	}

	/**
	 * @param nomArea
	 *            the nomArea to set
	 */
	public void setNomArea(String nomArea) {
		this.nomArea = nomArea;
	}

	/**
	 * @return the nombreUsuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * @param nombreUsuario
	 *            the nombreUsuario to set
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * @return the nomUs
	 */
	public String getNomUs() {
		return nomUs;
	}

	/**
	 * @param nomUs
	 *            the nomUs to set
	 */
	public void setNomUs(String nomUs) {
		this.nomUs = nomUs;
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
	 * @return the origenApl
	 */
	public String getOrigenApl() {
		return origenApl;
	}

	/**
	 * @param origenApl
	 *            the origenApl to set
	 */
	public void setOrigenApl(String origenApl) {
		this.origenApl = origenApl;
	}

	/**
	 * @return the precioAdquisi
	 */
	public BigDecimal getPrecioAdquisi() {
		return precioAdquisi;
	}

	/**
	 * @param precioAdquisi
	 *            the precioAdquisi to set
	 */
	public void setPrecioAdquisi(BigDecimal precioAdquisi) {
		this.precioAdquisi = precioAdquisi;
	}

	/**
	 * @return the precioTitulo
	 */
	public BigInteger getPrecioTitulo() {
		return precioTitulo;
	}

	/**
	 * @param precioTitulo
	 *            the precioTitulo to set
	 */
	public void setPrecioTitulo(BigInteger precioTitulo) {
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
	 * @return the sociedadSerie
	 */
	public String getSociedadSerie() {
		return sociedadSerie;
	}

	/**
	 * @param sociedadSerie
	 *            the sociedadSerie to set
	 */
	public void setSociedadSerie(String sociedadSerie) {
		this.sociedadSerie = sociedadSerie;
	}

	/**
	 * @return the tCta1
	 */
	public String getTCta1() {
		return tCta1;
	}

	/**
	 * @param cta1
	 *            the tCta1 to set
	 */
	public void setTCta1(String cta1) {
		tCta1 = cta1;
	}

	/**
	 * @return the tCta2
	 */
	public String getTCta2() {
		return tCta2;
	}

	/**
	 * @param cta2
	 *            the tCta2 to set
	 */
	public void setTCta2(String cta2) {
		tCta2 = cta2;
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
	 * @return the usuReal
	 */
	public String getUsuReal() {
		return usuReal;
	}

	/**
	 * @param usuReal
	 *            the usuReal to set
	 */
	public void setUsuReal(String usuReal) {
		this.usuReal = usuReal;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
	}

}
