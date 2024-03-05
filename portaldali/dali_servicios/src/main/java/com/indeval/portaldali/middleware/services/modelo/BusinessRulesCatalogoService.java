/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.modelo;

/**
 * Interface de los servicios de BusinessRulesCatalogo
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface BusinessRulesCatalogoService {

	/**
	 * Implementa la logica de negocio de catalogo..UP_libera_city
	 * 
	 * @param params
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean liberaOperaciones(LiberaOperacionesParams params) throws BusinessException;

	/**
	 * Implementa la logica de negocio de catalogo..UP_traslp
	 * 
	 * @param params
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean registraTraspasosYCompensaValores(RegistraTraspasosYCompensaValoresParams params) throws BusinessException;

}
