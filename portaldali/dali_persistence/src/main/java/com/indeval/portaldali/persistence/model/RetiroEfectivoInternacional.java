/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * Cata&acute;logo con los campos para los retiros de efectivo internacionales 
 * 
 * C_RETIRO_EFECTIVO_INT
 *
 * @author  Maria C. Buendia
 * @version 1.0
 */
@Entity
@Table(name="C_RETIRO_EFECTIVO_INT")
@SequenceGenerator(name = "SEQ_RetiroEfectivoInt", sequenceName = "C_RETIRO_EFECTIVO_INT_SEQ",allocationSize = 1,initialValue = 1)
public class RetiroEfectivoInternacional implements Serializable {
		
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** identificador de cuenta */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RetiroEfectivoInt")
	@Column(name = "ID_RETIRO_EFECTIVO_INT", nullable = false)
	private Long idRetiroEfectivoInt;
	
	/** estado de la cuenta */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ESTADO", nullable = false)	
	private EstadoInstruccionCat estado;
	
	/** divisa de la cuenta */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DIVISA", nullable = false)
	private Divisa divisa;
	
	/** institucion que crea la cuenta */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_INSTITUCION", nullable = false)	
	private Institucion institucion;
	
	/** boveda correspondiente */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_BOVEDA", nullable = false)
	private Boveda boveda;
	
	/** cuenta relacionada al retiro*/
	@Column(name = "ID_CTA_BENEFICIARIO", nullable = false)
	private Long idCuentaBeneficiario;
	
	/** importe traspaso */
	@Column(name = "IMPORTE_TRASPASO")
	private BigDecimal importeTraspaso;
	
	/** fecha valor */
	@Column(name = "FECHA_VALOR")
	private Date fechaValor;	
	
	/** concepto de pago*/
	@Column(name = "CONCEPTO_PAGO")
	private String conceptoPago;
	
	/** fecha creacion */
	@Column(name = "FECHA_CREACION")
	private Date fechaCreacion;
	
	/** firma de la creacion */
	@Column(name = "CREACION_FIRMADA")
	private Clob creacionFirmada;
	
	/** fecha aprobacion */
	@Column(name = "FECHA_AUTORIZACION")
	private Date fechaAutorizacion;
	
	/** firma de la aprobacion */
	@Column(name = "AUTORIZACION_FIRMADA")
	private Clob autorizacionFirmada;	

	/** fecha liberacion */
	@Column(name = "FECHA_LIBERACION")
	private Date fechaLiberacion;
	
	/** firma de la liberacion */
	@Column(name = "LIBERACION_FIRMADA")
	private Clob liberacionFirmada;
	
	/** fecha envio a PFI */
	@Column(name = "FECHA_ENVIO")
	private Date fechaEnvio;

	/** referencia operacion*/
	@Column(name = "REFER_OPERACION")
	private String referenciaOperacion;
	
	/** referencia operacion*/
	@Column(name = "REFER_MENSAJE")
	private String referenciaMensaje;
	
	/** usuario de creacion*/
	@Column(name = "CREACION_USUARIO")
	private String usuarioCreacion;

	/** serie de creacion*/
	@Column(name = "CREACION_SERIE")
	private String serieCreacion;

	/** usuario de autorizacion*/
	@Column(name = "AUTORIZACION_USUARIO")
	private String usuarioAutorizacion;

	/** serie de autorizacion*/
	@Column(name = "AUTORIZACION_SERIE")
	private String serieAutorizacion;
	
	/** usuario de liberacion*/
	@Column(name = "LIBERACION_USUARIO")
	private String usuarioLiberacion;

	/** serie de liberacion*/
	@Column(name = "LIBERACION_SERIE")
	private String serieLiberacion;
	
	public Divisa getDivisa() {
		return divisa;
	}

	public void setDivisa(Divisa divisa) {
		this.divisa = divisa;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public Boveda getBoveda() {
		return boveda;
	}

	public void setBoveda(Boveda boveda) {
		this.boveda = boveda;
	}

	public BigDecimal getImporteTraspaso() {
		return importeTraspaso;
	}

	public void setImporteTraspaso(BigDecimal importeTraspaso) {
		this.importeTraspaso = importeTraspaso;
	}

	public Date getFechaValor() {
		return fechaValor;
	}

	public void setFechaValor(Date fechaValor) {
		this.fechaValor = fechaValor;
	}

	public String getConceptoPago() {
		return conceptoPago;
	}

	public void setConceptoPago(String conceptoPago) {
		this.conceptoPago = conceptoPago;
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

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public EstadoInstruccionCat getEstado() {
		return estado;
	}

	public void setEstado(EstadoInstruccionCat estado) {
		this.estado = estado;
	}

	public Long getIdCuentaBeneficiario() {
		return idCuentaBeneficiario;
	}

	public void setIdCuentaBeneficiario(Long idCuentaBeneficiario) {
		this.idCuentaBeneficiario = idCuentaBeneficiario;
	}

	public Long getIdRetiroEfectivoInt() {
		return idRetiroEfectivoInt;
	}

	public void setIdRetiroEfectivoInt(Long idRetiroEfectivoInt) {
		this.idRetiroEfectivoInt = idRetiroEfectivoInt;
	}

	public String getReferenciaOperacion() {
		return referenciaOperacion;
	}

	public void setReferenciaOperacion(String referenciaOperacion) {
		this.referenciaOperacion = referenciaOperacion;
	}

	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public String getUsuarioAutorizacion() {
		return usuarioAutorizacion;
	}

	public void setUsuarioAutorizacion(String usuarioAutorizacion) {
		this.usuarioAutorizacion = usuarioAutorizacion;
	}

	public String getUsuarioLiberacion() {
		return usuarioLiberacion;
	}

	public void setUsuarioLiberacion(String usuarioLiberacion) {
		this.usuarioLiberacion = usuarioLiberacion;
	}

	public String getSerieCreacion() {
		return serieCreacion;
	}

	public void setSerieCreacion(String serieCreacion) {
		this.serieCreacion = serieCreacion;
	}

	public String getSerieAutorizacion() {
		return serieAutorizacion;
	}

	public void setSerieAutorizacion(String serieAutorizacion) {
		this.serieAutorizacion = serieAutorizacion;
	}

	public String getSerieLiberacion() {
		return serieLiberacion;
	}

	public void setSerieLiberacion(String serieLiberacion) {
		this.serieLiberacion = serieLiberacion;
	}

	public String getReferenciaMensaje() {
		return referenciaMensaje;
	}

	public void setReferenciaMensaje(String referenciaMensaje) {
		this.referenciaMensaje = referenciaMensaje;
	}


}
