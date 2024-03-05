/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import java.math.BigDecimal;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class HistoricoEstadoCuentaTraspasoVO extends AbstractBaseDTO {

    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;

    private AgenteVO contraparte;

    private String tipoOperacion;

    private String folioReferencia;

    private BigDecimal entradas;

    private BigDecimal salidas;

    private String ley;

    private BigDecimal importe;

    private EmisionVO emisionVO;

    private String cuentaActual;

    /**
     * Constructor
     */
    public HistoricoEstadoCuentaTraspasoVO() {
        this.entradas = BIG_DECIMAL_ZERO;
        this.salidas = BIG_DECIMAL_ZERO;
        this.importe = BIG_DECIMAL_ZERO;
    }

    /**
     * @return AgenteVO
     */
    public AgenteVO getContraparte() {
        return contraparte;
    }

    /**
     * @param contraparte
     */
    public void setContraparte(AgenteVO contraparte) {
        this.contraparte = contraparte;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getEntradas() {
        return entradas;
    }

    /**
     * @param entradas
     */
    public void setEntradas(BigDecimal entradas) {
        this.entradas = entradas;
    }

    /**
     * @return String
     */
    public String getFolioReferencia() {
        return folioReferencia;
    }

    /**
     * @param folioReferencia
     */
    public void setFolioReferencia(String folioReferencia) {
        this.folioReferencia = folioReferencia;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getSalidas() {
        return salidas;
    }

    /**
     * @param salidas
     */
    public void setSalidas(BigDecimal salidas) {
        this.salidas = salidas;
    }

    /**
     * @return String
     */
    public String getTipoOperacion() {
        return tipoOperacion;
    }

    /**
     * @param tipoOperacion
     */
    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
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
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {
    }
    
}
