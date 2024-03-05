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
public class TraspasosDineroCompradorParams extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private Date fecha;

	private String id1;

	private String fol1;

	private String cta1;

	private String tCta1;

	private String id2;

	private String fol2;

	private String cta2;

	private String tcta2;

	private String tv;

	private String emis;

	private String serie;

	private String cupon;

	private String baja;

	private BigDecimal cant;

	private String cveRep;

	private BigInteger diasPlazo;

	private Date fechaReporto;

	private BigInteger folTransm;

	private String mDine;

	private BigDecimal precioTitulo;

	private BigDecimal tasaPremio;

	private String usuario;

	private String usuarioReal;

	private Date fechaLiq;

	private BigInteger liq;

	private String mercado;

	private String origen;

	private String origenAplicac;

	private String tipoLib;

	private String llaveFolMd;

	private String nomUs;

	private String nomArea;

	private BigInteger folCont;

	private String folDesc;

	private String divisa;

	/**
	 * Valida que cada uno de los atributos obligatorios, no sean nulos.
	 * 
	 * @return boolean
	 */
	public boolean validaParams() {
		return this.id1 == null || this.fol1 == null || this.cta1 == null || this.tCta1 == null || this.id2 == null || this.fol2 == null || this.cta2 == null
				|| this.tcta2 == null || this.tv == null || this.emis == null || this.serie == null || this.cupon == null || this.cant == null ? false : true;

	}

	/**
	 * @return the baja
	 */
	public String getBaja() {
		return baja;
	}

	/**
	 * @param baja
	 *            the baja to set
	 */
	public void setBaja(String baja) {
		this.baja = baja;
	}

	/**
	 * @return the cant
	 */
	public BigDecimal getCant() {
		return cant;
	}

	/**
	 * @param cant
	 *            the cant to set
	 */
	public void setCant(BigDecimal cant) {
		this.cant = cant;
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
	 * @return the cveRep
	 */
	public String getCveRep() {
		return cveRep;
	}

	/**
	 * @param cveRep
	 *            the cveRep to set
	 */
	public void setCveRep(String cveRep) {
		this.cveRep = cveRep;
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
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha
	 *            the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
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
	 * @return the folCont
	 */
	public BigInteger getFolCont() {
		return folCont;
	}

	/**
	 * @param folCont
	 *            the folCont to set
	 */
	public void setFolCont(BigInteger folCont) {
		this.folCont = folCont;
	}

	/**
	 * @return the folDesc
	 */
	public String getFolDesc() {
		return folDesc;
	}

	/**
	 * @param folDesc
	 *            the folDesc to set
	 */
	public void setFolDesc(String folDesc) {
		this.folDesc = folDesc;
	}

	/**
	 * @return the folTransm
	 */
	public BigInteger getFolTransm() {
		return folTransm;
	}

	/**
	 * @param folTransm
	 *            the folTransm to set
	 */
	public void setFolTransm(BigInteger folTransm) {
		this.folTransm = folTransm;
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
	 * @return the llaveFolMd
	 */
	public String getLlaveFolMd() {
		return llaveFolMd;
	}

	/**
	 * @param llaveFolMd
	 *            the llaveFolMd to set
	 */
	public void setLlaveFolMd(String llaveFolMd) {
		this.llaveFolMd = llaveFolMd;
	}

	/**
	 * @return the mDine
	 */
	public String getMDine() {
		return mDine;
	}

	/**
	 * @param dine
	 *            the mDine to set
	 */
	public void setMDine(String dine) {
		mDine = dine;
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
	 * @return the tcta2
	 */
	public String getTcta2() {
		return tcta2;
	}

	/**
	 * @param tcta2
	 *            the tcta2 to set
	 */
	public void setTcta2(String tcta2) {
		this.tcta2 = tcta2;
	}

	/**
	 * @return the tipoLib
	 */
	public String getTipoLib() {
		return tipoLib;
	}

	/**
	 * @param tipoLib
	 *            the tipoLib to set
	 */
	public void setTipoLib(String tipoLib) {
		this.tipoLib = tipoLib;
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
	 * @return the usuarioReal
	 */
	public String getUsuarioReal() {
		return usuarioReal;
	}

	/**
	 * @param usuarioReal
	 *            the usuarioReal to set
	 */
	public void setUsuarioReal(String usuarioReal) {
		this.usuarioReal = usuarioReal;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
	}

}
