/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import java.math.BigInteger;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class MiscelaneaFiscalTotalVO extends AbstractBaseDTO {

    /** Constante de Serializacion */
    private static final long serialVersionUID = 1L;

    /** */
    private MiscelaneaFiscalVO miscelaneaFiscal[];

    /** */
    private BigInteger totalOperaciones;

    /**
     * @return MiscelaneaFiscalVO[] 
     */
    public MiscelaneaFiscalVO[] getMiscelaneaFiscal() {
        return miscelaneaFiscal;
    }

    /**
     * @param miscelaneaFiscal
     */
    public void setMiscelaneaFiscal(MiscelaneaFiscalVO[] miscelaneaFiscal) {
        this.miscelaneaFiscal = miscelaneaFiscal;
    }

    /**
     * @return BigInteger
     */
    public BigInteger getTotalOperaciones() {
        return totalOperaciones;
    }

    /**
     * @param totalOperaciones
     */
    public void setTotalOperaciones(BigInteger totalOperaciones) {
        this.totalOperaciones = totalOperaciones;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object arg0, Errors arg1) {

    }

}
