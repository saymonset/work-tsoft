/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;;

/**
 * @author <a href="mailto:ivan.kazakov@2hsoftware.com.mx">Ivan Kazakov</a>
 *
 */
public class AgenteArqueoVO implements Serializable {
    
	/**
     * Constante de versi&oacute;n de serializaci&oacute;n 
     */
    private static final long serialVersionUID = (long)1;
    
    /**Datos del Agente */
    private AgenteVO agente;
    
    /**Posicion Actual */
    private BigInteger posicionActual;
    
    /**Valua Mercado */
    private BigDecimal valuaMercado;
    
    /**Valuacion Nominal */
    private BigDecimal valuacionNominal;

    /**
     * Obtiene el Agente
     * 
     * @return Agente
     */
    public AgenteVO getAgente() {
        return agente;
    }

    /**
     * Establece el Agente
     * 
     * @param agente Agente a establecer
     */
    public void setAgente(AgenteVO agente) {
        this.agente = agente;
    }

    /**
     * Obtiene la Posicion Actual
     * 
     * @return Posicion Actual
     */
    public BigInteger getPosicionActual() {
        return posicionActual;
    }

    /**
     * Establece la Posicion Actual
     * 
     * @param posicionActual Posicion Actual a establecer
     */
    public void setPosicionActual(BigInteger posicionActual) {
        this.posicionActual = posicionActual;
    }

    /**
     * Obtiene la Valua Mercado
     *
     * @return Valua Mercado
     */
    public BigDecimal getValuaMercado() {
        return valuaMercado;
    }

    /**
     * Establece la Valua Mercado
     * 
     * @param valuaMercado Valua Mercado a establecer
     */
    public void setValuaMercado(BigDecimal valuaMercado) {
        this.valuaMercado = valuaMercado;
    }

    /**
     * Obtiene la Valuacion Nominal
     * 
     * @return Valuacion Nominal
     */
    public BigDecimal getValuacionNominal() {
        return valuacionNominal;
    }

    /**
     * Establece la Valuacion Nominal 
     * 
     * @param valuacionNominal Valuacion Nominal a establecer
     */
    public void setValuacionNominal(BigDecimal valuacionNominal) {
        this.valuacionNominal = valuacionNominal;
    } 

}
