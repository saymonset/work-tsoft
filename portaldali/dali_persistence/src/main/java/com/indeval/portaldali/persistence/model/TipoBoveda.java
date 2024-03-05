/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
     */
package com.indeval.portaldali.persistence.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Este cata&acute;logo describe los tipos de bo&acute;veda existentes.
 *
 * C_TIPO_BOVEDA
 *
 * @author rchavez
 * @version 1.0
 */
public enum TipoBoveda {
    
    /**
     * Define el tipo de bo&acute;veda nacional de efectivo.
     */
    NACIONAL_EFECTIVO("NACIONAL_EFECTIVO", 0),
    /**
     * Define el tipo de bo&acute;veda nacional de valores.
     */
    NACIONAL_VALORES("NACIONAL_VALORES", 1),
    /**
     * Define el tipo de bo&acute;veda internacional de efectivo.
     */
    INTERNACIONAL_EFECTIVO("INTERNACIONAL_EFECTIVO", 2),
    /**
     * Define el tipo de bo&acute;veda internacional de valores.
     */
    INTERNACIONAL_VALORES("INTERNACIONAL_VALORES", 3);

    /**
     * 0 - Bo&acute;veda nacional de efectivo.
     */
    public static final int NACIONAL_EFECTIVO_VALUE = 0;
    
    /**
     * 1 - Bo&acute;veda nacional de valores.
     */
    public static final int NACIONAL_VALORES_VALUE = 1;
    
    /**
     * 2 - Bo&acute;veda internacional de efectivo.
     */
    public static final int INTERNACIONAL_EFECTIVO_VALUE = 2;
    
    /**
     * 3 - Bo&acute;veda internacional de valores.
     */
    public static final int INTERNACIONAL_VALORES_VALUE = 3;

    private int value;
    
    private String name;

    private BigInteger idTipoBoveda; // PK

    private String claveTipoBoveda;

    private String ubicacion;

    private String descripcion;

    @Id
    @Column(name ="id_tipo_boveda", unique = true, nullable = false)
   public BigInteger getIdTipoBoveda() {
       return idTipoBoveda;
   }

    @Column(name ="clave_tipo_boveda", unique = true, nullable = false)
   public String getClaveTipoBoveda() {
       return claveTipoBoveda;
   }

    @Column(name ="ubicacion", unique = true, nullable = false)
   public String getUbicacion() {
       return ubicacion;
   }

    @Column(name ="descripcion", unique = false, nullable = true)
   public String getDescripcion() {
       return descripcion;
   }
    
    /**
     * Default constructor
     * @param name
     * @param value
     */
    private TipoBoveda(String name, int value) {
        this.name = name;
        this.value = value;
    }

    /**
     * @param i
     * @return TipoAccion
     * @throws RuntimeException
     */
    public static TipoBoveda fromInt(int i) {
    	switch(i) {
    		case NACIONAL_EFECTIVO_VALUE:
    			return NACIONAL_EFECTIVO;
    		case NACIONAL_VALORES_VALUE:
    			return NACIONAL_VALORES;
    		case INTERNACIONAL_EFECTIVO_VALUE:
    			return INTERNACIONAL_EFECTIVO;
    		case INTERNACIONAL_VALORES_VALUE:
    			return INTERNACIONAL_VALORES;
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

    /**
     * @param idTipoBoveda
     */
    public void setIdTipoBoveda(BigInteger idTipoBoveda) {
        this.idTipoBoveda = idTipoBoveda;
    }

    /**
     * @param claveTipoBoveda
     */
    public void setClaveTipoBoveda(String claveTipoBoveda) {
        this.claveTipoBoveda = claveTipoBoveda;
    }

    /**
     * @param ubicacion
     */
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    /**
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
