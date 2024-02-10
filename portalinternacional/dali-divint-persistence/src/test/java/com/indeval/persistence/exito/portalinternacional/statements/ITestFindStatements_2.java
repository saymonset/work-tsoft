/*
 * Copyright (c) 2010 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.statements;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaStatementsParam;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaStatementsTotalesVO;
import com.indeval.portalinternacional.persistence.dao.StatementsDivIntDao;

/**
 * Test para probar la consulta de Statements de Beneficiarios
 * @author Rafael Ibarra Zendejas
 * @version 1.0
 */
public class ITestFindStatements_2 extends BaseDaoTestCase {

	/** Log de la clase  */
	private static final Logger log = LoggerFactory.getLogger(ITestFindStatements_2.class);
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

	public void testFindTotalesStatements() {
		log.info("Ejecutando prueba testFindTotalesStatements()");
		
		// Parametros de consulta
		ConsultaStatementsParam param = new ConsultaStatementsParam();
		//param.setNombre("bolBRGGE");

		ConsultaStatementsTotalesVO totales = statementsDivIntDao.findTotalesStatements(param);
		assertNotNull("Totales nulo", totales);
		log.info("-----INFORMACION TOTALES");
		log.info("Total posiciones: [" + totales.getPosicion() + "]");
		log.info("Total dividendos: [" + totales.getDividendo() + "]");
		log.info("Total impuestos: [" + totales.getImpuestos() + "]");
		log.info("Total dividendos netos: [" + totales.getDividendoNeto() + "]");
	}
}
