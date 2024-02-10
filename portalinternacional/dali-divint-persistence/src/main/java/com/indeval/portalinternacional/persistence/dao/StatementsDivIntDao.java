/*
 * Copyright (c) 2010 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.StatementDivint;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaStatementsParam;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaStatementsTotalesVO;

/**
 * Dao para el manejo del modelo de Statements.
 * 
 * @author Rafael Ibarra Zendejas
 * @version 1.0
 */
public interface StatementsDivIntDao extends BaseDao {

	/**
	 * Metodo para buscar un statement por su Id
	 * @param id Id del statement a buscar
	 * @return El statement encontrado o null si no existe ninguno con es Id
	 */
	StatementDivint findStatementById( Long id );

	/**
	 * Metodo para consultar los statements
	 * @param param Objeto de los parametros de la consulta, no debe ser nulo
	 * @param paginaVO Estado de la paginación
	 * @return Consulta Paginada
	 */
	PaginaVO findStatements(ConsultaStatementsParam param, PaginaVO paginaVO);

	/**
	 * Metodo para consultar los totales para una consulta de Statements
	 * @param param Objeto de los parametros de la consulta, no debe ser nulo
	 * @return Totales de la consulta
	 */
	ConsultaStatementsTotalesVO findTotalesStatements(ConsultaStatementsParam param);

	/**
	 * Metodo para consultar los diferentes archivos cargados de los statements
	 * @param nombreArchivo Prefijo del archivo a buscar
	 * @param paginaVO Estado de la paginacion
	 * @return Consulta paginada
	 */
	PaginaVO findArchivoStatements(String nombreArchivo, PaginaVO paginaVO);

	/**
	 * Metodo para eliminar los registros
	 * @param nombreArchivo Nonmbre del archivo a eliminar
	 * @return Numero de filas borradas
	 */
	int deleteArchivo(String nombreArchivo);
	
	
	List<String> listaCustodios();

}
