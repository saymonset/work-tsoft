/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import java.math.BigDecimal;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class EstadoCuentaSocInvDetalleVO extends AbstractBaseDTO {

    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;

    private EmisionVO emisionVO;

    private BigDecimal saldoCirculacion;

    private BigDecimal saldoTesoreria;

    private BigDecimal saldoTotal;

    /**
     * @return EmisionVO
     */
    public EmisionVO getEmisionVO() {
        return emisionVO;
    }

    /**
     * @param emisionVO
     */
    public void setEmisionVO(EmisionVO emisionVO) {
        this.emisionVO = emisionVO;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getSaldoCirculacion() {
        return saldoCirculacion;
    }

    /**
     * @param saldoCirculacion
     */
    public void setSaldoCirculacion(BigDecimal saldoCirculacion) {
        this.saldoCirculacion = saldoCirculacion;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getSaldoTesoreria() {
        return saldoTesoreria;
    }

    /**
     * @param saldoTesoreria
     */
    public void setSaldoTesoreria(BigDecimal saldoTesoreria) {
        this.saldoTesoreria = saldoTesoreria;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getSaldoTotal() {
        return saldoTotal;
    }

    /**
     * @param saldoTotal
     */
    public void setSaldoTotal(BigDecimal saldoTotal) {
        this.saldoTotal = saldoTotal;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {
    }

}
