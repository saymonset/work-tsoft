/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.divisioninternacional;

import java.math.BigDecimal;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class PosicionValorVO extends AbstractBaseDTO {

    /** Constante de Serializacion */
    private static final long serialVersionUID = 1L;

    private EmisionVO emision;

    private BigDecimal posicionActual;

    private BigDecimal valorMercado;

    private BigDecimal valorNominal;

    private BigDecimal valuacionMercado;

    private BigDecimal valuacionNominal;

    /**
     * @return EmisionVO
     */
    public EmisionVO getEmision() {
        return emision;
    }

    /**
     * @param emision
     */
    public void setEmision(EmisionVO emision) {
        this.emision = emision;
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
    public BigDecimal getValorMercado() {
        return valorMercado;
    }

    /**
     * @param valorMercado
     */
    public void setValorMercado(BigDecimal valorMercado) {
        this.valorMercado = valorMercado;
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
        this.valorNominal = valorNominal;
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
        this.valuacionMercado = valuacionMercado;
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
        this.valuacionNominal = valuacionNominal;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object arg0, Errors arg1) {
    }

}