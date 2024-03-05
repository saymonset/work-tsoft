/**
 * Bursatec - Portal Dali
 * Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.aperturaSistema;

import java.math.BigInteger;

import com.indeval.portaldali.middleware.services.modelo.BusinessException;

/**
 * Interfaz de negocio que define los servicios para la apertura
 * de sistemas.
 * 
 * @author Pablo Balderas
 */
public interface AperturaSistemaService {

	/**
	 * Método que valida que la apertura de sistemas cumpla con las reglas de negocio.
	 * @param aperturaSistemasParametros Parametros a validar.
	 * @return La secuencia para la operacion.
	 * @throws BusinessException En caso de ocurrir un error en las reglas de negocio.
	 */
	BigInteger validarObtenerSecuencia(AperturaSistemasParametros aperturaSistemasParametros) throws BusinessException;
	
}
