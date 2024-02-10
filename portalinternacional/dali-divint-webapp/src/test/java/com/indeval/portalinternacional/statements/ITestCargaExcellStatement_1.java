package com.indeval.portalinternacional.statements;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.indeval.portalinternacional.middleware.servicios.excell.vo.CargaExcellVO;
import com.indeval.portalinternacional.middleware.servicios.vo.StatementDivintVO;
import com.indeval.portalinternacional.presentation.controller.statements.ExcellStatementUtil;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Map;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class ITestCargaExcellStatement_1 extends AbstractDependencyInjectionSpringContextTests {

	/** Servicio de log */
	private Log log = LogFactory.getLog(this.getClass());
	/** Util que sera probado en esta prueba */
	private ExcellStatementUtil excellStatementUtil;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");

	@Override
	protected String[] getConfigLocations() {
		return new String[]{
					"iTestApplicationContext.xml"
				};
	}

	protected Object getBean(String id) {
		return applicationContext.getBean(id);
	}

	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		if (excellStatementUtil == null) {
			excellStatementUtil = (ExcellStatementUtil) applicationContext.getBean("excellStatementUtil");
		}
	}

	public void testReadExcel() throws Exception {
		log.info("Entrando a ITestGuardaStatement_1.testGuardaStatement()");
		assertNotNull("Servicio nulo", excellStatementUtil);

		FileInputStream fis = new FileInputStream("/soft/tmp/071010 Statement LQD US4642872422.xls");

		CargaExcellVO<StatementDivintVO> cargaExcell = excellStatementUtil.cargaExcel(fis, "071010 Statement LQD US4642872422.xls", null, true);

		log.info("++++++++++RESULTADOS");
		log.info("Registros Totales: [" + cargaExcell.getTotal() + "]");
		log.info("\tErrorres: [" + cargaExcell.getNoCargados() + "]");
		log.info("\tOK: [" + cargaExcell.getCargados() + "]");

		log.info("++++++++++ERRORES");
		final Map<Integer, String> errores = cargaExcell.getErrores();
		if (errores != null && !errores.isEmpty()) {
			for (Integer key : errores.keySet()) {
				log.error("Error columna[" + key + "]: [" + errores.get(key) + "]");
			}
		}

		log.info("++++++++++REGISTROS");
		final List<StatementDivintVO> resultados = cargaExcell.getResultados();
		if (resultados != null && !resultados.isEmpty()) {
			for (StatementDivintVO statement : resultados) {
				log.info(statement.toString());
			}
		}

	}
}
