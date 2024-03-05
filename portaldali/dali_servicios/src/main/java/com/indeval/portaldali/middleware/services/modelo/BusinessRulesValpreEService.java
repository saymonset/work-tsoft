/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.modelo;

/**
 * Interface de los servicios de BusinessRulesValpreEService
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface BusinessRulesValpreEService {

	/**
	 * Implementa la logica de negocio de bdvalpre..UP_pv_garantias
	 * 
	 * @param params
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean asignaGarantiasPrestamo(AsignaGarantiasPrestamoParams params) throws BusinessException;

	/**
	 * Implementa la logica de negocio de bdvalpre..UP_pv_insparametro
	 * 
	 * @param params
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean insertaParametro(InsertaParametroParams params) throws BusinessException;

	/**
	 * Implementa la logica de negocio de bdvalpre..UP_pv_insprestamo
	 * 
	 * @param params
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean concertacionPrestamosValpreE(ConcertacionPrestamosValpreEParams params) throws BusinessException;

	/**
	 * Implementa la logica de negocio de bdvalpre..UP_pv_pendiente
	 * 
	 * @param params
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean regresaValoresPrestadosMarcaPrestamoPendienteInsertaVptran(RegresaValoresPrestadosMarcaPrestamoPendienteInsertaVptranParams params)
			throws BusinessException;

	/**
	 * Implementa la logica de negocio de bdvalpre..UP_pv_prorroga
	 * 
	 * @param params
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean prorrogaPrestamos(ProrrogaPrestamosParams params) throws BusinessException;

}
