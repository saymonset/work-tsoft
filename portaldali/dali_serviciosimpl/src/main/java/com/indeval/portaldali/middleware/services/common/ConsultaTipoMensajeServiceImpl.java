/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ConsultaTipoMensajeServiceImpl.java
 * 03/03/2008
 */
package com.indeval.portaldali.middleware.services.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.TipoMensajeDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaTipoMensajeService;
import com.indeval.portaldali.persistence.dao.common.TipoMensajeDAO;

/**
 * Implementación del servicio de negocio para la búsqueda del catálogo
 * de tipos de mensaje.
 * @author Emigdio Hernández
 *
 */
public class ConsultaTipoMensajeServiceImpl implements
		ConsultaTipoMensajeService {
	/**
	 * DAO de acceso al catálogo de tipos de mensaje
	 */
	private TipoMensajeDAO tipoMensajeDAO = null;
	/* (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.common.ConsultaTipoMensajeService#consultaTipoMensajePorId(int)
	 */
	public TipoMensajeDTO consultaTipoMensajePorId(int idTipoMensaje) {
		return tipoMensajeDAO.consultaTipoMensajePorId(idTipoMensaje);
	}

	/* (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.common.ConsultaTipoMensajeService#consultarTodosLosTiposMensaje()
	 */
	public List<TipoMensajeDTO> consultarTodosLosTiposMensaje() {
		return tipoMensajeDAO.consultarTodosLosTiposMensaje();
	}

	/**
	 * Obtiene el campo tipoMensajeDAO
	 * @return  tipoMensajeDAO
	 */
	public TipoMensajeDAO getTipoMensajeDAO() {
		return tipoMensajeDAO;
	}

	/**
	 * Asigna el campo tipoMensajeDAO
	 * @param tipoMensajeDAO el valor de tipoMensajeDAO a asignar
	 */
	public void setTipoMensajeDAO(TipoMensajeDAO tipoMensajeDAO) {
		this.tipoMensajeDAO = tipoMensajeDAO;
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.common.ConsultaTipoMensajeService#consultaTipoMensajePorClave(java.lang.String)
	 */
	public TipoMensajeDTO consultaTipoMensajePorClave(String claveTipoMensaje) {
		return tipoMensajeDAO.consultaTipoMensajePorClave(claveTipoMensaje);
	}

}
