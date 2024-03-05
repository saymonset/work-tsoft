/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.util.vo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import org.springframework.validation.Errors;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class BitacoraVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	/** */
	private BigDecimal idBitacoraOperaciones;
	/** */
	private String idTrasp;

	/** */
	private String folioTrasp;

	/** */
	private String referenciaMensaje;

	/** */
	private String cuentaTrasp;

	/** */
	private String idRecep;

	/** */
	private String folioRecep;

	/** */
	private String cuentaRecep;

	/** */
	private String tv;

	/** */
	private String emisora;

	/** */
	private String serie;

	/** */
	private String cupon;

	/** */
	private Date fechaLiquidacion;

	/** */
	private BigInteger cantidadTitulos;

	/** */
	private String tipoInstruccion;

	/** */
	private Integer folioControl;

	/** */
	private BigDecimal monto;

	/** */
	private BigDecimal precio;

	/** */
	private String divisa;

	/** */
	private Date fechaVencimiento;

	/** */
	private Date fechaConcertacion;

	/** */
	private BigDecimal tasaNegociada;

	/** */
	private Boolean tasaFija;

	/** */
	private BigDecimal tasaReferencia;

	/** */
	private String idFolioCtaPuente;

	/** */
	private Date fechaRegistro;

	/** */
	private String usuario;

	/** */
	private String estatusRegistro;

	/** */
	private String origenRegistro;

	/** */
	private String referenciaOperacion;

	/** */
	private String codigoError;

	/** */
	private String mercado;

	/** */
	private String marcaCompra;

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
	 * @return the cantidadTitulos
	 */
	public BigInteger getCantidadTitulos() {
		return cantidadTitulos;
	}

	/**
	 * @param cantidadTitulos
	 *            the cantidadTitulos to set
	 */
	public void setCantidadTitulos(BigInteger cantidadTitulos) {
		this.cantidadTitulos = cantidadTitulos;
	}

	/**
	 * @return the cuentaRecep
	 */
	public String getCuentaRecep() {
		return cuentaRecep;
	}

	/**
	 * @param cuentaRecep
	 *            the cuentaRecep to set
	 */
	public void setCuentaRecep(String cuentaRecep) {
		this.cuentaRecep = cuentaRecep;
	}

	/**
	 * @return the cuentaTrasp
	 */
	public String getCuentaTrasp() {
		return cuentaTrasp;
	}

	/**
	 * @param cuentaTrasp
	 *            the cuentaTrasp to set
	 */
	public void setCuentaTrasp(String cuentaTrasp) {
		this.cuentaTrasp = cuentaTrasp;
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
	 * @return the emisora
	 */
	public String getEmisora() {
		return emisora;
	}

	/**
	 * @param emisora
	 *            the emisora to set
	 */
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}

	/**
	 * @return the estatusRegistro
	 */
	public String getEstatusRegistro() {
		return estatusRegistro;
	}

	/**
	 * @param estatusRegistro
	 *            the estatusRegistro to set
	 */
	public void setEstatusRegistro(String estatusRegistro) {
		this.estatusRegistro = estatusRegistro;
	}

	/**
	 * @return the fechaConcertacion
	 */
	public Date getFechaConcertacion() {
		return fechaConcertacion;
	}

	/**
	 * @param fechaConcertacion
	 *            the fechaConcertacion to set
	 */
	public void setFechaConcertacion(Date fechaConcertacion) {
		this.fechaConcertacion = fechaConcertacion;
	}

	/**
	 * @return the fechaLiquidacion
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	/**
	 * @param fechaLiquidacion
	 *            the fechaLiquidacion to set
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro
	 *            the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * @return the fechaVencimiento
	 */
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	/**
	 * @param fechaVencimiento
	 *            the fechaVencimiento to set
	 */
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	/**
	 * @return the folioControl
	 */
	public Integer getFolioControl() {
		return folioControl;
	}

	/**
	 * @param folioControl
	 *            the folioControl to set
	 */
	public void setFolioControl(Integer folioControl) {
		this.folioControl = folioControl;
	}

	/**
	 * @return the folioRecep
	 */
	public String getFolioRecep() {
		return folioRecep;
	}

	/**
	 * @param folioRecep
	 *            the folioRecep to set
	 */
	public void setFolioRecep(String folioRecep) {
		this.folioRecep = folioRecep;
	}

	/**
	 * @return the folioTrasp
	 */
	public String getFolioTrasp() {
		return folioTrasp;
	}

	/**
	 * @param folioTrasp
	 *            the folioTrasp to set
	 */
	public void setFolioTrasp(String folioTrasp) {
		this.folioTrasp = folioTrasp;
	}

	/**
	 * @return the idFolioCtaPuente
	 */
	public String getIdFolioCtaPuente() {
		return idFolioCtaPuente;
	}

	/**
	 * @param idFolioCtaPuente
	 *            the idFolioCtaPuente to set
	 */
	public void setIdFolioCtaPuente(String idFolioCtaPuente) {
		this.idFolioCtaPuente = idFolioCtaPuente;
	}

	/**
	 * @return the idRecep
	 */
	public String getIdRecep() {
		return idRecep;
	}

	/**
	 * @param idRecep
	 *            the idRecep to set
	 */
	public void setIdRecep(String idRecep) {
		this.idRecep = idRecep;
	}

	/**
	 * @return the idTrasp
	 */
	public String getIdTrasp() {
		return idTrasp;
	}

	/**
	 * @param idTrasp
	 *            the idTrasp to set
	 */
	public void setIdTrasp(String idTrasp) {
		this.idTrasp = idTrasp;
	}

	/**
	 * @return the monto
	 */
	public BigDecimal getMonto() {
		return monto;
	}

	/**
	 * @param monto
	 *            the monto to set
	 */
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	/**
	 * @return the origenRegistro
	 */
	public String getOrigenRegistro() {
		return origenRegistro;
	}

	/**
	 * @param origenRegistro
	 *            the origenRegistro to set
	 */
	public void setOrigenRegistro(String origenRegistro) {
		this.origenRegistro = origenRegistro;
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
	 * @return the referenciaMensaje
	 */
	public String getReferenciaMensaje() {
		return referenciaMensaje;
	}

	/**
	 * @param referenciaMensaje
	 *            the referenciaMensaje to set
	 */
	public void setReferenciaMensaje(String referenciaMensaje) {
		this.referenciaMensaje = referenciaMensaje;
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
	 * @return the tasaFija
	 */
	public Boolean getTasaFija() {
		return tasaFija;
	}

	/**
	 * @param tasaFija
	 *            the tasaFija to set
	 */
	public void setTasaFija(Boolean tasaFija) {
		this.tasaFija = tasaFija;
	}

	/**
	 * @return the tasaNegociada
	 */
	public BigDecimal getTasaNegociada() {
		return tasaNegociada;
	}

	/**
	 * @param tasaNegociada
	 *            the tasaNegociada to set
	 */
	public void setTasaNegociada(BigDecimal tasaNegociada) {
		this.tasaNegociada = tasaNegociada;
	}

	/**
	 * @return the tasaReferencia
	 */
	public BigDecimal getTasaReferencia() {
		return tasaReferencia;
	}

	/**
	 * @param tasaReferencia
	 *            the tasaReferencia to set
	 */
	public void setTasaReferencia(BigDecimal tasaReferencia) {
		this.tasaReferencia = tasaReferencia;
	}

	/**
	 * @return the tipoInstruccion
	 */
	public String getTipoInstruccion() {
		return tipoInstruccion;
	}

	/**
	 * @param tipoInstruccion
	 *            the tipoInstruccion to set
	 */
	public void setTipoInstruccion(String tipoInstruccion) {
		this.tipoInstruccion = tipoInstruccion;
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

	/**
	 * @return the referenciaOperacion
	 */
	public String getReferenciaOperacion() {
		return referenciaOperacion;
	}

	/**
	 * @param referenciaOperacion
	 *            the referenciaOperacion to set
	 */
	public void setReferenciaOperacion(String referenciaOperacion) {
		this.referenciaOperacion = referenciaOperacion;
	}

	/**
	 * @return the codigoError
	 */
	public String getCodigoError() {
		return codigoError;
	}

	/**
	 * @param codigoError
	 *            the codigoError to set
	 */
	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}

	/**
	 * @return the marcaCompra
	 */
	public String getMarcaCompra() {
		return marcaCompra;
	}

	/**
	 * @param marcaCompra
	 *            the marcaCompra to set
	 */
	public void setMarcaCompra(String marcaCompra) {
		this.marcaCompra = marcaCompra;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getIdBitacoraOperaciones() {
		return idBitacoraOperaciones;
	}

	/**
	 * @param idBitacoraOperaciones
	 */
	public void setIdBitacoraOperaciones(BigDecimal idBitacoraOperaciones) {
		this.idBitacoraOperaciones = idBitacoraOperaciones;
	}

}
