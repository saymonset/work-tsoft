/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
* 
*
*/
@Entity
@Table(name = "T_BITACORA_OPERACIONES")
public class BitacoraOperaciones {

	private BigInteger idBitacoraOperaciones; // PK

	private String referenciaMensaje;

	private Date fechaConcertacion;

	private String idTrasp;

	private String folioTrasp;

	private String cuentaTrasp;

	private String idRecep;

	private String folioRecep;

	private String cuentaRecep;

	private String tv;

	private String emisora;

	private String serie;

	private String cupon;

	private BigInteger cantidadTitulos;

	private String tipoInstruccion;

	private BigInteger folioControl;

	private BigDecimal monto;

	private BigDecimal precio;

	private String boveda;

	private String bovedaEfectivo;

	private String divisa;

	private Date fechaVencimiento;

	private Date fechaLiquidacion;

	private BigDecimal tasaNegociada;

	private Integer tasaFija;

	private BigDecimal tasaReferencia;

	private String idFolioCtaPuente;

	private Date fechaRegistro;

	private String usuario;

	private String estatusRegistro;

	private String origenRegistro;

	private String codigoError;

	private String referenciaOperacion;

	private String referenciaRelacionada;

	private Integer cargoParticipante;

	private String mercado;

	private Date fechaHoraAlta;

	private Date fechaHoraCambio;

	private Date fechaHoraCierreOper;

	private Integer marcaCompra;

	private String operacionFirmada;

	private Clob datosAdicionales;

	@Id
	@Column(name = "id_bitacora_operaciones", unique = true, nullable = false)
	public BigInteger getIdBitacoraOperaciones() {
		return idBitacoraOperaciones;
	}

	@Column(name = "referencia_mensaje", unique = true, nullable = false)
	public String getReferenciaMensaje() {
		return referenciaMensaje;
	}

	@Column(name = "fecha_concertacion", unique = true, nullable = false)
	public Date getFechaConcertacion() {
		return fechaConcertacion;
	}

	@Column(name = "id_trasp", unique = false, nullable = true)
	public String getIdTrasp() {
		return idTrasp;
	}

	@Column(name = "folio_trasp", unique = false, nullable = true)
	public String getFolioTrasp() {
		return folioTrasp;
	}

	@Column(name = "cuenta_trasp", unique = false, nullable = true)
	public String getCuentaTrasp() {
		return cuentaTrasp;
	}

	@Column(name = "id_recep", unique = false, nullable = true)
	public String getIdRecep() {
		return idRecep;
	}

	@Column(name = "folio_recep", unique = false, nullable = true)
	public String getFolioRecep() {
		return folioRecep;
	}

	@Column(name = "cuenta_recep", unique = false, nullable = true)
	public String getCuentaRecep() {
		return cuentaRecep;
	}

	@Column(name = "tv", unique = false, nullable = true)
	public String getTv() {
		return tv;
	}

	@Column(name = "emisora", unique = false, nullable = true)
	public String getEmisora() {
		return emisora;
	}

	@Column(name = "serie", unique = false, nullable = true)
	public String getSerie() {
		return serie;
	}

	@Column(name = "cupon", unique = false, nullable = true)
	public String getCupon() {
		return cupon;
	}

	@Column(name = "cantidad_titulos", unique = false, nullable = true)
	public BigInteger getCantidadTitulos() {
		return cantidadTitulos;
	}

	@Column(name = "tipo_instruccion", unique = false, nullable = true)
	public String getTipoInstruccion() {
		return tipoInstruccion;
	}

	@Column(name = "folio_control", unique = false, nullable = true)
	public BigInteger getFolioControl() {
		return folioControl;
	}

	@Column(name = "monto", unique = false, nullable = true)
	public BigDecimal getMonto() {
		return monto;
	}

	@Column(name = "precio", unique = false, nullable = true)
	public BigDecimal getPrecio() {
		return precio;
	}

	@Column(name = "boveda", unique = false, nullable = true)
	public String getBoveda() {
		return boveda;
	}

	@Column(name = "boveda_efectivo", unique = false, nullable = true)
	public String getBovedaEfectivo() {
		return bovedaEfectivo;
	}

	@Column(name = "divisa", unique = false, nullable = true)
	public String getDivisa() {
		return divisa;
	}

	@Column(name = "fecha_vencimiento", unique = false, nullable = true)
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	@Column(name = "fecha_liquidacion", unique = false, nullable = true)
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	@Column(name = "tasa_negociada", unique = false, nullable = true)
	public BigDecimal getTasaNegociada() {
		return tasaNegociada;
	}

	@Column(name = "tasa_fija", unique = false, nullable = true)
	public Integer getTasaFija() {
		return tasaFija;
	}

	@Column(name = "tasa_referencia", unique = false, nullable = true)
	public BigDecimal getTasaReferencia() {
		return tasaReferencia;
	}

	@Column(name = "id_folio_cta_puente", unique = false, nullable = true)
	public String getIdFolioCtaPuente() {
		return idFolioCtaPuente;
	}

	@Column(name = "fecha_registro", unique = false, nullable = true)
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	@Column(name = "usuario", unique = false, nullable = true)
	public String getUsuario() {
		return usuario;
	}

	@Column(name = "estatus_registro", unique = false, nullable = true)
	public String getEstatusRegistro() {
		return estatusRegistro;
	}

	@Column(name = "origen_registro", unique = false, nullable = true)
	public String getOrigenRegistro() {
		return origenRegistro;
	}

	@Column(name = "codigo_error", unique = false, nullable = true)
	public String getCodigoError() {
		return codigoError;
	}

	@Column(name = "referencia_operacion", unique = false, nullable = true)
	public String getReferenciaOperacion() {
		return referenciaOperacion;
	}

	@Column(name = "referencia_relacionada", unique = false, nullable = true)
	public String getReferenciaRelacionada() {
		return referenciaRelacionada;
	}

	@Column(name = "cargo_participante", unique = false, nullable = true)
	public Integer getCargoParticipante() {
		return cargoParticipante;
	}

	@Column(name = "mercado", unique = false, nullable = true)
	public String getMercado() {
		return mercado;
	}

	@Column(name = "fecha_hora_alta", unique = false, nullable = true)
	public Date getFechaHoraAlta() {
		return fechaHoraAlta;
	}

	@Column(name = "fecha_hora_cambio", unique = false, nullable = true)
	public Date getFechaHoraCambio() {
		return fechaHoraCambio;
	}

	@Column(name = "fecha_hora_cierre_oper", unique = false, nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getFechaHoraCierreOper() {
		return fechaHoraCierreOper;
	}

	@Column(name = "marca_compra", unique = true, nullable = false)
	public Integer getMarcaCompra() {
		return marcaCompra;
	}

	@Column(name = "operacion_firmada", unique = false, nullable = true)
	public String getOperacionFirmada() {
		return operacionFirmada;
	}

	@Column(name = "datos_adicionales", unique = false, nullable = true)
	public Clob getDatosAdicionales() {
		return datosAdicionales;
	}

	/**
	 * @param idBitacoraOperaciones
	 */
	public void setIdBitacoraOperaciones(BigInteger idBitacoraOperaciones) {
		this.idBitacoraOperaciones = idBitacoraOperaciones;
	}

	/**
	 * @param referenciaMensaje
	 */
	public void setReferenciaMensaje(String referenciaMensaje) {
		this.referenciaMensaje = referenciaMensaje;
	}

	/**
	 * @param fechaConcertacion
	 */
	public void setFechaConcertacion(Date fechaConcertacion) {
		this.fechaConcertacion = fechaConcertacion;
	}

	/**
	 * @param idTrasp
	 */
	public void setIdTrasp(String idTrasp) {
		this.idTrasp = idTrasp;
	}

	/**
	 * @param folioTrasp
	 */
	public void setFolioTrasp(String folioTrasp) {
		this.folioTrasp = folioTrasp;
	}

	/**
	 * @param cuentaTrasp
	 */
	public void setCuentaTrasp(String cuentaTrasp) {
		this.cuentaTrasp = cuentaTrasp;
	}

	/**
	 * @param idRecep
	 */
	public void setIdRecep(String idRecep) {
		this.idRecep = idRecep;
	}

	/**
	 * @param folioRecep
	 */
	public void setFolioRecep(String folioRecep) {
		this.folioRecep = folioRecep;
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
	 * @param cantidadTitulos
	 */
	public void setCantidadTitulos(BigInteger cantidadTitulos) {
		this.cantidadTitulos = cantidadTitulos;
	}

	/**
	 * @param tipoInstruccion
	 */
	public void setTipoInstruccion(String tipoInstruccion) {
		this.tipoInstruccion = tipoInstruccion;
	}

	/**
	 * @param folioControl
	 */
	public void setFolioControl(BigInteger folioControl) {
		this.folioControl = folioControl;
	}

	/**
	 * @param monto
	 */
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	/**
	 * @param precio
	 */
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	/**
	 * @param boveda
	 */
	public void setBoveda(String boveda) {
		this.boveda = boveda;
	}

	/**
	 * @param bovedaEfectivo
	 */
	public void setBovedaEfectivo(String bovedaEfectivo) {
		this.bovedaEfectivo = bovedaEfectivo;
	}

	/**
	 * @param divisa
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	/**
	 * @param fechaVencimiento
	 */
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	/**
	 * @param fechaLiquidacion
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	/**
	 * @param tasaNegociada
	 */
	public void setTasaNegociada(BigDecimal tasaNegociada) {
		this.tasaNegociada = tasaNegociada;
	}

	/**
	 * @param tasaFija
	 */
	public void setTasaFija(Integer tasaFija) {
		this.tasaFija = tasaFija;
	}

	/**
	 * @param tasaReferencia
	 */
	public void setTasaReferencia(BigDecimal tasaReferencia) {
		this.tasaReferencia = tasaReferencia;
	}

	/**
	 * @param idFolioCtaPuente
	 */
	public void setIdFolioCtaPuente(String idFolioCtaPuente) {
		this.idFolioCtaPuente = idFolioCtaPuente;
	}

	/**
	 * @param fechaRegistro
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * @param usuario
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @param estatusRegistro
	 */
	public void setEstatusRegistro(String estatusRegistro) {
		this.estatusRegistro = estatusRegistro;
	}

	/**
	 * @param origenRegistro
	 */
	public void setOrigenRegistro(String origenRegistro) {
		this.origenRegistro = origenRegistro;
	}

	/**
	 * @param codigoError
	 */
	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}

	/**
	 * @param referenciaOperacion
	 */
	public void setReferenciaOperacion(String referenciaOperacion) {
		this.referenciaOperacion = referenciaOperacion;
	}

	/**
	 * @param referenciaRelacionada
	 */
	public void setReferenciaRelacionada(String referenciaRelacionada) {
		this.referenciaRelacionada = referenciaRelacionada;
	}

	/**
	 * @param cargoParticipante
	 */
	public void setCargoParticipante(Integer cargoParticipante) {
		this.cargoParticipante = cargoParticipante;
	}

	/**
	 * @param mercado
	 */
	public void setMercado(String mercado) {
		this.mercado = mercado;
	}

	/**
	 * @param fechaHoraAlta
	 */
	public void setFechaHoraAlta(Date fechaHoraAlta) {
		this.fechaHoraAlta = fechaHoraAlta;
	}

	public void setFechaHoraCierreOper(Date fechaHoraCierreOper) {
		this.fechaHoraCierreOper = fechaHoraCierreOper;
	}

	/**
	 * @param fechaHoraCambio
	 */
	public void setFechaHoraCambio(Date fechaHoraCambio) {
		this.fechaHoraCambio = fechaHoraCambio;
	}

	/**
	 * @param marcaCompra
	 */
	public void setMarcaCompra(Integer marcaCompra) {
		this.marcaCompra = marcaCompra;
	}

	/**
	 * @param operacionFirmada
	 */
	public void setOperacionFirmada(String operacionFirmada) {
		this.operacionFirmada = operacionFirmada;
	}

	/**
	 * @param datosAdicionales
	 */
	public void setDatosAdicionales(Clob datosAdicionales) {
		this.datosAdicionales = datosAdicionales;
	}

	/**
	 * @return boolean
	 */
	@Transient
	public boolean tieneidFolioTrasp() {
	    return StringUtils.isNotBlank(this.getIdTrasp()) 
	    	&& StringUtils.isNotBlank(this.getFolioTrasp());
	}

	/**
	 * @return boolean
	 */
	@Transient
	public boolean tieneidFolioRecep() {
	    return StringUtils.isNotBlank(this.getIdRecep()) 
	    	&& StringUtils.isNotBlank(this.getFolioRecep());
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("boveda", boveda)
				.append("bovedaEfectivo", bovedaEfectivo)
				.append("divisa", divisa).toString();
	}

}
