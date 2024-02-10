/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.vo;


import java.io.Serializable;
import java.math.BigDecimal;

public class PorcentajeCuentaVO implements Serializable {
    /**
     * Constante de serializacion 
     */
    private static final long serialVersionUID = 1L;
    
    /**Nombre de la Cuenta */
    private String cuenta;
    
    /**Porcentaje */
    private BigDecimal porcentaje;

    /**
     * Obtiene la Cuenta 
     * 
     * @return Cuenta
     */
    public String getCuenta() {
        return cuenta;
    }

    /**
     * Establece la Cuenta 
     * 
     * @param cuenta Cuenta a establecer
     */
    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    /**
     * Obtiene el Porcentaje de la Cuenta
     * 
     * @return Porcentaje de la Cuenta
     */
    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    /**
     * Establece el Porcentaje de la Cuenta 
     * 
     * @param porcentaje Porcentaje de la Cuenta a establecer
     */
    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }
    
}
