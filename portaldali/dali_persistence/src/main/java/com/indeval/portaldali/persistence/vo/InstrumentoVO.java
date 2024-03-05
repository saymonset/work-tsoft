/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class InstrumentoVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String uVersion;

    private String tv;

    private String aplicacion;

    private String claseIns;

    private String concepto;

    private String descripcionValor;

    private Integer excepcion;

    private Date fechaHora;

    private BigDecimal precioMax;

    private BigDecimal precioMin;

    private String uso;

    private String usuario;

    private String mercado;

    private Boolean socInv;

    private String divisa;

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @return the uVersion
     */
    public String getUVersion() {
        return uVersion;
    }

    /**
     * @return the tv
     */
    public String getTv() {
        return tv;
    }

    /**
     * @return the aplicacion
     */
    public String getAplicacion() {
        return aplicacion;
    }

    /**
     * @return the claseIns
     */
    public String getClaseIns() {
        return claseIns;
    }

    /**
     * @return the concepto
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * @return the descripcionValor
     */
    public String getDescripcionValor() {
        return descripcionValor;
    }

    /**
     * @return the excepcion
     */
    public Integer getExcepcion() {
        return excepcion;
    }

    /**
     * @return the fechaHora
     */
    public Date getFechaHora() {
        return fechaHora;
    }

    /**
     * @return the precioMax
     */
    public BigDecimal getPrecioMax() {
        return precioMax;
    }

    /**
     * @return the precioMin
     */
    public BigDecimal getPrecioMin() {
        return precioMin;
    }

    /**
     * @return the uso
     */
    public String getUso() {
        return uso;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @return the mercado
     */
    public String getMercado() {
        return mercado;
    }

    /**
     * @return the socInv
     */
    public Boolean getSocInv() {
        return socInv;
    }

    /**
     * @return the divisa
     */
    public String getDivisa() {
        return divisa;
    }

    /**
     * @param aplicacion
     */
    public void setAplicacion(String aplicacion) {
        this.aplicacion = aplicacion;
    }

    /**
     * @param claseIns
     */
    public void setClaseIns(String claseIns) {
        this.claseIns = claseIns;
    }

    /**
     * @param concepto
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    /**
     * @param descripcionValor
     */
    public void setDescripcionValor(String descripcionValor) {
        this.descripcionValor = descripcionValor;
    }

    /**
     * @param excepcion
     */
    public void setExcepcion(Integer excepcion) {
        this.excepcion = excepcion;
    }

    /**
     * @param fechaHora
     */
    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
     * @param mercado
     */
    public void setMercado(String mercado) {
        this.mercado = mercado;
    }

    /**
     * @param precioMax
     */
    public void setPrecioMax(BigDecimal precioMax) {
        this.precioMax = precioMax;
    }

    /**
     * @param precioMin
     */
    public void setPrecioMin(BigDecimal precioMin) {
        this.precioMin = precioMin;
    }

    /**
     * @param socInv
     */
    public void setSocInv(Boolean socInv) {
        this.socInv = socInv;
    }

    /**
     * @param tv
     */
    public void setTv(String tv) {
        this.tv = tv;
    }

    /**
     * @param uso
     */
    public void setUso(String uso) {
        this.uso = uso;
    }

    /**
     * @param usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @param version
     */
    public void setUVersion(String version) {
        uVersion = version;
    }

    /**
     * @param divisa
     */
    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }
    
}
