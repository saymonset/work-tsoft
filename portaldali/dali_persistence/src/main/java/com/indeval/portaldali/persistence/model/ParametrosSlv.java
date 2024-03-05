/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;

/**
 * Modelo de persistencia para la tabla C_PARAMETROS_LIQUIDACION
 * 
 * @author Jesus Ramos
 */
public class ParametrosSlv implements Serializable {

    private Long id = null;

    private String claveConfiguracion = null;

    private Long gatilloIntervaloMilisegundos = null;

    private Integer gatilloVolumenInstrucciones = null;

    private BigDecimal gatilloMontoInstrucciones = null;

    private Time horaCierreIndeval = null;

    private Time horaCierreBanxico = null;

    private Long timeoutRespuestaCompensador = null;

    private BigDecimal precioTituloMaxCompensable = null;

    private Boolean activo = null;

    /**
     * Default serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return the activo
     */
    public Boolean isActivo() {
        return activo;
    }

    /**
     * @param activo
     *            the activo to set
     */
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    /**
     * @return the claveConfiguracion
     */
    public String getClaveConfiguracion() {
        return claveConfiguracion;
    }

    /**
     * @param claveConfiguracion
     *            the claveConfiguracion to set
     */
    public void setClaveConfiguracion(String claveConfiguracion) {
        this.claveConfiguracion = claveConfiguracion;
    }

    /**
     * @return the gatilloIntervaloMilisegundos
     */
    public Long getGatilloIntervaloMilisegundos() {
        return gatilloIntervaloMilisegundos;
    }

    /**
     * @param gatilloIntervaloMilisegundos
     *            the gatilloIntervaloMilisegundos to set
     */
    public void setGatilloIntervaloMilisegundos(Long gatilloIntervaloMilisegundos) {
        this.gatilloIntervaloMilisegundos = gatilloIntervaloMilisegundos;
    }

    /**
     * @return the gatilloMontoInstrucciones
     */
    public BigDecimal getGatilloMontoInstrucciones() {
        return gatilloMontoInstrucciones;
    }

    /**
     * @param gatilloMontoInstrucciones
     *            the gatilloMontoInstrucciones to set
     */
    public void setGatilloMontoInstrucciones(BigDecimal gatilloMontoInstrucciones) {
        this.gatilloMontoInstrucciones = gatilloMontoInstrucciones;
    }

    /**
     * @return the gatilloVolumenInstrucciones
     */
    public Integer getGatilloVolumenInstrucciones() {
        return gatilloVolumenInstrucciones;
    }

    /**
     * @param gatilloVolumenInstrucciones
     *            the gatilloVolumenInstrucciones to set
     */
    public void setGatilloVolumenInstrucciones(Integer gatilloVolumenInstrucciones) {
        this.gatilloVolumenInstrucciones = gatilloVolumenInstrucciones;
    }

    /**
     * @return the horaCierreBanxico
     */
    public Time getHoraCierreBanxico() {
        return horaCierreBanxico;
    }

    /**
     * @param horaCierreBanxico
     *            the horaCierreBanxico to set
     */
    public void setHoraCierreBanxico(Time horaCierreBanxico) {
        this.horaCierreBanxico = horaCierreBanxico;
    }

    /**
     * @return the horaCierreIndeval
     */
    public Time getHoraCierreIndeval() {
        return horaCierreIndeval;
    }

    /**
     * @param horaCierreIndeval
     *            the horaCierreIndeval to set
     */
    public void setHoraCierreIndeval(Time horaCierreIndeval) {
        this.horaCierreIndeval = horaCierreIndeval;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the precioTituloMaxCompensable
     */
    public BigDecimal getPrecioTituloMaxCompensable() {
        return precioTituloMaxCompensable;
    }

    /**
     * @param precioTituloMaxCompensable
     *            the precioTituloMaxCompensable to set
     */
    public void setPrecioTituloMaxCompensable(BigDecimal precioTituloMaxCompensable) {
        this.precioTituloMaxCompensable = precioTituloMaxCompensable;
    }

    /**
     * @return the timeoutRespuestaCompensador
     */
    public Long getTimeoutRespuestaCompensador() {
        return timeoutRespuestaCompensador;
    }

    /**
     * @param timeoutRespuestaCompensador the timeoutRespuestaCompensador to set
     */
    public void setTimeoutRespuestaCompensador(Long timeoutRespuestaCompensador) {
        this.timeoutRespuestaCompensador = timeoutRespuestaCompensador;
    }

    /**
     * @return the activo
     */
    public Boolean getActivo() {
        return activo;
    }

}
