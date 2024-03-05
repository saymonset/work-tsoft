/**
 * Portal DALI
 * 
 * 2H Software SA
 *
 * ConsultaErroresLiquidacionServiceImpl.java 
 *
 * Creado el: Sep 13, 2008
 */
package com.indeval.portaldali.middleware.services.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.ErrorDaliDTO;
import com.indeval.portaldali.persistence.dao.common.ErrorLiquidacionDaliDAO;

/**
 * Implementación del servicio de negocio para la consulta del catálogo de
 * errores de liquidación del portal DALI.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public class ConsultaErroresLiquidacionServiceImpl implements ConsultaErroresLiquidacionService {

	/** DAO para la consulta de los errores de liquidación del DALI */
	private ErrorLiquidacionDaliDAO errorLiquidacionDaliDAO = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.common.ConsultaErroresLiquidacionService#buscarErroresLiquidacion()
	 */
	public List<ErrorDaliDTO> buscarErroresLiquidacion() {

		return errorLiquidacionDaliDAO.buscarErroresLiquidacion();
	}

	/**
	 * Obtiene el valor del atributo errorLiquidacionDaliDAO
	 * 
	 * @return el valor del atributo errorLiquidacionDaliDAO
	 */
	public ErrorLiquidacionDaliDAO getErrorLiquidacionDaliDAO() {
		return errorLiquidacionDaliDAO;
	}

	/**
	 * Establece el valor del atributo errorLiquidacionDaliDAO
	 * 
	 * @param errorLiquidacionDaliDAO
	 *            el valor del atributo errorLiquidacionDaliDAO a establecer.
	 */
	public void setErrorLiquidacionDaliDAO(ErrorLiquidacionDaliDAO errorLiquidacionDaliDAO) {
		this.errorLiquidacionDaliDAO = errorLiquidacionDaliDAO;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.common.ConsultaErroresLiquidacionService#buscarErrorLiquidacionPorId(int)
	 */
	public ErrorDaliDTO buscarErrorLiquidacionPorClaveError(String claveError) {
		
		return errorLiquidacionDaliDAO.buscarErrorLiquidacionPorClaveError(claveError);
	}

}
