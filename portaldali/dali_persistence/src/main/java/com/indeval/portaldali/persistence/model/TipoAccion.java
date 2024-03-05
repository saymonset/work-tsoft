/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

/**
 * Cata&acute;logo de tipos de acciones.
 *
 * C_TIPO_ACCION
 *
 * @author rchavez
 * @version 1.0
 */
public enum TipoAccion {
    /**
     * Define el tipo de accio&acute;n como cargo a cuenta de pasivo.
     */
    CARGO_PASIVO("CARGO_PASIVO", 0),
    /**
     * Define el tipo de accio&acute;n como cargo a cuenta de pasivo.
     */
    ABONO_PASIVO("ABONO_PASIVO", 1),
    /**
     * Define el tipo de accio&acute;n como cargo a cuenta de pasivo.
     */
    CARGO_ACTIVO("CARGO_ACTIVO", 2),
    /**
     * Define el tipo de accio&acute;n como cargo a cuenta de pasivo.
     */
    ABONO_ACTIVO("ABONO_ACTIVO", 3),
    /**
     * Define el tipo de accio&acute;n como abono al fideicomiso.
     */
    ABONO_FIDEICOMISO("ABONO_FIDEICOMISO", 4),
    /**
     * Define el tipo de accio&acute;n como cargo al fideicomiso.
     */
    CARGO_FIDEICOMISO("CARGO_FIDEICOMISO", 5);

	/**
     * 0 - Cargo Cuenta de Pasivo.
     */
    public static final int CARGO_PASIVO_VALUE = 0;
    /**
     * 1 - Abono Cuenta de Pasivo.
     */
    public static final int ABONO_PASIVO_VALUE = 1;
    /**
     * 2 - Cargo Cuenta de Activo.
     */
    public static final int  CARGO_ACTIVO_VALUE = 2;
    /**
     * 3 - Abono Cuenta de Activo.
     */
    public static final int ABONO_ACTIVO_VALUE = 3;
    /**
     * 4 - Abono al Fideicomiso.
     */
    public static final int  ABONO_FIDEICOMISO_VALUE = 4;
    /**
     * 5 - Cargo al Fideicomiso.
     */
    public static final int CARGO_FIDEICOMISO_VALUE = 5;

    private int value;
    private String name;
    
    /**
     * Default constructor
     * @param name
     * @param value
     */
    private TipoAccion(String name, int value) {
        this.name = name;
        this.value = value;
    }

    /**
     * @param i
     * @return TipoAccion
     * @throws RuntimeException
     */
    public static TipoAccion fromInt(int i) {
    	switch(i) {
    		case CARGO_PASIVO_VALUE:
    			return CARGO_PASIVO;
    		case ABONO_PASIVO_VALUE:
    			return ABONO_PASIVO;
    		case CARGO_ACTIVO_VALUE:
    			return CARGO_ACTIVO;
    		case ABONO_ACTIVO_VALUE:
    			return ABONO_ACTIVO;
    		case ABONO_FIDEICOMISO_VALUE:
    			return ABONO_FIDEICOMISO;
    		case CARGO_FIDEICOMISO_VALUE:
    			return CARGO_FIDEICOMISO;
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
