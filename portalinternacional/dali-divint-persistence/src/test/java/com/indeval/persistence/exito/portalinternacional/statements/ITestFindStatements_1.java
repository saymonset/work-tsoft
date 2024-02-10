/*
 * Copyright (c) 2010 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.statements;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaStatementsParam;
import com.indeval.portalinternacional.persistence.dao.StatementsDivIntDao;

/**
 * Test para probar la consulta de Statements de Beneficiarios
 * @author Rafael Ibarra Zendejas
 * @version 1.0
 */
public class ITestFindStatements_1 extends BaseDaoTestCase {

	/** Log de la clase  */
	private static final Logger log = LoggerFactory.getLogger(ITestFindStatements_1.class);
	/** Formato de fechas */
	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

	/**
	 * Dao que se va a probar
	 */
	private StatementsDivIntDao statementsDivIntDao;

	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		statementsDivIntDao = (StatementsDivIntDao) getBean("statementsDivIntDao");
		assertNotNull("DAO nulo",statementsDivIntDao);
	}

	public void testFindStatements() {
		log.info("Ejecutando prueba testFindStatements()");
		int registrosPorPagina = 200;
		// Estado de la paginacion
		PaginaVO paginaVO = new PaginaVO();
		paginaVO.setRegistrosXPag(registrosPorPagina);



		// Parametros de consulta
		ConsultaStatementsParam param = new ConsultaStatementsParam();
		param.setNombre("bolBRUGGE");

		paginaVO = statementsDivIntDao.findStatements(param, paginaVO);
		imprimeInfoPagina(paginaVO);

		paginaVO.setRegistros(null);
		paginaVO.setOffset(registrosPorPagina);
		paginaVO = statementsDivIntDao.findStatements(param, paginaVO);
		imprimeInfoPagina(paginaVO);

		paginaVO.setRegistros(null);
		paginaVO.setOffset(registrosPorPagina*2);
		paginaVO = statementsDivIntDao.findStatements(param, paginaVO);
		imprimeInfoPagina(paginaVO);

		paginaVO.setRegistros(null);
		paginaVO.setOffset(registrosPorPagina*3);
		paginaVO = statementsDivIntDao.findStatements(param, paginaVO);
		imprimeInfoPagina(paginaVO);

		paginaVO.setRegistros(null);
		paginaVO.setOffset(registrosPorPagina*4);
		paginaVO = statementsDivIntDao.findStatements(param, paginaVO);
		imprimeInfoPagina(paginaVO);

	}

	private void imprimeInfoPagina(PaginaVO paginaVO) {
		assertNotNull("Pagina nula", paginaVO);
		log.info("-----INFORMACION PAGINA");
		log.info("Total de registros: [" + paginaVO.getTotalRegistros() + "]");
		log.info("Registros por pagina: [" + paginaVO.getRegistrosXPag() + "]");
		log.info("Offset: [" + paginaVO.getOffset() + "]");
		assertNotNull("Lista nula", paginaVO.getRegistros());
		assertFalse("Lista vacia", paginaVO.getRegistros().isEmpty());
		log.info("Registros encontrados: [" + paginaVO.getRegistros().size() + "]");
	}

}
