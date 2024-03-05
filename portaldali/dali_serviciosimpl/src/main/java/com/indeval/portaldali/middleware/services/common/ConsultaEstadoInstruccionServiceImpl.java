/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ConsultaEstadoInstruccionServiceImpl.java
 * 29/02/2008
 */
package com.indeval.portaldali.middleware.services.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.EstadoInstruccionDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaEstadoInstruccionService;
import com.indeval.portaldali.persistence.dao.common.EstadoInstruccionDaliDAO;

/**
 * Implementación del servicio de negocio para la consulta de los estatus de
 * instrucción.
 * @author Emigdio Hernández
 *
 */
public class ConsultaEstadoInstruccionServiceImpl implements
		ConsultaEstadoInstruccionService {
	/**
	 * Objeto de acceso al catálogo de estados de instrucción
	 */
	private EstadoInstruccionDaliDAO estadoInstruccionDaliDAO = null;
	/* (non-Javadoc)
	 * @see com.indeval.dali.application.services.ConsultaEstadoInstruccionService#consultarTodosLosEstadosInstruccion()
	 */
	public List<EstadoInstruccionDTO> consultarTodosLosEstadosInstruccion() {
		return estadoInstruccionDaliDAO.consultarTodosLosEstadosInstruccion();
	}
	/**
	 * Obtiene el campo estadoInstruccionDaliDAO
	 * @return  estadoInstruccionDaliDAO
	 */
	public EstadoInstruccionDaliDAO getEstadoInstruccionDAO() {
		return estadoInstruccionDaliDAO;
	}
	/**
	 * Asigna el campo estadoInstruccionDaliDAO
	 * @param estadoInstruccionDaliDAO el valor de estadoInstruccionDaliDAO a asignar
	 */
	public void setEstadoInstruccionDAO(EstadoInstruccionDaliDAO estadoInstruccionDaliDAO) {
		this.estadoInstruccionDaliDAO = estadoInstruccionDaliDAO;
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.common.ConsultaEstadoInstruccionService#consultarEstadoInstruccionPorId(int)
	 */
	public EstadoInstruccionDTO consultarEstadoInstruccionPorId(
			long idEstadoInstruccion) {
		return estadoInstruccionDaliDAO.consultarEstadoInstruccionPorId(idEstadoInstruccion);
	}
	
	public EstadoInstruccionDTO consultarEstadoInstruccionPorClave(String claveEstadoInstruccion){
		return estadoInstruccionDaliDAO.consultarEstadoInstruccionPorClave(claveEstadoInstruccion);
	}
	
	public List<EstadoInstruccionDTO> buscarEstadosInstruccionPorIds(String prefijo){
		return estadoInstruccionDaliDAO.buscarEstadosInstruccionPorIds(prefijo);
	}
}
