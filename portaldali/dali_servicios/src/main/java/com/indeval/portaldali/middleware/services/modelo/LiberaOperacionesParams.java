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
public class LiberaOperacionesParams extends AbstractBaseDTO {

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

	private BigInteger folTransm;

	private String mDine;

	private String usuario;

	private Date fechaLiq;

	private String mercado;

	private String origen;

	private String origenAplicac;

	private String nomUs;

	private String llave_folio;

	private String nomArea;

	private BigInteger folCont;

	private BigInteger entrega;

	private String nombreCuenta;

	private String noCuenta;

	private String mt;

	private String descContrapar;

	private String cuentaContrap;

	private Date fechaEjec;

	private Date fechaOper;

	private String depLiq;

	private String custodio;

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
	 * @return the cuentaContrap
	 */
	public String getCuentaContrap() {
		return cuentaContrap;
	}

	/**
	 * @param cuentaContrap
	 *            the cuentaContrap to set
	 */
	public void setCuentaContrap(String cuentaContrap) {
		this.cuentaContrap = cuentaContrap;
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
	 * @return the custodio
	 */
	public String getCustodio() {
		return custodio;
	}

	/**
	 * @param custodio
	 *            the custodio to set
	 */
	public void setCustodio(String custodio) {
		this.custodio = custodio;
	}

	/**
	 * @return the depLiq
	 */
	public String getDepLiq() {
		return depLiq;
	}

	/**
	 * @param depLiq
	 *            the depLiq to set
	 */
	public void setDepLiq(String depLiq) {
		this.depLiq = depLiq;
	}

	/**
	 * @return the descContrapar
	 */
	public String getDescContrapar() {
		return descContrapar;
	}

	/**
	 * @param descContrapar
	 *            the descContrapar to set
	 */
	public void setDescContrapar(String descContrapar) {
		this.descContrapar = descContrapar;
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
	 * @return the entrega
	 */
	public BigInteger getEntrega() {
		return entrega;
	}

	/**
	 * @param entrega
	 *            the entrega to set
	 */
	public void setEntrega(BigInteger entrega) {
		this.entrega = entrega;
	}

	/**
	 * @return the fechaEjec
	 */
	public Date getFechaEjec() {
		return fechaEjec;
	}

	/**
	 * @param fechaEjec
	 *            the fechaEjec to set
	 */
	public void setFechaEjec(Date fechaEjec) {
		this.fechaEjec = fechaEjec;
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
	 * @return the fechaOper
	 */
	public Date getFechaOper() {
		return fechaOper;
	}

	/**
	 * @param fechaOper
	 *            the fechaOper to set
	 */
	public void setFechaOper(Date fechaOper) {
		this.fechaOper = fechaOper;
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
	 * @return the llave_folio
	 */
	public String getLlave_folio() {
		return llave_folio;
	}

	/**
	 * @param llave_folio
	 *            the llave_folio to set
	 */
	public void setLlave_folio(String llave_folio) {
		this.llave_folio = llave_folio;
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
	 * @return the mt
	 */
	public String getMt() {
		return mt;
	}

	/**
	 * @param mt
	 *            the mt to set
	 */
	public void setMt(String mt) {
		this.mt = mt;
	}

	/**
	 * @return the noCuenta
	 */
	public String getNoCuenta() {
		return noCuenta;
	}

	/**
	 * @param noCuenta
	 *            the noCuenta to set
	 */
	public void setNoCuenta(String noCuenta) {
		this.noCuenta = noCuenta;
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
	 * @return the nombreCuenta
	 */
	public String getNombreCuenta() {
		return nombreCuenta;
	}

	/**
	 * @param nombreCuenta
	 *            the nombreCuenta to set
	 */
	public void setNombreCuenta(String nombreCuenta) {
		this.nombreCuenta = nombreCuenta;
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
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
	}

}
