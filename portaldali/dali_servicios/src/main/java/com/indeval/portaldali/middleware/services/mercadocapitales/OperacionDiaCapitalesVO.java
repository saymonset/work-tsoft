/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import java.math.BigDecimal;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class OperacionDiaCapitalesVO extends AbstractBaseDTO {

    /** Version serial */
    private static final long serialVersionUID = 1L;

    private PaginaVO paginaVO;

    private String[] tv;

    private Long totalMovimientos;

    private BigDecimal totalTitulos;

    /**
     * @return the totalMovimientos
     */
    public Long getTotalMovimientos() {
        return totalMovimientos;
    }

    /**
     * @param totalMovimientos
     *            the totalMovimientos to set
     */
    public void setTotalMovimientos(Long totalMovimientos) {
        this.totalMovimientos = totalMovimientos;
    }

    /**
     * @return the totalTitulos
     */
    public BigDecimal getTotalTitulos() {
        return totalTitulos;
    }

    /**
     * @param totalTitulos
     *            the totalTitulos to set
     */
    public void setTotalTitulos(BigDecimal totalTitulos) {
        this.totalTitulos = totalTitulos;
    }

    /**
     * @return the tv
     */
    public String[] getTv() {
        return tv;
    }

    /**
     * @param tv
     *            the tv to set
     */
    public void setTv(String[] tv) {
        this.tv = tv;
    }

    /**
     * @return the paginaVO
     */
    public PaginaVO getPaginaVO() {
        return paginaVO;
    }

    /**
     * @param paginaVO
     *            the paginaVO to set
     */
    public void setPaginaVO(PaginaVO paginaVO) {
        this.paginaVO = paginaVO;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {
    }

}
