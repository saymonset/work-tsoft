/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.vo;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/**
 * @author drengifo
 *
 */
public class EmisionPK implements Serializable {
    
    private static final long serialVersionUID = 1L;

	private String tv;
    
    private String emisora;
    
    private String serie;
    
    private String cupon;

    /**
     * @hibernate.key-property
     *  column="tv"
     */
    public String getTv() {
        return tv;
    }

    /**
     * @hibernate.key-property
     *  column="emisora"
     */
    public String getEmisora() {
        return emisora;
    }

    /**
     * @hibernate.key-property
     *  column="serie"
     */
    public String getSerie() {
        return serie;
    }

    /**
     * @hibernate.key-property
     *  column="cupon"
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
     * Retorna una cadena String con los valores de los atributos tv, emisora,
     * serie, cupon del objeto
     * 
     * Override java.lang.Object#toString()
     * 
     * @return String
     */
    public String toString() {
        StringBuffer sbEmisionPK = new StringBuffer(
        		StringUtils.isNotBlank(this.getTv()) ? this.getTv().trim() : "");
        if (StringUtils.isNotBlank(this.getEmisora()))
            sbEmisionPK.append(" : " + this.getEmisora().trim());
        if (StringUtils.isNotBlank(this.getSerie()))
            sbEmisionPK.append(" : " + this.getSerie().trim());
        if (StringUtils.isNotBlank(this.getCupon()))
            sbEmisionPK.append(" : " + this.getCupon().trim());

        return sbEmisionPK.toString();
    }
    
    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        return toString().equals(obj.toString());
    }
    
    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return this.toString().hashCode();        
    }    

}
