/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import java.math.BigInteger;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class OperacionLiqFuturoTotalVO extends AbstractBaseDTO {

    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;

    private OperacionLiqFuturoVO[] operacionLiqFuturo;

    private String[] tipoValor;

    private BigInteger totalMovimiento;

    private PaginaVO[] paginaVO;

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object arg0, Errors arg1) {
    }

    /**
     * @return OperacionLiqFuturoVO[]
     */
    public OperacionLiqFuturoVO[] getOperacionLiqFuturo() {
        return operacionLiqFuturo;
    }

    /**
     * @param operacionLiqFuturo
     */
    public void setOperacionLiqFuturo(OperacionLiqFuturoVO[] operacionLiqFuturo) {
        this.operacionLiqFuturo = operacionLiqFuturo;
    }

    /**
     * @return BigInteger
     */
    public BigInteger getTotalMovimiento() {
        return totalMovimiento;
    }

    /**
     * @param totalMovimiento
     */
    public void setTotalMovimiento(BigInteger totalMovimiento) {
        this.totalMovimiento = totalMovimiento;
    }

    /**
     * @return PaginaVO[]
     */
    public PaginaVO[] getPaginaVO() {
        return paginaVO;
    }

    /**
     * @param paginaVO
     */
    public void setPaginaVO(PaginaVO[] paginaVO) {
        this.paginaVO = paginaVO;
    }

    /**
     * @return String[]
     */
    public String[] getTipoValor() {
        return tipoValor;
    }

    /**
     * @param tipoValor
     */
    public void setTipoValor(String[] tipoValor) {
        this.tipoValor = tipoValor;
    }

}
