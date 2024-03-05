/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import java.math.BigDecimal;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class PosicionValorVO extends AbstractBaseDTO {

    /** Constante de Serializacion */
    private static final long serialVersionUID = 1L;

    private EmisionVO claveValor;

    private String CuponCortado;

    private BigDecimal saldoDisponible;

    private BigDecimal ultimoHecho;

    private BigDecimal valorNominal;

    private BigDecimal valuacionMercado;

    private BigDecimal valuacionNominal;

    private AgenteVO agente;

    /**
     * @return the agente
     */
    public AgenteVO getAgente() {
        return agente;
    }

    /**
     * @param agente
     *            the agente to set
     */
    public void setAgente(AgenteVO agente) {
        this.agente = agente;
    }

    /**
     * @return EmisionVO
     */
    public EmisionVO getClaveValor() {
        return claveValor;
    }

    /**
     * @param claveValor
     */
    public void setClaveValor(EmisionVO claveValor) {
        this.claveValor = claveValor;
    }

    /**
     * @return String
     */
    public String getCuponCortado() {
        return CuponCortado;
    }

    /**
     * @param cuponCortado
     */
    public void setCuponCortado(String cuponCortado) {
        CuponCortado = cuponCortado;
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
     * @return BigDecimal
     */
    public BigDecimal getUltimoHecho() {
        return ultimoHecho;
    }

    /**
     * @param ultimoHecho
     */
    public void setUltimoHecho(BigDecimal ultimoHecho) {
        this.ultimoHecho = clonaBigDecimal(ultimoHecho);
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getValorNominal() {
        return valorNominal;
    }

    /**
     * @param valorNominal
     */
    public void setValorNominal(BigDecimal valorNominal) {
        this.valorNominal = clonaBigDecimal(valorNominal);
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getValuacionMercado() {
        return valuacionMercado;
    }

    /**
     * @param valuacionMercado
     */
    public void setValuacionMercado(BigDecimal valuacionMercado) {
        this.valuacionMercado = clonaBigDecimal(valuacionMercado);
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getValuacionNominal() {
        return valuacionNominal;
    }

    /**
     * @param valuacionNominal
     */
    public void setValuacionNominal(BigDecimal valuacionNominal) {
        this.valuacionNominal = clonaBigDecimal(valuacionNominal);
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object arg0, Errors arg1) {

    }

}
