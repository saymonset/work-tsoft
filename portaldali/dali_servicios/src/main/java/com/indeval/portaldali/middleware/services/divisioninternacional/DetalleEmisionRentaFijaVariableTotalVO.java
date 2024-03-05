/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.divisioninternacional;

import java.math.BigDecimal;
import java.util.List;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class DetalleEmisionRentaFijaVariableTotalVO extends AbstractBaseDTO {

    /** Constante de Serializacion */
    private static final long serialVersionUID = 1L;

    private EjercicioDerechoRentaFijaVariableVO renta;

    private BigDecimal totalPosicionActual;

    private BigDecimal totalImporte;

    private List registros;

    /**
     * @return List
     */
    public List getRegistros() {
        return registros;
    }

    /**
     * @param registros
     */
    public void setRegistros(List registros) {
        this.registros = registros;
    }

    /**
     * @return EjercicioDerechoRentaFijaVariableVO
     */
    public EjercicioDerechoRentaFijaVariableVO getRenta() {
        return renta;
    }

    /**
     * @param renta
     */
    public void setRenta(EjercicioDerechoRentaFijaVariableVO renta) {
        this.renta = renta;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getTotalImporte() {
        return totalImporte;
    }

    /**
     * @param totalImporte
     */
    public void setTotalImporte(BigDecimal totalImporte) {
        this.totalImporte = totalImporte;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getTotalPosicionActual() {
        return totalPosicionActual;
    }

    /**
     * @param totalPosicionActual
     */
    public void setTotalPosicionActual(BigDecimal totalPosicionActual) {
        this.totalPosicionActual = totalPosicionActual;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object arg0, Errors arg1) {
    }

}