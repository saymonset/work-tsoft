/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos;

import java.util.List;

import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosDetalleVO;

/**
 * Interface para los Servicios de Liquidacion de Decretos
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface LiquidacionDecretosService {

	/**
	 * Obtiene los registros de la consulta correspondiente al params
	 * proporcionados
	 * 
	 * @param params
	 * @return List Lista de objetos de la clase LiquidacionDecretosVO
	 * @throws BusinessException
	 */
	List getLiquidacionDecretos(LiquidacionDecretosParams params) throws BusinessException;

	/**
	 * Obtiene las instituciones de la consulta correspondiente al params
	 * porporcionados
	 * 
	 * @param params
	 * @return List Arreglo de objetos de la clase AgenteVO
	 * @throws BusinessException
	 */
	List getInstituciones(LiquidacionDecretosParams params) throws BusinessException;

	/**
	 * Obtiene los tipos de derecho (ejercicio) de la consulta correspondiente
	 * al params porporcionados
	 * 
	 * @param params
	 * @return List Arreglo de objetos de la clase String
	 * @throws BusinessException
	 */
	List getTiposDerecho(com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosParams params) throws BusinessException;

	/**
	 * Obtiene el detalle de cobros por decretos correspondiente a los
	 * parametros proporcionados
	 * 
	 * @param paramsDetalle
	 * @return LiquidacionDecretosDetalleVO Un objeto de la clase
	 *         LiquidacionDecretosDetalleVO
	 * @throws BusinessException
	 */
	LiquidacionDecretosDetalleVO getLiquidacionDecretosDetalle(LiquidacionDecretosDetalleParams paramsDetalle) throws BusinessException;

	/**
	 * Obtiene el detalle de las amortizaciones de cobros correspondiente a los
	 * parametros proporcionados
	 * 
	 * @param paramsDetalleAmortizaciones
	 * @return List Una lista de objetos
	 *         LiquidacionDecretosDetalleAmortizacionesVO
	 * @throws BusinessException
	 */
	List getLiquidacionDecretosDetalleAmortizaciones(
			com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosDetalleAmortizacionesParams paramsDetalleAmortizaciones) throws BusinessException;

	/**
	 * Obtiene la lista de tipos de ejercicio del portal y de ejercicio de
	 * derechos
	 * 
	 * @return List Una lista de objetos de la clase TiposEjercicioVO
	 * @throws BusinessException
	 */
	List getListaTiposEjercicio() throws BusinessException;

	/**
	 * Obtiene la lista de divisas del portal y de ejercicio de derechos
	 * 
	 * @return List
	 * @throws BusinessException
	 */
	List getListaDivisa() throws BusinessException;

}
