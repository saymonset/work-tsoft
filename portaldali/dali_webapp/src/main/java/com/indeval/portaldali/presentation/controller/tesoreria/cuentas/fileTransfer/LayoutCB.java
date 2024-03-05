/*
 *Copyright (c) 2010 Bursatec. All Rights Reserved 
 */
package com.indeval.portaldali.presentation.controller.tesoreria.cuentas.fileTransfer;

/**
 * @author Maria C. Buendia
 * @version 1.0
 */
public enum LayoutCB {
    CLAVE_TRASP (0, "Cve. Trasp."),
	CLAVE_RECEP (1, "Cve. Recep."),
	CUENTA_BENEF(2, "Cta. Benef."),
	NOMBRE_BENEF(3, "Nom. Benef."),
	MAX_MENSUAL (4, "Max. Mensual"),
	MAX_DIARIO  (5, "Max. Diario"),
	MAX_XTRANSC (6, "Max. Transc."),
	MOVS_MENSUAL(7, "Movs. Mensual");
    
    private Integer posicion;
    private String nombre;
    
    public Integer getPosicion() {
        return posicion;
    }
    public String getNombre(){
        return nombre;
    }

    private LayoutCB ( Integer posicion, String nombre ){
        this.posicion = posicion;
        this.nombre = nombre;
    }
}
