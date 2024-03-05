/*
 *Copyright (c) 2010 Bursatec. All Rights Reserved 
 */
package com.indeval.portaldali.presentation.controller.tesoreria.retiros.fileTransfer;

/**
 * @author Maria C. Buendia
 * @version 1.0
 */
public enum LayoutRB {
    CLAVE_TRASP (0, "Cve. Trasp."),
    ID_CTA_BENEF(1, "Id. Cta. Benf."),
    IMPORTE     (2, "Importe"),
    REFERENCIA  (3, "Referencia"),
    CONCEPTO    (4, "Concepto");
    
    private Integer posicion;
    private String nombre;
    
    public Integer getPosicion() {
        return posicion;
    }
    public String getNombre(){
        return nombre;
    }

    private LayoutRB ( Integer posicion, String nombre ){
        this.posicion = posicion;
        this.nombre = nombre;
    }
}
