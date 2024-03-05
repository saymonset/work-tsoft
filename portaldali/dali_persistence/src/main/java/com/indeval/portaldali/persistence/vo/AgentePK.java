/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.vo;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class AgentePK implements Serializable {

    /** Constante de Serializacion */
    private static final long serialVersionUID = 1L;

    /** IdInst de la institucion */
    private String idInst;

    /** FolioInst de la institucion */
    private String folioInst;

    /** Cuenta de la institucion */
    private String cuenta;

    
    /**
     * @return the idInst
     */
    public String getIdInst() {
        return idInst;
    }

    /**
     * @return the folioInst
     */
    public String getFolioInst() {
        return folioInst;
    }

    /**
     * @return the cuenta
     */
    public String getCuenta() {
        return cuenta;
    }

    /**
     * @param cuenta
     */
    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    /**
     * @param folioInst
     */
    public void setFolioInst(String folioInst) {
        this.folioInst = folioInst;
    }

    /**
     * @param idInst
     */
    public void setIdInst(String idInst) {
        this.idInst = idInst;
    }

    /**
     * Override java.lang.Object#toString()
     */
    public String toString() {
    	
        StringBuffer sbAgentePK = new StringBuffer(
        		StringUtils.isNotBlank(this.getIdInst()) ? this.getIdInst().trim() : "");
        
        if (StringUtils.isNotBlank(this.getFolioInst()))
        	sbAgentePK.append(" : " + this.getFolioInst().trim());
        if (StringUtils.isNotBlank(this.getCuenta()))
            sbAgentePK.append(" : " + this.getCuenta().trim());

        return sbAgentePK.toString();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        return toString().equals(obj.toString());
    }

    /**
     * Obtiene la clave del AgentePersistence concatenando su id y folio.
     * 
     * @return La clave del agente.
     */
    public String getClave() {
        StringBuffer clave = new StringBuffer("");
        if (this.getIdInst() != null) {
            clave.append(this.getIdInst().trim());
        }
        if (this.getFolioInst() != null) {
            clave.append(this.getFolioInst().trim());
        }
        return clave.toString();
    }
    
    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return this.toString().hashCode();
    }

}
