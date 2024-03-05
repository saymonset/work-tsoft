package com.indeval.portaldali.middleware.services.movimientosadministrativos;

import java.math.BigInteger;

import com.indeval.portaldali.middleware.services.modelo.BusinessException;

public interface TraspasosAdministrativosService {

	/**
	 * Validaciones y generacion de folios de la captura de traspasos
	 * administrativos
	 * 
	 * @param params
	 * @return BigInteger
	 * @throws BusinessException
	 */
	public BigInteger businessRulesTraspasosAdministrativos(TraspasosAdministrativosParams params) throws BusinessException;

}
