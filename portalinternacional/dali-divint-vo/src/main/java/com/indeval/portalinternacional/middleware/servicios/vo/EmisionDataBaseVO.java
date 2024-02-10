package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;

public class EmisionDataBaseVO implements Serializable {

    /**
     * Version Serial
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private String isin;
    /**
     *
     */
    private String tv;
    /**
     *
     */
    private String emisora;
    /**
     *
     */
    private String serie;
    /**
     *
     */
    private String bicProd;
    /**
     *
     */
    private String detalleCustodio;
    /**
     *
     */
    private String bicDepLiq;
    /**
     *
     */
    private String depLiq;
    /**
     *
     */
    private String claveTipoInstitucion;
    /**
     *
     */
    private String folioInstitucion;
    /**
     *
     */
    private String cuenta;

    public EmisionDataBaseVO() {
    }

    public EmisionDataBaseVO(String isin, String tv, String emisora, String serie,
            String bicProd, String detalleCustodio, String bicDepLiq, String depLiq,
            String claveTipoInstitucion, String folioInstitucion, String cuenta) {
        this.isin = isin;
        this.tv = tv;
        this.emisora = emisora;
        this.serie = serie;
        this.bicProd = bicProd;
        this.detalleCustodio = detalleCustodio;
        this.bicDepLiq = bicDepLiq;
        this.depLiq = depLiq;
        this.claveTipoInstitucion = claveTipoInstitucion;
        this.folioInstitucion = folioInstitucion;
        this.cuenta = cuenta;
    }

    /**
     * @return the isin
     */
    public String getIsin() {
        return isin;
    }

    /**
     * @param isin the isin to set
     */
    public void setIsin(String isin) {
        this.isin = isin;
    }

    /**
     * @return the tv
     */
    public String getTv() {
        return tv;
    }

    /**
     * @param tv the tv to set
     */
    public void setTv(String tv) {
        this.tv = tv;
    }

    /**
     * @return the emisora
     */
    public String getEmisora() {
        return emisora;
    }

    /**
     * @param emisora the emisora to set
     */
    public void setEmisora(String emisora) {
        this.emisora = emisora;
    }

    /**
     * @return the serie
     */
    public String getSerie() {
        return serie;
    }

    /**
     * @param serie the serie to set
     */
    public void setSerie(String serie) {
        this.serie = serie;
    }

    /**
     * @return the bicProd
     */
    public String getBicProd() {
        return bicProd;
    }

    /**
     * @param bicProd the bicProd to set
     */
    public void setBicProd(String bicProd) {
        this.bicProd = bicProd;
    }

    /**
     * @return the detalleCustodio
     */
    public String getDetalleCustodio() {
        return detalleCustodio;
    }

    /**
     * @param detalleCustodio the detalleCustodio to set
     */
    public void setDetalleCustodio(String detalleCustodio) {
        this.detalleCustodio = detalleCustodio;
    }

    /**
     * @return the bicDepLiq
     */
    public String getBicDepLiq() {
        return bicDepLiq;
    }

    /**
     * @param bicDepLiq the bicDepLiq to set
     */
    public void setBicDepLiq(String bicDepLiq) {
        this.bicDepLiq = bicDepLiq;
    }

    /**
     * @return the depLiq
     */
    public String getDepLiq() {
        return depLiq;
    }

    /**
     * @param depLiq the depLiq to set
     */
    public void setDepLiq(String depLiq) {
        this.depLiq = depLiq;
    }

    /**
     * @return the claveTipoInstitucion
     */
    public String getClaveTipoInstitucion() {
        return claveTipoInstitucion;
    }

    /**
     * @param claveTipoInstitucion the claveTipoInstitucion to set
     */
    public void setClaveTipoInstitucion(String claveTipoInstitucion) {
        this.claveTipoInstitucion = claveTipoInstitucion;
    }

    /**
     * @return the folioInstitucion
     */
    public String getFolioInstitucion() {
        return folioInstitucion;
    }

    /**
     * @param folioInstitucion the folioInstitucion to set
     */
    public void setFolioInstitucion(String folioInstitucion) {
        this.folioInstitucion = folioInstitucion;
    }

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

}
