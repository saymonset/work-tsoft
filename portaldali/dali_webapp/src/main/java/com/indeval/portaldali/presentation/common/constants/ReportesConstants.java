/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ReportesConstants.java
 * 04/03/2008
 */
package com.indeval.portaldali.presentation.common.constants;

/**
 * Clase que define las constantes requeridas para la generación de los reportes 
 * de la aplicación.
 * 
 * @author Emigdio Hernández
 * @version 1.0
 *
 */
public interface ReportesConstants {

	/**
	 * <p>Tipos de reportes validos que se pueden generar.</p>
	 */
	String[] VALID_TYPES =
	{ "text/html",            // Representación HTML estandar
	  "application/pdf",      // Formato de documento PDF
	  "application/ms-excel", // Formato de documento de excel
	  "application/msword"	  // Formato de documento de Word
	};

	/**
	 * <p>Prefijo con la ruta donde se encuentran los archivos de los reportes para generar el nombre.</p>
	 */
	String PREFIJO = "/reports/";
	
	/**
	 * <p>Sufijo con la extensin del nombre de los reportes compilados</p>
	 */
	String SUFIJO = ".jasper";
	
	/** parámetro para los reportes que contiene el número de cuenta seleccionada de los criterios de búsqueda */
	String CUENTA_PARAMETER = "CUENTA_PARAMETER";
	
	/** parámetro para los reportes que contiene el número de cuenta del participante seleccionada de los criterios de búsqueda */
	String CUENTA_PARTICIPANTE_PARAMETER = "CUENTA_PARTICIPANTE_PARAMETER";
	
	/** parámetro para los reportes que contiene el tipo de cuenta seleccionado de los criterios de búsqueda de la cuenta */
	String CUENTA_CONTRAPARTE_PARAMETER = "CUENTA_CONTRAPARTE_PARAMETER";
	
	/** parámetro para los reportes que contiene naturaleza seleccionada de los criterios de búsqueda de la cuenta */
	String NATURALEZA_PARAMETER = "NATURALEZA_PARAMETER";
	
	/** parámetro para los reportes que contiene naturaleza del participante seleccionada de los criterios de búsqueda de la cuenta */
	String NATURALEZA_PARTICIPANTE_PARAMETER = "NATURALEZA_PARTICIPANTE_PARAMETER";
	
	/** parámetro para los reportes que contiene naturaleza de la contraparte seleccionada de los criterios de búsqueda de la cuenta */
	String NATURALEZA_CONTRAPARTE_PARAMETER = "NATURALEZA_CONTRAPARTE_PARAMETER";
	
	/** parámetro para los reportes que contiene el tipo de cuenta seleccionado de los criterios de búsqueda de la cuenta */
	String TIPO_CUENTA_PARAMETER = "TIPO_CUENTA_PARAMETER";
	
	/** parámetro para los reportes que contiene el tipo de cuenta del participante seleccionado de los criterios de búsqueda de la cuenta */
	String TIPO_CUENTA_PARTICIPANTE_PARAMETER = "TIPO_CUENTA_PARTICIPANTE_PARAMETER";
	
	/** parámetro para los reportes que contiene el tipo de cuenta de la contraparte seleccionado de los criterios de búsqueda de la cuenta */
	String TIPO_CUENTA_CONTRAPARTE_PARAMETER = "TIPO_CUENTA_CONTRAPARTE_PARAMETER";
	
	/** parámetro para los reportes que contiene el tipo de tenencia seleccionada de los criterios de búsqueda de la cuenta */
	String TIPO_TENENCIA_PARAMETER = "TIPO_TENENCIA_PARAMETER";
	
	/** parámetro para los reportes que contiene el tipo de tenencia del participante seleccionada de los criterios de búsqueda de la cuenta */
	String TIPO_TENENCIA_PARTICIPANTE_PARAMETER = "TIPO_TENENCIA_PARTICIPANTE_PARAMETER";
	
	/** parámetro para los reportes que contiene el tipo de tenencia de la contraparte seleccionada de los criterios de búsqueda de la cuenta */
	String TIPO_TENENCIA_CONTRAPARTE_PARAMETER = "TIPO_TENENCIA_CONTRAPARTE_PARAMETER";
	
	/** parámetro para los reportes que contiene el tipo de mercado seleccionado de los criterios de búsqueda de la emisión */
	String TIPO_MERCADO_PARAMETER = "TIPO_MERCADO_PARAMETER";
	
	/** parámetro para los reportes que contiene el isin seleccionado de los criterios de búsqueda  de la emisión*/
	String ISIN_PARAMETER = "ISIN_PARAMETER";
	
	/** parámetro para los reportes que contiene el tipo de valor seleccionado de los criterios de búsqueda de la emisión */
	String TV_PARAMETER = "TV_PARAMETER";
	
	/** parámetro para los reportes que contiene la emisora seleccionada de los criterios de búsqueda de la emisión */
	String EMISORA_PARAMETER = "EMISORA_PARAMETER";
	
	/** parámetro para los reportes que contiene el número de serie seleccionado de los criterios de búsqueda de la emisión */
	String SERIE_PARAMETER = "SERIE_PARAMETER";
	
	/** parámetro para los reportes que contiene el cupón seleccionado de los criterios de búsqueda de la emisión */
	String CUPON_PARAMETER = "CUPON_PARAMETER";
	
	/** parámetro para los reportes que contiene la bóveda seleccionada de los criterios de búsqueda */
	String BOVEDA_PARAMETER = "BOVEDA_PARAMETER";
	
	/** parámetro para los reportes que contiene la fecha seleccionada de los criterios de búsqueda */
	String FECHA_PARAMETER = "FECHA_PARAMETER";
	
	/** parámetro para los reportes que contiene la fecha inicial seleccionada de los criterios de búsqueda */
	String FECHA_INICIAL_PARAMETER = "FECHA_INICIAL_PARAMETER";
	
	/** parámetro para los reportes que contiene la fecha final seleccionada de los criterios de búsqueda */
	String FECHA_FINAL_PARAMETER = "FECHA_FINAL_PARAMETER";
	
	/** parámetro para los reportes que contienen el rol del participante seleccionado de los criterios de búsqueda */
	String ROL_PARTICIPANTE_PARAMETER = "ROL_PARTICIPANTE_PARAMETER";
	
	/** parámetro para los reportes que contienen el rol de la contraparte seleccionado de los criterios de búsqueda */
	String ROL_CONTRAPARTE_PARAMETER = "ROL_CONTRAPARTE_PARAMETER";
	
	/** parámetro para los reportes que contienen el tipo de instrucción seleccionado de los criterios de búsqueda */
	String TIPO_INSTRUCCION_PARAMETER = "TIPO_INSTRUCCION_PARAMETER";
	
	/** parámetro para los reportes que contienen el tipo de operación seleccionado de los criterios de búsqueda */
	String TIPO_OPERACION_PARAMETER = "TIPO_OPERACION_PARAMETER";
	
	/** parámetro para los reportes que contienen el folio de la instrucción seleccionado de los criterios de búsqueda */
	String FOLIO_PARAMETER = "FOLIO_PARAMETER";
	
	/** parámetro para los reportes que contiene la divisa seleccionada de los criterios de búsqueda */
	String DIVISA_PARAMETER = "DIVISA_PARAMETER";
	
	/** parámetro para los reportes que contiene el url de la imagen del encabezado del reporte */
	String IMAGEN_ENCABEZADO_URL_PARAMETER = "IMAGEN_ENCABEZADO_URL_PARAMETER";
	
	/** Nombre del archivo del reporte de consulta de posiciones */
	String REPORTE_CONSULTA_POSICIONES = "ConsultaPosicionesReport";
	
	/** Nombre del archivo del reporte de consulta de saldos */
	String REPORTE_CONSULTA_SALDOS = "ConsultaSaldosReport";
	
	/** Nombre del archivo del reporte del estado de cuenta de valores */
	String REPORTE_ESTADO_CUENTA_POSICIONES = "EstadoCuentaValReport";
	
	/** Nombre del archivo del reporte del estado de cuenta de efectivo */
	String REPORTE_ESTADO_CUENTA_SALDOS = "EstadoCuentaEfeReport";
	
	/** Nombre del archivo del reporte de consulta de movimientos de valores */
	String REPORTE_CONSULTA_MOVIMIENTOS_VALORES = "ConsultaMovValoresReport";
	
	/** Nombre del archivo del reporte de consulta de movimientos de saldos de efectivo */
	String REPORTE_CONSULTA_MOVIMIENTOS_SALDOS = "ConsultaMovEfeReport";
	
	/** Tipo de reporte con formato PDF */
	String TIPO_ARCHIVO_PDF = "application/pdf";
	
	/** Tipo de reporte con formato excel */
	String TIPO_ARCHIVO_XLS = "application/ms-excel";
	
	/** Tipo de reporte con formato HTML */
	String TIPO_ARCHIVO_HTML = "text/html";
	
	/** Tipo de reporte con formato word */
	String TIPO_ARCHIVO_DOC = "application/msword";
	/**
	 * Nombre del parámetro que contiene el JR Datasource de resultados
	 */
	String RESULTADOS_PARAMETER = "RESULTADOS";
}
