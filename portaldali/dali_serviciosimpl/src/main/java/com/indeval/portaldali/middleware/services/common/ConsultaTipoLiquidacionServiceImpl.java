/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ConsultaTipoLiquidacionServiceImpl.java
 * 29/02/2008
 */
package com.indeval.portaldali.middleware.services.common;

import java.util.List;


import com.indeval.portaldali.middleware.dto.TipoLiquidacionDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaTipoLiquidacionService;
import com.indeval.portaldali.persistence.dao.common.TipoLiquidacionDaliDAO;

/**
 * Implementa la funcionalidad necesaria para realizar
 * las consultas del catálogo de tipos de liquidacion
 * @author Emigdio Hernández Rodríguez
 * @version 1.0
 * 
 */
public class ConsultaTipoLiquidacionServiceImpl implements
		ConsultaTipoLiquidacionService {

	private TipoLiquidacionDaliDAO tipoLiquidacionDaliDAO = null;
	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaTipoLiquidacionService#buscarTipoDeLiquidacionPorId(long)
	 */
	public TipoLiquidacionDTO buscarTipoDeLiquidacionPorId(
			long idTipoLiquidacion) {
		return tipoLiquidacionDaliDAO.buscarTipoDeLiquidacionPorId(idTipoLiquidacion);
	}

	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaTipoLiquidacionService#buscarTiposDeLiquidacion()
	 */
	public List<TipoLiquidacionDTO> buscarTiposDeLiquidacion() {
		return tipoLiquidacionDaliDAO.buscarTiposDeLiquidacion();
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.common.ConsultaTipoLiquidacionService#buscarTiposDeLiquidacionPorPrefijo(java.lang.String)
	 */
	public List<TipoLiquidacionDTO> buscarTiposDeLiquidacionPorPrefijo(
			String prefijo) {
		return tipoLiquidacionDaliDAO.buscarTiposDeLiquidacionPorPrefijo(prefijo);
	}
	/**
	 * Obtiene el campo tipoLiquidacionDaliDAO
	 * @return  tipoLiquidacionDaliDAO
	 */
	public TipoLiquidacionDaliDAO getTipoLiquidacionDAO() {
		return tipoLiquidacionDaliDAO;
	}

	/**
	 * Asigna el valor del campo tipoLiquidacionDaliDAO
	 * @param tipoLiquidacionDaliDAO el valor de tipoLiquidacionDaliDAO a asignar
	 */
	public void setTipoLiquidacionDAO(TipoLiquidacionDaliDAO tipoLiquidacionDaliDAO) {
		this.tipoLiquidacionDaliDAO = tipoLiquidacionDaliDAO;
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.common.ConsultaTipoLiquidacionService#buscarTipoDeLiquidacionPorClave(java.lang.String)
	 */
	public TipoLiquidacionDTO buscarTipoDeLiquidacionPorClave(
			String claveTipoLiquidacion) {
		return tipoLiquidacionDaliDAO.buscarTipoDeLiquidacionPorClave(claveTipoLiquidacion);
	}
	/*
	public TipoLiquidacionDTO buscarTipoDeLiquidacionPorClave(
			String claveTipoLiquidacion) {
		return tipoLiquidacionDaliDAO.buscarTipoDeLiquidacionPorClave(claveTipoLiquidacion);
		//return tipoLiquidacionDaliDAO.buscarTipoDeLiquidacionPorClave(claveTipoLiquidacion);
	}
	*/
	

	public List<TipoLiquidacionDTO> buscarTiposDeLiquidacion(
			String tiposCustodia) {
		return tipoLiquidacionDaliDAO.buscarTiposDeLiquidacion(tiposCustodia);
	}

	

}
