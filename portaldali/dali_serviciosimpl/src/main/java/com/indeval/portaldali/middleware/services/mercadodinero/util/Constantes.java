/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero.util;


/**
 * Clase que contiene constantes utilizadas en el modulo de Mercado de Dinero.
 * 
 * @author Christian S&aacute;nchez
 */
public interface Constantes extends com.indeval.portaldali.middleware.services.util.util.Constantes{
    
    /** Define la hora maxima para concertar */
    static final String HORA_MAXIMA_PARA_CONCERTAR = "hora maxima para concertar";
    
    /** Define Area de Trabajo para Apertura de Ssitema y Miscelanea Fiscal */
    static final String AREA_TRABAJO = "APSI";
    
    /** Define clave MERDIN */
    static final String MERDIN = "MERDIN";

    /** Define clave DINERO (DINE) */
    static final String DINERO = "DINE";

    /** Constante utilizada en el metodo cambiarEstatus() */
    static final String PASSWORD = "UNINGMD5";

    /** Define en firme */
    static final String EN_FIRME = "F";

    /** Define clave Clase Ins BM */
    static final String CLAVE_CLASE_INS_BM = "BM";

    /** Define clave "En Transito" */
    static final String EN_TRANSITO = "enTransito";

    /** Constante utilizada en el metodo posisionValores() */
    static final String FUTURO = "futuro";

    /** Define la aplicacion en el metodo insertarMovimientoMiscelaneaFiscal() */
    static final String APLICACION = "traspMD";

    /** Define la aplicacion en el metodo insertarMovimientoMiscelaneaFiscal() */
    static final String APLICACION_CAPITALES = "traspMC";

    /** Define el usuario en el metodo insertarMovimientoMiscelaneaFiscal() */
    static final String USUARIO = "portalLegado";

    /** Define mDinero en el metodo insertarMovimientoMiscelaneaFiscal() */
    static final String MDINERO = "D";

    /** Define mDinero en el metodo insertarMovimientoMiscelaneaFiscal() */
    static final String MDINERO_CAPITALES = "C";
    
//    /** Define Mercado de Dinero */
//    static final String MERCADO_DINERO = "MD";
//    
    /** Define Mercado de Capitales */
   static final String MERCADO_CAPITALES = "MC";

    /** Valor para la sociedad cuando la clave_reporto es DIFERENTE de F y T */
    static final String SOCIEDAD_CONFIRMADA = "C";

    /** Valor para la sociedad cuando la clave_reporto es IGUAL de F o T */
    static final String SOCIEDAD_PENDIENTE_DE_CONFIRMAR = "P";

    /** Define inicio de reporto */
    static final String INICIO_DE_REPORTO = "R";

    /** Define regreso de reporto */
    static final String REGRESO_DE_REPORTO = "X";

    /** Define venta */
    static final String VENTA = "V";

    /** Define traspaso de fondeo */
    static final String TRASPASO_DE_FONDEO = "F";

    /** Define clave reporto D */
    static final String CLAVE_REPORTO_D = "D";

    /** Define clave reporto C */
    static final String CLAVE_REPORTO_C = "C";

    /** Define clave reporto T */
    static final String CLAVE_REPORTO_T = "T";

    /** Define clave reporto Y */
    static final String CLAVE_REPORTO_Y = "Y";

    /** Define cuenta propia */
    static final String CUENTA_PROPIA = "PROP";

    /** Define cuenta terceros */
    static final String CUENTA_TERCEROS = "TERC";

    /** Define tipo cuenta DIRE */
    static final String CLAVE_TIPO_CUENTA_DIRE = "DIRE";

    /** Define tipo cuenta LIBR */
    static final String CLAVE_TIPO_CUENTA_LIBR = "LIBR";

    /** Define tipo cuenta COBE */
    static final String CLAVE_TIPO_CUENTA_COBE = "COBE";

    /** Define tipo cuenta CBPR */
    static final String CLAVE_TIPO_CUENTA_CBPR = "CBPR";

    /** Define tipo cuenta CBTE */
    static final String CLAVE_TIPO_CUENTA_CBTE = "CBTE";

    /** Define CASAS_DE_BOLSA (Id Institucion = 01) */
    static final String CASAS_DE_BOLSA = "01";

    /** Define INDEVAL (Id Institucion = 12) */
    static final String INDEVAL = "12";
    
    /** Define BANCOS (Id Institucion = 02) */
    static final String BANCOS = "02";

    /** Define OPERADORAS SOCIEDADES DE INVERSION (Id Institucion = 08) */
    static final String OPERADORAS_SOCIEDADES_INVERSION = "08";

    /** Define FIDEICOMISO_BANCO_MEXICO (Id Institucion = 13) */
    static final String FIDEICOMISO_BANCO_MEXICO = "13";

    /** Define IPAB (Id Institucion = 17) */
    static final String IPAB = "17";

    /** Define clave FCMD */
    static final String CLAVE_FCMD = "FCMD";

    /** Indica que no hay condiciones de prorroga */
    static int NO_HAY_CONDICIONES_DE_PRORROGA = -683;

    /** Prestamo Pendiente (Status = 3) */
    static final Integer PENDIENTE = new Integer("3");

    /** Indica estatus de registro nuevo en la tabla temporal */
    static final String REGISTRO_NUEVO = "NU";

    /** Indica estatus de registro cargado en la BD */
    static final String REGISTRO_CARGADO = "CA";

    /** Indica el conepto para consultar el precio de una apertura */
    static final String CONCEPTO_CATCONCP_AS = "AS";

    /**
     * Muestra el texto que indica a quien le fue cargado el monto de la
     * apertura - traspasante
     */
    static final String MENSAJE_CARGO_TRASPASANTE = "TRASPASANTE";

    /**
     * Muestra el texto que indica a quien le fue cargado el monto de la
     * apertura - receptor
     */
    static final String MENSAJE_CARGO_RECEPTOR = "RECEPTOR";

    /**
     * Define los campos del archivo de texto - metodo:
     * fileTransferPorcesaInformacion
     */
    static final String FOLIO_OPERACION = "Folio Descripcion";

    /** Constante para el identificado de vendedor */
    static final String ID_INST_VEND = "Id Vendedor";

    /** Constante para el folio de vendedor */
    static final String FOLIO_VEND = "Folio Vendedor";

    /** Constante para la cuenta del vendedor */
    static final String CUENTA_VEND = "Cuenta Vendedor";

    /** Constante para e identificador del id del receptor */
    static final String ID_RECEP = "Id Receptor";

    /** Constante para el folio del receptor */
    static final String FOLIO_RECEP = "Folio Receptor";

    /** Contante para la cuenta del receptor */
    static final String CUENTA_RECEP = "Cuenta Receptor";

    /** Constante para el tipo de valor de una emision */
    static final String TV = "Tipo Valor";

    /** Constante para la emisora de una emision */
    static final String EMISORA = "Emisora";

    /** Constante para la serie de una emision */
    static final String SERIE = "Serie";

    /** Constante para el cupon de una emision */
    static final String CUPON = "Cupon";

    /** Constante para la cantidad operada */
    static final String CANTIDAD = "Cantidad Operada";

    /** Constante para el tipo de operacion */
    static final String TIPO_OPER = "Tipo Operacion";
    
    /** Constante para el tipo de operacion "T" */
    static final String TIPO_OPER_T = "T";

    /** Constante para los dias de plazo */
    static final String DIAS_PLAZO = "Dias de Plazo";

    /** Constante para la fecha */
    static final String FECHA = "Fecha";

    /** Constante para la fecha_rep */
    static final String FECHA_REP = "Fecha Rep";

    /** Constante para el precio */
    static final String PRECIO = "Precio";

    /** Constante para la tasa */
    static final String TASA = "Tasa";

    /** Constante para el plazo */
    static final String PLAZO = "Plazo";

    /** Constante para la indexacion */
    static final String INDEXACION = "Divisa";

    /** Constanta para el id de la tasa ref */
    static final String ID_TASA_REF = "Id Tasa Ref";

    /** Constante para el banco liquidador */
    static final String BANCO_LIQUIDADOR = "Banco Liquidador";

    /**
     * Define los campos del archivo de text - metodo:
     * fileTransferConfirmaTraspaso
     */
    static final String FOLIO = "Folio";

    /** Constante para la fecha de liquidacion */
    static final String FECHA_LIQUIDACION = "Fecha de Liquidacion";

    /**
     * Indica el id del prestamista para la concertacion de prestamos de
     * valpre-e - pretamista = BANXICO
     */
    static final String ID_PRESTAMISTA_VALPRE = "02";

    /**
     * Indica el folio del prestamista para la concertacion de prestamos de
     * valpre-e - pretamista = BANXICO
     */
    static final String FOLIO_PRESTAMISTA_VALPRE = "033";

    /**
     * Indica cuenta del prestamista para la concertacion de prestamos de
     * valpre-e - pretamista = BANXICO
     */
    static final String CUENTA_PRESTAMISTA_VALPRE = "7779";

    /**
     * Define el parametro de entrada para obtener el valor de la tabla
     * parametros - monto maximo por emision
     */
    static final String PARAMETRO_MONTO_MAX_EMISION = "monto maximo por emision";

    /**
     * Define el parametro de entrada para obtener el valor de la tabla
     * parametros - plazo maximo prestamo
     */
    static final String PARAMETRO_PLAZO_MAX_PRESTAMO = "plazo maximo de prestamo";

    /** Indica que el movimiento es de MISCELANEA FISCAL */
    static final String MOVIMIENTO_MISC_FISCAL = "MISCFISC";

    /** Indica que el movimiento es de APERTURA DE SISTEMAS */
    static final String MOVIMIENTO_APERTURA_SISTEMA = "TRASAPSI";

    /** Indica variable status para los SP */
    static final String STATUS = "status";

    /** Indica variable folio_control */
    static final String FOLIO_CONTROL = "folioControl";

    /** Constante para la cuenta de emision 5000 */
    static final String CUENTA_EMISION_5000 = "5000";

    /** Constante para la cuenta de emision 5001 */
    static final String CUENTA_EMISION_5001 = "5001";

    /** Constante que define la clave reporto para una operaci&oacute;n primaria o recompra */
    static final String CLAVE_REPORTO_OP_PRIMARIA_RECOMPRA = "J";

    /** Constante para cuentas de tesoreria */
    static final String TENENCIA_TESO = "TESO";

    /** Constante para DOLAR */
    public static final String DOLLAR = "DL";

    /** Constante para UDI */
    public static final String UDI = "UD";

    /** Constante para PESO */
    public static final String PESO = "PE";
    
    /** Constante para baja logica B */
    public static final String BAJA_LOGICA_B = "B";
    
    /** Constante para el error */
    public static final String CLAVE_ERROR = "MDFTRJ";
    
    /** Define el status por default en el servicio prorroga() */
    static final Integer ERROR_NO_IDENTIFICADO = new Integer(-144);
    
    /** Indica estatus de registro con error en la tabla temporal */
    static final String REGISTRO_ERROR = "ER";

    /** Constante que define COUNT */
    public static final String COUNT = "COUNT";

    /** Constante que define SUMA */
    public static final String SUMA = "SUMA";

    /** Constante que define SELECT */
    public static final String SELECT = "SELECT";

    /** Constante que define DISTINCT */
    public static final String DISTINCT = "DISTINCT";

    /** Constante para el mercado **/
    public static final String PAPEL_GUBER = "PG";
    
    /** Constante para el origen papel guber**/
    public static final String ORIGEN_MERSECPG = "MERSECPG";
    
    /** Constante para el origen papel banca **/
    public static final String ORIGEN_MERSECPB = "MERSECPB";
    
    /** Constante para el origen **/
    public static final String ORIGEN = "01";

	static final String AFORES = "04";

	static final String CUENTA_25 = "25";

}
