/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class TraspasoCapitalesVO extends AbstractBaseDTO {

    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;

    /** */
    private String idInst;
    
    /** */
    private String folioInst;
    
    /** */
    private String cuenta;
    
    /** */
    private String llaveFolio;

    /**
     * @return the cuenta
     */
    public String getCuenta() {
        return cuenta;
    }
    
    /**
     * @param cuenta the cuenta to set
     */
    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }
    
    /**
     * @return the folioInst
     */
    public String getFolioInst() {
        return folioInst;
    }
    
    /**
     * @param folioInst the folioInst to set
     */
    public void setFolioInst(String folioInst) {
        this.folioInst = folioInst;
    }
    
    /**
     * @return the idInst
     */
    public String getIdInst() {
        return idInst;
    }
    
    /**
     * @param idInst the idInst to set
     */
    public void setIdInst(String idInst) {
        this.idInst = idInst;
    }
    
    /**
     * @return the llaveFolio
     */
    public String getLlaveFolio() {
        return llaveFolio;
    }
    
    /**
     * @param llaveFolio the llaveFolio to set
     */
    public void setLlaveFolio(String llaveFolio) {
        this.llaveFolio = llaveFolio;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {
    }

}
