/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ConsultaTipoInstruccionServiceImpl.java
 * 29/02/2008
 */
package com.indeval.portaldali.middleware.services.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaTipoInstruccionService;
import com.indeval.portaldali.persistence.dao.common.TipoInstruccionDaliDAO;

/**
 * Implementa la funcionalidad necesaria para realizar
 * las consultas del catálogo de tipos de instrucción.
 * @author Emigdio Hernández Rodríguez
 * @version 1.0
 *
 */
public class ConsultaTipoInstruccionServiceImpl implements
		ConsultaTipoInstruccionService {

	private TipoInstruccionDaliDAO tipoInstruccionDaliDAO = null;
	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaTipoInstruccionService#buscarTipoDeInstruccionPorId(long)
	 */
	public TipoInstruccionDTO buscarTipoDeInstruccionPorId(
			long idTipoInstruccion) {
		return tipoInstruccionDaliDAO.buscarTipoDeInstruccionPorId(idTipoInstruccion);
	}

	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaTipoInstruccionService#buscarTiposDeInstruccion()
	 */
	public List<TipoInstruccionDTO> buscarTiposDeInstruccion() {
		return tipoInstruccionDaliDAO.buscarTiposDeInstruccion();
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.common.ConsultaTipoInstruccionService#buscarTiposDeInstruccionPorPrefijo(java.lang.String)
	 */
	public List<TipoInstruccionDTO> buscarTiposDeInstruccionPorPrefijo(
			String prefijo) {
		return tipoInstruccionDaliDAO.buscarTiposDeInstruccionPorPrefijo(prefijo);
	}
	/**
	 * Obtiene el campo tipoInstruccionDaliDAO
	 * @return  tipoInstruccionDaliDAO
	 */
	public TipoInstruccionDaliDAO getTipoInstruccionDAO() {
		return tipoInstruccionDaliDAO;
	}

	/**
	 * Asigna el valor del campo tipoInstruccionDaliDAO
	 * @param tipoInstruccionDaliDAO el valor de tipoInstruccionDaliDAO a asignar
	 */
	public void setTipoInstruccionDAO(TipoInstruccionDaliDAO tipoInstruccionDaliDAO) {
		this.tipoInstruccionDaliDAO = tipoInstruccionDaliDAO;
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.common.ConsultaTipoInstruccionService#buscarTipoDeInstruccionPorClave(java.lang.String)
	 */
	public TipoInstruccionDTO buscarTipoDeInstruccionPorClave(
			String claveTipoInstruccion) {
		return tipoInstruccionDaliDAO.buscarTipoDeInstruccionPorClave(claveTipoInstruccion);
	}

	public List<TipoInstruccionDTO> buscarTiposDeInstruccion(
			String tiposCustodia) {
		return tipoInstruccionDaliDAO.buscarTiposDeInstruccion(tiposCustodia);
	}

	

}
