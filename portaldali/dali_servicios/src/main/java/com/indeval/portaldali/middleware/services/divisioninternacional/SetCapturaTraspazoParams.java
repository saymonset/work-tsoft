/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.divisioninternacional;

import java.math.BigDecimal;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class SetCapturaTraspazoParams extends AbstractBaseDTO {

    /** Constante de Serializacion */
    private static final long serialVersionUID = 1L;

    private AgenteVO agenteVOReceptor;

    private AgenteVO agenteVOtraspasante;

    private EmisionVO emisionVO;

    private BigDecimal cantidadOperada;

    private BigDecimal saldoDisponible;

    private BigDecimal posicionActual;

    private String descripcion;

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
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
     * @return AgenteVO
     */
    public AgenteVO getAgenteVOReceptor() {
        return agenteVOReceptor;
    }

    /**
     * @param agenteVOReceptor
     */
    public void setAgenteVOReceptor(AgenteVO agenteVOReceptor) {
        this.agenteVOReceptor = agenteVOReceptor;
    }

    /**
     * @return AgenteVO
     */
    public AgenteVO getAgenteVOtraspasante() {
        return agenteVOtraspasante;
    }

    /**
     * @param agenteVOtraspasante
     */
    public void setAgenteVOtraspasante(AgenteVO agenteVOtraspasante) {
        this.agenteVOtraspasante = agenteVOtraspasante;
    }

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
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object arg0, Errors arg1) {
    }

}