/*
 * Copyright (c) 2008 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 * 
 */
@Entity
@Table(name = "T_FILETRANSFER_DIVINT", uniqueConstraints = @UniqueConstraint(columnNames = {"id_inst", "folio_inst", "tipo_reg", "consec_reg" }))
@SequenceGenerator(name = "foliador", sequenceName = "ID_FILETRANSFER_DIVINT_SEQ", allocationSize = 1, initialValue = 1)
public class FileTransfer implements Serializable {
    
	/**
	 * Constante de serializacion por default 
	 */
	private static final long serialVersionUID = 1L;

	private Long idFiletransferDivint;

	private String idInst;

	private String folioInst;

	private String tipoReg;

	private BigInteger consecReg;

	private Date fechaReg;

	private String estadoReg;

	private String idInstTrasp;

	private String folioInstTrasp;

	private String cuentaTrasp;

	private String idInstRecep;

	private String folioInstRecep;

	private String cuentaRecep;

	private String tv;

	private String emisora;

	private String serie;

	private String cupon;

	private Long idBoveda;

	private String isin;

	private String cantidadTitulos;

	private String tipoOperacion;

	private String tipoMovto;

	private String fechaNotificacion;

	private String fechaLiquidacion;

	private String fechaOperacion;

	private String descCustodio;

	private String cuentaContraparte;

	private String descContraparte;

	private String nombreDepositante;

	private String nombreCuentaBeneficiario;

	private String numeroCuentaBeneficiario;

	private String importe;

	private String divisa;

	private String instruccEspeciales;

	private String tipoMensaje;

	private String estatusOper;

	private String bajaLogica;

	private String mercado;

	private String usuario;

	private Clob error;

	private String camposError;

	private int clavevalorProporcionada;
	
	private String liquidacionParcial;
	

	/**
	 * @return Long
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "id_filetransfer_divint", unique = true, nullable = false)
	public Long getIdFiletransferDivint() {
		return idFiletransferDivint;
	}

	/**
	 * @return String
	 */
	@Column(name = "id_inst", unique = false, nullable = false)
	public String getIdInst() {
		return idInst;
	}

	/**
	 * @return String
	 */
	@Column(name = "folio_inst", unique = false, nullable = false)
	public String getFolioInst() {
		return folioInst;
	}

	/**
	 * @return String
	 */
	@Column(name = "tipo_reg", unique = false, nullable = false)
	public String getTipoReg() {
		return tipoReg;
	}

	/**
	 * @return BigInteger
	 */
	@Column(name = "consec_reg", unique = false, nullable = false)
	public BigInteger getConsecReg() {
		return consecReg;
	}

	/**
	 * @return Date
	 */
	@Column(name = "fecha_reg", unique = false, nullable = false)
	public Date getFechaReg() {
		return fechaReg;
	}

	/**
	 * @return String
	 */
	@Column(name = "estado_reg", unique = false, nullable = false)
	public String getEstadoReg() {
		return estadoReg;
	}

	/**
	 * @return String
	 */ 
	@Column(name = "id_inst_trasp", unique = false, nullable = false)
	public String getIdInstTrasp() {
		return idInstTrasp;
	}

	/**
	 * @return
	 */
	@Column(name = "folio_inst_trasp", unique = false, nullable = false)
	public String getFolioInstTrasp() {
		return folioInstTrasp;
	}

	/**
	 * @return
	 */
	@Column(name = "cuenta_trasp", unique = false, nullable = false)
	public String getCuentaTrasp() {
		return cuentaTrasp;
	}

	/**
	 * @return String
	 */
	@Column(name = "id_inst_recep", unique = false, nullable = false)
	public String getIdInstRecep() {
		return idInstRecep;
	}

	/**
	 * @return String
	 */
	@Column(name = "folio_inst_recep", unique = false, nullable = false)
	public String getFolioInstRecep() {
		return folioInstRecep;
	}

	/**
	 * @return String
	 */
	@Column(name = "cuenta_recep", unique = false, nullable = false)
	public String getCuentaRecep() {
		return cuentaRecep;
	}

	/**
	 * @return String
	 */
	@Column(name = "tv", unique = false, nullable = true)
	public String getTv() {
		return tv;
	}

	/**
	 * @return String
	 */
	@Column(name = "emisora", unique = false, nullable = true)
	public String getEmisora() {
		return emisora;
	}

	/**
	 * @return String
	 */
	@Column(name = "serie", unique = false, nullable = true)
	public String getSerie() {
		return serie;
	}

	/**
	 * @return String
	 */
	@Column(name = "cupon", unique = false, nullable = true)
	public String getCupon() {
		return cupon;
	}

	/**
	 * @return BigInteger
	 */
	@Column(name = "id_boveda", unique = false, nullable = false)
	public Long getIdBoveda() {
		return idBoveda;
	}

	/**
	 * @return String
	 */
	@Column(name = "isin", unique = false, nullable = true)
	public String getIsin() {
		return isin;
	}

	/**
	 * @return String
	 */
	@Column(name = "cantidad_titulos", unique = false, nullable = false)
	public String getCantidadTitulos() {
		return cantidadTitulos;
	}

	/**
	 * @return String
	 */
	@Column(name = "tipo_operacion", unique = false, nullable = false)
	public String getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * @return String
	 */
	@Column(name = "tipo_movto", unique = false, nullable = false)
	public String getTipoMovto() {
		return tipoMovto;
	}

	/**
	 * @return String
	 */
	@Column(name = "fecha_notificacion", unique = false, nullable = false)
	public String getFechaNotificacion() {
		return fechaNotificacion;
	}

	/**
	 * @return String
	 */
	@Column(name = "fecha_liquidacion", unique = false, nullable = false)
	public String getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	/**
	 * @return String
	 */
	@Column(name = "fecha_operacion", unique = false, nullable = false)
	public String getFechaOperacion() {
		return fechaOperacion;
	}

	/**
	 * @return String
	 */
	@Column(name = "desc_custodio", unique = false, nullable = true)
	public String getDescCustodio() {
		return descCustodio;
	}

	/**
	 * @return String
	 */
	@Column(name = "cuenta_contraparte", unique = false, nullable = true)
	public String getCuentaContraparte() {
		return cuentaContraparte;
	}

	/**
	 * @return String
	 */
	@Column(name = "desc_contraparte", unique = false, nullable = true)
	public String getDescContraparte() {
		return descContraparte;
	}

	/**
	 * @return String
	 */
	@Column(name = "nombre_depositante", unique = false, nullable = true)
	public String getNombreDepositante() {
		return nombreDepositante;
	}

	/**
	 * @return String
	 */
	@Column(name = "nombre_cuenta_beneficiario", unique = false, nullable = true)
	public String getNombreCuentaBeneficiario() {
		return nombreCuentaBeneficiario;
	}

	/**
	 * @return String
	 */
	@Column(name = "numero_cuenta_beneficiario", unique = false, nullable = true)
	public String getNumeroCuentaBeneficiario() {
		return numeroCuentaBeneficiario;
	}

	/**
	 * @return String
	 */
	@Column(name = "importe", unique = false, nullable = true)
	public String getImporte() {
		return importe;
	}

	/**
	 * @return String
	 */
	@Column(name = "divisa", unique = false, nullable = true)
	public String getDivisa() {
		return divisa;
	}

	/**
	 * @return String
	 */
	@Column(name = "instrucc_especiales", unique = false, nullable = true)
	public String getInstruccEspeciales() {
		return instruccEspeciales;
	}

	/**
	 * @return String
	 */
	@Column(name = "tipo_mensaje", unique = false, nullable = false)
	public String getTipoMensaje() {
		return tipoMensaje;
	}

	/**
	 * @return String
	 */
	@Column(name = "estatus_oper", unique = false, nullable = false)
	public String getEstatusOper() {
		return estatusOper;
	}

	/**
	 * @return String
	 */
	@Column(name = "baja_logica", unique = false, nullable = true)
	public String getBajaLogica() {
		return bajaLogica;
	}

	/**
	 * @return String
	 */
	@Column(name = "mercado", unique = false, nullable = true)
	public String getMercado() {
		return mercado;
	}

	/**
	 * @return String
	 */
	@Column(name = "usuario", unique = false, nullable = true)
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @return Clob
	 */
	@Column(name = "error", unique = false, nullable = true)
	public Clob getError() {
		return error;
	}

	/**
	 * @return String
	 */
	@Column(name = "campos_error", unique = false, nullable = true)
	public String getCamposError() {
		return camposError;
	}

	/**
	 * @return the clavevalorProporcionada
	 */
	@Column(name = "clavevalor_proporcionada", unique = false, nullable = true)
	public int getClavevalorProporcionada() {
		return clavevalorProporcionada;
	}
	
	/**
	 * @return the liquidacionParcial
	 */
	@Column(name = "LIQ_PARCIAL")
	public String getLiquidacionParcial() {
		return liquidacionParcial;
	}

	/**
	 * @param idFiletransferDivint
	 */
	public void setIdFiletransferDivint(Long idFiletransferDivint) {
		this.idFiletransferDivint = idFiletransferDivint;
	}

	/**
	 * @param idInst
	 */
	public void setIdInst(String idInst) {
		this.idInst = idInst;
	}

	/**
	 * @param folioInst
	 */
	public void setFolioInst(String folioInst) {
		this.folioInst = folioInst;
	}

	/**
	 * @param tipoReg
	 */
	public void setTipoReg(String tipoReg) {
		this.tipoReg = tipoReg;
	}

	/**
	 * @param consecReg
	 */
	public void setConsecReg(BigInteger consecReg) {
		this.consecReg = consecReg;
	}

	/**
	 * @param fechaReg
	 */
	public void setFechaReg(Date fechaReg) {
		this.fechaReg = fechaReg;
	}

	/**
	 * @param estadoReg
	 */
	public void setEstadoReg(String estadoReg) {
		this.estadoReg = estadoReg;
	}

	/**
	 * @param idInstTrasp
	 */
	public void setIdInstTrasp(String idInstTrasp) {
		this.idInstTrasp = idInstTrasp;
	}

	/**
	 * @param folioInstTrasp
	 */
	public void setFolioInstTrasp(String folioInstTrasp) {
		this.folioInstTrasp = folioInstTrasp;
	}

	/**
	 * @param cuentaTrasp
	 */
	public void setCuentaTrasp(String cuentaTrasp) {
		this.cuentaTrasp = cuentaTrasp;
	}

	/**
	 * @param idInstRecep
	 */
	public void setIdInstRecep(String idInstRecep) {
		this.idInstRecep = idInstRecep;
	}

	/**
	 * @param folioInstRecep
	 */
	public void setFolioInstRecep(String folioInstRecep) {
		this.folioInstRecep = folioInstRecep;
	}

	/**
	 * @param cuentaRecep
	 */
	public void setCuentaRecep(String cuentaRecep) {
		this.cuentaRecep = cuentaRecep;
	}

	/**
	 * @param tv
	 */
	public void setTv(String tv) {
		this.tv = tv;
	}

	/**
	 * @param emisora
	 */
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}

	/**
	 * @param serie
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	/**
	 * @param cupon
	 */
	public void setCupon(String cupon) {
		this.cupon = cupon;
	}

	/**
	 * @param idBoveda
	 */
	public void setIdBoveda(Long idBoveda) {
		this.idBoveda = idBoveda;
	}

	/**
	 * @param isin
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}

	/**
	 * @param cantidadTitulos
	 */
	public void setCantidadTitulos(String cantidadTitulos) {
		this.cantidadTitulos = cantidadTitulos;
	}

	/**
	 * @param tipoOperacion
	 */
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	/**
	 * @param tipoMovto
	 */
	public void setTipoMovto(String tipoMovto) {
		this.tipoMovto = tipoMovto;
	}

	/**
	 * @param fechaNotificacion
	 */
	public void setFechaNotificacion(String fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	/**
	 * @param fechaLiquidacion
	 */
	public void setFechaLiquidacion(String fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	/**
	 * @param fechaOperacion
	 */
	public void setFechaOperacion(String fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	/**
	 * @param descCustodio
	 */
	public void setDescCustodio(String descCustodio) {
		this.descCustodio = descCustodio;
	}

	/**
	 * @param cuentaContraparte
	 */
	public void setCuentaContraparte(String cuentaContraparte) {
		this.cuentaContraparte = cuentaContraparte;
	}

	/**
	 * @param descContraparte
	 */
	public void setDescContraparte(String descContraparte) {
		this.descContraparte = descContraparte;
	}

	/**
	 * @param nombreDepositante
	 */
	public void setNombreDepositante(String nombreDepositante) {
		this.nombreDepositante = nombreDepositante;
	}

	/**
	 * @param nombreCuentaBeneficiario
	 */
	public void setNombreCuentaBeneficiario(String nombreCuentaBeneficiario) {
		this.nombreCuentaBeneficiario = nombreCuentaBeneficiario;
	}

	/**
	 * @param numeroCuentaBeneficiario
	 */
	public void setNumeroCuentaBeneficiario(String numeroCuentaBeneficiario) {
		this.numeroCuentaBeneficiario = numeroCuentaBeneficiario;
	}

	/**
	 * @param importe
	 */
	public void setImporte(String importe) {
		this.importe = importe;
	}

	/**
	 * @param divisa
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	/**
	 * @param instruccEspeciales
	 */
	public void setInstruccEspeciales(String instruccEspeciales) {
		this.instruccEspeciales = instruccEspeciales;
	}

	/**
	 * @param tipoMensaje
	 */
	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	/**
	 * @param estatusOper
	 */
	public void setEstatusOper(String estatusOper) {
		this.estatusOper = estatusOper;
	}

	/**
	 * @param bajaLogica
	 */
	public void setBajaLogica(String bajaLogica) {
		this.bajaLogica = bajaLogica;
	}

	/**
	 * @param mercado
	 */
	public void setMercado(String mercado) {
		this.mercado = mercado;
	}

	/**
	 * @param usuario
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @param error
	 */
	public void setError(Clob error) {
		this.error = error;
	}

	/**
	 * @param camposError
	 */
	public void setCamposError(String camposError) {
		this.camposError = camposError;
	}

	/**
	 * @param clavevalorProporcionada
	 *            the clavevalorProporcionada to set
	 */
	public void setClavevalorProporcionada(int clavevalorProporcionada) {
		this.clavevalorProporcionada = clavevalorProporcionada;
	}

	/**
	 * @param liquidacionParcial the liquidacionParcial to set
	 */
	public void setLiquidacionParcial(String liquidacionParcial) {
		this.liquidacionParcial = liquidacionParcial;
	}

}
