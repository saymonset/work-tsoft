/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2016 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.constantes;

/**
 * Interfaz que expone las constantes utilizadas en la generación de reportes.
 * 
 * @author Pablo Balderas
 */
public interface ExportacionConstantes {

	/** Indica que el reporte es de tipo PDF */
	int TIPO_REPORTE_PDF = 1;
	
	/** Indica que el reporte es de tipo XLS */
	int TIPO_REPORTE_XLS = 2;
	
	/** Indica que el reporte es de tipo TXT */
	int TIPO_REPORTE_TXT = 3;
	
	/** Indica que toma todos los valores */
	String TODOS = "TODOS";
	
	/** Indica que toma todas las opciones */
	String TODAS = "TODAS";
	
	/** Máximo número de registros a exportar */
	int MAX_REGISTROS_EXPORTAR = 50000;
}
