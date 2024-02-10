/*
 * Copyright (c) 2010 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional.ejb;


import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.DomiciliosInstituciones;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaStatementsParam;
import com.indeval.portalinternacional.middleware.servicios.vo.StatementDivintVO;

import java.util.List;
import java.util.Map;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;

import com.indeval.portalinternacional.middleware.services.SpringBeanAutowiringInterceptorDivInt;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.StatementsDivintService;

/**
 * EJB para los statements
 *
 * @author Rafael Ibarra
 * @version 1.0
 */
@Stateless(name = "ejb.statementsDivintService", mappedName = "ejb.statementsDivintService")
@Interceptors(SpringBeanAutowiringInterceptorDivInt.class)
@Remote(StatementsDivintService.class)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class StatementsDivintServiceBean implements StatementsDivintService {

	/** Servicio de statements de Div Int */
	@Autowired
	private StatementsDivintService statementsDivintService;

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PaginaVO consultaStatements(ConsultaStatementsParam param, PaginaVO paginaVO) throws BusinessException {
		return statementsDivintService.consultaStatements(param, paginaVO);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Long guardaStatement(StatementDivintVO statementDivintVO) throws BusinessException {
		return statementsDivintService.guardaStatement(statementDivintVO);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Map<Long, DomiciliosInstituciones> consultaDomiciliosFiscales() {
		return statementsDivintService.consultaDomiciliosFiscales();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void validaStatement(StatementDivintVO statementDivintVO) throws BusinessException {
		statementsDivintService.validaStatement(statementDivintVO);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PaginaVO consultaArchivosStatement(String nombreArchivo, PaginaVO paginaVO) throws BusinessException {
		return statementsDivintService.consultaArchivosStatement(nombreArchivo, paginaVO);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public int borraArchivo(String nombreArchivo) throws BusinessException {
		return statementsDivintService.borraArchivo(nombreArchivo);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<String> listaCustodios() throws BusinessException {
		return statementsDivintService.listaCustodios();
	}
}
