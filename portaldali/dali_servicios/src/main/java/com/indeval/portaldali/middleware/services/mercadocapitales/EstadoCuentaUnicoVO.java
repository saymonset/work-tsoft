/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import java.math.BigDecimal;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class EstadoCuentaUnicoVO extends AbstractBaseDTO {

    /** N&uacute;mero de serializaci&oacute;n */
    private static final long serialVersionUID = 1L;

    private String[] cuentasAgente;

    private EmisionVO[] emisionesByCuenta;

    private PaginaVO paginaHistoricos;

    private BigDecimal saldoDisponible;

    private BigDecimal saldoCalculado;

    private String ley;

    private BigDecimal importe;

    private String cuentaActual;

    private EmisionVO emisionVO;

    /**
     * @return PaginaVO
     */
    public PaginaVO getPaginaHistoricos() {
        return paginaHistoricos;
    }

    /**
     * @param paginaHistoricos
     */
    public void setPaginaHistoricos(PaginaVO paginaHistoricos) {
        this.paginaHistoricos = paginaHistoricos;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getSaldoCalculado() {
        return saldoCalculado;
    }

    /**
     * @param saldoCalculado
     */
    public void setSaldoCalculado(BigDecimal saldoCalculado) {
        this.saldoCalculado = saldoCalculado;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getSaldoDisponible() {
        return saldoDisponible;
    }

    /**
     * @param saldoDisponible
     */
    public void setSaldoDisponible(BigDecimal saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    /**
     * @return String[] 
     */
    public String[] getCuentasAgente() {
        return cuentasAgente;
    }

    /**
     * @param cuentasAgente
     */
    public void setCuentasAgente(String[] cuentasAgente) {
        this.cuentasAgente = cuentasAgente;
    }

    /**
     * @return EmisionVO[]
     */
    public EmisionVO[] getEmisionesByCuenta() {
        return emisionesByCuenta;
    }

    /**
     * @param emisionesByCuenta
     */
    public void setEmisionesByCuenta(EmisionVO[] emisionesByCuenta) {
        this.emisionesByCuenta = emisionesByCuenta;
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
    public String getLey() {
        return ley;
    }

    /**
     * @param ley
     */
    public void setLey(String ley) {
        this.ley = ley;
    }

    /**
     * @return String
     */
    public String getCuentaActual() {
        return cuentaActual;
    }

    /**
     * @param cuentaActual
     */
    public void setCuentaActual(String cuentaActual) {
        this.cuentaActual = cuentaActual;
    }

    /**
     * @return EmisionVO
     */
    public EmisionVO getEmisionVO() {
        return emisionVO;
    }

    /**
     * @param emisionVO
     */
    public void setEmisionVO(EmisionVO emisionVO) {
        this.emisionVO = emisionVO;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {

    }
    
}
