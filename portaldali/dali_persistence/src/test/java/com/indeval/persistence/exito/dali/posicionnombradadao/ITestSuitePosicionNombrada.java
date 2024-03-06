/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.posicionnombradadao;

import java.util.Enumeration;

import junit.framework.TestFailure;
import junit.framework.TestResult;
import junit.framework.TestSuite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 * 
 */
public class ITestSuitePosicionNombrada extends BaseDaoTestCase {

	/** Objeto de loggeo */
	private static final Log log = LogFactory
			.getLog(ITestSuitePosicionNombrada.class);

	/**
	 * TestCase para testSuite()
	 */
	public void testSuite() {

		log.debug("Entrando a testSuite()");

		TestSuite testSuitePosicionNombrada = new TestSuite();
		TestResult resultados = new TestResult();

		testSuitePosicionNombrada
				.addTestSuite(ITestGetDetalleEmisiones_1.class);
		testSuitePosicionNombrada.addTestSuite(ITestGetEmisoraByTv_1.class);
		testSuitePosicionNombrada
				.addTestSuite(ITestGetTPosicionNombrada_1.class);
		testSuitePosicionNombrada
				.addTestSuite(ITestGetTPosicionNombradaByAgente_1.class);
		testSuitePosicionNombrada
				.addTestSuite(ITestGetTPosicionNombradaByCuenta_1.class);
		testSuitePosicionNombrada
				.addTestSuite(ITestGetTPosicionNombradaByCuentas_1.class);
		testSuitePosicionNombrada
				.addTestSuite(ITestGetTPosicionNombradaByExample_1.class);
		testSuitePosicionNombrada
				.addTestSuite(ITestGetTPosicionNombradaByIdTipoValor_1.class);
		testSuitePosicionNombrada
				.addTestSuite(ITestGetTPosicionNombradaByInstitucionEdoCtaUnico_1.class);
		testSuitePosicionNombrada
				.addTestSuite(ITestGetTPosicionNombradaCapitales_1.class);
		testSuitePosicionNombrada
				.addTestSuite(ITestGetTPosicionNombradaDivisionInternacional_1.class);
		testSuitePosicionNombrada
				.addTestSuite(ITestGetTPosicionNombradaFileTransferMC_1.class);
		testSuitePosicionNombrada
				.addTestSuite(ITestGetTPosicionNombradaGarantias_1.class);
		testSuitePosicionNombrada
				.addTestSuite(ITestGetTPosicionNombradaPorEjercicioDivisionInternacional_1.class);
		testSuitePosicionNombrada
				.addTestSuite(ITestGetTPosicionNombradaValpreE_1.class);
		testSuitePosicionNombrada
				.addTestSuite(ITestGetTPosicionNombradaValpreEAdmonG_1.class);

		testSuitePosicionNombrada.run(resultados);

		if (!resultados.wasSuccessful()) {

			log.debug("Los errores fueron los siguientes : ");

			TestFailure falla = null;

			Enumeration fallos = resultados.failures();

			for (; fallos.hasMoreElements();) {
				falla = (TestFailure) fallos.nextElement();

				log.debug("Falla [" + falla.failedTest() + "] fallo ["
						+ falla.trace() + "]");
			}

			Enumeration errores = resultados.errors();

			for (; errores.hasMoreElements();) {
				falla = (TestFailure) errores.nextElement();

				log.debug("Error [" + falla.failedTest() + "] fallo ["
						+ falla.trace() + "]");
			}
		}

	}

}
