/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;;

public class PorcentajeAgenteVO implements Serializable {
    
    /**
     * Constante de serializacion 
     */
    private static final long serialVersionUID = 1L;

    /**Datos de Agente */
    private AgenteVO agente;
    
    /**Porcentaje */
    private BigDecimal porcentaje;

    /**
     * Obtiene los Datos del Agente
     * 
     * @return Datos del Agente
     */
    public AgenteVO getAgente() {
        return agente;
    }

    /**
     * Establece los Datos del Agente
     * 
     * @param agente Datos del Agente a establecer
     */
    public void setAgente(AgenteVO agente) {
        this.agente = agente;
    }

    /**
     * Obtiene el Porcentaje del Agente 
     * 
     * @return Porcentaje del Agente
     */
    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    /**
     * Establece el Porcentaje del Agente
     * 
     * @param porcentaje Porcentaje del Agente a establecer
     */
    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

}
