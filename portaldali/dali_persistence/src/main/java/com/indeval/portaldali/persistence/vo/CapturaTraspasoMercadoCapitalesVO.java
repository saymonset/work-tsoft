/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CapturaTraspasoMercadoCapitalesVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    
    private Date fecha;
    private String idTraspasante;
    private String folTraspasante;
    private String cuentaTraspasante;
    private String tipoCuentaTraspasante;
    private String idReceptor;
    private String folReceptor;
    private String cuentaReceptor;
    private String tipoCuentaReceptor;    
    private String tv;
    private String emisora;
    private String serie;
    private String cupon;
    private BigDecimal cantidad;
    private String cveReporto;
    private Date fechaReporto;
    private Date fechaLiquidacion;
    private String llaveFolioMd;
    private String folioDescripcion;
    private String divisa;
    private Integer folioControl;
    
    
    public BigDecimal getCantidad() {
        return cantidad;
    }
    public String getCuentaReceptor() {
        return cuentaReceptor;
    }
    public String getCuentaTraspasante() {
        return cuentaTraspasante;
    }
    public String getCupon() {
        return cupon;
    }
    public String getCveReporto() {
        return cveReporto;
    }
    public String getDivisa() {
        return divisa;
    }
    public String getEmisora() {
        return emisora;
    }
    public Date getFecha() {
        return fecha;
    }
    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }
    public Date getFechaReporto() {
        return fechaReporto;
    }
    public String getFolioDescripcion() {
        return folioDescripcion;
    }
    public String getFolReceptor() {
        return folReceptor;
    }
    public String getFolTraspasante() {
        return folTraspasante;
    }
    public String getIdReceptor() {
        return idReceptor;
    }
    public String getIdTraspasante() {
        return idTraspasante;
    }
    public String getLlaveFolioMd() {
        return llaveFolioMd;
    }
    public String getSerie() {
        return serie;
    }
    public String getTipoCuentaReceptor() {
        return tipoCuentaReceptor;
    }
    public String getTipoCuentaTraspasante() {
        return tipoCuentaTraspasante;
    }
    public String getTv() {
        return tv;
    }
    public Integer getFolioControl() {
        return folioControl;
    }
    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }
    public void setCuentaReceptor(String cuentaReceptor) {
        this.cuentaReceptor = cuentaReceptor;
    }
    public void setCuentaTraspasante(String cuentaTraspasante) {
        this.cuentaTraspasante = cuentaTraspasante;
    }
    public void setCupon(String cupon) {
        this.cupon = cupon;
    }
    public void setCveReporto(String cveReporto) {
        this.cveReporto = cveReporto;
    }
    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }
    public void setEmisora(String emisora) {
        this.emisora = emisora;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }
    public void setFechaReporto(Date fechaReporto) {
        this.fechaReporto = fechaReporto;
    }
    public void setFolioDescripcion(String folioDescripcion) {
        this.folioDescripcion = folioDescripcion;
    }
    public void setFolReceptor(String folReceptor) {
        this.folReceptor = folReceptor;
    }
    public void setFolTraspasante(String folTraspasante) {
        this.folTraspasante = folTraspasante;
    }
    public void setIdReceptor(String idReceptor) {
        this.idReceptor = idReceptor;
    }
    public void setIdTraspasante(String idTraspasante) {
        this.idTraspasante = idTraspasante;
    }
    public void setLlaveFolioMd(String llaveFolioMd) {
        this.llaveFolioMd = llaveFolioMd;
    }
    public void setSerie(String serie) {
        this.serie = serie;
    }
    public void setTipoCuentaReceptor(String tipoCuentaReceptor) {
        this.tipoCuentaReceptor = tipoCuentaReceptor;
    }
    public void setTipoCuentaTraspasante(String tipoCuentaTraspasante) {
        this.tipoCuentaTraspasante = tipoCuentaTraspasante;
    }
    public void setTv(String tv) {
        this.tv = tv;
    }
    public void setFolioControl(Integer folioControl) {
        this.folioControl = folioControl;
    }
    
    
    
       
}
