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
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Esta clase se utiliza para persistir en Base de Datos la instrucci&oacute;n
 * de efectivo procesada por el M&oacute;dulo de Operaci&oacute;n de Efectivo
 * (MOS).
 * <p>
 * Tabla: <i>T_INSTRUCCION_EFECTIVO</i> Secuencia:
 * <i>SEQ_T_INSTRUCCION_EFECTIVO</i>
 * 
 * @author Abraham Resendiz, apalacios
 * @since 1.0
 */
@Entity
@Table(name = "T_INSTRUCCION_EFECTIVO")
@NamedQuery(name = "InstruccionEfectivo.getIdInstruccionEfectivo", query = "FROM InstruccionEfectivo AS a WHERE a.id = :id")
public class InstruccionEfectivo implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Id de la Instrucci&oacute;n en Efectivo
	 */
	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_INSTRUCCION_EFECTIVO")
//	@Column(name = "ID_INSTRUCCION_EFECTIVO", updatable = false, insertable = false)
	private BigInteger id;

	/**
	 * Instrucci&oacute;n Traspasante
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_INSTITUCION_TRASPASANTE", updatable = true, insertable = true)
	private Institucion institucionTraspasante;
	

	

	/**
	 * Instrucci&oacute;n Receptora
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_INSTITUCION_RECEPTORA", updatable = true, insertable = true)
	private Institucion institucionReceptora;

	/**
	 * Cuenta Traspasante
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUENTA_TRASPASANTE", updatable = true, insertable = true)
	private CuentaNombrada cuentaTraspasante;

	/**
	 * Cuenta Receptora
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUENTA_RECEPTORA", updatable = true, insertable = true)
	private CuentaNombrada cuentaReceptora;

	/**
	 * Id de la Divisa
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DIVISA", updatable = true, insertable = true)
	private Divisa divisa;

	/**
	 * Id de la Boveda
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_BOVEDA", updatable = true, insertable = true)
	private Boveda boveda;

	/**
	 * Id del Tipo de instrucci&oacute;n
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_INSTRUCCION", updatable = true, insertable = true)
	private TipoInstruccion tipoInstruccion;

	/**
	 * Tipo Liquidacion
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_LIQ_INI", updatable = true, insertable = true)
	private TipoLiquidacion tipoLiquidacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_LIQ_VEN", updatable = true, insertable = true)
	private TipoLiquidacion liquidacionVencimiento;
	
	
	/**
	 * Id tipo mensaje
	 */
	@Column(name = "ID_TIPO_MENSAJE")
	private Integer idTipoMensaje;
	/**
	 * tipo de mensaje
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_MENSAJE", updatable = false, insertable = false)
	private TipoMensajeCat tipoMensajeCat;

	/**
	 * Monto
	 */
	@Column(name = "MONTO")
	private BigDecimal monto;
	
	
	@Column(name = "MONTO_INTERES")
	private BigDecimal montoVencimiento;
	
	
	@Column(name = "TASA_INTERES")
	private Double tasa;

	/**
	 * Fecha de liquidaci&oacute;n
	 */
	@Column(name = "FECHA_LIQUIDACION")
	private Date fechaLiquidacion;

	@Column(name = "FECHA_VENCIMIENTO")
	private Date fechaVencimiento;

	@Column(name = "FECHA_APLICACION")
	private Date fechaAplicacion;	

	@Column(name = "FECHA_REGISTRO")
	private Date fechaRegistro;

	/**
	 * Clabe
	 */
	@Column(name = "CLABE")
	private String clabe;

	/**
	 * nombreBeneficiario
	 */
	@Column(name = "NOMBRE_BENEFICIARIO")
	private String nombreBeneficiario;
	/**
	 * Referencia del mensaje
	 */
	// @Column(name = "REFERENCIA_MENSAJE")
	@Transient
	private BigInteger referenciaMensaje;

	/**
	 * Referencia del mensaje
	 */
	@Column(name = "REFERENCIA_OPERACION")
	private String referenciaOperacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ESTADO_INSTRUCCION", updatable = false, insertable = false)
	private EstadoInstruccionCat estadoInstruccion;

	@Column(name = "CONCEPTO")
	private String concepto;
	
	@Column(name = "CLAVE_RASTREO")
	private String claveRastreo;
	
	
	@Column(name = "REFERENCIA_NUMERICA")
	private String referenciaNumerica;	
	@Column(name = "PLAZO")
	private Integer plazo;

	

	/** El folio de la instrucción asociada a esta instrucción de efectivo */
//	@Id
//	@Column(name = "FOLIO_INSTRUCCION")
	@Column(name = "FOLIO_INSTRUCCION", updatable = false, insertable = false)
	private BigInteger folioInstruccion;

	@Column(name = "FOLIO_INST_LIQUIDACION", updatable = false, insertable = false)
	private BigInteger folioInstLiquidacion;

	/**
	 * Origen
	 */
	@Column(name = "ORIGEN")
	private String origen;

	/**
	 * Tipo Retiro (SIAC/SPEI)
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_RETIRO", updatable = false, insertable = false)	
	private TipoRetiro tipoRetiro;

	/**
	 * Mensaje recibido
	 */
	// @Lob
	// @Column(name = "MENSAJE_RECIBIDO")
	// private String mensajeRecibido;
	/**
	 * Fecha de recepci&oacute;n
	 */
	// @Column(name = "FECHA_RECEPCION")
	// private Date fechaRecepcion;

	/**
	 * Instrucción asociada a este detalle de miscelanea fiscal.
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_PAGO")
	private TipoPago tipoPago;

	/**
	 * El identificador del tipo de pago.
	 */
	@Column(name = "ID_TIPO_PAGO", updatable = false, insertable = false)
	private Integer idTipoPago;

	/** La instrucción liquidación ligada a esta instrucción de efectivo
	@OneToOne(mappedBy = "instruccionEfectivo", fetch=FetchType.LAZY)
	private InstruccionLiquidacion instruccionLiquidacion; */
	
	/** Descripcion del estado de la operacion, no del catalogo, descricion detallada */
	@Column(name = "DESCRIPCION_ESTADO")
	private String descripcionEstado;
	
	/** folio del origen del retiro 12001 son las que van desde el portal*/
	@Column(name = "ID_FOLIO_ORIGEN")
	private String folioOrigen;
	
	@Column(name = "ID_INSTITUCION_ORIGEN")
	private Integer idInstitucionOrigen;

	@Column(name = "REFERENCIA_PARTICIPANTE")
	private BigInteger referenciaPartipante;	

	@Column(name = "LIQ_SPEI")
	private Integer liqSpei;	
	
	/**
	 * nuevos campos para manejo por paquete
	 **/
	
	@Column(name = "REFERENCIA_PAQUETE")
	private String referenciaPaquete;
	
	@Column(name = "TOTAL_OPERACIONES_PAQUETE")
	private String totalOperacionesPaquete;
	
	@Column(name = "NUMERO_OPERACION_PAQUETE")
	private String numeroOperacionPaquete;
	
	@Column(name = "TOTAL_TITULOS_PAQUETE")
	private String totalTitulosPaquete;
	
	@Column(name = "TOTAL_IMPORTE_PAQUETE")
	private String totalImportePaquete;
	
	public Integer getLiqSpei() {
		return liqSpei;
	}

	public void setLiqSpei(Integer liqSpei) {
		this.liqSpei = liqSpei;
	}

	public TipoRetiro getTipoRetiro() {
		return tipoRetiro;
	}

	public void setTipoRetiro(TipoRetiro tipoRetiro) {
		this.tipoRetiro = tipoRetiro;
	}


	/**
	 * Regresa el valor de la propiedad <code>id</code>.
	 * 
	 * @return id. El id de la instruccion en efectivo
	 */
	public BigInteger getId() {
		return id;
	}

	/**
	 * Asigna la propiedad <code>id</code>.
	 * 
	 * @param id
	 *            El id de la instruccion en efectivo
	 */
	public void setId(BigInteger id) {
		this.id = id;
	}

	/**
	 * Obtiene el valor del atributo folioInstruccion
	 * 
	 * @return el valor del atributo folioInstruccion
	 */
	public BigInteger getFolioInstruccion() {
		return folioInstruccion;
	}

	/**
	 * Regresa el tipo de Pago para Operaciones de Deposito
	 * 
	 * @return
	 */
	public TipoPago getTipoPago() {
		return tipoPago;
	}

	/**
	 * Asigna Tipo de Pago apara Operaciones de Deposito
	 * 
	 * @param tipoPago
	 */
	public void setTipoPago(TipoPago tipoPago) {
		this.tipoPago = tipoPago;
	}

	/**
	 * Obtiene el valor del atributo idTipoPago
	 * 
	 * @return el valor del atributo idTipoPago
	 */
	public Integer getIdTipoPago() {
		return idTipoPago;
	}

	/**
	 * Establece el valor del atributo idTipoPago
	 * 
	 * @param idTipoPago
	 *            el valor del atributo idTipoPago a establecer
	 */
	public void setIdTipoPago(Integer idTipoPago) {
		this.idTipoPago = idTipoPago;
	}

	/**
	 * Establece el valor del atributo folioInstruccion
	 * 
	 * @param folioInstruccion
	 *            el valor del atributo folioInstruccion a establecer
	 */
	public void setFolioInstruccion(BigInteger folioInstruccion) {
		this.folioInstruccion = folioInstruccion;
	}

	/**
	 * Obtiene el valor del atributo instruccionLiquidacion
	 * 
	 * @return el valor del atributo instruccionLiquidacion
	 
	public InstruccionLiquidacion getInstruccionLiquidacion() {
		return instruccionLiquidacion;
	}*/

	/**
	 * Establece el valor del atributo instruccionLiquidacion
	 * 
	 * @param instruccionLiquidacion
	 *            el valor del atributo instruccionLiquidacion a establecer
	 
	public void setInstruccionLiquidacion(InstruccionLiquidacion instruccionLiquidacion) {
		this.instruccionLiquidacion = instruccionLiquidacion;
	}*/

	/**
	 * Regresa el valor de la propiedad <code>institucionTraspasante</code>.
	 * 
	 * @return institucionTraspasante. El id de la institucion traspasante
	 */
	public Institucion getInstitucionTraspasante() {
		return institucionTraspasante;
	}

	/**
	 * Asigna la propiedad <code>institucionTraspasante</code>.
	 * 
	 * @param institucionTraspasante
	 *            El id de la institucion traspasante
	 */
	public void setInstitucionTraspasante(Institucion institucionTraspasante) {
		this.institucionTraspasante = institucionTraspasante;
	}

	/**
	 * Regresa el valor de la propiedad <code>institucionReceptora</code>.
	 * 
	 * @return institucionReceptora. El id de la institucion receptora
	 */
	public Institucion getInstitucionReceptora() {
		return institucionReceptora;
	}

	/**
	 * Asigna la propiedad <code>institucionReceptora</code>.
	 * 
	 * @param institucionReceptora
	 *            El id de la institucion receptora
	 */
	public void setInstitucionReceptora(Institucion institucionReceptora) {
		this.institucionReceptora = institucionReceptora;
	}

	/**
	 * Regresa el valor de la propiedad <code>cuentaTraspasante</code>.
	 * 
	 * @return cuentaTraspasante. El id de la cuenta traspasante
	 */
	public CuentaNombrada getCuentaTraspasante() {
		return cuentaTraspasante;
	}

	/**
	 * Asigna la propiedad <code>cuentaTraspasante</code>.
	 * 
	 * @param cuentaTraspasante
	 *            El id de la cuenta traspasante
	 */
	public void setCuentaTraspasante(CuentaNombrada cuentaTraspasante) {
		this.cuentaTraspasante = cuentaTraspasante;
	}

	/**
	 * Regresa el valor de la propiedad <code>cuentaTraspasante</code>.
	 * 
	 * @return cuentaReceptora. El id de la cuenta receptora
	 */
	public CuentaNombrada getCuentaReceptora() {
		return cuentaReceptora;
	}

	/**
	 * Asigna la propiedad <code>cuentaTraspasante</code>.
	 * 
	 * @param cuentaReceptora
	 *            El id de la cuenta receptora
	 */
	public void setCuentaReceptora(CuentaNombrada cuentaReceptora) {
		this.cuentaReceptora = cuentaReceptora;
	}

	/**
	 * Regresa el valor de la propiedad <code>divisa</code>.
	 * 
	 * @return divisa. El id de la divisa
	 */

	public Divisa getDivisa() {
		return divisa;
	}

	/**
	 * Asigna la propiedad <code>divisa</code>.
	 * 
	 * @param divisa
	 *            El id de la divisa
	 */
	public void setDivisa(Divisa divisa) {
		this.divisa = divisa;
	}

	/**
	 * Regresa el valor de la propiedad <code>boveda</code>.
	 * 
	 * @return boveda. EL id de la boveda
	 */
	public Boveda getBoveda() {
		return boveda;
	}

	/**
	 * Asigna la propiedad <code>boveda</code>.
	 * 
	 * @param boveda
	 *            EL id de la boveda
	 */
	public void setBoveda(Boveda boveda) {
		this.boveda = boveda;
	}

	/**
	 * Regresa el valor de la propiedad <code>tipoInstruccion</code>.
	 * 
	 * @return tipoInstruccion. El id del tipo de instruccion
	 */
	public TipoInstruccion getTipoInstruccion() {
		return tipoInstruccion;
	}

	/**
	 * Asigna la propiedad <code>tipoInstruccion</code>.
	 * 
	 * @param tipoInstruccion
	 *            El id del tipo de instruccion
	 */
	public void setTipoInstruccion(TipoInstruccion tipoInstruccion) {
		this.tipoInstruccion = tipoInstruccion;
	}

	/**
	 * Regresa el valor de la propiedad <code>monto</code>.
	 * 
	 * @return monto. El monto
	 */
	public BigDecimal getMonto() {
		return monto;
	}

	/**
	 * Asigna la propiedad <code>monto</code>.
	 * 
	 * @param monto
	 *            El monto
	 */
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
	
	public BigDecimal getMontoVencimiento() {
		return montoVencimiento;
	}

	public void setMontoVencimiento(BigDecimal montoVencimiento) {
		this.montoVencimiento = montoVencimiento;
	}

	public Double getTasa() {
		return tasa;
	}

	public void setTasa(Double tasa) {
		this.tasa = tasa;
	}

	/**
	 * Regresa el valor de la propiedad <code>fechaLiquidacion</code>.
	 * 
	 * @return fechaLiquidacion. La fecha de liquidacion
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	/**
	 * Asigna la propiedad <code>fechaLiquidacion</code>.
	 * 
	 * @param fechaLiquidacion
	 *            La fecha de liquidacion
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	/**
	 * Regresa el valor de la propiedad <code>clabe</code>.
	 * 
	 * @return clabe. La clabe
	 */
	public String getClabe() {
		return clabe;
	}

	/**
	 * Asigna la propiedad <code>clabe</code>.
	 * 
	 * @param clabe
	 *            La clabe
	 */
	public void setClabe(String clabe) {
		this.clabe = clabe;
	}

	/**
	 * Regresa el valor de la propiedad <code>referenciaMensaje</code>.
	 * 
	 * @return referenciaMensaje. La referenciaMensaje
	 */
	public BigInteger getReferenciaMensaje() {
		return referenciaMensaje;
	}

	/**
	 * Asigna la propiedad <code>referenciaOperacion</code>.
	 * 
	 * @param referenciaOperacion
	 *            La referenciaOperacion
	 */
	public void setReferenciaOperacion(String referenciaOperacion) {
		this.referenciaOperacion = referenciaOperacion;
	}

	/**
	 * Regresa el valor de la propiedad <code>referenciaOperacion</code>.
	 * 
	 * @return referenciaOperacion. La referenciaOperacion
	 */
	public String getReferenciaOperacion() {
		return referenciaOperacion;
	}

	/**
	 * Asigna la propiedad <code>referencia</code>.
	 * 
	 * @param referenciaMensaje
	 *            La referencia
	 */
	public void setReferenciaMensaje(BigInteger referenciaMensaje) {
		this.referenciaMensaje = referenciaMensaje;
	}

	/**
	 * Regresa el valor de la propiedad <code>idTipoMensaje</code>.
	 * 
	 * @return idTipoMensaje
	 */
	public Integer getIdTipoMensaje() {
		return idTipoMensaje;
	}

	/**
	 * Asigna la propiedad <code>idTipoMensaje</code>.
	 * 
	 * @param idTipoMensaje
	 */
	public void setIdTipoMensaje(Integer idTipoMensaje) {
		this.idTipoMensaje = idTipoMensaje;
	}

	/**
	 * Regresa el valor de la propiedad <code>idInstitucionTraspasante</code>.
	 * 
	 * @return idInstitucionTraspasante
	 */
	public BigInteger getIdInstitucionTraspasante() {
		return institucionTraspasante.getIdInstitucion();
	}

	/**
	 * Regresa el valor de la propiedad <code>idInstitucionReceptora</code>.
	 * 
	 * @return idInstitucionReceptora
	 */
	public BigInteger getIdInstitucionReceptora() {
		return institucionReceptora.getIdInstitucion();
	}

	/**
	 * Regresa el valor de la propiedad <code>idCuentaTraspasante</code>.
	 * 
	 * @return idCuentaTraspasante
	 */
	public BigInteger getIdCuentaTraspasante() {
		return cuentaTraspasante.getIdCuentaNombrada();
	}

	/**
	 * Regresa el valor de la propiedad <code>idCuentaReceptora</code>.
	 * 
	 * @return idCuentaReceptora
	 */
	public BigInteger getIdCuentaReceptora() {
		return cuentaReceptora.getIdCuentaNombrada();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof InstruccionEfectivo)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		InstruccionEfectivo rhs = (InstruccionEfectivo) obj;
		return new EqualsBuilder().append(Double.parseDouble(id.toString()), rhs.getId()).isEquals();
	}

	/**
	 * Obtiene el campo tipoMensajeCat
	 * 
	 * @return tipoMensajeCat
	 */
	public TipoMensajeCat getTipoMensajeCat() {
		return tipoMensajeCat;
	}

	/**
	 * Asigna el campo tipoMensajeCat
	 * 
	 * @param tipoMensajeCat
	 *            el valor de tipoMensajeCat a asignar
	 */
	public void setTipoMensajeCat(TipoMensajeCat tipoMensajeCat) {
		this.tipoMensajeCat = tipoMensajeCat;
	}

	/**
	 * @param estadoInstruccion
	 *            the estadoInstruccion to set
	 */
	public void setEstadoInstruccion(EstadoInstruccionCat estadoInstruccion) {
		this.estadoInstruccion = estadoInstruccion;
	}

	/**
	 * @return the estadoInstruccion
	 */
	public EstadoInstruccionCat getEstadoInstruccion() {
		return estadoInstruccion;
	}

	/**
	 * @param origen
	 *            the origen to set
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * @return the origen
	 */
	public String getOrigen() {
		return origen;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public String getReferenciaNumerica() {
		return referenciaNumerica;
	}
	public void setReferenciaNumerica(String referenciaNumerica) {
		this.referenciaNumerica = referenciaNumerica;
	}
	
	public Integer getPlazo() {
		return plazo;
	}
	public void setPlazo(Integer plazo) {
		this.plazo = plazo;
	}

	public TipoLiquidacion getTipoLiquidacion() {
		return tipoLiquidacion;
	}

	public void setTipoLiquidacion(TipoLiquidacion tipoLiquidacion) {
		this.tipoLiquidacion = tipoLiquidacion;
	}

	public TipoLiquidacion getLiquidacionVencimiento() {
		return liquidacionVencimiento;
	}

	public void setLiquidacionVencimiento(TipoLiquidacion liquidacionVencimiento) {
		this.liquidacionVencimiento = liquidacionVencimiento;
	}

	public String getDescripcionEstado() {
		return descripcionEstado;
	}

	public void setDescripcionEstado(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}

	public String getFolioOrigen() {
		return folioOrigen;
	}

	public void setFolioOrigen(String folioOrigen) {
		this.folioOrigen = folioOrigen;
	}

	public String getNombreBeneficiario() {
		return nombreBeneficiario;
	}
	public void setNombreBeneficiario(String nombreBeneficiario) {
		this.nombreBeneficiario = nombreBeneficiario;
	}
	public String getClaveRastreo() {
		return claveRastreo;
	}

	public void setClaveRastreo(String claveRastreo) {
		this.claveRastreo = claveRastreo;
	}
	
	public Date getFechaAplicacion() {
		return fechaAplicacion;
	}

	public void setFechaAplicacion(Date fechaAplicacion) {
		this.fechaAplicacion = fechaAplicacion;
	}
	
	public String getReferenciaPaquete() {
		return referenciaPaquete;
	}

	public void setReferenciaPaquete(String referenciaPaquete) {
		this.referenciaPaquete = referenciaPaquete;
	}

	public String getTotalOperacionesPaquete() {
		return totalOperacionesPaquete;
	}

	public void setTotalOperacionesPaquete(String totalOperacionesPaquete) {
		this.totalOperacionesPaquete = totalOperacionesPaquete;
	}

	public String getNumeroOperacionPaquete() {
		return numeroOperacionPaquete;
	}

	public void setNumeroOperacionPaquete(String numeroOperacionPaquete) {
		this.numeroOperacionPaquete = numeroOperacionPaquete;
	}

	public String getTotalTitulosPaquete() {
		return totalTitulosPaquete;
	}

	public void setTotalTitulosPaquete(String totalTitulosPaquete) {
		this.totalTitulosPaquete = totalTitulosPaquete;
	}

	public String getTotalImportePaquete() {
		return totalImportePaquete;
	}

	public void setTotalImportePaquete(String totalImportePaquete) {
		this.totalImportePaquete = totalImportePaquete;
	}

	public BigInteger getFolioInstLiquidacion() {
		return folioInstLiquidacion;
	}

	public void setFolioInstLiquidacion(BigInteger folioInstLiquidacion) {
		this.folioInstLiquidacion = folioInstLiquidacion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Integer getIdInstitucionOrigen() {
		return idInstitucionOrigen;
	}

	public void setIdInstitucionOrigen(Integer idInstitucionOrigen) {
		this.idInstitucionOrigen = idInstitucionOrigen;
	}

	public BigInteger getReferenciaPartipante() {
		return referenciaPartipante;
	}

	public void setReferenciaPartipante(BigInteger referenciaPartipante) {
		this.referenciaPartipante = referenciaPartipante;
	}

}
