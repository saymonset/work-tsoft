/**
 * CMMV - Portal Dali
 * Copyrigth (c) 2018 CMMV. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.util;

import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;      

/**
 * Interfaz de negocio que define los metodos necesarios para el control de
 * las cuentas de emision en las operaciones de mercado de dinero y capitales.
 * 
 * @author Pablo Balderas
 */
public interface ValidadorCuentasEmision {

	/**
	 * 
	 * @param traspasante
	 * @param receptor
	 * @param emision
	 * @throws BusinessException
	 */
	void validarCuentasEmisionMercadoDinero(AgenteVO traspasante, AgenteVO receptor, EmisionVO emision) throws BusinessException;
	
}
