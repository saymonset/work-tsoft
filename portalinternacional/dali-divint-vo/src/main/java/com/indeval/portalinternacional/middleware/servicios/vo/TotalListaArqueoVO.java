package com.indeval.portalinternacional.middleware.servicios.vo;
/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

public class TotalListaArqueoVO implements Serializable {

    /**
     * Constante de serializacion 
     */
    private static final long serialVersionUID = 1L;
    
    /**Total de Agentes */
    private BigInteger totalNumAgentes;
    
    /**Total de la Posicion Actual */ 
    private BigInteger totalPosicionActual;
    
    /**Total de Valua Mercado */
    private BigDecimal totalValuaMercado;
    
    /**Total de Valuacion Nominal */
    private BigDecimal totalValuacionNominal;

    /**
     * Obtiene el Total de Agentes
     * 
     * @return Total de Agentes
     */
    public BigInteger getTotalNumAgentes() {
        return totalNumAgentes;
    }

    /**
     * Establece el Total de Agentes
     * 
     * @param totalNumAgentes Total de Agentes a establecer
     */
    public void setTotalNumAgentes(BigInteger totalNumAgentes) {
        this.totalNumAgentes = totalNumAgentes;
    }

    /**
     * Obtiene el Total de la Posicion Actual
     * 
     * @return Total de la Posicion Actual
     */
    public BigInteger getTotalPosicionActual() {
        return totalPosicionActual;
    }

    /**
     * Establece el Total de la Posicion Actual
     * 
     * @param totalPosicionActual Total de la Posicion Actual a establecer
     */
    public void setTotalPosicionActual(BigInteger totalPosicionActual) {
        this.totalPosicionActual = totalPosicionActual;
    }

    /**
     * Obtiene el Total Valua Mercado
     * 
     * @return Total Valua Mercado
     */
    public BigDecimal getTotalValuaMercado() {
        return totalValuaMercado;
    }

    /**
     * Establece el Total Valua Mercado
     * 
     * @param totalValuaMercado Total Valua Mercado a establecer
     */
    public void setTotalValuaMercado(BigDecimal totalValuaMercado) {
        this.totalValuaMercado = totalValuaMercado;
    }

    /**
     * Obtiene el Total de la Valuacion Nominal
     * 
     * @return Total de la Valuacion Nominal
     */
    public BigDecimal getTotalValuacionNominal() {
        return totalValuacionNominal;
    }

    /**
     * Establece el Total de la Valuacion Nominal
     * 
     * @param totalValuacionNominal Total de la Valuacion Nominal a establecer
     */
    public void setTotalValuacionNominal(BigDecimal totalValuacionNominal) {
        this.totalValuacionNominal = totalValuacionNominal;
    }
}
