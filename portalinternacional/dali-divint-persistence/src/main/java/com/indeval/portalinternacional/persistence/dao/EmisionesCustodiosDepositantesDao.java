package com.indeval.portalinternacional.persistence.dao;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portalinternacional.middleware.servicios.dto.CustodiosDepositantesDto;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;

public interface EmisionesCustodiosDepositantesDao extends BaseDao {
	
	public PaginaVO consultarEmisionesCustodiosDepositantes(CustodiosDepositantesDto dto, PaginaVO paginaVO);

}
