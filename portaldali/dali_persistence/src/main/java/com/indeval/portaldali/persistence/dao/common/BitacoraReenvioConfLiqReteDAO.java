package com.indeval.portaldali.persistence.dao.common;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.modelo.to.commons.BitacoraReenvioConfLiqReteDTO;

public interface BitacoraReenvioConfLiqReteDAO extends BaseDao {

	public void saveBitacora(List<BitacoraReenvioConfLiqReteDTO> retes);

}
