/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.enviooperaciones;

import java.util.HashMap;

import com.indeval.portaldali.middleware.services.modelo.BusinessException;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface DispatcherOperacionesService {

	/**
	 * 
	 * @throws BusinessException
	 */
	public void enviaOperacionesPortal() throws BusinessException;

	/**
	 * @param instituciones
	 * @throws BusinessException
	 */
	public void enviaOperacionesH2H(HashMap instituciones) throws BusinessException;

}
