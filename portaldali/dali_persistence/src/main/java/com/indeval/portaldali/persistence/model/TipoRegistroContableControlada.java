/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

/**
 * Este cata&acute;logo describe los tipos de registros contables de cuentas controladas.
 *
 * C_TIPO_REG_CONTABLE_CONTROLADA
 *
 * @author rchavez
 * @version 1.0
 */
public enum TipoRegistroContableControlada {
	/**
     * Define los registros contables de retiros de efectivo.
     */
	RETIROS_EFECTIVO("RETIROS_EFECTIVO", 0),
    /**
     * Define los registros contables de depositos de efectivo.
     */
	DEPOSITOS_EFECTIVO("DEPOSITOS_EFECTIVO", 1),
    /**
     * Define los registros contables de cargos y abonos de efectivo.
     */
	CARGOS_ABONOS_EFECTIVO("CARGOS_ABONOS_EFECTIVO", 2),
    /**
     * Define los registros contables de operaciones de valores.
     */
	VALORES("VALORES", 3);
	/**
     * 0 - Retiros de efectivo.
     */
    public static final int RETIROS_EFECTIVO_VALUE = 0;
    /**
     * 1 - Depositos de efectivo.
     */
    public static final int DEPOSITOS_EFECTIVO_VALUE = 1;
    /**
     * 2 - Cargos y abonos de efectivo.
     */
    public static final int CARGOS_ABONOS_EFECTIVO_VALUE = 2;
    /**
     * 3 - Operaciones de valores.
     */
    public static final int VALORES_VALUE = 3;

    private int value;
    private String name;
    
    /**
     * Default constructor
     * @param name
     * @param value
     */
    private TipoRegistroContableControlada(String name, int value) {
    	 this.name = name;
         this.value = value;
	}

    /**
     * @param i
     * @return TipoAccion
     * @throws RuntimeException
     */
    public static TipoRegistroContableControlada fromInt(int i) {
    	switch(i) {
    		case RETIROS_EFECTIVO_VALUE:
    			return RETIROS_EFECTIVO;
    		case DEPOSITOS_EFECTIVO_VALUE:
    			return DEPOSITOS_EFECTIVO;
    		case CARGOS_ABONOS_EFECTIVO_VALUE:
    			return CARGOS_ABONOS_EFECTIVO;
    		case VALORES_VALUE:
    			return VALORES;
    		default:
    			return null;
    	}
    }

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
}
