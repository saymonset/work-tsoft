/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

/**
 * Contiene los distintos tipos de pagos provenientes de la Interfaz de Efectivo.
 * 
 * @author apalacios
 * @version 1.0
 */
public enum TipoPagoEnum {

	/**
	 * Tipo pago de tercero a tecero.
	 */
    TERCERO_TERCERO("TERCERO_TERCERO", 0),
    
    /**
     * Tipo pago de participante a tercero.
     */
    PARTICIPANTE_TERCERO("PARTICIPANTE_TERCERO", 1),
    
    /**
     * Tipo pago de traspaso de fondos.
     */
    TRASPASOS_SIAC("TRASPASOS_SIAC_VALUE", 2);
    
    /**
     * Valor de tercero a tercero.
     */
    public static final int TERCERO_TERCERO_VALUE = 0;
    
    /**
     * Valor de participante a tercero. 
     */
    public static final int PARTICIPANTE_TERCERO_VALUE = 1;
    
    /**
     * Valor de traspasos del SIAC.
     */
    public static final int TRASPASOS_SIAC_VALUE = 2;

    /**
     * Valor del Enum
     */
	private int value;
	
	/**
	 * Nombre del Enum
	 */
    private String name;
    
    /**
     * Regresa objeto TipoPagoEnum a trave&acute;s del valor.
     * 
     * @param i
     * @return EstadoInstruccionEnum
     * @throws RuntimeException
     */
    public static TipoPagoEnum fromInt(int i) {
    	switch(i) {
    		case TERCERO_TERCERO_VALUE:
    			return TERCERO_TERCERO;
    		case PARTICIPANTE_TERCERO_VALUE:
    			return PARTICIPANTE_TERCERO;
    		case TRASPASOS_SIAC_VALUE:
    			return TRASPASOS_SIAC;
    		default:
    			return null;
    	}
    }    
    
    /**
     * Asigna nombre y valor.
     *  
     * @param name
     * @param value
     */
    private TipoPagoEnum(String name, int value) {
    	this.name = name;
    	this.value = value;
    }

    /**
     * @return value
     */
	public int getValue() {
		return value;
	}

	/**
	 * @param value
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}