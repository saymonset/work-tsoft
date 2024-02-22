package com.indeval.portalinternacional.middleware.servicios.vo;


import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

public class SaldoNombradaIntVO implements Serializable{
    private String divisa;
    private String boveda;
    private BigDecimal idCuenta;
    private BigDecimal idSaldo;
    private BigDecimal saldoDisponible;
    private BigDecimal saldoNoDisponible;

    public String getDivisa() {
        return divisa;
    }

    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }

    public String getBoveda() {
        return boveda;
    }

    public void setBoveda(String boveda) {
        this.boveda = boveda;
    }

    public BigDecimal getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(BigDecimal idCuenta) {
        this.idCuenta = idCuenta;
    }

    public BigDecimal getIdSaldo() {
        return idSaldo;
    }

    public void setIdSaldo(BigDecimal idSaldo) {
        this.idSaldo = idSaldo;
    }

    public BigDecimal getSaldoDisponible() {
        return saldoDisponible;
    }

    public void setSaldoDisponible(BigDecimal saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    public BigDecimal getSaldoNoDisponible() {
        return saldoNoDisponible;
    }

    public void setSaldoNoDisponible(BigDecimal saldoNoDisponible) {
        this.saldoNoDisponible = saldoNoDisponible;
    }
}
