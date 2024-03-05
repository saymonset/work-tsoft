package com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer.layout;

public enum LayoutMD {
    // se modifica
	//FOLIO_DESC(0, "Fol.Desc."),
    ID_TRAS(0, "Id.Trasp."),
    FOLIO_TRAS(1, "Fol.Trasp."),
    CTA_TRASP(2, "Cta.Trasp."),
    ID_RECEP(3, "Id.Recep."),
    FOLIO_RECEP(4, "Fol.Recep."),
    CTA_RECEP(5, "Cta.Recep."),
    TV(6, "TV"),
    EMISORA(7, "Emisora"),
    SERIE(8, "Serie"),
    CUPON(9, "Cupon"),
    CANTIDAD(10,"Cantidad"),
    TIPO_OPER(11, "Tpo.Oper."),
    DIAS_PLAZO(12, "Dias Plazo"),
    FECHA(13, "Fecha"),
    //se modifica
    //FECHA_REP(15 ,"Fecha Rep."),
    PRECIO(14, "Precio"),
    TASA(15, "Tasa"),
    PLAZO(16, "Plazo"),
    FECHA_HORA_CIERRE_OPER(17, "Fecha Concer."),
    DIVISA(18, "Divisa"),
    BOVEDA_EFECTIVO(19,"Boveda Efectivo");
    //SE MODIFICA
    //ID_TASA_REF(20, "Id.Tasa Ref."),
    //BL(21, "BL"); 
    
    private Integer posicion;
    private String nombre;
    
    public Integer getPosicion() {
        return posicion;
    }
    public String getNombre(){
        return nombre;
    }

    private LayoutMD ( Integer posicion, String nombre ){
        this.posicion = posicion;
        this.nombre = nombre;
    }
}