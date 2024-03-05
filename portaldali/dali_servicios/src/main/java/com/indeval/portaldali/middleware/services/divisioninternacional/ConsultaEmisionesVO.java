/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.divisioninternacional;

import java.math.BigInteger;
import java.util.Date;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ConsultaEmisionesVO extends AbstractBaseDTO {

    /** Constante de Serializacion */
    private static final long serialVersionUID = 1L;

    private EmisionVO emisionVO;

    private String cuponCortado;

    private Date fechaAlta;

    private Date fechaAsignacionlsin;

    private Date fechaVencimiento;

    private BigInteger dias;

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
     * @return String
     */
    public String getCuponCortado() {
        return cuponCortado;
    }

    /**
     * @param cuponCortado
     */
    public void setCuponCortado(String cuponCortado) {
        this.cuponCortado = cuponCortado;
    }

    /**
     * @return BigInteger
     */
    public BigInteger getDias() {
        return dias;
    }

    /**
     * @param dias
     */
    public void setDias(BigInteger dias) {
        this.dias = dias;
    }

    /**
     * @return Date
     */
    public Date getFechaAlta() {
        return fechaAlta;
    }

    /**
     * @param fechaAlta
     */
    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = clona(fechaAlta);
    }

    /**
     * @return Date
     */
    public Date getFechaAsignacionlsin() {
        return fechaAsignacionlsin;
    }

    /**
     * @param fechaAsignacionlsin
     */
    public void setFechaAsignacionlsin(Date fechaAsignacionlsin) {
        this.fechaAsignacionlsin = clona(fechaAsignacionlsin);
    }

    /**
     * @return Date
     */
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * @param fechaVencimiento
     */
    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = clona(fechaVencimiento);
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object arg0, Errors arg1) {
    }

}