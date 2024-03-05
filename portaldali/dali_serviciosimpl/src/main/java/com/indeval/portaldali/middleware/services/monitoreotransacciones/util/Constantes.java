/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.monitoreotransacciones.util;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface Constantes extends
        com.indeval.portaldali.middleware.services.util.util.Constantes {
    
    /** Define el estatus de pendiente - PE */
    static final String ESTATUS_PENDIENTE = "PE";
    
    /** Define el estatus de enviado - EN */
    static final String ESTATUS_ENVIADO = "EN";

    /** Define el estatus de ak - AK */
    static final String ESTATUS_AK = "AK";

    /** Define el estatus de falta valores - FV */
    static final String ESTATUS_FALTA_VALORES = "FV";

    /** Define el estatus de clave falta valores */
    static final String CLAVE_FALTA_VALORES = "Pendiente";

    /** Define el estatus de clave liquidacion */
    static final String CLAVE_LIQUIDACION = "MDFTRJ9904";

    /** Define el estatus de error - ER */
    static final String ESTATUS_ERROR = "ER";

    /** Define el estatus de nak - NA */
    static final String ESTATUS_NAK = "NA";

    /** Define el estatus de liquidacion - LQ */
    static final String ESTATUS_LIQUIDACION = "LQ";

    /** Define el dep&oacute;sito de valores de ANL a AL */
    static final String DEPOSITO_VALORES = "DV";

    /** Define el retiro de valores de AL a ANL */
    static final String RETIRO_VALORES = "RV";

    /** Define el dep&oacute;sito de garant&iacute;a */
    static final String DEPOSITO_GARANTIA = "DG";

    /** Define el retiro de garant&iacute;a */
    static final String RETIRO_GARANTIA = "RG";

    /** Define el traspaso anticipado */
    static final String TRASPASO_ANTICIPADO = "TA";

    /** Define el retiro de valores en el fin de d&iacute;a */
    static final String RETIRO_FIN_DIA = "FD";
    
    /** Define el idfoliocuenta fija */
    static final String IDFOLIOCUENTA_FIJA = "250010001";    
}
