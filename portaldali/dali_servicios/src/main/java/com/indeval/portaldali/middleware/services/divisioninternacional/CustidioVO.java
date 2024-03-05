/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.divisioninternacional;

import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class CustidioVO extends AgenteVO {

    /** Constante de Serializacion */
    private static final long serialVersionUID = 1L;

    private String descripcion;

    private String pais;

    private String moneda;

    /**
     * @return String
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return String
     */
    public String getMoneda() {
        return moneda;
    }

    /**
     * @param moneda
     */
    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    /**
     * @return String
     */
    public String getPais() {
        return pais;
    }

    /**
     * @param pais
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * @see com.indeval.portaldali.middleware.services.modelo.AgenteVO#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {
    }

}