/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

/**
 * Este cata&acute;logo contiene los distintos estados que puede tomar una instrucción
 * 
 * @author Rogelio Cha&acute;vez
 * @version 1.0
 */
public enum EstadoInstruccion {

    /**
     * Objeto EstadoInstruccion al que se le asigna el valor CON_MATCH_VALUE.
     */
    CON_MATCH("CON_MATCH", 0),

    /**
     * Objeto EstadoInstruccion al que se le asigna el valor SIN_MATCH_VALUE.
     */
    SIN_MATCH("SIN_MATCH", 1),
    /**
     * Objeto EstadoInstruccion al que se le asigna el valor CANCELADA_VALUE.
     */
    CANCELADA("CANCELADA", 2),
    /**
     * Objeto EstadoInstruccion al que se le asigna el valor PENDIENTE_VALUE.
     */
    PENDIENTE("PENDIENTE", 3),
    /**
     * Objeto EstadoInstruccion al que se le asigna el valor DEPENDIENTE_VALUE.
     */
    DEPENDIENTE("DEPENDIENTE", 4),
    /**
     * Objeto EstadoInstruccion al que se le asigna el valor NO_FONDEADA_VALUE.
     */
    NO_FONDEADA("NO_FONDEADA", 5),
    /**
     * Objeto EstadoInstruccion al que se le asigna el valor LIQUIDADA_VALUE.
     */
    LIQUIDADA("LIQUIDADA", 6),
    /**
     * Objeto EstadoInstruccion al que se le asigna el valor PRELIQUIDANDO_VALUE.
     */
    PRELIQUIDANDO("PRELIQUIDANDO", 7),
    /**
     * Objeto EstadoInstruccion al que se le asigna el valor EXPIRADA_VALUE.
     */
    EXPIRADA("EXPIRADA", 8),
    /**
     * Objeto EstadoInstruccion al que se le asigna el valor LIQUIDADA_CON_BCO_TRAB_VALUE.
     */
    LIQUIDADA_CON_BCO_TRAB("LIQUIDADA_CON_BCO_TRAB", 9),
    
    /**
     * Objeto EstadoInstruccion al que se le asigna el valor CANCELADA_CON_ERROR_VALUE.
     */
    CANCELADA_CON_ERROR("CANCELADA_CON_ERROR", 10),
    
    /**
     * Objeto EstadoInstruccion al que se le asigna el valor EXPIRADA_CON_ERROR_VALUE.
     */
    EXPIRADA_CON_ERROR("EXPIRADA_CON_ERROR", 11),
    
    /**
     * Objeto EstadoInstruccion al que se le asigna el valor PRELIQUIDABLE_VALUE.
     */
    PRELIQUIDABLE("PRELIQUIDABLE", 12),
    /**
     * Objeto EstadoInstruccion al que se le asigna el valor REGISTRADA_VALUE.
     */
    REGISTRADA("REGISTRADA", 19),
    /**
     * Objeto EstadoInstruccion al que se le asigna el valor AUTORIZADA_VALUE.
     */
    AUTORIZADA("AUTORIZADA", 23),
    
    /**
     * Objeto EstadoInstruccion al que se le asigna el valor LIBERADA_VALUE.
     */
    LIBERADA("LIBERADA", 24),
	
    /**
     * Objeto EstadoInstruccion al que se le asigna el valor APROBADA_VALUE.
     */
    APROBADA("APROBADA", 26);
    
    
    /**
     * 0 - Estado con match.
     */
    public static final int CON_MATCH_VALUE = 0;
    /**
     * 1 - Estado sin match.
     */
    public static final int SIN_MATCH_VALUE = 1;
    /**
     * 2 - Estado cancelada.
     */
    public static final int CANCELADA_VALUE = 2;
    /**
     * 3 - Estado pendiente.
     */
    public static final int PENDIENTE_VALUE = 3;
    /**
     * 4 - Estado dependiente.
     */
    public static final int DEPENDIENTE_VALUE = 4;
    /**
     * 5 - Estado no fondeada.
     */
    public static final int NO_FONDEADA_VALUE = 5;
    /**
     * 6 - Estado liquidada.
     */
    public static final int LIQUIDADA_VALUE = 6;
    /**
     * 7 - Estado preliquidando.
     */
    public static final int PRELIQUIDANDO_VALUE = 7;
    /**
     * 8 - Estado expirada.
     */
    public static final int EXPIRADA_VALUE = 8;
    /**
     * 9 - Estado liquidada con banco de trabajo.
     */
    public static final int LIQUIDADA_CON_BCO_TRAB_VALUE = 9;
    /**
     *10 - Estado cancelada con error.
     */
    public static final int CANCELADA_CON_ERROR_VALUE = 10;
    /**
     *11 - Estado expirada con error.
     */
    public static final int EXPIRADA_CON_ERROR_VALUE = 11;
    /**
     *12 - Estado preliquidable.
     */
    public static final int PRELIQUIDABLE_VALUE = 12;
    /**
     *19 - Estado registrada.
     */
    public static final int REGISTRADA_VALUE = 19;
    /**
     *24 - Estado autorizada.
     */
    public static final int AUTORIZADA_VALUE = 23;
    /**
     *24 - Estado liberada.
     */
    public static final int LIBERADA_VALUE = 24;
    /**
     *26 - Estado aprobada.
     */
    public static final int APROBADA_VALUE = 26;

    private int value;
    private String name;
    
    /**
     * Default constructor
     * @param name
     * @param value
     */
    private EstadoInstruccion(String name, int value) {
        this.name = name;
        this.value = value;
    }

    /**
     * 
     * @param i
     * @return EstadoInstruccion
     * @throws RuntimeException 
     */
    public static EstadoInstruccion fromInt(int i) {
    	switch(i) {
    		case CON_MATCH_VALUE:
    			return CON_MATCH;
    		case SIN_MATCH_VALUE:
    			return SIN_MATCH;
    		case CANCELADA_VALUE:
    			return CANCELADA;
    		case PENDIENTE_VALUE:
    			return PENDIENTE;
    		case DEPENDIENTE_VALUE:
    			return DEPENDIENTE;
    		case NO_FONDEADA_VALUE:
    			return NO_FONDEADA;
    		case LIQUIDADA_VALUE:
    			return LIQUIDADA;
    		case PRELIQUIDANDO_VALUE:
    			return PRELIQUIDANDO;
    		case EXPIRADA_VALUE:
    			return EXPIRADA;
    		case LIQUIDADA_CON_BCO_TRAB_VALUE:
    			return LIQUIDADA_CON_BCO_TRAB;
    		case CANCELADA_CON_ERROR_VALUE:
    			return CANCELADA_CON_ERROR;
    		case EXPIRADA_CON_ERROR_VALUE:
    			return EXPIRADA_CON_ERROR;
    		case PRELIQUIDABLE_VALUE:
    			return PRELIQUIDABLE;
    		case APROBADA_VALUE:
    			return APROBADA;    			
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