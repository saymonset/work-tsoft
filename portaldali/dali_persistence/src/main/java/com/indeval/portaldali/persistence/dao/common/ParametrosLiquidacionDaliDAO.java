/**
 * 
 * 
 *
 * 
 * 
 */
package com.indeval.portaldali.persistence.dao.common;

import java.math.BigInteger;

import com.indeval.portaldali.middleware.dto.ParametrosLiquidacionDTO;

/**
 * 
 * 
 * 
 * @author 
 */
public interface ParametrosLiquidacionDaliDAO {

	
	/**
	 * Este Metodo Busca la configuracion  de Parametros por id
	 * 
	 * @param id
	 * @return
	 */
	public ParametrosLiquidacionDTO buscarParametroByID(BigInteger id);


	
}
