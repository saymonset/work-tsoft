/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.vo;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class BitacoraMatchVOPersistence implements Serializable {

    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;

    private String mensaje;

    private BigInteger idBitacoraMatch;

    private Boolean confirmacion;

    private Boolean cancelacion;
    
    private String enviada; 

    /**
     * @return Boolean
     */
    public Boolean getCancelacion() {
        return cancelacion;
    }

    /**
     * @param cancelacion
     */
    public void setCancelacion(Boolean cancelacion) {
        this.cancelacion = cancelacion;
    }

    /**
     * @return Boolean
     */
    public Boolean getConfirmacion() {
        return confirmacion;
    }

    /**
     * 
     * @param confirmacion
     */
    public void setConfirmacion(Boolean confirmacion) {
        this.confirmacion = confirmacion;
    }

    /**
     * @return BigInteger
     */
    public BigInteger getIdBitacoraMatch() {
        return idBitacoraMatch;
    }

    /**
     * 
     * @param idBitacoraMatch
     */
    public void setIdBitacoraMatch(BigInteger idBitacoraMatch) {
        this.idBitacoraMatch = idBitacoraMatch;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje
     *            the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the enviada
     */
    public String getEnviada() {
        return enviada;
    }

    /**
     * @param enviada the enviada to set
     */
    public void setEnviada(String enviada) {
        this.enviada = enviada;
    }
}
