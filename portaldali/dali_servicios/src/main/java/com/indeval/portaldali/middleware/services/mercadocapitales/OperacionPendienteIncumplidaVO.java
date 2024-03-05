/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import org.springframework.validation.Errors;
/**
 * TODO revisar el uso de Double
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class OperacionPendienteIncumplidaVO extends AbstractBaseDTO {

    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;

    private String folioBMV;

    private String folioInd;

    private String estado;

    private String idVendedor;

    private String folioVendedor;

    private String cuentaVendedor;

    private String claveValor;

    private String idComprador;

    private String tipoValor;

    private EmisionVO emision;

    private BigInteger liquidacion;

    private BigDecimal precio;

    private Date fecha;

    private Date fechaConcertacion;

    private Date fechaLiqOrig;

    private String origen;

    private String to;

    private String st;

    private Double cantidadOperada;

    /**
     * @return String
     */
    public String getCuentaVendedor() {
        return cuentaVendedor;
    }

    /**
     * @param cuentaVendedor
     */
    public void setCuentaVendedor(String cuentaVendedor) {
        this.cuentaVendedor = cuentaVendedor;
    }

    /**
     * @return EmisionVO
     */
    public EmisionVO getEmision() {
        return emision;
    }

    /**
     * @param emision
     */
    public void setEmision(EmisionVO emision) {
        this.emision = emision;
    }

    /**
     * @return String
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return Date
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha
     */
    public void setFecha(Date fecha) {
        this.fecha = clona(fecha);
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
        this.fechaConcertacion = clona(fechaConcertacion);
    }

    /**
     * @return Date
     */
    public Date getFechaLiqOrig() {
        return fechaLiqOrig;
    }

    /**
     * @param fechaLiqOrig
     */
    public void setFechaLiqOrig(Date fechaLiqOrig) {
        this.fechaLiqOrig = clona(fechaLiqOrig);
    }

    /**
     * @return String
     */
    public String getFolioBMV() {
        return folioBMV;
    }

    /**
     * @param folioBMV
     */
    public void setFolioBMV(String folioBMV) {
        this.folioBMV = folioBMV;
    }

    /**
     * @return String
     */ 
    public String getFolioInd() {
        return folioInd;
    }

    /**
     * @param folioInd
     */
    public void setFolioInd(String folioInd) {
        this.folioInd = folioInd;
    }

    /**
     * @return String
     */
    public String getFolioVendedor() {
        return folioVendedor;
    }

    /**
     * @param folioVendedor
     */
    public void setFolioVendedor(String folioVendedor) {
        this.folioVendedor = folioVendedor;
    }

    /**
     * @return String
     */
    public String getIdComprador() {
        return idComprador;
    }

    /**
     * @param idComprador
     */
    public void setIdComprador(String idComprador) {
        this.idComprador = idComprador;
    }

    /**
     * @return String
     */
    public String getIdVendedor() {
        return idVendedor;
    }

    /**
     * @param idVendedor
     */
    public void setIdVendedor(String idVendedor) {
        this.idVendedor = idVendedor;
    }

    /**
     * @return BigInteger
     */
    public BigInteger getLiquidacion() {
        return liquidacion;
    }

    /**
     * @param liquidacion
     */
    public void setLiquidacion(BigInteger liquidacion) {
        this.liquidacion = liquidacion;
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
     * @return BigDecimal
     */
    public BigDecimal getPrecio() {
        return precio;
    }

    /**
     * @param precio
     */
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    /**
     * @return String
     */
    public String getSt() {
        return st;
    }

    /**
     * @param st
     */
    public void setSt(String st) {
        this.st = st;
    }

    /**
     * @return String
     */
    public String getTipoValor() {
        return tipoValor;
    }

    /**
     * @param tipoValor
     */
    public void setTipoValor(String tipoValor) {
        this.tipoValor = tipoValor;
    }

    /**
     * @return String
     */
    public String getTo() {
        return to;
    }

    /**
     * @param to
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * @return String
     */
    public String getClaveValor() {
        return claveValor;
    }

    /**
     * @param claveValor
     */
    public void setClaveValor(String claveValor) {
        this.claveValor = claveValor;
    }

    /**
     * @return Double
     */
    public Double getCantidadOperada() {
        return cantidadOperada;
    }

    /**
     * @param cantidadOperada
     */
    public void setCantidadOperada(Double cantidadOperada) {
        this.cantidadOperada = cantidadOperada;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object arg0, Errors arg1) {
    }
    
}
