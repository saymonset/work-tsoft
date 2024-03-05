/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Esta clase se utiliza para persistir en Base de Datos el Mensaje del
 * Liquidador en Efectivo.
 * 
 * @author Abraham Resendiz, apalacios
 * @since 1.0
 */
@Entity
@Table(name = "T_MENSAJE_LIQUIDADOR_EFECTIVO")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_MSJ_LIQUIDADOR_EFECTIVO", allocationSize = 1, initialValue = 1)
@NamedQuery(name = "MensajeLiquidadorEfectivo.getMsgLiqEfectivoByIdInstruccionEfectivo", query = "FROM MensajeLiquidadorEfectivo AS a WHERE a.instruccionEfectivo.id =:id and folioCiclo > 0")
public class MensajeLiquidadorEfectivo implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	private BigInteger id;
	private InstruccionEfectivo instruccionEfectivo;
	private Date fechaRecepcion;
	private Date fechaOperacion;
	private BigInteger claveInstitucionOrdenante;
	private String nombreOrdenante;
	private String claveBeneficiario;
	private String concepto;
	private BigInteger referenciaNumerica;
	private String claveRastreo;
	private BigDecimal monto;
	private BigDecimal saldo;
	private BigInteger folioCiclo;
	private BigInteger folioSolicitud;
	private BigInteger folio;
	private BigInteger folioMovimientoPaquete;
	private MotivoDevolucion motivoDevolucion;
	private String mensajeRecibido;
	private TipoPagoEnum tipoPago;

	/**
	 * Regresa el valor de la propiedad <code>claveBeneficiario</code>.
	 * 
	 * @return claveBeneficiario. La clave del beneficiario
	 */
	@Column(name = "CLAVE_BENEFICIARIO")
	public String getClaveBeneficiario() {
		return claveBeneficiario;
	}

	/**
	 * Asigna la propiedad <code>claveBeneficiario</code>.
	 * 
	 * @param claveBeneficiario
	 *            La clave del beneficiario
	 */
	public void setClaveBeneficiario(String claveBeneficiario) {
		this.claveBeneficiario = claveBeneficiario;
	}

	/**
	 * Regresa el valor de la propiedad <code>claveInstitucionOrdenante</code>.
	 * 
	 * @return claveInstitucionOrdenante. Clave de la Instrucci&oacute;n
	 *         Ordenante
	 */
	@Column(name = "CLAVE_INSTITUCION_ORDENANTE")
	public BigInteger getClaveInstitucionOrdenante() {
		return claveInstitucionOrdenante;
	}

	/**
	 * Asigna la propiedad <code>claveInstitucionOrdenante</code>.
	 * 
	 * @param claveInstitucionOrdenante
	 *            Clave de la Instrucci&oacute;n Ordenante
	 */
	public void setClaveInstitucionOrdenante(
			BigInteger claveInstitucionOrdenante) {
		this.claveInstitucionOrdenante = claveInstitucionOrdenante;
	}

	/**
	 * Regresa el valor de la propiedad <code>claveRastreo</code>.
	 * 
	 * @return claveRastreo. La clave del rastreo
	 */
	@Column(name = "CLAVE_RASTREO")
	public String getClaveRastreo() {
		return claveRastreo;
	}

	/**
	 * Asigna la propiedad <code>claveRastreo</code>.
	 * 
	 * @param claveRastreo
	 *            La clave del rastreo
	 */
	public void setClaveRastreo(String claveRastreo) {
		this.claveRastreo = claveRastreo;
	}

	/**
	 * Regresa el valor de la propiedad <code>concepto</code>.
	 * 
	 * @return concepto. El concepto
	 */
	@Column(name = "CONCEPTO")
	public String getConcepto() {
		return concepto;
	}

	/**
	 * Asigna la propiedad <code>concepto</code>.
	 * 
	 * @param concepto
	 *            El concepto
	 */
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	/**
	 * Regresa el valor de la propiedad <code>fecha</code>.
	 * 
	 * @return fechaRecepcion. Fecha de recepción
	 */
	@Column(name = "FECHA_RECEPCION")
	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}

	/**
	 * Asigna la propiedad <code>fecha</code>.
	 * 
	 * @param fechaRecepcion
	 */
	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}

	/**
	 * Regresa el valor de la propiedad <code>fechaOperacion</code>.
	 * 
	 * @return fechaOperacion. La fecha de operaci&oacute;n
	 */
	@Column(name = "FECHA_OPERACION")
	public Date getFechaOperacion() {
		return fechaOperacion;
	}

	/**
	 * Asigna la propiedad <code>fechaOperacion</code>.
	 * 
	 * @param fechaOperacion
	 *            La fecha de operaci&oacute;n
	 */
	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	/**
	 * Regresa el valor de la propiedad <code>folio</code>.
	 * 
	 * @return folio. Folio
	 */
	@Column(name = "FOLIO")
	public BigInteger getFolio() {
		return folio;
	}

	/**
	 * Asigna la propiedad <code>folio</code>.
	 * 
	 * @param folio
	 *            Folio
	 */
	public void setFolio(BigInteger folio) {
		this.folio = folio;
	}

	/**
	 * Regresa el valor de la propiedad <code>folioCiclo</code>.
	 * 
	 * @return folioCiclo. Ciclo del folio
	 */
	@Column(name = "FOLIO_CICLO")
	public BigInteger getFolioCiclo() {
		return folioCiclo;
	}

	/**
	 * Asigna la propiedad <code>folioCiclo</code>.
	 * 
	 * @param folioCiclo
	 *            Ciclo del folio
	 */
	public void setFolioCiclo(BigInteger folioCiclo) {
		this.folioCiclo = folioCiclo;
	}

	/**
	 * Regresa el valor de la propiedad <code>folioMovimientoPaquete</code>.
	 * 
	 * @return folioMovimientoPaquete. Folio del Movimiento del Paquete
	 */
	@Column(name = "FOLIO_MOVIMIENTO_PAQUETE")
	public BigInteger getFolioMovimientoPaquete() {
		return folioMovimientoPaquete;
	}

	/**
	 * Asigna la propiedad <code>folioMovimientoPaquete</code>.
	 * 
	 * @param folioMovimientoPaquete
	 *            Folio del Movimiento del Paquete
	 */
	public void setFolioMovimientoPaquete(BigInteger folioMovimientoPaquete) {
		this.folioMovimientoPaquete = folioMovimientoPaquete;
	}

	/**
	 * Regresa el valor de la propiedad <code>folioSolicitud</code>.
	 * 
	 * @return folioSolicitud. Folio de la Solicitud
	 */
	@Column(name = "FOLIO_SOLICITUD")
	public BigInteger getFolioSolicitud() {
		return folioSolicitud;
	}

	/**
	 * Asigna la propiedad <code>folioSolicitud</code>.
	 * 
	 * @param folioSolicitud
	 *            Folio de la Solicitud
	 */
	public void setFolioSolicitud(BigInteger folioSolicitud) {
		this.folioSolicitud = folioSolicitud;
	}

	/**
	 * Regresa el valor de la propiedad <code>id</code>.
	 * 
	 * @return id. Identificador del Mensaje de Liquidador en Efectivo
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_MENSAJE_LIQUIDADOR_EFECTIVO")
	public BigInteger getId() {
		return id;
	}

	/**
	 * Asigna la propiedad <code>id</code>.
	 * 
	 * @param id
	 *            Identificador del Mensaje de Liquidador en Efectivo
	 */
	public void setId(BigInteger id) {
		this.id = id;
	}

	/**
	 * Regresa el valor de la propiedad <code>instruccionEfectivo</code>.
	 * 
	 * @return instruccionEfectivo. Intrucci&ocaute;n en Efectivo
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_INSTRUCCION_EFECTIVO", updatable = true, insertable = true)
	public InstruccionEfectivo getInstruccionEfectivo() {
		return instruccionEfectivo;
	}

	/**
	 * Asigna la propiedad <code>instruccionEfectivo</code>.
	 * 
	 * @param instruccionEfectivo
	 *            Intrucci&ocaute;n en Efectivo
	 */
	public void setInstruccionEfectivo(InstruccionEfectivo instruccionEfectivo) {
		this.instruccionEfectivo = instruccionEfectivo;
	}

	/**
	 * Regresa el valor de la propiedad <code>mensajeRecibido</code>.
	 * 
	 * @return mensajeRecibido. El Mensaje Recibido
	 */
	@Lob
	@Column(name = "MENSAJE_RECIBIDO")
	public String getMensajeRecibido() {
		return mensajeRecibido;
	}

	/**
	 * Asigna la propiedad <code>mensajeRecibido</code>.
	 * 
	 * @param mensajeRecibido
	 *            El Mensaje Recibido
	 */
	public void setMensajeRecibido(String mensajeRecibido) {
		this.mensajeRecibido = mensajeRecibido;
	}

	/**
	 * Regresa el valor de la propiedad <code>monto</code>.
	 * 
	 * @return monto. Monto
	 */
	@Column(name = "MONTO")
	public BigDecimal getMonto() {
		return monto;
	}

	/**
	 * Asigna la propiedad <code>monto</code>.
	 * 
	 * @param monto
	 *            Monto
	 */
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	/**
	 * Regresa el valor de la propiedad <code>motivoDevolucion</code>.
	 * 
	 * @return motivoDevolucion. Motivo de la Devoluci&oacute;n
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_MOTIVO_DEVOLUCION", updatable = true, insertable = true)
	public MotivoDevolucion getMotivoDevolucion() {
		return motivoDevolucion;
	}

	/**
	 * Asigna la propiedad <code>motivoDevolucion</code>.
	 * 
	 * @param motivoDevolucion
	 *            Motivo de la Devoluci&oacute;n
	 */
	public void setMotivoDevolucion(MotivoDevolucion motivoDevolucion) {
		this.motivoDevolucion = motivoDevolucion;
	}

	/**
	 * Regresa el valor de la propiedad <code>nombreOrdenante</code>.
	 * 
	 * @return nombreOrdenante. Nombre del Ordenante
	 */
	@Column(name = "NOMBRE_ORDENANTE")
	public String getNombreOrdenante() {
		return nombreOrdenante;
	}

	/**
	 * Asigna la propiedad <code>nombreOrdenante</code>.
	 * 
	 * @param nombreOrdenante
	 *            Nombre del Ordenante
	 */
	public void setNombreOrdenante(String nombreOrdenante) {
		this.nombreOrdenante = nombreOrdenante;
	}

	/**
	 * Regresa el valor de la propiedad <code>referenciaNumerica</code>.
	 * 
	 * @return referenciaNumerica. Referencia Num&eacute;rica
	 */
	@Column(name = "REFERENCIA_NUMERICA")
	public BigInteger getReferenciaNumerica() {
		return referenciaNumerica;
	}

	/**
	 * Asigna la propiedad <code>referenciaNumerica</code>.
	 * 
	 * @param referenciaNumerica
	 *            Referencia Num&eacute;rica
	 */
	public void setReferenciaNumerica(BigInteger referenciaNumerica) {
		this.referenciaNumerica = referenciaNumerica;
	}

	/**
	 * Regresa el valor de la propiedad <code>saldo</code>.
	 * 
	 * @return saldo. El saldo
	 */
	@Column(name = "SALDO")
	public BigDecimal getSaldo() {
		return saldo;
	}

	/**
	 * Asigna la propiedad <code>saldo</code>.
	 * 
	 * @param saldo
	 *            El saldo
	 */
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	/**
	 * Regresa el valor de la propiedad <code>tipoPago</code>.
	 * 
	 * @return tipoPago
	 */
	@Enumerated
	@Column(name = "ID_TIPO_PAGO")
	public TipoPagoEnum getTipoPago() {
		return tipoPago;
	}

	/**
	 * Asigna la propiedad <code>tipoPago</code>.
	 * 
	 * @param tipoPago
	 */
	public void setTipoPago(TipoPagoEnum tipoPago) {
		this.tipoPago = tipoPago;
	}

}