/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.HistoricoBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaHistoricoBeneficiariosParam;
/**
 * 
 * @author Oscar Garcia
 *
 */
public interface HistoricoBeneficiarioDao extends BaseDao{
	
	/**
	 * Devuelve una lista de todos los HistoricoBeneficiario
	 * @param param Parametros de consulta
	 * @param paginaVO Estado de la paginacion
	 * @return Resultados paginados
	 */
	PaginaVO findHistoricoBeneficiario(ConsultaHistoricoBeneficiariosParam param, PaginaVO paginaVO);

	/**
	 * Devuelve una lista de todos los HistoricoBeneficiario
	 * @param param Parametros de consulta
	 * @return Resultados paginados
	 */
	List<HistoricoBeneficiario> findHistoricoBeneficiario(Long beneficiarioID, String tipoFormato);
	
}
