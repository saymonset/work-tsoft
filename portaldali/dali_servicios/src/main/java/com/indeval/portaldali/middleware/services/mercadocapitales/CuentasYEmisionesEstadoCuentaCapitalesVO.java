/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class CuentasYEmisionesEstadoCuentaCapitalesVO extends AbstractBaseDTO {

    /** Constante de serializaci&oacute;n */
    private static final long serialVersionUID = 1L;

    /** */
    String[] arregloCuentas;

    /** */
    EmisionVO[] arregloEmisionesCuenta;

    /**
     * @return the arregloCuentas
     */
    public String[] getArregloCuentas() {
        return arregloCuentas;
    }

    /**
     * @param arregloCuentas
     *            the arregloCuentas to set
     */
    public void setArregloCuentas(String[] arregloCuentas) {
        this.arregloCuentas = arregloCuentas;
    }

    /**
     * @return the arregloEmisionesCuenta
     */
    public EmisionVO[] getArregloEmisionesCuenta() {
        return arregloEmisionesCuenta;
    }

    /**
     * @param arregloEmisionesCuenta
     *            the arregloEmisionesCuenta to set
     */
    public void setArregloEmisionesCuenta(EmisionVO[] arregloEmisionesCuenta) {
        this.arregloEmisionesCuenta = arregloEmisionesCuenta;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {
    }

}
