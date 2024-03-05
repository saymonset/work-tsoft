/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * Mapeo de la tabla T_INSTRUCCION_OPERACION_VAL Tabla para persistir las
 * instrucciones de operación de valores una vez que llegan al MOV
 */

@Entity
@Table(name = "T_INSTRUCCION_OPERACION_VAL")
@SequenceGenerator(name = "SEQ_InstruccionOperacionVal", sequenceName = "T_INSTRUCCION_OPERACIO_VAL_SEQ")
@NamedQueries({
	@NamedQuery(name = "InstruccionOperacionVal.actualizaPlazoReporto",
			query = " UPDATE InstruccionOperacionVal " +
					" SET plazoReporto = :plazoReporto" +
					" WHERE idInstruccionOperacionVal = :idInstruccionOperacionVal")
})
public class InstruccionOperacionVal {
	@Id
	@Column(name = "ID_INSTRUCCION_OPERACION_VAL")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_InstruccionOperacionVal")
	private BigInteger idInstruccionOperacionVal;

	@Column(name = "ID_INSTITUCION_RECEPTORA")
	private BigInteger idInstitucionReceptora;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_INSTITUCION_RECEPTORA", updatable = false, insertable = false)
	private Institucion institucionReceptora;

	@Column(name = "ID_INSTITUCION_TRASPASANTE")
	private BigInteger idInstitucionTraspasante;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_INSTITUCION_TRASPASANTE", updatable = false, insertable = false)
	private Institucion institucionTraspasante;

	@Column(name = "ID_INSTITUCION_BANCO_TRABAJO")
	private BigInteger idInstitucionBancoTrabajo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_INSTITUCION_BANCO_TRABAJO", updatable = false, insertable = false)
	private Institucion institucionBancoTrabajo;

	@Column(name = "id_tipo_instruccion")
	private BigInteger idTipoInstruccion;

	/** El identificador del error de liquidación del DALI */
	@Column(name = "ID_ERROR_DALI")
	private BigInteger idErrorDali;

	/** El error de liquidación del DALI */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ERROR_DALI", updatable = false, insertable = false)
	private ErrorDali errorDali;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_instruccion", updatable = false, insertable = false)
	private TipoInstruccion tipoInstruccion;

	@Column(name = "plazo_reporto")
	private BigInteger plazoReporto;

	/** El identificador de la emisión pendiente */
	@Column(name = "ID_EMISION_PENDIENTE")
	private BigInteger idEmisionPendiente;

	/** La emisión pendiente de la instrucción */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EMISION_PENDIENTE", updatable = false, insertable = false)
	private EmisionPendiente emisionPendiente;

	// @Column (name = "origen_receptor")
	// private String origenReceptor;

	/*
	 * @Column (name = "id_emision_pendiente") private BigInteger
	 * idEmisionPendiente;
	 */

	/*
	 * @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "ID_PLAZO",
	 * updatable = false, insertable = false) private Plazo plazo;
	 */
	@Column(name = "ID_CUENTA_NOMBRADA_RECEPTORA")
	private BigInteger idCuentaNombradaReceptora;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUENTA_NOMBRADA_RECEPTORA", updatable = false, insertable = false)
	private CuentaNombrada cuentaNombradaReceptora;

	@Column(name = "ID_CUENTA_NOMBRADA_TRASPASANTE")
	private BigInteger idCuentaNombradaTraspasante;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUENTA_NOMBRADA_TRASPASANTE", updatable = false, insertable = false)
	private CuentaNombrada cuentaNombradaTraspasante;

	@Column(name = "ID_CUENTA_BANCO_TRABAJO")
	private BigInteger idCuentaBancoTrabajo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUENTA_BANCO_TRABAJO", updatable = false, insertable = false)
	private CuentaNombrada cuentaNombradaBancoTrabajo;

	@Column(name = "id_tipo_mensaje")
	private BigInteger idTipoMensaje;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_mensaje", updatable = false, insertable = false)
	private TipoMensajeCat tipoMensajeCat = null;

	@Column(name = "id_boveda")
	private BigInteger idBoveda;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_boveda", updatable = false, insertable = false, nullable = true)
	private Boveda boveda;
	
	@Column(name = "id_boveda_efectivo")
	private BigInteger idBovedaEfectivo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_boveda_efectivo", updatable = false, insertable = false, nullable = true)
	private Boveda bovedaEfectivo;

	@Column(name = "id_estado_instruccion")
	private BigInteger idEstadoInstruccion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estado_instruccion", updatable = false, insertable = false)
	private EstadoInstruccionCat estadoInstruccionCat = null;

	@Column(name = "ID_CUPON")
	private BigInteger idCupon;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUPON", updatable = false, insertable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private Cupon cupon;

	@Column(name = "id_estado_instruccion_envio")
	private BigInteger idEstadoInstruccionEnvio;

	@Column(name = "id_divisa")
	private BigInteger idDivisa;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_divisa", updatable = false, insertable = false, nullable = true)
	private Divisa divisa;

	@Column(name = "precio_titulo")
	private BigDecimal precioTitulo;

	@Column(name = "tasa_negociada")
	private BigDecimal tasaNegociada;

	@Column(name = "cantidad_titulos")
	private BigInteger cantidadTitulos;

	@Column(name = "importe")
	private BigDecimal importe;

	@Column(name = "fecha_liquidacion")
	private Date fechaLiquidacion;

	@Column(name = "fecha_concertacion")
	private Date fechaConcertacion;

	@Column(name = "folio_instruccion_traspasante")
	private String folioInstruccionTraspasante;

	@Column(name = "folio_instruccion_receptora")
	private String folioInstruccionReceptora;

	@Column(name = "folio_control")
	private BigInteger folioControl;

	@Column(name = "requiere_match")
	private Integer requiereMatch;

	@Column(name = "id_instruccion_anterior")
	private BigInteger idInstruccionAnterior;

	@Column(name = "id_instruccion_siguiente")
	private BigInteger idInstruccionSiguiente;

	@Lob
	@Column(name = "mensaje_recibido")
	private String mensajeRecibido;

	// @Column (name = "mensaje_enviado")
	// private Clob mensajeEnviado;

	@Column(name = "origen")
	private String origen;

	@Column(name = "ID_TASA_REFERENCIA")
	private BigInteger tasaReferencia;

	@Column(name = "INTERESES_GENERADOS")
	private BigDecimal interesesGenerados;

	@Column(name = "FECHA_INTERESES")
	private Date fechaIntereses;

	/**
	 * 
	 * operaci&oacute;n.
	 */
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_INSTRUCCION_OPERACION_VAL")
	private Set<InstruccionMovPosicionNom> InstruccionesMovPosicionNom;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "FOLIO_INSTRUCCION_LIQUIDACION")
	private Set<InstruccionLiquidacion> instruccionesLiquidacion;
	
	/**
	 * Las parcialidades relacionadas a esta instrucción
	 */
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_INSTRUCCION_OPERACION_VAL")
	private Set<LiquidacionParcialInstruccionMov> parcialidadesLiquidacion;
	
	/**
	 * Los datos extra para las instrucciones de miscelanea fiscal.
	 */
	@OneToOne(mappedBy = "instruccionOperacionVal")
	private OperacionMiscelaneaFiscal operacionMiscelaneaFiscal;

	
	/**
	 * nuevos campos para manejo por paquete
	 **/
	
	@Column(name ="REFERENCIA_PAQUETE")
	private String referenciaPaquete;
	
	@Column(name ="TOTAL_OPERACIONES_PAQUETE")
	private String totalOperacionesPaquete;
	
	@Column(name ="NUMERO_OPERACION_PAQUETE")
	private String numeroOperacionPaquete;
	
	@Column(name ="TOTAL_TITULOS_PAQUETE")
	private String totalTitulosPaquete;
	
	@Column(name ="TOTAL_IMPORTE_PAQUETE")
	private String totalImportePaquete;
	
	/**
	 * Obtiene el valor del atributo operacionMiscelaneaFiscal
	 * 
	 * @return el valor del atributo operacionMiscelaneaFiscal
	 */
	public OperacionMiscelaneaFiscal getOperacionMiscelaneaFiscal() {
		return operacionMiscelaneaFiscal;
	}

	/**
	 * Establece el valor del atributo operacionMiscelaneaFiscal
	 * 
	 * @param operacionMiscelaneaFiscal
	 *            el valor del atributo operacionMiscelaneaFiscal a establecer
	 */
	public void setOperacionMiscelaneaFiscal(OperacionMiscelaneaFiscal operacionMiscelaneaFiscal) {
		this.operacionMiscelaneaFiscal = operacionMiscelaneaFiscal;
	}

	/**
	 * Obtiene el valor del atributo idInstruccionOperacionVal
	 * 
	 * @return el valor del atributo idInstruccionOperacionVal
	 */
	public BigInteger getIdInstruccionOperacionVal() {
		return idInstruccionOperacionVal;
	}

	/**
	 * Establece el valor del atributo idInstruccionOperacionVal
	 * 
	 * @param idInstruccionOperacionVal
	 *            el valor del atributo idInstruccionOperacionVal a establecer.
	 */
	public void setIdInstruccionOperacionVal(BigInteger idInstruccionOperacionVal) {
		this.idInstruccionOperacionVal = idInstruccionOperacionVal;
	}

	/**
	 * Obtiene el valor del atributo idInstitucionReceptora
	 * 
	 * @return el valor del atributo idInstitucionReceptora
	 */
	public BigInteger getIdInstitucionReceptora() {
		return idInstitucionReceptora;
	}

	/**
	 * Establece el valor del atributo idInstitucionReceptora
	 * 
	 * @param idInstitucionReceptora
	 *            el valor del atributo idInstitucionReceptora a establecer.
	 */
	public void setIdInstitucionReceptora(BigInteger idInstitucionReceptora) {
		this.idInstitucionReceptora = idInstitucionReceptora;
	}

	/**
	 * Obtiene el valor del atributo institucionReceptora
	 * 
	 * @return el valor del atributo institucionReceptora
	 */
	public Institucion getInstitucionReceptora() {
		return institucionReceptora;
	}

	/**
	 * Establece el valor del atributo institucionReceptora
	 * 
	 * @param institucionReceptora
	 *            el valor del atributo institucionReceptora a establecer.
	 */
	public void setInstitucionReceptora(Institucion institucionReceptora) {
		this.institucionReceptora = institucionReceptora;
	}

	/**
	 * Obtiene el valor del atributo idInstitucionTraspasante
	 * 
	 * @return el valor del atributo idInstitucionTraspasante
	 */
	public BigInteger getIdInstitucionTraspasante() {
		return idInstitucionTraspasante;
	}

	/**
	 * Establece el valor del atributo idInstitucionTraspasante
	 * 
	 * @param idInstitucionTraspasante
	 *            el valor del atributo idInstitucionTraspasante a establecer.
	 */
	public void setIdInstitucionTraspasante(BigInteger idInstitucionTraspasante) {
		this.idInstitucionTraspasante = idInstitucionTraspasante;
	}

	/**
	 * Obtiene el valor del atributo institucionTraspasante
	 * 
	 * @return el valor del atributo institucionTraspasante
	 */
	public Institucion getInstitucionTraspasante() {
		return institucionTraspasante;
	}

	/**
	 * Establece el valor del atributo institucionTraspasante
	 * 
	 * @param institucionTraspasante
	 *            el valor del atributo institucionTraspasante a establecer.
	 */
	public void setInstitucionTraspasante(Institucion institucionTraspasante) {
		this.institucionTraspasante = institucionTraspasante;
	}

	/**
	 * Obtiene el valor del atributo idInstitucionBancoTrabajo
	 * 
	 * @return el valor del atributo idInstitucionBancoTrabajo
	 */
	public BigInteger getIdInstitucionBancoTrabajo() {
		return idInstitucionBancoTrabajo;
	}

	/**
	 * Establece el valor del atributo idInstitucionBancoTrabajo
	 * 
	 * @param idInstitucionBancoTrabajo
	 *            el valor del atributo idInstitucionBancoTrabajo a establecer.
	 */
	public void setIdInstitucionBancoTrabajo(BigInteger idInstitucionBancoTrabajo) {
		this.idInstitucionBancoTrabajo = idInstitucionBancoTrabajo;
	}

	/**
	 * Obtiene el valor del atributo institucionBancoTrabajo
	 * 
	 * @return el valor del atributo institucionBancoTrabajo
	 */
	public Institucion getInstitucionBancoTrabajo() {
		return institucionBancoTrabajo;
	}

	/**
	 * Establece el valor del atributo institucionBancoTrabajo
	 * 
	 * @param institucionBancoTrabajo
	 *            el valor del atributo institucionBancoTrabajo a establecer.
	 */
	public void setInstitucionBancoTrabajo(Institucion institucionBancoTrabajo) {
		this.institucionBancoTrabajo = institucionBancoTrabajo;
	}

	/**
	 * Obtiene el valor del atributo idTipoInstruccion
	 * 
	 * @return el valor del atributo idTipoInstruccion
	 */
	public BigInteger getIdTipoInstruccion() {
		return idTipoInstruccion;
	}

	/**
	 * Establece el valor del atributo idTipoInstruccion
	 * 
	 * @param idTipoInstruccion
	 *            el valor del atributo idTipoInstruccion a establecer.
	 */
	public void setIdTipoInstruccion(BigInteger idTipoInstruccion) {
		this.idTipoInstruccion = idTipoInstruccion;
	}

	/**
	 * Obtiene el valor del atributo idErrorDali
	 * 
	 * @return el valor del atributo idErrorDali
	 */
	public BigInteger getIdErrorDali() {
		return idErrorDali;
	}

	/**
	 * Establece el valor del atributo idErrorDali
	 * 
	 * @param idErrorDali
	 *            el valor del atributo idErrorDali a establecer.
	 */
	public void setIdErrorDali(BigInteger idErrorDali) {
		this.idErrorDali = idErrorDali;
	}

	/**
	 * Obtiene el valor del atributo errorDali
	 * 
	 * @return el valor del atributo errorDali
	 */
	public ErrorDali getErrorDali() {
		return errorDali;
	}

	/**
	 * Establece el valor del atributo errorDali
	 * 
	 * @param errorDali
	 *            el valor del atributo errorDali a establecer.
	 */
	public void setErrorDali(ErrorDali errorDali) {
		this.errorDali = errorDali;
	}

	/**
	 * Obtiene el valor del atributo tipoInstruccion
	 * 
	 * @return el valor del atributo tipoInstruccion
	 */
	public TipoInstruccion getTipoInstruccion() {
		return tipoInstruccion;
	}

	/**
	 * Establece el valor del atributo tipoInstruccion
	 * 
	 * @param tipoInstruccion
	 *            el valor del atributo tipoInstruccion a establecer.
	 */
	public void setTipoInstruccion(TipoInstruccion tipoInstruccion) {
		this.tipoInstruccion = tipoInstruccion;
	}

	/**
	 * Obtiene el valor del atributo plazoReporto
	 * 
	 * @return el valor del atributo plazoReporto
	 */
	public BigInteger getPlazoReporto() {
		return plazoReporto;
	}

	/**
	 * Establece el valor del atributo plazoReporto
	 * 
	 * @param plazoReporto
	 *            el valor del atributo plazoReporto a establecer.
	 */
	public void setPlazoReporto(BigInteger plazoReporto) {
		this.plazoReporto = plazoReporto;
	}

	/**
	 * Obtiene el valor del atributo idEmisionPendiente
	 * 
	 * @return el valor del atributo idEmisionPendiente
	 */
	public BigInteger getIdEmisionPendiente() {
		return idEmisionPendiente;
	}

	/**
	 * Establece el valor del atributo idEmisionPendiente
	 * 
	 * @param idEmisionPendiente
	 *            el valor del atributo idEmisionPendiente a establecer.
	 */
	public void setIdEmisionPendiente(BigInteger idEmisionPendiente) {
		this.idEmisionPendiente = idEmisionPendiente;
	}

	/**
	 * Obtiene el valor del atributo emisionPendiente
	 * 
	 * @return el valor del atributo emisionPendiente
	 */
	public EmisionPendiente getEmisionPendiente() {
		return emisionPendiente;
	}

	/**
	 * Establece el valor del atributo emisionPendiente
	 * 
	 * @param emisionPendiente
	 *            el valor del atributo emisionPendiente a establecer.
	 */
	public void setEmisionPendiente(EmisionPendiente emisionPendiente) {
		this.emisionPendiente = emisionPendiente;
	}

	/**
	 * Obtiene el valor del atributo idCuentaNombradaReceptora
	 * 
	 * @return el valor del atributo idCuentaNombradaReceptora
	 */
	public BigInteger getIdCuentaNombradaReceptora() {
		return idCuentaNombradaReceptora;
	}

	/**
	 * Establece el valor del atributo idCuentaNombradaReceptora
	 * 
	 * @param idCuentaNombradaReceptora
	 *            el valor del atributo idCuentaNombradaReceptora a establecer.
	 */
	public void setIdCuentaNombradaReceptora(BigInteger idCuentaNombradaReceptora) {
		this.idCuentaNombradaReceptora = idCuentaNombradaReceptora;
	}

	/**
	 * Obtiene el valor del atributo cuentaNombradaReceptora
	 * 
	 * @return el valor del atributo cuentaNombradaReceptora
	 */
	public CuentaNombrada getCuentaNombradaReceptora() {
		return cuentaNombradaReceptora;
	}

	/**
	 * Establece el valor del atributo cuentaNombradaReceptora
	 * 
	 * @param cuentaNombradaReceptora
	 *            el valor del atributo cuentaNombradaReceptora a establecer.
	 */
	public void setCuentaNombradaReceptora(CuentaNombrada cuentaNombradaReceptora) {
		this.cuentaNombradaReceptora = cuentaNombradaReceptora;
	}

	/**
	 * Obtiene el valor del atributo idCuentaNombradaTraspasante
	 * 
	 * @return el valor del atributo idCuentaNombradaTraspasante
	 */
	public BigInteger getIdCuentaNombradaTraspasante() {
		return idCuentaNombradaTraspasante;
	}

	/**
	 * Establece el valor del atributo idCuentaNombradaTraspasante
	 * 
	 * @param idCuentaNombradaTraspasante
	 *            el valor del atributo idCuentaNombradaTraspasante a
	 *            establecer.
	 */
	public void setIdCuentaNombradaTraspasante(BigInteger idCuentaNombradaTraspasante) {
		this.idCuentaNombradaTraspasante = idCuentaNombradaTraspasante;
	}

	/**
	 * Obtiene el valor del atributo cuentaNombradaTraspasante
	 * 
	 * @return el valor del atributo cuentaNombradaTraspasante
	 */
	public CuentaNombrada getCuentaNombradaTraspasante() {
		return cuentaNombradaTraspasante;
	}

	/**
	 * Establece el valor del atributo cuentaNombradaTraspasante
	 * 
	 * @param cuentaNombradaTraspasante
	 *            el valor del atributo cuentaNombradaTraspasante a establecer.
	 */
	public void setCuentaNombradaTraspasante(CuentaNombrada cuentaNombradaTraspasante) {
		this.cuentaNombradaTraspasante = cuentaNombradaTraspasante;
	}

	/**
	 * Obtiene el valor del atributo idCuentaBancoTrabajo
	 * 
	 * @return el valor del atributo idCuentaBancoTrabajo
	 */
	public BigInteger getIdCuentaBancoTrabajo() {
		return idCuentaBancoTrabajo;
	}

	/**
	 * Establece el valor del atributo idCuentaBancoTrabajo
	 * 
	 * @param idCuentaBancoTrabajo
	 *            el valor del atributo idCuentaBancoTrabajo a establecer.
	 */
	public void setIdCuentaBancoTrabajo(BigInteger idCuentaBancoTrabajo) {
		this.idCuentaBancoTrabajo = idCuentaBancoTrabajo;
	}

	/**
	 * Obtiene el valor del atributo cuentaNombradaBancoTrabajo
	 * 
	 * @return el valor del atributo cuentaNombradaBancoTrabajo
	 */
	public CuentaNombrada getCuentaNombradaBancoTrabajo() {
		return cuentaNombradaBancoTrabajo;
	}

	/**
	 * Establece el valor del atributo cuentaNombradaBancoTrabajo
	 * 
	 * @param cuentaNombradaBancoTrabajo
	 *            el valor del atributo cuentaNombradaBancoTrabajo a establecer.
	 */
	public void setCuentaNombradaBancoTrabajo(CuentaNombrada cuentaNombradaBancoTrabajo) {
		this.cuentaNombradaBancoTrabajo = cuentaNombradaBancoTrabajo;
	}

	/**
	 * Obtiene el valor del atributo idTipoMensaje
	 * 
	 * @return el valor del atributo idTipoMensaje
	 */
	public BigInteger getIdTipoMensaje() {
		return idTipoMensaje;
	}

	/**
	 * Establece el valor del atributo idTipoMensaje
	 * 
	 * @param idTipoMensaje
	 *            el valor del atributo idTipoMensaje a establecer.
	 */
	public void setIdTipoMensaje(BigInteger idTipoMensaje) {
		this.idTipoMensaje = idTipoMensaje;
	}

	/**
	 * Obtiene el valor del atributo tipoMensajeCat
	 * 
	 * @return el valor del atributo tipoMensajeCat
	 */
	public TipoMensajeCat getTipoMensajeCat() {
		return tipoMensajeCat;
	}

	/**
	 * Establece el valor del atributo tipoMensajeCat
	 * 
	 * @param tipoMensajeCat
	 *            el valor del atributo tipoMensajeCat a establecer.
	 */
	public void setTipoMensajeCat(TipoMensajeCat tipoMensajeCat) {
		this.tipoMensajeCat = tipoMensajeCat;
	}

	/**
	 * Obtiene el valor del atributo idBoveda
	 * 
	 * @return el valor del atributo idBoveda
	 */
	public BigInteger getIdBoveda() {
		return idBoveda;
	}

	/**
	 * Establece el valor del atributo idBoveda
	 * 
	 * @param idBoveda
	 *            el valor del atributo idBoveda a establecer.
	 */
	public void setIdBoveda(BigInteger idBoveda) {
		this.idBoveda = idBoveda;
	}

	/**
	 * Obtiene el valor del atributo idEstadoInstruccion
	 * 
	 * @return el valor del atributo idEstadoInstruccion
	 */
	public BigInteger getIdEstadoInstruccion() {
		return idEstadoInstruccion;
	}

	/**
	 * Establece el valor del atributo idEstadoInstruccion
	 * 
	 * @param idEstadoInstruccion
	 *            el valor del atributo idEstadoInstruccion a establecer.
	 */
	public void setIdEstadoInstruccion(BigInteger idEstadoInstruccion) {
		this.idEstadoInstruccion = idEstadoInstruccion;
	}

	/**
	 * Obtiene el valor del atributo estadoInstruccionCat
	 * 
	 * @return el valor del atributo estadoInstruccionCat
	 */
	public EstadoInstruccionCat getEstadoInstruccionCat() {
		return estadoInstruccionCat;
	}

	/**
	 * Establece el valor del atributo estadoInstruccionCat
	 * 
	 * @param estadoInstruccionCat
	 *            el valor del atributo estadoInstruccionCat a establecer.
	 */
	public void setEstadoInstruccionCat(EstadoInstruccionCat estadoInstruccionCat) {
		this.estadoInstruccionCat = estadoInstruccionCat;
	}

	/**
	 * Obtiene el valor del atributo idCupon
	 * 
	 * @return el valor del atributo idCupon
	 */
	public BigInteger getIdCupon() {
		return idCupon;
	}

	/**
	 * Establece el valor del atributo idCupon
	 * 
	 * @param idCupon
	 *            el valor del atributo idCupon a establecer.
	 */
	public void setIdCupon(BigInteger idCupon) {
		this.idCupon = idCupon;
	}

	/**
	 * Obtiene el valor del atributo cupon
	 * 
	 * @return el valor del atributo cupon
	 */
	public Cupon getCupon() {
		return cupon;
	}

	/**
	 * Establece el valor del atributo cupon
	 * 
	 * @param cupon
	 *            el valor del atributo cupon a establecer.
	 */
	public void setCupon(Cupon cupon) {
		this.cupon = cupon;
	}

	/**
	 * Obtiene el valor del atributo idEstadoInstruccionEnvio
	 * 
	 * @return el valor del atributo idEstadoInstruccionEnvio
	 */
	public BigInteger getIdEstadoInstruccionEnvio() {
		return idEstadoInstruccionEnvio;
	}

	/**
	 * Establece el valor del atributo idEstadoInstruccionEnvio
	 * 
	 * @param idEstadoInstruccionEnvio
	 *            el valor del atributo idEstadoInstruccionEnvio a establecer.
	 */
	public void setIdEstadoInstruccionEnvio(BigInteger idEstadoInstruccionEnvio) {
		this.idEstadoInstruccionEnvio = idEstadoInstruccionEnvio;
	}

	/**
	 * Obtiene el valor del atributo idDivisa
	 * 
	 * @return el valor del atributo idDivisa
	 */
	public BigInteger getIdDivisa() {
		return idDivisa;
	}

	/**
	 * Establece el valor del atributo idDivisa
	 * 
	 * @param idDivisa
	 *            el valor del atributo idDivisa a establecer.
	 */
	public void setIdDivisa(BigInteger idDivisa) {
		this.idDivisa = idDivisa;
	}

	/**
	 * Obtiene el valor del atributo divisa
	 * 
	 * @return el valor del atributo divisa
	 */
	public Divisa getDivisa() {
		return divisa;
	}

	/**
	 * Establece el valor del atributo divisa
	 * 
	 * @param divisa
	 *            el valor del atributo divisa a establecer.
	 */
	public void setDivisa(Divisa divisa) {
		this.divisa = divisa;
	}

	/**
	 * Obtiene el valor del atributo precioTitulo
	 * 
	 * @return el valor del atributo precioTitulo
	 */
	public BigDecimal getPrecioTitulo() {
		return precioTitulo;
	}

	/**
	 * Establece el valor del atributo precioTitulo
	 * 
	 * @param precioTitulo
	 *            el valor del atributo precioTitulo a establecer.
	 */
	public void setPrecioTitulo(BigDecimal precioTitulo) {
		this.precioTitulo = precioTitulo;
	}

	/**
	 * Obtiene el valor del atributo tasaNegociada
	 * 
	 * @return el valor del atributo tasaNegociada
	 */
	public BigDecimal getTasaNegociada() {
		return tasaNegociada;
	}

	/**
	 * Establece el valor del atributo tasaNegociada
	 * 
	 * @param tasaNegociada
	 *            el valor del atributo tasaNegociada a establecer.
	 */
	public void setTasaNegociada(BigDecimal tasaNegociada) {
		this.tasaNegociada = tasaNegociada;
	}

	/**
	 * Obtiene el valor del atributo cantidadTitulos
	 * 
	 * @return el valor del atributo cantidadTitulos
	 */
	public BigInteger getCantidadTitulos() {
		return cantidadTitulos;
	}

	/**
	 * Establece el valor del atributo cantidadTitulos
	 * 
	 * @param cantidadTitulos
	 *            el valor del atributo cantidadTitulos a establecer.
	 */
	public void setCantidadTitulos(BigInteger cantidadTitulos) {
		this.cantidadTitulos = cantidadTitulos;
	}

	/**
	 * Obtiene el valor del atributo importe
	 * 
	 * @return el valor del atributo importe
	 */
	public BigDecimal getImporte() {
		return importe;
	}

	/**
	 * Establece el valor del atributo importe
	 * 
	 * @param importe
	 *            el valor del atributo importe a establecer.
	 */
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	/**
	 * Obtiene el valor del atributo fechaLiquidacion
	 * 
	 * @return el valor del atributo fechaLiquidacion
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	/**
	 * Establece el valor del atributo fechaLiquidacion
	 * 
	 * @param fechaLiquidacion
	 *            el valor del atributo fechaLiquidacion a establecer.
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	/**
	 * Obtiene el valor del atributo fechaConcertacion
	 * 
	 * @return el valor del atributo fechaConcertacion
	 */
	public Date getFechaConcertacion() {
		return fechaConcertacion;
	}

	/**
	 * Establece el valor del atributo fechaConcertacion
	 * 
	 * @param fechaConcertacion
	 *            el valor del atributo fechaConcertacion a establecer.
	 */
	public void setFechaConcertacion(Date fechaConcertacion) {
		this.fechaConcertacion = fechaConcertacion;
	}

	/**
	 * Obtiene el valor del atributo folioInstruccionTraspasante
	 * 
	 * @return el valor del atributo folioInstruccionTraspasante
	 */
	public String getFolioInstruccionTraspasante() {
		return folioInstruccionTraspasante;
	}

	/**
	 * Establece el valor del atributo folioInstruccionTraspasante
	 * 
	 * @param folioInstruccionTraspasante
	 *            el valor del atributo folioInstruccionTraspasante a
	 *            establecer.
	 */
	public void setFolioInstruccionTraspasante(String folioInstruccionTraspasante) {
		this.folioInstruccionTraspasante = folioInstruccionTraspasante;
	}

	/**
	 * Obtiene el valor del atributo folioInstruccionReceptora
	 * 
	 * @return el valor del atributo folioInstruccionReceptora
	 */
	public String getFolioInstruccionReceptora() {
		return folioInstruccionReceptora;
	}

	/**
	 * Establece el valor del atributo folioInstruccionReceptora
	 * 
	 * @param folioInstruccionReceptora
	 *            el valor del atributo folioInstruccionReceptora a establecer.
	 */
	public void setFolioInstruccionReceptora(String folioInstruccionReceptora) {
		this.folioInstruccionReceptora = folioInstruccionReceptora;
	}

	/**
	 * Obtiene el valor del atributo folioControl
	 * 
	 * @return el valor del atributo folioControl
	 */
	public BigInteger getFolioControl() {
		return folioControl;
	}

	/**
	 * Establece el valor del atributo folioControl
	 * 
	 * @param folioControl
	 *            el valor del atributo folioControl a establecer.
	 */
	public void setFolioControl(BigInteger folioControl) {
		this.folioControl = folioControl;
	}

	/**
	 * Obtiene el valor del atributo requiereMatch
	 * 
	 * @return el valor del atributo requiereMatch
	 */
	public Integer getRequiereMatch() {
		return requiereMatch;
	}

	/**
	 * Establece el valor del atributo requiereMatch
	 * 
	 * @param requiereMatch
	 *            el valor del atributo requiereMatch a establecer.
	 */
	public void setRequiereMatch(Integer requiereMatch) {
		this.requiereMatch = requiereMatch;
	}

	/**
	 * Obtiene el valor del atributo idInstruccionAnterior
	 * 
	 * @return el valor del atributo idInstruccionAnterior
	 */
	public BigInteger getIdInstruccionAnterior() {
		return idInstruccionAnterior;
	}

	/**
	 * Establece el valor del atributo idInstruccionAnterior
	 * 
	 * @param idInstruccionAnterior
	 *            el valor del atributo idInstruccionAnterior a establecer.
	 */
	public void setIdInstruccionAnterior(BigInteger idInstruccionAnterior) {
		this.idInstruccionAnterior = idInstruccionAnterior;
	}

	/**
	 * Obtiene el valor del atributo idInstruccionSiguiente
	 * 
	 * @return el valor del atributo idInstruccionSiguiente
	 */
	public BigInteger getIdInstruccionSiguiente() {
		return idInstruccionSiguiente;
	}

	/**
	 * Establece el valor del atributo idInstruccionSiguiente
	 * 
	 * @param idInstruccionSiguiente
	 *            el valor del atributo idInstruccionSiguiente a establecer.
	 */
	public void setIdInstruccionSiguiente(BigInteger idInstruccionSiguiente) {
		this.idInstruccionSiguiente = idInstruccionSiguiente;
	}

	/**
	 * Obtiene el valor del atributo mensajeRecibido
	 * 
	 * @return el valor del atributo mensajeRecibido
	 */
	public String getMensajeRecibido() {
		return mensajeRecibido;
	}

	/**
	 * Establece el valor del atributo mensajeRecibido
	 * 
	 * @param mensajeRecibido
	 *            el valor del atributo mensajeRecibido a establecer.
	 */
	public void setMensajeRecibido(String mensajeRecibido) {
		this.mensajeRecibido = mensajeRecibido;
	}

	/**
	 * Obtiene el valor del atributo origen
	 * 
	 * @return el valor del atributo origen
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * Establece el valor del atributo origen
	 * 
	 * @param origen
	 *            el valor del atributo origen a establecer.
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * Obtiene el valor del atributo tasaReferencia
	 * 
	 * @return el valor del atributo tasaReferencia
	 */
	public BigInteger getTasaReferencia() {
		return tasaReferencia;
	}

	/**
	 * Establece el valor del atributo tasaReferencia
	 * 
	 * @param tasaReferencia
	 *            el valor del atributo tasaReferencia a establecer.
	 */
	public void setTasaReferencia(BigInteger tasaReferencia) {
		this.tasaReferencia = tasaReferencia;
	}

	/**
	 * Obtiene el valor del atributo interesesGenerados
	 * 
	 * @return el valor del atributo interesesGenerados
	 */
	public BigDecimal getInteresesGenerados() {
		return interesesGenerados;
	}

	/**
	 * Establece el valor del atributo interesesGenerados
	 * 
	 * @param interesesGenerados
	 *            el valor del atributo interesesGenerados a establecer.
	 */
	public void setInteresesGenerados(BigDecimal interesesGenerados) {
		this.interesesGenerados = interesesGenerados;
	}

	/**
	 * Obtiene el valor del atributo fechaIntereses
	 * 
	 * @return el valor del atributo fechaIntereses
	 */
	public Date getFechaIntereses() {
		return fechaIntereses;
	}

	/**
	 * Establece el valor del atributo fechaIntereses
	 * 
	 * @param fechaIntereses
	 *            el valor del atributo fechaIntereses a establecer.
	 */
	public void setFechaIntereses(Date fechaIntereses) {
		this.fechaIntereses = fechaIntereses;
	}

	/**
	 * Obtiene el valor del atributo instruccionesMovPosicionNom
	 * 
	 * @return el valor del atributo instruccionesMovPosicionNom
	 */
	public Set<InstruccionMovPosicionNom> getInstruccionesMovPosicionNom() {
		return InstruccionesMovPosicionNom;
	}

	/**
	 * Establece el valor del atributo instruccionesMovPosicionNom
	 * 
	 * @param instruccionesMovPosicionNom
	 *            el valor del atributo instruccionesMovPosicionNom a
	 *            establecer.
	 */
	public void setInstruccionesMovPosicionNom(Set<InstruccionMovPosicionNom> instruccionesMovPosicionNom) {
		InstruccionesMovPosicionNom = instruccionesMovPosicionNom;
	}

	/**
	 * Obtiene el valor del atributo instruccionesLiquidacion
	 * 
	 * @return el valor del atributo instruccionesLiquidacion
	 */
	public Set<InstruccionLiquidacion> getInstruccionesLiquidacion() {
		return instruccionesLiquidacion;
	}

	/**
	 * Establece el valor del atributo instruccionesLiquidacion
	 * 
	 * @param instruccionesLiquidacion
	 *            el valor del atributo instruccionesLiquidacion a establecer.
	 */
	public void setInstruccionesLiquidacion(Set<InstruccionLiquidacion> instruccionesLiquidacion) {
		this.instruccionesLiquidacion = instruccionesLiquidacion;
	}

	/**
	 * Obtiene el valor del atributo parcialidadesLiquidacion
	 * 
	 * @return el valor del atributo parcialidadesLiquidacion
	 */
	public Set<LiquidacionParcialInstruccionMov> getParcialidadesLiquidacion() {
		return parcialidadesLiquidacion;
	}

	/**
	 * Establece el valor del atributo parcialidadesLiquidacion
	 * 
	 * @param parcialidadesLiquidacion
	 *            el valor del atributo parcialidadesLiquidacion a establecer.
	 */
	public void setParcialidadesLiquidacion(Set<LiquidacionParcialInstruccionMov> parcialidadesLiquidacion) {
		this.parcialidadesLiquidacion = parcialidadesLiquidacion;
	}

	/**
	 * Obtiene el valor del atributo boveda
	 * 
	 * @return el valor del atributo boveda
	 */
	public Boveda getBoveda() {
		return boveda;
	}

	/**
	 * Establece el valor del atributo boveda
	 * 
	 * @param boveda
	 *            el valor del atributo parcialidadesLiquidacion a establecer.
	 */
	public void setBoveda(Boveda boveda) {
		this.boveda = boveda;
	}

	/**
	 * @return the idBovedaEfectivo
	 */
	public BigInteger getIdBovedaEfectivo() {
		return idBovedaEfectivo;
	}

	/**
	 * @return the bovedaEfectivo
	 */
	public Boveda getBovedaEfectivo() {
		return bovedaEfectivo;
	}

	/**
	 * @param idBovedaEfectivo the idBovedaEfectivo to set
	 */
	public void setIdBovedaEfectivo(BigInteger idBovedaEfectivo) {
		this.idBovedaEfectivo = idBovedaEfectivo;
	}

	/**
	 * @param bovedaEfectivo the bovedaEfectivo to set
	 */
	public void setBovedaEfectivo(Boveda bovedaEfectivo) {
		this.bovedaEfectivo = bovedaEfectivo;
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

}
