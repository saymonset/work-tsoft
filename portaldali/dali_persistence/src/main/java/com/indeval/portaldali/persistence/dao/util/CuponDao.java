package com.indeval.portaldali.persistence.dao.util;

import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.persistence.model.Cupon;

public interface CuponDao {
	
	public Cupon consultaCuponValidoParaEmision(EmisionVO emisionVO);

}
