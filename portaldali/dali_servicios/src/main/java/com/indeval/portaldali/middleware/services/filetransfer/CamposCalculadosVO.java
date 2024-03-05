/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.filetransfer;

import java.math.BigDecimal;
import java.util.Date;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class CamposCalculadosVO extends AbstractBaseDTO {

    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;
    
    private Date fechaLiquidacion; 
    private Date fechaReporto;
    private Date fechaConcertacion;
    private BigDecimal precioTitulo;
    private BigDecimal tasaPremio;
    private BigDecimal importe;
    private String mercado; 
    private String origenAplicacion; 
    private String origen; 
    private String divisa; 
    private String sociedadSerie;

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {

    }

    /**
     * @return String
     */
    public String getDivisa() {
        return divisa;
    }

    /**
     * @param divisa
     */
    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }

    /**
     * @return Date
     */
    public Date getFechaConcertacion() {
        return fechaConcertacion;
    }

    /**
     * @param fechaConcertacion
     */
    public void setFechaConcertacion(Date fechaConcertacion) {
        this.fechaConcertacion = fechaConcertacion;
    }

    /**
     * @return Date
     */
    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    /**
     * @param fechaLiquidacion
     */
    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    /**
     * @return Date
     */
    public Date getFechaReporto() {
        return fechaReporto;
    }

    /**
     * @param fechaReporto
     */
    public void setFechaReporto(Date fechaReporto) {
        this.fechaReporto = fechaReporto;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getImporte() {
        return importe;
    }

    /**
     * @param importe
     */
    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    /**
     * @return String
     */
    public String getMercado() {
        return mercado;
    }

    /**
     * @param mercado
     */
    public void setMercado(String mercado) {
        this.mercado = mercado;
    }

    /**
     * @return String
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * @param origen
     */
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    /**
     * @return String
     */
    public String getOrigenAplicacion() {
        return origenAplicacion;
    }

    /**
     * @param origenAplicacion
     */
    public void setOrigenAplicacion(String origenAplicacion) {
        this.origenAplicacion = origenAplicacion;
    }

    /** 
     * @return BigDecimal
     */
    public BigDecimal getPrecioTitulo() {
        return precioTitulo;
    }

    /**
     * @param precioTitulo
     */
    public void setPrecioTitulo(BigDecimal precioTitulo) {
        this.precioTitulo = precioTitulo;
    }

    /** 
     * @return String
     */
    public String getSociedadSerie() {
        return sociedadSerie;
    }

    /**
     * @param sociedadSerie
     */
    public void setSociedadSerie(String sociedadSerie) {
        this.sociedadSerie = sociedadSerie;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getTasaPremio() {
        return tasaPremio;
    }

    /**
     * @param tasaPremio
     */
    public void setTasaPremio(BigDecimal tasaPremio) {
        this.tasaPremio = tasaPremio;
    }

}
