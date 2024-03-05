/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ArchivoConciliacionResultVO implements Serializable {

    /**
     * Variable de serializacion
     */
    private static final long serialVersionUID = 1L;
    
    private String idInst;
    
    private String folioInst;
    
    private String cuenta;
    
    private String tipoCta;
    
    private String tenencia;
    
    private String tv;
    
    private String emisora;
    
    private String serie;
    
    private String cupon;
    
    private BigDecimal valorNominal;
    
    private BigDecimal ultimoHecho;
    
    private BigDecimal saldoInicial;
    
    private BigDecimal saldoDisponible;
    
    private BigDecimal saldoTesoreria;
    
    private BigDecimal total;
    
    private Date fechaHora;

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
     * @return Returns the fechaHora.
     */
    public Date getFechaHora() {
        return fechaHora;
    }

    /**
     * @param fechaHora The fechaHora to set.
     */
    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
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
     * @return Returns the saldoDisponible.
     */
    public BigDecimal getSaldoDisponible() {
        return saldoDisponible;
    }

    /**
     * @param saldoDisponible The saldoDisponible to set.
     */
    public void setSaldoDisponible(BigDecimal saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    /**
     * @return Returns the saldoInicial.
     */
    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    /**
     * @param saldoInicial The saldoInicial to set.
     */
    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    /**
     * @return Returns the saldoTesoreria.
     */
    public BigDecimal getSaldoTesoreria() {
        return saldoTesoreria;
    }

    /**
     * @param saldoTesoreria The saldoTesoreria to set.
     */
    public void setSaldoTesoreria(BigDecimal saldoTesoreria) {
        this.saldoTesoreria = saldoTesoreria;
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
     * @return Returns the total.
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * @param total The total to set.
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
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
     * @return Returns the ultimoHecho.
     */
    public BigDecimal getUltimoHecho() {
        return ultimoHecho;
    }

    /**
     * @param ultimoHecho The ultimoHecho to set.
     */
    public void setUltimoHecho(BigDecimal ultimoHecho) {
        this.ultimoHecho = ultimoHecho;
    }

    /**
     * @return Returns the valorNominal.
     */
    public BigDecimal getValorNominal() {
        return valorNominal;
    }

    /**
     * @param valorNominal The valorNominal to set.
     */
    public void setValorNominal(BigDecimal valorNominal) {
        this.valorNominal = valorNominal;
    }

    /**
     * @return Returns the tenencia.
     */
    public String getTenencia() {
        return tenencia;
    }

    /**
     * @param tenencia The tenencia to set.
     */
    public void setTenencia(String tenencia) {
        this.tenencia = tenencia;
    }

}
