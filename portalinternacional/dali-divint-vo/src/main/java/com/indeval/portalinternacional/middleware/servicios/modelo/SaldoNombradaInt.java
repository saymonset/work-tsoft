package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;

@Entity(name = "SaldoNombradaInt")
@Table(name = "T_SALDO_NOMBRADA")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_SALDO_NOMBRADA", allocationSize = 1, initialValue = 1)
public class SaldoNombradaInt implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador del saldo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
    @Column(name = "ID_SALDO")
    private BigInteger idSaldo;

    /**
     * Cuenta del saldo.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CUENTA", updatable = false, insertable = false)
    private CuentaNombrada cuentaNombrada;

    /**
     * Identificador de la cuenta del saldo.
     */
    @Column(name = "ID_CUENTA")
    private BigInteger idCuenta;

    /**
     * B&oacute;veda del saldo.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_BOVEDA", updatable = false, insertable = false)
    private Boveda boveda;

    /**
     * Identificador de la b&oacute;veda del saldo.
     */
    @Column(name = "ID_BOVEDA")
    private BigInteger idBoveda;

    /**
     * Divisa del saldo.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DIVISA", updatable = false, insertable = false)
    private DivisaInt divisa;

    /**
     * Identificador de la divisa del saldo.
     */
    @Column(name = "ID_DIVISA")
    private BigInteger idDivisa;

    /**
     * Saldo disponible.
     */
    @Column(name = "SALDO_DISPONIBLE")
    private BigDecimal saldoDisponible;

    /**
     * Saldo no disponible.
     */
    @Column(name = "SALDO_NO_DISPONIBLE")
    private BigDecimal saldoNoDisponible;

    /**
     * Identificador del saldo.
     * 
     * @return BigInteger
     */
    public BigInteger getIdSaldo() {
        return idSaldo;
    }

    /**
     * Identificador del saldo.
     * 
     * @param idSaldo
     */
    public void setIdSaldo(BigInteger idSaldo) {
        this.idSaldo = idSaldo;
    }

    /**
     * Identificador de la cuentaNombrada.
     * 
     * @return CuentaNombrada
     */
    public CuentaNombrada getCuentaNombrada() {
        return cuentaNombrada;
    }

    /**
     * Identificador de la cuentaNombrada.
     * 
     * @param cuentaNombrada
     */
    public void setCuentaNombrada(CuentaNombrada cuentaNombrada) {
        this.cuentaNombrada = cuentaNombrada;
    }

    /**
     * Identificador de la b&oacute;veda.
     * 
     * @return Boveda
     */
    public Boveda getBoveda() {
        return boveda;
    }

    /**
     * Identificador de la b&oacute;veda.
     * 
     * @param boveda
     */
    public void setBoveda(Boveda boveda) {
        this.boveda = boveda;
    }

    /**
     * Identificador de la divisa.
     * 
     * @return Divisa
     */
    public DivisaInt getDivisa() {
        return divisa;
    }

    /**
     * Identificador de la divisa.
     * 
     * @param divisa
     */
    public void setDivisa(DivisaInt divisa) {
        this.divisa = divisa;
    }

    /**
     * Saldo disponible de la cuentaNombrada.
     * 
     * @return BigDecimal
     */
    public BigDecimal getSaldoDisponible() {
        return saldoDisponible;
    }

    /**
     * Saldo disponible de la cuentaNombrada.
     * 
     * @param saldoDisponible
     */
    public void setSaldoDisponible(BigDecimal saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    /**
     * Saldo no disponible de la cuentaNombrada.
     * 
     * @return BigDecimal
     */
    public BigDecimal getSaldoNoDisponible() {
        return saldoNoDisponible;
    }

    /**
     * Saldo no disponible de la cuentaNombrada.
     * 
     * @param saldoNoDisponible
     */
    public void setSaldoNoDisponible(BigDecimal saldoNoDisponible) {
        this.saldoNoDisponible = saldoNoDisponible;
    }

    /**
     * Identificador de la b&oacute;veda.
     * 
     * @return the idBoveda
     */
    public BigInteger getIdBoveda() {
        return idBoveda;
    }

    /**
     * Identificador de la b&oacute;veda.
     * 
     * @param idBoveda
     *            the idBoveda to set
     */
    public void setIdBoveda(BigInteger idBoveda) {
        this.idBoveda = idBoveda;
    }

    /**
     * Identificador de la cuenta.
     * 
     * @return the idCuenta
     */
    public BigInteger getIdCuenta() {
        return idCuenta;
    }

    /**
     * Identificador de la cuenta.
     * 
     * @param idCuenta
     *            the idCuenta to set
     */
    public void setIdCuenta(BigInteger idCuenta) {
        this.idCuenta = idCuenta;
    }

    /**
     * Identificador de la divisa.
     * 
     * @return the idDivisa
     */
    public BigInteger getIdDivisa() {
        return idDivisa;
    }

    /**
     * Identificador de la divisa.
     * 
     * @param idDivisa
     *            the idDivisa to set
     */
    public void setIdDivisa(BigInteger idDivisa) {
        this.idDivisa = idDivisa;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(51, 71).append(idSaldo).toHashCode();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SaldoNombradaInt == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        SaldoNombradaInt rhs = (SaldoNombradaInt) obj;
        return new EqualsBuilder().append(idSaldo, rhs.getIdSaldo()).isEquals();
    }

}
