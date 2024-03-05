/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * En esta tabla se registran las operaciones generadas a partir de las
 * operaciones recibidas en el bus. Estas operaciones se generan a partir.
 * 
 * @author rchavez
 * @version 1.0
 */
@Entity
@Table(name = "T_OPERACION_NOMBRADA")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_OPERACION_NOMBRADA", allocationSize = 1, initialValue = 1)
public class OperacionNombrada implements Serializable, Cloneable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador de la operaci&oacute;n.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
    @Column(name = "ID_OPERACION")
    private BigInteger idOperacion;

    /**
     * Instrucci&oacute;n liquidaci&oacute;n a la que pertenece la
     * operaci&oacute;n.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_INSTRUCCION")
    private InstruccionLiquidacion instruccion;

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
    @JoinColumn(name = "ID_BOVEDA_EFECTIVO",  updatable = false, insertable = false)
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_POSICION_TRASPASANTE", updatable = false, insertable = false)
    private PosicionNombrada posicionNombrada;

    /**
     * Posici&oacute;n nombrada que referencia el registro.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_POSICION_RECEPTOR", updatable = false, insertable = false)
    public PosicionNombrada posicionReceptor;

    /**
     * Posici&oacute;n nombrada que referencia el registro.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_POSICION_TRASPASANTE", updatable = false, insertable = false)
    public PosicionNombrada posicionTraspasante;

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
    //@Column(name = "PARTICIPANTE_ORIGEN")
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
    @JoinColumn(name = "ID_BOVEDA_VALORES", updatable = false, insertable = false)
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
     * Relaci&oacute;n de los registros contables de efectivo de cuentas
     * controladas ligados a una operaci&oacute;n.
     */
    @ManyToMany(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "operaciones", targetEntity = RegContEfecControlada.class)
    private Set<RegContEfecControlada> registrosContablesEfectivo;

    /**
     * Relaci&oacute;n de los registros contables de valores de cuentas
     * controladas ligados a una operaci&oacute;n.
     */
    @ManyToMany(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "operaciones", targetEntity = RegContValControlada.class)
    private Set<RegContValControlada> registrosContablesValores;

    /**
     * Obtiene el campo idCuentaBovedaEfectivo
     * 
     * @return idCuentaBovedaEfectivo
     */
    public BigInteger getIdCuentaBovedaEfectivo() {
        return idCuentaBovedaEfectivo;
    }

    /**
     * Asigna el campo idCuentaBovedaEfectivo
     * 
     * @param idCuentaBovedaEfectivo
     *            el valor de idCuentaBovedaEfectivo a asignar
     */
    public void setIdCuentaBovedaEfectivo(BigInteger idCuentaBovedaEfectivo) {
        this.idCuentaBovedaEfectivo = idCuentaBovedaEfectivo;
    }

    /**
     * Obtiene el campo cuentaBovedaEfectivo
     * 
     * @return cuentaBovedaEfectivo
     */
    public CuentaNombrada getCuentaBovedaEfectivo() {
        return cuentaBovedaEfectivo;
    }

    /**
     * Asigna el campo cuentaBovedaEfectivo
     * 
     * @param cuentaBovedaEfectivo
     *            el valor de cuentaBovedaEfectivo a asignar
     */
    public void setCuentaBovedaEfectivo(CuentaNombrada cuentaBovedaEfectivo) {
        this.cuentaBovedaEfectivo = cuentaBovedaEfectivo;
    }

    /**
     * Obtiene el campo posicionNombrada
     * 
     * @return posicionNombrada
     */
    public PosicionNombrada getPosicionNombrada() {
        return posicionNombrada;
    }

    /**
     * Asigna el campo posicionNombrada
     * 
     * @param posicionNombrada
     *            el valor de posicionNombrada a asignar
     */
    public void setPosicionNombrada(PosicionNombrada posicionNombrada) {
        this.posicionNombrada = posicionNombrada;
    }

    /**
     * Obtiene el campo idPosicionNombrada
     * 
     * @return idPosicionNombrada
     */
    public BigInteger getIdPosicionNombrada() {
        return idPosicionNombrada;
    }

    /**
     * Asigna el campo idPosicionNombrada
     * 
     * @param idPosicionNombrada
     *            el valor de idPosicionNombrada a asignar
     */
    public void setIdPosicionNombrada(BigInteger idPosicionNombrada) {
        this.idPosicionNombrada = idPosicionNombrada;
    }

    /**
     * Obtiene el campo numeroTitulos
     * 
     * @return numeroTitulos
     */
    public BigInteger getNumeroTitulos() {
        return numeroTitulos;
    }

    /**
     * Asigna el campo numeroTitulos
     * 
     * @param numeroTitulos
     *            el valor de numeroTitulos a asignar
     */
    public void setNumeroTitulos(BigInteger numeroTitulos) {
        this.numeroTitulos = numeroTitulos;
    }

    /**
     * Obtiene el campo precio
     * 
     * @return precio
     */
    public BigDecimal getPrecio() {
        return precio;
    }

    /**
     * Asigna el campo precio
     * 
     * @param precio
     *            el valor de precio a asignar
     */
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    /**
     * Obtiene el campo participanteOrigen
     * 
     * @return participanteOrigen
     */
    public String getParticipanteOrigen() {
        return participanteOrigen;
    }

    /**
     * Asigna el campo participanteOrigen
     * 
     * @param participanteOrigen
     *            el valor de participanteOrigen a asignar
     */
    public void setParticipanteOrigen(String participanteOrigen) {
        this.participanteOrigen = participanteOrigen;
    }

    /**
     * Obtiene el campo cargoValoresA
     * 
     * @return cargoValoresA
     */
    public BigInteger getCargoValoresA() {
        return cargoValoresA;
    }

    /**
     * Asigna el campo cargoValoresA
     * 
     * @param cargoValoresA
     *            el valor de cargoValoresA a asignar
     */
    public void setCargoValoresA(BigInteger cargoValoresA) {
        this.cargoValoresA = cargoValoresA;
    }

    /**
     * Obtiene el campo idCupon
     * 
     * @return idCupon
     */
    public BigInteger getIdCupon() {
        return idCupon;
    }

    /**
     * Asigna el campo idCupon
     * 
     * @param idCupon
     *            el valor de idCupon a asignar
     */
    public void setIdCupon(BigInteger idCupon) {
        this.idCupon = idCupon;
    }

    /**
     * Obtiene el campo cupon
     * 
     * @return cupon
     */
    public Cupon getCupon() {
        return cupon;
    }

    /**
     * Asigna el campo cupon
     * 
     * @param cupon
     *            el valor de cupon a asignar
     */
    public void setCupon(Cupon cupon) {
        this.cupon = cupon;
    }

    /**
     * Obtiene el campo cuentaBovedaValores
     * 
     * @return cuentaBovedaValores
     */
    public CuentaNombrada getCuentaBovedaValores() {
        return cuentaBovedaValores;
    }

    /**
     * Asigna el campo cuentaBovedaValores
     * 
     * @param cuentaBovedaValores
     *            el valor de cuentaBovedaValores a asignar
     */
    public void setCuentaBovedaValores(CuentaNombrada cuentaBovedaValores) {
        this.cuentaBovedaValores = cuentaBovedaValores;
    }

    /**
     * Obtiene el campo idCuentaBovedaValores
     * 
     * @return idCuentaBovedaValores
     */
    public BigInteger getIdCuentaBovedaValores() {
        return idCuentaBovedaValores;
    }

    /**
     * Asigna el campo idCuentaBovedaValores
     * 
     * @param idCuentaBovedaValores
     *            el valor de idCuentaBovedaValores a asignar
     */
    public void setIdCuentaBovedaValores(BigInteger idCuentaBovedaValores) {
        this.idCuentaBovedaValores = idCuentaBovedaValores;
    }

    /**
     * Obtiene el campo bovedaValores
     * 
     * @return bovedaValores
     */
    public Boveda getBovedaValores() {
        return bovedaValores;
    }

    /**
     * Asigna el campo bovedaValores
     * 
     * @param bovedaValores
     *            el valor de bovedaValores a asignar
     */
    public void setBovedaValores(Boveda bovedaValores) {
        this.bovedaValores = bovedaValores;
    }

    /**
     * Obtiene el campo idBovedaValores
     * 
     * @return idBovedaValores
     */
    public BigInteger getIdBovedaValores() {
        return idBovedaValores;
    }

    /**
     * Asigna el campo idBovedaValores
     * 
     * @param idBovedaValores
     *            el valor de idBovedaValores a asignar
     */
    public void setIdBovedaValores(BigInteger idBovedaValores) {
        this.idBovedaValores = idBovedaValores;
    }

    /**
     * Obtiene el campo emision
     * 
     * @return emision
     */
    public Emision getEmision() {
        return emision;
    }

    /**
     * Asigna el campo emision
     * 
     * @param emision
     *            el valor de emision a asignar
     */
    public void setEmision(Emision emision) {
        this.emision = emision;
    }

    /**
     * Obtiene el campo idEmision
     * 
     * @return idEmision
     */
    public BigInteger getIdEmision() {
        return idEmision;
    }

    /**
     * Asigna el campo idEmision
     * 
     * @param idEmision
     *            el valor de idEmision a asignar
     */
    public void setIdEmision(BigInteger idEmision) {
        this.idEmision = idEmision;
    }

    /**
     * Obtiene el identificador de la operaci&oacute;n.
     * 
     * @return Identificador de la operaci&oacute;n <b>BigInteger</b>
     */
    public BigInteger getIdOperacion() {
        return idOperacion;
    }

    /**
     * Asigna el identificador de la operaci&oacute;n.
     * 
     * @param idOperacion
     *            Identificador de la operaci&oacute;n <b>BigInteger</b>
     */
    public void setIdOperacion(BigInteger idOperacion) {
        this.idOperacion = idOperacion;
    }

    /**
     * Obtiene el tipo de operaci&oacute;n.
     * 
     * @return Referencia a un objeto <b>TipoOperacion</b>
     */
    public TipoOperacion getTipoOperacion() {
        return tipoOperacion;
    }

    /**
     * Asigna el tipo de operaci&oacute;n.
     * 
     * @param tipoOperacion
     *            Referencia de un objeto <b>TipoOperacion</b>
     */
    public void setTipoOperacion(TipoOperacion tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    /**
     * Obtiene el folio de la operaci&oacute;n dentro de la instrucci&oacute;n
     * de liquidaci&oacute;n.
     * 
     * @return Folio de la operaci&oacute;n <b>BigInteger</b>
     */
    public BigInteger getFolioOperacion() {
        return folioOperacion;
    }

    /**
     * Asigna el folio de la operaci&oacute;n dentro de la instrucci&oacute;n de
     * liquidaci&oacute;n.
     * 
     * @param folioOperacion
     *            Folio de la operaci&oacute;n <b>BigInteger</b>
     */
    public void setFolioOperacion(BigInteger folioOperacion) {
        this.folioOperacion = folioOperacion;
    }

    /**
     * Obtiene la divisa de la operaci&oacute;n para los movimientos de
     * efectivo.
     * 
     * @return Divisa para los movimientos de efectivo <b>Divisa</b>
     */
    public Divisa getDivisa() {
        return divisa;
    }

    /**
     * Asigna la divisa de la operaci&oacute;n para los movimientos de efectivo.
     * 
     * @param divisa
     *            Divisa para los movimientos de efectivo <b>Divisa</b>
     */
    public void setDivisa(Divisa divisa) {
        this.divisa = divisa;
    }

    /**
     * Obtiene el identificador de la divisa de la operaci&oacute;n para los
     * movimientos de efectivo.
     * 
     * @return idDivisa Identificador de la divisa <b>BigInteger</b>
     */
    public BigInteger getIdDivisa() {
        return this.idDivisa;
    }

    /**
     * Asigna el identificador de la divisa de la operaci&oacute;n para los
     * movimientos de efectivo.
     * 
     * @param idDivisa
     *            Identificador de la divisa <b>BigInteger</b>
     */
    public void setIdDivisa(BigInteger idDivisa) {
        this.idDivisa = idDivisa;
    }

    /**
     * Obtiene el monto a liquidar para los movimientos de efectivo.
     * 
     * @return El monto a utilizar en los movimientos de efectivo <b>BigDecimal</b>
     */
    public BigDecimal getMonto() {
        return monto;
    }

    /**
     * Asigna el monto a liquidar para los movimientos de efectivo.
     * 
     * @param monto
     *            El monto a utilizar en los movimientos de efectivo
     *            <b>BigDecimal</b>
     */
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    /**
     * Obtiene el indicador de a que secci&oacute;n del saldo se cargan y abonan
     * los movimientos de efectivo. Si afecta al disponible es 1. Si afecta al
     * no disponible es 2.
     * 
     * @return Secci&oacute;n del saldo sobre la que se realiza el movimiento de
     *         efectivo
     */
    public BigInteger getCargoEfectivoA() {
        return cargoEfectivoA;
    }

    /**
     * Asigna el indicador de a que secci&oacute;n del saldo se cargan y abonan
     * los movimientos de efectivo. Si afecta al disponible es 1. Si afecta al
     * no disponible es 2.
     * 
     * @param cargoEfectivoA
     *            Secci&oacute;n del saldo sobre la que se realiza el movimiento
     *            de efectivo
     */
    public void setCargoEfectivoA(BigInteger cargoEfectivoA) {
        this.cargoEfectivoA = cargoEfectivoA;
    }

    /**
     * Obtiene la b&oacute;veda sobre la que se realizan los movimientos de
     * efectivo.
     * 
     * @return La b&oacute;veda de efectivo sobra la que se realizaran los
     *         movimientos de efectivo <b>Boveda</b>
     */
    public Boveda getBovedaEfectivo() {
        return bovedaEfectivo;
    }

    /**
     * Asigna la b&oacute;veda sobre la que se realizan los movimientos de
     * efectivo.
     * 
     * @param bovedaEfectivo
     *            La b&oacute;veda de efectivo sobra la que se realizaran los
     *            movimientos de efectivo <b>Boveda</b>
     */
    public void setBovedaEfectivo(Boveda bovedaEfectivo) {
        this.bovedaEfectivo = bovedaEfectivo;
    }

    /**
     * Obtiene la cuenta de valores del participante receptor.
     * 
     * @return the cuentaNombradaReceptor
     */
    public CuentaNombrada getCuentaNombradaReceptor() {
        return cuentaNombradaReceptor;
    }

    /**
     * Asigna la cuenta de valores del participante receptor.
     * 
     * @param cuentaNombradaReceptor
     *            the cuentaNombradaReceptor to set
     */
    public void setCuentaNombradaReceptor(CuentaNombrada cuentaNombradaReceptor) {
        this.cuentaNombradaReceptor = cuentaNombradaReceptor;
    }

    /**
     * Obtiene la cuenta de valores del participante traspasante.
     * 
     * @return the cuentaNombradaTraspasante
     */
    public CuentaNombrada getCuentaNombradaTraspasante() {
        return cuentaNombradaTraspasante;
    }

    /**
     * Asigna la cuenta de valores del participante traspasante.
     * 
     * @param cuentaNombradaTraspasante
     *            the cuentaNombradaTraspasante to set
     */
    public void setCuentaNombradaTraspasante(CuentaNombrada cuentaNombradaTraspasante) {
        this.cuentaNombradaTraspasante = cuentaNombradaTraspasante;
    }

    /**
     * Obtiene la instituci&oacute;n del participante receptor.
     * 
     * @return the institucionReceptor
     */
    public Institucion getInstitucionReceptor() {
        return institucionReceptor;
    }

    /**
     * Asigna la instituci&oacute;n del participante receptor.
     * 
     * @param institucionReceptor
     *            the institucionReceptor to set
     */
    public void setInstitucionReceptor(Institucion institucionReceptor) {
        this.institucionReceptor = institucionReceptor;
    }

    /**
     * Obtiene el identificador de la instituci&oacute;n del participante
     * receptor.
     * 
     * @return idInstitucionReceptor Identificador de la instituci&oacute;n
     *         <b>BigInteger</b>
     */
    public BigInteger getIdInstitucionReceptor() {
        return this.idInstitucionReceptor;
    }

    /**
     * Asigna el identificador de la instituci&oacute;n del participante
     * receptor.
     * 
     * @param idInstitucionReceptor
     *            Identificador de la instituci&oacute;n <b>BigInteger</b>
     */
    public void setIdInstitucionReceptor(BigInteger idInstitucionReceptor) {
        this.idInstitucionReceptor = idInstitucionReceptor;
    }

    /**
     * Obtiene la instituci&oacute;n del participante receptor.
     * 
     * @return the institucionTraspasante
     */
    public Institucion getInstitucionTraspasante() {
        return institucionTraspasante;
    }

    /**
     * Asigna la instituci&oacute;n del participante receptor.
     * 
     * @param institucionTraspasante
     *            the institucionTraspasante to set
     */
    public void setInstitucionTraspasante(Institucion institucionTraspasante) {
        this.institucionTraspasante = institucionTraspasante;
    }

    /**
     * Obtiene el identificador de la instituci&oacute;n del participante
     * receptor.
     * 
     * @return idInstitucionTraspasante Identificador de la instituci&oacute;n
     *         <b>BigInteger</b>
     */
    public BigInteger getIdInstitucionTraspasante() {
        return this.idInstitucionTraspasante;
    }

    /**
     * Asigna el identificador de la instituci&oacute;n del participante
     * receptor.
     * 
     * @param idInstitucionTraspasante
     *            Identificador de la instituci&oacute;n <b>BigInteger</b>
     */
    public void setIdInstitucionTraspasante(BigInteger idInstitucionTraspasante) {
        this.idInstitucionTraspasante = idInstitucionTraspasante;
    }

    /**
     * Obtiene la instrucci&oacute;n liquidaci&oacute;n a la que pertenece la
     * operaci&oacute;n.
     * 
     * @return the instruccion
     */
    public InstruccionLiquidacion getInstruccion() {
        return instruccion;
    }

    /**
     * Asigna la instrucci&oacute;n liquidaci&oacute;n a la que pertenece la
     * operaci&oacute;n.
     * 
     * @param instruccion
     *            the instruccion to set
     */
    public void setInstruccion(InstruccionLiquidacion instruccion) {
        this.instruccion = instruccion;
    }

    /**
     * Obtiene la instituci&oacute;n del banco de trabajo.
     * 
     * @return the BancoDeTrabajo
     */
    public Institucion getInstitucionBancoDeTrabajo() {
        return institucionBancoDeTrabajo;
    }

    /**
     * Asigna la instituci&oacute;n del banco de trabajo.
     * 
     * @param bancoDeTrabajo
     *            the bancoDeTrabajo to set
     */
    public void setInstitucionBancoDeTrabajo(Institucion bancoDeTrabajo) {
        this.institucionBancoDeTrabajo = bancoDeTrabajo;
    }

    /**
     * Obtiene identificador de la instituci&oacute;n del banco de trabajo.
     * 
     * @return idInstitucionBancoDeTrabajo Instituci&oacute;n del banco de
     *         trabajo <b>BigInteger</b>
     */
    public BigInteger getIdInstitucionBancoDeTrabajo() {
        return this.idInstitucionBancoDeTrabajo;
    }

    /**
     * Asigna identificador de la instituci&oacute;n del banco de trabajo.
     * 
     * @param idInstitucionBancoDeTrabajo
     *            Instituci&oacute;n del banco de trabajo <b>BigInteger</b>
     */
    public void setIdInstitucionBancoDeTrabajo(BigInteger idInstitucionBancoDeTrabajo) {
        this.idInstitucionBancoDeTrabajo = idInstitucionBancoDeTrabajo;
    }

    /**
     * Obtiene la cuenta del banco de trabajo.
     * 
     * @return the cuentaBancoDeTrabajo
     */
    public CuentaNombrada getCuentaBancoDeTrabajo() {
        return cuentaBancoDeTrabajo;
    }

    /**
     * Asigna la cuenta del banco de trabajo.
     * 
     * @param cuentaBancoDeTrabajo
     *            the cuentaBancoDeTrabajo to set
     */
    public void setCuentaBancoDeTrabajo(CuentaNombrada cuentaBancoDeTrabajo) {
        this.cuentaBancoDeTrabajo = cuentaBancoDeTrabajo;
    }

    /**
     * Obtiene el identificador del tipo de operaci&oacute;n.
     * 
     * @return the idtipoOperacion
     */
    public BigInteger getIdTipoOperacion() {
        return idTipoOperacion;
    }

    /**
     * Asigna el identificador del tipo de operaci&oacute;n.
     * 
     * @param idtipoOperacion
     *            the idtipoOperacion to set
     */
    public void setIdTipoOperacion(BigInteger idtipoOperacion) {
        this.idTipoOperacion = idtipoOperacion;
    }

    /**
     * Asigna los registros contables de efectivo que afectaron a una
     * operaci&oacute;n (normalmente solo ser&aacute; un registro contable el
     * que afecte a la operaci&oacute;).
     * 
     * @return Set<RegContEfecControlada>
     */
    public Set<RegContEfecControlada> getRegistrosContablesEfectivo() {
        return registrosContablesEfectivo;
    }

    /**
     * Asigna los registros contables de efectivo que afectaron a una
     * operaci&oacute;n (normalmente solo ser&aacute; un registro contable el
     * que afecte a la operaci&oacute;).
     * 
     * @param registrosContablesEfectivo
     */
    public void setRegistrosContablesEfectivo(Set<RegContEfecControlada> registrosContablesEfectivo) {
        this.registrosContablesEfectivo = registrosContablesEfectivo;
    }

    /**
     * Obtiene los registros contables de valores que afectaron a una
     * operaci&oacute;n (normalmente solo ser&aacute; un registro contable el
     * que afecte a la operaci&oacute;).
     * 
     * @return Set<RegContValControlada>
     */
    public Set<RegContValControlada> getRegistrosContablesValores() {
        return registrosContablesValores;
    }

    /**
     * Obtiene los registros contables de valores que afectaron a una
     * operaci&oacute;n (normalmente solo ser&aacute; un registro contable el
     * que afecte a la operaci&oacute;).
     * 
     * @param registrosContablesValores
     */
    public void setRegistrosContablesValores(Set<RegContValControlada> registrosContablesValores) {
        this.registrosContablesValores = registrosContablesValores;
    }

    /**
     * @return the idBovedaEfectivo
     */
    public BigInteger getIdBovedaEfectivo() {
        return this.idBovedaEfectivo;
    }

    /**
     * @param idBovedaEfectivo
     *            the idBovedaEfectivo to set
     */
    public void setIdBovedaEfectivo(BigInteger idBovedaEfectivo) {
        this.idBovedaEfectivo = idBovedaEfectivo;
    }

    /**
     * @return the idCuentaBancoDeTrabajo
     */
    public BigInteger getIdCuentaBancoDeTrabajo() {
        return this.idCuentaBancoDeTrabajo;
    }

    /**
     * @param idCuentaBancoDeTrabajo
     *            the idCuentaBancoDeTrabajo to set
     */
    public void setIdCuentaBancoDeTrabajo(BigInteger idCuentaBancoDeTrabajo) {
        this.idCuentaBancoDeTrabajo = idCuentaBancoDeTrabajo;
    }

    /**
     * @return the idCuentaNombradaReceptor
     */
    public BigInteger getIdCuentaNombradaReceptor() {
        return this.idCuentaNombradaReceptor;
    }

    /**
     * @param idCuentaNombradaReceptor
     *            the idCuentaNombradaReceptor to set
     */
    public void setIdCuentaNombradaReceptor(BigInteger idCuentaNombradaReceptor) {
        this.idCuentaNombradaReceptor = idCuentaNombradaReceptor;
    }

    /**
     * @return the idCuentaNombradaTraspasante
     */
    public BigInteger getIdCuentaNombradaTraspasante() {
        return this.idCuentaNombradaTraspasante;
    }

    /**
     * @param idCuentaNombradaTraspasante
     *            the idCuentaNombradaTraspasante to set
     */
    public void setIdCuentaNombradaTraspasante(BigInteger idCuentaNombradaTraspasante) {
        this.idCuentaNombradaTraspasante = idCuentaNombradaTraspasante;
    }

    /**
     * @return the cuentaEfectivoReceptor
     */
    public CuentaNombrada getCuentaEfectivoReceptor() {
        return cuentaEfectivoReceptor;
    }

    /**
     * @param cuentaEfectivoReceptor
     *            the cuentaEfectivoReceptor to set
     */
    public void setCuentaEfectivoReceptor(CuentaNombrada cuentaEfectivoReceptor) {
        this.cuentaEfectivoReceptor = cuentaEfectivoReceptor;
    }

    /**
     * @return the cuentaEfectivoTraspasante
     */
    public CuentaNombrada getCuentaEfectivoTraspasante() {
        return cuentaEfectivoTraspasante;
    }

    /**
     * @param cuentaEfectivoTraspasante
     *            the cuentaEfectivoTraspasante to set
     */
    public void setCuentaEfectivoTraspasante(CuentaNombrada cuentaEfectivoTraspasante) {
        this.cuentaEfectivoTraspasante = cuentaEfectivoTraspasante;
    }

    /**
     * @return the idCuentaEfectivoReceptor
     */
    public BigInteger getIdCuentaEfectivoReceptor() {
        return idCuentaEfectivoReceptor;
    }

    /**
     * @param idCuentaEfectivoReceptor
     *            the idCuentaEfectivoReceptor to set
     */
    public void setIdCuentaEfectivoReceptor(BigInteger idCuentaEfectivoReceptor) {
        this.idCuentaEfectivoReceptor = idCuentaEfectivoReceptor;
    }

    /**
     * @return the idCuentaEfectivoTraspasante
     */
    public BigInteger getIdCuentaEfectivoTraspasante() {
        return idCuentaEfectivoTraspasante;
    }

    /**
     * @param idCuentaEfectivoTraspasante
     *            the idCuentaEfectivoTraspasante to set
     */
    public void setIdCuentaEfectivoTraspasante(BigInteger idCuentaEfectivoTraspasante) {
        this.idCuentaEfectivoTraspasante = idCuentaEfectivoTraspasante;
    }

    /**
     * @see java.lang.Object#hashCode()
     * @return Devuelve el hashCode de las variables idOperacion y
     *         folioOperacion, de un objeto OperacionNombrada.
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(43, 53).append(idOperacion).append(folioOperacion).toHashCode();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * @param obj
     *            El objeto OperacionNombrada a compearar
     * @return true si los objetos son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof OperacionNombrada)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        OperacionNombrada rhs = (OperacionNombrada) obj;
        return new EqualsBuilder().append(folioOperacion, rhs.getFolioOperacion()).isEquals();
    }

    /**
     * @see java.lang.Object#clone()
     */
    @Override
    public OperacionNombrada clone() {
        OperacionNombrada operacion = null;
        try {
            operacion = (OperacionNombrada) super.clone();
        }
        catch (CloneNotSupportedException e) {
            // No deberia ocurrir dado que se dio permiso explcito a la clase
            // para
            // permitir que los objetos instanciados a partir de ella sean
            // clonados
            // implementando java.lang.Cloneable
        }
        return operacion;
    }

	/**
	 * @return the posicionReceptor
	 */
	public PosicionNombrada getPosicionReceptor() {
		return posicionReceptor;
	}

	/**
	 * @param posicionReceptor the posicionReceptor to set
	 */
	public void setPosicionReceptor(PosicionNombrada posicionReceptor) {
		this.posicionReceptor = posicionReceptor;
	}

	/**
	 * @return the posicionTraspasante
	 */
	public PosicionNombrada getPosicionTraspasante() {
		return posicionTraspasante;
	}

	/**
	 * @param posicionTraspasante the posicionTraspasante to set
	 */
	public void setPosicionTraspasante(PosicionNombrada posicionTraspasante) {
		this.posicionTraspasante = posicionTraspasante;
	}

}