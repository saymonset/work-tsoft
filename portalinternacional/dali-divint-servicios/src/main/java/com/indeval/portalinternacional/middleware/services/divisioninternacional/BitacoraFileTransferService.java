package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.vo.BitacoraFileTransferVO;

public interface BitacoraFileTransferService {
	
	public PaginaVO consultarFileTransfer(BitacoraFileTransferVO parametros, PaginaVO paginaVO, Boolean obtenerTotalRegistros) throws BusinessException;

}
