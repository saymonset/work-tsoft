/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.modelo;

/**
 * Interface de los servicios de BusinessRulesMercadoDinero
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface BusinessRulesMercadoDineroService {

	/**
	 * Implementa la logica de negocio de bddinero..UP_altamdin
	 * 
	 * @param params
	 * @return boolean if(true){ params.setAplicacion("BMVALGU"); } else {
	 *         params.setAplicacion("UP_almdin"); }
	 * @throws BusinessException
	 */
	public boolean altaMdintran(AltaMdintranParams params) throws BusinessException;

}
