/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.middleware.services.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.TipoOperacionDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaTipoOperacionService;
import com.indeval.portaldali.persistence.dao.common.TipoOperacionDAO;

/**
 * Implementa la funcionalidad definida por la interfaz del servicio de consulta de 
 * tipos de operación.
 * @author Emigdio Hernández Rodríguez
 * @version 1.0
 */
public class ConsultaTipoOperacionServiceImpl implements ConsultaTipoOperacionService {
	/**
	 * DAO para el acceso al catálogo de tipos de operación
	 */
	private TipoOperacionDAO tipoOperacionDAO = null;
	

	/**
	 * Obtiene el campo tipoOperacionDAO
	 * @return  tipoOperacionDAO
	 */
	public TipoOperacionDAO getTipoOperacionDAO() {
		return tipoOperacionDAO;
	}

	/**
	 * Asigna el valor del campo tipoOperacionDAO
	 * @param tipoOperacionDAO el valor de tipoOperacionDAO a asignar
	 */
	public void setTipoOperacionDAO(TipoOperacionDAO tipoOperacionDAO) {
		this.tipoOperacionDAO = tipoOperacionDAO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaTipoOperacionService#buscarTipoOperaciconPorId(long)
	 */
	public TipoOperacionDTO buscarTipoOperaciconPorId(long id) {
		return tipoOperacionDAO.buscarTipoOperaciconPorId(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaTipoOperacionService#buscatTipoOperacion()
	 */
	public List<TipoOperacionDTO> buscatTiposOperacion(String tiposCustodia) {
		return tipoOperacionDAO.buscatTiposOperacion(tiposCustodia);
	}
	
	@Override
	public List<TipoOperacionDTO> buscarTiposDeOperacion() {
		return this.tipoOperacionDAO.buscarTiposDeOperacion();
	}

}
