/**
 * Bursatec - Portal DALI
 * Copyrigth (c) 2014 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.dto.filetransfer;

/**
 * Enumeración con los campos que conforman el layout de miscelanea fiscal para capitales.
 * 
 * @author Pablo Balderas
 */
public enum MiscelaneaFiscalDinero {

    FECHA_REGISTRO(1),
    CVE_TRAS(2),
    CTA_TRAS(3),
    CVE_RECEP(4),
    CTA_RECEP(5),
    TV(6),
    EMISORA(7),
    SERIE(8),
    CUPON(9),
    ISIN(10),
    BOVEDA(11),
    CANTIDAD_OPERADA(12),
    DIVISA(13),
    FECHA_ADQ(14),
    PRECIO_ADQ(15),
    CLIENTE(16),
    RFC_CURP(17),
    EXTRANJERO(18),
    FECHA_HORA_CONCERTACION(19);
    
    private Integer posicion;

    /**
     * Constructor de la clase
     * @param posicion Posición del campo
     */
    private MiscelaneaFiscalDinero(Integer posicion) {
    	this.posicion = posicion;
    }
    
    /**
     * Obtiene el campo posición
     * @return Valor del campo posición
     */
    public Integer getPosicion() {
        return posicion;
    }


	
}
