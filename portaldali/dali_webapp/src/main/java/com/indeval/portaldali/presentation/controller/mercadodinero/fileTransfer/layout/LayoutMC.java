package com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer.layout;

public enum LayoutMC {
    FECHA_LIQ(0, "Fecha liquidacion"),
    CVE_TRAS(1, "Clave Traspasante"),
    CTA_TRAS(2, " Cuenta Traspasante"),
    CVE_RECEP(3, "Clave Receptor"),
    CTA_RECEP(4, "Cuenta Receptor"),
    TV(5, "TV"),
    EMISORA(6, "Emisora"),
    SERIE(7, "Serie"),
    CUPON(8, "Cupon"),
    TIPO_OPER(9, "Tpo.Oper."),
    PLAZO(10, "Plazo"),
    CANTIDAD(11, "Cantidad"),
    IMPORTE(12, "Importe"),
    FECHA_HORA_CIERRE_OPER(13, "Fecha Concer."),
    //SE AGREGAN 2 CAMPOS DIVISA Y BOVEDA
    DIVISA(14, "Divisa"),
    BOVEDA_EFECTIVO(15, "Boveda Efectivo");
    
    private Integer posicion;
    private String nombre;
    
    public Integer getPosicion() {
        return posicion;
    }
    public String getNombre(){
        return nombre;
    }

    private LayoutMC ( Integer posicion, String nombre ){
        this.posicion = posicion;
        this.nombre = nombre;
    }
   
//    FOLIO_DESC(0, "Fol.Desc."),
//    CTA_TRASP(1, "Cta.Trasp."),
//    ID_RECEP(2, "Id.Recep."), 
//    FOLIO_RECEP(3, "Fol.Recep."), 
//    CTA_RECEP(4, "Cta.Recep."), 
//    TV(5, "TV"), 
//    EMISORA(6, "Emisora"), 
//    SERIE(7, "Serie"), 
//    CUPON(8, "Cupon"), 
//    CANTIDAD( 9, "Cantidad"), 
//    FECHA(10, "Fecha");
//
//    private Integer posicion;
//
//    private String nombre;
//
//    public Integer getPosicion() {
//        return posicion;
//    }
//
//    public String getNombre() {
//        return nombre;
//    }
//
//    private LayoutMC(Integer posicion, String nombre) {
//        this.posicion = posicion;
//        this.nombre = nombre;
//    }
}
