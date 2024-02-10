package com.indeval.portalinternacional.persistence.dao;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portalinternacional.middleware.servicios.modelo.ExcepcionEmisionBenef;

public interface ExcepcionEmisionBenefDao  extends BaseDao {

	/**
	 * 
	 * @param idCuentaNombrada
	 * @return
	 */
	List<ExcepcionEmisionBenef> findExecepcionesEmision(Long idCuentaNombrada);
	
	/**
	 * 
	 * @return
	 */
	List<ExcepcionEmisionBenef> findExecepcionesEmision();
	
	/**
	 * 
	 * @param emisionBenef
	 * @return
	 */
	ExcepcionEmisionBenef findExecepcionesEmision(ExcepcionEmisionBenef emisionBenef);
	
	/**
	 * 
	 * @param cuentaCustodio
	 * @return
	 */
	ExcepcionEmisionBenef findEmisionPorcentajeCero(Long cuentaCustodio);
}
