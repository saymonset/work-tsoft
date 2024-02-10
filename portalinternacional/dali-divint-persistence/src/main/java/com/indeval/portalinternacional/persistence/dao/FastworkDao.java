package com.indeval.portalinternacional.persistence.dao;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.fo.FastworkMessageFO;
import com.indeval.portalinternacional.middleware.servicios.vo.FastworkMessageVO;

public interface FastworkDao extends BaseDao {
	
	public PaginaVO consultaMensajes(FastworkMessageFO fastworkMessagefo, PaginaVO paginaVO, boolean reporte);
	
	public FastworkMessageVO consultaIso(String dbkey);

}
