/*
 * Copyright (c) 2010 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.statements;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.persistence.dao.StatementsDivIntDao;

/**
 * Test para probar la consulta de Statements de Beneficiarios
 * @author Rafael Ibarra Zendejas
 * @version 1.0
 */
public class ITestFindArchivoStatements_2 extends BaseDaoTestCase {

	/** Log de la clase  */
	private static final Logger log = LoggerFactory.getLogger(ITestFindArchivoStatements_2.class);
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

	public void testDeleteArchivo() {
		log.info("Ejecutando prueba testDeleteArchivo()");
		
		// Parametros de consulta
		String nombreArchivo = "301210_STATEMENT_EWZ_US4642864007.XLS";

		int registrosBorrados = statementsDivIntDao.deleteArchivo(nombreArchivo);

		log.info("Archivos borrados: [" + registrosBorrados + "]");

	}

}
