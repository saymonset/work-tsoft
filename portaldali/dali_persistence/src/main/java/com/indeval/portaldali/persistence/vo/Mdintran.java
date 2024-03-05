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
public class Mdintran implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private String uVersion;

    private EmisionPersistence emision;

    private String aplicacion;

    private String claveReporto;

    private String mDinero;

    private String sociedadSerie;

    private String transmision;

    private String usuario;

    private String bajaLogica;

    private String mercado;

    private String origenAplicacion;

    private String origen;

    private String areaTrabajo;

    private String folioDescripcion;

    private String nombreUsuario;

    private String ipAddress;

    private String divisa;

    private String cuentaOrigen;

    private String cuentaOrigenRecep;

    private Date fechaHora;

    private Date fechaReporto;

    private Date fechaLiquidacion;

    private Date fechaConcer;

    private BigDecimal posicAnt;

    private BigDecimal precioPorTitulo;

    private BigDecimal tasaPremio;

    private BigDecimal sneComp;

    private BigDecimal sneVend;

    private BigDecimal tasaDescuento;

    private BigDecimal tasaRendimiento;

    private BigDecimal valorCarteraComp;

    private BigDecimal cantidadOperada;

    private BigDecimal valorCarteraVend;

    private BigDecimal posicAct;

    private BigDecimal importeParcial;

    private Integer folioTransmision;

    private Integer folioControl;

    private Integer liquidacion;

    private Integer diasPlazo;

    private Integer correccion;

    private AgentePersistence agente;

    private AgentePersistence agenteRecep;

    private String mensajeJava;

    private MdintranPk mdintranPk;

    private String tv;

    private String emisora;

    private String serie;

    private String cupon;

    /**
     * @return the emision
     */
    public EmisionPersistence getEmision() {
        return emision;
    }

    /**
     * @param emision
     *            the emision to set
     */
    public void setEmision(EmisionPersistence emision) {
        this.emision = emision;
    }

    /**
     * @return the agente
     */
    public AgentePersistence getAgente() {
        return agente;
    }

    /**
     * @param agente
     *            the agente to set
     */
    public void setAgente(AgentePersistence agente) {
        this.agente = agente;
    }

    /**
     * @return the agenteRecep
     */
    public AgentePersistence getAgenteRecep() {
        return agenteRecep;
    }

    /**
     * @param agenteRecep
     *            the agenteRecep to set
     */
    public void setAgenteRecep(AgentePersistence agenteRecep) {
        this.agenteRecep = agenteRecep;
    }

    /**
     * @return the uVersion
     */
    public String getUVersion() {
        return uVersion;
    }

    /**
     * @return the aplicacion
     */
    public String getAplicacion() {
        return aplicacion;
    }

    /**
     * @return the claveReporto
     */
    public String getClaveReporto() {
        return claveReporto;
    }

    /**
     * @return the mDinero
     */
    public String getMDinero() {
        return mDinero;
    }

    /**
     * @return the sociedadSerie
     */
    public String getSociedadSerie() {
        return sociedadSerie;
    }

    /**
     * @return the transmision
     */
    public String getTransmision() {
        return transmision;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @return the bajaLogica
     */
    public String getBajaLogica() {
        return bajaLogica;
    }

    /**
     * @return the mercado
     */
    public String getMercado() {
        return mercado;
    }

    /**
     * @return the origenAplicacion
     */
    public String getOrigenAplicacion() {
        return origenAplicacion;
    }

    /**
     * @return the origen
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * @return the areaTrabajo
     */
    public String getAreaTrabajo() {
        return areaTrabajo;
    }

    /**
     * @return the folioDescripcion
     */
    public String getFolioDescripcion() {
        return folioDescripcion;
    }

    /**
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * @return the ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @return the divisa
     */
    public String getDivisa() {
        return divisa;
    }

    /**
     * @return the cuentaOrigen
     */
    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    /**
     * @return the cuentaOrigenRecep
     */
    public String getCuentaOrigenRecep() {
        return cuentaOrigenRecep;
    }

    /**
     * @return the fechaHora
     */
    public Date getFechaHora() {
        return fechaHora;
    }

    /**
     * @return the fechaReporto
     */
    public Date getFechaReporto() {
        return fechaReporto;
    }

    /**
     * @return the fechaLiquidacion
     */
    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    /**
     * @return the fechaConcer
     */
    public Date getFechaConcer() {
        return fechaConcer;
    }

    /**
     * @return the posicAnt
     */
    public BigDecimal getPosicAnt() {
        return posicAnt;
    }

    /**
     * @return the precioPorTitulo
     */
    public BigDecimal getPrecioPorTitulo() {
        return precioPorTitulo;
    }

    /**
     * @return the tasaPremio
     */
    public BigDecimal getTasaPremio() {
        return tasaPremio;
    }

    /**
     * @return the sneComp
     */
    public BigDecimal getSneComp() {
        return sneComp;
    }

    /**
     * @return the sneVend
     */
    public BigDecimal getSneVend() {
        return sneVend;
    }

    /**
     * @return the tasaDescuento
     */
    public BigDecimal getTasaDescuento() {
        return tasaDescuento;
    }

    /**
     * @return the tasaRendimiento
     */
    public BigDecimal getTasaRendimiento() {
        return tasaRendimiento;
    }

    /**
     * @return the valorCarteraComp
     */
    public BigDecimal getValorCarteraComp() {
        return valorCarteraComp;
    }

    /**
     * @return the cantidadOperada
     */
    public BigDecimal getCantidadOperada() {
        return cantidadOperada;
    }

    /**
     * @return the valorCarteraVend
     */
    public BigDecimal getValorCarteraVend() {
        return valorCarteraVend;
    }

    /**
     * @return the posicAct
     */
    public BigDecimal getPosicAct() {
        return posicAct;
    }

    /**
     * @return the importeParcial
     */
    public BigDecimal getImporteParcial() {
        return importeParcial;
    }

    /**
     * @return the folioTransmision
     */
    public Integer getFolioTransmision() {
        return folioTransmision;
    }

    /**
     * @return the folioControl
     */
    public Integer getFolioControl() {
        return folioControl;
    }

    /**
     * @return the liquidacion
     */
    public Integer getLiquidacion() {
        return liquidacion;
    }

    /**
     * @return the diasPlazo
     */
    public Integer getDiasPlazo() {
        return diasPlazo;
    }

    /**
     * @return the correccion
     */
    public Integer getCorreccion() {
        return correccion;
    }

    /**
     * @return the mdintranPk
     */
    public MdintranPk getMdintranPk() {
        return mdintranPk;
    }

    /**
     * @return the tv
     */
    public String getTv() {
        return tv;
    }

    /**
     * @return the emisora
     */
    public String getEmisora() {
        return emisora;
    }

    /**
     * @return the serie
     */
    public String getSerie() {
        return serie;
    }

    /**
     * @return the cupon
     */
    public String getCupon() {
        return cupon;
    }

    public void setAplicacion(String aplicacion) {
        this.aplicacion = aplicacion;
    }

    public void setAreaTrabajo(String areaTrabajo) {
        this.areaTrabajo = areaTrabajo;
    }

    public void setBajaLogica(String bajaLogica) {
        this.bajaLogica = bajaLogica;
    }

    public void setCantidadOperada(BigDecimal cantidadOperada) {
        this.cantidadOperada = cantidadOperada;
    }

    public void setClaveReporto(String claveReporto) {
        this.claveReporto = claveReporto;
    }

    public void setCorreccion(Integer correccion) {
        this.correccion = correccion;
    }

    public void setCuentaOrigen(String cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public void setCuentaOrigenRecep(String cuentaOrigenRecep) {
        this.cuentaOrigenRecep = cuentaOrigenRecep;
    }

    public void setDiasPlazo(Integer diasPlazo) {
        this.diasPlazo = diasPlazo;
    }

    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }

    public void setFechaConcer(Date fechaConcer) {
        this.fechaConcer = fechaConcer;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    public void setFechaReporto(Date fechaReporto) {
        this.fechaReporto = fechaReporto;
    }

    public void setFolioControl(Integer folioControl) {
        this.folioControl = folioControl;
    }

    public void setFolioDescripcion(String folioDescripcion) {
        this.folioDescripcion = folioDescripcion;
    }

    public void setFolioTransmision(Integer folioTransmision) {
        this.folioTransmision = folioTransmision;
    }

    public void setImporteParcial(BigDecimal importeParcial) {
        this.importeParcial = importeParcial;
    }

    public void setLiquidacion(Integer liquidacion) {
        this.liquidacion = liquidacion;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setMDinero(String dinero) {
        mDinero = dinero;
    }

    public void setMercado(String mercado) {
        this.mercado = mercado;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public void setPosicAct(BigDecimal posicAct) {
        this.posicAct = posicAct;
    }

    public void setOrigenAplicacion(String origenAplicacion) {
        this.origenAplicacion = origenAplicacion;
    }

    public void setPosicAnt(BigDecimal posicAnt) {
        this.posicAnt = posicAnt;
    }

    public void setPrecioPorTitulo(BigDecimal precioPorTitulo) {
        this.precioPorTitulo = precioPorTitulo;
    }

    public void setSneComp(BigDecimal sneComp) {
        this.sneComp = sneComp;
    }

    public void setSneVend(BigDecimal sneVend) {
        this.sneVend = sneVend;
    }

    public void setSociedadSerie(String sociedadSerie) {
        this.sociedadSerie = sociedadSerie;
    }

    public void setTasaDescuento(BigDecimal tasaDescuento) {
        this.tasaDescuento = tasaDescuento;
    }

    public void setTasaRendimiento(BigDecimal tasaRendimiento) {
        this.tasaRendimiento = tasaRendimiento;
    }

    public void setTasaPremio(BigDecimal tasaPremio) {
        this.tasaPremio = tasaPremio;
    }

    public void setTransmision(String transmision) {
        this.transmision = transmision;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setUVersion(String version) {
        uVersion = version;
    }

    public void setValorCarteraComp(BigDecimal valorCarteraComp) {
        this.valorCarteraComp = valorCarteraComp;
    }

    public void setValorCarteraVend(BigDecimal valorCarteraVend) {
        this.valorCarteraVend = valorCarteraVend;
    }

    public void setMdintranPk(MdintranPk mdintranPk) {
        this.mdintranPk = mdintranPk;
    }

    /**
     * @return Returns the mensajeJava.
     */
    public String getMensajeJava() {
        return mensajeJava;
    }

    public void setMensajeJava(String mensajeJava) {
        this.mensajeJava = mensajeJava;
    }

    /**
     * @param cupon The cupon to set.
     */
    public void setCupon(String cupon) {
        this.cupon = cupon;
    }

    /**
     * @param emisora The emisora to set.
     */
    public void setEmisora(String emisora) {
        this.emisora = emisora;
    }

    /**
     * @param serie The serie to set.
     */
    public void setSerie(String serie) {
        this.serie = serie;
    }

    /**
     * @param tv The tv to set.
     */
    public void setTv(String tv) {
        this.tv = tv;
    }

}
