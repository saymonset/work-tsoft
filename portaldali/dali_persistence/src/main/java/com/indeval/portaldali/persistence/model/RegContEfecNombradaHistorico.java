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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Tabla que contiene las acciones (cargos y abonos) historicos que se realizaron sobre las
 * cuentas nombradas de efectivo.
 * 
 * T_REG_CONTABLE_EFE_NOMBRADA_H
 * 
 * @author rchavez
 * @version 1.0
 */
@Entity
@Table(name = "T_REG_CONTABLE_EFE_NOMBRADA_H")
public class RegContEfecNombradaHistorico implements Serializable {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador de la acci&oacute;n.
     */
    @Id
    @Column(name = "ID_REGISTRO_CONTABLE_H")
    private BigInteger idRegistroContable;

    @Column(name = "FECHA_CREACION")
    private Date fechaCreacion;

    /**
     * Identificador del saldo afectado.
     */
    @Column(name = "ID_SALDO", updatable = false)
    private BigInteger idSaldo;

    /** Saldo que afecta el registro contable */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
    	@JoinColumn(name = "ID_SALDO", nullable=true, insertable = false, updatable = false, referencedColumnName="ID_SALDO_NOMBRADA"),
    	@JoinColumn(name = "FECHA_CREACION", nullable=true, insertable = false, updatable = false, referencedColumnName="FECHA_CREACION")
    })
    private SaldoNombradaHistorico saldoNombradaHistorico;

    /**
     * Tipo de acci&oacutel;n aplicada al saldo.
     */
    @Column(name = "ID_TIPO_ACCION", updatable = false)
    private TipoAccion tipoAccion;

    /**
     * Importe aplicado al saldo.
     */
    @Column(name = "IMPORTE", updatable = false)
    private BigDecimal importe;

    /**
     * Fecha en que se afecto el saldo.
     */
    @Column(name = "FECHA", updatable = false)
    private Date fecha;

    /**
     * Identificador del ciclo durante el cual se realizo la afectaci&oacute;n.
     */
    @Column(name = "ID_CICLO", updatable = false)
    private BigInteger idCiclo;

    /**
     * Ciclo en el cual se realizo el registro contable
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CICLO", updatable = false, insertable = false)
    private Ciclo ciclo;

    /**
     * Identificador de la operaci&oacute;n que origino la afectaci&oacute;n.
     */
    @Column(name = "ID_OPERACION", updatable = false)
    private BigInteger idOperacion;

    /**
     * Operacion relacionada con el registro contable
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_OPERACION", updatable = false, insertable = false)
    private OperacionNombradaHistorico operacion;

    /**
     * Identificador de la cuenta del saldo afectado.
     */
    @Column(name = "ID_CUENTA", updatable = false)
    private BigInteger idCuenta;

    /**
     * Identifica si se afecto el disponible o el no disponible del saldo.
     */
    @Column(name = "CARGO_A", updatable = false)
    private BigInteger cargoA;

    /**
     * Identificador del registro contable de controladas donde participo este
     * registro.
     */
    @Column(name = "ID_REG_CONTABLE_CONTROLADA", updatable = false)
    private BigInteger idRegContableControlada;

    /**
     * Constructor para la clase RegContEfecNombrada.java
     */
    public RegContEfecNombradaHistorico() {
        super();
    }

    /**
     * Constructor.
     * 
     * @param idSaldo
     *            Identificador del saldo que esta&acute; siendo afectado
     * @param tipoAccion
     *            Tipo de acci&oacute;n a realizar. Puede ser un cargo o un
     *            abono
     * @param importe
     *            Importe a registrar
     * @param fecha
     *            Fecha en que se realiza el registro contable
     * @param idCiclo
     *            Identificador del ciclo que genera el registro contable
     * @param idOperacion
     *            Identificador de la operaci&oacute;n que genera el registro
     *            contable
     * @param idCuenta
     *            Identificador de la cuenta nombrada que se afectara
     * @param cargoA
     *            Indica si se carga al disponible (1) o al no disponible (2)
     * @param idRegContableControlada
     *            Identificador del registro contable de controlada
     */
    public RegContEfecNombradaHistorico(BigInteger idSaldo, TipoAccion tipoAccion, BigDecimal importe, Date fecha,
            BigInteger idCiclo, BigInteger idOperacion, BigInteger idCuenta, BigInteger cargoA, BigInteger idRegContableControlada) {
        super();
        this.idSaldo = idSaldo;
        this.tipoAccion = tipoAccion;
        this.importe = importe;
        this.fecha = fecha;
        this.idCiclo = idCiclo;
        this.idOperacion = idOperacion;
        this.idCuenta = idCuenta;
        this.cargoA = cargoA;
        this.idRegContableControlada = idRegContableControlada;
    }

    /**
     * Identificador del movimiento.
     * 
     * @return BigInteger
     */
    public BigInteger getIdRegistroContable() {
        return idRegistroContable;
    }

    /**
     * Identificador del movimiento.
     * 
     * @param idRegistroContable
     */
    public void setIdRegistroContable(BigInteger idRegistroContable) {
        this.idRegistroContable = idRegistroContable;
    }

    /**
     * Identificador de la posici&oacute;n afectada en la cuenta nombrada.
     * 
     * @return BigInteger
     */
    public BigInteger getIdSaldo() {
        return idSaldo;
    }

    /**
     * Identificador de la posici&oacute;n afectada en la cuenta nombrada.
     * 
     * @param idSaldo
     */
    public void setIdSaldo(BigInteger idSaldo) {
        this.idSaldo = idSaldo;
    }

    /**
     * Identificador del tipo de movimiento.
     * 
     * @return TipoAccion
     */
    public TipoAccion getTipoAccion() {
        return tipoAccion;
    }

    /**
     * Identificador del tipo de movimiento.
     * 
     * @param tipoAccion
     */
    public void setTipoAccion(TipoAccion tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    /**
     * Cantidad de efectivo que se movi&oacute;.
     * 
     * @return BigDecimal
     */
    public BigDecimal getImporte() {
        return importe;
    }

    /**
     * Cantidad de efectivo que se movi&oacute;.
     * 
     * @param importe
     */
    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    /**
     * Fecha en la que se realiz&oacute; el movimiento.
     * 
     * @return Date
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Fecha en la que se realiz&oacute; el movimiento.
     * 
     * @param fecha
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Identificador del ciclo de liquidaci&oacute;n.
     * 
     * @return the idCiclo
     */
    public BigInteger getIdCiclo() {
        return idCiclo;
    }

    /**
     * Identificador del ciclo de liquidaci&oacute;n.
     * 
     * @param idCiclo
     *            the idCiclo to set
     */
    public void setIdCiclo(BigInteger idCiclo) {
        this.idCiclo = idCiclo;
    }

    /**
     * Identificador de la operaci&oacute;n que origina el registro.
     * 
     * @return BigInteger
     */
    public BigInteger getIdOperacion() {
        return idOperacion;
    }

    /**
     * Identificador de la operaci&oacute;n que origina el registro.
     * 
     * @param idOperacion
     */
    public void setIdOperacion(BigInteger idOperacion) {
        this.idOperacion = idOperacion;
    }

    /**
     * Cuenta nombrada afectada en el registro contable.
     * 
     * @return the cuenta
     */
    public BigInteger getIdCuenta() {
        return idCuenta;
    }

    /**
     * Cuenta nombrada afectada en el registro contable.
     * 
     * @param idCuenta
     *            the cuentaNombrada to set
     */
    public void setIdCuenta(BigInteger idCuenta) {
        this.idCuenta = idCuenta;
    }

    /**
     * Indica si el cargo se hace al disponible o al no disponible.
     * 
     * @return BigInteger
     */
    public BigInteger getCargoA() {
        return cargoA;
    }

    /**
     * Indica si el cargo se hace al disponible o al no disponible.
     * 
     * @param cargoA
     */
    public void setCargoA(BigInteger cargoA) {
        this.cargoA = cargoA;
    }

    /**
     * @return the idRegContableControlada
     */
    public BigInteger getIdRegContableControlada() {
        return idRegContableControlada;
    }

    /**
     * @param idRegContableControlada
     *            the idRegContableControlada to set
     */
    public void setIdRegContableControlada(BigInteger idRegContableControlada) {
        this.idRegContableControlada = idRegContableControlada;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ("idRegistroContable ==>" + idRegistroContable + "\n" + "idSaldo ==>" + idSaldo
                + "\n" + "tipoAccion ==>" + tipoAccion + "\n" + "importe ==>" + importe + "\n"
                + "fecha ==>" + fecha + "\n" + "idCuenta==>" + idCuenta + "\n" + "idCiclo ==>"
                + idCiclo + "\n" + "cargoA ==>" + cargoA);
    }

    /**
     * @see java.lang.Object#hashCode()
     * @return Devuelve el hashCode de las variables idRegistroContable y
     *         idSaldo, de un objeto RegContEfecNombrada.
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(43, 53).append(idRegistroContable).append(idSaldo).toHashCode();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * @param obj
     *            El objeto RegContEfecNombrada a compearar
     * @return true si los objetos son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof RegContEfecNombradaHistorico)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        RegContEfecNombradaHistorico rhs = (RegContEfecNombradaHistorico) obj;
        return new EqualsBuilder().append(idRegistroContable, rhs.getIdRegistroContable())
                .isEquals();
    }

    /**
     * Obtiene el atributo saldoNombrada
     * 
     * @return El atrubuto saldoNombrada
     */
    public SaldoNombradaHistorico getSaldoNombradaHistorico() {
        return saldoNombradaHistorico;
    }

    /**
     * Establece la propiedad saldoNombrada
     *
     * @param saldoNombrada el campo saldoNombrada a establecer
     */
    public void setSaldoNombradaHistorico(SaldoNombradaHistorico saldoNombrada) {
        this.saldoNombradaHistorico = saldoNombrada;
    }

    /**
     * Obtiene el atributo ciclo
     *
     * @return El atrubuto ciclo
     */
    public Ciclo getCiclo() {
        return ciclo;
    }

    /**
     * Establece la propiedad ciclo
     *
     * @param ciclo el campo ciclo a establecer
     */
    public void setCiclo(Ciclo ciclo) {
        this.ciclo = ciclo;
    }

    /**
     * Obtiene el atributo operacion
     *
     * @return El atrubuto operacion
     */
    public OperacionNombradaHistorico getOperacion() {
        return operacion;
    }

    /**
     * Establece la propiedad operacion
     *
     * @param operacion el campo operacion a establecer
     */
    public void setOperacion(OperacionNombradaHistorico operacion) {
        this.operacion = operacion;
    }

	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
    
}
