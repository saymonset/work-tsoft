package com.indeval.portalinternacional.persistence.dao;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.vo.BitacoraFileTransferVO;

public interface BitacoraFileTransferDao extends BaseDao {
	
	public PaginaVO findFileTransfer(BitacoraFileTransferVO parametros, PaginaVO paginaVO, Boolean obtenerTotalRegistros);
	
}
