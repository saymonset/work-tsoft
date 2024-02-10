/*
 * Copyright (c) 2010 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.statements;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.persistence.dao.StatementsDivIntDao;

/**
 * Test para probar la consulta de Statements de Beneficiarios
 * @author Rafael Ibarra Zendejas
 * @version 1.0
 */
public class ITestFindArchivoStatements_1 extends BaseDaoTestCase {

	/** Log de la clase  */
	private static final Logger log = LoggerFactory.getLogger(ITestFindArchivoStatements_1.class);
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
		assertNotNull("Dao nulo",statementsDivIntDao);
	}

	public void testFindArchivoStatements() {
		log.info("Ejecutando prueba testFindArchivoStatements()");
		
		// Parametros de consulta
		String nombreArchivo = null;
		// Estado
		PaginaVO paginaVO = new PaginaVO();

		paginaVO = statementsDivIntDao.findArchivoStatements(nombreArchivo, paginaVO);
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
