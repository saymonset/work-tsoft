/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.enviooperaciones.util;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface Constantes extends
        com.indeval.portaldali.middleware.services.util.util.Constantes {

    /** Indica que el origen de la operacion es el portal */
    static final String ORIGEN_OPERACION_PORTAL = "PORTAL";

    /** Indica que el origen de la operacion es el host to host */
    static final String ORIGEN_OPERACION_H2H = "H2H";

    /** Define el estatus de enviado - EN */
    static final String ESTATUS_ENVIADO = "EN";

    /** Define el estatus de no enviado - NE */
    static final String ESTATUS_NO_ENVIADO = "NE";

    /** Define el estatus de acknowledge - AKC */
    static final String ESTATUS_ACK = "ACK";

    /** Define el estatus de not acknowledge - NAK */
    static final String ESTATUS_NAK = "NAK";

    /** Define el estatus de error - ER */
    static final String ESTATUS_ERROR = "ER";

    /** Define el estatus de liquidacion - LQ */
    static final String ESTATUS_LIQUIDACION = "LQ";

    /** Define el tipo de operacion Venta */
    static final String TIPO_INSTRUCCION_V = "V";
    
    /** Define el tipo de operacion Colocaion primaria (Recompra) */
    static final String TIPO_INSTRUCCION_J = "J";

    /** Define COLA_PORTAL (1) */
    static final int COLA_PORTAL = 1;

    /** Define COLA_H2H (2) */
    static final int COLA_H2H = 2;
    
    /** Define la constante para el tipo de instrucción Miscelanea Fiscal */
    static final String MISCELANEA_FISCAL = "M";
    
    static final String MISCELANEA_FISCAL_CANCELACION = "MCA";
    
    static final String DIVISA_MXN="MXN";
    
    static final String E_BANXICO = "E-BANXICO";

}
