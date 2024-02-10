package com.indeval.portalinternacional.persistence.dao;

import com.bursatec.persistence.dao.BaseDao;

public interface AdpCustodioPorcentajeDao extends BaseDao{

	/**
	 * Regresa el porcentaje según corresponda el ID de la clave ADP y
	 * ID de la Cuenta Nombrada
	 * 
	 * @param claveAdp
	 * @param cuentaNombrada
	 * @return
	 */
	Integer getPorcentaje(Long claveAdp, Long cuentaNombrada);
}