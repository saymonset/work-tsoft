/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class EstadoCuentaSocInvVO extends AbstractBaseDTO {

    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;

    private SociedadInversionVO[] sociedades;

    private SociedadInversionVO sociedadActual;

    private PaginaVO paginaVO;

    /**
     * @return regresa un PaginaVO con la lista de elementos que contiene el
     *         detalle estos son de tipo EstadoCuentaSocInvDetalleVO
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
     * @return regresa un arreglo de objetos de tipo SociedadInversionVO
     */
    public SociedadInversionVO[] getSociedades() {
        return sociedades;
    }

    /**
     * @param sociedades
     */
    public void setSociedades(SociedadInversionVO[] sociedades) {
        this.sociedades = sociedades;
    }

    /**
     * @return Returns the sociedadActual.
     */
    public SociedadInversionVO getSociedadActual() {
        return sociedadActual;
    }

    /**
     * @param sociedadActual
     *            The sociedadActual to set.
     */
    public void setSociedadActual(SociedadInversionVO sociedadActual) {
        this.sociedadActual = sociedadActual;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {

    }

}
