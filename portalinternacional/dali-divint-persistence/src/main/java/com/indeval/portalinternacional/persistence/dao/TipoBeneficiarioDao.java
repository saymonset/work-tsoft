/*
 * Copyright (c) 2010 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoBeneficiario;

/**
 * Dao para las consultas del Tipo de Beneficiario
 * 
 * @author Rafael Ibarra
 * @version 1.0
 */
public interface TipoBeneficiarioDao extends BaseDao{

	public TipoBeneficiario findTipoBeneficiarioByDesc(String description);

	/**
	 * Obtiene todos los tipos de beneficiarios del catalogo.
	 * @return Un listado de todos los tipos de beneficiarios.
	 */
	List<TipoBeneficiario> getTiposBeneficiario();

}
