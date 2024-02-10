package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Clase para Depositante
 * @author arivera
 * @version 1.0
 */
@XStreamAlias("depositante")
public class DepositanteVO implements Serializable {

    /**
     * Version Serial
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private String bicCodeDepositante;
    /**
     *
     */
    private String detalleDepositante;

    /**
     *
     */
    public DepositanteVO() {
    }

    /**
     *
     * @param bicCodeDepositante
     * @param depositante
     */
    public DepositanteVO(String bicCodeDepositante, String detalleDepositante) {
        this.bicCodeDepositante = bicCodeDepositante;
        this.detalleDepositante = detalleDepositante;
    }

    /**
     * @return the bicCodeDepositante
     */
    public String getBicCodeDepositante() {
        return bicCodeDepositante;
    }

    /**
     * @param bicCodeDepositante the bicCodeDepositante to set
     */
    public void setBicCodeDepositante(String bicCodeDepositante) {
        this.bicCodeDepositante = bicCodeDepositante;
    }

    /**
     * @return the detalleDepositante
     */
    public String getDetalleDepositante() {
        return detalleDepositante;
    }

    /**
     * @param detalleDepositante the detalleDepositante to set
     */
    public void setDetalleDepositante(String detalleDepositante) {
        this.detalleDepositante = detalleDepositante;
    }

}
