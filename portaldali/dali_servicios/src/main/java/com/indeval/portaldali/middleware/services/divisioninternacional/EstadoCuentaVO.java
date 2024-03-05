/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.divisioninternacional;

import java.math.BigDecimal;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class EstadoCuentaVO extends AbstractBaseDTO {

    /** Constante de Serializacion */
    private static final long serialVersionUID = 1L;

    private String tipoOperacion;

    private String folioControl;

    private EmisionVO claveValor;

    private BigDecimal entrada;

    private BigDecimal salida;

    private AgenteVO beneficiario;

    /**
     * @return AgenteVO
     */
    public AgenteVO getBeneficiario() {
        return beneficiario;
    }

    /**
     * @param beneficiario
     */
    public void setBeneficiario(AgenteVO beneficiario) {
        this.beneficiario = beneficiario;
    }

    /**
     * @return EmisionVO
     */
    public EmisionVO getClaveValor() {
        return claveValor;
    }

    /**
     * @param claveValor
     */
    public void setClaveValor(EmisionVO claveValor) {
        this.claveValor = claveValor;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getEntrada() {
        return entrada;
    }

    /**
     * @param entrada
     */
    public void setEntrada(BigDecimal entrada) {
        this.entrada = entrada;
    }

    /**
     * @return String
     */
    public String getFolioControl() {
        return folioControl;
    }

    /**
     * @param folioControl
     */
    public void setFolioControl(String folioControl) {
        this.folioControl = folioControl;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getSalida() {
        return salida;
    }

    /**
     * @param salida
     */
    public void setSalida(BigDecimal salida) {
        this.salida = salida;
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
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object arg0, Errors arg1) {
    }

}