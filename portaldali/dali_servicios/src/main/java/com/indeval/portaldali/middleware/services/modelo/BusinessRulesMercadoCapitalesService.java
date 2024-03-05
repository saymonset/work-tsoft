/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.modelo;

/**
 * Interface de los servicios de BusinessRulesMercadoCapitales
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface BusinessRulesMercadoCapitalesService {

	/**
	 * Implementa la logica de negocio de bdcaptal..UP_tranmcap
	 * 
	 * @param params
	 * @return boolean
	 * @throws BusinessException
	 */
	boolean traspasosDineroComprador(TraspasosDineroCompradorParams params) throws BusinessException;

	/**
	 * Implementa la logica de negocio de bdcaptal..UP_trasp_divint
	 * 
	 * @param params
	 * @return boolean
	 * @throws BusinessException
	 */
	boolean traspasosDivisionInternacionalEntreFideicomisos(TraspasosDivisionInternacionalEntreFideicomisosParams params) throws BusinessException;

	/**
	 * Implementa la logica de negocio de bdcaptal..UP_trasp_divintsf
	 * 
	 * @param params
	 * @return boolean
	 * @throws BusinessException
	 */
	boolean traspasosDivisionInternacionalSinFideicomisos(TraspasosDivisionInternacionalSinFideicomisosParams params) throws BusinessException;

}
