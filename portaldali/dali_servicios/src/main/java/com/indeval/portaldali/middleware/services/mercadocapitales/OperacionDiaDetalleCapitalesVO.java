/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class OperacionDiaDetalleCapitalesVO extends AbstractBaseDTO {
    
    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;

    private Integer folioControl;

    private AgenteVO comprador;

    private AgenteVO vendedor;

    private EmisionVO emisionVO;

    private Integer folioTransmision;

    private Date fechaHora;

    private BigDecimal cantidadOperada;

    private String claveReporto;

    private String bajaLogica;

    private String origen;

    private String llaveFolio;

    private String divisa;

    private BigDecimal precio;

    private Integer liquidacion;

    private BigDecimal importe;

    private String aplicacion;

    private AgenteVO emisor;

    private AgenteVO receptor;

    private String folioDescripcion;

    /**
     * @return the bajaLogica
     */
    public String getBajaLogica() {
        return bajaLogica;
    }

    /**
     * @param bajaLogica
     *            the bajaLogica to set
     */
    public void setBajaLogica(String bajaLogica) {
        this.bajaLogica = bajaLogica;
    }

    /**
     * @return the cantidadOperada
     */
    public BigDecimal getCantidadOperada() {
        return cantidadOperada;
    }

    /**
     * @param cantidadOperada
     *            the cantidadOperada to set
     */
    public void setCantidadOperada(BigDecimal cantidadOperada) {
        this.cantidadOperada = cantidadOperada;
    }

    /**
     * @return the claveReporto
     */
    public String getClaveReporto() {
        return claveReporto;
    }

    /**
     * @param claveReporto
     *            the claveReporto to set
     */
    public void setClaveReporto(String claveReporto) {
        this.claveReporto = claveReporto;
    }

    /**
     * @return the emisionVO
     */
    public EmisionVO getEmisionVO() {
        return emisionVO;
    }

    /**
     * @param emisionVO
     *            the emisionVO to set
     */
    public void setEmisionVO(EmisionVO emisionVO) {
        this.emisionVO = emisionVO;
    }

    /**
     * @return the fechaHora
     */
    public Date getFechaHora() {
        return fechaHora;
    }

    /**
     * @param fechaHora
     *            the fechaHora to set
     */
    public void setFechaHora(Timestamp fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
     * @return the folioControl
     */
    public Integer getFolioControl() {
        return folioControl;
    }

    /**
     * @param folioControl
     *            the folioControl to set
     */
    public void setFolioControl(Integer folioControl) {
        this.folioControl = folioControl;
    }

    /**
     * @return the folioTransmision
     */
    public Integer getFolioTransmision() {
        return folioTransmision;
    }

    /**
     * @param folioTransmision
     *            the folioTransmision to set
     */
    public void setFolioTransmision(Integer folioTransmision) {
        this.folioTransmision = folioTransmision;
    }

    /**
     * @return the origen
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * @param origen
     *            the origen to set
     */
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    /**
     * @return the comprador
     */
    public AgenteVO getComprador() {
        return comprador;
    }

    /**
     * @param comprador
     *            the comprador to set
     */
    public void setComprador(AgenteVO comprador) {
        this.comprador = comprador;
    }

    /**
     * @return the vendedor
     */
    public AgenteVO getVendedor() {
        return vendedor;
    }

    /**
     * @param vendedor
     *            the vendedor to set
     */
    public void setVendedor(AgenteVO vendedor) {
        this.vendedor = vendedor;
    }

    /**
     * @return the divisa
     */
    public String getDivisa() {
        return divisa;
    }

    /**
     * @param divisa
     *            the divisa to set
     */
    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }

    /**
     * @return the importe
     */
    public BigDecimal getImporte() {
        return importe;
    }

    /**
     * @param importe
     *            the importe to set
     */
    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    /**
     * @return the liquidacion
     */
    public Integer getLiquidacion() {
        return liquidacion;
    }

    /**
     * @param liquidacion
     *            the liquidacion to set
     */
    public void setLiquidacion(Integer liquidacion) {
        this.liquidacion = liquidacion;
    }

    /**
     * @return the llaveFolio
     */
    public String getLlaveFolio() {
        return llaveFolio;
    }

    /**
     * @param llaveFolio
     *            the llaveFolio to set
     */
    public void setLlaveFolio(String llaveFolio) {
        this.llaveFolio = llaveFolio;
    }

    /**
     * @return the precio
     */
    public BigDecimal getPrecio() {
        return precio;
    }

    /**
     * @param precio
     *            the precio to set
     */
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    /**
     * @return the aplicacion
     */
    public String getAplicacion() {
        return aplicacion;
    }

    /**
     * @param aplicacion
     *            the aplicacion to set
     */
    public void setAplicacion(String aplicacion) {
        this.aplicacion = aplicacion;
    }

    /**
     * @return the emisor
     */
    public AgenteVO getEmisor() {
        return emisor;
    }

    /**
     * @param emisor
     *            the emisor to set
     */
    public void setEmisor(AgenteVO emisor) {
        this.emisor = emisor;
    }

    /**
     * @return the receptor
     */
    public AgenteVO getReceptor() {
        return receptor;
    }

    /**
     * @param receptor
     *            the receptor to set
     */
    public void setReceptor(AgenteVO receptor) {
        this.receptor = receptor;
    }

    /**
     * @return the folioDescripcion
     */
    public String getFolioDescripcion() {
        return folioDescripcion;
    }

    /**
     * @param folioDescripcion
     *            the folioDescripcion to set
     */
    public void setFolioDescripcion(String folioDescripcion) {
        this.folioDescripcion = folioDescripcion;
    }

    /**
     * @param fechaHora
     *            the fechaHora to set
     */
    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {
    }
    
}
