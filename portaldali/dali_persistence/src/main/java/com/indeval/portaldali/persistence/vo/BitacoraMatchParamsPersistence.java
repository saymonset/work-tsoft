/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.vo;

import java.math.BigInteger;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;



/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class BitacoraMatchParamsPersistence {

    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;

    private BigInteger idBitacora;

    private String codigoOperacion;

    private String idTraspasante;

    private String idReceptor;

    private String folioTraspasante;

    private String folioReceptor;

    private String cuentaTraspasante;

    private String cuentaReceptor;

    private String rol;

    private String folioInstruccionTraspasante; // FK ->folioInstruccion

    private String folioInstruccionReceptor;

    private Date fecha;

    private String instancia;

    private BigInteger idBitacoraMatch;

    private String folioMatch;

    private String tipoOperacion;

    private String tipoMensaje;

    private Date fechaHoraRecepcion;

    private Date fechaLiquidacion;

    private BigInteger matchKey;

    private String expira;

    private EmisionPK emision;

    private String mercado;

    private BigInteger cantidadTitulos;

    private String folioUsuario;

    private boolean operacion;

    private Boolean remitidos;

    /**
     * Metodo que verifica que los parametros requeridos existan
     * 
     * @return true en caso de existan los parametros, false en caso contrario
     */
    public boolean validaRequeridos() {
        if (StringUtils.isNotBlank(this.getIdTraspasante())
                && StringUtils.isNotBlank(this.getFolioTraspasante())
                && StringUtils.isNotBlank(this.getRol())) {
            return true;
        }
        return false;

    }

    /**
     * 
     * @return
     */
    public Boolean getRemitidos() {
        return remitidos;
    }

    /**
     * 
     * @param remitidos
     */
    public void setRemitidos(Boolean remitidos) {
        this.remitidos = remitidos;
    }

    /**
     * 
     * @return
     */
    public String getFolioUsuario() {
        return folioUsuario;
    }

    /**
     * 
     * @param folioUsuario
     */

    public void setFolioUsuario(String folioUsuario) {
        this.folioUsuario = folioUsuario;
    }

    /**
     * 
     * @return
     */
    public BigInteger getCantidadTitulos() {
        return cantidadTitulos;
    }

    /**
     * 
     * @param cantidadTitulos
     */
    public void setCantidadTitulos(BigInteger cantidadTitulos) {
        this.cantidadTitulos = cantidadTitulos;
    }

    /**
     * @return the emision
     */
    public EmisionPK getEmision() {
        return emision;
    }

    /**
     * @param emision the emision to set
     */
    public void setEmision(EmisionPK emision) {
        this.emision = emision;
    }

    /**
     * @return the codigoOperacion
     */
    public String getCodigoOperacion() {
        return codigoOperacion;
    }

    /**
     * @param codigoOperacion
     *            the codigoOperacion to set
     */
    public void setCodigoOperacion(String codigoOperacion) {
        this.codigoOperacion = codigoOperacion;
    }

    /**
     * @return the expira
     */
    public String getExpira() {
        return expira;
    }

    /**
     * @param expira
     *            the expira to set
     */
    public void setExpira(String expira) {
        this.expira = expira;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha
     *            the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the fechaHoraRecepcion
     */
    public Date getFechaHoraRecepcion() {
        return fechaHoraRecepcion;
    }

    /**
     * @param fechaHoraRecepcion
     *            the fechaHoraRecepcion to set
     */
    public void setFechaHoraRecepcion(Date fechaHoraRecepcion) {
        this.fechaHoraRecepcion = fechaHoraRecepcion;
    }

    /**
     * @return the fechaLiquidacion
     */
    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    /**
     * @param fechaLiquidacion
     *            the fechaLiquidacion to set
     */
    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    /**
     * @return the folioInstruccionReceptor
     */
    public String getFolioInstruccionReceptor() {
        return folioInstruccionReceptor;
    }

    /**
     * @param folioInstruccionReceptor
     *            the folioInstruccionReceptor to set
     */
    public void setFolioInstruccionReceptor(String folioInstruccionReceptor) {
        this.folioInstruccionReceptor = folioInstruccionReceptor;
    }

    /**
     * @return the folioInstruccionTraspasante
     */
    public String getFolioInstruccionTraspasante() {
        return folioInstruccionTraspasante;
    }

    /**
     * @param folioInstruccionTraspasante
     *            the folioInstruccionTraspasante to set
     */
    public void setFolioInstruccionTraspasante(
            String folioInstruccionTraspasante) {
        this.folioInstruccionTraspasante = folioInstruccionTraspasante;
    }

    /**
     * @return the folioMatch
     */
    public String getFolioMatch() {
        return folioMatch;
    }

    /**
     * @param folioMatch
     *            the folioMatch to set
     */
    public void setFolioMatch(String folioMatch) {
        this.folioMatch = folioMatch;
    }

    /**
     * @return the folioReceptor
     */
    public String getFolioReceptor() {
        return folioReceptor;
    }

    /**
     * @param folioReceptor
     *            the folioReceptor to set
     */
    public void setFolioReceptor(String folioReceptor) {
        this.folioReceptor = folioReceptor;
    }

    /**
     * @return the folioTraspasante
     */
    public String getFolioTraspasante() {
        return folioTraspasante;
    }

    /**
     * @param folioTraspasante
     *            the folioTraspasante to set
     */
    public void setFolioTraspasante(String folioTraspasante) {
        this.folioTraspasante = folioTraspasante;
    }

    /**
     * @return the idBitacora
     */
    public BigInteger getIdBitacora() {
        return idBitacora;
    }

    /**
     * @param idBitacora
     *            the idBitacora to set
     */
    public void setIdBitacora(BigInteger idBitacora) {
        this.idBitacora = idBitacora;
    }

    /**
     * @return the idBitacoraMatch
     */
    public BigInteger getIdBitacoraMatch() {
        return idBitacoraMatch;
    }

    /**
     * @param idBitacoraMatch
     *            the idBitacoraMatch to set
     */
    public void setIdBitacoraMatch(BigInteger idBitacoraMatch) {
        this.idBitacoraMatch = idBitacoraMatch;
    }

    /**
     * @return the idReceptor
     */
    public String getIdReceptor() {
        return idReceptor;
    }

    /**
     * @param idReceptor
     *            the idReceptor to set
     */
    public void setIdReceptor(String idReceptor) {
        this.idReceptor = idReceptor;
    }

    /**
     * @return the idTraspasante
     */
    public String getIdTraspasante() {
        return idTraspasante;
    }

    /**
     * @param idTraspasante
     *            the idTraspasante to set
     */
    public void setIdTraspasante(String idTraspasante) {
        this.idTraspasante = idTraspasante;
    }

    /**
     * @return the instancia
     */
    public String getInstancia() {
        return instancia;
    }

    /**
     * @param instancia
     *            the instancia to set
     */
    public void setInstancia(String instancia) {
        this.instancia = instancia;
    }

    /**
     * @return the matchKey
     */
    public BigInteger getMatchKey() {
        return matchKey;
    }

    /**
     * @param matchKey
     *            the matchKey to set
     */
    public void setMatchKey(BigInteger matchKey) {
        this.matchKey = matchKey;
    }

    /**
     * @return the tipoMensaje
     */
    public String getTipoMensaje() {
        return tipoMensaje;
    }

    /**
     * @param tipoMensaje
     *            the tipoMensaje to set
     */
    public void setTipoMensaje(String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    /**
     * @return the tipoOperacion
     */
    public String getTipoOperacion() {
        return tipoOperacion;
    }

    /**
     * @param tipoOperacion
     *            the tipoOperacion to set
     */
    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {

    }

    /**
     * @return the rol
     */
    public String getRol() {
        return rol;
    }

    /**
     * @param rol
     *            the rol to set
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * 
     * @return
     */
    public String getMercado() {
        return mercado;
    }

    /**
     * 
     * @param mercado
     */
    public void setMercado(String mercado) {
        this.mercado = mercado;
    }

    /**
     * 
     * @return
     */
    public String getCuentaReceptor() {
        return cuentaReceptor;
    }

    /**
     * 
     * @param cuentaReceptor
     */
    public void setCuentaReceptor(String cuentaReceptor) {
        this.cuentaReceptor = cuentaReceptor;
    }

    /**
     * 
     * @return
     */
    public String getCuentaTraspasante() {
        return cuentaTraspasante;
    }

    /**
     * 
     * @param cuentaTraspasante
     */
    public void setCuentaTraspasante(String cuentaTraspasante) {
        this.cuentaTraspasante = cuentaTraspasante;
    }

    /**
     * @return the operacion
     */
    public boolean isOperacion() {
        return operacion;
    }

    /**
     * @param operacion
     *            the operacion to set
     */
    public void setOperacion(boolean operacion) {
        this.operacion = operacion;
    }

}
