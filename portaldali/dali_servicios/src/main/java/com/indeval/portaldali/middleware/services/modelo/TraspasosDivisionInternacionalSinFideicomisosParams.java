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
public class TraspasosDivisionInternacionalSinFideicomisosParams extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private String id1;

	private String fol1;

	private String cta1;

	private String id2;

	private String fol2;

	private String cta2;

	private String tv;

	private String emis;

	private String serie;

	private String cupon;

	private BigDecimal cant;

	private String cveRep;

	private BigInteger folTransm;

	private String mDine;

	private String usuario;

	private String usuarioVal1;

	private Date fechaLiq;

	private String mercado;

	private String origen;

	private String origenAplicac;

	private String nomUs;

	private String llaveFolio;

	private String nomArea;

	private BigInteger folCont;

	/**
	 * Metodo que se encarga de validar cada parametro
	 * 
	 * @return
	 */
	public boolean validaParams() {

		if (this.getId1() == null || this.getFol1() == null || this.getCta1() == null || this.getId2() == null || this.getFol2() == null
				|| this.getCta2() == null || this.getTv() == null || this.getEmis() == null || this.getSerie() == null || this.getCupon() == null
				|| this.getCant() == null || this.getCveRep() == null || this.getFolTransm() == null || this.getMDine() == null || this.getUsuario() == null
				|| this.getUsuarioVal1() == null || this.getFechaLiq() == null || this.getMercado() == null || this.getOrigen() == null
				|| this.getOrigenAplicac() == null || this.getLlaveFolio() == null || this.getFolCont() == null) {
			return false;
		}
		return true;

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
	 * @return the usuarioVal1
	 */
	public String getUsuarioVal1() {
		return usuarioVal1;
	}

	/**
	 * @param usuarioVal1
	 *            the usuarioVal1 to set
	 */
	public void setUsuarioVal1(String usuarioVal1) {
		this.usuarioVal1 = usuarioVal1;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
	}

}
