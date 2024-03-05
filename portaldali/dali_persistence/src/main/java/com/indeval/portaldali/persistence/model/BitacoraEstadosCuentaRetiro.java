/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.CascadeType;
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
 * Bitacora que guarda los cambios de estado de las cuentas y las firmas correspondientes.
 * 
 * T_BITACORA_EDOS_CUENTA_RETIRO
 *
 * @author  Maria C. Buendia
 * @version 1.0
 */
@Entity
@Table(name="T_BITACORA_EDOS_CUENTA_RETIRO")
@SequenceGenerator(name = "SEQ_BitEdosCuentaRetiro", sequenceName = "T_BITACORA_EDOS_CTARET_SEQ",allocationSize = 1,initialValue = 1)
public class BitacoraEstadosCuentaRetiro  implements Serializable {
		
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** identificador de cuenta */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BitEdosCuentaRetiro")
	@Column(name = "ID_BITACORA_EDOS_CTARET", nullable = false)
	private Long idBitacoraEdosCuenta;
	
	/** cuenta de retiro correspondiente a este detalle */
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_CUENTA_RETIRO", nullable = false)
	private CuentaRetiro cuentaRetiro;
	
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
	@Column(name = "FECHA_MODIFICACION")
	private Date fechaModificacion;
	
	/** firma de la cancelacion */
	@Column(name = "MODIFICACION_FIRMADA")
	private Clob modificacionFirmada;
	
	/** fecha cancelacion */
	@Column(name = "FECHA_CANCELACION")
	private Date fechaCancelacion;
	
	/** firma de la cancelacion */
	@Column(name = "CANCELACION_FIRMADA")
	private Clob cancelacionFirmada;
	
	/** creacion usuario*/
	@Column(name = "CREACION_USUARIO")
	private String creacionUsuario;

	/** creacion */
	@Column(name = "CREACION_SERIE")
	private String creacionSerie;      

	/** autorizacion usuario */
	@Column(name = "AUTORIZACION_USUARIO")
	private String autorizacionUsuario;
    	
	/** autorizacion */
	@Column(name = "AUTORIZACION_SERIE")
	private String autorizacionSerie;  
    	
	/** liberacion */
	@Column(name = "LIBERACION_USUARIO")
	private String liberacionUsuario;  
    	
	/** liberacion */
	@Column(name = "LIBERACION_SERIE")
	private String liberacionSerie;   
    	
	/** aprobacion */
	@Column(name = "APROBACION_USUARIO")
	private String aprobacionUsuario;  
    	
	/** aprobacion */
	@Column(name = "APROBACION_SERIE")
	private String aprobacionSerie;    
    	
	/** modificacion */
	@Column(name = "MODIFICACION_USUARIO")
	private String modificacionUsuario;
    	
	/** modificacion */
	@Column(name = "MODIFICACION_SERIE")
	private String modificacionSerie;  
    	
	/** cancelacion */
	@Column(name = "CANCELACION_USUARIO")
	private String cancelacionUsuario; 
    	
	/** cancelacion */
	@Column(name = "CANCELACION_SERIE")
	private String cancelacionSerie;   
		
	public CuentaRetiro getCuentaRetiro() {
		return cuentaRetiro;
	}

	public void setCuentaRetiro(CuentaRetiro cuentaRetiro) {
		this.cuentaRetiro = cuentaRetiro;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
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

	public Date getFechaCancelacion() {
		return fechaCancelacion;
	}

	public void setFechaCancelacion(Date fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}

	public Clob getCancelacionFirmada() {
		return cancelacionFirmada;
	}

	public void setCancelacionFirmada(Clob cancelacionFirmada) {
		this.cancelacionFirmada = cancelacionFirmada;
	}

	public Long getIdBitacoraEdosCuenta() {
		return idBitacoraEdosCuenta;
	}

	public void setIdBitacoraEdosCuenta(Long idBitacoraEdosCuenta) {
		this.idBitacoraEdosCuenta = idBitacoraEdosCuenta;
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

	public String getCreacionUsuario() {
		return creacionUsuario;
	}

	public void setCreacionUsuario(String creacionUsuario) {
		this.creacionUsuario = creacionUsuario;
	}

	public String getCreacionSerie() {
		return creacionSerie;
	}

	public void setCreacionSerie(String creacionSerie) {
		this.creacionSerie = creacionSerie;
	}

	public String getAutorizacionUsuario() {
		return autorizacionUsuario;
	}

	public void setAutorizacionUsuario(String autorizacionUsuario) {
		this.autorizacionUsuario = autorizacionUsuario;
	}

	public String getAutorizacionSerie() {
		return autorizacionSerie;
	}

	public void setAutorizacionSerie(String autorizacionSerie) {
		this.autorizacionSerie = autorizacionSerie;
	}

	public String getLiberacionUsuario() {
		return liberacionUsuario;
	}

	public void setLiberacionUsuario(String liberacionUsuario) {
		this.liberacionUsuario = liberacionUsuario;
	}

	public String getLiberacionSerie() {
		return liberacionSerie;
	}

	public void setLiberacionSerie(String liberacionSerie) {
		this.liberacionSerie = liberacionSerie;
	}

	public String getAprobacionUsuario() {
		return aprobacionUsuario;
	}

	public void setAprobacionUsuario(String aprobacionUsuario) {
		this.aprobacionUsuario = aprobacionUsuario;
	}

	public String getAprobacionSerie() {
		return aprobacionSerie;
	}

	public void setAprobacionSerie(String aprobacionSerie) {
		this.aprobacionSerie = aprobacionSerie;
	}

	public String getModificacionUsuario() {
		return modificacionUsuario;
	}

	public void setModificacionUsuario(String modificacionUsuario) {
		this.modificacionUsuario = modificacionUsuario;
	}

	public String getModificacionSerie() {
		return modificacionSerie;
	}

	public void setModificacionSerie(String modificacionSerie) {
		this.modificacionSerie = modificacionSerie;
	}

	public String getCancelacionUsuario() {
		return cancelacionUsuario;
	}

	public void setCancelacionUsuario(String cancelacionUsuario) {
		this.cancelacionUsuario = cancelacionUsuario;
	}

	public String getCancelacionSerie() {
		return cancelacionSerie;
	}

	public void setCancelacionSerie(String cancelacionSerie) {
		this.cancelacionSerie = cancelacionSerie;
	}
}
