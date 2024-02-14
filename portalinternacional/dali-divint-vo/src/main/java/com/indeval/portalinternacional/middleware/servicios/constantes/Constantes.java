/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.constantes;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.math.BigDecimal;

/**
 * @author javiles
 */
public interface Constantes extends com.indeval.portaldali.middleware.servicios.util.constants.Constantes {

    String ESTATUS_NO_ENVIADA = "NE";

    BigInteger UNO_BIGINTEGER = BigInteger.valueOf(1);

    BigInteger CERO_BIGINTEGER = BigInteger.valueOf(0);

    Integer UNO_INTEGER = Integer.valueOf(1);

    Integer CERO_INTEGER = Integer.valueOf(0);

    String CERO_STRING = "0";

    String UNO_STRING = "1";

    String MENOS_UNO_STRING = "-1";

    Long CERO_LONG = Long.valueOf(0);

    Long UNO_MENOS_LONG = Long.valueOf(-1);

    Double CERO_DOUBLE = Double.valueOf(0);

    String ESTADO_ERROR = "ER";

    String ESTADO_NUEVO = "NU";

    String ESTADO_CARGADO = "CA";

    String ESTADO_A_PROTOCOLO = "PR";

    String ESTADO_CORRECTO = "OK";

    String TIPO_FILE_TRANSFER = "INVERSION_EXTRANJERA";
    /**
     * Constante que define el valor m&aacute;ximo de d&iacute;as de plazo en la fecha liquidacion
     */
    int VALOR_MAXIMO_DIAS = 8;
    /**
     * Constante que define la longitud m&iacute;nima permitida para traspasos de divisi&oacute;n internacional
     */
    int LONGITUD_TRASP_DIV_INT_MIN = 403;

    /**
     * Constante que define la longitud permitida para traspasos de inversi&oacute;n extranjera
     */
    int LONGITUD_TRASP_INVERSION_EXTRANJERA = 84;

    /**
     * Constante que define la longitud m&aacute;xima permitida para traspasos de divisi&oacute;n internacional
     */
    int LONGITUD_TRASP_DIV_INT_MAX = 621;

    /**
     * Constante que define la longitud intermedia permitida para traspasos de divisi&oacute;n internacional
     */
    int LONGITUD_TRASP_DIV_INT_INTER = 421;

    /*
     * Constante que define la longitud del plazo permitida para traspasos de divisi&oacute;n internacional
     */

    int LONGITUD_PLAZO = 2;

    /**
     * Constante que define el GUION
     */
    String GUION = "-";

    /**
     * Constante que define el patron alfanumerico
     */
    String PATRON_ALFANUMERICO = "[A-Za-z_0-9- ]*";

    /**
     * Constante que define el patron alfanumerico sin espacio
     */
    String PATRON_ALFANUMERICO_SIN_ESPACIO = "[A-Za-z0-9]*";

    /**
     * Constante que define el patron alfabetico
     */
    String PATRON_ALFABETICO = "[A-Za-z ]*";

    /**
     * Constante que define el patron alfanumerico
     */
    String PATRON_NUMERICO = "[0-9 ]*";

    /**
     * Constante que define el patron de una fecha
     */
    String PATRON_FECHA = "\\d{2}\\-[A-Za-z]{3}\\-\\d{4}";

    /**
     * Constante que define el patron de rfc simple
     */
    //static final String PATRON_RFC_SIMPLE = "[A-Z,Ñ,&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9]";
    String PATRON_RFC_SIMPLE = "^([A-Z&Ññ]{3}|[A-Z][AEIOU][A-Z]{2})\\d{2}((01|03|05|07|08|10|12)(0[1-9]|[12]\\d|3[01])|02(0[1-9]|[12]\\d)|(04|06|09|11)(0[1-9]|[12]\\d|30))([A-Z0-9]{2}[0-9A])?$";

    /**
     * Constante que define el patron alfanumerico sin espacio
     */
    String PATRON_NUMERICO_SIN_ESPACIO = "[0-9]*";

    /**
     * Constante que define el patron para los tipos de operacion
     */
    String PATRON_TIPO_OPER = "[CDERTVJ]";

    /**
     * Constante que define el patron para las cantidades
     */
    String PATRON_CANTIDADES = "[0-9 ]*.[0-9 ]*";

    /**
     * Constante que define el titulo para la descripcion del folio
     */
    String FOLIO_DESC = "Fol.Desc.";

    /**
     * Constante que define el titulo para el id del vendedor
     */
    String ID_INST_VEND = "Id.Trasp.";

    /**
     * Constante que define el titulo para el folio del vendedor
     */
    String FOLIO_INST_VEND = "Fol.Trasp.";

    /**
     * Constante que define el titulo para la cuenta del vendedor
     */
    String CUENTA_VEND = "Cta.Trasp.";

    /**
     * Constante que define el titulo para el id del receptor
     */
    String ID_RECEP = "Id.Recep.";

    /**
     * Constante que define el titulo para el folio del receptor
     */
    String FOLIO_RECEP = "Fol.Recep.";

    /**
     * Constante que define el titulo para la cuenta del recpetor
     */
    String CUENTA_RECEP = "Cta.Recep.";

    /**
     * Constante que define el titulo para el folio de la operacion
     */
    String FOLIO_CONTROL = "folioControl";

    /**
     * Constante que define el titulo para el tv de la emision
     */
    String TV = "TV";

    /**
     * Constante que define el titulo para la emisora de la emision
     */
    String EMISORA = "Emisora";

    /**
     * Constante que define el titulo para la serie de la emision
     */
    String SERIE = "Serie";

    /**
     * Constante que define el titulo para el cupon de la emision
     */
    String CUPON = "Cupon";

    /**
     * Constante que define el titulo para el isin de la emision
     */
    String ISIN = "Isin";

    /**
     * Constante que define el titulo para el Id Boveda
     */
    String ID_BOVEDA = "Id Boveda";

    /**
     * Constante que define el titulo para la cantidad de titulos a operar
     */
    String CANTIDAD = "Cantidad";

    /**
     * Constante que define el titulo para el tipo de operacion a realizar
     */
    String TIPO_OPER = "Tpo.Oper.";

    /**
     * Constante que define el tipo de operacion TV (TLVE - SCO)
     */
    String TIPO_OPER_TV = "TV";

    /**
     * Constante que define el tipo de operacion T (Traspaso libre de pago)
     */
    String TIPO_OPER_T = "T";

    /**
     * Constante que define el tipo de operacion TI (Traspaso libre de pago Internacional)
     */
    String TIPO_OPER_TI = "TI";

    /**
     * Constante que define el tipo de operacion TL (Traspaso libre de pago - SCO)
     */
    String TIPO_OPER_TL = "TL";

    /**
     * Constante que define el tipo de operacion V (Venta)
     */
    String TIPO_OPER_V = "V";

    /**
     * Constante que define el tipo de operacion VI (Venta Internacional)
     */
    String TIPO_OPER_VI = "VI";

    /**
     * Constante que define el titulo para el tipo de movimiento a realizar
     */
    String TIPO_MOV = "Tipo Movimiento";

    /**
     * Constante que define el titulo para los dias de plazo del reporto
     */
    String DIAS_PLAZO = "Dias Plazo";

    /**
     * Constante que define el titulo para la fecha de carga
     */
    String FECHA = "Fecha";

    /**
     * Constante que define el titulo para la fecha de reporto
     */
    String FECHA_REP = "Fecha Rep.";

    /**
     * Constante que define el titulo para la fecha de concertacion
     */
    String FECHA_CONCER = "Fecha Concer.";

    /**
     * Constante que define el titulo para la fecha de notificaci&oacute;n
     */
    String FECHA_NOT = "Fecha Notificacion";

    /**
     * Constante que define el titulo para la fecha de la operacci&oacute;n
     */
    String FECHA_OPER = "Fecha Operacion";

    /**
     * Constante que define el titulo para el precio
     */
    String PRECIO = "Precio";

    /**
     * Constante que define el titulo para tasa de la operacion
     */
    String TASA = "Tasa";

    /**
     * Constante que define el titulo para el plazo de liquidacion
     */
    String PLAZO = "Plazo";

    /**
     * Constante que define el titulo para el campo divisa
     */
    String DIVISA = "Divisa";

    /**
     * Constante que define el titulo para las instrucciones especiales
     */
    String INST_ESP = "Instrucciones Especiales";

    /**
     * Constante que define el titulo para el tipo de divisa (indexacion)
     */
    String INDEXACION = "Indexacion";

    /**
     * Constante que define el titulo para el id de la tasa de referencia
     */
    String ID_TASA_REF = "Id.Tasa Ref.";

    /**
     * Constante que define el titulo para la bandera de banco liquidador
     */
    String BL = "BL";

    /**
     * Constante que define el titulo para la fecha de liquidacion
     */
    String FECHA_LIQ = "Fecha Liq.";

    /**
     * Constante que define el titulo para la clave del traspasante
     */
    String CLAVE_TRASP = "Cve. Trasp.";

    /**
     * Constante que define el titulo para la clave del receptor
     */
    String CLAVE_RECEP = "Cve. Recep.";

    /**
     * Constante que define el titulo para importe de la operacion
     */
    String IMPORTE = "Importe";

    /**
     * Define el Traspaso Libre de Pago
     */
    String TRASPASO_LIBRE = "TLP";

    /**
     * Define el Traspaso Contrapago
     */
    String TRASPASO_CONTRA = "TCP";

    /**
     * tipo agente = SICT
     */
    String SICT = "SICT";

    /**
     * tipo agente = SICP
     */
    String SICP = "SICP";

    /**
     * Constante que define el titulo de la descripci&oacute;n del custodio
     */
    String CUSTODIO = "Descripcion. Custodio";

    /**
     * Constante que define el titulo de la cuenta de la contraparte
     */
    String CUENTA_CONTRA = "Cuenta Contraparte";

    /**
     * Constante que define el titulo de la descripci&oacute;n de la contraparte
     */
    String CONTRAPARTE = "Descripcion Contraparte";

    /**
     * Constante que define el titulo del nombre del depositante
     */
    String DEPOSITANTE = "Nombre Depositante";

    /**
     * Constante que define el titulo del nombre de la cuenta del beneficiario
     */
    String NOMBRE_CUENTA_BEN = "Nombre Cuenta Beneficiario";

    /**
     * Constante que define el titulo del numero de la cuenta del beneficiario
     */
    String NUMERO_CUENTA_BEN = "Numero Cuenta Beneficiario";

    /**
     * Constante que define el tipo de movimiento de ENTREGA
     */
    String TIPO_MOVTO_E = "E";

    /**
     * Constante que define el tipo de movimiento de RECIBE
     */
    String TIPO_MOVTO_R = "R";

    String TLP_ENTREGA = "542";

    String TLP_RECEPCION = "540";

    String TCP_ENTREGA = "543";

    String TCP_RECEPCION = "541";

    String TLP_DESC_ENTREGA = "ENTREGA LIBRE PAGO";

    String TLP_DESC_RECEPCION = "RECEPCION LIBRE PAGO";

    String TCP_DESC_ENTREGA = "ENTREGA CONTRA PAGO";

    String TCP_DESC_RECEPCION = "RECEPCION CONTRA PAGO";

    String PENDIENTE_AUTORIZAR = "X";

    String NOTIFICADA = "N";

    String ESTATUS_EMISION_01 = "VIGENTE";

    String LIQ_PARCIAL = "Liquidacion Parcial";

    String LIQ_PARTIAL_VALUE = "PART";

    /**
     * Constante para definir el n&uacute;mero cero BigDecimal
     */
    BigDecimal BIG_DECIMAL_ZERO = BigDecimal.valueOf(0);

    /**
     * Anexando Constantes del UtilService
     */

    /**
     * variable para enviar la respuesta de exito en Posicion Valores
     */
    String EXITOPOSICION = "exitoposicion";
    String EXITOPOSICION_EXCEL = "exitoposicionExcel";
    String EXITOPOSICION_PDF = "exitoposicionPdf";
    String EXITOPOSICION_PRINT = "exitoposicionPrint";
    String EXITO_EDO_CTA_EFEC = "exitoEdoCtaEfectivo";
    /**
     * variable para enviar la respuesta de exito en Concentrado Aportaciones
     */
    String EXITOCONCENTRADO = "exitoconcentrado";
    String EXITOMP = "exitoMediosPagos";

    /**
     * Variable para consulta de confirmaci&oacute;n de Contrapago
     */
    String EXITOCONTRAPAGO = "exitoContrapago";

    String DET_LD_AMORT = "detLDAmort";
    String DET_LD = "detLD";

    /**
     * variable para enviar la respuesta de exito en Saldo Tres Cuentas
     */
    String SALDOTRESCUENTAS = "SaldoTresCuentas";

    /**
     * variable para inicializar en estado de cuenta de garantias
     */
    String EXITOINIT = "exitoInicializar";

    /**
     * variable para inicializar en Mercado Capitales captura traspasos
     */
    String EXITOTRASPASOS = "exitoInitTraspasos";

    /**
     * variable por inicializar en Confirmacion Contrapago de mercado de Capitales
     */
    String EXITOINITCONFCONTRA = "exitoInitContrapago";

    /**
     * variable para inicializar en Mercado Capitales captura traspasos
     */
    String EXITOCAPTURAT = "exitoCapturaTraspasos";


    /**
     * variable para limpiar estado de cuenta de Valores
     */
    String EXITOLIMPIAR = "limpiarEstadoCuentaValores";

    /**
     * variable para inicializar operaciones concertadas
     */
    String EXITOOPERACIONES = "exitoInitOperaciones";

    /**
     * variable para inicializar operaciones concertadas
     */
    String EXITOPRESTAMONETOSINC = "exitoInitPrestamos";

    String EXITO_EDO_CTA_CAP = "exitoEdoCtaCapitales";

    /**
     * variable para enviar la respuesta de exito
     */
    String EXITO = "exito";
    String EXITOLA = "listaAgentes";
    String EXITOLC = "listaCuentas";
    String EXITOLC_SILOPP = "listaCuentasSilopp";
    String EXITOLCBYN = "listaCuentasByNombre";
    String EXITOLE = "listaEmisiones";

    /**
     * variable para enviar la respuesta a la pagina de login
     */
    String RELOGIN = "relogin";

    String UNAUTORIZED = "unautorized";

    /**
     * variable para enviar la respuesta de error
     */
    String ERROR = "error";

    /**
     * variable para enviar la respuesta de error en las listas
     */
    String ERROR_LISTA = "errorLista";

    /**
     * variable para enviar la respuesta del caso de uso 1
     */
    String CASO_DE_USO_1 = "caso1";

    /**
     * variable para enviar la respuesta del caso de uso 2
     */
    String CASO_DE_USO_2 = "caso2";

    /**
     * variable para enviar la respuesta del caso de uso 3
     */
    String CASO_DE_USO_3 = "caso3";

    /**
     * variable para enviar la respuesta del caso de uso 1
     */
    String MD4301 = "estatus_operaciones";

    /**
     * variable para enviar la respuesta del caso de uso 2
     */
    String MD4302 = "captura_operaciones";

    /**
     * variable para enviar la respuesta del caso de uso 3
     */
    String MD4303 = "posicion_valores";

    /**
     * variable para enviar la respuesta del caso de uso 4
     */
    String MD4304 = "estado_cuenta";

    /**
     * variable para enviar la respuesta del caso de uso 1 de Garantias Banxico
     */
    String GB5301 = "captura_garantias";

    /**
     * variable para enviar la respuesta del caso de uso 2 de Garantias Banxico
     */
    String GB5302 = "posicion_garantias";

    /**
     * variable para enviar la respuesta del caso de uso 3 de Garantias Banxico
     */
    String GB5303 = "estatus_garantias";

    /**
     * variable para enviar la respuesta del caso de uso 4 de Garantias Banxico
     */
    String GB5304 = "lista_emisiones";

    /**
     * variable para enviar la respuesta de la carga de parametros de vuelta al caso de uso GB5303
     */
    String GB5305 = "carga_parametros";

    /**
     * variable para enviar la respuesta del caso de uso 2 de ValPre E
     */
    String VA6902 = "concertacion_prestamo";

    /**
     * variable para enviar la respuesta del caso de uso 3 de ValPre E
     */
    String VA6903 = "administracion_garantias";

    String MF01 = "apertura_misc_fiscal";
    String MF02 = "confimacion_aper_sistemas_dinero";
    String MF03 = "traspaso_misc_fiscal";
    String MF04 = "consulta_mov_misc_fiscal_merc_dinero";
    String MF05 = "traspaso_pantalla_misc_fiscal";
    String MF06 = "confirmacion_traspaso_misc_fiscal";

    String ADMIN_GARANTIAS = "administracionGarantias";
    String G6505 = "estado_cuenta_garantias";
    String C1159 = "estado_cuenta_valores";
    /**
     * variable para enviar la respuesta del caso de uso 12 de netos y operaciones
     */
    String cuCO5904 = "Asignacion_de_hechos";
    String NO6112 = "estado_cuenta_efectivo";
    String NO6110 = "operaciones_forzosas";
    String NO6113 = "detalle_operacion_forzosa";

    String TRASPASO_TIPO_CONTRA_PAGO = "TV";

    String TRASPASO_TIPO_LIBRE_PAGO = "TL";
    String TRASPASO_TIPO_ANTICIPADO = "TA";
    String TRASPASO_ESTADO_ENVIADO = "EN";
    String TRASPASO_ESTADO_RETENIDO = "RE";
    String TRASPASO_ESTADO_LQ = "LQ";

    String DEPOSITO_VALORES = "DEPO";
    String RETIRO_VALORES = "RETV";

    String MT_540 = "540";
    String MT_541 = "541";
    String MT_542 = "542";
    String MT_543 = "543";

    String DI5107 = "historicoRentaFija";
    String DI5108 = "historicoRentaVariable";
    /**
     * ADD OCDR
     */
    String MOSTRAR_CICLOS_LIQ = "mostrar_ciclos_liq";
    String ASIGNA_HECHOS_EXITO = "asigna_hechos_exito";
    String MOSTRAR_HECHOS_ERROR = "mostrar_hechos_error";
    String MOSTRAR_HECHOS_PDF = "mostrar_hechos_pdf";
    String MOSTRAR_HECHOS_XLS = "mostrar_hechos_xls";
    String HECHOS_EXITO = "exito_hecho";

    String OPEINCUMPLIDAS_EXITO = "exito_opeIncumplida";
    String MOSTRAR_OPEINCUMPLIDA_ERROR = "mostrar_opeIncumplida_error";
    String OPER_LIQ_FUTURO_EXITO = "exito_operLiqFuturo";
    /**
     * estado de cuenta unico
     */
    String EDO_CTA_UNICO_EXITO = "estadoCuentaUnico";
    String EDO_CTA_SOC_INV = "estadoCuentaSocInv";

    String MC4714 = "estado_cuenta_capitales";
    String MC4726 = "confirmacionTraspasoCapital";

    String COPIA = "copia";

    String OFF = "display:none;visibility:collapse;visibility:hidden;visibility:hide;";
    String ON = "display:block;visibility:visible;";

    String POS_VAL_SIMPLE = "posicion_valores_simple";
    String VENCIMIENTOS_PEND = "consulta_vencimientos_pendientes";

    String TRASPASO_DINERO = "confimacion_traspasos_dinero";

    String OPERACION_DIA_CAPITALES = "operaciones_dia_capitales";
    String OPERACION_DIA_DINERO = "operaciones_dia_dinero";
    String ARQUEO_VALORES = "arqueo_valores";
    String POS_VALOR_CONTROL = "posicionValorControl";
    /**
     * Mensaje de error para cuando el Hash y el iso no coinciden desde la pantalla
     */
    String ERROR_ISO_DIFERENTE = "EL c�digo Hash del iso generado y del iso firmado en pantalla son diferentes.";

    /* Constantes para estatus de Beneficiarios */
    long STATUS_BENEFICIARIO_REGISTRADO = 1;
    long STATUS_BENEFICIARIO_AUTORIZADO = 2;
    long STATUS_BENEFICIARIO_VENCIDO = 3;
    long STATUS_BENEFICIARIO_ACTUALIZADO = 4;
    long STATUS_BENEFICIARIO_CANCELADO = 5;
    long STATUS_BENEFICIARIO_ELIMINADO = 6;
    long STATUS_BENEFICIARIO_PRE_AUTORIZADO = 7;
    long STATUS_BENEFICIARIO_NO_EXISTE = 100;

    /* Constantes para custodios */
    long CLEARSTREAM = 1;
    long THE_BANK_OF_NEW_YORK_1 = 2;
    long THE_BANK_OF_NEW_YORK_2 = 3;
    long THE_BANK_OF_NEW_YORK_3 = 4;
    long EUROCLEAR_BANK = 5;
    long DEUSTCHE_BANK_1 = 6;
    long DEUSTCHE_BANK_2 = 7;
    long BANCO_BILBAO_VIZCAYA = 8;

    /* Constantes para los numeros de cuentas nombradas de los custodios */
    long CN_BANCO_BILBAO_VIZCAYA = 4033;
    long CN_CLEARSTREAM = 4030;
    long CN_DEUSTCHE_BANK = 4034;
    long CN_EUROCLEAR_BANK = 4031;
    long CN_JP_MORGAN = 4174;
    long CN_SANTANDER = 4035;
    long CN_THE_BANK_OF_NEW_YORK = 4032;
    long CN_BANK_OF_AMERICA = 95881;

    /* Constantes para tipos de Beneficiarios */
    long PERSONA_FISICA_NO_EUA = 1;
    long PERSONA_MORAL_NO_EUA = 2;
    long SIEFORE_AFORE = 3;
    long PERSONA_FISICA_EUA = 4;
    long INTERMEDIARIO_CALIFICADO_CRPR = 5;
    long INTERMEDIARIO_CALIFICADO_SRPR = 6;
    long INTERMEDIARIO_NO_CALIFICADO = 7;
    long SOCIEDAD_PARTNERSHIP = 8;
    long FIDEICOMISO_SIMPLE = 9;
    long FIDEICOMISO_COMPLEJO = 10;
    long PERSONA_MORAL_EUA = 11;
    long FIDEICOMISO_SIMPLE_W8IMY = 12;

    // DATOS ADICIONALES CAPTURA OPERACIONES SIC
    /**
     * Constante que define la llave del mapa de datos adicionales para fecha de liquidaci�n
     */
    String FECHA_LIQ_DA = "fechaLiquidacion";

    /**
     * Constante que define la llave del mapa de datos adicionales para fecha de Operaci�n
     */
    String FECHA_OP_DA = "fechaOperacion";

    /**
     * Constante que define la llave del mapa de datos adicionales para fecha de Notificaci�n
     */
    String FECHA_NOT_DA = "fechaNotificacion";

    /**
     * Constante que define la llave del mapa de datos adicionales para custodio
     */
    String CUSTODIO_DA = "custodio";

    /**
     * Constante que define la llave del mapa de datos adicionales para Cuenta Contraparte
     */
    String CUENTA_CONTRAPARTE_DA = "cuentaContraparte";

    /**
     * Constante que define la llave del mapa de datos adicionales para Descripci�n Contraparte
     */
    String DESC_CTA_CONTRAPARTE_DA = "descripcionContraparte";

    /**
     * Constante que define la llave del mapa de datos adicionales para Depositante
     */
    String DEPOSITANTE_DA = "depositante";

    /**
     * Constante que define la llave del mapa de datos adicionales para el Nombre de la Cuenta del Depositante
     */
    String NOM_CUENTA_BENEF_DA = "nomCuentaBeneficiario";

    /**
     * Constante que define la llave del mapa de datos adicionales para el N�mero de la Cuenta del Depositante
     */
    String NUM_CUNETA_BENEF_DA = "numCuentaBeneficiario";


    /**
     * Constante que define la llave del mapa de datos adicionales para Instrucciones Especiales de la Contraparte
     */
    String INSTRUCCIONES_ESP_DA = "instruccionesEsp";

    /**
     * Constante que define la llave del mapa de datos adicionales para Tipo Mensaje
     */
    String TIPO_MENSAJE_DA = "tipoMensaje";

    /**
     * Constante que define la llave del mapa de datos adicionales para Estatus
     */
    String ESTATUS_DA = "estatus";

    /**
     * Constante que define la llave del mapa de datos adicionales para Precio
     */
    String PRECIO_DA = "precio";

    /**
     * Constante que define la llave del mapa de datos adicionales para Divisa
     */
    String DIVISA_DA = "divisa";

    /**
     * Define estatus de operacion pendiente
     */
    long ST_OPER_PENDIENTE = 3l;

    /**
     * Define estatus de operacion pendiente de notificar
     */
    long ST_OPER_PENDIENTE_AUTORIZAR = 1l;

    /**
     * Define estatus de operacion notificada
     */
    long ST_OPER_NOTIFICADA = 2l;

    /**
     * Define estatus de operacion envio de cancelacion
     */
    long ST_OPER_ENVIO_CANCELACION = 10l;

    /**
     * Define estatus de operacion cancelada, esta cancelacion es debida a una cancelacion por parte del
     * custodio extranjero con un mensaje de cancelacion que procesa el SwiftDali.
     */
    long ST_OPER_CANCELADA = 11l;

    /**
     * Define estatus de operacion pendiente de liberar
     */
    long ST_OPER_PENDIENTE_LIBERAR = 8l;

    /**
     * Define estatus de operacion pendiente
     */
    long ST_OPER_MENSAJE_LIBERACION = 6l;

    /**
     * Define estatus de operacion envio de liberacion
     */
    long ST_OPER_ENVIO_LIBERACION = 7l;

    /**
     * Define estatus de operacion liberada
     */
    long ST_OPER_LIBERADA = 9l;

    /**
     * Define estatus de operacion confirmada
     */
    long ST_OPER_CONFIRMADA = 5l;

    /**
     * Define estatus de operacion enviada
     */
    long ST_OPER_ENVIADA = 4l;

    /**
     * Define estatus de operacion retenida
     */
    long ST_OPER_RETENIDA = 12l;

    /**
     * Define estatus de operacion habilitada
     */
    long ST_OPER_HABILITADA = 13l;

    /**
     * Define estatus de operacion en cancelacio sistema
     */
    long ST_OPER_CANCEL_SIST = 14l;

    /**
     * Define estatus de operacion en rechazo
     */
    long ST_OPER_EN_RECHAZO = 15l;

    /**
     * Define estatus de operacion rechazada
     */
    long ST_OPER_RECHAZADA = 16l;

    /**
     * Define estatus de operacion cancelada por usuario
     */
    long ST_OPER_CANCEL_USR = 17l;

    /**
     * Define estatus de operacion confirmacion parcial por usuario
     */
    long ST_OPER_CONFIRMADA_PARCIAL = 18l;

    /**
     * Define estatus de operacion liberacion parcial por usuario
     */
    long ST_OPER_LIBERADA_PARCIAL = 19l;

    /**
     * Define estatus de operacion remanente cancelado por usuario
     */
    long ST_OPER_REMANENTE_CANCELADO = 20l;

    /**
     * Define estatus de operacion pendiente de liberar
     */
    long ST_OPER_PENDIENTE_LIBERAR_PRCIAL = 22l;

    /**
     * Define estatus de operacion envio cancelacion remanente
     */
    long ST_OPER_ENVIO_CANCELACION_REMANENTE = 23l;

    /**
     * Define estatus de liquidaciones para las operaciones recibidas del PFI
     */
    String ST_MENSAJE_LIQUIDADO = "LIQ";

    /**
     * Define la descripcion para el estatus de liquidaciones para las operaciones recibidas del PFI
     */
    String DESC_ST_MENSAJE_LIQUIDADO = "LIQUIDADA";

    /**
     * Define estatus SATISFACTORIO del mensaje enviado al PFI
     */
    String ACKNOWLEGDE = "ACK";

    /**
     * Define estatus NO SATISFACTORIO del mensaje enviado al PFI
     */
    String NOT_ACKNOWLEGDE = "NAK";

    /**
     * Define estatus de error para las operaciones recibidas del PFI
     */
    String ST_MENSAJE_ERROR = "ERR";

    /**
     * Define estatus que se asigna a la operacion cuando hay faltante de valores
     */
    String ST_FALTANTE_VALORES = "FV";

    /**
     * Define estatus de pendiente para las operaciones recibidas del PFI
     */
    String CLAVE_MENSAJE_FALTANTE_VALORES = "Pendiente";

    /**
     * Cadena que forma parte del id de proceso para la transferencia sic de archivos
     */
    String ID_PROCESO_FILE_TRANSFER_INTERNACIONAL = "TI";

    /**
     * Cadena que forma parte del id de proceso para la transferencia de archivos
     */
    String ID_PROCESO_FILE_TRANSFER_STATEMENT = "ST";

    /**
     * ID del tipo de Operacion TLP
     */
    String TIPO_OPER_TLP = "T";

    /**
     * ID que refiere el origen como Division Internacional
     */
    String ID_ORIGEN_DIV_INTERNACIONAL = "DIVINT";

    /**
     * ID que refiere el origen como portal
     */
    String ID_ORIGEN_PORTAL = "PORTAL";

    /**
     * ID que refiere el origen como Division Internacional hacia el MAV
     */
    String ID_ORIGEN_DIV_INTERNACIONAL_MAV = "MOI";

    /**
     * ID de la secuencia
     */
    String SEQ_FOLIO_CONTROL = "SEQ_SLV_FOLIADOR";

    /**
     * Define la secuencia de los mensajes
     */
    String SEQ_REFERENCIA_MENSAJE = "SEQ_REFERENCIA_MENSAJE";

    /**
     * Define el estatus de cupon vigente
     */
    long ESTATUS_CUPON_VIGENTE = 1l;

    /**
     * Define todas las opciones de un criterio de busqueda
     */
    String OPCION_TODAS_CRITERIO = "TODAS";

    /**
     * Define todos las resultados de un criterio de busqueda
     */
    String OPCION_TODOS_CRITERIO = "TODOS";

    /**
     * Define la descripcion del  movimiento de entrega
     */
    String MOVTO_ENTREGA = "Entrega";

    /**
     * Define la descripcion del  movimiento de recepcion
     */
    String MOVTO_RECIBE = "Recibe";

    /**
     * Define la descripcion de un tipo de operacion de contrapago
     */
    String DESC_TRASPASO_CONTRA = "Operaci\u00F3n Contrapago";

    /**
     * Define la descripcion del un tipo de operacion libre de pago
     */
    String DESC_TRASPASO_LIBRE = "Operaci\u00F3n Libre de Pago";

    /**
     * Formato predeterminado dd/MM/yyyy
     */
    String FORMATO_DMA = "dd/MM/yyyy";

    /**
     * Nafinsa
     */
    String NAFINSA = "02022";

    /**
     * Vitro
     */
    String VITRO = "020228345";

    /**
     * Alta1 de Nafinsa
     */
    String ALTA1NAFINSA = "ADCP";

    /**
     * Alta2 de Nafinsa
     */
    String ALTA2NAFINSA = "CPOS";

    /**
     * Alta3 de Nafinsa
     */
    String ALTA3NAFINSA = "VI%";

    /**
     * Banamex
     */
    String BANAMEX = "02061";

    /**
     * Alta1 de Banamex
     */
    String ALTA1BANAMEX = "BADC";

    /**
     * Alta2 de Banamex
     */
    String ALTA2BANAMEX = "BCPO";

    /**
     * Inbursa
     */
    String INBURSA = "02021";

    /**
     * Alta1 de Inbursa
     */
    String ALTA1INBURSA = "IADC";

    /**
     * Alta2 de Inbursa
     */
    String ALTA2INBURSA = "ICPO";

    /**
     * Cuenta 54
     */
    String CUENTA_54 = "54";

    /**
     * Constante para etiquetar el valor nominal
     **/
    String VALOR_NOMINAL = "valorNominal";

    /**
     * Constante para etiquetar la divisa del valor nominal
     **/
    String DIVISA_VALOR_NOMINAL = "divisaValorNominal";

    /**
     * Constante para etiquetar la unidad de deposito
     **/
    String UNIDAD_DEPOSITO = "unidadDeposito";

    /**
     * Constante para etiquetar el isin para DIVINMAV
     **/
    String ISIN_DIVINMAV = "isin";

    /**
     * Constante para etiquetar el tipo de ingreso portal de la operacion
     */
    String OPERACION_INGRESADA_POR_PORTAL = "Portal";

    /**
     * Constante para etiquetar el tipo de ingreso PFI de la operacion
     */
    String OPERACION_INGRESADA_POR_PFI = "PFI";

    //Altas para comparacion de tope y circulante de fideicomiso
    /**
     * Constante que define la alta CPOS
     */
    String CPOS_VALUE = "CPOS";

    /**
     * Constante que define la alta ADCP
     */
    String ADCP_VALUE = "ADCP";

    /**
     * Constante que define la alta IADC
     */
    String IADC_VALUE = "IADC";

    /**
     * Constante que define la alta BADC
     */
    String BADC_VALUE = "BADC";

    /**
     * Constante que define la alta VIVI
     */
    String VIVI_VALUE = "VIVI";

    /**
     * Constante del tipo valor de las emisiones del SIC
     */
    String TIPO_VALOR_SIC = "1A";

    /**
     * Constante del tipo valor de las emisiones del SIC Indice
     */
    String TIPO_VALOR_SIC_INDICE = "1I";

    /**
     * Constante que indica la serie para las emisiones del SIC de EUA
     */
    String SERIE_SIC_EUA = "*";

    /**
     * Constante que define la alta GADC
     */
    String GADC_VALUE = "GADC";

    /**
     * Pesos mexicanos
     */
    String DIVISA_PESOS_MEXICANOS = "MXN";

    /**
     * Clave de usuario/aplicaci&oacute;n Divis&oacute;n Internacional
     */
    String APLICACION_DIVINT = "DIVINT";

    /**
     * Clave de estatus "vigente" para los registros de custodios/depositantes
     */
    String ESTATUS_REGISTRO_VIGENTE = "VIGENTE";

    /**
     * Clave de estatus "cancelado" para los registros de custodios/depositantes
     */
    String ESTATUS_REGISTRO_CANCELADO = "CANCELADO";

    /**
     * Valor para colocar activo al Custodio en alta
     **/
    Integer CUSTODIO_ACTIVO = 1;

    /**
     * Estatus de Beneficiarios
     */

    String BEN_CADENA_VACIA = "-----";

    String BEN_POSTURA_BENEFICIARIO = "B";
    String BEN_POSTURA_INTERMEDIARIO = "I";

    String BEN_TIPO_ENTIDAD_PFISICA = "I";
    String BEN_TIPO_ENTIDAD_PMORAL = "C";
    String BEN_TIPO_ENTIDAD_PMORAL_BFINAL = "B";
    String BEN_TIPO_ENTIDAD_PMORAL_INTER_SIMPLE = "F";
    String BEN_TIPO_ENTIDAD_PMORAL_INTER_CALIF = "Q";

    String BIC_CODE_CHILE = "DCVVCLRMXXX";

    /**
     * Constantes para derechos internacionales
     */

    int SIZE_ID_FOLIO = 5;
    int SIZE_ID = 2;

    Integer ID_ESTADUS_DERECHO_INT_CORTADO = Integer.valueOf(1);
    String KEY_DERECHO = "derecho";
    String KEY_DERECHO_SELECCIONADO = "derechoSeleccionado";
    String ROL_INT_BEN_INDEVAL = "INT_BEN_INDEVAL";
    String ROL_INT_BEN_INDEVAL_ADMIN = "INT_BEN_INDEVAL_ADMIN";
    String ROL_INT_BEN_CAPT_CONS = "INT_BEN_CAPT_CONS";
    String ROLINT_BEN_CONS = "INT_BEN_CONS";
    String ROL_INT_SIC_MILA_ADMIN = "INT_SIC_MILA_ADMIN";
    String ROL_INT_SIC = "INT_AUTORIZA_SIC_RCP";

    String ROL_INT_LIBERA_MOV_EFEC = "INDEVAL";//"INT_LIBERA_MOV_EFE"; //Libera Movimientos de Efectivo
    String ROL_INT_CANCELA_MOV_EFEC = "INDEVAL";// "INT_CANCELA_MOV_EFEC";  //Cancela Movimientos de Efectivo en Divisas

    String ROL_INT_AUTORIZA_HORA_CUST = "INDEVAL";//"INT_AUTORIZA_HORA_CUST"; //Permite las operaciones en horarios de Custodios
    String TO_CONSULTA_TABLA_RETENCION = "consultaTablaRetencion";
    String ID_BENEFICIARIO_DERECHO = "idBeneficiarioDerecho";
    String KEY_ID_DERECHO = "idDerecho";
    String KEY_ID_ESTATUS_DERECHO = "idEstatusDerecho";
    String KEY_ID_CUENTA_NOMBRADA = "idCuentaNombrada";
    String KEY_POSICICION_NO_ASIGNADA = "posicionNoAsignada";
    String KEY_PORCENTAJE_DEFAULT = "porcentajeDefault";
    String KEY_ID_EMISION = "idEmision";
    String KEY_INDEX = "index";
    String STRING_SCAPE_NEW_LINE = "\n";
    String STRING_SCAPE_TAB = "\t";

    String KEY_TIPO_VALOR = "tipoValor";
    String KEY_EMISORA = "emisora";
    String KEY_SERIE = "serie";
    String KEY_FECHA_CORTE = "fechaCorte";
    String KEY_CLAVE_INSTITUCION = "claveInstitucion";
    String KEY_CUENTA = "cuenta";

    Integer ID_ESTATUS_DERECHO_CORTADO = Integer.valueOf(1);
    Integer ID_ESTATUS_DERECHO_PREVIO = Integer.valueOf(2);
    Integer ID_ESTATUS_DERECHO_AUTORIZADO = Integer.valueOf(3);
    Integer ID_ESTATUS_DERECHO_CONFIRMADO = Integer.valueOf(4);
    Integer ID_ESTATUS_DERECHO_PAGADO = Integer.valueOf(5);
    String ESTATUS_DERECHO_PAGADO = "PAGADO";
    int ID_ESTATUS_DERECHO_CORTADO_MAV = 6;

    String TO_AGREGAR_EMISION_SIN_BENEF = "agregarEmisionSinBenef";
    String TO_ACTUALIZAR_EMISION_SIN_BENEF = "actualizarEmisionSinBenef";
    String TO_AGREGAR_HORARIO = "agregarHorario";
    String TO_ACTUALIZAR_HORARIO = "actualizarHorario";
    String KEY_EMISION_SELECCIONADA = "selectedEmision";
    String KEY_HORARIO_SELECCIONADO = "selectedHorario";

    int ID_DERECHO_DIVIDENDO_EN_EFECTIVO = 1;
    int ID_DERECHO_DISTRIBUCION = 18;

    DecimalFormat DECIMAL_FORMAT_PORCENTAJE_RET = new DecimalFormat("#0.0#####");
    DecimalFormat DECIMAL_FORMAT_PORCENTAJE_VIEW = new DecimalFormat("##0.######");
    DecimalFormat DECIMAL_FORMAT_FEE = new DecimalFormat("##0.0#####");
    DecimalFormat DECIMAL_FORMAT_PROPORCION = new DecimalFormat("##0.0############");
    int PRECICION_PROPORCION = 13;
    int SIZE_OF_UOI = 50;
    int SIZE_OF_POSICION = 18;
    int CIEN_PORCENTAJE = 100;

    BigDecimal CIEN_BIGDECIMAL = new BigDecimal(CIEN_PORCENTAJE);

    int ID_EMISION_EXTRANJERA = 1;
    String COUNTRY_W9 = "USA";

    Integer CALENDARIO_DERECHOS_REGISTRADO = 1;
    Integer CALENDARIO_DERECHOS_AUTORIZADO = 2;
    Integer CALENDARIO_DERECHOS_PREVIO = 3;
    Integer CALENDARIO_DERECHOS_CONFIRMADO = 4;
    Integer CALENDARIO_DERECHOS_PRELIQUIDADO = 5;
    Integer CALENDARIO_DERECHOS_LIQUIDADO = 6;
    Integer CALENDARIO_DERECHOS_CANCELADO = 7;
    Integer CALENDARIO_DERECHOS_REVERSAL = 8;
    Integer CALENDARIO_DERECHOS_CORREGIDO = 9;
    Integer CALENDARIO_DERECHOS_SUSPENDIDO = 10;


    /*Datos Nuevos paran pfi*/

    String Instrucciones_Especiales = "InstruccionesEspeciales";
    String Descripcion_Beneficiario_Final = "DescripcionBeneficiarioFinal";
    String Descripcion_Cuenta_Contraparte = "DescripcionCuentaContraparte";
    String Cuenta_Contraparte = "CuentaContraparte";
    String Lugar_De_Liquidacion = "LugarDeLiquidacion";
    String Custodio = "Custodio";
    String Cuenta_Beneficiario_Final = "CuentaBeneficiarioFinal";

    int noRegistrosxPag = 500;


    int VIGENCIA_ANIOS_FORMATO_W8BEN = 3;

    /*porcentaje de Retenci&oacute;n*/
    String PATRON_PORCENTAJE_RETENCION = "^(?!\\.?$)\\d{0,3}(\\.\\d{0,2})?$";

    Double VALOR_MAXIMO_PORCENTAJE_RETENCION = 100.00;

    Double VALOR_MINIMO_PORCENTAJE_RETENCION = 0.00;

    /*Datos Multicarga Internacional ADP*/

    String PATRON_UOI = "[\\-\\_\\/\\.a-zA-Z�-�0-9\\\\u00f1\\\\u00d1\\p{Z}��]+";

    String PATRON_ADP = "[a-zA-Z0-9\\-]*";

    String UOI_NULO = "UOI nulo";

    String ADP_NULO = "ADP nulo";

    String INSTITUCION_ORIGEN_NULO = "La Instituci�n Origen tiene valor nulo";

    String FORMATO_UOI_INCORRECTO = "UOI con Formato Incorrecto";

    String FORMATO_ADP_INCORRECTO = "ADP con Formato Incorrecto";

    String FORMATO_INSTITUCION_ORIGEN_INCORRECTO = "Instituci�n Origen con Formato Incorrecto";

    String UOI_SIN_BENEFICIARIO = "UOI no existe";

    String VALOR_NULO = "Valor nulo";

    String REGISTRO_REPETIDO = "Repetido en archivo";

    String CUSTODIO_INVALIDO = "Custodio inv�lido";

    String UOI_CON_ADP = "UOI ya cuenta con ADP";

    String UOI_NO_AUTORIZADO = "UOI No autorizado";

    String UOI_NO_EN_INSTITUCION = "UOI no pertenece a Instituci�n Origen";

    String INSTITUCION_ORIGEN_NO_ENCONTRADA = "No existe la instituci�n Origen";

    String UOI_YA_CUENTA_CON_INSTITUCION = "UOI ya cuenta con la instituci�n destino";

    String DEUTSCHE = "DEUTSCHE BANK AG, NY";

    int LONGITUD_MAXIMA_VALOR_UOI = 50;

    int LONGITUD_MINIMA_VALOR_UOI = 9;

    int LONGITUD_MAXIMA_VALOR_ADP = 20;

    //int LONGITUD_MINIMA_VALOR_ADP = 8;
    int LONGITUD_MINIMA_VALOR_ADP = 1;

    int LONGITUD_INSTITUCION = 5;

    /* Constantes para estatus de multicarga */
    long STATUS_MULTICARGA_PENDIENTE_DE_AUTORIZAR = 1;

    long STATUS_MULTICARGA_AUTORIZADO = 2;

    long STATUS_MULTICARGA_ELIMINADO = 3;

    long STATUS_MULTICARGA_AUTORIZADO_CON_ERROR = 4;
    /* Constantes para tipo de operacion de multicarga */

    long OPERACION_MULTICARGA_ADP = 1;

    long OPERACION_MULTICARGA_MULTIEMPRESA = 2;

    /* Constantes para FileTransfer Captura Beneficiarios*/

    long FORMATO_W9 = 1;

    long FORMATO_W8BEN2014 = 2;

    long FORMATO_W8BENE = 3;

    long FORMATO_W8IMY = 4;

    String FORMATO_W9_CADENA = "1";

    int LONGITUD_TIPO_FORMATO = 2;

    int LONGITUD_TIPO_FORMATO_W8BEN = 4;

    int LONGITUD_TIPO_BENEFICIARIO = 5;

    int LONGITUD_TIPO_BENEFICIARIO_W8BEN = 6;

    int LONGITUD_CLAVE_CUSTODIO = 4;

    int LONGITUD_FECHA_FORMATO = 11;

    int LONGITUD_FECHA_FORMATO_2 = 10;

    int LONGITUD_ESTADO_ACTIVO = 6;

    int LONGITUD_ESTADO_INACTIVO = 8;

    int LONGITUD_MAXIMA_NAME = 116;

    int LONGITUD_MAXIMA_BUSINESS_NAME = 116;

    int LONGITUD_MAXIMA_CLASIFICATION = 60;

    int LONGITUD_MAXIMA_CODE_EXEMPT_PAYEE = 2;

    int LONGITUD_CODE_FATCA = 2;

    //public static final int LONGITUD_MAXIMA_STREET = 45;

    //public static final int LONGITUD_MAXIMA_OUTER_NUMBER = 10;

    //public static final int LONGITUD_MAXIMA_INTERIOR_NUMBER = 10;
    int LONGITUD_MAXIMA_DIRECCION = 65;

    //public static final int LONGITUD_MAXIMA_ZIP_CODE = 10;

    //public static final int LONGITUD_MAXIMA_CITY = 30;

    //public static final int LONGITUD_MAXIMA_STATE = 30;

    int LONGITUD_MAXIMA_REQUESTER_NAME_AND_ADDRESS = 148;

    int LONGITUD_MAXIMA_LIST_ACCOUND_NUMBER = 103;

    int LONGITUD_SSN = 11;

    int LONGITUD_EMPLOYER_IDENTIFICATION_NUMBER = 10;

    String PATRON_SSN = "\\d{3}\\-\\d{2}\\-\\d{4}";

    String PATRON_EMPLOYED_IDENTIFICATION_NUMBER = "\\d{2}\\-\\d{7}";

    /**
     * Constante que define el patron de una fecha beneficiarios
     */
    String PATRON_FECHA_FORMATO = "[A-Za-z]{3}\\-\\d{2}\\-\\d{4}";

    String PATRON_PORCENTAJE = "\\d{2}\\.\\d{1}|\\d{2}";

    int MENSAJE_CAMPO_NULO = 1;

    int MENSAJE_FORMATO_INCORRECTO = 2;

    int MENSAJE_CAMPO_INVALIDO = 3;

    int MENSAJE_LONGITUD_INVALIDO = 4;

    int MENSAJE_INHABILITADO = 5;


    /**
     * NOMBRE DE LOS CAMPOS QUE CONTIENE EL FILETRANSFER CAPTURA BENEFICIARIOS W9
     */
    String W9_TIPO_FORMATO = " Tipo Formato ";

    String W9_TIPO_BENEFICIARIO = " Tipo Beneficiario ";

    String W9_CUSTODIO = " Custodio ";

    String W9_ID_INSTITUCION = " Id Instituci\u00F3n ";

    String W9_FECHA_FORMATO = " Fecha Formato ";

    String W9_ESTADO = " Estado ";

    String W9_NAME = " Name ";

    String W9_BUSINESS_NAME = " Business Name ";

    String W9_CLASIFICATION = " Clasification ";

    String W9_EXEMPT_PAYEE_CODE = " Exempt payee code ";

    String W9_EXEMPTION_FATCA_CODE = " Exemption from FATCA reporting code ";

    String W9_STREET = " Street ";

    String W9_OUTER_NUMBER = " Outer Number ";

    String W9_INTERIOR_NUMBER = " Interior Number ";

    String W9_ZIP_CODE = " ZIP Code ";

    String W9_STREER_OUTER_NUMBER_INTERIOR = "Street, Outer Number e Interior Number";

    String W9_CITY_STATE_ZIP_CODE = "Zip Code, City y State";

    String W9_CITY = " City ";

    String W9_STATE = " State ";

    String W9_REQUESTER_NAME_AND_ADDRESS = " Requesters name and address ";

    String W9_LIST_ACCOUNT_NUMBER = " List account number(s) ";

    String W9_SECURITY_NUMBER = "Security number";

    String W9_EMPLOYER_ID_NUMBER = " Employer identification number ";

    //////////////////////////////////////

    String W9_INDIVIDUAL = "INDIVIDUAL";

    String W9_NO_APLICA = "NA";

    ////////////////////////////////////

    long INDIVIDUAL = 1;

    long OTHER = 5;

    long LLIMITED_LIABILITY_COMPANY = 4;

    String BENEFICIARIO_REPETIDO = "Error: Beneficiario Repetido";


    int W8BEN2014_LONGITUD_Name = 55;

    int W8BEN2014_LONGITUD_Ape_paterno = 55;

    int W8BEN2014_LONGITUD_Ape_materno = 55;

    int W8BEN2014_LONGITUD_TIPO_FORMATO = 5;

    int W8BEN2014_LONGITUD_RESIDENCIA = 25;

    int W8BEN2014_LONGITUD_article = 10;

    int W8BEN2014_LONGITUD_rate_withholding = 4;

    int W8BEN2014_LONGITUD_type_of_income_reasons = 69;

    int W8BEN2014_LONGITUD_reasons = 69;

    int W8BEN2014_LONGITUD_reference_number = 30;

    int W8BEN2014_LONGITUD_Foreign_tax_payer_ID = 22;


    int W8BEN2014_LONGITUD_Street = 83;

    int W8BEN2014_LONGITUD_OutNumber = 5;

    int W8BEN2014_LONGITUD_IntNumber = 5;

    int W8BEN2014_LONGITUD_PostalCode = 10;

    int W8BEN2014_LONGITUD_City = 27;

    int W8BEN2014_LONGITUD_State = 27;

    int W8BEN2014_LONGITUD_Country = 19;


    String W8BEN2014_apellido_paterno = "primary last name";

    String W8BEN2014_apellido_materno = "second last name";

    String W8BEN2014_RESIDENCE_COUNTRY = "Country Address";

    String W8BEN2014_Country_of_citizenship = "Country of citizenship";


    String W8BEN2014_STREET_MA = " Street MA";

    String W8BEN2014_OUTER_NUMBER_MA = " Outer Number MA";

    String W8BEN2014_INTERIOR_NUMBER_MA = " Interior Number MA";

    String W8BEN2014_ZIP_CODE_MA = " ZIP Code MA";

    String W8BEN2014_CITY_MA = " City MA";

    String W8BEN2014_STATE_MA = " State MA";

    String W8BEN2014_COUNTRY_MA = "Country MA";

    String W8BEN2014_Foreign_tax_payer_ID = "Foreign tax payer ID";

    String W8BEN2014_reference_number = "reference number";

    String W8BEN2014_date_of_birth = "date of birth";

    String W8BEN2014_article = "article";

    String W8BEN2014_rate_withholding = "rate with holding";

    String W8BEN2014_type_of_income_reasons = "type of income";

    String W8BEN2014_name_of_signer = "name of signer";

    String W8BEN2014_capacity_whichacting = "capacity whichacting";

    String W8BEN2014_NA_MA = " Street, Outer Number e Interior Number MA ";


    String W8BEN2014_country_of_residence = "country of residence";

    String W8BEN2014_reasons = "reasons";


    //------------------Evento Corporativo----------------------

    String TIPO_EVCORP_MANDATORIO = "MAN";
    String TIPO_EVCORP_OPCIONAL = "OPC";

    String TIPO_EVCORP_MANDATORIO_DESC = "MANDATORIO";
    String TIPO_EVCORP_OPCIONAL_DESC = "OPCIONAL";

    String TIPO_EVCORP_CLAVE_MERCADO_ORIGEN = "MO";
    String TIPO_EVCORP_CLAVE_MERCADO_LOCAL = "ML";

    String OPCION_PREFIX = "opc_";
    String NOTA_PREFIX = "not_";
    String ADJUNTOS_PREFIX = "adj_";
    String NOTIFICACIONES_PREFIX = "noti_";
    String VALIDACIONES_PREFIX = "vali_";

    String EVCO_AGREGAR_ACCION = "AG";
    String EVCO_MODIFICAR_ACCION = "MO";
    String EVCO_BORRAR_ACCION = "BO";

    Long ESTADO_LIQUIDADO_EVCO = 6l;

    //------------------Fin  Evento Corporativo----------------------
    int MAX_REGISTROS_EXPORTAR = 50000;

    String AUTORIZADOR_SIC = "INT_SIC_AUTORIZAR";
    String OPERADOR_SIC = "INT_SIC_OPERADOR";
    //String EXPRESION_REGULAR_CAMBIO_OPERACION_SIC = "([0-9])+(,?([0-9]))+";
    String EXPRESION_REGULAR_CAMBIO_OPERACION_SIC = "([0-9])+";

    /**
     * Estatus de File Transfer de Cambio de B&oacute;veda.
     */
    String ESTATUS_CREADO = "CREADO";
    String ESTATUS_PROCESADO = "PROCESADO";
    String ESTATUS_CANCELADO = "CANCELADO";

    int TIPO_BOVEDA_VALORES_INT = 3;

    /**
     * Lista blanca de estatus de operacion SIC para cambio de boveda
     */
    Long[] ESTATUS_PERMITIDOS_OPER_SIC = new Long[]{
            ST_OPER_LIBERADA, ST_OPER_CANCELADA, ST_OPER_CANCEL_SIST, ST_OPER_RECHAZADA, ST_OPER_CANCEL_USR
    };


    // ---- Mensajes para envio de correo: Tabla de Retencion del Derecho

    String SUBJECT = "Tabla de Retencion ";
    String MENSAJE = "Se adjunta el reporte con la Tabla de Retencion del Derecho: ";
    String CODIFICACION = "UTF-8";
    String ETQ_INI_STRONG = "<strong>";
    String ETQ_FIN_STRONG = "</strong>";
    String SALTO_LINEA = "<br />";
    String FECHA_CORTE = "Fecha de Corte";
    String FECHA_PAGO = "Fecha de Pago";

    // ---- Mensaje para correo al pagar un derecho
    String MENSAJE_DERECHO_PAGADO = "Se puede liquidar el derecho con id ";

    // ---- Constantes para validaciones en formatos W8BENE
    String PART_X = "L";
    String PART_XI = "M";
    String PART_XII = "N";
    String PART_XIV = "P";
    String PART_XV = "Q";
    String PART_XXIII = "Y";
    String PART_XXVI = "AB";
    String CAMPO_YES = "YES";

    // -- Mensaje para validar abreviacion de Custodios
    String ABREVIACION_CUSTODIO = " Abreviacion de custodio ";
    String REGEXP = ".*[.].*";
    ;

    // -- Constantes para Tipo Valor
    String TIPO_VALOR_D4 = "D4";
    String TIPO_VALOR_D5 = "D5";
    String TIPO_VALOR_D6 = "D6";

    Double VALOR_MAXIMO_TITULOS = new Double("500000");

    // -- Valores para asignar Beneficiarios Derecho
    Long SIZE_FT_BENEFICIARIO = 10240L;
    Long SIZE_BENEFICIARIO_DERECHO = 10L;
    Integer TAMANIO_KB = 1024;

    String CLAVE_WORKFLOW_ACTUALIZA_CUENTA_EF = "DIVINT_ACT_CUEF";
    String CLAVE_WORKFLOW_INSERTA_CUENTA_EF = "DIVINT_INS_CUEF";

    String CLAVE_WORKFLOW_CANCELA_MOV_EF = "DIVINT_CANC_MOEF";

    String CLAVE_TIPO_CUSTODIA_BOVEDA_EFECTIVO = "E";

    Integer ID_TIPO_BOVEDA_EFECTIVO_INTERNACIONAL = 2;

    Long ESTADO_MOVIMIENTO_PENDIENTE = 1L;
    Long ESTADO_MOVIMIENTO_CANCELADO = 5L;

    Long ESTADO_LIQ_INDEVAL_REGISTRADO = 1L;
    Long ESTADO_LIQ_INDEVAL_CANCELADO = 3L;
    Long ESTADO_LIQ_INDEVAL_ERROR = 4L;


    Long ID_ESTADO_MOVIMIENTO_EFECTIVO_REGISTRADO = 1L;
    Long ID_ESTADO_MOVIMIENTO_EFECTIVO_RETENIDO = 2L;
    Long ID_ESTADO_MOVIMIENTO_EFECTIVO_AUTORIZADO = 3L;
    Long ID_ESTADO_MOVIMIENTO_EFECTIVO_LIBERADO = 4L;
    Long ID_ESTADO_MOVIMIENTO_EFECTIVO_APLICADO = 5L;
    Long ID_ESTADO_MOVIMIENTO_EFECTIVO_CANCELADO = 6L;

    int ID_ESTADO_MOVIMIENTO_EFECTIVO_LIBERADO_INT = 4;
    int ID_ESTADO_MOVIMIENTO_EFECTIVO_CANCELADO_INT = 6;

    int TIPO_MOVIMIENTO_EFECTIVO_LIBERAR_SELECCION = 10;
    int TIPO_MOVIMIENTO_EFECTIVO_LIBERA = 11;
    int TIPO_MOVIMIENTO_EFECTIVO_CANCELAR_SELECCION = 20;
    int TIPO_MOVIMIENTO_EFECTIVO_CANCELA = 21;


    int TIPO_CANCELA_SELECCION = 20;
    int TIPO_CANCELA = 21;
    int TIPO_AUTORIZA_SELECCION = 30;
    int TIPO_AUTORIZA = 31;

    Integer ID_ESTADO_HORARIO_CUSTODIO_REGISTRADO = 1;
    Integer ID_ESTADO_HORARIO_CUSTODIO_AUTORIZADO = 2;
    Integer ID_ESTADO_HORARIO_CUSTODIO_CANCELADO = 3;

    String DESC_ESTADO_HORARIO_CUSTODIO_REGISTRADO = "REGISTRADO";
    String DESC_ESTADO_HORARIO_CUSTODIO_AUTORIZADO = "AUTORIZADO";
    String DESC_ESTADO_HORARIO_CUSTODIO_CANCELADO = "CANCELADO";


    String DESC_ESTADO_MOVIMIENTO_EFECTIVO_AUTORIZADO = "AUTORIZADO";
    String DESC_ESTADO_MOVIMIENTO_EFECTIVO_LIBERADO = "LIBERADO";
    String DESC_ESTADO_MOVIMIENTO_EFECTIVO_REGISTRADO = "REGISTRADO";
    String DESC_ESTADO_MOVIMIENTO_EFECTIVO_RETENIDO = "RETENIDO";
    String DESC_ESTADO_MOVIMIENTO_EFECTIVO_APLICADO = "APLICADO";
    String DESC_ESTADO_MOVIMIENTO_EFECTIVO_CANCELADO = "CANCELADO";

    String PREFIJO_ID_RETIRO = "RE";
    String PREFIJO_ID_DEPOSITO = "DE";

    String DESC_RETIRO = "RETIRO";
    String DESC_DEPOSITO = "DEPOSITO";

    String MENSAJE_FACULTAD_FIRMA_DIGITAL_REQUERIDA = "Usted No tiene habilitada la facultad de firma digital.";
    String MENSAJE_USUARIO_INVALIDO_CANCELACION = "S\u00F3lo quien Autoriz\u00F3 el movimiento con folio control %d tiene facultad para Cancelarlo.";
    String MENSAJE_USUARIO_INVALIDO_AUTORIZACION = "Usted Registr\u00F3 el movimiento con folio control %d por lo que No tiene facultad para Autorizarlo.";
    String MENSAJE_USUARIO_INVALIDO_LIBERACION = "Usted Registr\u00F3 o Autoriz\u00F3 el movimiento con folio control %d por lo que No tiene facultad para Liberarlo.";
    String MENSAJE_USUARIO_INVALIDO_REGISTRO = "Usted No tiene facultad para registrar el movimiento con folio control ";

    String MENSAJE_OPERACION_INVALIDA = "La operaci\u00F3n que desea realizar es desconocida";

    String MENSAJE_MOVIMIENTO_INVALIDO_CANCELACION = "El movimiento con folio control %d no puede ser Cancelarlo.";
    String MENSAJE_MOVIMIENTO_INVALIDO_LIBERACION = "El movimiento con folio control %d no puede ser Liberado.";
    // ##### Constantes para uso de BitacoraService
    String USUARIO_DECONOCIDO = "Desconocido";
    Integer ID_MODULO_MOV_EFE_DIV_EXT = 11;

    Integer ID_OP_TR_REGISTRO_MOV_EFE_DIV_EXT = 1101;
    Integer ID_OP_TR_AUTORIZACION_MOV_EFE_DIV_EXT = 1102;
    Integer ID_OP_TR_LIBERACION_MOV_EFE_DIV_EXT = 1103;
    Integer ID_OP_TR_CANCELACION_MOV_EFE_DIV_EXT = 1104;

    String PREFIJO_CADENA_FIRMADA = "{SHA1withRSA}";

    public static final String CLAVE_WORKFLOW_ALTA_CUSTODIO_CSDR = "DIVINT_ALTA_CSDR";
    public static final String CLAVE_WORKFLOW_EDICION_CUSTODIO_CSDR = "DIVINT_EDIT_CSDR";
    public static final String CLAVE_WORKFLOW_ELIMINAR_CUSTODIO_CSDR = "DIVINT_DEL_CSDR";

    public static final String ESTATUS_SOLICITUD_PENDIENTE = "PE";
    public static final String ESTATUS_SOLICITUD_AUTORIZADO = "AU";

    String PARTIAL_SETTLEMENT = "PARTIAL_SETTLEMENT";

    public static final String MT_103 = "103";
    public static final String MT_202 = "202";

    public static final String PATRON_BIGDECIMAL = "^-{0,1}\\d+(\\.\\d{1,})?$";
    //"\\d(\\.\\d{1,3})?$";

    public static final Long FILE_TRANSFER_ERROR = 12L;
    public static final Long FILE_TRANSFER_CARGANDO = 6L;
    public static final Long FILE_TRANSFER_REGISTRADO = 0L;
    public static final Long FILE_TRANSFER_AUTORIZADO = 1L;

    public static final String FILE_TRANSFER_TIPO_PROCESO = "MD";

}
