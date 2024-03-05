/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * 03/03/2008
 */
package com.indeval.portaldali.middleware.services.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.EmisoraDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaEmisoraService;
import com.indeval.portaldali.persistence.dao.common.EmisoraDaliDAO;

/**
 * Implementación del servicio de negocio que expone los métodos para las operaciones realizadas 
 * sobre el catálogo de emisoras de la base de datos.
 * 
 * @author Sandra Flores Arrieta
 * @version 1.0
 */
public class ConsultaEmisoraServiceImpl implements ConsultaEmisoraService {
	
	/**
	 * DAO utilizado para consultar el catálogo de mercados
	 */
	private EmisoraDaliDAO emisoraDaliDAO;
	
	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaEmisoraService#buscarEmisoras()
	 */
	public List<EmisoraDTO> buscarEmisoras() {
		
		return emisoraDaliDAO.buscarEmisoras();
	}

	/**
	 * método para obtener el atributo emisoraDaliDAO
	 * 
	 * @return the emisoraDaliDAO
	 */
	public EmisoraDaliDAO getEmisoraDAO() {
		return emisoraDaliDAO;
	}

	/**
	 * método para establecer el atributo emisoraDaliDAO
	 * 
	 * @param emisoraDaliDAO the emisoraDaliDAO to set
	 */
	public void setEmisoraDAO(EmisoraDaliDAO emisoraDaliDAO) {
		this.emisoraDaliDAO = emisoraDaliDAO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaEmisoraService#buscarEmisoraPorId(long)
	 */
	public EmisoraDTO buscarEmisoraPorId(long idEmisora) {
		
		return emisoraDaliDAO.buscarEmisoraPorId(idEmisora);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaEmisoraService#buscarEmisorasPorPrefijo(java.lang.String)
	 */
	public List<EmisoraDTO> buscarEmisorasPorPrefijo(String prefijo) {
		
		return emisoraDaliDAO.buscarEmisorasPorPrefijo(prefijo);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaEmisoraService#buscarEmisoraPorDescripcion(java.lang.String)
	 */
	public EmisoraDTO buscarEmisoraPorDescripcion(String descripcion) {
		
		return emisoraDaliDAO.buscarEmisoraPorDescripcion(descripcion);
	}
}
