/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.vo;

import java.io.Serializable;

/**
 * @author @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class SaldosEfectivoPK implements Serializable {

    private static final long serialVersionUID = 1L;

    private String salIdInst;

    private String salFolioInst;

    private String salTpoCuenta;

    /**
     * @return the salIdInst
     */
    public String getSalIdInst() {
        return salIdInst;
    }

    /**
     * @return the salFolioInst
     */
    public String getSalFolioInst() {
        return salFolioInst;
    }

    /**
     * @return the salTpoCuenta
     */
    public String getSalTpoCuenta() {
        return salTpoCuenta;
    }

    public void setSalFolioInst(String salFolioInst) {
        this.salFolioInst = salFolioInst;
    }

    public void setSalIdInst(String salIdInst) {
        this.salIdInst = salIdInst;
    }

    public void setSalTpoCuenta(String salTpoCuenta) {
        this.salTpoCuenta = salTpoCuenta;
    }

}
