/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class AyudaEmisionSaldoDisponibleVO extends AbstractBaseDTO {

    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;

    private Date fecha;

    private EmisionVO emision;

    private BigInteger posicionActual;

    private BigDecimal valorMercado;

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
     * @return Date
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha
     */
    public void setFecha(Date fecha) {
        this.fecha = clona(fecha);
    }

    /**
     * @return BigInteger
     */
    public BigInteger getPosicionActual() {
        return posicionActual;
    }

    /**
     * @param posicionActual
     */
    public void setPosicionActual(BigInteger posicionActual) {
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
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object arg0, Errors arg1) {
    }
    
}
