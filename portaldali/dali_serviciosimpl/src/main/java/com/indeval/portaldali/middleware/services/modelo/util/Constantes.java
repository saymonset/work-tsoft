/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.modelo.util;

/**
 * Clase que contiene constantes utilizadas en el modelo.
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface Constantes extends com.indeval.portaldali.middleware.services.util.util.Constantes {

    /** Define Origen 03 */
    static final String BMV = "03";
    
    /** Define dine */
    static final String DINE= "DINE";
    
    /** Indica que la consulta  utiliza el filtro Institucion */
    static final String BY_INSTITUCION = "BY_INSTITUCION";
    
    /** Indica que la consulta  utiliza el filtro mercado */
    static final String BY_MERCADO = "BY_MERCADO";
    
    /** Indica que la consulta  utiliza el filtro isin */
    static final String BY_ISIN = "BY_ISIN";
    
    /**
     * Indica que el mercado del instrumento es Papel Bancario, usado en
     * getListaEmisiones()
     */
    public static final String PAPEL_BANCARIO = "PB";
    
    /** Define en firme */
    static final String EN_FIRME = "F";
    
    /**
     * Indica que el mercado del instrumento es Papel de Dinero, usado en
     * getListaEmisiones() Tambien se utiliza para indicar que el archivo a leer
     * en el File Transfer es de "dinero"
     */
    public static final String PAPELES_DINERO = "PD";

    /**
     * Indica que el mercado del instrumento es Papel gubernamental, usado en
     * getListaEmisiones() Tambien se utiliza para indicar que el archivo a leer
     * en el File Transfer es de "guber"
     */
    public static final String PAPEL_GUBERNAMENTAL = "PG";
    
    /** Indica el tipo de papel que corresponde en mercado de capitales */
    public static final String MERCADO_CAPITALES = "MC";
    
    /** Define GE_ACTUAL */
    static final String GE_ACTUAL_S = "S";
    
    /** Escala para los big decimal. */
    static final int ESCALA_BIGDECIMAL = 2;
    
    /** Escala para los big decimal. */
    static final int REGISTROS_PREDETERMINADOS_X_PAGINA = 30;

    /** Nafinsa */
    static final String NAFINSA = "02022";

    /** Alta1 de Nafinsa */
    static final String ALTA1NAFINSA = "ADCP";

    /** Alta2 de Nafinsa */
    static final String ALTA2NAFINSA = "CPOS";

    /** Alta3 de Nafinsa */
    static final String ALTA3NAFINSA = "VI%";

    /** Banamex */
    static final String BANAMEX = "02061";

    /** Alta1 de Banamex */
    static final String ALTA1BANAMEX = "BADC";

    /** Alta2 de Banamex */
    static final String ALTA2BANAMEX = "BCPO";

    /** Inbursa */
    static final String INBURSA = "02021";

    /** Alta1 de Inbursa */
    static final String ALTA1INBURSA = "IADC";

    /** Alta2 de Inbursa */
    static final String ALTA2INBURSA = "ICPO";
    
    /** Define TIPO_OPERACION_T (T) */
    static String TIPO_OPERACION_T = "T";

    /** Define TIPO_OPERACION_V (V) */
    static String TIPO_OPERACION_V = "V";

    /** Define TIPO_OPERACION_D (D) */
    static String TIPO_OPERACION_D = "D";

    /** Define TIPO_OPERACION_R (R) */
    static String TIPO_OPERACION_R = "R";

    /** Define TIPO_OPERACION_S (S) */
    static String TIPO_OPERACION_S = "S";

    /** Define TIPO_OPERACION_A (A) */
    static String TIPO_OPERACION_A = "A";

    /** Define TIPO_OPERACION_O (O) */
    static String TIPO_OPERACION_O = "O";
    
}
