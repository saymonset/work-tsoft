/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.filetransfer.util;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface Constantes extends com.indeval.portaldali.middleware.services.util.util.Constantes {

    /** Constante que define el estado nuevo del registro */
    static final String ESTADO_NUEVO = "NU";

    /** Constante que define el estado cargado del registro */
    static final String ESTADO_CARGADO = "CA";

    /** Constante que define el estado error del registro */
    static final String ESTADO_ERROR = "ER";

    /** Constante que define que el registro fue procesado por el protocolo */
    static final String ESTADO_PROTOCOLO = "PR";

    /** Constante que define el tipo de proceso para traspasos mercado dinero */
    static final String TRASPASO_MD = "TD";

    /** Constante que define el tipo de proceso para confirmacion mercado dinero */
    static final String CONFIRMACION_MD = "CD";

    /** Constante que define el tipo de proceso para traspasos mercado capitales */
    static final String TRASPASO_MC = "TC";
    
    /** Constante  que define un deposito de efectivo para BCP Banxico  */
    static final String DEPOSITO_EFECTIVO = "DE";

    /** Constante que define el tipo de proceso para traspasos SCO */
    static final String TRASPASO_SCO = "TS";

    /** Constante que define el tipo de proceso para confirmacion SCO */
    static final String CONFIRMACION_SCO = "CS";

    /** Constante que define el tipo de proceso para liberacion SCO */
    static final String LIBERACION_SCO = "LS";

    /** Constante que define la longitud maxima permitida para traspasos mercado dinero */
    static final int LONGITUD_TRASP_MD_MAX = 145;
    
    /** Constante que define la longitud maxima permitida para traspasos mercado dinero */
    static final int LONGITUD_MAXIMA_TRASP_MD = 155;
    
    /** Constante que define la longitud media permitida para traspasos mercado dinero */
    static final int LONGITUD_MEDIA_TRASP_MD = 137;
    
    /** Constante que define la longitud minima permitida para traspasos mercado dinero */
    static final int LONGITUD_MINIMA_TRASP_MD = 134;

    /** Constante que define la longitud minima permitida para traspasos mercado dinero */
    static final int LONGITUD_TRASP_MD_MIN = 142;

    /** Constante que define la longitud permitida para confirmacion mercado dinero */
    static final int LONGITUD_CONF_MD = 17;

    /** Constante que define la longitud permitida para traspasos SCO */
    static final int LONGITUD_TRASP_SCO = 94;

    /** Constante que define la longitud permitida para traspasos mrecado capitales */
    static final int LONGITUD_TRASP_MC = 94;
    
    /** Constante que define la longitud  maxima permitida para traspasos mrecado capitales */
    static final int LONGITUD_MAXIMA_TRASP_MC = 133;
    
    /** Constante que define la longitud minima permitida para traspasos mrecado capitales */
    static final int LONGITUD_MINIMA_TRASP_MC = 112;
    
    /** Constante que define la longitud media permitida para traspasos mrecado capitales */
    static final int LONGITUD_MEDIA_TRASP_MC = 115;
    
    /** Constante que define el GUION */
    static final String GUION = "-";
    
    static final String FORMATO_FECHA_HORA_FILE_TRANSFER_TRAS = "MM/dd/yyyy HH:mm";

    /** Constante que define el patron alfanumerico */
    static final String PATRON_ALFANUMERICO = "[A-Za-z_0-9Ññ~ ]*";

    /** Constante que define el patron alfabetico */
    static final String PATRON_ALFABETICO = "[A-Za-zÑñ~ ]*";
    
    /** Constante que define el patron alfabetico */
    static final String PATRON_NOMBRE_CORTO_BOVEDA = "[A-Za-z]-[A-Za-z]+";

    /** Constante que define el patron alfanumerico */
    static final String PATRON_NUMERICO = "[ ]*[0-9]+[ ]*";

     /** Constante que define el patron para los tipos de operacion */
     static final String PATRON_TIPO_OPER = "[RTVJ]";

     /** Constante que define el patron para las cantidades */
     static final String PATRON_CANTIDADES = "[0-9]+[./]?[0-9]*";

     /** Constante que define el titulo para la descripcion del folio */
     static final String FOLIO_DESC = "Fol.Desc.";

     /** Constante que define el titulo para el id del vendedor */
     static final String ID_INST_VEND = "Id.Trasp.";

     /** Constante que define el titulo para el folio del vendedor */
     static final String FOLIO_INST_VEND = "Fol.Trasp.";

     /** Constante que define el titulo para la cuenta del vendedor */
     static final String CUENTA_VEND = "Cta.Trasp.";

     /** Constante que define el titulo para el id del receptor */
     static final String ID_RECEP = "Id.Recep.";

     /** Constante que define el titulo para el folio del receptor */
     static final String FOLIO_RECEP = "Fol.Recep.";

     /** Constante que define el titulo para la cuenta del recpetor */
     static final String CUENTA_RECEP = "Cta.Recep.";

     /** Constante que define el titulo para el folio de la operacion */
     static final String FOLIO_CONTROL = "folioControl";

     /** Constante que define el titulo para el tv de la emision */
     static final String TV = "TV";

     /** Constante que define el titulo para la emisora de la emision */
     static final String EMISORA = "Emisora";

     /** Constante que define el titulo para la serie de la emision */
     static final String SERIE = "Serie";

     /** Constante que define el titulo para el cupon de la emision */
     static final String CUPON = "Cupon";

     /** Constante que define el titulo para la cantidad de titulos a operar */
     static final String CANTIDAD = "Cantidad";

     /** Constante que define el titulo para el tipo de operacion a realizar */
     static final String TIPO_OPER = "Tpo.Oper.";

     /** Constante que define el titulo para los dias de plazo del reporto */
     static final String DIAS_PLAZO = "Dias Plazo";

     /** Constante que define el titulo para la fecha de carga */
     static final String FECHA = "Fecha";

     /** Constante que define el titulo para la fecha de reporto */
     static final String FECHA_REP = "Fecha Rep.";

     /** Constante que define el titulo para la fecha de concertacion */
     static final String FECHA_CONCER = "Fecha Concer.";

     /** Constante que define el titulo para el precio */
     static final String PRECIO = "Precio";

     /** Constante que define el titulo para tasa de la operacion */
     static final String TASA = "Tasa";

     /** Constante que define el titulo para el plazo de liquidacion */
     static final String PLAZO = "Plazo";

     /** Constante que define el titulo para el campo divisa */
     static final String DIVISA = "Divisa";

     /** Constante que define el titulo para el tipo de divisa (indexacion) */
     static final String INDEXACION = "Indexacion";
     
     /** Constante que define el titulo para el tipo de divisa (indexacion) */
     static final String BOVEDA_EFECTIVO = "Boveda Efectivo";
     
     /** Constante que define el titulo para el tipo de divisa (indexacion) */
     static final String FECHA_HORA_CIERRE_OPER = "Fecha Hora Concer.";
     
     /** Constante que define el titulo para el tipo de divisa (indexacion) */
     static final String FECHA_HORA_CIERRE_OPER_KEY = "FHCO";

     /** Constante que define el titulo para el id de la tasa de referencia */
     static final String ID_TASA_REF = "Id.Tasa Ref.";

     /** Constante que define el titulo para la bandera de banco liquidador */
     static final String BL = "BL";

     /** Constante que define el titulo para la fecha de liquidacion */
     static final String FECHA_LIQ = "Fecha Liq.";

     /** Constante que define el titulo para la clave del traspasante */
     static final String CLAVE_TRASP = "Cve. Trasp.";

     /** Constante que define el titulo para la clave del receptor */
     static final String CLAVE_RECEP = "Cve. Recep.";

     /** Constante que define el titulo para importe de la operacion */
     static final String IMPORTE = "Importe";

     /** Constante que define el tipo de operacion J (Colocacion primaria o recompra) */
     static final String TIPO_OPER_J = "J";
     
     /** Constante que define el tipo de operacion J (Colocacion primaria o recompra) */
     static final String TIPO_OPER_CORE = "CORE";

     /** Constante que define la cuenta de emision 5000 */
     static final String CUENTA_EMISION_5000 = "5000";

     /** Constante que define la cuenta de emision 5001 */
     static final String CUENTA_EMISION_5001 = "5001";

     /** Constante que define la clase ins BM del instrumento */
     static final String CLASE_INS_BM = "BM";

     /** Constante que define el tipo de papel como gubernamental */
     static final String PAPEL_GUBERNAMENTAL = "PG";

     /** Constante que define el tipo de papel como bancario */
     static final String PAPEL_BANCARIO = "PB";

     /** Constante que define el tipo de operacion C (Reporto indices conocidos) */
     static final String TIPO_OPER_C = "C";

     /** Constante que define el tipo de operacion D (Reporto en dolares) */
     static final String TIPO_OPER_D = "D";

     /** Constante que define el tipo de operacion E (Reporto real) */
     static final String TIPO_OPER_E = "E";

     /** Constante que define el tipo de operacion R (Reporto nominal) */
     static final String TIPO_OPER_R = "R";
     
     /** Constante que define el tipo de operacion R (Reporto nominal) */
     static final String TIPO_OPER_REPO = "REPO";

     /** Constante que define el tipo de operacion V (Venta) */
     static final String TIPO_OPER_V = "V";
     
     /** Constante que define el tipo de operacion V para mercado de dinero(COVE) */
     static final String TIPO_OPER_COVE = "COVE";
     
     /** Constante que define el tipo de operacion V para mercado de capitales(DVPC) */
     static final String TIPO_OPER_DVPC = "DVPC";

     /** Constante que define el tipo de operacion Y (Reporto tasa variable) */
     static final String TIPO_OPER_Y = "Y";

     /** Constante que define el tipo de operacion T (Traspaso libre de pago) */
     static final String TIPO_OPER_T = "T";

     /** Constante que define el tipo de operacion A (TLVE - SCO) */
     static final String TIPO_OPER_A = "A";

     /** Constante que define el tipo de operacion TL (Traspaso libre de pago - SCO) */
     static final String TIPO_OPER_TL = "TL";

     /** Constante que define el tipo de operacion TV (TLVE - SCO) */
     static final String TIPO_OPER_TV = "TV";

     /** Constante que define el tipo de divisa para pesos */
     static final String DIVISA_PESOS = "PE";

     /** Constante que define el tipo de divisa para udis */
     static final String DIVISA_UDIS = "UD";

     /** Constante que define el tipo de divisa para dolares */
     static final String DIVISA_DOLARES = "DL";

     /** Constante que define la sociedad serie P (pendiente de liquidar) */
     static final String SOCIEDAD_SERIE_P = "P";

     /** Constante que define la sociedad serie C (por confirmar) */
     static final String SOCIEDAD_SERIE_C = "C";

     /** Constante que define el titulo para la sociedad serie */
     static final String SOCIEDAD_SERIE = "Soc. Serie";

     /** Constante que define el nombre del tipo de dato BigDecimal  */
     static final String TIPO_DATO_BIG_DECIMAL = "BIGDECIMAL";

     /** Constante que define el nombre del tipo de dato String  */
     static final String TIPO_DATO_STRING = "STRING";

     /** Constante que define el nombre del tipo de dato Integer  */
     static final String TIPO_DATO_INTEGER = "INTEGER";

     /** Constante que define el nombre del tipo de dato Date  */
     static final String TIPO_DATO_DATE = "DATE";
     
     /** Constante que define el nombre del tipo de dato Date con fecha y hora  */
     static final String TIPO_DATO_DATE_TIME = "DATE_TIME";

     /** Constante que define el nombre del tipo de dato Date  */
     static final String ERROR = "error";

     /** Constante que define la cuenta de camara */
     static final String CUENTA_CCV = "250010001";

     /** Constante que define el mercado de capitales */
     static final String MERCADO_MC = "MC";

     /** Constante que define el titulo de mercado */
     static final String MERCADO = "Mercado";

     /** Constante que define el cupon cortado en firme */
     static final String CUPON_CORTADO_EN_FIRME = "F";

     /** Constante que define la baja logica como P */
     static final String BAJA_LOGICA = "P";

     /** Constante que define la baja logica como F */
     static final String BAJA_LOGICA_F = "F";

     /** Constante que define el area de trabajo para la carga por filetransfer - en SCO se llama programa */
     static final String AREA_TRABAJO_FILETRANSFER = "FILETRAN";

     /** Constante que define el estatus para el stored procedure fileTransferDinero */
     static final String STATUS = "status";

     /** Constante que define el titulo de origen */
     static final String ORIGEN = "Origen";

     /** Constante que define el titulo de origen_aplicacion */
     static final String ORIGEN_APLICACION = "Origen Aplic.";

     /** Constante que define el origen de la aplicacion como 01 */
     static final String ORIGEN_APLICACION_01 = "01";

     /** Constante que define el ultimo ciclo en bdcamara..kprocesos */
     static String ULTIMO_CICLO_S = "S";

     /** Constante que define la cuenta con terminacion 97 */
     static final String CUENTA_97 = "97";

     /** Constante que define la cuenta con terminacion 98 */
     static final String CUENTA_98 = "98";

     /** Constante que define la clave del estado RE - Ktraspasos */
     static final String CV_ESTADO_RE = "RE";

     /** Constante que define la clave de la confirmacion N - Ktraspasos */
     static final String GE_CONFIRMA_N = "N";

     /** Constante que define la clave de la confirmacion S - Ktraspasos */
     static final String GE_CONFIRMA_S = "S";

     /** Constante que define la version de alta - u_version */
     static final String U_VERSION_ALTA = "!";

     /** Constante que define la clave INDEVAL */
     static final String CLAVE_INDEVAL = "INDEVAL";

     /** Constante que define la clave FCMD */
     static final String CLAVE_FCMD = "FCMD";

     /** Constante que define FUTC (FUTC) */
     static final String FUTC = "FUTC";

     /** Constante que define INDEVAL (INDEVAL) */
     static final String INDEVAL = "INDEVAL";

     /** Define ERROR_MCFTRJ (MCFTRJ) */
     static final String ERROR_MCFTRJ = "MCFTRJ";

     /** Define la aplicacion para dar de alta el traspaso desde el FileTransfer */
     static final String APLICACION_UPALMDIC = "UPalmdic";

     /**
      * Define el campo m_dinero para dar de alta el traspaso desde el
      * FileTransfer
      */
     static final String M_DINERO = "C";

     /** Define ORIGEN_MERSECMC (MERSECMC) */
     static final String ORIGEN_MERSECMC = "MERSECMC";

     /** Define el area de trabajo para el trapaso - FileTransfer */
     static final String AREA_TRABAJO_MERCAP = "MERCAP";

     /** Define la clave del estado EN - Ktraspasos */
     static final String CV_ESTADO_EN = "EN";
     
     static final String CASFIM = "CASFIM";

     /**
      * Constante que define el area de trabajo para la carga por filetransfer -
      * en MC se llama programa
      */

     /** Constante que define el patron del folio descripcion */
     static final String PATRON_FOLIO_DESC = "[A-Za-z_0-9Ññ~ ]";

     /** Constante que define el patron de la cuenta */
     static final String PATRON_CUENTA = "[0-9 ](0,3)[0-9]";

     /** Constante que define el patron del ID */
     static final String PATRON_ID = "[0-9](2)";

     /** Constante que define el patron del Folio */
     static final String PATRON_FOLIO = "[0-9](3)";

     /** Constante que define el patron del TV */
     static final String PATRON_TV = "[A-Za-z_0-9Ññ~ ](0,3)[A-Za-z_0-9Ññ~]";

     /** Constante que define el patron de la emisora */
     static final String PATRON_EMISORA = "[A-Za-z_0-9Ññ~ ](0,6)[A-Za-z_0-9Ññ~]";

     /** Constante que define el patron de la serie */
     static final String PATRON_SERIE = "[A-Za-z_0-9Ññ~ ](0,5)[A-Za-z_0-9Ññ~]";

     /** Constante que define el patron de la cupon */
     static final String PATRON_CUPON = "[A-Za-z_0-9Ññ~ ](0,3)[A-Za-z_0-9Ññ~]";

     /** Constante que define el patron de la fecha */
     static final String PATRON_FECHA = "[0-9](2)-[A-Za-z](3)-[0-9](4)";

     /** Constante que define el nombre de la cuenta */
     static final String CUENTA = "Cuenta";

     /** Constante que define el tamaño del folio descripcion */
     static final int TAM_FOLIO_DESC = 8;

     /** Constante que define el tamaño de la cuenta */
     static final int TAM_CUENTA = 4;

     /** Constante que define el tamaño del Id Inst */
     static final int TAM_ID_INST = 2;

     /** Constante que define el tamaño del Folio Inst */
     static final int TAM_FOLIO_INST = 3;

     /** Constante que define el tamaño del TV */
     static final int TAM_TV = 4;

     /** Constante que define el tamaño del Emisora */
     static final int TAM_EMISORA = 7;

     /** Constante que define el tamaño del Serie */
     static final int TAM_SERIE = 6;

     /** Constante que define el tamaño del Cupon */
     static final int TAM_CUPON = 4;

     /** Constante que define el tamaño del Cantidad */
     static final int TAM_CANTIDAD = 20;

     /** Constante que define el tamaño del Fecha */
     static final int TAM_FECHA = 11;

     /** Constante que define el arreglo de valores para el traspaso de MC */
     static final int[] TP_MC_VALORES = {
             TAM_FOLIO_DESC, TAM_CUENTA, TAM_ID_INST, TAM_FOLIO_INST, TAM_CUENTA, TAM_TV,
             TAM_EMISORA, TAM_SERIE, TAM_CUPON, TAM_CANTIDAD, TAM_FECHA };

     /** Constante que define el arreglo de nombres para el traspaso de MC */
     static final String[] TP_MC_NOMBRES = {
             FOLIO_DESC, CUENTA, ID_RECEP, FOLIO_RECEP, CUENTA_RECEP, TV,
             EMISORA, SERIE, CUPON, CANTIDAD, FECHA };

     /** Define CUENTA_AGENTE_FIRMADO (T) */
     static final String CLAVE_REPORTO_T = "T";

     /** Define PESO (PE) */
     static final String PESO = "PE";

     /** Define Marca de compra*/
     static final String  MARCA_COMPRA = "MC";
     
     /** Constante  que define una carga de cuentas de banca comercial*/
     static final String  CUENTAS_BCOM = "CB";
     
     /** Constante  que define una carga de retiros de banca comercial*/
     static final String  RETIROS_BCOM = "RB";
     
     /** Constante  que define una carga de retiros de banca comercial*/
     static final String  TRASPASOS_EFECTIVO = "TR";
     
     /** Constante que define la longitud permitida para cuentas de banca comercial*/
     static final int LONGITUD_MIN_CUENTAS_BCOM = 110;
     
     /** Constante que define la longitud permitida para retiros de banca comercial*/
     static final int LONGITUD_RETIROS_BCOM = 74;
     
     /** Constante que define la longitud permitida para retiros de banca comercial*/
     static final int LONGITUD_TRASPASOS_EFECTIVO = 46;
     
     /** Constante que define el titulo para la fecha de liquidacion */
     static final String CUENTA_BENEF = "Cta. Benef.";
     
     /** Constante que define el titulo para la fecha de liquidacion */
     static final String NOMBRE_BENEF = "Nom. Benef.";
     
     /** Constante que define el titulo para la fecha de liquidacion */
     static final String MAX_MENSUAL  = "Max. Mensual";
     
     /** Constante que define el titulo para la fecha de liquidacion */
     static final String MAX_DIARIO   = "Max. Diario";
     
     /** Constante que define el titulo para la fecha de liquidacion */
     static final String MAX_XTRANSC  = "Max. Transc.";
     
     /** Constante que define el titulo para la fecha de liquidacion */
     static final String MOVS_MENSUAL = "Movs. Mensual";
     
     /** Constante que define el titulo para la fecha de liquidacion */
     static final String ID_CTA_BENEF = "Id. Cta. Benf.";
     
     /** Constante que define el titulo para la fecha de liquidacion */
     static final String REFERENCIA = "Referencia";
     
     /** Constante que define el titulo para la fecha de liquidacion */
     static final String CONCEPTO = "Concepto";
     
     /** Id Folio de la institucion INDEVAL */
 	static final String ID_FOLIO_INDEVAL = "12001";

}
