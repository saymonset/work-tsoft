/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */

public class ArchivoConciliacionMovimientosResultVO implements Serializable {

    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;
    
    private String idInst;
    
    private String folioInst;
    
    private String cuenta;
    
    private String nombreCorto;
    
    private String tipoCta;

    private String idInstRecep;
    
    private String folioInstRecep;
    
    private String cuentaRecep;
    
    private String tipoCtaRecep;
    
    private String nombreCortoRecep;
    
    private String tv;
    
    private String emisora;
    
    private String serie;
    
    private String cupon;
    
    private BigDecimal cantidadOperada;
    
    private String origen;
    
    private String tipoOperacion; //clave_reporto en traspasos -- tipo_movimiento en depositos_retiros
    
    private Integer folioControl; //solo en traspasos
    
    private String folioDescripcion; //solo en traspasos
    
    private String llaveFolio;

    /**
     * @return Returns the cantidadOperada.
     */
    public BigDecimal getCantidadOperada() {
        return cantidadOperada;
    }

    /**
     * @param cantidadOperada The cantidadOperada to set.
     */
    public void setCantidadOperada(BigDecimal cantidadOperada) {
        this.cantidadOperada = cantidadOperada;
    }

    /**
     * @return Returns the cuenta.
     */
    public String getCuenta() {
        return cuenta;
    }

    /**
     * @param cuenta The cuenta to set.
     */
    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    /**
     * @return Returns the cuentaRecep.
     */
    public String getCuentaRecep() {
        return cuentaRecep;
    }

    /**
     * @param cuentaRecep The cuentaRecep to set.
     */
    public void setCuentaRecep(String cuentaRecep) {
        this.cuentaRecep = cuentaRecep;
    }

    /**
     * @return Returns the cupon.
     */
    public String getCupon() {
        return cupon;
    }

    /**
     * @param cupon The cupon to set.
     */
    public void setCupon(String cupon) {
        this.cupon = cupon;
    }

    /**
     * @return Returns the emisora.
     */
    public String getEmisora() {
        return emisora;
    }

    /**
     * @param emisora The emisora to set.
     */
    public void setEmisora(String emisora) {
        this.emisora = emisora;
    }

    /**
     * @return Returns the folioControl.
     */
    public Integer getFolioControl() {
        return folioControl;
    }

    /**
     * @param folioControl The folioControl to set.
     */
    public void setFolioControl(Integer folioControl) {
        this.folioControl = folioControl;
    }

    /**
     * @return Returns the folioDescripcion.
     */
    public String getFolioDescripcion() {
        return folioDescripcion;
    }

    /**
     * @param folioDescripcion The folioDescripcion to set.
     */
    public void setFolioDescripcion(String folioDescripcion) {
        this.folioDescripcion = folioDescripcion;
    }

    /**
     * @return Returns the folioInst.
     */
    public String getFolioInst() {
        return folioInst;
    }

    /**
     * @param folioInst The folioInst to set.
     */
    public void setFolioInst(String folioInst) {
        this.folioInst = folioInst;
    }

    /**
     * @return Returns the folioInstRecep.
     */
    public String getFolioInstRecep() {
        return folioInstRecep;
    }

    /**
     * @param folioInstRecep The folioInstRecep to set.
     */
    public void setFolioInstRecep(String folioInstRecep) {
        this.folioInstRecep = folioInstRecep;
    }

    /**
     * @return Returns the idInst.
     */
    public String getIdInst() {
        return idInst;
    }

    /**
     * @param idInst The idInst to set.
     */
    public void setIdInst(String idInst) {
        this.idInst = idInst;
    }

    /**
     * @return Returns the idInstRecep.
     */
    public String getIdInstRecep() {
        return idInstRecep;
    }

    /**
     * @param idInstRecep The idInstRecep to set.
     */
    public void setIdInstRecep(String idInstRecep) {
        this.idInstRecep = idInstRecep;
    }

    /**
     * @return Returns the llaveFolio.
     */
    public String getLlaveFolio() {
        return llaveFolio;
    }

    /**
     * @param llaveFolio The llaveFolio to set.
     */
    public void setLlaveFolio(String llaveFolio) {
        this.llaveFolio = llaveFolio;
    }

    /**
     * @return Returns the nombreCorto.
     */
    public String getNombreCorto() {
        return nombreCorto;
    }

    /**
     * @param nombreCorto The nombreCorto to set.
     */
    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    /**
     * @return Returns the nombreCortoRecep.
     */
    public String getNombreCortoRecep() {
        return nombreCortoRecep;
    }

    /**
     * @param nombreCortoRecep The nombreCortoRecep to set.
     */
    public void setNombreCortoRecep(String nombreCortoRecep) {
        this.nombreCortoRecep = nombreCortoRecep;
    }

    /**
     * @return Returns the origen.
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * @param origen The origen to set.
     */
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    /**
     * @return Returns the serie.
     */
    public String getSerie() {
        return serie;
    }

    /**
     * @param serie The serie to set.
     */
    public void setSerie(String serie) {
        this.serie = serie;
    }

    /**
     * @return Returns the tipoOperacion.
     */
    public String getTipoOperacion() {
        return tipoOperacion;
    }

    /**
     * @param tipoOperacion The tipoOperacion to set.
     */
    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    /**
     * @return Returns the tv.
     */
    public String getTv() {
        return tv;
    }

    /**
     * @param tv The tv to set.
     */
    public void setTv(String tv) {
        this.tv = tv;
    }

    /**
     * @return Returns the tipoCta.
     */
    public String getTipoCta() {
        return tipoCta;
    }

    /**
     * @param tipoCta The tipoCta to set.
     */
    public void setTipoCta(String tipoCta) {
        this.tipoCta = tipoCta;
    }

    /**
     * @return Returns the tipoCtaRecep.
     */
    public String getTipoCtaRecep() {
        return tipoCtaRecep;
    }

    /**
     * @param tipoCtaRecep The tipoCtaRecep to set.
     */
    public void setTipoCtaRecep(String tipoCtaRecep) {
        this.tipoCtaRecep = tipoCtaRecep;
    }
    
}
