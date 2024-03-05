/**
 * Portal DALI
 * 
 * 2H Software SA
 *
 * ErrorLiquidacionDaliDAOImpl.java 
 *
 * Creado el: Sep 13, 2008
 */
package com.indeval.portaldali.persistence.dao.common.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.dto.ErrorDaliDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.persistence.dao.common.ErrorLiquidacionDaliDAO;
import com.indeval.portaldali.persistence.model.ErrorDali;

/**
 * Implementación del DAO para las operaciones CRUD del catálogo de Errores de
 * Liquidación del DALI.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public class ErrorLiquidacionDaliDAOImpl extends HibernateDaoSupport implements ErrorLiquidacionDaliDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.common.ErrorLiquidacionDaliDAO#buscarErroresLiquidacion()
	 */
	@SuppressWarnings("unchecked")
	public List<ErrorDaliDTO> buscarErroresLiquidacion() {

		List<ErrorDaliDTO> errores = new ArrayList<ErrorDaliDTO>();

		List<ErrorDali> erroresDali = getHibernateTemplate().find("FROM " + ErrorDali.class.getName() + " error ORDER BY error.claveError ");

		for (ErrorDali error : erroresDali) {
			errores.add(DTOAssembler.crearErrorDaliDTO(error));
		}

		return errores;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.common.ErrorLiquidacionDaliDAO#buscarErrorLiquidacionPorId(int)
	 */
	@SuppressWarnings("unchecked")
	public ErrorDaliDTO buscarErrorLiquidacionPorClaveError(String claveError) {
		ErrorDaliDTO error = null;
		List<ErrorDali> erroresDali = getHibernateTemplate().find("FROM " + ErrorDali.class.getName() + " error WHERE error.claveError = ? ORDER BY error.claveError ", claveError);
		
		if(erroresDali != null && !erroresDali.isEmpty()) {
			error = DTOAssembler.crearErrorDaliDTO(erroresDali.get(0));
		}
		
		return error;
	}

}
