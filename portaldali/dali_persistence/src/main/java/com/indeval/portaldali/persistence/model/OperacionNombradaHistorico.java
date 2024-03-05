/**
 * Sistema Portal DALI
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Nov 24, 2008
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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Mapeo de Hibernate para la tabla T_OPERACION_NOMBRADA_H. Contiene el
 * histórico de las operaciones nombradas.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
@Entity
@Table(name = "T_OPERACION_NOMBRADA_H")
public class OperacionNombradaHistorico implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * Identificador de la operaci&oacute;n.
	 */
	@Id
	@Column(name = "ID_OPERACION_H")
	private BigInteger idOperacion;

	/**
	 * Instrucci&oacute;n liquidaci&oacute;n a la que pertenece la
	 * operaci&oacute;n.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_INSTRUCCION", updatable = false, insertable = false)
	private InstruccionLiquidacionHistorico instruccion;

	/**
	 * Tipo de la operaci&oacute;n.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_OPERACION", updatable = false, insertable = false)
	private TipoOperacion tipoOperacion;

	/**
	 * Identificador del tipo de operaci&oacute;n.
	 */
	@Column(name = "ID_TIPO_OPERACION")
	private BigInteger idTipoOperacion;

	/**
	 * Folio de la operaci&oacute;n dentro de la instrucci&oacute;n
	 * liquidaci&oacute;n.
	 */
	@Column(name = "FOLIO_OPERACION")
	private BigInteger folioOperacion;

	/**
	 * Divisa sobre la que se realizan los movimientos de efectivo.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DIVISA", updatable = false, insertable = false)
	private Divisa divisa;

	/**
	 * Identificador de la Divisa sobre la que se realizan los movimientos de
	 * efectivo.
	 */
	@Column(name = "ID_DIVISA")
	private BigInteger idDivisa;

	/** La fecha de creación del registro */
	@Column(name = "FECHA_CREACION")
	private Date fechaCreacion;

	/**
	 * Monto por el que se realizan los movimientos de efectivo.
	 */
	@Column(name = "MONTO")
	private BigDecimal monto;

	/**
	 * Cuenta nombrada de valores del traspasante.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUENTA_TRASPASANTE", updatable = false, insertable = false)
	private CuentaNombrada cuentaNombradaTraspasante;

	/**
	 * Identificador de la cuenta nombrada de valores del traspasante.
	 */
	@Column(name = "ID_CUENTA_TRASPASANTE")
	private BigInteger idCuentaNombradaTraspasante;

	/**
	 * Instituci&oacute;n del traspasante.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TRASPASANTE", updatable = false, insertable = false)
	private Institucion institucionTraspasante;

	/**
	 * Identificador de la Instituci&oacute;n del traspasante.
	 */
	@Column(name = "ID_TRASPASANTE")
	private BigInteger idInstitucionTraspasante;

	/**
	 * Cuenta de valores del participante receptor.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUENTA_RECEPTOR", updatable = false, insertable = false)
	private CuentaNombrada cuentaNombradaReceptor;

	/**
	 * Identificador de la cuenta nombrada de valores del receptor.
	 */
	@Column(name = "ID_CUENTA_RECEPTOR")
	private BigInteger idCuentaNombradaReceptor;

	/**
	 * B&oacute;veda sobre la que se realizan los movimientos de efectivo.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_BOVEDA_EFECTIVO", updatable = false, insertable = false)
	private Boveda bovedaEfectivo;

	/**
	 * Identificador de la b&oacute;veda sobre la que se realizan los
	 * movimientos de efectivo.
	 */
	@Column(name = "ID_BOVEDA_EFECTIVO")
	private BigInteger idBovedaEfectivo;

	/**
	 * Instituci&oacute;n del receptor.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_RECEPTOR", updatable = false, insertable = false)
	private Institucion institucionReceptor;

	/**
	 * Identificador de la Instituci&oacute;n del receptor.
	 */
	@Column(name = "ID_RECEPTOR")
	private BigInteger idInstitucionReceptor;

	/**
	 * Indicador de a que secci&oacute;n del saldo se cargan y abonan los
	 * movimientos de efectivo. Si afecta al disponible es 1. Si afecta al no
	 * disponible es 2.
	 */
	@Column(name = "CARGO_EFECTIVO_A")
	private BigInteger cargoEfectivoA;

	/**
	 * Instituci&oacute;n del banco de trabajo.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_BANCO_TRABAJO", updatable = false, insertable = false)
	private Institucion institucionBancoDeTrabajo;

	/**
	 * Identificador de la instituci&oacute;n del banco de trabajo.
	 */
	@Column(name = "ID_BANCO_TRABAJO")
	private BigInteger idInstitucionBancoDeTrabajo;

	/**
	 * Cuenta nombrada del banco de trabajo.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUENTA_BANCO_TRABAJO", updatable = false, insertable = false)
	private CuentaNombrada cuentaBancoDeTrabajo;

	/**
	 * Identificador de la cuenta nombrada del banco de trabajo.
	 */
	@Column(name = "ID_CUENTA_BANCO_TRABAJO")
	private BigInteger idCuentaBancoDeTrabajo;

	/**
	 * Identificador de la cuenta nombrada de efectivo del traspasante.
	 */
	@Column(name = "ID_CUENTA_EFECTIVO_TRASPASANTE")
	private BigInteger idCuentaEfectivoTraspasante;

	/**
	 * Cuenta nombrada de efectivo del traspasante.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUENTA_EFECTIVO_TRASPASANTE", updatable = false, insertable = false)
	private CuentaNombrada cuentaEfectivoTraspasante;

	/**
	 * Identificador de la cuenta nombrada de efectivo del receptor.
	 */
	@Column(name = "ID_CUENTA_EFECTIVO_RECEPTOR")
	private BigInteger idCuentaEfectivoReceptor;

	/**
	 * Cuenta nombrada de efectivo del receptor.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUENTA_EFECTIVO_RECEPTOR", updatable = false, insertable = false)
	private CuentaNombrada cuentaEfectivoReceptor;

	/**
	 * Identificador de la cuenta nombrada de de la b&oacute;veda de efectivo.
	 */
	@Column(name = "ID_CUENTA_BOVEDA_EFECTIVO")
	private BigInteger idCuentaBovedaEfectivo;

	/**
	 * Cuenta nombrada de la b&oacute;veda de efectivo.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUENTA_BOVEDA_EFECTIVO", updatable = false, insertable = false)
	private CuentaNombrada cuentaBovedaEfectivo;

	/**
	 * Posici&oacute;n nombrada que referencia el registro.
	 */
	@ManyToOne(fetch = FetchType.LAZY,optional=true)
	@JoinColumns ({
        @JoinColumn(name="ID_POSICION_TRASPASANTE", referencedColumnName = "ID_POSICION_NOMBRADA",updatable = false, insertable = false,nullable=true),
        @JoinColumn(name="FECHA_CREACION", referencedColumnName = "FECHA_CREACION",updatable = false, insertable = false,nullable=true)
    })

	private PosicionNombradaHistorico posicionNombrada;

	/**
	 * Posici&oacute;n nombrada que referencia el registro.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns ({
        @JoinColumn(name="ID_POSICION_RECEPTOR", referencedColumnName = "ID_POSICION_NOMBRADA",updatable = false, insertable = false),
        @JoinColumn(name="FECHA_CREACION", referencedColumnName = "FECHA_CREACION",updatable = false, insertable = false)
    })
	public PosicionNombradaHistorico posicionReceptor;

	/**
	 * Posici&oacute;n nombrada que referencia el registro.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns ({
        @JoinColumn(name="ID_POSICION_TRASPASANTE", referencedColumnName = "ID_POSICION_NOMBRADA",updatable = false, insertable = false),
        @JoinColumn(name="FECHA_CREACION", referencedColumnName = "FECHA_CREACION",updatable = false, insertable = false)
    })
	public PosicionNombradaHistorico posicionTraspasante;

	/**
	 * Identificador de la posici&oacute;n nombrada que referencia el registro.
	 */
	@Column(name = "ID_POSICION_TRASPASANTE")
	private BigInteger idPosicionNombrada;

	/**
	 * N&uacute;mero de titulos involucrados en la operaci&oacute;n.
	 */
	@Column(name = "NUMERO_TITULOS")
	private BigInteger numeroTitulos;

	/**
	 * Precio por titulo.
	 */
	@Column(name = "PRECIO")
	private BigDecimal precio;

	/**
	 * Indica si el registro pertenece al traspasante (T) o al receptor (R).
	 */
	// @Column(name = "PARTICIPANTE_ORIGEN")
	@Transient
	private String participanteOrigen;

	/**
	 * Indica si el cargo se realiza sobre el disponible o el no disponible en
	 * las posiciones nombradas.
	 */
	@Column(name = "CARGO_VALORES_A")
	private BigInteger cargoValoresA;

	/**
	 * El cupón relacionado con la operación y la posición.
	 */
	@Column(name = "ID_CUPON", updatable = false, insertable = false)
	private BigInteger idCupon;

	/**
	 * El objeto que representa la relación del cupón con la operación y la
	 * posición.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUPON", updatable = false, insertable = false)
	private Cupon cupon;

	/**
	 * Cuenta nombrada de la b&oacute;veda de valores.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUENTA_BOVEDA_VALORES", updatable = false, insertable = false)
	private CuentaNombrada cuentaBovedaValores;

	/**
	 * Identificador de la cuenta nombrada de la b&oacute;veda de valores.
	 */
	@Column(name = "ID_CUENTA_BOVEDA_VALORES")
	private BigInteger idCuentaBovedaValores;

	/**
	 * B&oacute;veda sobre la que se realizan los movimientos de valores.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_BOVEDA_VALORES",  referencedColumnName="ID_BOVEDA", updatable = false, insertable = false)
	private Boveda bovedaValores;

	/**
	 * Identificador de la b&oacute;veda sobre la que se realizan los
	 * movimientos de valores.
	 */
	@Column(name = "ID_BOVEDA_VALORES")
	private BigInteger idBovedaValores;

	/**
	 * B&oacute;veda sobre la que se realizan los movimientos de valores.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EMISION", updatable = false, insertable = false)
	private Emision emision;

	/**
	 * Identificador de la b&oacute;veda sobre la que se realizan los
	 * movimientos de valores.
	 */
	@Column(name = "ID_EMISION")
	private BigInteger idEmision;

	/**
	 * Obtiene el valor del atributo idOperacion
	 * 
	 * @return el valor del atributo idOperacion
	 */
	public BigInteger getIdOperacion() {
		return idOperacion;
	}

	/**
	 * Establece el valor del atributo idOperacion
	 * 
	 * @param idOperacion
	 *            el valor del atributo idOperacion a establecer
	 */
	public void setIdOperacion(BigInteger idOperacion) {
		this.idOperacion = idOperacion;
	}

	/**
	 * Obtiene el valor del atributo instruccion
	 * 
	 * @return el valor del atributo instruccion
	 */
	public InstruccionLiquidacionHistorico getInstruccion() {
		return instruccion;
	}

	/**
	 * Establece el valor del atributo instruccion
	 * 
	 * @param instruccion
	 *            el valor del atributo instruccion a establecer
	 */
	public void setInstruccion(InstruccionLiquidacionHistorico instruccion) {
		this.instruccion = instruccion;
	}

	/**
	 * Obtiene el valor del atributo tipoOperacion
	 * 
	 * @return el valor del atributo tipoOperacion
	 */
	public TipoOperacion getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * Establece el valor del atributo tipoOperacion
	 * 
	 * @param tipoOperacion
	 *            el valor del atributo tipoOperacion a establecer
	 */
	public void setTipoOperacion(TipoOperacion tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	/**
	 * Obtiene el valor del atributo idTipoOperacion
	 * 
	 * @return el valor del atributo idTipoOperacion
	 */
	public BigInteger getIdTipoOperacion() {
		return idTipoOperacion;
	}

	/**
	 * Establece el valor del atributo idTipoOperacion
	 * 
	 * @param idTipoOperacion
	 *            el valor del atributo idTipoOperacion a establecer
	 */
	public void setIdTipoOperacion(BigInteger idTipoOperacion) {
		this.idTipoOperacion = idTipoOperacion;
	}

	/**
	 * Obtiene el valor del atributo folioOperacion
	 * 
	 * @return el valor del atributo folioOperacion
	 */
	public BigInteger getFolioOperacion() {
		return folioOperacion;
	}

	/**
	 * Establece el valor del atributo folioOperacion
	 * 
	 * @param folioOperacion
	 *            el valor del atributo folioOperacion a establecer
	 */
	public void setFolioOperacion(BigInteger folioOperacion) {
		this.folioOperacion = folioOperacion;
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
	 *            el valor del atributo divisa a establecer
	 */
	public void setDivisa(Divisa divisa) {
		this.divisa = divisa;
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
	 *            el valor del atributo idDivisa a establecer
	 */
	public void setIdDivisa(BigInteger idDivisa) {
		this.idDivisa = idDivisa;
	}

	/**
	 * Obtiene el valor del atributo fechaCreacion
	 * 
	 * @return el valor del atributo fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * Establece el valor del atributo fechaCreacion
	 * 
	 * @param fechaCreacion
	 *            el valor del atributo fechaCreacion a establecer
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * Obtiene el valor del atributo monto
	 * 
	 * @return el valor del atributo monto
	 */
	public BigDecimal getMonto() {
		return monto;
	}

	/**
	 * Establece el valor del atributo monto
	 * 
	 * @param monto
	 *            el valor del atributo monto a establecer
	 */
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
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
	 *            el valor del atributo cuentaNombradaTraspasante a establecer
	 */
	public void setCuentaNombradaTraspasante(CuentaNombrada cuentaNombradaTraspasante) {
		this.cuentaNombradaTraspasante = cuentaNombradaTraspasante;
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
	 *            el valor del atributo idCuentaNombradaTraspasante a establecer
	 */
	public void setIdCuentaNombradaTraspasante(BigInteger idCuentaNombradaTraspasante) {
		this.idCuentaNombradaTraspasante = idCuentaNombradaTraspasante;
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
	 *            el valor del atributo institucionTraspasante a establecer
	 */
	public void setInstitucionTraspasante(Institucion institucionTraspasante) {
		this.institucionTraspasante = institucionTraspasante;
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
	 *            el valor del atributo idInstitucionTraspasante a establecer
	 */
	public void setIdInstitucionTraspasante(BigInteger idInstitucionTraspasante) {
		this.idInstitucionTraspasante = idInstitucionTraspasante;
	}

	/**
	 * Obtiene el valor del atributo cuentaNombradaReceptor
	 * 
	 * @return el valor del atributo cuentaNombradaReceptor
	 */
	public CuentaNombrada getCuentaNombradaReceptor() {
		return cuentaNombradaReceptor;
	}

	/**
	 * Establece el valor del atributo cuentaNombradaReceptor
	 * 
	 * @param cuentaNombradaReceptor
	 *            el valor del atributo cuentaNombradaReceptor a establecer
	 */
	public void setCuentaNombradaReceptor(CuentaNombrada cuentaNombradaReceptor) {
		this.cuentaNombradaReceptor = cuentaNombradaReceptor;
	}

	/**
	 * Obtiene el valor del atributo idCuentaNombradaReceptor
	 * 
	 * @return el valor del atributo idCuentaNombradaReceptor
	 */
	public BigInteger getIdCuentaNombradaReceptor() {
		return idCuentaNombradaReceptor;
	}

	/**
	 * Establece el valor del atributo idCuentaNombradaReceptor
	 * 
	 * @param idCuentaNombradaReceptor
	 *            el valor del atributo idCuentaNombradaReceptor a establecer
	 */
	public void setIdCuentaNombradaReceptor(BigInteger idCuentaNombradaReceptor) {
		this.idCuentaNombradaReceptor = idCuentaNombradaReceptor;
	}

	/**
	 * Obtiene el valor del atributo bovedaEfectivo
	 * 
	 * @return el valor del atributo bovedaEfectivo
	 */
	public Boveda getBovedaEfectivo() {
		return bovedaEfectivo;
	}

	/**
	 * Establece el valor del atributo bovedaEfectivo
	 * 
	 * @param bovedaEfectivo
	 *            el valor del atributo bovedaEfectivo a establecer
	 */
	public void setBovedaEfectivo(Boveda bovedaEfectivo) {
		this.bovedaEfectivo = bovedaEfectivo;
	}

	/**
	 * Obtiene el valor del atributo idBovedaEfectivo
	 * 
	 * @return el valor del atributo idBovedaEfectivo
	 */
	public BigInteger getIdBovedaEfectivo() {
		return idBovedaEfectivo;
	}

	/**
	 * Establece el valor del atributo idBovedaEfectivo
	 * 
	 * @param idBovedaEfectivo
	 *            el valor del atributo idBovedaEfectivo a establecer
	 */
	public void setIdBovedaEfectivo(BigInteger idBovedaEfectivo) {
		this.idBovedaEfectivo = idBovedaEfectivo;
	}

	/**
	 * Obtiene el valor del atributo institucionReceptor
	 * 
	 * @return el valor del atributo institucionReceptor
	 */
	public Institucion getInstitucionReceptor() {
		return institucionReceptor;
	}

	/**
	 * Establece el valor del atributo institucionReceptor
	 * 
	 * @param institucionReceptor
	 *            el valor del atributo institucionReceptor a establecer
	 */
	public void setInstitucionReceptor(Institucion institucionReceptor) {
		this.institucionReceptor = institucionReceptor;
	}

	/**
	 * Obtiene el valor del atributo idInstitucionReceptor
	 * 
	 * @return el valor del atributo idInstitucionReceptor
	 */
	public BigInteger getIdInstitucionReceptor() {
		return idInstitucionReceptor;
	}

	/**
	 * Establece el valor del atributo idInstitucionReceptor
	 * 
	 * @param idInstitucionReceptor
	 *            el valor del atributo idInstitucionReceptor a establecer
	 */
	public void setIdInstitucionReceptor(BigInteger idInstitucionReceptor) {
		this.idInstitucionReceptor = idInstitucionReceptor;
	}

	/**
	 * Obtiene el valor del atributo cargoEfectivoA
	 * 
	 * @return el valor del atributo cargoEfectivoA
	 */
	public BigInteger getCargoEfectivoA() {
		return cargoEfectivoA;
	}

	/**
	 * Establece el valor del atributo cargoEfectivoA
	 * 
	 * @param cargoEfectivoA
	 *            el valor del atributo cargoEfectivoA a establecer
	 */
	public void setCargoEfectivoA(BigInteger cargoEfectivoA) {
		this.cargoEfectivoA = cargoEfectivoA;
	}

	/**
	 * Obtiene el valor del atributo institucionBancoDeTrabajo
	 * 
	 * @return el valor del atributo institucionBancoDeTrabajo
	 */
	public Institucion getInstitucionBancoDeTrabajo() {
		return institucionBancoDeTrabajo;
	}

	/**
	 * Establece el valor del atributo institucionBancoDeTrabajo
	 * 
	 * @param institucionBancoDeTrabajo
	 *            el valor del atributo institucionBancoDeTrabajo a establecer
	 */
	public void setInstitucionBancoDeTrabajo(Institucion institucionBancoDeTrabajo) {
		this.institucionBancoDeTrabajo = institucionBancoDeTrabajo;
	}

	/**
	 * Obtiene el valor del atributo idInstitucionBancoDeTrabajo
	 * 
	 * @return el valor del atributo idInstitucionBancoDeTrabajo
	 */
	public BigInteger getIdInstitucionBancoDeTrabajo() {
		return idInstitucionBancoDeTrabajo;
	}

	/**
	 * Establece el valor del atributo idInstitucionBancoDeTrabajo
	 * 
	 * @param idInstitucionBancoDeTrabajo
	 *            el valor del atributo idInstitucionBancoDeTrabajo a establecer
	 */
	public void setIdInstitucionBancoDeTrabajo(BigInteger idInstitucionBancoDeTrabajo) {
		this.idInstitucionBancoDeTrabajo = idInstitucionBancoDeTrabajo;
	}

	/**
	 * Obtiene el valor del atributo cuentaBancoDeTrabajo
	 * 
	 * @return el valor del atributo cuentaBancoDeTrabajo
	 */
	public CuentaNombrada getCuentaBancoDeTrabajo() {
		return cuentaBancoDeTrabajo;
	}

	/**
	 * Establece el valor del atributo cuentaBancoDeTrabajo
	 * 
	 * @param cuentaBancoDeTrabajo
	 *            el valor del atributo cuentaBancoDeTrabajo a establecer
	 */
	public void setCuentaBancoDeTrabajo(CuentaNombrada cuentaBancoDeTrabajo) {
		this.cuentaBancoDeTrabajo = cuentaBancoDeTrabajo;
	}

	/**
	 * Obtiene el valor del atributo idCuentaBancoDeTrabajo
	 * 
	 * @return el valor del atributo idCuentaBancoDeTrabajo
	 */
	public BigInteger getIdCuentaBancoDeTrabajo() {
		return idCuentaBancoDeTrabajo;
	}

	/**
	 * Establece el valor del atributo idCuentaBancoDeTrabajo
	 * 
	 * @param idCuentaBancoDeTrabajo
	 *            el valor del atributo idCuentaBancoDeTrabajo a establecer
	 */
	public void setIdCuentaBancoDeTrabajo(BigInteger idCuentaBancoDeTrabajo) {
		this.idCuentaBancoDeTrabajo = idCuentaBancoDeTrabajo;
	}

	/**
	 * Obtiene el valor del atributo idCuentaEfectivoTraspasante
	 * 
	 * @return el valor del atributo idCuentaEfectivoTraspasante
	 */
	public BigInteger getIdCuentaEfectivoTraspasante() {
		return idCuentaEfectivoTraspasante;
	}

	/**
	 * Establece el valor del atributo idCuentaEfectivoTraspasante
	 * 
	 * @param idCuentaEfectivoTraspasante
	 *            el valor del atributo idCuentaEfectivoTraspasante a establecer
	 */
	public void setIdCuentaEfectivoTraspasante(BigInteger idCuentaEfectivoTraspasante) {
		this.idCuentaEfectivoTraspasante = idCuentaEfectivoTraspasante;
	}

	/**
	 * Obtiene el valor del atributo cuentaEfectivoTraspasante
	 * 
	 * @return el valor del atributo cuentaEfectivoTraspasante
	 */
	public CuentaNombrada getCuentaEfectivoTraspasante() {
		return cuentaEfectivoTraspasante;
	}

	/**
	 * Establece el valor del atributo cuentaEfectivoTraspasante
	 * 
	 * @param cuentaEfectivoTraspasante
	 *            el valor del atributo cuentaEfectivoTraspasante a establecer
	 */
	public void setCuentaEfectivoTraspasante(CuentaNombrada cuentaEfectivoTraspasante) {
		this.cuentaEfectivoTraspasante = cuentaEfectivoTraspasante;
	}

	/**
	 * Obtiene el valor del atributo idCuentaEfectivoReceptor
	 * 
	 * @return el valor del atributo idCuentaEfectivoReceptor
	 */
	public BigInteger getIdCuentaEfectivoReceptor() {
		return idCuentaEfectivoReceptor;
	}

	/**
	 * Establece el valor del atributo idCuentaEfectivoReceptor
	 * 
	 * @param idCuentaEfectivoReceptor
	 *            el valor del atributo idCuentaEfectivoReceptor a establecer
	 */
	public void setIdCuentaEfectivoReceptor(BigInteger idCuentaEfectivoReceptor) {
		this.idCuentaEfectivoReceptor = idCuentaEfectivoReceptor;
	}

	/**
	 * Obtiene el valor del atributo cuentaEfectivoReceptor
	 * 
	 * @return el valor del atributo cuentaEfectivoReceptor
	 */
	public CuentaNombrada getCuentaEfectivoReceptor() {
		return cuentaEfectivoReceptor;
	}

	/**
	 * Establece el valor del atributo cuentaEfectivoReceptor
	 * 
	 * @param cuentaEfectivoReceptor
	 *            el valor del atributo cuentaEfectivoReceptor a establecer
	 */
	public void setCuentaEfectivoReceptor(CuentaNombrada cuentaEfectivoReceptor) {
		this.cuentaEfectivoReceptor = cuentaEfectivoReceptor;
	}

	/**
	 * Obtiene el valor del atributo idCuentaBovedaEfectivo
	 * 
	 * @return el valor del atributo idCuentaBovedaEfectivo
	 */
	public BigInteger getIdCuentaBovedaEfectivo() {
		return idCuentaBovedaEfectivo;
	}

	/**
	 * Establece el valor del atributo idCuentaBovedaEfectivo
	 * 
	 * @param idCuentaBovedaEfectivo
	 *            el valor del atributo idCuentaBovedaEfectivo a establecer
	 */
	public void setIdCuentaBovedaEfectivo(BigInteger idCuentaBovedaEfectivo) {
		this.idCuentaBovedaEfectivo = idCuentaBovedaEfectivo;
	}

	/**
	 * Obtiene el valor del atributo cuentaBovedaEfectivo
	 * 
	 * @return el valor del atributo cuentaBovedaEfectivo
	 */
	public CuentaNombrada getCuentaBovedaEfectivo() {
		return cuentaBovedaEfectivo;
	}

	/**
	 * Establece el valor del atributo cuentaBovedaEfectivo
	 * 
	 * @param cuentaBovedaEfectivo
	 *            el valor del atributo cuentaBovedaEfectivo a establecer
	 */
	public void setCuentaBovedaEfectivo(CuentaNombrada cuentaBovedaEfectivo) {
		this.cuentaBovedaEfectivo = cuentaBovedaEfectivo;
	}

	/**
	 * Obtiene el valor del atributo posicionNombrada
	 * 
	 * @return el valor del atributo posicionNombrada
	 */
	public PosicionNombradaHistorico getPosicionNombrada() {
		return posicionNombrada;
	}

	/**
	 * Establece el valor del atributo posicionNombrada
	 * 
	 * @param posicionNombrada
	 *            el valor del atributo posicionNombrada a establecer
	 */
	public void setPosicionNombrada(PosicionNombradaHistorico posicionNombrada) {
		this.posicionNombrada = posicionNombrada;
	}

	/**
	 * Obtiene el valor del atributo posicionReceptor
	 * 
	 * @return el valor del atributo posicionReceptor
	 */
	public PosicionNombradaHistorico getPosicionReceptor() {
		return posicionReceptor;
	}

	/**
	 * Establece el valor del atributo posicionReceptor
	 * 
	 * @param posicionReceptor
	 *            el valor del atributo posicionReceptor a establecer
	 */
	public void setPosicionReceptor(PosicionNombradaHistorico posicionReceptor) {
		this.posicionReceptor = posicionReceptor;
	}

	/**
	 * Obtiene el valor del atributo posicionTraspasante
	 * 
	 * @return el valor del atributo posicionTraspasante
	 */
	public PosicionNombradaHistorico getPosicionTraspasante() {
		return posicionTraspasante;
	}

	/**
	 * Establece el valor del atributo posicionTraspasante
	 * 
	 * @param posicionTraspasante
	 *            el valor del atributo posicionTraspasante a establecer
	 */
	public void setPosicionTraspasante(PosicionNombradaHistorico posicionTraspasante) {
		this.posicionTraspasante = posicionTraspasante;
	}

	/**
	 * Obtiene el valor del atributo idPosicionNombrada
	 * 
	 * @return el valor del atributo idPosicionNombrada
	 */
	public BigInteger getIdPosicionNombrada() {
		return idPosicionNombrada;
	}

	/**
	 * Establece el valor del atributo idPosicionNombrada
	 * 
	 * @param idPosicionNombrada
	 *            el valor del atributo idPosicionNombrada a establecer
	 */
	public void setIdPosicionNombrada(BigInteger idPosicionNombrada) {
		this.idPosicionNombrada = idPosicionNombrada;
	}

	/**
	 * Obtiene el valor del atributo numeroTitulos
	 * 
	 * @return el valor del atributo numeroTitulos
	 */
	public BigInteger getNumeroTitulos() {
		return numeroTitulos;
	}

	/**
	 * Establece el valor del atributo numeroTitulos
	 * 
	 * @param numeroTitulos
	 *            el valor del atributo numeroTitulos a establecer
	 */
	public void setNumeroTitulos(BigInteger numeroTitulos) {
		this.numeroTitulos = numeroTitulos;
	}

	/**
	 * Obtiene el valor del atributo precio
	 * 
	 * @return el valor del atributo precio
	 */
	public BigDecimal getPrecio() {
		return precio;
	}

	/**
	 * Establece el valor del atributo precio
	 * 
	 * @param precio
	 *            el valor del atributo precio a establecer
	 */
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	/**
	 * Obtiene el valor del atributo participanteOrigen
	 * 
	 * @return el valor del atributo participanteOrigen
	 */
	public String getParticipanteOrigen() {
		return participanteOrigen;
	}

	/**
	 * Establece el valor del atributo participanteOrigen
	 * 
	 * @param participanteOrigen
	 *            el valor del atributo participanteOrigen a establecer
	 */
	public void setParticipanteOrigen(String participanteOrigen) {
		this.participanteOrigen = participanteOrigen;
	}

	/**
	 * Obtiene el valor del atributo cargoValoresA
	 * 
	 * @return el valor del atributo cargoValoresA
	 */
	public BigInteger getCargoValoresA() {
		return cargoValoresA;
	}

	/**
	 * Establece el valor del atributo cargoValoresA
	 * 
	 * @param cargoValoresA
	 *            el valor del atributo cargoValoresA a establecer
	 */
	public void setCargoValoresA(BigInteger cargoValoresA) {
		this.cargoValoresA = cargoValoresA;
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
	 *            el valor del atributo idCupon a establecer
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
	 *            el valor del atributo cupon a establecer
	 */
	public void setCupon(Cupon cupon) {
		this.cupon = cupon;
	}

	/**
	 * Obtiene el valor del atributo cuentaBovedaValores
	 * 
	 * @return el valor del atributo cuentaBovedaValores
	 */
	public CuentaNombrada getCuentaBovedaValores() {
		return cuentaBovedaValores;
	}

	/**
	 * Establece el valor del atributo cuentaBovedaValores
	 * 
	 * @param cuentaBovedaValores
	 *            el valor del atributo cuentaBovedaValores a establecer
	 */
	public void setCuentaBovedaValores(CuentaNombrada cuentaBovedaValores) {
		this.cuentaBovedaValores = cuentaBovedaValores;
	}

	/**
	 * Obtiene el valor del atributo idCuentaBovedaValores
	 * 
	 * @return el valor del atributo idCuentaBovedaValores
	 */
	public BigInteger getIdCuentaBovedaValores() {
		return idCuentaBovedaValores;
	}

	/**
	 * Establece el valor del atributo idCuentaBovedaValores
	 * 
	 * @param idCuentaBovedaValores
	 *            el valor del atributo idCuentaBovedaValores a establecer
	 */
	public void setIdCuentaBovedaValores(BigInteger idCuentaBovedaValores) {
		this.idCuentaBovedaValores = idCuentaBovedaValores;
	}

	/**
	 * Obtiene el valor del atributo bovedaValores
	 * 
	 * @return el valor del atributo bovedaValores
	 */
	public Boveda getBovedaValores() {
		return bovedaValores;
	}

	/**
	 * Establece el valor del atributo bovedaValores
	 * 
	 * @param bovedaValores
	 *            el valor del atributo bovedaValores a establecer
	 */
	public void setBovedaValores(Boveda bovedaValores) {
		this.bovedaValores = bovedaValores;
	}

	/**
	 * Obtiene el valor del atributo idBovedaValores
	 * 
	 * @return el valor del atributo idBovedaValores
	 */
	public BigInteger getIdBovedaValores() {
		return idBovedaValores;
	}

	/**
	 * Establece el valor del atributo idBovedaValores
	 * 
	 * @param idBovedaValores
	 *            el valor del atributo idBovedaValores a establecer
	 */
	public void setIdBovedaValores(BigInteger idBovedaValores) {
		this.idBovedaValores = idBovedaValores;
	}

	/**
	 * Obtiene el valor del atributo emision
	 * 
	 * @return el valor del atributo emision
	 */
	public Emision getEmision() {
		return emision;
	}

	/**
	 * Establece el valor del atributo emision
	 * 
	 * @param emision
	 *            el valor del atributo emision a establecer
	 */
	public void setEmision(Emision emision) {
		this.emision = emision;
	}

	/**
	 * Obtiene el valor del atributo idEmision
	 * 
	 * @return el valor del atributo idEmision
	 */
	public BigInteger getIdEmision() {
		return idEmision;
	}

	/**
	 * Establece el valor del atributo idEmision
	 * 
	 * @param idEmision
	 *            el valor del atributo idEmision a establecer
	 */
	public void setIdEmision(BigInteger idEmision) {
		this.idEmision = idEmision;
	}

}
