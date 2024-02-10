package com.indeval.portalinternacional.middleware.servicios.constantes;
/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */

/**
 * Interfaz de negocio que expone las constantes utilizadas en los catalogos del Portal Internacional.
 * 
 * @author Pablo Balderas
 *
 */
public interface CatalogosConstantes {

	/** Valor estatus activo */
	Long VALOR_ESTATUS_ACTIVO = 0L;
	
	/** Estatus activo */
	String ESTATUS_ACTIVO = "Activo";
	
	/** Valor estatus inactivo */
	Long VALOR_ESTATUS_INACTIVO = 1L;
	
	/** Estatus inactivo */
	String ESTATUS_INACTIVO = "Inactivo";
	
	/** Valor todos */
	Long VALOR_TODOS = -1L;
	String VALOR_TODOS_STR = "-1";
	
	/** Todos */
	String TODOS = "Todos";
	
	/** Indica el nombre del parámetro id lista de distribución */
	String PARAMETRO_ID_LISTA_DISTRIBUCION = "idListaDistribucion";
	
	/** Indica el nombre del parámetro id grupo */
	String PARAMETRO_ID_GRUPO = "idGrupo";	
	
	/** Indica el nombre del parámetro id persona */
	String PARAMETRO_ID_PERSONA = "idPersona";
	
	/** Expresión para validar correo electrónico */
	String EXPRESION_REGULAR_COREO = 
		"^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	
	String INSTITUCION_INDEVAL = "12001";
	
	/** Id estatus derecho pagado */
	Long ID_ESTATUS_DERECHO_PAGADO = 3L;
	
	/** Estatus derecho pagado */
	String ESTATUS_DERECHO_PAGADO = "PAGADO";
	
	/** Id estatus derecho pagado */
	Long ID_ESTATUS_DERECHO_CORTADO = 6L;
	
	/** Estatus derecho pagado */
	String ESTATUS_DERECHO_CORTADO = "CORTADO";
	
	/** Opciones para las consultas por fee */
	Integer ID_AMBOS = 1;
	String AMBOS = "AMBOS";
	Integer ID_CON_FEE = 2;
	String CON_FEE = "CON FEE";
	Integer ID_SIN_FEE = 3;
	String SIN_FEE = "SIN FEE";
}
