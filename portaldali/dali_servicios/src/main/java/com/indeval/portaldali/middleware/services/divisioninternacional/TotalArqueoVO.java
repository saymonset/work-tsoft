/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.divisioninternacional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class TotalArqueoVO implements Serializable {
    
    /**
     * Constante de serializacion 
     */
    private static final long serialVersionUID = 1L;
    /**
     * Total de Emisiones 
     */
    private BigInteger totalEmisiones;
    /**
     * Saldo Total 
     */
    private BigDecimal saldoTotal;
    /**
     * Valua Mercado 
     */
    private BigDecimal totalValuaMercado;
    /**
     * Valuacion Nominal 
     */
    private BigDecimal totalValuacionNominal;
    
    /**
     * Obtiene el Saldo Total
     *  
     * @return Salto Total
     */
    public BigDecimal getSaldoTotal() {
        return saldoTotal;
    }

    /**
     * Establece el Saldo Total
     * 
     * @param saldoTotal Saldo Total a establecer
     */
    public void setSaldoTotal(BigDecimal saldoTotal) {
        this.saldoTotal = saldoTotal;
    }

    /**
     * Obtiene el Total de Emisiones
     * 
     * @return Total de Emisiones
     */
    public BigInteger getTotalEmisiones() {
        return totalEmisiones;
    }

    /**
     * Establece el Total de Emisiones
     * 
     * @param totalEmisiones Total de Emisiones a establecer
     */
    public void setTotalEmisiones(BigInteger totalEmisiones) {
        this.totalEmisiones = totalEmisiones;
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

    /**
     * Obtiene el Total de la Valuacion de Mercado
     * @return Total de la Valuacion de Mercado
     */
    public BigDecimal getTotalValuaMercado() {
        return totalValuaMercado;
    }

    /**
     * Establece el Total de la Valuacion de Mercado
     * @param totalValuaMercado Total de la Valuacion de Mercado a establecer
     */
    public void setTotalValuaMercado(BigDecimal totalValuaMercado) {
        this.totalValuaMercado = totalValuaMercado;
    }
}
