/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class SociedadInversionVO extends AbstractBaseDTO {

    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;

    private String emisora;

    private String razonSocial;

    /**
     * Constructor por defecto
     */
    public SociedadInversionVO() {
    }

    /**
     * Constructor
     * 
     * @param emisora
     * @param razon
     */
    public SociedadInversionVO(String emisora, String razon) {
        this.emisora = emisora;
        this.razonSocial = razon;
    }

    /**
     * @return String
     */
    public String getEmisora() {
        return emisora;
    }

    /**
     * @param emisora
     */
    public void setEmisora(String emisora) {
        this.emisora = emisora;
    }

    /**
     * @return String
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * @param razonSocial
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {
    }

}
