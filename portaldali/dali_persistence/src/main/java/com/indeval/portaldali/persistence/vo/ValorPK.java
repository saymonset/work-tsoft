/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.vo;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ValorPK implements Serializable, Comparable {
    
	/** Constante de Serializacion */
	private static final long serialVersionUID = 1L;

    /** */
	private String idInst;
    
    /** */
    private String folioInst;
    
    /** */
    private String cuenta;
    
    /** */
    private String tv;
    
    /** */
    private String emisora;
    
    /** */
    private String serie;
    
    /** */
    private String cupon;
    
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
     * @return the tv
     */
    public String getTv() {
        return tv;
    }

    /**
     * @return the emisora
     */
    public String getEmisora() {
        return emisora;
    }

    /**
     * @return the serie
     */
    public String getSerie() {
        return serie;
    }

    /**
     * @return the cupon
     */
    public String getCupon() {
        return cupon;
    }

    /**
	 * @param cupon
	 */
	public void setCupon(String cupon) {
		this.cupon = cupon;
	}

	/**
	 * @param emisora
	 */
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}

	/**
	 * @param serie
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	/**
	 * @param tv
	 */
	public void setTv(String tv) {
		this.tv = tv;
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
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer sbValorPK = new StringBuffer(StringUtils.isNotBlank(getIdInst()) ? getIdInst().trim() : "");
		if(StringUtils.isNotBlank(getFolioInst())) sbValorPK.append(getFolioInst().trim());
        if(StringUtils.isNotBlank(getCuenta())) sbValorPK.append(getCuenta().trim());
        if(StringUtils.isNotBlank(getTv())) sbValorPK.append(getTv().trim());
        if(StringUtils.isNotBlank(getEmisora())) sbValorPK.append(getEmisora().trim());
        if(StringUtils.isNotBlank(getSerie())) sbValorPK.append(getSerie().trim());
        if(StringUtils.isNotBlank(getCupon())) sbValorPK.append(getCupon().trim());
		
        return  sbValorPK.toString();
    }
    
    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }
    
    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return this.toString().hashCode();        
    }

	/**
	 * @see java.lang.Comparable#compareTo(T)
	 */
	public int compareTo(Object o) {
		return this.toString().compareTo(((ValorPK)o).toString());
	}
    
}
