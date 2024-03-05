/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * DaliPresentationConstants.java
 * 29/02/2008
 */
package com.indeval.portaldali.middleware.services.common.constants;

/**
 * Constantes generales del portal DALI
 * @author Emigdio Hernández
 *
 */
public interface DaliConstants {
	/**
	 * Valor que debe tomar un criterio de una lista elegible que repsenta la selcción "TODOS"
	 */
	int VALOR_COMBO_TODOS = -1;
	/**
	 * Valor que debe tomar un criterio de una lista elegible que repsenta la seleccion alguun valor inexistente
	 */
	int VALOR_COMBO_NINGUNO = -1234;
	
	/** El identificador del estado del cupón que indica que es un cupón vigente */
	int ID_CUPON_VIGENTE = 1;
	
	/**
	 * Mximo de resultados a mostrar en un autocomplete
	 */
	int CANTIDAD_MAXIMA_RESULTADOS_SUGGESTION_BOX = 500;
	/**
	 * Formato utilizado para la fecha
	 */
	String FORMATO_FECHA_CORTO = "dd/MM/yyyy";
	/**
	 * Formato utilizado para la fecha largo
	 */
	String FORMATO_FECHA_LARGO = "dd/MM/yyyy HH:mm:ss";
	/**
	 * Formato de moneda
	 */
	String FORMATO_MONEDA = "$ ###,##0.00";
	/**
	 * Formato de moneda sin signo
	 */
	String FORMATO_MONEDA_SIN_SIGNO = "###,##0.00";
	/**
	 * Formato de moneda
	 */
	String FORMATO_MONEDA_LARGO_8_DECIMALES = "$ ###,##0.00000000";
	/**
	 * formato porcentual para las tasas
	 */
	String FORMATO_PORCENTAJE_6_DECIMALES = "0.000000";
	/**
	 * Formato decimal con separador de miles y 2 dgitos
	 */
	String FORMATO_DECIMAL = "###,##0.00";
	/**
	 * Longitud del campo id y folio de institución juntos
	 */
	int LONGITUD_ID_FOLIO_INSTITUCION = 5;
	
	/** El identificador de la divisa peso en el catálogo */
	long ID_DIVISA_MEXICAN_PESO = 1;
	
	/** El identificador de la divisa euro en el catalogo */
	long ID_DIVISA_EURO = 4;
	
	/** El identificador de la divisa usd en el catalogo */
	long ID_DIVISA_USD = 3;

	/** El identificador del mercado papel gubernamental en el catálogo */
	long ID_MERCADO_PAPEL_GUBER = 3;
	
	/** El identificador del mercado papel bancario en el catálogo */
	long ID_MERCADO_PAPEL_BANCARIO = 2;
	
	/** El identificador del mercado capitales en el catálogo */
	long ID_MERCADO_CAPITALES = 1;
	
	/** El identificador del mercado de dinero */
	long ID_MERCADO_DINERO = 4;

	/** Se refiere al mercado de dinero */
	String PAPEL_MERCADO_TODOS = "mercadoTodos";
	
	/** Se refiere al mercado de dinero */
	String PAPEL_MERCADO_DINERO = "mercadoDinero";
	
	/** Se refiere al mercado de dinero */
	String PAPEL_MERCADO_CAPITALES = "mercadoCapitales";
	
	/** La descripción de la opción de los combo TODOS */
	String DESCRIPCION_TODOS = "TODOS";
	
	/** Opcion Todas de los combos */
	String DESCRIPCION_TODAS = "TODAS";
	
	/** La descripción del mercado papel gubernamental */
	String DESCRIPCION_MERCADO_PAPEL_GUBER = "PG";
	
	/** La descripción del mercado papel bancario */
	String DESCRIPCION_MERCADO_PAPEL_BANCARIO = "PB";
	
	/** La descripción del mercado capitales */
	String DESCRIPCION_MERCADO_CAPITALES = "MC";
	
	/** La descripción del mercado dinero */
	String DESCRIPCION_MERCADO_DINERO = "MD";
	
	/** La abreviación del estatus SIN_MATCH */
	String ABREV_ESTATUS_SIN_MATCH = "SM";
	
	/** La abreviación del estatus CANCELADA */
	String ABREV_ESTATUS_CANCELADA = "CA";
	
	/** Tipo de operaci&oacute;n COVE*/
	String OPERACION_TIPO_COVE = "V";
	
	/** Tipo de operaci&oacute;n CORE*/
	String OPERACION_TIPO_CORE = "J";
	
	/** Tipo de operaci&oacute;n REPO*/
	String OPERACION_TIPO_REPO_NOMINAL = "R";
	
	/** Tipo de mensaje 543*/
	String MENSAJE_VENTA = "543";
	
	/**Tipo de mensaje 541*/
	String MENSAJE_COMPRA = "541";
}
