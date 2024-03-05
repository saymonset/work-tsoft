/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class EstadoCuentaVO extends AbstractBaseDTO {

    /** Constante de Serializacion */
    private static final long serialVersionUID = 1L;

    private EmisionVO[] emision;

    private PaginaVO[] paginaVO;

    private TotalesEstadoCuentaVO[] totales;

    /**
     * @return EmisionVO[]
     */
    public EmisionVO[] getEmision() {
        return emision;
    }

    /**
     * @param emision
     */
    public void setEmision(EmisionVO[] emision) {
        this.emision = emision;
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
     * @return TotalesEstadoCuentaVO[]
     */
    public TotalesEstadoCuentaVO[] getTotales() {
        return totales;
    }

    /**
     * @param totales
     */
    public void setTotales(TotalesEstadoCuentaVO[] totales) {
        this.totales = totales;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object arg0, Errors arg1) {

    }

}
