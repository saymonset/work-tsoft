/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales.util;

/**
 * Clase que contiene constantes utilizadas en el modulo de Mercado de
 * Capitales.
 *
 * @author Christian S&aacute;nchez
 */
public interface Constantes extends com.indeval.portaldali.middleware.services.util.util.Constantes {

    /** Define Depositos Retiros (DEP_RET) */
    static final String DEP_RET = "DEP_RET";

    /** Define Todas (ALLS) */
    static final String TODAS = "ALLS";

    /** Define PESO (PE) */
    static final String PESO = "PE";

    /** Define AREA_TRABAJO_DVP (DVP) */
    static final String AREA_TRABAJO_DVP = "DVP";

    /** Define AREA_TRABAJO_TLP (TLP) */
    static final String AREA_TRABAJO_TLP = "TLP";

    /** Define ORIGEN_MERSECMC (MERSECMC) */
    static final String ORIGEN_MERSECMC = "MERSECMC";

    /** Define MERCADO_MC (MC) */
    static final String MERCADO_MC = "MC";

    /** Define APLICACION_PORTAL (PORTAL) */
    static final String APLICACION_PORTAL = "PORTAL";

    /** Define ERROR_MCFTRJ (MCFTRJ) */
    static final String ERROR_MCFTRJ = "MCFTRJ";

    /** Define SALIDA (SALIDA) */
    static final String SALIDA = "SALIDA";

    /** Define ENTRADA (ENTRADA) */
    static final String ENTRADA = "ENTRADA";

    /** Define TOTALES (TOTALES) */
    static final String TOTALES = "TOTALES";

    /** Define REGISTROS (REGISTROS) */
    static final String REGISTROS = "REGISTROS";

    /** Define CUPON_CORTADO_EN_FIRME (F) */
    static final String CUPON_CORTADO_EN_FIRME = "F";

    /** Define TENENCIA_TESORERIA (TESO) */
    static final String TENENCIA_TESORERIA = "TESO";

    /** Define CUENTA_AGENTE_FIRMADO (T) */
    static final String CLAVE_REPORTO_T = "T";

    /** Define CUENTA_AGENTE_FIRMADO (CUENTA_AGENTE_FIRMADO) */
    static final String CUENTA_AGENTE_FIRMADO = "CUENTA_AGENTE_FIRMADO";

    /** Define COMPRADOR (Boolean.TRUE) */
    static Boolean COMPRADOR = Boolean.TRUE;

    /** Define TIPO_FECHA (liquidacion) */
    static String TIPO_FECHA = "liquidacion";

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

    /** Define CUENTA_53 (53) */
    static final String CUENTA_53 = "53";

    /** Define CUENTA_97 (97) */
    static final String CUENTA_97 = "97";

    /** Define CUENTA_98 (98) */
    static final String CUENTA_98 = "98";

    /** Define ACTIVO (activo) */
    static final String ACTIVO = "activo";

    /** Define ORIGEN_APLICACION_TLP_MERSECMC (MERSECMC) */
    static final String ORIGEN_APLICACION_TLP_MERSECMC = "MERSECMC";

    /** Define ORIGEN_APLICACION_ACTIVO_FONDEO (FONDEO) */
    static final String ORIGEN_APLICACION_ACTIVO_FONDEO = "FONDEO";

    /** Define DEPOSITO (DEP) */
    static final String DEPOSITO = "DEP";

    /** Define RETIRO (RET) */
    static final String RETIRO = "RET";

    /** Define COMPRA (COM) */
    static final String COMPRA = "COM";

    /** Define TRASPASO (TRA) */
    static final String TRASPASO = "TRA";

    /** Define INTERESES (INT) */
    static final String INTERESES = "INT";

    /** Define AMORTIZACION_PARCIAL (AMP) */
    static final String AMORTIZACION_PARCIAL = "AMP";

    /** Define AMORTIZACION_TOTAL (AMT) */
    static final String AMORTIZACION_TOTAL = "AMT";

    /** Define DIVIDENDOS (DIV) */
    static final String DIVIDENDOS = "DIV";

    /** Define CAPTURA (CAP) */
    static final String CAPTURA = "CAP";

    /** Define TRASPASO_SALIDA (TS) */
    static final String TRASPASO_SALIDA = "TS";

    /** Define TRASPASO_ENTRADA (TE) */
    static final String TRASPASO_ENTRADA = "TE";

    /** Define VENTA (VEN) */
    static final String VENTA = "VEN";

    /** Define la clave del estado RE - Ktraspasos */
    static final String CV_ESTADO_RE = "RE";

    /** Define la clave del estado EN - Ktraspasos */
    static final String CV_ESTADO_EN = "EN";

    /** Define la clave de la confirmacion N - Ktraspasos */
    static final String GE_CONFIRMA_N = "N";

    /** Define la clave de la confirmacion S - Ktraspasos */
    static final String GE_CONFIRMA_S = "S";

    /** Define la clave del traspaso TL - Ktraspasos */
    static final String GE_TRASPASO_TL = "TL";

    /** Define la clave del traspaso TV - Ktraspasos */
    static final String GE_TRASPASO_TV = "TV";

    /** Define la aplicacion para dar de alta el traspaso desde el FileTransfer */
    static final String APLICACION_UPALMDIC = "UPalmdic";

    /** Define el campo m_dinero para dar de alta el traspaso desde el FileTransfer */
    static final String M_DINERO = "C";

    /** Define la sociedad para dar de alta el traspaso desde el FileTransfer */
    static final String SOCIEDAD_SERIE_P = "P";

    /** Define la baja logica para dar de alta el traspaso desde el FileTransfer */
    static final String BAJA_LOGICA_F = "F";

    /** Define la variable del estatus */
    static final String STATUS = "status";

    /** Define ultimo ciclo igual a S en bdcamara..kprocesos */
    static String ULTIMO_CICLO_S = "S";

    /** Define la llave del mapa que regresa el SP - FileTransfer */
    static final String ERROR = "error";

    /** Define la clave del estado para el registro nuevo - FileTransfer */
    static final String ESTADO_NUEVO = "NU";

    /** Define el origen de la aplicacion para el trapaso - FileTransfer */
    static final String ORIGEN_APLICACION_01 = "01";

    /** Define el area de trabajo para el trapaso - FileTransfer */
    static final String AREA_TRABAJO_MERCAP = "MERCAP";

    /** Define los campos del archivo de texto - metodo: fileTransferPorcesaInformacionTraspaso */
    static final String FECHA_LIQUIDACION = "Fecha de Liquidacion";

    /** */
    static final String CLAVE_TRASPASANTE = "Clave Traspasante";

    /** */
    static final String CUENTA_TRASPASANTE = "Cuenta Traspasante";

    /** */
    static final String CLAVE_RECEPTOR = "Clave Receptor";

    /** */
    static final String CUENTA_RECEPTOR = "Cuenta Receptor";

    /** */
    static final String TV = "Tipo Valor";

    /** */
    static final String EMISORA = "Emisora";

    /** */
    static final String SERIE = "Serie";

    /** */
    static final String CUPON = "Cupon";

    /** */
    static final String TIPO_OPERACION = "Tipo Operacion";

    /** */
    static final String PLAZO = "Plazo";

    /** */
    static final String CANTIDAD = "Cantidad Operada";

    /** */
    static final String IMPORTE = "Importe";

    /** */
    static final String FOLIO_DESCRIPCION = "Folio Descripcion";

    /** */
    static final String ID_INST_RECEP = "Id Receptor";

    /** */
    static final String FOLIO_INST_RECEP = "Folio Receptor";

    /** */
    static final String FECHA = "Fecha";

    /** Define el campo que se utiliza para liberacion y confirmacion en FileTransfer */
    static final String FOLIO = "Folio";

    /** Constante para la divisa d&oacute;lar */
    static final String DOLAR = "DL";

    /** Constante para las unidades de inversi&oacute;n */
    static final String UDI = "UD";

    /** Constante para la clave reporto '0' */
    static final String CLAVE_REPORTO_O = "O";

    /** Constante para la clave reporto 'P' */
    static final String CLAVE_REPORTO_P = "P";

    /** Constante para definir la cuenta de camara (CCV) */
    static final String CUENTA_CCV = "250010001";

    /** Define CUENTA_25 (25) */
    static final String CUENTA_25 = "25";

    /** Constante para la clave reporto 'V' */
    static final String CLAVE_REPORTO_V = "V";

    /** Constante para la clave reporto 'A' */
    static final String CLAVE_REPORTO_A = "A";

    /** Constante para el origen 08 */
    static final String ORIGEN_08 = "08";

    /** Constante para la cuenta propia */
    static final String CUENTA_PROPIA = "PRO";

    /** Constante para la cuenta de terceros */
    static final String CUENTA_TERCEROS = "TER";

    /** Constante para la cuenta mder*/
    static final String CUENTA_MDER = "MDER";

    /** Constante para la cuenta cgar */
    static final String CUENTA_CGAR = "CGAR";

    /** Define USUARIO_JV (JV_USER) */
    static final String USUARIO_JV = "JV_USER";
    
}
