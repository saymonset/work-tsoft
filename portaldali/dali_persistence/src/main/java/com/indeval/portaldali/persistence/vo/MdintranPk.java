/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.vo;

import java.io.Serializable;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class MdintranPk implements Serializable {
    
	/**
	 * serial Version
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * cuenta
	 */
	private String cuenta;
	/**
	 * folioInst
	 */
	private String folioInst;
	/**
	 * llaveFolio
	 */
	private String llaveFolio;
	
	/**
	 * idInst
	 */
	private String idInst;

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

}
