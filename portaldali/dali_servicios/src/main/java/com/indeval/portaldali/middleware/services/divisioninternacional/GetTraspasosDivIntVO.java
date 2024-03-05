/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.divisioninternacional;

import java.math.BigInteger;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class GetTraspasosDivIntVO extends AbstractBaseDTO {

    /** Constante de Serializacion */
    private static final long serialVersionUID = 1L;

    private Integer totalTV;

    private BigInteger titulosOperados;

    private PaginaVO paginaVO;

    /**
     * @return BigInteger
     */
    public BigInteger getTitulosOperados() {
        return titulosOperados;
    }

    /**
     * @param titulosOperados
     */
    public void setTitulosOperados(BigInteger titulosOperados) {
        this.titulosOperados = titulosOperados;
    }

    /**
     * @return Integer
     */
    public Integer getTotalTV() {
        return totalTV;
    }

    /**
     * @param totalTV
     */
    public void setTotalTV(Integer totalTV) {
        this.totalTV = totalTV;
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
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object obj, Errors errors) {
    }

}
