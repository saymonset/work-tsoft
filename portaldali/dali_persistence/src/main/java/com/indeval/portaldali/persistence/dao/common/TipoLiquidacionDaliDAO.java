/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * TipoLiquidacionDaliDAO.java
 * 29/02/2008
 */
package com.indeval.portaldali.persistence.dao.common;

import java.util.List;



import com.indeval.portaldali.middleware.dto.TipoLiquidacionDTO;

/**
 * Interface que expone los métodos para las operaciones realizadas sobre el catálogo
 * de tipos de liquidacion de la base de datos.
 * 
 * @author Fernadno Vázquez Ulloa
 * @version 1.0
 */
public interface TipoLiquidacionDaliDAO {

	/**
	 * Obtiene una lista con objetos de tipo {@link TipoLiquidacionDTO} con los datos
	 * de los tipos de instrucción obtenidos de la base de datos..
	 *
	 * 
	 * @return  Lista con objetos de tipo {@link TipoLiquidacionDTO} con los datos de los tipos de 
	 * instrucciones.
	 */
	List<TipoLiquidacionDTO> buscarTiposDeLiquidacion();
	/**
	 * Busca un Tipo de Liquidacion en la base de datos basado en el ID.
	 * @param idTipoliquidacion Id del tipo de liquidacion.
	 * @return DTO que representa los datos del tipos de liquidacion.
	 */
	TipoLiquidacionDTO buscarTipoDeLiquidacionPorId(long idTipoLiquidacion);
	
	/**
	 * Obtiene una lista con objetos de tipo {@link TipoLiquidacionDTO} con los datos
	 * de los tipos de liquidacion obtenidos de la base de datos.
	 * Se filtran los tipos de liquidacion por el prefijo del nombre corto del tipo de liquidacion
	 * @param prefijo La cadena con el prefijo a filtrar
	 * @return  Lista con objetos de tipo {@link TipoLiquidacionDTO} con los datos de los tipos de 
	 * liquidaciones.
	 */
	List<TipoLiquidacionDTO> buscarTiposDeLiquidacionPorPrefijo(String prefijo);
	/**
	 * Busca un Tipo de Liquidacion en la base de datos basado en su clave
	 * @param claveTipoLiquidacion Clave  del tipo de Liquidacion
	 * @return DTO que representa los datos del tipos de Liquidacion.
	 */
	TipoLiquidacionDTO buscarTipoDeLiquidacionPorClave(String claveTipoLiquidacion);
	/**
	 * Obtiene una lista con objetos de tipo {@link TipoLiquidacionDTO} con los datos
	 * de los tipos de instrucción obtenidos de la base de datos..
	 * Se filtran los tipos de Liquidacionn por un indicador de efectivo o de valor.
	 * @param tiposCustodia Lista de valores separados por coma que indican los tipos de custoria que aplican
	 * para la consulta de Liquidacion.
	 * @return  Lista con objetos de tipo {@link TipoLiquidacionDTO} con los datos de los tipos de 
	 * instrucciones.
	 */
	List<TipoLiquidacionDTO> buscarTiposDeLiquidacion(String tiposCustodia);
	
	/**
	 * Busca en el catálogo de tipos de instrucción aquellos tipos de instrucción de efectivo que comiencen con un prefijo dado.
	 * 
	 * @param prefijo el prefijo a consultar en el catálogo
	 * @return una lista con los tipos de instrucción que cumplen con el criterio de consulta.
	 */
	List<TipoLiquidacionDTO> buscarTiposDeLiquidacionPorIdsEfectivo(String prefijo);
	
	/**
	 * Busca en el catálogo de tipos de Liquidacion aquellos tipos de Liquidacion de valores que comiencen con un prefijo dado.
	 * 
	 * @param prefijo el prefijo a consultar en el catálogo
	 * @return una lista con los tipos de Liquidacion que cumplen con el criterio de consulta.
	 */
	List<TipoLiquidacionDTO> buscarTiposDeLiquidacionPorIdsValores(String prefijo);
	List<TipoLiquidacionDTO> buscarTipoDeLiquidacionPorIds();
	
	//TipoLiquidacionDTO buscarTipoDeLiquidacionPorClave(final String claveTipoLiquidacion);
	
}
