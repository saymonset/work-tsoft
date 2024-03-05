package com.indeval.portaldali.middleware.services.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.TipoInstitucionDTO;

public interface ConsultaTipoInstitucionService {
	
	public List<TipoInstitucionDTO> consultaTipoInstitucionPorPrefijo(String prefijo);

}
