/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ConsultaTipoInstruccionService.java
 * 29/02/2008
 */
package com.indeval.portaldali.middleware.services.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.TipoLiquidacionDTO;

/**
 * Interfaz que define el contrato para las operaciones de consulta
 * que el servicio de consulta de tipo de instrucción debera implementar.
 * @author Emigdio Hernández Rodríguez
 * @version 1.0
 *
 */
public interface ConsultaTipoLiquidacionService {
	/**
	 * Obtiene una lista con objetos de tipo {@link TipoLiquidacionDTO} con los datos
	 * de los tipos de instrucción obtenidos de la base de datos..
	 * Se filtran los tipos de instrucción por un indicador de efectivo o de valor.
	 * @param tiposCustodia Lista de valores separados por coma que indican los tipos de custoria que aplican
	 * para la consulta de Liquidaciones.
	 * @return  Lista con objetos de tipo {@link TipoLiquidacionDTO} con los datos de los tipos de 
	 * Liquidaciones.
	 */
	List<TipoLiquidacionDTO> buscarTiposDeLiquidacion(String tiposCustodia);
	/**
	 * Obtiene una lista con objetos de tipo {@link TipoLiquidacionDTO} con los datos
	 * de los tipos de instrucción obtenidos de la base de datos..
	 * Se filtran los tipos de instrucción por un indicador de efectivo o de valor.
	 * 
	 * @return  Lista con objetos de tipo {@link TipoLiquidacionDTO} con los datos de los tipos de 
	 * Liquidaciones.
	 */
	List<TipoLiquidacionDTO> buscarTiposDeLiquidacion();
	/**
	 * Busca un Tipo de Instrucción en la base de datos basado en el ID.
	 * @param idTipoLiquidacion Id del tipo de instrucción.
	 * @return DTO que representa los datos del tipos de instrucción.
	 */
	TipoLiquidacionDTO buscarTipoDeLiquidacionPorId(long idTipoLiquidacion);
	/**
	 * Obtiene una lista con objetos de tipo {@link TipoLiquidacionDTO} con los datos
	 * de los tipos de instrucción obtenidos de la base de datos.
	 * Se filtran los tipos de instrucción por el prefijo del nombre corto del tipo de instrucción
	 * @param prefijo La cadena con el prefijo a filtrar
	 * @return  Lista con objetos de tipo {@link TipoLiquidacionDTO} con los datos de los tipos de 
	 * Liquidaciones.
	 */
	List<TipoLiquidacionDTO> buscarTiposDeLiquidacionPorPrefijo(String prefijo);
	
	
	/**
	 * Busca un Tipo de Instrucción en la base de datos basado en su clave
	 * @param claveTipoLiquidacion Clave  del tipo de instrucción.
	 * @return DTO que representa los datos del tipos de instrucción.
	 */
	TipoLiquidacionDTO buscarTipoDeLiquidacionPorClave(String claveTipoLiquidacion);
}
