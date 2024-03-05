/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Historico que guarda los cambios de las cuentas internacionales y las firmas correspondientes del 
 * estado y configuracion anterior, el estado y configuracion actual es la que queda en la tabla 
 * de cuentas.
 *  
 * H_ADMON_CUENTA_RETIRO_INT
 *
 * @author  Maria C. Buendia
 * @version 1.0
 */
@Entity
@Table(name="H_ADMON_CUENTA_RETIRO_INT")
@SequenceGenerator(name = "SEQ_HistCuentaRetiroInt", sequenceName = "H_ADMON_CUENTA_RETIRO_INT_SEQ",allocationSize = 1,initialValue = 1)
public class HistoricoAdmonCuentasRetiroInternacional implements Serializable {
		
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** identificador de cuenta */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HistCuentaRetiroInt")
	@Column(name = "ID_ADMON_CUENTA_RETIRO_INT", nullable = false)
	private BigInteger idHistCuentaRetiroInt;
	
	/** identificador de la bitacora */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_BITACORA", nullable = false)
	private BitacoraEstadosCuentaRetiro bitacora;
	
	/** institucion que crea la cuenta */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_INSTITUCION", nullable = false)	
	private Institucion institucion;
	
	/** estado de la cuenta */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ESTADO", nullable = false)	
	private EstadoInstruccionCat estado;
	
	/** divisa de la cuenta */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DIVISA", nullable = false)
	private Divisa divisa;
	
	/** monto maximo mensual */
	@Column(name = "MONTO_MAX_MENSUAL")
	private BigDecimal montoMaxMensual;
	
	/** monto maximo diario */
	@Column(name = "MONTO_MAX_DIARIO")
	private BigDecimal montoMaxDiario;
	
	/** monto maximo por transaccion */
	@Column(name = "MONTO_MAX_TRANSAC")
	private BigDecimal montoMaxPorTran;
	
	/** maximo de movimientos por mes */
	@Column(name = "MAX_MOVS_MENSUAL")
	private Long maxMovsMensual;
	
	/** nombre corto */
	@Column(name = "NOMBRE_CORTO")
	private String nombreCorto;

	/** banco beneficiario */
	@Column(name = "BCO_BENEFICIARIO", nullable = false)
	private String bancoBeneficiario;

	/** nombre banco beneficiario */
	@Column(name = "NOMBCO_BENEFICIARIO", nullable = false)
	private String nombreBancoBeneficiario;

	/** cuenta beneficiario */
	@Column(name = "CTA_BENEFICIARIO", nullable = false)
	private String cuentaBeneficiario;
	
	/** cuenta intermediario */
	@Column(name = "CTA_INTERMEDIARIO")
	private String cuentaIntermediario;

	/** banco intermediario */
	@Column(name = "BCO_INTERMEDIARIO")
	private String bancoIntermediario;

	/** nombre intermediario */
	@Column(name = "NOM_INTERMEDIARIO")
	private String nombreIntermediario;

	/** detalles de pago */
	@Column(name = "DETALLES_PAGO")
	private String detallesPago;

	/** fecha creacion */
	@Column(name = "FECHA_CREACION")
	private Date fechaCreacion;
	
	/** firma de la creacion */
	@Column(name = "CREACION_FIRMADA")
	private Clob creacionFirmada;
	
	/** fecha autorizacion */
	@Column(name = "FECHA_AUTORIZACION")
	private Date fechaAutorizacion;
	
	/** firma de la creacion */
	@Column(name = "AUTORIZACION_FIRMADA")
	private Clob autorizacionFirmada;
	
	/** fecha liberacion */
	@Column(name = "FECHA_LIBERACION")
	private Date fechaLiberacion;
	
	/** firma de la liberacion */
	@Column(name = "LIBERACION_FIRMADA")
	private Clob liberacionFirmada;
	
	/** fecha aprobacion */
	@Column(name = "FECHA_APROBACION")
	private Date fechaAprobacion;
	
	/** firma de la aprobacion */
	@Column(name = "APROBACION_FIRMADA")
	private Clob aprobacionFirmada;
	
	/** fecha cancelacion */
	@Column(name = "FECHA_MODIFICACION", nullable = false)
	private Date fechaModificacion;
	
	/** firma de la cancelacion */
	@Column(name = "MODIFICACION_FIRMADA", nullable = false)
	private Clob modificacionFirmada;

	public BigInteger getIdHistCuentaRetiroInt() {
		return idHistCuentaRetiroInt;
	}

	public void setIdHistCuentaRetiroInt(BigInteger idHistCuentaRetiroInt) {
		this.idHistCuentaRetiroInt = idHistCuentaRetiroInt;
	}

	public BitacoraEstadosCuentaRetiro getBitacora() {
		return bitacora;
	}

	public void setBitacora(BitacoraEstadosCuentaRetiro bitacora) {
		this.bitacora = bitacora;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public EstadoInstruccionCat getEstado() {
		return estado;
	}

	public void setEstado(EstadoInstruccionCat estado) {
		this.estado = estado;
	}

	public Divisa getDivisa() {
		return divisa;
	}

	public void setDivisa(Divisa divisa) {
		this.divisa = divisa;
	}

	public BigDecimal getMontoMaxMensual() {
		return montoMaxMensual;
	}

	public void setMontoMaxMensual(BigDecimal montoMaxMensual) {
		this.montoMaxMensual = montoMaxMensual;
	}

	public BigDecimal getMontoMaxDiario() {
		return montoMaxDiario;
	}

	public void setMontoMaxDiario(BigDecimal montoMaxDiario) {
		this.montoMaxDiario = montoMaxDiario;
	}

	public BigDecimal getMontoMaxPorTran() {
		return montoMaxPorTran;
	}

	public void setMontoMaxPorTran(BigDecimal montoMaxPorTran) {
		this.montoMaxPorTran = montoMaxPorTran;
	}

	public Long getMaxMovsMensual() {
		return maxMovsMensual;
	}

	public void setMaxMovsMensual(Long maxMovsMensual) {
		this.maxMovsMensual = maxMovsMensual;
	}

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public String getBancoBeneficiario() {
		return bancoBeneficiario;
	}

	public void setBancoBeneficiario(String bancoBeneficiario) {
		this.bancoBeneficiario = bancoBeneficiario;
	}

	public String getNombreBancoBeneficiario() {
		return nombreBancoBeneficiario;
	}

	public void setNombreBancoBeneficiario(String nombreBancoBeneficiario) {
		this.nombreBancoBeneficiario = nombreBancoBeneficiario;
	}

	public String getCuentaBeneficiario() {
		return cuentaBeneficiario;
	}

	public void setCuentaBeneficiario(String cuentaBeneficiario) {
		this.cuentaBeneficiario = cuentaBeneficiario;
	}

	public String getCuentaIntermediario() {
		return cuentaIntermediario;
	}

	public void setCuentaIntermediario(String cuentaIntermediario) {
		this.cuentaIntermediario = cuentaIntermediario;
	}

	public String getBancoIntermediario() {
		return bancoIntermediario;
	}

	public void setBancoIntermediario(String bancoIntermediario) {
		this.bancoIntermediario = bancoIntermediario;
	}

	public String getNombreIntermediario() {
		return nombreIntermediario;
	}

	public void setNombreIntermediario(String nombreIntermediario) {
		this.nombreIntermediario = nombreIntermediario;
	}

	public String getDetallesPago() {
		return detallesPago;
	}

	public void setDetallesPago(String detallesPago) {
		this.detallesPago = detallesPago;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Clob getModificacionFirmada() {
		return modificacionFirmada;
	}

	public void setModificacionFirmada(Clob modificacionFirmada) {
		this.modificacionFirmada = modificacionFirmada;
	}

	public Clob getCreacionFirmada() {
		return creacionFirmada;
	}

	public void setCreacionFirmada(Clob creacionFirmada) {
		this.creacionFirmada = creacionFirmada;
	}

	public Date getFechaAutorizacion() {
		return fechaAutorizacion;
	}

	public void setFechaAutorizacion(Date fechaAutorizacion) {
		this.fechaAutorizacion = fechaAutorizacion;
	}

	public Clob getAutorizacionFirmada() {
		return autorizacionFirmada;
	}

	public void setAutorizacionFirmada(Clob autorizacionFirmada) {
		this.autorizacionFirmada = autorizacionFirmada;
	}

	public Date getFechaLiberacion() {
		return fechaLiberacion;
	}

	public void setFechaLiberacion(Date fechaLiberacion) {
		this.fechaLiberacion = fechaLiberacion;
	}

	public Clob getLiberacionFirmada() {
		return liberacionFirmada;
	}

	public void setLiberacionFirmada(Clob liberacionFirmada) {
		this.liberacionFirmada = liberacionFirmada;
	}

	public Date getFechaAprobacion() {
		return fechaAprobacion;
	}

	public void setFechaAprobacion(Date fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}

	public Clob getAprobacionFirmada() {
		return aprobacionFirmada;
	}

	public void setAprobacionFirmada(Clob aprobacionFirmada) {
		this.aprobacionFirmada = aprobacionFirmada;
	}
}
