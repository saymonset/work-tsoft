/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.divisioninternacional;

import java.math.BigDecimal;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ValoresOperacionVO extends AbstractBaseDTO {

    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;

    private BigDecimal precioVector;

    private BigDecimal cantidadOperada;

    private BigDecimal saldoDisponible;

    private BigDecimal posicionActual;

    private String instrucciones;

    /**
     * @return BigDecimal
     */
    public BigDecimal getCantidadOperada() {
        return cantidadOperada;
    }

    /**
     * @param cantidadOperada
     */
    public void setCantidadOperada(BigDecimal cantidadOperada) {
        this.cantidadOperada = cantidadOperada;
    }

    /**
     * @return String
     */
    public String getInstrucciones() {
        return instrucciones;
    }

    /**
     * @param instrucciones
     */
    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getPosicionActual() {
        return posicionActual;
    }

    /**
     * @param posicionActual
     */
    public void setPosicionActual(BigDecimal posicionActual) {
        this.posicionActual = posicionActual;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getPrecioVector() {
        return precioVector;
    }

    /**
     * @param precioVector
     */
    public void setPrecioVector(BigDecimal precioVector) {
        this.precioVector = precioVector;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getSaldoDisponible() {
        return saldoDisponible;
    }

    /**
     * @param saldoDisponible
     */
    public void setSaldoDisponible(BigDecimal saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {

    }
    
}
