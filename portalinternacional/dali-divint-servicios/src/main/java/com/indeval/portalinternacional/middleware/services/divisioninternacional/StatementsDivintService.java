/*
 * Copyright (c) 2010 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.DomiciliosInstituciones;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaStatementsParam;
import com.indeval.portalinternacional.middleware.servicios.vo.StatementDivintVO;

import java.util.List;
import java.util.Map;

/**
 * Servicio para el control de los statements
 * @author Rafael Ibarra Zendejas
 * @version 1.0
 */
public interface StatementsDivintService {

	/**
	 * Metodo para realizar la consulta de los statements
	 * @param param Parametros de consulta
	 * @param paginaVO Estado de la paginacion
	 * @return Consulta paginada
	 * @throws BusinessException En caso de error
	 */
	public PaginaVO consultaStatements(ConsultaStatementsParam param, PaginaVO paginaVO) throws BusinessException;

	/**
	 * Metodo para validar y guardar un statement
	 * @param statementDivintVO Vo del statement a guardar
	 * @return Id del beneficiario guardado
	 * @throws BusinessException En caso de error de validacion
	 */
	public Long guardaStatement(StatementDivintVO statementDivintVO) throws BusinessException;

	/**
	 * Metodo para solamente validar un statement
	 * @param statementDivintVO VO del statement a validar
	 * @throws BusinessException En caso de error de validacion
	 */
	public void validaStatement(StatementDivintVO statementDivintVO) throws BusinessException;

	/**
	 * Consulta los domicilios fiscales de las instituciones
	 * @return El mapa de los domicilios fiscales de las instituciones
	 */
	public Map<Long, DomiciliosInstituciones> consultaDomiciliosFiscales();

	/**
	 * Consulta para los archivos de los statements
	 * @param nombreArchivo Nombre del archivo a buscar
	 * @param paginaVO Estado de la paginacion
	 * @return Consulta Paginada
	 * @throws BusinessException En caso de error de validacion
	 */
	public PaginaVO consultaArchivosStatement(String nombreArchivo, PaginaVO paginaVO) throws BusinessException;

	/**
	 * Metodo para borrar los archivos
	 * @param nombreArchivo Nombre del archivo a borrar
	 * @return Numero de registros a borrar
	 * @throws BusinessException En caso de error de validacion
	 */
	public int borraArchivo(String nombreArchivo) throws BusinessException;
	
	
	public List<String> listaCustodios()throws BusinessException;
	
}
