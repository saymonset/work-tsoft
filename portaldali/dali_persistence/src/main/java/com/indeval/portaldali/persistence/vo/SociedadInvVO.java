/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */

public class SociedadInvVO implements Serializable {

    /** Constante se Serializacion */
    private static final long serialVersionUID = 1L;
    
    private String razonSocial;

    private String tv;

    private String emisora;

    private String serie;

    private String cupon;

    private BigDecimal saldoCir;

    private BigDecimal saldoTes;

    private BigDecimal saldoTot;
    
    /**
     * @return String
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
     * @return BigDecimal
     */
    public BigDecimal getSaldoCir() {
        return saldoCir;
    }

    /**
     * @param saldoCir
     */
    public void setSaldoCir(BigDecimal saldoCir) {
        this.saldoCir = saldoCir;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getSaldoTes() {
        return saldoTes;
    }

    /**
     * @param saldoTes
     */
    public void setSaldoTes(BigDecimal saldoTes) {
        this.saldoTes = saldoTes;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getSaldoTot() {
        return saldoTot;
    }

    /**
     * @param saldoTot
     */
    public void setSaldoTot(BigDecimal saldoTot) {
        this.saldoTot = saldoTot;
    }

    /**
     * @return String
     */
    public String getSerie() {
        return serie;
    }

    /**
     * @param serie
     */
    public void setSerie(String serie) {
        this.serie = serie;
    }

    /**
     * @return String
     */
    public String getTv() {
        return tv;
    }

    /**
     * @param tv
     */
    public void setTv(String tv) {
        this.tv = tv;
    }
    

}
