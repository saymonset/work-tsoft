package com.indeval.portaldali.middleware.services.common;

import java.util.List;

import com.indeval.portaldali.modelo.to.commons.BitacoraReenvioConfLiqReteDTO;
import com.indeval.portaldali.persistence.dao.common.BitacoraReenvioConfLiqReteDAO;

public class BitacoraReenvioConfLiqReteServiceImpl implements BitacoraReenvioConfLiqReteService {

	private BitacoraReenvioConfLiqReteDAO bitacoraReenvioConfLiqReteDAO;
	
	@Override
	public void saveBitacora(List<BitacoraReenvioConfLiqReteDTO> retes) {
		bitacoraReenvioConfLiqReteDAO.saveBitacora(retes);
	}

	public void setBitacoraReenvioConfLiqReteDAO(BitacoraReenvioConfLiqReteDAO bitacoraReenvioConfLiqReteDAO) {
		this.bitacoraReenvioConfLiqReteDAO = bitacoraReenvioConfLiqReteDAO;
	}

}