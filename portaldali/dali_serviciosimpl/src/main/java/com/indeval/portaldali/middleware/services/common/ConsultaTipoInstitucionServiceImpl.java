package com.indeval.portaldali.middleware.services.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.TipoInstitucionDTO;
import com.indeval.portaldali.persistence.dao.common.TipoInstitucionDaliDAO;

public class ConsultaTipoInstitucionServiceImpl implements ConsultaTipoInstitucionService {
	
	private TipoInstitucionDaliDAO tipoInstitucionDaliDAO = null;
	
	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaTipoInstitucionService#consultaTipoInstitucionPorPrefijo(String)
	 */
	public List<TipoInstitucionDTO> consultaTipoInstitucionPorPrefijo(String prefijo) {
				
		return tipoInstitucionDaliDAO.buscarTipoInstitucionPorPrefijo(prefijo);
	}

	public TipoInstitucionDaliDAO getTipoInstitucionDAO() {
		return tipoInstitucionDaliDAO;
	}

	public void setTipoInstitucionDAO(TipoInstitucionDaliDAO tipoInstitucionDaliDAO) {
		this.tipoInstitucionDaliDAO = tipoInstitucionDaliDAO;
	}
	
	

}
