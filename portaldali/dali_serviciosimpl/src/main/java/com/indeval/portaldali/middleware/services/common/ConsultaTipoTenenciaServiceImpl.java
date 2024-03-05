/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.middleware.services.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaTipoTenenciaService;
import com.indeval.portaldali.persistence.dao.common.TipoTenenciaDAO;

/**
 * Implementa la funcionalidad para realizar la consulta de tipo de cuenta ( también llamado tipos de tenencia)
 * Utiliza como criterio de búsqueda el tipo de cuenta (nombrada o controlada) y el tipo de naturaleza (activo o pasivo)
 * @author Emigdio Hernández
 * @version 1.0
 */
public class ConsultaTipoTenenciaServiceImpl implements
		ConsultaTipoTenenciaService {
	/**
	 * DAO para acceso a datos del catálogo de tipos de cuenta
	 */
	private TipoTenenciaDAO tipoTenenciaDAO = null;
	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaTipoTenenciaService#consultarTipoCuentaPorTipoCuentaYTipoNaturaleza(com.indeval.estadocuenta.core.application.dto.TipoTenenciaDTO)
	 */
	public List<TipoTenenciaDTO> consultarTipoCuentaPorTipoCuentaYTipoNaturaleza(
			TipoTenenciaDTO criterio) {
		
		return tipoTenenciaDAO.buscarTipoTenenciaPorNaturalezaTipoCuentaYTipoCustodia(criterio);
		
	}
	/**
	 * Obtiene el campo tipoTenenciaDAO
	 * @return  tipoTenenciaDAO
	 */
	public TipoTenenciaDAO getTipoTenenciaDAO() {
		return tipoTenenciaDAO;
	}
	/**
	 * Asigna el valor del campo tipoTenenciaDAO
	 * @param tipoTenenciaDAO el valor de tipoTenenciaDAO a asignar
	 */
	public void setTipoTenenciaDAO(TipoTenenciaDAO tipoTenenciaDAO) {
		this.tipoTenenciaDAO = tipoTenenciaDAO;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaTipoTenenciaService#buscarTipoTenenciaPorId(long)
	 */
	public TipoTenenciaDTO buscarTipoTenenciaPorId(long idTipoTenencia) {
		
		return tipoTenenciaDAO.buscarTipoTenenciaPorId(idTipoTenencia);
		
	}

}
