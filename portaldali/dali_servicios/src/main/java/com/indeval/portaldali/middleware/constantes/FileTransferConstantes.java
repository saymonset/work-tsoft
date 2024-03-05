/**
 * Bursatec - Portal DALI
 * Copyrigth (c) 2014 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.constantes;


/**
 * Interface que expone las constantes utilizadas en los procesos de File Transfer.
 * 
 * @author Pablo Balderas
 */
public interface FileTransferConstantes {

	/** Tipo de operacion de miscelanea fiscal para capitales */
	String TIPO_OPERACION_FC = "FC";
	
	/** Tipo de operacion de miscelanea fiscal para deuda */
	String TIPO_OPERACION_FD = "FD";
	
	/** Tipo de contenido texto plano */
	String CONTENT_TYPE_TEXTO_PLANO = "text/plain";
	
	/** Indica que la carga del archivo fue abortada */
	char ABORT_TRUE = 'T';

	/** Indica que la carga del archivo no fue abortada */
	char ABORT_FALSE = 'F';
	
	/** Formato de fecha utilizado en los procesos de Miscelanea Fiscal */
	String FORMATO_FECHA_MISCELANEA_FISCAL = "dd-MM-yyyy";
	
	/** Formato de fecha-hora utilizado en los procesos de Miscelanea Fiscal */
	String FORMATO_FECHA_HORA_MISCELANEA_FISCAL = "dd-MM-yyyy HH:mm";
	
	/** Indica la longitud de los registros de miscelanea fiscal de capital */
	int LONGITUD_FT_MISCELANEA_FISCAL_FC = 198;
	
	/** Indica la longitud de los registros de miscelanea fiscal de deuda */
	int LONGITUD_FT_MISCELANEA_FISCAL_FD = 210;
	
	/** Indica la longitud maxima de la cadena a guardar en la tabla de file transfer */
	int LONGITUD_MAXIMA_CADENA = 255;
	
	/** Indica el número máximo de registros permitidos para miscelanea fiscal */
	int MAXIMO_REGISTROS_MISCELANEA_FISCAL = 1000;
	
	/** Parametro de c_propiedades_dali para numero maximo de registros a carga en file transfer */
	String PARAMETRO_MAXIMO_REG_FT_PORTAL = "portalDali.maximo_registros_file_transfer";
	
	/** RFC para registros extranjeros en miscelanea fiscal */
	String RFC_EXTRANJERO = "EXT790101NI4";
	
	/** Indica si un registro es extranjero */
	String MISCELANEA_FISCAL_EXTRANJERO = "1";
	
	/** Indica si un registro es nacional */
	String MISCELANEA_FISCAL_NACIONAL = "0";
	
	/** Id institución de la CCV */
	String ID_INSTITUCION_CCV = "25";
}
