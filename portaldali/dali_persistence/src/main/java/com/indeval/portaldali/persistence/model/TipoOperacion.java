/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Este cat&aacute;logo contiene la caracterizaci&oacute;n de las operaciones segu&acute;n su tipo.
 *
 * C_TIPO_OPERACION
 *
 * @author rchavez
 * @version 1.0
 */
@Entity
@Table(name = "C_TIPO_OPERACION")
public class TipoOperacion implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador del tipo de operaci&oacute;n.
	 */
	@Id
	@Column(name = "ID_TIPO_OPERACION")
	private BigInteger idTipoOperacion;
	/**
	 * Clave del tipo de operaci&oacute;n.
	 */
	@Column(name = "CLAVE_TIPO_OPERACION")
	private String claveTipoOperacion;
	/**
	 * Descripci&oacute;n de la operaci&oacute;n.
	 */
	@Column(name = "DESCRIPCION")
	private String descripcion;
	/**
	 * Indica si se requiere el identificador del traspasante.
	 */
	@Column(name = "REQ_ID_TRASPASANTE")
	private boolean reqIdTraspasante;
	/**
	 * Indica si se requiere el folio del traspasante.
	 */
	@Column(name = "REQ_FOLIO_TRASPASANTE")
	private boolean reqFolioTraspasante;
	/**
	 * Indica si se requiere la cuenta nombrada del traspasante.
	 */
	@Column(name = "REQ_CN_TRASPASANTE")
	private boolean reqCnTraspasante;
	/**
	 * Indica si se requiere la b&oacute;veda del traspasante.
	 */
	@Column(name = "REQ_BOVEDA_TRASPASANTE")
	private boolean reqBovedaTraspasante;
	/**
	 * Indica si se requiere el identificador del receptor.
	 */
	@Column(name = "REQ_ID_RECEPTOR")
	private boolean reqIdReceptor;
	/**
	 * Indica si se requiere el folio del receptor.
	 */
	@Column(name = "REQ_FOLIO_RECEPTOR")
	private boolean reqFolioReceptor;
	/**
	 * Indica si se requiere la cuenta nombrada del receptor.
	 */
	@Column(name = "REQ_CN_RECEPTOR")
	private boolean reqCnReceptor;
	/**
	 * Indica si se requiere la b&oacute;veda del receptor.
	 */
	@Column(name = "REQ_BOVEDA_RECEPTOR")
	private boolean reqBovedaReceptor;
	/**
	 * Indica si se requiere el tipo valor.
	 */
	@Column(name = "REQ_TIPO_VALOR")
	private boolean reqTipoValor;
	/**
	 * Indica si se requiere la emisi&oacute;n.
	 */
	@Column(name = "REQ_EMISION")
	private boolean reqEmision;
	/**
	 * Indica si se requiere la serie.
	 */
	@Column(name = "REQ_SERIE")
	private boolean reqSerie;
	/**
	 * Indica si se requiere el cup&oacute;n.
	 */
	@Column(name = "REQ_CUPON")
	private boolean reqCupon;
	/**
	 * Indica si se requiere el isin.
	 */
	@Column(name = "REQ_ISIN")
	private boolean reqIsin;
	/**
	 * Indica si se requiere el n&uacute;mero de titulos.
	 */
	@Column(name = "REQ_NUM_TITULOS")
	private boolean reqNumTitulos;
	/**
	 * Indica si se requiere el precio por titulo.
	 */
	@Column(name = "REQ_PRECIO")
	private boolean reqPrecio;
	/**
	 * Indica si se requiere la divisa.
	 */
	@Column(name = "REQ_DIV")
	private boolean reqDiv;
	/**
	 * Indica si se requiere el monto de efectivo.
	 */
	@Column(name = "REQ_MONTO")
	private boolean reqMonto;
	/**
	 * Indica si se requiere el origen de la operaci&oacute;n.
	 */
	@Column(name = "REQ_ORIGEN")
	private boolean reqOrigen;
	/**
	 * Indica si la operaci&oacute;n es compensable.
	 */
	@Column(name = "OPERACION_COMPENSABLE")
	private boolean operacionCompensable;
	/**
	 * Indica el tipo de custodia que se maneja en la operación
	 */
	@Column(name = "MANEJA")
	private String maneja;
	/**
	 * Identificador del tipo de operaci&oacute;n.
	 * @return long
	 */
	public BigInteger getIdTipoOperacion() {
		return idTipoOperacion;
	}

	/**
	 * Identificador del tipo de operaci&oacute;n.
	 * @param idTipoOperacion
	 */
	public void setIdTipoOperacion(BigInteger idTipoOperacion) {
		this.idTipoOperacion = idTipoOperacion;
	}

	/**
	 * Clave del tipo de operaci&oacute;n.
	 * @return String
	 */
	public String getClaveTipoOperacion() {
		return claveTipoOperacion;
	}

	/**
	 * Clave del tipo de operaci&oacute;n.
	 * @param claveTipoOperacion
	 */
	public void setClaveTipoOperacion(String claveTipoOperacion) {
		this.claveTipoOperacion = claveTipoOperacion;
	}

	/**
	 * Descripci&oacute;n de la operaci&oacute;n.
	 * @return String
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Descripci&oacute;n de la operaci&oacute;n.
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Requiere el identificador del traspasante.
	 * @return boolean
	 */
	public boolean isReqIdTraspasante() {
		return reqIdTraspasante;
	}

	/**
	 * Requiere el identificador del traspasante.
	 * @param reqIdTraspasante
	 */
	public void setReqIdTraspasante(boolean reqIdTraspasante) {
		this.reqIdTraspasante = reqIdTraspasante;
	}

	/**
	 * Requiere el folio del traspasante.
	 * @return boolean
	 */
	public boolean isReqFolioTraspasante() {
		return reqFolioTraspasante;
	}

	/**
	 * Requiere el folio del traspasante.
	 * @param reqFolioTraspasante
	 */
	public void setReqFolioTraspasante(boolean reqFolioTraspasante) {
		this.reqFolioTraspasante = reqFolioTraspasante;
	}

	/**
	 * Requiere la cuenta nombrada del traspasante.
	 * @return boolean
	 */
	public boolean isReqCnTraspasante() {
		return reqCnTraspasante;
	}

	/**
	 * Requiere la cuenta nombrada del traspasante.
	 * @param reqCnTraspasante
	 */
	public void setReqCnTraspasante(boolean reqCnTraspasante) {
		this.reqCnTraspasante = reqCnTraspasante;
	}

	/**
	 * Requiere la b&oacute;veda del traspasante.
	 * @return boolean
	 */
	public boolean isReqBovedaTraspasante() {
		return reqBovedaTraspasante;
	}

	/**
	 * Requiere la b&oacute;veda del traspasante.
	 * @param reqBovedaTraspasante
	 */
	public void setReqBovedaTraspasante(boolean reqBovedaTraspasante) {
		this.reqBovedaTraspasante = reqBovedaTraspasante;
	}

	/**
	 * Requiere el identificador del receptor.
	 * @return boolean
	 */
	public boolean isReqIdReceptor() {
		return reqIdReceptor;
	}

	/**
	 * Requiere el identificador del receptor.
	 * @param reqIdReceptor
	 */
	public void setReqIdReceptor(boolean reqIdReceptor) {
		this.reqIdReceptor = reqIdReceptor;
	}

	/**
	 * Requiere el folio del receptor.
	 * @return boolean
	 */
	public boolean isReqFolioReceptor() {
		return reqFolioReceptor;
	}

	/**
	 * Requiere el folio del receptor.
	 * @param reqFolioReceptor
	 */
	public void setReqFolioReceptor(boolean reqFolioReceptor) {
		this.reqFolioReceptor = reqFolioReceptor;
	}

	/**
	 * Requiere la cuenta nombrada del receptor.
	 * @return boolean
	 */
	public boolean isReqCnReceptor() {
		return reqCnReceptor;
	}

	/**
	 * Requiere la cuenta nombrada del receptor.
	 * @param reqCnReceptor
	 */
	public void setReqCnReceptor(boolean reqCnReceptor) {
		this.reqCnReceptor = reqCnReceptor;
	}

	/**
	 * Requiere la b&oacute;veda del receptor.
	 * @return boolean
	 */
	public boolean isReqBovedaReceptor() {
		return reqBovedaReceptor;
	}

	/**
	 * Requiere la b&oacute;veda del receptor.
	 * @param reqBovedaReceptor
	 */
	public void setReqBovedaReceptor(boolean reqBovedaReceptor) {
		this.reqBovedaReceptor = reqBovedaReceptor;
	}

	/**
	 * Requiere el tipo valor.
	 * @return boolean
	 */
	public boolean isReqTipoValor() {
		return reqTipoValor;
	}

	/**
	 * Requiere el tipo valor.
	 * @param reqTipoValor
	 */
	public void setReqTipoValor(boolean reqTipoValor) {
		this.reqTipoValor = reqTipoValor;
	}

	/**
	 * Requiere la emisi&oacute;n.
	 * @return boolean
	 */
	public boolean isReqEmision() {
		return reqEmision;
	}

	/**
	 * Requiere la emisi&oacute;n.
	 * @param reqEmision
	 */
	public void setReqEmision(boolean reqEmision) {
		this.reqEmision = reqEmision;
	}

	/**
	 * Requiere la serie.
	 * @return boolean
	 */
	public boolean isReqSerie() {
		return reqSerie;
	}

	/**
	 * Requiere la serie.
	 * @param reqSerie
	 */
	public void setReqSerie(boolean reqSerie) {
		this.reqSerie = reqSerie;
	}

	/**
	 * Requiere el cup&oacute;n.
	 * @return boolean
	 */
	public boolean isReqCupon() {
		return reqCupon;
	}

	/**
	 * Requiere el cup&oacute;n.
	 * @param reqCupon
	 */
	public void setReqCupon(boolean reqCupon) {
		this.reqCupon = reqCupon;
	}

	/**
	 * Requiere el isin.
	 * @return boolean
	 */
	public boolean isReqIsin() {
		return reqIsin;
	}

	/**
	 * Requiere el isin.
	 * @param reqIsin
	 */
	public void setReqIsin(boolean reqIsin) {
		this.reqIsin = reqIsin;
	}

	/**
	 * Requiere el nu&acute;mero de títulos.
	 * @return boolean
	 */
	public boolean isReqNumTitulos() {
		return reqNumTitulos;
	}

	/**
	 * Requiere el nu&acute;mero de ti&acute;tulos.
	 * @param reqNumTitulos
	 */
	public void setReqNumTitulos(boolean reqNumTitulos) {
		this.reqNumTitulos = reqNumTitulos;
	}

	/**
	 * Requiere el precio.
	 * @return boolean
	 */
	public boolean isReqPrecio() {
		return reqPrecio;
	}

	/**
	 * Requiere el precio.
	 * @param reqPrecio
	 */
	public void setReqPrecio(boolean reqPrecio) {
		this.reqPrecio = reqPrecio;
	}

	/**
	 * Requiere el div.
	 * @return boolean
	 */
	public boolean isReqDiv() {
		return reqDiv;
	}

	/**
	 * Requiere el div.
	 * @param reqDiv
	 */
	public void setReqDiv(boolean reqDiv) {
		this.reqDiv = reqDiv;
	}

	/**
	 * Requiere el monto.
	 * @return boolean
	 */
	public boolean isReqMonto() {
		return reqMonto;
	}

	/**
	 * Requiere el monto.
	 * @param reqMonto
	 */
	public void setReqMonto(boolean reqMonto) {
		this.reqMonto = reqMonto;
	}

	/**
	 * Requiere el origen de la operaci&oacute;n.
	 * @return boolean
	 */
	public boolean isReqOrigen() {
		return reqOrigen;
	}

	/**
	 * Requiere el origen de la operaci&oacute;n.
	 * @param reqOrigen
	 */
	public void setReqOrigen(boolean reqOrigen) {
		this.reqOrigen = reqOrigen;
	}

	/**
	 * Es una operaci&oacute;n compensable.
	 * @return boolean
	 */
	public boolean isOperacionCompensable() {
		return operacionCompensable;
	}

	/**
	 * Es una operaci&oacute;n compensable.
	 * @param operacionCompensable
	 */
	public void setOperacionCompensable(boolean operacionCompensable) {
		this.operacionCompensable = operacionCompensable;
	}

	/**
	 * Obtiene el campo maneja
	 * @return  maneja
	 */
	public String getManeja() {
		return maneja;
	}

	/**
	 * Asigna el valor del campo maneja
	 * @param maneja el valor de maneja a asignar
	 */
	public void setManeja(String maneja) {
		this.maneja = maneja;
	}
	
}
