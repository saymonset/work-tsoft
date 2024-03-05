/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.util.util;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Clase que contiene constantes comunes.
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface Constantes {

	/** Constante para String 0 */
	String CERO_STR = "0";

	/** Define Constante de cadena 0 */
	String CERO_STRING = "0";

	/** Define Constante de cadena 1 */
	String UNO_STRING = "1";

	/** Define Constante de cadena 2 */
	String DOS_STRING = "2";

	/** Constante para referir al valor cero (0) */
	int CERO = 0;

	/** Define Constante int para 0 */
	int CERO_INT = 0;

	/** Define Constante para 1 */
	int UNO_INT = 1;

	/** Constante para el primitivo int = 1 */
	int UNO = 1;

	/** Define Constante int de 2 */
	int DOS_INT = 2;

	/** Define Constante de int para 3 */
	int TRES_INT = 3;

	/** Constante para el primitivo int = 3 */
	int TRES = 3;

	/** Define Constante de int para 4 */
	int CUATRO_INT = 4;

	/** Define Constante de int 5 */
	int CINCO_INT = 5;

	/** Constante para el primitivo int = 5 */
	int CINCO = 5;

	/** Define Constante para int 6 */
	int SEIS_INT = 6;

	/** Constante para el primitivo int = 7 */
	int SIETE = 7;

	/** Define Constante para int 8 */
	int OCHO_INT = 8;

	/** Constante para el primitivo int = 8 */
	int OCHO = 8;

	/** Define Constante de int para 10 */
	int DIEZ_INT = 10;

	/** Constante para el primitivo int = 10 */
	int DIEZ = 10;

	/** Define Constante de int para 11 */
	int ONCE_INT = 11;

	/** Constante para el primitivo int = 11 */
	int ONCE = 11;

	/** Define Constante de int para 12 */
	int DOCE_INT = 12;

	/** Constante para definir el n&uacute;mero cero BigDecimal */
	BigDecimal CERO_BIG_DECIMAL = BigDecimal.valueOf(0);

	/** Constante para definir el n&uacute;mero cero BigDecimal */
	BigDecimal BIG_DECIMAL_ZERO = BigDecimal.valueOf(0);

	/** Constante BigDecimal UNO (1) */
	public BigDecimal BIG_DECIMAL_UNO = BigDecimal.valueOf(1);

	/** Constante BigDecimal ZIEN (100.0) */
	public BigDecimal CIEN_BIG_DECIMAL = BigDecimal.valueOf(100);

	/** Constante BigDecimal ZIEN (100.0) */
	public BigDecimal BIG_DECIMAL_ZIEN = BigDecimal.valueOf(100);

	/** Define Dias x Ao x 100 */
	BigDecimal DIAS_X_ANHIO_X_CIEN = BigDecimal.valueOf(36000);

	/** Constante para definir el n&uacute;mero cero BigInteger */
	BigInteger CERO_BIG_INTEGER = BigInteger.valueOf(0);

	/** Constante para definir el n&uacute;mero cero BigInteger */
	BigInteger BIG_INTEGER_ZERO = BigInteger.valueOf(0);

	/** Constante BigInteger UNO (1) */
	public BigInteger BIG_INTEGER_UNO = BigInteger.valueOf(1);

	/** Constante BigInteger UNO (2) */
	public BigInteger BIG_INTEGER_DOS = BigInteger.valueOf(2);

	/** Constante BigInteger UNO (100) */
	public BigInteger BIG_INTEGER_ZIEN = BigInteger.valueOf(100);

	/** Constante para definir el n&uacute;mero cero Integer */
	Integer CERO_INTEGER = Integer.valueOf(0);

	/** Constante para definir el n&uacute;mero cero Integer */
	Integer INTEGER_ZERO = Integer.valueOf(0);

	/** Constante Integer ZERO (0) */
	public Integer INTEGER_UNO = Integer.valueOf("1");

	/** Numero de milisegundos por dia */
	long MILLISECONDXDAY = 86400000;

	/** Constante de espacio en blanco */
	String BLANK_SPACE = " ";
	/** Constante de espacio en blanco */
	String BLANK = " ";

	/** TRASPASANTE = T */
	String TRASPASANTE = "T";

	/** RECEPTOR = R */
	String RECEPTOR = "R";

	/** Define clave AMBOS (AMBOS) */
	String AMBOS = "A";

	/** Indica TODOS */
	String TODOS = "TODOS";

	/** Constante para la aplicaci&oacute;n FUTR */
	String APLICACION_FUTR = "FUTR";

	/** Constante para la aplicaci&oacute;n FUTC */
	String APLICACION_FUTC = "FUTC";

	/** Constante para el activo de prestamistas = 'A' */
	String ACTIVO_PRESTAMISTA = "A";

	/** Constante para el activo de prestamistas = 'M' */
	String ACTIVO_PRESTAMISTA_M = "M";

	/** Constante para definir permiso anticipar */
	String PERMISO_ANTICIPAR = "permiso para anticipar";

	/** Constante para definir monto maximo emision */
	String MONTO_MAX_EMISION = "monto maximo por emision";

	/** Contante para definir el monto maximo total */
	String MONTO_MAX_TOTAL = "monto maximo total";

	/** Define clave INDEVAL */
	String CLAVE_INDEVAL = "INDEVAL";

	/** Define Mercado de Dinero */
	String MERCADO_DINERO = "MD";

	/** Define Mercado de Capitales */
	String MERCADO_CAPITALES = "MC";

	/* Constantes para Bitacora Match */

	/** Define Codigo de Operacion VALID_PROCESS */
	String CODIGO_OPERACION_VALID_PROCESS = "VALIDPROCESS";

	/** Define Estado Instruccion CON_MATCH */
	String ESTADO_INSTRUCCION_CON_MATCH = "CON_MATCH";

	/** Define Estado Instruccion SIN_MATCH */
	String ESTADO_INSTRUCCION_SIN_MATCH = "SIN_MATCH";

	/** Define Estado Instruccion EXPIRADO */
	String ESTADO_INSTRUCCION_EXPIRADO = "EXPIRADO";

	/** Define Estado Instruccion CANCELADO */
	String ESTADO_INSTRUCCION_CANCELADO = "CANCELADO";

	/** Define el estatus de enviado - EN */
	String ESTATUS_ENVIADO = "EN";

	/** Define el estatus de acknowledge - AKC */
	String ESTATUS_ACK = "ACK";

	/** Define el estatus de not acknowledge - NAK */
	String ESTATUS_NAK = "NAK";

	/** Define el estatus de no enviado - NE */
	String ESTATUS_NE = "NE";

	/** Constante que define que es 540 */
	String TMSJ_540 = "540";

	/** Constante que define el tipo de mensaje 541 */
	String TMSJ_541 = "541";

	/** Constante que define que es 542 */
	String TMSJ_542 = "542";

	/** Constante que define el tipo de mensaje 543 */
	String TMSJ_543 = "543";

	/** Constante que define que el mensaje es de Venta */
	String DESC_MENSAJEV = "VENTA";

	/** Constante que define que el mensaje es de Compra */
	String DESC_MENSAJEC = "COMPRA";

	/** Define el tipo de operacion para Misc. Fiscal */
	String TIPO_OPERACION_M = "M";
	
	/** ID Retiro SIAC */
	int ID_RETIRO_SIAC = 21;
	
	/** Descripcion de un retiro SIAC */
	String DESC_RETIRO_SIAC = "SIAC";
	
	/** Descripcion de un retiro SIAC */
	String DESC_RETIRO_SPEI = "SPEI";

	/* Secuencias */

	/** Define la Secuencia para el ID de la Bitacora */
	String SEQ_BITACORA_OPERACIONES = "SEQ_BITACORA_OPERACIONES";

	/** Define la Secuencia para la referencia del mensaje */
	String SEQ_REFERENCIA_MENSAJE = "SEQ_REFERENCIA_MENSAJE";

	/** Define la Secuencia para la referencia numerica SPEI de los retiros de efectivo */
	String SEQ_REFERENCIA_NUMERICA_SPEI = "SEQ_REFERENCIA_NUMERICA_SPEI";

	/** Define la Secuencia para la referencia del mensaje */
	String SEQ_OPERACION_ENVIABLE = "SEQ_OPERACION_ENVIABLE";

	/** Define la Secuencia para la obtencion del folio para dinero */
	String SEQ_FOLIO_FEC2 = "SEQ_FOLIO_FEC2";

	/** Define la Secuencia para la obtencion del folio para capitales */
	String SEQ_OPPP0302 = "SEQ_OPPP0302";
	
	String SECUENCIA_FOLIO_CONTROL = "SEQ_SLV_FOLIADOR";

	String OPERACION_CANCELADA = "CA";
	
	String DIVISA_MXN="MXN";
    
    String E_BANXICO = "E-BANXICO";

}
