/**
 * 2H Software - Bursatec
 * Sistema de Consulta de Estados de Cuenta
 *
 * ConsultaEmisionServiceImpl.java
 * Dec 6, 2007
 */
package com.indeval.portaldali.middleware.services.common;


import java.util.List;

import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.SerieDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaEmisionService;
import com.indeval.portaldali.persistence.dao.common.EmisionDaliDAO;

/**
 * Implementación del servicio de negocio que define los métodos para la consulta de emisiones
 * 
 * @author Sandra Flores Arrieta
 * @version 1.0
 */
public class ConsultaEmisionServiceImpl implements ConsultaEmisionService {

	/**
	 * DAO utilizado para consultar las emisiones.
	 */
	private EmisionDaliDAO emisionDaliDAO;
	
	
	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaEmisionService#buscarSeries(com.indeval.estadocuenta.core.application.dto.TipoValorDTO, com.indeval.estadocuenta.core.application.dto.EmisoraDTO)
	 */
	public List<SerieDTO> buscarSeries(SerieDTO criterio) {

		return emisionDaliDAO.buscarSeries(criterio);
	}

	/**
	 * método para obtener el atributo emisionDaliDAO
	 * 
	 * @return the emisionDaliDAO
	 */
	public EmisionDaliDAO getEmisionDAO() {
		return emisionDaliDAO;
	}

	/**
	 * método para establecer el atributo emisionDaliDAO
	 * 
	 * @param emisionDaliDAO the emisionDaliDAO to set
	 */
	public void setEmisionDAO(EmisionDaliDAO emisionDaliDAO) {
		this.emisionDaliDAO = emisionDaliDAO;
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaEmisionService#consultarEmisiones(com.indeval.estadocuenta.core.application.dto.EmisionDTO, com.indeval.estadocuenta.core.application.dto.EstadoPaginacionDTO)
	 */
	public List<EmisionDTO> consultarEmisiones(EmisionDTO criterio,EstadoPaginacionDTO estadoPaginacion) {
		return emisionDaliDAO.consultarEmisiones(criterio,estadoPaginacion);
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaEmisionService#obtenerProyeccionDeEmisiones(com.indeval.estadocuenta.core.application.dto.EmisionDTO)
	 */
	public int obtenerProyeccionDeEmisiones(EmisionDTO criterio) {
		return emisionDaliDAO.obtenerProyeccionDeEmisiones(criterio);
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaEmisionService#consultarEmisionPorId(long)
	 */
	public EmisionDTO consultarEmisionPorId(long idEmision) {
		return emisionDaliDAO.consultarEmisionPorId(idEmision);
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaEmisionService#consultarEmisiones(com.indeval.estadocuenta.core.application.dto.EmisionDTO, com.indeval.estadocuenta.core.application.dto.EstadoPaginacionDTO, java.util.Set)
	 */
	public List<EmisionDTO> consultarEmisiones(EmisionDTO criterio,
			EstadoPaginacionDTO estadoPaginacion, List<Long> emisionesValidas) {
		return emisionDaliDAO.consultarEmisiones(criterio, estadoPaginacion, emisionesValidas);
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaEmisionService#obtenerProyeccionDeEmisiones(com.indeval.estadocuenta.core.application.dto.EmisionDTO, java.util.Set)
	 */
	public int obtenerProyeccionDeEmisiones(EmisionDTO criterio,
			List<Long> emisionesValidas) {
		return emisionDaliDAO.obtenerProyeccionDeEmisiones(criterio, emisionesValidas);
	}
	
	public List<EmisionDTO> consultarEmisionesPorDescripciones(EmisionDTO criterio, final EstadoPaginacionDTO estadoPaginacion){
		List<EmisionDTO> listaEmisiones;
		
		listaEmisiones = emisionDaliDAO.consultarEmisionesPorDescripciones(criterio, estadoPaginacion);
		
		return listaEmisiones;
	}
}
