package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativoAltaDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.EventoCorporativoEdicionDTO;


public interface SaveEventosCorporativosService {	

	Long saveEventoCorporativo(EventoCorporativoAltaDTO params)throws BusinessException;
	Long updateEventoCorporativo(EventoCorporativoEdicionDTO params) throws BusinessException;
	void deleteAdjunto(Long idAdjuntos)throws BusinessException;

}
