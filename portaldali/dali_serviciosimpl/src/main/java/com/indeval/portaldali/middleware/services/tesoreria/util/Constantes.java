/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.tesoreria.util;

import java.math.BigDecimal;

/**
 * Clase que contiene constantes utilizadas en el modulo de Tesoreria.
 * @author Christian S&aacutenchez
 */
public interface Constantes extends
        com.indeval.portaldali.middleware.services.util.util.Constantes {

    /** Define Cuenta Concentradora de Efectivo */
    public static final String CUENTA_CONCENTRADORA = "CC";

    /** Define Cuenta Liquidadora de Efectivo */
    public static final String LIQUIDADORA_DINERO = "MD";

    /** Define Cuenta Liquidadora de Capitales de Efectivo */
    public static final String LIQUIDADORA_CAPITALES = "MC";

    /** Define la referencia a retiros SPEI, usado en retirarFondos */
    public static final String REEFSPEI = "REEFSPEI";

    /** Define la referencia a retiros SIAC, usado en retirarFondos */
    public static final String REEFSIAC = "REEFSIAC";

    /** Define Status, usado en retirarFondos */
    public static final String STATUS = "status";

    /** Define Error, usado en retirarFondos */
    public static final String ERROR = "error";

    /** Define Error, usado en permitirRetirarFondos */
    public static final String ERROR_AREAS_TRABAJO = "error";

    /** Define Mercado Secundario, usado en getEstadoCuentaSNE */
    public static final String MERCADO_SECUNDARIO = "MERSEC";

    /** Define RSP, usado en getEstadoCuentaSNE */
    public static final String RSP = "RSP";

    /** Define RSP, usado en getEstadoCuentaSNE */
    public static final String TRR = "TRR";

    /** Define Fase 8 */
    public static final int FASE_OCHO = 8;

    /** Define TESORERIA */
    public static final String TESORERIA = "TESORERIA";

    /** Define TESOR */
    public static final String TESOR = "TESOR";

    /** Define TRJ */
    public static final String TRJ = "TRJ";

    /** Define COLPRIM */
    public static final String COLPRIM = "COLPRIM";

    /** Define MOVEFINT */
    public static final String MOVEFINT = "MOVEFINT";

    /** Define VENCGAR */
    public static final String VENCGAR = "VENCGAR";

    /** Define VENCEMIS */
    public static final String VENCEMIS = "VENCEMIS";

    /** Define INTEMIS */
    public static final String INTEMIS = "INTEMIS";

    /** Define DECRETOS */
    public static final String DECRETOS = "DEC";

    /** Define Constante de cadena 0 * */
    public static final String CERO_STRING = "0";

    /** Define Constante de cadena 2 * */
    public static final String DOS_STRING = "2";

    /** Define Constante int para 0 * */
    public static final int CERO_INT = 0;

    /** Define Constante para 1* */
    public static final int UNO_INT = 1;

    /** Define Constante int de 2 * */
    public static final int DOS_INT = 2;

    /** Define Constante de int para 3* */
    public static int TRES_INT = 3;

    /** Define Constante de int para 4* */
    public static int CUATRO_INT = 4;

    /** Define Constante de int 5 * */
    public static int CINCO_INT = 5;

    /** Define Constante para int 6 * */
    public static int SEIS_INT = 6;

    /** Define Constante para int 8 * */
    public static int OCHO_INT = 8;

    /** Define Constante de int para 10* */
    public static int DIEZ_INT = 10;

    /** Define Constante de int para 11* */
    public static int ONCE_INT = 11;

    /** Define Constante de int para 12* */
    public static int DOCE_INT = 12;

    /** Indica el tipo de operacion RETIROS_SIAC en getTraspFondos() */
    public static final String RETIROS_SIAC = "21";

    /** Indica el tipo de operacion RETIROS_AMBOS en getTraspFondos() */
    public static final String RETIROS_AMBOS = "21,28";

    /** Indica el tipo de operacion DEPOSITOS_SIAC en getTraspFondos() */
    public static final String DEPOSITOS_SIAC = "23";

    /**
     * Indica el tipo de operacion DEPOSITOS_LIBERACION_DE_GARANTIAS en
     * getTraspFondos()
     */
    public static final String DEPOSITOS_LIBERACION_DE_GARANTIAS = "25";

    /** Indica el tipo de operacion DEPOSITOS_SPEI en getTraspFondos() */
    public static final String DEPOSITOS_SPEI = "27";

    /** Indica que el tipo de operacion es Amortizaciones */
    public static final String AMORTIZACIONES = "AMP_AMT";

    /** Indica que el tipo de operacion es Dividendos */
    public static final String DIVIDENDOS = "DIV";

    /** Indica que el tipo de operacion es Intereses */
    public static final String INTERESES = "INT";

    /** Indica que el tipo de operacion es Todos */
    public static final String TODOS = "TODOS";

    /** Indica que el tipo de operacion es Casi-Todos */
    public static final String CASI_TODOS = "CASI_TODOS";

    /** Indica que el tipo de moneda es dolares */
    public static final String DOLARES = "DL";

    /** Indica que el tipo de moneda son udis */
    public static final String UDIS = "UD";

    /** Numero de error a buscar * */
    public static final String ERROR_J9999 = "J9999";

    /** Define el tipo de moneda para ejercicio de derechos - PESOS */
    public static final String MXN = "MXN";

    /** Define el tipo de moneda para ejercicio de derechos - DOLARES */
    public static final String USD = "USD";

    /** Define el tipo de moneda para ejercicio de derechos - UDIS */
    public static final String MXV = "MXV";

    /** Define Constante para tipos de ejercicio COB * */
    public static final String TIPO_EJERCICIO_COB = "COB";

    /** Define  Constante para tipos de ejercicio EJDPB  **/
    public static final String EJDPB = "EJDPB";

    /** Define  Constante para tipos de ejercicio EJDPG  **/
    public static final String EJDPG = "EJDPG";
    
    /** Define la constante para el ejercicio de derechos de mercado de capitales */
    static final String EJDMC = "EJDMC";
    
    /** Define la constante para la liquidacion de ejercicio de derechos de mercado de capitales */
    static final String LIQEJDMC = "LIQEJDMC";
    
    /** Define la constante para el origen de aplicaci&oacute;n de SIDV */
    static final String ORIGEN_SIDV = "01";
    
    /** Define la constante para el origen de la aplicaci&oacute;n de Ejercicio de Derechos */
    static final String ORIGEN_EJERCICIO_DERECHOS = "16";
    
    /** Define la constante para el mercado MC 'MERCADO DE CAPITALES' */
    static final String MERCADO_CAPITALES = "MC";
    
    /** Define la constante para papel bancario */
    static final String PAPEL_BANCARIO = "PB";
    
    /** Define la constante para papel gubernamental */
    static final String PAPEL_GUBERNAMENTAL = "PG";
    
    /** Indica el tipo de operacion RETIROS_SPEI */
    static final String RETIROS_SPEI = "28";
    
    /** Define la constante cero para el tipo de dato BigDecimal */
    static final BigDecimal CERO_BIG_DECIMAL = new BigDecimal("0");
    
    /** Define la constante para la instituci&oacute;n Indeval */
    static final String INDEVAL = "INDEVAL";
    
    /** Define la constante para la aplicaci&oacute;n DREF */
    static final String APLICACION_DREF = "DREF";
    
    /** Define la constante para la aplicaci&oacute;n de Traspaso LC to LD (CVJGTCD) */
    static final String APLICACION_CVJGTCD = "CVJGTCD";
    
    /** Define la constante para la aplicaci&oacute;n de Traspaso LC to LD (CVJGTCC) */
    static final String APLICACION_CVJGTCC = "CVJGTCC";
        
    /** Define la constante para la aplicaci&oacute;n de Traspaso LC to LD (CVEBTE01) */
    static final String APLICACION_CVEBTE01 = "CVEBTE01";
    
    /** Define la constante para el origen de aplicaci&oacute;n '01' (SIDV) */
    static final String ORIGEN_APLICACION_01 = "01";
    
    /** Define la constante para los dep&acoute;sitos de SIDV */
    static final String DEPOSITO_SIDV = "DEPSIDV";
    
    /** Define la constante para los retiros de SIDV */
    static final  String RETIRO_SIDV  = "RETSIDV";
    
    /** Define la constante para la aplicaci&oacute;n 'PROCOVAL' */
    static final String APLICACION_PROCOVAL = "PROCOVAL";
    
    /** Define la constante para el estatus de aplicaci&oacute;n activo */
    static final String ESTATUS_ACTIVO = "A";
    
    /** Define la constante para el estatis de aplicaci&oacute;n cancelado */
    static final String ESTATUS_CANCELADO = "C";
    
    /** Define la constante para dep&oacute;sitos efectivo SIDV*/
    static final String DEEFSI = "DEEFSI";
    
    /** Define la constante para dep&oacute:sitos efectivo SPEI*/
    static final String DEEFSP = "DEEFSP";
    
    /** Define la constante AMTGAR*/
    static final String AMTGAR = "AMTGAR";
    
    /** Define la constante DEEFPE */
    static final String DEEFPE = "DEEFPE";
    
    /** Define la constante REEFSPUA */
    static final String REEFSPUA = "REEFSPUA";
    
    /** Define la constante DEPSIDV */
    static final String DEPSIDV = "DEPSIDV"; 
    
    /** Define la constante para la aplicaci&oacute;n liquidaci&oacute;n de capitales */
    static final String LIQUIDACION_CAPITALES = "LIQ_CAP";
    
    /** Define la constante para el &aacute;rea de liquidaci&oacute;n */
    static final String AREA_LIQUIDACION = "LIQUIDA";
    
    /** Define la constante para el id_inst de Banxico */
    static final String ID_INSTITUCION_BANXICO = "02";
    
    /** Define la constante para el folio_inst de Banxico */
    static final String FOLIO_INSTITUCION_BANXICO = "033";

    /** Define la constante para la aplicaci&oacute;n TREF */
    static final String APLICACION_TREF = "TREF";
    
    /** Define la constante para el mercado MD 'MERCADO DE DINERO' */
    static final String MERCADO_DINERO = "MD";
    
    /** Tipo de Operacion Retiro de Cuenta Concentradora SPEI */
    String TIPO_OPERACION__RETIRO_CCS             = "CCS";
    /** Tipo de Operacion Retiro SPEI */
    String TIPO_OPERACION__RETIRO_SPEI            = "SPEI";
    /** Tipo de Operacion Retiro SIAC */
    String TIPO_OPERACION__RETIRO_SIAC            = "SIAC";
    /** Tipo de Operacion Transferencia Efectivo */
    String TIPO_OPERACION__TRANSFERENCIA_EFECTIVO = "TREF";
 
    /** Define la constante para el origen de la OPERACION DE EFECTIVO */
    static final String ORIGEN__PORTAL_DALI = "DALI";
    
    static final String DATOS_FIRMA__USUARIO     = "usuario";
    static final String DATOS_FIRMA__SERIE       = "serie";
    static final String DATOS_FIRMA__ISO_FIRMADO = "iso_firmado";
    static final String DATOS_FIRMA__TICKET      = "usrCredencial";
    
    static final int ID_TIPO_INSTRUCCION__TREF      = 30;
    static final int ID_TIPO_INSTRUCCION__RETE      = 33;
}

