/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;

import org.springframework.validation.Errors;
/**
 *
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ConfirmaTraspasoVO extends AbstractBaseDTO {

    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;
    private BigInteger folio;
    private AgenteVO traspasante;
    private AgenteVO receptor;
    private EmisionVO emision;
    private BigDecimal cantidadOperada;
    private Date fechaLiquidacion;
    private Date fechaAdquisicion;
    private Boolean confirmar;

    /**
     *
     * @return BigDecimal
     */
    public BigDecimal getCantidadOperada() {

        return cantidadOperada;

    }

    /**
     *
     * @param cantidadOperada
     */
    public void setCantidadOperada(BigDecimal cantidadOperada) {

        this.cantidadOperada = cantidadOperada;

    }

    /**
     *
     * @return Boolean
     */
    public Boolean getConfirmar() {

        return confirmar;

    }

    /**
     *
     * @param confirmar
     */
    public void setConfirmar(Boolean confirmar) {

        this.confirmar = confirmar;

    }

    /**
     *
     * @return EmisionVO
     */
    public EmisionVO getEmision() {

        return emision;

    }

    /**
     *
     * @param emision
     */
    public void setEmision(EmisionVO emision) {

        this.emision = emision;

    }

    /**
     *
     * @return Date
     */
    public Date getFechaAdquisicion() {

        return fechaAdquisicion;

    }

    /**
     *
     * @param fechaAdquisicion
     */
    public void setFechaAdquisicion(Date fechaAdquisicion) {

        this.fechaAdquisicion = clona(fechaAdquisicion);

    }

    /**
     *
     * @return Date
     */
    public Date getFechaLiquidacion() {

        return fechaLiquidacion;

    }

    /**
     *
     * @param fechaLiquidacion
     */
    public void setFechaLiquidacion(Date fechaLiquidacion) {

        this.fechaLiquidacion = clona(fechaLiquidacion);

    }

    /**
     *
     * @return BigInteger
     */
    public BigInteger getFolio() {

        return folio;

    }

    /**
     *
     * @param folio
     */
    public void setFolio(BigInteger folio) {

        this.folio = folio;

    }

    /**
     *
     * @return AgenteVO
     */
    public AgenteVO getReceptor() {

        return receptor;

    }

    /**
     *
     * @param receptor
     */
    public void setReceptor(AgenteVO receptor) {

        this.receptor = receptor;

    }

    /**
     *
     * @return AgenteVO
     */
    public AgenteVO getTraspasante() {

        return traspasante;

    }

    /**
     *
     * @param traspasante
     */
    public void setTraspasante(AgenteVO traspasante) {

        this.traspasante = traspasante;

    }

    /**
     * 
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object arg0, Errors arg1) {}

}
