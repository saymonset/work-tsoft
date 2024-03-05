/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.divisioninternacional;

import java.math.BigDecimal;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class EstadoCuentaTotalVO extends AbstractBaseDTO {

    /** Constante de Serializacion */
    private static final long serialVersionUID = 1L;

    private String cuentaActual;

    private String[] cuentas;

    private PaginaVO paginaVO;

    private BigDecimal totalSalida;

    private BigDecimal totalEntrada;

    /**
     * @return String
     */
    public String getCuentaActual() {
        return cuentaActual;
    }

    /**
     * @param cuentaActual
     */
    public void setCuentaActual(String cuentaActual) {
        this.cuentaActual = cuentaActual;
    }

    /**
     * @return String[]
     */
    public String[] getCuentas() {
        return cuentas;
    }

    /**
     * @param cuentas
     */
    public void setCuentas(String[] cuentas) {
        this.cuentas = cuentas;
    }

    /**
     * @return PaginaVO
     */
    public PaginaVO getPaginaVO() {
        return paginaVO;
    }

    /**
     * @param paginaVO
     */
    public void setPaginaVO(PaginaVO paginaVO) {
        this.paginaVO = paginaVO;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getTotalEntrada() {
        return totalEntrada;
    }

    /**
     * @param totalEntrada
     */
    public void setTotalEntrada(BigDecimal totalEntrada) {
        this.totalEntrada = totalEntrada;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getTotalSalida() {
        return totalSalida;
    }

    /**
     * @param totalSalida
     */
    public void setTotalSalida(BigDecimal totalSalida) {
        this.totalSalida = totalSalida;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object arg0, Errors arg1) {
    }

}