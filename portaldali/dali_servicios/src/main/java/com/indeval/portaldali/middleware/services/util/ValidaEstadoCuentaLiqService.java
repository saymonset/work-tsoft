/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.util;

import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.tesoreria.GetEdoCtaLiqParams;

/**
 * 
 * @author Sergio Mena
 * 
 */
public interface ValidaEstadoCuentaLiqService {

	/**
	 * @param params
	 * @throws BusinessException
	 */
	void validaParamsEdoCuentaLiq(GetEdoCtaLiqParams params) throws BusinessException;
}
