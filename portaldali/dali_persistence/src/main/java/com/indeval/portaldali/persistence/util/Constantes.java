/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.util;

import java.math.BigInteger;

/**
 * Interface que define las constantes utilizadas en la capa de persistencia de Dali.
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public interface Constantes {

    /** Indica cupon cortado EN_FIRME */
    static final String CUPON_CORTADO_EN_FIRME = "F";
    
    /** Indica mercado capitales */
    static final String MERCADO_CAPITALES = "MC";
    
    /** Constante para indicar los papeles de mercado de dinero */
    static final String PAPELES_DINERO = "PD";

    /** Indica papeles bancario */
    static final String PAPEL_BANCARIO = "PB";

    /** Indica papel gubernamental */
    static final String PAPEL_GUBERNAMENTAL = "PG";

    /** Indica cuenta propia */
    static final String CUENTA_PROPIA = "PROP_NOM";

    /** Indica cuenta propia */
    static final String CUENTA_GARANTIA_VALPRE_E = "G_VALPRE_E";  //Administracion de garantias de ValpreE

    /** Clave FUNCION_AM */
    static final String FUNCION_AM = "AM"; //Administracion de garantias de ValpreE

    /** Clave FUNCION_G */
    static final String FUNCION_G = "G"; //Administracion de garantias de ValpreE
    
    /** Indica Todos*/
    static final String TODOS = "TODOS";
    
    /** Indica estatus vigente */
    static final BigInteger VIGENTE = BigInteger.valueOf(1);
    
    /** Indica que la consulta  utiliza el filtro Institucion */
    static final String BY_INSTITUCION = "BY_INSTITUCION";
    
    /** Indica que la consulta  utiliza el filtro mercado */
    static final String BY_MERCADO = "BY_MERCADO";
    
    /** Indica que la consulta  utiliza el filtro isin */
    static final String BY_ISIN = "BY_ISIN";
    
    /** ID del tipo de operacion */
    static final String ID_TIPO_OPERACION_BLOQUEADA = "10";
    
    /** ID del tipo de operacion */
    static final String ID_TIPO_OPERACION_DESBLOQUEADA = "12";
    
    /**Constante para el bloqueo de titulos*/
	static final String BLOQUEO_TITULOS = "BLOQ T";
	
	/**Constante para el desbloqueo de titulos*/
	static final String DESBLOQUEO_TITULOS = "DESB T";
	
	/**Constante para Entrega ContraEntrega*/
	static final String ENTREGA_CONTRAENTREGA = "E/E";
	
	/**Constante para bloqueo efectivo*/
	static final String BLOQUEO_EFECTIVO = "BLOQ $";
	
	/**Constante para desbloqueo efectivo*/
	static final String DESBLOQUEO_EFECTIVO = "DESB $";
}
