/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.divisioninternacional;

import java.math.BigDecimal;
import java.util.Date;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class CapturaOperacionParams extends AbstractBaseDTO {

    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;

    private AgenteVO agente;

    private EmisionVO emision;

    private Integer tipoOperacion;

    private Date fechaNotificacion;

    private Date fechaLiquidacion;

    private Date fechaOperacion;

    private BigDecimal cantidad;

    private String custodio;

    private String descContraparte;

    private String cuentaContraparte;

    private String nombreDepositoLiq;

    private String instruccEspeciales;

    private String nombreCuenta;

    private String numeroCuenta;

    private String usuario;

    private String nombreUsuario;

    /**
     * @return AgenteVO
     */
    public AgenteVO getAgente() {
        return agente;
    }

    /**
     * @param agente
     */
    public void setAgente(AgenteVO agente) {
        this.agente = agente;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad
     */
    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return String
     */
    public String getCuentaContraparte() {
        return cuentaContraparte;
    }

    /**
     * @param cuentaContraparte
     */
    public void setCuentaContraparte(String cuentaContraparte) {
        this.cuentaContraparte = cuentaContraparte;
    }

    /**
     * @return String
     */
    public String getCustodio() {
        return custodio;
    }

    /**
     * @param custodio
     */
    public void setCustodio(String custodio) {
        this.custodio = custodio;
    }

    /**
     * @return String
     */
    public String getDescContraparte() {
        return descContraparte;
    }

    /**
     * @param descContraparte
     */
    public void setDescContraparte(String descContraparte) {
        this.descContraparte = descContraparte;
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
    public Date getFechaNotificacion() {
        return fechaNotificacion;
    }

    /**
     * @param fechaNotificacion
     */
    public void setFechaNotificacion(Date fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }

    /**
     * @return Date
     */
    public Date getFechaOperacion() {
        return fechaOperacion;
    }

    /**
     * @param fechaOperacion
     */
    public void setFechaOperacion(Date fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    /**
     * @return String
     */
    public String getInstruccEspeciales() {
        return instruccEspeciales;
    }

    /**
     * @param instruccEspeciales
     */
    public void setInstruccEspeciales(String instruccEspeciales) {
        this.instruccEspeciales = instruccEspeciales;
    }

    /**
     * @return String
     */
    public String getNombreCuenta() {
        return nombreCuenta;
    }

    /**
     * @param nombreCuenta
     */
    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    /**
     * @return String
     */
    public String getNombreDepositoLiq() {
        return nombreDepositoLiq;
    }

    /**
     * @param nombreDepositoLiq
     */
    public void setNombreDepositoLiq(String nombreDepositoLiq) {
        this.nombreDepositoLiq = nombreDepositoLiq;
    }

    /**
     * @return String
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * @param nombreUsuario
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * @return String
     */
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    /**
     * @param numeroCuenta
     */
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    /**
     * @return Integer
     */
    public Integer getTipoOperacion() {
        return tipoOperacion;
    }

    /**
     * @param tipoOperacion
     */
    public void setTipoOperacion(Integer tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    /**
     * @return String
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {
    }

}
