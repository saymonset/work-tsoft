/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class TotalesEstadoCuentaVO extends AbstractBaseDTO {

    /** Constante de Serializacion */
    private static final long serialVersionUID = 1L;

    private BigInteger totalOperaciones;

    private BigDecimal saldoInicial;

    private BigDecimal saldoActual;

    private BigInteger totalEntradas;

    private BigInteger totalSalidas;

    private BigInteger saldoCalculado;

    /**
     * Constructor
     */
    public TotalesEstadoCuentaVO() {
        this.totalOperaciones = BIG_INTEGER_ZERO;
        this.saldoInicial = BIG_DECIMAL_ZERO;
        this.saldoActual = BIG_DECIMAL_ZERO;
        this.totalEntradas = BIG_INTEGER_ZERO;
        this.totalSalidas = BIG_INTEGER_ZERO;
        this.saldoCalculado = BIG_INTEGER_ZERO;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getSaldoActual() {
        return saldoActual;
    }

    /**
     * @param saldoActual
     */
    public void setSaldoActual(BigDecimal saldoActual) {
        this.saldoActual = saldoActual;
    }

    /**
     * @return BigInteger
     */
    public BigInteger getSaldoCalculado() {
        return saldoCalculado;
    }

    /**
     * @param saldoCalculado
     */
    public void setSaldoCalculado(BigInteger saldoCalculado) {
        this.saldoCalculado = saldoCalculado;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    /**
     * @param saldoInicial
     */
    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    /**
     * @return BigInteger
     */
    public BigInteger getTotalEntradas() {
        return totalEntradas;
    }

    /**
     * @param totalEntradas
     */
    public void setTotalEntradas(BigInteger totalEntradas) {
        this.totalEntradas = totalEntradas;
    }

    /**
     * @return BigInteger
     */
    public BigInteger getTotalOperaciones() {
        return totalOperaciones;
    }

    /**
     * @param totalOperaciones
     */
    public void setTotalOperaciones(BigInteger totalOperaciones) {
        this.totalOperaciones = totalOperaciones;
    }

    /**
     * @return BigInteger
     */
    public BigInteger getTotalSalidas() {
        return totalSalidas;
    }

    /**
     * @param totalSalidas
     */
    public void setTotalSalidas(BigInteger totalSalidas) {
        this.totalSalidas = totalSalidas;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object arg0, Errors arg1) {

    }

}
