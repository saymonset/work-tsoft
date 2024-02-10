/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.math.BigDecimal;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.servicios.modelo.AbstractBaseDTO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class TopeCirculanteFidecomisoVO extends AbstractBaseDTO {

    /** Constante de Serializacion */
    private static final long serialVersionUID = 1L;

    /**
     * EmisionVO
     */
    private EmisionVO emisionVO;

    /**
     * BigDecimal
     */
    private BigDecimal topeFideicomiso;

    /**
     * BigDecimal
     */
    private BigDecimal titulosCirculacion;

    /**
     * BigDecimal
     */
    private BigDecimal titulosDisponibles;

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
    public BigDecimal getTitulosCirculacion() {
        return titulosCirculacion;
    }

    /**
     * @param titulosCirculacion
     */
    public void setTitulosCirculacion(BigDecimal titulosCirculacion) {
        this.titulosCirculacion = titulosCirculacion;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getTitulosDisponibles() {
        return titulosDisponibles;
    }

    /**
     * @param titulosDisponibles
     */
    public void setTitulosDisponibles(BigDecimal titulosDisponibles) {
        this.titulosDisponibles = titulosDisponibles;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getTopeFideicomiso() {
        return topeFideicomiso;
    }

    /**
     * @param topeFideicomiso
     */
    public void setTopeFideicomiso(BigDecimal topeFideicomiso) {
        this.topeFideicomiso = topeFideicomiso;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object arg0, Errors arg1) {

    }

}